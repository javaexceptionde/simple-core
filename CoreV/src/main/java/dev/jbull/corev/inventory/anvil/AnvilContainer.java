package dev.jbull.corev.inventory.anvil;

import net.minecraft.server.v1_8_R3.*;

public class AnvilContainer extends ContainerAnvil {
    public AnvilContainer(EntityHuman entityhuman) {
        super(entityhuman.inventory, entityhuman.world, new BlockPosition(0, 0, 0), entityhuman);
    }

    @Override
    public boolean a(EntityHuman entityhuman) {
        return true;
    }
}
