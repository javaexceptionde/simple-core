package dev.jbull.corev.player.nameUuid;

import java.util.UUID;

public interface INameUuidFetcher {

    public String getName(UUID uuid);

    public UUID getUUID(String name);

    public boolean hasPlayedOnNetwork(UUID uuid);

    public boolean hasPlayedOnNetwork(String name);

    public boolean isUpdated(UUID uuid, String name);

    public void insert(UUID uuid, String name);

    public void update(UUID uuid, String name);
}
