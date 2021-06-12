package dev.jbull.simplecore.npc;

import org.bukkit.Location;

import java.util.ArrayList;

public interface INPCProvider {

    public void spawnNPC(String texture, String signature, Location location, String name, String id);

    public void destroy(String id);
    public ArrayList<NPC> getNpcs();
}
