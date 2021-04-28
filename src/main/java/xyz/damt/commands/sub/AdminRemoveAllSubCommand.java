package xyz.damt.commands.sub;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import xyz.damt.NameMC;
import xyz.damt.api.events.AdminRemoveAllVerifyEvent;
import xyz.damt.handler.impl.VerificationHandler;

public class AdminRemoveAllSubCommand extends xyz.damt.commands.framework.SubCommand {

    private final NameMC nameMC;

    private final VerificationHandler verificationHandler;

    public AdminRemoveAllSubCommand(NameMC nameMC) {
        super("removeall", nameMC.getConfig().getString("admin.verify.access"),
                "/namemc removeall", "");

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

        nameMC.getServer().getPluginManager().callEvent(new AdminRemoveAllVerifyEvent(sender));
        verificationHandler.removeAll();

        sender.sendMessage(nameMC.getMessages().getString("messages.admin.remove-all-message"));
    }
}
