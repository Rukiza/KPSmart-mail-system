package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import kps.KPSmartSystem;
import kps.data.Node;
import kps.enums.Priority;
import kps.ui.formlistener.PriceUpdateListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

public class PriceUpdateWindow extends AbstractFormWindow{

	private final String FROM = "from";
	private final String TO = "to";
	private final String OLD_WEIGHT_COST = "old weight cost";
	private final String OLD_VOL_COST = "old volume cost";
	private final String NEW_WEIGHT_COST = "new weight cost";
	private final String NEW_VOL_COST = "new volume cost";
	private final String PRIORITY = "priority";
	private final String[] fieldNames = new String[] { FROM , TO, OLD_WEIGHT_COST, OLD_VOL_COST, NEW_WEIGHT_COST, NEW_VOL_COST, PRIORITY};

	private KPSmartSystem system;

	/**
	 * displays the current weight cost of the currently selected route
	 */
	private JTextField oldWeightCostField;
	/**
	 * displays the current volume cost of the currently selected route
	 */
	private JTextField oldVolCostField;

	private JComboBox<Object> fromComboBox;
	private JComboBox<Object> toComboBox;

	public PriceUpdateWindow(PriceUpdateListener listener, KPSmartSystem system){

		super("update route price");
		this.system = system;
		setLayout(new BorderLayout());

		// add fields
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new SpringLayout());

		inputPanel = new JPanel();
		inputPanel.setLayout(new SpringLayout());

		int fieldCount = fieldNames.length;

		// populate the to combobox (and also the routes combo)
		// not dealing with routes, rather just src, dest
		List<String> nodeStrings = new ArrayList<>();
		for (Node n : system.getRouteGraph().getNodes()){
			nodeStrings.add(n.getName());
		}

		fromComboBox = makeFromComboBox(nodeStrings.toArray(new String[]{}), inputPanel);
		toComboBox = makeToComboBox(inputPanel);

		makeComboBox(PRIORITY, Priority.values(), inputPanel);

		// fields for the old (or current) prices
		oldWeightCostField = makeTextField(OLD_WEIGHT_COST, inputPanel);
		oldVolCostField = makeTextField(OLD_VOL_COST, inputPanel);
        oldWeightCostField.setEditable(false);
        oldVolCostField.setEditable(false);

		// fields for new price input
		makeTextField(NEW_WEIGHT_COST, inputPanel);
		makeTextField(NEW_VOL_COST, inputPanel);

		// initialise comboboxes
		populateToCombo();

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

			String from = (String) fields.get(FROM);
			String to = (String) fields.get(TO);
			Priority priority = (Priority)fields.get(PRIORITY);
			int weightCost = Integer.parseInt(weightStr);
			int volumeCost = Integer.parseInt(volStr);

			listener.onPriceUpdateSubmitted(from, to, weightCost, volumeCost, priority);
			UIUtils.closeWindow(this);
		});

		cancel.addActionListener((ActionEvent e) -> {
			listener.onCancel();
			UIUtils.closeWindow(PriceUpdateWindow.this);
		});

		// open window
		pack();
		setVisible(true);
	}

	@Override
	public void comboBoxUpdated(JComboBox<Object> comboBox, String name){
		super.comboBoxUpdated(comboBox, name);
		updateOldPrices();
	}

	/**
	 * makes a combobox for the "from" field
	 * @param name
	 * @param items
	 * @param cont
	 * @return the combobox which is created
	 */
	private JComboBox<Object> makeFromComboBox(String[] sources, Container cont){
		JComboBox<Object> fromComboBox = makeComboBox(FROM, sources, cont);
		fromComboBox.removeActionListener(fromComboBox.getActionListeners()[0]);

		fromComboBox.addActionListener((ActionEvent e) -> {
			comboBoxUpdated(fromComboBox, FROM);
            populateToCombo();
		});
		return fromComboBox;
	}

	/**
	 * make a combobox for the "to" field
	 * @param cont
	 * @return the combobox which is created
	 */
	private JComboBox<Object> makeToComboBox(Container cont){
		JComboBox<Object> toComboBox = makeComboBox(TO, new Node[]{}, cont);
		toComboBox.removeActionListener(toComboBox.getActionListeners()[0]);
		toComboBox.addActionListener((ActionEvent e) -> {
			comboBoxUpdated(toComboBox, TO);
		});
		return toComboBox;
	}

	private void populateToCombo(){
        comboBoxUpdated(toComboBox, TO);
        String from = (String) fromComboBox.getSelectedItem();
        Set<String> validDests = system.getRouteGraph().destsFromSource(from);
        toComboBox.setModel(new DefaultComboBoxModel<Object>(validDests.toArray()));
        updateOldPrices();
	}

	private void updateOldPrices(){
		String from = (String) fromComboBox.getSelectedItem();
		String to = (String) toComboBox.getSelectedItem();
		Priority priority = (Priority) fields.get(PRIORITY);
		double weightCost = system.getWeightCost(from, to, priority);
		double volCost = system.getVolumeCost(from, to, priority);

		String weightCostMsg = weightCost == 0 ? "not available" : weightCost + "";
		String volCostMsg = volCost == 0 ? "not available" : volCost + "";
		oldWeightCostField.setText(weightCostMsg);
		oldVolCostField.setText(volCostMsg);
	}

	@Override
	protected boolean isFormComplete() {
		for (String fieldName : fieldNames){
			if (fieldName.equals(OLD_WEIGHT_COST) || fieldName.equals(OLD_VOL_COST)){
				continue;
			}
			Object val = fields.get(fieldName);
			if (val == null){
				System.out.println(fieldName + " was null");
				return false;
			}
		}
		return true;
	}
}