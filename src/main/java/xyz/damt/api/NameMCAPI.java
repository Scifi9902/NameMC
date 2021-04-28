package xyz.damt.api;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.NameMC;
import xyz.damt.handlers.VerificationHandler;

import java.util.UUID;

public class NameMCAPI {

    private final VerificationHandler verificationHandler;

    public NameMCAPI(NameMC nameMC) {
        this.verificationHandler = nameMC.getHandlerManager().getHandler(VerificationHandler.class);
    }

    public void addVerify(UUID uuid) {
        verificationHandler.containsUser(uuid);
    }

    public void removeVerify(UUID uuid) {
        verificationHandler.getLikedUsers().remove(uuid);
    }

    public boolean isVerified(UUID uuid) {
        return verificationHandler.getLikedUsers().contains(uuid);
    }

}
