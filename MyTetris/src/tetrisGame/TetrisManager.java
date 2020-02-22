package tetrisGame;
/* TODO
 * - Add more GUI functionality (Screen superclass with streamlined layout management, etc.)
 * - Look at Delays for certain actions (Particularly at higher levels)
 * - Work on CPU
 */
import java.io.IOException;

public class TetrisManager{
	
	@SuppressWarnings("unused")
	public static void main(String args[]) throws IOException, InterruptedException
	{
		TitleScreen title = new TitleScreen();
		SetControlsScreen controlsScreen = new SetControlsScreen();
		title.open();
		controlsScreen.close();
		while(!title.hasGameStarted()) 
		{
			if(title.hasHitOptions())
			{
				title.close();
				controlsScreen.open();
			}
			
			if(controlsScreen.hasHitTitle())
			{
				controlsScreen.close();
				title.open();
			}

		}
		title.close();
		MainGame game = new MainGame(title.getInitialLevel());
	}
}