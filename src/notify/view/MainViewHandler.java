/**
 * Author: Kenneth Ho Chee Chong
 * Matric No: A0125364J
 * For CS2103T - Notify
 */

//@@author A0125364J
package notify.view;

import notify.Task;
import notify.TaskType;
import notify.logic.*;
import notify.logic.command.Action;
import notify.logic.command.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class MainViewHandler {
	
	private static String[] DAYS_OF_WEEK = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };	
	
	// styling for container containing each line of item
	private static double HBOX_NODE_SPACING = 3.5;
	private static Pos HBOX_NODE_ALIGNMENT = Pos.TOP_LEFT;
	private static Insets HEADER_PADDING = new Insets(0, 0, 2, 0);	//top, right, bottom, left
	
	// font color for different type of task
	private static Paint ERROR_MESSAGE_FILL = Paint.valueOf("#CC181E");
	private static Paint FEEDBACK_MESSAGE_FILL = Paint.valueOf("#16A085");
	
	private static String TITLE_CSS_CLASS = "title";
	private static String SUBTITLE_CSS_CLASS = "subtitle";
	private static String TASK_NAME_CSS_CLASS = "taskname";
	private static String TIMESTAMP_CSS_CLASS = "timestamp";
	
	// String pattern for dates
	private static String SHORT_DAY_PATTERN = "EEE";
	private static String LONG_DAY_PATTERN = "EEEE";
	private static String SHORT_DATE_PATTERN = "dd MMM yy";
	private static String LONG_DATE_PATTERN = "dd MMMM yy";
	private static String TIME_PATTERN = "hh:mm a";
	
	// String format for deadline tasks timestamp
	private static String DEADLINE_DATE_START_END_TIME_TIMESTAMP_FORMAT = "%1$s, %2$s to %3$s";
	private static String DEADLINE_DATE_END_TIME_TIMESTAMP_FORMAT = "%1$s, %2$s";
	private static String DEADLINE_END_DATE_TIMESTAMP_FORMAT = "%1$s";
	private static String DEADLINE_FROM_START_TO_END_TIME_TIMESTAMP_FORMAT = "from %1$s to %2$s";
	private static String DEADLINE_AT_END_TIME_TIMESTAMP_FORMAT = "at %1$s";
	
	// String format for ranged tasks timestamp
	private static String RANGE_START_DATE_TIME_TO_END_DATE_TIME_TIMESTAMP_FORMAT = "%1$s, %2$s to %3$s, %4$s";
	private static String RANGE_START_DATE_TO_END_DATE_TIMESTAMP_FORMAT = "%1$s to %2$s";
	private static String RANGE_FROM_START_TIME_TILL_END_DATE_TIME_TIMESTAMP_FORMAT = "from %1$s till %2$s, %3$s";
	private static String RANGE_TILL_END_DATE_TIME_TIMESTAMP_FORMAT = "till %1$s, %2$s";
	private static String RANGE_TILL_END_DATE_TIMESTAMP_FORMAT = "till %1$s";
	
	// String format for title timestamp
	private static String LONG_DAY_DATE_FORMAT = "%1$s, %2$s";
	
	// String for title of different tasks status
	private static String OVERDUE_TITLE = "Overdue";
	private static String FLOATING_TITLE = "Floating";
	private static String COMING_TITLE = "Coming Soon...";
	private static String TODAY_TITLE = "Today";
	private static String TOMORROW_TITLE = "Tomorrow";
	
	// String for error messages
	private static String INVALID_COMMAND_MESSAGE = "Invalid command '%1$s'. Please try again.";
	private static String ADDED_MESSAGE = "Task added: '%1$s'.";
	private static String EDITED_MESSAGE = "Task '%1$s' edited.";
	private static String DELETED_MESSAGE = "Task '%1$s' deleted.";
	private static String SEARCH_RESULT_MESSAGE = "Search Result for '%1$s':";
	
	private static String SEARCH_INPUT = "";
	
	// stack to store commands history
	private static Stack<String> COMMAND_HISTORY_STACK = new Stack<String>();
	private static Stack<String> COMMAND_FUTURE_STACK = new Stack<String>();
	
	private Logic logic;
	
	// arraylist of different types of tasks
	private ArrayList<Task> overdueTasks;
	private ArrayList<Task> floatingTasks;
	private ArrayList<Task> comingTasks;
	private ArrayList<Task> completedTasks;
	private ArrayList<ArrayList<Task>> dailyTasksList;
	
	// fxml controls
	@FXML private VBox vboxFloating;
	@FXML private VBox vboxOverdue;
	@FXML private VBox vboxComing;
	@FXML private VBox vboxOne;
	@FXML private VBox vboxTwo;
	@FXML private VBox vboxThree;
	@FXML private VBox vboxFour;
	@FXML private VBox vboxFive;
	@FXML private VBox vboxSix;
	@FXML private VBox vboxSeven;
	@FXML private VBox vboxSearchCompleted;
	@FXML private VBox vboxSearchUncompleted;
	@FXML private VBox vboxCompletedTask;
	
	@FXML private TextField txtCommand;
	@FXML private Label lblFeedback;
	
	@FXML private Pane pnOverlay;
	@FXML private BorderPane bpnSearch;
	@FXML private BorderPane bpnCompleted;
	@FXML private GridPane gpnHelp;
	@FXML private Label lblSearchTitle;
	

	
	@FXML
	public void initialize() {
		//initDailyView();
		//populateOverdueTask();
		//populateFloatingTask();
		//populateComingSoonTask();

		/*loadOverdueTask();
		loadFloatingTask();
		loadComingTask();
		loadDailyTask();*/
	}
	
	
	/**
	 * Setter for logic.
	 * @param logic the logic object created in the Main class.
	 */
	public void setLogic(Logic logic) {
		
		this.logic = logic;
		
	}
	
	
	
	/**
	 * Loads the component of the UI
	 */
	public void load() {
		
		txtCommand.requestFocus();
		
		loadOverdueTask();
		loadFloatingTask();
		loadComingTask();
		loadDailyTask();
		
	}
	
	public boolean isSearchViewVisible() {
		
		return bpnSearch.isVisible();
		
	}
	
	
	public boolean isCompletedViewVisible() {
		
		return bpnCompleted.isVisible();
		
	}
	
	public boolean isHelpViewVisible() {
		
		return gpnHelp.isVisible();
		
	}
	
	
	
	/**
	 * Loads the tasks that are overdue.
	 */
	public void loadOverdueTask() {
		
		overdueTasks = logic.getOverdueTasks();

		HBox header = generateHeader(OVERDUE_TITLE);
		ArrayList<HBox> items = generateList(overdueTasks, false);
		
		vboxOverdue.getChildren().clear();
		vboxOverdue.getChildren().add(header);
		vboxOverdue.getChildren().addAll(items);
		
	}
	
	
	
	/**
	 * Loads the tasks that does not have any dates (floating tasks).
	 */
	public void loadFloatingTask() {
		
		floatingTasks = logic.getFloatingTasks();
		
		HBox header = generateHeader(FLOATING_TITLE);
		ArrayList<HBox> items = generateList(floatingTasks, false);
		
		vboxFloating.getChildren().clear();
		vboxFloating.getChildren().add(header);
		vboxFloating.getChildren().addAll(items);
		
	}
	
	
	
	/**
	 * Loads the upcoming tasks (tasks that have dates more than 1 week from current date).
	 */
	public void loadComingTask() {
		
		comingTasks = logic.getComingSoonTasks();

		HBox header = generateHeader(COMING_TITLE);
		ArrayList<HBox> items = generateList(comingTasks, false);
		
		vboxComing.getChildren().clear();
		vboxComing.getChildren().add(header);
		vboxComing.getChildren().addAll(items);
		
	}
	
	
	
	/**
	 * Loads the tasks that are within the week (current date and 6 days ahead).
	 */
	public void loadDailyTask() {
		
		VBox[] vboxes = { vboxOne, vboxTwo, vboxThree, vboxFour, vboxFive, vboxSix, vboxSeven };
		Calendar calendar = Calendar.getInstance();
		
		dailyTasksList = new ArrayList<ArrayList<Task>>();
		
		for(int i = 0; i < DAYS_OF_WEEK.length; i++) {
			
			ArrayList<Task> dailyTasks = logic.getDailyTasks(calendar, false);
			
			HBox header = generateHeader(calendar);
			ArrayList<HBox> items = generateList(dailyTasks, false);
			
			vboxes[i].getChildren().clear();
			vboxes[i].getChildren().add(header);
			vboxes[i].getChildren().addAll(items);
			
			dailyTasksList.add(dailyTasks);		
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			
		}
		
	}
	
	
	public void loadSearchResult(ArrayList<Task> searchResults) {
		
		ArrayList<Task> completedTasks = new ArrayList<Task>();
		ArrayList<Task> uncompletedTasks = new ArrayList<Task>();
		
		completedTasks = filterTask(searchResults, true);
		uncompletedTasks = filterTask(searchResults, false);

		ArrayList<HBox> hboxesCompleted = generateList(completedTasks, true);
		ArrayList<HBox> hboxesUncompleted = generateList(uncompletedTasks, true);
		
		vboxSearchCompleted.getChildren().clear();
		vboxSearchCompleted.getChildren().addAll(hboxesCompleted);
		
		vboxSearchUncompleted.getChildren().clear();
		vboxSearchUncompleted.getChildren().addAll(hboxesUncompleted);
		
	}
	
	public void loadCompletedTasks() {
		
		completedTasks = logic.getCompletedTasks();
		
		ArrayList<HBox> hboxes = generateList(completedTasks, true);
		
		vboxCompletedTask.getChildren().clear();
		vboxCompletedTask.getChildren().addAll(hboxes);
		
	}
	
	public void showSearchView(String searchTerm) {
		
		lblSearchTitle.setText(String.format(SEARCH_RESULT_MESSAGE, searchTerm));
		
		hideCompletedView();
		hideHelpView();
		
		pnOverlay.setVisible(true);
		bpnSearch.setVisible(true);
		
	}
	
	public void showCompletedView() {
		
		hideSearchView();
		hideHelpView();
		
		pnOverlay.setVisible(true);
		bpnCompleted.setVisible(true);
		
	}
	
	public void showHelpView() {
		
		hideSearchView();
		hideCompletedView();
		
		pnOverlay.setVisible(true);
		gpnHelp.setVisible(true);
		
	}
	
	public void hideSearchView() {
		
		pnOverlay.setVisible(false);
		bpnSearch.setVisible(false);
		
	}
	
	public void hideCompletedView() {
		
		pnOverlay.setVisible(false);
		bpnCompleted.setVisible(false);
		
	}
	
	public void hideHelpView() {
		
		pnOverlay.setVisible(false);
		gpnHelp.setVisible(false);
		
	}
	
	public ArrayList<Task> filterTask(ArrayList<Task> tasks, boolean isCompleted) {
		
		ArrayList<Task> results = new ArrayList<Task>();
		
		for(Task task: tasks) {
			
			if(task.isCompleted() == isCompleted) {
				
				results.add(task);
				
			}
			
		}
		
		return results;
		
	}
	
	
	/**
	 * Generates the header (without subtitle)
	 * 
	 * @param title the title of the header
	 * @param titleTextFill the font color of the title
	 * @return a HBox object (which contains the title)
	 */
	public HBox generateHeader(String title) {
		
		Label lblTitle = createLabel(title, TITLE_CSS_CLASS);
		HBox hbox = createItem(HEADER_PADDING, false, false, lblTitle);
		
		return hbox;
		
	}
	
	
	
	/**
	 * Generates the timestamp for all tasks with time
	 * @param task the task to have its timestamp generated
	 * @return timestamp (e.g. (23 Oct 15, 02:00PM)
	 */
	public String generateTimeStamp(Task task, boolean isSearch) {
		
		TaskType taskType = task.getTaskType();
		String timeStamp = "";
		
		switch(taskType) {
		
			case DEADLINE:

				timeStamp = generateDeadlineTimestamp(task, isSearch);
				break;
				
			case RANGE:
				
				timeStamp = generateRangeTimestamp(task, isSearch);		
				break;
				
			default:
				
				timeStamp = "";
				break;
		
		}
		
		return timeStamp;
		
	}
	
	
	
	/**
	 * Generates the timestamp for deadline tasks
	 * @param task the task to have its timestamp generated
	 * @return timestamp (e.g. (23 Oct 15, 02:00PM)
	 */
	public String generateDeadlineTimestamp(Task task, boolean isSearch) {
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(SHORT_DATE_PATTERN);
		SimpleDateFormat timeFormatter = new SimpleDateFormat(TIME_PATTERN);
		
		Calendar taskEndDate = task.getEndDate();
		Calendar taskStartTime = task.getStartTime();
		Calendar taskEndTime = task.getEndTime();
		
		String taskEndDateStamp = "";
		String taskStartTimeStamp = "";
		String taskEndTimeStamp = "";
		String timestamp = "";

		taskEndDateStamp = dateFormatter.format(taskEndDate.getTime());
		
		if(task.isComingSoon() || task.isOverdue() || isSearch) {

			if(taskStartTime != null) {

				taskStartTimeStamp = timeFormatter.format(taskStartTime.getTime());
				taskEndTimeStamp = timeFormatter.format(taskEndTime.getTime());
				timestamp = String.format(DEADLINE_DATE_START_END_TIME_TIMESTAMP_FORMAT, taskEndDateStamp, taskStartTimeStamp, taskEndTimeStamp);
				
			} else if(taskEndTime != null) {

				taskEndTimeStamp = timeFormatter.format(taskEndTime.getTime());
				timestamp = String.format(DEADLINE_DATE_END_TIME_TIMESTAMP_FORMAT, taskEndDateStamp, taskEndTimeStamp);
				
			} else {
				
				timestamp = String.format(DEADLINE_END_DATE_TIMESTAMP_FORMAT, taskEndDateStamp);
				
			}
			
		} else {
		
			if(taskStartTime != null) {
				
				taskStartTimeStamp = timeFormatter.format(taskStartTime.getTime());
				taskEndTimeStamp = timeFormatter.format(taskEndTime.getTime());
				timestamp = String.format(DEADLINE_FROM_START_TO_END_TIME_TIMESTAMP_FORMAT, taskStartTimeStamp, taskEndTimeStamp);
				
			} else if(taskEndTime != null) {
				
				taskEndTimeStamp = timeFormatter.format(taskEndTime.getTime());
				timestamp = String.format(DEADLINE_AT_END_TIME_TIMESTAMP_FORMAT, taskEndTimeStamp);
				
			}
			
		}

		return timestamp;
		
	}
	
	/**
	 * Generates the timestamp for ranged tasks
	 * @param task the task to have its timestamp generated
	 * @return timestamp (e.g. 23 Oct 15, 12:00AM to 12:00PM)
	 */
	public String generateRangeTimestamp(Task task, boolean isSearch) {
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(SHORT_DATE_PATTERN);
		SimpleDateFormat timeFormatter = new SimpleDateFormat(TIME_PATTERN);
		
		Calendar taskStartDate = task.getStartDate();
		Calendar taskStartTime = task.getStartTime();
		Calendar taskEndDate = task.getEndDate();
		Calendar taskEndTime = task.getEndTime();
		
		String taskStartDateStamp = "";
		String taskStartTimeStamp = "";
		String taskEndDateStamp = "";
		String taskEndTimeStamp = "";
		String timeStamp = "";
		
		if(task.isComingSoon() || task.isOverdue() || isSearch) {
			
			if(taskStartTime != null && taskEndTime != null) {
				
				taskStartDateStamp = dateFormatter.format(taskStartDate.getTime());
				taskStartTimeStamp = timeFormatter.format(taskStartTime.getTime());
				taskEndDateStamp = dateFormatter.format(taskEndDate.getTime());
				taskEndTimeStamp = timeFormatter.format(taskEndTime.getTime());
				
				timeStamp = String.format(RANGE_START_DATE_TIME_TO_END_DATE_TIME_TIMESTAMP_FORMAT, taskStartDateStamp, taskStartTimeStamp, taskEndDateStamp, taskEndTimeStamp);
				
			} else {
				
				taskStartDateStamp = dateFormatter.format(taskStartDate.getTime());
				taskEndDateStamp = dateFormatter.format(taskEndDate.getTime());
				
				timeStamp = String.format(RANGE_START_DATE_TO_END_DATE_TIMESTAMP_FORMAT, taskStartDateStamp, taskEndDateStamp);
				
			}
			
		} else {
			
			if(task.isEndingSoon()) {
				
				dateFormatter = new SimpleDateFormat(SHORT_DAY_PATTERN);
				
			} else {
				
				dateFormatter = new SimpleDateFormat(SHORT_DATE_PATTERN);
				
			}
			
			taskEndDateStamp = dateFormatter.format(taskEndDate.getTime());
			
			if(taskStartTime != null && taskEndTime != null) {
				
				taskStartTimeStamp = timeFormatter.format(taskStartTime.getTime());
				taskEndTimeStamp = timeFormatter.format(taskEndTime.getTime());
				
				if(task.isStarted()) {
					
					timeStamp = String.format(RANGE_TILL_END_DATE_TIME_TIMESTAMP_FORMAT, taskEndDateStamp, taskEndTimeStamp);
					
				} else {
					
					timeStamp = String.format(RANGE_FROM_START_TIME_TILL_END_DATE_TIME_TIMESTAMP_FORMAT, taskStartTimeStamp, taskEndDateStamp, taskEndTimeStamp);
					
				}
				
			} else {
				
				timeStamp = String.format(RANGE_TILL_END_DATE_TIMESTAMP_FORMAT, taskEndDateStamp);
				
			}
			
		}
		
		return timeStamp;
		
	}
	
	/**
	 * Generates the header by providing the date of the header
	 * @param calendar the date of the header to be generated
	 * @param titleTextFill the font color of the title
	 * @param subtitleTextFill the font color of the subtitle
	 * @return
	 */
	public HBox generateHeader(Calendar calendar) {
		
		Calendar today = Calendar.getInstance();
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		
		SimpleDateFormat dayFormatter = new SimpleDateFormat(LONG_DAY_PATTERN);
		SimpleDateFormat dateFormatter = new SimpleDateFormat(LONG_DATE_PATTERN);
		
		String title = dayFormatter.format(calendar.getTime());
		String subtitle = dateFormatter.format(calendar.getTime());
		
		if(calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
			
			subtitle = String.format(LONG_DAY_DATE_FORMAT, title, subtitle);
			title = TODAY_TITLE; 
			
		} else if(calendar.get(Calendar.DAY_OF_MONTH) == tomorrow.get(Calendar.DAY_OF_MONTH)) {

			subtitle = String.format(LONG_DAY_DATE_FORMAT, title, subtitle);
			title = TOMORROW_TITLE;
			
		}
		
		Label lblTitle = createLabel(title, TITLE_CSS_CLASS);
		Label lblSubtitle = createLabel(subtitle, SUBTITLE_CSS_CLASS);
		
		//lblTitle.getStyleClass().add("title");
		//lblSubtitle.getStyleClass().add("subtitle");
		//lblTitle.setStyle("-fx-font-family:'Roboto Condensed'");
		//lblSubtitle.setStyle("-fx-font-family:'Roboto Condensed'");fff
		
		HBox hbox = createItem(HEADER_PADDING, false, false, lblTitle, lblSubtitle);
		
		return hbox;
		
	}
	
	
	/**
	 * Generates the items in each list.
	 * @param tasks a list
	 * @param textFill the color of the main text
	 * @param subtextFill the color of the subtext
	 * @return
	 */
	public ArrayList<HBox> generateList(ArrayList<Task> tasks, boolean isSearch) {
		
		ArrayList<HBox> hboxes = new ArrayList<HBox>();
		String subtext = "";
		
		for(int i = 0; i < tasks.size(); i++) {
			
			Task task = tasks.get(i);
			
			CheckBox checkBox = createCheckbox(task.getTaskId() + "", task.isCompleted());
			Label lblTaskName = createLabel(task.getTaskName(), TASK_NAME_CSS_CLASS);
			Label lblTaskTime;
			HBox hbox;
			
			switch(task.getTaskType()) {
			
				case FLOATING:
					
					hbox = createItem(true, isSearch, checkBox, lblTaskName);
					
					break;
				
				default:
					
					subtext = generateTimeStamp(task, isSearch);
					lblTaskTime = createLabel(subtext, TIMESTAMP_CSS_CLASS);
					hbox = createItem(true, isSearch, checkBox, lblTaskName, lblTaskTime);
					
					break;
			
			}

			hboxes.add(hbox);
			
		}
		
		return hboxes;
		
	}
	
	
	
	/**
	 * Creates the HBox with nodes
	 * @param nodes the nodes to be added to the hbox (labels, checkboxes, etc.)
	 * @return the HBox which represents a single line of item to be added into the VBox
	 */
	public HBox createItem(boolean hasCheckbox, boolean isSearch, Node... nodes) {
		
		HBox hbox = new HBox();
		hbox.setSpacing(HBOX_NODE_SPACING);
		hbox.setAlignment(HBOX_NODE_ALIGNMENT);

		int startIndex = 0;
		if(hasCheckbox) {
			
			hbox.getChildren().add(nodes[startIndex]);
			startIndex = 1;
			
		}

		if(isSearch) {
			
			VBox vbox = new VBox();
			
			for(int i = startIndex; i< nodes.length; i++) {
				
				vbox.getChildren().add(nodes[i]);
				
			}
			
			hbox.getChildren().add(vbox);
			
		} else {
			
			FlowPane flowPane = new FlowPane();
			flowPane.setHgap(HBOX_NODE_SPACING);
			
			for(int i = startIndex; i < nodes.length; i++) {
				
				flowPane.getChildren().add(nodes[i]);
				
			}
			
			hbox.getChildren().add(flowPane);
			
		}
		
		
		return hbox;
		
	}
	
	public HBox createItem(Insets insets, boolean hasCheckbox, boolean isSearch, Node... nodes) {
		
		HBox hbox = createItem(hasCheckbox, isSearch, nodes);
		hbox.setPadding(insets);
		
		return hbox;
		
	}
	
	/**
	 * Create a label with its formatting
	 * @param text the text of the label
	 * @param font the font of the label such as font family, font weight, font posture and font size
	 * @return a label which contains the text and formatting (default text color)
	 */
	public Label createLabel(String text, String cssClass) {
		
		Label label = new Label(text);
		label.getStyleClass().add(cssClass);
		
		return label;
		
	}
	
	public CheckBox createCheckbox(String text, boolean isChecked) {
		
		CheckBox checkbox = new CheckBox(text);
		checkbox.setSelected(isChecked);
		checkbox.setDisable(isChecked);
		checkbox.setFocusTraversable(false);

		checkbox.setOnAction(event -> checkboxEventHandler(event, checkbox));

		return checkbox;
		
	}
	
	
	public void addCommandHistory(String userInput) {

		if(!COMMAND_FUTURE_STACK.isEmpty()) {
			
			COMMAND_HISTORY_STACK.push(userInput);
			
		}
		
		while(!COMMAND_FUTURE_STACK.isEmpty()) {
			
			COMMAND_HISTORY_STACK.push(COMMAND_FUTURE_STACK.pop());
			
		}

		COMMAND_HISTORY_STACK.push(userInput);
		
	}
	
	
	public void setFeedbackLabel(String message, Paint textFill) {
		
		lblFeedback.setText(message);
		lblFeedback.setTextFill(textFill);
		
	}
	
	public void processResult(Result result, String userInput) {
		
		Action actionPerformed = result.getActionPerformed();
		
		switch(actionPerformed) {
		
			case SEARCH:
				
				String searchTerm = userInput.replaceFirst("search", "").trim();
				SEARCH_INPUT = userInput;
				
				loadSearchResult(result.getResults());
				showSearchView(searchTerm);
				setFeedbackLabel("", FEEDBACK_MESSAGE_FILL);
				
				break;
				
			case BACK:
				
				hideSearchView();
				hideHelpView();
				hideCompletedView();
				setFeedbackLabel("", FEEDBACK_MESSAGE_FILL);
				
				break;
				
			case DISPLAY:
				
				loadCompletedTasks();
				showCompletedView();
				setFeedbackLabel("", FEEDBACK_MESSAGE_FILL);
				
				break;
				
			case HELP:
				
				showHelpView();
				
				break;
				
			case EXIT:

				System.exit(0);
				
				break;
			
			default:
				
				if(isSearchViewVisible()) {
					
					Result searchResult = logic.processCommand(SEARCH_INPUT);
					loadSearchResult(searchResult.getResults());
					
				}
				
				if(isCompletedViewVisible()) {
					
					loadCompletedTasks();
					
				}
				
				Task task = result.getFirstResult();
				
				if(actionPerformed == Action.ADD) {
					
					setFeedbackLabel(String.format(ADDED_MESSAGE, task.getTaskName()), FEEDBACK_MESSAGE_FILL);
					
				} else if(actionPerformed == Action.EDIT) {

					setFeedbackLabel(String.format(EDITED_MESSAGE, task.getTaskName()), FEEDBACK_MESSAGE_FILL);
					
				} else if(actionPerformed == Action.DELETE) {
					
					setFeedbackLabel(String.format(DELETED_MESSAGE, task.getTaskName()), FEEDBACK_MESSAGE_FILL);
					
				} else {

					setFeedbackLabel("", FEEDBACK_MESSAGE_FILL);
					
				}
				
				load();
				
				break;
		
		}
		
	}
	
	public void checkboxEventHandler(ActionEvent event, CheckBox checkbox) {
		
		if(checkbox.isSelected()) {
			
			String userInput = "mark " + checkbox.getText();
			Result result = logic.processCommand(userInput);
			
			processResult(result, userInput);
			
		}
		
	}
	
	/**
	 * Key pressed handler for text box.
	 * @param keyEvent key event for the button that is being pressed.
	 */
	public void txtCommandOnKeyPressedHandler(KeyEvent keyEvent) {
		String userInput = txtCommand.getText().trim();
		
		try {

			KeyCode keyCode = keyEvent.getCode();

			if(keyCode == KeyCode.ENTER && !userInput.equals("")) {
				
				processEnter(userInput);
				
			} else if(keyCode == KeyCode.BACK_SPACE) {
				
				processBackspace(userInput);
				
			} else if(keyCode == KeyCode.UP) {

				processUp(userInput);
				
			} else if(keyCode == KeyCode.DOWN) {
				
				processDown(userInput);
				
			} else {

				KeyCombination keyCombination = new KeyCodeCombination(KeyCode.Z, KeyCombination.META_DOWN);
				
				if(keyCombination.match(keyEvent)) {
					
					userInput = "undo";
					Result result = logic.processCommand(userInput);
					
					processResult(result, userInput);
					
				}
				
			}
			
		} catch(IllegalArgumentException e) {

			setFeedbackLabel(String.format(INVALID_COMMAND_MESSAGE, userInput), ERROR_MESSAGE_FILL);
			txtCommand.setText("");

			addCommandHistory(userInput);
			
		}
		
		
		
	}
	
	/**
	 * Process the event that occurs after the user presses the enter button.
	 * @param userInput the command keyed in by the user.
	 */
	public void processEnter(String userInput) {
		
		Result result = logic.processCommand(userInput);
		processResult(result, userInput);
		
		addCommandHistory(userInput);

		txtCommand.setText("");
		
	}
	
	/**
	 * Process the event that occurs after the user presses the backspace button.
	 * @param userInput the command keyed in by the user.
	 */
	public void processBackspace(String userInput) {

		if(userInput.equals("") && isSearchViewVisible()) {
			
			hideSearchView();
			
		} else if(userInput.equals("") && isHelpViewVisible()) {
			
			hideHelpView();
			
		} else if(userInput.equals("") && isCompletedViewVisible()) {
			
			hideCompletedView();
			
		}
		
	}
	
	/**
	 * Process the event that occurs after the user presses the up button.
	 * @param userInput the command keyed in by the user.
	 */
	public void processUp(String userInput) {

		if(!COMMAND_HISTORY_STACK.isEmpty()) {
			
			String previousCommand = COMMAND_HISTORY_STACK.pop();
			
			if(!userInput.equals("")) {
				
				COMMAND_FUTURE_STACK.push(userInput);
				
			}
			
			txtCommand.setText(previousCommand);
			
		}
		
		txtCommand.positionCaret(txtCommand.getText().length());
		
	}
	
	/**
	 * Process the event that occurs after the user presses the down button.
	 * @param userInput the command keyed in by the user.
	 */
	public void processDown(String userInput) {
		
		if(!COMMAND_FUTURE_STACK.isEmpty()) {
			
			String nextCommand = COMMAND_FUTURE_STACK.pop();
			
			COMMAND_HISTORY_STACK.push(userInput);
			txtCommand.setText(nextCommand);
			
		} else if(!userInput.equals("")) {
			
			COMMAND_HISTORY_STACK.push(userInput);
			txtCommand.setText("");
			
		}
		
		txtCommand.positionCaret(txtCommand.getText().length());
		
	}
	
}
