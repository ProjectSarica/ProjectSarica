package me.ci.project.navi.goals;

import me.ci.project.navi.algorithm.PathNode;

/**
 * A pathfinding goal definition that can be applied to the pathfinder in order
 * to complete a specific task.
 *
 * @author thedudefromci
 */
public interface IGoal
{
	/**
	 * Gets an estimated heuristic cost for the node. This value is a heuristic for
	 * host close the node is to being a goal node, with lower values meaning closer
	 * to the goal. A unit of 1 represents one meter away from the goal.
	 *
	 * @param node
	 *            - The node to check.
	 *
	 * @return
	 */
	float getHeuristic(PathNode node);


	/**
	 * Gets the overall maximum number of nodes that can be explored while
	 * pathfinding, assuming better paths are constantly being discovered. By
	 * default, this value is Integer.MAX_VALUE.
	 *
	 * @return The overall maximum number of nodes that can be explored in a single
	 *         pathfinding instance.
	 *
	 * @see {@link IGoal#getMaxNodesWithoutBetter()}
	 */
	default int getMaxNodes()
	{
		return Integer.MAX_VALUE;
	}


	/**
	 * Gets the total number of nodes that can be explored without finding a better
	 * path before giving up. Assuming the goal position has not been found, each
	 * time a new path node is discovered, if the heuristic value of that path node
	 * is lower than the previous best, this counter is reset to 0. Otherwise, it is
	 * incremented by 1. If the counter reaches this value, the pathfinder will
	 * assume there is no available path to the goal. By default this value is 1024
	 * nodes.
	 *
	 * @return The maximum number of nodes that can be explored without finding a
	 *         better path before giving up.
	 *
	 * @see {@link IGoal#getMaxNodes()}
	 */
	default int getMaxNodesWithoutBetter()
	{
		return 1024;
	}


	/**
	 * Checks if the given path node is a viable goal position.
	 *
	 * @param node
	 *            - The node to check against.
	 *
	 * @return True if the path node is an acceptable goal position. False
	 *         otherwise.
	 */
	boolean isGoal(PathNode node);
}
