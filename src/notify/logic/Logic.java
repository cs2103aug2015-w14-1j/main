package notify.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

import notify.Task;
import notify.TaskType;
import notify.logic.command.Command;
import notify.logic.command.Result;
import notify.logic.parser.CommandParser;
import notify.storage.Storage;
import notify.logic.command.ReversibleCommand;

public class Logic {
	
	private ArrayList<Task> tasks;
	private CommandParser parser;
	private Storage storage;
	private TaskManager taskManager;
	private Stack<ReversibleCommand> history;
	
	public Logic() {
		
		this.tasks = new ArrayList<Task>();
		this.storage = new Storage();
		this.history = new Stack<ReversibleCommand>();
		this.taskManager = new TaskManager(storage);
		this.parser = new CommandParser(storage, taskManager, history);
		
	}
	
	public Storage getStorage() {
		return storage;
	}
	
	public TaskManager getTaskManager() {
		return taskManager;
	}
	
	/**
	 * Process the input entered by the user.
	 * @param input input entered by the user.
	 */
	public void processCommand(String input) {
		
		Command command = parser.parse(input);
		Result result = command.execute();

		System.out.println(result.getFirstResult().getTaskName());
	}
	
	public ArrayList<Task> getComingSoonTasks() {
		
		return taskManager.getComingSoonTasks();
		
	}
	
	public ArrayList<Task> getOverdueTasks() {
		
		return taskManager.getOverdueTasks();
		
	}
	
	public ArrayList<Task> getFloatingTasks() {
		
		return taskManager.getTask(TaskType.FLOATING, false);
		
	}

}
