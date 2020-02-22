package tetrisGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class StartButton extends JButton{
	private static final long serialVersionUID = -7259867836152148393L;
	private boolean wasClicked = false;
	public StartButton()
	{
		setText("Start");
		addActionListener(new ClickListener());
	}
	private class ClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			StartButton start = (StartButton) source;
			start.clickButton();
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
}
