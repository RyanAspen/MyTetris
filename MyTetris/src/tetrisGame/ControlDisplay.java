package tetrisGame;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ControlDisplay extends JPanel{
	private static final long serialVersionUID = 3260296773247966514L;
	private int actionId;
	private char characterOnDisplay;
	private JLabel label = new JLabel();
	private static Border border = BorderFactory.createRaisedBevelBorder();
	private static final char LEFT_ARROW = '%';
	private static final char RIGHT_ARROW = '\'';
	private static final char UP_ARROW = '&';
	private static final char DOWN_ARROW = '(';
	private static final char SPACE = ' ';
	public ControlDisplay(int actionId)
	{
		this.actionId = actionId;
		characterOnDisplay = BindChangeButton.binds[actionId];
		if (characterOnDisplay == LEFT_ARROW)
		{
			label = new JLabel("Left Arrow");
		}
		else if (characterOnDisplay == RIGHT_ARROW)
		{
			label = new JLabel("Right Arrow");
		}
		else if (characterOnDisplay == UP_ARROW)
		{
			label = new JLabel("Up Arrow");
		}
		else if (characterOnDisplay == DOWN_ARROW)
		{
			label = new JLabel("Down Arrow");
		}
		else if (characterOnDisplay == SPACE)
		{
			label = new JLabel("Space Bar");
		}
		else
		{
			label = new JLabel(Character.toString(characterOnDisplay));
		}
		add(label);
		label.setOpaque(true);
		setBorder(border);
		setBackground(Color.white);
	}
	public void updateDisplay()
	{
		characterOnDisplay = BindChangeButton.binds[actionId];
		
		if (characterOnDisplay == LEFT_ARROW)
		{
			label.setText("Left Arrow");
		}
		else if (characterOnDisplay == RIGHT_ARROW)
		{
			label.setText("Right Arrow");
		}
		else if (characterOnDisplay == UP_ARROW)
		{
			label.setText("Up Arrow");
		}
		else if (characterOnDisplay == DOWN_ARROW)
		{
			label.setText("Down Arrow");
		}
		else if (characterOnDisplay == SPACE)
		{
			label.setText("Space Bar");
		}
		else
		{
			label.setText(Character.toString(characterOnDisplay)); 
		}
	}
}


