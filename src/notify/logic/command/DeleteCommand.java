/**
 * Author: Sadhika Billa
 * Matric number: A0130319R
 * For CS2103 - Notify
 */

package notify.logic.command;

import java.util.ArrayList;
import java.util.Stack;
import notify.Task;
import notify.logic.TaskManager;


public class DeleteCommand extends ReversibleCommand {

	private Task task;
	private int id;
	private TaskManager manager;

	public DeleteCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager manager) {
		
		super(commandAction, historyStack);
		this.manager = manager;

	}

	public void addValues(int id) {
		
		this.id = id;
	}

	public int getId() {
		
		return this.id;
	}

	@Override
	public Result execute() {
		
		assert id != Constants.UNASSIGNED_TASK: "Task id cannot be unassigned";
		
		Task temptask = manager.deleteTask(id);
		ArrayList<Task> listOfResults = new ArrayList<Task>();
		listOfResults.add(temptask);
		Result result = new Result(Action.DELETE, listOfResults);
		this.task = temptask;
		pushToStack();
		return result;
	}

	@Override
	public Result undo() {
		
		assert id != Constants.UNASSIGNED_TASK: "Task id cannot be unassigned";
		
		Task temptask = manager.undeleteTask(id);
		ArrayList<Task> listOfResults = new ArrayList<Task>();
		listOfResults.add(temptask);
		Result result = new Result(Action.UNDO, listOfResults);
		this.task = temptask;
		return result;
	}

}
