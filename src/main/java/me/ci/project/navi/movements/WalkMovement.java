package me.ci.project.navi.movements;

import java.util.List;
import me.ci.project.navi.algorithm.IMovement;
import me.ci.project.navi.algorithm.MovementType;
import me.ci.project.navi.algorithm.PathNode;
import me.ci.project.navi.algorithm.PathNodeContext;
import me.ci.project.navi.world.IWorldContext;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class WalkMovement implements IMovement
{
	/**
	 * @see {@link MovementType.IMovementFactory#getMovements(PathNode, PathNodeContext, List)}
	 */
	public static void getMovements(PathNode node, PathNodeContext nodeContext, List<IMovement> movements)
	{
		// tryMoveTowards(node, nodeContext, movements, new Vector3d(1, 0, 0));
		// tryMoveTowards(node, nodeContext, movements, new Vector3d(-1, 0, 0));
		// tryMoveTowards(node, nodeContext, movements, new Vector3d(0, 0, 1));
		// tryMoveTowards(node, nodeContext, movements, new Vector3d(0, 0, -1));

		tryWalk(node, nodeContext, movements, Direction.NORTH);
		tryWalk(node, nodeContext, movements, Direction.EAST);
		tryWalk(node, nodeContext, movements, Direction.SOUTH);
		tryWalk(node, nodeContext, movements, Direction.WEST);
	}


	private static boolean isSolid(IWorldContext worldContext, BlockPos pos)
	{
		return !worldContext.getBlock(pos).getCollisionShape(worldContext.getMCWorld(), pos).isEmpty();
	}


	private static void tryWalk(PathNode node, PathNodeContext nodeContext, List<IMovement> movements, Direction rel)
	{
		IWorldContext worldContext = node.getWorldContext();
		BlockPos relPos = node.getPosition().relative(rel);

		if (isSolid(worldContext, relPos)
			|| isSolid(worldContext, relPos.relative(Direction.UP))
			|| !isSolid(worldContext, relPos.relative(Direction.DOWN))) return;

		PathNode relNode = nodeContext.getNodeAt(relPos, worldContext);
		movements.add(new WalkMovement(relNode, relNode.getEstimatedEntityPosition(), 1));
	}


	private final PathNode endNode;
	private final Vector3d targetPos;
	private final float cost;


	private WalkMovement(PathNode endNode, Vector3d targetPos, float cost)
	{
		this.endNode = endNode;
		this.targetPos = targetPos;
		this.cost = cost;
	}


	@Override
	public boolean executeTick(MobEntity entity)
	{
		entity.getMoveControl().setWantedPosition(this.targetPos.x, this.targetPos.y, this.targetPos.z, 1.0);
		return entity.position().distanceToSqr(this.targetPos) < 0.1 * 0.1;
	}


	@Override
	public float getCost()
	{
		return this.cost;
	}


	@Override
	public PathNode getPosition()
	{
		return this.endNode;
	}

}
