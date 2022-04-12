package me.ci.project.navi.util;

import java.util.Iterator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class CuboidIterator implements Iterator<BlockPos>
{
	public static Iterable<BlockPos> fromAABB(AxisAlignedBB aabb)
	{
		BlockPos min = new BlockPos(aabb.minX, aabb.minY, aabb.minZ);
		BlockPos max = new BlockPos(aabb.maxX, aabb.maxY, aabb.maxZ);
		return () -> new CuboidIterator(min, max);
	}


	public static Iterable<BlockPos> fromSize(int sizeX, int sizeY, int sizeZ)
	{
		BlockPos min = new BlockPos(0, 0, 0);
		BlockPos max = new BlockPos(sizeX, sizeY, sizeZ);
		return () -> new CuboidIterator(min, max);
	}


	private final BlockPos min;
	private final BlockPos max;
	private BlockPos index;


	private CuboidIterator(BlockPos min, BlockPos max)
	{
		this.min = min;
		this.max = max;
		this.index = new BlockPos(min.getX(), min.getY(), min.getZ());
	}


	@Override
	public boolean hasNext()
	{
		return this.index.getY() <= this.max.getY();
	}


	@Override
	public BlockPos next()
	{
		BlockPos toReturn = this.index;

		if (this.index.getX() < this.max.getX())
		{
			this.index = new BlockPos(this.index.getX() + 1, this.index.getY(), this.index.getZ());
		}
		else if (this.index.getZ() < this.max.getZ())
		{
			this.index = new BlockPos(this.min.getX(), this.index.getY(), this.index.getZ() + 1);
		}
		else
		{
			this.index = new BlockPos(this.min.getX(), this.index.getY() + 1, this.min.getZ());
		}

		return toReturn;
	}
}
