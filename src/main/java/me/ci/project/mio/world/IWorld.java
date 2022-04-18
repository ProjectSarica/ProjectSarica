package me.ci.project.mio.world;

import net.minecraft.util.math.BlockPos;

public interface IWorld
{
	IBlockState getBlock(BlockPos blockPos);
}
