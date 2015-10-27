package notify.logic.command;


import java.util.ArrayList;

import notify.Task;
import notify.logic.TaskManager;

public class SearchCommand extends Command {

	
	private TaskManager manager;
	private String keyword;
	
	public SearchCommand(Action commandAction, TaskManager manager){
		super(Action.SEARCH);
		this.manager = manager;
		
	}
	
	public void addValues(String keyword){
		this.keyword = keyword;
	}
	
	@Override
	public Result execute() {
		ArrayList<Task> searchTasks = manager.searchTask(keyword);
		
		Result result = new Result(Action.SEARCH, searchTasks);
		
		return result;
	}

}
