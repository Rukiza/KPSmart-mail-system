package kps.ui.formlistener;

import kps.enums.Priority;


public interface PriceUpdateListener extends RouteUpdateListener{

	public void onPriceUpdateSubmitted(String src, String dest, double weightCost, double volumeCost, Priority priority);

}
