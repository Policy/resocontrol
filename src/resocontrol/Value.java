package resocontrol;

public class Value {
	double min_value;
	double max_value;
	double current_value;
	String id;
	
	public Value(String id, double current_value, double min_value, double max_value) {
		this.current_value = current_value;
		this.min_value = min_value;
		this.max_value = max_value;
		this.id = id;
	}
	
	public double getMin_value() {
		return min_value;
	}
	public void setMin_value(double min_value) {
		this.min_value = min_value;
	}
	public double getMax_value() {
		return max_value;
	}
	public void setMax_value(double max_value) {
		this.max_value = max_value;
	}
	public int getCurrent_value(double i) {
		//System.out.println("("+ current_value +" - " + min_value + ")/("+max_value+ " - "+min_value+ " )   *    " +i );
		if (max_value == min_value ) return 0;
		return safeLongToInt(((current_value*1.0-min_value*1.0)/(max_value*1.0-min_value*1.0)) *  i*1.0);
	}
	public void setCurrent_value(double current_value) {
		this.current_value = current_value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public  int safeLongToInt(double l) {
	    if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
	        return Integer.MAX_VALUE;
	    }
	    return (int) l;
	}
	
	
}
