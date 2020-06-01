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

package appeng.block.networking;


import net.minecraft.state.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import appeng.block.AEBaseTileBlock;
import appeng.helpers.AEGlassMaterial;


public class BlockEnergyCell extends AEBaseTileBlock
{

	public static final PropertyInteger ENERGY_STORAGE = PropertyInteger.create( "fullness", 0, 7 );

	@Override
	public int getMetaFromState( final BlockState state )
	{
		return state.getValue( ENERGY_STORAGE );
	}

	@Override
	public BlockState getStateFromMeta( final int meta )
	{
		return this.getDefaultState().with( ENERGY_STORAGE, Math.min( 7, Math.max( 0, meta ) ) );
	}

	public BlockEnergyCell()
	{
		super( AEGlassMaterial.INSTANCE );
	}

	@Override
	@OnlyIn( Dist.CLIENT )
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> itemStacks)
	{
		super.getSubBlocks( tabs, itemStacks );

		final ItemStack charged = new ItemStack( this, 1 );
        final CompoundNBT tag = charged.getOrCreateTag();
		tag.putDouble("internalCurrentPower", this.getMaxPower());
		tag.putDouble("internalMaxPower", this.getMaxPower());

		itemStacks.add( charged );
	}

	public double getMaxPower()
	{
		return 200000.0;
	}

	@Override
	protected IProperty[] getAEStates()
	{
		return new IProperty[] { ENERGY_STORAGE };
	}

}
