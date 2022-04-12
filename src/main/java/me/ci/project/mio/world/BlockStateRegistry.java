package me.ci.project.mio.world;

import java.util.HashMap;
import java.util.Map;

public final class BlockStateRegistry
{
	private static final Map<String, BlockState> blockStates = new HashMap<>();

	static
	{
		registerBlockState(new BlockStateBuilder("projectmio", "void"));
	}


	public static BlockState getBlockState(String registryName)
	{
		return blockStates.get(registryName);
	}


	public static void registerBlockState(BlockStateBuilder builder)
	{
		BlockState block = builder.build();
		blockStates.put(block.getRegistryName(), block);
	}


	private BlockStateRegistry()
	{}
}
