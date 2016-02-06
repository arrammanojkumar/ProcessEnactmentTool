package processElements;

public class Interruption {

	private String name;
	private String description;

	public Interruption() {
		
	}
	
	public Interruption(String n, String d) {
		name = n;
		description = d;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return description;
	}
	
	public void replaceName(String n) {
		name = n;
	}
	
	public void replaceDesc(String d) {
		description = d;
	}
	
	public String toString() {
		return "Interruption: " + name + "\n   Description: " + description;
	}
	
	public String toString(String indent) {
		return "Interruption:\n" + indent + "    Name: "+ name + "\n" + indent + "    Description: " + description;
	}


}
