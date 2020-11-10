package mini.twitter;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * File: UserControlPanel.java
 * Goal: To define the UserControlPanel Logic and GUI
 * */


public class UserControlPanel
{
    /**Private Widget Fields */
    private TextArea followIDTextArea;
    private Button followButton;

    private ListView<User> usersFollowing; //Who the User is Following
    private ListView<User> userFollowers; //Followers of the User

    private TextArea tweetTextArea; //Message Box To Send Tweets
    private Button tweetButton; //Submit Button For Tweets

    private ListView<String> newsFeed; //News feed of all the users we are following

    //Labels to specify what each box does
    private Label followingLabel;
    private Label newsFeedLabel;
    private Label followersLabel;




    /**Private Data Fields*/
    private User user;
    private Stage stage;
    private Scene scene;
    private Pane layout;

    //Constructor
    public UserControlPanel(User user)
    {
        /**Initializing the Private Data Fields*/
        this.followIDTextArea = new TextArea();
        this.followIDTextArea.setPromptText("Enter The Name Of the Person To Follow Here");

        this.followButton = new Button("Follow User");

        this.usersFollowing = new ListView<>(); //List all the users its currently following
        this.followingLabel = new Label("Following");

        this.userFollowers = new ListView<>();
        this.followersLabel = new Label("Followers");

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
                tweetButton, newsFeed, followersLabel,followingLabel,newsFeedLabel, userFollowers);

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

        //List View Of all the Followers of the Current User
        followersLabel.setLayoutX(650);
        followersLabel.setLayoutY(20);
        userFollowers.setLayoutX(650);
        userFollowers.setLayoutY(50);

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


}
