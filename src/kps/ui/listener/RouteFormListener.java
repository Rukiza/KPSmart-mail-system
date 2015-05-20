package kps.ui.listener;


public interface RouteFormListener extends FormSubmitListener {

	public void onRouteFormSubmitted(String company, String to, String from, String type, double weightCost, double volCost
			, double maxWeight, double maxVol, double dur, double freq, String priority, String day);

	@Override
	public void onCancel();
}
