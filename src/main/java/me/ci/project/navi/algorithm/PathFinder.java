package me.ci.project.navi.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import me.ci.project.navi.ProjectNavi;
import me.ci.project.navi.goals.IGoal;
import me.ci.project.navi.world.WorldBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PathFinder
{
	private static int compare(PathNode a, PathNode b)
	{
		return Float.compare(a.fScore, b.fScore);
	}


	public static Path findPath(MovementNetwork movements, World world, Entity entity, IGoal goal, BlockPos start)
	{
		long time = System.currentTimeMillis();

		WorldBase worldBase = new WorldBase(world, entity);
		PathNode startNode = new PathNode(worldBase, start);
		PathFinder pathfinder = new PathFinder(movements, goal, startNode);
		Path path = pathfinder.findPath();

		time = System.currentTimeMillis() - time;
		ProjectNavi.LOGGER.trace("Found path '{}' with {} nodes in {}ms.", path.getStatus(), path.remainingNodes(), time);
		return path;
	}


	private final PriorityQueue<PathNode> openSet = new PriorityQueue<>(PathFinder::compare);
	private final PathNodeContext nodeContext = new PathNodeContext(this.openSet);
	private final MovementNetwork movements;
	private final IGoal goal;
	private int totalNodesExplored;
	private int nodesWithoutBest;

	private PathNode bestHeuristic;
	private float bestHeuristicValue;


	private PathFinder(MovementNetwork movements, IGoal goal, PathNode startNode)
	{
		this.movements = movements;
		this.goal = goal;
		this.openSet.add(startNode);

		startNode.gScore = 0;
		startNode.fScore = goal.getHeuristic(startNode);

		this.bestHeuristic = startNode;
		this.bestHeuristicValue = startNode.fScore;
	}


	private Path buildPath(PathStatus status, PathNode node)
	{
		Path path = new Path(status);

		while (node != null)
		{
			path.addNode(node);
			node = node.getParent();
		}

		return path;
	}


	public Path findPath()
	{
		List<IMovement> availableMovements = new LinkedList<>();

		while (!this.openSet.isEmpty())
		{
			PathNode node = this.openSet.poll();
			if (this.goal.isGoal(node)) return buildPath(PathStatus.SUCCESS, node);

			availableMovements.clear();
			this.movements.getAvailableMovements(node, this.nodeContext, availableMovements);

			for (IMovement move : availableMovements)
			{
				PathNode neighbor = move.getPosition();
				float tScore = node.gScore + move.getCost();

				if (tScore < neighbor.gScore)
				{
					neighbor.setParent(node, move);
					float heuristic = this.goal.getHeuristic(neighbor);

					neighbor.gScore = tScore;
					neighbor.fScore = tScore + heuristic;
					this.openSet.add(neighbor);

					ProjectNavi.LOGGER.trace("Handled neighbor. G: {}, H: {}/", tScore, heuristic, this.bestHeuristicValue);

					if (heuristic < this.bestHeuristicValue)
					{
						this.bestHeuristic = neighbor;
						this.bestHeuristicValue = heuristic;
						this.nodesWithoutBest = 0;
					}

				}

			}

			this.nodeContext.clearCache();
			this.totalNodesExplored++;
			this.nodesWithoutBest++;

			if (this.totalNodesExplored >= this.goal.getMaxNodes()
				|| this.nodesWithoutBest >= this.goal.getMaxNodesWithoutBetter())
			{
				ProjectNavi.LOGGER
					.trace("Failed to find path. Canceling after {}/{}:{}/{} nodes.", this.nodesWithoutBest, this.goal
						.getMaxNodesWithoutBetter(), this.totalNodesExplored, this.goal.getMaxNodes());

				return buildPath(PathStatus.TOO_FAR, this.bestHeuristic);
			}

		}

		ProjectNavi.LOGGER.trace("Failed to find path. All available nodes explored.");
		return buildPath(PathStatus.NO_PATH, this.bestHeuristic);
	}

}
