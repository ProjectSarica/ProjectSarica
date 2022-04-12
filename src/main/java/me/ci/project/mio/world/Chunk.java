package me.ci.project.mio.world;

import java.util.Arrays;
import org.joml.Vector3ic;
import me.ci.project.mio.math.VectorUtils;

public class Chunk
{
	private final BlockState[] blocks = new BlockState[16 * 16 * 16];
	private final Vector3ic chunkIndex;


	Chunk(Vector3ic chunkIndex)
	{
		this.chunkIndex = chunkIndex;

		BlockState voidBlock = BlockStateRegistry.getBlockState("projectmio:void");
		Arrays.fill(this.blocks, voidBlock);
	}


	BlockState getBlock(Vector3ic blockIndex)
	{
		int index = VectorUtils.blockPosToIndex(blockIndex);
		return this.blocks[index];
	}


	Vector3ic getChunkIndex()
	{
		return this.chunkIndex;
	}


	void setBlock(Vector3ic blockIndex, BlockState block)
	{
		int index = VectorUtils.blockPosToIndex(blockIndex);
		this.blocks[index] = block;
	}
}
