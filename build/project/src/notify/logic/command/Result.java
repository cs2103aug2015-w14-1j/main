package notify.logic.command;

import java.util.ArrayList;

import notify.Task;

public class Result {

	private static final int FIRST_RESULT_INDEX = 0;
	private static final boolean DEFAULT_IS_SUCCESS = true;

	private Action actionPerformed;
	private ArrayList<Task> results;
	private boolean isSuccess;

	public Result(Action actionPerformed, ArrayList<Task> results) {
		this(actionPerformed, results, DEFAULT_IS_SUCCESS);
	}

	public Result(Action actionPerformed, ArrayList<Task> results,
			boolean isSuccess) {
		this.actionPerformed = actionPerformed;
		this.results = results;
		this.isSuccess = isSuccess;
	}

	public Action getActionPerformed() {
		return actionPerformed;
	}

	
	/**
	 * Returns the first task in the ArrayList of results.
	 * 
	 * @return	task at index 0 in the ArrayList of results
	 */
	
	public Task getFirstResult() {

		if (results.isEmpty()) {
			return null;
		}

		return results.get(FIRST_RESULT_INDEX);
	}

	/**
	 * Returns the ArrayList of tasks.
	 * 
	 * @return	ArrayList of tasks
	 */
	
	public ArrayList<Task> getResults() {
		return results;
	}

	public boolean isSuccess() {
		return isSuccess;
	}
}