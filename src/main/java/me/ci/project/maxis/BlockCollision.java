package me.ci.project.maxis;

public class BlockCollision
{
	public static final BlockCollision EMPTY = new BlockCollision(true);
	public static final BlockCollision BLOCK = new BlockCollision(false);

	private final boolean empty;


	public BlockCollision(boolean empty)
	{
		this.empty = empty;
	}


	public boolean isEmpty()
	{
		return this.empty;
	}
}
