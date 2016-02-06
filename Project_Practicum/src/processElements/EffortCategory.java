package processElements;

public class EffortCategory {

	private String name;
	private String description;
	private int kind;
	private ArtifactListController aList;
	private PlanListController pList;
	private InterruptionListController iList;
	private DefectListController dList;
	
	private final ArtifactListController aNull = new ArtifactListController();
	private final PlanListController pNull = new PlanListController();
	private final InterruptionListController iNull = new InterruptionListController();
	private final DefectListController dNull = new DefectListController();

	public EffortCategory() {
		name = "";
		description = "";
		kind = 0;
		aList = aNull;
		pList = pNull;
		iList = iNull;
		dList = dNull;
	}
	
	public EffortCategory(String n, String d, int k, ArtifactListController aL, 
			PlanListController pL, InterruptionListController iL, DefectListController dL) {
		name = n;
		description = d;
		kind = k;
		switch (k){
		case 0:
			aList = new ArtifactListController(aL);
			pList = pNull;
			iList = iNull;
			dList = dNull;
			break;
		case 1:
			aList = aNull;
			pList = new PlanListController(pL);
			iList = iNull;
			dList = dNull;
			break;
		case 2:
			aList = aNull;
			pList = pNull;
			iList = new InterruptionListController(iL);
			dList = dNull;
			break;
		case 3:
			aList = aNull;
			pList = pNull;
			iList = iNull;
			dList = new DefectListController(dL);
			break;
		default:
			aList = aNull;
			pList = pNull;
			iList = iNull;
			dList = dNull;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return description;
	}
	
	public int getKind() {
		return kind;
	}
	
	public ArtifactListController getArtifactListController() {
		return aList;
	}
	
	public PlanListController getPlanListController() {
		return pList;
	}
	
	public InterruptionListController getInterruptionListController() {
		return iList;
	}
	
	public DefectListController getDefectListController() {
		return dList;
	}

	public String getKindString() {
		if (kind == 0) return "Artifact";
		else if (kind == 1) return "Plan";
		else if (kind == 2) return "Interruption";
		else if (kind == 3) return "Defect";
		else return "Other";
	}
	
	public void replaceName(String n) {
		name = n;
	}
	
	public void replaceDesc(String d) {
		description = d;
	}
	
	public String toString() {
		String result = "EffortCategory:\n    Name: "+ name;
		switch (kind){
		case 0:
			result += " (Artifact)\n    Description: " + description + 
						"\n" + aList.toString();
			break;
		case 1:
			result += " (Plan)\n    Description: " + description + 
			"\n" + pList.toString("   ");
			break;
		case 2:
			result += " (Interruption)\n    Description: " + description + 
			"\n" +iList.toString("   ");
			break;
		case 3:
			result += " (Defect)\n    Description: " + description + 
			"\n" + dList.toString("   ");
			break;
		default:
			result += " (Other)\n    Description: " + description + 
			"\n" + dList.toString("   ");
		}
		return result;
	}
	
	public String toString(String indent) {
		String result = "EffortCategory:\n" + indent + "    Name: "+ name;
		switch (kind){
		case 0:
			result += " (Artifact)\n" + indent + "    Description: " + description + 
						"\n" + aList.toString(indent + "    ");
			break;
		case 1:
			result += " (Plan)\n" + indent + "    Description: " + description + 
						"\n" + pList.toString(indent + "    ");
			break;
		case 2:
			result += " (Interruption)\n" + indent + "    Description: " + description + 
						"\n" + iList.toString(indent + "    ");
			break;
		case 3:
			result += " (Defect)\n" + indent + "    Description: " + description + 
						"\n" + dList.toString(indent + "    ");
			break;
		default:
			result += " (Other)\n" + indent + "    Description: " + description + 
						"\n" + dList.toString(indent + "    ");
		}
		return result;
	}
}
