package tetrisGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class HoldDisplayer extends JPanel {
	private static final long serialVersionUID = -6705726926195243246L;
	//Shows the next 5 pieces
	private int holdVisual[][] = new int[6][6];
	private int pieceId = -1;
	
	public HoldDisplayer()
	{
		this.setVisible(true);	
		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		int boxX, boxY;
		int boxWidth = getWidth() / holdVisual[0].length;
		int boxHeight = boxWidth;
		int displayWidth = getWidth();
		int displayHeight = getHeight();
		g.setFont(new Font ("SansSerif", Font.BOLD, 20));
		g.setColor(Color.BLACK);
		g.drawString("Hold", (int)(displayWidth * 0.35), (int)(displayHeight * 0.15));
		for (int a = 0; a < holdVisual.length; a++)
		{
			for (int b = 0; b < holdVisual[0].length; b++)
			{
				boxY = boxWidth  * a;
				boxX = boxHeight * b;
				if (holdVisual[a][b] != 0)
				{
					setColor(g, holdVisual[a][b]);
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
	
	public void firstHold(int currentId)
	{
		pieceId = currentId;
		updateVisual();
	}
	
	public int hold(int currentId)
	{
		int newCurrent = pieceId;
		pieceId = currentId;
		updateVisual();
		return newCurrent;
	}
	
	public void updateVisual()
	{
		holdVisual = new int[6][6];
		int currentShape[][] = TetrisPiece.allShapes[pieceId][0];
		int cornerX = 1, cornerY = 1;
		for (int a = 0; a < currentShape.length; a++)
		{
			for (int b = 0; b < currentShape[0].length; b++)
			{
				holdVisual[a + cornerY][b + cornerX] = currentShape[a][b];
			}
		}
		repaint();
	}
}
