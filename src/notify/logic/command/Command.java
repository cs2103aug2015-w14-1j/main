package notify.logic.command;

public abstract class Command {

	private Action commandAction;
	
	public Command(Action commandAction) {
		this.commandAction = commandAction;
	}
	
	public abstract Result execute();
	
	public boolean isPersistable() {
		return this.commandAction.isPersistent();
	}
}
