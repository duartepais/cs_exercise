package dpais.exercise;

public class InputEvent {
	
	static public int nrEvents = 0;
	
	public String id = "";
	public String state = "";
	public String host = "";
	public String type = "";
	public long timestamp = 0;
	
	
	public InputEvent() {
		nrEvents += 1;
	}
}