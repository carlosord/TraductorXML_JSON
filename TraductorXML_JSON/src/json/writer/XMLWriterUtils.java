package json.writer;

import model.XMLAttribute;
import model.XMLElement;
import xml.utils.XMLUtils;

public class XMLWriterUtils {
	
	public static String writeElement(XMLElement element, String before, boolean list) {
		StringBuilder sb = new StringBuilder();
		
		if (XMLUtils.isOnlyTextElement(element)) {
			sb.append(writeTextElementOnly(element, before));
		} else if (XMLUtils.hasAttributesAndText(element)) {
			sb.append(writeAttributesAndTextElement(element, before));
		} else if (XMLUtils.isListElement(element)) {
			sb.append(writeListElement(element, before, list));
		} else {
			sb.append(writeAttributesAndChildsElement(element, before, list));
		}
		
		return sb.toString();
		
	}
	
	private static String writeTextElementOnly(XMLElement element, String before) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(element.getTag());
		sb.append("\"");
		sb.append(": ");
		sb.append("\"");
		sb.append(element.getText().trim());
		sb.append("\"");
		return sb.toString();
	}
	
	private static String writeAttributesAndTextElement(XMLElement element, String before) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(element.getTag());
		sb.append("\"");
		sb.append(": {\n");
		for (XMLAttribute a: element.getAttributes()) {
			sb.append("\t" + before + writeAttribute(a, before + "\t") + ",\n");
		}
		sb.append("\t");
		sb.append(before);
		sb.append("\"");
		sb.append("#text");
		sb.append("\"");
		sb.append(": ");
		sb.append("\"");
		sb.append(element.getText().trim());
		sb.append("\"\n");
		sb.append(before);
		sb.append("}");
		return sb.toString();
	}
	
	private static String writeAttribute(XMLAttribute attribute, String before) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append("@" + attribute.getName());
		sb.append("\"");
		sb.append(": ");
		sb.append("\"");
		sb.append(attribute.getValue());
		sb.append("\"");
		return sb.toString();
	}
	
	private static String writeAttributesAndChildsElement(XMLElement element, String before, boolean list) {
		StringBuilder sb = new StringBuilder();
		if (list) {
			sb.append("{\n");
		} else {
			sb.append("\"");
			sb.append(element.getTag());
			sb.append("\"");
			sb.append(": {\n");
		}
		for (XMLAttribute a: element.getAttributes()) {
			sb.append("\t" + before + writeAttribute(a, "\t") + ",\n");
		}
		for (XMLElement e: element.getChilds()) {
			sb.append("\t" + before + writeElement(e, before + "\t", false));
			if (!e.equals(element.getChilds().get(element.getChilds().size() - 1)))
				sb.append(",\n");
			else
				sb.append("\n");
		}
		sb.append(before);
		sb.append("}");
		return sb.toString();
	}
	
	private static String writeListElement(XMLElement element, String before, boolean list) {
		StringBuilder sb = new StringBuilder();
		if (!list) {
			sb.append("\"");
			sb.append(element.getTag());
			sb.append("\"");
			if (!element.getAttributes().isEmpty()) {
				sb.append(": {\n");
				for (XMLAttribute a: element.getAttributes()) {
					sb.append("\t" + before + writeAttribute(a, "\t\t") + ",\n");
				}
				sb.append("\t\"" + element.getChilds().get(0).getTag() + "\": [\n");
			} else {
				sb.append(": [\n");
			}
		} else {
			sb.append("\t{\n");
			sb.append(before);
			sb.append("\t\"" + element.getChilds().get(0).getTag() + "\": [\n");
		}
		for (XMLElement e: element.getChilds()) {
			String tab = "";
			if (!element.getAttributes().isEmpty()) 
				tab = "\t\t";
			else
				tab = "\t";
			if (XMLUtils.isListElement(e))
				sb.append("\t" + before + writeListElement(e, before + tab, true));
			else
				sb.append("\t" + before + writeAttributesAndChildsElement(e, before + tab, true));
			if (!e.equals(element.getChilds().get(element.getChilds().size() - 1)))
				sb.append(",\n");
			else
				sb.append("\n");
		}
		if (!element.getAttributes().isEmpty()) {
			sb.append(before);
			sb.append("\t]\n");
			sb.append(before);
			sb.append("}");
		} else {
			sb.append(before);
			sb.append("]");
		}
		return sb.toString();
	}

	public static String repareJson(String json) {
		return json.substring(json.indexOf("{"));
	}
	
}
