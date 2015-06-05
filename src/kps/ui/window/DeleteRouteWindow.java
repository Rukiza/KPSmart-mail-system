package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import kps.data.Route;
import kps.data.RouteGraph;
import kps.ui.formlistener.DeleteRouteListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

/**
 * @author hardwiwill
 *
 * A popup window to prompt the user for a user to delete
 * When the users clicks on confirm button, the form details will be sent
 * to a DeleteRouteListener
 *
 */
public class DeleteRouteWindow extends AbstractRouteChooserWindow{

	private String[] fieldNames = new String[] { FROM, TO, ROUTES };

	public DeleteRouteWindow(DeleteRouteListener deleteRouteListener, RouteGraph routeGraph) {
		super("Delete a route", deleteRouteListener, routeGraph);

		int fieldCount = fieldNames.length;

		SpringUtilities.makeCompactGrid(inputPanel,
				fieldCount, 2,	//rows, cols
                6, 6,	//initX, initY
                6, 6);	//xPad, yPad)

		// add buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		JButton OK = new JButton("OK");
		JButton cancel = new JButton("Cancel");
		buttonPanel.add(OK);
		buttonPanel.add(cancel);

		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// event handling
		OK.addActionListener((ActionEvent e) -> {
			if (!isFormComplete()){
				completeFormPrompt();
				return;
			}
			Route route = (Route)fields.get(ROUTES);
			deleteRouteListener.onDeleteFormSubmitted(route);
			JOptionPane.showMessageDialog(this, "Route successfully deleted");
			UIUtils.closeWindow(this);
		});

		cancel.addActionListener((ActionEvent e) -> {
			deleteRouteListener.onCancel();
			UIUtils.closeWindow(this);
		});

		// open window
		pack();
		setVisible(true);
	}
}
