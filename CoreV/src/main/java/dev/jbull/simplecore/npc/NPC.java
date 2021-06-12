package dev.jbull.simplecore.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.jbull.simplecore.loader.CoreSpigot;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NPC {
    private PacketPlayOutPlayerInfo playerInfo;
    private PacketPlayOutNamedEntitySpawn entitySpawn;
    private PacketPlayOutEntityMetadata packet;
    private PacketPlayOutPlayerInfo packetPlayOutPlayerInfo;
    private PacketPlayOutEntityHeadRotation headRotation;
    private PacketPlayOutEntity.PacketPlayOutEntityLook packetPlayOutEntity;

    public NPC(Location location, String texture, String signature, String name, String id){
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld)location.getWorld()).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);
        gameProfile.getProperties().put("textures", new Property("textures", texture, signature));
        EntityPlayer npc = new EntityPlayer(server, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        playerInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc);
        entitySpawn = new PacketPlayOutNamedEntitySpawn(npc);
        packet = new PacketPlayOutEntityMetadata(npc.getId(), npc.getDataWatcher(), true);
        packetPlayOutPlayerInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc);
        headRotation = new PacketPlayOutEntityHeadRotation(npc, (byte)(location.getYaw() * 256 / 360));
        packetPlayOutEntity = new PacketPlayOutEntity.PacketPlayOutEntityLook(npc.getId(), (byte)(location.getYaw() * 256 / 360), (byte)(location.getPitch() * 256 / 360), true);
    }

    public void spawn(Player player){


            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(playerInfo);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(entitySpawn);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(headRotation);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutEntity);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
            Bukkit.getScheduler().scheduleSyncDelayedTask(CoreSpigot.getInstance(), () -> ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutPlayerInfo), 5);

    }
}
