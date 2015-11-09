// @@author A0124072

package notify.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import notify.DateRange;
import notify.Task;
import notify.TaskType;
import notify.storage.api.Storage;

public class TaskManager {

	/**
	 * Variables used in this class. {@value #taskList} is used as a cache to
	 * store the user tasks before saving it to the data file.
	 */
	private int latestId;
	private ArrayList<Task> taskList;
	private Storage storage;

	/**
	 * class Constructor which instantiate the class variables and invokes
	 * {@link #updateLatestId()} to update the taskId.
	 * 
	 * @param storage
	 *            a Storage object which was instantiated in logic {@see
	 *            notify.logic.Logic}
	 */
	public TaskManager(Storage storage) {

		assert (storage != null);
		this.latestId = 0;
		this.storage = storage;
		this.taskList = this.storage.loadTasks();

		updateLatestId();

	}

	/**
	 * This class creates a Task object and adds it into the list
	 * {@value #taskList}
	 * 
	 * @param name
	 *            the name of the task
	 * @param timespan
	 *            the period(date and/or time) of the task
	 * @param category
	 *            the category which the task belongs to
	 * @param taskType
	 *            the type of task
	 * 
	 * @return the Task object which is being added
	 */
	public Task addTask(String name, DateRange timespan, String category,
			TaskType taskType) {

		Task task = new Task(this.latestId, taskType, name, timespan, category,
				false);
		this.taskList.add(task);
		this.latestId++;

		return task;

	}

	/**
	 * Delete the task with the specified id
	 * 
	 * @param id
	 *            The ID of the task to be deleted
	 * 
	 * @return the Task that has been deleted
	 */
	public Task deleteTask(int id) {

		Task task = getTask(id);

		if (task != null) {

			task.setDeleted(true);

			return task;

		}

		return null;

	}

	/**
	 * To recover a deleted task with the specified id
	 * 
	 * @param id
	 *            the id of the task to be recovered
	 * 
	 * @return the recovered task
	 */
	public Task undeleteTask(int id) {

		Task task = getTask(id);

		if (task != null) {
			task.setDeleted(false);
			return task;
		}

		return null;

	}

	/**
	 * Updates the task details
	 * 
	 * @param id
	 *            the id of the task to be updated
	 * @param newName
	 *            the new updated name
	 * @param newDateRange
	 *            the new period(date and/or time)
	 * @param category
	 *            the new category
	 * @param newType
	 *            the new task type
	 * @return the modified Task; null if the task with the specified id does
	 *         not exist
	 */
	public Task updateTask(int id, String newName, DateRange newDateRange,
			String category, TaskType newType) {

		Task task = getTask(id);

		if (task != null) {

			task.setTaskName(newName);
			task.setDateRange(newDateRange);
			task.setCategory(category);
			task.setTaskType(newType);

			return task;

		}

		return null;

	}

	/**
	 * To mark a task with the specified id as completed
	 * 
	 * @param id
	 *            the id of the task to be mark completed
	 * @param isCompleted
	 *            a boolean object which indicates the completion of the task.
	 *            'true' completed; 'false' uncompleted
	 * @return the Task that marked completed. null if the task with the
	 *         specified id does not exist
	 */
	public Task markTask(int id, boolean isCompleted) {

		Task task = getTask(id);

		if (task != null) {

			task.setCompleted(isCompleted);

			return task;

		}
		return null;

	}

	/**
	 * To search a list of tasks that contains the specified keyWord It searches
	 * through the id, task name and task category.
	 * 
	 * @param keyWord
	 *            the search key word which the user specifies
	 * 
	 * @return a list of Task which contains the specified search key word
	 */
	public ArrayList<Task> searchTask(String keyWord) {

		ArrayList<Task> tempList = new ArrayList<Task>();

		assert (this.taskList != null);

		for (Task task : this.taskList) {

			if (task.isSearchedTask(keyWord) && !task.isDeleted()) {

				tempList.add(task);

			}

		}

		Collections.sort(tempList);
		return tempList;

	}

	/**
	 * Pass the list of tasks to the storage to get written into the file
	 */
	public void exit() {

		assert (this.storage != null);
		this.storage.saveTasks(this.taskList);

	}

	/**
	 * To update the latestId {@value #latestId}
	 */
	private void updateLatestId() {

		if (this.taskList == null) {

			this.taskList = new ArrayList<Task>();

		} else {

			if (!this.taskList.isEmpty()) {

				int lastTaskIndex = this.taskList.size() - 1;
				int lastTaskId = this.taskList.get(lastTaskIndex).getTaskId();
				this.latestId = lastTaskId + 1;

			}

		}

	}

