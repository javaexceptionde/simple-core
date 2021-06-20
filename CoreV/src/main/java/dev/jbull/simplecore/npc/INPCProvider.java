package dev.jbull.simplecore.npc;

import org.bukkit.Location;

import java.util.ArrayList;

public interface INPCProvider {

    /**
     * Spawns an NPC for all Players online on the Spigot Server and for all future players
     * @param texture The value of the Texture. You can get it from the Mojang API
     * @param signature The Signature of the Texture. You can get it from the Mojang API
     * @param location The Location where the NPC should be spawned
     * @param name The Display Name of the NPC
     * @param id The ID of the NPC it must be unique
     */
    public void spawnNPC(String texture, String signature, Location location, String name, String id);

    public void destroy(String id);
    public ArrayList<NPC> getNpcs();
}
