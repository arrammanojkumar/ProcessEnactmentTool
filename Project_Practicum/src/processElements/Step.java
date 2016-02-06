package processElements;

public class Step {
	
	public String name;
	public String description;
	public ConditionListController preConditionList = new ConditionListController();;
	public ConditionListController  postConditionList = new ConditionListController();;
	public TaskListController taskList = new TaskListController();

	public Step() {
	}
	
	public Step(String n, String d, ConditionListController pre, ConditionListController post, TaskListController t){
		name = n;
		description = d;
		preConditionList = pre;
		postConditionList = post;
		taskList = t;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return description;
	}
	
	public ConditionListController getPreConditionList(){
		return preConditionList;
	}
	
	public ConditionListController getPostConditionList(){
		return postConditionList;
	}
	
	public TaskListController getTaskList(){
		return taskList;
	}
	
	public ConditionListController getPreConditionListCopy(){
		ConditionListController copy = new ConditionListController(preConditionList);
		return copy;
	}
	
	public ConditionListController getPostConditionListCopy(){
		ConditionListController copy = new ConditionListController(postConditionList);
		return copy;
	}

	public TaskListController getTaskListCopy(){
		TaskListController copy = new TaskListController(taskList);
		return copy;
	}

	public String toString(){
		String result = "Step: " + name + "\n   Description: " + description;

		int sequenceNumber = 1;
		result += "\n   PreConditions:";
		for (Condition c: preConditionList.getConditions())
			result += "\n     " + sequenceNumber++ + ": " + c.toString();

		sequenceNumber = 1;
		result += "\n   PostConditions:";
		for (Condition c: postConditionList.getConditions())
			result += "\n     " + sequenceNumber++ + ": " + c.toString();

		sequenceNumber = 1;
		result += "\n   Tasks:";
		for (Task t: taskList.getTasks())
			result += "\n     " + sequenceNumber++ + ": " + t.toString();
		return result;
	}
	
	public String toString(String indent){
		String result = "Step: " + name + "\n"+ indent + "   Description: " + description;

		int sequenceNumber = 1;
		result += "\n" + indent + "   PreConditions:";
		for (Condition c: preConditionList.getConditions())
			result += "\n     " + indent + sequenceNumber++ + ": " + c.toString(indent + "   ");

		sequenceNumber = 1;
		result += "\n" + indent + "   PostConditions:";
		for (Condition c: postConditionList.getConditions())
			result += "\n     " + sequenceNumber++ + ": " + c.toString(indent + "   ");

		sequenceNumber = 1;
		result += "\n" + indent + "   Tasks:";
		for (Task t: taskList.getTasks())
			result += "\n     " + sequenceNumber++ + ": " + t.toString(indent + "   ");
		return result;
	}
}
