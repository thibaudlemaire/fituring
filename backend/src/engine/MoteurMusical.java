package engine;

import java.util.ArrayList;

import interfaces.BPMupdateInterface;
import interfaces.MovementFoundInterface;
import interfaces.UpdateParamInterface;

public class MoteurMusical implements 	BPMupdateInterface, 
										MovementFoundInterface, 
										UpdateParamInterface
{
	private ArrayList<Movement> movements = new ArrayList<Movement>();
	private ArrayList<Sound> sounds = new ArrayList<Sound>();
	
	public void initEngine()
	{
		movements.add(new MovementNormal("toto.mvt", new int[] {1, 2, 3, 4, 5} ));
		sounds.add(new Sound("monSon.wav", new int[] {5, 4, 3, 2, 1, 0, 10, 9} ));
	}
	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBPM(int newBPM) {
		// TODO Auto-generated method stub
		
	}

}
