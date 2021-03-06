package ass3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.TextField;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.IdentityHashMap;

import javax.swing.SwingUtilities;

import ass3.collection.ArrListClass;
import ass3.collection.ObjectArrClass;
import ass3.collection.ObjectClass;
import ass3.collection.SimpleArrClass;
import ass3.collection.SimpleClass;
import ass3.serialize.Serializer;
import ass3.sockets.Client;

public class ObjectCreator {
	private Frame main;
	private MainButtonListener mainBtnListener;
	private IdentityHashMap<Integer, Object> doc;
	private Serializer srl;
	private Client srv;

	private ScrollPane scrollpane;
	private List objList;
	private Panel rightPanel, btnPanel, addPanel;

	private Dialog dialog;

	private Integer id;

	public ObjectCreator(IdentityHashMap<Integer, Object> doc, Serializer srl, Client srv) {
		init();
		this.doc = doc;
		this.srl = srl;
		this.srv = srv;
		main.add("East", rightPanel);
		rightPanel.add(addPanel);
		rightPanel.add(btnPanel);

		addButtons();

		scrollpane.add(objList);
		main.add("Center", scrollpane);

		frameConfig();
	}

	private void addButtons() {
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

		Button serializeButton = new Button("Serialize & Send");
		serializeButton.addActionListener(mainBtnListener);
		btnPanel.add(serializeButton);
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
			case "Serialize & Send":
				srl.doSerialize();
				srv.send();
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
			objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
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
			putObjectIntoDoc(tmp);
			objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
			// dialogPanel.reset();
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
				tmp.arr[i] = dialogPanel.getObject().object;
				// dialogPanel.reset();
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
					tmp.arr.add(dialogPanel.getObject().object);
				}
			});
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					putObjectIntoDoc(tmp);
					objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
					putObjectIntoDoc(tmp.arr);
					objList.add(tmp.arr.getClass().getSimpleName() + " " + (id - 1));
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
			} catch (NumberFormatException | NullPointerException e) {
				return false;
			}
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

			btnPanel = new Panel(new GridLayout(1, 6));

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

			Button addExistingButton = new Button("Add Existing");
			addExistingButton.addActionListener(btnListener);
			btnPanel.add(addExistingButton);

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
				case "Add Existing":
					if (objList.getItemCount() > 0) {
						addExistingClass();
						okButtonAction();
					}
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
				// dialogPanel.reset();
			}

			private void addExistingClass() {
				Panel dialogPanel = new Panel(new GridLayout(2, 1));
				ScrollPane scrl = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
				Button okButton = new Button("OK");
				List existingList = new List();
				for (int i = 0; i < objList.getItemCount(); i++) {
					existingList.add(objList.getItem(i));
				}
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println(doc.get(existingList.getSelectedIndex()));
						obj.object = doc.get(existingList.getSelectedIndex());
						Window win = SwingUtilities.getWindowAncestor(dialogPanel);
						win.dispose();
					}
				});

				scrl.add(existingList);
				dialogPanel.add(scrl);
				dialogPanel.add(okButton);

				dialog = new Dialog(main, "Add Existing Object", ModalityType.APPLICATION_MODAL);
				dialog.add(dialogPanel);
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
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
				objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
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
					obj.object = tmp;
					tmp.arr[i] = dialogPanel.getObject().object;
					// dialogPanel.reset();
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
						tmp.arr.add(dialogPanel.getObject().object);
					}
				});
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						obj.object = tmp;
						putObjectIntoDoc(tmp);
						objList.add(tmp.getClass().getSimpleName() + " " + (id - 1));
						putObjectIntoDoc(tmp.arr);
						objList.add(tmp.arr.getClass().getSimpleName() + " " + (id - 1));
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
