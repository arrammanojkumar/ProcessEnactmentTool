package processElements;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class TaskListController {
	
	private TaskListController root = null;
	private List <Task> taskList = new ArrayList <Task> ();
	private int numberOfTasks = 0;
	
	public TaskListController() {	
	}
	
	public TaskListController(TaskListController that) {
		root = that.root;
		if (root == null) root = that;
		numberOfTasks = that.numberOfTasks;
		taskList = that.getTaskListCopy();
	}
	
	public int getNumberOfTasks() {
		return numberOfTasks;
	}
	
	public boolean isEmpty() {
		return numberOfTasks == 0;
	}

	public boolean isTheSameAs(TaskListController that) {
		if (this.numberOfTasks != that.numberOfTasks) return false;
		for (int ndx= 0; ndx < this.numberOfTasks; ndx++)
			if (this.taskList.get(ndx) != that.taskList.get(ndx)) return false;
		return true;
	}

	
	public Task getTask(int ndx) {
		return taskList.get(ndx);
	}
	
	public List <Task> getTasks() {
		return taskList;
	}
 	
	public List <Task> getTaskListCopy(){
		List <Task> copy = new ArrayList <Task> ();
		for (int ndx = 0; ndx < taskList.size(); ndx++)
			copy.add(taskList.get(ndx));
		return copy;
	}
	
	public Task extractTask(int ndx){
		Task result = taskList.get(ndx);
		taskList.remove(ndx);
		return result;
	}

	
	public String [] buildSelectList(){
		String [] result = new String[numberOfTasks];
		for (int ndx=0; ndx < numberOfTasks; ndx++)
			result[ndx] = ((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + taskList.get(ndx).getName();
		return result;
	}
	
	public TaskListController buildSubList (TaskListController included) {
		List <Task> workingList = new ArrayList<Task>();
		for (int ndx = 0; ndx < taskList.size(); ndx++)
			workingList.add(ndx, taskList.get(ndx));
		workingList.removeAll(included.getTasks());
		TaskListController temp = new TaskListController();
		temp.root = included.root;
		for (int ndx = 0; ndx < workingList.size(); ndx++)
			temp.addTask(workingList.get(ndx));
		return temp;
	}
	
	public ComboBoxModel<String> buildComboBoxModel () {
		DefaultComboBoxModel<String> result = (new DefaultComboBoxModel<String>());
		for (int ndx = 0; ndx < taskList.size(); ndx++)
			result.addElement(((ndx+1) < 10? "0" : "") + (ndx+1) + ". " + taskList.get(ndx).getName());
		return result;
	}

	public void addTask(Task t) {
		taskList.add(t);
		numberOfTasks++;
	}
	
	public void addTask(int ndx, Task t) {
		taskList.add(ndx, t);
		numberOfTasks++;
	}

	public void addTask(String taskName, String taskDescription, ArtifactListController au, ArtifactListController ap){
		taskList.add(new Task(taskName, taskDescription, 
					new ArtifactListController(au), 
					new ArtifactListController(ap)));
		numberOfTasks++;
	}
	
	public void addTask(int ndx, String taskName, String taskDescription, ArtifactListController au, ArtifactListController ap){
		taskList.add(ndx, new Task(taskName, taskDescription, 
				new ArtifactListController(au), 
				new ArtifactListController(ap)));
		numberOfTasks++;
	}
	
	public void replaceTask(int ndx, String taskName, String taskDescription, ArtifactListController au, ArtifactListController ap){
		if (ndx < 0 || ndx >= numberOfTasks)
			return;
		Task t = new Task(taskName, taskDescription, 
				new ArtifactListController(au), 
				new ArtifactListController(ap));
		taskList.set(ndx, t);
	}
	
	public void moveTaskUp(int ndx) {
		if (ndx <= 0 || ndx >= numberOfTasks)
			return;
		Task temp = taskList.remove(ndx);
		taskList.add(ndx-1, temp);
	}
	
	public void moveTaskDn(int ndx) {
		if (ndx < 0 || ndx >= numberOfTasks-1)
			return;
		Task temp = taskList.remove(ndx);
		taskList.add(ndx+1, temp);
	}

	public void deleteTask(int ndx){
		taskList.remove(ndx);
		numberOfTasks--;
	}
	
	private boolean findMember(Task t) {
		for (Task task: taskList)
			if (task == t)
				return true;
		return false;
	}
	
	public void validateMembers(TaskListController root) {
		for (Task task: taskList)
			if (root.findMember(task))
				continue;
			else
				root.addTask(task);
	}

	
	public String toString() {
		String result = "TaskList:";
		int taskSequenceNumber = 1;
		for (Task t: taskList)
			result += "\n     " + taskSequenceNumber++ + ": " + t.toString();
		return result;
	}
	
	public String toString(String indent) {
		String result = indent + "TaskList:";
		int taskSequenceNumber = 1;
		for (Task t: taskList)
			result += indent + "\n" + indent + taskSequenceNumber++ + ": " + t.toString(indent+"   ");
		return result;
	}
}
