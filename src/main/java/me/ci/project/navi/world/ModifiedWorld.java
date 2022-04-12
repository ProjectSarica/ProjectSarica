package me.ci.project.navi.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModifiedWorld implements IWorldContext
{
	private final WorldBase parent;
	private final Map<BlockPos, BlockState> modifications = new HashMap<>();
	private int modHash;


	public ModifiedWorld(IWorldContext parent)
	{

		if (parent instanceof ModifiedWorld)
		{
			ModifiedWorld modParent = (ModifiedWorld) parent;
			this.parent = modParent.parent;
			modParent.modifications.forEach((a, b) -> this.modifications.put(a, b));
		}
		else if (parent instanceof WorldBase) this.parent = (WorldBase) parent;
		else throw new IllegalArgumentException("Unsupported world context!");

		updateModHash();
	}


	@Override
	public boolean equals(Object obj)
	{
		if (obj == null
			|| !(obj instanceof ModifiedWorld)) return false;

		ModifiedWorld world = (ModifiedWorld) obj;
		return this.modifications.equals(world.modifications);
	}


	@Override
	public BlockState getBlock(BlockPos pos)
	{
		if (this.modifications.containsKey(pos)) return this.modifications.get(pos);
		return this.parent.getBlock(pos);
	}


	@Override
	public Entity getEntity()
	{
		return this.parent.getEntity();
	}


	@Override
	public World getMCWorld()
	{
		return this.parent.getMCWorld();
	}


	@Override
	public int hashCode()
	{
		int hash = this.parent.hashCode();
		return hash * 21 + this.modHash;
	}


	public void setBlock(BlockPos pos, BlockState state)
	{
		this.modifications.put(pos, state);
		updateModHash();
	}


	private void updateModHash()
	{
		this.modHash = 5;

		for (BlockPos pos : this.modifications.keySet())
		{
			this.modHash = this.modHash * 21 + Objects.hash(pos, this.modifications.get(pos));
		}

	}
}
