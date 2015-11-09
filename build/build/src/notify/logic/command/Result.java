//@@author A0125471L-reused

/**
 * Author: Chua Si Hao
 * Matric No: A0125471L
 * For CS2103T - Notify
 */

package notify.logic.command;

import java.util.ArrayList;

import notify.Task;

/**
 * The Result class stores the retrieve results to be used by 
 * Command class and Logic class
 */
public class Result {

	// These are variables used for quick access and default values required
	private static final int FIRST_RESULT_INDEX = 0;
	private static final boolean DEFAULT_IS_SUCCESS = true;

	// These are variables used to indicate the status of the results
	private Action actionPerformed;
	private ArrayList<Task> results;
	private boolean isSuccess;

	public Result(Action actionPerformed, ArrayList<Task> results) {
		
		this(actionPerformed, results, DEFAULT_IS_SUCCESS);
	
	}

	public Result(Action actionPerformed, ArrayList<Task> results, boolean isSuccess) {
		
		this.actionPerformed = actionPerformed;
		this.results = results;
		this.isSuccess = isSuccess;
	
	}

	/**
	 * Returns the Action type that is requested for
	 */
	public Action getActionPerformed() {
		
		return actionPerformed;
	
	}

	/**
	 * Returns the first item on the results list for quick access
	 * 
	 * @return Task containing the relevant results stored in within
	 * 			if the results list is empty, null is returned
	 * 
	 */
	public Task getFirstResult() {

		if (results.isEmpty()) {
			
			return null;
		
		}

		return results.get(FIRST_RESULT_INDEX);
	
	}

	/**
	 * Returns the list of results retrieved
	 */
	public ArrayList<Task> getResults() {
		
		return results;
	
	}

	/**
	 * Returns the state of execution of the action performed
	 * 
	 * @return	true if the execution is successful
	 * 
	 */
	public boolean isSuccess() {
		
		return isSuccess;
	
	}
	
}