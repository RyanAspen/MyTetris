package tetrisGame;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class LevelSetter extends JSpinner{
	private static final long serialVersionUID = 6805157769803273067L;

	public LevelSetter()
	{
		setToolTipText("Choose Level");
		setModel(new SpinnerNumberModel(0, 0, 99, 1));
	}
}
