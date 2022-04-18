package me.ci.project.navi.algorithm;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;
import me.ci.project.mio.world.IWorld;
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


	private Optional<PathNode> findNode(BlockPos pos, IWorld world)
	{
		return Stream
			.concat(this.openSet.stream(), this.cache.stream())
			.filter(node -> node.getPosition().equals(pos))
			.filter(node -> node.getWorld().equals(world))
			.findFirst();
	}


	public PathNode getNodeAt(BlockPos pos, IWorld world)
	{
		Optional<PathNode> node = findNode(pos, world);
		if (node.isPresent()) return node.get();

		PathNode newNode = new PathNode(world, pos);
		this.cache.add(newNode);
		return newNode;
	}
}
