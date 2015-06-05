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
import kps.ui.formlistener.RouteUpdateListener;
import kps.ui.util.SpringUtilities;
import kps.ui.util.UIUtils;

public class AbstractRouteChooserWindow extends AbstractFormWindow{

	protected RouteUpdateListener listener;
	protected RouteGraph routeGraph;

	protected JPanel inputPanel;

	protected final String FROM = "from";
	protected final String TO = "to";
	protected final String ROUTES = "routes";


	// to and from comboboxes
	// items change dynamically
	private JComboBox<Object> fromComboBox;
	private JComboBox<Object> toComboBox;
	private JComboBox<Object> routesComboBox;

	private String[] fieldNames = new String[] { FROM, TO, ROUTES };

	public AbstractRouteChooserWindow(String title, RouteUpdateListener listener, RouteGraph routeGraph) {
		super(title);

		this.listener = listener;
		this.routeGraph = routeGraph;
		setLayout(new BorderLayout());

		// add fields
		inputPanel = new JPanel();
		inputPanel.setLayout(new SpringLayout());

		// to and from fields are different
		// the combobox objects must be accessible for dynamic item changing
		fromComboBox = makeFromComboBox(routeGraph.getNodes().toArray(new Node[]{}), inputPanel);
		toComboBox = makeToComboBox(inputPanel);
		routesComboBox = makeComboBox(fieldNames[2], new Object[]{}, inputPanel);

		// populate the to combobox (and also the routes combo)
		populateToCombo();

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
	 * @return the combobox which is created
	 */
	private JComboBox<Object> makeFromComboBox(Node[] sources, Container cont){
		JComboBox<Object> fromComboBox = makeComboBox(fieldNames[0], sources, cont);
		fromComboBox.removeActionListener(fromComboBox.getActionListeners()[0]);

		fromComboBox.addActionListener((ActionEvent e) -> {
			comboBoxUpdated(fromComboBox, fieldNames[0]);
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
		JComboBox<Object> toComboBox = makeComboBox(fieldNames[1], new Node[]{}, cont);
		toComboBox.removeActionListener(toComboBox.getActionListeners()[0]);
		toComboBox.addActionListener((ActionEvent e) -> {
			comboBoxUpdated(toComboBox, fieldNames[1]);
			populateRouteCombo();
		});
		return toComboBox;
	}

	private void populateToCombo(){
        comboBoxUpdated(toComboBox, fieldNames[1]);
        Node from = (Node) fromComboBox.getSelectedItem();
        Set<String> validDests = routeGraph.destsFromSource(from.getName());
        toComboBox.setModel(new DefaultComboBoxModel<Object>(validDests.toArray()));
        populateRouteCombo();
	}

	protected void populateRouteCombo(){
        String source = (String) fromComboBox.getSelectedItem().toString();
        String dest = (String) toComboBox.getSelectedItem().toString();
        Set<Route> validRoutes = routeGraph.getRoutes(source, dest);

        routesComboBox.setModel(new DefaultComboBoxModel<Object>(validRoutes.toArray()));
        comboBoxUpdated(routesComboBox, fieldNames[2]);
	}

	private void fireFormUpdate(){
		Route route = (Route)fields.get(fieldNames[2]);
        listener.onRouteUpdate(route);
	}

	@Override
	protected boolean isFormComplete() {
		for (Object field : fields.values()){
			if (field == null)
				return false;
		}
		return true;
	}

}
