/**
 * Author: Sadhika Billa
 * Matric number: A0130319R
 * For CS2103 - Notify
 */
package notify.logic.command;

import java.util.ArrayList;
import java.util.Stack;
import notify.Task;

public class UndoCommand extends Command {

	private Stack<ReversibleCommand> history;
	
	public UndoCommand(Action commandAction, Stack<ReversibleCommand> history){
		
    super(commandAction);
	this.history = history;
	
	}
	
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
