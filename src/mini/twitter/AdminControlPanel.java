package mini.twitter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * File:
 * Goal: To Define the Admin Panel GUI
 * */


public class AdminControlPanel extends Application
{
    /**Private Widget Data Fields */

    private TreeView<UserComponent> tree; //Used to Show Users and UserGroups
    private TreeItem<UserComponent> root; //Root Of the tree

    private TextArea userIdTextArea;
    private Button addUserButton;

    private TextArea groupIdTextArea;
    private Button addGroupButton;

    private Button openUserViewButton;

    private Button showUserTotalButton;
    private Button showMessageTotalButton;
    private Button showGroupTotalButton;
    private Button showPositivePercentageButton;


    /**Private Data Fields*/
    private TreeItem<UserComponent> currentSelectedUserGroup; //Holds the Currently Selected UserGroup
    private TreeItem<UserComponent> currentSelectedUser; //Holds the Currently Selected User Initially Null

    private AdminControlPanelSingleton adminSingleton = AdminControlPanelSingleton.getInstance(); // Getting a reference to the singleton


    /**Private Static Final Fields */
    private static final String USER_GROUP = "**";

    //Default Constructor
    public AdminControlPanel()
    {
        //Initializing Private Data Fields
        String rootName = USER_GROUP + "Root" + USER_GROUP;
        this.root = new TreeItem<UserComponent>(new UserGroup(rootName));
        this.root.setExpanded(true);
        this.tree = new TreeView(root);
        tree.setShowRoot(true);
        this.userIdTextArea = new TextArea();
        this.addUserButton = new Button("Add User");
        this.groupIdTextArea = new TextArea();
        this.addGroupButton = new Button("Add Group");
        this.openUserViewButton = new Button("Open Selected User Panel");
        this.showUserTotalButton = new Button("Show User Total");
        this.showMessageTotalButton = new Button("Show Message Total");
        this.showGroupTotalButton = new Button("Show Group Total");
        this.showPositivePercentageButton = new Button("Show Positive Percentage");

        //Setting the Size of the Private Widgets
        this.tree.setPrefSize(300,400);
        this.userIdTextArea.setPrefSize(200,50);
        this.groupIdTextArea.setPrefSize(200,50);

        //Initially The Currently Selected Item is the Root
        //It When No Item is Selected it will default to the Root
        this.currentSelectedUserGroup = this.root;

        this.currentSelectedUser = null; //Initially Null

    }


    /**
     * JavaFX Application Start Function Which starts the Admin Panel
     * - Defining GUI Stuff in this Function
     * */
    @Override
    public void start(Stage stage) throws Exception
    {
        Pane layout = new Pane(); //Defining the Layout Of the Stage
        //Adding all the Widgets to the Layout
        layout.getChildren().addAll(tree, userIdTextArea, addUserButton, groupIdTextArea, addGroupButton,
                openUserViewButton, showUserTotalButton, showMessageTotalButton, showGroupTotalButton, showPositivePercentageButton);

        //Positioning All the Widgets in the layout
        this.positionWidgets();

        //Checking if the user has selected another Tree Item In the Tree View
        this.tree.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {

                    //Checking if the User Has Selected another Tree Item By Default We assume its the Root
                    if(newValue != null)
                    {
                        //Ensuring the Newly selected tree item is a UserGroup
                        if(newValue.getValue() instanceof UserGroup)
                        {
                            this.currentSelectedUserGroup = newValue;//Updating the currently Selected TreeItem
                        }
                        else
                        {
                            this.currentSelectedUser = newValue; //Currently Selected User
                        }
                    }
                });

        /**Checking If Any Of the Buttons were Pressed*/

