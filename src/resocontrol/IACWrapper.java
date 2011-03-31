package resocontrol;
import rwmidi.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiDevice.Info;

public class IACWrapper {
	static Logger logger = Logger.getLogger("MyLog");
	static final String RESOCONTROLLER_NAME = "Reso-control";
	MidiOutput output;

	public IACWrapper(){
		super();
	}
	
	public void setOutput(){
		
		logger.log(Level.INFO, "Output:" );
		String[] outDevices = RWMidi.getOutputDeviceNames() ;

		int l = outDevices.length;

		for (int i = 0; i < l; i++) {
			logger.log(Level.INFO, outDevices[i] ); 
		}

		
		try {
			output = IACWrapper.getResoMIDIDevice().createOutput();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playTestNote(){
		output.sendNoteOn(0, 3, 3);
	}
	
	public static MidiOutputDevice getResoMIDIDevice() throws MidiUnavailableException{
		for (String i : RWMidi.getOutputDeviceNames()){
			logger.log(Level.INFO, i);
			if (i.startsWith(RESOCONTROLLER_NAME)){
				MidiOutputDevice d = RWMidi.getOutputDevice(i);
				return d;
			}
		}
		throw new MidiUnavailableException();

	}
	
	public void sendController(int channel, int cc, int value){
		logger.log(Level.INFO, "Playing value [" + value + "] on " + channel + "#" + cc);
		output.sendController(channel, cc, value);
	}
	
}
