package kps.ui.formlistener;

import kps.data.Route;

public interface PriceUpdateListener extends RouteUpdateListener{

	public void onPriceUpdateSubmitted(Route route, double weightCost, double volumeCost);

}
