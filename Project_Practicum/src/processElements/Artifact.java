package processElements;

public class Artifact {

	private String name;
	private String description;

	public Artifact() {
		
	}
	
	public Artifact(String n, String d) {
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
		return "Artifact: " + name + "\n   Description: " + description;
	}
	
	public String toString(String indent) {
		return "Artifact:\n" + indent + "    Name: "+ name + "\n" + indent + "    Description: " + description;
	}


}
