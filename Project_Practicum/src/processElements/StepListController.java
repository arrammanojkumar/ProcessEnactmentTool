package processElements;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class StepListController {
	
	private StepListController root = null;
	private List <Step> stepList = new ArrayList <> ();
	private int numberOfSteps = 0;
	
	public StepListController() {
		
	}
		
	public StepListController(StepListController that) {
		root = that.root;
		if (root == null) root = that;
		numberOfSteps = that.numberOfSteps;
		stepList = that.getStepListCopy();
	}
	
	public int getNumberOfSteps() {
		return stepList.size();
	}
	
	public List <Step> getSteps() {
		return stepList;
	}
	
	public Step getStep(int ndx) {
		return stepList.get(ndx);
	}
	
	public Step extractStep(int ndx){
		Step result = stepList.get(ndx);
		stepList.remove(ndx);
		return result;
	}
	
	public String [] buildSelectList(){
		String [] result = new String[numberOfSteps];
		for (int ndx=0; ndx < numberOfSteps; ndx++)
			result[ndx] = ((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + stepList.get(ndx).getName();
		return result;
	}
	
	public StepListController buildSubList (StepListController included) {
		List <Step> workingList = new ArrayList<Step>();
		for (int ndx = 0; ndx < stepList.size(); ndx++)
			workingList.add(ndx, stepList.get(ndx));
		workingList.removeAll(included.getSteps());
		StepListController result = new StepListController();
		result.root = included.root;
		for (int ndx = 0; ndx < workingList.size(); ndx++)
			result.addStep(workingList.get(ndx));
		return result;
	}
	
	public ComboBoxModel<String> buildComboBoxModel () {
		DefaultComboBoxModel<String> result = (new DefaultComboBoxModel<String>());
		for (int ndx = 0; ndx < stepList.size(); ndx++)
			result.addElement(((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + stepList.get(ndx).getName());
		return result;
	}

	public void addStep(Step t) {
		stepList.add(t);
		numberOfSteps++;
	}
	public void addStep(LifeCycleStepListController that)
	{
		for(int i=0;i<that.getNumberOfLifeCycleSteps();i++)
			stepList.add(that.getLifeCycleStep(i).getLifeCycleStepList());
	}
	
	public void addStep(int ndx, Step t) {
		stepList.add(ndx, t);
		numberOfSteps++;
	}

	public void addStep(String n, String d, ConditionListController pre, 
			ConditionListController post, TaskListController  tasks){
		stepList.add(new Step(n, d, 
				new ConditionListController(pre), 
				new ConditionListController(post), 
				new TaskListController(tasks)));
		numberOfSteps++;
		System.out.println(this.toString(""));
	}
	
	public void addStep(int ndx, String n, String d, ConditionListController pre, 
			ConditionListController post, TaskListController  tasks){
		stepList.add(ndx, new Step(n, d, 
				new ConditionListController(pre), 
				new ConditionListController(post), 
				new TaskListController(tasks)));
		numberOfSteps++;
		System.out.println(this.toString(""));
	}
		
	public List <Step> getStepListCopy(){
		List <Step> copy = new ArrayList <> ();
		for (int ndx = 0; ndx < stepList.size(); ndx++)
			copy.add(stepList.get(ndx));
		return copy;
	}

	public void replaceStep(int ndx, String n, String d, ConditionListController pre, 
			ConditionListController post, TaskListController  tasks){
		if (ndx < 0 || ndx >= numberOfSteps)
			return;
		Step t = new Step(n, d, pre, post, tasks);
		stepList.set(ndx, t);
	}
	
	public boolean sameAs(StepListController that) {
		if (this.numberOfSteps != that.numberOfSteps) return false;
		for (int ndx= 0; ndx < this.numberOfSteps; ndx++)
			if (this.stepList.get(ndx) != that.stepList.get(ndx)) return false;
		return true;
	}

	
	public void moveStepUp(int ndx) {
		if (ndx <= 0 || ndx >= numberOfSteps)
			return;
		Step temp = stepList.remove(ndx);
		stepList.add(ndx-1, temp);
	}
	
	public void moveStepDn(int ndx) {
		if (ndx < 0 || ndx >= numberOfSteps-1)
			return;
		Step temp = stepList.remove(ndx);
		stepList.add(ndx+1, temp);
	}

	public void deleteStep(int ndx){
		stepList.remove(ndx);
		numberOfSteps--;
	}
	public void deleteStep(Step step)
	{
		for(int i=0;i<stepList.size();i++)
		{
			if(stepList.get(i)==step)
				deleteStep(i);
		}
	}
	private boolean findMember(Step t) {
		for (Step step: stepList)
			if (step == t)
				return true;
		return false;
	}
	public void validateMembers(LifeCycleListController root) {
		List <Step> tempstepList = new ArrayList <> ();
		for (LifeCycle  lc: root.getLifeCycleList())
		{
			List<LifeCycleStep> al=lc.getLifeCycleStepList().getLifeCycleStepList();
			for(int i=0;i<lc.getLifeCycleStepList().getNumberOfLifeCycleSteps();i++)
			{
				if(this.findMember(al.get(i).getLifeCycleStepList()))
				{}
				else
				{
					tempstepList.add(al.get(i).getLifeCycleStepList());
				}
					
			}
		}
		
		for(int i=0;i<tempstepList.size();i++)
		{
			if(!stepList.contains(tempstepList.get(i))){
			this.stepList.add(tempstepList.get(i));
			numberOfSteps++;
			}
		}
	}

	 
	public String toString() {
		String result = "StepList:";
		int taskSequenceNumber = 1;
		for (Step s: stepList)
			result += "\n     " + taskSequenceNumber++ + ": " + s.toString();
		return result;
	}
	
	public String toString(String indent) {
		String result = indent + "StepList:";
		int taskSequenceNumber = 1;
		for (Step s: stepList)
			result += indent + "\n   " + indent + taskSequenceNumber++ + ": " + s.toString(indent+"   ");
		return result;
	}
}
