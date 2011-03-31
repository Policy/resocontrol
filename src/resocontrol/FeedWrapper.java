package resocontrol;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pachube.PachubeRequest;

public class FeedWrapper {
	JSONObject feedInfo;
	int id = -1;
	String name = null;

	public JSONObject getFeedInfo() {
		return feedInfo;
	}

	public void setFeedInfo(JSONObject feedInfo) {
		this.feedInfo = feedInfo;
	}

	public void expand() throws JSONException {
		name = feedInfo.get("title").toString();
		id = Integer.parseInt(feedInfo.get("id").toString());

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, Value> readValueMap() throws IOException {
		assert(id != -1);
		PachubeRequest pr = new PachubeRequest("http://api.pachube.com/v2/feeds/" + id, "json");	
		pr.addParameter("key", PSensorManager.KEY);
		try {
			return processRequest(pr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private HashMap<String, Value> processRequest(PachubeRequest pr) throws IOException, ParseException{
		String js = pr.retrieveJSON();
		JSONObject q;
		HashMap<String, Value> hm = new HashMap<String,Value>();
		try {
			q = new JSONObject(js);

			JSONArray datastreams = q.getJSONArray("datastreams");

			for(int i = 0; i < datastreams.length(); i++ ){
				JSONObject finfo = (JSONObject) datastreams.get(i);
				DecimalFormat fm = new DecimalFormat("#.#####");
				long max_value = (fm.parse(finfo.getString("max_value")).longValue());
				long min_value = (fm.parse(finfo.getString("min_value")).longValue());
				long value = (fm.parse(finfo.getString("current_value")).longValue());
				
				String id = finfo.getString("id");
				
				Value v = new Value(id, value, min_value, max_value);
				hm.put(v.getId(), v);

			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		assert false : "I shouldn't go here";
		return hm;
	}
}
