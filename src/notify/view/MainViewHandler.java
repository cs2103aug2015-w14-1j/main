/**
 * Author: Kenneth Ho Chee Chong
 * Matric No: A0125364J
 * For CS2103T - Notify
 */

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
	// DAY_DATE_SUBTITLE example: (Friday, 23 October)
	// DATE_SUBTITLE example: (23 October)
	private static String[] DAYS_OF_WEEK = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
	//private static String DAY_DATE_STRING_FORMAT = "(%1$s, %2$s)";
	//private static String DATE_STRING_FORMAT = "(%1$s)";
	//private static String TIME_RANGE_STRING_FORMAT = "%1$s to %2$s";

	private static String SHORT_DAY_PATTERN = "EEE";
	private static String LONG_DAY_PATTERN = "EEEE";
	private static String SHORT_DATE_PATTERN = "dd MMM yy";
	private static String LONG_DATE_PATTERN = "dd MMMM yy";
	private static String TIME_PATTERN = "hh:mm a";
	
	//private static SimpleDateFormat DAY_DATE_FORMAT = new SimpleDateFormat("(EEE, dd MMM)");
	//private static SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("EEEE");
	//private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("(dd MMMM)");
	
	
	
	// styling for container containing each line of item
	private static double HBOX_NODE_SPACING = 3.5;
	private static Pos HBOX_NODE_ALIGNMENT = Pos.TOP_LEFT;
	private static Insets HEADER_PADDING = new Insets(0, 0, 2, 0);	//top, right, bottom, left
	
	
	
	// font family settings for different types of text displayed
	private static String TITLE_FONT_FAMILY = "Roboto Condensed";
	private static String SUBTITLE_FONT_FAMILY = "Roboto Condensed";
	private static String CHECKBOX_FONT_FAMILY = "Roboto";
	private static String TASK_FONT_FAMILY = "Roboto Slab Regular";
	private static String TASK_SUBTEXT_FONT_FAMILY = "Roboto Condensed";
	
	
	
	// font weight settings for different types of text displayed
	private static FontWeight TITLE_FONT_WEIGHT = FontWeight.BOLD;
	private static FontWeight SUBTITLE_FONT_WEIGHT = FontWeight.BOLD;
	private static FontWeight CHECKBOX_FONT_WEIGHT = FontWeight.BOLD;
	private static FontWeight TASK_FONT_WEIGHT = FontWeight.BOLD;
	private static FontWeight TASK_SUBTEXT_FONT_WEIGHT = FontWeight.BOLD;
	
	
	
	// font posture settings for different types of text displayed
	private static FontPosture TITLE_FONT_POSTURE = FontPosture.REGULAR;
	private static FontPosture SUBTITLE_FONT_POSTURE = FontPosture.REGULAR;
	private static FontPosture CHECKBOX_FONT_POSTURE = FontPosture.REGULAR;
	private static FontPosture TASK_FONT_POSTURE = FontPosture.REGULAR;
	private static FontPosture TASK_SUBTEXT_FONT_POSTURE = FontPosture.REGULAR;
	
	
	
	// font size settings for different types of text displayed
	private static int TITLE_FONT_SIZE = 17;
	private static int SUBTITLE_FONT_SIZE = 12;
	private static int CHECKBOX_FONT_SIZE = 10;
	private static int TASK_FONT_SIZE = 12;
	private static int TASK_SUBTEXT_FONT_SIZE = 11;
	

	
	// font object that is set up by the settings above
	private static Font TITLE_FONT = Font.font(TITLE_FONT_FAMILY, TITLE_FONT_WEIGHT, TITLE_FONT_POSTURE, TITLE_FONT_SIZE);
	private static Font SUBTITLE_FONT = Font.font(SUBTITLE_FONT_FAMILY, SUBTITLE_FONT_WEIGHT, SUBTITLE_FONT_POSTURE, SUBTITLE_FONT_SIZE);
	private static Font CHECKBOX_FONT = Font.font(CHECKBOX_FONT_FAMILY, CHECKBOX_FONT_WEIGHT, CHECKBOX_FONT_POSTURE, CHECKBOX_FONT_SIZE);
	private static Font TASK_FONT = new Font (TASK_FONT_FAMILY, TASK_FONT_SIZE);
	private static Font TASK_SUBTEXT_FONT = Font.font(TASK_SUBTEXT_FONT_FAMILY, TASK_SUBTEXT_FONT_WEIGHT, TASK_SUBTEXT_FONT_POSTURE, TASK_SUBTEXT_FONT_SIZE);
	
	
	
	// font color for different type of task
	private static Paint OVERDUE_TEXT_FILL = Paint.valueOf("#5D322E");
	private static Paint OVERDUE_SUBTEXT_FILL = Paint.valueOf("#B26059");
	private static Paint FLOATING_TEXT_FILL = Paint.valueOf("#2D4D5A");
	private static Paint FLOATING_SUBTEXT_FILL = Paint.valueOf("#5997B2");
	private static Paint COMING_TEXT_FILL = Paint.valueOf("#485A33");
	private static Paint COMING_SUBTEXT_FILL = Paint.valueOf("#8EB264");
	private static Paint DAILY_TEXT_FILL = Paint.valueOf("#5D5D33");
	private static Paint DAILY_SUBTEXT_FILL = Paint.valueOf("#B2B262");
	private static Paint SEARCH_TEXT_FILL = Paint.valueOf("#FFFFFF");
	private static Paint SEARCH_SUBTEXT_FILL = Paint.valueOf("#FFFFFF");
	private static Paint ERROR_MESSAGE_FILL = Paint.valueOf("#CC181E");
	private static Paint FEEDBACK_MESSAGE_FILL = Paint.valueOf("#16A085");
	
	

	private static String DEADLINE_DATE_START_END_TIME_TIMESTAMP_FORMAT = "%1$s, %2$s to %3$s";
	private static String DEADLINE_DATE_END_TIME_TIMESTAMP_FORMAT = "%1$s, %2$s";
	private static String DEADLINE_END_DATE_TIMESTAMP_FORMAT = "%1$s";
	private static String DEADLINE_FROM_START_TO_END_TIME_TIMESTAMP_FORMAT = "from %1$s to %2$s";
	private static String DEADLINE_AT_END_TIME_TIMESTAMP_FORMAT = "at %1$s";
	
	

	private static String RANGE_START_DATE_TIME_TO_END_DATE_TIME_TIMESTAMP_FORMAT = "%1$s, %2$s to %3$s, %4$s";
	private static String RANGE_START_DATE_TO_END_DATE_TIMESTAMP_FORMAT = "%1$s to %2$s";
	private static String RANGE_FROM_START_TIME_TILL_END_DATE_TIME_TIMESTAMP_FORMAT = "from %1$s till %2$s, %3$s";
	private static String RANGE_TILL_END_DATE_TIME_TIMESTAMP_FORMAT = "till %1$s, %2$s";
	private static String RANGE_TILL_END_DATE_TIMESTAMP_FORMAT = "till %1$s";
	
	private static String OVERDUE_TITLE = "Overdue";
	private static String FLOATING_TITLE = "Floating";
	private static String COMING_TITLE = "Coming Soon...";
	
	private static String INVALID_COMMAND_MESSAGE = "Invalid command '%1$s'. Please try again.";
	private static String ADDED_MESSAGE = "Task added: '%1$s'.";
	private static String EDITED_MESSAGE = "Task '%1$s' edited.";
	private static String DELETED_MESSAGE = "Task '%1$s' deleted.";
	
	private static String SEARCH_INPUT = "";
	
	private static Stack<String> COMMAND_HISTORY_STACK = new Stack<String>();
	private static Stack<String> COMMAND_FUTURE_STACK = new Stack<String>();
	
	private Logic logic;
	
	private ArrayList<Task> overdueTasks;
	private ArrayList<Task> floatingTasks;
	private ArrayList<Task> comingTasks;
	private ArrayList<ArrayList<Task>> dailyTasksList;

	
	
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
	
	
	
	@FXML private TextField txtCommand;
	@FXML private Label lblFeedback;
	
	
	
	@FXML private Pane pnOverlay;
	@FXML private BorderPane bpnSearch;
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
		
		return false;
		
	}
	
	public boolean isHelpViewVisible() {
		
		return gpnHelp.isVisible();
		
	}
	
	
	
	/**
	 * Loads the tasks that are overdue.
	 */
	public void loadOverdueTask() {
		
		overdueTasks = logic.getOverdueTasks();

		HBox hboxHeader = generateListHeader(OVERDUE_TITLE, OVERDUE_TEXT_FILL);
		ArrayList<HBox> hboxes = generateListItem(overdueTasks, false, OVERDUE_TEXT_FILL, OVERDUE_SUBTEXT_FILL);
		
		vboxOverdue.getChildren().clear();
		vboxOverdue.getChildren().add(hboxHeader);
		vboxOverdue.getChildren().addAll(hboxes);
		
	}
	
	
	
	/**
	 * Loads the tasks that does not have any dates (floating tasks).
	 */
	public void loadFloatingTask() {
		
		floatingTasks = logic.getFloatingTasks();
		
		HBox hboxHeader = generateListHeader(FLOATING_TITLE, FLOATING_TEXT_FILL);
		ArrayList<HBox> hboxes = generateListItem(floatingTasks, false, FLOATING_TEXT_FILL, FLOATING_SUBTEXT_FILL);
		
		vboxFloating.getChildren().clear();
		vboxFloating.getChildren().add(hboxHeader);
		vboxFloating.getChildren().addAll(hboxes);
		
	}
	
	
	
	/**
	 * Loads the upcoming tasks (tasks that have dates more than 1 week from current date).
	 */
	public void loadComingTask() {
		
		comingTasks = logic.getComingSoonTasks();

		HBox hboxHeader = generateListHeader(COMING_TITLE, COMING_TEXT_FILL);
		ArrayList<HBox> hboxes = generateListItem(comingTasks, false, COMING_TEXT_FILL, COMING_SUBTEXT_FILL);
		
		vboxComing.getChildren().clear();
		vboxComing.getChildren().add(hboxHeader);
		vboxComing.getChildren().addAll(hboxes);
		
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
			
			HBox hboxHeader = generateListHeader(calendar, DAILY_TEXT_FILL, DAILY_SUBTEXT_FILL);
			ArrayList<HBox> hboxes = generateListItem(dailyTasks, false, DAILY_TEXT_FILL, DAILY_SUBTEXT_FILL);
			
			vboxes[i].getChildren().clear();
			vboxes[i].getChildren().add(hboxHeader);
			vboxes[i].getChildren().addAll(hboxes);
			
			dailyTasksList.add(dailyTasks);		
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			
		}
		
	}
	
	
	public void loadSearchResult(ArrayList<Task> searchResults) {
		
		ArrayList<Task> completedTasks = new ArrayList<Task>();
		ArrayList<Task> uncompletedTasks = new ArrayList<Task>();
		
		completedTasks = filterTask(searchResults, true);
		uncompletedTasks = filterTask(searchResults, false);

		ArrayList<HBox> hboxesCompleted = generateListItem(completedTasks, true, SEARCH_TEXT_FILL, SEARCH_SUBTEXT_FILL);
		ArrayList<HBox> hboxesUncompleted = generateListItem(uncompletedTasks, true, SEARCH_TEXT_FILL, SEARCH_SUBTEXT_FILL);
		
		vboxSearchCompleted.getChildren().clear();
		vboxSearchCompleted.getChildren().addAll(hboxesCompleted);
		
		vboxSearchUncompleted.getChildren().clear();
		vboxSearchUncompleted.getChildren().addAll(hboxesUncompleted);
		
	}
	
	public void showSearchView() {
		
		pnOverlay.setVisible(true);
		bpnSearch.setVisible(true);
		
	}
	
	public void showHelpView() {
		
		pnOverlay.setVisible(true);
		gpnHelp.setVisible(true);
		
	}
	
	public void hideSearchView() {
		
		pnOverlay.setVisible(false);
		bpnSearch.setVisible(false);
		
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
	public HBox generateListHeader(String title, Paint titleTextFill) {
		
		Label lblTitle = createLabel(title, TITLE_FONT, titleTextFill);
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
	public HBox generateListHeader(Calendar calendar, Paint titleTextFill, Paint subtitleTextFill) {
		
		Calendar today = Calendar.getInstance();
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		
		SimpleDateFormat dayFormatter = new SimpleDateFormat(LONG_DAY_PATTERN);
		SimpleDateFormat dateFormatter = new SimpleDateFormat(LONG_DATE_PATTERN);
		
		String title = dayFormatter.format(calendar.getTime());
		String subtitle = dateFormatter.format(calendar.getTime());
		
		if(calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
			
			subtitle = title + ", " + subtitle;
			title = "Today"; 
			
		} else if(calendar.get(Calendar.DAY_OF_MONTH) == tomorrow.get(Calendar.DAY_OF_MONTH)) {

			subtitle = title + ", " + subtitle;
			title = "Tomorrow";
			
		}
		
		Label lblTitle = createLabel(title, TITLE_FONT, titleTextFill);
		Label lblSubtitle = createLabel(subtitle, SUBTITLE_FONT, subtitleTextFill);

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
	public ArrayList<HBox> generateListItem(ArrayList<Task> tasks, boolean isSearch, Paint textFill, Paint subtextFill) {
		
		ArrayList<HBox> hboxes = new ArrayList<HBox>();
		CheckBox checkBox = new CheckBox();
		Label lblTaskName = new Label();
		Label lblTaskTime = new Label();
		String subtext = "";
		
		for(int i = 0; i < tasks.size(); i++) {
			
			Task task = tasks.get(i);
			
			checkBox = createCheckbox(task.getTaskId() + "", task.isCompleted(), CHECKBOX_FONT, textFill);
			lblTaskName = createLabel(task.getTaskName(), TASK_FONT, textFill);
			
			if(task.getTaskType() == TaskType.DEADLINE) {

				subtext = generateTimeStamp(task, isSearch);
				lblTaskTime = createLabel(subtext, TASK_SUBTEXT_FONT, subtextFill);
				
			} else if(task.getTaskType() == TaskType.RANGE) {
				
				subtext = generateTimeStamp(task, isSearch);
				lblTaskTime = createLabel(subtext, TASK_SUBTEXT_FONT, subtextFill);
				
			}
			
			HBox hbox = createItem(true, isSearch, checkBox, lblTaskName, lblTaskTime);

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
		
		//FlowPane flowPane = new FlowPane();
		//flowPane.setHgap(HBOX_NODE_SPACING);

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
	public Label createLabel(String text, Font font) {
		Label label = new Label(text);
		label.setFont(font);
		
		return label;
	}
	
	/**
	 * Overloading method for creating a label with text color
	 * @param text the text of the label
	 * @param font the font of the label such as font family, font weight, font posture and font size
	 * @param textFill the color of the text fill
	 * @return a label which contains the text and formatting
	 */
	public Label createLabel(String text, Font font, Paint textFill) {
		Label label = createLabel(text, font);
		label.setTextFill(textFill);
		
		return label;
	}
	
	/**
	 * Overloading method for creating a label with padding
	 * @param text the text of the label
	 * @param font the font of the label such as font family, font weight, font posture and font size
	 * @param textFill the color of the text fill
	 * @param insets padding for the label
	 * @return a label which contains the text and formatting
	 */
	/*public Label createLabel(String text, Font font, Paint textFill, Insets insets) {
		Label label = createLabel(text, font, textFill);
		label.setPadding(insets);
		
		return label;
	}*/
	
	public CheckBox createCheckbox(String text, boolean isChecked, Font font, Paint textFill) {
		
		CheckBox checkbox = new CheckBox(text);
		checkbox.setFont(font);
		checkbox.setTextFill(textFill);
		checkbox.setSelected(isChecked);
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
				
				SEARCH_INPUT = userInput;
				
				loadSearchResult(result.getResults());
				showSearchView();
				setFeedbackLabel("", FEEDBACK_MESSAGE_FILL);
				
				break;
				
			case BACK:
				
				hideSearchView();
				hideHelpView();
				setFeedbackLabel("", FEEDBACK_MESSAGE_FILL);
				
				break;
				
			case DISPLAY:
				
				System.out.println("MainViewHandler: display command");
				setFeedbackLabel("", FEEDBACK_MESSAGE_FILL);
				
				break;
				
			/*case HELP:
				
				showHelpView();
				
				break;*/
				
			case EXIT:

				System.exit(0);
				
				break;
			
			default:
				
				if(isSearchViewVisible()) {
					
					Result searchResult = logic.processCommand(SEARCH_INPUT);
					loadSearchResult(searchResult.getResults());
					
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
	
	public void txtCommandOnKeyPressedHandler(KeyEvent keyEvent) {
		String userInput = txtCommand.getText().trim();
		
		try {

			KeyCode keyCode = keyEvent.getCode();

			if(keyCode == KeyCode.ENTER && !userInput.equals("")) {
				
				Result result = logic.processCommand(userInput);
				processResult(result, userInput);
				
				addCommandHistory(userInput);

				txtCommand.setText("");
				
			} else if(keyCode == KeyCode.BACK_SPACE) {
				
				if(txtCommand.getText().equals("") && isSearchViewVisible()) {
					
					hideSearchView();
					
				} else if(txtCommand.getText().equals("") && isHelpViewVisible()) {
					
					hideHelpView();
					
				}
				
			} else if(keyCode == KeyCode.UP) {
				
				if(!COMMAND_HISTORY_STACK.isEmpty()) {
					
					String previousCommand = COMMAND_HISTORY_STACK.pop();
					String currentCommand = txtCommand.getText().trim();
					
					if(!currentCommand.equals("")) {
						
						COMMAND_FUTURE_STACK.push(currentCommand);
						
					}
					
					txtCommand.setText(previousCommand);
					
				}
				
			} else if(keyCode == KeyCode.DOWN) {
				
				String currentCommand = txtCommand.getText().trim();
				
				if(!COMMAND_FUTURE_STACK.isEmpty()) {
					
					String nextCommand = COMMAND_FUTURE_STACK.pop();
					
					COMMAND_HISTORY_STACK.push(currentCommand);
					txtCommand.setText(nextCommand);
					
				} else if(!currentCommand.equals("")) {
					
					COMMAND_HISTORY_STACK.push(currentCommand);
					txtCommand.setText("");
					
				}
				
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
	
}
