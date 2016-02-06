package processElements;

public class LifeCycleStep {
	
	private Step lifeCycleStep = new Step();
	private int default_delivarable=0;
	
	private ArtifactListController artifactEffortList = new ArtifactListController();
	private PlanListController planEffortList = new PlanListController();
	private InterruptionListController interruptionEffortList = new InterruptionListController();
	private DefectListController defectEffortList = new DefectListController();
	private String otherName = "";
	private String otherDesc = "";
	
	public LifeCycleStep()
	{	}
	
	
	public LifeCycleStep(int default_del,Step slc, ArtifactListController alc, PlanListController plc, InterruptionListController ilc, DefectListController dlc, String oN, String oD)
	{
		default_delivarable=default_del;
		lifeCycleStep = slc;
		artifactEffortList = alc;
		planEffortList = plc;
		interruptionEffortList = ilc;
		defectEffortList = dlc;
		otherName = oN;
		otherDesc = oD;
	}

	public Step getLifeCycleStepList() {
		return lifeCycleStep;
	}

	public void setLifeCycleStepList(Step lifeCycleStepList) {
		this.lifeCycleStep = lifeCycleStepList;
	}

	public ArtifactListController getArtifactEffortList() {
		return artifactEffortList;
	}

	public void setArtifactEffortList(ArtifactListController artifactEffortList) {
		this.artifactEffortList = artifactEffortList;
	}

	public PlanListController getPlanEffortList() {
		return planEffortList;
	}

	public void setPlanEffortList(PlanListController planEffortList) {
		this.planEffortList = planEffortList;
	}

	public InterruptionListController getInterruptionEffortList() {
		return interruptionEffortList;
	}

	public void setInterruptionEffortList(
			InterruptionListController interruptionEffortList) {
		this.interruptionEffortList = interruptionEffortList;
	}

	public DefectListController getDefectEffortList() {
		return defectEffortList;
	}

	public void setDefectEffortList(DefectListController defectEffortList) {
		this.defectEffortList = defectEffortList;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getOtherDesc() {
		return otherDesc;
	}

	public void setOtherDesc(String otherDesc) {
		this.otherDesc = otherDesc;
	}
	public int getDefaultDel(){
		return default_delivarable;
	}
	public void setDefaultDel(int n){
		this.default_delivarable=n;
	}

	@Override
	public String toString() {
		String result = "Step Name :"+lifeCycleStep.getName()+" Description : "+lifeCycleStep.getDesc();
		result+="default delivarable :"+Integer.toString(default_delivarable);
		if(artifactEffortList!=null)
				result +=" Artifact List Controller "+artifactEffortList;
		if(planEffortList!=null)
				result += " Plan List Controller "+planEffortList;
		if(interruptionEffortList != null)
				result +=" Interruption List Controller "+interruptionEffortList;
		if(defectEffortList != null)
				result += " Defect List Controller "+defectEffortList;
		if(otherName != "" || otherName != null)		
				result +=" Other Name "+otherName;
		if(otherDesc != "" || otherDesc != null)
				result += " Other Description "+otherDesc;
		
		return result;
	}
	public String tosString(String indent)
	{
		String result = "Step Name :"+lifeCycleStep.getName()+indent+" Description : "+lifeCycleStep.getDesc();
		if(artifactEffortList!=null)
				result +=" Artifact List Controller "+artifactEffortList;
		if(planEffortList!=null)
				result += " Plan List Controller "+planEffortList;
		if(interruptionEffortList != null)
				result +=" Interruption List Controller "+interruptionEffortList;
		if(defectEffortList != null)
				result += " Defect List Controller "+defectEffortList;
		if(otherName != "" || otherName != null)		
				result +=" Other Name "+otherName;
		if(otherDesc != "" || otherDesc != null)
				result += " Other Description "+otherDesc;
		
		return result;
	}

	
}
