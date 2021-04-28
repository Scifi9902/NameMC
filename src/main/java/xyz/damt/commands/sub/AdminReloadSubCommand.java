package xyz.damt.commands.sub;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.NameMC;

public class AdminReloadSubCommand extends xyz.damt.commands.framework.SubCommand {

    private final NameMC nameMC;

    public AdminReloadSubCommand(NameMC nameMC) {
        super("reload", nameMC.getConfig().getString("admin.verify.access"),
                "/namemc reload", "");

        this.nameMC = nameMC;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + getUsage());
            return;
        }

        nameMC.getConfig().reload();
        nameMC.getMessages().reload();

        sender.sendMessage(nameMC.getMessages().getString("messages.admin.reload-message"));
    }
}
