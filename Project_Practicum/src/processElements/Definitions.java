package processElements;

public class Definitions {

	private ArtifactListController artifacts = null;
	private ConditionListController coniditons = null;
	private DefectListController defects = null;
	private EffortCategoryListController effortCategories = null;
	private InterruptionListController interruptions = null;
	private LifeCycleListController lifeCycles = null;
	private PlanListController plans = null;
	private StepListController steps = null;
	private TaskListController tasks = null;
	private LifeCycleStepListController lifecyclesteps=null; 
	public Definitions(
			ArtifactListController artifacts,
			ConditionListController coniditons,
			DefectListController defects,
			EffortCategoryListController effortCategories,
			InterruptionListController interruptions,
			LifeCycleListController lifeCycles,
			PlanListController plans,
			StepListController steps,
			TaskListController tasks,
			LifeCycleStepListController lifecyclesteps
			){
		this.artifacts = artifacts;
		this.coniditons = coniditons;
		this.defects = defects;
		this.effortCategories = effortCategories;
		this.interruptions = interruptions;
		this.lifeCycles = lifeCycles;
		this.plans = plans;
		this.steps = steps;
		this.tasks = tasks;
		this.lifecyclesteps=lifecyclesteps;
	}
	
	public ArtifactListController getArtifactListController(){
		return artifacts;
	}
	
	public ConditionListController getConditionListController(){
		return coniditons;
	}
	
	public DefectListController getDefectListController(){
		return defects;
	}
	
	public EffortCategoryListController getEffortCategoryListController(){
		return effortCategories;
	}
	
	public InterruptionListController getInterruptionListController(){
		return interruptions;
	}
	
	public LifeCycleListController getLifeCycleListController(){
		return lifeCycles;
	}
	
	public PlanListController getPlanListController(){
		return plans;
	}
	
	public StepListController getStepListController(){
		return steps;
	}
	
	public TaskListController getTaskListController(){
		return tasks;
	}
	public LifeCycleStepListController getLifeCycleStepListController()
	{
		return lifecyclesteps;
	}
}
