package processElements;

public class Defect {

	private String name;
	private String description;

	public Defect() {
		
	}
	
	public Defect(String n, String d) {
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
		return "Defect: " + name + "\n   Description: " + description;
	}
	
	public String toString(String indent) {
		return "Defect:\n" + indent + "    Name: "+ name + "\n" + indent + "    Description: " + description;
	}


}
