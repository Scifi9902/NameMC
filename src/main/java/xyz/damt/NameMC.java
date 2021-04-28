package xyz.damt;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.api.NameMCAPI;
import xyz.damt.commands.NameMCCommand;
import xyz.damt.commands.VerifyCommand;
import xyz.damt.commands.framework.BaseCommand;
import xyz.damt.handler.HandlerManager;
import xyz.damt.handler.IHandler;
import xyz.damt.handlers.VerificationHandler;
import xyz.damt.placeholder.PlaceHolderHook;
import xyz.damt.util.CC;
import xyz.damt.util.Config;

import java.util.Arrays;

@Getter
public final class NameMC extends JavaPlugin {

    private NameMCAPI nameMCAPI;
    private HandlerManager handlerManager;
    private final Config config = new Config("config", this);
    private final Config messages = new Config("messages", this);

    @Override
    public void onEnable() {
        this.handlerManager = new HandlerManager();

        this.handlerManager.registerHandler(new VerificationHandler(this));

        this.nameMCAPI = new NameMCAPI(this);

        Arrays.asList(
                new NameMCCommand(this),
                new VerifyCommand(this)
        ).forEach(BaseCommand::register);

        if (this.config.getBoolean("settings.placeholder-api")) {
            if (new PlaceHolderHook(this).register()) {
                getServer().getConsoleSender().sendMessage(CC.translate("&7[&dNameMC&7] &aHooked up with PlaceHolderAPI"));
                getServer().getConsoleSender().sendMessage(CC.translate("&aAvailable PlaceHolders &7- &f%namemc_verified%,"));
            } else {
                getServer().getConsoleSender().sendMessage(CC.translate("&7[&dNameMC&7] &cFailed to hook up with PlaceHolderAPI"));
            }
        }

        this.getServer().getConsoleSender().sendMessage(CC.translate("&6NameMC | Verification plugin loaded"));
        this.getServer().getConsoleSender().sendMessage(CC.translate("&aCoded by damt"));
    }

    @Override
    public void onDisable() {
        this.getHandlerManager().getHandlers().forEach(IHandler::unload);
    }

}
