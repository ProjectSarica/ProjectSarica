package me.ci.project.mio.world;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.joml.Vector3ic;
import me.ci.project.mio.math.VectorUtils;

public class World
{
	private final Set<Region> regions = new HashSet<>();


	public BlockState getBlock(Vector3ic blockPos)
	{
		Chunk chunk = getChunk(VectorUtils.blockToChunk(blockPos), false);
		if (chunk == null) return BlockStateRegistry.getBlockState("projectmio:void");
		return chunk.getBlock(blockPos);
	}


	private Chunk getChunk(Vector3ic chunkPos, boolean create)
	{
		Region region = getRegion(VectorUtils.blockToChunk(chunkPos), create);
		if (region == null) return null;

		return region.getChunk(chunkPos, create);
	}


	private Region getRegion(Vector3ic regionPos, boolean create)
	{
		Optional<Region> region = this.regions
			.stream()
			.filter(r -> r.getRegionIndex().equals(regionPos))
			.findAny();

		if (region.isPresent()) return region.get();
		if (!create) return null;

		Region newRegion = new Region(regionPos);
		this.regions.add(newRegion);
		return newRegion;
	}


	public void setBlock(Vector3ic blockPos, BlockState blockState)
	{
		Chunk chunk = getChunk(VectorUtils.blockToChunk(blockPos), true);
		chunk.setBlock(blockPos, blockState);
	}


	public void unloadChunk(Vector3ic chunkPos)
	{
		Region region = getRegion(VectorUtils.blockToChunk(chunkPos), false);
		if (region == null) return;
		region.unloadChunk(chunkPos);

		if (region.getLoadedChunkCount() == 0) this.regions.remove(region);
	}
}
