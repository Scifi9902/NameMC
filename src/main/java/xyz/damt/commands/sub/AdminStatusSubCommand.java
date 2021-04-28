package xyz.damt.commands.sub;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.NameMC;
import xyz.damt.handlers.VerificationHandler;

public class AdminStatusSubCommand extends xyz.damt.commands.framework.SubCommand {

    private final NameMC nameMC;

    private final VerificationHandler verificationHandler;

    public AdminStatusSubCommand(NameMC nameMC) {
        super("status", nameMC.getConfig().getString("admin.verify.access"),
                "/namemc status <user>", "");

        this.nameMC = nameMC;
        this.verificationHandler = this.nameMC.getHandlerManager().getHandler(VerificationHandler.class);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + getUsage());
            return;
        }

        if (args[1].isEmpty()) {
            sender.sendMessage(this.nameMC.getMessages().getString("messages.invalid-value"));
            return;
        }

        OfflinePlayer player = nameMC.getServer().getOfflinePlayer(args[1]);
        String status = verificationHandler.containsUser(player.getUniqueId()) ? "Verified" : "Not Verified";

        sender.sendMessage(nameMC.getMessages().getString("messages.admin.status-message").replace("{user}", player.getName())
        .replace("{status}", status));
    }
}
