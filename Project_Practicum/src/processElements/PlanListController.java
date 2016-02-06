package processElements;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class PlanListController {
	private PlanListController root = null;
	private List <Plan> planList = new ArrayList <> ();
	private int numberOfPlans = 0;
	
	public PlanListController() {
		
	}
	
	public PlanListController(PlanListController that) {
		root = that.root;
		if (root == null) root = that;
		numberOfPlans = that.numberOfPlans;
		planList = that.getPlansListCopy();
	}
	
	public int getNumberOfPlans(){
		return numberOfPlans;
	}
	
	public PlanListController getRoot() {
		return root;
	}
	
	public boolean isEmpty(){
		return numberOfPlans == 0;
	}
	
	public boolean isNotEmpty(){
		return numberOfPlans > 0;
	}
	
	public Plan getPlan(int ndx){
		return planList.get(ndx);
	}
	
	public Plan extractPlan(int ndx){
		Plan result = planList.get(ndx);
		planList.remove(ndx);
		return result;
	}
 
	public List <Plan> getPlans(){
		return planList;
	}
 	
	public List <Plan> getPlansListCopy(){
		List <Plan> copy = new ArrayList <> ();
		for (int ndx = 0; ndx < planList.size(); ndx++)
			copy.add(planList.get(ndx));
		return copy;
	}
	
	public boolean isTheSameAs(PlanListController that){
		if (planList.size() != that.getNumberOfPlans())
			return false;
		for (int ndx = 0; ndx < planList.size(); ndx++)
			if (planList.get(ndx) != that.getPlan(ndx))
				return false;
		return true;
	}
	
	
	public String [] getList(){
		String [] result = new String[numberOfPlans];
		for (int ndx=0; ndx < numberOfPlans; ndx++)
			result[ndx] = planList.get(ndx).getName();
		return result;
	}
	
	public String [] buildSelectList(){
		String [] result = new String[numberOfPlans];
		for (int ndx=0; ndx < numberOfPlans; ndx++)
			result[ndx] = ((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + planList.get(ndx).getName();
		return result;
	}
	
	public PlanListController buildSubList (PlanListController included) {
		List <Plan> workingList = new ArrayList<Plan>();
		for (int ndx = 0; ndx < planList.size(); ndx++)
			workingList.add(ndx, planList.get(ndx));
		workingList.removeAll(included.getPlans());
		PlanListController temp = new PlanListController();
		temp.root = included.root;
		for (int ndx = 0; ndx < workingList.size(); ndx++)
			temp.addPlan(workingList.get(ndx));
		return temp;
	}
	
	public ComboBoxModel<String> buildComboBoxModel () {
		DefaultComboBoxModel<String> result = (new DefaultComboBoxModel<String>());
		for (int ndx = 0; ndx < planList.size(); ndx++)
			result.addElement(((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + planList.get(ndx).getName());
		return result;
	}
	
	public void cleanUp(PlanListController standard){
		for (int ndx = 0; ndx < numberOfPlans; ndx++)
			if (standard.getPlans().indexOf(planList.get(ndx)) == -1) {
				planList.remove(ndx);
				numberOfPlans--;
			}
	}
	
	public void addPlan(Plan a) {
		planList.add(a);
		numberOfPlans++;
	}
	
	public void addPlan(int ndx, Plan a) {
		planList.add(ndx, a);
		numberOfPlans++;
	}
	
	public void addPlan(String name, String description) {
		Plan a = new Plan(name, description);
		planList.add(a);
		numberOfPlans++;
	}
	
	public void addPlan(int ndx, String name, String description) {
		if (ndx < 0 || ndx > numberOfPlans)
			return;
		Plan a = new Plan(name, description);
		planList.add(ndx, a);
		numberOfPlans++;
	}
	
	public void replacePlan(int ndx, String name, String description) {
		if (ndx < 0 || ndx >= numberOfPlans)
			return;
		Plan a = planList.get(ndx);
		a.replaceName(name);
		a.replaceDesc(description);
	}
	
	public void movePlanUp(int ndx) {
		if (ndx <= 0 || ndx >= numberOfPlans)
			return;
		Plan temp = planList.remove(ndx);
		planList.add(ndx-1, temp);
	}
	
	public void movePlanDn(int ndx) {
		if (ndx < 0 || ndx >= numberOfPlans-1)
			return;
		Plan temp = planList.remove(ndx);
		planList.add(ndx+1, temp);
	}
	
	public void deletePlan(int ndx) {
		if (ndx < 0 || ndx >= numberOfPlans)
			return;
		planList.remove(ndx);
		numberOfPlans--;
	}
	
	public void locateOrReestablishPlan(Plan planItem) {
		for (Plan p: planList)
			if (p == planItem)
				return;
		planList.add(planItem);
		numberOfPlans++;
		return;
	}
	
	private boolean findMember(Plan p) {
		for (Plan plan: planList)
			if (plan == p)
				return true;
		return false;
	}
	
	public void validateMembers(PlanListController root) {
		for (Plan plan: planList)
			if (root.findMember(plan))
				continue;
			else
				root.addPlan(plan);
	}
	
	public String toString(){
		String result = "PlanList:";
		if (root == null)
			result += " (root = null)";
		else
			result += " (root != null)";
		int taskSequenceNumber = 1;
		for (Plan t: planList)
			result += "\n     " + taskSequenceNumber++ + ". " + t.toString("     ");
		return result;
	}
	
	public String toString(String indent){
		String result = indent + "PlanList:";
		if (root == null)
			result += " (root = null)";
		else
			result += " (root != null)";
		int planSequenceNumber = 1;
		for (Plan t: planList)
			result += "\n" + indent + "     " + planSequenceNumber++ + ". " + t.toString(indent + "     ");
		return result;
	}
}
