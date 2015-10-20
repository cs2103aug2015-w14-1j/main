package notify.logic;

import java.util.ArrayList;
import java.util.Calendar;

import notify.Task;
import notify.logic.command.Command;

public class Logic {
	
	private ArrayList<Task> tasks;
	private Parser parser;
	//private Storage storage;
	
	public Logic() {
		this.tasks = new ArrayList<Task>();
		this.parser = new Parser();
		//this.storage = new Storage();
	}
	
	/**
	 * Process the input entered by the user.
	 * @param input input entered by the user.
	 */
	public void processCommand(String input) {
		parser.parse(input);
		Command command = parser.getCommand();
		
		switch(command) {
			case CREATE:
				create();
				break;
		}
	}
	
	
	public Command create() {
		Task task = parser.getTask();
		//storage.addTask(task, "NON-CATEGORIZED");
		tasks.add(task);
		System.out.println(task.getTaskId());
		System.out.println(task.getTaskName());
		System.out.println(task.getFromDate());
		System.out.println(task.getToDate());
		return parser.getCommand();
	}
	
	
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	
	public ArrayList<Task> getTasksOn(Date date) {
		return getTasksBefore(date);
	}
	
	public ArrayList<Task> getTasksBefore(Date date) {
		
		ArrayList<Task> tasks = new ArrayList<Task>();
		
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
		return tasks;
		
	}
	
	public ArrayList<Task> getTasksAfter(Date date) {
		return getTasksBefore(date);
	}
	
	public ArrayList<Task> getFloatingTasks() {
		
		ArrayList<Task> tasks = new ArrayList<Task>();
		
		String[] taskNames = { "IT Cell Meeting", "User Guide Submission", "Developer Guide Submission", "CS2101 Reflection" };

		for(int i = 0; i < taskNames.length; i++) {
			
			Task task = new Task();
			task.setTaskId(i);
			task.setTaskName(taskNames[i]);
			task.setTaskType(Task.TaskType.FLOATING);
			
			tasks.add(task);
			
		}
		
		return tasks;
	}

}
