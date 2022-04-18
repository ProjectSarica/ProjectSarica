package me.ci.project.mio.world;

import net.minecraft.util.math.shapes.VoxelShape;

public interface IBlockState
{
	VoxelShape getCollision();

	String getProperty(String property);

	String getRegistryName();

	boolean hasProperty(String property);
}
