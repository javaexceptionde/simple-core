package dev.jbull.corev.listener;

import dev.jbull.corev.Core;
import dev.jbull.corev.player.nameUuid.NameUuidFetcher;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private NameUuidFetcher nameUuidFetcher = Core.getInstance().getNameUuidFetcher();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (nameUuidFetcher.hasPlayedOnNetwork(player.getUniqueId())){
            nameUuidFetcher.insert(player.getUniqueId(), player.getName());
        }else {
            if (nameUuidFetcher.getName(player.getUniqueId()).equalsIgnoreCase(player.getName())){
                nameUuidFetcher.update(player.getUniqueId(), player.getName());
            }
        }
    }
}
