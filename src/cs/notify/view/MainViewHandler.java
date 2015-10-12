package cs.notify.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.sound.sampled.Control;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class MainViewHandler {
	// DAY_DATE_SUBTITLE example: (Friday, 23 October)
	// DATE_SUBTITLE example: (23 October)
	private static String[] DAYS_OF_WEEK = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
	private static String DAY_DATE_SUBTITLE = "(%1$s, %2$s)";
	private static String DATE_SUBTITLE = "(%1$s)";
	
	private static double HBOX_NODE_SPACING = 3.5;
	private static Pos HBOX_NODE_ALIGNMENT = Pos.CENTER_LEFT;
	private static Insets HEADER_PADDING = new Insets(0, 0, 5, 0);	//top, right, bottom, left
	
	// styling for title
	private static String TITLE_FONT_FAMILY = "Roboto Condensed";
	private static FontWeight TITLE_FONT_WEIGHT = FontWeight.BOLD;
	private static FontPosture TITLE_FONT_POSTURE = FontPosture.REGULAR;
	private static int TITLE_FONT_SIZE = 17;
	private static Paint TITLE_TEXT_FILL = Paint.valueOf("#7E7E45");
	private static Font TITLE_FONT = Font.font(TITLE_FONT_FAMILY, TITLE_FONT_WEIGHT, TITLE_FONT_POSTURE, TITLE_FONT_SIZE);
	
	// styling for subtitle
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
	private static Paint COMING_SOON_TEXT_FILL = Paint.valueOf("#485A33");
	private static Paint COMING_SOON_SUBTEXT_FILL = Paint.valueOf("#8EB264");
	private static Paint DAILY_TEXT_FILL = Paint.valueOf("#5D5D33");
	private static Paint DAILY_SUBTEXT_FILL = Paint.valueOf("#B2B262");

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
	
	@FXML
	public void initialize() {
		initDailyView();
		populateOverdueTask();
		populateFloatingTask();
		populateComingSoonTask();
	}
	
	public void initDailyView() {
		VBox[] vboxes = { vboxOne, vboxTwo, vboxThree, vboxFour, vboxFive, vboxSix, vboxSeven };

		// get the calendar instance and set the time to today.
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		// format the date
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM");

		// set up the view for today and the next 6 days
		for(int i = 0; i < vboxes.length; i++) {
			// create and add the header to the current vbox
			// and populate the current vbox
			initDailyTaskHeader(vboxes[i], calendar, sdf);
			populateDailyTask(vboxes[i]);
			
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
			lblSubtitle = createLabel(String.format(DAY_DATE_SUBTITLE, day, date), SUBTITLE_FONT, SUBTITLE_TEXT_FILL);
		}
		else if(calendar.get(Calendar.DAY_OF_MONTH) == tomorrow.get(Calendar.DAY_OF_MONTH)) {
			lblTitle = createLabel("Tomorrow", TITLE_FONT, TITLE_TEXT_FILL);
			lblSubtitle = createLabel(String.format(DAY_DATE_SUBTITLE, day, date), SUBTITLE_FONT, SUBTITLE_TEXT_FILL);
		}
		else {
			lblTitle = createLabel(day, TITLE_FONT, TITLE_TEXT_FILL);
			lblSubtitle = createLabel(String.format(DATE_SUBTITLE, date), SUBTITLE_FONT, SUBTITLE_TEXT_FILL);
		}
		
		HBox hbox = createItem(HEADER_PADDING, lblTitle, lblSubtitle);

		vbox.getChildren().add(hbox);
	}
	
	public void populateDailyTask(VBox vbox) {
		String[] tasks = {"Clean pencil box", "IT Cell Meeting", "CS2103 Meeting", "Cut Nails", "IT Cell Core Team Meeting", "User Guide Submission", "Developer Guide Submission"};
		String[] timings = {"10.00am", "12.30pm", "3.00pm", "4.00pm", "6.30pm", "11.59pm", "11.59pm"};
		
		for(int i = 0; i < tasks.length; i++) {
			CheckBox checkbox = createCheckbox((i + 1) + "", CHECKBOX_FONT, DAILY_TEXT_FILL);
			Label task = createLabel(tasks[i], TASK_FONT, DAILY_TEXT_FILL);
			Label time = createLabel(timings[i], TASK_SUBTEXT_FONT, DAILY_SUBTEXT_FILL);
			
			HBox hbox = createItem(checkbox, task, time);
			vbox.getChildren().add(hbox);
		}
	}
	
	public void populateOverdueTask() {
		String[] tasks = {"Clean pencil box", "IT Cell Meeting", "CS2103 Meeting", "User Guide Submission", "Developer Guide Submission", "Cut Nails", "IT Cell Core Team Meeting"};
		String[] timings = {"10.00am", "12.30pm", "3.00pm", "4.00pm", "6.30pm", "11.59pm", "11.59pm"};
		
		for(int i = 0; i < tasks.length; i++) {
			CheckBox checkbox = createCheckbox((i + 1) + "", CHECKBOX_FONT, OVERDUE_TEXT_FILL);
			Label task = createLabel(tasks[i], TASK_FONT, OVERDUE_TEXT_FILL);
			Label time = createLabel(timings[i], TASK_SUBTEXT_FONT, OVERDUE_SUBTEXT_FILL);
			
			HBox hbox = createItem(checkbox, task, time);
			vboxOverdue.getChildren().add(hbox);
		}
	}
	
	
	public void populateFloatingTask() {
		String[] tasks = {"Clean pencil box", "IT Cell Meeting", "CS2103 Meeting", "User Guide Submission", "Developer Guide Submission", "Cut Nails", "IT Cell Core Team Meeting"};
		String[] timings = {"10.00am", "12.30pm", "3.00pm", "4.00pm", "6.30pm", "11.59pm", "11.59pm"};
		
		for(int i = 0; i < tasks.length; i++) {
			CheckBox checkbox = createCheckbox((i + 1) + "", CHECKBOX_FONT, FLOATING_TEXT_FILL);
			Label task = createLabel(tasks[i], TASK_FONT, FLOATING_TEXT_FILL);
			Label time = createLabel(timings[i], TASK_SUBTEXT_FONT, FLOATING_SUBTEXT_FILL);
			
			HBox hbox = createItem(checkbox, task, time);
			vboxFloating.getChildren().add(hbox);
		}
	}
	
	public void populateComingSoonTask() {
		String[] tasks = {"Clean pencil box", "IT Cell Meeting", "CS2103 Meeting", "User Guide Submission", "Developer Guide Submission", "Cut Nails", "IT Cell Core Team Meeting"};
		String[] timings = {"10.00am", "12.30pm", "3.00pm", "4.00pm", "6.30pm", "11.59pm", "11.59pm"};
		
		for(int i = 0; i < tasks.length; i++) {
			CheckBox checkbox = createCheckbox((i + 1) + "", CHECKBOX_FONT, COMING_SOON_TEXT_FILL);
			Label label = createLabel(tasks[i], TASK_FONT, COMING_SOON_TEXT_FILL);
			Label time = createLabel(timings[i], TASK_SUBTEXT_FONT, COMING_SOON_SUBTEXT_FILL);
			
			HBox hbox = createItem(checkbox, label, time);
			vboxComing.getChildren().add(hbox);
		}
	}
	
	/**
	 * Creates the HBox with nodes
	 * @param nodes the nodes to be added to the hbox (labels, checkboxes, etc.)
	 * @return the HBox which represents a single line of item to be added into the VBox
	 */
	public HBox createItem(Node... nodes) {
		HBox hbox = new HBox();
		hbox.setSpacing(HBOX_NODE_SPACING);
		hbox.setAlignment(HBOX_NODE_ALIGNMENT);
		
		for(int i = 0; i < nodes.length; i++) {
			hbox.getChildren().add(nodes[i]);
		}
		
		return hbox;
	}
	
	public HBox createItem(Insets insets, Node... nodes) {
		HBox hbox = createItem(nodes);
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
	public Label createLabel(String text, Font font, Paint textFill, Insets insets) {
		Label label = createLabel(text, font, textFill);
		label.setPadding(insets);
		
		return label;
	}
	
	public CheckBox createCheckbox(String text, Font font, Paint textFill) {
		CheckBox checkbox = new CheckBox(text);
		checkbox.setFont(font);
		checkbox.setTextFill(textFill);
		
		return checkbox;
	}
}
