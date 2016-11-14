package ass3.objectCreatorGUI.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import ass3.serialize.Serializer;

public class MainMenu {
	private Frame main;
	private MainButtonListener mainBtnListener = new MainButtonListener();
	private MainItemListener mainItmListener = new MainItemListener();
	
	private ScrollPane scrollpane;
	private List objList;
	private Panel btnPanel;
	private ArrayList<Object> doc;
	private Serializer srl;

	public MainMenu() {
		main = new Frame();
		main.setLayout(new BorderLayout());
		
		doc = new ArrayList<Object>();
		srl = new Serializer();

		// Button Panel
		btnPanel = new Panel(new GridLayout(0, 1));
		main.add("East", btnPanel);

		Button addButton = new Button("Add");
		addButton.addActionListener(mainBtnListener);
		btnPanel.add(addButton);

		Button editButton = new Button("Edit...");
		editButton.addActionListener(mainBtnListener);
		btnPanel.add(editButton);

		Button deleteButton = new Button("Delete");
		deleteButton.addActionListener(mainBtnListener);
		btnPanel.add(deleteButton);

		Button sendButton = new Button("Serialize");
		sendButton.addActionListener(mainBtnListener);
		btnPanel.add(sendButton);

		// Scrollpane
		objList = new List();
		objList.addItemListener(mainItmListener);
		scrollpane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		scrollpane.add(objList);
		main.add("Center", scrollpane);

		// Window
		main.setTitle("Assignment 3: Object Creator");
		main.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				main.dispose();
			}
		});
		main.setSize(500, 500);
		main.setVisible(true);
	}
	
	private class MainButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Add":
//				new NewObjectMenu();
				objList.add("added");
				break;
			case "Edit...":
				objList.add("edited");
				break;
			case "Delete":
				objList.add("deleted");
				break;
			case "Serialize":
				srl.serialize(doc);
				break;
			default:
				System.out.println("What did you do? You're not supposed to be able to see this. Report the bug right away!");
			}
		}
	}
	
	private class MainItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
