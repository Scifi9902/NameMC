package xyz.damt.commands.sub;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.NameMC;
import xyz.damt.api.events.AdminRemoveAllVerifyEvent;
import xyz.damt.handlers.VerificationHandler;

public class AdminListSubCommand extends xyz.damt.commands.framework.SubCommand {

    private final NameMC nameMC;

    private final VerificationHandler verificationHandler;

    public AdminListSubCommand(NameMC nameMC) {
        super("list", nameMC.getConfig().getString("admin.verify.access"),
                "/namemc list", "");

        this.nameMC = nameMC;
        this.verificationHandler = this.nameMC.getHandlerManager().getHandler(VerificationHandler.class);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + getUsage());
            return;
        }

        if (verificationHandler.getLikedUsers().isEmpty()) {
            sender.sendMessage(nameMC.getMessages().getString("messages.admin.no-verifications"));
            return;
        }

        StringBuilder builder = new StringBuilder();

        verificationHandler.getLikedUsers().forEach(uuid -> builder
                .append(nameMC.getServer().getOfflinePlayer(uuid).getName())
                .append(", "));

        sender.sendMessage(nameMC.getMessages().getString("messages.admin.list-message").replace("{users}", builder.toString()));
    }
}
