package pachube;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PachubeRequest {
	static Logger logger = Logger.getLogger("MyLog");
	
	private HashMap<String, String> parameters = new HashMap<String, String>();
	private String type;
	private String address;
	
	public PachubeRequest(String address, String type, HashMap<String, String> parameters){
		this(address, type);
		this.parameters = parameters;
	}
	
	public PachubeRequest(String address, String type){
		this.type = type;
		this.address = address;
	}
	
	public void addParameter(String key, String value){
		parameters.put(key, value);
	}
	
	public String getAddress(){
		String out = address.startsWith("http://") ? address : "http://" + address;
		if (type != null && type.length() > 0)  out += "." + type + "?";
		for (String o : parameters.keySet()){
			out +=o + "=" + parameters.get(o) + "&";
		}
		return (out.indexOf('?') == out.length() - 1) ? out.substring(0, out.length() - 1) : out;
	}
	
	public BufferedReader retrieveJSONReader() throws IOException{
		String address = this.getAddress();
		URL url;
		BufferedReader reader = null;
		InputStream response;
			logger.log(Level.INFO, "Retrieving " + address);
			url = new URL(address);
			if (((HttpURLConnection) url.openConnection()).getResponseCode() == HttpURLConnection.HTTP_OK){
				response = url.openStream();
				reader = new BufferedReader(new InputStreamReader(response));
				System.out.println(url.toString() + ":" + "Success");
			}
			else{
				System.out.println(url.toString() + ":" + ((HttpURLConnection) url.openConnection()).getResponseCode() );
				throw new IOException();
			}
				
		return reader;
	}
	
	public String retrieveJSON() throws IOException{
		String b = "";
		BufferedReader reader = retrieveJSONReader();
		try {
			for (String line; (line = reader.readLine()) != null;) {
			    b+=line;		    
			}
			assert(b.length() > 0);
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
}
