package xyz.damt.handlers;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.NameMC;
import xyz.damt.handler.IHandler;
import xyz.damt.util.CC;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Getter
public class VerificationHandler implements IHandler {

    private final Set<UUID> likedUsers;
    private final NameMC nameMC;


    public VerificationHandler(NameMC nameMC) {
        this.nameMC = nameMC;
        this.likedUsers = new HashSet<>();
    }

    public void load() {
        this.nameMC.getConfig().getStringList("data.liked").forEach(s -> likedUsers.add(UUID.fromString(s)));
    }

    public boolean containsUser(UUID uuid) {
        return this.likedUsers.contains(uuid);
    }

    public void giveRewards(Player player) {

        //ToDo clean-up
        List<String> places = new ArrayList<>();

        Objects.requireNonNull(nameMC.getConfig().getConfigurationSection("rewards")).getKeys(false).forEach(reward -> {
            if (!nameMC.getConfig().getBoolean("settings.randomize-rewards")) {

                String path = "rewards." + reward + ".";
                if (nameMC.getConfig().getBoolean(path + "item.enabled")) {
                    String itemInfo = nameMC.getConfig().getString(path + "item.info");
                    String[] args = itemInfo.split(":");

                    ItemStack stack = new ItemStack(Material.valueOf(args[0]), Integer.parseInt(args[1]), (byte) Integer.parseInt(args[2]));
                    ItemMeta meta = stack.getItemMeta();

                    meta.setDisplayName(CC.translate(args[3]));
                    meta.setLore(CC.translate(nameMC.getConfig().getStringList(path + "item.lore")));
                    nameMC.getConfig().getStringList(path + "item.enchants").forEach(s -> {
                        String[] enchants = s.split(":");
                        Enchantment enchant = Enchantment.getByName(enchants[0]);
                        if (enchant == null) return;
                        stack.addUnsafeEnchantment(enchant, Integer.parseInt(args[1]));
                    });
                    stack.setItemMeta(meta);
                    player.getInventory().addItem(stack);
                }

                if (nameMC.getConfig().getBoolean(path + "command.enabled")) {
                    List<String> commands = nameMC.getConfig().getStringList(path + "command.commands");
                    commands.forEach(string -> {
                        nameMC.getServer().dispatchCommand(nameMC.getServer().getConsoleSender(), string.replace("{player}", player.getName()));
                    });
                }
                return;
            }

            places.add("rewards." + reward);

            if (!places.isEmpty()) {
                for (int i = 0; i < nameMC.getConfig().getInt("settings.amount-of-rewards"); i++) {
                    int index = new Random().nextInt(places.size());

                    String item = places.get(index);
                    String path = item + ".";

                    if (nameMC.getConfig().getBoolean(path + "item.enabled")) {
                        String itemInfo = nameMC.getConfig().getString(path + "item.info");
                        String[] args = itemInfo.split(":");

                        ItemStack stack = new ItemStack(Material.valueOf(args[0]), Integer.parseInt(args[1]), (byte) Integer.parseInt(args[2]));
                        ItemMeta meta = stack.getItemMeta();

                        meta.setDisplayName(CC.translate(args[3]));
                        meta.setLore(CC.translate(nameMC.getConfig().getStringList(path + "item.lore")));
                        nameMC.getConfig().getStringList(path + "item.enchants").forEach(s -> {
                            String[] enchants = s.split(":");
                            Enchantment enchant = Enchantment.getByName(enchants[0]);
                            if (enchant == null) return;
                            stack.addUnsafeEnchantment(enchant, Integer.parseInt(args[1]));
                        });
                        stack.setItemMeta(meta);
                        player.getInventory().addItem(stack);
                    }

                    if (nameMC.getConfig().getBoolean(path + "command.enabled")) {
                        List<String> commands = nameMC.getConfig().getStringList(path + "command.commands");
                        commands.forEach(string -> {
                            nameMC.getServer().dispatchCommand(nameMC.getServer().getConsoleSender(), string.replace("{player}", player.getName()));
                        });
                    }
                }
            }
        });
    }

    public void removeAll() {
            likedUsers.clear();
            nameMC.getConfig().set("data.liked", null);
            nameMC.saveConfig();
    }

    public void save() {
            nameMC.getConfig().set("data.liked", likedUsers.stream().map(UUID::toString).collect(Collectors.toList()));
            nameMC.saveConfig();
    }
}
