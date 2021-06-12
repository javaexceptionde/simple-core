package dev.jbull.simplecore.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.jbull.simplecore.loader.CoreSpigot;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import java.util.ArrayList;
import java.util.UUID;

public class NPCProvider implements INPCProvider {
    private ArrayList<NPC> npcs = new ArrayList<>();

    @Override
    public void spawnNPC(String texture, String signature, Location location, String name, String id) {
        NPC npc = new NPC(location, texture, signature, name, id);
        Bukkit.getOnlinePlayers().forEach(npc::spawn);
        npcs.add(npc);
    }

    @Override
    public void destroy(String id) {

    }

    @Override
    public ArrayList<NPC> getNpcs() {
        return npcs;
    }
}
