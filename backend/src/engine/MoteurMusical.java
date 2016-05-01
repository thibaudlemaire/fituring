package engine;

import interfaces.BPMupdateInterface;
import interfaces.MovementFoundInterface;
import interfaces.UpdateParamInterface;

public class MoteurMusical implements 	BPMupdateInterface, 
										MovementFoundInterface, 
										UpdateParamInterface
{

	@Override
	public void connected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnected() {
		// TODO Auto-generated method stub
		
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
