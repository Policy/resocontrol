package resocontrol;

import java.util.Vector;

import org.json.JSONObject;

import Pachube.Feed;

public class FeedEvaluator {
	
	Vector<FeedWrapper> feeds = new Vector<FeedWrapper>(); 
	
	public FeedEvaluator(Vector<FeedWrapper> v) {
		feeds = v;
	}

	public boolean isInteresting(FeedWrapper feed) {
		return true;
	}

	public boolean needMore() {
		if (feeds.size() > 5)
			return false;
		return true;
	}

}
