/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.spatial;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class StorageCellBiome {

    public static final Biome INSTANCE = new Biome.Settings()
            .generationSettings(new GenerationSettings.Builder()
                    .surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.NOPE, SurfaceBuilder.STONE_CONFIG))
                    .build())
            .precipitation(Biome.Precipitation.NONE).category(Biome.Category.NONE).depth(0).scale(1)
            // Copied from the vanilla void biome
            .temperature(0.5F).downfall(0.5F)
            .effects(new BiomeEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(0).skyColor(0x111111).build())
            .parent(null)
            .spawnSettings(new SpawnSettings.Builder()
                    .creatureSpawnProbability(0)
                    .build())
            .build();

}
