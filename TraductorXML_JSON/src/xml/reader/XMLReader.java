package xml.reader;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.XMLAttribute;
import model.XMLElement;

public class XMLReader {

	private static XMLElement root;
	private static XMLElement actual;

	public static XMLElement read(File file) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		Document doc = dBuilder.parse(file);

		root = new XMLElement("root");
		actual = root;

		if (doc.hasChildNodes()) {
			analizeNodes(doc.getChildNodes());
		}

		return root;
	}

	private static void analizeNodes(NodeList nodeList) {

		for (int i = 0; i < nodeList.getLength(); i++) {

			Node tempNode = nodeList.item(i);
			XMLElement tempElement = new XMLElement(tempNode.getNodeName());

			if (tempNode.getNodeType() == Node.TEXT_NODE && !tempNode.getNodeValue().trim().equals("")) {
				// Eliminamos caracteres como \t, \n, \r y espacios inecesarios
			    StringTokenizer tokens = new StringTokenizer(tempNode.getNodeValue().trim().replaceAll("\\s"," "));
		        StringBuilder sb = new StringBuilder();
		        while (tokens.hasMoreTokens()) {
		            sb.append(" ").append(tokens.nextToken());
		        }
				actual.setText(sb.toString());
			}

			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				// Añadimos el elemento al actual
				actual.getChilds().add(tempElement);

				if (tempNode.hasAttributes()) {
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int j = 0; j < nodeMap.getLength(); j++) {
						Node node = nodeMap.item(j);
						tempElement.getAttributes().add(new XMLAttribute(node.getNodeName(), node.getNodeValue()));
					}
				}

				if (tempNode.hasChildNodes()) {
					// Guardamos una referencia del nodo 'actual'
					XMLElement aux = actual;
					// Actual pasa a ser el elemento analizado
					actual = tempElement;
					analizeNodes(tempNode.getChildNodes());
					// Actual vuelve a ser el que era antes de analizar los hijos
					actual = aux;
				}
				
			}
		}		
	}

}
