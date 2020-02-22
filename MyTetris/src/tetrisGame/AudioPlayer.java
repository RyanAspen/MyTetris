package tetrisGame;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
	private Clip clip;
	private AudioInputStream audioInputStream;
	private String filePath = "C:\\Users\\Ryan\\Music\\Tetris Wav\\15-Soldier-of-Dance.wav";
	private FloatControl controller;
	
	public AudioPlayer() throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
		clip = AudioSystem.getClip();
		
		clip.open(audioInputStream);
		controller = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void play()  
    { 
        //start the clip 
        clip.start(); 
        controller.setValue(-30.0f);
    } 
	
}
