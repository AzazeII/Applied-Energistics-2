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

package appeng.client.render.model;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;

import appeng.api.client.ICellModelRegistry;
import appeng.client.render.BasicUnbakedModel;
import appeng.core.Api;
import appeng.core.api.client.ApiCellModelRegistry;

public class DriveModel implements BasicUnbakedModel<DriveModel> {

    private static final ResourceLocation MODEL_BASE = new ResourceLocation(
            "appliedenergistics2:block/drive/drive_base");
    private static final ResourceLocation MODEL_CELL_EMPTY = new ResourceLocation(
            "appliedenergistics2:block/drive/drive_cell_empty");

    @Override
    public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery,
            Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform,
            ItemOverrideList overrides, ResourceLocation modelLocation) {
        final ICellModelRegistry cellRegistry = Api.instance().client().cells();
        final Map<Item, IBakedModel> cellModels = new IdentityHashMap<>();

        // Load the base model and the model for each cell model.
        for (Entry<Item, ResourceLocation> entry : cellRegistry.models().entrySet()) {
            IBakedModel cellModel = bakery.getBakedModel(entry.getValue(), modelTransform, spriteGetter);
            cellModels.put(entry.getKey(), cellModel);
        }

        final IBakedModel baseModel = bakery.getBakedModel(MODEL_BASE, modelTransform, spriteGetter);
        final IBakedModel defaultCell = bakery.getBakedModel(cellRegistry.getDefaultModel(), modelTransform,
                spriteGetter);
        cellModels.put(Items.AIR, bakery.getBakedModel(MODEL_CELL_EMPTY, modelTransform, spriteGetter));

        return new DriveBakedModel(baseModel, cellModels, defaultCell);
    }

    @Override
    public Collection<ResourceLocation> getModelDependencies() {
        ICellModelRegistry cells = Api.instance().client().cells();
        return ImmutableSet.<ResourceLocation>builder().add(cells.getDefaultModel())
                .addAll(ApiCellModelRegistry.getModels()).addAll(cells.models().values()).build();
    }

}
