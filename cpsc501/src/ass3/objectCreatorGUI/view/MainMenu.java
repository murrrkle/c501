package ass3.objectCreatorGUI.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import ass3.serialize.Serializer;

public class MainMenu extends Frame implements ActionListener, ItemListener, ContainerListener {
	private ScrollPane scrollpane;
	private List objList;
	private Panel btnPanel;
	private ArrayList<Object> doc;
	private Serializer srl;

	public MainMenu() {
		setLayout(new BorderLayout());
		
		doc = new ArrayList<Object>();
		srl = new Serializer();

		// Button Panel
		btnPanel = new Panel(new GridLayout(0, 1));
		add("East", btnPanel);

		Button addButton = new Button("Add");
		addButton.addActionListener(this);
		btnPanel.add(addButton);

		Button editButton = new Button("Edit...");
		editButton.addActionListener(this);
		btnPanel.add(editButton);

		Button deleteButton = new Button("Delete");
		deleteButton.addActionListener(this);
		btnPanel.add(deleteButton);

		Button sendButton = new Button("Serialize & Send");
		sendButton.addActionListener(this);
		btnPanel.add(sendButton);

		// Scrollpane
		objList = new List();
		objList.addItemListener(this);
		scrollpane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		scrollpane.add(objList);
		add("Center", scrollpane);

		// Window
		setTitle("Assignment 3: Object Creator");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		});
		setSize(500, 500);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Add":
			new AddObjectMenu();
			objList.add("added");
			break;
		case "Edit...":
			objList.add("edited");
			break;
		case "Delete":
			objList.add("deleted");
			break;
		case "Serialize & Send":
			objList.add("sent");
			break;
		default:
			System.out.println("What the hell did you do");
		}
	}

	@Override
	public void componentAdded(ContainerEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentRemoved(ContainerEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
	}
}
