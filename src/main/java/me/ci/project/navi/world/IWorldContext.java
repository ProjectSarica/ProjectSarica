package me.ci.project.navi.world;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;

// TODO: Extend IBlockReader?
public interface IWorldContext
{
	/**
	 * Gets the block state at the given position. This block state is represented
	 * by the theorectial block state at the given location within this context and
	 * may not reflect the actual block in the actual world.
	 *
	 * @param pos
	 *            - The block position.
	 *
	 * @return The block state.
	 */
	BlockState getBlock(BlockPos pos);


	/**
	 * Gets the ceiling collision level relative to the given block position. If the
	 * given block position has a solid block in it, for example, this method would
	 * return 0. If there are two empty blocks followed by an upper half slab, this
	 * method would return 2.5.
	 *
	 * @param pos
	 *            - The block position to check.
	 * @param maxBlocksVertically
	 *            - The maximum number of blocks vertically to check before assuming
	 *            there is no ceiling.
	 *
	 * @return The relative ceiling level, or Double.POSITIVE_INFINITY if there are
	 *         no solid blocks found above the given position.
	 */
	default double getCeilingLevelAt(BlockPos pos, int maxBlocksVertically)
	{
		World world = getMCWorld();
		double vertOffset = 0;

		for (int i = 0; i < maxBlocksVertically; i++)

		{
			BlockState posBlock = getBlock(pos);
			VoxelShape posCollision = posBlock.getCollisionShape(world, pos);
			double posMaxY = posCollision.min(Axis.Y);
			if (Double.isFinite(posMaxY)) return posMaxY + vertOffset;

			vertOffset++;
			pos = pos.relative(Direction.UP);
		}

		return Double.POSITIVE_INFINITY;
	}


	/**
	 * Gets the entity that this world context is acting on.
	 *
	 * @return The entity.
	 */
	Entity getEntity();


	/**
	 * Gets the ground collision level relative to the given block position. If the
	 * given block position has a solid block in it, for example, this method would
	 * return 1. If the block below the given position is a lower half slab, this
	 * method would return -0.5.
	 *
	 * @param pos
	 *            - The block position to check.
	 * @param maxBlocksVertically
	 *            - The maximum number of blocks vertically to check before assuming
	 *            there is no ground.
	 *
	 * @return The relative ceiling level, or Double.NEGATIVE_INFINITY if there are
	 *         no solid blocks found below the given position.
	 */
	default double getGroundLevelAt(BlockPos pos, int maxBlocksVertically)
	{
		World world = getMCWorld();
		double vertOffset = 0;

		for (int i = 0; i < maxBlocksVertically; i++)

		{
			BlockState posBlock = getBlock(pos);
			VoxelShape posCollision = posBlock.getCollisionShape(world, pos);
			double posMaxY = posCollision.max(Axis.Y);
			if (Double.isFinite(posMaxY)) return posMaxY + vertOffset;

			vertOffset--;
			pos = pos.relative(Direction.DOWN);
		}

		return Double.NEGATIVE_INFINITY;
	}


	/**
	 * Gets the actual Minecraft world this context is built off of.
	 *
	 * @return The Minecraft world instance.
	 */
	World getMCWorld();
}
