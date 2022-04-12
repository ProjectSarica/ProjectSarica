package me.ci.project.mio.world;

import java.util.HashMap;
import java.util.Map;

public class BlockStateBuilder
{
	private final Map<String, String> properties = new HashMap<>();
	private final String namespace;
	private final String name;
	private BlockCollision collision = BlockCollision.EMPTY;


	public BlockStateBuilder(String namespace, String name)
	{
		this.namespace = namespace;
		this.name = name;
	}


	BlockState build()
	{
		return new BlockState(this.namespace, this.name, this.properties, this.collision);
	}


	public BlockStateBuilder setCollision(BlockCollision collision)
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
