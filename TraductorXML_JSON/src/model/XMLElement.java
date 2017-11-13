package model;

import java.util.ArrayList;
import java.util.List;

public class XMLElement {
	
	private String tag;
	private String text;
	private List<XMLAttribute> attributes;
	private List<XMLElement> childs;
	
	public XMLElement(String tag) {
		this.tag = tag;
		this.attributes = new ArrayList<>();
		this.childs = new ArrayList<>();
	}
	
	public String getTag() {
		return tag;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<XMLAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<XMLAttribute> attributes) {
		this.attributes = attributes;
	}

	public List<XMLElement> getChilds() {
		return childs;
	}

	public void setChilds(List<XMLElement> childs) {
		this.childs = childs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((childs == null) ? 0 : childs.hashCode());
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XMLElement other = (XMLElement) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (childs == null) {
			if (other.childs != null)
				return false;
		} else if (!childs.equals(other.childs))
			return false;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "XMLElement [tag=" + tag + ", text=" + text + ", attributes=" + attributes + ", childs=" + childs + "]";
	}	

}
