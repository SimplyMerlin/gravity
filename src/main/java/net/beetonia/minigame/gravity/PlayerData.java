package net.beetonia.minigame.gravity;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.UUID;

public class PlayerData {

    public int points = 0;
    public int gamesPlayed = 0;
    public int victories = 0;
    public int winStreak = 0;
    public int totalFails = 0;
    public int hardcoreVictories = 0;
    public int hardcorePoints = 0;
    GamePlayer gamePlayer;
    UUID uuid;
    MongoCollection<Document> collection;

    public PlayerData(GamePlayer gamePlayer) {
        this.uuid = gamePlayer.getPlayer().getUniqueId();
        this.gamePlayer = gamePlayer;
        this.collection = Gravity.getPlayerDataCollection();
        Bukkit.getServer().getScheduler().runTaskAsynchronously(Gravity.getInstance(), this::initializeData);
    }

    private void initializeData() {
        Document playerdoc = new Document("UUID", uuid.toString());
        Document found = collection.find(playerdoc).first();
        if (found == null) {
            System.out.println("New player, " + uuid);
            generatePlayerDataDocument(playerdoc);

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

    private void saveData() {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(Gravity.getInstance(), () -> {
            Document document = new Document("UUID", uuid.toString());
            generatePlayerDataDocument(document);
            collection.updateOne(Filters.eq("UUID", uuid.toString()), document);
        });
    }

    private void generatePlayerDataDocument(Document document) {
        document.append("points", points);
        document.append("gamesPlayed", gamesPlayed);
        document.append("victories", victories);
        document.append("winStreak", winStreak);
        document.append("totalFails", totalFails);

        document.append("hardcoreVictories", hardcoreVictories);
        document.append("hardcorePoints", hardcorePoints);
    }

}
