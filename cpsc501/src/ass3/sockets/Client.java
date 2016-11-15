package ass3.sockets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	public void send() {
		try {
			String serverName = "localhost";
			int port = 27015;
			Socket socket = new Socket(serverName, port);

			int count;
			String inputFile = "C:" + File.separator + "XMLData" + File.separator + "file.xml";
			File myFile = new File(inputFile);
			byte[] buffer = new byte[(int) myFile.length()];
			OutputStream out = socket.getOutputStream();
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(myFile));
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
				out.flush();
			}
			in.close();
			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
