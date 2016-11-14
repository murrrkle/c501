package ass3.objectCreatorGUI.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import ass3.serialize.Serializer;

public class AddObjectMenu extends Frame implements ActionListener, ContainerListener, ItemListener {
	private Panel btnPanel;
	private ScrollPane scrollpane;
	private List fldList;

	public AddObjectMenu() {
		setLayout(new BorderLayout());

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
		fldList = new List();
		fldList.addItemListener(this);
		scrollpane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		scrollpane.add(fldList);
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
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentAdded(ContainerEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentRemoved(ContainerEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
