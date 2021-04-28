package xyz.damt.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.NameMC;
import xyz.damt.commands.framework.BaseCommand;
import xyz.damt.commands.sub.*;
import xyz.damt.util.CC;

import javax.print.attribute.standard.JobName;

public class NameMCCommand extends BaseCommand {

    private final NameMC nameMC;

    public NameMCCommand(NameMC nameMC) {
        super(nameMC, "namemc", nameMC.getConfig().getString("admin.verify.access"), "/namemc");

        this.nameMC = nameMC;
        this.playerOnly = false;

        this.getSubCommands().add(new AdminStatusSubCommand(nameMC));
        this.getSubCommands().add(new AdminAddSubCommand(nameMC));
        this.getSubCommands().add(new AdminRemoveSubCommand(nameMC));
        this.getSubCommands().add(new AdminListSubCommand(nameMC));
        this.getSubCommands().add(new AdminRemoveAllSubCommand(nameMC));
        this.getSubCommands().add(new AdminReloadSubCommand(nameMC));

    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        CC.translate(this.nameMC.getMessages().getStringList("messages.admin.wrong-usage"))
                .forEach(sender::sendMessage);
    }
}
