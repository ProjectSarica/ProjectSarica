package me.ci.project.maxis;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class BlockStateBuilder
{
	private final Map<String, String> properties = new HashMap<>();
	private final String namespace;
	private final String name;
	private VoxelShape collision = VoxelShapes.empty();


	public BlockStateBuilder(String namespace, String name)
	{
		this.namespace = namespace;
		this.name = name;
	}


	BlockState build()
	{
		return new BlockState(this.namespace, this.name, this.properties, this.collision);
	}


	public BlockStateBuilder setCollision(VoxelShape collision)
	{
		this.collision = collision;
		return this;
	}


	public BlockStateBuilder setProperty(String property, String value)
	{
		this.properties.put(property, value);
		return this;
	}
}
