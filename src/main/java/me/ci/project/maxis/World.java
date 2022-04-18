package me.ci.project.maxis;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import me.ci.project.mio.world.IBlockState;
import me.ci.project.mio.world.IWorld;
import net.minecraft.util.math.BlockPos;

public final class World implements IWorld
{
	private final Set<Region> regions = new HashSet<>();


	private BlockPos blockPosToChunkPos(BlockPos blockPos)
	{
		return chunkPosToRegionPos(blockPos);
	}


	private BlockPos chunkPosToRegionPos(BlockPos chunkPos)
	{
		int x = chunkPos.getX() >> 4;
		int y = chunkPos.getY() >> 4;
		int z = chunkPos.getZ() >> 4;
		return new BlockPos(x, y, z);
	}


	@Override
	public IBlockState getBlock(BlockPos blockPos)
	{
		Chunk chunk = getChunk(blockPosToChunkPos(blockPos), false);
		if (chunk == null) return BlockStateRegistry.getBlockState("projectmio:void");
		return chunk.getBlock(blockPos);
	}


	private Chunk getChunk(BlockPos chunkPos, boolean create)
	{
		Region region = getRegion(chunkPosToRegionPos(chunkPos), create);
		if (region == null) return null;

		return region.getChunk(chunkPos, create);
	}


	private Region getRegion(BlockPos regionPos, boolean create)
	{
		Optional<Region> region = this.regions
			.stream()
			.filter(r -> r.getRegionPos().equals(regionPos))
			.findAny();

		if (region.isPresent()) return region.get();
		if (!create) return null;

		Region newRegion = new Region(regionPos);
		this.regions.add(newRegion);
		return newRegion;
	}


	@Override
	public void setBlock(BlockPos blockPos, IBlockState blockState)
	{
		Chunk chunk = getChunk(blockPosToChunkPos(blockPos), true);
		chunk.setBlock(blockPos, blockState);
	}


	@Override
	public void setChunk(BlockPos chunkPos, IBlockState[] blockStates)
	{
		Chunk chunk = getChunk(chunkPos, true);
		chunk.setBlocks(blockStates);
	}


	@Override
	public void unloadChunk(BlockPos chunkPos)
	{
		Region region = getRegion(chunkPosToRegionPos(chunkPos), false);
		if (region == null) return;
		region.unloadChunk(chunkPos);
		if (region.getLoadedChunkCount() == 0) this.regions.remove(region);
	}
}
