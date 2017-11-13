package xml.utils;

import model.XMLElement;

public class XMLUtils {

	public static boolean isOnlyTextElement(XMLElement element) {
		return element.getText() != null && element.getAttributes().isEmpty() && element.getChilds().isEmpty();
	}
	
	public static boolean hasAttributesAndText(XMLElement element) {
		return element.getText() != null && !element.getAttributes().isEmpty();
	}
	
	public static boolean isListElement(XMLElement element) {
		if (element.getChilds().size() < 2 )
			return false;
		else {
			String tag = element.getChilds().get(0).getTag();
			for (int i = 1; i < element.getChilds().size(); i++) {
				if (!tag.equals(element.getChilds().get(i).getTag()))
					return false;
			}
			return true;
		}
			
	}
	
}
