package me.ci.project.maxis.future;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import me.ci.project.maxis.World;
import me.ci.project.mio.world.IBlockState;
import me.ci.project.mio.world.IWorld;
import net.minecraft.util.math.BlockPos;

public final class ModifiedWorld implements IWorld
{
	private final World parent;
	private final Map<BlockPos, IBlockState> modifications = new HashMap<>();
	private int modHash;


	public ModifiedWorld(ModifiedWorld parent)
	{
		ModifiedWorld modParent = parent;
		this.parent = modParent.parent;
		modParent.modifications.forEach((a, b) -> this.modifications.put(a, b));
		updateModHash();
	}


	public ModifiedWorld(World parent)
	{
		this.parent = parent;
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
	public IBlockState getBlock(BlockPos pos)
	{
		if (this.modifications.containsKey(pos)) return this.modifications.get(pos);
		return this.parent.getBlock(pos);
	}


	@Override
	public int hashCode()
	{
		int hash = this.parent.hashCode();
		return hash * 21 + this.modHash;
	}


	public void setBlock(BlockPos pos, IBlockState state)
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
