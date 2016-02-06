package processElements;

import java.util.ArrayList;
import java.util.List;

public class LifeCycleStepListController {
	private LifeCycleStepListController root = null;
	private List<LifeCycleStep> lifeCycleStepList = new ArrayList<>();
	private int numberOfLifeCycleSteps = 0;
	//private String oD;
	
	
	public LifeCycleStepListController()
	{
		
	}
	public LifeCycleStepListController(List<LifeCycleStep> x)
	{
		lifeCycleStepList=x;
	}
	
	public LifeCycleStepListController(StepListController that)
	{
		for(int i=0;i<that.getNumberOfSteps();i++)
		{
			ArtifactListController alc=new ArtifactListController();
			
			PlanListController plc = new PlanListController();
			InterruptionListController ilc = new InterruptionListController();
			DefectListController dlc = new DefectListController();
			String oN="";
			String oD = "";
			LifeCycleStep temp=new LifeCycleStep(0,that.getStep(i), alc, plc, ilc, dlc, oN, oD);
			lifeCycleStepList.add(i,temp);
			numberOfLifeCycleSteps++;
		}
	}
	public LifeCycleStepListController(LifeCycleStepListController that)
	{
		root = that.root;
		if(root == null) root = that;
		numberOfLifeCycleSteps = that.numberOfLifeCycleSteps;
		lifeCycleStepList = that.getLifeCycleStepListCopy();
		
	}
	
	public List<LifeCycleStep> getLifeCycleStepList() {
		return lifeCycleStepList;
	}
	public List<Step> StepList() {
		List<Step> to_return = new ArrayList<Step>();
		for(int i=0;i<lifeCycleStepList.size();i++)
		{
			to_return.add(i, lifeCycleStepList.get(i).getLifeCycleStepList());
		}
		return to_return;
	}
	public int getNumberOfLifeCycleSteps() {
		return lifeCycleStepList.size();
		//return numberOfLifeCycleSteps;
	}

	public LifeCycleStep getLifeCycleStep(int ndx)
	{
		return lifeCycleStepList.get(ndx);
	}
	
	public Step extractLifeCycleStep(int ndx)
	{
		LifeCycleStep result = lifeCycleStepList.get(ndx);
		lifeCycleStepList.remove(ndx);
		result.getLifeCycleStepList();
		return result.getLifeCycleStepList();
	}
	
