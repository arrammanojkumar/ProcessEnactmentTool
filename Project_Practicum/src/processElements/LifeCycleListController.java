package processElements;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class LifeCycleListController {
	
	private LifeCycleListController root = null;
	private List <LifeCycle> lifeCycleList = new ArrayList <> ();
	private int numberOfLifeCycles = 0;
	
	public LifeCycleListController() {
	}
	
	public LifeCycleListController(LifeCycleListController that) {
		root = that.root;
		if (root == null) root = that;
		numberOfLifeCycles = that.numberOfLifeCycles;
		lifeCycleList = that.getLifeCycleListCopy();
	}
	
	public int getNumberOfLifeCycles() {
		return numberOfLifeCycles;
	}
	
	public List <LifeCycle> getLifeCycleList() {
		return lifeCycleList;
	}
	
	public LifeCycle getLifeCycle(int ndx) {
		return lifeCycleList.get(ndx);
	}
	
	public LifeCycle extractLifeCycle(int ndx){
		LifeCycle result = lifeCycleList.get(ndx);
		lifeCycleList.remove(ndx);
		return result;
	}
	
	public String [] buildSelectList(){
		String [] result = new String[numberOfLifeCycles];
		for (int ndx=0; ndx < numberOfLifeCycles; ndx++)
			result[ndx] = ((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + lifeCycleList.get(ndx).getName();
		return result;
	}
	
	public LifeCycleListController buildSubList (LifeCycleListController included) {
		List <LifeCycle> workingList = new ArrayList<LifeCycle>();
		for (int ndx = 0; ndx < lifeCycleList.size(); ndx++)
			workingList.add(ndx, lifeCycleList.get(ndx));
		workingList.removeAll(included.getLifeCycleList());
		LifeCycleListController result = new LifeCycleListController();
		result.root = included.root;
		for (int ndx = 0; ndx < workingList.size(); ndx++)
			result.addLifeCycle(workingList.get(ndx));
		return result;
	}
	
	public ComboBoxModel<String> buildComboBoxModel () {
		DefaultComboBoxModel<String> result = (new DefaultComboBoxModel<String>());
		for (int ndx = 0; ndx < lifeCycleList.size(); ndx++)
			result.addElement(((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + lifeCycleList.get(ndx).getName());
		return result;
	}

	
	public void addLifeCycle(LifeCycle lc) {
		lifeCycleList.add(lc);
		numberOfLifeCycles++;
	}
	
	public void addLifeCycle(int ndx, LifeCycle lc) {
		lifeCycleList.add(ndx, lc);
		numberOfLifeCycles++;
	}

	public void addLifeCycle(String n, String d, LifeCycleListController lclc, ConditionListController clc,boolean lgl, int ndx, LifeCycleStepListController lcslc){
		
	
		
		lifeCycleList.add(new LifeCycle(n, d, 
				new LifeCycleListController(lclc), 
				new ConditionListController(clc),
				lgl,
				ndx,
				new LifeCycleStepListController(lcslc)));

		numberOfLifeCycles++;
		System.out.println(this.toString(""));
	}
	//int ndx, String n, String d, LifeCycleListController lclc, boolean lgl, int mode, LifeCycleStepListController lcslc
	public void addLifeCycle(int ndx, String n, String d, LifeCycleListController lclc,ConditionListController clc, boolean lgl, int mode, LifeCycleStepListController lcslc){
		//we will add only lifecycle objects
		
		
		
		lifeCycleList.add(ndx,new LifeCycle(n, d, 
				new LifeCycleListController(lclc), 
				new ConditionListController(clc),
				lgl,
				mode,
				new LifeCycleStepListController(lcslc)));
		numberOfLifeCycles++;
		System.out.println(this.toString(""));
	}
		
	public List <LifeCycle> getLifeCycleListCopy(){
		List <LifeCycle> copy = new ArrayList <LifeCycle> ();
		for (int ndx = 0; ndx < lifeCycleList.size(); ndx++)
			copy.add(lifeCycleList.get(ndx));
		return copy;
	}

	public void replaceLifeCycle(int ndx, String n, String d, LifeCycleListController lclc, ConditionListController clc,boolean lgl, int mode, LifeCycleStepListController lcslc){
		if (ndx < 0 || ndx >= numberOfLifeCycles)
			return;
		LifeCycle t = new LifeCycle(n, d, lclc, clc, lgl, mode, lcslc);
		lifeCycleList.set(ndx, t);
	}
	
	public boolean sameAs(LifeCycleListController that) {
		if (this.numberOfLifeCycles != that.numberOfLifeCycles) return false;
		for (int ndx= 0; ndx < this.numberOfLifeCycles; ndx++)
			if (this.lifeCycleList.get(ndx) != that.lifeCycleList.get(ndx)) return false;
		return true;
	}
	
	public void moveLifeCycleUp(int ndx) {
		if (ndx <= 0 || ndx >= numberOfLifeCycles)
			return;
		LifeCycle temp = lifeCycleList.remove(ndx);
		lifeCycleList.add(ndx-1, temp);
	}
	
	public void moveLifeCycleDn(int ndx) {
		if (ndx < 0 || ndx >= numberOfLifeCycles-1)
			return;
		LifeCycle temp = lifeCycleList.remove(ndx);
		lifeCycleList.add(ndx+1, temp);
	}

	
	public void deleteLifeCycle(int ndx){
		lifeCycleList.remove(ndx);
		numberOfLifeCycles--;
	}
	public String toString() {
		String result = "LifeCycleList:";
		int taskSequenceNumber = 1;
		for (LifeCycle lc: lifeCycleList)
			result += "\n     " + taskSequenceNumber++ + ": " + lc.toString();
		return result;
	}
	
	public String toString(String indent) {
		String result = indent + "LifeCycleList:";
		int taskSequenceNumber = 1;
		for (LifeCycle lc: lifeCycleList)
			result += indent + "\n" + indent + taskSequenceNumber++ + ": " + lc.toString(indent+"   ");
		return result;
	}
}
