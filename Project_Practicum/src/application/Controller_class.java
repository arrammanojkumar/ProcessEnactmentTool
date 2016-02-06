package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import processElements.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

@SuppressWarnings("unused")
public class Controller_class implements Initializable {
	
	/************************************ Tabbed Pane ******************************************************/
	public TabPane Tabpane;
	public AnchorPane LifeCycleAnchor;
	public Label msgFileFound;
	public Label msgNewFileFound;

	private int current_tab=-1;
	
	/************************************ Common To All ******************************************************/
	
	private LifeCycleListController lstLifeCycleList = new LifeCycleListController();
	private LifeCycleListController emptyLifeCycleList = new LifeCycleListController(lstLifeCycleList);
			
	public LifeCycleStepListController lstSelectedLifeCycleStep = new LifeCycleStepListController();
	public LifeCycleStepListController lstLocalLCECLifeCycleStep = new LifeCycleStepListController();

	// This is the link to the root Step list controller object and an emptylist
	private StepListController lstStepList = new StepListController();
	private StepListController emptyStepList = new StepListController(lstStepList);

	// This is the link to the root Task list controller object and an emptylist
	private TaskListController lstTaskList = new TaskListController();
	private TaskListController emptyTaskList = new TaskListController(lstTaskList);

	// This is the link to the root Condition list controller object and an empty list
	private ConditionListController lstConditionList = new ConditionListController();
	private ConditionListController emptyConditionList = new ConditionListController(lstConditionList);

	// This is the link to the root EffortCategory list controller object and an empty list
	private EffortCategoryListController lstEffortCategoryList = new EffortCategoryListController();
	private EffortCategoryListController emptyEffortCategoryList = new EffortCategoryListController(lstEffortCategoryList);
			

	// This is the link to the root Artifact list controller object and an empty list
	private ArtifactListController lstArtifactList = new ArtifactListController();
	private final ArtifactListController emptyArtifactList = new ArtifactListController(lstArtifactList);

	// This is the link to the Plan list controller object and an empty list
	private PlanListController lstPlanList = new PlanListController();
	private final PlanListController emptyPlanList = new PlanListController(lstPlanList);

	// This is the link to the Interruption list controller object and an empty list
	private InterruptionListController lstInterruptionList = new InterruptionListController();
	private final InterruptionListController emptyInterruptionList = new InterruptionListController(
			lstInterruptionList);

	// This is the link to the Defect list controller object and an empty list
	private DefectListController lstDefectList = new DefectListController();
	private DefectListController emptyDefectList = new DefectListController(lstDefectList);

	private LifeCycleStepListController lstLifeCycleStepList = new LifeCycleStepListController();
	private LifeCycleStepListController emptyLifeCycleStepList = new LifeCycleStepListController(lstLifeCycleStepList);

	
	/************************************ Life Cycles variables ******************************************************/
	
	// This is the link to the root Life Cycle list controller object and an empty list
	public Label msgLifeCycles; // Label for the life cycle select list
	private String[] lstLifeCycleSelectList = new String[] {}; // The current

	public ListView jlstLifeCycles; // The List view of life cycles
	private int ndxJlstLifeCycleSelector = -1; // The selector for the List view
	private boolean lglLifeCyclesListIsBeingUpdated = false; // Flag to avoid
	
	// These are the attributes that define a specific life cycle
	public Label msgLCName;
	public TextField fldLCName; // The life cycle name
	private String strLocalLCName = "";
	private String strSelectedLCName = "";
	public Label msgLCDesc;
	public TextArea fldLCDesc; // The life cycle description
	private String strLocalLCDesc = "";
	private String strSelectedLCDesc = "";

	// These are the action buttons that are used to create and manipulate Life Cycles
	public Button btnLCNew;
	public Button btnLCNewAbove;
	public Button btnLCNewBelow;
	public Button btnLCMvUp;
	public Button btnLCMvDn;
	public Button btnLCDelete;
	public Button btnLCSave;
	public Button btnLCClear;

	// A Life Cycle can be of several forms depending on two selectors:
	// 1. ComposedOfOptions
	// 2. IterationOptions
	//
	// The following are the two fixed parts of the panel layout for these two selectors

	// The attributes that define and specify the fixed part for the ComposedOfOptions
	private String[] strComposedOfOptions = { "Life Cycle Steps", // 0
	"Subordinate Life Cycles" }; // 1
	public ComboBox jcbLifeCycleComposedOfOption;
	private int ndxLifeCycleComposedOfOption = 0; // Selector index
	private boolean lglLocalLifeCycleComposedOfSteps = true; // Local state value
	private boolean lglSelectedLifeCycleComposedOfSteps = true; // Selected state value

	// The attributes that define and specify the fixed part for the IterationOptions
	private String[] strIterationOptions = { "Does not iterate", // 0
											 "Iterates a fixed number of times", // 1
											 "Iterates until conditions are satisfied" }; // 2
	public ComboBox jcbLifeCycleIterationOption;
	private int ndxLocalLifeCycleIterationSelector = 0; // Local state value
	private int ndxSelectedLifeCycleIterationSelector = 0; // Selected state value

	// These attributes define the variable part of the Life Cycle Panel: Life Cycle Steps
	// The life cycle steps List view elements
	private String[] lstLifeCycleStepsSelectListSource = new String[] {};
	public ListView jlstLifeCycleSteps;
	private int ndxJlstLifeCycleStepsSelector = -1;
	private boolean lglLifeCycleStepsListIsBeingUpdated = false;
	private ScrollPane jspLifeCycleSteps;

	// The life cycle steps ComboBox elements used to populate the life cycle
	// steps List view
	private LifeCycleStepListController cmbLifeCycleStepsSelectList = new LifeCycleStepListController(
			emptyLifeCycleStepList);
	public ComboBox jcbLifeCycleSteps;
	private int ndxCmbLifeCycleStepsSelector = -1;

	public Label msgLifeCycleSteps = new Label("Life Cycle Steps:");
	public Label msgstepselect;
	public LifeCycleStepListController lstLocalLifeCycleStepsList = new LifeCycleStepListController();
	public LifeCycleStepListController lstSelectedLifeCycleStepsList = lstLocalLifeCycleStepsList;

	// The life cycle steps ComboBox elements used to populate the life cycle steps List view
	public Label msgLCSSelect = new Label("Select one of these Life Cycle Steps:");

	// The life cycle steps action buttons
	public Button btnLCSNew;
	public Button btnLCSAbove;
	public Button btnLCSBelow;
	public Button btnLCSMvUp;
	public Button btnLCSMvDn;
	public Button btnLCSDelete;

	// buttons for updating Effort Category
	public Button btnLCECUpdate;
	public Label lableEffortCategory;
	public Label labelSelect;
	public Label labelECDefinitions;

	// public Button fldNewFileName;

	public Label msgSubLifeCycle = new Label("Subordinate Life Cycles:");

	// The subordinate life cycle ComboBox elements used to populate the
	// subordinate life cycle List view
	public Label msgSubLifeCycleSelect = new Label(
			"Select one of these Subordinate Life Cycles:");

	// These attributes define the variable part of the Life Cycle Panel:
	// Subordinate Life Cycles
	//
	// The subordinate life cycle List view elements
	private LifeCycleListController lstLocalSubLifeCycleList = new LifeCycleListController(
			emptyLifeCycleList);
	private LifeCycleListController lstSelectedSubLifeCycleList = lstLocalSubLifeCycleList;
	private String[] lstSubLifeCycleSelectListSource = new String[] {};
	public ListView jlstSubLifeCycle;
	private int ndxJlstSubLifeCycleSelector = -1;
	private boolean lglSubLifeCycleListIsBeingUpdated = false;

	// The subordinate life cycle ComboBox elements used to populate the
	// subordinate life cycle List view
	private LifeCycleListController cmbSubLifeCycleSelectList = new LifeCycleListController(
			emptyLifeCycleList);
	public ComboBox jcbSubLifeCycle;
	private int ndxCmbSubLifeCycleSelector = -1;

	private String[] cmbModelLifeCycleSteps;
	// The subordinate life cycle action buttons
	public Button btnSubLCNew;
	public Button btnSubLCAbove;
	public Button btnSubLCBelow;
	public Button btnSubLCMvUp;
	public Button btnSubLCMvDn;
	public Button btnSubLCDelete;

	public ComboBox jcbLCEC;
	public ComboBox jcbLCECSelect;
	//
	// These attributes define the five Effort Categories for the life cycle

	// Effort Categories: Artifacts
	private EffortCategoryListController cmbLCECArtifactSelectList = new EffortCategoryListController(
			emptyEffortCategoryList);
	private String[] cmbModelLCECArtifact = new String[] {};
	private int ndxCmbLCECArtifactSelector = -1;
	private ArtifactListController lstLocalLCECArtifact = new ArtifactListController(
			emptyArtifactList);
	private ArtifactListController lstSelectedLCECArtifact = new ArtifactListController(
			emptyArtifactList);

	// Effort Categories: Plans
	private EffortCategoryListController cmbLCECPlanSelectList = new EffortCategoryListController(
			emptyEffortCategoryList);
	private String[] cmbModelLCECPlan = new String[] {};
	private int ndxCmbLCECPlanSelector = -1;
	private PlanListController lstLocalLCECPlan = new PlanListController(
			emptyPlanList);
	private PlanListController lstSelectedLCECPlan = new PlanListController(
			emptyPlanList);

	// Effort Categories: Interruptions
	private EffortCategoryListController cmbLCECInterruptionSelectList = new EffortCategoryListController(
			emptyEffortCategoryList);
	private String[] cmbModelLCECInterruption = new String[] {};
	private int ndxCmbLCECInterruptionSelector = -1;
	private InterruptionListController lstLocalLCECInterruption = new InterruptionListController(
			lstInterruptionList);
	private InterruptionListController lstSelectedLCECInterruption = new InterruptionListController(
			lstInterruptionList);

	// Effort Categories: Defects
	private EffortCategoryListController cmbLCECDefectSelectList = new EffortCategoryListController(
			emptyEffortCategoryList);
	private String[] cmbModelLCECDefect = new String[] {};
	private int ndxCmbLCECDefectSelector = -1;
	private DefectListController lstLocalLCECDefect = new DefectListController(
			lstDefectList);
	private DefectListController lstSelectedLCECDefect = new DefectListController(
			lstDefectList);

	// Effort Categories: Others
	private EffortCategoryListController cmbLCECOtherSelectList = new EffortCategoryListController(
			emptyEffortCategoryList);
	private String[] cmbModelLCECOther = new String[] {};
	private int ndxCmbLCECOtherSelector = -1;
	private String strLocalLCECOtherName = "";
	private String strSelectedLCECOtherName = "";
	private String strLocalLCECOtherDesc = "";
	private String strSelectedLCECOtherDesc = "";

	/**
	 * The following attributes define the messages and buttons that establish
	 * the generic portion of the user interface (These fields are visible
	 * regardless of which tabbed pane is currently visible
	 */
	// These components form the labels and the input fields for the file names to read in and write out the definitions
	public TextField fldFileName;
	public TextField fldNewFileName;
	public Button btnReadData;
	// These local variable are used to support the input of the PET definitions
	private String strLocalFileName;

	// These local variable are used to support the creation of a new set of PET definitions
	private String strLocalNewFileName;
	public File fileInputFile;
	public File fileOutputFile;
	public Definitions definitionsRepository;

	String[] cmbModelSubLifeCycle;

	public int ndxlifecycleeffortcategory = -1;

	public Button btnLCItersNew;
	public Button btnLCItersAbove;
	public Button btnLCItersBelow;
	public Button btnLCItersMvUp;
	public Button btnLCItersMvDn;
	public Button btnLCItersDelete;
	public Button btnSaveDefinitions;
	public String[] cmbModelLCEC;
	public ListView jlstLifeCycleIterationCondition;

	// for default
	public ComboBox jcbLCECDefaultSelect;
	public Label labelSelectDefault;
	private int lcstepDefaultDelivarable = 0;
	private String[] cmbModelLCECDefaultArtifact = new String[] {};
	private String[] cmbModelLCECDefaultPlan = new String[] {};
	private String[] cmbModelLCECDefaultInterruption = new String[] {};
	private String[] cmbModelLCECDefaultDefect = new String[] {};
	private String[] cmbModelLCECDefaultOther = new String[] {};

	// The iteration condition comboBox elements used to populate the Iteration
	// Condition List view
	public Label msgLifeCycleIterationCondition;
	private ConditionListController cmbLifeCycleIterationConditionSelectList = new ConditionListController(
			emptyConditionList);
	private String[] cmbModelLifeCycleIterationCondition;
	public ComboBox jcbLifeCycleIterationCondition;
	private int ndxCmbLifeCycleIterationConditionSelector = -1;

	public Label msgLifeCycleIterationConditions;
	public ConditionListController lstLocalLifeCycleIterationConditionList = new ConditionListController();
	public ConditionListController lstSelectedLifeCycleIterationConditionList = lstLocalLifeCycleIterationConditionList;
	public String[] lstLifeCycleIterationConditionSelectListSource = new String[] {};
	public int ndxJlstLifeCycleIterationConditionSelector = -1;
	public boolean lglLifeCycleIterationConditionListIsBeingUpdated = false;

	public ObservableList cmbModelLCECArtifactObservable;
	private boolean selectListLifeCycleitemselected=false;
	private int ndxJlstLifeCycledeleteSelector = 0;
	
	
	/************************************ Life Cycle Steps variables ******************************************************/

	public ListView<String> jlstSteps;
	public TextField fldStepName;
	public TextArea fldStepDesc;
	public Button btnStepsNew;
	public Button btnStepsNewAbove;
	public Button btnStepsNewBelow;
	public Button btnStepsMvUp;
	public Button btnStepsMvDn;
	public Button btnStepsDelete;

	public Button btnStepsSave;
	public Button btnStepsClear;

	public Button btnPreConditionNew;
	public Button btnPreConditionAbove;
	public Button btnPreConditionBelow;
	public Button btnPreConditionMvUp;
	public Button btnPreConditionMvDn;
	public Button btnPreConditionDelete;
	public ListView<String> jlstPreCondition;
	public ComboBox jcbPreCondition;

	public Button btnPostConditionNew;
	public Button btnPostConditionAbove;
	public Button btnPostConditionBelow;
	public Button btnPostConditionMvUp;
	public Button btnPostConditionMvDn;
	public Button btnPostConditionDelete;
	public ListView<String> jlstPostCondition;
	public ComboBox jcbPostCondition;

	public Button btnStepTaskNew;
	public Button btnStepTaskAbove;
	public Button btnStepTaskBelow;
	public Button btnStepTaskMvUp;
	public Button btnStepTaskMvDn;
	public Button btnStepTaskDelete;
	public ListView<String> jlstStepTask;
	public ComboBox jcbStepTask;

	public String[] cmbModelPreCondition;
	public String[] cmbModelPostCondition;
	public String[] cmbModelStepTask;

	public String[] lstStepSelectListSource = new String[] {}; // The step
	// select list
	// The JList of steps
	public boolean lglStepListIsBeingUpdated = false; // The flag to stop
	// definition looping
	public int ndxJlstStepSelector = -1; // The index of the current selected
	// step
	public boolean lglPostConditionListIsBeingUpdated = false;
	// The Name and Description elements for the selected Step
	// Step Name
	public String strLocalStepName = "";
	public String strSelectedStepName = ""; // Step Description
	public String strLocalStepDesc = "";
	public String strSelectedStepDesc = "";

	public ConditionListController lstSelectedPreConditionList = new ConditionListController(
			emptyConditionList);
	public ConditionListController lstLocalPreConditionList = new ConditionListController(
			emptyConditionList);
	public String[] lstPreConditionSelectListSource = new String[] {};
	public String[] lstPostConditionSelectListSource = new String[] {};
	public int ndxJlstPreConditionSelector = -1;
	public int ndxJlstPostConditionSelector = -1;
	public boolean lglStepTaskListIsBeingUpdated = false;
	public boolean lglPreConditionListIsBeingUpdated = false;

	public ArtifactListController lstLocalArtifactsProducedList = new ArtifactListController(
			emptyArtifactList);
	public ArtifactListController lstSelectedArtifactsProducedList = new ArtifactListController(
			emptyArtifactList);
	public ArtifactListController cmbArtifactsProducedSelectList = new ArtifactListController(
			emptyArtifactList);

	public ConditionListController lstSelectedPostConditionList = new ConditionListController(
			emptyConditionList);
	public ConditionListController lstLocalPostConditionList = new ConditionListController(
			emptyConditionList);
	public String[] lstStepTaskSelectListSource = new String[] {};
	public ConditionListController cmbPreConditionSelectList = new ConditionListController(
			emptyConditionList);
	public int ndxCmbSelPreConditionSelector = -1;
	public int ndxCmbSelPostConditionSelector = -1;
	public int ndxJlstStepTaskSelector = -1;
	public TaskListController lstLocalStepTaskList = new TaskListController(
			emptyTaskList);
	public TaskListController lstSelectedStepTaskList = new TaskListController(
			emptyTaskList);
	public int ndxCmbSelStepTaskSelector = -1;
	public TaskListController cmbStepTaskSelectList = new TaskListController(
			emptyTaskList);

	public ConditionListController cmbPostConditionSelectList = new ConditionListController(
			emptyConditionList);
	

	
	/************************************ Tasks variables ******************************************************/
	// These are the attributes the define the Task list and the creation and
	// editing of a Task
	private String[] lstTaskSelectListSource = new String[] {}; // The step
	// select list
	public ListView<String> jlstTasks = new ListView<String>(); // The JList of
	// tasks
	private int ndxJlstTaskSelector = -1; // The selector of the current task
	private boolean lglTaskListIsBeingUpdated = false; // The flag to avoid
	// update looping

	// These are the attributes that are used to define a specific task
	public TextField fldTaskName;
	private String strLocalTaskName = "";
	private String strSelectedTaskName = "";
	public TextArea fldTaskDesc;
	private String strLocalTaskDesc = "";
	private String strSelectedTaskDesc = "";
	// These are the action buttons that define and manage the list of tasks
	public Button btnTasksNew;
	public Button btnTasksNewAbove;
	public Button btnTasksNewBelow;
	public Button btnTasksMvUp;
	public Button btnTasksMvDn;
	public Button btnTasksDelete;
	public Button btnTasksSave;
	public Button btnTasksClear;

	/************************************ Conditions variables ******************************************************/

	public Button btnConditionsSave;
	public Button btnConditionsNew;
	public Button btnConditionsNewAbove;
	public Button btnConditionsNewBelow;
	public Button btnConditionsDelete;
	public Button btnConditionsMvUp;
	public Button btnConditionsMvDn;
	public Label msgConditions;
	private String strLocalConditionName = "";
	public TextField fldConditionName;
	private String strLocalConditionDesc = "";
	public TextArea fldConditionDesc;
	private int ndxJlstConditionSelector = -1;
	private String[] lstConditionsSelectListSource = new String[] {};
	private boolean lglConditionListIsBeingUpdated = false;
	public ScrollPane ConditionScrollPane;
	public String strSelectedConditionName = "";
	private String strSelectedConditionDesc = "";
	public ListView<String> jlstConditions;
	public boolean lglConditionState = true;
	public boolean lglLocalSelectedConditionState = true;
	public ComboBox jcbConditionState;

	
	
	/************************************ Effort categories variables ******************************************************/

	private String[] lstEffortCategoriesSelectListSource = new String[] {};

	private boolean lglEffortCategoryListIsBeingUpdated = false;
	public ListView<String> jlstEffortCategories;
	private int ndxJlstEffortCategorySelector = -1;
	private int ndxLocalEffortCategoryState = 0;
	private ArtifactListController lstLocalEffortCategoryArtifactList = new ArtifactListController(
			emptyArtifactList);
	private String[] lstEffortCategoryArtifactSelectListSource = new String[] {};
	private boolean lglEffortCategoryArtifactListIsBeingUpdated = false;
	private ArtifactListController cmbEffortCategoryNewArtifactSelectList = new ArtifactListController(
			emptyArtifactList);
	private String[] cmbModelEffortCategoryNewArtifact;
	private int ndxCmbEffortCategoryNewArtifactSelector = 0;

	private PlanListController lstLocalEffortCategoryPlanList = new PlanListController(
			emptyPlanList);
	private String[] lstEffortCategoryPlanSelectListSource = new String[] {};
	private boolean lglEffortCategoryPlanListIsBeingUpdated = false;
	private PlanListController cmbEffortCategoryNewPlanSelectList = new PlanListController(
			emptyPlanList);
	private String[] cmbModelEffortCategoryNewPlan;
	private int ndxCmbEffortCategoryNewPlanSelector = 0;

	private InterruptionListController lstLocalEffortCategoryInterruptionList = new InterruptionListController(
			lstInterruptionList);
	private String[] lstEffortCategoryInterruptionSelectListSource = new String[] {};
	private boolean lglEffortCategoryInterruptionListIsBeingUpdated = false;
	private InterruptionListController cmbEffortCategoryNewInterruptionSelectList = new InterruptionListController(
			lstInterruptionList);
	private String[] cmbModelEffortCategoryNewInterruption;
	private int ndxCmbEffortCategoryNewInterruptionSelector = 0;

	private DefectListController lstLocalEffortCategoryDefectList = new DefectListController(
			lstDefectList);
	private String[] lstEffortCategoryDefectSelectListSource = new String[] {};
	private boolean lglEffortCategoryDefectListIsBeingUpdated = false;
	private DefectListController cmbEffortCategoryNewDefectSelectList = new DefectListController(
			lstDefectList);
	private String[] cmbModelEffortCategoryNewDefect;
	private int ndxCmbEffortCategoryNewDefectSelector = 0;

	public Label msgEffortCategoryArtifact;
	public Label msgEffortCategoryNewArtifact;
	public ComboBox<String> jcbEffortCategoryNewArtifact;
	public Button btnEffortCategoryArtNew;
	public Button btnEffortCategoryArtAbove;
	public Button btnEffortCategoryArtBelow;
	public Button btnEffortCategoryArtMvUp;
	public Button btnEffortCategoryArtMvDn;
	public Button btnEffortCategoryArtDelete;
	public ListView<String> jlstEffortCategoryArtifact;

	public Label msgEffortCategoryPlan;
	public Label msgEffortCategoryNewPlan;
	public ComboBox<String> jcbEffortCategoryNewPlan;
	public Button btnEffortCategoryPlanNew;
	public Button btnEffortCategoryPlanAbove;
	public Button btnEffortCategoryPlanBelow;
	public Button btnEffortCategoryPlanMvUp;
	public Button btnEffortCategoryPlanMvDn;
	public Button btnEffortCategoryPlanDelete;
	public ListView<String> jlstEffortCategoryPlan;

	public Label msgEffortCategoryInterruption;
	public Label msgEffortCategoryNewInterruption;
	public ComboBox<String> jcbEffortCategoryNewInterruption;
	public Button btnEffortCategoryInterruptionNew;
	public Button btnEffortCategoryInterruptionAbove;
	public Button btnEffortCategoryInterruptionBelow;
	public Button btnEffortCategoryInterruptionMvUp;
	public Button btnEffortCategoryInterruptionMvDn;
	public Button btnEffortCategoryInterruptionDelete;
	public ListView<String> jlstEffortCategoryInterruption;
	private ArtifactListController lstSelectedEffortCategoryArtifactList = new ArtifactListController(
			emptyArtifactList);
	private PlanListController lstSelectedEffortCategoryPlanList = new PlanListController(
			emptyPlanList);
	private InterruptionListController lstSelectedEffortCategoryInterruptionList = new InterruptionListController(
			lstInterruptionList);
	private DefectListController lstSelectedEffortCategoryDefectList = new DefectListController(
			lstDefectList);
	public Label msgEffortCategoryDefect;
	public Label msgEffortCategoryNewDefect;
	public ComboBox<String> jcbEffortCategoryNewDefect;
	public Button btnEffortCategoryDefectNew;
	public Button btnEffortCategoryDefectAbove;
	public Button btnEffortCategoryDefectBelow;
	public Button btnEffortCategoryDefectMvUp;
	public Button btnEffortCategoryDefectMvDn;
	public Button btnEffortCategoryDefectDelete;
	public ListView<String> jlstEffortCategoryDefect;

	public ComboBox<String> jcbEffortCategoryOption;

	private int ndxJlstEffortCategoryDefectSelector = -1;
	private int ndxJlstEffortCategoryArtifactSelector = -1;
	private int ndxJlstEffortCategoryPlanSelector = -1;
	private int ndxJlstEffortCategoryInterruptionSelector = -1;

	private String strLocalEffortCategoryName = "";
	private String strLocalEffortCategoryDesc = "";
	private String strSelectedEffortCategoryDesc = "";
	private String strSelectedEffortCategoryName = "";

	public TextField fldEffortCategoryName;
	public TextArea fldEffortCategoryDesc;

	public int ndxSelectedEffortCategoryState = 0;

	public Button btnEffortCategoriesNew;
	public Button btnEffortCategoriesNewAbove;
	public Button btnEffortCategoriesNewBelow;
	public Button btnEffortCategoriesSave;
	public Button btnEffortCategoriesMvUp;
	public Button btnEffortCategoriesMvDn;
	public Button btnEffortCategoriesDelete;
	public Button btnEffortCategoriesClear;
	
	/************************************ Artifacts variables ******************************************************/
	public ListView<String> jlstArtifacts;
	private String strLocalArtifactName = "";
	public TextField fldArtifactName;
	private String strLocalArtifactDesc = "";
	public TextArea fldArtifactDesc;
	private int ndxJlstArtifactSelector = -1;
	private String[] lstArtifactsSelectListSource = new String[] {};
	private boolean lglArtifactListIsBeingUpdated = false;
	public ScrollPane ArtifactScrollPane;
	public String strSelectedArtifactName = "";
	private String strSelectedArtifactDesc = "";

	public Button btnArtifactsSave;
	public Button btnArtifactsNew;
	public Button btnArtifactsNewAbove;
	public Button btnArtifactsNewBelow;
	public Button btnArtifactsDelete;
	public Button btnArtifactsMvUp;
	public Button btnArtifactsMvDn;
	public Label msgArtifacts;

	public Label msgArtifactName;
	public Label msgArtifactDesc;

	//
	// These are the attributes that establish the Artifacts Used definition of
	// a Step

	private ArtifactListController lstLocalArtifactsUsedList = new ArtifactListController(
			emptyArtifactList);
	private ArtifactListController lstSelectedArtifactsUsedList = new ArtifactListController(
			emptyArtifactList);
	private String[] lstArtUsedSelectListSource = new String[] {};
	public ListView<String> jlstArtUsed = new ListView<String>();
	private int ndxJlstArtUsedSelector = -1;
	private boolean lglArtUsedListIsBeingUpdated = false;

	// These are the attributes that establish the Artifact comboBox to populate
	// the Artifacts Used JList
	private ArtifactListController cmbArtifactsUsedSelectList = new ArtifactListController(
			emptyArtifactList);
	public String[] cmbModelArtUsed;
	@SuppressWarnings("rawtypes")
	public ComboBox jcbArtUsed;
	private int ndxCmbArtUsedSelector = -1;

	// These are the action buttons for the Artifacts Used portion of a Step
	public Button btnArtUsedNew;
	public Button btnArtUsedAbove;
	public Button btnArtUsedBelow;
	public Button btnArtUsedMvUp;
	public Button btnArtUsedMvDn;
	public Button btnArtUsedDelete;

	// These are the attributes that establish the Artifacts Used definition of
	// a Step

	private String[] lstArtProducedSelectListSource = new String[] {};
	public ListView<String> jlstArtProduced = new ListView<String>();
	private int ndxJlstArtProducedSelector = -1;
	private boolean lglArtProducedListIsBeingUpdated = false;

	// These are the attributes that establish the Artifact comboBox to define
	// Artifacts Produced

	private String[] cmbModelArtProduced;

	@SuppressWarnings("rawtypes")
	public ComboBox jcbArtProduced;
	private int ndxCmbArtProducedSelector = -1;

	// These are the action buttons for the Artifacts Used portion of a Step
	public Button btnArtProducedNew;
	public Button btnArtProducedAbove;
	public Button btnArtProducedBelow;
	public Button btnArtProducedMvUp;
	public Button btnArtProducedMvDn;
	public Button btnArtProducedDelete;
	
	
	/************************************ Plans variables ******************************************************/
	public ListView<String> jlstPlans;
	private String strLocalPlanName = "";
	public TextField fldPlanName;
	private String strLocalPlanDesc = "";
	public TextArea fldPlanDesc;
	private int ndxJlstPlanSelector = -1;
	private String[] lstPlansSelectListSource = new String[] {};
	private boolean lglPlanListIsBeingUpdated = false;
	public ScrollPane PlanScrollPane;
	public String strSelectedPlanName = "";
	private String strSelectedPlanDesc = "";

	public Button btnPlansSave;
	public Button btnPlansNew;
	public Button btnPlansNewAbove;
	public Button btnPlansNewBelow;
	public Button btnPlansDelete;
	public Button btnPlansMvUp;
	public Button btnPlansMvDn;
	public Label msgPlans;

	public Label msgPlanName;
	public Label msgPlanDesc;

	
	
	/************************************ Interruptions variables ******************************************************/
	public ListView<String> jlstInterruptions;
	private String strLocalInterruptionName = "";
	public TextField fldInterruptionName;
	private String strLocalInterruptionDesc = "";
	public TextArea fldInterruptionDesc;
	private int ndxJlstInterruptionSelector = -1;
	private String[] lstInterruptionsSelectListSource = new String[] {};
	private boolean lglInterruptionListIsBeingUpdated = false;
	public ScrollPane InterruptionScrollPane;
	public String strSelectedInterruptionName = "";
	private String strSelectedInterruptionDesc = "";

	public Button btnInterruptionsSave;
	public Button btnInterruptionsNew;
	public Button btnInterruptionsNewAbove;
	public Button btnInterruptionsNewBelow;
	public Button btnInterruptionsDelete;
	public Button btnInterruptionsMvUp;
	public Button btnInterruptionsMvDn;
	public Label msgInterruptions;

	public Label msgInterruptionName;
	public Label msgInterruptionDesc;

	
	
	/**
	 * Defects Buttons
	 */
	public String strLocalDefectName = "";
	public TextField fldDefectName;
	public String strLocalDefectDesc = "";
	public TextArea fldDefectDesc;

	private int ndxJlstDefectSelector = -1;
	private String[] lstDefectsSelectListSource = new String[] {};
	private boolean lglDefectListIsBeingUpdated = false;
	
	public ScrollPane DefectScrollPane;
	public String strSelectedDefectName = "";
	private String strSelectedDefectDesc = "";
	public ListView<String> jlstDefects;
	
	public Button btnDefectsSave;
	public Button btnDefectsNew;
	public Button btnDefectsNewAbove;
	public Button btnDefectsNewBelow;
	public Button btnDefectsDelete;
	public Button btnDefectsMvUp;
	public Button btnDefectsMvDn;

	
	/**
	 * This function takes care about the tabs in the tabbed pane and it chages its index according to it
	 */
	public void performTabChange() {
		int ndx = Tabpane.getSelectionModel().getSelectedIndex();
		if(ndx==current_tab)
		{
			switch(ndx)
			{
			case 0:
				int l1=jlstLifeCycles.getSelectionModel().getSelectedIndex();
				jlstLifeCycles.getSelectionModel().select(l1);
				int l2=jlstLifeCycleSteps.getSelectionModel().getSelectedIndex();
				jlstLifeCycleSteps.getSelectionModel().select(l2);
				break;
			case 1:
				int l3=jlstSteps.getSelectionModel().getSelectedIndex();
				jlstSteps.getSelectionModel().select(l3);
				int l4=jlstPreCondition.getSelectionModel().getSelectedIndex();
				jlstPreCondition.getSelectionModel().select(l4);
				int l5=jlstStepTask.getSelectionModel().getSelectedIndex();
				jlstStepTask.getSelectionModel().select(l5);
				break;
			case 2:
				int l6=jlstTasks.getSelectionModel().getSelectedIndex();
				jlstTasks.getSelectionModel().select(l6);
				int l7=jlstArtUsed.getSelectionModel().getSelectedIndex();
				jlstArtUsed.getSelectionModel().select(l7);
				int l8=jlstArtProduced.getSelectionModel().getSelectedIndex();
				jlstArtProduced.getSelectionModel().select(l8);
				break;
			case 3:
				int l9=jlstConditions.getSelectionModel().getSelectedIndex();
				jlstConditions.getSelectionModel().select(l9);
				break;
			case 4:
				int l10=jlstEffortCategories.getSelectionModel().getSelectedIndex();
				jlstEffortCategories.getSelectionModel().select(l10);
				int l11=jlstEffortCategoryDefect.getSelectionModel().getSelectedIndex();
				jlstEffortCategoryDefect.getSelectionModel().select(l11);
				int l12=jlstEffortCategoryInterruption.getSelectionModel().getSelectedIndex();
				jlstEffortCategoryInterruption.getSelectionModel().select(l12);
				int l13=jlstEffortCategoryPlan.getSelectionModel().getSelectedIndex();
				jlstEffortCategoryPlan.getSelectionModel().select(l13);
				int l14=jlstEffortCategoryArtifact.getSelectionModel().getSelectedIndex();
				jlstEffortCategoryArtifact.getSelectionModel().select(l14);
				break;
			case 5:
				int l15=jlstArtifacts.getSelectionModel().getSelectedIndex();
				jlstArtifacts.getSelectionModel().select(l15);
				break;
			case 6:
				int l16=jlstPlans.getSelectionModel().getSelectedIndex();
				jlstPlans.getSelectionModel().select(l16);
				break;
			case 7:
				int l17=jlstInterruptions.getSelectionModel().getSelectedIndex();
				jlstInterruptions.getSelectionModel().select(l17);
				break;
			case 8:
				int l18=jlstDefects.getSelectionModel().getSelectedIndex();
				jlstDefects.getSelectionModel().select(l18);
				break;
			}

			return;
		}
		current_tab=ndx;

		// The following process each of the tab activations. The WakeUp routines do not know
		// if the list on the pane has a selected element or not, so in order to use them when
		// changing sub-panes and when loading the panes from a file, we have to process the
		// buttons here as opposed to within the wake up routine.

		// In this case, we want the item that was selected the last time the user was at the pane
		// during the same session to still be selected when returning to that pane, so we do not
		// make any changes to the selection index.
		switch (ndx) {
		// Life Cycle Pane
		case 0:
			performWakeUpLifeCyclesPane();
			checkLifeCycleButtons();
			break;

			// Steps Pane
		case 1:
			performWakeUpStepsPane();
			checkStepButtons();
			break;

			// Tasks Pane
		case 2:
			performWakeUpTasksPane();
			checkTaskButtons();
			break;

			// Conditions Pane
		case 3:
			performWakeUpConditionsPane();
			checkConditionButtons();
			break;

			// Effort Categories Pane
		case 4:
			performWakeUpEffortCategoriesPane();
			checkEffortCategoryButtons();
			break;

			// Artifacts Pane
		case 5:
			performWakeUpArtifactsPane();
			checkArtifactButtons();
			break;

			// Plans Pane
		case 6:
			performWakeUpPlansPane();
			checkPlanButtons();
			break;

			// Interruption Pane
		case 7:
			performWakeUpInterruptionsPane();
			checkInterruptionButtons();
			break;

			// Defect Pane
		case 8:
			performWakeUpDefectsPane();
			checkDefectButtons();
			break;

		default: // No action needed if it is not a case where a wake up is required.
		}
	}
	
	
	/************************************************************************************************************
	 * This routine checks to see if the definitions file is there and if so,
	 * sets flags so it is possible to read in the file and load the
	 * definitions. If a file is not found, an error message is displayed and
	 * the button is disabled. This routine does *not* verify that the data
	 * within the file is properly structured, so invalid data in the file can
	 * mess up this program, depending on how well the xml library input routine
	 * does its job.
	 */
	public void rtnCheckFldFileName(){
		strLocalFileName = fldFileName.getText().trim();		// Whenever the text area for the file name is changed
		if (strLocalFileName.length()<=0){						// this routine is called to see if it is a valid filename
			msgFileFound.setTextFill(Color.BLACK);			// If the text area is empty, there is no error, but
			msgFileFound.setText("");							// the button to load the definitions is not enabled
			btnReadData.setDisable(true);
		} else {												// If there is something in the file name text area
			fileInputFile = new File(strLocalFileName);			// we check to see if there is a file that matches that name.
			if (!fileInputFile.exists()) {						// If there is no file that that matches the name, we display
				msgFileFound.setTextFill(Color.RED.darker());	// a red error message and make sure that
				msgFileFound.setText("File not found!");		// the button to load the definitions is disabled
				btnReadData.setDisable(true);
			} else {											// If there is a file that matches the name, we set up things
				msgFileFound.setTextFill(Color.GREEN.darker());// so that file can be read and used to
				msgFileFound.setText("File found!");			// initialize the definitions.
				btnReadData.setDisable(false);
			}
		}
	}

	/************************************************************************************************************
	 * This routine checks to see if the file name being proposed for a save
	 * operation is already there so an appropriate warning message can be
	 * displayed. If there is no output file name, the routine warns that the
	 * file name is empty and disables the save button. If there is a file name,
	 * it checks to see if a file by that name already exists and gives a
	 * warning message. If the file name is there but it does not match an
	 * existing file, a message saying that this is a new file is displayed and
	 * the save button is enabled
	 */
	public void rtnCheckFldNewFileName(){
		msgNewFileFound.setVisible(true);						// Make the title for the text area visible
		strLocalNewFileName = fldNewFileName.getText().trim();	// Fetch the input the user has entered into the text area
		if (strLocalNewFileName.length()<=0){					// If that text string is empty, tell the user this
			msgNewFileFound.setText("");				
			btnSaveDefinitions.setDisable(true);				// And disable the button to save the definitions
		} else {												// If there is something in the file name text area
			fileOutputFile = new File(strLocalNewFileName);		// Check to see if the file name matches an existing file
			if (!fileOutputFile.exists()) {						// If not, then tell the user with a green message that the
				msgNewFileFound.setTextFill(Color.GREEN.darker());// file is a valid new file name and
				msgNewFileFound.setText("New file name!");		// enable the button to save the file
				btnSaveDefinitions.setDisable(false);	
			} else {											// If the file matches an existing file, tell the user
				msgNewFileFound.setTextFill(Color.RED.darker());// with a dark red message that using this
				msgNewFileFound.setText("Replace this file?");	// filename will replace an existing file
				btnSaveDefinitions.setDisable(false);				// but enable the save... replace is valid
			}
		}
	}

	/************************************************************************************************************
	 * This is the routine to read the definition file after the user has
	 * pressed the button to make that happen.
	 */
	public void readTheDefinitions() {
		// The input text area title is altered
		// to specify

		XStream xstream = new XStream(); // Create the XStream object

		try {
			try {
				// Establish the requirements to read in the xml file and create
				// the definitionsRepository

				BufferedReader in = new BufferedReader(new FileReader(
						fileInputFile));
				ObjectInputStream objectIn = xstream
						.createObjectInputStream(in);
				definitionsRepository = (Definitions) objectIn.readObject();
				objectIn.close();
			} catch (IOException ex) {
				System.exit(0);
			} catch (ClassNotFoundException ex) {
				System.exit(0);
			}

			// Reaching here tells us that the definitionsRepository was read in
			// properly, so we can extract
			// the various lists that define the definitions for this
			// application
			lstArtifactList = definitionsRepository.getArtifactListController();
			lstConditionList = definitionsRepository
					.getConditionListController();
			lstDefectList = definitionsRepository.getDefectListController();
			lstEffortCategoryList = definitionsRepository
					.getEffortCategoryListController();
			lstInterruptionList = definitionsRepository
					.getInterruptionListController();
			lstLifeCycleList = definitionsRepository
					.getLifeCycleListController();
			lstPlanList = definitionsRepository.getPlanListController();
			lstStepList = definitionsRepository.getStepListController();
			lstTaskList = definitionsRepository.getTaskListController();
			lstLifeCycleStepList = definitionsRepository
					.getLifeCycleStepListController();

		} catch (XStreamException e) {
			System.exit(0);
		}
		performWakeUpDefectsPane();
		ndxJlstDefectSelector = -1;
		jlstDefects.getSelectionModel().clearSelection();
		checkDefectButtons();

		performWakeUpInterruptionsPane();
		ndxJlstInterruptionSelector = -1;
		jlstInterruptions.getSelectionModel().clearSelection();
		checkInterruptionButtons();

		performWakeUpPlansPane();
		ndxJlstPlanSelector = -1;
		jlstPlans.getSelectionModel().clearSelection();
		checkPlanButtons();

		performWakeUpEffortCategoriesPane();
		ndxJlstEffortCategorySelector = -1;
		jlstEffortCategories.getSelectionModel().clearSelection();
		checkEffortCategoryButtons();

		performWakeUpConditionsPane();
		ndxJlstConditionSelector = -1;
		jlstConditions.getSelectionModel().clearSelection();
		checkConditionButtons();

		performWakeUpArtifactsPane();
		ndxJlstArtifactSelector = -1;
		jlstArtifacts.getSelectionModel().clearSelection();
		checkArtifactButtons();

		performWakeUpTasksPane();
		ndxJlstTaskSelector = -1;
		jlstTasks.getSelectionModel().clearSelection();
		checkTaskButtons();

		performWakeUpStepsPane();
		ndxJlstStepSelector = -1;
		jlstSteps.getSelectionModel().clearSelection();
		checkStepButtons();

		performWakeUpLifeCyclesPane();
		ndxJlstLifeCycleSelector = -1;
		jlstLifeCycles.getSelectionModel().clearSelection();
		checkLifeCycleButtons();

		// This causes the Life Cycles Pane to be activated

	}

	/************************************************************************************************************
	 * This is the routine to save the definition file after the user has
	 * pressed the button to make that happen.
	 */
	public void saveTheDefinitions() {
		// Create the definitionsRepository and define the various lists within
		// it
		definitionsRepository = new Definitions(lstArtifactList,
				lstConditionList, lstDefectList, lstEffortCategoryList,
				lstInterruptionList, lstLifeCycleList, lstPlanList,
				lstStepList, lstTaskList, lstLifeCycleStepList);

	

		// Create the output stream and write out the XML
		//
		XStream xstream = new XStream();
		FileWriter fn;
		try {
			fn = new FileWriter(fileOutputFile);
			ObjectOutputStream out = xstream.createObjectOutputStream(fn);
			String xml = xstream.toXML(definitionsRepository);
			System.out.println(xml);
			out.writeObject(definitionsRepository);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IO exception writing " + fileOutputFile);
			System.exit(0);
		}
		// Getting to this point says that the write operation completed without
		// a problem
	}

	/*********************************************************************************************************************
	 * Life Cycles
	 * 
	 */
	/**
	 * This method is invoked when the user selects one of the life cycle items
	 * in the primary List view for the Life Cycle pane. If the index has actually
	 * changed, establish the selected and local copies of the attributes.
	 */
	public void selectListLifeCycleListItemSwitchSelected() {

		int ndx = jcbLCEC.getSelectionModel().getSelectedIndex();
		Object item = jcbLCEC.getSelectionModel().getSelectedItem();
		ndxJlstLifeCycleSelector = ndx;

		/**
		 * Artifacts-0, plans-1, interruptions-2, defects-3, others-4
		 */

		switch (ndx) {
		case 0: // artifact
			cmbLCECArtifactSelectList = lstEffortCategoryList
			.extractEffortCategoryArtifact();
			cmbModelLCECArtifact = cmbLCECArtifactSelectList.buildSelectList();
			cmbModelLCECArtifactObservable = FXCollections
					.observableArrayList(cmbModelLCECArtifact);
			jcbLCECSelect.setItems(cmbModelLCECArtifactObservable);
			break;
		case 1: // plans

			cmbLCECArtifactSelectList = lstEffortCategoryList
			.extractEffortCategoryPlan();
			cmbModelLCECArtifact = cmbLCECArtifactSelectList.buildSelectList();
			cmbModelLCECArtifactObservable = FXCollections
					.observableArrayList(cmbModelLCECArtifact);
			jcbLCECSelect.setItems(cmbModelLCECArtifactObservable);
			// performLCECPlanSelection();
			break;
		case 2: // interruptions
			cmbLCECArtifactSelectList = lstEffortCategoryList
			.extractEffortCategoryInterruption();
			cmbModelLCECArtifact = cmbLCECArtifactSelectList.buildSelectList();
			cmbModelLCECArtifactObservable = FXCollections
					.observableArrayList(cmbModelLCECArtifact);
			jcbLCECSelect.setItems(cmbModelLCECArtifactObservable);
			break;

		case 3: // defects
			cmbLCECArtifactSelectList = lstEffortCategoryList
			.extractEffortCategoryDefect();
			cmbModelLCECArtifact = cmbLCECArtifactSelectList.buildSelectList();
			cmbModelLCECArtifactObservable = FXCollections
					.observableArrayList(cmbModelLCECArtifact);
			jcbLCECSelect.setItems(cmbModelLCECArtifactObservable);
			break;
		case 4: // others
			cmbLCECArtifactSelectList = lstEffortCategoryList
			.extractEffortCategoryOther();
			cmbModelLCECArtifact = cmbLCECArtifactSelectList.buildSelectList();
			cmbModelLCECArtifactObservable = FXCollections
					.observableArrayList(cmbModelLCECArtifact);
			jcbLCECSelect.setItems(cmbModelLCECArtifactObservable);
			break;
		}

	}

	/**
	 * This method is invoked when the user selects one of the life cycle items
	 * in the primary List view for the Life Cycle pane. If the index has actually
	 * changed, establish the selected and local copies of the attributes.
	 * 
	 */
	
	public void selectListLifeCycleListItemSelected() {
		selectListLifeCycleitemselected=true;
		// Fetch the selected index from the List view.
		if (lglLifeCyclesListIsBeingUpdated)
			return;
		int ndx = jlstLifeCycles.getSelectionModel().getSelectedIndex();

		// If the index matches the current local copies, there is no need to
		// proceed
		if (ndxJlstLifeCycleSelector == ndx)
			return;

		// If the index is different, then we must update the local copies
		ndxJlstLifeCycleSelector = ndx; // Remember the new index

		// This sets the selected and local copy of the Name
		strSelectedLCName = lstLifeCycleList.getLifeCycle(
				ndxJlstLifeCycleSelector).getName();
		strLocalLCName = strSelectedLCName; // The selected and local versions
		// start out
		fldLCName.setText(strLocalLCName); // being the same, but the user might
		// change that

		// This sets the selected and local copy of the Description
		strSelectedLCDesc = lstLifeCycleList.getLifeCycle(
				ndxJlstLifeCycleSelector).getDesc();
		strLocalLCDesc = strSelectedLCDesc; // Do the same thing for the
		// Description
		fldLCDesc.setText(strLocalLCDesc);

		// This sets the selected copies of the various Effort Category Items
		// Artifact

		// This sets the selected and local copy of the ComposedOfSteps flag

		lglSelectedLifeCycleComposedOfSteps = lstLifeCycleList.getLifeCycle(
				ndxJlstLifeCycleSelector).getLifeCycleComposedOfSteps();
		lglLocalLifeCycleComposedOfSteps = lglSelectedLifeCycleComposedOfSteps;

		// This established the proper state of the comboBox for "composedOf"

		if (lglLocalLifeCycleComposedOfSteps) {
			jcbLifeCycleComposedOfOption.getSelectionModel().select(0);
		} else {
			jcbLifeCycleComposedOfOption.getSelectionModel().select(1);
		}

		// Based on the ComposedOfSteps flag, establish this optional part of
		// the pane
		configureComposedOf();

		if (lglSelectedLifeCycleComposedOfSteps) {
			// Setup the selected and local copies of the ComposedOf Steps list
			lstSelectedLifeCycleStepsList = lstLifeCycleList.getLifeCycle(
					ndxJlstLifeCycleSelector).getLifeCycleStepList();
			lstLocalLifeCycleStepsList = lstLifeCycleList.getLifeCycle(
					ndxJlstLifeCycleSelector).getLifeCycleStepListCopy();

			jcbLifeCycleComposedOfOption.getSelectionModel().select(0);
			performWakeUpLifeCycleStepsGUI();
		} else {
			// Setup the selected and local copies of the ComposedOf Subordinate
			// Life Cycle list
			lstSelectedSubLifeCycleList = lstLifeCycleList.getLifeCycle(
					ndxJlstLifeCycleSelector).getSubLifeCycleList();
			lstLocalSubLifeCycleList = lstLifeCycleList.getLifeCycle(
					ndxJlstLifeCycleSelector).getSubLifeCycleListCopy();

			jcbLifeCycleComposedOfOption.getSelectionModel().select(1);
			performWakeUpSubLifeCycleGUI();
		}

		// This sets the selected and local copy of the Iteration Mode selector
		ndxSelectedLifeCycleIterationSelector = lstLifeCycleList.getLifeCycle(
				ndxJlstLifeCycleSelector).getLifeCycleIterMode();
		ndxLocalLifeCycleIterationSelector = ndxSelectedLifeCycleIterationSelector;
		jcbLifeCycleIterationOption.getSelectionModel().select(
				ndxLocalLifeCycleIterationSelector);

		// Based on the ndxSelectedLCIterSelector
		ndxLocalLifeCycleIterationSelector = ndxSelectedLifeCycleIterationSelector;

		// Set up the ComboBox that is used to add new conditions to the
		// Iteration Conditions List view
		jcbLifeCycleIterationOption.getSelectionModel().select(
				ndxLocalLifeCycleIterationSelector);

		// And then process the dependent fields
		if (ndxSelectedLifeCycleIterationSelector == 0) {
			// This code deals with the no iteration case
			setupIterMode0();
		} else if (ndxSelectedLifeCycleIterationSelector == 1) {
			// This code deals with the fixed number of iterations case
			setupIterMode1();
		} else if (ndxSelectedLifeCycleIterationSelector == 2) {
			// This code deals with the iteration until conditions are satisfied
			// case
			setupIterMode2();

			lstSelectedLifeCycleIterationConditionList = lstLifeCycleList
					.getLifeCycle(ndxJlstLifeCycleSelector)
					.getLifeCycleItersList();
			lstLocalLifeCycleIterationConditionList = lstLifeCycleList
					.getLifeCycle(ndxJlstLifeCycleSelector)
					.getLifeCycleItersListCopy();
			performWakeUpLCItersGUI();
		}

		// Now that all of the selection actions have takes place, check to see
		// about all of the
		// action buttons. Enable those that should be enabled.
		checkLifeCycleButtons();
		selectListLifeCycleitemselected=false;
	}

	/**
	 * This method is called any time the user makes even a single character
	 * change to the name of the life cycle. The method does not allow the name
	 * to be more than 34 characters in length.
	 */
	public void checkLifeCycleName() {
		if (fldLCName.getText().length() < 35)
			// As long as there are not more than 34 characters, all we need to
			// do is to fetch the text
			// and save a copy in the local attribute.
			strLocalLCName = fldLCName.getText();
		else {
			// Limit the size of the input to 34 characters, so discard any
			// after the first 34.
			strLocalLCName = fldLCName.getText().substring(0, 34);

			// We can't change the input field during the notification, so we
			// schedule a task to be run
			// after the update and other I/O actions finish to prune the input
			// to just the first 34
			// characters and beep the console.

			// This creates the task that is to be run later.
			Runnable resetInput = new Runnable() {
				public void run() {
					fldLCName.setText(strLocalLCName);
					java.awt.Toolkit.getDefaultToolkit().beep();
				}
			};

			// This is the command to actual schedule that.
			Platform.runLater(resetInput);
		}

		// This method is called when a change of state to the Step Name list
		// *may* cause a change of
		// state in the Step buttons, so we check it.
		checkLifeCycleButtons();
	}

	/**
	 * This method is called any time the user makes even a single character
	 * change to the description of the life cycle. The method does not allow
	 * the name to be more than 1000 characters in length.
	 */
	public void checkLifeCycleDesc() {

		strLocalLCDesc = fldLCDesc.getText();

		checkLifeCycleButtons();

	}

	public void performLCNew() {
		// Set up the Effort Category Selected Lists for this insert
		commonUpdateBeforeALifeCycleInsert();

		// This method is only used for inserting the first item into an empty
		// List view. (Once the List view
		// has an item in it, the user must specify whether to insert above or
		// below the selected list
		// item.)
		// here we add the name desc and sub life cycle list life cycle step list to the lifecycles.
		lstLifeCycleList.addLifeCycle(strLocalLCName, strLocalLCDesc,
				lstLocalSubLifeCycleList,
				lstLocalLifeCycleIterationConditionList,
				lglLocalLifeCycleComposedOfSteps,
				ndxLocalLifeCycleIterationSelector,
				lstLocalLifeCycleStepsList);

		// Specify that the first item should be selected
		ndxJlstLifeCycleSelector = 0;

		// Select that item and finish establishing the selected items
		commonUpdateAfterALifeCycleInsert();
	}

	/**
	 * This method is very similar to the performLCNew method, but this method
	 * inserts into a non-empty list and the new item will be inserted just
	 * above the selected item in the List view
	 */
	public void performLCNewAbove() {
		// Set up the Effort Category Selected Lists for this insert
		commonUpdateBeforeALifeCycleInsert();

		// This method is only used for inserting above an element in a non-empty List view.
		lstLifeCycleList.addLifeCycle(ndxJlstLifeCycleSelector, strLocalLCName,
				strLocalLCDesc, lstSelectedSubLifeCycleList,
				lstLocalLifeCycleIterationConditionList,
				lglLocalLifeCycleComposedOfSteps,
				ndxLocalLifeCycleIterationSelector, lstLocalLifeCycleStepsList);

		// The previous position in the list is still the current item, so no
		// change to
		// ndxJlstLifeCycleSelector is needed

		// Rebuild the List view, select the proper element and establish the
		// selected items
		commonUpdateAfterALifeCycleInsert();
	}

	/**
	 * This method is very similar to the performLCNew method, but this method
	 * inserts into a non-empty list and the new item will be inserted just
	 * below the selected item in the List view
	 */
	public void performLCNewBelow() {
		// Set up the Effort Category Selected Lists for this insert
		commonUpdateBeforeALifeCycleInsert();

		// This method is only used for inserting below a non-empty List view.
		lstLifeCycleList.addLifeCycle(ndxJlstLifeCycleSelector + 1,
				strLocalLCName, strLocalLCDesc, lstSelectedSubLifeCycleList,
				lstLocalLifeCycleIterationConditionList,
				lglLocalLifeCycleComposedOfSteps,
				ndxLocalLifeCycleIterationSelector, lstLocalLifeCycleStepsList);

		// Specify that selected item has move down one
		ndxJlstLifeCycleSelector++;

		// Rebuild the List view, select the proper element and establish the
		// selected items
		commonUpdateAfterALifeCycleInsert();
	}

	/**
	 * This method is called when the user pushes the button to move the
	 * currently selected Life Cycle up one position in the List view
	 */
	public void performLCMvUp() {
		// The following statement actually moves the item up in the list
		lstLifeCycleList.moveLifeCycleUp(ndxJlstLifeCycleSelector);

		// Now we need to update the GUI to match what we have done.
		ndxJlstLifeCycleSelector--; // Keep the same item selected

		// Update the GUI to reflect the change
		commonUpdateAfterALifeCycleMove();
	}

	/**
	 * This method is called when the user pushes the button to move the
	 * currently selected
	 */
	public void performLCMvDn() {
		// The following statement actually moves the item in the list
		lstLifeCycleList.moveLifeCycleDn(ndxJlstLifeCycleSelector);

		// Now we need to update the GUI to match what we have done.
		ndxJlstLifeCycleSelector++; // Keep the same item selected

		// Update the GUI to reflect the change
		commonUpdateAfterALifeCycleMove();
	}

	/**
	 * Life Cycle insert action button routines (Insert, Insert UP and Insert
	 * DN) as well as the save command use this method to set up what will be
	 * the new selected values once the Insert or the Save has been completed.
	 */
	public void commonUpdateBeforeALifeCycleInsert() {

		lstSelectedSubLifeCycleList = new LifeCycleListController(
				lstLocalSubLifeCycleList);
		lstSelectedLifeCycleStep = new LifeCycleStepListController(
				lstLocalLCECLifeCycleStep);
	}

	/**
	 * Life Cycle insert action button routines (Insert, Insert UP and Insert
	 * DN) as well as the save command use this method to reset the GUI to
	 * reflect the new state of the display *after* an insert or save has taken
	 * place.
	 */
	public void commonUpdateAfterALifeCycleInsert() {
		// Since we have changed the list, we need to build a new select list.
		// Recall that the
		// sequence numbers are added by the application and are *not* part of
		// the name.
		lstLifeCycleSelectList = lstLifeCycleList.buildSelectList();

		// We can't just change the List view as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the List view,
		// so that handler will just ignore events until we are done. Then we do
		// the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglLifeCyclesListIsBeingUpdated = true;
		ObservableList lstLifeCycleSelectListObservable = FXCollections
				.observableArrayList(lstLifeCycleSelectList);
		jlstLifeCycles.setItems(lstLifeCycleSelectListObservable);
		lglLifeCyclesListIsBeingUpdated = false;
		// Now that the list has been updated, we need to select the proper item
		jlstLifeCycles.getSelectionModel().select(ndxJlstLifeCycleSelector);

		// The last step is to set the local copies of the name, description,
		// and the two lists of
		// artifacts match the new selected artifact.

		// This sets the selected copy of the Name
		strSelectedLCName = strLocalLCName; // The selected and local versions
		// start out the same

		// This sets the selected copy of the Description
		strSelectedLCDesc = strLocalLCDesc; // Same here

		// This sets the selected copy of the ComposedOfSteps flag
		lglSelectedLifeCycleComposedOfSteps = lglLocalLifeCycleComposedOfSteps;

		// This sets the selected copy of the Iteration Mode selector
		ndxSelectedLifeCycleIterationSelector = ndxLocalLifeCycleIterationSelector;

		// Establish the local copies of the optional lists
		lstSelectedLifeCycleStepsList = new LifeCycleStepListController(
				lstLocalLifeCycleStepsList);
		lstSelectedSubLifeCycleList = new LifeCycleListController(
				lstLocalSubLifeCycleList);
		lstSelectedLifeCycleIterationConditionList = new ConditionListController(
				lstLocalLifeCycleIterationConditionList);

		// Since the state has changed, the action buttons need to be updated
		// (visible and enabled)
		// ///////////*************************/////////////////////
		performWakeUpSubLifeCycleGUI();
		checkLifeCycleButtons();
	}

	/**
	 * Life Cycle move action button routines (Move UP and Move DN) use this
	 * method to reset the GUI to reflect the new state of the display *after*
	 * an move has taken place.
	 */
	public void commonUpdateAfterALifeCycleMove() {
		// The list has changed so we need to build a new list.
		lstLifeCycleSelectList = lstLifeCycleList.buildSelectList();

		// We can't just change the List view as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the List view,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglLifeCyclesListIsBeingUpdated = true;
		ObservableList lstLifeCycleSelectListObservable = FXCollections
				.observableArrayList(lstLifeCycleSelectList);
		jlstLifeCycles.setItems(lstLifeCycleSelectListObservable);
		lglLifeCyclesListIsBeingUpdated = false;

		// Now issue the command to make the original item selected again
		jlstLifeCycles.getSelectionModel().select(ndxJlstLifeCycleSelector);

		// These changes may cause changes in the state of the various action buttons, so we 
		// will check them all
		checkLifeCycleButtons();
	}

	public void performLCSave() {
		commonUpdateBeforeALifeCycleInsert();

		// This method is used for replace the current list item into the life
		// cycle List view.
		lstLifeCycleList.replaceLifeCycle(ndxJlstLifeCycleSelector,
				strLocalLCName, strLocalLCDesc, lstLocalSubLifeCycleList,
				lstLocalLifeCycleIterationConditionList,
				lglLocalLifeCycleComposedOfSteps,
				ndxLocalLifeCycleIterationSelector, lstLocalLifeCycleStepsList);

		// Select the previously selected item and finish establishing the
		// selected items
		commonUpdateAfterALifeCycleInsert();


	}

	public void performLCDelete() {
		// The following statement actually deletes the item from the list
		lstLifeCycleList.deleteLifeCycle(ndxJlstLifeCycleSelector);
		ndxJlstLifeCycledeleteSelector = ndxJlstLifeCycleSelector;

		// Now we need to update the GUI to match what we have done.
		ndxJlstLifeCycleSelector = -1; // After a delete, no item is selected,
		// so we reset
		jlstLifeCycles.getSelectionModel().clearSelection(); // both the local
		// index and the
		// GUI

		// The list has changed so we need to build a new list.
		lstLifeCycleSelectList = lstLifeCycleList.buildSelectList();

		// We can't just change the listview as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the listview,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglLifeCyclesListIsBeingUpdated = true;
		ObservableList lstLifeCycleSelectListObservable = FXCollections
				.observableArrayList(lstLifeCycleSelectList);
		jlstLifeCycles.setItems(lstLifeCycleSelectListObservable);
		lglLifeCyclesListIsBeingUpdated = false;

		// We need to clear the local copies as there is no current selected
		// item
		strSelectedLCName = ""; // Reset the local name, description, and the
		strSelectedLCDesc = ""; // two lists of artifacts
		lglLocalLifeCycleComposedOfSteps = true; // Reset the selectors for the
		// optional elements
		ndxLocalLifeCycleIterationSelector = 0;

		// Clear the local list variables for the optional listviews
		lstLocalLifeCycleStepsList = new LifeCycleStepListController();
		lstLocalArtifactsProducedList = new ArtifactListController(
				emptyArtifactList);
		lstLocalLifeCycleIterationConditionList = new ConditionListController(
				emptyConditionList);
		
		if(lstLifeCycleList.getNumberOfLifeCycles()==0){
		String xy[]=new String[]{};
		
		lglLifeCyclesListIsBeingUpdated = true;
		ObservableList xyq = FXCollections
				.observableArrayList(lstLocalLifeCycleStepsList.buildSelectList());
		jlstLifeCycleSteps.setItems(xyq);
		lglLifeCyclesListIsBeingUpdated = false;
		cmbLifeCycleStepsSelectList = lstLifeCycleStepList
				.buildSubList(lstLocalLifeCycleStepsList);

		cmbModelLifeCycleSteps = cmbLifeCycleStepsSelectList.buildSelectList();

		ObservableList<String> items1 = FXCollections
				.observableArrayList(cmbModelLifeCycleSteps);

		jcbLifeCycleSteps.setItems(items1);
		if(cmbModelLifeCycleSteps.length!=0)
		jcbLifeCycleSteps.getSelectionModel().select(0);
		else
			jcbLifeCycleSteps.getSelectionModel().select(-1);

		if (ndxCmbLifeCycleStepsSelector >= cmbLifeCycleStepsSelectList
				.getNumberOfLifeCycleSteps())
			ndxCmbLifeCycleStepsSelector = cmbLifeCycleStepsSelectList
			.getNumberOfLifeCycleSteps() - 1;
		}
		// These changes may cause changes in the state of the five buttons, so
		// we process them all
		// rather than try to reason about which buttons might be affected.
		// ///////////*************************/////////////////////
		performWakeUpSubLifeCycleGUI();
		checkLifeCycleButtons();
	}

/**
	 * This method is called when the user click on the Life Cycle Clear button.
	 * It clears all of the user editable/definable fields and resets the lists
	 * the user may be able to create on this screen to the empty state.
	 */
	public void performLCClear() {
		// Clear the local copy of the Name
		strLocalLCName = "";
		fldLCName.setText(strLocalLCName);

		// Clear the local copy of the Description
		strLocalLCDesc = "";
		fldLCDesc.setText(strLocalLCDesc);

		// Clear the Effort Categories

		// Reset the Life Cycle is composed of fields
		ndxLifeCycleComposedOfOption = 0;
		lglLocalLifeCycleComposedOfSteps = true;
		jcbLifeCycleComposedOfOption.getSelectionModel().select(0);
		lglLocalLifeCycleComposedOfSteps = false;
		configureComposedOf();

		// Reset the Life Cycle's iteration mode
		ndxLocalLifeCycleIterationSelector = 0;
		jcbLifeCycleIterationOption.getSelectionModel().select(0);
		configureIter();

		// This clears the local copy of the Life Cycle Steps list
		lstLocalLifeCycleStepsList = new LifeCycleStepListController(
				emptyStepList);
		lstLifeCycleStepsSelectListSource = lstLocalLifeCycleStepsList
				.buildSelectList();

		lglLifeCycleStepsListIsBeingUpdated = true; // Ignore update events

		lglLifeCycleStepsListIsBeingUpdated = false;

		ndxJlstLifeCycleStepsSelector = -1;
		jlstLifeCycleSteps.getSelectionModel().clearSelection();

		// This clears the local copy of the Subordinate Life Cycle list
		lstLocalSubLifeCycleList = new LifeCycleListController(
				emptyLifeCycleList);
		lstSubLifeCycleSelectListSource = lstLocalSubLifeCycleList
				.buildSelectList();

		ndxJlstSubLifeCycleSelector = -1; // No item is selected
		jlstSubLifeCycle.getSelectionModel().clearSelection();

		// This clears the local copy of the Life Cycle Iteration list
		lstLocalLifeCycleIterationConditionList = new ConditionListController(
				emptyConditionList);
		lstLifeCycleIterationConditionSelectListSource = lstLocalLifeCycleIterationConditionList
				.buildSelectList();

		ndxJlstLifeCycleIterationConditionSelector = -1; // No item is selected
		jlstLifeCycleIterationCondition.getSelectionModel().clearSelection();

		// This sets the local copy of the ComboBox the is used to add items to
		// the Life Cycle Steps list
		cmbLifeCycleIterationConditionSelectList = lstConditionList
				.buildSubList(lstLocalLifeCycleIterationConditionList);

		// No item is selected if the list is empty else the first item in the
		// list is selected

		// These changes may cause changes in the state of the five buttons, so
		// we process them all
		// rather than try to reason about which buttons might be affected.
		checkLifeCycleButtons();
	}

	/**
	 * This method is called when the user clicks on the Life Cycle Steps Insert
	 * button. It causes the user input to be used to create a new Life Cycle
	 * Step and add it to a previously empty List view
	 */

	/**
	 * This method is called when the user clicks on the Life Cycle Steps Insert
	 * button. It causes the user input to be used to create a new Life Cycle
	 * Step and add it to a previously empty List view
	 */
	public void jcbLifeCycleIteration() {
		// Get the index from the ComboBox
		int newIterSelector = jcbLifeCycleIterationOption.getSelectionModel()
				.getSelectedIndex();

		// If it has not changed, do not bother processing it
		if (newIterSelector == ndxLocalLifeCycleIterationSelector)
			return;

		// Only process changes when the index changes
		ndxLocalLifeCycleIterationSelector = newIterSelector;
		configureIter();
		checkLifeCycleButtons();
	}

	/**
	 * Process the Life Cycle Effort Category Plan selection list event:
	 * establish the local list
	 */
	public void LifeCycleComposedOfOption() {
		int newSelectedIndex = jcbLifeCycleComposedOfOption.getSelectionModel()
				.getSelectedIndex();

		if (newSelectedIndex == ndxLifeCycleComposedOfOption)
			return;

		ndxLifeCycleComposedOfOption = newSelectedIndex;
		lglLocalLifeCycleComposedOfSteps = ndxLifeCycleComposedOfOption == 0;
		configureComposedOf();

		checkLifeCycleButtons();
	}

	private void configureComposedOf() {
		if (ndxLifeCycleComposedOfOption == 0) {
			// For Mode 0: The Life Cycle is composed of steps
			lglLocalLifeCycleComposedOfSteps = true;
			msgLifeCycleSteps.setVisible(true);
			jlstLifeCycleSteps.setVisible(true);

			btnLCSNew.setVisible(true);
			btnLCSAbove.setVisible(false);
			btnLCSBelow.setVisible(false);
			btnLCSMvUp.setVisible(true);
			btnLCSMvDn.setVisible(true);
			btnLCSDelete.setVisible(true);

			btnLCSNew.setDisable(true);
			btnLCSAbove.setDisable(true);
			btnLCSBelow.setDisable(true);
			btnLCSMvUp.setDisable(true);
			btnLCSMvDn.setDisable(true);
			btnLCSDelete.setDisable(true);

			btnSubLCNew.setVisible(false);
			btnSubLCAbove.setVisible(false);
			btnSubLCBelow.setVisible(false);
			btnSubLCMvUp.setVisible(false);
			btnSubLCMvDn.setVisible(false);
			btnSubLCDelete.setVisible(false);

			btnSubLCNew.setDisable(true);
			btnSubLCAbove.setDisable(true);
			btnSubLCBelow.setDisable(true);
			btnSubLCMvUp.setDisable(true);
			btnSubLCMvDn.setDisable(true);
			btnSubLCDelete.setDisable(true);

			msgSubLifeCycle.setVisible(false);
			jlstSubLifeCycle.setVisible(false);
			msgLCSSelect.setVisible(true);
			jcbLifeCycleSteps.setVisible(true);
			msgSubLifeCycleSelect.setVisible(false);
			jcbSubLifeCycle.setVisible(false);
			performWakeUpLifeCycleStepsGUI();
			checkLCECButtons();
		} else {
			// For Mode 1: The Life Cycle is composed of subordinate life cycles
			lglLocalLifeCycleComposedOfSteps = false;
			msgLifeCycleSteps.setVisible(false);
			jlstLifeCycleSteps.setVisible(false);
			jlstSubLifeCycle.setVisible(true);

			btnLCSNew.setVisible(false);
			btnLCSAbove.setVisible(false);
			btnLCSBelow.setVisible(false);
			btnLCSMvUp.setVisible(false);
			btnLCSMvDn.setVisible(false);
			btnLCSDelete.setVisible(false);

			btnLCSNew.setDisable(true);
			btnLCSAbove.setDisable(true);
			btnLCSBelow.setDisable(true);
			btnLCSMvUp.setDisable(true);
			btnLCSMvDn.setDisable(true);
			btnLCSDelete.setDisable(true);

			btnLCSNew.setDisable(true);
			btnLCSAbove.setDisable(true);
			btnLCSBelow.setDisable(true);
			btnLCSMvUp.setDisable(true);
			btnLCSMvDn.setDisable(true);
			btnLCSDelete.setDisable(true);

			btnSubLCNew.setVisible(true);
			btnSubLCAbove.setVisible(false);
			btnSubLCBelow.setVisible(false);
			btnSubLCMvUp.setVisible(true);
			btnSubLCMvDn.setVisible(true);
			btnSubLCDelete.setVisible(true);

			btnSubLCNew.setDisable(true);
			btnSubLCAbove.setDisable(true);
			btnSubLCBelow.setDisable(true);
			btnSubLCMvUp.setDisable(true);
			btnSubLCMvDn.setDisable(true);
			btnSubLCDelete.setDisable(true);

			btnSubLCNew.setDisable(true);
			btnSubLCAbove.setDisable(true);
			btnSubLCBelow.setDisable(true);
			btnSubLCMvUp.setDisable(true);
			btnSubLCMvDn.setDisable(true);
			btnSubLCDelete.setDisable(true);

			msgSubLifeCycle.setVisible(true);
			jlstSubLifeCycle.setVisible(true);
			msgLCSSelect.setVisible(false);
			jcbLifeCycleSteps.setVisible(false);
			msgSubLifeCycleSelect.setVisible(true);
			jcbSubLifeCycle.setVisible(true);
			performWakeUpSubLifeCycleGUI();
			checkLCECButtons();
		}

	}

	public void setupIterMode0() {
		msgLifeCycleIterationConditions.setVisible(false);
		jlstLifeCycleIterationCondition.setVisible(false);
		msgLifeCycleIterationCondition.setVisible(false);
		jcbLifeCycleIterationCondition.setVisible(false);
		btnLCItersNew.setVisible(false);
		btnLCItersAbove.setVisible(false);
		btnLCItersBelow.setVisible(false);
		btnLCItersMvUp.setVisible(false);
		btnLCItersMvDn.setVisible(false);
		btnLCItersDelete.setVisible(false);
	}

	/**
	 * This method is called when Iteration Mode 1 is established. This hides a
	 * set of GUI elements associated with Mode 0 and Mode 2 and makes those
	 * associated with Mode 1 visible.
	 */
	public void setupIterMode1() {
		msgLifeCycleIterationConditions.setVisible(false);
		jlstLifeCycleIterationCondition.setVisible(false);
		msgLifeCycleIterationCondition.setVisible(false);
		jcbLifeCycleIterationCondition.setVisible(false);
		btnLCItersNew.setVisible(false);
		btnLCItersAbove.setVisible(false);
		btnLCItersBelow.setVisible(false);
		btnLCItersMvUp.setVisible(false);
		btnLCItersMvDn.setVisible(false);
		btnLCItersDelete.setVisible(false);
	}

	/**
	 * This method is called when Iteration Mode 2 is established. This hides a
	 * set of GUI elements associated with Mode 0 and Mode 1 and makes those
	 * associated with Mode 2 visible.
	 */
	public void setupIterMode2() {
		msgLifeCycleIterationConditions.setVisible(true);
		jlstLifeCycleIterationCondition.setVisible(true);
		msgLifeCycleIterationCondition.setVisible(true);
		jcbLifeCycleIterationCondition.setVisible(true);
		jcbLifeCycleIterationCondition.setDisable(false);
		btnLCItersNew.setVisible(true);
		btnLCItersAbove.setVisible(true);
		btnLCItersBelow.setVisible(true);
		btnLCItersMvUp.setVisible(true);
		btnLCItersMvDn.setVisible(true);
		btnLCItersDelete.setVisible(true);
	}

	public void configureIter() {
		if (ndxLocalLifeCycleIterationSelector == 0) {
			setupIterMode0();
		} else if (ndxLocalLifeCycleIterationSelector == 1) {
			setupIterMode1();
		} else {
			setupIterMode2();
			performWakeUpLCItersGUI();
		}
		checkLCECButtons();
	}

	/**
	 * This method is invoked when the user selects the LifeCycle pane and the
	 * "composedOf" flag is set to display "Life Cycle Steps". It is possible
	 * that actions on these other panes may have changed attributes that alter
	 * how this portion of this pane is displayed. Therefore, this method
	 * reconstructs the GUI lists and displays them.
	 */
	private void performWakeUpLifeCycleStepsGUI() // SHOULD BE MODDIFIED // ACCORDINGLY
	{
		//this step is used to change the GUI and modify the list view

		lstLifeCycleStepsSelectListSource = lstLocalLifeCycleStepsList
				.buildSelectList();
		lglLifeCycleStepsListIsBeingUpdated = true;

		ObservableList items = FXCollections
				.observableArrayList(lstLifeCycleStepsSelectListSource);

		jlstLifeCycleSteps.setItems(items);

		lglLifeCycleStepsListIsBeingUpdated = false;

		jlstLifeCycleSteps.getSelectionModel().clearSelection(); // This resets
		// the List view
		// element
		ndxJlstLifeCycleStepsSelector = -1; // And this is the local variable we
		// use

		LifeCycleStepListController temp = new LifeCycleStepListController(
				lstStepList);

		cmbLifeCycleStepsSelectList = temp
				.buildSubList(lstLocalLifeCycleStepsList);
		cmbModelLifeCycleSteps = cmbLifeCycleStepsSelectList.buildSelectList();

		ObservableList items1 = FXCollections
				.observableArrayList(cmbModelLifeCycleSteps);

		jcbLifeCycleSteps.setItems(items1);

		if (cmbModelLifeCycleSteps.length == 0) {
			jcbLifeCycleSteps.getSelectionModel().select(-1);
			ndxCmbLifeCycleStepsSelector = -1;
		}
		// Otherwise select the list's first item (index of zero)
		else {
			jcbLifeCycleSteps.getSelectionModel().select(0);
			ndxCmbLifeCycleStepsSelector = 0;
		}


	}

	/**
	 * This method is invoked when the user selects the LifeCycle pane and the
	 * "composedOf" flag is set to display "Subordinate Life Cycles". It is
	 * possible that actions on these other panes may have changed attributes
	 * that alter how this portion of this pane is displayed. Therefore, this
	 * method reconstructs the GUI lists and displays them.
	 */
	private void performWakeUpSubLifeCycleGUI() {

		// Establish the Subordinate Life Cycle Select List and ComboBox
		System.out.println("performWakeUpSubLifeCycleGUI");
		lstSubLifeCycleSelectListSource = lstLocalSubLifeCycleList
				.buildSelectList();

		// Establish this select list as the basis for the List view GUI element
		lglSubLifeCycleListIsBeingUpdated = true;
		ObservableList SubLifeCycleSelectListSourceObservable = FXCollections
				.observableArrayList(lstSubLifeCycleSelectListSource);
		jlstSubLifeCycle.setItems(SubLifeCycleSelectListSourceObservable);
		lglSubLifeCycleListIsBeingUpdated = false;

		// Do not have any item in the list be currently selected
		jlstSubLifeCycle.getSelectionModel().clearSelection();
		ndxJlstSubLifeCycleSelector = -1;

		// Establish the Subordinate Life Cycle comboBox (use to insert other
		// life cycles into the list)
		LifeCycleListController temp = new LifeCycleListController(
				lstLifeCycleList); // All possible steps
		cmbSubLifeCycleSelectList = temp.buildSubList(lstLocalSubLifeCycleList); // Remove all that have been used
		cmbModelSubLifeCycle = cmbSubLifeCycleSelectList.buildSelectList(); // Establish the comboBox model

		ObservableList cmbModelSubLifeCycleObservable = FXCollections
				.observableArrayList(cmbModelSubLifeCycle);
		jcbSubLifeCycle.setItems(cmbModelSubLifeCycleObservable); // And link it to the actual ComboBox

		// If there are no life cycle items that can be added, do not have any
		// item selected
		if (cmbModelSubLifeCycle.length == 0) {
			jcbSubLifeCycle.getSelectionModel().select(-1);
			ndxCmbSubLifeCycleSelector = -1;
		} else {
			// Otherwise select the list's first item (index of zero)
			jcbSubLifeCycle.getSelectionModel().select(0);
			ndxCmbSubLifeCycleSelector = 0;
		}
	}

	public void SubLifeCycleAction() {
		if (lglSubLifeCycleListIsBeingUpdated)
			return;
		selectListSubLifeCycleItemSelected();
	}

	/**
	 * This method processes user selections of the Subordinate Life Cycle
	 * List view. As a result of a user selection the status of the action buttons
	 * may need to be changed, so that is checked.
	 */
	public void selectListSubLifeCycleItemSelected() {
		// Fetch the selected index from the List view.
		int ndx = jlstSubLifeCycle.getSelectionModel().getSelectedIndex();

		// If the index matches the current local copies, there is no need to
		// proceed
		if (ndxJlstSubLifeCycleSelector == ndx)
			return;

		// If the index is different, then we must update the local copy and
		// remember the new index
		ndxJlstSubLifeCycleSelector = ndx;

		// This change may cause changes in the state of the five buttons for
		// the Artifacts Produced list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkSubLifeCycleButtons();
	}

	public void performSubLCNew() {
		// This method is only used for inserting the first item into an empty
		// List view. (Once the List view has an
		// item in it, the user must specify whether to insert above or below
		// the selected list item.)

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to
		// match.
		ndxCmbSubLifeCycleSelector = jcbSubLifeCycle.getSelectionModel()
				.getSelectedIndex(); // Get the index of the artifact
		LifeCycle toBeInserted = cmbSubLifeCycleSelectList
				.extractLifeCycle(ndxCmbSubLifeCycleSelector);
		lstLocalSubLifeCycleList.addLifeCycle(toBeInserted); 

		// Since there is only one item in the list now, we must highlight the
		// first one as the selected item
		ndxJlstSubLifeCycleSelector = 0;

		// Perform the common code required to reestablish the GUI after the insert
		commonUpdateAfterASubLifeCycleInsert();
	}

	public void performSubLCAbove() {
		// This method is only used for inserting above a selected item into a
		// non-empty List view.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbSubLifeCycleSelector = jcbSubLifeCycle.getSelectionModel()
				.getSelectedIndex();
		LifeCycle toBeInserted = cmbSubLifeCycleSelectList
				.extractLifeCycle(ndxCmbSubLifeCycleSelector);
		if (ndxJlstSubLifeCycleSelector == -1) {
			ndxJlstSubLifeCycleSelector = jlstSubLifeCycle.getItems().size() - 1;
		}
		lstLocalSubLifeCycleList.addLifeCycle(ndxJlstSubLifeCycleSelector,
				toBeInserted);

		// We have inserted above the previous current item, so that item will
		// be pushed down one and the just
		// inserted item will take its old place. Therefore, the index of the
		// current item stays the same.

		// Update the GUI to reflect this change
		commonUpdateAfterASubLifeCycleInsert();
	}

	public void performSubLCBelow() {
		// This method is only used for inserting above a selected item into a
		// non-empty List view.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbSubLifeCycleSelector = jcbSubLifeCycle.getSelectionModel()
				.getSelectedIndex();
		LifeCycle toBeInserted = cmbSubLifeCycleSelectList
				.extractLifeCycle(ndxCmbSubLifeCycleSelector);
		if (ndxJlstSubLifeCycleSelector == -1) {
			ndxJlstSubLifeCycleSelector = jlstSubLifeCycle.getItems().size() - 1;
		}
		lstLocalSubLifeCycleList.addLifeCycle(ndxJlstSubLifeCycleSelector + 1,
				toBeInserted);

		// We have inserted below the previous current item, so the new item
		// will be inserted just after
		// the previous current tie,. Therefore, the index of the current item
		// must be increased.
		ndxJlstSubLifeCycleSelector++;

		// Update the GUI to reflect this change
		commonUpdateAfterASubLifeCycleInsert();
	}

	/**
	 * Update the GUI after on the in the SubLifeCycle inserts.
	 */
	public void commonUpdateAfterASubLifeCycleInsert() {
		// The Step list has changed, so we need to rebuild the list that is
		// displayed
		lstSubLifeCycleSelectListSource = lstLocalSubLifeCycleList.buildSelectList();
		// Since we are updating the list, we need to ignore the list change events
		lglSubLifeCycleListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Used select list to display the new list
		ObservableList SubLifeCycleSelectListSourceObservable = FXCollections
				.observableArrayList(lstSubLifeCycleSelectListSource);
		jlstSubLifeCycle.setItems(SubLifeCycleSelectListSourceObservable);

		lglSubLifeCycleListIsBeingUpdated = false;

		jlstSubLifeCycle.getSelectionModel()
		.select(ndxJlstSubLifeCycleSelector);

		// The ComboBox list has also changed (one fewer item), so it must be rebuilt as well
		// This command creates this list by taking the entire list of steps that exists and then
		// removes those that are in the Life Cycle Steps list.
		cmbSubLifeCycleSelectList = lstLifeCycleList
				.buildSubList(lstLocalSubLifeCycleList);

		// Create the ComboBox Model, complete with sequence numbers
		cmbModelSubLifeCycle = cmbSubLifeCycleSelectList.buildSelectList();

		ObservableList cmbModelSubLifeCycleObservable = FXCollections
				.observableArrayList(cmbModelSubLifeCycle);
		jcbSubLifeCycle.setItems(cmbModelSubLifeCycleObservable);

		// Since the size of the ComboBox is now smaller, we need to check the index of the current
		// selected item to be sure that it is still in a valid range, and fix
		// it if not. (Remember, it could now be empty!)
		if (ndxCmbSubLifeCycleSelector >= cmbSubLifeCycleSelectList
				.getNumberOfLifeCycles())
			ndxCmbSubLifeCycleSelector = cmbSubLifeCycleSelectList
			.getNumberOfLifeCycles() - 1;

		// Establish the current step in the ComboBox
		jcbSubLifeCycle.getSelectionModel().select(ndxCmbSubLifeCycleSelector);

		// These changes may cause changes in the state of the five buttons for the Life Cycle Steps list,
		// so we process them all rather than try to reason about which buttons might be affected. It
		// cascade checks the main pane's buttons as well.
		checkLifeCycleButtons();
	}

	public void performSubLCMvUp() {
		//  This method is only used for moving a selected item up a non-empty List view when there is at least one element above it.
		// This statements actually does the move. The rest are about adjusting the GUI state to match.
		lstLocalSubLifeCycleList.moveLifeCycleUp(ndxJlstSubLifeCycleSelector);

		// We moved it up, so to keep the same item selected, the index must be reduced by one
		ndxJlstSubLifeCycleSelector--;

		// Update the GUI to match what we have just done
		commonUpdateAfterASubLifeCycleMove();
	}

	public void performSubLCMvDn() {
		// This method is only used for moving a selected item down a non-empty List view when there is at least
		// one element below it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalSubLifeCycleList.moveLifeCycleDn(ndxJlstSubLifeCycleSelector);

		// We moved it up, so to keep the same item selected, the index must be
		// reduced by one
		ndxJlstSubLifeCycleSelector++;

		// Update the GUI to match what we have just done
		commonUpdateAfterASubLifeCycleMove();
	}

	public void commonUpdateAfterASubLifeCycleMove() {
		// The Lifer Cycle Step list has changed, so we need to rebuild the list
		// that is displayed
		lstSubLifeCycleSelectListSource = lstLocalSubLifeCycleList
				.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglSubLifeCycleListIsBeingUpdated = true;

		// Update the GUI component for the Step select list to display the new
		// list
		ObservableList SubLifeCycleSelectListSourceObservable = FXCollections
				.observableArrayList(lstSubLifeCycleSelectListSource);
		jlstSubLifeCycle.setItems(SubLifeCycleSelectListSourceObservable);

		// Given the new list, this command will once again select the item that
		// was moved
		jlstSubLifeCycle.getSelectionModel()
		.select(ndxJlstSubLifeCycleSelector);

		// We will now process changes to the list
		lglSubLifeCycleListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for  the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons might be affected.
		checkLifeCycleButtons();
	}

	public void performSubLCDelete() {
		// This method is only used for removing a selected item from a non-empty List view.

		// This statements actually does the delete. The rest are about
		// adjusting the GUI state to match.
		lstLocalSubLifeCycleList.deleteLifeCycle(ndxJlstSubLifeCycleSelector);

		// Now that the artifact has been deleted, we need to clean up the UI to match this state.

		// The list has changed so we need to build a new list.
		lstSubLifeCycleSelectListSource = lstLocalSubLifeCycleList.buildSelectList();

		// Since we are updating the list, we need to ignore the list change events
		lglSubLifeCycleListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Used select list to display the new list
		ObservableList SubLifeCycleSelectListSourceObservable = FXCollections
				.observableArrayList(lstSubLifeCycleSelectListSource);
		jlstSubLifeCycle.setItems(SubLifeCycleSelectListSourceObservable);

		// We have delete the current item from the list, so no item in the list will be selected now.
		// Therefore, we clear the index and the selection from the GUI component
		ndxJlstSubLifeCycleSelector = -1;
		jlstSubLifeCycle.getSelectionModel().clearSelection();

		// The ComboBox list has also changed (one more item), so it must be rebuilt as well
		// This command creates this list by taking the entire list of artifacts that exist and then
		// removes those that are in the Artifacts Used list.
		cmbSubLifeCycleSelectList = lstLifeCycleList.buildSubList(lstLocalSubLifeCycleList);

		// Create the ComboBox Model, complete with sequence numbers
		cmbModelSubLifeCycle = cmbSubLifeCycleSelectList.buildSelectList();

		// Establish the updated ComboBox using the model that has just been created
		ObservableList cmbModelSubLifeCycleObservable = FXCollections
				.observableArrayList(cmbModelSubLifeCycle);
		jcbSubLifeCycle.setItems(cmbModelSubLifeCycleObservable);

		// Since the size of the ComboBox is now larger, we know that there is at least one item in it,
		// so we will default the ComboBox to the first item in the list.

		// Set the index to be the first item in the list
		ndxCmbSubLifeCycleSelector = 0;

		// Set the GUI component so it has selected the first item in the list
		jcbSubLifeCycle.getSelectionModel().select(ndxCmbSubLifeCycleSelector);

		// We will now process changes to the list
		lglSubLifeCycleListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons might be affected.
		checkLifeCycleButtons();
	}

	/**
	 * This method is invoked when the user selects the LifeCycle pane and the
	 * "Iteration" mode is set to display "Iteration Conditions" list. It is
	 * possible that actions on these other panes may have changed attributes
	 * that alter how this portion of this pane is displayed. Therefore, this
	 * method reconstructs the GUI lists and displays them.
	 */
	private void performWakeUpLCItersGUI() {

		// Establish the Life Cycle Iteration Condition Select List (assume the
		// local controller is valid)
		lstLifeCycleIterationConditionSelectListSource = lstLocalLifeCycleIterationConditionList
				.buildSelectList();

		// Reconstruct the List view GUI element
		lglLifeCycleIterationConditionListIsBeingUpdated = true;
		ObservableList<String> lcitrconditions = FXCollections
				.observableArrayList(lstLifeCycleIterationConditionSelectListSource);
		jlstLifeCycleIterationCondition.setItems(lcitrconditions);
		lglLifeCycleIterationConditionListIsBeingUpdated = false;

		// Make it so no item in the list is currently selected
		jlstLifeCycleIterationCondition.getSelectionModel().clearSelection();
		ndxJlstLifeCycleIterationConditionSelector = -1;

		// Establish the Life Cycle Iteration Condition comboBox to to populate
		// the List view
		ConditionListController temp = new ConditionListController(lstConditionList); // All possible steps
				
		cmbLifeCycleIterationConditionSelectList = temp
				.buildSubList(lstLocalLifeCycleIterationConditionList); // Remove all that have been used
		cmbModelLifeCycleIterationCondition = cmbLifeCycleIterationConditionSelectList
				.buildSelectList();
		cmbModelLifeCycleIterationCondition = cmbLifeCycleIterationConditionSelectList
				.buildSelectList();
		// cmbLifeCycleIterationConditionSelectList.buildComboBoxModel();
		ObservableList<String> conditions = FXCollections
				.observableArrayList(cmbModelLifeCycleIterationCondition);

		// Establish the updated ComboBox using the string array that has just been created
		jcbLifeCycleIterationCondition.setItems(conditions);

		// If there are no Life Cycle Iteration Condition items that can be added, do not select any
		if (cmbModelLifeCycleIterationCondition.length == 0) {
			jcbLifeCycleIterationCondition.getSelectionModel().select(-1);
			ndxCmbLifeCycleIterationConditionSelector = -1;
		} else {
			// If there is at least one, select the first on in the list
			jcbLifeCycleIterationCondition.getSelectionModel().select(0);
			ndxCmbLifeCycleIterationConditionSelector = 0;
		}
	}

	/**
	 * This is the top level wake up routine for the Life Cycle pane. Any
	 * transition to this pane from some other pane comes here first. This sets
	 * up the common elements and then based on the two options selectors
	 * ("composedOf" and "Iteration Mode") subordinate methods are called to
	 * complete the wake up process. The method wraps up by calling for a check
	 * of the status of all of the action buttons on the pane.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void performWakeUpLifeCyclesPane() {
		// Rebuild the common selection lists as the underlying list of data may have changed.
		System.out.println("Wake up the LifeCycle tabbed pane");
		lstLifeCycleStepList = new LifeCycleStepListController(lstStepList);
		lstLifeCycleSelectList = lstLifeCycleList.buildSelectList();
		// Update the List view
		lglLifeCyclesListIsBeingUpdated = true;
		ObservableList jlstLifeCycleObservable = FXCollections
				.observableArrayList(lstLifeCycleSelectList);

		jlstLifeCycles.setItems(jlstLifeCycleObservable);
		lglLifeCyclesListIsBeingUpdated = false;

		// On returning to this pane, no life cycle is selected, as there may have been changes
		// to dependent fields, so we want the user to go through the selection process.
		ndxJlstLifeCycleSelector = -1;
		jlstLifeCycles.getSelectionModel().clearSelection();

		// Load Each effort category to its corresponding list

		// Artifacts
		cmbLCECArtifactSelectList = lstEffortCategoryList
				.extractEffortCategoryArtifact();
		cmbModelLCECArtifact = cmbLCECArtifactSelectList.buildSelectList();

		// Plans
		cmbLCECPlanSelectList = lstEffortCategoryList
				.extractEffortCategoryPlan();
		cmbModelLCECPlan = cmbLCECPlanSelectList.buildSelectList();

		// Interruptions
		cmbLCECInterruptionSelectList = lstEffortCategoryList
				.extractEffortCategoryInterruption();
		cmbModelLCECInterruption = cmbLCECInterruptionSelectList
				.buildSelectList();

		// Defects
		cmbLCECDefectSelectList = lstEffortCategoryList
				.extractEffortCategoryDefect();
		cmbModelLCECDefect = cmbLCECDefectSelectList.buildSelectList();

		// Others
		cmbLCECOtherSelectList = lstEffortCategoryList
				.extractEffortCategoryOther();
		cmbModelLCECOther = cmbLCECOtherSelectList.buildSelectList();

		// performWakeUpLifeCycleStepsGUI();

		if (lglLocalLifeCycleComposedOfSteps) {
			// Establish the Life Cycle Steps portion of the optional screen
			performWakeUpLifeCycleStepsGUI();
			if (ndxJlstLifeCycleSelector > -1)
				lstSelectedLifeCycleStepsList = lstLifeCycleList.getLifeCycle(
						ndxJlstLifeCycleSelector).getLifeCycleStepList();

			// Establish the selected list, but assume the local list is still valid
		} else {
			// Establish the Subordinate Life Cycle portion of the optional screen
			performWakeUpSubLifeCycleGUI();
			if (ndxJlstLifeCycleSelector > -1)
				lstSelectedSubLifeCycleList = lstLifeCycleList.getLifeCycle(
						ndxJlstLifeCycleSelector).getSubLifeCycleList();
			// Establish the selected list, but assume the local list is still valid
		}

		// Based on the Iteration Selector, establish the proper optional
		// portion of the screen
		if (ndxLocalLifeCycleIterationSelector == 2) {
			// Mode 2 is the only one that requires a wake up call
			performWakeUpLCItersGUI();
			if (ndxJlstLifeCycleSelector > -1)
				lstSelectedLifeCycleIterationConditionList = lstLifeCycleList
				.getLifeCycle(ndxJlstLifeCycleSelector)
				.getLifeCycleItersList();
			// Establish the selected list, but assume the local list is still valid
		}
		checkLifeCycleButtons();
	}

	public void SelectDefaultEffortCategory() {

		lcstepDefaultDelivarable = jcbLCECDefaultSelect.getSelectionModel()
				.getSelectedIndex();
		checkLCECButtons();
	}
	// this flag is to make sure that the action function of other combo box is not invoked
	int flagger = 0;
	ObservableList cmbModelLCECDefaultSelectedObservable;

	public void one_EC_selected() {
		if (flagger == 1)
			return;

		int index = jcbLCEC.getSelectionModel().getSelectedIndex();
		int ndx = jcbLCECSelect.getSelectionModel().getSelectedIndex();
		/*
		 * artifact -0 plan -1 Interruption -2 Defect -3 Other -4
		 * 
		 */
		// the switch is used to update  the combo box with delivarables in case an option is selected.
		switch (index) {
		case 0:
			
			lstLocalLCECArtifact = cmbLCECArtifactSelectList.getEffortCategory(ndx).getArtifactListController();
			cmbModelLCECDefaultArtifact = lstLocalLCECArtifact.buildSelectList();
			cmbModelLCECDefaultSelectedObservable = FXCollections.observableArrayList(cmbModelLCECDefaultArtifact);
			jcbLCECDefaultSelect.setItems(cmbModelLCECDefaultSelectedObservable);

			lstLocalLCECPlan = new PlanListController(emptyPlanList);
			lstLocalLCECInterruption = new InterruptionListController(emptyInterruptionList);
			lstLocalLCECDefect = new DefectListController(emptyDefectList);

			strLocalLCECOtherName = "";
			strLocalLCECOtherDesc = "";
			
			break;
		case 1:
			lstLocalLCECArtifact = new ArtifactListController(emptyArtifactList);
			lstLocalLCECPlan = cmbLCECPlanSelectList.getEffortCategory(ndx).getPlanListController();
					
			cmbModelLCECDefaultPlan = lstLocalLCECPlan.buildSelectList();
			cmbModelLCECDefaultSelectedObservable = FXCollections.observableArrayList(cmbModelLCECDefaultPlan);
			jcbLCECDefaultSelect.setItems(cmbModelLCECDefaultSelectedObservable);

			lstLocalLCECInterruption = new InterruptionListController(emptyInterruptionList);
			lstLocalLCECDefect = new DefectListController(emptyDefectList);

			strLocalLCECOtherName = "";
			strLocalLCECOtherDesc = "";
			break;
		case 2:
			lstLocalLCECArtifact = new ArtifactListController(emptyArtifactList);
			lstLocalLCECPlan = new PlanListController(emptyPlanList);

			lstLocalLCECInterruption = cmbLCECInterruptionSelectList
					.getEffortCategory(ndx).getInterruptionListController();
			cmbModelLCECDefaultInterruption = lstLocalLCECInterruption.buildSelectList();
					
			cmbModelLCECDefaultSelectedObservable = FXCollections
					.observableArrayList(cmbModelLCECDefaultInterruption);
			jcbLCECDefaultSelect.setItems(cmbModelLCECDefaultSelectedObservable);
			lstLocalLCECDefect = new DefectListController(emptyDefectList);

			strLocalLCECOtherName = "";
			strLocalLCECOtherDesc = "";
			break;
		case 3:
			lstLocalLCECArtifact = new ArtifactListController(emptyArtifactList);
			lstLocalLCECPlan = new PlanListController(emptyPlanList);
			lstLocalLCECInterruption = new InterruptionListController(emptyInterruptionList);

			lstLocalLCECDefect = cmbLCECDefectSelectList.getEffortCategory(ndx).getDefectListController();
					
			cmbModelLCECDefaultDefect = lstLocalLCECDefect.buildSelectList();
			cmbModelLCECDefaultSelectedObservable = FXCollections
					.observableArrayList(cmbModelLCECDefaultDefect);
			jcbLCECDefaultSelect.setItems(cmbModelLCECDefaultSelectedObservable);

			strLocalLCECOtherName = "";
			strLocalLCECOtherDesc = "";
			break;
		case 4:
			lstLocalLCECArtifact = new ArtifactListController(emptyArtifactList);
			lstLocalLCECPlan = new PlanListController(emptyPlanList);
			lstLocalLCECInterruption = new InterruptionListController(
					emptyInterruptionList);
			lstLocalLCECDefect = new DefectListController(emptyDefectList);
			strLocalLCECOtherName = cmbLCECOtherSelectList.getEffortCategory(
					ndx).getName();
			strLocalLCECOtherDesc = cmbLCECOtherSelectList.getEffortCategory(
					ndx).getDesc();
			break;
		}
		checkLCECButtons();
	}

	/**
	 * This method is invoked when the user selects one of the effort category
	 * items.
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void selectECListItemSelected() {
		flagger = 1;
		int index = jcbLCEC.getSelectionModel().getSelectedIndex();
		ObservableList cmbModelLCECSelectedObservable;
		/**
		 * Artifacts-0, plans-1, interruptions-2, defects-3, others-4
		 */
		// when we select an option this step inserts the effort categories assosiaated.
		switch (index) {
		case 0:
			cmbModelLCECSelectedObservable = FXCollections
			.observableArrayList(cmbModelLCECArtifact);
			jcbLCECSelect.setItems(cmbModelLCECSelectedObservable);
			break;
		case 1:
			cmbModelLCECSelectedObservable = FXCollections
			.observableArrayList(cmbModelLCECPlan);
			jcbLCECSelect.setItems(cmbModelLCECSelectedObservable);
			break;
		case 2:
			cmbModelLCECSelectedObservable = FXCollections
			.observableArrayList(cmbModelLCECInterruption);
			jcbLCECSelect.setItems(cmbModelLCECSelectedObservable);
			break;
		case 3:
			cmbModelLCECSelectedObservable = FXCollections
			.observableArrayList(cmbModelLCECDefect);
			jcbLCECSelect.setItems(cmbModelLCECSelectedObservable);
			break;
		case 4:
			cmbModelLCECSelectedObservable = FXCollections
			.observableArrayList(cmbModelLCECOther);
			jcbLCECSelect.setItems(cmbModelLCECSelectedObservable);
			break;
		}
		checkLCECButtons();
		flagger = 0;
	}

	/**
	 * This method records changes to the Life Cycle Iteration Condition
	 * selection list and updates the visibility and enabled status of the
	 * action buttons
	 */
	public void selectListLifeCycleItersListItemSelected() {
		int ndx = jlstLifeCycleIterationCondition.getSelectionModel()
				.getSelectedIndex();
		if (ndxJlstLifeCycleIterationConditionSelector == ndx)
			return;
		ndxJlstLifeCycleIterationConditionSelector = ndx;
		checkLifeCycleButtons();
	}

	/**
	 * This method records changes to the comboBox that adds new conditions to
	 * the Life Cycle Iteration Condition selection list and updates the
	 * visibility and enabled status of the action buttons
	 */
	public void comboBoxItersListItemSelected() {
		int ndx = jcbLifeCycleIterationCondition.getSelectionModel()
				.getSelectedIndex();
		if (ndxCmbLifeCycleIterationConditionSelector == ndx)
			return;
		ndxCmbLifeCycleIterationConditionSelector = ndx;
		checkLifeCycleButtons();

	}

	/**
	 * This method processes user selections of the Life Cycle Steps List view. As a
	 * result of a user selection the status of the action buttons may need to
	 * be changed, so that is checked.
	 */

	public void selectListLifeCycleStepItemSelected() {
		int ndx = jlstLifeCycleSteps.getSelectionModel().getSelectedIndex();
		if (ndxJlstLifeCycleStepsSelector == ndx)
			return;
		// If the index is different, then we must update the local copy and
		// remember the new index
		ndxJlstLifeCycleStepsSelector = ndx;
		// first we get all the assosiated artifacts plans Interruptions and defects.
		
		int default_index = lstLocalLifeCycleStepsList.getLifeCycleStep(
				ndxJlstLifeCycleStepsSelector).getDefaultDel();
		lstSelectedLCECArtifact = lstLocalLifeCycleStepsList.getLifeCycleStep(
				ndxJlstLifeCycleStepsSelector).getArtifactEffortList();
		ndxCmbLCECArtifactSelector = lstEffortCategoryList
				.getArtifactSelector(lstSelectedLCECArtifact) + 1;
		cmbLCECArtifactSelectList = lstEffortCategoryList
				.extractEffortCategoryArtifact();

		// Plan
		lstSelectedLCECPlan = lstLocalLifeCycleStepsList.getLifeCycleStep(
				ndxJlstLifeCycleStepsSelector).getPlanEffortList();
		ndxCmbLCECPlanSelector = lstEffortCategoryList
				.getPlanSelector(lstSelectedLCECPlan) + 1;
		cmbLCECPlanSelectList = lstEffortCategoryList
				.extractEffortCategoryPlan();

		// Interruption
		lstSelectedLCECInterruption = lstLocalLifeCycleStepsList
				.getLifeCycleStep(ndxJlstLifeCycleStepsSelector)
				.getInterruptionEffortList();
		ndxCmbLCECInterruptionSelector = lstEffortCategoryList
				.getInterruptionSelector(lstSelectedLCECInterruption) + 1;
		cmbLCECInterruptionSelectList = lstEffortCategoryList
				.extractEffortCategoryInterruption();

		// Defect

		lstSelectedLCECDefect = lstLocalLifeCycleStepsList.getLifeCycleStep(
				ndxJlstLifeCycleStepsSelector).getDefectEffortList();
		ndxCmbLCECDefectSelector = lstEffortCategoryList
				.getDefectSelector(lstSelectedLCECDefect) + 1;
		cmbLCECDefectSelectList = lstEffortCategoryList
				.extractEffortCategoryDefect();

		// Other
		try {
			strSelectedLCECOtherName = lstLocalLifeCycleStepsList
					.getLifeCycleStep(ndxJlstLifeCycleStepsSelector).getOtherName();
			strSelectedLCECOtherDesc = lstLifeCycleStepList.getLifeCycleStep(
					ndxJlstLifeCycleStepsSelector).getOtherDesc();
			ndxCmbLCECOtherSelector = lstEffortCategoryList.getOtherSelector(
					strSelectedLCECOtherName, strSelectedLCECOtherDesc) + 1;
			cmbLCECOtherSelectList = lstEffortCategoryList
					.extractEffortCategoryOther();
		} catch (Exception e) {

		}
		if (lstSelectedLCECArtifact.getNumberOfArtifacts() != 0 ) {
			// if the number of artifacts is not zero 
			// we select the first combo box with artifacts and
			// second combo box with associated artifact of that and default  index
			jcbLCEC.getSelectionModel().select(0);
			jcbLCECSelect.getSelectionModel().select(
					ndxCmbLCECArtifactSelector - 1);
			jcbLCECDefaultSelect.getSelectionModel().select(default_index);

		} else if (lstSelectedLCECPlan.getNumberOfPlans() != 0) {
			// if the number of plans is not zero 
			// we select the first combo box with plans and
			// second combo box with associated plan of that and default  index
			jcbLCEC.getSelectionModel().select(1);
			jcbLCECSelect.getSelectionModel()
			.select(ndxCmbLCECPlanSelector - 1);

			jcbLCECDefaultSelect.getSelectionModel().select(default_index);

		} else if (lstSelectedLCECInterruption.getNumberOfInterruptions() != 0) {
			// if the number of Interruption is not zero 
			// we select the first combo box with Interruption and
			// second combo box with associated Interruption of that and default  index
			jcbLCEC.getSelectionModel().select(2);
			jcbLCECSelect.getSelectionModel().select(
					ndxCmbLCECInterruptionSelector - 1);

			jcbLCECDefaultSelect.getSelectionModel().select(default_index);
		} else if (lstSelectedLCECDefect.getNumberOfDefects() != 0) {
			// if the number of defects is not zero 
			// we select the first combo box with defects and
			// second combo box with associated defects of that and default  index
			jcbLCEC.getSelectionModel().select(3);
			jcbLCECSelect.getSelectionModel().select(
					ndxCmbLCECDefectSelector - 1);
			jcbLCECDefaultSelect.getSelectionModel().select(default_index);
		} else {
			jcbLCEC.getSelectionModel().select(4);
			jcbLCECSelect.getSelectionModel().select(ndxCmbLCECOtherSelector-1);
			jcbLCECDefaultSelect.getSelectionModel().select(default_index);
		}
		// This change may cause changes in the state of the five buttons for
		// the Artifacts Produced list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkLifeCycleStepsButtons(); // TO BE UNCOMMENTD
	}

	/**
	 * This method is called when the user clicks on the Life Cycle Steps Up
	 * button. It causes the user input to be used to create a new Life Cycle
	 * Step and add it above the currently selected List view element
	 */
	public void performLCSNew() {
		ndxCmbLifeCycleStepsSelector = jcbLifeCycleSteps.getSelectionModel().getSelectedIndex();
		Step toBeInserted = cmbLifeCycleStepsSelectList.extractLifeCycleStep(ndxCmbLifeCycleStepsSelector);
		
		// we then combine and add the deliverables and step with and insert it into life cycle step
		lstLocalLifeCycleStepsList.addLifeCycleStep(lcstepDefaultDelivarable,
				toBeInserted, new ArtifactListController(lstLocalLCECArtifact),
				new PlanListController(lstLocalLCECPlan),
				new InterruptionListController(lstLocalLCECInterruption),
				new DefectListController(lstLocalLCECDefect),
				strLocalLCECOtherName, strLocalLCECOtherDesc);

		lcstepDefaultDelivarable = 0;
		ndxJlstLifeCycleStepsSelector = 0;
		commonUpdateAfterALifeCycleStepInsert();
	}

	public void performLCSAbove() {
		// get the selected combo box index from combo box
		ndxCmbLifeCycleStepsSelector = jcbLifeCycleSteps.getSelectionModel().getSelectedIndex();
		// Extract the step which is to be inserts
		Step toBeInserted = cmbLifeCycleStepsSelectList
				.extractLifeCycleStep(ndxCmbLifeCycleStepsSelector);
		// add that step with list controller to life cycle step
		lstLocalLifeCycleStepsList.addLifeCycleStep(
				ndxJlstLifeCycleStepsSelector, lcstepDefaultDelivarable,
				toBeInserted, new ArtifactListController(lstLocalLCECArtifact),
				new PlanListController(lstLocalLCECPlan),
				new InterruptionListController(lstLocalLCECInterruption),
				new DefectListController(lstLocalLCECDefect),
				strLocalLCECOtherName, strLocalLCECOtherDesc);
		
		lcstepDefaultDelivarable = 0;
		commonUpdateAfterALifeCycleStepInsert();
	}

	/**
	 * This method is called when the user clicks on the Life Cycle Steps Dn
	 * button. It causes the user input to be used to create a new Life Cycle
	 * Step and add it below the currently selected List view element
	 */

	public void performLCSBelow() {
		ndxCmbLifeCycleStepsSelector = jcbLifeCycleSteps.getSelectionModel().getSelectedIndex();
		// extract the associated step
		Step toBeInserted = cmbLifeCycleStepsSelectList
				.extractLifeCycleStep(ndxCmbLifeCycleStepsSelector);
		// Insert the step along with llist controllers to life cylce step
		lstLocalLifeCycleStepsList.addLifeCycleStep(
				ndxJlstLifeCycleStepsSelector + 1, lcstepDefaultDelivarable,
				toBeInserted, new ArtifactListController(lstLocalLCECArtifact),
				new PlanListController(lstLocalLCECPlan),
				new InterruptionListController(lstLocalLCECInterruption),
				new DefectListController(lstLocalLCECDefect),
				strLocalLCECOtherName, strLocalLCECOtherDesc);

		// We have inserted below the previous current item, so the new item
		// will be inserted just after
		// the previous current item. Therefore, the index of the current item
		// must be increased.
		// We changed the list, so we must tell the GUI component which item is
		// selected now.
		lcstepDefaultDelivarable = 0;
		ndxJlstLifeCycleStepsSelector++;

		// Perform the common update
		commonUpdateAfterALifeCycleStepInsert();

	}

	private void commonUpdateAfterALifeCycleStepInsert() {
		// after step insert update the GUI of the list 
		lstLifeCycleStepsSelectListSource = lstLocalLifeCycleStepsList
				.buildSelectList();

		lglLifeCycleStepsListIsBeingUpdated = true;

		ObservableList<String> items = FXCollections
				.observableArrayList(lstLifeCycleStepsSelectListSource);

		jlstLifeCycleSteps.setItems(items);

		lglLifeCycleStepsListIsBeingUpdated = false;
		// upadte the selection after insert
		jlstLifeCycleSteps.getSelectionModel().select(
				ndxJlstLifeCycleStepsSelector);

		cmbLifeCycleStepsSelectList = lstLifeCycleStepList
				.buildSubList(lstLocalLifeCycleStepsList);

		cmbModelLifeCycleSteps = cmbLifeCycleStepsSelectList.buildSelectList();

		ObservableList<String> items1 = FXCollections
				.observableArrayList(cmbModelLifeCycleSteps);

		jcbLifeCycleSteps.setItems(items1);

		if (ndxCmbLifeCycleStepsSelector >= cmbLifeCycleStepsSelectList
				.getNumberOfLifeCycleSteps())
			ndxCmbLifeCycleStepsSelector = cmbLifeCycleStepsSelectList
			.getNumberOfLifeCycleSteps() - 1;

		jcbLifeCycleSteps.getSelectionModel().select(
				ndxCmbLifeCycleStepsSelector);

		// These changes may cause changes in the state of the five buttons for the Life Cycle Steps list,
		// so we process them all rather than try to reason about which buttons might be affected. It cascade checks the main pane's buttons as well.
		checkLifeCycleButtons();
	}

	public void performLCSMvUp() {

		// This method is only used for moving a selected item up a non-empty JList when there is at least 
		// one element above it.
		
		// This statements actually does the move.  The rest are about adjusting the GUI state to match.

		lstLocalLifeCycleStepsList
		.moveLifeCycleStepUp(ndxJlstLifeCycleStepsSelector);
		// We moved it up, so to keep the same item selected, the index must be reduced by one
		ndxJlstLifeCycleStepsSelector--;
		// Update the GUI to reflect the move
		commonUpdateAfterALifeCycleStepMove();
	}

	public void performLCSMvDn() {
		// This method is only used for moving a selected item down a non-empty JList when there is at least 
		// one element below it.
				
		// This statements actually does the move.  The rest are about adjusting the GUI state to match.
		lstLocalLifeCycleStepsList
		.moveLifeCycleStepDn(ndxJlstLifeCycleStepsSelector);
		// We moved it up, so to keep the same item selected, the index must be reduced by one
		ndxJlstLifeCycleStepsSelector++;
		// Update the GUI to reflect the move
		commonUpdateAfterALifeCycleStepMove();
	}

	private void commonUpdateAfterALifeCycleStepMove() {
		// The Lifer Cycle Step list has changed, so we need to rebuild the list that is displayed
		lstLifeCycleStepsSelectListSource = lstLocalLifeCycleStepsList.buildSelectList();
		// Since we are updating the list, we need to ignore the list change events
		lglLifeCycleStepsListIsBeingUpdated = true;

		ObservableList<String> items = FXCollections
				.observableArrayList(lstLifeCycleStepsSelectListSource);
		jlstLifeCycleSteps.setItems(items);
		lglLifeCycleStepsListIsBeingUpdated = false;
		jlstLifeCycleSteps.getSelectionModel().select(
				ndxJlstLifeCycleStepsSelector);
		// These changes may cause changes in the state of the five buttons for the Artifacts Used list, 
		// so we process them all rather than try to reason about which buttons might be affected.
		checkLifeCycleButtons();
	}

	public void performLCSDelete() {

		// This method is only used for removing a selected item from a non-empty JList.
		// This statements actually does the delete.  The rest are about adjusting the GUI state to match.
		ndxJlstLifeCycleStepsSelector = jlstLifeCycleSteps.getSelectionModel().getSelectedIndex();
		
		lstLocalLifeCycleStepsList.deleteLifeCycleStep(ndxJlstLifeCycleStepsSelector);
		// Now that the artifact has been deleted, we need to clean up the UI to match this state.
		// The list has changed so we need to build a new list.
		lstLifeCycleStepsSelectListSource = lstLocalLifeCycleStepsList.buildSelectList();

		lglLifeCycleStepsListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstLifeCycleStepsSelectListSource);
		jlstLifeCycleSteps.setItems(items);

		lglLifeCycleStepsListIsBeingUpdated = false;
		// We have delete the current item from the list, so no item in the list will be selected now.
		// Therefore, we clear the index and the selection from the GUI component
		ndxJlstLifeCycleStepsSelector = -1;
		jlstLifeCycleSteps.getSelectionModel().clearSelection();

		cmbLifeCycleStepsSelectList = lstLifeCycleStepList
				.buildSubList(lstLocalLifeCycleStepsList);
		// The ComboBox list has also changed (one more item), so it must be rebuilt as well
		// This command creates this list by taking the entire list of artifacts that exist and then
		// removes those that are in the Artifacts Used list.

		cmbModelLifeCycleSteps = cmbLifeCycleStepsSelectList.buildSelectList();

		ObservableList<String> items1 = FXCollections
				.observableArrayList(cmbModelLifeCycleSteps);

		jcbLifeCycleSteps.setItems(items1);
		ndxCmbLifeCycleStepsSelector = 0;
		jcbLifeCycleSteps.getSelectionModel().select(
				ndxCmbLifeCycleStepsSelector);
		if (jcbLifeCycleSteps.getItems().size() == 0) {
			ndxJlstLifeCycleSelector = -1;
			jcbLCEC.getSelectionModel().clearSelection();
		}
		// These changes may cause changes in the state of the five buttons for the Artifacts Used list, 
		// so we process them all rather than try to reason about which buttons might be affected.
		checkLifeCycleButtons();
	}

	/*
	 * Iteration conditions functions
	 */

	public void performLCItersNew() {

		// This method is only used for inserting the first item into an empty
		// List view. (Once the List view has an
		// item in it, the user must specify whether to insert above or below
		// the selected list item.)

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbLifeCycleIterationConditionSelector = jcbLifeCycleIterationCondition
				.getSelectionModel().getSelectedIndex(); // Get the index of the
		// artifact
		Condition toBeInserted = cmbLifeCycleIterationConditionSelectList
				.extractCondition(ndxCmbLifeCycleIterationConditionSelector);
		lstLocalLifeCycleIterationConditionList.addCondition(toBeInserted); 

		// Since there is only one item in the list now, we must highlight the
		// first one as the selected
		// item
		ndxJlstLifeCycleIterationConditionSelector = 0;

		// Update the GUI after the change has been made
		commonUpdateAfterALifeCycleIterationInsert();
	}

	public void performLCItersAbove() {
		// This method is only used for inserting above a selected item into a
		// non-empty List view.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state
		// to match.
		ndxCmbLifeCycleIterationConditionSelector = jcbLifeCycleIterationCondition
				.getSelectionModel().getSelectedIndex();
		Condition toBeInserted = cmbLifeCycleIterationConditionSelectList
				.extractCondition(ndxCmbLifeCycleIterationConditionSelector);
		lstLocalLifeCycleIterationConditionList.addCondition(
				ndxJlstLifeCycleIterationConditionSelector, toBeInserted);

		// We have inserted above the previous current item, so that item will
		// be pushed down one and
		// the just inserted item will take its old place. Therefore, the index
		// of the current item
		// stays the same.

		// Update the GUI after the change has been made
		commonUpdateAfterALifeCycleIterationInsert();
	}

	public void performLCItersBelow() {
		// This method is only used for inserting above a selected item into a
		// non-empty List view.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbLifeCycleIterationConditionSelector = jcbLifeCycleIterationCondition
				.getSelectionModel().getSelectedIndex();

		Condition toBeInserted = cmbLifeCycleIterationConditionSelectList
				.extractCondition(ndxCmbLifeCycleIterationConditionSelector);

		lstLocalLifeCycleIterationConditionList.addCondition(
				ndxJlstLifeCycleIterationConditionSelector + 1, toBeInserted);

		// We have inserted below the previous current item, so the new item
		// will be inserted just after
		// the previous current tie,. Therefore, the index of the current item
		// must be increased.
		ndxJlstLifeCycleIterationConditionSelector++;

		// Update the GUI after the change has been made
		commonUpdateAfterALifeCycleIterationInsert();
	}

	private void commonUpdateAfterALifeCycleIterationInsert() {
		// The Lifer Cycle Step list has changed, so we need to rebuild the list
		// that is displayed
		lstLifeCycleIterationConditionSelectListSource = lstLocalLifeCycleIterationConditionList
				.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglLifeCycleIterationConditionListIsBeingUpdated = true;

		// Update the GUI component for the Step select list to display the new
		// list
		ObservableList<String> lcitrconditions = FXCollections
				.observableArrayList(lstLifeCycleIterationConditionSelectListSource);
		jlstLifeCycleIterationCondition.setItems(lcitrconditions);
		// We changed the list, so we must tell the GUI component which item is
		// selected now.
		jlstLifeCycleIterationCondition.getSelectionModel().select(
				ndxJlstLifeCycleIterationConditionSelector);

		// The ComboBox list has also changed (one fewer item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Life Cycle Steps list.
		cmbLifeCycleIterationConditionSelectList = lstConditionList
				.buildSubList(lstLocalLifeCycleIterationConditionList);

		// Create the ComboBox Model, complete with sequence numbers

		cmbModelLifeCycleIterationCondition = cmbLifeCycleIterationConditionSelectList
				.buildSelectList();
		// cmbLifeCycleIterationConditionSelectList.buildComboBoxModel();
		ObservableList<String> conditions = FXCollections
				.observableArrayList(cmbModelLifeCycleIterationCondition);

		// Establish the updated ComboBox using the string array that has just
		// been created
		jcbLifeCycleIterationCondition.setItems(conditions);

		// Since the size of the ComboBox is now smaller, we need to check the
		// index of the current
		// selected item to be sure that it is still in a valid range, and fix
		// it if not. (Remember,
		// it could now be empty!)
		if (ndxCmbLifeCycleIterationConditionSelector >= cmbLifeCycleIterationConditionSelectList
				.getNumberOfConditions())
			ndxCmbLifeCycleIterationConditionSelector = cmbLifeCycleIterationConditionSelectList
			.getNumberOfConditions() - 1;

		// Establish the current artifact in the ComboBox
		jcbLifeCycleIterationCondition.getSelectionModel().select(
				ndxCmbLifeCycleIterationConditionSelector);

		// We will now process changes to the list
		lglLifeCycleIterationConditionListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkLifeCycleButtons();
	}

	public void performLCItersMvUp() {
		// This method is only used for moving a selected item up a non-empty
		// List view when there is at least
		// one element above it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalLifeCycleIterationConditionList
		.moveConditionUp(ndxJlstLifeCycleIterationConditionSelector);

		// We moved it up, so to keep the same item selected, the index must be
		// reduced by one
		ndxJlstLifeCycleIterationConditionSelector--;

		// Update the GUI after the move
		commonUpdateAfterALifeCycleIterationMove();

	}

	public void performLCItersMvDn() {
		// This method is only used for moving a selected item down a non-empty
		// List view when there is at least
		// one element below it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalLifeCycleIterationConditionList
		.moveConditionDn(ndxJlstLifeCycleIterationConditionSelector);

		// We moved it up, so to keep the same item selected, the index must be
		// reduced by one
		ndxJlstLifeCycleIterationConditionSelector++;

		// Update the GUI after the move
		commonUpdateAfterALifeCycleIterationMove();
	}

	public void commonUpdateAfterALifeCycleIterationMove() {
		// The Lifer Cycle Step list has changed, so we need to rebuild the list
		// that is displayed
		lstLifeCycleIterationConditionSelectListSource = lstLocalLifeCycleIterationConditionList
				.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglLifeCycleIterationConditionListIsBeingUpdated = true;

		// Update the GUI component for the Step select list to display the new
		// list
		ObservableList<String> lcitrconditions = FXCollections
				.observableArrayList(lstLifeCycleIterationConditionSelectListSource);
		jlstLifeCycleIterationCondition.setItems(lcitrconditions);

		// Given the new list, this command will once again select the item that
		// was moved
		jlstLifeCycleIterationCondition.getSelectionModel().select(
				ndxJlstLifeCycleIterationConditionSelector);

		// We will now process changes to the list
		lglLifeCycleIterationConditionListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkLifeCycleButtons();
	}

	public void performLCItersDelete() {
		// This method is only used for removing a selected item from a
		// non-empty List view.

		// This statements actually does the delete. The rest are about
		// adjusting the GUI state to match.
		lstLocalLifeCycleIterationConditionList
		.deleteCondition(ndxJlstLifeCycleIterationConditionSelector);

		// Now that the artifact has been deleted, we need to clean up the UI to
		// match this state.

		// The list has changed so we need to build a new list.
		lstLifeCycleIterationConditionSelectListSource = lstLocalLifeCycleIterationConditionList
				.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglLifeCycleIterationConditionListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Used select list to display
		// the new list
		ObservableList<String> lcitrconditions = FXCollections
				.observableArrayList(lstLifeCycleIterationConditionSelectListSource);
		jlstLifeCycleIterationCondition.setItems(lcitrconditions);

		// We have delete the current item from the list, so no item in the list
		// will be selected now.
		// Therefore, we clear the index and the selection from the GUI
		// component
		ndxJlstLifeCycleIterationConditionSelector = -1;
		jlstLifeCycleIterationCondition.getSelectionModel().clearSelection();

		// The ComboBox list has also changed (one more item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Artifacts Used list.
		cmbLifeCycleIterationConditionSelectList = lstConditionList
				.buildSubList(lstLocalLifeCycleIterationConditionList);
		// Create the ComboBox Model, complete with sequence numbers
		cmbModelLifeCycleIterationCondition = cmbLifeCycleIterationConditionSelectList
				.buildSelectList();
		// Establish the updated ComboBox using the model that has just been
		// created
		ObservableList<String> conditions = FXCollections
				.observableArrayList(cmbModelLifeCycleIterationCondition);
		// Establish the updated ComboBox using the string array that has just
		// been created
		jcbLifeCycleIterationCondition.setItems(conditions);
		// Since the size of the ComboBox is now larger, we know that there is
		// at least one item in it,
		// so we will default the ComboBox to the first item in the list.
		// Set the index to be the first item in the list
		ndxCmbLifeCycleIterationConditionSelector = 0;
		// Set the GUI component so it has selected the first item in the list
		jcbLifeCycleIterationCondition.getSelectionModel().select(
				ndxCmbLifeCycleIterationConditionSelector);
		// We will now process changes to the list
		lglLifeCycleIterationConditionListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkLifeCycleButtons();
	}

	private void checkLifeCycleButtons() {
		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the
		// exception of the enabled status for the single large Insert, the
		// Insert Above, and the Insert Below
		// buttons which are processed in the second step below.

		// This is the first step in the process.
		int numLifeCycles = lstLifeCycleList.getNumberOfLifeCycles();
		if (numLifeCycles == 0) {
			// If the list is empty, only one insert button is visible as well
			// as the save and delete buttons.
			// The save and delete should be disabled. The semantics of the
			// enabled and disabled status of the
			// Insert buttons is more complex, so it is handled at the end of
			// this method.
			btnLCNew.setVisible(true);
			btnLCNewAbove.setDisable(true);
			btnLCNewAbove.setVisible(false);
			btnLCNewBelow.setDisable(true);
			btnLCNewBelow.setVisible(false);
			btnLCMvUp.setDisable(true);
			btnLCMvDn.setDisable(true);
			btnLCDelete.setDisable(true);
			btnLCSave.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one
			// for inserting below the selected item. So we start by making the
			// insert above and below buttons
			// visible and make the single insert button invisible and disabled.
			btnLCNewAbove.setVisible(true);
			btnLCNewBelow.setVisible(true);
			btnLCNew.setVisible(false);
			btnLCNew.setDisable(true);

			if (ndxJlstLifeCycleSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled.
				// (It is always visible.)
				btnLCDelete.setDisable(false);
				checkLifeCycleSaveButton();
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are
				// disabled (they are always visible) regardless of what is in
				// the input fields for the name, the
				// description, or the condition of the used and produced lists
				btnLCDelete.setDisable(true);
				btnLCSave.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two or more items in the list and
			// one item is selected
			if (numLifeCycles < 2 || ndxJlstLifeCycleSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected neither of the two buttons
				// should be enabled
				btnLCMvUp.setDisable(true);
				btnLCMvDn.setDisable(true);

			} else if (ndxJlstLifeCycleSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnLCMvUp.setDisable(true);
				btnLCMvDn.setDisable(false);

			} else if (ndxJlstLifeCycleSelector == numLifeCycles - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnLCMvUp.setDisable(false);
				btnLCMvDn.setDisable(true);

			} else {
				// Otherwise both options are valid.
				btnLCMvUp.setDisable(false);
				btnLCMvDn.setDisable(false);
			}
		}

		// The following is the second step.

		// The remaining code is about the enabled or disabled status of the
		// insert buttons. Visibility has been
		// handled above as has the enabled status for Delete and Save. This
		// code deals with the visibility of the
		// three insert buttons.
		if (lstLifeCycleList.getNumberOfLifeCycles() == 0
				&& strLocalLCName.length() == 0) {
			// The list is empty and the user input name field is empty, so the
			// single Insert button is visible,
			// but it is disabled. The two insert buttons were disabled above
			// and their visibility was handled above.
			btnLCNew.setDisable(true);
		} else if (lstLifeCycleList.getNumberOfLifeCycles() == 0
				&& strLocalLCName.length() > 0) {
			// The list is empty, but the user input name field is not, so the
			// single Insert button is visible and
			// enabled. The two Insert buttons are not visible (from above) and
			// are disabled.
			btnLCNew.setDisable(false);
		} else if (lstLifeCycleList.getNumberOfLifeCycles() > 0
				&& strLocalLCName.length() == 0) {
			// The list is not empty, but the user input name field is empty, so
			// the two Insert buttons are visible
			// and enabled. The single Insert button is not visible and disabled
			// from above).
			btnLCNewAbove.setDisable(true);
			btnLCNewBelow.setDisable(true);
		} else if (lstLifeCycleList.getNumberOfLifeCycles() > 0
				&& strLocalLCName.length() > 0) {
			// The list is not empty and the user input name field is not empty,
			// so the status of the two Insert
			// buttons in terms of enabled or not is determined by where or not
			// an item in the list has been selected.
			// We do know that the single insert was made not visible and
			// disabled above, so no need to do it here.
			if (ndxJlstLifeCycleSelector == -1) {
				// No item is selected, so it is not possible to insert above or
				// below it.
				btnLCNewAbove.setDisable(true);
				btnLCNewBelow.setDisable(true);
			} else {
				// An item is selected, so it is possible to insert above or
				// below it
				btnLCNewAbove.setDisable(false);
				btnLCNewBelow.setDisable(false);
			}
		}

		if (lglLocalLifeCycleComposedOfSteps) {
			checkLifeCycleStepsButtons();
			// checkLCECButtons();
		} else
			checkSubLifeCycleButtons();

		if (ndxLocalLifeCycleIterationSelector == 2)
			checkLifeCycleItersButtons();

	}

	private void checkLifeCycleStepsButtons() {
		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the
		// exception of the enabled status for the single large Insert, the
		// Insert Above, and the Insert Below
		// buttons which are processed in the second step below.

		// This is the first step in the process.
		int numLifeCycleSteps = lstLocalLifeCycleStepsList
				.getNumberOfLifeCycleSteps();
		if (numLifeCycleSteps == 0) {
			// If the list is empty, only one insert button is visible as well
			// as the save and delete buttons.
			// The save and delete should be disabled. The semantics of the
			// enabled and disabled status of the
			// Insert buttons is more complex, so it is handled at the end of
			// this method.
			btnLCSNew.setVisible(true);
			btnLCSAbove.setDisable(true);
			btnLCSAbove.setVisible(false);
			btnLCSBelow.setDisable(true);
			btnLCSBelow.setVisible(false);
			btnLCSMvUp.setDisable(true);
			btnLCSMvDn.setDisable(true);
			btnLCSDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for
			// inserting below the selected item. So we start by making the
			// insert above and below buttons
			// visible and make the single insert button invisible and disabled.
			btnLCSAbove.setVisible(true);
			btnLCSBelow.setVisible(true);
			btnLCSNew.setVisible(false);
			btnLCSNew.setDisable(true);

			if (ndxJlstLifeCycleStepsSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled.
				// (It is always visible.)
				btnLCSDelete.setDisable(false);
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are
				// disabled (they are always visible) regardless of what is in
				// the input fields for the name and
				// the description.
				btnLCSDelete.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two or more items in the list and
			// one item is selected
			if (numLifeCycleSteps < 2 || ndxJlstLifeCycleStepsSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected neither of the two buttons
				// should be enabled
				btnLCSMvUp.setDisable(true);
				btnLCSMvDn.setDisable(true);

			} else if (ndxJlstLifeCycleStepsSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnLCSMvUp.setDisable(true);
				btnLCSMvDn.setDisable(false);

			} else if (ndxJlstLifeCycleStepsSelector == numLifeCycleSteps - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnLCSMvUp.setDisable(false);
				btnLCSMvDn.setDisable(true);

			} else {
				// Otherwise both options (up and down) are valid.
				btnLCSMvUp.setDisable(false);
				btnLCSMvDn.setDisable(false);
			}
		}

		// The following is the second step... Determine the enabled status of
		// the insert buttons.

		if (numLifeCycleSteps > 0) {
			// The list is not empty
			if (ndxJlstLifeCycleStepsSelector == -1
					|| ndxCmbLifeCycleStepsSelector == -1) {
				// The list is not empty, but no item is selected or no ComboBox
				// item is selected, so it is not
				// possible to insert and artifact above or below.
				btnLCSAbove.setDisable(true);
				btnLCSBelow.setDisable(true);
			} else {
				// A non-empty list with an item selected and a selected item in
				// the ComboBox, so it is possible
				// to insert above or below
				btnLCSAbove.setDisable(false);
				btnLCSBelow.setDisable(false);
			}
		} else {
			// The list is empty, so the we need to see if the empty list insert
			// should be enabled
			if (ndxCmbLifeCycleStepsSelector == -1)
				// If the ComboBox does not have a selected item, then no insert
				// is possible
				btnLCSNew.setDisable(true);
			else
				// Empty list, but the ComboxBox has a selected item, so it
				// could be inserted into the list
				btnLCSNew.setDisable(false);

			// Since the list is empty, it is not possible to have a selected
			// element, so above or below is not possible
			btnLCSAbove.setDisable(true);
			btnLCSBelow.setDisable(true);
		}
		checkLCECButtons();
	}

	private void checkLifeCycleSaveButton() {
		if (!strLocalLCName.equals(strSelectedLCName)) {
			btnLCSave.setDisable(false);
			return;
		}
		if (!strLocalLCDesc.equals(strSelectedLCDesc)) {
			btnLCSave.setDisable(false);
			return;
		}
		if (!lstLocalLCECArtifact.isTheSameAs(lstSelectedLCECArtifact)) {
			btnLCSave.setDisable(false);
			return;
		}
		if (!lstLocalLCECPlan.isTheSameAs(lstSelectedLCECPlan)) {
			btnLCSave.setDisable(false);
			return;
		}
		if (!lstLocalLCECInterruption.isTheSameAs(lstSelectedLCECInterruption)) {
			btnLCSave.setDisable(false);
			return;
		}
		if (!lstLocalLCECDefect.isTheSameAs(lstSelectedLCECDefect)) {
			btnLCSave.setDisable(false);
			return;
		}
		if (!strLocalLCECOtherName.equals(strSelectedLCECOtherName)) {
			btnLCSave.setDisable(false);
			return;
		}
		if (!strLocalLCECOtherDesc.equals(strSelectedLCECOtherDesc)) {
			btnLCSave.setDisable(false);
			return;
		}
		if (lglSelectedLifeCycleComposedOfSteps == lglLocalLifeCycleComposedOfSteps

				// see if the iteration modes match
				&& ndxSelectedLifeCycleIterationSelector == ndxLocalLifeCycleIterationSelector

				// see if the Life Cycle Steps or the Subordinate Life Cycles
				// match
				&& (lglLocalLifeCycleComposedOfSteps
						&& lstLocalLifeCycleStepsList
						.sameAs(lstSelectedLifeCycleStepsList) || !lglLocalLifeCycleComposedOfSteps
						&& lstLocalSubLifeCycleList
						.sameAs(lstSelectedSubLifeCycleList))

						// see if the Life Cycle Iterator Conditions match
						&& (ndxLocalLifeCycleIterationSelector == 0
						|| ndxLocalLifeCycleIterationSelector == 1 || (ndxLocalLifeCycleIterationSelector == 2 && lstLocalLifeCycleIterationConditionList
						.isTheSameAs(lstSelectedLifeCycleIterationConditionList)))) {
			btnLCSave.setDisable(true);
			return;
		} else {
			btnLCSave.setDisable(false);
			return;
		}
	}

	public void checkLCECUpdate() {
		int ndx = jlstLifeCycleSteps.getSelectionModel().getSelectedIndex();
		Step toBeInserted = lstLocalLifeCycleStepsList.getLifeCycleStep(ndx)
				.getLifeCycleStepList();
		lstLocalLifeCycleStepsList.deleteLifeCycleStep(ndx);
		ndx = jcbLCECDefaultSelect.getSelectionModel().getSelectedIndex();
		lstLocalLifeCycleStepsList.addLifeCycleStep(
				ndxJlstLifeCycleStepsSelector, ndx, toBeInserted,
				new ArtifactListController(lstLocalLCECArtifact),
				new PlanListController(lstLocalLCECPlan),
				new InterruptionListController(lstLocalLCECInterruption),
				new DefectListController(lstLocalLCECDefect),
				strLocalLCECOtherName, strLocalLCECOtherDesc);
		commonUpdateAfterALifeCycleStepInsert();

		int ndx1 = jlstLifeCycles.getSelectionModel().getSelectedIndex();
		if(ndx1!=-1)
		{
			lstLifeCycleList.deleteLifeCycle(ndx1);

			commonUpdateBeforeALifeCycleInsert();

			lstLifeCycleList.addLifeCycle(ndxJlstLifeCycleSelector, strLocalLCName,
					strLocalLCDesc, lstSelectedSubLifeCycleList,
					lstLocalLifeCycleIterationConditionList,
					lglLocalLifeCycleComposedOfSteps,
					ndxLocalLifeCycleIterationSelector, lstLocalLifeCycleStepsList);

			commonUpdateAfterALifeCycleInsert();
		}
		checkLifeCycleButtons();
		checkLCECButtons();
	}

	private void checkLCECButtons() {
		int numberOfSteps = jcbLifeCycleSteps.getItems().size();
		int index = jcbLifeCycleComposedOfOption.getSelectionModel().getSelectedIndex();
		int stepIndex = jlstLifeCycleSteps.getSelectionModel().getSelectedIndex();
		if(numberOfSteps==0 ||index==1) 
		{

			if(jlstLifeCycleSteps.getItems().size()==0 || jcbLifeCycleComposedOfOption.getSelectionModel().getSelectedIndex()==1)
			{
				ndxJlstLifeCycleStepsSelector = -1;
				jcbLCEC.getSelectionModel().clearSelection();
				labelECDefinitions.setVisible(false);
				jcbLCEC.setVisible(false);
				jcbLCECSelect.setVisible(false);
				btnLCECUpdate.setVisible(false);
				lableEffortCategory.setVisible(false);
				labelSelect.setVisible(false);
				jcbLCECDefaultSelect.setVisible(false);
				labelSelectDefault.setVisible(false);
			}
			else
			{
				labelECDefinitions.setVisible(true);
				lableEffortCategory.setVisible(true);
				jcbLCEC.setVisible(true);
				int Select = jcbLCEC.getSelectionModel().getSelectedIndex();
				if(Select!=-1)
				{
					if(jcbLCECSelect.getItems().size()!=0)
					{
						labelSelect.setVisible(true);
						jcbLCECSelect.setVisible(true);
						int letSelect = jcbLCECSelect.getSelectionModel().getSelectedIndex();
						if(letSelect!=-1)
						{
							labelSelectDefault.setVisible(true);
							jcbLCECDefaultSelect.setVisible(true);
							if(stepIndex!=-1)
								btnLCECUpdate.setVisible(true);
						}
					}
				}
			}
		}
		else
		{
			if(jcbLifeCycleSteps.getItems().size()!=0)
			{
				labelECDefinitions.setVisible(true);
				lableEffortCategory.setVisible(true);
				jcbLCEC.setVisible(true);
				int Select = jcbLCEC.getSelectionModel().getSelectedIndex();

				if(Select!=-1)
				{
					if(jcbLCECSelect.getItems().size()!=0)
					{
						labelSelect.setVisible(true);
						jcbLCECSelect.setVisible(true);
						int letSelect = jcbLCECSelect.getSelectionModel().getSelectedIndex();
						if(letSelect!=-1)
						{
							labelSelectDefault.setVisible(true);
							jcbLCECDefaultSelect.setVisible(true);
							if(stepIndex!=-1)
								btnLCECUpdate.setVisible(true);
						}
					}
				}
			}
			else if(stepIndex==-1)
			{
				ndxJlstLifeCycleSelector = -1;
				jcbLCEC.getSelectionModel().clearSelection();
				jcbLCECSelect.getSelectionModel().clearSelection();
			}


		}

		if(selectListLifeCycleitemselected==true)
		{
			jcbLCEC.getSelectionModel().clearSelection();
			jcbLCECSelect.getSelectionModel().clearSelection();
			jcbLCECDefaultSelect.getSelectionModel().clearSelection();
		}
		if(jcbLCEC.getSelectionModel().getSelectedIndex()==4)
		{
			jcbLCECDefaultSelect.setVisible(false);
			labelSelectDefault.setVisible(false);
		}
		if(flagger==1)
		{
			jcbLCECDefaultSelect.setVisible(false);
			labelSelectDefault.setVisible(false);
		}

	}

	private void checkSubLifeCycleButtons() {
		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the
		// exception of the enabled status for the single large Insert, the
		// Insert Above, and the Insert Below
		// buttons which are processed in the second step below.

		// This is the first step in the process.
		int numSubLifeCycle = lstLocalSubLifeCycleList.getNumberOfLifeCycles();
		if (numSubLifeCycle == 0) {
			// If the list is empty, only one insert button is visible as well
			// as the save and delete buttons.
			// The save and delete should be disabled. The semantics of the
			// enabled and disabled status of the
			// Insert buttons is more complex, so it is handled at the end of
			// this method.
			btnSubLCNew.setVisible(true);
			btnSubLCAbove.setDisable(true);
			btnSubLCAbove.setVisible(false);
			btnSubLCBelow.setDisable(true);
			btnSubLCBelow.setVisible(false);
			btnSubLCMvUp.setDisable(true);
			btnSubLCMvDn.setDisable(true);
			btnSubLCDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for
			// inserting below the selected item. So we start by making the
			// insert above and below buttons
			// visible and make the single insert button invisible and disabled.
			btnSubLCAbove.setVisible(true);
			btnSubLCBelow.setVisible(true);
			btnSubLCNew.setVisible(false);
			btnSubLCNew.setDisable(true);

			if (ndxJlstSubLifeCycleSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled.
				// (It is always visible.)
				btnSubLCDelete.setDisable(false);
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are
				// disabled (they are always visible) regardless of what is in
				// the input fields for the name and
				// the description.
				btnSubLCDelete.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two or more items in the list and
			// one item is selected
			if (numSubLifeCycle < 2 || ndxJlstSubLifeCycleSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected neither of the two buttons
				// should be enabled
				btnSubLCMvUp.setDisable(true);
				btnSubLCMvDn.setDisable(true);

			} else if (ndxJlstSubLifeCycleSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnSubLCMvUp.setDisable(true);
				btnSubLCMvDn.setDisable(false);

			} else if (ndxJlstSubLifeCycleSelector == numSubLifeCycle - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnSubLCMvUp.setDisable(false);
				btnSubLCMvDn.setDisable(true);

			} else {
				// Otherwise both options (up and down) are valid.
				btnSubLCMvUp.setDisable(false);
				btnSubLCMvDn.setDisable(false);
			}
		}

		// The following is the second step... Determine the enabled status of
		// the insert buttons.

		if (numSubLifeCycle > 0) {
			// The list is not empty
			if (ndxJlstSubLifeCycleSelector == -1
					|| ndxCmbSubLifeCycleSelector == -1) {
				// The list is not empty, but no item is selected or no ComboBox
				// item is selected, so it is not
				// possible to insert and artifact above or below.
				btnSubLCAbove.setDisable(true);
				btnSubLCBelow.setDisable(true);
			} else {
				// A non-empty list with an item selected and a selected item in
				// the ComboBox, so it is possible
				// to insert above or below
				btnSubLCAbove.setDisable(false);
				btnSubLCBelow.setDisable(false);
			}
		} else {
			// The list is empty, so the we need to see if the empty list insert
			// should be enabled
			if (ndxCmbSubLifeCycleSelector == -1)
				// If the ComboBox does not have a selected item, then no insert
				// is possible
				btnSubLCNew.setDisable(true);
			else
				// Empty list, but the ComboxBox has a selected item, so it
				// could be inserted into the list
				btnSubLCNew.setDisable(false);

			// Since the list is empty, it is not possible to have a selected
			// element, so above or below is not possible
			btnSubLCAbove.setDisable(true);
			btnSubLCBelow.setDisable(true);
		}
	}

	private void checkLifeCycleItersButtons() {
		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the
		// exception of the enabled status for the single large Insert, the
		// Insert Above, and the Insert Below
		// buttons which are processed in the second step below.

		// This is the first step in the process.
		int numLCIters = lstLocalLifeCycleIterationConditionList
				.getNumberOfConditions();
		if (numLCIters == 0) {
			// If the list is empty, only one insert button is visible as well
			// as the save and delete buttons.
			// The save and delete should be disabled. The semantics of the
			// enabled and disabled status of the
			// Insert buttons is more complex, so it is handled at the end of
			// this method.
			btnLCItersNew.setVisible(true);
			btnLCItersAbove.setDisable(true);
			btnLCItersAbove.setVisible(false);
			btnLCItersBelow.setDisable(true);
			btnLCItersBelow.setVisible(false);
			btnLCItersMvUp.setDisable(true);
			btnLCItersMvDn.setDisable(true);
			btnLCItersDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for
			// inserting below the selected item. So we start by making the
			// insert above and below buttons
			// visible and make the single insert button invisible and disabled.
			btnLCItersAbove.setVisible(true);
			btnLCItersBelow.setVisible(true);
			btnLCItersNew.setVisible(false);
			btnLCItersNew.setDisable(true);

			if (ndxJlstLifeCycleIterationConditionSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled.
				// (It is always visible.)
				btnLCItersDelete.setDisable(false);
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are
				// disabled (they are always visible) regardless of what is in
				// the input fields for the name and
				// the description.
				btnLCItersDelete.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two or more items in the list and
			// one item is selected
			if (numLCIters < 2
					|| ndxJlstLifeCycleIterationConditionSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected neither of the two buttons
				// should be enabled
				btnLCItersMvUp.setDisable(true);
				btnLCItersMvDn.setDisable(true);

			} else if (ndxJlstLifeCycleIterationConditionSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnLCItersMvUp.setDisable(true);
				btnLCItersMvDn.setDisable(false);

			} else if (ndxJlstLifeCycleIterationConditionSelector == numLCIters - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnLCItersMvUp.setDisable(false);
				btnLCItersMvDn.setDisable(true);

			} else {
				// Otherwise both options (up and down) are valid.
				btnLCItersMvUp.setDisable(false);
				btnLCItersMvDn.setDisable(false);
			}
		}

		// The following is the second step... Determine the enabled status of
		// the insert buttons.

		if (numLCIters > 0) {
			// The list is not empty
			if (ndxJlstLifeCycleIterationConditionSelector == -1
					|| ndxCmbLifeCycleIterationConditionSelector == -1) {
				// The list is not empty, but no item is selected or no ComboBox
				// item is selected, so it is not
				// possible to insert and artifact above or below.
				btnLCItersAbove.setDisable(true);
				btnLCItersBelow.setDisable(true);
			} else {
				// A non-empty list with an item selected and a selected item in
				// the ComboBox, so it is possible
				// to insert above or below
				btnLCItersAbove.setDisable(false);
				btnLCItersBelow.setDisable(false);
			}
		} else {
			// The list is empty, so the we need to see if the empty list insert
			// should be enabled
			if (ndxCmbLifeCycleIterationConditionSelector == -1)
				// If the ComboBox does not have a selected item, then no insert
				// is possible
				btnLCItersNew.setDisable(true);
			else
				// Empty list, but the ComboxBox has a selected item, so it
				// could be inserted into the list
				btnLCItersNew.setDisable(false);

			// Since the list is empty, it is not possible to have a selected
			// element, so above or below is not possible
			btnLCItersAbove.setDisable(true);
			btnLCItersBelow.setDisable(true);
		}
	}


	

	/*********************************************************************************************************************
	 * 
	 * Artifacts
	 * 
	 */
	
	
	
	/*****
	 * This method is called when the Insert button is pressed to insert the
	 * first Artifact Used into a task's Artifact Used list. This code follows a
	 * pattern that is nearly the same as for performArtUsedAbove and
	 * performArtUsedBelow. Any changes to this or the other two methods and you
	 * should think about all three.
	 */
	public void performArtUsedNew() {
		// This method is only used for inserting the first item into an empty
		// JList. (Once the JList has an
		// item in it, the user must specify whether to insert above or below
		// the selected list item.)

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbArtUsedSelector = jcbArtUsed.getSelectionModel()
				.getSelectedIndex(); // Get the index of the artifact

		Artifact toBeInserted = cmbArtifactsUsedSelectList
				.extractArtifact(ndxCmbArtUsedSelector);

		lstLocalArtifactsUsedList.addArtifact(toBeInserted); // Insert the
		// artifact into
		// the list

		// Since there is only one item in the list now, we must highlight the
		// first one as the
		// selected item
		ndxJlstArtUsedSelector = 0;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAnArtifactUsedInsert();
	}

	/*****
	 * This method is called when the Insert button is pressed to insert an
	 * Artifact Used into a task's existing Artifact Used list above the current
	 * selected item. This code follows a pattern that is nearly the same as for
	 * performArtUsedNew and performArtUsedBelow. Any changes to this or the
	 * other two methods and you should think about all three.
	 */
	public void performArtUsedAbove() {
		// This method is only used for inserting above a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbArtUsedSelector = jcbArtUsed.getSelectionModel()
				.getSelectedIndex();

		Artifact toBeInserted = cmbArtifactsUsedSelectList
				.extractArtifact(ndxCmbArtUsedSelector);

		lstLocalArtifactsUsedList.addArtifact(ndxJlstArtUsedSelector,
				toBeInserted);

		// We have inserted above the previous current item, so that item will
		// be pushed down one and
		// the just inserted item will take its old place. Therefore, the index
		// of the current item
		// stays the same.

		// Update the GUI to reflect the just made change
		commonUpdateAfterAnArtifactUsedInsert();
	}

	/*****
	 * This method is called when the Insert button is pressed to insert an
	 * Artifact Used into a task's existing Artifact Used list below the current
	 * selected item. This code follows a pattern that is nearly the same as for
	 * performArtUsedNew and performArtUsedAbove. Any changes to this or the
	 * other two methods and you should think about all three.
	 */
	public void performArtUsedBelow() {
		// This method is only used for inserting below a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbArtUsedSelector = jcbArtUsed.getSelectionModel()
				.getSelectedIndex();
		Artifact toBeInserted = cmbArtifactsUsedSelectList
				.extractArtifact(ndxCmbArtUsedSelector);
		lstLocalArtifactsUsedList.addArtifact(ndxJlstArtUsedSelector + 1,
				toBeInserted);

		// We have inserted below the previous current item, so this just
		// inserted item will have an
		// index that is one larger that the previous selected item. Therefore,
		// the index of the
		// current item is incremented.
		ndxJlstArtUsedSelector++;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAnArtifactUsedInsert();
	}

	private void commonUpdateAfterAnArtifactUsedInsert() {
		// The Artifact used list has changed, so we need to rebuild the list
		// that is displayed
		lstArtUsedSelectListSource = lstLocalArtifactsUsedList
				.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglArtUsedListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Used select list to display
		// the new list

		ObservableList lstArtUsedObservable = FXCollections
				.observableArrayList(lstArtUsedSelectListSource);

		jlstArtUsed.setItems(lstArtUsedObservable);

		// We will now process changes to the list
		lglArtUsedListIsBeingUpdated = false;

		// We changed the list, so we must tell the GUI component which item is
		// selected now.
		jlstArtUsed.getSelectionModel().select(ndxJlstArtUsedSelector);

		// The ComboBox list has also changed (one fewer item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Artifacts Used list.
		cmbArtifactsUsedSelectList = lstArtifactList
				.buildSubList(lstLocalArtifactsUsedList);

		// Create the ComboBox Model, complete with sequence numbers
		cmbModelArtUsed = cmbArtifactsUsedSelectList.buildSelectList();

		// Establish the updated ComboBox using the model that has just been
		// created

		ObservableList cmbModelArtUsedObservable = FXCollections
				.observableArrayList(cmbModelArtUsed);

		jcbArtUsed.setItems(cmbModelArtUsedObservable);

		// Since the size of the ComboBox is now smaller, we need to check the
		// index of the current
		// selected item to be sure that it is still in a valid range, and fix
		// it if not. (Remember,
		// it could now be empty!)
		if (ndxCmbArtUsedSelector >= cmbArtifactsUsedSelectList
				.getNumberOfArtifacts())
			ndxCmbArtUsedSelector = cmbArtifactsUsedSelectList
					.getNumberOfArtifacts() - 1;

		// Establish the current artifact in the ComboBox
		jcbArtUsed.getSelectionModel().select(ndxCmbArtUsedSelector);

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkArtUsedButtons();
	}

	/*****
	 * This method is called when the Delete button is pressed to remove the
	 * current selected Artifact Used from a task's existing Artifact Used list.
	 */
	public void performArtUsedDelete() {
		// This method is only used for removing a selected item from a
		// non-empty JList.

		// This statements actually does the delete. The rest are about
		// adjusting the GUI state to match.
		lstLocalArtifactsUsedList.deleteArtifact(ndxJlstArtUsedSelector);

		// Now that the artifact has been deleted, we need to clean up the UI to
		// match this state.

		// The list has changed so we need to build a new list.
		lstArtUsedSelectListSource = lstLocalArtifactsUsedList
				.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglArtUsedListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Used select list to display
		// the new list
		ObservableList<String> lstArtUsedSelectObservable = FXCollections
				.observableArrayList(lstArtUsedSelectListSource);
		jlstArtUsed.setItems(lstArtUsedSelectObservable);

		// We have delete the current item from the list, so no item in the list
		// will be selected now.
		// Therefore, we clear the index and the selection from the GUI
		// component
		ndxJlstArtUsedSelector = -1;
		jlstArtUsed.getSelectionModel().clearSelection();

		// The ComboBox list has also changed (one more item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Artifacts Used list.
		cmbArtifactsUsedSelectList = lstArtifactList
				.buildSubList(lstLocalArtifactsUsedList);

		// Create the ComboBox Model, complete with sequence numbers
		cmbModelArtUsed = cmbArtifactsUsedSelectList.buildSelectList();

		// Establish the updated ComboBox using the model that has just been
		// created
		ObservableList<String> cmbModelArtUsedObservable = FXCollections
				.observableArrayList(cmbModelArtUsed);
		jcbArtUsed.setItems(cmbModelArtUsedObservable);

		// Since the size of the ComboBox is now larger, we know that there is
		// at least one item in it,
		// so we will default the ComboBox to the first item in the list.

		// Set the index to be the first item in the list
		ndxCmbArtUsedSelector = 0;

		// Set the GUI component so it has selected the first item in the list
		jcbArtUsed.getSelectionModel().select(ndxCmbArtUsedSelector);

		// We will now process changes to the list
		lglArtUsedListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkArtUsedButtons();
	}

	/*****
	 * This method is called when the Move Up button is pressed to move the
	 * current selected Artifact Used from its current location in the list to
	 * the next position toward the top.
	 */
	public void performArtUsedMvUp() {
		// This method is only used for moving a selected item up a non-empty
		// JList when there is at least
		// one element above it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalArtifactsUsedList.moveArtifactUp(ndxJlstArtUsedSelector);

		// We moved it up, so to keep the same item selected, the index must be
		// reduced by one
		ndxJlstArtUsedSelector--;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAnArtifactUsedMove();
	}

	/*****
	 * This method is called when the Move Dn button is pressed to move the
	 * current selected Artifact Used from its current location in the list to
	 * the next position toward the bottom.
	 */
	public void performArtUsedMvDn() {
		// This method is only used for moving a selected item down a non-empty
		// JList when there is at least
		// one element below it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalArtifactsUsedList.moveArtifactDn(ndxJlstArtUsedSelector);

		// We moved it down, so to keep the same item selected, the index must
		// be increased by one
		ndxJlstArtUsedSelector++;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAnArtifactUsedMove();
	}

	private void commonUpdateAfterAnArtifactUsedMove() {
		// The list has changed so we need to build a new list.
		lstArtUsedSelectListSource = lstLocalArtifactsUsedList
				.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglArtUsedListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Used select list to display
		// the new list
		ObservableList lstArtUsedObservable = FXCollections
				.observableArrayList(lstArtUsedSelectListSource);

		jlstArtUsed.setItems(lstArtUsedObservable);

		// Given the new list, this command will once again select the item that
		// was moved
		jlstArtUsed.getSelectionModel().select(ndxJlstArtUsedSelector);

		// We will now process changes to the list
		lglArtUsedListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkArtUsedButtons();
	}

	/*****
	 * This method is called when the Insert button is pressed to insert the
	 * first Artifact Produced into a task's Artifact Produced list. This code
	 * follows a pattern that is nearly the same as for performArtProducedAbove
	 * and performArtProducedBelow. Any changes to this or the other two methods
	 * and you should think about all three.
	 */
	public void performArtProducedNew() {
		// This method is only used for inserting the first item into an empty
		// JList. (Once the JList has an
		// item in it, the user must specify whether to insert above or below
		// the selected list item.)

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbArtProducedSelector = jcbArtProduced.getSelectionModel()
				.getSelectedIndex(); // Get the index of the artifact

		Artifact toBeInserted = cmbArtifactsProducedSelectList
				.extractArtifact(ndxCmbArtProducedSelector);

		lstLocalArtifactsProducedList.addArtifact(toBeInserted); // Insert the
		// artifact
		// into the
		// list

		// Since there is only one item in the list now, we must highlight the
		// first one as the selected item
		ndxJlstArtProducedSelector = 0;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAnArtifactProducedInsert();
	}

	/*****
	 * This method is called when the Insert button is pressed to insert an
	 * Artifact Produced into a task's existing Artifact Produced list above the
	 * current selected item. This code follows a pattern that is nearly the
	 * same as for performArtProducedNew and performArtProducedBelow. Any
	 * changes to this or the other two methods and you should think about all
	 * three.
	 */
	public void performArtProducedAbove() {
		// This method is only used for inserting above a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbArtProducedSelector = jcbArtProduced.getSelectionModel()
				.getSelectedIndex();

		Artifact toBeInserted = cmbArtifactsProducedSelectList
				.extractArtifact(ndxCmbArtProducedSelector);

		lstLocalArtifactsProducedList.addArtifact(ndxJlstArtProducedSelector,
				toBeInserted);

		ndxJlstArtProducedSelector = ndxJlstArtProducedSelector + 1;
		// We have inserted above the previous current item, so that item will
		// be pushed down one and the just
		// inserted item will take its old place. Therefore, the index of the
		// current item stays the same.

		// Update the GUI to reflect the just made change
		commonUpdateAfterAnArtifactProducedInsert();
	}

	/*****
	 * This method is called when the Insert button is pressed to insert an
	 * Artifact Produced into a task's existing Artifact Produced list below the
	 * current selected item. This code follows a pattern that is nearly the
	 * same as for performArtUsedNew and performArtUsedAbove. Any changes to
	 * this or the other two methods and you should think about all three.
	 */
	public void performArtProducedBelow() {
		// This method is only used for inserting below a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbArtProducedSelector = jcbArtProduced.getSelectionModel()
				.getSelectedIndex();
		Artifact toBeInserted = cmbArtifactsProducedSelectList
				.extractArtifact(ndxCmbArtProducedSelector);
		lstLocalArtifactsProducedList.addArtifact(
				ndxJlstArtProducedSelector + 1, toBeInserted);

		// We have inserted below the previous current item, so this just
		// inserted item will have an index that is
		// one larger that the previous selected item. Therefore, the index of
		// the current item is incremented.
		// We changed the list, so we must tell the GUI component which item is
		// selected now.
		ndxJlstArtProducedSelector++;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAnArtifactProducedInsert();
	}

	private void commonUpdateAfterAnArtifactProducedInsert() {
		// The Artifact Produced list has changed, so we need to rebuild the
		// list that is displayed
		lstArtProducedSelectListSource = lstLocalArtifactsProducedList
				.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglArtProducedListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Produced select list to
		// display the new list
		ObservableList lstArtProducedObservable = FXCollections
				.observableArrayList(lstArtProducedSelectListSource);
		jlstArtProduced.setItems(lstArtProducedObservable);

		// We will now process changes to the list
		lglArtProducedListIsBeingUpdated = false;

		// We changed the list, so we must tell the GUI component which item is
		// selected now.
		jlstArtProduced.getSelectionModel().select(ndxJlstArtProducedSelector);

		// The ComboBox list has also changed (one fewer item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Artifacts Produced list.
		cmbArtifactsProducedSelectList = lstArtifactList
				.buildSubList(lstLocalArtifactsProducedList);

		// Create the ComboBox Model, complete with sequence numbers
		cmbModelArtProduced = cmbArtifactsProducedSelectList.buildSelectList();

		// Establish the updated ComboBox using the model that has just been
		// created
		ObservableList cmbModelArtProducedObservable = FXCollections
				.observableArrayList(cmbModelArtProduced);
		jcbArtProduced.setItems(cmbModelArtProducedObservable);
		// Since the size of the ComboBox is now smaller, we need to check the
		// index of the current
		// selected item to be sure that it is still in a valid range, and fix
		// it if not. (Remember,
		// it could now be empty!)
		if (ndxCmbArtProducedSelector >= cmbArtifactsProducedSelectList
				.getNumberOfArtifacts())
			ndxCmbArtProducedSelector = cmbArtifactsProducedSelectList
					.getNumberOfArtifacts() - 1;

		// Establish the current artifact in the ComboBox
		jcbArtProduced.getSelectionModel().select(ndxCmbArtProducedSelector);

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkArtProducedButtons();
	}

	/*****
	 * This method is called when the Delete button is pressed to remove the
	 * current selected Artifact Produced from a task's existing Artifact
	 * Produced list.
	 */
	public void performArtProducedDelete() {
		// This method is only used for removing a selected item from a
		// non-empty JList.

		// This statements actually does the delete. The rest are about
		// adjusting the GUI state to match.
		lstLocalArtifactsProducedList
				.deleteArtifact(ndxJlstArtProducedSelector);

		// Now that the artifact has been deleted, we need to clean up the UI to
		// match this state.

		// The list has changed so we need to build a new list.
		lstArtProducedSelectListSource = lstLocalArtifactsProducedList
				.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglArtProducedListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Used select list to display
		// the new list
		ObservableList<String> lstArtProducedObservable = FXCollections
				.observableArrayList(lstArtProducedSelectListSource);
		jlstArtProduced.setItems(lstArtProducedObservable);

		// We have delete the current item from the list, so no item in the list
		// will be selected now.
		// Therefore, we clear the index and the selection from the GUI
		// component
		ndxJlstArtProducedSelector = -1;
		jlstArtProduced.getSelectionModel().clearSelection();

		// The ComboBox list has also changed (one more item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Artifacts Used list.
		cmbArtifactsProducedSelectList = lstArtifactList
				.buildSubList(lstLocalArtifactsProducedList);

		cmbModelArtProduced = cmbArtifactsProducedSelectList.buildSelectList();

		// Create the ComboBox Model, complete with sequence numbers
		ObservableList<String> cmbModelArtProducedObservable = FXCollections
				.observableArrayList(cmbModelArtProduced);
		jcbArtProduced.setItems(cmbModelArtProducedObservable);

		// Since the size of the ComboBox is now larger, we know that there is
		// at least one item in it,
		// so we will default the ComboBox to the first item in the list.

		// Set the index to be the first item in the list
		ndxCmbArtProducedSelector = 0;

		// Set the GUI component so it has selected the first item in the list
		jcbArtProduced.getSelectionModel().select(ndxCmbArtProducedSelector);

		// We will now process changes to the list
		lglArtProducedListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkArtProducedButtons();
	}

	/*****
	 * This method is called when the Move Up button is pressed to move the
	 * current selected Artifact Produced from its current location in the list
	 * to the next position toward the top.
	 */
	public void performArtProducedMvUp() {
		// This method is only used for moving a selected item up a non-empty
		// JList when there is at least
		// one element above it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalArtifactsProducedList
				.moveArtifactUp(ndxJlstArtProducedSelector);

		// We moved it up, so to keep the same item selected, the index must be
		// reduced by one
		ndxJlstArtProducedSelector--;

		// Update the GUI to reflect the just made changes
		commonUpdateAfterAnArtifactProducedMove();
	}

	/*****
	 * This method is called when the Move Dn button is pressed to move the
	 * current selected Artifact Produced from its current location in the list
	 * to the next position toward the bottom.
	 */
	public void performArtProducedMvDn() {
		// This method is only used for moving a selected item down a non-empty
		// JList when there is at least
		// one element below it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalArtifactsProducedList
				.moveArtifactDn(ndxJlstArtProducedSelector);

		// We moved it down, so to keep the same item selected, the index must
		// be increased by one
		ndxJlstArtProducedSelector++;

		// Update the GUI to reflect the just made changes
		commonUpdateAfterAnArtifactProducedMove();
	}

	private void commonUpdateAfterAnArtifactProducedMove() {
		// The list has changed so we need to build a new list.
		lstArtProducedSelectListSource = lstLocalArtifactsProducedList
				.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglArtProducedListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Produced select list to
		// display the new list
		ObservableList lstArtProducedObservable = FXCollections
				.observableArrayList(lstArtProducedSelectListSource);
		jlstArtProduced.setItems(lstArtProducedObservable);

		// Given the new list, this command will once again select the item that
		// was moved
		jlstArtProduced.getSelectionModel().select(ndxJlstArtProducedSelector);

		// We will now process changes to the list
		lglArtProducedListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Produced list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkArtProducedButtons();
	}

	/*****
	 * This method is used when the user click on one of the items in the
	 * Artifacts Used list. This change in the selection does not change
	 * anything other attribute, so the only impact other than the new selection
	 * is the possible change in the associated action buttons (are they enabled
	 * or not).
	 * 
	 * @param event
	 */
	public void jltArtUsedListItemSelected() {
		// The language can't be sure that this cast
		// is safe even though *we* know no other GUI
		// component uses this method.
		// Fetch the selected index from the JList.
		// int ndx = ((JList) (event.getSource())).getMinSelectionIndex();
		int ndx = jlstArtUsed.getSelectionModel().getSelectedIndex();

		// If the index matches the current local copy, there is no need to
		// proceed
		if (ndxJlstArtUsedSelector == ndx)
			return;

		// If the index is different, then we must update the local copy,
		// remember the new index
		ndxJlstArtUsedSelector = ndx;

		// This change may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkArtUsedButtons();
	}

	public void comboBoxArtProducedListItemSelected() {
		// No actions required when this ComboBox is changed
	}

	public void comboBoxArtUsedListItemSelected() {
		// No actions required when this ComboBox is changed
	}

	public void comboBoxStepTaskListItemSelected() {

	}

	/*****
	 * This method is used when the user click on one of the items in the
	 * Artifacts Produced list. This change in the selection does not change
	 * anything other attribute, so the only impact other than the new selection
	 * is the possible change in the associated action buttons (are they enabled
	 * or not).
	 * 
	 * @param event
	 */
	public void selectListArtProducedListItemSelected() {
		// The language can't be sure that this cast
		// is safe even though *we* know no other GUI
		// component uses this method.
		// Fetch the selected index from the JList.
		// int ndx = ((JList) (event.getSource())).getMinSelectionIndex();
		int ndx = jlstArtProduced.getSelectionModel().getSelectedIndex();

		// If the index matches the current local copies, there is no need to
		// proceed
		if (ndxJlstArtProducedSelector == ndx)
			return;

		// If the index is different, then we must update the local copies,
		// remember the new index
		ndxJlstArtProducedSelector = ndx;

		// This change may cause changes in the state of the five buttons for
		// the Artifacts Produced list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkArtProducedButtons();
	}

	/*****
	 * This method is called any time the user changes something that *may*
	 * cause a change in state that could influence which of the related buttons
	 * are visible or enabled. Rather than deal with each change separately,
	 * this method processes the state and determines the proper condition
	 * (visible and enabled) for *all* of the buttons.
	 */
	private void checkArtUsedButtons() {
		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the
		// exception of the enabled status for the single large Insert, the
		// Insert Above, and the Insert Below
		// buttons which are processed in the second step below.

		// This is the first step in the process.
		int numArtifacts = lstLocalArtifactsUsedList.getNumberOfArtifacts();
		if (numArtifacts == 0) {
			// If the list is empty, only one insert button is visible as well
			// as the save and delete buttons.
			// The save and delete should be disabled. The semantics of the
			// enabled and disabled status of the
			// Insert buttons is more complex, so it is handled at the end of
			// this method.
			btnArtUsedNew.setVisible(true);
			btnArtUsedAbove.setDisable(true);
			btnArtUsedAbove.setVisible(false);
			btnArtUsedBelow.setDisable(true);
			btnArtUsedBelow.setVisible(false);
			btnArtUsedMvUp.setDisable(true);
			btnArtUsedMvDn.setDisable(true);
			btnArtUsedDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for
			// inserting below the selected item. So we start by making the
			// insert above and below buttons
			// visible and make the single insert button invisible and disabled.
			btnArtUsedAbove.setVisible(true);
			btnArtUsedBelow.setVisible(true);
			btnArtUsedNew.setVisible(false);
			btnArtUsedNew.setDisable(true);

			if (ndxJlstArtUsedSelector > -1) {

				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled.
				// (It is always visible.)
				btnArtUsedDelete.setDisable(false);
			} else {

				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are
				// disabled (they are always visible) regardless of what is in
				// the input fields for the name and
				// the description.
				btnArtUsedDelete.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two or more items in the list and
			// one item is selected
			if (numArtifacts < 2 || ndxJlstArtUsedSelector == -1) {

				// If there are not at least two items in the list or no item is
				// selected neither of the two buttons
				// should be enabled
				btnArtUsedMvUp.setDisable(true);
				btnArtUsedMvDn.setDisable(true);

			} else if (ndxJlstArtUsedSelector == 0) {

				// If the first item in the list is selected, you can move down,
				// but not up
				btnArtUsedMvUp.setDisable(true);
				btnArtUsedMvDn.setDisable(false);

			} else if (ndxJlstArtUsedSelector == numArtifacts - 1) {

				// If the last item in the list is selected, you can move up but
				// not down
				btnArtUsedMvUp.setDisable(false);
				btnArtUsedMvDn.setDisable(true);

			} else {
				// Otherwise both options (up and down) are valid.
				btnArtUsedMvUp.setDisable(false);
				btnArtUsedMvDn.setDisable(false);
			}
		}

		// The following is the second step... Determine the enabled status of
		// the insert buttons.

		if (lstLocalArtifactsUsedList.getNumberOfArtifacts() > 0) {

			// The list is not empty
			if (ndxJlstArtUsedSelector == -1 || ndxCmbArtUsedSelector == -1) {

				// The list is not empty, but no item is selected or no ComboBox
				// item is selected, so it is not
				// possible to insert and artifact above or below.
				btnArtUsedAbove.setDisable(true);
				btnArtUsedBelow.setDisable(true);
			} else {

				// A non-empty list with an item selected and a selected item in
				// the ComboBox, so it is possible
				// to insert above or below
				btnArtUsedAbove.setDisable(false);
				btnArtUsedBelow.setDisable(false);
			}
		} else {

			// The list is empty, so the we need to see if the empty list insert
			// should be enabled
			if (ndxCmbArtUsedSelector == -1) {

				// If the ComboBox does not have a selected item, then no insert
				// is possible
				btnArtUsedNew.setDisable(true);
			} else {

				// Empty list, but the ComboxBox has a selected item, so it
				// could be inserted into the list
				btnArtUsedNew.setDisable(false);
			}
			// Since the list is empty, it is not possible to have a selected
			// element, so above or below is not possible

			btnArtUsedAbove.setDisable(true);
			btnArtUsedBelow.setDisable(true);
		}

	}

	/*****
	 * This method is called any time the user changes something that *may*
	 * cause a change in state that could influence which of the related buttons
	 * are visible or enabled. Rather than deal with each change separately,
	 * this method processes the state and determines the proper condition
	 * (visible and enabled) for *all* of the buttons.
	 */
	private void checkArtProducedButtons() {
		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the
		// exception of the enabled status for the single large Insert, the
		// Insert Above, and the Insert Below
		// buttons which are processed in the second step below.

		// This is the first step in the process.
		int numArtifacts = lstLocalArtifactsProducedList.getNumberOfArtifacts();
		if (numArtifacts == 0) {
			// If the list is empty, only one insert button is visible as well
			// as the save and delete buttons.
			// The save and delete should be disabled. The semantics of the
			// enabled and disabled status of the
			// Insert buttons is more complex, so it is handled at the end of
			// this method.
			btnArtProducedNew.setVisible(true);
			btnArtProducedAbove.setDisable(true);
			btnArtProducedAbove.setVisible(false);
			btnArtProducedBelow.setDisable(true);
			btnArtProducedBelow.setVisible(false);
			btnArtProducedMvUp.setDisable(true);
			btnArtProducedMvDn.setDisable(true);
			btnArtProducedDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for
			// inserting below the selected item. So we start by making the
			// insert above and below buttons
			// visible and make the single insert button invisible and disabled.
			btnArtProducedAbove.setVisible(true);
			btnArtProducedBelow.setVisible(true);
			btnArtProducedNew.setVisible(false);
			btnArtProducedNew.setDisable(true);

			if (ndxJlstArtProducedSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled.
				// (It is always visible.)
				btnArtProducedDelete.setDisable(false);
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are
				// disabled (they are always visible) regardless of what is in
				// the input fields for the name and
				// the description.
				btnArtProducedDelete.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two or more items in the list and
			// one item is selected
			if (numArtifacts < 2 || ndxJlstArtProducedSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected neither of the two buttons
				// should be enabled
				btnArtProducedMvUp.setDisable(true);
				btnArtProducedMvDn.setDisable(true);

			} else if (ndxJlstArtProducedSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnArtProducedMvUp.setDisable(true);
				btnArtProducedMvDn.setDisable(false);

			} else if (ndxJlstArtProducedSelector == numArtifacts - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnArtProducedMvUp.setDisable(false);
				btnArtProducedMvDn.setDisable(true);

			} else {
				// Otherwise both options (up and down) are valid.
				btnArtProducedMvUp.setDisable(false);
				btnArtProducedMvDn.setDisable(false);
			}
		}

		// The following is the second step... Determine the enabled status of
		// the insert buttons.

		if (lstLocalArtifactsProducedList.getNumberOfArtifacts() > 0) {
			// The list is not empty
			if (ndxJlstArtProducedSelector == -1
					|| ndxCmbArtProducedSelector == -1) {
				// The list is not empty, but no item is selected or no ComboBox
				// item is selected, so it is not
				// possible to insert and artifact above or below.
				btnArtProducedAbove.setDisable(true);
				btnArtProducedBelow.setDisable(true);
			} else {
				// A non-empty list with an item selected and a selected item in
				// the ComboBox, so it is possible
				// to insert above or below
				btnArtProducedAbove.setDisable(false);
				btnArtProducedBelow.setDisable(false);
			}
		} else {
			// The list is empty, so the we need to see if the empty list insert
			// should be enabled
			if (ndxCmbArtProducedSelector == -1)
				// If the ComboBox does not have a selected item, then no insert
				// is possible
				btnArtProducedNew.setDisable(true);
			else
				// Empty list, but the ComboxBox has a selected item, so it
				// could be inserted into the list
				btnArtProducedNew.setDisable(false);

			// Since the list is empty, it is not possible to have a selected
			// element, so above or below is not possible
			btnArtProducedAbove.setDisable(true);
			btnArtProducedBelow.setDisable(true);
		}
	}



	/************************************* Methods for Steps *************************************/
	
	/*****
	 * This method is called any time the user switches from some other screen
	 * or pane to the Steps pane. The purpose is to set up the GUI elements
	 * based on the current state of the attributes upon which it depends,
	 * knowing that they may have changed from the last time (if ever) the pane
	 * has been displayed.
	 */
	public void performWakeUpStepsPane() {
		checkStepButtons();

		System.out.println("Wake up the steps tabbed pane");
		// Rebuild the selection lists as one of the underlying lists may have
		// changed.
		lstStepList.validateMembers(lstLifeCycleList);
		lstStepSelectListSource = lstStepList.buildSelectList();

		lglStepListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstStepSelectListSource);
		jlstSteps.setItems(items);
		lglStepListIsBeingUpdated = false;

		// Establish the PreCondition list using the lstLocalPreConditionList
		// attribute

		lglPreConditionListIsBeingUpdated = true;
		// Ignore list change events

		// Create the PreCondition list (a list with the sequence numbers)
		lstPreConditionSelectListSource = lstLocalPreConditionList
				.buildSelectList();

		ObservableList<String> itemsprecond = FXCollections
				.observableArrayList(lstPreConditionSelectListSource);

		jlstPreCondition.setItems(itemsprecond); // Set the list
		lglPreConditionListIsBeingUpdated = false; // Recognize list change
		// events
		ndxJlstPreConditionSelector = -1; // No item is selected
		jlstPreCondition.getSelectionModel().clearSelection(); // so clear the
		// selection

		// Create the ComboBox that add artifacts to the PreCondition list
		cmbPreConditionSelectList = lstConditionList
				.buildSubList(lstLocalPreConditionList);
		cmbModelPreCondition = cmbPreConditionSelectList.buildSelectList();
		ObservableList<String> precond = FXCollections
				.observableArrayList(cmbModelPreCondition);
		jcbPreCondition.setItems(precond);

		// Determine if the ComboBox has an item in it or not so we know whether
		// or not to
		// turn on the insert buttons. If there is one or more item in the
		// ComboBox, then
		// an insert is always valid, if there are none, it is not valid
		if (cmbModelPreCondition.length > 0) {
			jcbPreCondition.getSelectionModel().select(0);
			ndxCmbSelPreConditionSelector = 0;
		} else

			ndxCmbSelPreConditionSelector = -1; // Indicate there is nothing to
		// Establish the Artifact Used list using the lstLocalArtifactsUsedList
		// attribute

		lglPostConditionListIsBeingUpdated = true; // Ignore list change events

		// Create the PostCondition list (a list with the sequence numbers)
		lstPostConditionSelectListSource = lstLocalPostConditionList
				.buildSelectList();
		ObservableList<String> itemspostcond = FXCollections
				.observableArrayList(lstPostConditionSelectListSource);
		jlstPostCondition.setItems(itemspostcond); // Set the list

		ndxJlstPostConditionSelector = -1; // No item is selected
		jlstPostCondition.getSelectionModel().clearSelection(); // so clear the
		// selection

		// Create the ComboBox that add artifacts to the Artifacts Produced list
		cmbPostConditionSelectList = lstConditionList
				.buildSubList(lstLocalPostConditionList);
		cmbModelPostCondition = cmbPostConditionSelectList.buildSelectList();
		ObservableList<String> postcond = FXCollections
				.observableArrayList(cmbModelPostCondition);
		jcbPostCondition.setItems(postcond);

		// Same ComboBox process as above
		if (cmbModelPostCondition.length > 0) {
			jcbPostCondition.getSelectionModel().select(0);
			ndxCmbSelPostConditionSelector = 0;
		} else
			ndxCmbSelPostConditionSelector = -1; // Indicate there is nothing to
			lglPostConditionListIsBeingUpdated = false; // Ignore list change events
			lglStepTaskListIsBeingUpdated = true; // Ignore list change events

			// Create the Task list (a list with the sequence numbers)

		lstStepTaskSelectListSource = lstLocalStepTaskList.buildSelectList();
		ObservableList<String> itemsTasks = FXCollections
				.observableArrayList(lstStepTaskSelectListSource);

		jlstStepTask.setItems(itemsTasks);

		ndxJlstStepTaskSelector = -1; // No item is selected
		jlstStepTask.getSelectionModel().clearSelection(); // so clear the
		// selection

		// Create the ComboBox that add tasks to the Task list
		cmbStepTaskSelectList = lstTaskList.buildSubList(lstLocalStepTaskList);
		cmbModelStepTask = cmbStepTaskSelectList.buildSelectList();
		ObservableList<String> tasks = FXCollections
				.observableArrayList(cmbModelStepTask);
		jcbStepTask.setItems(tasks);

		// Same ComboBox process as above
		if (cmbModelStepTask.length > 0) {
			jcbStepTask.getSelectionModel().select(0);
			ndxCmbSelStepTaskSelector = 0;// Default to the first item
			// in
			// the list
		} else
			ndxCmbSelStepTaskSelector = -1; // Indicate there is nothing to
		// insert

		lglStepTaskListIsBeingUpdated = false; // Ignore list change events

		if (ndxJlstStepSelector == -1) {
			jlstSteps.getSelectionModel().clearSelection();
		} else
			jlstSteps.getSelectionModel().select(ndxJlstStepSelector);
		checkStepButtons();
	}

	/*****
	 * This method is called whenever a ListSelectEvent for the Steps List
	 * occurs. The method checks to see if this event duplicates another by
	 * seeing if the selection index from the event is the same as the local
	 * copy in ndxLstStepsSelector. If they differ, a new Step is selected and
	 * the various attributes associated with that Step are fetched and
	 * established.
	 * 
	 * @param event
	 *            - The event information we use to determine the new selected
	 *            list item.
	 */
	public void selectListStepsListItemSelected() {
		// Fetch the selected index from the JList.
		int ndx = jlstSteps.getSelectionModel().getSelectedIndex();

		// If the index matches the current local copies, there is no need to
		// proceed
		if (ndxJlstStepSelector == ndx)
			return;

		// If the index is different, then we must update the local copies
		ndxJlstStepSelector = ndx; // Remember the new index

		// This sets the local copy of the Name
		strSelectedStepName = lstStepList.getStep(ndxJlstStepSelector)
				.getName();
		strLocalStepName = strSelectedStepName; // The selected and local
		// versions start out
		fldStepName.setText(strLocalStepName); // being the same, but the user
		// might change that

		// This sets the local copy of the Description
		strSelectedStepDesc = lstStepList.getStep(ndxJlstStepSelector)
				.getDesc();
		strLocalStepDesc = strSelectedStepDesc; // Do the same thing for the
		// Description
		fldStepDesc.setText(strLocalStepDesc);

		// This sets the local copy of the PreCondition list
		lstSelectedPreConditionList = lstStepList.getStep(ndxJlstStepSelector)
				.getPreConditionList();
		lstSelectedPreConditionList.validateMembers(lstConditionList);
		lstLocalPreConditionList = lstStepList.getStep(ndxJlstStepSelector)
				.getPreConditionListCopy();
		lstPreConditionSelectListSource = lstLocalPreConditionList
				.buildSelectList();
		lglPreConditionListIsBeingUpdated = true; // Ignore update events
		ObservableList<String> preconditions = FXCollections
				.observableArrayList(lstPreConditionSelectListSource);
		jlstPreCondition.setItems(preconditions); // Establish the new list
		lglPreConditionListIsBeingUpdated = false; // Start accepting events
		// from this list
		// This app does not recall the last select item
		ndxJlstPreConditionSelector = -1; // This app does not recall the last
		// select item
		jlstPreCondition.getSelectionModel().clearSelection(); // so clear both
		// the index and
		// the GUI list

		// This set the local copy of the ComboBox the is used to add items to
		// the PreCondition list
		cmbPreConditionSelectList = lstConditionList
				.buildSubList(lstLocalPreConditionList);
		cmbModelPreCondition = cmbPreConditionSelectList.buildSelectList();
		ObservableList<String> precond = FXCollections
				.observableArrayList(cmbModelPreCondition);
		jcbPreCondition.setItems(precond); // Establish the actual ComboBox list

		if (cmbModelPreCondition.length > 0) {
			ndxCmbSelPreConditionSelector = 0; // Default to the first item in
												// the list
			jcbPreCondition.getSelectionModel().select(0);
		}
		else {
			ndxCmbSelPreConditionSelector = -1; // Indicate there is nothing to
			// insert
		}

		// This sets the local copy of the PostCondition list
		lstSelectedPostConditionList = lstStepList.getStep(ndxJlstStepSelector)
				.getPostConditionList();
		lstSelectedPostConditionList.validateMembers(lstConditionList);
		lstLocalPostConditionList = lstStepList.getStep(ndxJlstStepSelector)
				.getPostConditionListCopy();
		lglPostConditionListIsBeingUpdated = true;
		lstPostConditionSelectListSource = lstLocalPostConditionList
				.buildSelectList();
		lglPostConditionListIsBeingUpdated = false; // Start accepting events
		// from this list

		ObservableList<String> postconditions = FXCollections
				.observableArrayList(lstPostConditionSelectListSource);

		jlstPostCondition.setItems(postconditions); // Establish the new list

		ndxJlstPostConditionSelector = -1;
		jlstPostCondition.getSelectionModel().clearSelection();

		// so clear both the index and the GUI list

		// This set the local copy of the ComboBox the is used to add items to
		// the PostCondition list
		cmbPostConditionSelectList = lstConditionList
				.buildSubList(lstLocalPostConditionList);
		cmbModelPostCondition = cmbPostConditionSelectList.buildSelectList();
		ObservableList<String> postcond = FXCollections
				.observableArrayList(cmbModelPostCondition);
		jcbPostCondition.setItems(postcond); // Establish the actual ComboBox
		// list

		if (cmbModelPostCondition.length >= 0) {
			ndxCmbSelPostConditionSelector = 0; // Default to the first item in
			jcbPostCondition.getSelectionModel().select(0);
			// the list
		} else
			ndxCmbSelPostConditionSelector = -1; // Indicate there is nothing to
		// insert

		// This sets the local copy of the Task list
		lstSelectedStepTaskList = lstStepList.getStep(ndxJlstStepSelector)
				.getTaskList();
		lstSelectedStepTaskList.validateMembers(lstTaskList);
		lstLocalStepTaskList = lstStepList.getStep(ndxJlstStepSelector)
				.getTaskListCopy();
		lstStepTaskSelectListSource = lstLocalStepTaskList.buildSelectList();
		lglStepTaskListIsBeingUpdated = true;
		ObservableList<String> tasklist = FXCollections
				.observableArrayList(lstStepTaskSelectListSource);
		jlstStepTask.setItems(tasklist); // Establish the new list
		lglStepTaskListIsBeingUpdated = false; // Start accepting events from
		// this list
		ndxJlstStepTaskSelector = -1;
		jlstStepTask.getSelectionModel().clearSelection(); // so clear both the
		// index and the GUI
		// list

		// This set the local copy of the ComboBox the is used to add items to
		// the Task list
		cmbStepTaskSelectList = lstTaskList.buildSubList(lstLocalStepTaskList);
		cmbModelStepTask = cmbStepTaskSelectList.buildSelectList();
		ObservableList<String> steptasks = FXCollections
				.observableArrayList(cmbModelStepTask);
		jcbStepTask.setItems(steptasks); // Establish the actual ComboBox list

		if (cmbModelStepTask.length > 0) {
			jcbStepTask.getSelectionModel().select(0);
			ndxCmbSelStepTaskSelector = 0; // Default to the first item in the
			// list
		} else
			ndxCmbSelStepTaskSelector = -1; // Indicate there is nothing to
		// insert

		// These changes may cause changes in the state of the five buttons, so
		// we process them all
		// rather than try to reason about which buttons might be affected.
		checkStepButtons();
	}

	/**
	 * This method is called with the user clicks on the Insert button to insert
	 * a new Step into an empty Steps List.
	 */
	public void performStepNew() {
		// This method is only used for inserting the first item into an empty
		// JList. (Once
		// the JList has an item in it, the user must specify whether to insert
		// above or
		// below the selected list item.)
		lstStepList.addStep(strLocalStepName, strLocalStepDesc,
				lstLocalPreConditionList, lstLocalPostConditionList,
				lstLocalStepTaskList);

		// After an insert, the just inserted item is the now selected item.
		// Since the list was
		// empty just before this insert, there is now just one item in the list
		// and that item's
		// index is zero. (Remember this is Java and the first item in a Java
		// list has an index
		// of zero.)

		// These commands set the first item in the list to be selected
		ndxJlstStepSelector = 0; // Set the local copy of the selector

		// Update the GUI to reflect the just made change
		commonUpdateAfterAStepInsert();
	}

	/**
	 * This method is called with the user clicks on the Insert button to insert
	 * a new Step above the current selected task in the Steps List.
	 */
	public void performStepNewAbove() {
		// Create a new artifact and insert it above the current selected
		// artifact. Using the
		// lstArtifactsSelector, as is, is what causes the above to occur.
		// Otherwise this is
		// exactly the same as the performArtifactsNewBelow method
		lstStepList.addStep(ndxJlstStepSelector, strLocalStepName,
				strLocalStepDesc, lstLocalPreConditionList,
				lstLocalPostConditionList, lstLocalStepTaskList);

		// We want the just inserted item to be the currently selected item, so
		// we have to update
		// the selector to be this new item. Since we inserted above, the index
		// of the selected
		// item will be the same.

		// Update the GUI to reflect the just made change
		commonUpdateAfterAStepInsert();
	}

	/**
	 * This method is called with the user clicks on the Insert button, in the
	 * Steps group of buttons, to insert a new Step below the current selected
	 * task in the Steps List
	 */
	public void performStepNewBelow() {
		// Create a new artifact and insert it below the current selected
		// artifact. Using the
		// ndxLstStepsSelector+1 causes the insert below to occur. Otherwise
		// this is exactly the
		// same as the performStepsNewAbove method
		lstStepList.addStep(ndxJlstStepSelector + 1, strLocalStepName,
				strLocalStepDesc, lstLocalPreConditionList,
				lstLocalPostConditionList, lstLocalStepTaskList);

		// We want the just inserted item to be the currently selected item, so
		// we have to update
		// the selector to be this new item. Since we inserted below, the index
		// of the selected
		// item will be one larger than it was.
		ndxJlstStepSelector++;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAStepInsert();
	}

	/**
	 * This method is called with the user clicks on the Save button in the
	 * Steps group of buttons. The method saves changes made in the current
	 * selected Step item.
	 */
	public void performStepSave() {
		// We know that this method can only be called when the button is
		// enabled and that means
		// that a Step was selected and the input fields do not match what has
		// been stored.
		// Therefore, we need to update the stored list to match this updated
		// artifact. We
		// blindly update both the name and the description, even if only one
		// has been changed.
		lstStepList.replaceStep(ndxJlstStepSelector, strLocalStepName,
				strLocalStepDesc, lstLocalPreConditionList,
				lstLocalPostConditionList, lstLocalStepTaskList);

		// Update the GUI to reflect the just made change
		commonUpdateAfterAStepInsert();
	}

	private void commonUpdateAfterAStepInsert() {
		// The list has changed so we need to build a new list.
		lstStepSelectListSource = lstStepList.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglStepListIsBeingUpdated = true;
		ObservableList<String> steps = FXCollections
				.observableArrayList(lstStepSelectListSource);
		jlstSteps.setItems(steps);
		lglStepListIsBeingUpdated = false;

		// To ensure the just updated item remains selected, we must issue this
		// command
		jlstSteps.getSelectionModel().select(ndxJlstStepSelector);

		// This sets the local copy of the Name
		strSelectedStepName = lstStepList.getStep(ndxJlstStepSelector)
				.getName();
		strLocalStepName = strSelectedStepName;

		// This sets the local copy of the Description
		strSelectedStepDesc = lstStepList.getStep(ndxJlstStepSelector)
				.getDesc();
		strLocalStepDesc = strSelectedStepDesc;

		// This sets the selected copy of the two condition lists and the task
		// List
		lstSelectedPreConditionList = new ConditionListController(lstStepList
				.getStep(ndxJlstStepSelector).getPreConditionList());
		lstSelectedPostConditionList = new ConditionListController(lstStepList
				.getStep(ndxJlstStepSelector).getPostConditionList());
		lstSelectedStepTaskList = new TaskListController(lstStepList.getStep(
				ndxJlstStepSelector).getTaskList());

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkStepButtons();
	}

	/**
	 * This method is called with the user clicks on the Move Up button in the
	 * Steps group of buttons.
	 */
	public void performStepMvUp() {
		// The following statement actually moves the item in the list
		lstStepList.moveStepUp(ndxJlstStepSelector);

		// Now we need to update the GUI to match what we have done.
		ndxJlstStepSelector--; // Keep the same item selected

		// Update the GUI to reflect the just made change
		commonUpdateAfterAStepMove();
	}

	/**
	 * This method is called with the user clicks on the Move Dn button in the
	 * Steps group of buttons.
	 */
	public void performStepMvDn() {
		// The following statement actually moves the item in the list
		lstStepList.moveStepDn(ndxJlstStepSelector);

		// Now we need to update the GUI to match what we have done.
		ndxJlstStepSelector++; // Keep the same item selected

		// Update the GUI to reflect the just made change
		commonUpdateAfterAStepMove();
	}

	public void commonUpdateAfterAStepMove() {
		// The list has changed so we need to build a new list.
		lstStepSelectListSource = lstStepList.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglStepListIsBeingUpdated = true;
		ObservableList<String> steps = FXCollections
				.observableArrayList(lstStepSelectListSource);
		jlstSteps.setItems(steps);
		lglStepListIsBeingUpdated = false;

		// Now issue the command to make the original item selected again
		jlstSteps.getSelectionModel().select(ndxJlstStepSelector);

		// This sets the selected copy of the two condition lists and the task
		// List
		lstSelectedPreConditionList = new ConditionListController(lstStepList
				.getStep(ndxJlstStepSelector).getPreConditionList());
		lstSelectedPostConditionList = new ConditionListController(lstStepList
				.getStep(ndxJlstStepSelector).getPostConditionList());
		lstSelectedStepTaskList = new TaskListController(lstStepList.getStep(
				ndxJlstStepSelector).getTaskList());

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkStepButtons();
	}

	/**
	 * This method is called with the user clicks on the Delete button in the
	 * Steps group of buttons. The method removes the current selected Step
	 * item.
	 */
	public void performStepDelete() {
		// The following statement actually deletes the item from the list
		// These changes may cause changes in the state of the five buttons,
		// so we process them all rather
		// than try to reason about which buttons might be affected.

		// We know that it is only possible to get here when an defect in the
		// list has been selected, or the button would have been disabled. So no
		// checking is needed.
		lstStepList.deleteStep(ndxJlstStepSelector);

		// Now that the Step has been deleted, we need to clean up the UI
		// to match this state.

		// After a delete, no item is selected, so that is what these statements
		// do
		ndxJlstStepSelector = -1;
		jlstSteps.getSelectionModel().clearSelection();

		// The list has changed so we need to build a new list.
		lstStepSelectListSource = lstStepList.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglStepListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstStepSelectListSource);
		jlstSteps.setItems(items);
		lglStepListIsBeingUpdated = false;

		// We need to clear the local copies as there is no current selected
		// item
		strSelectedStepName = "";
		strSelectedStepDesc = "";

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkStepButtons();

	}

	public void performStepClear() {
		// Clear the local copy of the Name
		strLocalStepName = "";
		fldStepName.setText(strLocalStepName);

		// Clear the local copy of the Description
		strLocalStepDesc = "";
		fldStepDesc.setText(strLocalStepDesc);
		checkStepButtons();
	}

	/**
	 * This method is called whenever the state of the Steps GUI form is
	 * changed. This method determines which of the buttons associated with the
	 * Steps List should be visible and/or enabled based on the state of the
	 * attributes associated with this form.
	 */
	public void checkStepButtons() {
		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the
		// exception of the enabled status for the single large Insert, the
		// Insert Above, and the Insert Below
		// buttons which are processed in the second step below.

		// This is the first step in the process.
		int numSteps = lstStepList.getNumberOfSteps();
		if (numSteps == 0) {
			// If the list is empty, only one insert button is visible as well
			// as the save and delete buttons.
			// The save and delete should be disabled. The semantics of the
			// enabled and disabled status of the
			// Insert buttons is more complex, so it is handled at the end of
			// this method.
			btnStepsNew.setVisible(true);
			btnStepsNewAbove.setDisable(true);
			btnStepsNewAbove.setVisible(false);
			btnStepsNewBelow.setDisable(true);
			btnStepsNewBelow.setVisible(false);
			btnStepsMvUp.setDisable(true);
			btnStepsMvDn.setDisable(true);
			btnStepsDelete.setDisable(true);
			btnStepsSave.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one
			// for inserting below the selected item. So we start by making the
			// insert above and below buttons
			// visible and make the single insert button invisible and disabled.
			btnStepsNewAbove.setVisible(true);
			btnStepsNewBelow.setVisible(true);
			btnStepsNew.setVisible(false);
			btnStepsNew.setDisable(true);

			if (ndxJlstStepSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled.
				// (It is always visible.)
				btnStepsDelete.setDisable(false);
				if (
				// the Step Names match
				strSelectedStepName.equals(strLocalStepName)

				// the Descriptions match
						&& strSelectedStepDesc.equals(strLocalStepDesc)

						// The PreConditions match
						&& ((!lstLocalPreConditionList.isEmpty()
								&& !lstSelectedPreConditionList.isEmpty() && lstLocalPreConditionList
									.isTheSameAs(lstSelectedPreConditionList)) || (lstLocalPreConditionList
								.isEmpty() && lstSelectedPreConditionList
								.isEmpty()))

						// The PostConditions match
						&& ((!lstLocalPostConditionList.isEmpty()
								&& !lstSelectedPostConditionList.isEmpty() && lstLocalPostConditionList
									.isTheSameAs(lstSelectedPostConditionList)) || (lstLocalPostConditionList
								.isEmpty() && lstSelectedPostConditionList
								.isEmpty()))

						// The Step Tasks match
						&& ((!lstLocalStepTaskList.isEmpty()
								&& !lstSelectedStepTaskList.isEmpty() && lstLocalStepTaskList
									.isTheSameAs(lstSelectedStepTaskList)) || (lstLocalStepTaskList
								.isEmpty() && lstSelectedStepTaskList.isEmpty()))) {
					// Getting here says that the selected and the user input
					// copies match, so save is not enabled
					btnStepsSave.setDisable(true);
				} else {
					// Getting here says that the selected and the user input
					// copies have at least one difference
					// and so saving should be enabled. We only allow a save,
					// however, when the Name field is not
					// empty
					if (strLocalStepName.length() > 0)
						btnStepsSave.setDisable(false);
					else
						btnStepsSave.setDisable(true);
				}
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are
				// disabled (they are always visible) regardless of what is in
				// the input fields for the name, the
				// description, or the condition of the used and produced lists
				btnStepsDelete.setDisable(true);
				btnStepsSave.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two or more items in the list and
			// one item is selected
			if (numSteps < 2 || ndxJlstStepSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected neither of the two buttons
				// should be enabled
				btnStepsMvUp.setDisable(true);
				btnStepsMvDn.setDisable(true);

			} else if (ndxJlstStepSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnStepsMvUp.setDisable(true);
				btnStepsMvDn.setDisable(false);

			} else if (ndxJlstStepSelector == numSteps - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnStepsMvUp.setDisable(false);
				btnStepsMvDn.setDisable(true);

			} else {
				// Otherwise both options are valid.
				btnStepsMvUp.setDisable(false);
				btnStepsMvDn.setDisable(false);
			}
		}

		// The following is the second step.

		// The remaining code is about the enabled or disabled status of the
		// insert buttons. Visibility has been
		// handled above as has the enabled status for Delete and Save. This
		// code deals with the visibility of the
		// three insert buttons.
		if (lstStepList.getNumberOfSteps() == 0
				&& strLocalStepName.length() == 0) {
			// The list is empty and the user input name field is empty, so the
			// single Insert button is visible,
			// but it is disabled. The two insert buttons were disabled above
			// and their visibility was handled above.
			btnStepsNew.setDisable(true);
		} else if (lstStepList.getNumberOfSteps() == 0
				&& strLocalStepName.length() > 0) {
			// The list is empty, but the user input name field is not, so the
			// single Insert button is visible and
			// enabled. The two Insert buttons are not visible (from above) and
			// are disabled.
			btnStepsNew.setDisable(false);
		} else if (lstStepList.getNumberOfSteps() > 0
				&& strLocalStepName.length() == 0) {
			// The list is not empty, but the user input name field is empty, so
			// the two Insert buttons are visible
			// and enabled. The single Insert button is not visible and disabled
			// from above).
			btnStepsNewAbove.setDisable(true);
			btnStepsNewBelow.setDisable(true);
		} else if (lstStepList.getNumberOfSteps() > 0
				&& strLocalStepName.length() > 0) {
			// The list is not empty and the user input name field is not empty,
			// so the status of the two Insert
			// buttons in terms of enabled or not is determined by where or not
			// an item in the list has been selected.
			// We do know that the single insert was made not visible and
			// disabled above, so no need to do it here.
			if (ndxJlstStepSelector == -1) {
				// No item is selected, so it is not possible to insert above or
				// below it.
				btnStepsNewAbove.setDisable(true);
				btnStepsNewBelow.setDisable(true);
			} else {
				// An item is selected, so it is possible to insert above or
				// below it
				btnStepsNewAbove.setDisable(false);
				btnStepsNewBelow.setDisable(false);
			}
		}
		checkPreConditionButtons();
		checkStepTaskButtons();
		checkPostConditionButtons();
	}

	/**
	 * This method is called when the Insert button is pressed to insert the
	 * first Artifact Used into a task's Artifact Used list. This code follows a
	 * pattern that is nearly the same as for performPreConditionAbove and
	 * performPreConditionBelow. Any changes to this or the other two methods
	 * and you should think about all three.
	 */
	public void performPreConditionNew() {
		// This method is only used for inserting the first item into an empty
		// JList. (Once the JList has an
		// item in it, the user must specify whether to insert above or below
		// the selected list item.)

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbSelPreConditionSelector = jcbPreCondition.getSelectionModel()
				.getSelectedIndex(); // Get the index of the artifact

		Condition toBeInserted = cmbPreConditionSelectList
				.extractCondition(ndxCmbSelPreConditionSelector);

		lstLocalPreConditionList.addCondition(toBeInserted); // Insert the
		// artifact into
		// the list

		// Since there is only one item in the list now, we must highlight the
		// first one as the selected item
		ndxJlstPreConditionSelector = 0;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAPreConditionInsert();
	}

	/**
	 * This method is called when the Insert button is pressed to insert an
	 * Artifact Used into a task's existing Artifact Used list above the current
	 * selected item. This code follows a pattern that is nearly the same as for
	 * performPreConditionNew and performPreConditionBelow. Any changes to this
	 * or the other two methods and you should think about all three.
	 */
	public void performPreConditionAbove() {
		// This method is only used for inserting above a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbSelPreConditionSelector = jcbPreCondition.getSelectionModel()
				.getSelectedIndex();
		Condition toBeInserted = cmbPreConditionSelectList
				.extractCondition(ndxCmbSelPreConditionSelector);
		lstLocalPreConditionList.addCondition(ndxJlstPreConditionSelector,
				toBeInserted);

		// We have inserted above the previous current item, so that item will
		// be pushed down one and
		// the just inserted item will take its old place. Therefore, the index
		// of the current item
		// stays the same.

		// Update the GUI to reflect the just made change
		commonUpdateAfterAPreConditionInsert();
	}

	/*****
	 * This method is called when the Insert button is pressed to insert an
	 * Artifact Used into a task's existing Artifact Used list below the current
	 * selected item. This code follows a pattern that is nearly the same as for
	 * performPreConditionNew and performPreConditionAbove. Any changes to this
	 * or the other two methods and you should think about all three.
	 */
	public void performPreConditionBelow() {
		// This method is only used for inserting below a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbSelPreConditionSelector = jcbPreCondition.getSelectionModel()
				.getSelectedIndex();
		Condition toBeInserted = cmbPreConditionSelectList
				.extractCondition(ndxCmbSelPreConditionSelector);
		lstLocalPreConditionList.addCondition(ndxJlstPreConditionSelector + 1,
				toBeInserted);

		// We have inserted below the previous current item, so this just
		// inserted item will have an index that is
		// one larger that the previous selected item. Therefore, the index of
		// the current item is incremented.
		// We changed the list, so we must tell the GUI component which item is
		// selected now.
		ndxJlstPreConditionSelector++;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAPreConditionInsert();
	}

	public void commonUpdateAfterAPreConditionInsert() {
		// The Artifact used list has changed, so we need to rebuild the list
		// that is displayed
		lstPreConditionSelectListSource = lstLocalPreConditionList
				.buildSelectList();
		// Since we are updating the list, we need to ignore the list change
		// events
		lglPreConditionListIsBeingUpdated = true;
		// Update the GUI component for the Artifact Used select list to display
		// the new list
		ObservableList<String> preconditions = FXCollections
				.observableArrayList(lstPreConditionSelectListSource);
		jlstPreCondition.setItems(preconditions); // Establish the new list
		// We changed the list, so we must tell the GUI component which item is
		// selected now.
		jlstPreCondition.getSelectionModel()
				.select(ndxJlstPreConditionSelector);
		// The ComboBox list has also changed (one fewer item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Artifacts Used list.
		cmbPreConditionSelectList = lstConditionList
				.buildSubList(lstLocalPreConditionList);
		cmbModelPreCondition = cmbPreConditionSelectList.buildSelectList();
		ObservableList<String> preconds = FXCollections
				.observableArrayList(cmbModelPreCondition);
		jcbPreCondition.setItems(preconds);
		// Establish the updated ComboBox using the model that has just been
		// created
		// Since the size of the ComboBox is now smaller, we need to check the
		// index of the current
		// selected item to be sure that it is still in a valid range, and fix
		// it if not. (Remember,
		// it could now be empty!)
		if (ndxCmbSelPreConditionSelector >= cmbPreConditionSelectList
				.getNumberOfConditions())
			ndxCmbSelPreConditionSelector = cmbPreConditionSelectList
					.getNumberOfConditions() - 1;

		// Establish the current artifact in the ComboBox
		jcbPreCondition.getSelectionModel().select(
				ndxCmbSelPreConditionSelector);

		// We will now process changes to the list
		lglPreConditionListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkStepButtons();
	}

	/*****
	 * This method is called when the Delete button is pressed to remove the
	 * current selected Artifact Used from a task's existing Artifact Used list.
	 */
	public void performPreConditionDelete() {
		// This method is only used for removing a selected item from a
		// non-empty JList.

		// This statements actually does the delete. The rest are about
		// adjusting the GUI state to match.
		lstLocalPreConditionList.deleteCondition(ndxJlstPreConditionSelector);

		// Now that the artifact has been deleted, we need to clean up the UI to
		// match this state.

		// The list has changed so we need to build a new list.
		lstPreConditionSelectListSource = lstLocalPreConditionList
				.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglPreConditionListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Used select list to display
		// the new list

		ObservableList<String> preconditons = FXCollections
				.observableArrayList(lstPreConditionSelectListSource);
		jlstPreCondition.setItems(preconditons);

		// We have delete the current item from the list, so no item in the list
		// will be selected now.
		// Therefore, we clear the index and the selection from the GUI
		// component
		ndxJlstPreConditionSelector = -1;
		jlstPreCondition.getSelectionModel().clearSelection();
		// The ComboBox list has also changed (one more item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Artifacts Used list.
		cmbPreConditionSelectList = lstConditionList
				.buildSubList(lstLocalPreConditionList);

		// Create the ComboBox Model, complete with sequence numbers
		cmbModelPreCondition = cmbPreConditionSelectList.buildSelectList();
		ObservableList<String> preconditions = FXCollections
				.observableArrayList(cmbModelPreCondition);

		// Establish the updated ComboBox using the model that has just been
		// created
		jcbPreCondition.setItems(preconditions);

		// Since the size of the ComboBox is now larger, we know that there is
		// at least one item in it,
		// so we will default the ComboBox to the first item in the list.

		// Set the index to be the first item in the list
		ndxCmbSelPreConditionSelector = 0;

		// Set the GUI component so it has selected the first item in the list
		jcbPreCondition.getSelectionModel().select(
				ndxCmbSelPreConditionSelector);

		// We will now process changes to the list
		lglPreConditionListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkStepButtons();
	}

	/*****
	 * This method is called when the Move Up button is pressed to move the
	 * current selected Artifact Used from its current location in the list to
	 * the next position toward the top.
	 */
	public void performPreConditionMvUp() {
		// This method is only used for moving a selected item up a non-empty
		// JList when there is at least
		// one element above it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalPreConditionList.moveConditionUp(ndxJlstPreConditionSelector);

		// We moved it up, so to keep the same item selected, the index must be
		// reduced by one
		ndxJlstPreConditionSelector--;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAPreConditionMove();
	}

	/*****
	 * This method is called when the Move Dn button is pressed to move the
	 * current selected Artifact Used from its current location in the list to
	 * the next position toward the bottom.
	 */
	public void performPreConditionMvDn() {
		// This method is only used for moving a selected item down a non-empty
		// JList when there is at least
		// one element below it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalPreConditionList.moveConditionDn(ndxJlstPreConditionSelector);

		// We moved it down, so to keep the same item selected, the index must
		// be increased by one
		ndxJlstPreConditionSelector++;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAPreConditionMove();
	}

	public void commonUpdateAfterAPreConditionMove() {
		// The list has changed so we need to build a new list.
		lstPreConditionSelectListSource = lstLocalPreConditionList
				.buildSelectList();
		// Since we are updating the list, we need to ignore the list change
		// events
		lglPreConditionListIsBeingUpdated = true;
		ObservableList<String> preconditions = FXCollections
				.observableArrayList(lstPreConditionSelectListSource);
		// Update the GUI component for the Artifact Used select list to display
		// the new list
		jlstPreCondition.setItems(preconditions);
		// Given the new list, this command will once again select the item that
		// was moved
		jlstPreCondition.getSelectionModel()
				.select(ndxJlstPreConditionSelector);
		// We will now process changes to the list
		lglPreConditionListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkStepButtons();
	}

	/*****
	 * This method is called when the Insert button is pressed to insert the
	 * first Artifact Produced into a task's Artifact Produced list. This code
	 * follows a pattern that is nearly the same as for
	 * performPostConditionAbove and performPostConditionBelow. Any changes to
	 * this or the other two methods and you should think about all three.
	 */
	public void performPostConditionNew() {
		// This method is only used for inserting the first item into an empty
		// JList. (Once the JList has an
		// item in it, the user must specify whether to insert above or below
		// the selected list item.)

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbSelPostConditionSelector = jcbPostCondition.getSelectionModel()
				.getSelectedIndex(); // Get the index of the artifact
		Condition toBeInserted = cmbPostConditionSelectList
				.extractCondition(ndxCmbSelPostConditionSelector);
		lstLocalPostConditionList.addCondition(toBeInserted); // Insert the
		// artifact into
		// the list

		// Since there is only one item in the list now, we must highlight the
		// first one as the selected item
		ndxJlstPostConditionSelector = 0;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAPostContisionInsert();
	}

	/*****
	 * This method is called when the Insert button is pressed to insert an
	 * Artifact Produced into a task's existing Artifact Produced list above the
	 * current selected item. This code follows a pattern that is nearly the
	 * same as for performPostConditionNew and performPostConditionBelow. Any
	 * changes to this or the other two methods and you should think about all
	 * three.
	 */
	public void performPostConditionAbove() {
		// This method is only used for inserting above a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbSelPostConditionSelector = jcbPostCondition.getSelectionModel()
				.getSelectedIndex();
		Condition toBeInserted = cmbPostConditionSelectList
				.extractCondition(ndxCmbSelPostConditionSelector);
		lstLocalPostConditionList.addCondition(ndxJlstPostConditionSelector,
				toBeInserted);

		// We have inserted above the previous current item, so that item will
		// be pushed down one and
		// the just inserted item will take its old place. Therefore, the index
		// of the current item
		// stays the same.

		// Update the GUI to reflect the just made change
		commonUpdateAfterAPostContisionInsert();
	}

	/*****
	 * This method is called when the Insert button is pressed to insert an
	 * Artifact Produced into a task's existing Artifact Produced list below the
	 * current selected item. This code follows a pattern that is nearly the
	 * same as for performPreConditionNew and performPreConditionAbove. Any
	 * changes to this or the other two methods and you should think about all
	 * three.
	 */
	public void performPostConditionBelow() {
		// This method is only used for inserting below a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbSelPostConditionSelector = jcbPostCondition.getSelectionModel()
				.getSelectedIndex();
		Condition toBeInserted = cmbPostConditionSelectList
				.extractCondition(ndxCmbSelPostConditionSelector);
		lstLocalPostConditionList.addCondition(
				ndxJlstPostConditionSelector + 1, toBeInserted);

		// We have inserted below the previous current item, so this just
		// inserted item will have an index that is
		// one larger that the previous selected item. Therefore, the index of
		// the current item is incremented.
		// We changed the list, so we must tell the GUI component which item is
		// selected now.
		ndxJlstPostConditionSelector++;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAPostContisionInsert();
	}

	public void commonUpdateAfterAPostContisionInsert() {
		// The Artifact Produced list has changed, so we need to rebuild the
		// list that is displayed
		lstPostConditionSelectListSource = lstLocalPostConditionList
				.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglPostConditionListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Produced select list to
		// display the new list

		ObservableList<String> postconditions = FXCollections
				.observableArrayList(lstPostConditionSelectListSource);
		// Establish the new list
		jlstPostCondition.setItems(postconditions);

		// We will now process changes to the list
		lglPostConditionListIsBeingUpdated = false;

		// We changed the list, so we must tell the GUI component which item is
		// selected now.
		jlstPostCondition.getSelectionModel().select(
				ndxJlstPostConditionSelector);

		// The ComboBox list has also changed (one fewer item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Artifacts Produced list.
		cmbPostConditionSelectList = lstConditionList
				.buildSubList(lstLocalPostConditionList);

		// Create the ComboBox Model, complete with sequence numbers

		cmbModelPostCondition = cmbPostConditionSelectList.buildSelectList();
		ObservableList<String> postconds = FXCollections
				.observableArrayList(cmbModelPostCondition);

		jcbPostCondition.setItems(postconds); // Establish the actual ComboBox
		// list

		// Since the size of the ComboBox is now smaller, we need to check the
		// index of the current
		// selected item to be sure that it is still in a valid range, and fix
		// it if not. (Remember,
		// it could now be empty!)
		if (ndxCmbSelPostConditionSelector >= cmbPostConditionSelectList
				.getNumberOfConditions())
			ndxCmbSelPostConditionSelector = cmbPostConditionSelectList
					.getNumberOfConditions() - 1;

		// Establish the current artifact in the ComboBox
		jcbPostCondition.getSelectionModel().select(
				ndxCmbSelPostConditionSelector);

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkStepButtons();
	}

	/*****
	 * This method is called when the Delete button is pressed to remove the
	 * current selected Artifact Produced from a task's existing Artifact
	 * Produced list.
	 */
	public void performPostConditionDelete() {
		// This method is only used for removing a selected item from a
		// non-empty JList.

		// This statements actually does the delete. The rest are about
		// adjusting the GUI state to match.
		lstLocalPostConditionList.deleteCondition(ndxJlstPostConditionSelector);

		// Now that the artifact has been deleted, we need to clean up the UI to
		// match this state.

		// The list has changed so we need to build a new list.
		lstPostConditionSelectListSource = lstLocalPostConditionList
				.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglPostConditionListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Used select list to display
		// the new list

		ObservableList<String> postconditions = FXCollections
				.observableArrayList(lstPostConditionSelectListSource);
		jlstPostCondition.setItems(postconditions); // Establish the new list
		ndxJlstPostConditionSelector = -1;
		jlstPostCondition.getSelectionModel().clearSelection(); // so clear both
		// the index and
		// the GUI list

		// We have delete the current item from the list, so no item in the list
		// will be selected now.
		// Therefore, we clear the index and the selection from the GUI
		// component

		// The ComboBox list has also changed (one more item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Artifacts Used list.
		cmbPostConditionSelectList = lstConditionList
				.buildSubList(lstLocalPostConditionList);
		cmbModelPostCondition = cmbPostConditionSelectList.buildSelectList();
		ObservableList<String> postconditions1 = FXCollections
				.observableArrayList(cmbModelPostCondition);
		// Create the ComboBox Model, complete with sequence numbers
		jcbPostCondition.setItems(postconditions1);

		// Since the size of the ComboBox is now larger, we know that there is
		// at least one item in it,
		// so we will default the ComboBox to the first item in the list.

		// Set the index to be the first item in the list
		ndxCmbSelPostConditionSelector = 0;

		// Set the GUI component so it has selected the first item in the list
		jcbPostCondition.getSelectionModel().select(
				ndxCmbSelPostConditionSelector);

		// We will now process changes to the list
		lglPostConditionListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkStepButtons();
	}

	/*****
	 * This method is called when the Move Up button is pressed to move the
	 * current selected Artifact Produced from its current location in the list
	 * to the next position toward the top.
	 */
	public void performPostConditionMvUp() {
		// This method is only used for moving a selected item up a non-empty
		// JList when there is at least
		// one element above it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalPostConditionList.moveConditionUp(ndxJlstPostConditionSelector);

		// We moved it up, so to keep the same item selected, the index must be
		// reduced by one
		ndxJlstPostConditionSelector--;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAPostConditionMove();
	}

	/*****
	 * This method is called when the Move Dn button is pressed to move the
	 * current selected Artifact Produced from its current location in the list
	 * to the next position toward the bottom.
	 */
	public void performPostConditionMvDn() {
		// This method is only used for moving a selected item down a non-empty
		// JList when there is at least
		// one element below it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalPostConditionList.moveConditionDn(ndxJlstPostConditionSelector);

		// We moved it down, so to keep the same item selected, the index must
		// be increased by one
		ndxJlstPostConditionSelector++;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAPostConditionMove();
	}

	public void commonUpdateAfterAPostConditionMove() {
		// The list has changed so we need to build a new list.
		lstPostConditionSelectListSource = lstLocalPostConditionList
				.buildSelectList();
		// Since we are updating the list, we need to ignore the list change
		// events
		lglPostConditionListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Produced select list to
		// display the new list

		ObservableList<String> postconditions = FXCollections
				.observableArrayList(lstPostConditionSelectListSource);
		jlstPostCondition.setItems(postconditions); // Establish the new list

		// Given the new list, this command will once again select the item that
		// was moved
		jlstPostCondition.getSelectionModel().select(
				ndxJlstPostConditionSelector);

		// We will now process changes to the list
		lglPostConditionListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Produced list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkStepButtons();
	}

	/*****
	 * This method is used when the user click on one of the items in the
	 * Artifacts Used list. This change in the selection does not change
	 * anything other attribute, so the only impact other than the new selection
	 * is the possible change in the associated action buttons (are they enabled
	 * or not).
	 * 
	 * @param event
	 */
	public void comboBoxPreConditionListItemSelected() {
		// No actions required when this ComboBox is changed

	}

	public void comboBoxPostConditionListItemSelected() {
		// No actions required when this ComboBox is changed
		//

	}

	public void jltPreConditionListItemSelected() {
		// Fetch the selected index from the JList.
		int ndx = jlstPreCondition.getSelectionModel().getSelectedIndex();
		// If the index matches the current local copy, there is no need to
		// proceed
		if (ndxJlstPreConditionSelector == ndx)
			return;
		// If the index is different, then we must update the local copy,
		// remember the new index
		ndxJlstPreConditionSelector = ndx;

		// This change may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkStepButtons();
	}

	/*****
	 * This method is used when the user click on one of the items in the
	 * Artifacts Produced list. This change in the selection does not change
	 * anything other attribute, so the only impact other than the new selection
	 * is the possible change in the associated action buttons (are they enabled
	 * or not).
	 * 
	 * @param event
	 */
	public void selectListPostConditionListItemSelected() {
		// Fetch the selected index from the JList.
		int ndx = jlstPostCondition.getSelectionModel().getSelectedIndex();

		// If the index matches the current local copies, there is no need to
		// proceed
		if (ndxJlstPostConditionSelector == ndx)
			return;

		// If the index is different, then we must update the local copies,
		// remember the new index
		ndxJlstPostConditionSelector = ndx;

		// This change may cause changes in the state of the five buttons for
		// the Artifacts Produced list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkStepButtons();
	}

	/*****
	 * This method is called any time the user changes something that *may*
	 * cause a change in state that could influence which of the related buttons
	 * are visible or enabled. Rather than deal with each change separately,
	 * this method processes the state and determines the proper condition
	 * (visible and enabled) for *all* of the buttons.
	 */
	public void checkPreConditionButtons() {
		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the
		// exception of the enabled status for the single large Insert, the
		// Insert Above, and the Insert Below
		// buttons which are processed in the second step below.

		// This is the first step in the process.
		int numConditions = lstLocalPreConditionList.getNumberOfConditions();
		if (numConditions == 0) {
			// If the list is empty, only one insert button is visible as well
			// as the save and delete buttons.
			// The save and delete should be disabled. The semantics of the
			// enabled and disabled status of the
			// Insert buttons is more complex, so it is handled at the end of
			// this method.
			btnPreConditionNew.setVisible(true);
			btnPreConditionAbove.setDisable(true);
			btnPreConditionAbove.setVisible(false);
			btnPreConditionBelow.setDisable(true);
			btnPreConditionBelow.setVisible(false);
			btnPreConditionMvUp.setDisable(true);
			btnPreConditionMvDn.setDisable(true);
			btnPreConditionDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for
			// inserting below the selected item. So we start by making the
			// insert above and below buttons
			// visible and make the single insert button invisible and disabled.
			btnPreConditionAbove.setVisible(true);
			btnPreConditionBelow.setVisible(true);
			btnPreConditionNew.setVisible(false);
			btnPreConditionNew.setDisable(true);

			if (ndxJlstPreConditionSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled.
				// (It is always visible.)
				btnPreConditionDelete.setDisable(false);
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are
				// disabled (they are always visible) regardless of what is in
				// the input fields for the name and
				// the description.
				btnPreConditionDelete.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two or more items in the list and
			// one item is selected
			if (numConditions < 2 || ndxJlstPreConditionSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected neither of the two buttons
				// should be enabled
				btnPreConditionMvUp.setDisable(true);
				btnPreConditionMvDn.setDisable(true);

			} else if (ndxJlstPreConditionSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnPreConditionMvUp.setDisable(true);
				btnPreConditionMvDn.setDisable(false);

			} else if (ndxJlstPreConditionSelector == numConditions - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnPreConditionMvUp.setDisable(false);
				btnPreConditionMvDn.setDisable(true);

			} else {
				// Otherwise both options (up and down) are valid.
				btnPreConditionMvUp.setDisable(false);
				btnPreConditionMvDn.setDisable(false);
			}
		}

		// The following is the second step... Determine the enabled status of
		// the insert buttons.

		if (lstLocalPreConditionList.getNumberOfConditions() > 0) {
			// The list is not empty
			if (ndxJlstPreConditionSelector == -1
					|| ndxCmbSelPreConditionSelector == -1) {
				// The list is not empty, but no item is selected or no ComboBox
				// item is selected, so it is not
				// possible to insert and artifact above or below.
				btnPreConditionAbove.setDisable(true);
				btnPreConditionBelow.setDisable(true);
			} else {
				// A non-empty list with an item selected and a selected item in
				// the ComboBox, so it is possible
				// to insert above or below
				btnPreConditionAbove.setDisable(false);
				btnPreConditionBelow.setDisable(false);
			}
		} else {
			// The list is empty, so the we need to see if the empty list insert
			// should be enabled
			if (ndxCmbSelPreConditionSelector == -1)
				// If the ComboBox does not have a selected item, then no insert
				// is possible
				btnPreConditionNew.setDisable(true);
			else
				// Empty list, but the ComboxBox has a selected item, so it
				// could be inserted into the list
				btnPreConditionNew.setDisable(false);

			// Since the list is empty, it is not possible to have a selected
			// element, so above or below is not possible
			btnPreConditionAbove.setDisable(true);
			btnPreConditionBelow.setDisable(true);
		}
	}

	/*****
	 * This method is called any time the user changes something that *may*
	 * cause a change in state that could influence which of the related buttons
	 * are visible or enabled. Rather than deal with each change separately,
	 * this method processes the state and determines the proper condition
	 * (visible and enabled) for *all* of the buttons.
	 */
	public void checkPostConditionButtons() {
		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the
		// exception of the enabled status for the single large Insert, the
		// Insert Above, and the Insert Below
		// buttons which are processed in the second step below.

		// This is the first step in the process.
		int numConditions = lstLocalPostConditionList.getNumberOfConditions();
		if (numConditions == 0) {
			// If the list is empty, only one insert button is visible as well
			// as the save and delete buttons.
			// The save and delete should be disabled. The semantics of the
			// enabled and disabled status of the
			// Insert buttons is more complex, so it is handled at the end of
			// this method.
			btnPostConditionNew.setVisible(true);
			btnPostConditionAbove.setDisable(true);
			btnPostConditionAbove.setVisible(false);
			btnPostConditionBelow.setDisable(true);
			btnPostConditionBelow.setVisible(false);
			btnPostConditionMvUp.setDisable(true);
			btnPostConditionMvDn.setDisable(true);
			btnPostConditionDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for
			// inserting below the selected item. So we start by making the
			// insert above and below buttons
			// visible and make the single insert button invisible and disabled.
			btnPostConditionAbove.setVisible(true);
			btnPostConditionBelow.setVisible(true);
			btnPostConditionNew.setVisible(false);
			btnPostConditionNew.setDisable(true);

			if (ndxJlstPostConditionSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled.
				// (It is always visible.)
				btnPostConditionDelete.setDisable(false);
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are
				// disabled (they are always visible) regardless of what is in
				// the input fields for the name and
				// the description.
				btnPostConditionDelete.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two or more items in the list and
			// one item is selected
			if (numConditions < 2 || ndxJlstPostConditionSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected neither of the two buttons
				// should be enabled
				btnPostConditionMvUp.setDisable(true);
				btnPostConditionMvDn.setDisable(true);

			} else if (ndxJlstPostConditionSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnPostConditionMvUp.setDisable(true);
				btnPostConditionMvDn.setDisable(false);

			} else if (ndxJlstPostConditionSelector == numConditions - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnPostConditionMvUp.setDisable(false);
				btnPostConditionMvDn.setDisable(true);

			} else {
				// Otherwise both options (up and down) are valid.
				btnPostConditionMvUp.setDisable(false);
				btnPostConditionMvDn.setDisable(false);
			}
		}

		// The following is the second step... Determine the enabled status of
		// the insert buttons.

		if (lstLocalPostConditionList.getNumberOfConditions() > 0) {
			// The list is not empty
			if (ndxJlstPostConditionSelector == -1
					|| ndxCmbSelPostConditionSelector == -1) {
				// The list is not empty, but no item is selected or no ComboBox
				// item is selected, so it is not
				// possible to insert and artifact above or below.
				btnPostConditionAbove.setDisable(true);
				btnPostConditionBelow.setDisable(true);
			} else {
				// A non-empty list with an item selected and a selected item in
				// the ComboBox, so it is possible
				// to insert above or below
				btnPostConditionAbove.setDisable(false);
				btnPostConditionBelow.setDisable(false);
			}
		} else {
			// The list is empty, so the we need to see if the empty list insert
			// should be enabled
			if (ndxCmbSelPostConditionSelector == -1)
				// If the ComboBox does not have a selected item, then no insert
				// is possible
				btnPostConditionNew.setDisable(true);
			else
				// Empty list, but the ComboxBox has a selected item, so it
				// could be inserted into the list
				btnPostConditionNew.setDisable(false);

			// Since the list is empty, it is not possible to have a selected
			// element, so above or below is not possible
			btnPostConditionAbove.setDisable(true);
			btnPostConditionBelow.setDisable(true);
		}
	}

	/*****
	 * This method is called any time the user changes something that *may*
	 * cause a change in state that could influence which of the related buttons
	 * are visible or enabled. Rather than deal with each change separately,
	 * this method processes the state and determines the proper condition
	 * (visible and enabled) for *all* of the buttons.
	 */
	public void checkStepTaskButtons() {
		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the
		// exception of the enabled status for the single large Insert, the
		// Insert Above, and the Insert Below
		// buttons which are processed in the second step below.

		// This is the first step in the process.
		int numTasks = lstLocalStepTaskList.getNumberOfTasks();
		if (numTasks == 0) {
			// If the list is empty, only one insert button is visible as well
			// as the save and delete buttons.
			// The save and delete should be disabled. The semantics of the
			// enabled and disabled status of the
			// Insert buttons is more complex, so it is handled at the end of
			// this method.
			btnStepTaskNew.setVisible(true);
			btnStepTaskAbove.setDisable(true);
			btnStepTaskAbove.setVisible(false);
			btnStepTaskBelow.setDisable(true);
			btnStepTaskBelow.setVisible(false);
			btnStepTaskMvUp.setDisable(true);
			btnStepTaskMvDn.setDisable(true);
			btnStepTaskDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for
			// inserting below the selected item. So we start by making the
			// insert above and below buttons
			// visible and make the single insert button invisible and disabled.
			btnStepTaskAbove.setVisible(true);
			btnStepTaskBelow.setVisible(true);
			btnStepTaskNew.setVisible(false);
			btnStepTaskNew.setDisable(true);

			if (ndxJlstStepTaskSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled.
				// (It is always visible.)
				btnStepTaskDelete.setDisable(false);
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are
				// disabled (they are always visible) regardless of what is in
				// the input fields for the name and
				// the description.
				btnStepTaskDelete.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two or more items in the list and
			// one item is selected
			if (numTasks < 2 || ndxJlstStepTaskSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected neither of the two buttons
				// should be enabled
				btnStepTaskMvUp.setDisable(true);
				btnStepTaskMvDn.setDisable(true);

			} else if (ndxJlstStepTaskSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnStepTaskMvUp.setDisable(true);
				btnStepTaskMvDn.setDisable(false);

			} else if (ndxJlstStepTaskSelector == numTasks - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnStepTaskMvUp.setDisable(false);
				btnStepTaskMvDn.setDisable(true);

			} else {
				// Otherwise both options (up and down) are valid.
				btnStepTaskMvUp.setDisable(false);
				btnStepTaskMvDn.setDisable(false);
			}
		}

		// The following is the second step... Determine the enabled status of
		// the insert buttons.

		if (lstLocalStepTaskList.getNumberOfTasks() > 0) {
			// The list is not empty
			if (ndxJlstStepTaskSelector == -1
					|| ndxCmbSelStepTaskSelector == -1) {
				// The list is not empty, but no item is selected or no ComboBox
				// item is selected, so it is not
				// possible to insert and artifact above or below.
				btnStepTaskAbove.setDisable(true);
				btnStepTaskBelow.setDisable(true);
			} else {
				// A non-empty list with an item selected and a selected item in
				// the ComboBox, so it is possible
				// to insert above or below
				btnStepTaskAbove.setDisable(false);
				btnStepTaskBelow.setDisable(false);
			}
		} else {
			// The list is empty, so the we need to see if the empty list insert
			// should be enabled
			if (ndxCmbSelStepTaskSelector == -1)
				// If the ComboBox does not have a selected item, then no insert
				// is possible
				btnStepTaskNew.setDisable(true);
			else
				// Empty list, but the ComboxBox has a selected item, so it
				// could be inserted into the list
				btnStepTaskNew.setDisable(false);

			// Since the list is empty, it is not possible to have a selected
			// element, so above or below is not possible
			btnStepTaskAbove.setDisable(true);
			btnStepTaskBelow.setDisable(true);
		}
	}

	public void selectListStepTaskListItemSelected() {
		// Fetch the selected index from the JList.
		int ndx = jlstStepTask.getSelectionModel().getSelectedIndex();

		// If the index matches the current local copies, there is no need to
		// proceed
		if (ndxJlstStepTaskSelector == ndx)
			return;

		// If the index is different, then we must update the local copies,
		// remember the new index
		ndxJlstStepTaskSelector = ndx;

		// This change may cause changes in the state of the five buttons for
		// the Artifacts Produced list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkStepTaskButtons();
	}

	public void performStepTaskNew() {
		// This method is only used for inserting the first item into an empty
		// JList. (Once the JList has an
		// item in it, the user must specify whether to insert above or below
		// the selected list item.)

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbSelStepTaskSelector = jcbStepTask.getSelectionModel()
				.getSelectedIndex(); // Get the index of the artifact
		Task toBeInserted = cmbStepTaskSelectList
				.extractTask(ndxCmbSelStepTaskSelector);
		lstLocalStepTaskList.addTask(toBeInserted); // Insert the artifact into
		// the list

		// Since there is only one item in the list now, we must highlight the
		// first one as the selected item
		ndxJlstStepTaskSelector = 0;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAStepTaskInsert();
	}

	public void performStepTaskAbove() {
		// This method is only used for inserting above a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbSelStepTaskSelector = jcbStepTask.getSelectionModel()
				.getSelectedIndex();
		Task toBeInserted = cmbStepTaskSelectList
				.extractTask(ndxCmbSelStepTaskSelector);
		lstLocalStepTaskList.addTask(ndxJlstStepTaskSelector, toBeInserted);

		// We have inserted above the previous current item, so that item will
		// be pushed down one and
		// the just inserted item will take its old place. Therefore, the index
		// of the current item
		// stays the same.

		// Update the GUI to reflect the just made change
		commonUpdateAfterAStepTaskInsert();
	}

	public void performStepTaskBelow() {
		// This method is only used for inserting below a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbSelStepTaskSelector = jcbStepTask.getSelectionModel()
				.getSelectedIndex();
		Task toBeInserted = cmbStepTaskSelectList
				.extractTask(ndxCmbSelStepTaskSelector);
		lstLocalStepTaskList.addTask(ndxJlstStepTaskSelector + 1, toBeInserted);

		// We have inserted below the previous current item, so this just
		// inserted item will have an index that is
		// one larger that the previous selected item. Therefore, the index of
		// the current item is incremented.
		// We changed the list, so we must tell the GUI component which item is
		// selected now.
		ndxJlstStepTaskSelector++;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAStepTaskInsert();
	}

	public void commonUpdateAfterAStepTaskInsert() {
		// The Artifact used list has changed, so we need to rebuild the list
		// that is displayed
		lstStepTaskSelectListSource = lstLocalStepTaskList.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglStepTaskListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Used select list to display
		// the new list

		ObservableList<String> tasklist = FXCollections
				.observableArrayList(lstStepTaskSelectListSource);
		jlstStepTask.setItems(tasklist);

		// We will now process changes to the list
		lglStepTaskListIsBeingUpdated = false;

		// We changed the list, so we must tell the GUI component which item is
		// selected now.
		jlstStepTask.getSelectionModel().select(ndxJlstStepTaskSelector);

		// The ComboBox list has also changed (one fewer item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Artifacts Used list.
		cmbStepTaskSelectList = lstTaskList.buildSubList(lstLocalStepTaskList);

		// Create the ComboBox Model, complete with sequence numbers

		cmbModelStepTask = cmbStepTaskSelectList.buildSelectList();
		ObservableList<String> steptasks = FXCollections
				.observableArrayList(cmbModelStepTask);
		// Establish the updated ComboBox using the model that has just been
		// created
		jcbStepTask.setItems(steptasks);

		// Since the size of the ComboBox is now smaller, we need to check the
		// index of the current
		// selected item to be sure that it is still in a valid range, and fix
		// it if not. (Remember,
		// it could now be empty!)
		if (ndxCmbSelStepTaskSelector >= cmbStepTaskSelectList
				.getNumberOfTasks())
			ndxCmbSelStepTaskSelector = cmbStepTaskSelectList
					.getNumberOfTasks() - 1;

		// Establish the current artifact in the ComboBox
		jcbStepTask.getSelectionModel().select(ndxCmbSelStepTaskSelector);

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkStepButtons();
	}

	public void performStepTaskMvUp() {
		// This method is only used for moving a selected item up a non-empty
		// JList when there is at least
		// one element above it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalStepTaskList.moveTaskUp(ndxJlstStepTaskSelector);

		// We moved it up, so to keep the same item selected, the index must be
		// reduced by one
		ndxJlstStepTaskSelector--;

		// Update GUI to reflect the just made change
		commonUpdateAfterAStepTaskMove();
	}

	public void performStepTaskMvDn() {
		// This method is only used for moving a selected item down a non-empty
		// JList when there is at least
		// one element below it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalStepTaskList.moveTaskDn(ndxJlstStepTaskSelector);

		// We moved it down, so to keep the same item selected, the index must
		// be increased by one
		ndxJlstStepTaskSelector++;

		// Update GUI to reflect the just made change
		commonUpdateAfterAStepTaskMove();
	}

	public void commonUpdateAfterAStepTaskMove() {
		// The list has changed so we need to build a new list.
		lstStepTaskSelectListSource = lstLocalStepTaskList.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglStepTaskListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Used select list to display
		// the new list
		ObservableList<String> tasklist = FXCollections
				.observableArrayList(lstStepTaskSelectListSource);
		jlstStepTask.setItems(tasklist);

		// Given the new list, this command will once again select the item that
		// was moved
		jlstStepTask.getSelectionModel().select(ndxJlstStepTaskSelector);

		// We will now process changes to the list
		lglStepTaskListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkStepButtons();
	}

	public void performStepTaskDelete() {
		// This method is only used for removing a selected item from a
		// non-empty JList.

		// This statements actually does the delete. The rest are about
		// adjusting the GUI state to match.
		lstLocalStepTaskList.deleteTask(ndxJlstStepTaskSelector);

		// Now that the artifact has been deleted, we need to clean up the UI to
		// match this state.

		// The list has changed so we need to build a new list.
		lstStepTaskSelectListSource = lstLocalStepTaskList.buildSelectList();

		// Since we are updating the list, we need to ignore the list change
		// events
		lglStepTaskListIsBeingUpdated = true;

		// Update the GUI component for the Artifact Used select list to display
		// the new list
		ObservableList<String> tasklist = FXCollections
				.observableArrayList(lstStepTaskSelectListSource);
		jlstStepTask.setItems(tasklist); // Establish the new list
		// We have delete the current item from the list, so no item in the list
		// will be selected now.
		// Therefore, we clear the index and the selection from the GUI
		// component

		ndxJlstStepTaskSelector = -1;

		jlstStepTask.getSelectionModel().clearSelection(); // so clear both the
		// index and the GUI
		// list

		// The ComboBox list has also changed (one more item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Artifacts Used list.
		cmbStepTaskSelectList = lstTaskList.buildSubList(lstLocalStepTaskList);

		// Create the ComboBox Model, complete with sequence numbers

		cmbModelStepTask = cmbStepTaskSelectList.buildSelectList();
		ObservableList<String> steptasks = FXCollections
				.observableArrayList(cmbModelStepTask);
		// Establish the updated ComboBox using the model that has just been
		// created
		jcbStepTask.setItems(steptasks);

		// Since the size of the ComboBox is now larger, we know that there is
		// at least one item in it,
		// so we will default the ComboBox to the first item in the list.

		// Set the index to be the first item in the list
		ndxCmbSelStepTaskSelector = 0;

		// Set the GUI component so it has selected the first item in the list
		jcbStepTask.getSelectionModel().select(ndxCmbSelStepTaskSelector);

		// We will now process changes to the list
		lglStepTaskListIsBeingUpdated = false;

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkStepButtons();
	}

	/*****
	 * Process a change to the user input Name Field
	 * 
	 * Limit the total number of characters in the field to just 34. If there
	 * are more, discard them and beep the console to inform the user that a
	 * limit has been reached.
	 * 
	 * The current state of the input field is echoed in the public attribute
	 * named strLocalStepName.
	 */
	public void rtnCheckFldStepsNewName() {
		if (fldStepName.getText().length() < 35)
			// As long as there are not more than 34 characters, all we need to
			// do is to fetch the text and save a
			// copy in the local attribute.
			strLocalStepName = fldStepName.getText();
		else {
			// Limit the size of the input to 34 characters, so discard any
			// after the first 34.
			strLocalStepName = fldStepName.getText().substring(0, 34);

			// We can't change the input field during the notification, so we
			// schedule a task to be run after the
			// update and other I/O actions finish to prune the input to just
			// the first 34 characters and beep
			// the console.

			// This creates the task that is to be run later.
			Runnable resetInput = new Runnable() {
				public void run() {
					fldStepName.setText(strLocalStepName);
					java.awt.Toolkit.getDefaultToolkit().beep();
				}
			};
			// This is the command to actual schedule that.
			Platform.runLater(resetInput);

		}

		// This method is called when a change of state to the Step Name list
		// *may* cause a change of state in
		// the Step buttons, so we check it.
		checkStepButtons();
	}

	/*****
	 * Process a change to the user input Description Field
	 * 
	 * Limit the total number of characters in the field to just 1000. If there
	 * are more, discard them and beep the console to inform the user that a
	 * limit has been reached.
	 * 
	 * The current state of the input field is echoed in the public attribute
	 * named strLocalStepDesc.
	 */
	public void rtnCheckFldStepsNewDesc() {
		if (fldStepDesc.getText().length() < 1001) {
			// As long as there are not more than 1000 characters, all we need
			// to do is to fetch the text and save a copy in the local
			// attribute.
			strLocalStepDesc = fldStepDesc.getText();
		} else {
			// Limit the size of the input to 1000 characters, so discard any
			// after the first 1000.
			strLocalStepDesc = fldStepDesc.getText().substring(0, 1000);

			// We can't change the input field during the notification, so we
			// schedule a task to be run after the update and other I/O actions
			// finish to prune the input to just the first 1000 characters and
			// beep
			// the console.

			// This creates the task that is to be run later.
			Runnable pruneTheInput = new Runnable() {
				public void run() {
					fldStepDesc.setText(strLocalStepDesc);
					java.awt.Toolkit.getDefaultToolkit().beep();
				}
			};
			// This is the command to actual schedule that.
			Platform.runLater(pruneTheInput);
		}

		// This method is called when a change of state to the Step Description
		// list *may* cause a change of state in
		// the Step buttons, so we check it.
		checkStepButtons();
	}
	
	/************************************************************************************************************
	 * Tasks panel Methods
	 */

	/*****
	 * This method is called any time the user switches from some other screen
	 * or pane to the Tasks pane. The purpose is to set up the GUI elements
	 * based on the current state of the attributes upon which it depends,
	 * knowing that they may have changed from the last time (if ever) the pane
	 * has been displayed.
	 */
	@SuppressWarnings("unchecked")
	public void performWakeUpTasksPane() {
		System.out.println("Wakeup the Tasks tabbed pane");
		lstTaskSelectListSource = lstTaskList.buildSelectList(); 
		lglTaskListIsBeingUpdated = true;
		ObservableList<String> listTaskItems = FXCollections
				.observableArrayList(lstTaskSelectListSource);
		jlstTasks.setItems(listTaskItems);
		lglTaskListIsBeingUpdated = false;
		// Establish the Artifact Used list using the lstLocalArtifactsUsedList  attribute
		// Create the Artifact Used list (a list with the sequence numbers)
		lstArtUsedSelectListSource = lstLocalArtifactsUsedList.buildSelectList();
		// Establish the Artifact Used list using the lstLocalArtifactsUsedList attribute
		lglArtUsedListIsBeingUpdated = true; // Ignore list change events
		ObservableList<String> items = FXCollections
				.observableArrayList(lstArtUsedSelectListSource);
		jlstArtUsed.setItems(items); // Set the list
		lglArtUsedListIsBeingUpdated = false; // Recognize list change events
		ndxJlstArtUsedSelector = -1; // No item is selected
		jlstArtUsed.getSelectionModel().clearSelection(); // so clear the

		// Create the ComboBox that add artifacts to the Artifacts Used list
		cmbArtifactsUsedSelectList = lstArtifactList.buildSubList(lstLocalArtifactsUsedList);

		cmbModelArtUsed = cmbArtifactsUsedSelectList.buildSelectList();
		ObservableList<String> cmbUsedObservList = FXCollections
				.observableArrayList(cmbModelArtUsed);
		jcbArtUsed.setItems(cmbUsedObservList);

		// Determine if the ComboBox has an item in it or not so we know whether
		// or not to turn on the insert buttons.
		// If there is one or more item in the ComboBox, then an insert is
		// always valid, if there are none, it is not valid
		if (cmbModelArtUsed.length > 0) {
			jcbArtUsed.getSelectionModel().select(0);
			ndxCmbArtUsedSelector = 0; // Default to the first item in the list
		} else
			ndxCmbArtUsedSelector = -1; // Indicate there is nothing to insert

		// Establish the Artifact Used list using the lstLocalArtifactsUsedList attribute
		lglArtProducedListIsBeingUpdated = true; // Ignore list change events
		lstArtProducedSelectListSource = lstLocalArtifactsProducedList.buildSelectList(); 
		lglArtProducedListIsBeingUpdated = true; // Ignore list change events

		ObservableList<String> producedItems = FXCollections
				.observableArrayList(lstArtProducedSelectListSource);
		jlstArtProduced.setItems(producedItems);

		lglArtProducedListIsBeingUpdated = false; // Process change events
		ndxJlstArtProducedSelector = -1; // No item is selected

		jlstArtProduced.getSelectionModel().clearSelection(); // so clear the selection

		cmbArtifactsProducedSelectList = lstArtifactList 
				.buildSubList(lstLocalArtifactsProducedList);

		cmbModelArtProduced = cmbArtifactsProducedSelectList.buildSelectList();
		ObservableList<String> producedObservableList = FXCollections
				.observableArrayList(cmbModelArtProduced);
		jcbArtProduced.setItems(producedObservableList);

		if (cmbModelArtProduced.length > 0) { // Same ComboBox process as above
			jcbArtProduced.getSelectionModel().select(0);
			ndxCmbArtProducedSelector = 0; // Default to the first item in the
		} else
			ndxCmbArtProducedSelector = -1; // Indicate there is nothing to

		lglArtProducedListIsBeingUpdated = false; // Ignore list
		// Now that the lists have been established, it is time to see about
		// which buttons are
		// visible and which ones should be enabled
		if (ndxJlstTaskSelector == -1)
			jlstTasks.getSelectionModel().clearSelection();
		else
			jlstPlans.getSelectionModel().select(ndxJlstTaskSelector);
		checkTaskButtons();
	}

	/*****
	 * This method is called whenever a ListSelectEvent for the Tasks List
	 * occurs. The method checks to see if this event duplicates another by
	 * seeing if the selection index from the event is the same as the local
	 * copy in ndxLstTasksSelector. If they differ, a new Task is selected and
	 * the various attributes associated with that Task are fetched and
	 * established.
	 * 
	 * @param event
	 *            - The event information we use to determine the new selected
	 *            list item.
	 */
	public void selectListTasksListItemSelected() {
		// The language can't be sure that this cast is safe even though *we*
		// know no other GUI component uses this method.
		int ndx = jlstTasks.getSelectionModel().getSelectedIndex(); // Fetch the
																	// selected
																	// index
																	// from the
																	// JList.
		if (ndxJlstTaskSelector == ndx) // If the index matches the current
										// local copies, there is no need to
										// proceed
			return;

		// If the index is different, then we must update the local copies
		ndxJlstTaskSelector = ndx; // Remember the new index

		strSelectedTaskName = lstTaskList.getTask(ndxJlstTaskSelector) 
				.getName();
		strLocalTaskName = strSelectedTaskName; // The selected and local
												// versions start out
		fldTaskName.setText(strLocalTaskName); // being the same, but the user
												// might change that

		strSelectedTaskDesc = lstTaskList.getTask(ndxJlstTaskSelector) 
				.getDesc();
		strLocalTaskDesc = strSelectedTaskDesc; // Do the same thing for the Description
		fldTaskDesc.setText(strLocalTaskDesc);

		lstLocalArtifactsUsedList = lstTaskList.getTask(ndxJlstTaskSelector) 
				.getArtifactsUsedListCopy();
		lstLocalArtifactsUsedList.validateMembers(lstArtifactList);
		lstArtUsedSelectListSource = lstLocalArtifactsUsedList
				.buildSelectList();
		lglArtUsedListIsBeingUpdated = true; // Ignore update events
		ObservableList lstArtUsedObservable = FXCollections
				.observableArrayList(lstArtUsedSelectListSource);
		jlstArtUsed.setItems(lstArtUsedObservable);

		// Establish the new list
		lglArtUsedListIsBeingUpdated = false; // Start accepting events from this list
		ndxJlstArtUsedSelector = -1; // This app does not recall the last select item
		jlstArtUsed.getSelectionModel().clearSelection(); // so clear both the
		// index and the GUI
		// list

		// This set the local copy of the ComboBox the is used to add items to
		// the artifacts used list
		cmbArtifactsUsedSelectList = lstArtifactList
				.buildSubList(lstLocalArtifactsUsedList);
		cmbModelArtUsed = cmbArtifactsUsedSelectList.buildSelectList();

		ObservableList cmbModelArtUsedObservable = FXCollections
				.observableArrayList(cmbModelArtUsed);
		jcbArtUsed.setItems(cmbModelArtUsedObservable);
		// jcbArtUsed.setModel(cmbModelArtUsed); // Establish the actual
		// ComboBox list
		if (cmbModelArtUsed.length > 0)
			ndxCmbArtUsedSelector = 0; // Default to the first item in the list
		else
			ndxCmbArtUsedSelector = -1; // Indicate there is nothing to insert

		// This sets the local copy of the artifacts produced list
		lstLocalArtifactsProducedList = lstTaskList
				.getTask(ndxJlstTaskSelector).getArtifactsProducedListCopy();
		lstLocalArtifactsProducedList.validateMembers(lstArtifactList);
		lstArtProducedSelectListSource = lstLocalArtifactsProducedList
				.buildSelectList();
		lglArtProducedListIsBeingUpdated = true;

		ObservableList lstArtProducedObservable = FXCollections
				.observableArrayList(lstArtProducedSelectListSource);
		jlstArtProduced.setItems(lstArtProducedObservable); // Establish the new
															// list

		lglArtProducedListIsBeingUpdated = false; // Start accepting events from
													// this list
		ndxJlstArtProducedSelector = -1; // This app does not recall the last
											// select item
		jlstArtProduced.getSelectionModel().clearSelection(); // so clear both
																// the index and
																// the GUI list

		cmbArtifactsProducedSelectList = lstArtifactList
				.buildSubList(lstLocalArtifactsProducedList);
		cmbModelArtProduced = cmbArtifactsProducedSelectList.buildSelectList();

		ObservableList cmbModelArtProducedObservable = FXCollections
				.observableArrayList(cmbModelArtProduced); // Establish the
															// actual ComboBox
															// list
		jcbArtProduced.setItems(cmbModelArtProducedObservable);

		if (cmbModelArtProduced.length > 0)
			ndxCmbArtProducedSelector = 0; // Default to the first item in the
		// list
		else
			ndxCmbArtProducedSelector = -1; // Indicate there is nothing to
											// insert
		// These changes may cause changes in the state of the five buttons, so
		// we process them all
		// rather than try to reason about which buttons might be affected.
		checkTaskButtons();
	}

	/*****
	 * This method is called with the user clicks on the Insert button to insert
	 * a new Task into an empty Tasks List.
	 */
	public void performTaskNew() {
		// This method is only used for inserting the first item into an
		// emptyJList. (Once
		// the JList has an item in it, the user must specify whether to insert
		// above or below the selected list item.)
		lstTaskList.addTask(strLocalTaskName, strLocalTaskDesc,
				lstLocalArtifactsUsedList, lstLocalArtifactsProducedList);

		// After an insert, the just inserted item is the now selected
		// item.Since the list was empty just before this insert,
		// there is now just one item in the list and that item's index is zero.
		// (Remember this is Java and the first item in a Java list has an index
		// of zero.)
		// These commands set the first item in the list to be selected
		ndxJlstTaskSelector = 0; // Set the local copy of the selector

		commonUpdateAfterATaskInsert(); // Update the GUI to reflect the just
										// made change
	}

	/*****
	 * This method is called with the user clicks on the Insert button to insert
	 * a new Task above the current selected task in the Tasks List.
	 */
	public void performTaskNewAbove() {
		// Create a new artifact and insert it above the current selected
		// artifact. Using the lstArtifactsSelector, as is, is what causes the
		// above to occur.
		// Otherwise this is exactly the same as the performArtifactsNewBelow
		// method

		lstTaskList.addTask(ndxJlstTaskSelector, strLocalTaskName,
				strLocalTaskDesc, lstLocalArtifactsUsedList,
				lstLocalArtifactsProducedList);

		// We want the just inserted item to be the currently selected item, so
		// we have to update the selector to be this new item.
		// Since we inserted above, the index of the selected item will be the
		// same.

		commonUpdateAfterATaskInsert(); // Update the GUI to reflect the just
										// made change
	}

	/*****
	 * This method is called with the user clicks on the Insert button, in the
	 * Tasks group of buttons, to insert a new Task below the current selected
	 * task in the Tasks List
	 */
	public void performTaskNewBelow() {
		// Create a new artifact and insert it below the current selected
		// artifact. Using the
		// ndxLstTasksSelector+1 causes the insert below to occur. Otherwise
		// this is exactly the
		// same as the performTasksNewAbove method
		lstTaskList.addTask(ndxJlstTaskSelector + 1, strLocalTaskName,
				strLocalTaskDesc, lstLocalArtifactsUsedList,
				lstLocalArtifactsProducedList);

		// We want the just inserted item to be the currently selected item, so
		// we have to update
		// the selector to be this new item. Since we inserted below, the index
		// of the selected
		// item will be one larger than it was.
		ndxJlstTaskSelector++;

		// Update the GUI to reflect the just made change
		commonUpdateAfterATaskInsert();
	}

	public void commonUpdateAfterATaskInsert() {
		// The list has changed so we need to build a new list.
		lstTaskSelectListSource = lstTaskList.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results in undesirable side effects.
		// The easiest way to avoid them is to ignore them. To do that, we first
		// have have to set a flag informing ourself that we are
		// updating the JList, so that handler will just ignore events, until we
		// are done. Then we do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the event handler should not process any events that are
		// generated.

		lglTaskListIsBeingUpdated = true;

		ObservableList lstTaskObservable = FXCollections
				.observableArrayList(lstTaskSelectListSource);
		jlstTasks.setItems(lstTaskObservable);

		lglTaskListIsBeingUpdated = false;

		jlstTasks.getSelectionModel().select(ndxJlstTaskSelector); 

		strSelectedTaskName = lstTaskList.getTask(ndxJlstTaskSelector)
				.getName(); // This sets the local copy of the Name
		strLocalTaskName = strSelectedTaskName;
		strSelectedTaskDesc = lstTaskList.getTask(ndxJlstTaskSelector) 
				.getDesc();
		strLocalTaskDesc = strSelectedTaskDesc;
		lstSelectedArtifactsUsedList = new ArtifactListController(lstTaskList 
				.getTask(ndxJlstTaskSelector).getArtifactsUsed());
		lstSelectedArtifactsProducedList = new ArtifactListController(
				lstTaskList.getTask(ndxJlstTaskSelector).getArtifactsProduced());

		// These changes may cause changes in the state of the five buttons, so
		// we process them all rather than try to reason about which buttons
		// might be affected.
		checkTaskButtons();
	}

	/*****
	 * This method is called with the user clicks on the Save button in the
	 * Tasks group of buttons. The method saves changes made in the current
	 * selected Task item.
	 */
	public void performTaskSave() {
		// We know that this method can only be called when the button is
		// enabled and that means
		// that a Task was selected and the input fields do not match what has
		// been stored.
		// Therefore, we need to update the stored list to match this updated
		// artifact. We
		// blindly update both the name and the description, even if only one
		// has been changed.
		lstTaskList.replaceTask(ndxJlstTaskSelector, strLocalTaskName,
				strLocalTaskDesc, lstLocalArtifactsUsedList,
				lstLocalArtifactsProducedList);

		commonUpdateAfterATaskInsert(); // Update the GUI to reflect the just
										// made change
	}

	/*****
	 * This method is called with the user clicks on the Move Up button in the
	 * Tasks group of buttons.
	 */
	public void performTaskMvUp() {
		lstTaskList.moveTaskUp(ndxJlstTaskSelector); // The following statement
														// actually moves the
														// item in the list
		// Now we need to update the GUI to match what we have done.
		ndxJlstTaskSelector--; // Keep the same item selected
		// Update the GUI to reflect the just made change
		commonUpdateAfterATaskMove();
	}

	/*****
	 * This method is called with the user clicks on the Move Dn button in the
	 * Tasks group of buttons.
	 */
	public void performTaskMvDn() {
		// The following statement actually moves the item in the list
		lstTaskList.moveTaskDn(ndxJlstTaskSelector);
		// Now we need to update the GUI to match what we have done.
		ndxJlstTaskSelector++; // Keep the same item selected

		commonUpdateAfterATaskMove(); // Update the GUI to reflect the just made
										// change
	}

	private void commonUpdateAfterATaskMove() {
		// The list has changed so we need to build a new list.
		lstTaskSelectListSource = lstTaskList.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglTaskListIsBeingUpdated = true;
		ObservableList lstTaskSelectObservable = FXCollections
				.observableArrayList(lstTaskSelectListSource);
		jlstTasks.setItems(lstTaskSelectObservable);

		lglTaskListIsBeingUpdated = false;

		// Now issue the command to make the original item selected again
		jlstTasks.getSelectionModel().select(ndxJlstTaskSelector);

		// These changes may cause changes in the state of the five buttons, so
		// we process them all rather than try to reason about
		// which buttons might be affected.

		checkTaskButtons();
	}

	/*****
	 * This method is called with the user clicks on the Delete button in the
	 * Tasks group of buttons. The method removes the current selected Task
	 * item.
	 */
	public void performTaskDelete() {
		// The following statement actually deletes the item from the list
		lstTaskList.deleteTask(ndxJlstTaskSelector);

		// Now we need to update the GUI to match what we have done.
		ndxJlstTaskSelector = -1; // After a delete, no item is selected, so we
		// reset
		jlstTasks.getSelectionModel().clearSelection(); // both the local index
		// and the GUI

		// The list has changed so we need to build a new list.
		lstTaskSelectListSource = lstTaskList.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglTaskListIsBeingUpdated = true;
		ObservableList<String> listTaskListObservable = FXCollections
				.observableArrayList(lstTaskSelectListSource);
		jlstTasks.setItems(listTaskListObservable);
		lglTaskListIsBeingUpdated = false;

		// We need to clear the local copies as there is no current selected
		// item
		strSelectedTaskName = ""; // Reset the local name, description, and the
		strSelectedTaskDesc = ""; // two lists of artifacts
		lstLocalArtifactsUsedList = new ArtifactListController();
		lstLocalArtifactsProducedList = new ArtifactListController();
		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkTaskButtons();

	}

	public void performTaskClear() {
		// Reset the user input Task Name text field
		strLocalTaskName = "";
		fldTaskName.setText(strLocalTaskName);

		// Reset the user input Task Description text field
		strLocalTaskDesc = "";
		fldTaskDesc.setText(strLocalTaskDesc);

		// Clear the list of artifacts used and rebuild the related ComboBox to
		// match
		lstLocalArtifactsUsedList = new ArtifactListController(
				emptyArtifactList);
		lstArtUsedSelectListSource = lstLocalArtifactsUsedList
				.buildSelectList();
		ObservableList lstArtUsedSelectListSourceObservable = FXCollections
				.observableArrayList(lstArtUsedSelectListSource);
		jlstArtUsed.setItems(lstArtUsedSelectListSourceObservable); // Establish
																	// an empty
																	// list
		ndxJlstArtUsedSelector = -1;
		jlstArtUsed.getSelectionModel().clearSelection(); // Clear both the
															// index and the GUI
															// list

		// Clear the list of artifacts produced and rebuild the related ComboBox
		// to match
		lstLocalArtifactsProducedList = new ArtifactListController(
				emptyArtifactList);
		lstArtProducedSelectListSource = lstLocalArtifactsUsedList
				.buildSelectList();
		ObservableList lstArtUsedSelectListSourceObservablee = FXCollections
				.observableArrayList(lstArtUsedSelectListSource);
		jlstArtProduced.setItems(lstArtUsedSelectListSourceObservablee); // Establish
																			// an
																			// empty
																			// list
		ndxJlstArtProducedSelector = -1;
		jlstArtProduced.getSelectionModel().clearSelection(); // Clear both the
																// index and the
																// GUI list

		// These changes may cause changes in the state of the five buttons, so
		// we process them all
		// rather than try to reason about which buttons might be affected.
		checkTaskButtons();
	}

	/*****
	 * Process a change to the user input Name Field
	 * 
	 * Limit the total number of characters in the field to just 34. If there
	 * are more, discard them and beep the console to inform the user that a
	 * limit has been reached.
	 * 
	 * The current state of the input field is echoed in the private attribute
	 * named strLocalTaskName.
	 */
	public void rtnCheckFldTasksNewName() {
		strLocalTaskName = fldTaskName.getText();
		checkTaskButtons();
	}

	/*****
	 * Process a change to the user input Description Field
	 * 
	 * Limit the total number of characters in the field to just 1000. If there
	 * are more, discard them and beep the console to inform the user that a
	 * limit has been reached.
	 * 
	 * The current state of the input field is echoed in the private attribute
	 * named strLocalTaskDesc.
	 */
	public void rtnCheckFldTasksNewDesc() {

		strLocalTaskDesc = fldTaskDesc.getText();
		checkTaskButtons();
	}

	/*****
	 * This method is called whenever the state of the Tasks GUI form is
	 * changed. This method determines which of the buttons associated with the
	 * Tasks List should be visible and/or enabled based on the state of the
	 * attributes associated with this form.
	 */
	public void checkTaskButtons() {
		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the
		// exception of the enabled status for the single large Insert, the
		// Insert Above, and the Insert Below
		// buttons which are processed in the second step below.

		// This is the first step in the process.
		int numTasks = lstTaskList.getNumberOfTasks();
		if (numTasks == 0) {
			// If the list is empty, only one insert button is visible as well
			// as the save and delete buttons.
			// The save and delete should be disabled. The semantics of the
			// enabled and disabled status of the
			// Insert buttons is more complex, so it is handled at the end of
			// this method.
			btnTasksNew.setVisible(true);
			btnTasksNewAbove.setDisable(true);
			btnTasksNewAbove.setVisible(false);
			btnTasksNewBelow.setDisable(true);
			btnTasksNewBelow.setVisible(false);
			btnTasksMvUp.setDisable(true);
			btnTasksMvDn.setDisable(true);
			btnTasksDelete.setDisable(true);
			btnTasksSave.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one
			// for inserting below the selected item. So we start by making the
			// insert above and below buttons
			// visible and make the single insert button invisible and disabled.
			btnTasksNewAbove.setVisible(true);
			btnTasksNewBelow.setVisible(true);
			btnTasksNew.setVisible(false);
			btnTasksNew.setDisable(true);

			if (ndxJlstTaskSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled.
				// (It is always visible.)
				btnTasksDelete.setDisable(false);

				if (
				// the Step Names match
				strSelectedTaskName.equals(strLocalTaskName)

				// the Descriptions match
						&& strSelectedTaskDesc.equals(strLocalTaskDesc)

						// The PreConditions match
						&& ((!lstLocalArtifactsUsedList.isEmpty()
								&& !lstSelectedArtifactsUsedList.isEmpty() && lstLocalArtifactsUsedList
									.isTheSameAs(lstSelectedArtifactsUsedList)) || (lstLocalArtifactsUsedList
								.isEmpty() && lstSelectedArtifactsUsedList
								.isEmpty()))

						// The PostConditions match
						&& ((!lstLocalArtifactsProducedList.isEmpty()
								&& !lstSelectedArtifactsProducedList.isEmpty() && lstLocalArtifactsProducedList
									.isTheSameAs(lstSelectedArtifactsProducedList)) || (lstLocalArtifactsProducedList
								.isEmpty() && lstSelectedArtifactsProducedList
								.isEmpty()))) {
					// Getting here says that the selected and the user input
					// copies match, so save is not enabled
					btnTasksSave.setDisable(true);
				} else {
					// Getting here says that the selected task and the user
					// input has at least one difference, but
					// we will not support a save if the Name field is empty
					if (strLocalTaskName.length() > 0)
						btnTasksSave.setDisable(false);
					else
						btnTasksSave.setDisable(true);
				}
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are
				// disabled (they are always visible) regardless of what is in
				// the input fields for the name, the
				// description, or the condition of the used and produced lists
				btnTasksDelete.setDisable(true);
				btnTasksSave.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two or more items in the list and
			// one item is selected
			if (numTasks < 2 || ndxJlstTaskSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected neither of the two buttons
				// should be enabled
				btnTasksMvUp.setDisable(true);
				btnTasksMvDn.setDisable(true);

			} else if (ndxJlstTaskSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnTasksMvUp.setDisable(true);
				btnTasksMvDn.setDisable(false);

			} else if (ndxJlstTaskSelector == numTasks - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnTasksMvUp.setDisable(false);
				btnTasksMvDn.setDisable(true);

			} else {
				// Otherwise both options are valid.
				btnTasksMvUp.setDisable(false);
				btnTasksMvDn.setDisable(false);
			}
		}

		// The following is the second step.

		// The remaining code is about the enabled or disabled status of the
		// insert buttons. Visibility has been
		// handled above as has the enabled status for Delete and Save. This
		// code deals with the visibility of the
		// three insert buttons.
		if (lstTaskList.getNumberOfTasks() == 0
				&& strLocalTaskName.length() == 0) {
			// The list is empty and the user input name field is empty, so the
			// single Insert button is visible,
			// but it is disabled. The two insert buttons were disabled above
			// and their visibility was handled above.
			btnTasksNew.setDisable(true);
		} else if (lstTaskList.getNumberOfTasks() == 0
				&& strLocalTaskName.length() > 0) {
			// The list is empty, but the user input name field is not, so the
			// single Insert button is visible and
			// enabled. The two Insert buttons are not visible (from above) and
			// are disabled.
			btnTasksNew.setDisable(false);
		} else if (lstTaskList.getNumberOfTasks() > 0
				&& strLocalTaskName.length() == 0) {
			// The list is not empty, but the user input name field is empty, so
			// the two Insert buttons are visible
			// and enabled. The single Insert button is not visible and disabled
			// from above).
			btnTasksNewAbove.setDisable(true);
			btnTasksNewBelow.setDisable(true);
		} else if (lstTaskList.getNumberOfTasks() > 0
				&& strLocalTaskName.length() > 0) {
			// The list is not empty and the user input name field is not empty,
			// so the status of the two Insert
			// buttons in terms of enabled or not is determined by where or not
			// an item in the list has been selected.
			// We do know that the single insert was made not visible and
			// disabled above, so no need to do it here.
			if (ndxJlstTaskSelector == -1) {
				// No item is selected, so it is not possible to insert above or
				// below it.
				btnTasksNewAbove.setDisable(true);
				btnTasksNewBelow.setDisable(true);
			} else {
				// An item is selected, so it is possible to insert above or
				// below it
				btnTasksNewAbove.setDisable(false);
				btnTasksNewBelow.setDisable(false);
			}
		}

		// Verify that the subordinate pane buttons are properly set as well.
		checkArtUsedButtons();
		checkArtProducedButtons();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}


	/**
	 * conditions
	 */

	/**
	 * functions to run conditions pane
	 */

	public void performWakeUpConditionsPane() {
		// this function is performed immediately after the condition tab is
		// selected
		System.out.println("Wake up the Conditions tabbed pane");
		// inserted items in the list are set
		lstConditionList.checkToInsert(lstLifeCycleList);
		lstConditionsSelectListSource = lstConditionList.buildSelectList();
		lglConditionListIsBeingUpdated = true;
		// adding items to list
		ObservableList<String> items = FXCollections
				.observableArrayList(lstConditionsSelectListSource);
		// setting them to list view
		jlstConditions.setItems(items);

		lglConditionListIsBeingUpdated = false;
		if (ndxJlstConditionSelector == -1) {
			jlstConditions.getSelectionModel().clearSelection();
		} else
			jlstConditions.getSelectionModel().select(ndxJlstConditionSelector);
		checkConditionButtons();

	}

	public void Conditionselection() {
		// to enable the selection of the conditions
		int a = jlstConditions.getSelectionModel().getSelectedIndex();
		if (lglConditionListIsBeingUpdated)
			return;
		jltConditionsListItemSelected();

	}

	public void performConditionsNew() {
		// the details given in tab are inserted in to list in the starting
		// index
		lstConditionList.addCondition(strLocalConditionName,
				strLocalConditionDesc, lglConditionState);
		ndxJlstConditionSelector = 0;
		commonUpdateAfterAConditionInsert();
	}

	public void performConditionsNewAbove() {
		// copies the details given in tab and inserts in to list above the
		// selected index
		lstConditionList
				.addCondition(ndxJlstConditionSelector, strLocalConditionName,
						strLocalConditionDesc, lglConditionState);
		ndxJlstConditionSelector++;
		commonUpdateAfterAConditionInsert();
	}

	public void performConditionsNewBelow() {
		// copies the details given in tab and inserts in to list below the
		// selected index
		lstConditionList
				.addCondition(ndxJlstConditionSelector + 1,
						strLocalConditionName, strLocalConditionDesc,
						lglConditionState);
		ndxJlstConditionSelector++;
		commonUpdateAfterAConditionInsert();
	}

	public void performConditionMvUp() {
		lstConditionList.moveConditionUp(ndxJlstConditionSelector);
		// index is selected and moved up
		ndxJlstConditionSelector--;
		// the following is to be performed so as to update the changes
		commonUpdateAfterAConditionMove();

	}

	public void performConditionMvDn() {
		lstConditionList.moveConditionDn(ndxJlstConditionSelector);
		// index is moved down
		ndxJlstConditionSelector++;
		// the following is to be performed so as to update the changes
		commonUpdateAfterAConditionMove();
	}

	private void commonUpdateAfterAConditionMove() {

		lstConditionsSelectListSource = lstConditionList.buildSelectList();// a
																			// source
																			// list
																			// is
																			// set
																			// to
																			// update
																			// the
																			// inserted
																			// list
		lglConditionListIsBeingUpdated = true;
		// the list items are set to an observable list
		ObservableList<String> items = FXCollections
				.observableArrayList(lstConditionsSelectListSource);
		// the items are now moved to list view
		jlstConditions.setItems(items);
		lglConditionListIsBeingUpdated = false;
		jlstConditions.getSelectionModel().select(ndxJlstConditionSelector);
		checkConditionButtons();
	}

	public void performConditionsDelete() {
		// We know that it is only possible to get here when an Condition in the
		// list has been selected, or the button would have been disabled. So no
		// checking is needed.
		lstConditionList.deleteCondition(ndxJlstConditionSelector);

		// Now that the Condition has been deleted, we need to clean up the UI
		// to match this state.

		// After a delete, no item is selected, so that is what these statements
		// do
		ndxJlstConditionSelector = -1;
		jlstConditions.getSelectionModel().clearSelection();

		// The list has changed so we need to build a new list.
		lstConditionsSelectListSource = lstConditionList.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglConditionListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstConditionsSelectListSource);
		jlstConditions.setItems(items);
		lglConditionListIsBeingUpdated = false;

		// We need to clear the local copies as there is no current selected
		// item
		strSelectedConditionName = "";
		strSelectedConditionDesc = "";

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkConditionButtons();

	}

	public void performConditionsClear() {

		ndxJlstConditionSelector = -1; // Reset the local copy of the current

		jlstConditions.getSelectionModel().clearSelection(); // Clear the JList
		// selection
		fldConditionName.setText(""); // Clear the Name input field
		strLocalConditionName = ""; // and the local copy
		fldConditionDesc.setText(""); // Clear the Description input field
		strLocalConditionDesc = ""; // and the local copy

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkConditionButtons();
	}

	private void jltConditionsListItemSelected() {
		@SuppressWarnings("unchecked")
		// The language can't be sure that this cast
		// is safe even though *we* know no other GUI
		// component uses this method.
		// Fetch the selected index from the JList.
		int ndx = jlstConditions.getSelectionModel().getSelectedIndex();

		// If the index matches the current local copies, there is no need to
		// proceed
		if (ndxJlstConditionSelector == ndx)
			return;

		// If the index is different, then we must update the local copies
		// Remember the new index
		ndxJlstConditionSelector = ndx;

		// Establish the local copies for the Name field
		strSelectedConditionName = lstConditionList.getCondition(ndx).getName();
		strLocalConditionName = strSelectedConditionName;
		fldConditionName.setText(strLocalConditionName);

		// Establish the local copies for the Description field
		strSelectedConditionDesc = lstConditionList.getCondition(ndx).getDesc();
		strLocalConditionDesc = strSelectedConditionDesc;
		fldConditionDesc.setText(strLocalConditionDesc);

		lglLocalSelectedConditionState = lstConditionList.getCondition(ndx)
				.getState();
		lglConditionState = lglLocalSelectedConditionState;
		if (lglConditionState) {
			jcbConditionState.getSelectionModel().select(0);
		} else {
			jcbConditionState.getSelectionModel().select(1);
		}

		// Changing of these fields may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkConditionButtons();
	}

	public void ConditionAction() {
		lglConditionState = jcbConditionState.getSelectionModel()
				.getSelectedIndex() == 0;
	}

	public void rtnCheckFldConditionsNewName() {
		strLocalConditionName = fldConditionName.getText();
		checkConditionButtons();
	}

	public void rtnCheckFldConditionsNewDesc() {
		strLocalConditionDesc = fldConditionDesc.getText();
		checkConditionButtons();
	}

	public void performConditionsSave() {
		lstConditionList
				.replaceCondition(ndxJlstConditionSelector,
						strLocalConditionName, strLocalConditionDesc,
						lglConditionState);
		commonUpdateAfterAConditionInsert();
	}

	public void commonUpdateAfterAConditionInsert() {
		// The list has changed so we need to build a new list.
		lstConditionsSelectListSource = lstConditionList.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglConditionListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstConditionsSelectListSource);
		jlstConditions.setItems(items);
		lglConditionListIsBeingUpdated = false;

		// To insure the just updated item remains selected, we must issue this
		// command
		jlstConditions.getSelectionModel().select(ndxJlstConditionSelector);

		// We also need to updated the local copies of the selected attribute
		strSelectedConditionName = strLocalConditionName;
		strSelectedConditionDesc = strLocalConditionDesc;
		lglLocalSelectedConditionState = lglConditionState;

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkConditionButtons();
	}

	private void checkConditionButtons() {

		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the exception of the enabled status for
		// the single large Insert, the Insert Above, and the Insert Below
		// buttons
		// which are processed in the second step below.

		// This is the first step in the process.
		int numConditions = lstConditionList.getNumberOfConditions();
		if (numConditions == 0) {
			// If the list is empty, only one insert button is visible as well
			// as
			// the save and delete buttons. The save and delete should be
			// disabled
			// The semantics of the enabled and disabled status of the Insert
			// buttons is more complex, so it is handled at the end of this
			// method.
			btnConditionsNew.setVisible(true);
			btnConditionsNewAbove.setDisable(true);
			btnConditionsNewAbove.setVisible(false);
			btnConditionsNewBelow.setDisable(true);
			btnConditionsNewBelow.setVisible(false);
			btnConditionsDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for inserting below the selected
			// item.
			// So we start by making the insert above and below buttons visible
			// and make the single insert button invisible and disabled.
			btnConditionsNewAbove.setVisible(true);
			btnConditionsNewBelow.setVisible(true);
			btnConditionsNew.setVisible(false);
			btnConditionsNew.setDisable(true);

			if (ndxJlstConditionSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled. (It is always visible.)
				btnConditionsDelete.setDisable(false);
				if (strSelectedConditionName.equals(strLocalConditionName)
						&& strSelectedConditionDesc
								.equals(strLocalConditionDesc)
						&& lglLocalSelectedConditionState == lglConditionState) {
					// The list is not empty, one item in the list is selected,
					// and both the name and description input fields match
					// the fields stored in the data structure, so there is
					// no reason to perform a Save operation and therefore that
					// button is visible but disabled.
					btnConditionsSave.setDisable(true);
				} else {
					// The list is not empty, one item in the list is selected,
					// but there is a a difference between the name and
					// description
					// input fields and the fields stored in the data structure,
					// so there may be a need to perform a Save operation and
					// therefore the button is visible and enabled.
					btnConditionsSave.setDisable(false);
				}
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are disabled (they are always
				// visible) regardless of what is in the input fields fore the
				// name and the description.
				btnConditionsDelete.setDisable(true);
				btnConditionsSave.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two
			// or more items in the list and one item is selected
			if (numConditions < 2 || ndxJlstConditionSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected
				// neither of the two buttons should be enabled
				btnConditionsMvUp.setDisable(true);
				btnConditionsMvDn.setDisable(true);

			} else if (ndxJlstConditionSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnConditionsMvUp.setDisable(true);
				btnConditionsMvDn.setDisable(false);

			} else if (ndxJlstConditionSelector == numConditions - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnConditionsMvUp.setDisable(false);
				btnConditionsMvDn.setDisable(true);

			} else {
				// Otherwise both options are valid.
				btnConditionsMvUp.setDisable(false);
				btnConditionsMvDn.setDisable(false);
			}
		}

		// The remaining code is about the enabled or disabled status of the
		// insert buttons.
		// Visibility has been handled above as has the enabled status for
		// Delete and Save.
		// This code deals with the visibility of the three insert buttons.
		if (lstConditionList.getNumberOfConditions() == 0
				&& strLocalConditionName.length() == 0) {
			// The list is empty and the user input name field is empty, so the
			// single
			// Insert button is visible, but it is disabled. The two insert
			// buttons were
			// disabled above and their visibility was handled above.
			btnConditionsNew.setDisable(true);
		} else if (lstConditionList.getNumberOfConditions() == 0
				&& strLocalConditionName.length() > 0) {
			// The list is empty, but the user input name field is not, so the
			// single
			// Insert button is visible and enabled. The two Insert buttons are
			// not visible
			// (from above) and are disabled.
			btnConditionsNew.setDisable(false);
		} else if (lstConditionList.getNumberOfConditions() > 0
				&& strLocalConditionName.length() == 0) {
			// The list is not empty, but the user input name field is empty, so
			// the two
			// Insert buttons are visible and enabled. The single Insert button
			// is not visible
			// and disabled from above).
			btnConditionsNewAbove.setDisable(true);
			btnConditionsNewBelow.setDisable(true);
		} else if (lstConditionList.getNumberOfConditions() > 0
				&& strLocalConditionName.length() > 0) {
			// The list is not empty and the user input name field is not empty,
			// so the status of
			// the two Insert buttons in terms of enabled or not is determined
			// by where or not
			// an item in the list has been selected. We do know that the single
			// insert was
			// made not visible and disabled above, so no need to do it here.
			if (ndxJlstConditionSelector == -1) {
				// No item is selected, so it is not possible to insert above or
				// below it.
				btnConditionsNewAbove.setDisable(true);
				btnConditionsNewBelow.setDisable(true);
			} else {
				// An item is selected, so it is possible to insert above or
				// below it
				btnConditionsNewAbove.setDisable(false);
				btnConditionsNewBelow.setDisable(false);
			}
		}
	}
	
	/**************************************************************************************************************
	 * 
	 * 
	 * Effort categories
	 */
	
	/************************************ Effort categories functions ******************************************************/

	private void showEffortCategoryArtifact() {
		msgEffortCategoryArtifact.setVisible(true);
		jlstEffortCategoryArtifact.setVisible(true);
		msgEffortCategoryNewArtifact.setVisible(true);
		jcbEffortCategoryNewArtifact.setVisible(true);
		btnEffortCategoryArtNew.setVisible(true);
		btnEffortCategoryArtAbove.setVisible(true);
		btnEffortCategoryArtBelow.setVisible(true);
		btnEffortCategoryArtMvUp.setVisible(true);
		btnEffortCategoryArtMvDn.setVisible(true);
		btnEffortCategoryArtDelete.setVisible(true);
	}

	private void hideEffortCategoryArtifact() {
		msgEffortCategoryArtifact.setVisible(false);
		jlstEffortCategoryArtifact.setVisible(false);
		msgEffortCategoryNewArtifact.setVisible(false);
		jcbEffortCategoryNewArtifact.setVisible(false);
		btnEffortCategoryArtNew.setVisible(false);
		btnEffortCategoryArtAbove.setVisible(false);
		btnEffortCategoryArtBelow.setVisible(false);
		btnEffortCategoryArtMvUp.setVisible(false);
		btnEffortCategoryArtMvDn.setVisible(false);
		btnEffortCategoryArtDelete.setVisible(false);
	}

	private void showEffortCategoryPlan() {
		msgEffortCategoryPlan.setVisible(true);
		jlstEffortCategoryPlan.setVisible(true);
		msgEffortCategoryNewPlan.setVisible(true);
		jcbEffortCategoryNewPlan.setVisible(true);
		btnEffortCategoryPlanNew.setVisible(true);
		btnEffortCategoryPlanAbove.setVisible(true);
		btnEffortCategoryPlanBelow.setVisible(true);
		btnEffortCategoryPlanMvUp.setVisible(true);
		btnEffortCategoryPlanMvDn.setVisible(true);
		btnEffortCategoryPlanDelete.setVisible(true);
	}

	private void hideEffortCategoryPlan() {
		msgEffortCategoryPlan.setVisible(false);
		jlstEffortCategoryPlan.setVisible(false);
		msgEffortCategoryNewPlan.setVisible(false);
		jcbEffortCategoryNewPlan.setVisible(false);
		btnEffortCategoryPlanNew.setVisible(false);
		btnEffortCategoryPlanAbove.setVisible(false);
		btnEffortCategoryPlanBelow.setVisible(false);
		btnEffortCategoryPlanMvUp.setVisible(false);
		btnEffortCategoryPlanMvDn.setVisible(false);
		btnEffortCategoryPlanDelete.setVisible(false);
	}

	private void showEffortCategoryInterruption() {
		msgEffortCategoryInterruption.setVisible(true);
		jlstEffortCategoryInterruption.setVisible(true);
		msgEffortCategoryNewInterruption.setVisible(true);
		jcbEffortCategoryNewInterruption.setVisible(true);
		btnEffortCategoryInterruptionNew.setVisible(true);
		btnEffortCategoryInterruptionAbove.setVisible(true);
		btnEffortCategoryInterruptionBelow.setVisible(true);
		btnEffortCategoryInterruptionMvUp.setVisible(true);
		btnEffortCategoryInterruptionMvDn.setVisible(true);
		btnEffortCategoryInterruptionDelete.setVisible(true);
	}

	private void hideEffortCategoryInterruption() {
		msgEffortCategoryInterruption.setVisible(false);
		jlstEffortCategoryInterruption.setVisible(false);
		msgEffortCategoryNewInterruption.setVisible(false);
		jcbEffortCategoryNewInterruption.setVisible(false);
		btnEffortCategoryInterruptionNew.setVisible(false);
		btnEffortCategoryInterruptionAbove.setVisible(false);
		btnEffortCategoryInterruptionBelow.setVisible(false);
		btnEffortCategoryInterruptionMvUp.setVisible(false);
		btnEffortCategoryInterruptionMvDn.setVisible(false);
		btnEffortCategoryInterruptionDelete.setVisible(false);
	}

	private void showEffortCategoryDefect() {
		msgEffortCategoryDefect.setVisible(true);
		jlstEffortCategoryDefect.setVisible(true);
		msgEffortCategoryNewDefect.setVisible(true);
		jcbEffortCategoryNewDefect.setVisible(true);
		btnEffortCategoryDefectNew.setVisible(true);
		btnEffortCategoryDefectAbove.setVisible(true);
		btnEffortCategoryDefectBelow.setVisible(true);
		btnEffortCategoryDefectMvUp.setVisible(true);
		btnEffortCategoryDefectMvDn.setVisible(true);
		btnEffortCategoryDefectDelete.setVisible(true);
	}

	private void hideEffortCategoryDefect() {
		msgEffortCategoryDefect.setVisible(false);
		jlstEffortCategoryDefect.setVisible(false);
		msgEffortCategoryNewDefect.setVisible(false);
		jcbEffortCategoryNewDefect.setVisible(false);
		btnEffortCategoryDefectNew.setVisible(false);
		btnEffortCategoryDefectAbove.setVisible(false);
		btnEffortCategoryDefectBelow.setVisible(false);
		btnEffortCategoryDefectMvUp.setVisible(false);
		btnEffortCategoryDefectMvDn.setVisible(false);
		btnEffortCategoryDefectDelete.setVisible(false);
	}

	// this method is called when the change in effort category happens the
	// index is also recorded.
	public void performEffortCategoryOptionFunction() {
		int ndx = jcbEffortCategoryOption.getSelectionModel()
				.getSelectedIndex();
		checkEffortCategoryButtons(); 
		if (ndx == ndxLocalEffortCategoryState) {
			return;
		}
		ndxLocalEffortCategoryState = ndx;
		switch (ndx) {
		// Artifact Effort Category
		case 0:
			ndxJlstEffortCategoryArtifactSelector = -1;
			wakeUpEffortCategoriesArtifactList();
			break;
		// Plan Effort Category
		case 1:
			ndxJlstEffortCategoryPlanSelector = -1;
			wakeUpEffortCategoriesPlanList();
			break;
		// Interruption Effort Category
		case 2:
			ndxJlstEffortCategoryInterruptionSelector = -1;
			wakeUpEffortCategoriesInterruptionList();
			break;
		// Defect Effort Category
		case 3:
			ndxJlstEffortCategoryDefectSelector = -1;
			wakeUpEffortCategoriesDefectList();
			break;
		// Other Effort Category
		default:
			wakeUpEffortCategoriesOther();
			break;
		}
		checkEffortCategoryButtons();
	}

	/*****
	 * This method is called each time the EffortCategories Tab is activated.
	 */

	public void performWakeUpEffortCategoriesPane() {

		System.out.println("Wake up the EffortCategories tabbed pane");
		lstEffortCategoriesSelectListSource = lstEffortCategoryList
				.buildSelectList();
		lglEffortCategoryListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoriesSelectListSource);
		jlstEffortCategories.setItems(items);
		lglEffortCategoryListIsBeingUpdated = false;
		if (ndxJlstEffortCategorySelector == -1) {
			jlstEffortCategories.getSelectionModel().clearSelection();
			// jlstEffortCategories.setItems(items1);
		} else
			jlstEffortCategories.getSelectionModel().select(
					ndxJlstEffortCategorySelector);

		switch (ndxLocalEffortCategoryState) {
		case 0:
			wakeUpEffortCategoriesArtifactList();
			break;

		case 1:
			wakeUpEffortCategoriesPlanList();
			break;

		case 2:
			wakeUpEffortCategoriesInterruptionList();
			break;

		case 3:
			wakeUpEffortCategoriesDefectList();
			break;

		default:
			wakeUpEffortCategoriesOther();
			break;
		}
		checkEffortCategoryButtons();

	}

	// this method is called when the option choosen is artifact.
	private void wakeUpEffortCategoriesArtifactList() {
		// to add artifacts which are in the local effort catogry artifact list.
		lstLocalEffortCategoryArtifactList.validateMembers(lstArtifactList);
		lstEffortCategoryArtifactSelectListSource = lstLocalEffortCategoryArtifactList
				.buildSelectList();
		// mofifieng the gui.
		lglEffortCategoryArtifactListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoryArtifactSelectListSource);
		jlstEffortCategoryArtifact.setItems(items);
		lglEffortCategoryArtifactListIsBeingUpdated = false;
		cmbEffortCategoryNewArtifactSelectList = lstArtifactList
				.buildSubList(lstLocalEffortCategoryArtifactList);
		cmbModelEffortCategoryNewArtifact = cmbEffortCategoryNewArtifactSelectList
				.buildSelectList();
		ObservableList<String> items1 = FXCollections
				.observableArrayList(cmbModelEffortCategoryNewArtifact);
		// the combo box is also modified/
		jcbEffortCategoryNewArtifact.setItems(items1);
		if (cmbModelEffortCategoryNewArtifact.length == 0) {
			jcbEffortCategoryNewArtifact.getSelectionModel().select(-1);

			ndxCmbEffortCategoryNewArtifactSelector = -1;
		} else {
			jcbEffortCategoryNewArtifact.getSelectionModel().select(0);
			ndxCmbEffortCategoryNewArtifactSelector = 0;
		}
		showEffortCategoryArtifact();
		hideEffortCategoryPlan();
		hideEffortCategoryInterruption();
		hideEffortCategoryDefect();
		checkEffortCategoryArtifactButtons();

	}

	private void wakeUpEffortCategoriesPlanList() {
		// to add plan which are in the local effort catogry plan list.
		lstLocalEffortCategoryPlanList.validateMembers(lstPlanList);
		lstEffortCategoryPlanSelectListSource = lstLocalEffortCategoryPlanList
				.buildSelectList();
		// mofifieng the gui by building the select list.
		lglEffortCategoryPlanListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoryPlanSelectListSource);
		jlstEffortCategoryPlan.setItems(items);
		lglEffortCategoryPlanListIsBeingUpdated = false;
		cmbEffortCategoryNewPlanSelectList = lstPlanList
				.buildSubList(lstLocalEffortCategoryPlanList);
		cmbModelEffortCategoryNewPlan = cmbEffortCategoryNewPlanSelectList
				.buildSelectList();
		ObservableList<String> items1 = FXCollections
				.observableArrayList(cmbModelEffortCategoryNewPlan);
		jcbEffortCategoryNewPlan.setItems(items1);
		if (cmbModelEffortCategoryNewPlan.length == 0) {
			jcbEffortCategoryNewPlan.getSelectionModel().select(-1);
			ndxCmbEffortCategoryNewPlanSelector = -1;
		} else {
			jcbEffortCategoryNewPlan.getSelectionModel().select(0);
			ndxCmbEffortCategoryNewPlanSelector = 0;
		}
		hideEffortCategoryArtifact();
		showEffortCategoryPlan();
		hideEffortCategoryInterruption();
		hideEffortCategoryDefect();
		checkEffortCategoryPlanButtons();

	}

	private void wakeUpEffortCategoriesInterruptionList() {
		// to add Interruption which are in the local effort catogry
		// Interruption list.
		lstLocalEffortCategoryInterruptionList
				.validateMembers(lstInterruptionList);
		lstEffortCategoryInterruptionSelectListSource = lstLocalEffortCategoryInterruptionList
				.buildSelectList();
		lglEffortCategoryInterruptionListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoryInterruptionSelectListSource);
		jlstEffortCategoryInterruption.setItems(items);
		lglEffortCategoryInterruptionListIsBeingUpdated = false;
		// here we modify the GUI by setting the itemms in the list view
		cmbEffortCategoryNewInterruptionSelectList = lstInterruptionList
				.buildSubList(lstLocalEffortCategoryInterruptionList);
		cmbModelEffortCategoryNewInterruption = cmbEffortCategoryNewInterruptionSelectList
				.buildSelectList();
		ObservableList<String> items1 = FXCollections
				.observableArrayList(cmbModelEffortCategoryNewInterruption);
		jcbEffortCategoryNewInterruption.setItems(items1);
		if (cmbModelEffortCategoryNewInterruption.length == 0) {
			jcbEffortCategoryNewInterruption.getSelectionModel().select(-1);
			ndxCmbEffortCategoryNewInterruptionSelector = -1;
		} else {
			jcbEffortCategoryNewInterruption.getSelectionModel().select(0);
			ndxCmbEffortCategoryNewInterruptionSelector = 0;
		}
		hideEffortCategoryArtifact();
		hideEffortCategoryPlan();
		showEffortCategoryInterruption();
		hideEffortCategoryDefect();
		checkEffortCategoryInterruptionButtons();

	}

	private void wakeUpEffortCategoriesDefectList() {
		// to add Defect which are in the local effort catogry Defect list.
		lstLocalEffortCategoryDefectList.validateMembers(lstDefectList);
		lstEffortCategoryDefectSelectListSource = lstLocalEffortCategoryDefectList
				.buildSelectList();
		lglEffortCategoryDefectListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoryDefectSelectListSource);
		jlstEffortCategoryDefect.setItems(items);
		lglEffortCategoryDefectListIsBeingUpdated = false;
		cmbEffortCategoryNewDefectSelectList = lstDefectList
				.buildSubList(lstLocalEffortCategoryDefectList);
		cmbModelEffortCategoryNewDefect = cmbEffortCategoryNewDefectSelectList
				.buildSelectList();
		ObservableList<String> items1 = FXCollections
				.observableArrayList(cmbModelEffortCategoryNewDefect);
		jcbEffortCategoryNewDefect.setItems(items1);
		if (cmbModelEffortCategoryNewDefect.length == 0) {
			jcbEffortCategoryNewDefect.getSelectionModel().select(-1);
			ndxCmbEffortCategoryNewDefectSelector = -1;
		} else {
			jcbEffortCategoryNewDefect.getSelectionModel().select(0);
			ndxCmbEffortCategoryNewDefectSelector = 0;
		}
		hideEffortCategoryArtifact();
		hideEffortCategoryPlan();
		hideEffortCategoryInterruption();
		showEffortCategoryDefect();
		checkEffortCategoryDefectButtons();

	}

	private void wakeUpEffortCategoriesOther() {
		// we hide every thing when this option is selected.
		hideEffortCategoryArtifact();
		hideEffortCategoryPlan();
		hideEffortCategoryInterruption();
		hideEffortCategoryDefect();

	}

	public void performEffortCategoriesNew() {
		// This method is only used for inserting the first item into an empty
		// Listview. (Once
		// the Listview has an item in it, the user must specify whether to
		// insert above or
		// below the selected list item.)
		lstEffortCategoryList.addEffortCategory(strLocalEffortCategoryName,
				strLocalEffortCategoryDesc, ndxLocalEffortCategoryState,
				new ArtifactListController(lstLocalEffortCategoryArtifactList),
				new PlanListController(lstLocalEffortCategoryPlanList),
				new InterruptionListController(
						lstLocalEffortCategoryInterruptionList),
				new DefectListController(lstLocalEffortCategoryDefectList));
		// After an insert, the just inserted item is the selected item. Since
		// the list was
		// empty just before this insert, there is now just one item in the list
		// and that item's
		// index is zero. (Remember this is Java and the first item in a Java
		// list has an index
		// of zero.
		// These commands set the first item in the list to be selected

		ndxJlstEffortCategorySelector = 0;
		// Update the GUI to reflect the just made change

		commonUpdateAfterAnEffortCategoryInsert();

	}

	/*****
	 * Insert the new EffortCategory *above* the current selected EffortCategory
	 * in the JList.
	 */

	public void performEffortCategoriesNewAbove() {

		// Create a new Effort Category and insert it above the current selected
		// Effort Category.
		// Using the lstEffortCategoriesSelector, as is, is what causes the
		// above to occur. Otherwise
		// this is exactly the same as the performEffortCategoriesNewBelow
		// method
		lstEffortCategoryList.addEffortCategory(ndxJlstEffortCategorySelector,
				strLocalEffortCategoryName, strLocalEffortCategoryDesc,
				ndxLocalEffortCategoryState, new ArtifactListController(
						lstLocalEffortCategoryArtifactList),
				new PlanListController(lstLocalEffortCategoryPlanList),
				new InterruptionListController(
						lstLocalEffortCategoryInterruptionList),
				new DefectListController(lstLocalEffortCategoryDefectList));

		// We want the just inserted item to be the currently selected item, so
		// we have to update
		// the selector to be this new item. Since we inserted above, the index
		// of the selected
		// item will be the same.

		// Update the GUI to reflect the just made change
		commonUpdateAfterAnEffortCategoryInsert();

	}

	public void performEffortCategoriesNewBelow() {
		// Create a new artifact and insert it below the current selected
		// artifact. The "+1"
		// for the lstEffortCategoriesSelector is what causes the below to
		// occur. Otherwise this is
		// exactly the same as the performEffortCategoriesNewAbove method
		lstEffortCategoryList.addEffortCategory(
				ndxJlstEffortCategorySelector + 1, strLocalEffortCategoryName,
				strLocalEffortCategoryDesc, ndxLocalEffortCategoryState,
				new ArtifactListController(lstLocalEffortCategoryArtifactList),
				new PlanListController(lstLocalEffortCategoryPlanList),
				new InterruptionListController(
						lstLocalEffortCategoryInterruptionList),
				new DefectListController(lstLocalEffortCategoryDefectList));
		ndxJlstEffortCategorySelector++;
		// We want the just inserted item to be the currently selected item, so
		// we have to update
		// the selector to be one more than it was.
		// Update the GUI to reflect the just made change
		commonUpdateAfterAnEffortCategoryInsert();

	}

	int ndxJlstEffortCategorydeleteSelector = 0;

	public void performEffortCategoriesSave() {
		// We know that this method can only be called when the button is
		// enabled and that means
		// that an artifact was selected and the input fields do not match what
		// has been stored.
		// Therefore, we need to update the stored list to match this updated
		// artifact. We
		// blindly update both the name and the description, even if only one
		// has been changed.
		performEffortCategoriesDelete();

		jlstEffortCategories.getSelectionModel().select(
				ndxJlstEffortCategorydeleteSelector - 1);
		ndxJlstEffortCategorySelector = ndxJlstEffortCategorydeleteSelector - 1;
		performEffortCategoriesNewBelow();

	}

	public void commonUpdateAfterAnEffortCategoryInsert() {
		// We can't just change the List view as that causes a new cascade of
		// events which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the List view,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lstEffortCategoriesSelectListSource = lstEffortCategoryList
				.buildSelectList();
		lglEffortCategoryListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoriesSelectListSource);
		jlstEffortCategories.setItems(items);
		lglEffortCategoryListIsBeingUpdated = false;
		// To insure the just updated item remains selected, we must issue this
		// command
		jlstEffortCategories.getSelectionModel().select(
				ndxJlstEffortCategorySelector);
		strSelectedEffortCategoryName = strLocalEffortCategoryName;
		strSelectedEffortCategoryDesc = strLocalEffortCategoryDesc;
		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.

		checkEffortCategoryButtons();

	}

	public void performEffortCategoryMvUp() {
		lstEffortCategoryList
				.moveEffortCategoryUp(ndxJlstEffortCategorySelector);

		// Keep the same item selected
		ndxJlstEffortCategorySelector--;

		// Update the GUI to reflect the just made change
		commonUpdateAfterAnEffortCategoryMove();

	}

	public void performEffortCategoryMvDn() {
		// Move the artifact down in the actual list
		lstEffortCategoryList
				.moveEffortCategoryDn(ndxJlstEffortCategorySelector);
		// Keep the same item selected
		ndxJlstEffortCategorySelector++;
		// Update the GUI to reflect the just made change
		commonUpdateAfterAnEffortCategoryMove();

	}

	public void commonUpdateAfterAnEffortCategoryMove() {

		lstEffortCategoriesSelectListSource = lstEffortCategoryList
				.buildSelectList();
		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglEffortCategoryListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoriesSelectListSource);
		jlstEffortCategories.setItems(items);
		lglEffortCategoryListIsBeingUpdated = false;
		jlstEffortCategories.getSelectionModel().select(
				ndxJlstEffortCategorySelector);
		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkEffortCategoryButtons();

	}

	public void performEffortCategoriesDelete() {
		// We know that it is only possible to get here when an artifact in the
		// list has been selected, or the button would have been disabled. So no
		// checking is needed.
		lstEffortCategoryList
				.deleteEffortCategory(ndxJlstEffortCategorySelector);
		// Now that the artifact has been deleted, we need to clean up the UI
		// to match this state.

		// After a delete, no item is selected, so that is what these statements
		// do
		ndxJlstEffortCategorydeleteSelector = ndxJlstEffortCategorySelector;
		ndxJlstEffortCategorySelector = -1;
		jlstEffortCategories.getSelectionModel().clearSelection();

		lstEffortCategoriesSelectListSource = lstEffortCategoryList
				.buildSelectList();
		lglEffortCategoryListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoriesSelectListSource);
		jlstEffortCategories.setItems(items);

		lglEffortCategoryListIsBeingUpdated = false;
		strSelectedEffortCategoryName = "";
		strSelectedEffortCategoryDesc = "";

		checkEffortCategoryButtons();
	}

	public void performEffortCategoryArtNew() {
		// This method is only used for inserting the first item into an empty
		// JList. (Once the JList has an
		// item in it, the user must specify whether to insert above or below
		// the selected list item.)

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbEffortCategoryNewArtifactSelector = jcbEffortCategoryNewArtifact
				.getSelectionModel().getSelectedIndex();
		Artifact toBeInserted = cmbEffortCategoryNewArtifactSelectList
				.extractArtifact(ndxCmbEffortCategoryNewArtifactSelector);
		lstLocalEffortCategoryArtifactList.addArtifact(toBeInserted);
		// Since there is only one item in the list now, we must highlight the
		// first one as the
		// selected item
		ndxJlstEffortCategoryArtifactSelector = 0;
		// Update the GUI to reflect the change
		commonUpdateAfterAnEffortCategoryArtifactInsert();

	}

	public void performEffortCategoryArtNewAbove() {
		// This method is only used for inserting above a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbEffortCategoryNewArtifactSelector = jcbEffortCategoryNewArtifact
				.getSelectionModel().getSelectedIndex();

		Artifact toBeInserted = cmbEffortCategoryNewArtifactSelectList
				.extractArtifact(ndxCmbEffortCategoryNewArtifactSelector);
		lstLocalEffortCategoryArtifactList.addArtifact(
				ndxJlstEffortCategoryArtifactSelector, toBeInserted);
		// We have inserted above the previous current item, so that item will
		// be pushed down one and
		// the just inserted item will take its old place. Therefore, the index
		// of the current item
		// stays the same.

		// Update the GUI to reflect the change

		commonUpdateAfterAnEffortCategoryArtifactInsert();

	}

	public void performEffortCategoryArtNewBelow() {
		// This method is only used for inserting below a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbEffortCategoryNewArtifactSelector = jcbEffortCategoryNewArtifact
				.getSelectionModel().getSelectedIndex(); // Get the index of the
		// artifact
		Artifact toBeInserted = cmbEffortCategoryNewArtifactSelectList
				.extractArtifact(ndxCmbEffortCategoryNewArtifactSelector);
		lstLocalEffortCategoryArtifactList.addArtifact(
				ndxJlstEffortCategoryArtifactSelector + 1, toBeInserted);
		// We have inserted below the previous current item, so this just
		// inserted item will have an index that is
		// one larger that the previous selected item. Therefore, the index of
		// the current item is incremented.
		// We changed the list, so we must tell the GUI component which item is
		// selected now.
		ndxJlstEffortCategoryArtifactSelector++;
		commonUpdateAfterAnEffortCategoryArtifactInsert();

	}

	int flag = 1;

	public void commonUpdateAfterAnEffortCategoryArtifactInsert() {
		// The Artifact used list has changed, so we need to rebuild the list
		// that is displayed
		lstEffortCategoryArtifactSelectListSource = lstLocalEffortCategoryArtifactList
				.buildSelectList();
		// Since we are updating the list, we need to ignore the list change
		// events

		lglEffortCategoryArtifactListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoryArtifactSelectListSource);
		jlstEffortCategoryArtifact.setItems(items);
		lglEffortCategoryArtifactListIsBeingUpdated = false;
		// We changed the list, so we must tell the GUI component which item is
		// selected now.
		jlstEffortCategoryArtifact.getSelectionModel().select(
				ndxJlstEffortCategoryArtifactSelector);
		// The ComboBox list has also changed (one fewer item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Artifacts Used list.
		cmbEffortCategoryNewArtifactSelectList = lstArtifactList
				.buildSubList(lstLocalEffortCategoryArtifactList);
		cmbModelEffortCategoryNewArtifact = cmbEffortCategoryNewArtifactSelectList
				.buildSelectList();

		ObservableList<String> items1 = FXCollections
				.observableArrayList(cmbModelEffortCategoryNewArtifact);
		jcbEffortCategoryNewArtifact.setItems(items1);
		// Since the size of the ComboBox is now smaller, we need to check the
		// index of the current
		// selected item to be sure that it is still in a valid range, and fix
		// it if not. (Remember,
		// it could now be empty!)

		flag = 0;
		if (ndxCmbEffortCategoryNewArtifactSelector >= cmbEffortCategoryNewArtifactSelectList
				.getNumberOfArtifacts())
			ndxCmbEffortCategoryNewArtifactSelector = cmbEffortCategoryNewArtifactSelectList
					.getNumberOfArtifacts() - 1;
		flag = 1;
		jcbEffortCategoryNewArtifactAction();

		jcbEffortCategoryNewArtifact.getSelectionModel().select(
				ndxCmbEffortCategoryNewArtifactSelector);

		checkEffortCategoryButtons();
	}

	public void performEffortCategoryArtMvUp() {
		// This method is only used for moving a selected item up a non-empty
		// JList when there is at least
		// one element above it.

		lstLocalEffortCategoryArtifactList
				.moveArtifactUp(ndxJlstEffortCategoryArtifactSelector);
		// We moved it up, so to keep the same item selected, the index must be
		// reduced by one

		ndxJlstEffortCategoryArtifactSelector--;

		commonUpdateAfterAnEffortCategoryArtifactMove();

	}

	public void performEffortCategoryArtMvDn() {
		// This method is only used for moving a selected item down a non-empty
		// JList when there is at least
		// one element below it.
		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalEffortCategoryArtifactList
				.moveArtifactDn(ndxJlstEffortCategoryArtifactSelector);
		// We moved it down, so to keep the same item selected, the index must
		// be increased by one
		ndxJlstEffortCategoryArtifactSelector++;

		commonUpdateAfterAnEffortCategoryArtifactMove();
	}

	public void commonUpdateAfterAnEffortCategoryArtifactMove() {
		// The list has changed so we need to build a new list.
		lstEffortCategoryArtifactSelectListSource = lstLocalEffortCategoryArtifactList
				.buildSelectList();

		lglEffortCategoryArtifactListIsBeingUpdated = true;
		// Since we are updating the list, we need to ignore the list change
		// events
		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoryArtifactSelectListSource);
		jlstEffortCategoryArtifact.setItems(items);

		lglEffortCategoryArtifactListIsBeingUpdated = false;
		// Given the new list, this command will once again select the item that
		// was moved
		jlstEffortCategoryArtifact.getSelectionModel().select(
				ndxJlstEffortCategoryArtifactSelector);
		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkEffortCategoryButtons();
	}

	public void performEffortCategoryArtDelete() {
		// This method is only used for removing a selected item from a
		// non-empty JList.

		// This statements actually does the delete. The rest are about
		// adjusting the GUI state to match.
		lstLocalEffortCategoryArtifactList
				.deleteArtifact(ndxJlstEffortCategoryArtifactSelector);
		// Now that the artifact has been deleted, we need to clean up the UI to
		// match this state.

		// The list has changed so we need to build a new list.
		lstEffortCategoryArtifactSelectListSource = lstLocalEffortCategoryArtifactList
				.buildSelectList();

		lglEffortCategoryArtifactListIsBeingUpdated = true;

		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoryArtifactSelectListSource);
		jlstEffortCategoryArtifact.setItems(items);

		lglEffortCategoryArtifactListIsBeingUpdated = false;
		// We have delete the current item from the list, so no item in the list
		// will be selected now.
		// Therefore, we clear the index and the selection from the GUI
		// component

		ndxJlstEffortCategoryArtifactSelector = -1;

		jlstEffortCategoryArtifact.getSelectionModel().clearSelection();
		// The ComboBox list has also changed (one more item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Artifacts Used list.

		cmbEffortCategoryNewArtifactSelectList = lstArtifactList
				.buildSubList(lstLocalEffortCategoryArtifactList);

		cmbModelEffortCategoryNewArtifact = cmbEffortCategoryNewArtifactSelectList
				.buildSelectList();

		ObservableList<String> items1 = FXCollections
				.observableArrayList(cmbModelEffortCategoryNewArtifact);

		jcbEffortCategoryNewArtifact.setItems(items1);
		// Since the size of the ComboBox is now larger, we know that there is
		// at least one item in it,
		// so we will default the ComboBox to the first item in the list.

		// Set the index to be the first item in the list

		ndxCmbEffortCategoryNewArtifactSelector = 0;

		jcbEffortCategoryNewArtifact.getSelectionModel().select(
				ndxCmbEffortCategoryNewArtifactSelector);

		// These changes may cause changes in the state of the five buttons for
		// the Artifacts Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkEffortCategoryButtons();

	}

	public void performEffortCategoryPlanNew() {
		// This method is only used for inserting the first item into an empty
		// JList. (Once the JList has an
		// item in it, the user must specify whether to insert above or below
		// the selected list item.)

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.

		ndxCmbEffortCategoryNewPlanSelector = jcbEffortCategoryNewPlan
				.getSelectionModel().getSelectedIndex();

		Plan toBeInserted = cmbEffortCategoryNewPlanSelectList
				.extractPlan(ndxCmbEffortCategoryNewPlanSelector);

		lstLocalEffortCategoryPlanList.addPlan(toBeInserted);

		// Since there is only one item in the list now, we must highlight the
		// first one as the
		// selected item

		ndxJlstEffortCategoryPlanSelector = 0;

		commonUpdateAfterAnEffortCategoryPlanInsert();

	}

	public void performEffortCategoryPlanNewAbove() {
		// This method is only used for inserting above a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbEffortCategoryNewPlanSelector = jcbEffortCategoryNewPlan
				.getSelectionModel().getSelectedIndex();

		Plan toBeInserted = cmbEffortCategoryNewPlanSelectList
				.extractPlan(ndxCmbEffortCategoryNewPlanSelector);

		lstLocalEffortCategoryPlanList.addPlan(
				ndxJlstEffortCategoryPlanSelector, toBeInserted);
		// We have inserted above the previous current item, so that item will
		// be pushed down one and the just
		// inserted item will take its old place. Therefore, the index of the
		// current item stays the same.

		// Update the GUI to reflect the change

		commonUpdateAfterAnEffortCategoryPlanInsert();
	}

	public void performEffortCategoryPlanNewBelow() {
		// This method is only used for inserting below a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbEffortCategoryNewPlanSelector = jcbEffortCategoryNewPlan
				.getSelectionModel().getSelectedIndex();

		Plan toBeInserted = cmbEffortCategoryNewPlanSelectList
				.extractPlan(ndxCmbEffortCategoryNewPlanSelector);
		lstLocalEffortCategoryPlanList.addPlan(
				ndxJlstEffortCategoryPlanSelector + 1, toBeInserted);
		// We have inserted below the previous current item, so this just
		// inserted item will have an index that is
		// one larger that the previous selected item. Therefore, the index of
		// the current item is incremented.
		// We changed the list, so we must tell the GUI component which item is
		// selected now.

		ndxJlstEffortCategoryPlanSelector++;

		commonUpdateAfterAnEffortCategoryPlanInsert();

	}

	int flag1 = 1;

	public void commonUpdateAfterAnEffortCategoryPlanInsert() {
		// The Plan used list has changed, so we need to rebuild the list that
		// is displayed
		lstEffortCategoryPlanSelectListSource = lstLocalEffortCategoryPlanList
				.buildSelectList();

		lglEffortCategoryPlanListIsBeingUpdated = true;
		// Update the GUI component for the Plan Used select list to display the
		// new list
		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoryPlanSelectListSource);
		jlstEffortCategoryPlan.setItems(items);

		lglEffortCategoryPlanListIsBeingUpdated = false;

		jlstEffortCategoryPlan.getSelectionModel().select(
				ndxJlstEffortCategoryPlanSelector);
		// The ComboBox list has also changed (one fewer item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Plans Used list.
		cmbEffortCategoryNewPlanSelectList = lstPlanList
				.buildSubList(lstLocalEffortCategoryPlanList);

		cmbModelEffortCategoryNewPlan = cmbEffortCategoryNewPlanSelectList
				.buildSelectList();

		ObservableList<String> items1 = FXCollections
				.observableArrayList(cmbModelEffortCategoryNewPlan);

		jcbEffortCategoryNewPlan.setItems(items1);

		flag1 = 0;

		if (ndxCmbEffortCategoryNewPlanSelector >= cmbEffortCategoryNewPlanSelectList
				.getNumberOfPlans())
			ndxCmbEffortCategoryNewPlanSelector = cmbEffortCategoryNewPlanSelectList
					.getNumberOfPlans() - 1;
		flag1 = 1;
		jcbEffortCategoryNewPlanAction();
		jcbEffortCategoryNewPlan.getSelectionModel().select(
				ndxCmbEffortCategoryNewPlanSelector);

		// These changes may cause changes in the state of the five buttons for
		// the Plans Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkEffortCategoryButtons();
	}

	public void performEffortCategoryPlanMvUp() {
		lstLocalEffortCategoryPlanList
				.movePlanUp(ndxJlstEffortCategoryPlanSelector);

		ndxJlstEffortCategoryPlanSelector--;

		commonUpdateAfterAnEffortCategoryPlanMove();

	}

	public void performEffortCategoryPlanMvDn() {
		lstLocalEffortCategoryPlanList
				.movePlanDn(ndxJlstEffortCategoryPlanSelector);

		ndxJlstEffortCategoryPlanSelector++;

		commonUpdateAfterAnEffortCategoryPlanMove();

	}

	public void commonUpdateAfterAnEffortCategoryPlanMove() {
		// The list has changed so we need to build a new list.
		lstEffortCategoryPlanSelectListSource = lstLocalEffortCategoryPlanList
				.buildSelectList();

		lglEffortCategoryPlanListIsBeingUpdated = true;

		ObservableList<String> items1 = FXCollections
				.observableArrayList(lstEffortCategoryPlanSelectListSource);
		jlstEffortCategoryPlan.setItems(items1);

		lglEffortCategoryPlanListIsBeingUpdated = false;
		// Given the new list, this command will once again select the item that
		// was moved
		jlstEffortCategoryPlan.getSelectionModel().select(
				ndxJlstEffortCategoryPlanSelector);
		// These changes may cause changes in the state of the five buttons for
		// the Plans Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkEffortCategoryButtons();

	}

	public void performEffortCategoryPlanDelete() {
		// This method is only used for removing a selected item from a
		// non-empty JList.

		// This statements actually does the delete. The rest are about
		// adjusting the GUI state to match.

		lstLocalEffortCategoryPlanList
				.deletePlan(ndxJlstEffortCategoryPlanSelector);

		lstEffortCategoryPlanSelectListSource = lstLocalEffortCategoryPlanList
				.buildSelectList();

		lglEffortCategoryPlanListIsBeingUpdated = true;

		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoryPlanSelectListSource);
		jlstEffortCategoryPlan.setItems(items);

		lglEffortCategoryPlanListIsBeingUpdated = false;

		ndxJlstEffortCategoryPlanSelector = -1;
		jlstEffortCategoryPlan.getSelectionModel().clearSelection();

		cmbEffortCategoryNewPlanSelectList = lstPlanList
				.buildSubList(lstLocalEffortCategoryPlanList);

		cmbModelEffortCategoryNewPlan = cmbEffortCategoryNewPlanSelectList
				.buildSelectList();

		ObservableList<String> items1 = FXCollections
				.observableArrayList(cmbModelEffortCategoryNewPlan);
		jcbEffortCategoryNewPlan.setItems(items1);

		ndxCmbEffortCategoryNewPlanSelector = 0;

		jcbEffortCategoryNewPlan.getSelectionModel().select(
				ndxCmbEffortCategoryNewPlanSelector);

		checkEffortCategoryButtons();

	}

	public void performEffortCategoryInterruptionNew() {
		// This method is only used for inserting the first item into an empty
		// JList. (Once the JList has an
		// item in it, the user must specify whether to insert above or below
		// the selected list item.)

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbEffortCategoryNewInterruptionSelector = jcbEffortCategoryNewInterruption
				.getSelectionModel().getSelectedIndex();

		Interruption toBeInserted = cmbEffortCategoryNewInterruptionSelectList
				.extractInterruption(ndxCmbEffortCategoryNewInterruptionSelector);
		// Since there is only one item in the list now, we must highlight the
		// first one as the
		// selected item
		ndxJlstEffortCategoryInterruptionSelector = 0;

		lstLocalEffortCategoryInterruptionList.addInterruption(toBeInserted);

		commonUpdateAfterAnEffortCategoryInterruptionInsert();

	}

	public void performEffortCategoryInterruptionNewAbove() {
		// This method is only used for inserting above a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbEffortCategoryNewInterruptionSelector = jcbEffortCategoryNewInterruption
				.getSelectionModel().getSelectedIndex();

		Interruption toBeInserted = cmbEffortCategoryNewInterruptionSelectList
				.extractInterruption(ndxCmbEffortCategoryNewInterruptionSelector);

		lstLocalEffortCategoryInterruptionList.addInterruption(
				ndxJlstEffortCategoryInterruptionSelector, toBeInserted);
		// We have inserted above the previous current item, so that item will
		// be pushed down one and the just
		// inserted item will take its old place. Therefore, the index of the
		// current item stays the same.

		// Update the GUI to reflect this change

		commonUpdateAfterAnEffortCategoryInterruptionInsert();

	}

	public void performEffortCategoryInterruptionNewBelow() {
		// This method is only used for inserting below a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.

		ndxCmbEffortCategoryNewInterruptionSelector = jcbEffortCategoryNewInterruption
				.getSelectionModel().getSelectedIndex();

		Interruption toBeInserted = cmbEffortCategoryNewInterruptionSelectList
				.extractInterruption(ndxCmbEffortCategoryNewInterruptionSelector);

		lstLocalEffortCategoryInterruptionList.addInterruption(
				ndxJlstEffortCategoryInterruptionSelector + 1, toBeInserted);
		// We have inserted below the previous current item, so this just
		// inserted item will have an
		// index that is one larger that the previous selected item. Therefore,
		// the index of the
		// current item is incremented.
		ndxJlstEffortCategoryInterruptionSelector++;

		commonUpdateAfterAnEffortCategoryInterruptionInsert();

	}

	int flag2 = 1;

	public void commonUpdateAfterAnEffortCategoryInterruptionInsert() {
		// The Interruption used list has changed, so we need to rebuild the
		// list that is displayed
		lstEffortCategoryInterruptionSelectListSource = lstLocalEffortCategoryInterruptionList
				.buildSelectList();

		lglEffortCategoryInterruptionListIsBeingUpdated = true;

		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoryInterruptionSelectListSource);
		jlstEffortCategoryInterruption.setItems(items);

		lglEffortCategoryInterruptionListIsBeingUpdated = false;

		jlstEffortCategoryInterruption.getSelectionModel().select(
				ndxJlstEffortCategoryInterruptionSelector);
		// The ComboBox list has also changed (one fewer item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Interruptions Used list.

		cmbEffortCategoryNewInterruptionSelectList = lstInterruptionList
				.buildSubList(lstLocalEffortCategoryInterruptionList);

		cmbModelEffortCategoryNewInterruption = cmbEffortCategoryNewInterruptionSelectList
				.buildSelectList();

		ObservableList<String> items1 = FXCollections
				.observableArrayList(cmbModelEffortCategoryNewInterruption);

		jcbEffortCategoryNewInterruption.setItems(items1);
		flag2 = 0;
		// Since the size of the ComboBox is now smaller, we need to check the
		// index of the current
		// selected item to be sure that it is still in a valid range, and fix
		// it if not. (Remember,
		// it could now be empty!)
		if (ndxCmbEffortCategoryNewInterruptionSelector >= cmbEffortCategoryNewInterruptionSelectList
				.getNumberOfInterruptions())
			ndxCmbEffortCategoryNewInterruptionSelector = cmbEffortCategoryNewInterruptionSelectList
					.getNumberOfInterruptions() - 1;
		flag2 = 1;
		jcbEffortCategoryNewInterruptionAction();

		jcbEffortCategoryNewInterruption.getSelectionModel().select(
				ndxCmbEffortCategoryNewInterruptionSelector);
		// These changes may cause changes in the state of the five buttons for
		// the Interruptions Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkEffortCategoryButtons();
	}

	public void performEffortCategoryInterruptionMvUp() {
		// This method is only used for moving a selected item up a non-empty
		// JList when there is at least
		// one element above it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalEffortCategoryInterruptionList
				.moveInterruptionUp(ndxJlstEffortCategoryInterruptionSelector);

		ndxJlstEffortCategoryInterruptionSelector--;

		commonUpdateAfterAnEffortCategoryInterruptionMove();

	}

	public void performEffortCategoryInterruptionMvDn() {
		// This method is only used for moving a selected item down a non-empty
		// JList when there is at least
		// one element below it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalEffortCategoryInterruptionList
				.moveInterruptionDn(ndxJlstEffortCategoryInterruptionSelector);

		ndxJlstEffortCategoryInterruptionSelector++;

		commonUpdateAfterAnEffortCategoryInterruptionMove();

	}

	public void commonUpdateAfterAnEffortCategoryInterruptionMove() {
		lstEffortCategoryInterruptionSelectListSource = lstLocalEffortCategoryInterruptionList
				.buildSelectList();
		// Since we are updating the list, we need to ignore the list change
		// events
		lglEffortCategoryInterruptionListIsBeingUpdated = true;

		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoryInterruptionSelectListSource);
		jlstEffortCategoryInterruption.setItems(items);

		lglEffortCategoryInterruptionListIsBeingUpdated = false;

		jlstEffortCategoryInterruption.getSelectionModel().select(
				ndxJlstEffortCategoryInterruptionSelector);
		// These changes may cause changes in the state of the five buttons for
		// the Interruptions Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkEffortCategoryButtons();

	}

	public void performEffortCategoryInterruptionDelete() {
		// This method is only used for removing a selected item from a
		// non-empty JList.

		// This statements actually does the delete. The rest are about
		// adjusting the GUI state to match.
		lstLocalEffortCategoryInterruptionList
				.deleteInterruption(ndxJlstEffortCategoryInterruptionSelector);

		lstEffortCategoryInterruptionSelectListSource = lstLocalEffortCategoryInterruptionList
				.buildSelectList();

		lglEffortCategoryInterruptionListIsBeingUpdated = true;

		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoryInterruptionSelectListSource);
		jlstEffortCategoryInterruption.setItems(items);

		lglEffortCategoryInterruptionListIsBeingUpdated = false;

		ndxJlstEffortCategoryInterruptionSelector = -1;

		jlstEffortCategoryInterruption.getSelectionModel().clearSelection();
		// The ComboBox list has also changed (one more item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Interruptions Used list.
		cmbEffortCategoryNewInterruptionSelectList = lstInterruptionList
				.buildSubList(lstLocalEffortCategoryInterruptionList);

		cmbModelEffortCategoryNewInterruption = cmbEffortCategoryNewInterruptionSelectList
				.buildSelectList();

		ObservableList<String> items1 = FXCollections
				.observableArrayList(cmbModelEffortCategoryNewInterruption);
		jcbEffortCategoryNewInterruption.setItems(items1);

		ndxCmbEffortCategoryNewInterruptionSelector = 0;

		jcbEffortCategoryNewInterruption.getSelectionModel().select(
				ndxCmbEffortCategoryNewInterruptionSelector);
		// These changes may cause changes in the state of the five buttons for
		// the Interruptions Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkEffortCategoryButtons();

	}

	public void performEffortCategoryDefectNew() {
		// This method is only used for inserting the first item into an empty
		// JList. (Once the JList has an
		// item in it, the user must specify whether to insert above or below
		// the selected list item.)

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbEffortCategoryNewDefectSelector = jcbEffortCategoryNewDefect
				.getSelectionModel().getSelectedIndex();

		Defect toBeInserted = cmbEffortCategoryNewDefectSelectList
				.extractDefect(ndxCmbEffortCategoryNewDefectSelector);

		lstLocalEffortCategoryDefectList.addDefect(toBeInserted);

		ndxJlstEffortCategoryDefectSelector = 0;
		// Since there is only one item in the list now, we must highlight the
		// first one as the
		// selected item

		commonUpdateAfterAnEffortCategoryDefectInsert();

	}

	public void performEffortCategoryDefectNewAbove() {
		// This method is only used for inserting above a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbEffortCategoryNewDefectSelector = jcbEffortCategoryNewDefect
				.getSelectionModel().getSelectedIndex();

		Defect toBeInserted = cmbEffortCategoryNewDefectSelectList
				.extractDefect(ndxCmbEffortCategoryNewDefectSelector);

		lstLocalEffortCategoryDefectList.addDefect(
				ndxJlstEffortCategoryDefectSelector, toBeInserted);
		// Inserting above the current item pushes it down and so the new item
		// is in the same position
		// as the previous selected item, so we do not need to change the
		// selector

		// Update the GUI to reflect the change

		commonUpdateAfterAnEffortCategoryDefectInsert();

	}

	public void performEffortCategoryDefectNewBelow() {
		// This method is only used for inserting below a selected item into a
		// non-empty JList.

		// These three statements actually do the Insert. The rest are about
		// adjusting the GUI state to match.
		ndxCmbEffortCategoryNewDefectSelector = jcbEffortCategoryNewDefect
				.getSelectionModel().getSelectedIndex();

		Defect toBeInserted = cmbEffortCategoryNewDefectSelectList
				.extractDefect(ndxCmbEffortCategoryNewDefectSelector);

		lstLocalEffortCategoryDefectList.addDefect(
				ndxJlstEffortCategoryDefectSelector + 1, toBeInserted);
		// We have inserted below the previous current item, so this just
		// inserted item will have an index that is
		// one larger that the previous selected item. Therefore, the index of
		// the current item is incremented.
		ndxJlstEffortCategoryDefectSelector++;

		commonUpdateAfterAnEffortCategoryDefectInsert();

	}

	int flag3 = 1;

	public void commonUpdateAfterAnEffortCategoryDefectInsert() {
		// The Defect used list has changed, so we need to rebuild the list that
		// is displayed
		lstEffortCategoryDefectSelectListSource = lstLocalEffortCategoryDefectList
				.buildSelectList();

		lglEffortCategoryDefectListIsBeingUpdated = true;

		ObservableList<String> items1 = FXCollections
				.observableArrayList(lstEffortCategoryDefectSelectListSource);
		jlstEffortCategoryDefect.setItems(items1);

		lglEffortCategoryDefectListIsBeingUpdated = false;

		jlstEffortCategoryDefect.getSelectionModel().select(
				ndxJlstEffortCategoryDefectSelector);

		// The ComboBox list has also changed (one fewer item), so it must be
		// rebuilt as well
		// This command creates this list by taking the entire list of artifacts
		// that exist and then
		// removes those that are in the Defects Used list.

		cmbEffortCategoryNewDefectSelectList = lstDefectList
				.buildSubList(lstLocalEffortCategoryDefectList);

		cmbModelEffortCategoryNewDefect = cmbEffortCategoryNewDefectSelectList
				.buildSelectList();

		ObservableList<String> items = FXCollections
				.observableArrayList(cmbModelEffortCategoryNewDefect);
		jcbEffortCategoryNewDefect.setItems(items);
		flag3 = 0;
		// Since the size of the ComboBox is now smaller, we need to check the
		// index of the current
		// selected item to be sure that it is still in a valid range, and fix
		// it if not. (Remember,
		// it could now be empty!)

		if (ndxCmbEffortCategoryNewDefectSelector >= cmbEffortCategoryNewDefectSelectList
				.getNumberOfDefects())
			ndxCmbEffortCategoryNewDefectSelector = cmbEffortCategoryNewDefectSelectList
					.getNumberOfDefects() - 1;
		flag3 = 1;
		jcbEffortCategoryNewDefectAction();
		jcbEffortCategoryNewDefect.getSelectionModel().select(
				ndxCmbEffortCategoryNewDefectSelector);

		checkEffortCategoryButtons();

	}

	public void performEffortCategoryDefectMvUp() {
		// This method is only used for moving a selected item up a non-empty
		// JList when there is at least
		// one element above it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalEffortCategoryDefectList
				.moveDefectUp(ndxJlstEffortCategoryDefectSelector);

		ndxJlstEffortCategoryDefectSelector--;

		commonUpdateAfterAnEffortCategoryDefectMove();
	}

	public void performEffortCategoryDefectMvDn() {
		// This method is only used for moving a selected item down a non-empty
		// JList when there is at least
		// one element below it.

		// This statements actually does the move. The rest are about adjusting
		// the GUI state to match.
		lstLocalEffortCategoryDefectList
				.moveDefectDn(ndxJlstEffortCategoryDefectSelector);

		ndxJlstEffortCategoryDefectSelector++;

		commonUpdateAfterAnEffortCategoryDefectMove();

	}

	public void commonUpdateAfterAnEffortCategoryDefectMove() {
		// The list has changed so we need to build a new list.
		lstEffortCategoryDefectSelectListSource = lstLocalEffortCategoryDefectList
				.buildSelectList();

		lglEffortCategoryDefectListIsBeingUpdated = true;

		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoryDefectSelectListSource);
		jlstEffortCategoryDefect.setItems(items);

		lglEffortCategoryDefectListIsBeingUpdated = false;

		jlstEffortCategoryDefect.getSelectionModel().select(
				ndxJlstEffortCategoryDefectSelector);

		// These changes may cause changes in the state of the five buttons for
		// the Defects Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		checkEffortCategoryButtons();
	}

	public void performEffortCategoryDefectDelete() {
		// This method is only used for removing a selected item from a
		// non-empty JList.

		// This statements actually does the delete. The rest are about
		// adjusting the GUI state to match.
		lstLocalEffortCategoryDefectList
				.deleteDefect(ndxJlstEffortCategoryDefectSelector);
		// Now that the artifact has been deleted, we need to clean up the UI to
		// match this state.

		// The list has changed so we need to build a new list.
		lstEffortCategoryDefectSelectListSource = lstLocalEffortCategoryDefectList
				.buildSelectList();

		lglEffortCategoryDefectListIsBeingUpdated = true;

		ObservableList<String> items = FXCollections
				.observableArrayList(lstEffortCategoryDefectSelectListSource);
		jlstEffortCategoryDefect.setItems(items);

		lglEffortCategoryDefectListIsBeingUpdated = false;
		// We have delete the current item from the list, so no item in the list
		// will be selected now.
		// Therefore, we clear the index and the selection from the GUI
		// component

		ndxJlstEffortCategoryDefectSelector = -1;
		jlstEffortCategoryDefect.getSelectionModel().clearSelection();

		cmbEffortCategoryNewDefectSelectList = lstDefectList
				.buildSubList(lstLocalEffortCategoryDefectList);

		cmbModelEffortCategoryNewDefect = cmbEffortCategoryNewDefectSelectList
				.buildSelectList();

		ObservableList<String> items1 = FXCollections
				.observableArrayList(cmbModelEffortCategoryNewDefect);
		jcbEffortCategoryNewDefect.setItems(items1);
		// Since the size of the ComboBox is now larger, we know that there is
		// at least one item in it,
		// so we will default the ComboBox to the first item in the list.

		// Set the index to be the first item in the list

		ndxCmbEffortCategoryNewDefectSelector = 0;
		// These changes may cause changes in the state of the five buttons for
		// the Defects Used list,
		// so we process them all rather than try to reason about which buttons
		// might be affected.
		jcbEffortCategoryNewDefect.getSelectionModel().select(
				ndxCmbEffortCategoryNewDefectSelector);

		checkEffortCategoryButtons();
	}

	public void performEffortCategoriesClear() {
		ndxJlstEffortCategorySelector = -1; // Reset the local copy of the
		// current index
		jlstEffortCategories.getSelectionModel().clearSelection(); // Clear the
		// JList
		// selection
		fldEffortCategoryName.setText(""); // Clear the Name input field
		strLocalEffortCategoryName = ""; // and the local copy
		fldEffortCategoryDesc.setText(""); // Clear the Description input field
		strLocalEffortCategoryDesc = ""; // and the local copy

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected

		checkEffortCategoryButtons();

	}

	// this function is called when the effort category item is selected
	/*****
	 * Process a select list selection event. This method is called whenever the
	 * user clicks on or scrolls over items in the JList. After each event this
	 * method updates the display and local values, giving a real-time display
	 * rather just showing the result when the update to the JList is complete.
	 * This makes the application appear to be "much more responsive".
	 * 
	 * If the new selected item is the same as what is currently the selected
	 * item, we do nothing. If it is a new item, we then set up both the
	 * selected attributes for both the name and the description as well as set
	 * the user input fields to the values from the select list, discarding
	 * whatever input the user may have entered. (Change the selection and then
	 * update the name and description, not the other way around!)
	 * 
	 * @param event
	 */
	public void jltEffortCategoriesListItemSelected() {
		int ndx = jlstEffortCategories.getSelectionModel().getSelectedIndex();

		if (ndxJlstEffortCategorySelector == ndx)
			return;

		ndxJlstEffortCategorySelector = ndx;

		strSelectedEffortCategoryName = lstEffortCategoryList
				.getEffortCategory(ndx).getName();
		strLocalEffortCategoryName = strSelectedEffortCategoryName;
		fldEffortCategoryName.setText(strLocalEffortCategoryName);

		strSelectedEffortCategoryDesc = lstEffortCategoryList
				.getEffortCategory(ndx).getDesc();
		strLocalEffortCategoryDesc = strSelectedEffortCategoryDesc;
		fldEffortCategoryDesc.setText(strLocalEffortCategoryDesc);

		ndxLocalEffortCategoryState = lstEffortCategoryList.getEffortCategory(
				ndx).getKind();
		ndxSelectedEffortCategoryState = ndxLocalEffortCategoryState;
		jcbEffortCategoryOption.getSelectionModel().select(
				ndxLocalEffortCategoryState);

		lstSelectedEffortCategoryArtifactList = lstEffortCategoryList
				.getEffortCategory(ndx).getArtifactListController();
		lstSelectedEffortCategoryPlanList = lstEffortCategoryList
				.getEffortCategory(ndx).getPlanListController();
		lstSelectedEffortCategoryInterruptionList = lstEffortCategoryList
				.getEffortCategory(ndx).getInterruptionListController();
		lstSelectedEffortCategoryDefectList = lstEffortCategoryList
				.getEffortCategory(ndx).getDefectListController();

		lstLocalEffortCategoryArtifactList = new ArtifactListController(
				lstSelectedEffortCategoryArtifactList);
		lstLocalEffortCategoryPlanList = new PlanListController(
				lstSelectedEffortCategoryPlanList);
		lstLocalEffortCategoryInterruptionList = new InterruptionListController(
				lstSelectedEffortCategoryInterruptionList);
		lstLocalEffortCategoryDefectList = new DefectListController(
				lstSelectedEffortCategoryDefectList);

		switch (ndxLocalEffortCategoryState) {
		case 0:
			wakeUpEffortCategoriesArtifactList();
			break;

		case 1:
			wakeUpEffortCategoriesPlanList();
			break;

		case 2:
			wakeUpEffortCategoriesInterruptionList();
			break;

		case 3:
			wakeUpEffortCategoriesDefectList();
			break;

		default:
			wakeUpEffortCategoriesOther();
			break;
		}
		// Changing of these fields may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkEffortCategoryButtons();
	}

	/*****
	 * Process a change to the user input Name Field
	 * 
	 * Limit the total number of characters in the field to just 34. If there
	 * are more, discard them and beep the console to inform the user that a
	 * limit has been reached.
	 * 
	 * The current state of the input field is echoed in the private attribute
	 * named strLocalEffortCategoryName.
	 */
	public void rtnCheckFldEffortCategoriesNewName() {

		if (fldEffortCategoryName.getText().length() < 35)
			// As long as there are not more than 34 characters, all we need
			// to do is to fetch the text and save a copy in the local
			// attribute.
			strLocalEffortCategoryName = fldEffortCategoryName.getText();
		else {
			// Limit the size of the input to 34 characters, so discard any
			// after the first 34.
			strLocalEffortCategoryName = fldEffortCategoryName.getText()
					.substring(0, 34);

			// We can't change the input field during the notification, so we
			// schedule a task to be run after the update and other I/O actions
			// finish to prune the input to just the first 34 characters and
			// beep
			// the console.

			// This creates the task that is to be run later.
			// Runnable resetInput = new Runnable() {
			// public void run() {
			fldEffortCategoryName.setText(strLocalEffortCategoryName);
			// java.awt.Toolkit.getDefaultToolkit().beep();
			// }
			// };
			//
			// // This is the command to actual schedule that.
			// // SwingUtilities.invokeLater(resetInput);
		}

		// Changing of this field may cause changes in the state of the five
		// buttons, so we process them all rather
		// than try to reason about which buttons might be affected.
		checkEffortCategoryButtons();

	}

	/*****
	 * Process a change to the user input Description Field
	 * 
	 * Limit the total number of characters in the field to just 1000. If there
	 * are more, discard them and beep the console to inform the user that a
	 * limit has been reached.
	 * 
	 * The current state of the input field is echoed in the private attribute
	 * named strEffortCategoryDesc.
	 */
	public void rtnCheckFldEffortCategoriesNewDesc() {
		if (fldEffortCategoryDesc.getText().length() < 1001) {
			// As long as there are not more than 1000 characters, all we need
			// to do is to fetch the text and save
			// a copy in the local attribute.
			strLocalEffortCategoryDesc = fldEffortCategoryDesc.getText();
		} else {
			// Limit the size of the input to 1000 characters, so discard any
			// after the first 1000.
			strLocalEffortCategoryDesc = fldEffortCategoryDesc.getText()
					.substring(0, 1000);

			// We can't change the input field during the notification, so we
			// schedule a task to be run after the
			// update and other I/O actions finish to prune the input to just
			// the first 1000 characters and beep
			// the console.

			// This creates the task that is to be run later.
			// Runnable pruneTheInput = new Runnable() {
			// public void run() {
			// fldEffortCategoryDesc.setText(strLocalEffortCategoryDesc);
			// java.awt.Toolkit.getDefaultToolkit().beep();
			// }
			// };
			//
			// // This is the command to actual schedule that.
			// SwingUtilities.invokeLater(pruneTheInput);
		}

		// Changing of this field may cause changes in the state of the five
		// buttons, so we process them all rather
		// than try to reason about which buttons might be affected.
		checkEffortCategoryButtons();

	}

	private boolean effortCategoryOptionalFieldsAreTheSame() {
		boolean result = true;
		switch (ndxSelectedEffortCategoryState) {
		case 0:
			result = lstLocalEffortCategoryArtifactList
					.isTheSameAs(lstSelectedEffortCategoryArtifactList);
			break;

		case 1:
			result = lstLocalEffortCategoryPlanList
					.isTheSameAs(lstSelectedEffortCategoryPlanList);
			break;

		case 2:
			result = lstLocalEffortCategoryInterruptionList
					.isTheSameAs(lstSelectedEffortCategoryInterruptionList);
			break;

		case 3:
			result = lstLocalEffortCategoryDefectList
					.isTheSameAs(lstSelectedEffortCategoryDefectList);
			break;

		default:
			result = true;
			break;
		}
		return result;
	}

	public void jltEffortCategoryArtifactListItemSelected() {

		if (lglEffortCategoryArtifactListIsBeingUpdated)
			return;

		int ndx = jlstEffortCategoryArtifact.getSelectionModel()
				.getSelectedIndex();

		// If the index matches the current local copies, there is no need to
		// proceed
		if (ndxJlstEffortCategoryArtifactSelector == ndx)
			return;
		ndxJlstEffortCategoryArtifactSelector = ndx;
		checkEffortCategoryArtifactButtons();
	}

	public void jcbEffortCategoryNewArtifactAction() {
		if (flag == 1)
			return;
		ndxCmbEffortCategoryNewArtifactSelector = jcbEffortCategoryNewArtifact
				.getSelectionModel().getSelectedIndex();
	}

	public void jltEffortCategoryPlanListItemSelected() {
		if (lglEffortCategoryPlanListIsBeingUpdated)
			return;

		int ndx = jlstEffortCategoryPlan.getSelectionModel().getSelectedIndex();

		// If the index matches the current local copies, there is no need to
		// proceed
		if (ndxJlstEffortCategoryPlanSelector == ndx)
			return;
		ndxJlstEffortCategoryPlanSelector = ndx;
		checkEffortCategoryPlanButtons();

	}

	public void jcbEffortCategoryNewPlanAction() {
		if (flag1 == 1)
			return;
		ndxCmbEffortCategoryNewPlanSelector = jcbEffortCategoryNewPlan
				.getSelectionModel().getSelectedIndex();
	}

	public void jltEffortCategoryInterruptionListItemSelected() {

		if (lglEffortCategoryInterruptionListIsBeingUpdated)
			return;
		int ndx = jlstEffortCategoryInterruption.getSelectionModel()
				.getSelectedIndex();

		// If the index matches the current local copies, there is no need to
		// proceed
		// 50271575410
		if (ndxJlstEffortCategoryInterruptionSelector == ndx)
			return;
		ndxJlstEffortCategoryInterruptionSelector = ndx;
		checkEffortCategoryInterruptionButtons();
	}

	public void jcbEffortCategoryNewInterruptionAction() {
		if (flag2 == 1)
			return;
		ndxCmbEffortCategoryNewInterruptionSelector = jcbEffortCategoryNewInterruption
				.getSelectionModel().getSelectedIndex();
	}

	public void jltEffortCategoryDefectListItemSelected() {
		if (lglEffortCategoryDefectListIsBeingUpdated)
			return;
		int ndx = jlstEffortCategoryDefect.getSelectionModel()
				.getSelectedIndex();

		// If the index matches the current local copies, there is no need to
		// proceed
		if (ndxJlstEffortCategoryDefectSelector == ndx)
			return;
		ndxJlstEffortCategoryDefectSelector = ndx;
		checkEffortCategoryDefectButtons();
	}

	public void jcbEffortCategoryNewDefectAction() {
		if (flag3 == 1)
			return;
		ndxCmbEffortCategoryNewDefectSelector = jcbEffortCategoryNewDefect
				.getSelectionModel().getSelectedIndex();
	}

	private void checkEffortCategoryButtons() {

		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the exception of the enabled status for
		// the single large Insert, the Insert Above, and the Insert Below
		// buttons
		// which are processed in the second step below.

		// This is the first step in the process.
		int numEffortCategories = lstEffortCategoryList
				.getNumberOfEffortCategories();
		if (numEffortCategories == 0) {
			// If the list is empty, only one insert button is visible as well
			// as
			// the save and delete buttons. The save and delete should be
			// disabled
			// The semantics of the enabled and disabled status of the Insert
			// buttons is more complex, so it is handled at the end of this
			// method.
			btnEffortCategoriesNew.setVisible(true);
			btnEffortCategoriesNewAbove.setDisable(true);
			btnEffortCategoriesNewAbove.setVisible(false);
			btnEffortCategoriesNewBelow.setDisable(true);
			btnEffortCategoriesNewBelow.setVisible(false);
			btnEffortCategoriesDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for inserting below the selected
			// item.
			// So we start by making the insert above and below buttons visible
			// and make the single insert button invisible and disabled.
			btnEffortCategoriesNewAbove.setVisible(true);
			btnEffortCategoriesNewBelow.setVisible(true);
			btnEffortCategoriesNew.setVisible(false);
			btnEffortCategoriesNew.setDisable(true);

			if (ndxJlstEffortCategorySelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled. (It is always visible.)
				btnEffortCategoriesDelete.setDisable(false);

				if (strSelectedEffortCategoryName
						.equals(strLocalEffortCategoryName)
						&& strSelectedEffortCategoryDesc
								.equals(strLocalEffortCategoryDesc)
						&& (ndxSelectedEffortCategoryState == ndxLocalEffortCategoryState)
						&& effortCategoryOptionalFieldsAreTheSame()) {

					// The list is not empty, one item in the list is selected,
					// and both the name and description input fields match
					// the fields stored in the data structure. We also found
					// the kinds of Effort Categories match as do the associated
					// lists.
					btnEffortCategoriesSave.setDisable(true);
				} else {
					// The list is not empty, one item in the list is selected,
					// but there is a a difference between the name and
					// description
					// input fields and the fields stored in the data structure,
					// so there may be a need to perform a Save operation and
					// therefore the button is visible and enabled.
					btnEffortCategoriesSave.setDisable(false);
				}
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are disabled (they are always
				// visible) regardless of what is in the input fields fore the
				// name and the description.
				btnEffortCategoriesDelete.setDisable(true);
				btnEffortCategoriesSave.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two
			// or more items in the list and one item is selected
			if (numEffortCategories < 2 || ndxJlstEffortCategorySelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected
				// neither of the two buttons should be enabled
				btnEffortCategoriesMvUp.setDisable(true);
				btnEffortCategoriesMvDn.setDisable(true);

			} else if (ndxJlstEffortCategorySelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnEffortCategoriesMvUp.setDisable(true);
				btnEffortCategoriesMvDn.setDisable(false);

			} else if (ndxJlstEffortCategorySelector == numEffortCategories - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnEffortCategoriesMvUp.setDisable(false);
				btnEffortCategoriesMvDn.setDisable(true);

			} else {
				// Otherwise both options are valid.
				btnEffortCategoriesMvUp.setDisable(false);
				btnEffortCategoriesMvDn.setDisable(false);
			}
		}

		// The following is the second step.

		// The remaining code is. about the enabled or disabled status of the
		// insert buttons.
		// Visibility has been handled above as has the enabled status for
		// Delete and Save.
		// This code deals with the visibility of the three insert buttons.
		int combo = jcbEffortCategoryOption.getSelectionModel()
				.getSelectedIndex();
		int x = 0;
		switch (combo) {
		case 0:
			x = lstLocalEffortCategoryArtifactList.getNumberOfArtifacts();
			break;
		case 1:
			x = lstLocalEffortCategoryPlanList.getNumberOfPlans();
			break;
		case 2:
			x = lstLocalEffortCategoryInterruptionList
					.getNumberOfInterruptions();
			break;
		case 3:
			x = lstLocalEffortCategoryDefectList.getNumberOfDefects();
			break;
		case 4:
			x = 1;
		}
		// System.out.println("x is "+x);
		if (lstEffortCategoryList.getNumberOfEffortCategories() == 0
				&& strLocalEffortCategoryName.length() == 0) {
			// The list is empty and the user input name field is empty, so the
			// single
			// Insert button is visible, but it is disabled. The two insert
			// buttons were
			// disabled above and their visibility was handled above.
			btnEffortCategoriesNew.setDisable(true);
		} else if (lstEffortCategoryList.getNumberOfEffortCategories() == 0
				&& strLocalEffortCategoryName.length() > 0 && x <= 0) {
			// The list is empty, but the user input name field is not, so the
			// single
			// Insert button is visible and enabled. The two Insert buttons are
			// not visible
			// (from above) and are disabled.
			btnEffortCategoriesNew.setDisable(true);
		} else if (lstEffortCategoryList.getNumberOfEffortCategories() == 0
				&& strLocalEffortCategoryName.length() > 0 && x > 0) {
			// The list is empty, but the user input name field is not, so the
			// single
			// Insert button is visible and enabled. The two Insert buttons are
			// not visible
			// (from above) and are disabled.
			btnEffortCategoriesNew.setDisable(false);
		} else if (lstEffortCategoryList.getNumberOfEffortCategories() > 0
				&& strLocalEffortCategoryName.length() == 0) {
			// The list is not empty, but the user input name field is empty, so
			// the two
			// Insert buttons are visible and enabled. The single Insert button
			// is not visible
			// and disabled from above).
			btnEffortCategoriesNewAbove.setDisable(true);
			btnEffortCategoriesNewBelow.setDisable(true);
		} else if (lstEffortCategoryList.getNumberOfEffortCategories() > 0
				&& strLocalEffortCategoryName.length() > 0 && x <= 0) {
			// The list is not empty and the user input name field is not empty,
			// so the status of
			// the two Insert buttons in terms of enabled or not is determined
			// by where or not
			// an item in the list has been selected. We do know that the single
			// insert was
			// made not visible and disabled above, so no need to do it here.
			if (ndxJlstEffortCategorySelector == -1) {
				// No item is selected, so it is not possible to insert above or
				// below it.
				btnEffortCategoriesNewAbove.setDisable(true);
				btnEffortCategoriesNewBelow.setDisable(true);
			} else {
				// An item is selected, so it is possible to insert above or
				// below it
				btnEffortCategoriesNewAbove.setDisable(true);
				btnEffortCategoriesNewBelow.setDisable(true);
			}
		} else if (lstEffortCategoryList.getNumberOfEffortCategories() > 0
				&& strLocalEffortCategoryName.length() > 0) {
			// The list is not empty and the user input name field is not empty,
			// so the status of
			// the two Insert buttons in terms of enabled or not is determined
			// by where or not
			// an item in the list has been selected. We do know that the single
			// insert was
			// made not visible and disabled above, so no need to do it here.
			if (ndxJlstEffortCategorySelector == -1) {
				// No item is selected, so it is not possible to insert above or
				// below it.
				btnEffortCategoriesNewAbove.setDisable(true);
				btnEffortCategoriesNewBelow.setDisable(true);
			} else {
				// An item is selected, so it is possible to insert above or
				// below it
				btnEffortCategoriesNewAbove.setDisable(false);
				btnEffortCategoriesNewBelow.setDisable(false);
			}
		}

		switch (ndxLocalEffortCategoryState) {
		case 0:
			checkEffortCategoryArtifactButtons();
			break;

		case 1:
			checkEffortCategoryPlanButtons();
			break;

		case 2:
			checkEffortCategoryInterruptionButtons();
			break;

		case 3:
			checkEffortCategoryDefectButtons();
			break;

		default:
			break;
		}

	}

	public void checkEffortCategoryArtifactButtons() {
		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the
		// exception of the enabled status for the single large Insert, the
		// Insert Above, and the Insert Below
		// buttons which are processed in the second step below.

		// This is the first step in the process.
		int numArtifacts = lstLocalEffortCategoryArtifactList
				.getNumberOfArtifacts();
		if (numArtifacts == 0) {
			// If the list is empty, only one insert button is visible as well
			// as the save and delete buttons.
			// The save and delete should be disabled. The semantics of the
			// enabled and disabled status of the
			// Insert buttons is more complex, so it is handled at the end of
			// this method.
			btnEffortCategoryArtNew.setVisible(true);
			btnEffortCategoryArtAbove.setDisable(true);
			btnEffortCategoryArtAbove.setVisible(false);
			btnEffortCategoryArtBelow.setDisable(true);
			btnEffortCategoryArtBelow.setVisible(false);
			btnEffortCategoryArtMvUp.setDisable(true);
			btnEffortCategoryArtMvDn.setDisable(true);
			btnEffortCategoryArtMvUp.setVisible(true);
			btnEffortCategoryArtMvDn.setVisible(true);
			btnEffortCategoryArtDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for
			// inserting below the selected item. So we start by making the
			// insert above and below buttons
			// visible and make the single insert button invisible and disabled.
			btnEffortCategoryArtAbove.setVisible(true);
			btnEffortCategoryArtBelow.setVisible(true);
			btnEffortCategoryArtNew.setVisible(false);
			btnEffortCategoryArtNew.setDisable(true);

			if (ndxJlstEffortCategoryArtifactSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled.
				// (It is always visible.)
				btnEffortCategoryArtDelete.setDisable(false);
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are
				// disabled (they are always visible) regardless of what is in
				// the input fields for the name and
				// the description.
				btnEffortCategoryArtDelete.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two or more items in the list and
			// one item is selected
			if (numArtifacts < 2 || ndxJlstEffortCategoryArtifactSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected neither of the two buttons
				// should be enabled
				btnEffortCategoryArtMvUp.setDisable(true);
				btnEffortCategoryArtMvDn.setDisable(true);

			} else if (ndxJlstEffortCategoryArtifactSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnEffortCategoryArtMvUp.setDisable(true);
				btnEffortCategoryArtMvDn.setDisable(false);

			} else if (ndxJlstEffortCategoryArtifactSelector == numArtifacts - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnEffortCategoryArtMvUp.setDisable(false);
				btnEffortCategoryArtMvDn.setDisable(true);

			} else {
				// Otherwise both options (up and down) are valid.
				btnEffortCategoryArtMvUp.setDisable(false);
				btnEffortCategoryArtMvDn.setDisable(false);
			}
		}

		// The following is the second step... Determine the enabled status of
		// the insert buttons.
		ndxJlstEffortCategoryArtifactSelector = jlstEffortCategoryArtifact
				.getSelectionModel().getSelectedIndex(); 
		if (lstLocalEffortCategoryArtifactList.getNumberOfArtifacts() > 0) {
			if (ndxJlstEffortCategoryArtifactSelector == -1
					|| ndxCmbEffortCategoryNewArtifactSelector == -1) {
				// The list is not empty, but no item is selected or no ComboBox
				// item is selected, so it is not
				// possible to insert and artifact above or below.
				btnEffortCategoryArtAbove.setDisable(true);
				btnEffortCategoryArtBelow.setDisable(true);
			} else {
				// A non-empty list with an item selected and a selected item in
				// the ComboBox, so it is possible
				// to insert above or below
				btnEffortCategoryArtAbove.setDisable(false);
				btnEffortCategoryArtBelow.setDisable(false);
			}
		} else {
			// The list is empty, so the we need to see if the empty list insert
			// should be enabled
			if (ndxCmbEffortCategoryNewArtifactSelector == -1)
				// If the ComboBox does not have a selected item, then no insert
				// is possible
				btnEffortCategoryArtNew.setDisable(true);
			else
				// Empty list, but the ComboxBox has a selected item, so it
				// could be inserted into the list
				btnEffortCategoryArtNew.setDisable(false);

			// Since the list is empty, it is not possible to have a selected
			// element, so above or below is not possible
			btnEffortCategoryArtAbove.setDisable(true);
			btnEffortCategoryArtBelow.setDisable(true);
		}
	}

	public void checkEffortCategoryPlanButtons() {
		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the
		// exception of the enabled status for the single large Insert, the
		// Insert Above, and the Insert Below
		// buttons which are processed in the second step below.

		// This is the first step in the process.
		int numPlans = lstLocalEffortCategoryPlanList.getNumberOfPlans();
		if (numPlans == 0) {
			// If the list is empty, only one insert button is visible as well
			// as the save and delete buttons.
			// The save and delete should be disabled. The semantics of the
			// enabled and disabled status of the
			// Insert buttons is more complex, so it is handled at the end of
			// this method.
			btnEffortCategoryPlanNew.setVisible(true);
			btnEffortCategoryPlanAbove.setDisable(true);
			btnEffortCategoryPlanAbove.setVisible(false);
			btnEffortCategoryPlanBelow.setDisable(true);
			btnEffortCategoryPlanBelow.setVisible(false);
			btnEffortCategoryPlanMvUp.setDisable(true);
			btnEffortCategoryPlanMvDn.setDisable(true);
			btnEffortCategoryPlanMvUp.setVisible(true);
			btnEffortCategoryPlanMvDn.setVisible(true);
			btnEffortCategoryPlanDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for
			// inserting below the selected item. So we start by making the
			// insert above and below buttons
			// visible and make the single insert button invisible and disabled.
			btnEffortCategoryPlanAbove.setVisible(true);
			btnEffortCategoryPlanBelow.setVisible(true);
			btnEffortCategoryPlanNew.setVisible(false);
			btnEffortCategoryPlanNew.setDisable(true);

			if (ndxJlstEffortCategoryPlanSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled.
				// (It is always visible.)
				btnEffortCategoryPlanDelete.setDisable(false);
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are
				// disabled (they are always visible) regardless of what is in
				// the input fields for the name and
				// the description.
				btnEffortCategoryPlanDelete.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two or more items in the list and
			// one item is selected
			if (numPlans < 2 || ndxJlstEffortCategoryPlanSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected neither of the two buttons
				// should be enabled
				btnEffortCategoryPlanMvUp.setDisable(true);
				btnEffortCategoryPlanMvDn.setDisable(true);

			} else if (ndxJlstEffortCategoryPlanSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnEffortCategoryPlanMvUp.setDisable(true);
				btnEffortCategoryPlanMvDn.setDisable(false);

			} else if (ndxJlstEffortCategoryPlanSelector == numPlans - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnEffortCategoryPlanMvUp.setDisable(false);
				btnEffortCategoryPlanMvDn.setDisable(true);

			} else {
				// Otherwise both options (up and down) are valid.
				btnEffortCategoryPlanMvUp.setDisable(false);
				btnEffortCategoryPlanMvDn.setDisable(false);
			}
		}

		// The following is the second step... Determine the enabled status of
		// the insert buttons.
		ndxJlstEffortCategoryPlanSelector = jlstEffortCategoryPlan
				.getSelectionModel().getSelectedIndex();
		if (lstLocalEffortCategoryPlanList.getNumberOfPlans() > 0) {
			// The list is not empty
			if (ndxJlstEffortCategoryPlanSelector == -1
					|| ndxCmbEffortCategoryNewPlanSelector == -1) {
				// The list is not empty, but no item is selected or no ComboBox
				// item is selected, so it is not
				// possible to insert and artifact above or below.
				btnEffortCategoryPlanAbove.setDisable(true);
				btnEffortCategoryPlanBelow.setDisable(true);
			} else {
				// A non-empty list with an item selected and a selected item in
				// the ComboBox, so it is possible
				// to insert above or below
				btnEffortCategoryPlanAbove.setDisable(false);
				btnEffortCategoryPlanBelow.setDisable(false);
			}
		} else {
			// The list is empty, so the we need to see if the empty list insert
			// should be enabled
			if (ndxCmbEffortCategoryNewPlanSelector == -1)
				// If the ComboBox does not have a selected item, then no insert
				// is possible
				btnEffortCategoryPlanNew.setDisable(true);
			else
				// Empty list, but the ComboxBox has a selected item, so it
				// could be inserted into the list
				btnEffortCategoryPlanNew.setDisable(false);

			// Since the list is empty, it is not possible to have a selected
			// element, so above or below is not possible
			btnEffortCategoryPlanAbove.setDisable(true);
			btnEffortCategoryPlanBelow.setDisable(true);
		}
	}

	public void checkEffortCategoryInterruptionButtons() {
		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the
		// exception of the enabled status for the single large Insert, the
		// Insert Above, and the Insert Below
		// buttons which are processed in the second step below.

		// This is the first step in the process.
		int numInterruptions = lstLocalEffortCategoryInterruptionList
				.getNumberOfInterruptions();
		if (numInterruptions == 0) {
			// If the list is empty, only one insert button is visible as well
			// as the save and delete buttons.
			// The save and delete should be disabled. The semantics of the
			// enabled and disabled status of the
			// Insert buttons is more complex, so it is handled at the end of
			// this method.
			btnEffortCategoryInterruptionNew.setVisible(true);
			btnEffortCategoryInterruptionAbove.setDisable(true);
			btnEffortCategoryInterruptionAbove.setVisible(false);
			btnEffortCategoryInterruptionBelow.setDisable(true);
			btnEffortCategoryInterruptionBelow.setVisible(false);
			btnEffortCategoryInterruptionMvUp.setDisable(true);
			btnEffortCategoryInterruptionMvDn.setDisable(true);
			btnEffortCategoryInterruptionMvUp.setVisible(true);
			btnEffortCategoryInterruptionMvDn.setVisible(true);
			btnEffortCategoryInterruptionDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for
			// inserting below the selected item. So we start by making the
			// insert above and below buttons
			// visible and make the single insert button invisible and disabled.
			btnEffortCategoryInterruptionAbove.setVisible(true);
			btnEffortCategoryInterruptionBelow.setVisible(true);
			btnEffortCategoryInterruptionNew.setVisible(false);
			btnEffortCategoryInterruptionNew.setDisable(true);

			if (ndxJlstEffortCategoryInterruptionSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled.
				// (It is always visible.)
				btnEffortCategoryInterruptionDelete.setDisable(false);
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are
				// disabled (they are always visible) regardless of what is in
				// the input fields for the name and
				// the description.
				btnEffortCategoryInterruptionDelete.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two or more items in the list and one item is selected
			if (numInterruptions < 2
					|| ndxJlstEffortCategoryInterruptionSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected neither of the two buttons should be enabled
				btnEffortCategoryInterruptionMvUp.setDisable(true);
				btnEffortCategoryInterruptionMvDn.setDisable(true);

			} else if (ndxJlstEffortCategoryInterruptionSelector == 0) {
				// If the first item in the list is selected, you can move down, but not up
				btnEffortCategoryInterruptionMvUp.setDisable(true);
				btnEffortCategoryInterruptionMvDn.setDisable(false);

			} else if (ndxJlstEffortCategoryInterruptionSelector == numInterruptions - 1) {
				// If the last item in the list is selected, you can move up but not down
				btnEffortCategoryInterruptionMvUp.setDisable(false);
				btnEffortCategoryInterruptionMvDn.setDisable(true);

			} else {
				// Otherwise both options (up and down) are valid.
				btnEffortCategoryInterruptionMvUp.setDisable(false);
				btnEffortCategoryInterruptionMvDn.setDisable(false);
			}
		}

		// The following is the second step... Determine the enabled status of the insert buttons.
		ndxJlstEffortCategoryInterruptionSelector = jlstEffortCategoryInterruption.getSelectionModel().getSelectedIndex();
		if (lstLocalEffortCategoryInterruptionList.getNumberOfInterruptions() > 0) {
			// The list is not empty
			if (ndxJlstEffortCategoryInterruptionSelector == -1
					|| ndxCmbEffortCategoryNewInterruptionSelector == -1) {
				// The list is not empty, but no item is selected or no ComboBox
				// item is selected, so it is not possible to insert and artifact above or below.
				btnEffortCategoryInterruptionAbove.setDisable(true);
				btnEffortCategoryInterruptionBelow.setDisable(true);
			} else {
				// A non-empty list with an item selected and a selected item in the ComboBox, so it is possible
				// to insert above or below
				btnEffortCategoryInterruptionAbove.setDisable(false);
				btnEffortCategoryInterruptionBelow.setDisable(false);
			}
		} else {
			// The list is empty, so the we need to see if the empty list insert should be enabled
			if (ndxCmbEffortCategoryNewInterruptionSelector == -1)
				// If the ComboBox does not have a selected item, then no insert is possible
				btnEffortCategoryInterruptionNew.setDisable(true);
			else
				// Empty list, but the ComboxBox has a selected item, so it could be inserted into the list
				btnEffortCategoryInterruptionNew.setDisable(false);

			// Since the list is empty, it is not possible to have a selected
			// element, so above or below is not possible
			btnEffortCategoryInterruptionAbove.setDisable(true);
			btnEffortCategoryInterruptionBelow.setDisable(true);
		}
	}

	public void checkEffortCategoryDefectButtons() {
		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the
		// exception of the enabled status for the single large Insert, the Insert Above, and the Insert Below
		// buttons which are processed in the second step below.

		// This is the first step in the process.
		int numDefects = lstLocalEffortCategoryDefectList.getNumberOfDefects();
		if (numDefects == 0) {
			// If the list is empty, only one insert button is visible as well as the save and delete buttons.
			// The save and delete should be disabled. The semantics of the
			// enabled and disabled status of the
			// Insert buttons is more complex, so it is handled at the end of this method.
			btnEffortCategoryDefectNew.setVisible(true);
			btnEffortCategoryDefectAbove.setDisable(true);
			btnEffortCategoryDefectAbove.setVisible(false);
			btnEffortCategoryDefectBelow.setDisable(true);
			btnEffortCategoryDefectBelow.setVisible(false);
			btnEffortCategoryDefectMvUp.setDisable(true);
			btnEffortCategoryDefectMvDn.setDisable(true);
			btnEffortCategoryDefectMvUp.setVisible(true);
			btnEffortCategoryDefectMvDn.setVisible(true);
			btnEffortCategoryDefectDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one for inserting above and one for
			// inserting below the selected item. So we start by making the insert above and below buttons
			// visible and make the single insert button invisible and disabled.
			btnEffortCategoryDefectAbove.setVisible(true);
			btnEffortCategoryDefectBelow.setVisible(true);
			btnEffortCategoryDefectNew.setVisible(false);
			btnEffortCategoryDefectNew.setDisable(true);

			if (ndxJlstEffortCategoryDefectSelector > -1) {
				// The list is not empty, and one item in the list is selected, so the Delete button is enabled.
				// (It is always visible.)
				btnEffortCategoryDefectDelete.setDisable(false);
			} else {
				// The list is not empty, but no item in the list is selected, so the Delete and Save buttons are
				// disabled (they are always visible) regardless of what is in
				// the input fields for the name and the description.
				btnEffortCategoryDefectDelete.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two or more items in the list and one item is selected
			if (numDefects < 2 || ndxJlstEffortCategoryDefectSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected neither of the two buttons should be enabled
				btnEffortCategoryDefectMvUp.setDisable(true);
				btnEffortCategoryDefectMvDn.setDisable(true);

			} else if (ndxJlstEffortCategoryDefectSelector == 0) {
				// If the first item in the list is selected, you can move down, but not up
				btnEffortCategoryDefectMvUp.setDisable(true);
				btnEffortCategoryDefectMvDn.setDisable(false);

			} else if (ndxJlstEffortCategoryDefectSelector == numDefects - 1) {
				// If the last item in the list is selected, you can move up but not down
				btnEffortCategoryDefectMvUp.setDisable(false);
				btnEffortCategoryDefectMvDn.setDisable(true);

			} else {
				// Otherwise both options (up and down) are valid.
				btnEffortCategoryDefectMvUp.setDisable(false);
				btnEffortCategoryDefectMvDn.setDisable(false);
			}
		}

		// The following is the second step... Determine the enabled status of the insert buttons.
		ndxJlstEffortCategoryDefectSelector = jlstEffortCategoryDefect
				.getSelectionModel().getSelectedIndex();
		if (lstLocalEffortCategoryDefectList.getNumberOfDefects() > 0) {
			// The list is not empty
			if (ndxJlstEffortCategoryDefectSelector == -1
					|| ndxCmbEffortCategoryNewDefectSelector == -1) {
				// The list is not empty, but no item is selected or no ComboBox
				// item is selected, so it is not
				// possible to insert and artifact above or below.
				btnEffortCategoryDefectAbove.setDisable(true);
				btnEffortCategoryDefectBelow.setDisable(true);
			} else {
				// A non-empty list with an item selected and a selected item in
				// the ComboBox, so it is possible to insert above or below
				btnEffortCategoryDefectAbove.setDisable(false);
				btnEffortCategoryDefectBelow.setDisable(false);
			}
		} else {
			// The list is empty, so the we need to see if the empty list insert
			// should be enabled
			if (ndxCmbEffortCategoryNewDefectSelector == -1)
				// If the ComboBox does not have a selected item, then no insert is possible
				btnEffortCategoryDefectNew.setDisable(true);
			else
				// Empty list, but the ComboxBox has a selected item, so it
				// could be inserted into the list
				btnEffortCategoryDefectNew.setDisable(false);

			// Since the list is empty, it is not possible to have a selected
			// element, so above or below is not possible
			btnEffortCategoryDefectAbove.setDisable(true);
			btnEffortCategoryDefectBelow.setDisable(true);
		}
	}

	
	
	/****************************************************************************************************************
	 * 
	 * Plans
	 */

	public void Planselection() {

		int a = jlstPlans.getSelectionModel().getSelectedIndex();
		if (lglPlanListIsBeingUpdated)
			return;
		jltPlansListItemSelected();

	}

	/**
	 * This method is called each time the Plans Tab is activated.
	 */

	public void performWakeUpPlansPane() {
		System.out.println("Wake up the Plans tabbed pane");

		lstPlansSelectListSource = lstPlanList.buildSelectList();
		lglPlanListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstPlansSelectListSource);
		jlstPlans.setItems(items);
		lglPlanListIsBeingUpdated = false;
		if (ndxJlstPlanSelector == -1) {
			jlstPlans.getSelectionModel().clearSelection();
		} else
			jlstPlans.getSelectionModel().select(ndxJlstPlanSelector);
		checkPlanButtons();

	}

	/**
	 * Insert an Plan into an empty JList.
	 */
	public void performPlansNew() {

		// This method is only used for inserting the first item into an empty JList. (Once
		// the JList has an item in it, the user must specify whether to insert above or
		// below the selected list item.)
		lstPlanList.addPlan(strLocalPlanName, strLocalPlanDesc);
		// After an insert, the just inserted item is the selected item. Since the list was
		// empty just before this insert, there is now just one item in the list and that item's
		// index is zero. (Remember this is Java and the first item in a Java list has an index of zero.

		// These commands set the first item in the list to be selected
		ndxJlstPlanSelector = 0;
		commonUpdateAfterAPlanInsert(); // Update the GUI to reflect this change
	}

	/**
	 * Insert the new Plan *above* the current selected Plan in the JList.
	 */
	public void performPlansNewAbove() {
		// Create a new plan and insert it above the current selected plan.
		// Using the
		// lstPlansSelector, as is, is what causes the above to occur. Otherwise
		// this is
		// exactly the same as the performPlansNewBelow method
		lstPlanList.addPlan(ndxJlstPlanSelector, strLocalPlanName,
				strLocalPlanDesc);
		// We want the just inserted item to be the currently selected item, so
		// we have to update
		// the selector to be this new item. Since we inserted above, the index
		// of the selected
		// item will be the same.

		ndxJlstPlanSelector++;
		commonUpdateAfterAPlanInsert(); // Update the GUI to reflect this change

	}

	/**
	 * Insert the new Plan *below* the current selected Plan in the JList.
	 */

	public void performPlansNewBelow() {
		// Create a new plan and insert it below the current selected plan. The
		// "+1"
		// for the lstPlansSelector is what causes the below to occur. Otherwise
		// this is
		// exactly the same as the performPlansNewAbove method
		lstPlanList.addPlan(ndxJlstPlanSelector + 1, strLocalPlanName,
				strLocalPlanDesc);
		// We want the just inserted item to be the currently selected item, so
		// we have to update
		// the selector to be one more than it was.
		ndxJlstPlanSelector++;
		commonUpdateAfterAPlanInsert(); // Update the GUI to reflect this change
	}

	/*****
	 * Save changes made to the selected item. This is done by updating the
	 * already stored object as opposed to creating a new one, so all of the old
	 * references to the plan will se the results of the changes that were made.
	 * (Please see how PlanListController.replacePlan accomplishes this!)
	 */

	public void performPlansSave() {
		// We know that this method can only be called when the button is
		// enabled and that means
		// that an plan was selected and the input fields do not match what has
		// been stored.
		// Therefore, we need to update the stored list to match this updated plan.
		// We blindly update both the name and the description, even if only one
		// has been changed.
		lstPlanList.replacePlan(ndxJlstPlanSelector, strLocalPlanName,
				strLocalPlanDesc);
		// Update the GUI to reflect this change
		commonUpdateAfterAPlanInsert();
	}

	/**
	 * Move the current selected Plan up one position
	 */
	public void performPlanMvUp() {
		lstPlanList.movePlanUp(ndxJlstPlanSelector); // Move the plan up in the  actual list
		ndxJlstPlanSelector--; // Keep the same item selected

		commonUpdateAfterAPlanMove();// Update the GUI to reflect this change
	}

	/**
	 * Move the current selected Plan down one position
	 */
	public void performPlanMvDn() {
		lstPlanList.movePlanDn(ndxJlstPlanSelector); // Move the plan up in the
														// actual list
		ndxJlstPlanSelector++; // Keep the same item selected
		commonUpdateAfterAPlanMove(); // Update the GUI to reflect this change
	}

	private void commonUpdateAfterAPlanMove() {
		// The list has changed so we need to build a new list.
		lstPlansSelectListSource = lstPlanList.buildSelectList();
		// We can't just change the JList as that causes a new cascade of events which results
		// in undesirable side effects. The easiest way to avoid them is to  ignore them. To do
		// that, we first have have to set a flag informing ourself that we are  updating the JList,
		// so that handler will just ignore events, until we are done. Then we do the update.
		// When we are done, we then inform ourselves that we are done doing the  update and the
		// event handler should not process any events that are generated.
		lglPlanListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstPlansSelectListSource);
		jlstPlans.setItems(items);
		lglPlanListIsBeingUpdated = false;
		jlstPlans.getSelectionModel().select(ndxJlstPlanSelector); 
		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkPlanButtons();
	}

	public void performPlansDelete() {

		// We know that it is only possible to get here when an Plan in the
		// list has been selected, or the button would have been disabled. So no
		// checking is needed.
		lstPlanList.deletePlan(ndxJlstPlanSelector);

		// Now that the Plan has been deleted, we need to clean up the UI
		// to match this state.

		// After a delete, no item is selected, so that is what these statements
		// do
		ndxJlstPlanSelector = -1;
		jlstPlans.getSelectionModel().clearSelection();

		// The list has changed so we need to build a new list.
		lstPlansSelectListSource = lstPlanList.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglPlanListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstPlansSelectListSource);
		jlstPlans.setItems(items);
		lglPlanListIsBeingUpdated = false;

		// We need to clear the local copies as there is no current selected
		// item
		strSelectedPlanName = "";
		strSelectedPlanDesc = "";

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkPlanButtons();

	}

	public void performPlansClear() {
		ndxJlstPlanSelector = -1; // Reset the local copy of the current index
		jlstPlans.getSelectionModel().clearSelection(); // Clear the JList
		// selection
		fldPlanName.setText(""); // Clear the Name input field
		strLocalPlanName = ""; // and the local copy
		fldPlanDesc.setText(""); // Clear the Description input field
		strLocalPlanDesc = ""; // and the local copy

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkPlanButtons();
	}

	private void jltPlansListItemSelected() {
		@SuppressWarnings("unchecked")
		// The language can't be sure that this cast
		// is safe even though *we* know no other GUI
		// component uses this method.
		// Fetch the selected index from the JList.
		int ndx = jlstPlans.getSelectionModel().getSelectedIndex();
		// If the index matches the current local copies, there is no need to
		// proceed
		if (ndxJlstPlanSelector == ndx)
			return;
		// If the index is different, then we must update the local copies
		// Remember the new index
		ndxJlstPlanSelector = ndx;

		// Establish the local copies for the Name field
		strSelectedPlanName = lstPlanList.getPlan(ndx).getName();
		strLocalPlanName = strSelectedPlanName;
		fldPlanName.setText(strLocalPlanName);

		// Establish the local copies for the Description field
		strSelectedPlanDesc = lstPlanList.getPlan(ndx).getDesc();
		strLocalPlanDesc = strSelectedPlanDesc;
		fldPlanDesc.setText(strLocalPlanDesc);

		// Changing of these fields may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkPlanButtons();
	}

	public void rtnCheckFldPlansNewName() {
		strLocalPlanName = fldPlanName.getText();
		checkPlanButtons();
	}

	public void rtnCheckFldPlansNewDesc() {
		strLocalPlanDesc = fldPlanDesc.getText();
		checkPlanButtons();
	}

	public void commonUpdateAfterAPlanInsert() {
		// The list has changed so we need to build a new list.
		lstPlansSelectListSource = lstPlanList.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglPlanListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstPlansSelectListSource);
		jlstPlans.setItems(items);
		lglPlanListIsBeingUpdated = false;

		// To insure the just updated item remains selected, we must issue this
		// command
		jlstPlans.getSelectionModel().select(ndxJlstPlanSelector);

		// We also need to updated the local copies of the selected attribute
		strSelectedPlanName = strLocalPlanName;
		strSelectedPlanDesc = strLocalPlanDesc;

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkPlanButtons();
	}

	private void checkPlanButtons() {

		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the exception of the enabled status for
		// the single large Insert, the Insert Above, and the Insert Below
		// buttons
		// which are processed in the second step below.

		// This is the first step in the process.
		int numPlans = lstPlanList.getNumberOfPlans();
		if (numPlans == 0) {
			// If the list is empty, only one insert button is visible as well
			// as
			// the save and delete buttons. The save and delete should be
			// disabled
			// The semantics of the enabled and disabled status of the Insert
			// buttons is more complex, so it is handled at the end of this
			// method.
			btnPlansNew.setVisible(true);
			btnPlansNewAbove.setDisable(true);
			btnPlansNewAbove.setVisible(false);
			btnPlansNewBelow.setDisable(true);
			btnPlansNewBelow.setVisible(false);
			btnPlansDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for inserting below the selected
			// item.
			// So we start by making the insert above and below buttons visible
			// and make the single insert button invisible and disabled.
			btnPlansNewAbove.setVisible(true);
			btnPlansNewBelow.setVisible(true);
			btnPlansNew.setVisible(false);
			btnPlansNew.setDisable(true);

			if (ndxJlstPlanSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled. (It is always visible.)
				btnPlansDelete.setDisable(false);
				if (strSelectedPlanName.equals(strLocalPlanName)
						&& strSelectedPlanDesc.equals(strLocalPlanDesc)) {
					// The list is not empty, one item in the list is selected,
					// and both the name and description input fields match
					// the fields stored in the data structure, so there is
					// no reason to perform a Save operation and therefore that
					// button is visible but disabled.
					btnPlansSave.setDisable(true);
				} else {
					// The list is not empty, one item in the list is selected,
					// but there is a a difference between the name and
					// description
					// input fields and the fields stored in the data structure,
					// so there may be a need to perform a Save operation and
					// therefore the button is visible and enabled.
					btnPlansSave.setDisable(false);
				}
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are disabled (they are always
				// visible) regardless of what is in the input fields fore the
				// name and the description.
				btnPlansDelete.setDisable(true);
				btnPlansSave.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two
			// or more items in the list and one item is selected
			if (numPlans < 2 || ndxJlstPlanSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected
				// neither of the two buttons should be enabled
				btnPlansMvUp.setDisable(true);
				btnPlansMvDn.setDisable(true);

			} else if (ndxJlstPlanSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnPlansMvUp.setDisable(true);
				btnPlansMvDn.setDisable(false);

			} else if (ndxJlstPlanSelector == numPlans - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnPlansMvUp.setDisable(false);
				btnPlansMvDn.setDisable(true);

			} else {
				// Otherwise both options are valid.
				btnPlansMvUp.setDisable(false);
				btnPlansMvDn.setDisable(false);
			}
		}

		// The following is the second step.

		// The remaining code is about the enabled or disabled status of the
		// insert buttons.
		// Visibility has been handled above as has the enabled status for
		// Delete and Save.
		// This code deals with the visibility of the three insert buttons.
		if (lstPlanList.getNumberOfPlans() == 0
				&& strLocalPlanName.length() == 0) {
			// The list is empty and the user input name field is empty, so the
			// single
			// Insert button is visible, but it is disabled. The two insert
			// buttons were
			// disabled above and their visibility was handled above.
			btnPlansNew.setDisable(true);
		} else if (lstPlanList.getNumberOfPlans() == 0
				&& strLocalPlanName.length() > 0) {
			// The list is empty, but the user input name field is not, so the
			// single
			// Insert button is visible and enabled. The two Insert buttons are
			// not visible
			// (from above) and are disabled.
			btnPlansNew.setDisable(false);
		} else if (lstPlanList.getNumberOfPlans() > 0
				&& strLocalPlanName.length() == 0) {
			// The list is not empty, but the user input name field is empty, so
			// the two
			// Insert buttons are visible and enabled. The single Insert button
			// is not visible
			// and disabled from above).
			btnPlansNewAbove.setDisable(true);
			btnPlansNewBelow.setDisable(true);
		} else if (lstPlanList.getNumberOfPlans() > 0
				&& strLocalPlanName.length() > 0) {
			// The list is not empty and the user input name field is not empty,
			// so the status of
			// the two Insert buttons in terms of enabled or not is determined
			// by where or not
			// an item in the list has been selected. We do know that the single
			// insert was
			// made not visible and disabled above, so no need to do it here.
			if (ndxJlstPlanSelector == -1) {
				// No item is selected, so it is not possible to insert above or
				// below it.
				btnPlansNewAbove.setDisable(true);
				btnPlansNewBelow.setDisable(true);
			} else {
				// An item is selected, so it is possible to insert above or
				// below it
				btnPlansNewAbove.setDisable(false);
				btnPlansNewBelow.setDisable(false);
			}
		}
	}

	
	/***************************************************************************************************************
	 * 
	 * Artifacts
	 */
	
	public void Artifactselection() {
		int a = jlstArtifacts.getSelectionModel().getSelectedIndex();
		if (lglArtifactListIsBeingUpdated)
			return;
		jltArtifactsListItemSelected();
	}

	/**
	 * This method is called each time the Artifacts Tab is activated.
	 */
	public void performWakeUpArtifactsPane() {

		System.out.println("Wake up the Artifacts tabbed pane");
		lstArtifactsSelectListSource = lstArtifactList.buildSelectList();
		lglArtifactListIsBeingUpdated = true;

		ObservableList<String> items = FXCollections
				.observableArrayList(lstArtifactsSelectListSource);
		jlstArtifacts.setItems(items);
		lglArtifactListIsBeingUpdated = false;
		if (ndxJlstArtifactSelector == -1) {

			jlstArtifacts.getSelectionModel().clearSelection();
		} else
			jlstArtifacts.getSelectionModel().select(ndxJlstArtifactSelector);
		checkArtifactButtons();

	}

	/**
	 * Insert an Artifact into an empty JList.
	 */
	public void performArtifactsNew() {
		// This method is only used for inserting the first item into an empty
		// JList. (Once
		// the JList has an item in it, the user must specify whether to insert
		// above or
		// below the selected list item.)
		lstArtifactList.addArtifact(strLocalArtifactName, strLocalArtifactDesc);
		// After an insert, the just inserted item is the selected item. Since
		// the list was
		// empty just before this insert, there is now just one item in the list
		// and that item's
		// index is zero. (Remember this is Java and the first item in a Java
		// list has an index
		// of zero.

		// These commands set the first item in the list to be selected
		ndxJlstArtifactSelector = 0;
		commonUpdateAfterAArtifactInsert(); // Update the GUI to reflect the
											// just made changes
	}

	/**
	 * Insert the new Artifact *above* the current selected Artifact in the
	 * JList.
	 */
	public void performArtifactsNewAbove() {
		// Create a new artifact and insert it above the current selected
		// artifact. Using the
		// lstArtifactsSelector, as is, is what causes the above to occur.
		// Otherwise this is
		// exactly the same as the performArtifactsNewBelow method
		lstArtifactList.addArtifact(ndxJlstArtifactSelector,
				strLocalArtifactName, strLocalArtifactDesc);
		ndxJlstArtifactSelector++;
		// We want the just inserted item to be the currently selected item, so
		// we have to update
		// the selector to be this new item. Since we inserted above, the index
		// of the selected
		// item will be the same.

		// Update the GUI to reflect the just made changes
		commonUpdateAfterAArtifactInsert();
	}

	public void performArtifactsNewBelow() {
		// Create a new artifact and insert it above the current selected
		// artifact. Using the
		// lstArtifactsSelector, as is, is what causes the above to occur.
		// Otherwise this is
		// exactly the same as the performArtifactsNewBelow method
		lstArtifactList.addArtifact(ndxJlstArtifactSelector + 1,
				strLocalArtifactName, strLocalArtifactDesc);
		ndxJlstArtifactSelector++;
		// We want the just inserted item to be the currently selected item, so
		// we have to update
		// the selector to be this new item. Since we inserted above, the index
		// of the selected
		// item will be the same.

		// Update the GUI to reflect the just made changes
		commonUpdateAfterAArtifactInsert();
	}

	/*****
	 * Save changes made to the selected item. This is done by updating the
	 * already stored object as opposed to creating a new one, so all of the old
	 * references to the artifact will se the results of the changes that were
	 * made. (Please see how ArtifactListController.replaceArtifact accomplishes
	 * this!)
	 */
	public void performArtifactsSave() {
		// We know that this method can only be called when the button is
		// enabled and that means
		// that an artifact was selected and the input fields do not match what
		// has been stored.
		// Therefore, we need to update the stored list to match this updated
		// artifact. We
		// blindly update both the name and the description, even if only one
		// has been changed.
		lstArtifactList.replaceArtifact(ndxJlstArtifactSelector,
				strLocalArtifactName, strLocalArtifactDesc);
		commonUpdateAfterAArtifactInsert(); // Update the GUI to reflect the
											// just made changes
	}

	/**
	 * Move the current selected Artifact up one position
	 */
	public void performArtifactMvUp() {
		lstArtifactList.moveArtifactUp(ndxJlstArtifactSelector); 
		ndxJlstArtifactSelector--; // Keep the same item selected

		commonUpdateAfterAArtifactMove(); // Update the GUI to reflect the
											// change that was just made
	}

	public void performArtifactMvDn() {
		lstArtifactList.moveArtifactDn(ndxJlstArtifactSelector); 
		ndxJlstArtifactSelector++; // Keep the same item selected
		commonUpdateAfterAArtifactMove(); // Update the GUI to reflect the
											// change that was just made
	}

	private void commonUpdateAfterAArtifactMove() {
		// The list has changed so we need to build a new list.
		lstArtifactsSelectListSource = lstArtifactList.buildSelectList();
		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglArtifactListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstArtifactsSelectListSource);
		jlstArtifacts.setItems(items);
		lglArtifactListIsBeingUpdated = false;
		jlstArtifacts.getSelectionModel().select(ndxJlstArtifactSelector); 
		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkArtifactButtons();
	}

	public void performArtifactsDelete() {
		// We know that it is only possible to get here when an Artifact in the
		// list has been selected, or the button would have been disabled. So no
		// checking is needed.
		lstArtifactList.deleteArtifact(ndxJlstArtifactSelector);

		// Now that the Artifact has been deleted, we need to clean up the UI
		// to match this state.

		// After a delete, no item is selected, so that is what these statements
		// do
		ndxJlstArtifactSelector = -1;
		ObservableList<String> items1 = FXCollections.observableArrayList("");

		jlstArtifacts.setItems(items1);

		// The list has changed so we need to build a new list.
		lstArtifactsSelectListSource = lstArtifactList.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglArtifactListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstArtifactsSelectListSource);
		jlstArtifacts.setItems(items);
		lglArtifactListIsBeingUpdated = false;

		// We need to clear the local copies as there is no current selected
		// item
		strSelectedArtifactName = "";
		strSelectedArtifactDesc = "";

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkArtifactButtons();

	}

	public void performArtifactsClear() {

		ndxJlstArtifactSelector = -1; // Reset the local copy of the current
		// index
		// ObservableList<String> items =FXCollections.observableArrayList ("");
		// jlstArtifacts.setItems(items); // Clear the JList selection
		fldArtifactName.setText(""); // Clear the Name input field
		strLocalArtifactName = ""; // and the local copy
		fldArtifactDesc.setText(""); // Clear the Description input field
		strLocalArtifactDesc = ""; // and the local copy

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkArtifactButtons();
	}

	private void jltArtifactsListItemSelected() {
		@SuppressWarnings("unchecked")
		// The language can't be sure that this cast
		// is safe even though *we* know no other GUI
		// component uses this method.
		// Fetch the selected index from the JList.
		int ndx = jlstArtifacts.getSelectionModel().getSelectedIndex();

		// If the index matches the current local copies, there is no need to
		// proceed
		if (ndxJlstArtifactSelector == ndx)
			return;

		// If the index is different, then we must update the local copies
		// Remember the new index
		ndxJlstArtifactSelector = ndx;

		// Establish the local copies for the Name field
		strSelectedArtifactName = lstArtifactList.getArtifact(ndx).getName();
		strLocalArtifactName = strSelectedArtifactName;
		fldArtifactName.setText(strLocalArtifactName);

		// Establish the local copies for the Description field
		strSelectedArtifactDesc = lstArtifactList.getArtifact(ndx).getDesc();
		strLocalArtifactDesc = strSelectedArtifactDesc;
		fldArtifactDesc.setText(strLocalArtifactDesc);

		// Changing of these fields may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkArtifactButtons();
	}

	public void rtnCheckFldArtifactsNewName() {
		strLocalArtifactName = fldArtifactName.getText();
		checkArtifactButtons();
	}

	public void rtnCheckFldArtifactsNewDesc() {
		strLocalArtifactDesc = fldArtifactDesc.getText();
		checkArtifactButtons();
	}

	public void commonUpdateAfterAArtifactInsert() {
		// The list has changed so we need to build a new list.
		lstArtifactsSelectListSource = lstArtifactList.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglArtifactListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstArtifactsSelectListSource);
		jlstArtifacts.setItems(items);
		lglArtifactListIsBeingUpdated = false;

		// To insure the just updated item remains selected, we must issue this
		// command
		jlstArtifacts.getSelectionModel().select(ndxJlstArtifactSelector);

		// We also need to updated the local copies of the selected attribute
		strSelectedArtifactName = strLocalArtifactName;
		strSelectedArtifactDesc = strLocalArtifactDesc;

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkArtifactButtons();
	}

	private void checkArtifactButtons() {

		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the exception of the enabled status for
		// the single large Insert, the Insert Above, and the Insert Below
		// buttons
		// which are processed in the second step below.

		// This is the first step in the process.
		int numArtifacts = lstArtifactList.getNumberOfArtifacts();
		if (numArtifacts == 0) {
			// If the list is empty, only one insert button is visible as well
			// as
			// the save and delete buttons. The save and delete should be
			// disabled
			// The semantics of the enabled and disabled status of the Insert
			// buttons is more complex, so it is handled at the end of this
			// method.
			btnArtifactsNew.setVisible(true);
			btnArtifactsNewAbove.setDisable(true);
			btnArtifactsNewAbove.setVisible(false);
			btnArtifactsNewBelow.setDisable(true);
			btnArtifactsNewBelow.setVisible(false);
			btnArtifactsDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for inserting below the selected
			// item.
			// So we start by making the insert above and below buttons visible
			// and make the single insert button invisible and disabled.
			btnArtifactsNewAbove.setVisible(true);
			btnArtifactsNewBelow.setVisible(true);
			btnArtifactsNew.setVisible(false);
			btnArtifactsNew.setDisable(true);

			if (ndxJlstArtifactSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled. (It is always visible.)
				btnArtifactsDelete.setDisable(false);
				if (strSelectedArtifactName.equals(strLocalArtifactName)
						&& strSelectedArtifactDesc.equals(strLocalArtifactDesc)) {
					// The list is not empty, one item in the list is selected,
					// and both the name and description input fields match
					// the fields stored in the data structure, so there is
					// no reason to perform a Save operation and therefore that
					// button is visible but disabled.
					btnArtifactsSave.setDisable(true);
				} else {
					// The list is not empty, one item in the list is selected,
					// but there is a a difference between the name and
					// description
					// input fields and the fields stored in the data structure,
					// so there may be a need to perform a Save operation and
					// therefore the button is visible and enabled.
					btnArtifactsSave.setDisable(false);
				}
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are disabled (they are always
				// visible) regardless of what is in the input fields fore the
				// name and the description.
				btnArtifactsDelete.setDisable(true);
				btnArtifactsSave.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two
			// or more items in the list and one item is selected
			if (numArtifacts < 2 || ndxJlstArtifactSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected
				// neither of the two buttons should be enabled
				btnArtifactsMvUp.setDisable(true);
				btnArtifactsMvDn.setDisable(true);

			} else if (ndxJlstArtifactSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnArtifactsMvUp.setDisable(true);
				btnArtifactsMvDn.setDisable(false);

			} else if (ndxJlstArtifactSelector == numArtifacts - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnArtifactsMvUp.setDisable(false);
				btnArtifactsMvDn.setDisable(true);

			} else {
				// Otherwise both options are valid.
				btnArtifactsMvUp.setDisable(false);
				btnArtifactsMvDn.setDisable(false);
			}
		}

		// The following is the second step.

		// The remaining code is about the enabled or disabled status of the
		// insert buttons.
		// Visibility has been handled above as has the enabled status for
		// Delete and Save.
		// This code deals with the visibility of the three insert buttons.
		if (lstArtifactList.getNumberOfArtifacts() == 0
				&& strLocalArtifactName.length() == 0) {
			// The list is empty and the user input name field is empty, so the
			// single
			// Insert button is visible, but it is disabled. The two insert
			// buttons were
			// disabled above and their visibility was handled above.
			btnArtifactsNew.setDisable(true);
		} else if (lstArtifactList.getNumberOfArtifacts() == 0
				&& strLocalArtifactName.length() > 0) {
			// The list is empty, but the user input name field is not, so the
			// single
			// Insert button is visible and enabled. The two Insert buttons are
			// not visible
			// (from above) and are disabled.
			btnArtifactsNew.setDisable(false);
		} else if (lstArtifactList.getNumberOfArtifacts() > 0
				&& strLocalArtifactName.length() == 0) {
			// The list is not empty, but the user input name field is empty, so
			// the two
			// Insert buttons are visible and enabled. The single Insert button
			// is not visible
			// and disabled from above).
			btnArtifactsNewAbove.setDisable(true);
			btnArtifactsNewBelow.setDisable(true);
		} else if (lstArtifactList.getNumberOfArtifacts() > 0
				&& strLocalArtifactName.length() > 0) {
			// The list is not empty and the user input name field is not empty,
			// so the status of
			// the two Insert buttons in terms of enabled or not is determined
			// by where or not
			// an item in the list has been selected. We do know that the single
			// insert was
			// made not visible and disabled above, so no need to do it here.
			if (ndxJlstArtifactSelector == -1) {
				// No item is selected, so it is not possible to insert above or
				// below it.
				btnArtifactsNewAbove.setDisable(true);
				btnArtifactsNewBelow.setDisable(true);
			} else {
				// An item is selected, so it is possible to insert above or
				// below it
				btnArtifactsNewAbove.setDisable(false);
				btnArtifactsNewBelow.setDisable(false);
			}
		}
	}

	
	
	/*****************************************************************************************************
	 * Interruptions
	 */

	public void performWakeUpInterruptionsPane() {
		// this function is performed whenever the interruption tab is selected
		System.out.println("Wake up the Interruptions tabbed pane");
		lstInterruptionsSelectListSource = lstInterruptionList
				.buildSelectList();
		lglInterruptionListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstInterruptionsSelectListSource);
		jlstInterruptions.setItems(items);
		lglInterruptionListIsBeingUpdated = false;
		if (ndxJlstInterruptionSelector == -1) {
			jlstInterruptions.getSelectionModel().clearSelection();
		} else
			jlstInterruptions.getSelectionModel().select(
					ndxJlstInterruptionSelector);
		checkInterruptionButtons();

	}

	public void Interruptionselection() {

		int a = jlstInterruptions.getSelectionModel().getSelectedIndex();
		if (lglInterruptionListIsBeingUpdated)
			return;
		jltInterruptionsListItemSelected();

	}

	public void performInterruptionsNew() {
		// insertion is done at the starting index
		lstInterruptionList.addInterruption(strLocalInterruptionName,
				strLocalInterruptionDesc);
		ndxJlstInterruptionSelector = 0;
		commonUpdateAfterAInterruptionInsert();
	}

	public void performInterruptionsNewAbove() {
		// insertion is done above the selected index
		lstInterruptionList.addInterruption(ndxJlstInterruptionSelector,
				strLocalInterruptionName, strLocalInterruptionDesc);
		ndxJlstInterruptionSelector++;
		commonUpdateAfterAInterruptionInsert();
	}

	public void performInterruptionsNewBelow() {
		// insertion is done below the selected index
		lstInterruptionList.addInterruption(ndxJlstInterruptionSelector + 1,
				strLocalInterruptionName, strLocalInterruptionDesc);
		ndxJlstInterruptionSelector++;
		commonUpdateAfterAInterruptionInsert();
	}

	public void performInterruptionMvUp() {
		// list index is selected and item is moved up
		lstInterruptionList.moveInterruptionUp(ndxJlstInterruptionSelector);
		ndxJlstInterruptionSelector--;

		commonUpdateAfterAInterruptionMove();
	}

	public void performInterruptionMvDn() {
		// list index is selected and item is moved down
		lstInterruptionList.moveInterruptionDn(ndxJlstInterruptionSelector);
		ndxJlstInterruptionSelector++;
		commonUpdateAfterAInterruptionMove();
	}

	private void commonUpdateAfterAInterruptionMove() {
		// this function is performed to update the lists after insertion
		lstInterruptionsSelectListSource = lstInterruptionList
				.buildSelectList();
		lglInterruptionListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstInterruptionsSelectListSource);
		jlstInterruptions.setItems(items);
		lglInterruptionListIsBeingUpdated = false;
		jlstInterruptions.getSelectionModel().select(
				ndxJlstInterruptionSelector);
		checkInterruptionButtons();
	}

	public void performInterruptionsDelete() {
		// We know that it is only possible to get here when an Interruption in
		// the
		// list has been selected, or the button would have been disabled. So no
		// checking is needed.
		lstInterruptionList.deleteInterruption(ndxJlstInterruptionSelector);

		// Now that the Interruption has been deleted, we need to clean up the
		// UI
		// to match this state.

		ndxJlstInterruptionSelector = -1;
		jlstInterruptions.getSelectionModel().clearSelection();

		// The list has changed so we need to build a new list.
		lstInterruptionsSelectListSource = lstInterruptionList
				.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglInterruptionListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstInterruptionsSelectListSource);
		jlstInterruptions.setItems(items);
		lglInterruptionListIsBeingUpdated = false;

		// We need to clear the local copies as there is no current selected
		// item
		strSelectedInterruptionName = "";
		strSelectedInterruptionDesc = "";

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkInterruptionButtons();

	}

	public void performInterruptionsClear() {

		ndxJlstInterruptionSelector = -1; // Reset the local copy of the current
		jlstInterruptions.getSelectionModel().clearSelection();
		fldInterruptionName.setText(""); // Clear the Name input field
		strLocalInterruptionName = ""; // and the local copy
		fldInterruptionDesc.setText(""); // Clear the Description input field
		strLocalInterruptionDesc = ""; // and the local copy

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkInterruptionButtons();
	}

	private void jltInterruptionsListItemSelected() {
		@SuppressWarnings("unchecked")
		// The language can't be sure that this cast
		// is safe even though *we* know no other GUI
		// component uses this method.
		// Fetch the selected index from the JList.
		int ndx = jlstInterruptions.getSelectionModel().getSelectedIndex();

		// If the index matches the current local copies, there is no need to
		// proceed
		if (ndxJlstInterruptionSelector == ndx)
			return;

		// If the index is different, then we must update the local copies
		// Remember the new index
		ndxJlstInterruptionSelector = ndx;

		// Establish the local copies for the Name field
		strSelectedInterruptionName = lstInterruptionList.getInterruption(ndx)
				.getName();
		strLocalInterruptionName = strSelectedInterruptionName;
		fldInterruptionName.setText(strLocalInterruptionName);

		// Establish the local copies for the Description field
		strSelectedInterruptionDesc = lstInterruptionList.getInterruption(ndx)
				.getDesc();
		strLocalInterruptionDesc = strSelectedInterruptionDesc;
		fldInterruptionDesc.setText(strLocalInterruptionDesc);

		// Changing of these fields may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkInterruptionButtons();
	}

	public void rtnCheckFldInterruptionsNewName() {
		// checks the text field description
		strLocalInterruptionName = fldInterruptionName.getText();
		checkInterruptionButtons();
	}

	public void rtnCheckFldInterruptionsNewDesc() {
		// checks the text filed name
		strLocalInterruptionDesc = fldInterruptionDesc.getText();
		checkInterruptionButtons();
	}

	public void performInterruptionsSave() {
		// Saves any changes applied to the values
		lstInterruptionList.replaceInterruption(ndxJlstInterruptionSelector,
				strLocalInterruptionName, strLocalInterruptionDesc);
		commonUpdateAfterAInterruptionInsert();
	}

	public void commonUpdateAfterAInterruptionInsert() {
		// The list has changed so we need to build a new list.
		lstInterruptionsSelectListSource = lstInterruptionList
				.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglInterruptionListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstInterruptionsSelectListSource);
		jlstInterruptions.setItems(items);
		lglInterruptionListIsBeingUpdated = false;

		// To insure the just updated item remains selected, we must issue this
		// command
		jlstInterruptions.getSelectionModel().select(
				ndxJlstInterruptionSelector);

		// We also need to updated the local copies of the selected attribute
		strSelectedInterruptionName = strLocalInterruptionName;
		strSelectedInterruptionDesc = strLocalInterruptionDesc;

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkInterruptionButtons();
	}

	private void checkInterruptionButtons() {

		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the exception of the enabled status for
		// the single large Insert, the Insert Above, and the Insert Below
		// buttons
		// which are processed in the second step below.

		// This is the first step in the process.
		int numInterruptions = lstInterruptionList.getNumberOfInterruptions();
		if (numInterruptions == 0) {
			// If the list is empty, only one insert button is visible as well
			// as
			// the save and delete buttons. The save and delete should be
			// disabled
			// The semantics of the enabled and disabled status of the Insert
			// buttons is more complex, so it is handled at the end of this
			// method.
			btnInterruptionsNew.setVisible(true);
			btnInterruptionsNewAbove.setDisable(true);
			btnInterruptionsNewAbove.setVisible(false);
			btnInterruptionsNewBelow.setDisable(true);
			btnInterruptionsNewBelow.setVisible(false);
			btnInterruptionsDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for inserting below the selected
			// item.
			// So we start by making the insert above and below buttons visible
			// and make the single insert button invisible and disabled.
			btnInterruptionsNewAbove.setVisible(true);
			btnInterruptionsNewBelow.setVisible(true);
			btnInterruptionsNew.setVisible(false);
			btnInterruptionsNew.setDisable(true);

			if (ndxJlstInterruptionSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled. (It is always visible.)
				btnInterruptionsDelete.setDisable(false);
				if (strSelectedInterruptionName
						.equals(strLocalInterruptionName)
						&& strSelectedInterruptionDesc
								.equals(strLocalInterruptionDesc)) {
					// The list is not empty, one item in the list is selected,
					// and both the name and description input fields match
					// the fields stored in the data structure, so there is
					// no reason to perform a Save operation and therefore that
					// button is visible but disabled.
					btnInterruptionsSave.setDisable(true);
				} else {
					// The list is not empty, one item in the list is selected,
					// but there is a a difference between the name and
					// description
					// input fields and the fields stored in the data structure,
					// so there may be a need to perform a Save operation and
					// therefore the button is visible and enabled.
					btnInterruptionsSave.setDisable(false);
				}
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are disabled (they are always
				// visible) regardless of what is in the input fields fore the
				// name and the description.
				btnInterruptionsDelete.setDisable(true);
				btnInterruptionsSave.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two
			// or more items in the list and one item is selected
			if (numInterruptions < 2 || ndxJlstInterruptionSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected
				// neither of the two buttons should be enabled
				btnInterruptionsMvUp.setDisable(true);
				btnInterruptionsMvDn.setDisable(true);

			} else if (ndxJlstInterruptionSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnInterruptionsMvUp.setDisable(true);
				btnInterruptionsMvDn.setDisable(false);

			} else if (ndxJlstInterruptionSelector == numInterruptions - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnInterruptionsMvUp.setDisable(false);
				btnInterruptionsMvDn.setDisable(true);

			} else {
				// Otherwise both options are valid.
				btnInterruptionsMvUp.setDisable(false);
				btnInterruptionsMvDn.setDisable(false);
			}
		}

		// The following is the second step.

		// The remaining code is about the enabled or disabled status of the
		// insert buttons.
		// Visibility has been handled above as has the enabled status for
		// Delete and Save.
		// This code deals with the visibility of the three insert buttons.
		if (lstInterruptionList.getNumberOfInterruptions() == 0
				&& strLocalInterruptionName.length() == 0) {
			// The list is empty and the user input name field is empty, so the
			// single
			// Insert button is visible, but it is disabled. The two insert
			// buttons were
			// disabled above and their visibility was handled above.
			btnInterruptionsNew.setDisable(true);
		} else if (lstInterruptionList.getNumberOfInterruptions() == 0
				&& strLocalInterruptionName.length() > 0) {
			// The list is empty, but the user input name field is not, so the
			// single
			// Insert button is visible and enabled. The two Insert buttons are
			// not visible
			// (from above) and are disabled.
			btnInterruptionsNew.setDisable(false);
		} else if (lstInterruptionList.getNumberOfInterruptions() > 0
				&& strLocalInterruptionName.length() == 0) {
			// The list is not empty, but the user input name field is empty, so
			// the two
			// Insert buttons are visible and enabled. The single Insert button
			// is not visible
			// and disabled from above).
			btnInterruptionsNewAbove.setDisable(true);
			btnInterruptionsNewBelow.setDisable(true);
		} else if (lstInterruptionList.getNumberOfInterruptions() > 0
				&& strLocalInterruptionName.length() > 0) {
			// The list is not empty and the user input name field is not empty,
			// so the status of
			// the two Insert buttons in terms of enabled or not is determined
			// by where or not
			// an item in the list has been selected. We do know that the single
			// insert was
			// made not visible and disabled above, so no need to do it here.
			if (ndxJlstInterruptionSelector == -1) {
				// No item is selected, so it is not possible to insert above or
				// below it.
				btnInterruptionsNewAbove.setDisable(true);
				btnInterruptionsNewBelow.setDisable(true);
			} else {
				// An item is selected, so it is possible to insert above or
				// below it
				btnInterruptionsNewAbove.setDisable(false);
				btnInterruptionsNewBelow.setDisable(false);
			}
		}
	}
	
	/****************************************************************************************************
	 * 
	 * Defects
	 */

	public void performWakeUpDefectsPane() {
		// this function is performed every time after the tab is selected
		System.out.println("Wake up the Defects tabbed pane");
		lstDefectsSelectListSource = lstDefectList.buildSelectList();
		lglDefectListIsBeingUpdated = true;

		ObservableList<String> items = FXCollections
				.observableArrayList(lstDefectsSelectListSource);
		jlstDefects.setItems(items);
		lglDefectListIsBeingUpdated = false;

		if (ndxJlstDefectSelector == -1) {
			jlstDefects.getSelectionModel().clearSelection();
		} else
			jlstDefects.getSelectionModel().select(ndxJlstDefectSelector);
		checkDefectButtons();

	}

	public void defectselection() {

		int a = jlstDefects.getSelectionModel().getSelectedIndex();
		if (lglDefectListIsBeingUpdated)
			return;
		jltDefectsListItemSelected();

	}

	public void performDefectsNew() {
		// inserting the items at the starting index
		lstDefectList.addDefect(strLocalDefectName, strLocalDefectDesc);
		ndxJlstDefectSelector = 0;
		commonUpdateAfterADefectInsert();
	}

	public void performDefectsNewAbove() {
		// inserting the items above the selected index
		lstDefectList.addDefect(ndxJlstDefectSelector, strLocalDefectName,
				strLocalDefectDesc);
		ndxJlstDefectSelector++;
		commonUpdateAfterADefectInsert();
	}

	public void performDefectsNewBelow() {
		// inserting the items below the selected index
		lstDefectList.addDefect(ndxJlstDefectSelector + 1, strLocalDefectName,
				strLocalDefectDesc);
		ndxJlstDefectSelector++;
		commonUpdateAfterADefectInsert();
	}

	public void performDefectMvUp() {
		// index is moved down after inserting above is selected
		lstDefectList.moveDefectUp(ndxJlstDefectSelector);
		ndxJlstDefectSelector--;
		commonUpdateAfterADefectMove();
	}

	public void performDefectMvDn() {
		// index is moved down after inserting below is selected
		lstDefectList.moveDefectDn(ndxJlstDefectSelector);
		ndxJlstDefectSelector++;
		commonUpdateAfterADefectMove();
	}

	private void commonUpdateAfterADefectMove() {
		// insertion is to be updated after the defect is moved down
		lstDefectsSelectListSource = lstDefectList.buildSelectList();
		lglDefectListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstDefectsSelectListSource);
		jlstDefects.setItems(items);
		lglDefectListIsBeingUpdated = false;
		jlstDefects.getSelectionModel().select(ndxJlstDefectSelector);
		checkDefectButtons();
	}

	public void performDefectsDelete() {
		// We know that it is only possible to get here when an defect in the
		// list has been selected, or the button would have been disabled. So no
		// checking is needed.
		lstDefectList.deleteDefect(ndxJlstDefectSelector);

		// Now that the defect has been deleted, we need to clean up the UI
		// to match this state.

		// After a delete, no item is selected, so that is what these statements
		// do
		ndxJlstDefectSelector = -1;
		jlstDefects.getSelectionModel().clearSelection();

		// The list has changed so we need to build a new list.
		lstDefectsSelectListSource = lstDefectList.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglDefectListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstDefectsSelectListSource);
		jlstDefects.setItems(items);
		lglDefectListIsBeingUpdated = false;

		// We need to clear the local copies as there is no current selected
		// item
		strSelectedDefectName = "";
		strSelectedDefectDesc = "";

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkDefectButtons();

	}

	public void performDefectsClear() {

		ndxJlstDefectSelector = -1; // Reset the local copy of the current index
		jlstDefects.getSelectionModel().clearSelection(); // Clear the JList
		// selection
		fldDefectName.setText(""); // Clear the Name input field
		strLocalDefectName = ""; // and the local copy
		fldDefectDesc.setText(""); // Clear the Description input field
		strLocalDefectDesc = ""; // and the local copy

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkDefectButtons();
	}

	private void jltDefectsListItemSelected() {
		@SuppressWarnings("unchecked")
		// The language can't be sure that this cast
		// is safe even though *we* know no other GUI
		// component uses this method.
		// Fetch the selected index from the JList.
		int ndx = jlstDefects.getSelectionModel().getSelectedIndex();

		// If the index matches the current local copies, there is no need to
		// proceed
		if (ndxJlstDefectSelector == ndx)
			return;

		// If the index is different, then we must update the local copies
		// Remember the new index
		ndxJlstDefectSelector = ndx;

		// Establish the local copies for the Name field
		strSelectedDefectName = lstDefectList.getDefect(ndx).getName();
		strLocalDefectName = strSelectedDefectName;
		fldDefectName.setText(strLocalDefectName);

		// Establish the local copies for the Description field
		strSelectedDefectDesc = lstDefectList.getDefect(ndx).getDesc();
		strLocalDefectDesc = strSelectedDefectDesc;
		fldDefectDesc.setText(strLocalDefectDesc);

		// Changing of these fields may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkDefectButtons();
	}

	public void rtnCheckFldDefectsNewName() {
		strLocalDefectName = fldDefectName.getText();
		checkDefectButtons();
	}

	public void rtnCheckFldDefectsNewDesc() {
		strLocalDefectDesc = fldDefectDesc.getText();
		checkDefectButtons();
	}

	public void performDefectsSave() {
		lstDefectList.replaceDefect(ndxJlstDefectSelector, strLocalDefectName,
				strLocalDefectDesc);
		commonUpdateAfterADefectInsert();
	}

	public void commonUpdateAfterADefectInsert() {
		// The list has changed so we need to build a new list.
		lstDefectsSelectListSource = lstDefectList.buildSelectList();

		// We can't just change the JList as that causes a new cascade of events
		// which results
		// in undesirable side effects. The easiest way to avoid them is to
		// ignore them. To do
		// that, we first have have to set a flag informing ourself that we are
		// updating the JList,
		// so that handler will just ignore events, until we are done. Then we
		// do the update.
		// When we are done, we then inform ourselves that we are done doing the
		// update and the
		// event handler should not process any events that are generated.
		lglDefectListIsBeingUpdated = true;
		ObservableList<String> items = FXCollections
				.observableArrayList(lstDefectsSelectListSource);
		jlstDefects.setItems(items);
		lglDefectListIsBeingUpdated = false;

		// To insure the just updated item remains selected, we must issue this
		// command
		jlstDefects.getSelectionModel().select(ndxJlstDefectSelector);

		// We also need to updated the local copies of the selected attribute
		strSelectedDefectName = strLocalDefectName;
		strSelectedDefectDesc = strLocalDefectDesc;

		// These changes may cause changes in the state of the five
		// buttons, so we process them all rather than try to reason about
		// which buttons might be affected.
		checkDefectButtons();
	}

	private void checkDefectButtons() {

		// This method processes the state in two steps. The first part deals
		// with all of the buttons with the exception of the enabled status for
		// the single large Insert, the Insert Above, and the Insert Below
		// buttons
		// which are processed in the second step below.

		// This is the first step in the process.
		int numDefects = lstDefectList.getNumberOfDefects();
		if (numDefects == 0) {
			// If the list is empty, only one insert button is visible as well
			// as
			// the save and delete buttons. The save and delete should be
			// disabled
			// The semantics of the enabled and disabled status of the Insert
			// buttons is more complex, so it is handled at the end of this
			// method.
			btnDefectsNew.setVisible(true);
			btnDefectsNewAbove.setDisable(true);
			btnDefectsNewAbove.setVisible(false);
			btnDefectsNewBelow.setDisable(true);
			btnDefectsNewBelow.setVisible(false);
			btnDefectsDelete.setDisable(true);
		} else {
			// If the list is not empty, then there are two insert buttons, one
			// for inserting above and one for inserting below the selected
			// item.
			// So we start by making the insert above and below buttons visible
			// and make the single insert button invisible and disabled.
			btnDefectsNewAbove.setVisible(true);
			btnDefectsNewBelow.setVisible(true);
			btnDefectsNew.setVisible(false);
			btnDefectsNew.setDisable(true);

			if (ndxJlstDefectSelector > -1) {
				// The list is not empty, and one item in the list is selected,
				// so the Delete button is enabled. (It is always visible.)
				btnDefectsDelete.setDisable(false);
				if (strSelectedDefectName.equals(strLocalDefectName)
						&& strSelectedDefectDesc.equals(strLocalDefectDesc)) {
					// The list is not empty, one item in the list is selected,
					// and both the name and description input fields match
					// the fields stored in the data structure, so there is
					// no reason to perform a Save operation and therefore that
					// button is visible but disabled.
					btnDefectsSave.setDisable(true);
				} else {
					// The list is not empty, one item in the list is selected,
					// but there is a a difference between the name and
					// description
					// input fields and the fields stored in the data structure,
					// so there may be a need to perform a Save operation and
					// therefore the button is visible and enabled.
					btnDefectsSave.setDisable(false);
				}
			} else {
				// The list is not empty, but no item in the list is selected,
				// so the Delete and Save buttons are disabled (they are always
				// visible) regardless of what is in the input fields fore the
				// name and the description.
				btnDefectsDelete.setDisable(true);
				btnDefectsSave.setDisable(true);
			}

			// The Move Up and Move Dn buttons are only enabled when there are
			// two
			// or more items in the list and one item is selected
			if (numDefects < 2 || ndxJlstDefectSelector == -1) {
				// If there are not at least two items in the list or no item is
				// selected
				// neither of the two buttons should be enabled
				btnDefectsMvUp.setDisable(true);
				btnDefectsMvDn.setDisable(true);

			} else if (ndxJlstDefectSelector == 0) {
				// If the first item in the list is selected, you can move down,
				// but not up
				btnDefectsMvUp.setDisable(true);
				btnDefectsMvDn.setDisable(false);

			} else if (ndxJlstDefectSelector == numDefects - 1) {
				// If the last item in the list is selected, you can move up but
				// not down
				btnDefectsMvUp.setDisable(false);
				btnDefectsMvDn.setDisable(true);

			} else {
				// Otherwise both options are valid.
				btnDefectsMvUp.setDisable(false);
				btnDefectsMvDn.setDisable(false);
			}
		}

		// The following is the second step.

		// The remaining code is about the enabled or disabled status of the
		// insert buttons.
		// Visibility has been handled above as has the enabled status for
		// Delete and Save.
		// This code deals with the visibility of the three insert buttons.
		if (lstDefectList.getNumberOfDefects() == 0
				&& strLocalDefectName.length() == 0) {
			// The list is empty and the user input name field is empty, so the
			// single
			// Insert button is visible, but it is disabled. The two insert
			// buttons were
			// disabled above and their visibility was handled above.
			btnDefectsNew.setDisable(true);
		} else if (lstDefectList.getNumberOfDefects() == 0
				&& strLocalDefectName.length() > 0) {
			// The list is empty, but the user input name field is not, so the
			// single
			// Insert button is visible and enabled. The two Insert buttons are
			// not visible
			// (from above) and are disabled.
			btnDefectsNew.setDisable(false);
		} else if (lstDefectList.getNumberOfDefects() > 0
				&& strLocalDefectName.length() == 0) {
			// The list is not empty, but the user input name field is empty, so
			// the two
			// Insert buttons are visible and enabled. The single Insert button
			// is not visible
			// and disabled from above).
			btnDefectsNewAbove.setDisable(true);
			btnDefectsNewBelow.setDisable(true);
		} else if (lstDefectList.getNumberOfDefects() > 0
				&& strLocalDefectName.length() > 0) {
			// The list is not empty and the user input name field is not empty,
			// so the status of
			// the two Insert buttons in terms of enabled or not is determined
			// by where or not
			// an item in the list has been selected. We do know that the single
			// insert was
			// made not visible and disabled above, so no need to do it here.
			if (ndxJlstDefectSelector == -1) {
				// No item is selected, so it is not possible to insert above or
				// below it.
				btnDefectsNewAbove.setDisable(true);
				btnDefectsNewBelow.setDisable(true);
			} else {
				// An item is selected, so it is possible to insert above or
				// below it
				btnDefectsNewAbove.setDisable(false);
				btnDefectsNewBelow.setDisable(false);
			}
		}
	}
}
