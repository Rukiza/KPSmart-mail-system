package kps.ui.window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import kps.data.Node;
import kps.data.Route;
import kps.data.RouteGraph;
import kps.ui.formlistener.DeleteRouteListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

public class DeleteRouteWindow extends AbstractFormWindow{

	private DeleteRouteListener listener;
	private RouteGraph routeGraph;

	// to and from comboboxes
	// items change dynamically
	private JComboBox<Object> toComboBox;
	private JComboBox<Object> routesComboBox;

	private String[] fieldNames = new String[] { "from", "to", "routes" };

	public DeleteRouteWindow(DeleteRouteListener deleteRouteListener, RouteGraph routeGraph) {
		super("Delete a route");

		this.listener = deleteRouteListener;
		this.routeGraph = routeGraph;
		setLayout(new BorderLayout());

		// add fields
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new SpringLayout());

		// to and from fields are different
		// the combobox objects must be accessible for dynamic item changing
		makeFromComboBox(routeGraph.getNodes().toArray(new Node[]{}), inputPanel);
		toComboBox = makeToComboBox(inputPanel);
		routesComboBox = makeComboBox(fieldNames[2], new Object[]{}, inputPanel);

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
			Route route = (Route)fields.get(fieldNames[2]);
			deleteRouteListener.onDeleteFormSubmitted(route);
			UIUtils.closeWindow(this);
		});

		cancel.addActionListener((ActionEvent e) -> {
			deleteRouteListener.onCancel();
			UIUtils.closeWindow(DeleteRouteWindow.this);
		});

		// open window
		setSize(new Dimension(600, 50 + fieldCount * 35));
		setVisible(true);
	}

	@Override
	protected JTextField makeTextField(String name, Container cont) {
		JTextField textField = super.makeTextField(name, cont);
		// get the text field that was just added by the super method
		textField.getDocument().addDocumentListener(new DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
					fields.put(name, textField.getText());
					// listen if all fields are complete
					if (isFormComplete()) {
						fireFormUpdate();
					}
                }
				@Override public void changedUpdate(DocumentEvent e) { }
                @Override public void removeUpdate(DocumentEvent e) {
                	fields.put(name, textField.getText());
					if (isFormComplete()) {
						fireFormUpdate();
					}
                }
		});
		return textField;
	}

	@Override
	protected JComboBox<Object> makeComboBox(String name, Object[] items, Container cont){
		JComboBox<Object> combobox = super.makeComboBox(name, items, cont);
		// get the combobox as most recently added component
		combobox.addItemListener((ItemEvent e) -> {
                if (isFormComplete()) {
                        fireFormUpdate();
                }
		});
		return combobox;
	}

	/**
	 * makes a combobox for the "from" field
	 * @param name
	 * @param items
	 * @param cont
	 */
	private JComboBox<Object> makeFromComboBox(Node[] sources, Container cont){
		JComboBox<Object> fromComboBox = makeComboBox(fieldNames[0], sources, cont);
		fromComboBox.removeActionListener(fromComboBox.getActionListeners()[0]);

		fromComboBox.addActionListener((ActionEvent e) -> {
			comboBoxUpdated(fromComboBox, fieldNames[0]);
			// update to combo
			Node from = (Node) fromComboBox.getSelectedItem();
			Set<String> validDests = routeGraph.destsFromSource(from.getName());
            toComboBox.setModel(new DefaultComboBoxModel<Object>(validDests.toArray()));
            comboBoxUpdated(toComboBox, fieldNames[1]);
            // update routes combo
            updateRoutesCombo();
		});
		return fromComboBox;
	}

	private JComboBox<Object> makeToComboBox(Container cont){
		JComboBox<Object> toComboBox = makeComboBox(fieldNames[1], new Node[]{}, cont);
		toComboBox.removeActionListener(toComboBox.getActionListeners()[0]);
		toComboBox.addActionListener((ActionEvent e) -> {
			comboBoxUpdated(toComboBox, fieldNames[0]);
			updateRoutesCombo();
		});
		return toComboBox;
	}

	private void updateRoutesCombo(){
        String source = (String) fields.get(fieldNames[0]).toString();
        String dest = (String) toComboBox.getSelectedItem().toString();
        Set<Route> validRoutes = routeGraph.getRoutes(source, dest);

        System.out.println("updating routes: " + validRoutes);

        routesComboBox.setModel(new DefaultComboBoxModel<Object>(validRoutes.toArray()));
        comboBoxUpdated(routesComboBox, fieldNames[2]);
	}

	private void fireFormUpdate(){
		Route route = (Route)fields.get(fieldNames[2]);
        listener.onCompletedFormUpdate(route);
	}

	@Override
	protected boolean isFormComplete() {
		System.out.println("form contains:");
		for (Object field : fields.values()){
			System.out.println(field);
			if (field == null)
				return false;
		}
		System.out.println("-------------");
		return true;
	}

}
