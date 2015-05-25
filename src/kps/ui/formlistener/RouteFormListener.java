package kps.ui.formlistener;

import kps.enums.Day;
import kps.enums.Priority;
import kps.enums.TransportType;


public interface RouteFormListener extends FormListener {

	/**
	 * Executed when a route form has been submitted
	 * @param company
	 * @param to
	 * @param from
	 * @param type
	 * @param weightCost
	 * @param volCost
	 * @param maxWeight
	 * @param maxVol
	 * @param dur
	 * @param freq
	 * @param priority
	 * @param day
	 */
	public void onRouteFormSubmitted(String company, String to, String from, TransportType type, double weightCost, double volCost
			, double maxWeight, double maxVol, double dur, double freq, Priority priority, Day day);

}