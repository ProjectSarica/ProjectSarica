package me.ci.project.navi.goals;

import me.ci.project.navi.algorithm.PathNode;
import net.minecraft.util.math.BlockPos;

public class MoveToPositionGoal implements IGoal
{
	private final BlockPos pos;
	private final int radius;


	public MoveToPositionGoal(BlockPos pos)
	{
		this(pos, 0);
	}


	public MoveToPositionGoal(BlockPos pos, int radius)
	{
		this.pos = pos;
		this.radius = radius;
	}


	@Override
	public float getHeuristic(PathNode node)
	{
		return (float) Math.sqrt(node.getPosition().distSqr(this.pos));
	}


	@Override
	public boolean isGoal(PathNode node)
	{
		return node.getPosition().distManhattan(this.pos) <= this.radius;
	}

}
