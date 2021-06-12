package dev.jbull.simplecore.maps;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class VoidGenerator extends ChunkGenerator {
    int currentHeight = 50;

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);
        int z = chunkZ;
        int x = chunkX;
        for (int i = 1; i <= 16; i++){
            for (int ii = 1; ii <= 16; ii++){
                for (int y = 0; y <= 3; y++){
                    chunk.setBlock(x, y, z, Material.AIR);
                }
                z++;
            }
            z = chunkZ;
            x++;
        }
        return chunk;
    }
}
