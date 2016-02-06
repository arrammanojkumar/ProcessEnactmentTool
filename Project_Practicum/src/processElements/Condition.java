package processElements;

public class Condition {
	private String name;
	private String description;
	private boolean state;

	public Condition() {

	}

	public Condition(String n, String d, boolean s) {
		name = n;
		description = d;
		state = s;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return description;
	}

	public boolean getState() {
		return state;
	}

	public void replaceName(String n) {
		name = n;
	}

	public void replaceDesc(String d) {
		description = d;
	}

	public void replaceState(boolean s) {
		state = s;
	}

	public String toString() {
		return "Condition: " + name + "\n   Description: " + description + "\n   State: " + state;
	}

	public String toString(String indent) {
		return "Condition:\n" + indent + "    Name: "+ name + 
				"\n" + indent + "    Description: " + description +
				"\n" + indent + "    State: " + state;
	}
}
