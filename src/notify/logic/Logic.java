package notify.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

import notify.Task;
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
	
	
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	
	public ArrayList<Task> getTasksOn(Calendar date) {
		
		return taskManager.getTask(date);
		
		//return getTasksBefore(date);
	}
	
	public ArrayList<Task> getTasksBefore(Calendar date) {
		
		return taskManager.beforeDate(date);
		
		/*ArrayList<Task> tasks = new ArrayList<Task>();
		
		String[] taskNames = { "IT Cell Meeting", "User Guide Submission", "Developer Guide Submission", "CS2101 Reflection" };
		int[] years = { 2015, 2015, 2015, 2015 };
		int[] months = { 10, 10, 10, 10 };
		int[] days = { 8, 14, 14, 16 };

		for(int i = 0; i < taskNames.length; i++) {
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(years[i], months[i], days[i]);
			
			Task task = new Task();
			task.setTaskId(i);
			task.setTaskName(taskNames[i]);
			task.setTaskType(Task.TaskType.DAY);
			task.setToDate(calendar.getTime());
			
			tasks.add(task);
			
		}
		
		System.out.println("ASD");
		return tasks;*/
		
	}
	
	public ArrayList<Task> getTasksAfter(Calendar date) {
		
		return taskManager.afterDate(date);
		
		//return getTasksBefore(date);
	}
	
	public ArrayList<Task> getFloatingTasks() {
		
		return taskManager.getFloatingTask();
		
		/*ArrayList<Task> tasks = new ArrayList<Task>();
		
		String[] taskNames = { "IT Cell Meeting", "User Guide Submission", "Developer Guide Submission", "CS2101 Reflection" };

		for(int i = 0; i < taskNames.length; i++) {
			
			Task task = new Task();
			task.setTaskId(i);
			task.setTaskName(taskNames[i]);
			task.setTaskType(Task.TaskType.FLOATING);
			
			tasks.add(task);
			
		}
		
		return tasks;*/
	}

}
