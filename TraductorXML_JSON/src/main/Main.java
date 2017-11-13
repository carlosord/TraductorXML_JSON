package main;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import json.writer.XMLWriter;
import model.XMLElement;
import xml.analizer.XMLAnalizer;
import xml.reader.XMLReader;

public class Main {

	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println(
					"Llamada incorrecta, método de uso: java -jar TraductorXML_JSON [fichero].xml [fichero].json");
		} else {

			try {
				XMLElement root = XMLReader.read(new File(args[0]));
				root = XMLAnalizer.analize(root);
				XMLWriter.write(root, args[1]);
			} catch (ParserConfigurationException | SAXException e) {
				// Esta exception ya imprime el mensaje y donde se encuentra el fallo
				// System.err.println(e.getMessage());
			} catch (IOException e1) {
				System.err.println(
						"Ha ocurrido un error al leer el fichero (no existe, el nombre está mal escrito, etc).");
			}
			
		}

	}

}
