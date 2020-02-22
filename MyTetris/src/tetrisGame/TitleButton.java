package tetrisGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;


public class TitleButton extends Button{
	private static final long serialVersionUID = -7259867836152148393L;
	private boolean wasClicked = false;
	public TitleButton(Screen screen)
	{
		super(screen);
		setText("Back");
		addActionListener(new ClickListener());
	}
	private class ClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			TitleButton start = (TitleButton) source;
			if (checkBinds())
			{
				start.clickButton();
			}
			else
			{
				setText("Invalid Control Scheme");
			}
		}
	}
	
	public void resetButton()
	{
		wasClicked = false;
	}
	public void clickButton()
	{
		wasClicked = true;
	}
	public boolean getStatus()
	{
		return wasClicked;
	}
	public boolean checkBinds()
	{
		ArrayList<Character> usedChars = new ArrayList<Character>();
		for (int a = 0; a < BindChangeButton.getNumberOfActions(); a++)
		{
			if (searchForCharacter(usedChars, BindChangeButton.binds[a]))
			{
				return false;
			}
			else
			{
				usedChars.add(BindChangeButton.binds[a]);
			}
		}
		return true;
	}
	private boolean searchForCharacter(ArrayList<Character> list, char c)
	{
		Character ch = new Character(c);
		for (int a = 0; a < list.size(); a++)
		{
			if (list.get(a).equals(ch))
			{
				return true;
			}
		}
		return false;		
	}
}
