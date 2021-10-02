package dpais.exercise;

public class AnalysedEvent {
	String host = "";
	String type = "";
	int duration;
	boolean alert;
	
	public AnalysedEvent(String analysedHost, String analysedType, int analysedDuration) {
		host = analysedHost;
		type = analysedType;
		duration = analysedDuration;
		alert = duration > 4 ? true : false ;	// evaluate the alert
	}
}
