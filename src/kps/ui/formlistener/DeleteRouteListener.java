package kps.ui.formlistener;

import kps.enums.TransportType;

public interface DeleteRouteListener extends FormListener{

	public void onDeleteFormSubmitted(String company, String to, String from, TransportType type);

	public void onCompletedFormUpdate(String company, String to, String from, TransportType type);
}
