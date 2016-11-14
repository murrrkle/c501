package ass3.gui;

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.IdentityHashMap;

import javax.swing.SwingUtilities;

import ass3.collection.*;
import ass3.serialize.Serializer;

public class ObjectCreator {
	private Frame main;
	private MainButtonListener mainBtnListener;

	private ScrollPane scrollpane;
	private List objList;
	private Panel rightPanel, btnPanel, addPanel;

	private Dialog dialog;

	private IdentityHashMap<Integer, Object> doc;
	private Integer id;
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
		
		id = new Integer(0);
		objList = new List();
		scrollpane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		rightPanel = new Panel(new GridLayout(2, 1));
		addPanel = new Panel(new GridLayout(1, 6));
		btnPanel = new Panel(new GridLayout(1, 1));

		doc = new IdentityHashMap<Integer, Object>();
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
				addObjectClass();
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
			AddSimpleClassDialogPanel dialogPanel = new AddSimpleClassDialogPanel();
			dialog = new Dialog(main, "Add New SimpleClass Object", ModalityType.APPLICATION_MODAL);
			dialog.add(dialogPanel);
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			SimpleClass tmp = new SimpleClass();
			tmp.value = Integer.parseInt(dialogPanel.getFieldText());
			putObjectIntoDoc(tmp);
			objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
			dialogPanel.reset();
		}
		
		private void addObjectClass() {
			AddObjectClassDialogPanel dialogPanel = new AddObjectClassDialogPanel();
			dialog = new Dialog(main, "Add New ObjectClass Object", ModalityType.APPLICATION_MODAL);
			dialog.add(dialogPanel);
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			ObjectClass tmp = new ObjectClass();
			tmp.object = Integer.parseInt(dialogPanel.getObject());
			putObjectIntoDoc(tmp);
			objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
			dialogPanel.reset();
		}
	}
	
	private void putObjectIntoDoc(Object object) {
		doc.put(id, object);
		id++;
	}

	private class AddSimpleClassDialogPanel extends Panel {
		private Label valueLabel;
		private TextField valueField;

		private Button okButton;

		public AddSimpleClassDialogPanel() {
			okButton = new Button("OK");
			valueLabel = new Label("Value: ");
			valueField = new TextField(10);

			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					okButtonAction();
				}
			});
			add(valueLabel);
			add(valueField);
			add(okButton);
		}

		private void okButtonAction() {
			if (isInteger(valueField.getText())) {
				Window win = SwingUtilities.getWindowAncestor(this);
				win.dispose();
			}
		}

		public String getFieldText() {
			return valueField.getText();
		}

		public void reset() {
			valueField.setText("");
		}

		public boolean isInteger(String s) {
			try {
				Integer.parseInt(s);
			} catch (NumberFormatException e) {
				return false;
			} catch (NullPointerException e) {
				return false;
			}
			// only got here if we didn't return false
			return true;
		}
	}
	
	private class AddObjectClassDialogPanel extends Panel {
		private Label valueLabel;
		private TextField valueField;

		private Button okButton;

		public AddObjectClassDialogPanel() {
			okButton = new Button("OK");
			valueLabel = new Label("Value: ");
			valueField = new TextField(10);

			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					okButtonAction();
				}
			});
			add(valueLabel);
			add(valueField);
			add(okButton);
		}

		private void okButtonAction() {
			if (isInteger(valueField.getText())) {
				Window win = SwingUtilities.getWindowAncestor(this);
				win.dispose();
			}
		}

		public String getObject() {
			return valueField.getText();
		}

		public void reset() {
			valueField.setText("");
		}

		public boolean isInteger(String s) {
			try {
				Integer.parseInt(s);
			} catch (NumberFormatException e) {
				return false;
			} catch (NullPointerException e) {
				return false;
			}
			// only got here if we didn't return false
			return true;
		}
	}

}
