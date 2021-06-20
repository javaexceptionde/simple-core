package dev.jbull.simplecore.events;

import dev.jbull.simplecore.Core;
import dev.jbull.simplecore.loader.CoreSpigot;
import dev.jbull.simplecore.npc.NPC;
import net.minecraft.server.v1_8_R3.PacketPlayOutKeepAlive;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class NpcClickEvent extends CustomEvent {
    private String name;
    private int id;
    private NPC npc;
    private Player player;

    public NpcClickEvent(int id, Player player){
        Core.getInstance().getNpcProvider().getNpcs().forEach(npc1 -> {
            if (npc1.getId() == id)this.npc = npc1;
        });
        this.player = player;
    }

    public NPC getNpc() {
        return npc;
    }

    public Player getPlayer() {
        return player;
    }
}
