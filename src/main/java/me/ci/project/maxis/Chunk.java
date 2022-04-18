package me.ci.project.maxis;

import java.util.Arrays;
import me.ci.project.mio.world.IBlockState;
import net.minecraft.util.math.BlockPos;

public class Chunk
{
	private final IBlockState[] blocks = new IBlockState[16 * 16 * 16];


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


	IBlockState getBlock(BlockPos blockPos)
	{
		int index = blockPosIndex(blockPos);
		return this.blocks[index];
	}


	void setBlock(BlockPos blockPos, IBlockState block)
	{
		int index = blockPosIndex(blockPos);
		this.blocks[index] = block;
	}


	void setBlocks(IBlockState[] blocks)
	{
		if (blocks.length != this.blocks.length) throw new IllegalArgumentException("Blocks array must equal 4096!");

		for (int i = 0; i < blocks.length; i++)
			this.blocks[i] = blocks[i];
	}
}
