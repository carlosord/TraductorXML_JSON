package json.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import model.XMLElement;

public class XMLWriter {
	
	private static PrintWriter pr;
	
	public static void write(XMLElement root, String name) throws IOException {
		
		pr = new PrintWriter(new FileWriter(name.endsWith(".json") ? name : name + ".json"));		
		print(root.getChilds().get(0));
		pr.close();
		
	}
	
	public static void print(XMLElement element) {
		String json = XMLWriterUtils.writeElement(element, "", false);
		pr.write(XMLWriterUtils.repareJson(json));
	}
	
}
