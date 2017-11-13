package xml.analizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.XMLElement;
import xml.utils.XMLUtils;

public class XMLAnalizer {
	
	public static XMLElement analize(XMLElement root) {
		return analizeElement(root);
	}
	
	private static XMLElement analizeElement(XMLElement element) {
		
		for (XMLElement e: element.getChilds()) {
			analizeElement(e);
		}
		
		if (!XMLUtils.isListElement(element)) {
		
			Set<String> groups = new HashSet<>();
			List<XMLElement> childs = element.getChilds();
			
			// Comprobamos que elementos están bajo la misma etiqueta
			for (int i = 0; i < childs.size(); i++) {
				for (int j = 0; j < childs.size(); j++) {
					if (i != j) {
						if (childs.get(i).getTag().equals(childs.get(j).getTag())) {
							groups.add(childs.get(i).getTag());
						}						
					}
				}
			}
			
			if (!groups.isEmpty()) {
			
				Map<String, List<XMLElement>> elementsRepeated = new HashMap<>();
				
				// Creamos grupos de elementos con la misma etiqueta
				for (String tag: groups) {
					List<XMLElement> list = new ArrayList<>();
					for (XMLElement e: childs) {
						if (tag.equals(e.getTag())) {
							list.add(e);
						}
					}
					elementsRepeated.put(tag, list);
				}
				
				Map<String, Boolean> groupsInserted = new HashMap<>();
				for (String g: groups)
					groupsInserted.put(g, false);
				
				// Eliminamos los elementos con la etiqueta indicada y los sustituimos por un contenedor
				List<XMLElement> aux = new ArrayList<>();
				
				for (XMLElement e: childs) {
					if (isElementToRemove(e.getTag(), groups)) {
						if (!groupsInserted.get(e.getTag())) {
							XMLElement elem = new XMLElement(e.getTag());
							elem.getChilds().addAll(elementsRepeated.get(e.getTag()));
							aux.add(elem);
							groupsInserted.put(e.getTag(), true);
						}
					} else
						aux.add(e);
				}
				
				element.setChilds(aux);
			
			}
			
		}
		
		return element;
		
	}
	
	private static boolean isElementToRemove(String tag, Set<String> groups) {
		for (String g: groups)
			if (tag.equals(g))
				return true;
		return false;
	}

}
