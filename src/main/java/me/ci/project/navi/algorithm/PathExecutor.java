package me.ci.project.navi.algorithm;

import net.minecraft.entity.MobEntity;

public class PathExecutor
{
	private final MobEntity entity;
	private Path path;


	public PathExecutor(MobEntity entity)
	{
		this.entity = entity;
	}


	public boolean isNavigating()
	{
		return this.path != null;
	}


	public void setPath(Path path)
	{
		if (path != null
			&& path.isEmpty()) path = null;

		this.path = path;
	}


	public void tick()
	{
		if (this.path == null) return;

		PathNode node = this.path.getNextNode();
		boolean done = node.getMovement().executeTick(this.entity);
		if (done) this.path.finishNode();

		if (this.path.isEmpty()) this.path = null;
	}
}
