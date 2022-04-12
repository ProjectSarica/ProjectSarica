package me.ci.project.navi.algorithm;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;
import me.ci.project.navi.world.IWorldContext;
import net.minecraft.util.math.BlockPos;

public class PathNodeContext
{
	private final Collection<PathNode> openSet;
	private final Collection<PathNode> cache = new LinkedList<>();


	PathNodeContext(Collection<PathNode> openSet)
	{
		this.openSet = openSet;
	}


	void clearCache()
	{
		this.cache.clear();
	}


	private Optional<PathNode> findNode(BlockPos pos, IWorldContext worldContext)
	{
		return Stream
			.concat(this.openSet.stream(), this.cache.stream())
			.filter(node -> node.getPosition().equals(pos))
			.filter(node -> node.getWorldContext().equals(worldContext))
			.findFirst();
	}


	public PathNode getNodeAt(BlockPos pos, IWorldContext worldContext)
	{
		Optional<PathNode> node = findNode(pos, worldContext);
		if (node.isPresent()) return node.get();

		PathNode newNode = new PathNode(worldContext, pos);
		this.cache.add(newNode);
		return newNode;
	}
}
