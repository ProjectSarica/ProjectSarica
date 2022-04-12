package me.ci.project.navi.algorithm;

import java.util.Objects;
import javax.annotation.Nullable;
import me.ci.project.navi.world.IWorldContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

/**
 * Represents a single point along a path.
 *
 * @author thedudefromci
 */
public class PathNode
{
	private final IWorldContext world;
	private final BlockPos position;
	private IMovement movement = null;
	private PathNode parent;
	private double entityY;
	float gScore = Float.POSITIVE_INFINITY;
	float fScore = Float.POSITIVE_INFINITY;


	PathNode(IWorldContext world, BlockPos position)
	{
		this.world = world;
		this.position = position;
		this.entityY = 0;
	}


	@Override
	public boolean equals(Object obj)
	{
		if (obj == null
			|| !(obj instanceof PathNode)) return false;

		// Just like with hashCode(), we are intentionally ignoring the movement type.

		PathNode node = (PathNode) obj;
		return this.world.equals(node.world)
			&& this.position.equals(node.position);
	}


	public double getEntityY()
	{
		return this.entityY;
	}


	public Vector3d getEstimatedEntityPosition()
	{
		return new Vector3d(this.position.getX() + 0.5, this.position.getY() + this.entityY, this.position
			.getZ() + 0.5);
	}


	@Nullable
	public IMovement getMovement()
	{
		return this.movement;
	}


	PathNode getParent()
	{
		return this.parent;
	}


	public BlockPos getPosition()
	{
		return this.position;
	}


	public IWorldContext getWorldContext()
	{
		return this.world;
	}


	@Override
	public int hashCode()
	{
		// Don't include movement type in hash.
		// We don't case how we got here, only where we are and the state the world is
		// in when we are here.

		return Objects.hash(this.world, this.position);
	}


	public void setEntityY(double entityY)
	{
		this.entityY = entityY;
	}


	void setParent(PathNode parent, IMovement movement)
	{
		this.parent = parent;
		this.movement = movement;
	}
}
