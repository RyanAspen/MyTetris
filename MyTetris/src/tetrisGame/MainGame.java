package tetrisGame;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

public class MainGame extends Screen{
	private static final long serialVersionUID = 1L;
	public static int frameTime = 17; //Approx 1000 / 60 (60 fps)
	private GameBoard gb; 
	private PieceQueue pq;
	private LevelAndScoreDisplayer lasd;
	private HoldDisplayer hd;
	private PausePanel p = new PausePanel();

	private GridBagConstraints layoutConstraints = new GridBagConstraints();
	
	private int searchForCharacterIndex; //Temporary variable used for button indexing
	private ArrayList<Character> keysActive = new ArrayList<Character>();
	private ArrayList<Character> keysMomentarilyActive = new ArrayList<Character>();
	
	public MainGame(int initialVal) throws IOException, InterruptedException
	{		
		super(900,1200,Color.BLACK, "NES Tetris", new GridBagLayout());
		initializeConstraints();		
		updateConstraints(1, 0, 1, 2, 482, 850);
		gb = new GameBoard(initialVal, this);
		add(gb, layoutConstraints);
		updateConstraints(2, 0, 1, 1, 150, 800);
		pq = gb.getQueue();
		add(pq, layoutConstraints);
		updateConstraints(2, 1, 1, 1, 150, 150);
		lasd = gb.getDisplayer();
		add(lasd, layoutConstraints);
		updateConstraints(0, 0, 1, 1, 150, 150);
		hd = gb.getHolder();
		add(hd, layoutConstraints);
		updateConstraints(0, 1, 1, 1, 150, 150);
		add(p, layoutConstraints);
		//gb.addKeyListener(gb); 
		open();
		while (true)
		{
			Thread.sleep(frameTime);
			gb.frame();
			if (gb.isPaused())
			{
				p.setVisible(true);
			}
			else
			{
				p.setVisible(false);
			}
		}
	}

	public void initializeConstraints()
	{
		layoutConstraints.weightx = 1;
		layoutConstraints.weighty = 1;
		layoutConstraints.fill = GridBagConstraints.NORTH;
	}
	public void updateConstraints(int gx, int gy)
	{
		layoutConstraints.gridx = gx;
		layoutConstraints.gridy = gy;
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
	
	public boolean isOver()
	{
		return gb.isOver();
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent arg0) 
	{
		char c = (char) arg0.getKeyCode();
		if (!searchForCharacter(c))
		{
			keysActive.add((char) arg0.getKeyCode());
			keysMomentarilyActive.add((char) arg0.getKeyCode());
		}
	}
	public void keyReleased(KeyEvent arg0) 
	{
		char c = (char) arg0.getKeyCode();
		if (searchForCharacter(c))
		{
			keysActive.remove(searchForCharacterIndex);
		}		
	}
	boolean searchForCharacter(char c)
	{
		Character ch = new Character(c);
		for (int a = 0; a < keysActive.size(); a++)
		{
			if (keysActive.get(a).equals(ch))
			{
				searchForCharacterIndex = a;
				return true;
			}
		}
		return false;		
	}
	
	boolean searchForBriefCharacter(char c)
	{
		Character ch = new Character(c);
		for (int a = 0; a < keysMomentarilyActive.size(); a++)
		{
			if (keysMomentarilyActive.get(a).equals(ch))
			{
				searchForCharacterIndex = a;
				return true;
			}
		}
		return false;		
	}
	
	
	
	void removeLingeringKeys()
	{
		for (int a = keysMomentarilyActive.size() - 1; a > -1; a--)
		{
			keysMomentarilyActive.remove(a);
		}
	}
}
