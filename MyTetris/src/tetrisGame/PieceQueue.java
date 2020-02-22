package tetrisGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PieceQueue extends JPanel {
	private static final long serialVersionUID = 8901474768054379443L;
	//Shows the next 5 pieces
	private int queueVisual[][] = new int[26][5];
	private int pieceIds[] = new int[7];
	private ArrayList<Integer> nextBag = new ArrayList<Integer>();
	
	public PieceQueue()
	{
		this.setVisible(true);	
		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.black));
		initializeQueue();
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		int boxX, boxY;
		int boxWidth = getWidth() / queueVisual[0].length;
		int boxHeight = boxWidth;
		for (int a = 0; a < queueVisual.length; a++)
		{
			for (int b = 0; b < queueVisual[0].length; b++)
			{
				boxY = boxWidth  * a;
				boxX = (boxHeight * b) - (int)(0.5 * boxHeight);
				
				if (queueVisual[a][b] != 0)
				{
					setColor(g, queueVisual[a][b]);
					g.fillRect(boxX, boxY, boxWidth, boxHeight);
					g.setColor(Color.black);
					g.drawRect(boxX, boxY, boxWidth, boxHeight);	
				}
				
				
			}
		}	
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
		else
		{
			g.setColor(Color.WHITE);
		}
	}
	
	public void initializeQueue()
	{
		int id;
		int b = 0;
		ArrayList<Integer> allIds = new ArrayList<Integer>();
		for (int a = 0; a < 7; a++)
		{
			allIds.add(a);
		}
		while(allIds.size() > 0)
		{
			id = (int)((Math.random()) * (allIds.size() - 1));
			pieceIds[b] = allIds.get(id);
			allIds.remove(id);
			b++;
		}
		createNewBag();
		repaint();
	}
	
	public void createNewBag()
	{
		int id;
		ArrayList<Integer> allIds = new ArrayList<Integer>();
		for (int a = 0; a < 7; a++)
		{
			allIds.add(a);
		}
		while(allIds.size() > 0)
		{
			id = (int)((Math.random()) * (allIds.size() - 1));
			nextBag.add(allIds.get(id));
			allIds.remove(id);
		}
	}
	
	public int progressQueue()
	{
		int currentId = pieceIds[0];
		for (int a = 0; a < 6; a++)
		{
			pieceIds[a] = pieceIds[a+1];
		}
		pieceIds[6] = nextBag.get(0);
		nextBag.remove(0);
		if (nextBag.size() < 1)
		{
			createNewBag();
		}
		updateVisual();
		return currentId;
	}
	
	public void updateVisual()
	{
		queueVisual = new int[26][5];
		int currentShape[][];
		int cornerX = 1, cornerY;
		for (int a = 0; a < 5; a++)
		{
			currentShape = TetrisPiece.allShapes[pieceIds[a]][0];
			cornerY = (a * 5) + 1;
			for (int b = 0; b < currentShape.length; b++)
			{
				for (int c = 0; c < currentShape[0].length; c++)
				{
					queueVisual[b + cornerY][c + cornerX] = currentShape[b][c];
				}
			}
		}
		repaint();
	}
}
