package parser;

import parser.XmlTree;

public class Test {
	
	public static void main(String[] args) {
		
		String filePath = new String("/Users/bannikun/eclipse-workspace/xmlParser/random.xml");
		String fString = new String("/Users/bannikun/eclipse-workspace/xmlParser/random.xml");
		if(filePath == fString) {
			System.out.println("equal");
		}else {
			System.out.println("nto");
		}
		
//		XmlTree tree = new XmlTree();
//		tree.buildTree(filePath);
//		
//		System.out.println("////////////////////////////");
//				
//		tree.levelOrderTraversal();
		
		
	}

}
