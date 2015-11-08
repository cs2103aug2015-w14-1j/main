//@@author A0130319R

package notify.logic.command;

import java.util.ArrayList;
import java.util.Stack;
import notify.Task;

/**
 * The UndoCommand class extends the abstract class Command.
 * 
 * The UndoCommand class is responsible for creating the Result object, corresponding
 * to the UNDO action.
 * 
 * This class contains the execute method called by Logic. It calls the undo method in the corresponding
 * classes. {@see AddCommand#undo()} {@see DeleteCommand#undo()} {@see EditCommand#undo()}
 * {@see MarkCommand#undo()}
 *
 * @author sadhikabilla
 *
 */

public class UndoCommand extends Command {

	private Stack<ReversibleCommand> history;
	
	public UndoCommand(Action commandAction, Stack<ReversibleCommand> history){
		
    super(commandAction);
	this.history = history;
	
	}
	
	/**
	 * This method is responsible for creating the Result object corresponding to the UNDO action.
	 * 
	 * When called, it retrives the top most action from the stack and calls the undo() method of the
	 * that class.  
	 */
	
	@Override
	public Result execute() {
		
		if(!history.isEmpty()){
			
			ReversibleCommand command = history.pop();
			Result result = command.undo();
			return result;
		}
		return new Result(Action.UNDO, new ArrayList<Task>());
	}
	

}
