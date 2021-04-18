package dev.jbull.corev.api.utils;

import dev.jbull.corev.Core;
import dev.jbull.corev.api.CoreV;
import dev.jbull.corev.player.nameUuid.INameUuidFetcher;
import dev.jbull.corev.player.nameUuid.NameUuidFetcher;

import java.util.UUID;

public class NameUUID  {
    private NameUuidFetcher nameUuidFetcher;

    public String getName(UUID uuid) {
        return nameUuidFetcher.getName(uuid);
    }

    public UUID getUUID(String name) {
        return nameUuidFetcher.getUUID(name);
    }

    public boolean hasPlayedOnNetwork(UUID uuid) {
        return nameUuidFetcher.hasPlayedOnNetwork(uuid);
    }

    public boolean hasPlayedOnNetwork(String name) {
        return nameUuidFetcher.hasPlayedOnNetwork(name);
    }

    public boolean isUpdated(UUID uuid, String name) {
        return nameUuidFetcher.isUpdated(uuid, name);
    }

    public void insert(UUID uuid, String name) {
        nameUuidFetcher.update(uuid, name);
    }

    public void update(UUID uuid, String name) {

    }
}
