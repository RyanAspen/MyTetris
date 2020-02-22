package tetrisGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class LevelAndScoreDisplayer extends JPanel {
	private static final long serialVersionUID = -3724970430605385419L;
	private int score = 0;
	private int level;
	
	public LevelAndScoreDisplayer(int initialLevel)
	{
		level = initialLevel;
		this.setVisible(true);	
		setBackground(Color.GRAY);
		setBorder(BorderFactory.createLineBorder(Color.black));
		repaint();
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		int displayWidth = getWidth();
		int displayHeight = getHeight();
		g.setFont(new Font ("SansSerif", Font.BOLD, 20));
		g.setColor(Color.BLACK);
		g.drawString("Level: " + level, (int)(displayWidth * 0.05), (int)(displayHeight * 0.15));
		g.drawString("Score: " + score, (int)(displayWidth * 0.05), (int)(displayHeight * 0.65));
	}
	
	public void setDisplay(int score, int level)
	{
		this.score = score;
		this.level = level;
		repaint();
	}
}
