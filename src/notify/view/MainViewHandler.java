package notify.view;

import notify.Task;
import notify.TaskType;
import notify.logic.*;
import notify.logic.command.Action;
import notify.logic.command.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.sound.sampled.Control;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
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
	private static String DAY_DATE_STRING_FORMAT = "(%1$s, %2$s)";
	private static String DATE_STRING_FORMAT = "(%1$s)";
	private static String TIME_RANGE_STRING_FORMAT = "%1$s to %2$s";
	private static SimpleDateFormat DAY_DATE_FORMAT = new SimpleDateFormat("(EEE, dd MMM)");
	private static SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("EEEE");
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("(dd MMMM)");
	
	// styling for container containing each line of item
	private static double HBOX_NODE_SPACING = 3.5;
	private static Pos HBOX_NODE_ALIGNMENT = Pos.TOP_LEFT;
	private static Insets HEADER_PADDING = new Insets(0, 0, 2, 0);	//top, right, bottom, left
	
	// styling for title of each section
	private static String TITLE_FONT_FAMILY = "Roboto Condensed";
	private static FontWeight TITLE_FONT_WEIGHT = FontWeight.BOLD;
	private static FontPosture TITLE_FONT_POSTURE = FontPosture.REGULAR;
	private static int TITLE_FONT_SIZE = 17;
	private static Paint TITLE_TEXT_FILL = Paint.valueOf("#7E7E45");
	private static Font TITLE_FONT = Font.font(TITLE_FONT_FAMILY, TITLE_FONT_WEIGHT, TITLE_FONT_POSTURE, TITLE_FONT_SIZE);
	
	// styling for subtitle of each section
	private static String SUBTITLE_FONT_FAMILY = "Roboto Condensed";
	private static FontWeight SUBTITLE_FONT_WEIGHT = FontWeight.BOLD;
	private static FontPosture SUBTITLE_FONT_POSTURE = FontPosture.REGULAR;
	private static int SUBTITLE_FONT_SIZE = 12;
	private static Paint SUBTITLE_TEXT_FILL = Paint.valueOf("#B2B262");
	private static Font SUBTITLE_FONT = Font.font(SUBTITLE_FONT_FAMILY, SUBTITLE_FONT_WEIGHT, SUBTITLE_FONT_POSTURE, SUBTITLE_FONT_SIZE);

	// styling for checkbox
	private static String CHECKBOX_FONT_FAMILY = "Roboto";
	private static FontWeight CHECKBOX_FONT_WEIGHT = FontWeight.BOLD;
	private static FontPosture CHECKBOX_FONT_POSTURE = FontPosture.REGULAR;
	private static int CHECKBOX_FONT_SIZE = 10;
	private static Font CHECKBOX_FONT = Font.font(CHECKBOX_FONT_FAMILY, CHECKBOX_FONT_WEIGHT, CHECKBOX_FONT_POSTURE, CHECKBOX_FONT_SIZE);
	
	// styling for task item
	private static String TASK_FONT_FAMILY = "Roboto Slab Regular";
	private static FontWeight TASK_FONT_WEIGHT = FontWeight.BOLD;
	private static FontPosture TASK_FONT_POSTURE = FontPosture.REGULAR;
	private static int TASK_FONT_SIZE = 12;
	private static Font TASK_FONT = new Font (TASK_FONT_FAMILY, TASK_FONT_SIZE);
	
	// styling for task subtext (date day time etc.)
	private static String TASK_SUBTEXT_FONT_FAMILY = "Roboto Condensed";
	private static FontWeight TASK_SUBTEXT_FONT_WEIGHT = FontWeight.BOLD;
	private static FontPosture TASK_SUBTEXT_FONT_POSTURE = FontPosture.REGULAR;
	private static int TASK_SUBTEXT_FONT_SIZE = 11;
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
	
	private static String OVERDUE_TITLE = "Overdue";
	private static String FLOATING_TITLE = "Floating";
	private static String COMING_TITLE = "Coming Soon...";
	
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
	@FXML private TextField txtCommand;
	@FXML private Label lblFeedback;
	@FXML private Pane pnOverlay;
	@FXML private BorderPane bpnSearch;
	
	@FXML
	public void initialize() {
		initDailyView();
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
		
		loadOverdueTask();
		loadFloatingTask();
		loadComingTask();
		loadDailyTask();
		
	}
	
	
	
	/**
	 * Loads the tasks that are overdue.
	 */
	public void loadOverdueTask() {
		
		overdueTasks = logic.getOverdueTasks();

		HBox hboxHeader = generateListHeader(OVERDUE_TITLE, OVERDUE_TEXT_FILL);
		ArrayList<HBox> hboxes = generateListItem(overdueTasks, OVERDUE_TEXT_FILL, OVERDUE_SUBTEXT_FILL);
		
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
		ArrayList<HBox> hboxes = generateListItem(floatingTasks, FLOATING_TEXT_FILL, FLOATING_SUBTEXT_FILL);
		
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
		ArrayList<HBox> hboxes = generateListItem(comingTasks, COMING_TEXT_FILL, COMING_SUBTEXT_FILL);
		
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
			//ArrayList<Task> dailyTasks = new ArrayList<Task>();
			
			HBox hboxHeader = generateListHeader(calendar, DAILY_TEXT_FILL, DAILY_SUBTEXT_FILL);
			ArrayList<HBox> hboxes = generateListItem(dailyTasks, DAILY_TEXT_FILL, DAILY_SUBTEXT_FILL);
			
			vboxes[i].getChildren().clear();
			vboxes[i].getChildren().add(hboxHeader);
			vboxes[i].getChildren().addAll(hboxes);
			
			dailyTasksList.add(dailyTasks);		
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			
		}
		
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
		HBox hbox = createItem(HEADER_PADDING, false, lblTitle);
		
		return hbox;
		
	}
	
	
	
	public String generateTimeStamp(Task task) {
		
		TaskType taskType = task.getTaskType();
		String timeStamp = "";
		
		switch(taskType) {
		
			case DEADLINE:

				timeStamp = generateDeadlineTimestamp(task);
				break;
				
			case RANGE:
				
				timeStamp = generateRangeTimestamp(task);		
				break;
				
			default:
				
				timeStamp = "";
				break;
		
		}
		
		return timeStamp;
		
	}
	
	
	
	public String generateDeadlineTimestamp(Task task) {
		
		Calendar endDate = task.getEndDate();
		Calendar startTime = task.getStartTime();
		Calendar endTime = task.getEndTime();

		String DAY_DATE_PATTERN = "dd MMM yy";
		String TIME_PATTERN = "hh:mm a";
		String DEADLINE_WITH_START_END_TIME_TIMESTAMP = "(%1$s, %2$s to %3$s)";
		String DEADLINE_WITH_END_TIME_TIMESTAMP = "(%1$s, %2$s)";
		String DEADLINE_TIMESTAMP = "(%1$s)";
		
		String DAILY_DEADLINE_WITH_START_END_TIME_TIMESTAMP = "from %1$s to %2$s";
		String DAILY_DEADLINE_WITH_END_TIME_TIMESTAMP = "at %1$s";
		
		String startTimeStamp = "";
		String endDateStamp = "";
		String endTimeStamp = "";
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(DAY_DATE_PATTERN);
		SimpleDateFormat timeFormatter = new SimpleDateFormat(TIME_PATTERN);
		
		String timeStamp = "";

		if(task.isComingSoon() || task.isOverdue()) {

			if(startTime != null) {

				endDateStamp = dateFormatter.format(endDate.getTime());
				startTimeStamp = timeFormatter.format(startTime.getTime());
				endTimeStamp = timeFormatter.format(endTime.getTime());
						
				timeStamp = String.format(DEADLINE_WITH_START_END_TIME_TIMESTAMP, endDateStamp, startTimeStamp, endTimeStamp);
				
			} else if(endTime != null) {

				endDateStamp = dateFormatter.format(endDate.getTime());
				endTimeStamp = timeFormatter.format(endTime.getTime());
				
				timeStamp = String.format(DEADLINE_WITH_END_TIME_TIMESTAMP, endDateStamp, endTimeStamp);
				
			} else {
				
				endDateStamp = dateFormatter.format(endDate.getTime());

				timeStamp = String.format(DEADLINE_TIMESTAMP, endDateStamp);
			}
			
		} else {
		
			if(startTime != null) {
				
				startTimeStamp = timeFormatter.format(startTime.getTime());
				endTimeStamp = timeFormatter.format(endTime.getTime());
				
				timeStamp = String.format(DAILY_DEADLINE_WITH_START_END_TIME_TIMESTAMP, startTimeStamp, endTimeStamp);
				
			} else if(endTime != null) {
				
				endTimeStamp = timeFormatter.format(endTime.getTime());
				
				timeStamp = String.format(DAILY_DEADLINE_WITH_END_TIME_TIMESTAMP, endTimeStamp);
				
			}
			
		}

		return timeStamp;
		
	}
	
	public String generateRangeTimestamp(Task task) {
		
		Calendar startDate = task.getStartDate();
		Calendar endDate = task.getEndDate();
		Calendar startTime = task.getStartTime();
		Calendar endTime = task.getEndTime();

		String DAY_PATTERN = "EEE";
		String DAY_DATE_PATTERN = "dd MMM yy";
		String TIME_PATTERN = "hh:mm a";
		String RANGE_WITH_START_END_TIME_TIMESTAMP = "(%1$s, %2$s to %3$s, %4$s)";
		String RANGE_WITH_NO_TIME = "(%1$s to %2$s)";
		String DAILY_RANGE_WITH_NO_TIME = "till %1$s";
		String DAILY_RANGE_WITH_TIME = "till %1$s %2$s";
		String DAILY_RANGE_NOT_STARTED_TIMESTAMP = "from %1$s till %2$s %3$s";
		String DEADLINE_TIMESTAMP = "";
		
		String startDateStamp = "";
		String startTimeStamp = "";
		String endDateStamp = "";
		String endTimeStamp = "";
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(DAY_DATE_PATTERN);
		SimpleDateFormat timeFormatter = new SimpleDateFormat(TIME_PATTERN);
		
		String timeStamp = "";
		
		if(task.isComingSoon() || task.isOverdue()) {
			
			if(startTime != null && endTime != null) {
				
				startDateStamp = dateFormatter.format(startDate.getTime());
				startTimeStamp = timeFormatter.format(startTime.getTime());
				endDateStamp = dateFormatter.format(endDate.getTime());
				endTimeStamp = timeFormatter.format(endTime.getTime());
				
				timeStamp = String.format(RANGE_WITH_START_END_TIME_TIMESTAMP, startDateStamp, startTimeStamp, endDateStamp, endTimeStamp);
				
				//timeStamp = dateFormat.format(startDate.getTime()) + ", " + timeFormat.format(startTime.getTime()) + " to " + dateFormat.format(endDate.getTime()) + ", " + timeFormat.format(endTime.getTime());
				
			} else {
				
				startDateStamp = dateFormatter.format(startDate.getTime());
				endDateStamp = dateFormatter.format(endDate.getTime());
				
				timeStamp = String.format(RANGE_WITH_NO_TIME, startDateStamp, endDateStamp);
				//timeStamp = dateFormat.format(startDate.getTime()) + " to " + dateFormat.format(endDate.getTime());
				
			}
			
		} else {
			
			if(task.isEndingSoon()) {
				
				dateFormatter = new SimpleDateFormat(DAY_PATTERN);
				
			} else {
				
				dateFormatter = new SimpleDateFormat(DAY_DATE_PATTERN);
				
			}
			
			endDateStamp = dateFormatter.format(endDate.getTime());
			
			if(startTime != null && endTime != null) {
				
				startTimeStamp = timeFormatter.format(startTime.getTime());
				endTimeStamp = timeFormatter.format(endTime.getTime());
				
				if(task.isStarted()) {
					
					timeStamp = String.format(DAILY_RANGE_WITH_TIME, endDateStamp, endTimeStamp);
					
				} else {
					
					timeStamp = String.format(DAILY_RANGE_NOT_STARTED_TIMESTAMP, startTimeStamp, endDateStamp, endTimeStamp);
					
				}
				
			} else {
				
				timeStamp = String.format(DAILY_RANGE_WITH_NO_TIME, endDateStamp);
				
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
		
		String title = DAY_FORMAT.format(calendar.getTime());
		String subtitle = DATE_FORMAT.format(calendar.getTime());
		
		if(calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
			
			title = "Today"; 
			subtitle = DAY_DATE_FORMAT.format(calendar.getTime());
			
		} else if(calendar.get(Calendar.DAY_OF_MONTH) == tomorrow.get(Calendar.DAY_OF_MONTH)) {
			
			title = "Tomorrow";
			subtitle = DAY_DATE_FORMAT.format(calendar.getTime());
			
		}
		
		Label lblTitle = createLabel(title, TITLE_FONT, titleTextFill);
		Label lblSubtitle = createLabel(subtitle, SUBTITLE_FONT, subtitleTextFill);

		HBox hbox = createItem(HEADER_PADDING, false, lblTitle, lblSubtitle);
		
		return hbox;
		
	}
	
	
	
	/**
	 * Generates the items in each list.
	 * @param tasks a list
	 * @param textFill the color of the main text
	 * @param subtextFill the color of the subtext
	 * @return
	 */
	public ArrayList<HBox> generateListItem(ArrayList<Task> tasks, Paint textFill, Paint subtextFill) {
		
		ArrayList<HBox> hboxes = new ArrayList<HBox>();
		CheckBox checkBox = new CheckBox();
		Label lblTaskName = new Label();
		Label lblTaskTime = new Label();
		String subtext = "";
		
		for(int i = 0; i < tasks.size(); i++) {
			
			Task task = tasks.get(i);
			
			checkBox = createCheckbox(task.getTaskId() + "", CHECKBOX_FONT, textFill);
			lblTaskName = createLabel(task.getTaskName(), TASK_FONT, textFill);
			
			if(task.getTaskType() == TaskType.DEADLINE) {

				subtext = generateTimeStamp(task);
				lblTaskTime = createLabel(subtext, TASK_SUBTEXT_FONT, subtextFill);
				
			} else if(task.getTaskType() == TaskType.RANGE) {
				
				subtext = generateTimeStamp(task);
				lblTaskTime = createLabel(subtext, TASK_SUBTEXT_FONT, subtextFill);
				
			}
			
			HBox hbox = createItem(true, checkBox, lblTaskName, lblTaskTime);

			hboxes.add(hbox);
			
		}
		
		return hboxes;
		
	}
	
	public void initDailyView() {
		VBox[] vboxes = { vboxOne, vboxTwo, vboxThree, vboxFour, vboxFive, vboxSix, vboxSeven };

		// get the calendar instance and set the time to today.
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		// format the date
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM");
		

		// temporary dummy tasks
		String[] tasksMonday = {"CS2103T Group Meeting", "User Guide Submission", "Developer Guide Submission"};
		String[] timingsMonday = {"6.00pm", "11.59pm", "11.59pm"};
		
		String[] tasksTuesday = {"GEK1901 Midterms", "MC Meeting"};
		String[] timingsTuesday = {"2.00pm to 4.00pm", "6.30pm to 8.30pm"};
		
		String[] tasksWednesday = {"IT Cell Core Meeting"};
		String[] timingsWednesday = {"6.30pm to 7.30pm"};

		String[] tasksThursday = {};
		String[] timingsThursday = {};
		
		String[] tasksFriday = {"CS2102 Project Meeting", "Collect Parcel", "Dinner with Jim", "Supper with Jim's Mother"};
		String[] timingsFriday = {"12.00pm", "4.00pm", "7.00pm", "11.00pm"};
		
		String[] tasksSaturday = {"Startup Hackathon", "Hair cut"};
		String[] timingsSaturday = {"9.00am to 12.00pm", "3.00pm"};
		
		String[] tasksSunday = {};
		String[] timingsSunday = {};

		String[][] tasks = {tasksMonday, tasksTuesday, tasksWednesday, tasksThursday, tasksFriday, tasksSaturday, tasksSunday};
		String[][] timings = {timingsMonday, timingsTuesday, timingsWednesday, timingsThursday, timingsFriday, timingsSaturday, timingsSunday};

		// set up the view for today and the next 6 days
		for(int i = 0; i < vboxes.length; i++) {
			// create and add the header to the current vbox
			// and populate the current vbox
			initDailyTaskHeader(vboxes[i], calendar, sdf);
			populateDailyTask(vboxes[i], tasks[i], timings[i]);
			
			// increase the day by 1
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
	
	public void initDailyTaskHeader(VBox vbox, Calendar calendar, SimpleDateFormat sdf) {
		// values of today and tomorrow to check with the date and print accordingly
		Calendar today = Calendar.getInstance();
		Calendar tomorrow = Calendar.getInstance();
		
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		
		// -1 due to index starting from 0 but Calendar starts from 1
		int dayIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		// get the day and date
		String day = DAYS_OF_WEEK[dayIndex];
		String date = sdf.format(calendar.getTime());
		
		Label lblTitle = new Label();
		Label lblSubtitle = new Label();
		
		// if statement to check if it's today
		// if it's not, check if it's tomorrow, if it's not then print their day
		if(calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
			lblTitle = createLabel("Today", TITLE_FONT, TITLE_TEXT_FILL);
			lblSubtitle = createLabel(String.format(DAY_DATE_STRING_FORMAT, day, date), SUBTITLE_FONT, SUBTITLE_TEXT_FILL);
		}
		else if(calendar.get(Calendar.DAY_OF_MONTH) == tomorrow.get(Calendar.DAY_OF_MONTH)) {
			lblTitle = createLabel("Tomorrow", TITLE_FONT, TITLE_TEXT_FILL);
			lblSubtitle = createLabel(String.format(DAY_DATE_STRING_FORMAT, day, date), SUBTITLE_FONT, SUBTITLE_TEXT_FILL);
		}
		else {
			lblTitle = createLabel(day, TITLE_FONT, TITLE_TEXT_FILL);
			lblSubtitle = createLabel(String.format(DATE_STRING_FORMAT, date), SUBTITLE_FONT, SUBTITLE_TEXT_FILL);
		}
		
		HBox hbox = createItem(HEADER_PADDING, false, lblTitle, lblSubtitle);

		vbox.getChildren().add(hbox);
	}
	
	public void populateDailyTask(VBox vbox, String[] tasks, String[] timings) {
		for(int i = 0; i < tasks.length; i++) {
			CheckBox checkbox = createCheckbox((i + 14 + tasks.length) + "", CHECKBOX_FONT, DAILY_TEXT_FILL);
			Label task = createLabel(tasks[i], TASK_FONT, DAILY_TEXT_FILL);
			Label time = createLabel(timings[i], TASK_SUBTEXT_FONT, DAILY_SUBTEXT_FILL);
			
			HBox hbox = createItem(true, checkbox, task, time);
			vbox.getChildren().add(hbox);
		}
	}
	
	public void populateOverdueTask() {
		String[] tasks = {"IT Cell Meeting"};
		String[] timings = {"(Friday 9 October, 6.00pm to 8.00pm)"};
		
		for(int i = 0; i < tasks.length; i++) {
			CheckBox checkbox = createCheckbox((i + 21) + "", CHECKBOX_FONT, OVERDUE_TEXT_FILL);
			Label task = createLabel(tasks[i], TASK_FONT, OVERDUE_TEXT_FILL);
			Label time = createLabel(timings[i], TASK_SUBTEXT_FONT, OVERDUE_SUBTEXT_FILL);
			
			HBox hbox = createItem(true, checkbox, task, time);
			vboxOverdue.getChildren().add(hbox);
		}
	}
	
	
	public void populateFloatingTask() {
		String[] tasks = {"Bake brownie", "Buy Birthday Present", "Source for Blazer"};
		//String[] timings = {"10.00am", "12.30pm", "3.00pm", "4.00pm", "6.30pm", "11.59pm", "11.59pm"};
		
		for(int i = 0; i < tasks.length; i++) {
			CheckBox checkbox = createCheckbox((i + 4) + "", CHECKBOX_FONT, FLOATING_TEXT_FILL);
			Label task = createLabel(tasks[i], TASK_FONT, FLOATING_TEXT_FILL);
			//Label time = createLabel(timings[i], TASK_SUBTEXT_FONT, FLOATING_SUBTEXT_FILL);
			
			HBox hbox = createItem(true, checkbox, task);
			vboxFloating.getChildren().add(hbox);
		}
	}
	
	public void populateComingSoonTask() {
		String[] tasks = {"Clean pencil box", "IT Cell Meeting", "CS2103 Meeting", "User Guide Submission", "Developer Guide Submission", "Cut Nails", "IT Cell Core Team Meeting"};
		String[] timings = {"10.00am", "12.30pm", "3.00pm", "4.00pm", "6.30pm", "11.59pm", "11.59pm"};
		
		for(int i = 0; i < tasks.length; i++) {
			CheckBox checkbox = createCheckbox((i + 16) + "", CHECKBOX_FONT, COMING_TEXT_FILL);
			Label label = createLabel(tasks[i], TASK_FONT, COMING_TEXT_FILL);
			Label time = createLabel(timings[i], TASK_SUBTEXT_FONT, COMING_SUBTEXT_FILL);
			
			HBox hbox = createItem(true, checkbox, label, time);
			vboxComing.getChildren().add(hbox);
		}
	}
	
	/**
	 * Creates the HBox with nodes
	 * @param nodes the nodes to be added to the hbox (labels, checkboxes, etc.)
	 * @return the HBox which represents a single line of item to be added into the VBox
	 */
	public HBox createItem(boolean hasCheckbox, Node... nodes) {
		HBox hbox = new HBox();
		hbox.setSpacing(HBOX_NODE_SPACING);
		hbox.setAlignment(HBOX_NODE_ALIGNMENT);
		
		FlowPane flowPane = new FlowPane();
		flowPane.setHgap(HBOX_NODE_SPACING);

		int startIndex = 0;
		if(hasCheckbox) {
			hbox.getChildren().add(nodes[startIndex]);
			startIndex = 1;
		}

		for(int i = startIndex; i < nodes.length; i++) {
			flowPane.getChildren().add(nodes[i]);
		}
		
		hbox.getChildren().add(flowPane);
		
		return hbox;
	}
	
	public HBox createItem(Insets insets, boolean hasCheckbox, Node... nodes) {
		HBox hbox = createItem(hasCheckbox, nodes);
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
	
	public CheckBox createCheckbox(String text, Font font, Paint textFill) {
		CheckBox checkbox = new CheckBox(text);
		checkbox.setFont(font);
		checkbox.setTextFill(textFill);
		
		return checkbox;
	}
	
	public void txtCommandOnKeyPressedHandler(KeyEvent keyEvent) {
		if(keyEvent.getCode() == KeyCode.ENTER) {
			Result result = logic.processCommand(txtCommand.getText());
			
			if(result.getActionPerformed() == Action.SEARCH) {
				
				pnOverlay.setVisible(true);
				bpnSearch.setVisible(true);
				
			}
			
			if(result.getActionPerformed() == Action.BACK) {
				
				pnOverlay.setVisible(false);
				bpnSearch.setVisible(false);
				
			}
			
			load();
			txtCommand.setText("");
		}
	}
	
	public void add(ArrayList<Task> tasklist) {
		Node header = vboxFloating.getChildren().get(0);
		
		//vboxFloating.getChildren().clear();
		//vboxFloating.getChildren().add(header);
		
		/*for(int i = 0; i < tasklist.size(); i++) {
			CheckBox checkbox = createCheckbox((i + 4) + "", CHECKBOX_FONT, FLOATING_TEXT_FILL);
			Label task = createLabel(tasklist.get(i).getTaskName(), TASK_FONT, FLOATING_TEXT_FILL);
			//Label time = createLabel(timings[i], TASK_SUBTEXT_FONT, FLOATING_SUBTEXT_FILL);
			
			HBox hbox = createItem(true, checkbox, task);
			vboxFloating.getChildren().add(hbox);
		}*/

		CheckBox checkbox = createCheckbox((4) + "", CHECKBOX_FONT, FLOATING_TEXT_FILL);
		Label task = createLabel(tasklist.get(0).getTaskName(), TASK_FONT, FLOATING_TEXT_FILL);
		//Label time = createLabel(timings[i], TASK_SUBTEXT_FONT, FLOATING_SUBTEXT_FILL);

		HBox hbox = createItem(true, checkbox, task);
		vboxFloating.getChildren().add(hbox);
		vboxFloating.requestLayout();
	}
}
