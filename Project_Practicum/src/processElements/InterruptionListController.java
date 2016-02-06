
package processElements;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class InterruptionListController {
	private InterruptionListController root = null;
	private List <Interruption> interruptionList = new ArrayList <> ();
	private int numberOfInterruptions = 0;
	
	public InterruptionListController() {
		
	}
	
	public InterruptionListController(InterruptionListController that) {
		root = that.root;
		if (root == null) root = that;
		numberOfInterruptions = that.numberOfInterruptions;
		interruptionList = that.getInterruptionsListCopy();
	}
	
	public int getNumberOfInterruptions(){
		return numberOfInterruptions;
	}
	
	public InterruptionListController getRoot() {
		return root;
	}
	
	public boolean isEmpty(){
		return numberOfInterruptions == 0;
	}
	
	public boolean isNotEmpty(){
		return numberOfInterruptions > 0;
	}
	
	public Interruption getInterruption(int ndx){
		return interruptionList.get(ndx);
	}
	
	public Interruption extractInterruption(int ndx){
		Interruption result = interruptionList.get(ndx);
		interruptionList.remove(ndx);
		return result;
	}
 
	public List <Interruption> getInterruptions(){
		return interruptionList;
	}
 	
	public List <Interruption> getInterruptionsListCopy(){
		List <Interruption> copy = new ArrayList <> ();
		for (int ndx = 0; ndx < interruptionList.size(); ndx++)
			copy.add(interruptionList.get(ndx));
		return copy;
	}
	
	public boolean isTheSameAs(InterruptionListController that){
		if (interruptionList.size() != that.getNumberOfInterruptions())
			return false;
		for (int ndx = 0; ndx < interruptionList.size(); ndx++)
			if (interruptionList.get(ndx) != that.getInterruption(ndx))
				return false;
		return true;
	}
	
	public String [] getList(){
		String [] result = new String[numberOfInterruptions];
		for (int ndx=0; ndx < numberOfInterruptions; ndx++)
			result[ndx] = interruptionList.get(ndx).getName();
		return result;
	}
	
	public String [] buildSelectList(){
		String [] result = new String[numberOfInterruptions];
		for (int ndx=0; ndx < numberOfInterruptions; ndx++)
			result[ndx] = ((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + interruptionList.get(ndx).getName();
		return result;
	}
	
	public InterruptionListController buildSubList (InterruptionListController included) {
		root = included.root;
		List <Interruption> workingList = new ArrayList<Interruption>();
		for (int ndx = 0; ndx < interruptionList.size(); ndx++)
			workingList.add(ndx, interruptionList.get(ndx));
		workingList.removeAll(included.getInterruptions());
		InterruptionListController temp = new InterruptionListController();
		temp.root = included.root;
		for (int ndx = 0; ndx < workingList.size(); ndx++)
			temp.addInterruption(workingList.get(ndx));
		return temp;
	}
	
	public ComboBoxModel<String> buildComboBoxModel () {
		DefaultComboBoxModel<String> result = (new DefaultComboBoxModel<String>());
		for (int ndx = 0; ndx < interruptionList.size(); ndx++)
			result.addElement(((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + interruptionList.get(ndx).getName());
		return result;
	}
	
	public void cleanUp(InterruptionListController standard){
		for (int ndx = 0; ndx < numberOfInterruptions; ndx++)
			if (standard.getInterruptions().indexOf(interruptionList.get(ndx)) == -1) {
				interruptionList.remove(ndx);
				numberOfInterruptions--;
			}
	}
	
	public void addInterruption(Interruption a) {
		interruptionList.add(a);
		numberOfInterruptions++;
	}
	
	public void addInterruption(int ndx, Interruption a) {
		interruptionList.add(ndx, a);
		numberOfInterruptions++;
	}
	
	public void addInterruption(String name, String description) {
		Interruption a = new Interruption(name, description);
		interruptionList.add(a);
		numberOfInterruptions++;
	}
	
	public void addInterruption(int ndx, String name, String description) {
		if (ndx < 0 || ndx > numberOfInterruptions)
			return;
		Interruption a = new Interruption(name, description);
		interruptionList.add(ndx, a);
		numberOfInterruptions++;
	}
	
	public void replaceInterruption(int ndx, String name, String description) {
		if (ndx < 0 || ndx >= numberOfInterruptions)
			return;
		Interruption a = interruptionList.get(ndx);
		a.replaceName(name);
		a.replaceDesc(description);
	}
	
	public void moveInterruptionUp(int ndx) {
		if (ndx <= 0 || ndx >= numberOfInterruptions)
			return;
		Interruption temp = interruptionList.remove(ndx);
		interruptionList.add(ndx-1, temp);
	}
	
	public void moveInterruptionDn(int ndx) {
		if (ndx < 0 || ndx >= numberOfInterruptions-1)
			return;
		Interruption temp = interruptionList.remove(ndx);
		interruptionList.add(ndx+1, temp);
	}
	
	public void deleteInterruption(int ndx) {
		if (ndx < 0 || ndx >= numberOfInterruptions)
			return;
		interruptionList.remove(ndx);
		numberOfInterruptions--;
	}
	
	public void locateOrReestablishInterruption(Interruption intItem) {
		for (Interruption i: interruptionList)
			if (i == intItem)
				return;
		interruptionList.add(intItem);
		numberOfInterruptions++;
		return;
	}
	
	private boolean findMember(Interruption i) {
		for (Interruption interruption: interruptionList)
			if (interruption == i)
				return true;
		return false;
	}
	
	public void validateMembers(InterruptionListController root) {
		for (Interruption interruption: interruptionList)
			if (root.findMember(interruption))
				continue;
			else
				root.addInterruption(interruption);
	}
	
	public String toString(){
		String result = "InterruptionList:";
		if (root == null)
			result += " (root = null)";
		else
			result += " (root != null)";
		int taskSequenceNumber = 1;
		for (Interruption t: interruptionList)
			result += "\n     " + taskSequenceNumber++ + ". " + t.toString("     ");
		return result;
	}
	
	public String toString(String indent){
		String result = indent + "InterruptionList:";
		if (root == null)
			result += " (root = null)";
		else
			result += " (root != null)";
		int taskSequenceNumber = 1;
		for (Interruption t: interruptionList)
			result += "\n" + indent + "     " + taskSequenceNumber++ + ". " + t.toString(indent + "     ");
		return result;
	}
}
