package processElements;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class ConditionListController {
	private ConditionListController root = null;
	private List <Condition> conditionList = new ArrayList <> ();
	private int numberOfConditions = 0;
	
	public ConditionListController() {
		
	}
	
	public ConditionListController(ConditionListController that) {
		root = that.root;
		if (root == null) root = that;
		numberOfConditions = that.numberOfConditions;
		conditionList = that.getConditionListCopy();
	}
	
	public int getNumberOfConditions(){
		return numberOfConditions;
	}
	
	public boolean isEmpty(){
		return numberOfConditions == 0;
	}
	
	public Condition getCondition(int ndx){
		return conditionList.get(ndx);
	}
	
	public Condition extractCondition(int ndx){
		Condition result = conditionList.get(ndx);
		conditionList.remove(ndx);
		return result;
	}
 
	public List <Condition> getConditions(){
		return conditionList;
	}
 	
	public List <Condition> getConditionListCopy(){
		List <Condition> copy = new ArrayList <Condition> ();
		for (int ndx = 0; ndx < conditionList.size(); ndx++)
			copy.add(conditionList.get(ndx));
		return copy;
	}
	
	public boolean listIsTheSame(List <Condition> that){
		if (conditionList.size() != that.size())
			return false;
		for (int ndx = 0; ndx < conditionList.size(); ndx++)
			if (conditionList.get(ndx) != that.get(ndx))
				return false;
		return true;
	}
	
	public boolean isTheSameAs(ConditionListController that) {
		if (this.numberOfConditions != that.numberOfConditions) return false;
		for (int ndx= 0; ndx < this.numberOfConditions; ndx++)
			if (this.conditionList.get(ndx) != that.conditionList.get(ndx)) return false;
		return true;
	}
	
	public String [] getList(){
		String [] result = new String[numberOfConditions];
		for (int ndx=0; ndx < numberOfConditions; ndx++)
			result[ndx] = conditionList.get(ndx).getName();
		return result;
	}
	
	public String [] buildSelectList(){
		String [] result = new String[numberOfConditions];
		for (int ndx=0; ndx < numberOfConditions; ndx++)
			result[ndx] = ((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + conditionList.get(ndx).getName();
		return result;
	}
	
	public ConditionListController buildSubList (ConditionListController included) {
		List <Condition> workingList = new ArrayList<Condition>();
		for (int ndx = 0; ndx < conditionList.size(); ndx++)
			workingList.add(ndx, conditionList.get(ndx));
		workingList.removeAll(included.getConditions());
		ConditionListController temp = new ConditionListController();
		temp.root = included.root;
		for (int ndx = 0; ndx < workingList.size(); ndx++)
			temp.addCondition(workingList.get(ndx));
		return temp;
	}
	
	public ComboBoxModel<String> buildComboBoxModel () {
		DefaultComboBoxModel<String> result = (new DefaultComboBoxModel<String>());
		for (int ndx = 0; ndx < conditionList.size(); ndx++)
			result.addElement(((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + conditionList.get(ndx).getName());
		return result;
	}
	
	public void cleanUp(ArtifactListController standard){
		for (int ndx = 0; ndx < numberOfConditions; ndx++)
			if (standard.getArtifacts().indexOf(conditionList.get(ndx)) == -1) {
				conditionList.remove(ndx);
				numberOfConditions--;
			}
	}
	
	public void addCondition(Condition c) {
		conditionList.add(c);
		numberOfConditions++;
	}
	
	public void addCondition(int ndx, Condition c) {
		conditionList.add(ndx, c);
		numberOfConditions++;
	}
	
	public void addCondition(String n, String d, boolean s) {
		Condition c = new Condition(n, d, s);
		conditionList.add(c);
		numberOfConditions++;
	}
	
	public void addCondition(int ndx, String n, String d, boolean s) {
		if (ndx < 0 || ndx > numberOfConditions)
			return;
		Condition c = new Condition(n, d, s);
		conditionList.add(ndx, c);
		numberOfConditions++;
	}
	
	public void replaceCondition(int ndx, String n, String d, boolean s) {
		if (ndx < 0 || ndx >= numberOfConditions)
			return;
		Condition c = conditionList.get(ndx);
		c.replaceName(n);
		c.replaceDesc(d);
		c.replaceState(s);
	}
	
	public void moveConditionUp(int ndx) {
		if (ndx <= 0 || ndx >= numberOfConditions)
			return;
		Condition temp = conditionList.remove(ndx);
		conditionList.add(ndx-1, temp);
	}
	
	public void moveConditionDn(int ndx) {
		if (ndx < 0 || ndx >= numberOfConditions-1)
			return;
		Condition temp = conditionList.remove(ndx);
		conditionList.add(ndx+1, temp);
	}
	
	public void deleteCondition(int ndx) {
		if (ndx < 0 || ndx >= numberOfConditions)
			return;
		conditionList.remove(ndx);
		numberOfConditions--;
	}
	
	private boolean findMember(Condition c) {
		for (Condition cond: conditionList)
			if (cond == c)
				return true;
		return false;
	}
	
	public void validateMembers(ConditionListController root) {
		for (Condition cond: conditionList)
			if (root.findMember(cond))
				continue;
			else
				root.addCondition(cond);
	}
	
	public void checkToInsert(LifeCycleListController root)
	{
		for(LifeCycle lifeCycle : root.getLifeCycleList())
		{
			for(Condition con : lifeCycle.getLifeCycleItersList().getConditions())  //conditions of a lifecycle
			{
				if(conditionList.contains(con))
				{
				}
				else
				{
					conditionList.add(con);
					numberOfConditions++;
				}
			}
		}
		
		/*ConditionListController temp = new ConditionListController();
		for (int ndx = 0; ndx < conditionList.size(); ndx++)
			temp.addCondition(conditionList.get(ndx));
		return temp;*/
	}
	public String toString(){
		String result = "ConditionList:";
		int taskSequenceNumber = 1;
		for (Condition t: conditionList)
			result += "\n     " + taskSequenceNumber++ + ". " + t.toString("     ");
		return result;
	}

}
