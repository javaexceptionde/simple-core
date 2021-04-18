package dev.jbull.corev.inventory.anvil;

import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class AnvilMap extends ConcurrentHashMap<Player, AnvilInventory> {
    private static AnvilMap instance;

    public static AnvilMap getInstance(){
        if (instance == null)instance = new AnvilMap();
        return instance;
    }

}
