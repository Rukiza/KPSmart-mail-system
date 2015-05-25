package kps.ui.form;

import kps.enums.Day;
import kps.enums.Priority;
import kps.enums.TransportType;


public interface RouteFormListener extends FormListener {

	public void onRouteFormSubmitted(String company, String to, String from, TransportType type, double weightCost, double volCost
			, double maxWeight, double maxVol, double dur, double freq, Priority priority, Day day);

}