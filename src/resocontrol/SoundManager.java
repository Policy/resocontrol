package resocontrol;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoundManager implements Runnable{
	int MIDICHANNEL = 1;
	long SLEEPTIME = 5000;
	FeedsPool feedsPool;
	IACWrapper iacw = new IACWrapper(); 

	static Logger logger = Logger.getLogger("MyLog");
	
	public SoundManager(FeedsPool fp) {
		this.feedsPool = fp;
		iacw.setOutput();
	}

	public void run() {
		feedsPool.needNew();
		MappingManager mm = new MappingManager();
		while(true){
			try {
				logger.log(Level.INFO, "New Cycle");
				FeedWrapper fw = feedsPool.getCurrentFeed();
				logger.log(Level.INFO, "Sound from: " + fw.getName());
				HashMap<String, Value> hm = fw.readValueMap();
				mm.updateInputs(hm);
				HashMap<String, Integer> map = mm.getMidimap();
				playSounds(hm, map);
				Thread.sleep(SLEEPTIME);

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(SLEEPTIME);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				feedsPool.needNew();
				continue;
			}
		}
	}

	private void playSounds(HashMap<String, Value> hm,
			HashMap<String, Integer> map) {
		for (String s : map.keySet()){
			int midi = map.get(s);
			Value v = hm.get(s);
			//logger.log(Level.INFO, "ORIGINAL:"+ v.current_value);
			//logger.log(Level.INFO, "ROUNDED:"+ v.getCurrent_value(127));
			iacw.sendController(1, midi, Math.round(v.getCurrent_value(127)));
		}
		
	}



}
