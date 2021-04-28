package xyz.damt.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.NameMC;
import xyz.damt.handlers.VerificationHandler;

public class PlaceHolderHook extends PlaceholderExpansion {

    private final NameMC nameMC;

    public PlaceHolderHook(NameMC nameMC) {
        this.nameMC = nameMC;
    }

    @Override
    public String getIdentifier() {
        return "namemc";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "damt";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        if (params.equalsIgnoreCase("verified")) {
            return nameMC.getHandlerManager().getHandler(VerificationHandler.class).containsUser(p.getUniqueId()) ? "Yes" : "No";
        }
        return null;
    }
}
