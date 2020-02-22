package tetrisGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

public class SetControlsScreen extends Screen
{
	private static final long serialVersionUID = -1919646616413723964L;
	private BindChangeButton[] bindButtons;
	private ControlDisplay[] controlDisplays;
	private TitleButton titleButton;
	private static BindChangeButton activeButton;
	public SetControlsScreen()
	{
		super(600, 1000, Color.DARK_GRAY, "Modify Controls", new GridLayout(0,2));
		initializeBindButtons();
		for (int a = 0; a < bindButtons.length; a++)
		{		
			add(bindButtons[a]);
			add(controlDisplays[a]);
		}
		titleButton = new TitleButton(this);
		add(titleButton);
	}
	
	public void initializeBindButtons()
	{
		bindButtons = new BindChangeButton[BindChangeButton.getNumberOfActions()];
		controlDisplays = new ControlDisplay[BindChangeButton.getNumberOfActions()];
		for (int a = 0; a < BindChangeButton.getNumberOfActions(); a++)
		{
			bindButtons[a] = new BindChangeButton(a, this);
			controlDisplays[a] = new ControlDisplay(a);
		}
	}

	public boolean hasHitTitle() throws InterruptedException 
	{
		Thread.sleep(1);
		if (titleButton.getStatus())
		{
			titleButton.resetButton();
			return true;
		}
		return false;
	}
	
	public BindChangeButton getActiveButton()
	{
		return activeButton;
	}
	
	public static void setActiveButton(BindChangeButton button)
	{
		activeButton = button;
	}
	
	public void resetActiveButton()
	{
		activeButton = null;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		char c = (char) arg0.getKeyCode();
		if (BindChangeButton.getActiveId() != -1)
		{
			BindChangeButton.changeBind(BindChangeButton.getActiveId(), c);
			updateControlDisplay(); 
			activeButton.resetButton();
			resetActiveButton();
		}
		//Put KeyActive into bind array at index of active button, if any are active
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	public void updateControlDisplay()
	{
		for (int a = 0; a < controlDisplays.length; a++)
		{
			controlDisplays[a].updateDisplay();
		}
	}
}
