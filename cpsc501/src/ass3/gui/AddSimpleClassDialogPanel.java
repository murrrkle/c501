package ass3.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.SwingUtilities;

public class AddSimpleClassDialogPanel extends Panel {
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

	public static boolean isInteger(String s) {
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
