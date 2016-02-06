package processElements;

public class Task {
	
	private String name;
	private String description;

	private ArtifactListController artifactsUsed = new ArtifactListController ();
	private ArtifactListController artifactsProduced = new ArtifactListController ();
	
	public Task() {
		
	}
	
	public Task(String tn, String td, ArtifactListController au, ArtifactListController ad) {
		name = tn;
		description = td;
		artifactsUsed = au;
		artifactsProduced = ad;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return description;
	}
	
	public ArtifactListController getArtifactsUsed() {
		return artifactsUsed;
	}
	
	public ArtifactListController getArtifactsProduced() {
		return artifactsProduced;
	}
	
	public ArtifactListController getArtifactsUsedListCopy() {
		ArtifactListController result = new ArtifactListController(artifactsUsed);
		return result;
	}
	
	public ArtifactListController getArtifactsProducedListCopy() {
		ArtifactListController result = new ArtifactListController(artifactsProduced);
		return result;
	}

	
	public String toString() {
		String result = "Task: " + name + "\n   Description: " + description;
		int artifactSequenceNumber = 1;
		result += "\n   Artifacts Used:";
		for (Artifact a: artifactsUsed.getArtifacts())
			result += "\n     " + artifactSequenceNumber++ + ": " + a.toString();
		artifactSequenceNumber = 1;
		result += "\n   Artifacts Produced:";
		for (Artifact a: artifactsProduced.getArtifacts())
			result += "\n     " + artifactSequenceNumber++ + ": " + a.toString();
		return result;
	}
	
	public String toString(String indent) {
		String result = "Task: " + name + "\n" + indent + "   Description: " + description;
		int artifactSequenceNumber = 1;
		result += indent + "\n" + indent + "   Artifacts Used:";
		for (Artifact a: artifactsUsed.getArtifacts())
			result += indent + "\n" + indent + "      " + artifactSequenceNumber++ + ": " + a.toString(indent + "      ");
		artifactSequenceNumber = 1;
		result += indent + "\n" + indent + "   Artifacts Produced:";
		for (Artifact a: artifactsProduced.getArtifacts())
			result += indent + "\n" + indent + "      " + artifactSequenceNumber++ + ": " + a.toString(indent + "      ");
		return result;
	}
}
