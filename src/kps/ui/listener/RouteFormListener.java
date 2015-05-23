package kps.ui.listener;

import kps.enums.Day;
import kps.enums.Priority;
import kps.enums.TransportType;


public interface RouteFormListener extends FormSubmitListener {

	public void onRouteFormSubmitted(String company, String to, String from, TransportType type, double weightCost, double volCost
			, double maxWeight, double maxVol, double dur, double freq, Priority priority, Day day);

	@Override
	public void onCancel();
}
