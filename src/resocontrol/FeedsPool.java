package resocontrol;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;

public  class FeedsPool {

	Vector<FeedWrapper> feeds = new Vector<FeedWrapper>();
	FeedWrapper currentFeed = null;
	static Logger logger = Logger.getLogger("MyLog");

	public synchronized void update(Vector<FeedWrapper> feeds){
		this.feeds = feeds;
	}

	public synchronized Vector<FeedWrapper> get() throws InterruptedException{
		if (this.feeds == null && this.feeds.size() < 1) {
			wait();
		}
		return this.feeds;
	}

	

	public synchronized void needNew(){
		notifyAll();
	}

	public synchronized FeedWrapper getCurrentFeed() throws InterruptedException {
		if (this.currentFeed == null) {
			wait();
		}
		return currentFeed;
	}

	public synchronized void  setCurrentFeed(FeedWrapper currentFeed) throws JSONException, InterruptedException {
		currentFeed.expand();
		logger.log(Level.INFO, "Extracted feed " + currentFeed.getId() + " with name " + currentFeed.getName());
		this.currentFeed = currentFeed;
		notify();
		wait();
	}
}
