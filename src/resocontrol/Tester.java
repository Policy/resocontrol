package resocontrol;
import java.util.logging.Logger;

public class Tester  {
	static Logger logger = Logger.getLogger("MyLog");
	static final String RESOCONTROLLER_NAME = "Reso-control";

	public static void main(String[] args){
		IACWrapper iw = new IACWrapper();
		iw.setOutput();
		iw.playTestNote();

	}

}
