package dev.jbull.simplecore.maps;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class MapLoader implements IMapLoader {
    @Override
    public void loadMap(String name, MapType type) {
        if (type.equals(MapType.NORMAL)) {
            WorldCreator wc = new WorldCreator(name);
            wc.type(WorldType.NORMAL);
            org.bukkit.World world = Bukkit.createWorld(wc);
            if (!Bukkit.getWorlds().contains(world)){
                Bukkit.getWorlds().add(world);
            }
            world.setDifficulty(Difficulty.PEACEFUL);
            world.setAutoSave(true);
            world.setTime(1000);
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("doMobSpawning", "false");
        }else if (type.equals(MapType.FLAT)){
            WorldCreator wc = new WorldCreator(name);
            wc.type(WorldType.FLAT);
            org.bukkit.World world = Bukkit.createWorld(wc);
            if (!Bukkit.getWorlds().contains(world)){
                Bukkit.getWorlds().add(world);
            }
            world.setDifficulty(Difficulty.PEACEFUL);
            world.setAutoSave(true);
            world.setTime(1000);
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("doMobSpawning", "false");
        } else if (type.equals(MapType.VOID)) {
            WorldCreator wc = new WorldCreator(name);
            wc.type(WorldType.FLAT);
            wc.generateStructures(false);
            wc.generator(new VoidGenerator());
            org.bukkit.World world = Bukkit.createWorld(wc);
            if (!Bukkit.getWorlds().contains(world)){
                Bukkit.getWorlds().add(world);
            }
            world.setDifficulty(Difficulty.PEACEFUL);
            world.setAutoSave(true);
            world.setTime(1000);
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("doMobSpawning", "false");
        }
    }
}
