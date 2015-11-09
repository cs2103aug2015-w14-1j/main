# A0125471Lreused
###### src/notify/logic/command/Action.java
``` java

/**
 * Author: Chua Si Hao
 * Matric No: A0125471L
 * For CS2103T - Notify
 */

package notify.logic.command;

/**
 * The Action enumeration is used a list of command that is 
 * recognized by the system.
 */
public enum Action {
	
	ADD		    (true, true),
	BACK 		(false, false),
	DELETE 		(true, true),
	EDIT 		(true, true),
	SEARCH 		(false, false),
	MARK 		(true, true), 	
	DISPLAY 	(false, false),	
	UNDO 		(false, true), 
	SET 		(true, true),
	EXIT		(false, false),
	INVALID 	(false, false),
	HELP		(false, false);

	
	// These are the variables required to store states for each action
	public boolean isReversible;
	public boolean isPersistent;
	
	private Action(boolean isReversible, boolean isPersistent) {
		
		this.isReversible = isReversible;
		this.isPersistent = isPersistent;
	
	}
	
	/**
	 * Returns true if the selected command action can be reversed.
	 * 
	 * @return	true if the selected command action can be reversed
	 * 
	 */
	public boolean isReversible() {
	
		return this.isReversible;
	
	}
	
	/**
	 * Returns true if the selected command requires data to be stored.
	 * 
	 * @return	true if the selected command requires data to be stored
	 * 
	 */
	public boolean isPersistent() {
	
		return this.isPersistent;
	
	}
}
```
###### src/notify/logic/command/Command.java
``` java

/**
 * Author: Chua Si Hao
 * Matric No: A0125471L
 * For CS2103T - Notify
 */

package notify.logic.command;

/**
 * The Command abstraction is used as the basis for commands
 * supported by the system. 
 */
public abstract class Command {

	// Stores the selected action that the command is classified under
	private Action commandAction;
	
	public Command(Action commandAction) {
		
		this.commandAction = commandAction;
	
	}
	
	/**
	 * Commands have to implement their corresponding execute method
	 * The execute method should contain the logic to retrieve 
	 * and handle the indicated command 
	 */
	public abstract Result execute();
	
	/**
	 * Returns the type of persistence of the commandAction
	 * 
	 * @return	true if the commandAction indicated require records
	 * 				in storage to be updated
	 * 
	 */
	public boolean isPersistable() {
		
		return this.commandAction.isPersistent();
	
	}
}
```
###### src/notify/logic/command/Result.java
``` java

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
```
