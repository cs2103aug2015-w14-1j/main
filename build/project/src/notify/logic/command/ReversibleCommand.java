//@@author A0125471L

/**
 * Author: Chua Si Hao
 * Matric No: A0125471L
 * For CS2103T - Notify
 */

package notify.logic.command;

import java.util.Stack;

/**
 * The ReversibleCommand abstraction is used as the basis for commands
 * that provides the ability to have Action reversed after execution
 */
public abstract class ReversibleCommand extends Command {

	// Stores a list of actions that the user have entered
	private Stack<ReversibleCommand> historyStack;
	
	public ReversibleCommand(Action commandAction, Stack<ReversibleCommand> historyStack) {
	
		super(commandAction);
		this.historyStack = historyStack;
	
	}
	
	/**
	 * Pushes the current Command into the stack to prepare for undo
	 * Classes that are reversible should call this method once execute function is called
	 */
	protected void pushToStack() {
	
		historyStack.push(this);
	
	}
	
	/**
	 * Returns the command from the top of the stack
	 * This command is the immediate command that will be reverted
	 */
	protected ReversibleCommand retrieveLast() {
	
		return historyStack.pop();
	
	}
	
	/**
	 * Commands have to implement their corresponding undo method
	 * The undo method should contain the logic to revert the requested action
	 */
	public abstract Result undo();

}
