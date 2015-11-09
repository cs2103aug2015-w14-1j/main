//@@author A0125364J
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
import notify.storage.api.Storage;

public class Logic {
	
	private CommandParser commandParser;
	private Storage storage;
	private TaskManager taskManager;
	private Stack<ReversibleCommand> history;
	
	public Logic() {
		
		this.storage = new Storage();
		this.history = new Stack<ReversibleCommand>();
		this.taskManager = new TaskManager(this.storage);
		this.commandParser = new CommandParser(this.storage, this.taskManager, this.history);
		
	}

	/**
	 * Retrieve the command parser object.
	 * 
	 * @return returns the command parser object
	 */
	public CommandParser getCommandParser() {
		
		return this.commandParser;
		
	}
	
	/**
	 * Retrieve the history stack object.
	 * 
	 * @return returns the history stack object
	 */
	public Stack<ReversibleCommand> getHistory(){
		
		return this.history;
	}
	
	/**
	 * Retrieve the storage object.
	 * 
	 * @return returns the storage object.
	 */
	public Storage getStorage() {
		
		return this.storage;
		
	}
	
	/**
	 * Retrieve the task manager object.
	 * 
	 * @return returns the task manager object
	 */
	public TaskManager getTaskManager() {
		
		return this.taskManager;
		
	}
	
	/**
	 * Writes the changes into the file.
	 */
	public void save() {
		
		this.storage.saveTasks(taskManager.getTasks());
		
	}
	
	/**
	 * Process the input entered by the user.
	 * 
	 * @param input input entered by the user.
	 */
	public Result processCommand(String input) {
		
		assert (!input.equals(""));
		
		Command command = this.commandParser.parse(input);
		Result result = command.execute();
		
		if (command.isPersistable()) {
			
			save();
			
		}
		
		return result;
		
	}
	
	/**
	 * Retrieve completed or uncompleted tasks on the date specified.
	 * 
	 * @param date date to retrieve the tasks
	 * @param isCompleted determine whether to retrieve completed or uncompleted tasks
	 * 
	 * @return returns a list of tasks on the date specified
	 */
	public ArrayList<Task> getTasksOn(Calendar date, boolean isCompleted) {
		
		assert (date != null);
		
		return taskManager.getTasks(date, isCompleted);
		
	}
	 
	/**
	 * Retrieve daily tasks on the date specified.
	 * Remove duplicates if the tasks is a range class.
	 * 
	 * @param date date to retrieve the tasks
	 * @param isCompleted determine whether to retrieve completed or uncompleted tasks
	 * 
	 * @return returns a list of daily tasks on the date specified
	 */
	public ArrayList<Task> getDailyTasks(Calendar date, boolean isCompleted) {
		
		assert (date != null);
		
		Calendar today = Calendar.getInstance();
		
		int todayYear = today.get(Calendar.YEAR);
		int todayDay = today.get(Calendar.DAY_OF_YEAR);
		int dateYear = date.get(Calendar.YEAR);
		int dateDay = date.get(Calendar.DAY_OF_YEAR);
		
		ArrayList<Task> tasks = this.taskManager.getTasks(date, isCompleted);
		
		for (Iterator<Task> iterator = tasks.iterator(); iterator.hasNext(); ) {
			
			Task task = iterator.next();
			
			if (task.getTaskType() == TaskType.RANGE) {
				
				int taskStartYear = task.getStartDate().get(Calendar.YEAR);
				int taskStartDay = task.getStartDate().get(Calendar.DAY_OF_YEAR);
				
				if (task.isStarted()) {

					if (!(todayYear == dateYear && todayDay == dateDay)) {
						
						iterator.remove();
						
					}
					
				} else {
					
					if (!(taskStartYear == dateYear && taskStartDay == dateDay)) {
						
						iterator.remove();
						
					}
					
				}
				
			}
			
		}
		
		return tasks;
		
	}
	
	/**
	 * Retrieves the list of coming soon tasks.
	 * Coming soon tasks are tasks that starts after 7 days from today.
	 * 
	 * @return returns a list of coming soon tasks
	 */
	public ArrayList<Task> getComingSoonTasks() {
		
		return this.taskManager.getComingSoonTasks();
		
	}
	
	/**
	 * Retrieves the list of overdue tasks.
	 * Overdue tasks are tasks that were due before today and is not completed.
	 * 
	 * @return returns a list of overdue tasks
	 */
	public ArrayList<Task> getOverdueTasks() {
		
		return this.taskManager.getOverdueTasks();
		
	}
	
	/**
	 * Retrieves the list of floating tasks.
	 * Floating tasks are tasks that do not have any deadlines or range of date.
	 * 
	 * @return returns a list of floating tasks
	 */
	public ArrayList<Task> getFloatingTasks() {
		
		return this.taskManager.getTasks(TaskType.FLOATING, false);
		
	}
	
	/**
	 * Retrieves the list of completed tasks.
	 * Completed tasks are tasks that have been completed.
	 * 
	 * @return returns a list of completed tasks
	 */
	public ArrayList<Task> getCompletedTasks() {
		
		return this.taskManager.getTasks(true);
		
	}

}
