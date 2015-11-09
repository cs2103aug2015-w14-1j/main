//@@author A0130319R

package notify.logic.command;

import java.util.ArrayList;
import java.util.Stack;

import notify.Task;
import notify.logic.TaskManager;

/**
 * The DeleteCommand class extends the abstract class ReversibleCommand.
 * 
 * The DeleteCommand class is responsible for creating the Result object, corresponding
 * to the DELETE command.
 * 
 * This class contains the undo and execute method called by Logic.
 *
 * @author sadhikabilla
 *
 */
public class DeleteCommand extends ReversibleCommand {
	
	//These are variables that are required to store the fields of each task 
	private Task task;
	private int id;
	private TaskManager manager;

	public DeleteCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager manager) {
		
		super(commandAction, historyStack);
		this.manager = manager;

	}
	
	/**
	 * This method assigns the Task fields
	 * 
	 * @param id
	 */
	public void addValues(int id) {
		
		this.id = id;
		
	}

	/**
     * This method is responsible for creating the Result object corresponding to the DELETE action.
     * It also pushes the particular action onto a historyStack, to allow it to be undone in future. 
     * 
     * This method gets the Task object to be deleted by calling the deleteTask method
     * of TaskManger. It then adds the Task to an ArrayList to create the Result object which is used by
     * the Logic class. 
     * 
     * If the Task object is null, the method creates a result object by setting the boolean 
     * flag to false to indicated that it has not been successfully deleted.
     * 
     * @return 'result' object corresponding to the DELETE action.
     */
	 @Override
	public Result execute() {
		
		assert id != Constants.UNASSIGNED_TASK: Constants.ERROR_TASK_ID_UNASSIGNED;
		 
		Result result = null;

		ArrayList<Task> list = new ArrayList<Task>();
		this.task = manager.deleteTask(id);
			
		if (this.task != null) {
			
			list.add(task);
			result = new Result(Action.DELETE, list, true);
			pushToStack();
			
		}
			
		else {
				
			 result = new Result(Action.DELETE, list, false);
		
		}
		
		return result;
		
	}
    
    /**
	 * This method is responsible for reverting the DELETE action (i.e add the task).
	 * 
	 * This method gets the Task object to be added by calling the undeleteTask method of TaskManager.
	 * It then adds the task to an ArrayList to create the Result object.
	 * 
	 * This method is called by the UndoCommand class {@see UndoCommand#execute()}
	 * 
	 * @return 'result' object corresponding to the UNDO action.  
	 */
	@Override
	public Result undo() {
		
		assert id != Constants.UNASSIGNED_TASK: Constants.ERROR_TASK_ID_UNASSIGNED;
		
		this.task = manager.undeleteTask(id);
		
		ArrayList<Task> list = new ArrayList<Task>();
		list.add(task);
		
		Result result = new Result(Action.UNDO, list);
		
		return result;
	
	}

	/* This method get the fields of the task */
	public int getId() {
		
		return this.id;
		
	}

}
