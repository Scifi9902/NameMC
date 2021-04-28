package xyz.damt.commands.sub;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.NameMC;
import xyz.damt.api.events.AdminAddVerifyEvent;
import xyz.damt.api.events.AdminRemoveVerifyEvent;
import xyz.damt.handlers.VerificationHandler;

import java.util.Objects;

public class AdminRemoveSubCommand extends xyz.damt.commands.framework.SubCommand {

    private final NameMC nameMC;

    private final VerificationHandler verificationHandler;

    public AdminRemoveSubCommand(NameMC nameMC) {
        super("remove", nameMC.getConfig().getString("admin.verify.access"),
                "/namemc remove <user>", "");

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
            sender.sendMessage(nameMC.getMessages().getString("messages.invalid-value"));
            return;
        }

        OfflinePlayer player = nameMC.getServer().getOfflinePlayer(args[1]);

        if (!verificationHandler.containsUser(player.getUniqueId())) {
            sender.sendMessage(nameMC.getMessages().getString("messages.admin.is-not-verified").replace("{user}", Objects.requireNonNull(player.getName())));
            return;
        }

        nameMC.getServer().getPluginManager().callEvent(new AdminRemoveVerifyEvent(sender, player.getUniqueId()));
        verificationHandler.getLikedUsers().remove(player.getUniqueId());

        sender.sendMessage(nameMC.getMessages().getString("messages.admin.remove-message").replace("{user}", Objects.requireNonNull(player.getName())));
    }

}
