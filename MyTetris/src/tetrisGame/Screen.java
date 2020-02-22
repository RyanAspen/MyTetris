package tetrisGame;

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public abstract class Screen extends JFrame implements KeyListener{
	private static final long serialVersionUID = 4655145862572993297L;
	private ArrayList<Button> buttons = new ArrayList<Button>();
	public Screen(int width, int length, Color backgroundColor, String title, LayoutManager layout)
	{
		setVisible(false);
		setSize(width, length);
		getContentPane().setBackground(backgroundColor);
		setLayout(layout);
		setTitle(title);
		addKeyListener(this);
		setFocusable(true);
		setResizable(false);
	}
	
	public void addButton(Button b)
	{
		buttons.add(b);
	}
	
	public void clearButtons()
	{
		buttons.clear();
	}
	
	public void open()
	{
		setVisible(true);
		requestFocusInWindow();
	}
	
	public void close()
	{
		setVisible(false);
		for (int a = 0; a < buttons.size(); a++)
		{
			buttons.get(a).resetButton();
		}
	}
}
