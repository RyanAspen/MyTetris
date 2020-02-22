package tetrisGame;

public class TetrisPiece {
	private int shapeAnimation[][][];
	private int shapeId = 0;
	private int id = 0;
	public static int allShapes[][][][] = 
	{
	  {
	    {
	      {0,0,0,0},
	      {1,1,1,1}
	    },
	    { 
	      {0,1},
	      {0,1},
	      {0,1},
	      {0,1}
	    }
	  },
	  {
	    { 
	      {0,0,0},
	      {2,2,2},
	      {0,0,2},
	    },
	    { 
	      {0,2},
	      {0,2},
	      {2,2}
	    },
	    { 
	      {2,0,0},
	      {2,2,2}
	    },
	    { 
	      {0,2,2},
	      {0,2,0},
	      {0,2,0}
	    }
	  },
	  {
	  {
	      {0,0,0},
	      {3,3,3},
	      {3,0,0}
	    },
	    { 
	      {3,3},
	      {0,3},
	      {0,3}
	    },
	    { 
	      {0,0,3},
	      {3,3,3},
	      {0,0,0}
	    },
	    { 
	      {0,3,0},
	      {0,3,0},
	      {0,3,3}
	    }
	  },
	  {
	  { 
		  {0,0,0},
		  {0,4,4},
	      {4,4,0}
	    },
	    { 
	      {4,0},
	      {4,4},
	      {0,4}
	    }
	  },
	  {
	  {
		  {0,0,0},
		  {5,5,5},
	      {0,5,0}
	    },
	    { 
	      {0,5},
	      {5,5},
	      {0,5}
	    },
	    {
	      {0,5,0},
	      {5,5,5}
	    },
	    { 
	      {0,5,0},
	      {0,5,5},
	      {0,5,0}
	    }
	  },
	  { 
		{
	      {0,0,0},
	      {6,6,0},
	      {0,6,6}
	    },
	    {
	      {0,6},
	      {6,6},
	      {6,0}
	    }
	  },
	  {
	    { 
	      {7,7},
	      {7,7}
	    }
	  }
	};
	public TetrisPiece()
	{
		this.setId((int)((Math.random() * 7)));
		this.setShapeAnimation(allShapes[id]);
	}
	public TetrisPiece(int id)
	{
		this.setId(id);
		this.setShapeAnimation(allShapes[id]);
	}
	public int getWidth()
	{
		return shapeAnimation[shapeId][0].length;
	}
	public int getHeight()
	{
		return shapeAnimation[shapeId].length;
	}
	public void counterClockwiseRotate()
	{
		if (shapeId == 0)
		{
			shapeId = shapeAnimation.length - 1;
		}
		else 
		{
			shapeId--;
		}		
	}
	public void clockwiseRotate()
	{
		shapeId++;
		shapeId %= shapeAnimation.length;
	}
	public int[][][] getShapeAnimation() {
		return shapeAnimation;
	}
	public void setShapeAnimation(int shapeAnimation[][][]) {
		this.shapeAnimation = shapeAnimation;
	}
	public int[][] getShape()
	{
		return shapeAnimation[shapeId];
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getShapeId() {
		return shapeId;
	}
	public void setShapeId(int shapeId) {
		this.shapeId = shapeId;
	}
}