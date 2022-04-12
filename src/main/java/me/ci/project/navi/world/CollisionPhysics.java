package me.ci.project.navi.world;

import java.util.ArrayList;
import java.util.List;
import me.ci.project.navi.util.CuboidIterator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;

public final class CollisionPhysics
{
	private static double computeOffsetX(AxisAlignedBB block, AxisAlignedBB entity, double offsetX)
	{

		if (entity.maxY > block.minY
			&& entity.minY < block.maxY
			&& entity.maxZ > block.minZ
			&& entity.minZ < block.maxZ)
		{

			if (offsetX > 0.0
				&& entity.maxX <= block.minX)
			{
				offsetX = Math.min(block.minX - entity.maxX, offsetX);
			}
			else if (offsetX < 0.0
				&& entity.minX >= block.maxX)
			{
				offsetX = Math.max(block.maxX - entity.minX, offsetX);
			}

		}

		return offsetX;
	}


	private static double computeOffsetY(AxisAlignedBB block, AxisAlignedBB entity, double offsetY)
	{

		if (entity.maxX > block.minX
			&& entity.minX < block.maxX
			&& entity.maxZ > block.minZ
			&& entity.minZ < block.maxZ)
		{

			if (offsetY > 0.0
				&& entity.maxY <= block.minY)
			{
				offsetY = Math.min(block.minY - entity.maxY, offsetY);
			}
			else if (offsetY < 0.0
				&& entity.minY >= block.maxY)
			{
				offsetY = Math.max(block.maxY - entity.minY, offsetY);
			}

		}

		return offsetY;
	}


	private static double computeOffsetZ(AxisAlignedBB block, AxisAlignedBB entity, double offsetZ)
	{

		if (entity.maxX > block.minX
			&& entity.minX < block.maxX
			&& entity.maxY > block.minY
			&& entity.minY < block.maxY)
		{

			if (offsetZ > 0.0
				&& entity.maxZ <= block.minZ)
			{
				offsetZ = Math.min(block.minZ - entity.maxZ, offsetZ);
			}
			else if (offsetZ < 0.0
				&& entity.minZ >= block.maxZ)
			{
				offsetZ = Math.max(block.maxZ - entity.minZ, offsetZ);
			}

		}

		return offsetZ;
	}


	public static AxisAlignedBB getAABBAtPos(AxisAlignedBB bounds, Vector3d position)
	{
		double sizeX = bounds.getXsize() / 2;
		double sizeY = bounds.getYsize();
		double sizeZ = bounds.getZsize() / 2;
		return new AxisAlignedBB(position.x - sizeX, position.y, position.z - sizeZ, position.x + sizeX, position.y + sizeY, position.z + sizeZ);
	}


	private static List<AxisAlignedBB> getSurroundingBBs(IWorldContext worldContext, AxisAlignedBB query)
	{
		List<AxisAlignedBB> surroundingBB = new ArrayList<>();

		for (BlockPos pos : CuboidIterator.fromAABB(query))
		{
			VoxelShape shape = worldContext.getBlock(pos).getCollisionShape(worldContext.getMCWorld(), pos);
			shape.forAllBoxes((s0, s1, s2, s3, s4, s5) -> surroundingBB.add(new AxisAlignedBB(s0, s1, s2, s3, s4, s5)));
		}

		return surroundingBB;
	}


	private static Vector3d linearCollisionShift(IWorldContext worldContext, AxisAlignedBB aabb, Vector3d shift)
	{
		AxisAlignedBB queryAabb = aabb.expandTowards(shift).expandTowards(0, -1, 0);
		List<AxisAlignedBB> surroundingBB = getSurroundingBBs(worldContext, queryAabb);

		for (AxisAlignedBB blockBB : surroundingBB)
			shift = new Vector3d(shift.x, computeOffsetY(blockBB, aabb, shift.y), shift.z);

		for (AxisAlignedBB blockBB : surroundingBB)
			shift = new Vector3d(computeOffsetX(blockBB, aabb, shift.x), shift.y, shift.z);

		for (AxisAlignedBB blockBB : surroundingBB)
			shift = new Vector3d(shift.x, shift.y, computeOffsetZ(blockBB, aabb, shift.z));

		return shift;
	}


	public static Vector3d simulateWalk(IWorldContext worldContext, AxisAlignedBB aabb, Vector3d shift, double stepHeight)
	{
		Vector3d preColShift = shift;
		shift = linearCollisionShift(worldContext, aabb, shift);
		return stepCollisionShift(worldContext, aabb, shift, preColShift, stepHeight);
	}


	private static Vector3d stepCollisionShift(IWorldContext worldContext, AxisAlignedBB aabb, Vector3d shift, Vector3d preColShift, double stepHeight)
	{
		shift = new Vector3d(shift.x, stepHeight, shift.z);
		AxisAlignedBB queryBB = aabb.expandTowards(preColShift.x, shift.y, preColShift.z);
		List<AxisAlignedBB> surroundingBB = getSurroundingBBs(worldContext, queryBB);

		AxisAlignedBB bb1 = aabb;
		AxisAlignedBB bb2 = aabb;
		AxisAlignedBB bbXZ = aabb.expandTowards(shift.x, 0, shift.z);

		double dy1 = shift.y;
		double dy2 = shift.y;

		for (AxisAlignedBB blockBB : surroundingBB)
		{
			dy1 = computeOffsetY(blockBB, bbXZ, dy1);
			dy2 = computeOffsetY(blockBB, bb2, dy2);
		}

		bb1.move(0, dy1, 0);
		bb2.move(0, dy2, 0);

		double dx1 = shift.x;
		double dx2 = shift.x;

		for (AxisAlignedBB blockBB : surroundingBB)
		{
			dx1 = computeOffsetX(blockBB, bb1, dx1);
			dx2 = computeOffsetX(blockBB, bb2, dx2);
		}

		bb1.move(dx1, 0, 0);
		bb2.move(dx2, 0, 0);

		double dz1 = shift.z;
		double dz2 = shift.z;

		for (AxisAlignedBB blockBB : surroundingBB)
		{
			dz1 = computeOffsetY(blockBB, bb1, dz1);
			dz2 = computeOffsetY(blockBB, bb2, dz2);
		}

		bb1.move(0, 0, dz1);
		bb2.move(0, 0, dz2);

		double norm1 = dx1 * dx1 + dz1 * dz1;
		double norm2 = dx2 * dx2 + dz2 * dz2;

		if (norm1 > norm2)
		{
			shift = new Vector3d(dx1, -dy1, dz1);
			aabb = bb1;
		}
		else
		{
			shift = new Vector3d(dx2, -dy2, dz2);
			aabb = bb2;
		}

		for (AxisAlignedBB blockBB : surroundingBB)
			shift = new Vector3d(shift.x, computeOffsetY(blockBB, aabb, shift.y), shift.z);

		return shift;
	}


	private CollisionPhysics()
	{}
}