	/**
	 * Retrieve the task with the specified task id
	 * 
	 * @param taskId
	 *            the Id of the task
	 * 
	 * @return the Task object with the specified id. null if the task does not
	 *         exist.
	 */
	public Task getTask(int taskId) {

		assert (this.taskList != null);
		for (Task task : this.taskList) {

			if (task.getTaskId() == taskId) {

				return task;

			}

		}

		return null;

	}

	// @@author

	// @@author A0125364J
	/**
	 * Retrieve the task that is not deleted with the id specified or null if
	 * there are no task with the id specified.
	 * 
	 * @param taskId
	 *            task id of the task to be retrieved
	 * @param isCompleted
	 *            true to retrieve only completed tasks, false to retrieve only
	 *            uncompleted task
	 * @return a task object of the task is found, otherwise return null
	 */
	public Task getTask(int taskId, boolean isCompleted) {

		for (Task task : taskList) {

			if (task.getTaskId() == taskId && !task.isDeleted()
					&& task.isCompleted() == isCompleted) {

				return task;

			}

		}

		return null;

	}

	/**
	 * Retrieve all the tasks (including tasks that are deleted).
	 * 
	 * @return a list of all the tasks
	 */
	public ArrayList<Task> getTasks() {

		return taskList;

	}

	/**
	 * Retrieve all the tasks where its task type is equals to the task type
	 * specified.
	 * 
	 * @param taskType
	 *            type of task to retrieve
	 * @param isCompleted
	 *            true to retrieve only completed tasks, false to retrieve only
	 *            uncompleted task
	 * @return a list of task where task type is equals to the task type
	 *         specified.
	 */
	public ArrayList<Task> getTasks(TaskType taskType, boolean isCompleted) {

		ArrayList<Task> tempList = new ArrayList<Task>();

		for (Task task : taskList) {

			if (task.getTaskType() == taskType && !task.isDeleted()
					&& task.isCompleted() == isCompleted) {

				tempList.add(task);

			}

		}

		Collections.sort(tempList);
		return tempList;

	}

	/**
	 * Retrieve all the tasks where its date falls on the date specified or the
	 * date specified is within its range.
	 * 
	 * @param date
	 *            date of the task to retrieve
	 * @param isCompleted
	 *            true to retrieve only completed tasks, false to retrieve only
	 *            uncompleted task
	 * @return a list of task where its date falls on the date specified or the
	 *         date specified is within its range.
	 */
	public ArrayList<Task> getTasks(Calendar date, boolean isCompleted) {

		ArrayList<Task> tempList = new ArrayList<Task>();

		for (Task task : taskList) {

			if (task.isOn(date) && !task.isDeleted()
					&& task.isCompleted() == isCompleted) {

				tempList.add(task);

			}

		}

		Collections.sort(tempList);
		return tempList;

	}

	/**
	 * Retrieve all the tasks that are not deleted and its completed status
	 * matches the completed status specified.
	 * 
	 * @param isCompleted
	 *            true to retrieve completed tasks. false to retrieve
	 *            uncompleted tasks
	 * @return a list of completed task or uncompleted task depending on the
	 *         value passed in.
	 */
	public ArrayList<Task> getTasks(boolean isCompleted) {

		ArrayList<Task> tempList = new ArrayList<Task>();

		for (Task task : taskList) {

			if (task.isCompleted() == isCompleted && !task.isDeleted()) {

				tempList.add(task);

			}

		}

		Collections.sort(tempList);
		return tempList;
	}

	/**
	 * Retrieve all the tasks that are overdue. Overdue tasks are tasks that
	 * have its end date earlier than todays date.
	 * 
	 * @return a list of tasks that are overdue
	 */
	public ArrayList<Task> getOverdueTasks() {

		ArrayList<Task> tempList = new ArrayList<Task>();

		for (Task task : taskList) {

			if (task.isOverdue()) {

				assert task.isCompleted() == false;
				assert task.isDeleted() == false;

				tempList.add(task);

			}

		}

		Collections.sort(tempList);
		return tempList;

	}

	/**
	 * Retrieve all the tasks that are coming soon. Coming soon tasks are tasks
	 * that are not within the week (or today and up coming 6 days )
	 * 
	 * @return a list of tasks that are coming soon.
	 */
	public ArrayList<Task> getComingSoonTasks() {

		ArrayList<Task> tempList = new ArrayList<Task>();

		for (Task task : taskList) {

			if (task.isComingSoon()) {

				assert task.isCompleted() == false;
				assert task.isDeleted() == false;

				tempList.add(task);

			}

		}

		Collections.sort(tempList);
		return tempList;

	}
	// @@author
}