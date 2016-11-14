package ass3.gui;

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.*;
import java.util.ArrayList;

import ass3.collection.*;
import ass3.serialize.Serializer;

public class ObjectCreator {
	private Frame main;
	private MainButtonListener mainBtnListener;

	private ScrollPane scrollpane;
	private List objList;
	private Panel rightPanel, btnPanel, addPanel;
	private AddSimpleClassDialogPanel dialogPanel;

	private Dialog dialog;

	private ArrayList<Object> doc;
	private Serializer srl;

	public ObjectCreator() {
		init();
		main.add("East", rightPanel);
		rightPanel.add(addPanel);
		rightPanel.add(btnPanel);

		// Button Panel
		Button addSimpleButton = new Button("Add Simple");
		addSimpleButton.addActionListener(mainBtnListener);
		addPanel.add(addSimpleButton);

		Button addObjectButton = new Button("Add Object");
		addObjectButton.addActionListener(mainBtnListener);
		addPanel.add(addObjectButton);
		
		Button addSimpleArrayButton = new Button("Add Simple Array");
		addSimpleArrayButton.addActionListener(mainBtnListener);
		addPanel.add(addSimpleArrayButton);
		
		Button addObjectArrayButton = new Button("Add Obejct Array");
		addObjectArrayButton.addActionListener(mainBtnListener);
		addPanel.add(addObjectArrayButton);
		
		Button addArrayListButton = new Button("Add ArrayList");
		addArrayListButton.addActionListener(mainBtnListener);
		addPanel.add(addArrayListButton);
		
		Button sendButton = new Button("Serialize");
		sendButton.addActionListener(mainBtnListener);
		btnPanel.add(sendButton);

		// Scrollpane
		scrollpane.add(objList);
		main.add("Center", scrollpane);

		// Window
		frameConfig();
	}

	private void init() {
		main = new Frame();
		mainBtnListener = new MainButtonListener();

		objList = new List();
		scrollpane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		rightPanel = new Panel(new GridLayout(2, 1));
		addPanel = new Panel(new GridLayout(1, 6));
		btnPanel = new Panel(new GridLayout(1, 1));

		doc = new ArrayList<Object>();
		srl = new Serializer();
		main.setLayout(new BorderLayout());
	}

	private void frameConfig() {
		main.setTitle("Assignment 3: Object Creator");
		main.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				main.dispose();
			}
		});
		main.setSize(800, 500);
		main.setVisible(true);
	}

	private class MainButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Add Simple":
				addSimpleClass();
				break;
			case "Add Object":
				addSimpleClass();
				break;
			case "Add Simple Array":
				addSimpleClass();
				break;
			case "Add Object Array":
				addSimpleClass();
				break;
			case "Add ArrayList":
				addSimpleClass();
				break;
			case "Serialize":
				srl.serialize(doc);
				break;
			default:
				break;
			}
		}

		private void addSimpleClass() {
			dialogPanel = new AddSimpleClassDialogPanel();
			dialog = new Dialog(main, "Add New SimpleClass Object", ModalityType.APPLICATION_MODAL);
			dialog.add(dialogPanel);
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			SimpleClass tmp = new SimpleClass();
			tmp.value = Integer.parseInt(dialogPanel.getFieldText());
			doc.add(tmp);
			objList.add(tmp.getClass().getSimpleName() + " " + Integer.toString(tmp.hashCode()));
			dialogPanel.reset();
		}
	}

}
