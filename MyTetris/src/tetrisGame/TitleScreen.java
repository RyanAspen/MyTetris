package tetrisGame;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TitleScreen extends Screen {
	private static final long serialVersionUID = 1371465588324568323L;
	private GridBagConstraints layoutConstraints = new GridBagConstraints();
	private StartButton startButton = new StartButton();
	private SetControlsButton controlsButton; 
	private LevelSetter levelSetter = new LevelSetter();
	private Image titleArt; 
	private ImageArt titleLabel; 
	public TitleScreen()
	{
		super(800,800,Color.DARK_GRAY, "NES Tetris", new GridBagLayout());
		try {
			titleArt = ImageIO.read(new File("C:\\Users\\Ryan\\Pictures\\TetrisImages\\TitleArt.PNG"));
			titleLabel = new ImageArt(titleArt);
		}
		catch(IOException ex)
		{
			System.out.println("Files not found");
		}
		controlsButton = new SetControlsButton(this);
		//At some point, the buttons should be added to the buttons ArrayList
		initializeConstraints();
		updateConstraints(0, 0, 1, 1, titleLabel.getWidth(), titleLabel.getHeight());
		add(titleLabel, layoutConstraints);
		updateConstraints(0, 1, 1, 1, 200, 100);
		add(startButton, layoutConstraints);
		updateConstraints(0, 2, 1, 1, 50, 100);
		add(levelSetter, layoutConstraints);
		updateConstraints(0, 3, 1, 1, 200, 100);
		add(controlsButton, layoutConstraints);
		setResizable(false);
		setVisible(true);
	}
	
	public void initializeConstraints()
	{
		layoutConstraints.weightx = 1;
		layoutConstraints.weighty = 1;
		layoutConstraints.fill = GridBagConstraints.NORTH;
	}
	public void updateConstraints(int gx, int gy, int gwidth, int gheight, int ix, int iy)
	{
		layoutConstraints.gridx = gx;
		layoutConstraints.gridy = gy;
		layoutConstraints.gridwidth = gwidth;
		layoutConstraints.gridheight = gheight;
		layoutConstraints.ipadx = ix;
		layoutConstraints.ipady = iy;
	}
	
	public boolean hasGameStarted() throws InterruptedException
	{
		Thread.sleep(1);
		if (startButton.getStatus())
		{
			startButton.resetButton();
			return true;
		}
		return false;
	}
	
	public boolean hasHitOptions() throws InterruptedException
	{
		Thread.sleep(1);
		if (controlsButton.getStatus())
		{
			controlsButton.resetButton();
			return true;
		}
		return false;
	}
	public int getInitialLevel()
	{
		return (int) levelSetter.getValue();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
