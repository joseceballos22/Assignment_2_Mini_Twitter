package mini.twitter;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * File: UserControlPanel.java
 * Goal: To define the UserControlPanel Logic and GUI
 *
 * */


/**
 * UserControlPanel will also implement the Observer Interface
 * Meaning Everytime A
 * */
public class UserControlPanel implements Observer
{
    /**Private Widget Fields */
    private TextArea followIDTextArea;
    private Button followButton;

    private ListView<User> usersFollowing; //Who the User is Following

    private TextArea tweetTextArea; //Message Box To Send Tweets
    private Button tweetButton; //Submit Button For Tweets

    private ListView<String> newsFeed; //News feed of all the users we are following

    //Labels to specify what each box does
    private Label followingLabel;
    private Label newsFeedLabel;


    /**Private Data Fields*/
    private User user;
    private Stage stage;
    private Scene scene;
    private Pane layout;

    //UserSubject which updates all the Observers associated with this User when it makes a tweet
    private UserSubject userSubject;

    //Keeps track of the number of followers
    private int numOfFollowers;

    private AdminControlPanelSingleton adminSingleton; //Used to Store a Reference to the SingleTon

    //Constructor
    public UserControlPanel(User user)
    {
        /**Initializing the Private Data Fields*/
        this.followIDTextArea = new TextArea();
        this.followIDTextArea.setPromptText("Enter The Name Of the Person To Follow Here");

        this.followButton = new Button("Follow User");

        this.usersFollowing = new ListView<>(); //List all the users its currently following
        this.followingLabel = new Label("Following");

        this.tweetTextArea = new TextArea();
        this.tweetTextArea.setPromptText("Enter The Tweet Message Here");
        this.tweetButton = new Button("Post Tweet");

        this.newsFeed = new ListView<>(); //List all the News Feed from Users Following
        this.newsFeedLabel = new Label("News Feed");

        this.user = user; //Saving a Reference to the specified User this Control Panel is Associated with

        this.stage = new Stage();
        this.layout = new Pane();
        //Linking the Layout and the Scene together
        this.scene = new Scene(layout, 1100,800);
        //Linking the Scene to the Stage
        this.stage.setScene(scene);

        this.stage.setTitle(user.getName() + "'s Control Panel");

        this.adminSingleton = AdminControlPanelSingleton.getInstance(); //Getting a Reference to the singleton

        this.userSubject = new UserSubject(); //Initializing the UserSubject So Everytime this User Makes a Tweet its observers will know

        this.numOfFollowers = 0; //Initially No Users have followed this user
    }

    /**
     * Starts the GUI Of the User Control Panel
     * */
    public void start()
    {
        //Defining the Positions of the Widgets in the Layout
        this.setWidgetPosition();

        //Adding the Widgets to the Layout
        this.layout.getChildren().addAll(followIDTextArea,followButton,usersFollowing,tweetTextArea,
                tweetButton, newsFeed,followingLabel,newsFeedLabel);

        /**
         *  Checking if the Follow User Button Was Clicked
         *  And Updating the Following and Followers ArrayList of the Users
         *  As Well as the ListViews
         *
         *  EveryTime a User gets followed the user which followed him is now observing him
         */
        this.followButton.setOnAction(e->{

            //Getting the User to follow
            User userToFollow = adminSingleton.getUser(this.followIDTextArea.getText());
            //Ensuring a Valid User
            if(userToFollow == null)
            {
                AlertBox.display("AlertBox", "Please Enter a Valid User To Follow");
            }
            else
            {
                //Following the User and seeing if it was successful
                boolean didWeFollow = this.user.followUser(userToFollow);
                //If We Followed the User
                if(didWeFollow)
                {
                    //Update the list view's
                    this.usersFollowing.getItems().add(userToFollow);

                    // Updating the userToFollow's followers ArrayList since it just got a new follower
                    userToFollow.addNewFollower(this.user); //Since this user is now following that user
                }
                //Else We did not follow the User because the User Was Already Following that user
                else
                {
                    AlertBox.display("AlertBox", "Error: User Is Already Following " + userToFollow.getName());
                }
            }

        });



        /**
         * Checking if the Tweet Button was pressed
         * - If so then Update all the news feed of all the Users that are following this user
         * By using the Observer Design pattern
         * */
        this.tweetButton.setOnAction(e->
        {
            /**
             * Registering All the Observers that follow this User
             * */

            //Now that we sorted all


        });

        //Starting the Stage
        this.stage.show();
    }

    //Sets the position of the widgets in the layout
    private void setWidgetPosition()
    {
        //List View Of all the users the current user is following
        followingLabel.setLayoutX(50);
        followingLabel.setLayoutY(20);
        usersFollowing.setLayoutX(50);
        usersFollowing.setLayoutY(50);


        //List View of all the news feed from the users the current user is following
        newsFeedLabel.setLayoutX(350);
        newsFeedLabel.setLayoutY(20);
        newsFeed.setLayoutX(350);
        newsFeed.setLayoutY(50);

        //Tweet Button and Tweet Text Area
        tweetTextArea.setLayoutX(50);
        tweetTextArea.setLayoutY(500);
        tweetButton.setLayoutX(50);
        tweetButton.setLayoutY(470);


        //Follow Button and Follow Text Area
        followIDTextArea.setLayoutX(550);
        followIDTextArea.setLayoutY(500);
        followButton.setLayoutX(550);
        followButton.setLayoutY(470);

    }

    /**
     * Updates the class that implements the Observer interface
     * Everytime its Associated Subject Updates the Data
     * <p>
     * Therefore When a User that this user is following post a new Message Its newsFeed gets Updated
     *
     * @param newMessage
     */
    @Override
    public void update(String newMessage)
    {

    }


}
