/*
 * Copyright  (c) 2021.  Jonathan Bull Contact at jonathan@jbull.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    private final PacketPlayOutPlayerInfo playerInfo;
    private final PacketPlayOutNamedEntitySpawn entitySpawn;
    private final PacketPlayOutEntityMetadata packet;
    private final PacketPlayOutPlayerInfo packetPlayOutPlayerInfo;
    private final PacketPlayOutEntityHeadRotation headRotation;
    private final PacketPlayOutEntity.PacketPlayOutEntityLook packetPlayOutEntity;
    private final EntityPlayer npc;

    public NPC(Location location, String texture, String signature, String name, String id){
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld)location.getWorld()).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);
        gameProfile.getProperties().put("textures", new Property("textures", texture, signature));
        npc = new EntityPlayer(server, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        playerInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc);
        entitySpawn = new PacketPlayOutNamedEntitySpawn(npc);
        packet = new PacketPlayOutEntityMetadata(npc.getId(), npc.getDataWatcher(), true);

        headRotation = new PacketPlayOutEntityHeadRotation(npc, (byte)(location.getYaw() * 256 / 360));
        packetPlayOutEntity = new PacketPlayOutEntity.PacketPlayOutEntityLook(npc.getId(), (byte)(location.getYaw() * 256 / 360), (byte)(location.getPitch() * 256 / 360), true);
        packetPlayOutPlayerInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc);
    }

    public void spawn(Player player){


            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(playerInfo);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(entitySpawn);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(headRotation);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutEntity);

            Bukkit.getScheduler().scheduleSyncDelayedTask(CoreSpigot.getInstance(), () -> ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetPlayOutPlayerInfo), 5);

    }

    public int getId(){
        return npc.getId();
    }

    public EntityPlayer getNpc() {
        return npc;
    }
}