	public String [] buildSelectList(){
		String [] result = new String[lifeCycleStepList.size()];
		for (int ndx=0; ndx < lifeCycleStepList.size(); ndx++)
			result[ndx] = ((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + lifeCycleStepList.get(ndx).getLifeCycleStepList().getName();//Returns the step name
		return result;
	}
	
	public LifeCycleStepListController buildSubList (LifeCycleStepListController included) {
		List <Step> workingList = new ArrayList<Step>();
		for (int ndx = 0; ndx < lifeCycleStepList.size(); ndx++)
		{
			workingList.add(ndx, lifeCycleStepList.get(ndx).getLifeCycleStepList());
		}
		workingList.removeAll(included.StepList());
		LifeCycleStepListController result = new LifeCycleStepListController();
		result.root = included.root;
		for (int ndx = 0; ndx < workingList.size(); ndx++)
		{
			ArtifactListController alc=new ArtifactListController();
			PlanListController plc =  new PlanListController();
			InterruptionListController ilc = new InterruptionListController();
			DefectListController dlc = new DefectListController();
			String oN="";
			String oD="";
			LifeCycleStep rlc = new LifeCycleStep(0,workingList.get(ndx),alc,plc,ilc,dlc,oN,oD);
			result.addLifeCycleStep(rlc);
		}
		
		return result;
	}
	
	public void addLifeCycleStep(LifeCycleStep lcs)
	{
		lifeCycleStepList.add(lcs);
		numberOfLifeCycleSteps++;
	}
	
	public void addLifeCycleStep(int ndx,LifeCycleStep lcs)
	{
		lifeCycleStepList.add(ndx,lcs);
		numberOfLifeCycleSteps++;
	}
	
	public void addLifeCycleStep(int n,Step slc, ArtifactListController alc, PlanListController plc, InterruptionListController ilc, DefectListController dlc, String oN, String oD)
	{
		lifeCycleStepList.add(new LifeCycleStep(n,slc, alc, plc, ilc, dlc, oN, oD));
		numberOfLifeCycleSteps++;
	}
	
	public void addLifeCycleStep(int ndx,int n,Step slc, ArtifactListController alc, PlanListController plc, InterruptionListController ilc, DefectListController dlc, String oN, String oD)
	{
		lifeCycleStepList.add(ndx,new LifeCycleStep(n,slc, alc, plc, ilc, dlc, oN, oD));
		numberOfLifeCycleSteps++;
	}
	
	public List<LifeCycleStep> getLifeCycleStepListCopy()
	{
		List<LifeCycleStep> copy = new ArrayList<>();
		for(int ndx = 0; ndx < lifeCycleStepList.size(); ndx++)
			copy.add(lifeCycleStepList.get(ndx));
		
		return copy;
	}
	
	
	public void replaceLifeCycleStep(int ndx,int n,Step slc, ArtifactListController alc, PlanListController plc, InterruptionListController ilc, DefectListController dlc, String oN, String oD)
	{
		if(ndx < 0 || ndx > numberOfLifeCycleSteps)
			return;
		lifeCycleStepList.add(ndx,new LifeCycleStep(n,slc, alc, plc, ilc, dlc, oN, oD));
	}
	
	public boolean sameAs(LifeCycleStepListController that) {
		if (this.numberOfLifeCycleSteps != that.numberOfLifeCycleSteps) return false;
		for (int ndx= 0; ndx < this.numberOfLifeCycleSteps; ndx++)
			if (this.lifeCycleStepList.get(ndx) != that.lifeCycleStepList.get(ndx)) return false;
		return true;
	}
	
	public void moveLifeCycleStepUp(int ndx) {
		if (ndx <= 0 || ndx >= numberOfLifeCycleSteps)
			return;
		LifeCycleStep temp = lifeCycleStepList.remove(ndx);
		lifeCycleStepList.add(ndx-1, temp);
	}
	
	public void moveLifeCycleStepDn(int ndx) {
		if (ndx < 0 || ndx >= numberOfLifeCycleSteps)
			return;
		LifeCycleStep temp = lifeCycleStepList.remove(ndx);
		lifeCycleStepList.add(ndx+1, temp);
	}
	
	public void deleteLifeCycleStep(int ndx){
		lifeCycleStepList.remove(ndx);
		numberOfLifeCycleSteps--;
	}
	public boolean findMember(LifeCycleStep t) {
		for (int i=0;i<lifeCycleStepList.size();i++)
		{	
			if (lifeCycleStepList.get(i).getLifeCycleStepList() == t.getLifeCycleStepList())
				return true;
		}
		return false;
	}
	public void validateMembers(StepListController root) {
		for (LifeCycleStep step: lifeCycleStepList)
			if (this.findMember(step))
				continue;
			else
				root.addStep(step.getLifeCycleStepList());
	}

	public String toString() {
		String result = "LifeCycleList:";
		int taskSequenceNumber = 1;
		for (LifeCycleStep lc: lifeCycleStepList)
			result += "\n     " + taskSequenceNumber++ + ": " + lc.toString();
		return result;
	}
	
	public String toString(String indent) {
		String result = indent + "LifeCycleList:";
		int taskSequenceNumber = 1;
		for (LifeCycleStep lc: lifeCycleStepList)
			result += indent + "\n" + indent + taskSequenceNumber++ + ": " + lc.tosString(indent+" ");
			
		return result;
	}
}

