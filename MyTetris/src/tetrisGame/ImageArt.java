package tetrisGame;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class ImageArt extends JPanel{
	private static final long serialVersionUID = -1690290456133906320L;
	public Image image;
	public ImageArt(Image img)
	{
		image = img;
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image,0,0,this);
	}
	public int getWidth()
	{
		return image.getWidth(null);
	}
	public int getHeight()
	{
		return image.getHeight(null);
	}
}
