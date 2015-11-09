//@@author A0125471L-reused

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
