package me.ci.project.mio.world;

import org.joml.Vector3ic;
import me.ci.project.mio.math.VectorUtils;

class Region
{
	private final Chunk[] chunks = new Chunk[16 * 16 * 16];
	private final Vector3ic regionIndex;
	private int loadedChunks = 0;


	Region(Vector3ic regionIndex)
	{
		this.regionIndex = regionIndex;
	}


	Chunk getChunk(Vector3ic chunkIndex, boolean create)
	{
		int index = VectorUtils.blockPosToIndex(chunkIndex);

		if (this.chunks[index] == null && create)
		{
			this.chunks[index] = new Chunk(VectorUtils.maskBlockPos(chunkIndex));
			this.loadedChunks++;
		}

		return this.chunks[index];
	}


	int getLoadedChunkCount()
	{
		return this.loadedChunks;
	}


	Vector3ic getRegionIndex()
	{
		return this.regionIndex;
	}


	void unloadChunk(Vector3ic chunkIndex)
	{
		int index = VectorUtils.blockPosToIndex(chunkIndex);
		this.chunks[index] = null;
		this.loadedChunks--;
	}
}
