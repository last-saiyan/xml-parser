package parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



class openingTag{
	
	public openingTag(String input) {
		
	}
	
}
public class Parser {
	private static final String opening = "<";
	private static final String prolog = "<?";
	private static final String comment = "<!--";
	private static final String equals = "=";
	private static final String singleQuote = "'";
	private static final String doubleQuote = "\"";
	private static final String ending = ">";
	private boolean lookForAttribute = false;
	private boolean lookForText = false;
	
	private String xmlPath;
	private int iterator = 0;
	private String xmlFile = "";
	
	
	public Parser(String xmlPath) {
		this.xmlPath = xmlPath;
		readXml(this.xmlPath);
	}
	
	private void readXml(String filename) {
        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);
            sc.useDelimiter("\\Z");
            this.xmlFile = sc.next();
            sc.close();
        } catch (FileNotFoundException e) {
            // throw e;
            //TODO: handle exception
            this.xmlFile = "";
        }
        
    }
	
	
	public Token getNextToken() {
		if(this.iterator != this.xmlFile.length()) {
			Token tok = parse();
			if(tok!=null) {
				System.out.println(tok.value + " - " + tok.type);
			}else {
				System.out.println("null");
			}
			return tok;
		}else {
			return null;
		}
	}
	

	private Token generateToken(String type, String[] delim) {
		String value = "";
		int min = -1;
		for(int i=0;i<delim.length; i++) {
			int ind = getSubstringIndex(delim[i]);
			if(ind != -1 && (min == -1 || ind < min)) { 
				min = ind;
			}
		}
		
		if(min != -1) {
			value = this.xmlFile.substring(this.iterator, min);
			value = value.trim();
			this.iterator = min ;			
			if(value != "") {
				return new Token(value, type);
			}else {
				return null;
			}
		}else {
//			TODO throw err 
			return null;
		}
	}
	
	public int getSubstringIndex(String pattern){
		int xIndex = this.iterator;
		String xmlFile = this.xmlFile;
		boolean found = false;
		while(true) {
			if(xIndex >= xmlFile.length())
				break;
			int pIndex = 0;
			while(true) {
				if(pIndex == pattern.length()) {
					found = true;
				}
				if(pIndex >= pattern.length() || xIndex >= xmlFile.length()) 
					break;
				if(xmlFile.charAt(xIndex) == pattern.charAt(pIndex)) {
					xIndex++;
					pIndex++;
				}else {
					break;
				}
			}
			if(pIndex == pattern.length()) {
				break;
			}else {
				xIndex++;
			}
		}
		if(!found) {
			return -1;
		}else {
			return (xIndex - pattern.length());
		}
	}
	
	private Token parse() {
		
		while( this.iterator < this.xmlFile.length() ) {
			char curr = this.xmlFile.charAt(this.iterator);

			if(getSubstringIndex(prolog) == this.iterator) {
				this.iterator = this.iterator + prolog.length();
//				prolog tag token
				String [] delim = {"?>"};
				Token token = generateToken("", delim);
				System.out.println(token.value);
			}
			if(getSubstringIndex(comment) == this.iterator) {
//				comment comment token
				String [] delim = {"-->"};
				Token token = generateToken("comment", delim);
				System.out.println(token.value);
			}
			if(getSubstringIndex("</") == this.iterator) {
//				closing close token
				this.iterator = this.iterator + "</".length();
				String [] delim = {">"};
				Token token = generateToken("close", delim);
				this.lookForAttribute = false;
				this.lookForText = false;
				return token;
			}
			if(getSubstringIndex(opening) == this.iterator) {
				this.iterator = this.iterator + opening.length();
				String [] delim = {" ", ">"};
				Token token = generateToken("open", delim);
				this.lookForAttribute = true;
				this.lookForText = false;
				return token;
			}
			if(getSubstringIndex(equals) == this.iterator) {
				this.iterator = this.iterator + equals.length();
				String [] delim = { ">", "\" " };
				Token token = generateToken("attributeValue", delim);
				return token;
			}
			if(getSubstringIndex(singleQuote) == this.iterator) {
				this.iterator = this.iterator + singleQuote.length();
				String [] delim = { " ", ">" };
				Token token = generateToken("attributeValue", delim);
				return token;
			}
			if(getSubstringIndex(doubleQuote) == this.iterator) {
				System.out.println(curr+" curr");
				this.iterator = this.iterator + doubleQuote.length();
				String [] delim = { "\"" };
				Token token = generateToken("attributeValue", delim);
				return token;
			}
			if(getSubstringIndex(ending) == this.iterator) {
				this.lookForAttribute = false;
				this.lookForText = true;
				this.iterator++;
				continue;
			}
			
			if(curr == ' ' || (int)curr == 9 || (int)curr == 10) {
//				System.out.println("Escaped...");
				this.iterator++;
				continue;
			}else if (lookForAttribute) {
				String [] delim = {"="};
				Token token = generateToken("attributeName", delim);
				return token;
			}else if (lookForText && curr != '<' && curr != ' ' && curr != '\t' && curr != '\n') {
				String [] delim = {"<"};
				Token token = generateToken("text", delim);
				return token;
			}else {
				System.out.println("something wrong - " + this.xmlFile.charAt(this.iterator) + " - " + this.iterator);
			}
		}
		return null;
	}
	
//	public static void main(String[] args) {
//		Parser parser = new Parser("/Users/bannikun/eclipse-workspace/xmlParser/random.xml");
//		while(parser.getNextToken() != null) {
//			System.out.println("-----");
//		}
//	}
}
