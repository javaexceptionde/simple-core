package dev.jbull.simplecore.listener;

import dev.jbull.simplecore.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.xml.bind.Marshaller;

public class NpcSpawnListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Core.getInstance().getNpcProvider().getNpcs().forEach(npc -> {
            npc.spawn(event.getPlayer());
        });
    }
}
