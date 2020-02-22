package tetrisGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

public abstract class Button extends JButton implements KeyListener{
	private static final long serialVersionUID = 1885788223169020192L;
	private Screen screen;
	public Button(Screen screen)
	{
		this.screen = screen;
		addKeyListener(this);
	}
	public Screen getScreen()
	{
		return screen;
	}
	public void keyPressed(KeyEvent k) {}
	public void keyReleased(KeyEvent k) {}
	public void keyTyped(KeyEvent k) {}
	public abstract void resetButton();
}
