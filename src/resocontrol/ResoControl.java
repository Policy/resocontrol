package resocontrol;

public class ResoControl {

	
	public static void main(String args[]){
		//IACWrapper iw = new IACWrapper();
		//iw.setOutput();
		
		FeedsPool fp = new FeedsPool();
		PSensorManager psm = new PSensorManager(fp);
		new Thread(psm).start();
		
		SoundManager sm = new SoundManager(fp);
		new Thread(sm).start();
	}
}
