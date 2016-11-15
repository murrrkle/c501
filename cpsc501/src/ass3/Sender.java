package ass3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.IdentityHashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

import ass3.collection.ArrListClass;
import ass3.collection.ObjectArrClass;
import ass3.collection.ObjectClass;
import ass3.collection.SimpleArrClass;
import ass3.collection.SimpleClass;
import ass3.serialize.Serializer;
import ass3.sockets.Client;

public class Sender {

	public static void main(String[] args) {
		IdentityHashMap<Integer, Object> doc = new IdentityHashMap<Integer, Object>();
		Serializer srl = new Serializer(doc);
		Client srv = new Client();
		ObjectCreator app = new ObjectCreator(doc, srl, srv);
	}
}