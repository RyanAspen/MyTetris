package tetrisGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PausePanel extends JPanel{
	private static final long serialVersionUID = -6438796371802938219L;
	public PausePanel()
	{
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
		g.setFont(new Font ("SansSerif", Font.BOLD, 30));
		g.setColor(Color.BLACK);
		g.drawString("PAUSED", (int)(displayWidth * 0.05), (int)(displayHeight * 0.15));
	}
}
