package net.beetonia.minigame.gravity;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.UUID;

public class PlayerData {

    UUID uuid;
    MongoCollection<Document> collection;

    public int points = 0;
    public int gamesPlayed = 0;
    public int victories = 0;
    public int winStreak = 0;
    public int totalFails = 0;

    public int hardcoreVictories = 0;
    public int hardcorePoints = 0;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.collection = Gravity.getPlayerDataCollection();
        Bukkit.getServer().getScheduler().runTaskAsynchronously(Gravity.getInstance(), this::initializeData);
    }

    private void initializeData() {
        Document playerdoc = new Document("UUID", uuid.toString());
        Document found = (Document) collection.find(playerdoc).first();
        if (found == null) {
            System.out.println("New player, " + uuid);
            playerdoc.append("points", points);
            playerdoc.append("gamesPlayed", gamesPlayed);
            playerdoc.append("victories", victories);
            playerdoc.append("winStreak", winStreak);
            playerdoc.append("totalFails", totalFails);

            playerdoc.append("hardcoreVictories", hardcoreVictories);
            playerdoc.append("hardcorePoints", hardcorePoints);

            collection.insertOne(playerdoc);
            System.out.println("Finished initializing new player " + uuid);
        } else {
            points = found.getInteger("points");
            gamesPlayed = found.getInteger("gamesPlayed");
            victories = found.getInteger("victories");
            winStreak = found.getInteger("winStreak");
            totalFails = found.getInteger("totalFails");
            hardcoreVictories = found.getInteger("hardcoreVictories");
            hardcorePoints = found.getInteger("hardcorePoints");
        }
    }

}
