package xyz.damt.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.NameMC;
import xyz.damt.api.events.PlayerVerifyEvent;
import xyz.damt.commands.framework.BaseCommand;
import xyz.damt.handlers.VerificationHandler;
import xyz.damt.request.Request;

import java.util.logging.Level;
import java.util.logging.Logger;

public class VerifyCommand extends BaseCommand {

    private final NameMC nameMC;

    private final VerificationHandler verificationHandler;

    public VerifyCommand(NameMC nameMC) {
        super(nameMC,"verify", nameMC.getConfig().getString("settings.verify-command-permission"),
                "/verify");

        this.nameMC = nameMC;
        this.verificationHandler = this.nameMC.getHandlerManager().getHandler(VerificationHandler.class);
        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Request request = new Request(player.getUniqueId(), nameMC.getConfig().getString("settings.server-ip"));

        if (verificationHandler.containsUser(player.getUniqueId())) {
            player.sendMessage(this.nameMC.getMessages().getString("messages.user.already-liked"));
            return;
        }

        if (!request.hasLiked()) {
            player.sendMessage(nameMC.getMessages().getString("messages.user.did-not-like"));
            return;
        }

        nameMC.getServer().getPluginManager().callEvent(new PlayerVerifyEvent(player));
        verificationHandler.getLikedUsers().add(player.getUniqueId());

        player.sendMessage(nameMC.getMessages().getString("messages.user.has-liked"));
        verificationHandler.giveRewards(player);
    }
}
