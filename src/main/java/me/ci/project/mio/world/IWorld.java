package me.ci.project.mio.world;

import net.minecraft.util.math.BlockPos;

/**
 * Represents a collection of block states along an infinite 3D grid.
 *
 * @author thedudefromci
 */
public interface IWorld
{
	/**
	 * Gets the current block state at the given block position.
	 *
	 * @param blockPos
	 *            - The position of the block.
	 *
	 * @return The current block state at that position.
	 */
	IBlockState getBlock(BlockPos blockPos);

	/**
	 * Sets a new block state to the given block position. This will automatically
	 * load an empty chunk as needed.
	 *
	 * @param blockPos
	 *            - The position of the block.
	 * @param blockState
	 *            - The new state of the block.
	 */
	void setBlock(BlockPos blockPos, IBlockState blockState);

	/**
	 * Uploads a 16*16*16 region of block states for the given chunk position. This
	 * will automatically load an empty chunk to fill as needed.<br/>
	 * <br/>
	 * Note that the block states array is stored with the indexing system:
	 *
	 * <pre>
	 * x * 256 + y * 16 + z
	 * </pre>
	 *
	 * @param chunkPos
	 *            - The position of the chunk in chunk coordinates.
	 * @param blockStates
	 *            - An array of block states for till within the chunk.
	 */
	void setChunk(BlockPos chunkPos, IBlockState[] blockStates);

	/**
	 * Unloads all block data for the chunk at the given chunk position.
	 *
	 * @param chunkPos
	 *            - The position of the chunk in chunk coordinates.
	 */
	void unloadChunk(BlockPos chunkPos);
}