        //Checking if the Add User Button was pressed
        addUserButton.setOnAction(e -> {
            //Getting the User to add from the userIdTextArea
            String userToAdd = this.userIdTextArea.getText();
            //Trying to add the User to the program
            boolean isAdded = this.adminSingleton.addUser(userToAdd);
            //Added the User To the program
            if(isAdded)
            {
                //Updating our tree view
                this.makeUserBranch(userToAdd);
            }
            //Did not add the user to the program
            else
            {
                //User Was Not Added Therefore Display a AlertBox
                AlertBox.display("AlertBox", "Error User Is Already Added\nPlease Try To Add A Unique User" );
            }
        });

        //Checking if the Add User Group Button Was Pressed
        addGroupButton.setOnAction(e ->{
            //Getting the User Group to add from the groupIdTextArea
            String userGroupToAdd = this.groupIdTextArea.getText();
            //Trying to add the UserGroup to the program
            boolean isAdded = this.adminSingleton.addUserGroup(userGroupToAdd);
            //Added the User Group to the Program
            if(isAdded)
            {
                //Updating our Tree View
               this.makeUserGroupBranch(userGroupToAdd);
            }
            //Did Not Add the User Group to the program
            else
            {
                //User Group was Not added to the Program
                AlertBox.display("AlertBox", "Error User Group Is Already Added\nPlease Try To Add A Unique User Group ");
            }
        });

        //Checking if the openUserViewButton was pressed
        openUserViewButton.setOnAction(e-> {
            //First Checking if the currentSelectedUser is valid
            if(this.currentSelectedUser != null)
            {
                //Starting the userControl panel Specified
                new UserControlPanel((User)this.currentSelectedUser.getValue()).start();
            }
            else
            {
                //Not Valid
                AlertBox.display("AlertBox", "Please Select A User\nIn Order to go to the User Control Panel");
            }

        });


        Scene scene = new Scene(layout, 800, 800);
        stage.setTitle("Admin Panel");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Private Helper Method Which Adds Users To the Tree View
     * - Adds the User based on the currentSelectedTreeItem
     * - Which defaults to root if the user hasn't selected other Groups
     * */
    private void makeUserBranch(String name)
    {
        TreeItem<UserComponent> userToAdd = new TreeItem<UserComponent>(new User(name));
        userToAdd.setExpanded(true);
        this.currentSelectedUserGroup.getChildren().add(userToAdd);
    }

    /**
     * Private Helper Method which
     * - Adds User groups to the Tree View
     * - Adds the User Group to the Tree View Based on the currentSelectedTreeItem
     * - Which defaults to root if the user hasn't selected other Groups
     * */
    private void makeUserGroupBranch(String userGroupName)
    {
        String newUserGroupName = USER_GROUP + userGroupName + USER_GROUP;
        TreeItem<UserComponent> userGroupToAdd = new TreeItem<>(new UserGroup(newUserGroupName));
        userGroupToAdd.setExpanded(true);

        //Adding the UserGroup as a child to the currently selected Tree Item
        this.currentSelectedUserGroup.getChildren().add(userGroupToAdd);
    }

    //Positioning all the widgets
    private void positionWidgets()
    {
        tree.setLayoutX(0);
        tree.setLayoutY(0);

        userIdTextArea.setLayoutX(400);
        userIdTextArea.setLayoutY(100);
        addUserButton.setLayoutX(600);
        addUserButton.setLayoutY(100);

        groupIdTextArea.setLayoutX(400);
        groupIdTextArea.setLayoutY(200);
        addGroupButton.setLayoutX(600);
        addGroupButton.setLayoutY(200);

        openUserViewButton.setLayoutX(400);
        openUserViewButton.setLayoutY(300);

        showUserTotalButton.setLayoutX(400);
        showUserTotalButton.setLayoutY(500);

        showGroupTotalButton.setLayoutX(600);
        showGroupTotalButton.setLayoutY(500);

        showMessageTotalButton.setLayoutX(400);
        showMessageTotalButton.setLayoutY(600);

        showPositivePercentageButton.setLayoutX(600);
        showPositivePercentageButton.setLayoutY(600);
    }

}
