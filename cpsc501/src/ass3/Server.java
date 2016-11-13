package ass3;

import java.awt.im.InputContext;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.InflaterInputStream;

public class Server {
	public static void main(String[] args) {
		try {
			int portNumber = 6666;
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket socket = serverSocket.accept();
			// DataInputStream incomingData = new
			// DataInputStream(socket.getInputStream());
			// String receivedMessage = (String) incomingData.readUTF();
			// System.out.println("message: " + receivedMessage);

			String outputFile = "C:\\XMLData\\outfile.xml";
			File myFile = new File(outputFile);
			FileOutputStream fos = new FileOutputStream(myFile);
			BufferedOutputStream out = new BufferedOutputStream(fos);
			byte[] buffer = new byte[1024];
			int count;
			InputStream in = socket.getInputStream();
			while ((count = in.read(buffer)) > 0) {
				byte revisedBuffer[] = new String(buffer).replaceAll("\0", "").getBytes();
				fos.write(revisedBuffer);
			}
			fos.close();

			serverSocket.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
