//@@author A0130319R

package notify.logic.command;

import java.util.ArrayList;

import notify.Task;
import notify.storage.Storage;

/**
 * The SetCommand class extends the abstract class Command.
 * 
 * The SetCommand class is responsible for creating the Result object, corresponding
 * to the SET action.
 * 
 * This class contains the undo and execute method called by Logic.
 *
 * @author sadhikabilla
 *
 */

public class SetCommand extends Command {

	//These are variables that are required to store the fields of each task 
	private Storage storage;
	private String newFilePath;
	
	
	public SetCommand(Action commandAction, Storage storage){
		super(Action.SET);
		this.storage = storage;
	}
	
	/**
	 * This method assigns the Task fields
	 * 
	 * @param newFilePath
	 */
	public void addValues(String newFilePath){
		this.newFilePath = newFilePath;
	}
	
	/**
	 * This method is responsible for creating the Result object corresponding to the SET action.
	 *  
	 * 
	 * @return 'result' object corresponding to the SET action. 
	 */
	@Override
	public Result execute(){
		
		Result result = null;
		if(storage.setFileDestination(this.newFilePath)){
			
			result = new Result(Action.SET, new ArrayList<Task>(), true);
		}
		else{
			
			result = new Result(Action.SET, new ArrayList<Task>(), false);
		}
		
		return result;
	}
	
	
}
