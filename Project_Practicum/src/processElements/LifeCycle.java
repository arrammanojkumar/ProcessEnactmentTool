package processElements;
public class LifeCycle {
	
	private String name;
	private String description;
	private LifeCycleListController subLifeCycleList = new LifeCycleListController();
	private ConditionListController lifeCycleIterConditionList  = new ConditionListController();
	private boolean lglLifeCycleComposedOfSteps = true;
	private int nxtLifeCycleIterMode = 0;
	
	
	private LifeCycleStepListController lifecyclesteplist = new LifeCycleStepListController();
	
	public LifeCycle() {
	}
	
	
	public LifeCycle(String n,String d,LifeCycleListController slc,ConditionListController clc, 
			boolean lgl, int ndx,LifeCycleStepListController lcslc)
	{
		name=n;
		description=d;
		subLifeCycleList = slc;
		lifeCycleIterConditionList=clc;
		lglLifeCycleComposedOfSteps = lgl;
		nxtLifeCycleIterMode = ndx;
		lifecyclesteplist=lcslc;
	}
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return description;
	}
	
	public LifeCycleStepListController getSubLifeCycleStepList(){
		return lifecyclesteplist;
	}
	
	public LifeCycleStepListController getLifeCycleStepList(){
		//return null;
		return lifecyclesteplist;
	}
	public LifeCycleListController getSubLifeCycleList(){
		return subLifeCycleList;
	}
	public ConditionListController getLifeCycleItersList(){
		return lifeCycleIterConditionList;
	}
	
	public boolean getLifeCycleComposedOfSteps(){
		return lglLifeCycleComposedOfSteps;
	}
	
	public int getLifeCycleIterMode(){
		return nxtLifeCycleIterMode;
	}
	
	
	public LifeCycleListController getSubLifeCycleListCopy(){
		LifeCycleListController copy = new LifeCycleListController(subLifeCycleList);
		return copy;
	}
	
	public LifeCycleStepListController getLifeCycleStepListCopy(){
		LifeCycleStepListController copy = new LifeCycleStepListController(lifecyclesteplist);
		return copy;
	}
	
	public ConditionListController getLifeCycleItersListCopy(){
		ConditionListController copy = new ConditionListController(lifeCycleIterConditionList);
		return copy;
	}

	public String toString(){
		int sequenceNumber;
		
		String result = "Life Cycle: " + name + "\n        Description: " + description;
		/**
		 * result+ = "\n        " + lifeCycleStepList.toString();
		 */
		
		result += "\n        " + lifecyclesteplist.toString();

		if (lglLifeCycleComposedOfSteps)
			result += "\n   This life cycle is composed of Life Cycle Steps";
		else
			result += "\n   This life cycle is composed of Subordinate Life Cycles";
		
		if (lglLifeCycleComposedOfSteps) {
			sequenceNumber = 1;
			result += "\n      LifeCycle Steps:";
			
		}
		else {
			sequenceNumber = 1;
			result += "\n      Subordinate LifeCycles:";
			for (LifeCycle lc: subLifeCycleList.getLifeCycleList())
				result += "\n     " + sequenceNumber++ + ": " + lc.toString();
		}
		
		if (nxtLifeCycleIterMode == 0) 
			result += "\n     This life cycle is performed just once.";
		else if (nxtLifeCycleIterMode == 1)
			result += "\n     This life cycle is performed n times, where n is specified at instantiation time.";
		else {
			result += "\n     This life cycle is iterates until the following conditions are satisfied.";
			sequenceNumber = 1;
			result += "\n      Life Cycle Iteration Conditions:";
			for (Condition c: lifeCycleIterConditionList.getConditions())
				result += "\n     " + sequenceNumber++ + ": " + c.toString();
		}

		return result;
	}

	public String toString(String indent){
		int sequenceNumber;
		
		String result = "Life Cycle: " + name + "\n   " + indent + "     Description: " + description;
		/**
		 * result += "\n " + indent + lifecyclesteplistController.toString(indent + "    ");
		 */
		
		
		result += "\n " + indent + lifecyclesteplist.toString(indent + "    ");
		

		 
		if (lglLifeCycleComposedOfSteps)
			result += "\n        " + indent + "This life cycle is composed of Life Cycle Steps";
		else
			result += "\n        " + indent + "This life cycle is composed of Subordinate Life Cycles";
		
		if (lglLifeCycleComposedOfSteps) {
			sequenceNumber = 1;
			result += "\n        " + indent + "LifeCycle Steps:";
		
		}
		else {
			sequenceNumber = 1;
			result += "\n        " + indent + "Subordinate LifeCycles:";
			for (LifeCycle lc: subLifeCycleList.getLifeCycleList())
				result += "\n        " + indent + sequenceNumber++ + ": " + lc.toString();
		}
		
		if (nxtLifeCycleIterMode == 0) 
			result += "\n        " + indent + "This life cycle is performed just once.";
		else if (nxtLifeCycleIterMode == 1)
			result += "\n        " + indent + "This life cycle is performed n times, where n is specified at instantiation time.";
		else {
			result += "\n        " + indent + "This life cycle is iterates until the following conditions are satisfied.";
			sequenceNumber = 1;
			result += "\n     " + indent + "Life Cycle Iteration Conditions:";
			for (Condition c: lifeCycleIterConditionList.getConditions())
				result += "\n        " + indent + sequenceNumber++ + ": " + c.toString();
		}

		return result;
	}
	

}
