package kps.ui.formlistener;

import kps.enums.Priority;

public interface PriceUpdateListener extends FormListener{

	public void onPriceUpdateSubmitted(String to, String from, Priority priority, double weightCost, double volumeCost);

}
