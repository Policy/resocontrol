package resocontrol;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.print.attribute.standard.MediaSize.JIS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONException;

import pachube.PachubeRequest;



import Pachube.Data;
import Pachube.Feed;
import Pachube.Pachube;
import Pachube.PachubeException;

public class PSensorManager implements Runnable {
	static Logger logger = Logger.getLogger("MyLog");

	final public static String KEY = "rlIuOhxTNqW3peQe9bRLAiWBYGlCi3HCsL9iIM32Vb4";
	final long UPDATE_TIME_MS = 5000;

	FeedsPool feedsPool;

	public PSensorManager(FeedsPool fp) {
		this.feedsPool = fp;
	}

	public JSONArray getFeedArray() throws IOException{
		PachubeRequest pr = new PachubeRequest("http://api.pachube.com/v2/feeds", "json");
		pr.addParameter("key", KEY);
		pr.addParameter("content", "summary");
		try {
			String JSONAvailableSensors = pr.retrieveJSON();
			JSONObject q = new JSONObject(JSONAvailableSensors);
			int o = (Integer) q.get("itemsPerPage");
			System.out.println(""+o);
			JSONArray a = q.getJSONArray("results");
			return a;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;

	}

	public void populatePool() throws IOException{
		JSONArray a = getFeedArray();
		Vector<FeedWrapper> v = new Vector<FeedWrapper>();
		FeedEvaluator fe = new FeedEvaluator(v);
		try {
			for(int i = 0; i < a.length(); i++ ){
				JSONObject finfo = (JSONObject) a.get(i);
				FeedWrapper feed = new FeedWrapper();
				feed.setFeedInfo(finfo);
				if (fe.isInteresting(feed) && fe.needMore()){
					v.add(feed);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		//Synch
		feedsPool.update(v);
		
		
	}

	public void run() {
		// Every UPDATE_TIME_MS populates a list of interesting feeds
		while(true){
			try {
				populatePool();
			} catch (IOException e1) {
				e1.printStackTrace();
				continue;
			}
			try {
				Vector<FeedWrapper> g = feedsPool.get();
				FeedWrapper currentFeed = g.get((int)(Math.random()*(g.size()-1)));
				logger.log(Level.INFO, "Setting the current and waiting");
				feedsPool.setCurrentFeed(currentFeed);
				Thread.sleep(UPDATE_TIME_MS);
			}
			catch (JSONException e) {
				e.printStackTrace();
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}

