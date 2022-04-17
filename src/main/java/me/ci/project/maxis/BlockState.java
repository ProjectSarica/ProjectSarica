package me.ci.project.maxis;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BlockState
{
	private final Map<String, String> properties = new HashMap<>();
	private final String namespace;
	private final String name;
	private final String registryName;
	private final BlockCollision collision;
	private final int hash;


	BlockState(String namespace, String name, Map<String, String> properties, BlockCollision collision)
	{
		this.namespace = namespace;
		this.name = name;
		this.collision = collision;

		properties.forEach((k, v) -> this.properties.put(k, v));

		String propertyText = properties
			.entrySet()
			.stream()
			.map(e -> String.format("%s=%s", e.getKey(), e.getValue()))
			.collect(Collectors.joining());

		if (this.properties.isEmpty()) propertyText = "";
		else propertyText = String.format("[%s]", propertyText);

		this.registryName = String.format("%s:%s%s", this.namespace, this.name, propertyText);
		this.hash = this.registryName.hashCode();
	}


	@Override
	public boolean equals(Object obj)
	{
		return this.registryName.equals(obj);
	}


	public BlockCollision getCollision()
	{
		return this.collision;
	}


	public String getName()
	{
		return this.name;
	}


	public String getNamespace()
	{
		return this.namespace;
	}


	public String getProperty(String property)
	{
		return this.properties.get(property);
	}


	public String getRegistryName()
	{
		return this.registryName;
	}


	@Override
	public int hashCode()
	{
		return this.hash;
	}


	public boolean hasProperty(String property)
	{
		return this.properties.containsKey(property);
	}


	@Override
	public String toString()
	{
		return this.registryName;
	}
}
