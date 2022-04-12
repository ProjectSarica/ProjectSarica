package me.ci.project.navi.algorithm;

import java.util.LinkedList;

public class Path
{
	private final PathStatus status;
	private final LinkedList<PathNode> nodes = new LinkedList<>();


	public Path(PathStatus status)
	{
		this.status = status;
	}


	void addNode(PathNode node)
	{
		if (node.getMovement() == null) return;
		this.nodes.addFirst(node);
	}


	public void finishNode()
	{
		this.nodes.removeFirst();
	}


	public PathNode getNextNode()
	{
		return this.nodes.getFirst();
	}


	public PathStatus getStatus()
	{
		return this.status;
	}


	public boolean isEmpty()
	{
		return this.nodes.isEmpty();
	}


	int remainingNodes()
	{
		return this.nodes.size();
	}
}
