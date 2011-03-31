package resocontrol;

import java.util.HashMap;
import java.util.HashSet;

public class MappingManager {
	static final int[] availableCC = {1,2,3,4,5,6,7,8,9,10}; 
	HashMap<String, Integer> midimap = new HashMap<String, Integer>();
	

	public void updateInputs(HashMap<String, Value> hm) {
		HashSet<String> set=  new HashSet<String>();
		set.addAll(hm.keySet());
		HashMap<String, Integer> newmidimap = new HashMap<String, Integer>();
		for (String s : hm.keySet()){
			if (midimap.containsKey(s)) {
				newmidimap.put(s, midimap.get(s));
				set.remove(s);
			}
		}
		for (String s : set){
			int freemidi = -1;
			if(availableCC.length > midimap.size()){
				for (int i = 0; i < availableCC.length; i++){
					if (! newmidimap.containsValue(availableCC[i])) {
						freemidi = availableCC[i];
						break;
					}
				}
				newmidimap.put(s, freemidi);
			}
			else break;
		}
		midimap = newmidimap;
		
	}


	public HashMap<String, Integer> getMidimap() {
		return midimap;
	}


	public void setMidimap(HashMap<String, Integer> midimap) {
		this.midimap = midimap;
	}

}
