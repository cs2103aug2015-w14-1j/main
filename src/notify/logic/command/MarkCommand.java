//@@author A0130319R

package notify.logic.command;

import java.util.ArrayList;
import java.util.Stack;

import notify.Task;
import notify.logic.TaskManager;

/**
 * The MarkCommand class extends the abstract class ReversibleCommand.
 * 
 * The MarkCommand class is responsible for creating the Result object, corresponding
 * to the MARK command.
 * 
 * This class contains the undo and execute method called by Logic.
 *
 * @author sadhikabilla
 *
 */

public class MarkCommand extends ReversibleCommand {
	
	//These are variables that are required to store the fields of each task 
	private TaskManager manager;
	private Task task;
	private int id;
	
	public MarkCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager manager){
		
		super(commandAction, historyStack);
		this.manager = manager;
	}
	
	public void addValues(int id){
		
		this.id = id;
	}
	
	/**
     * This method is responsible for creating the Result object corresponding to the MARK action.
     * It also pushes the particular action onto a historyStack, to allow it to be undone in future. 
     * 
     * This method gets the Task object to be marked as completed by calling the markTask method
     * of TaskManger and setting the parameter to 'true'. 
     * It then adds the Task to an ArrayList to create the Result object which is used by
     * the Logic class.  
     * 
     * @return 'result' object corresponding to the MARK action.
     */
	
	@Override
	public Result execute() {
		assert id != Constants.UNASSIGNED_TASK: "Task id cannot be unassigned";
		
		Task markTask = manager.markTask(id, true);
		ArrayList<Task> list = new ArrayList<Task>();
		list.add(markTask);
		Result result = new Result(Action.MARK, list);
		pushToStack();
		return result;
	}

	/**
	 * This method is responsible for reverting the MARK action (i.e unmark the task).
	 * 
	 * This method gets the Task object to be un-marked by calling the markTask method of TaskManager and setting
	 * the parameter to 'false'.
	 * It then adds the task to an ArrayList to create the Result object.
	 * 
	 * This method is called by the UndoCommand class {@see UndoCommand#execute()}
	 * 
	 * @return 'result' object corresponding to the UNDO action.  
	 */
	
	@Override
	public Result undo() {
		
		assert id != Constants.UNASSIGNED_TASK: "Task id cannot be unassigned";
		
		Task tempTask = manager.markTask(id, false);
		ArrayList<Task> list = new ArrayList<Task>();
		list.add(tempTask);
		Result result = new Result(Action.UNDO, list);
		return result;
	}
	

	public int getId(){
		
		return this.id;
	}

}
