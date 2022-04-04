package me.ci.project.sarica.mio.pathfinding;

import me.ci.project.sarica.mio.agent.MioAgent;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class MoveToPositionGoal extends AbstractPathfindingGoal
{
	private final Vector3d position;
	private final int radius;


	public MoveToPositionGoal(MioAgent agent, BlockPos position)
	{
		this(agent, new Vector3d(position.getX() + 0.5, position.getY(), position.getZ() + 0.5), 0);
	}


	public MoveToPositionGoal(MioAgent agent, Vector3d position)
	{
		this(agent, position, 0);
	}


	public MoveToPositionGoal(MioAgent agent, Vector3d position, int radius)
	{
		super(agent);
		this.position = position;
		this.radius = radius;
	}


	@Override
	public Path computePath()
	{
		return getNavigation()
			.createPath(this.position.x, this.position.y, this.position.z, this.radius);
	}
}
