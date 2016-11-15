package ass3;

import java.util.IdentityHashMap;
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