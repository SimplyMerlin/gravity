package net.beetonia.minigame.gravity.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import net.beetonia.minigame.gravity.state.ScheduledStateSeries;
import net.beetonia.minigame.gravity.util.Chat;
import org.bukkit.command.CommandSender;

@CommandPermission("beetonia.admin.skipphase")
@CommandAlias("freezephase|freeze")
public class FreezePhaseCommand extends BaseCommand {

    ScheduledStateSeries scheduledStateSeries;

    public FreezePhaseCommand(ScheduledStateSeries scheduledStateSeries) {
        this.scheduledStateSeries = scheduledStateSeries;
    }

    @Default
    public void onDefault(CommandSender sender) {
        scheduledStateSeries.setFrozen(!scheduledStateSeries.getFrozen());
        Chat.send(sender, scheduledStateSeries.getFrozen() ? "&bFroze &athe current phase!" : "&bUnfroze &athe current phase!");
    }

}
