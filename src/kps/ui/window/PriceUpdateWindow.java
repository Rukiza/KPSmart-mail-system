package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import kps.data.Route;
import kps.data.RouteGraph;
import kps.ui.formlistener.PriceUpdateListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

public class PriceUpdateWindow extends AbstractRouteChooserWindow {

	private final String OLD_WEIGHT_COST = "old weight cost";
	private final String OLD_VOL_COST = "old volume cost";
	private final String NEW_WEIGHT_COST = "new weight cost";
	private final String NEW_VOL_COST = "new volume cost";
	private final String[] fieldNames = new String[] { FROM , TO, ROUTES, OLD_WEIGHT_COST , OLD_VOL_COST, NEW_WEIGHT_COST, NEW_VOL_COST };

	/**
	 * displays the current weight cost of the currently selected route
	 */
	private JTextField oldWeightCostField;
	/**
	 * displays the current volume cost of the currently selected route
	 */
	private JTextField oldVolCostField;

	public PriceUpdateWindow(PriceUpdateListener listener, RouteGraph routeGraph){

		super("Update route price", listener, routeGraph);

		int fieldCount = fieldNames.length;

		// fields for the old (or current) prices
		oldWeightCostField = makeTextField(OLD_WEIGHT_COST, inputPanel);
		oldVolCostField = makeTextField(OLD_VOL_COST, inputPanel);
        oldWeightCostField.setEditable(false);
        oldVolCostField.setEditable(false);

		// fields for new price input
		makeTextField(NEW_WEIGHT_COST, inputPanel);
		makeTextField(NEW_VOL_COST, inputPanel);

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
			// check digit fields
			String weightStr = (String)fields.get(NEW_WEIGHT_COST);
			String volStr = (String)fields.get(NEW_VOL_COST);
			if (!UIUtils.isInteger(weightStr, volStr)){
				numberFieldsPrompt("weight and volume costs should only contain digits");
				return;
			}

			Route route = (Route) fields.get(ROUTES);
			int weightCost = Integer.parseInt(weightStr);
			int volumeCost = Integer.parseInt(volStr);

			listener.onPriceUpdateSubmitted(route, weightCost, volumeCost);
			UIUtils.closeWindow(this);
		});

		cancel.addActionListener((ActionEvent e) -> {
			listener.onCancel();
			UIUtils.closeWindow(PriceUpdateWindow.this);
		});

		// open window
		setVisible(true);
	}

	@Override
	protected void populateRouteCombo(){
		super.populateRouteCombo();
		// update the old cost fields
		Route route = ((Route)fields.get(ROUTES));
		double weightPrice = route.getWeightPrice();
		double volPrice = route.getVolumePrice();

		oldWeightCostField.setText(weightPrice + "");
		oldVolCostField.setText(volPrice + "");
	}

	@Override
	protected boolean isFormComplete() {
		for (String fieldName : fieldNames){
			Object val = fields.get(fieldName);
			if (val == null)
				return false;
		}
		return true;
	}
}