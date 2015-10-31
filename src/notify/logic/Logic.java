package notify.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Stack;

import notify.Task;
import notify.TaskType;
import notify.logic.command.Command;
import notify.logic.command.Result;
import notify.logic.command.ReversibleCommand;
import notify.logic.parser.CommandParser;
import notify.storage.Storage;

public class Logic {
	
	private ArrayList<Task> tasks;
	private CommandParser parser;
	private Storage storage;
	private TaskManager taskManager;
	private Stack<ReversibleCommand> history;
	
	public Logic() {
		
		this.storage = new Storage();
		this.history = new Stack<ReversibleCommand>();
		this.taskManager = new TaskManager(storage);
		this.parser = new CommandParser(storage, taskManager, history);
	}
	
	public CommandParser getCommandParser() {
		return this.parser;
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
	public Result processCommand(String input) {
		
		Command command = parser.parse(input);

		Result result = command.execute();
		
		//System.out.println(result.getFirstResult().getTaskName());
		
		return result;
	}
	
	public ArrayList<Task> getTasksOn(Calendar date, boolean isCompleted) {
		
		return taskManager.getTask(date, isCompleted);
		
	}
	 
	public ArrayList<Task> getDailyTasks(Calendar date, boolean isCompleted) {
		
		Calendar today = Calendar.getInstance();
		
		int todayYear = today.get(Calendar.YEAR);
		int todayDay = today.get(Calendar.DAY_OF_YEAR);
		int dateYear = date.get(Calendar.YEAR);
		int dateDay = date.get(Calendar.DAY_OF_YEAR);
		
		ArrayList<Task> tasks = taskManager.getTask(date, isCompleted);
		
		for(Iterator<Task> iterator = tasks.iterator(); iterator.hasNext(); ) {
			
			Task task = iterator.next();
			
			if(task.getTaskType() == TaskType.RANGE) {
				int taskStartYear = task.getStartDate().get(Calendar.YEAR);
				int taskStartDay = task.getStartDate().get(Calendar.DAY_OF_YEAR);
				
				if(task.isStarted()) {

					System.out.println(task.getTaskName());
					if(!(todayYear == dateYear && todayDay == dateDay)) {
						
						iterator.remove();
						
					}
					
				} else {
					
					if(!(taskStartYear == dateYear && taskStartDay == dateDay)) {
						
						iterator.remove();
						
					}
					
				}
			}
			
		}
		
		/*for(int i = 0; i < tasks.size(); i++) {
			
			Task task = tasks.get(i);
			
			assert task.getTaskType() != TaskType.FLOATING;
			
			if(task.getTaskType() == TaskType.RANGE) {
				int taskStartYear = task.getStartDate().get(Calendar.YEAR);
				int taskStartDay = task.getStartDate().get(Calendar.DAY_OF_YEAR);
				
				if(task.isStarted()) {
					
					if(!(todayYear == dateYear && todayDay == dateDay)) {
						
						tasks.remove(i);
						
					}
					
				} else {
					
					if(!(taskStartYear == dateYear && taskStartDay == dateDay)) {
						
						tasks.remove(i);
						
					}
					
				}*/
				
				/*if(taskStartYear < todayYear || (taskStartYear == todayYear && taskStartDay < todayDay)) {
					
					if(dateYear > todayYear || (dateYear == todayYear && dateDay > todayDay)) {
						
						tasks.remove(i);
						
					}
					
				
				} else {
					
					if(dateYear > taskStartYear || (dateYear == taskStartYear && dateDay > taskStartDay)) {
						
						tasks.remove(i);
						
					}
					
				} */
				
			/*}
			
		}*/
		
		return tasks;
		
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
