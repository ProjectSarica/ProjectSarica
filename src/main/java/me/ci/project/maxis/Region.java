package me.ci.project.maxis;

import net.minecraft.util.math.BlockPos;

class Region
{
	private final Chunk[] chunks = new Chunk[16 * 16 * 16];
	private final BlockPos regionPos;
	private int loadedChunks = 0;


	Region(BlockPos regionPos)
	{
		this.regionPos = regionPos;
	}


	private int chunkPosIndex(BlockPos chunkPos)
	{
		int x = chunkPos.getX() & 15;
		int y = chunkPos.getY() & 15;
		int z = chunkPos.getZ() & 15;
		return x * 16 * 16 + y * 16 + z;
	}


	Chunk getChunk(BlockPos chunkPos, boolean create)
	{
		int index = chunkPosIndex(chunkPos);

		if (this.chunks[index] == null && create)
		{
			this.chunks[index] = new Chunk();
			this.loadedChunks++;
		}

		return this.chunks[index];
	}


	int getLoadedChunkCount()
	{
		return this.loadedChunks;
	}


	BlockPos getRegionPos()
	{
		return this.regionPos;
	}


	void unloadChunk(BlockPos chunkPos)
	{
		int index = chunkPosIndex(chunkPos);
		this.chunks[index] = null;
		this.loadedChunks--;
	}
}
