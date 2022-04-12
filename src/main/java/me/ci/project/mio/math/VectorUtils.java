package me.ci.project.mio.math;

import java.util.function.Function;
import org.joml.Vector3i;
import org.joml.Vector3ic;

public final class VectorUtils
{
	public static int blockPosToIndex(Vector3ic vec)
	{
		int x = vec.x() & 15;
		int y = vec.y() & 15;
		int z = vec.z() & 15;
		return x * 16 * 16 + y * 16 + z;
	}


	public static Vector3ic blockToChunk(Vector3ic vec)
	{
		return mapVector(c -> c >> 4, vec);
	}


	public static Vector3ic mapVector(Function<Integer, Integer> mapper, Vector3ic vec)
	{
		return new Vector3i(mapper.apply(vec.x()), mapper.apply(vec.y()), mapper.apply(vec.z()));
	}


	public static Vector3ic maskBlockPos(Vector3ic vec)
	{
		return mapVector(c -> c & 15, vec);
	}


	private VectorUtils()
	{}
}
