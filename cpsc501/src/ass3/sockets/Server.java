package ass3.sockets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public void getXMLFile() {
		try {
			int portNumber = 27015;
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket socket = serverSocket.accept();

			String outputFile = "C:" + File.separator + "XMLData" + File.separator + "outfile.xml";
			File myFile = new File(outputFile);
			if (!myFile.exists()) {
				myFile.getParentFile().mkdirs();
				myFile.createNewFile();
			}

			FileOutputStream fos = new FileOutputStream(myFile);
			byte[] buffer = new byte[4096];
			InputStream in = socket.getInputStream();
			while ((in.read(buffer)) > 0) {
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
