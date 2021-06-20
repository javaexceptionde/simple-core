package dev.jbull.simplecore.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import dev.jbull.simplecore.events.NpcClickEvent;
import dev.jbull.simplecore.loader.CoreSpigot;
import org.bukkit.Bukkit;

public class EntityUseListener {

    public void startCheck(){
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(CoreSpigot.getInstance(), ListenerPriority.HIGH, PacketType.Play.Client.USE_ENTITY){
            public void onPacketReceiving(PacketEvent event){
                if(event.getPacketType() == PacketType.Play.Client.USE_ENTITY){
                    PacketContainer packet = event.getPacket();
                    int id = packet.getIntegers().read(0);
                    Bukkit.getPluginManager().callEvent(new NpcClickEvent(id, event.getPlayer()));
                }
            }
        });
    }
}
