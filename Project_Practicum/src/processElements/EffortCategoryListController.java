package processElements;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class EffortCategoryListController {
	private EffortCategoryListController root = null;
	private List <EffortCategory> effortCategoryList = new ArrayList <> ();
	private int numberOfEffortCategories = 0;
	
	private final ArtifactListController aNull = new ArtifactListController();
	private final PlanListController pNull = new PlanListController();
	private final InterruptionListController iNull = new InterruptionListController();
	private final DefectListController dNull = new DefectListController();

	public EffortCategoryListController() {
	
	}
	
	public EffortCategoryListController(EffortCategoryListController that) {
		root = that.root;
		numberOfEffortCategories = that.numberOfEffortCategories;
		effortCategoryList = that.getEffortCategoriesListCopy();
	}
	
	public int getNumberOfEffortCategories(){
		return numberOfEffortCategories;
	}
	
	public boolean isEmpty(){
		return numberOfEffortCategories == 0;
	}
	
	public boolean isNotEmpty(){
		return numberOfEffortCategories > 0;
	}
	
	public EffortCategory getEffortCategory(int ndx){
		if (ndx < 0) return null;
		return effortCategoryList.get(ndx);
	}
	
	public EffortCategory extractEffortCategory(int ndx){
		EffortCategory result = effortCategoryList.get(ndx);
		effortCategoryList.remove(ndx);
		return result;
	}
 
	public List <EffortCategory> getEffortCategorys(){
		return effortCategoryList;
	}
 	
	public List <EffortCategory> getEffortCategoriesListCopy(){
		List <EffortCategory> copy = new ArrayList <> ();
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			copy.add(effortCategoryList.get(ndx));
		return copy;
	}
	
	public boolean listIsTheSame(List <EffortCategory> that){
		if (effortCategoryList.size() != that.size())
			return false;
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx) != that.get(ndx))
				return false;
		return true;
	}
	
	public String [] getList(){
		String [] result = new String[numberOfEffortCategories];
		for (int ndx=0; ndx < numberOfEffortCategories; ndx++)
			result[ndx] = effortCategoryList.get(ndx).getName();
		return result;
	}
	
	public String [] buildSelectList(){
		String [] result = new String[numberOfEffortCategories];
		for (int ndx=0; ndx < numberOfEffortCategories; ndx++)
			result[ndx] = ((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + 
					effortCategoryList.get(ndx).getName() + " (" + 
					effortCategoryList.get(ndx).getKindString() + ")";
		return result;
	}
	
	public EffortCategoryListController extractEffortCategoryArtifact() {
		EffortCategoryListController result = new EffortCategoryListController();
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx).getKind() == 0)
				result.addEffortCategory(effortCategoryList.get(ndx));
		result.root = root;
		return result;
	}
	
	public EffortCategoryListController extractEffortCategoryPlan() {
		EffortCategoryListController result = new EffortCategoryListController();
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx).getKind() == 1)
				result.addEffortCategory(effortCategoryList.get(ndx));
		result.root = root;
		return result;
	}
	
	public EffortCategoryListController extractEffortCategoryInterruption() {
		EffortCategoryListController result = new EffortCategoryListController();
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx).getKind() == 2)
				result.addEffortCategory(effortCategoryList.get(ndx));
		result.root = root;
		return result;
	}
	
	public EffortCategoryListController extractEffortCategoryDefect() {
		EffortCategoryListController result = new EffortCategoryListController();
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx).getKind() == 3)
				result.addEffortCategory(effortCategoryList.get(ndx));
		result.root = root;
		return result;
	}
	
	public EffortCategoryListController extractEffortCategoryOther() {
		EffortCategoryListController result = new EffortCategoryListController();
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx).getKind() == 4)
				result.addEffortCategory(effortCategoryList.get(ndx));
		result.root = root;
		return result;
	}
	
	public EffortCategoryListController buildSubList (EffortCategoryListController included) {
		List <EffortCategory> workingList = new ArrayList<EffortCategory>();
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			workingList.add(ndx, effortCategoryList.get(ndx));
		workingList.removeAll(included.getEffortCategorys());
		EffortCategoryListController temp = new EffortCategoryListController();
		temp.root = included.root;
		for (int ndx = 0; ndx < workingList.size(); ndx++)
			temp.addEffortCategory(workingList.get(ndx));
		return temp;
	}
	
	public ComboBoxModel<String> buildComboBoxModel () {
		DefaultComboBoxModel<String> result = (new DefaultComboBoxModel<String>());
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			result.addElement(((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + 
					effortCategoryList.get(ndx).getName() + " (" + 
					effortCategoryList.get(ndx).getKindString() + ")");
		return result;
	}
	public ComboBoxModel<String> buildComboBoxModel2() {
		DefaultComboBoxModel<String> result = (new DefaultComboBoxModel<String>());
		result.addElement("No item selected");
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			result.addElement(((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + 
					effortCategoryList.get(ndx).getName() + " (" + 
					effortCategoryList.get(ndx).getKindString() + ")");
		return result;
	}
	
	public void cleanUp(EffortCategoryListController standard){
		for (int ndx = 0; ndx < numberOfEffortCategories; ndx++)
			if (standard.getEffortCategorys().indexOf(effortCategoryList.get(ndx)) == -1) {
				effortCategoryList.remove(ndx);
				numberOfEffortCategories--;
			}
	}
	
	public void addEffortCategory(EffortCategory a) {
		effortCategoryList.add(a);
		numberOfEffortCategories++;
	}
	
	public void addEffortCategory(int ndx, EffortCategory a) {
		effortCategoryList.add(ndx, a);
		numberOfEffortCategories++;
	}
	
	public void addEffortCategory(String name, String description, int kind, 
			ArtifactListController aL, PlanListController pL, InterruptionListController iL, 
			DefectListController dL) {
		EffortCategory a = new EffortCategory(name, description, kind, aL, pL, iL, dL);
		effortCategoryList.add(a);
		numberOfEffortCategories++;
	}
	
	public void addEffortCategory(int ndx, String name, String description, int kind, 
			ArtifactListController aL, PlanListController pL, InterruptionListController iL, 
			DefectListController dL) {
		if (ndx < 0 || ndx > numberOfEffortCategories)
			return;
		EffortCategory a = new EffortCategory(name, description, kind, aL, pL, iL, dL);
		effortCategoryList.add(ndx, a);
		numberOfEffortCategories++;
	}
	
	public void replaceEffortCategory(int ndx, String name, String description) {
		if (ndx < 0 || ndx >= numberOfEffortCategories)
			return;
		EffortCategory a = effortCategoryList.get(ndx);
		a.replaceName(name);
		a.replaceDesc(description);
	}
	
	public void moveEffortCategoryUp(int ndx) {
		if (ndx <= 0 || ndx >= numberOfEffortCategories)
			return;
		EffortCategory temp = effortCategoryList.remove(ndx);
		effortCategoryList.add(ndx-1, temp);
	}
	
	public void moveEffortCategoryDn(int ndx) {
		if (ndx < 0 || ndx >= numberOfEffortCategories-1)
			return;
		EffortCategory temp = effortCategoryList.remove(ndx);
		effortCategoryList.add(ndx+1, temp);
	}
	
	public void deleteEffortCategory(int ndx) {
		if (ndx < 0 || ndx >= numberOfEffortCategories)
			return;
		effortCategoryList.remove(ndx);
		numberOfEffortCategories--;
	}
	
	public int getArtifactSelector(ArtifactListController aList){
		// If the list is empty, there is no work to do
		if (aList.getNumberOfArtifacts() == 0)
			return -1;
		
		// Try to find the match in the existing data structures
		int returnResult = 0;
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx).getKind() == 0)
				if (effortCategoryList.get(ndx).getArtifactListController().isTheSameAs(aList))
					return returnResult;
				else
					returnResult++;
		
		// If we get here, something has changed, so we will need to recreate the match
		ArtifactListController artRoot = aList.getRoot();
		
		// Ensure each Artifact element in the list is in the the Artifact pool.
		for (int ndx = 0; ndx < aList.getNumberOfArtifacts(); ndx++) 
			artRoot.locateOrReestablishArtifact(aList.getArtifact(ndx));
			
		// Establish the new Effort Category list from this aList and return its index
		EffortCategory newEC = new EffortCategory("Generated[" + (numberOfEffortCategories+1) + "]",
				"Generated by this application when a match could not be found", 
				0, 
				new ArtifactListController(aList), 
				pNull, 
				iNull, 
				dNull);
		effortCategoryList.add(newEC);
		numberOfEffortCategories++;
		
		returnResult = 0;
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx).getKind() == 0)
				if (effortCategoryList.get(ndx).getArtifactListController().isTheSameAs(aList))
					return returnResult;
				else
					returnResult++;

		return -1;
	}
	
	public int getPlanSelector(PlanListController pList){
		// If the list is empty, there is no work to do
		if (pList.getNumberOfPlans() == 0)
			return -1;
		
		// Try to find the match in the existing data structures
		int returnResult = 0;
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx).getKind() == 1)
				if (effortCategoryList.get(ndx).getPlanListController().isTheSameAs(pList))
					return returnResult;
				else
					returnResult++;
		
		// If we get here, something has changed, so we will need to recreate the match
		PlanListController planRoot = pList.getRoot();
		
		// Ensure each Artifact element in the list is in the the Artifact pool.
		for (int ndx = 0; ndx < pList.getNumberOfPlans(); ndx++) 
			planRoot.locateOrReestablishPlan(pList.getPlan(ndx));
			
		// Establish the new Effort Category list from this aList and return its index
		EffortCategory newEC = new EffortCategory("Generated[" + (numberOfEffortCategories+1) + "]",
				"Generated by this application when a match could not be found", 
				1, 
				aNull, 
				new PlanListController(pList), 
				iNull, 
				dNull);
		effortCategoryList.add(newEC);
		numberOfEffortCategories++;
		
		returnResult = 0;
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx).getKind() == 1)
				if (effortCategoryList.get(ndx).getPlanListController().isTheSameAs(pList))
					return returnResult;
				else
					returnResult++;

		return -1;
	}
	
	public int getInterruptionSelector(InterruptionListController iList){
		// If the list is empty, there is no work to do
		if (iList.getNumberOfInterruptions() == 0)
			return -1;
		
		// Try to find the match in the existing data structures
		int returnResult = 0;
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx).getKind() == 2)
				if (effortCategoryList.get(ndx).getInterruptionListController().isTheSameAs(iList))
					return returnResult;
				else
					returnResult++;
		
		// If we get here, something has changed, so we will need to recreate the match
		InterruptionListController intRoot = iList.getRoot();
		
		// Ensure each Artifact element in the list is in the the Artifact pool.
		for (int ndx = 0; ndx < iList.getNumberOfInterruptions(); ndx++) 
			intRoot.locateOrReestablishInterruption(iList.getInterruption(ndx));
			
		// Establish the new Effort Category list from this aList and return its index
		EffortCategory newEC = new EffortCategory("Generated[" + (numberOfEffortCategories+1) + "]",
				"Generated by this application when a match could not be found", 
				2, 
				aNull, 
				pNull, 
				new InterruptionListController(iList), 
				dNull);
		effortCategoryList.add(newEC);
		numberOfEffortCategories++;
		
		returnResult = 0;
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx).getKind() == 2)
				if (effortCategoryList.get(ndx).getInterruptionListController().isTheSameAs(iList))
					return returnResult;
				else
					returnResult++;

		return -1;
	}
	
	public int getDefectSelector(DefectListController dList){
		// If the list is empty, there is no work to do
		if (dList.getNumberOfDefects() == 0)
			return -1;
		
		// Try to find the match in the existing data structures
		int returnResult = 0;
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx).getKind() == 3)
				if (effortCategoryList.get(ndx).getDefectListController().isTheSameAs(dList))
					return returnResult;
				else
					returnResult++;
		
		// If we get here, something has changed, so we will need to recreate the match
		DefectListController defectRoot = dList.getRoot();
		
		// Ensure each Artifact element in the list is in the the Artifact pool.
		for (int ndx = 0; ndx < dList.getNumberOfDefects(); ndx++) 
			defectRoot.locateOrReestablishDefect(dList.getDefect(ndx));
			
		// Establish the new Effort Category list from this aList and return its index
		EffortCategory newEC = new EffortCategory("Generated[" + (numberOfEffortCategories+1) + "]",
				"Generated by this application when a match could not be found", 
				3, 
				aNull, 
				pNull, 
				iNull, 
				new DefectListController(dList));
		effortCategoryList.add(newEC);
		numberOfEffortCategories++;

		returnResult = 0;
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx).getKind() == 3)
				if (effortCategoryList.get(ndx).getDefectListController().isTheSameAs(dList))
					return returnResult;
				else
					returnResult++;

		return -1;
	}
	
	public int getOtherSelector(String oN, String oD) {
		if (oN.length() == 0 && oD.length() == 0)
			return -1;
		
		// Try to find the match in the existing data structures
		int returnResult = 0;
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx).getKind() == 4)
				if (effortCategoryList.get(ndx).getName().equals(oN) &&
						effortCategoryList.get(ndx).getDesc().equals(oD))
					return returnResult;
				else
					returnResult++;
			
		// Establish the new Effort Category list from this aList and return its index
		EffortCategory newEC = new EffortCategory(oN,
				oD, 
				4, 
				aNull, 
				pNull, 
				iNull, 
				dNull);
		effortCategoryList.add(newEC);
		numberOfEffortCategories++;

		returnResult = 0;
		for (int ndx = 0; ndx < effortCategoryList.size(); ndx++)
			if (effortCategoryList.get(ndx).getKind() == 4)
				if (effortCategoryList.get(ndx).getName().equals(oN) &&
						effortCategoryList.get(ndx).getDesc().equals(oD))
					return returnResult;
				else
					returnResult++;

		return -1;
	}
	
	public String toString(){
		String result = "EffortCategoryList:";
		int taskSequenceNumber = 1;
		for (EffortCategory t: effortCategoryList)
			result += "\n     " + taskSequenceNumber++ + ". " + t.toString("     ");
		return result;
	}
	
	public String toString(String indent){
		String result = indent + "EffortCategoryList:";
		int effortCategorySequenceNumber = 1;
		for (EffortCategory ec: effortCategoryList)
			result += "\n     " + indent +  effortCategorySequenceNumber++ + ". " + ec.toString(indent + "     ");
		return result;
	}
}
