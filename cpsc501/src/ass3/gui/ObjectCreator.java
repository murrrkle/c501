package ass3.gui;

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;

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

		Button addObjectArrayButton = new Button("Add Object Array");
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
				addSimpleArray();
				break;
			case "Add Object Array":
				addObjectArrClass();
				break;
			case "Add ArrayList":
				addArrListClass();
				break;
			case "Serialize":
				for (Map.Entry<Integer, Object> o : doc.entrySet()) {
					Integer key = o.getKey();
					System.out.println(key + " " + doc.get(key));
				}
				srl.serialize(doc);
				break;
			default:
				break;
			}
			dialog = null;
		}

		private void addSimpleClass() {
			AddSimpleClassDialogPanel dialogPanel = new AddSimpleClassDialogPanel();
			dialog = new Dialog(main, "Add New SimpleClass Object", ModalityType.APPLICATION_MODAL);
			dialog.add(dialogPanel);
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			SimpleClass tmp = dialogPanel.getObject();
			putObjectIntoDoc(tmp);
			objList.add(tmp.getClass().getSimpleName() + " " + (id - 1) + " " + tmp.value);
		}

		private void addSimpleArray() {
			AddSimpleArrayDialogPanel dialogPanel = new AddSimpleArrayDialogPanel();
			dialog = new Dialog(main, "Add New SimpleArrClass Object", ModalityType.APPLICATION_MODAL);
			dialog.add(dialogPanel);
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			SimpleArrClass tmp = dialogPanel.getObject();
			putObjectIntoDoc(tmp);
			objList.add(tmp.getClass().getSimpleName() + " " + (id - 1) + " " + tmp.arr);
		}

		private void addObjectClass() {
			AddObjectClassDialogPanel dialogPanel = new AddObjectClassDialogPanel();
			dialog = new Dialog(main, "Add New ObjectClass Object", ModalityType.APPLICATION_MODAL);
			dialog.add(dialogPanel);
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			ObjectClass tmp = dialogPanel.getObject();
			putObjectIntoDoc(tmp);
			objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
			dialogPanel.reset();
		}

		private void addObjectArrClass() {
			ObjectArrClass tmp = new ObjectArrClass();
			for (int i = 0; i < 3; i++) {
				AddObjectClassDialogPanel dialogPanel = new AddObjectClassDialogPanel();
				dialog = new Dialog(main, "Add New ObjectClass Object", ModalityType.APPLICATION_MODAL);
				dialog.add(dialogPanel);
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				tmp.arr[i] = dialogPanel.getObject();
				putObjectIntoDoc(tmp.arr[i]);
				objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
				dialogPanel.reset();
			}
			putObjectIntoDoc(tmp);
			objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
		}

		private void addArrListClass() {
			ArrListClass tmp = new ArrListClass();
			Panel dialogPanel = new Panel(new GridLayout(2, 1));
			Button addButton = new Button("Add to ArrayList");
			Button okButton = new Button("OK");
			addButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AddObjectClassDialogPanel dialogPanel = new AddObjectClassDialogPanel();
					dialog = new Dialog(main, "Add New ObjectClass Object", ModalityType.APPLICATION_MODAL);
					dialog.add(dialogPanel);
					dialog.pack();
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
					tmp.arr.add(dialogPanel.getObject());
					putObjectIntoDoc(dialogPanel.getObject());
					objList.add(dialogPanel.getObject().getClass().getSimpleName() + " " + (id - 1));
				}
			});
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					putObjectIntoDoc(tmp);
					objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
					Window win = SwingUtilities.getWindowAncestor(dialogPanel);
					win.dispose();
				}
			});
			dialogPanel.add(addButton);
			dialogPanel.add(okButton);
			dialog = new Dialog(main, "Add New ArrListClass Object", ModalityType.APPLICATION_MODAL);
			dialog.add(dialogPanel);
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		}
	}

	private void putObjectIntoDoc(Object object) {
		doc.put(id, object);
		id++;
	}

	private class AddSimpleClassDialogPanel extends Panel {
		private Label valueLabel;
		private TextField valueField;
		private SimpleClass obj;
		private Button okButton;

		public AddSimpleClassDialogPanel() {
			obj = new SimpleClass();
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
				obj.value = Integer.parseInt(valueField.getText());
				Window win = SwingUtilities.getWindowAncestor(this);
				win.dispose();
			}
		}

		public SimpleClass getObject() {
			return obj;
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

	private class AddSimpleArrayDialogPanel extends Panel {
		Panel textFieldPanel;
		private Label valueLabel1;
		private TextField valueField1;
		private Label valueLabel2;
		private TextField valueField2;
		private Label valueLabel3;
		private TextField valueField3;
		private SimpleArrClass obj;
		private Button okButton;

		public AddSimpleArrayDialogPanel() {
			setLayout(new GridLayout(2, 1));
			textFieldPanel = new Panel(new GridLayout(3, 2));
			obj = new SimpleArrClass();
			okButton = new Button("OK");
			valueLabel1 = new Label("Value 1: ");
			valueField1 = new TextField(10);
			valueLabel2 = new Label("Value 2: ");
			valueField2 = new TextField(10);
			valueLabel3 = new Label("Value 3: ");
			valueField3 = new TextField(10);

			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					okButtonAction();
				}
			});

			textFieldPanel.add(valueLabel1);
			textFieldPanel.add(valueField1);
			textFieldPanel.add(valueLabel2);
			textFieldPanel.add(valueField2);
			textFieldPanel.add(valueLabel3);
			textFieldPanel.add(valueField3);
			add(textFieldPanel);
			add(okButton);
		}

		private void okButtonAction() {
			if (isInteger(valueField1.getText()) && isInteger(valueField2.getText())
					&& isInteger(valueField3.getText())) {
				obj.arr[0] = Integer.parseInt(valueField1.getText());
				obj.arr[1] = Integer.parseInt(valueField2.getText());
				obj.arr[2] = Integer.parseInt(valueField3.getText());
				Window win = SwingUtilities.getWindowAncestor(this);
				win.dispose();
			}
		}

		public SimpleArrClass getObject() {
			return obj;
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
		private Label messageLabel;
		private Panel btnPanel;
		private ObjectClass obj;
		private ButtonListener btnListener;

		public AddObjectClassDialogPanel() {
			obj = new ObjectClass();
			btnListener = new ButtonListener();

			setLayout(new GridLayout(2, 1));
			messageLabel = new Label("Choose an object: ");
			add(messageLabel);

			btnPanel = new Panel(new GridLayout(1, 5));

			Button addSimpleButton = new Button("Add Simple");
			addSimpleButton.addActionListener(btnListener);
			btnPanel.add(addSimpleButton);

			Button addObjectButton = new Button("Add Object");
			addObjectButton.addActionListener(btnListener);
			btnPanel.add(addObjectButton);

			Button addSimpleArrayButton = new Button("Add Simple Array");
			addSimpleArrayButton.addActionListener(btnListener);
			btnPanel.add(addSimpleArrayButton);

			Button addObjectArrayButton = new Button("Add Object Array");
			addObjectArrayButton.addActionListener(btnListener);
			btnPanel.add(addObjectArrayButton);

			Button addArrayListButton = new Button("Add ArrayList");
			addArrayListButton.addActionListener(btnListener);
			btnPanel.add(addArrayListButton);

			add(btnPanel);
		}

		private class ButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "Add Simple":
					addSimpleClass();
					okButtonAction();
					break;
				case "Add Object":
					addObjectClass();
					okButtonAction();
					break;
				case "Add Simple Array":
					addSimpleArrClass();
					okButtonAction();
					break;
				case "Add Object Array":
					addObjectArrClass();
					okButtonAction();
					break;
				case "Add ArrayList":
					addArrListClass();
					okButtonAction();
					break;
				default:
					break;
				}
				dialog = null;
			}

			private void addSimpleClass() {
				AddSimpleClassDialogPanel dialogPanel = new AddSimpleClassDialogPanel();
				dialog = new Dialog(main, "Add New SimpleClass Object", ModalityType.APPLICATION_MODAL);
				dialog.add(dialogPanel);
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				SimpleClass tmp = dialogPanel.getObject();
				obj.object = tmp;
				putObjectIntoDoc(tmp);
				objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
			}

			private void addObjectClass() {
				AddObjectClassDialogPanel dialogPanel = new AddObjectClassDialogPanel();
				dialog = new Dialog(main, "Add New ObjectClass Object", ModalityType.APPLICATION_MODAL);
				dialog.add(dialogPanel);
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				ObjectClass tmp = dialogPanel.getObject();
				obj.object = tmp;
				putObjectIntoDoc(tmp);
				objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
				dialogPanel.reset();
			}

			private void addSimpleArrClass() {
				AddSimpleArrayDialogPanel dialogPanel = new AddSimpleArrayDialogPanel();
				dialog = new Dialog(main, "Add New SimpleArrClass Object", ModalityType.APPLICATION_MODAL);
				dialog.add(dialogPanel);
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				SimpleArrClass tmp = dialogPanel.getObject();
				obj.object = tmp;
				putObjectIntoDoc(tmp);
				objList.add(tmp.getClass().getSimpleName() + " " + (id - 1) + " " + tmp.arr);
			}

			private void addObjectArrClass() {
				ObjectArrClass tmp = new ObjectArrClass();
				for (int i = 0; i < 3; i++) {
					AddObjectClassDialogPanel dialogPanel = new AddObjectClassDialogPanel();
					dialog = new Dialog(main, "Add New ObjectClass Object", ModalityType.APPLICATION_MODAL);
					dialog.add(dialogPanel);
					dialog.pack();
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
					tmp.arr[i] = dialogPanel.getObject();
					putObjectIntoDoc(tmp.arr[i]);
					objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
					dialogPanel.reset();
				}
				putObjectIntoDoc(tmp);
				objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
			}

			private void addArrListClass() {
				ArrListClass tmp = new ArrListClass();
				Panel dialogPanel = new Panel(new GridLayout(2, 1));
				Button addButton = new Button("Add to ArrayList");
				Button okButton = new Button("OK");
				addButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						AddObjectClassDialogPanel dialogPanel = new AddObjectClassDialogPanel();
						dialog = new Dialog(main, "Add New ObjectClass Object", ModalityType.APPLICATION_MODAL);
						dialog.add(dialogPanel);
						dialog.pack();
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
						tmp.arr.add(dialogPanel.getObject());
						putObjectIntoDoc(dialogPanel.getObject());
						objList.add(dialogPanel.getObject().getClass().getSimpleName() + " " + (id - 1));
					}
				});
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						obj.object = tmp;
						putObjectIntoDoc(tmp);
						objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
						Window win = SwingUtilities.getWindowAncestor(dialogPanel);
						win.dispose();
					}
				});
				dialogPanel.add(addButton);
				dialogPanel.add(okButton);
				dialog = new Dialog(main, "Add New ArrListClass Object", ModalityType.APPLICATION_MODAL);
				dialog.add(dialogPanel);
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}
		}

		private void okButtonAction() {
			Window win = SwingUtilities.getWindowAncestor(this);
			win.dispose();
		}

		public ObjectClass getObject() {
			return obj;
		}

		public void reset() {
			obj = null;
		}
	}
}
