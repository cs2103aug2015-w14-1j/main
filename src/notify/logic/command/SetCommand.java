package notify.logic.command;

import java.util.ArrayList;

import notify.Task;
import notify.storage.Storage;

public class SetCommand extends Command {

	private Storage storage;
	private String newFilePath;
	
	public SetCommand(Action commandAction, Storage storage){
		super(Action.SET);
		this.storage = storage;
	}
	
	public void addValues(String newFilePath){
		this.newFilePath = newFilePath;
	} 
	
	@Override
	public Result execute() {
		Result result = null;
		
		if(storage.setFilePath(this.newFilePath)) {
			result = new Result(Action.SET, new ArrayList<Task>(), true);
		} else {
			result = new Result(Action.SET, new ArrayList<Task>(), false);
		}
		
		return result;
	}

}
