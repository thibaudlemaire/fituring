package detectionRythme;

public class PositionPics {

	private boolean[][] Pics ;
	private boolean[] selectionAutocorr;
	
	
	public PositionPics(){
		this.Pics=new boolean[450][66];
		this.selectionAutocorr = new boolean[66];
	}
	
	public boolean[][] getPics(){
		return this.Pics;
	}
	
	public void setPics(int line, int colonne,boolean val){
		Pics[line][colonne]=val;
	}
	
	public boolean[] getSelectionAutocorr(){
		return this.selectionAutocorr;
	}
	
	public void SetSelectionAutocorr(){
		for (int j=0;j<66;j++){
			int a=0;
			for (int i=0;i<450;i++){
				if (Pics[i][j]){
					a=1;
				}
			}
		if (a==1){
			selectionAutocorr[j]=true;
		}
		}
	}
}
