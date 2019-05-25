package parser;

import java.util.Stack;

import parser.Node;
import parser.Parser;

public class XmlTree {
	
	public Node root;
	private Stack<Node> nodeStack = new Stack<Node>();
	
	public XmlTree() {
		this.root = null;
	}
	
	public void buildTree(String filePath) {
		Parser parser = new Parser(filePath);
		String attributeNameString = "";
		boolean lookForAttributevalue = false;
		while(true) {
			Token token = parser.getNextToken();
			if( token == null)
				break;
			if(token.type == "attributeValue") {
				if(lookForAttributevalue) {
					Node curr = nodeStack.peek();
					if(curr == null) {
//						TODO throw err;
					}else {
						lookForAttributevalue = false;
						curr.addAttribute(attributeNameString, token.value);
					}
				}else {
//					TODO throw err;
				}
			}else if(lookForAttributevalue) {
//				TODO throw err;
			}else if(token.type == "open") {
				if(root == null) {
					root = new Node(token.value);
					nodeStack.add(root);
				}else {
					Node currNode = nodeStack.peek();
					if(currNode == null) {
						System.out.println("error");
//						TODO throw err
					}else {
						Node tempNode = new Node(token.value);
						currNode.addChildNode(tempNode);
						nodeStack.add(new Node(token.value));
					}
				}
			}else if(token.type == "attributeName") {
				lookForAttributevalue = true;
				attributeNameString = token.value;
			}else if(token.type == "close"){
				Node currNode = nodeStack.peek();
				if(currNode == null) {
					System.out.println("erro r");
//					TODO throw err
				}else if(!currNode.name.equals(token.value)){
					System.out.println( currNode.name + " -error - " +token.value);
//					TODO throw err
				}else {
					nodeStack.pop();
				}
			}
		}
		
		if(nodeStack.size() != 0) {
//			TODO throw err
		}
	}
	
	
	public void levelOrderTraversal() {
		
		Node tempNode = root;
		System.out.println(tempNode.name);
		System.out.println(tempNode.child.get(0).child);
//		levelOrderTraversal(tempNode);
//		while(tempNode != null) {
//			System.out.println(tempNode.name);
//			for(int i=0; i<tempNode.child.size(); i++) {
//				
//			}
//		}
	}

	public void levelOrderTraversal(Node node) {
		if(node == null)
			return;
		System.out.print(node.name + "=");
		if(node.child == null) 
			return;
		
		for(int i=0; i<node.child.size(); i++) {
			Node tempNode = node.child.get(i);
			levelOrderTraversal(tempNode);
		}
		System.out.println("------");
	}
	
}
