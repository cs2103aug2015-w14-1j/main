//@@author A0130319R

package notify.logic.command;

import java.util.ArrayList;
import java.util.Stack;
import notify.DateRange;
import notify.Task;
import notify.TaskType;
import notify.logic.TaskManager;

/**
 * The AddCommand class extends the abstract class ReversibleCommand.
 * 
 * The AddCommand class is responsible for creating the Result object, corresponding
 * to the ADD command.
 * 
 * This class contains the undo and execute method called by Logic.
 *
 * @author sadhikabilla
 *
 */

public class AddCommand extends ReversibleCommand {
	
	//These are variables that are required to store the fields of each task 
	private Task task;
    private String taskName;
	private TaskType taskType;
	private DateRange dateRange;
	private String category;
	private TaskManager manager;
	
	public AddCommand(Action commandAction, TaskManager manager, Stack<ReversibleCommand> historyStack){
		
		super(commandAction, historyStack);
		this.manager = manager;
	}

	public void addValues(String taskName, TaskType taskType, DateRange dateRange, String category) {
		
		this.taskName = taskName.trim();
		this.taskType = taskType;
		this.dateRange = dateRange;
		this.category = category;
	}
	
	/**
     * This method is responsible for creating the Result object corresponding to the ADD action.
     * It also pushes the particular action onto a historyStack, to allow it to be undone in future. 
     * 
     * This method gets the Task object to be added by calling the addTask method
     * of TaskManger. It then adds the Task to an ArrayList to create the Result object which is used by
     * the Logic class.  
     * 
     * @return 'result' object corresponding to the ADD action.
     */

	
    @Override
	public Result execute(){
		
	    Task addTask = manager.addTask(taskName, dateRange, category, taskType);
		ArrayList<Task> list = new ArrayList<Task>();
		list.add(addTask);
		Result result = new Result(Action.ADD, list);
		this.task = addTask;
		pushToStack();
		return result;
	}


    /**
	 * This method is responsible for reverting the ADD action (i.e delete the task).
	 * 
	 * This method creates the Task object to be deleted by calling the deleteTask method of TaskManager.
	 * It then adds the task to an ArrayList to create the Result object.
	 * 
	 * This method is called by the UndoCommand class {@see UndoCommand#execute()}
	 * 
	 * @return 'result' object corresponding to the UNDO action.  
	 */
	
	@Override
	public Result undo(){
		
		assert task.getTaskId() != Constants.UNASSIGNED_TASK: "Task id cannot be unassigned";
		
		Task temptask = manager.deleteTask(task.getTaskId()); 
		ArrayList<Task> list = new ArrayList<Task>();
		list.add(temptask);
		Result result = new Result(Action.UNDO, list);
		return result;
	}
	
	
	//methods to retrieve the details of Task
    public String getTaskName() {
		
		return this.taskName;
	}
	
	public TaskType getTaskType() { 
		
		return this.taskType;
	}
	
	public String getCategory() {
		
		return this.category;
	}
	
	public DateRange getDateRange() {
		
		return this.dateRange;
	}
	
	
	
}
