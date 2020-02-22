package tetrisGame;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class BindChangeButton extends Button{
	private static final long serialVersionUID = -7259867836152148393L;
	private static String[] actions = {"Counter-Clockwise Rotaton", "Clockwise Rotation", "Fast Fall", "Quick Fall", "Left Shift", "Right Shift", "Hold", "Pause"};
	public static Character[] binds = {'A', 'D', '(', ' ', '%', '\'', 'E', 'P'}; //Default Controls
	private int actionId;
	private static int activeId = -1;
	public BindChangeButton(int actionId, SetControlsScreen screen)
	{
		super(screen);
		this.actionId = actionId;
		setText("Set " + actions[actionId]);
		setName(actions[actionId]);
		addActionListener(new ClickListener());
	}
	private class ClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			BindChangeButton buttonSource = (BindChangeButton) e.getSource();
			if (buttonSource.isButtonActive())
			{
				updateActiveButton(buttonSource);
				changeActiveId(buttonSource.getActionId());
				buttonSource.setText("Click the Key for " + actions[buttonSource.getActionId()]);
				activeId = buttonSource.getActionId();
			}			
		}
	}
	public static void updateActiveButton(BindChangeButton b)
	{
		SetControlsScreen.setActiveButton(b);
	}
	public int getActionId()
	{
		return actionId;
	}
	public static int getNumberOfActions()
	{
		return actions.length;
	}
	public static void changeActiveId(int id)
	{
		activeId = id;
	}
	public static int getActiveId()
	{
		return activeId;
	}
	public static void changeBind(int id, char c)
	{
		binds[id] = c;
	}
	public void keyPressed(KeyEvent arg0) 
	{
		char c = (char) arg0.getKeyCode();
		if (BindChangeButton.getActiveId() != -1)
		{
			BindChangeButton.changeBind(BindChangeButton.getActiveId(), c);
			((SetControlsScreen)getScreen()).updateControlDisplay(); 
			((SetControlsScreen)getScreen()).getActiveButton().resetButton();
			((SetControlsScreen)getScreen()).resetActiveButton();
		}
	}
	public boolean isButtonActive()
	{
		if (activeId == -1)
		{
			return true;
		}
		return (actionId == activeId);
	}
	public void resetButton()
	{
		setText("Set " + actions[actionId]);
		changeActiveId(-1);
	}
}
