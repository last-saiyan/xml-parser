package parser;

import java.util.ArrayList;
import parser.Attribute;

public class Node {
	public String name;
	public ArrayList<Node> child;
	public ArrayList<Attribute> attribute;
	public ArrayList<String> textString;
	
	public Node (String name) {
		this.name = name;
	}
	void addChildNode(Node child) {
		if(this.child == null) {
			System.out.println("------adding child node----");
			this.child = new ArrayList<Node>();
			this.child.add(child);
		}else {
			System.out.println("-=====-adding child node-=====-");
			this.child.add(child);
		}
	}
	void addText(String text) {
		if(this.textString == null) {
//			System.out.println("------adding child node----");
			this.textString = new ArrayList<String>();
			this.textString.add(text);
		}else {
			this.textString.add(text);
		}
	}
	void addAttribute(String name, String value) {
		if(this.attribute == null) {
			this.attribute = new ArrayList<Attribute>();
			this.attribute.add(new Attribute(name, value));
		}else {
			this.attribute.add(new Attribute(name, value));
		}
	}

}
