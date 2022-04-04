package me.ci.project.sarica.mio.agent;

import org.joml.Vector3f;
import me.ci.project.mio.api.IMinecraftAgent;
import me.ci.project.mio.api.actions.IAction;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.vector.Vector3d;

public class MioAgent implements IMinecraftAgent
{
	private final MobEntity mob;
	private IAction action;


	public MioAgent(MobEntity mob)
	{
		this.mob = mob;
	}


	@Override
	public IAction getAction()
	{
		if (this.action != null && !this.action.isRunning()) this.action = null;

		return this.action;
	}


	public MobEntity getMob()
	{
		return this.mob;
	}


	@Override
	public Vector3f getPosition()
	{
		Vector3d position = this.mob.position();
		return new Vector3f((float) position.x, (float) position.y, (float) position.z);
	}


	@Override
	public void setAction(IAction action)
	{
		if (getAction() != null) getAction().cancel();
		this.action = action;
		if (action != null) action.start();
	}
}
