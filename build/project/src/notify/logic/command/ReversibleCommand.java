package notify.logic.command;

import java.util.Stack;

public abstract class ReversibleCommand extends Command {

	private Stack<ReversibleCommand> history;
	
	public ReversibleCommand(Action commandAction, Stack<ReversibleCommand> history) {
		super(commandAction);
		this.history = history;
	}
	
	protected void pushToStack() {
		history.push(this);
	}
	
	protected ReversibleCommand retrieveLast() {
		return history.pop();
	}
	
	public abstract Result undo();

}
