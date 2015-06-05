package kps.ui.formlistener;

import kps.data.Route;

public interface DeleteRouteListener extends RouteUpdateListener{

	public void onDeleteFormSubmitted(Route route);
}
