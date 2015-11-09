//@@author A0130319R

package notify.logic.command;


import java.util.ArrayList;
import java.util.Stack;
import notify.Task;
import notify.logic.TaskManager;

/**
 * The SearchCommand class extends the abstract class ReversibleCommand.
 * 
 * The SearchCommand class is responsible for creating the Result object, corresponding
 * to the SEARCH command.
 * 
 * This class contains the execute method called by Logic.
 *
 * @author sadhikabilla
 *
 */


public class SearchCommand extends Command {
	
	//These are variables that are required to store the fields of each task 
	private TaskManager manager;
	private String keyword;
	
	public SearchCommand(Action commandAction, TaskManager manager){
		super(Action.SEARCH);
		this.manager = manager;
		
	}
	
	/**
	 * This method assigns the Task fields
	 * 
	 * @param keyword
	 */
	public void addValues(String keyword){
		this.keyword = keyword;
	}
	
	/**
	 * This method is responsible for creating the Result object corresponding to the SEARCH action.
	 * The result object is used by the Logic class. 
	 * 
	 * It gets the list of all the tasks that match the specified keyword using the searchTask method
	 * of TaskManager class. It creates the result based on this list. 
	 * 
	 * @return 'result' object corresponding to the SEARCH action. 
	 */
	
	@Override
	public Result execute() {
		
		ArrayList<Task> searchTasks = manager.searchTask(keyword);
		Result result = new Result(Action.SEARCH, searchTasks);
		return result;
	}

}
