package processElements;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class DefectListController {
	private DefectListController root = null;
	private List <Defect> defectList = new ArrayList <> ();
	private int numberOfDefects = 0;
	
	public DefectListController() {
		
	}
	
	public DefectListController(DefectListController that) {
		root = that.root;
		if (root == null) root = that;
		numberOfDefects = that.numberOfDefects;
		defectList = that.getDefectsListCopy();
	}
	
	public int getNumberOfDefects(){
		return numberOfDefects;
	}
	
	public boolean isEmpty(){
		return numberOfDefects == 0;
	}
	
	public DefectListController getRoot() {
		return root;
	}
	
	public boolean isNotEmpty(){
		return numberOfDefects > 0;
	}
	
	public Defect getDefect(int ndx){
		return defectList.get(ndx);
	}
	
	public Defect extractDefect(int ndx){
		Defect result = defectList.get(ndx);
		defectList.remove(ndx);
		return result;
	}
 
	public List <Defect> getDefects(){
		return defectList;
	}
 	
	public List <Defect> getDefectsListCopy(){
		List <Defect> copy = new ArrayList <> ();
		for (int ndx = 0; ndx < defectList.size(); ndx++)
			copy.add(defectList.get(ndx));
		return copy;
	}
	
	public boolean isTheSameAs(DefectListController that){
		if (defectList.size() != that.getNumberOfDefects())
			return false;
		for (int ndx = 0; ndx < defectList.size(); ndx++)
			if (defectList.get(ndx) != that.getDefect(ndx))
				return false;
		return true;
	}
		
	public String [] getList(){
		String [] result = new String[numberOfDefects];
		for (int ndx=0; ndx < numberOfDefects; ndx++)
			result[ndx] = defectList.get(ndx).getName();
		return result;
	}
	
	public String [] buildSelectList(){
		String [] result = new String[numberOfDefects];
		for (int ndx=0; ndx < numberOfDefects; ndx++)
			result[ndx] = ((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + defectList.get(ndx).getName();
		return result;
	}
	
	public DefectListController buildSubList (DefectListController included) {
		List <Defect> workingList = new ArrayList<Defect>();
		for (int ndx = 0; ndx < defectList.size(); ndx++)
			workingList.add(ndx, defectList.get(ndx));
		workingList.removeAll(included.getDefects());
		DefectListController temp = new DefectListController();
		temp.root = included.root;
		for (int ndx = 0; ndx < workingList.size(); ndx++)
			temp.addDefect(workingList.get(ndx));
		return temp;
	}
	
	public ComboBoxModel<String> buildComboBoxModel () {
		DefaultComboBoxModel<String> result = (new DefaultComboBoxModel<String>());
		for (int ndx = 0; ndx < defectList.size(); ndx++)
			result.addElement(((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + defectList.get(ndx).getName());
		return result;
	}
	
	public void cleanUp(DefectListController standard){
		for (int ndx = 0; ndx < numberOfDefects; ndx++)
			if (standard.getDefects().indexOf(defectList.get(ndx)) == -1) {
				defectList.remove(ndx);
				numberOfDefects--;
			}
	}
	
	public void addDefect(Defect a) {
		defectList.add(a);
		numberOfDefects++;
	}
	
	public void addDefect(int ndx, Defect a) {
		defectList.add(ndx, a);
		numberOfDefects++;
	}
	
	public void addDefect(String name, String description) {
		Defect a = new Defect(name, description);
		defectList.add(a);
		numberOfDefects++;
	}
	
	public void addDefect(int ndx, String name, String description) {
		if (ndx < 0 || ndx > numberOfDefects)
			return;
		Defect a = new Defect(name, description);
		defectList.add(ndx, a);
		numberOfDefects++;
	}
	
	public void replaceDefect(int ndx, String name, String description) {
		if (ndx < 0 || ndx >= numberOfDefects)
			return;
		Defect a = defectList.get(ndx);
		a.replaceName(name);
		a.replaceDesc(description);
	}
	
	public void moveDefectUp(int ndx) {
		if (ndx <= 0 || ndx >= numberOfDefects)
			return;
		Defect temp = defectList.remove(ndx);
		defectList.add(ndx-1, temp);
	}
	
	public void moveDefectDn(int ndx) {
		if (ndx < 0 || ndx >= numberOfDefects-1)
			return;
		Defect temp = defectList.remove(ndx);
		defectList.add(ndx+1, temp);
	}
	
	public void deleteDefect(int ndx) {
		if (ndx < 0 || ndx >= numberOfDefects)
			return;
		defectList.remove(ndx);
		numberOfDefects--;
	}
	
	public void locateOrReestablishDefect(Defect defectItem) {
		for (Defect d: defectList)
			if (d == defectItem)
				return;
		defectList.add(defectItem);
		numberOfDefects++;
		return;
	}
	
	private boolean findMember(Defect d) {
		for (Defect defect: defectList)
			if (defect == d)
				return true;
		return false;
	}
	
	public void validateMembers(DefectListController root) {
		for (Defect defect: defectList)
			if (root.findMember(defect))
				continue;
			else
				root.addDefect(defect);
	}
	
	public String toString(){
		String result = "DefectList:";
		if (root == null)
			result += " (root = null)";
		else
			result += " (root != null)";
		int defectSequenceNumber = 1;
		for (Defect d: defectList)
			result += "\n     " + defectSequenceNumber++ + ". " + d.toString("     ");
		return result;
	}
	
	public String toString(String indent){
		String result = indent + "DefectList:";
		if (root == null)
			result += " (root = null)";
		else
			result += " (root != null)";
		int defectSequenceNumber = 1;
		for (Defect d: defectList)
			result += "\n" + indent + "     " + defectSequenceNumber++ + ". " +
						d.toString(indent + "     ");
		return result;
	}
}
