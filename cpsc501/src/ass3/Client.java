package ass3;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		try {
			String serverName = "localhost";
			int port = 6666;
			Socket socket = new Socket(serverName, port);
			// DataOutputStream outPutStream = new
			// DataOutputStream(socket.getOutputStream());
			// outPutStream.writeUTF("Hello Server");
			// outPutStream.flush();
			// outPutStream.close();

			int count;
			String inputFile = "C:\\XMLData\\file.xml";
			File myFile = new File(inputFile);
			byte[] buffer = new byte[(int) myFile.length()];
			OutputStream out = socket.getOutputStream();
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(myFile));
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
				out.flush();
			}

			socket.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
