package me.ci.project.sarica.mio.pathfinding;

import me.ci.project.mio.api.actions.IAction;
import me.ci.project.sarica.mio.agent.MioAgent;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;

public abstract class AbstractPathfindingGoal implements IAction
{
	protected final MioAgent agent;
	private boolean isRunning;
	private Path lastPath;


	protected AbstractPathfindingGoal(MioAgent agent)
	{
		this.agent = agent;
	}


	@Override
	public void cancel()
	{
		if (!isRunning()) throw new IllegalStateException("Cannot cancel an action that is not running!");
		if (!isCancelable()) throw new IllegalStateException("Cannot cancel an uncancelable action!");

		getNavigation().stop();
		this.isRunning = false;
	}


	public abstract Path computePath();


	@Override
	public MioAgent getAgent()
	{
		return this.agent;
	}


	protected PathNavigator getNavigation()
	{
		return getAgent().getMob().getNavigation();
	}


	@Override
	public boolean isCancelable()
	{
		return true;
	}


	@Override
	public boolean isRunning()
	{
		Path currentPath = getNavigation().getPath();
		if (currentPath != this.lastPath) this.isRunning = false;
		return this.isRunning;
	}


	public boolean needsRecomputing()
	{
		return false;
	}


	@Override
	public void start()
	{
		this.lastPath = computePath();
		this.isRunning = getNavigation().moveTo(this.lastPath, 1.0);
	}
}
