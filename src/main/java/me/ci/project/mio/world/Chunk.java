package me.ci.project.mio.world;

import java.util.Arrays;
import net.minecraft.util.math.BlockPos;

public class Chunk
{
	private final BlockState[] blocks = new BlockState[16 * 16 * 16];


	Chunk()
	{
		BlockState voidBlock = BlockStateRegistry.getBlockState("projectmio:void");
		Arrays.fill(this.blocks, voidBlock);
	}


	private int blockPosIndex(BlockPos blockPos)
	{
		int x = blockPos.getX() & 15;
		int y = blockPos.getY() & 15;
		int z = blockPos.getZ() & 15;
		return x * 16 * 16 + y * 16 + z;
	}


	BlockState getBlock(BlockPos blockPos)
	{
		int index = blockPosIndex(blockPos);
		return this.blocks[index];
	}


	void setBlock(BlockPos blockPos, BlockState block)
	{
		int index = blockPosIndex(blockPos);
		this.blocks[index] = block;
	}
}
