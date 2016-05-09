package engine;

import java.util.ArrayList;

import interfaces.BPMupdateInterface;
import interfaces.LectureAudioInterface;
import interfaces.LectureInterface;
import interfaces.MovementFoundInterface;
import interfaces.UpdateParamInterface;

public class MoteurMusical implements 	BPMupdateInterface, 
										MovementFoundInterface, 
										UpdateParamInterface
{
	private ArrayList<Movement> movements = new ArrayList<Movement>();
	private ArrayList<Sound> sounds = new ArrayList<Sound>();
	
	private LectureAudioInterface player;
	
	public void initEngine(LectureAudioInterface player)
	{
		this.player = player;
		
		movements.add(new MovementNormal("toto.mvt", new int[] {1, 2, 3, 4, 5} ));
		sounds.add(new Sound("monSon.wav", new int[] {5, 4, 3, 2, 1, 0, 10, 9} ));
		sounds.add(new Sound("bass1.wav", new int[] {20, 45, 0, 10, 15, 0, 20, 10} ));
		sounds.add(new Sound("bass2.wav", new int[] {20, 0, 0, 10, 0, 10, 10, 5} ));
		sounds.add(new Sound("bass3.wav", new int[] {40, 40, 0, 20, 0, 10, 20, 10} ));
		sounds.add(new Sound("bass4.wav", new int[] {30, 45, 0, 10, 20, 0, 20, 20} ));
		sounds.add(new Sound("bass5.wav", new int[] {25, 30, 0, 30, 5, 0, 10, 0} ));
		sounds.add(new Sound("loopamb1.wav", new int[] {25, 10, 0, 5, 0, 10, 10, 0} ));
		sounds.add(new Sound("loopamb2.wav", new int[] {30, 10, 0, 5, 0, 10, 10, 0} ));
		sounds.add(new Sound("loopamb3.wav", new int[] {30, 0, 0, 0, 10, 10, 0, 40} ));
		sounds.add(new Sound("loopamb4.wav", new int[] {30, 0, 0, 0, 10, 50, 0, 40} ));
		sounds.add(new Sound("loopamb5.wav", new int[] {35, 0, 0, 30, 5, 10, 10, 10} ));
		sounds.add(new Sound("loopamb6.wav", new int[] {40, 30, 0, 30, 10, 0, 10, 0} ));
		sounds.add(new Sound("loopamb7.wav", new int[] {25, 30, 0, 30, 5, 10, 10, 10} ));
		
		for (int i = 1; i < 9; i++)
			sounds.add(new Sound("arab" + i + ".wav", new int[] {45, 0, 100, 0, 0, 10, 0, 0} ));
		
		sounds.add(new Sound("batt1.wav", new int[] {50, 0, 0, 0, 50, 0, 0, 20} ));
		sounds.add(new Sound("batt2.wav", new int[] {45, 0, 0, 0, 50, 0, 0, 20} ));
		sounds.add(new Sound("batt3.wav", new int[] {45, 0, 0, 0, 50, 0, 10, 20} ));
		sounds.add(new Sound("caiss1.wav", new int[] {10, 0, 0, 0, 100, 0, 0, 0} ));
		sounds.add(new Sound("claps.wav", new int[] {0, 0, 0, 0, 0, 50, 0, 0} ));
		sounds.add(new Sound("loop1.wav", new int[] {30, 0, 0, 50, 0, 0, 10, 0} ));
		sounds.add(new Sound("loop2.wav", new int[] {30, 0, 0, 50, 20, 0, 10, 0} ));
		sounds.add(new Sound("loop3.wav", new int[] {20, 0, 0, 30, 30, 0, 10, 0} ));
		sounds.add(new Sound("loop4.wav", new int[] {10, 0, 0, 30, 30, 0, 0, 0} ));
		sounds.add(new Sound("loop5.wav", new int[] {5, 0, 0, 30, 30, 0, 0, 0} ));
		sounds.add(new Sound("loop6.wav", new int[] {5, 0, 0, 30, 30, 10, 0, 0} ));
		sounds.add(new Sound("loop7.wav", new int[] {5, 0, 0, 20, 30, 20, 0, 0} ));
		sounds.add(new Sound("loop8.wav", new int[] {5, 0, 0, 50, 30, 20, 0, 0} ));
		sounds.add(new Sound("loop9.wav", new int[] {10, 0, 0, 50, 50, 0, 0, 0} ));
		sounds.add(new Sound("loop10.wav", new int[] {15, 0, 0, 50, 50, 0, 0, 0} ));
		sounds.add(new Sound("loop11.wav", new int[] {20, 0, 0, 20, 50, 0, 0, 0} ));
		sounds.add(new Sound("loop12.wav", new int[] {40, 20, 0, 10, 50, 0, 0, 0} ));
		sounds.add(new Sound("loop13.wav", new int[] {40, 50, 0, 40, 20, 0, 10, 0} ));
		
		for (int i = 1; i < 3; i++) {
			sounds.add(new Sound("bass" + i +".mp3", new int[] {30, 20, 0, 0, 0, 0, 60, 20} ));
			sounds.add(new Sound("danc120" + i +".mp3", new int[] {40, 20, 0, 00, 00, 0, 60, 20} ));
		}
		
		for (int i = 1; i < 8; i++) {
			sounds.add(new Sound("disco" + i + ".mp3", new int[] {50, 10, 0, 70, 20, 0, 20, 0} ));
		}
		
		for (int i = 1; i < 26; i++) {
			sounds.add(new Sound("bass" + i + ".mp3", new int[] {50, 70, 0, 30, 20, 0, 0, 0} ));
		}
		
		for (int i = 1; i < 3; i++)
			sounds.add(new Sound("dj" + i + ".mp3", new int[] {60, 70, 0, 0, 40, 0, 0, 0} ));
	
		for (int i = 1; i < 16; i++)
			sounds.add(new Sound("elec" + i + ".mp3", new int[] {50, 80, 0, 0, 20, 0, 0, 0} ));
		
		for (int i = 1; i < 50; i++)
			sounds.add(new Sound("gui" + i + ".mp3", new int[] {40, 0, 0, 0, 0, 0, 0, 80} ));
		
		for (int i = 1; i < 11; i++) {
			sounds.add(new Sound("bass" + i + ".mp3", new int[] {40, 0, 0, 0, 0, 80, 0, 0} ));
			sounds.add(new Sound("hiphop" + i + ".mp3", new int[] {40, 0, 0, 0, 0, 80, 0, 0} ));
		}
		
		for (int i = 1; i < 3; i++)
			sounds.add(new Sound("clari" + i + ".mp3", new int[] {60, 40, 0, 20, 20, 20, 20, 20} ));
		
		for (int i = 1; i < 7; i++)
			sounds.add(new Sound("saxo" + i + ".mp3", new int[] {60, 40, 0, 20, 20, 20, 20, 20} ));
		
		for (int i = 1; i < 4; i++)
			sounds.add(new Sound("rnb" + i + ".mp3", new int[] {40, 0, 0, 0, 0, 80, 0, 0} ));
	}
	
	public static final int RANGE = 0;
	public static final int ELECTRIC = 1;
	public static final int ARABE = 2;
	public static final int DISCO = 3;
	public static final int EXPLOSIVE = 4;
	public static final int HIPHOP = 5;
	public static final int DANCE = 6;
	public static final int ROCK = 7;
	
	@Override
	public void connected() {
		System.out.println("Client connecte");
	}

	@Override
	public void disconnected() {
		System.out.println("Client deconnecte");
	}

	@Override
	public void setVolume(int volume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStyle(int musicStyle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getVolume() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getStyle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void startFituring() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopFituring() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void MovementDone(int movementNumber) {
		Movement movement = movements.get(movementNumber);
		if (movement.getClass() == MovementSpecial.class)
		{
			MovementSpecial movementSpecial = (MovementSpecial) movement;
			Sound soundToPlay = sounds.get(movementSpecial.getSoundID());
			player.playSound(soundToPlay.getPath());
		}
		
	}

	@Override
	public void updateBPM(int newBPM) {
		// TODO Auto-generated method stub
		
	}

}
