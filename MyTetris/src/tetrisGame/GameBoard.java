package tetrisGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;

import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import tetrisGame.HoldDisplayer;
import tetrisGame.LevelAndScoreDisplayer;
import tetrisGame.PieceQueue;
import tetrisGame.TetrisPiece;

public class GameBoard extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	private MainGame game;
	
	private int tetrisBoard[][] = new int[25][16];
	
	private int level;
	private int fallSpeedInFrames[] = {48, 43, 38, 33, 28, 23, 18, 13, 8, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}; //Goes up to lv 28 (29+ is 1) 
	private int currentFallSpeed; 
	private TetrisPiece current;
	private int currentScore = 0;
	private int clearsInLevel = 0;
	private int clearsUntilLevelUp;
	
	//Delays in frames for various actions
	private int framesUntilFall = currentFallSpeed; //Normal Gravity
	private int framesUntilFastFall = 0; //Fast Fall (Twice as fast)
	private int framesUntilShift = 0; //Speed to shift left-right
	private int framesUntilDASShift = 0; //Speed to shift once DAS is active
	private boolean isDASEnabled = false;
	private int DASCounter = 0;
	//There is no delay for rotate 
	//Not sure yet for row clear
	
	private boolean isChange = false;
	
	
	
	private int x,y; //Left-corner of piece
	private boolean isFalling = true;
	
	//Controls
	private Character counterClockwiseRotateButton = BindChangeButton.binds[0]; 
	private Character clockwiseRotateButton = BindChangeButton.binds[1]; 
	private Character fastFallButton = BindChangeButton.binds[2]; //Down arrow
	private Character quickFallButton = BindChangeButton.binds[3]; //Space Key
	private Character leftShiftButton = BindChangeButton.binds[4]; //Left arrow
	private Character rightShiftButton = BindChangeButton.binds[5]; //Right arrow
	private Character holdButton = BindChangeButton.binds[6]; 
	private Character pauseButton = BindChangeButton.binds[7];
	
	private boolean isPaused = false;
	private boolean isOver = false;
	
	private PieceQueue queue = new PieceQueue();
	private LevelAndScoreDisplayer displayer;
	private HoldDisplayer holdDisplay;
	private boolean firstHold = false;
	private boolean canHold = true;
	
	public GameBoard(int initialLevel, MainGame mg) throws IOException
	{
		game = mg;
		this.setVisible(true);
		for (int a = 0; a < tetrisBoard.length; a++)
		{
			for (int b = 0; b < tetrisBoard[0].length; b++)
			{
				if (b < 3 || b > tetrisBoard[0].length - 4 || a == tetrisBoard.length - 1)
				{
					tetrisBoard[a][b] = 9;
				}
				else
				{
					tetrisBoard[a][b] = 0;
				}
				
			}
		}
		level = initialLevel;
		if (level > 28)
		{
			currentFallSpeed = 1;
		}
		else
		{
			currentFallSpeed = fallSpeedInFrames[level];
		}	
		displayer = new LevelAndScoreDisplayer(level);
		holdDisplay = new HoldDisplayer();
		initializeLineCount();
		createNewPiece();
		addKeyListener(new KeyAdapter(){});
		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setFocusable(true);
	}
	
	private void initializeLineCount()
	{
		clearsUntilLevelUp = Math.min((level * 10) + 10, Math.max(100, (level * 10) - 50));
	}
	
	public PieceQueue getQueue()
	{
		return queue;
	}
	
	public LevelAndScoreDisplayer getDisplayer()
	{
		return displayer;
	}
	
	public HoldDisplayer getHolder()
	{
		return holdDisplay;
	}
	
	//In progress
	public void paint(Graphics g) { 
		super.paint(g);
		//Width of blocks is just width of the screen divided by the used width of the array in blocks
		//Height of blocks = width
		
		int boxX, boxY;
		//Test vars
		int boxWidth = Math.round(getWidth() / (tetrisBoard[0].length - 4));
		int boxHeight = boxWidth;
		for (int a = 4; a < tetrisBoard.length; a++)
		{
			for (int b = 2; b < tetrisBoard[0].length - 2; b++)
			{
				boxY = boxHeight * (a - 4);
				boxX = boxWidth * (b - 2);
				
				if (tetrisBoard[a][b] != 0)
				{
					setColor(g, tetrisBoard[a][b]);
					g.fillRect(boxX, boxY, boxWidth, boxHeight);
					g.setColor(Color.black);
					g.drawRect(boxX, boxY, boxWidth, boxHeight);	
				}
				
				
			}
		}	
	}
	
	public void frame() //Called every frame (about 17 ms)
	{
		//Pause Functionality
		if (searchForBriefCharacter(pauseButton))
		{
			if (isPaused)
			{
				isPaused = false;
			}
			else
			{
				isPaused = true;
			}
		}
		if (!isPaused && !isOver)
		{
			updateDelays();
			isChange = false; //Check if repaint is necessary
			
			//This is where override actions take place (place piece delay, clear row delay, etc.)		
			if (isFalling)
			{
				//Gravity
				if(framesUntilFall == 0)
				{
					fall();
					framesUntilFall = currentFallSpeed;
					isChange = true;
				}
				//Manual Controls
				else
				{
					//Quick Fall
					if(searchForBriefCharacter(quickFallButton))
					{
						quickFall();
						framesUntilFall = currentFallSpeed;
						isChange = true;
					}
					//Fast Fall
					else if (searchForCharacter(fastFallButton) && framesUntilFastFall == 0)
					{
						
						fall();
						framesUntilFastFall = 3; //Fix later
						framesUntilFall = currentFallSpeed;
						isChange = true;
					}	
					
					if (isFalling)
					{
						//Left-Right Shift
						if (searchForCharacter(leftShiftButton) && canShift())
						{
							shiftLeft();
							framesUntilShift = 16;
							framesUntilDASShift = 6;
							if (DASCounter < 0)
							{
								DASCounter = 0;
							}
							else
							{
								DASCounter++;
							}
							if (DASCounter >= 2)
							{
								isDASEnabled = true;
							}
							else
							{
								isDASEnabled = false;
							}
							isChange = true;
						}
						else if (searchForCharacter(rightShiftButton) && canShift())
						{
							shiftRight();
							framesUntilShift = 16;
							framesUntilDASShift = 6;
							if (DASCounter > 0)
							{
								DASCounter = 0;
							}
							else
							{
								DASCounter--;
							}
							if (DASCounter <= -2)
							{
								isDASEnabled = true;
							}
							else
							{
								isDASEnabled = false;
							}
							isChange = true;
						}
						//For resetting DASCounter
						else if (canShift())
						{
							DASCounter = 0;
							isDASEnabled = false;
						}
						
						//Rotation
						if (searchForBriefCharacter(clockwiseRotateButton))
						{
							rotateClockwise();
							isChange = true;
						}
						else if (searchForBriefCharacter(counterClockwiseRotateButton))
						{
							rotateCounterClockwise();
							isChange = true;
						}	
						
						//Hold
						if (searchForBriefCharacter(holdButton) && canHold)
						{
							hold();
							framesUntilFall = currentFallSpeed;
							isChange = true;
							canHold = false;
						}
					}
				}	
			}
			if (!isFalling)
			{
				if (isGameOver())
				{
					isOver = true;
					//Animation?
				}
				else
				{
					clearRows(); //<-- Should eventually interact with delays
					createNewPiece(); //<-- Should eventually interact with delays
					isFalling = true;
					canHold = true;
				}
			}
			if (isChange)
			{
				repaint();
			}	
		}
		removeLingeringKeys();
	}
	
	/* I need code for the following methods
	 * - Fall
	 * - Shift Left
	 * - Shift Right
	 * - Rotate Clockwise
	 * - Rotate Counter-Clockwise
	 * 
	 * All of these methods should have built-in checks to prevent game breaks
	 */
	
	public boolean canShift() //Function concerning frames/delays, not whether the piece actually can shift
	{
		if (isDASEnabled)
		{
			if (framesUntilDASShift == 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if (framesUntilShift == 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	//Create new tetris piece on board
	public void createNewPiece()
	{
		int id = queue.progressQueue();
		current = new TetrisPiece(id); 
		x = 8;
		y = 0;
		placePiece(x,y);
	}
	
	//Create new tetris piece on board from hold
	public void hold()
	{
		int id;
		if (!firstHold)
		{
			id = queue.progressQueue();
			firstHold = true;
			holdDisplay.firstHold(current.getId());
		}
		else
		{
			id = holdDisplay.hold(current.getId());
		}
		removePiece();
		current = new TetrisPiece(id); 
		x = 8;
		y = 0;
		placePiece(x,y);
	}
	
	public void removePiece()
	{
		int shape[][] = current.getShape();
		for(int a = 0; a < current.getHeight(); a++)
		{
			for (int b = 0; b < current.getWidth(); b++)
			{
				if(shape[a][b] != 0)
				{
					tetrisBoard[a + y][b + x] = 0;
				}
			}
		}
	}
	
	//This method places a piece on the array at a point (coordinates refer to pivot point)
	public void placePiece(int x, int y)
	{
		int shape[][] = current.getShape();
		this.x = x;
		this.y = y;
		for(int a = 0; a < current.getHeight(); a++)
		{
			for (int b = 0; b < current.getWidth(); b++)
			{
				if(shape[a][b] != 0)
				{
					tetrisBoard[a + y][b + x] = shape[a][b];
				}
			}
		}
	}
	
	//This method moves a piece on the array from the old point to the new point (coordinates refer to left corner)
	public void movePiece(int newX, int newY)
	{
		int shape[][] = current.getShape();
		for(int a = 0; a < current.getHeight(); a++)
		{
			for (int b = 0; b < current.getWidth(); b++)
			{
				if(shape[a][b] != 0)
				{
					tetrisBoard[a + y][b + x] = 0;
				}
			}
		}
		placePiece(newX, newY);
	}
	
	//Check if a given movement can happen
	public boolean checkMovement(int newX, int newY)
	{
		int shape[][] = current.getShape();
		for(int a = 0; a < current.getHeight(); a++)
		{
			for (int b = 0; b < current.getWidth(); b++)
			{
				if(shape[a][b] != 0)
				{
					tetrisBoard[a + y][b + x] = 0;
				}
			}
		}
		for(int c = 0; c < current.getHeight(); c++)
		{
			for (int d = 0; d < current.getWidth(); d++)
			{
				if(shape[c][d] != 0)
				{
					if (tetrisBoard[c + newY][d + newX] != 0)
					{
						placePiece(x,y);
						return false;
					}
				}
			}
		}
		placePiece(x,y);
		return true;
	}
	
	public boolean checkClockwiseRotation()
	{
		int shape[][] = current.getShape();
		for(int a = 0; a < current.getHeight(); a++)
		{
			for (int b = 0; b < current.getWidth(); b++)
			{
				if(shape[a][b] != 0)
				{
					tetrisBoard[a + y][b + x] = 0;
				}
			}
		}
		current.clockwiseRotate();
		shape = current.getShape();
		for(int a = 0; a < current.getHeight(); a++)
		{
			for (int b = 0; b < current.getWidth(); b++)
			{
				if(shape[a][b] != 0)
				{
					if (tetrisBoard[a + y][b + x] != 0)
					{
						current.counterClockwiseRotate();
						placePiece(x,y);
						return false;
					}
				}
			}
		}
		current.counterClockwiseRotate();
		placePiece(x,y);
		return true;
	}
	
	public boolean checkCounterClockwiseRotation()
	{
		int shape[][] = current.getShape();
		for(int a = 0; a < current.getHeight(); a++)
		{
			for (int b = 0; b < current.getWidth(); b++)
			{
				if(shape[a][b] != 0)
				{
					tetrisBoard[a + y][b + x] = 0;
				}
			}
		}
		current.counterClockwiseRotate();
		shape = current.getShape();
		for(int a = 0; a < current.getHeight(); a++)
		{
			for (int b = 0; b < current.getWidth(); b++)
			{
				if(shape[a][b] != 0)
				{
					if (tetrisBoard[a + y][b + x] != 0)
					{
						current.clockwiseRotate();
						placePiece(x,y);
						return false;
					}
				}
			}
		}
		current.clockwiseRotate();
		placePiece(x,y);
		return true;
	}
	
	//Check for Game Over
	public boolean isGameOver()
	{
		//Check top two rows
		for (int a = 0; a < 4; a++)
		{
			for (int b = 3; b < tetrisBoard[0].length - 3; b++)
			{
				if (tetrisBoard[a][b] != 0)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	//Checks for and clears full rows
	public void clearRows()
	{
		boolean isRowClear;
		int numberOfClears = 0;
		for (int a = 4; a < tetrisBoard.length - 1; a++)
		{
			isRowClear = true;
			for (int b = 3; b < tetrisBoard[0].length - 3; b++)
			{
				if (tetrisBoard[a][b] == 0)
				{
					isRowClear = false;
				}
			}
			if (isRowClear)
			{
				numberOfClears++;
				//Trigger "Animation"
				for (int c = a; c > 3; c--)
				{
					for (int d = 3; d < tetrisBoard[0].length - 3; d++)
					{
						tetrisBoard[c][d] = tetrisBoard[c - 1][d];
					}
				}
				
			}
		}
		if (numberOfClears == 4)
		{
			currentScore += (1200 * (level + 1));
		}
		else if (numberOfClears == 3)
		{
			currentScore += (300 * (level + 1));
		}
		else if (numberOfClears == 2)
		{
			currentScore += (100 * (level + 1));
		}
		else if (numberOfClears == 1)
		{
			currentScore += (40 * (level + 1));
		}
		clearsInLevel += numberOfClears;
		if (clearsInLevel >= clearsUntilLevelUp)
		{
			clearsInLevel -= clearsUntilLevelUp;
			clearsUntilLevelUp += 10;
			level++;
			if (level > 28)
			{
				currentFallSpeed = 1;
			}
			else
			{
				currentFallSpeed = fallSpeedInFrames[level];
			}
		}
		displayer.setDisplay(currentScore, level);
	}
	
	//Fall Function
	public void fall()
	{
		if(checkMovement(x, y + 1))
		{
			movePiece(x, y + 1);
		}
		else
		{
			isFalling = false;
		}
	}
	
	public void quickFall()
	{
		while(isFalling)
		{
			fall();
		}
	}
	
	public void shiftLeft()
	{
		if(checkMovement(x-1, y))
		{
			movePiece(x-1, y);
		}
	}
	
	public void shiftRight()
	{
		if(checkMovement(x+1, y))
		{
			movePiece(x+1, y);
		}
	}
	
	public void rotateClockwise()
	{
		if(checkClockwiseRotation())
		{
			int shape[][] = current.getShape();
			for(int a = 0; a < current.getHeight(); a++)
			{
				for (int b = 0; b < current.getWidth(); b++)
				{
					if(shape[a][b] != 0)
					{
						tetrisBoard[a + y][b + x] = 0;
					}
				}
			}
			current.clockwiseRotate();
			placePiece(x,y);
		}
	}
	
	public void rotateCounterClockwise()
	{
		if(checkCounterClockwiseRotation())
		{
			int shape[][] = current.getShape();
			for(int a = 0; a < current.getHeight(); a++)
			{
				for (int b = 0; b < current.getWidth(); b++)
				{
					if(shape[a][b] != 0)
					{
						tetrisBoard[a + y][b + x] = 0;
					}
				}
			}
			current.counterClockwiseRotate();
			placePiece(x,y);
		}
	}
	
	public void updateDelays()
	{
		if (framesUntilFall > 0)
		{
			framesUntilFall--;
		}
		if (framesUntilFastFall > 0)
		{
			framesUntilFastFall--;
		}
		if (framesUntilShift > 0)
		{
			framesUntilShift--;
		}
		if (framesUntilDASShift > 0)
		{
			framesUntilDASShift--;
		}
	}
	
	public int getScore()
	{
		return currentScore;
	}
	public void setColor(Graphics g, int c)
	{
		if (c == 1)
		{
			g.setColor(Color.CYAN);
		}
		else if (c == 2)
		{
			g.setColor(new Color(255,165,0));
		}
		else if (c == 3)
		{
			g.setColor(Color.BLUE);
		}
		else if (c == 4)
		{
			g.setColor(new Color(147,112,219));
		}
		else if (c == 5)
		{
			g.setColor(Color.GREEN);
		}
		else if (c == 6)
		{
			g.setColor(Color.RED);
		}
		else if (c == 7)
		{
			g.setColor(Color.YELLOW);
		}
		else if (c == 9) //Boundaries
		{
			g.setColor(Color.DARK_GRAY);
		}
		else
		{
			g.setColor(Color.WHITE);
		}
	}	

	public boolean isOver()
	{
		return isOver;
	}
	
	public boolean isPaused()
	{
		return isPaused;
	}
	
	private boolean searchForCharacter(char c)
	{
		return game.searchForCharacter(c);	
	}
	
	private boolean searchForBriefCharacter(char c)
	{
		return game.searchForBriefCharacter(c);	
	}
	
	
	
	public void removeLingeringKeys()
	{
		game.removeLingeringKeys();
	}
	
	public void displayInMatrix()
	{
		System.out.println("___________________________");
		for (int a = 0; a < tetrisBoard.length; a++)
		{
			for (int b = 0; b < tetrisBoard[0].length; b++)
			{
				System.out.print(tetrisBoard[a][b] + " ");
			}
			System.out.println();
		}
		System.out.println("___________________________");
	}
}
/* 
 * 
 * 
 * 
 * 
 * 
*/