package parser;

public class Token {
	
//	TODO handle validation
	
	public String value;
	public String type;
	public Token(String value, String type) {
		this.type = type;
		this.value = value;
	}
	
}
