package kps.ui.formlistener;

import kps.data.Route;

public interface DeleteRouteListener extends FormListener{

	public void onDeleteFormSubmitted(Route route);

	public void onCompletedFormUpdate(Route route);
}
