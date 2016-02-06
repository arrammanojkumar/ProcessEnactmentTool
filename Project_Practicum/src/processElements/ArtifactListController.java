package processElements;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class ArtifactListController {
	private ArtifactListController root = null;
	private List <Artifact> artifactList = new ArrayList <> ();
	private int numberOfArtifacts = 0;
	
	public ArtifactListController() {
	}
	
	public ArtifactListController(ArtifactListController that) {
		root = that.root;
		if (root == null) root = that;
		numberOfArtifacts = that.numberOfArtifacts;
		artifactList = that.getArtifactsListCopy();
	}
	
	public int getNumberOfArtifacts(){
		return artifactList.size();
	}
	
	public ArtifactListController getRoot() {
		return root;
	}
	
	public boolean isEmpty(){
		return numberOfArtifacts == 0;
	}
	
	public boolean isNotEmpty(){
		return numberOfArtifacts > 0;
	}
	
	public Artifact getArtifact(int ndx){
		return artifactList.get(ndx);
	}
	
	public Artifact extractArtifact(int ndx){
		Artifact result = artifactList.get(ndx);
		artifactList.remove(ndx);
		return result;
	}
 
	public List <Artifact> getArtifacts(){
		return artifactList;
	}
 	
	public List <Artifact> getArtifactsListCopy(){
		List <Artifact> copy = new ArrayList <> ();
		for (int ndx = 0; ndx < artifactList.size(); ndx++)
			copy.add(artifactList.get(ndx));
		return copy;
	}
	
	public boolean isTheSameAs(ArtifactListController that) {
		if (this.numberOfArtifacts != that.numberOfArtifacts) return false;
		for (int ndx= 0; ndx < this.numberOfArtifacts; ndx++)
			if (this.artifactList.get(ndx) != that.artifactList.get(ndx)) return false;
		return true;
	}
	
	public String [] getList(){
		String [] result = new String[numberOfArtifacts];
		for (int ndx=0; ndx < numberOfArtifacts; ndx++)
			result[ndx] = artifactList.get(ndx).getName();
		return result;
	}
	
	public String [] buildSelectList(){
		String [] result = new String[numberOfArtifacts];
		for (int ndx=0; ndx < numberOfArtifacts; ndx++)
			result[ndx] = ((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + artifactList.get(ndx).getName();
		return result;
	}
	
	public ArtifactListController buildSubList (ArtifactListController included) {
		List <Artifact> workingList = new ArrayList<Artifact>();
		for (int ndx = 0; ndx < artifactList.size(); ndx++)
			workingList.add(ndx, artifactList.get(ndx));
		workingList.removeAll(included.getArtifacts());
		ArtifactListController temp = new ArtifactListController();
		temp.root = included.root;
		for (int ndx = 0; ndx < workingList.size(); ndx++)
			temp.addArtifact(workingList.get(ndx));
		return temp;
	}
	
	public ComboBoxModel<String> buildComboBoxModel () {
		DefaultComboBoxModel<String> result = (new DefaultComboBoxModel<String>());
		for (int ndx = 0; ndx < artifactList.size(); ndx++)
			result.addElement(((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + artifactList.get(ndx).getName());
		return result;
	}
	
	public void cleanUp(ArtifactListController standard){
		for (int ndx = 0; ndx < numberOfArtifacts; ndx++)
			if (standard.getArtifacts().indexOf(artifactList.get(ndx)) == -1) {
				artifactList.remove(ndx);
				numberOfArtifacts--;
			}
	}
	
	public void addArtifact(Artifact a) {
		artifactList.add(a);
		numberOfArtifacts++;
	}
	
	public void addArtifact(int ndx, Artifact a) {
		artifactList.add(ndx, a);
		numberOfArtifacts++;
	}
	
	public void addArtifact(String name, String description) {
		Artifact a = new Artifact(name, description);
		artifactList.add(a);
		numberOfArtifacts++;
	}
	
	public void addArtifact(int ndx, String name, String description) {
		if (ndx < 0 || ndx > numberOfArtifacts)
			return;
		Artifact a = new Artifact(name, description);
		artifactList.add(ndx, a);
		numberOfArtifacts++;
	}
	
	public void replaceArtifact(int ndx, String name, String description) {
		if (ndx < 0 || ndx >= numberOfArtifacts)
			return;
		Artifact a = artifactList.get(ndx);
		a.replaceName(name);
		a.replaceDesc(description);
	}
	
	public void moveArtifactUp(int ndx) {
		if (ndx <= 0 || ndx >= numberOfArtifacts)
			return;
		Artifact temp = artifactList.remove(ndx);
		artifactList.add(ndx-1, temp);
	}
	
	public void moveArtifactDn(int ndx) {
		if (ndx < 0 || ndx >= numberOfArtifacts-1)
			return;
		Artifact temp = artifactList.remove(ndx);
		artifactList.add(ndx+1, temp);
	}
	
	public void deleteArtifact(int ndx) {
		if (ndx < 0 || ndx >= numberOfArtifacts)
			return;
		artifactList.remove(ndx);
		numberOfArtifacts--;
	}
	
	public void locateOrReestablishArtifact(Artifact artItem) {
		for (Artifact a: artifactList)
			if (a == artItem)
				return;
		artifactList.add(artItem);
		numberOfArtifacts++;
		return;
	}
	
	private boolean findMember(Artifact t) {
		for (Artifact artifact: artifactList)
			if (artifact == t)
				return true;
		return false;
	}
	
	public void validateMembers(ArtifactListController root) {
		for (Artifact artifact: artifactList)
			if (root.findMember(artifact))
				continue;
			else
				root.addArtifact(artifact);
	}
	
	public String toString(){
		String result = "ArtifactList:";
		if (root == null)
			result += " (root = null)";
		else
			result += " (root != null)";
		int artifactSequenceNumber = 1;
		for (Artifact a: artifactList)
			result += "\n     " + artifactSequenceNumber++ + ". " + a.toString("     ");
		return result;
	}
	
	public String toString(String indent){
		String result = indent + "ArtifactList:";
		if (root == null)
			result += " (root = null)";
		else
			result += " (root != null)";
		int taskSequenceNumber = 1;
		for (Artifact t: artifactList)
			result += "\n" + indent + "     " + taskSequenceNumber++ + ". " + t.toString(indent +"     ");
		return result;
	}
}
