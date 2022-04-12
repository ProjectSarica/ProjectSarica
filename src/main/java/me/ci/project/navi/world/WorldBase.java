package me.ci.project.navi.world;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldBase implements IWorldContext
{
	private final World world;
	private final Entity entity;


	public WorldBase(World world, Entity entity)
	{
		this.world = world;
		this.entity = entity;
	}


	@Override
	public BlockState getBlock(BlockPos pos)
	{
		return this.world.getBlockState(pos);
	}


	public Entity getEntity()
	{
		return this.entity;
	}


	@Override
	public World getMCWorld()
	{
		return this.world;
	}


	@Override
	public int hashCode()
	{
		return this.world.hashCode();
	}
}
