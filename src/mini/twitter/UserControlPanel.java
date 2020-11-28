package mini.twitter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * File: UserControlPanel.java
 * Goal: To define the UserControlPanel Logic and GUI
 *
 * */


public class UserControlPanel
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

    //Creation Time Widgets
    private Label creationTimeLabel;
    private Label creationTime;



    /**Private Data Fields*/
    private User user;
    private Stage stage;
    private Scene scene;
    private Pane layout;

    private AdminControlPanelSingleton adminSingleton; //Used to Store a Reference to the SingleTon

    //Constructor
    public UserControlPanel(User user)
    {
        /**Initializing the Private Data Fields*/
        this.followIDTextArea = new TextArea();
        this.followIDTextArea.setPromptText("Enter User To Follow");

        this.followButton = new Button("Follow User");

        this.usersFollowing = new ListView<>(); //List all the users its currently following
        this.followingLabel = new Label("Following");

        this.tweetTextArea = new TextArea();
        this.tweetTextArea.setPromptText("Enter Tweet Message");
        this.tweetButton = new Button("Post Tweet");

        this.newsFeed = new ListView<>(); //List all the News Feed from Users Following
        this.newsFeedLabel = new Label("News Feed");

        this.creationTimeLabel = new Label("Creation Time");
        this.creationTime = new Label(user.getCreationTime() + "");

        this.user = user; //Saving a Reference to the specified User this Control Panel is Associated with

        this.stage = new Stage();
        this.layout = new Pane();
        //Linking the Layout and the Scene together
        this.scene = new Scene(layout, 700,700);
        //Linking the Scene to the Stage
        this.stage.setScene(scene);

        this.stage.setTitle(user.getName() + "'s Control Panel");
        this.adminSingleton = AdminControlPanelSingleton.getInstance(); //Getting a Reference to the singleton

        //Setting the Size of the Buttons
        this.tweetButton.setMinSize(250,50);
        this.followButton.setMinSize(250,50);

        //Setting the Size of the Text Areas
        this.tweetTextArea.setMaxSize(250,50);
        this.followIDTextArea.setMaxSize(250,50);

        //Setting the Size of the Creation Time
        this.creationTimeLabel.setMinSize(250, 50);
        this.creationTime.setMinSize(250, 50);

        //Setting the Font For the Labels
        this.followingLabel.setFont(new Font("Arial", 24));
        this.newsFeedLabel.setFont(new Font("Arial", 24));

        this.creationTimeLabel.setFont(new Font("Arial", 16));
        this.creationTime.setFont(new Font("Arial", 16));


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
                tweetButton, newsFeed,followingLabel,newsFeedLabel, creationTime, creationTimeLabel);

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
                    //Side Note: I Can Store this logic inside of the User Class
                    userToFollow.addNewFollower(this.user); //Since this user is now following that user

                    //Now that we have followed the user we need to make this user the observer of the user it followed
                    userToFollow.register(this.user); //This User will now receive updates when userToFollow Post something

                }
                //Else We did not follow the User because the User Was Already Following that user
                else
                {
                    AlertBox.display("AlertBox", "Error: User Is Already Following " + userToFollow.getName());
                }
            }
            //Clearing the FollowIdTextArea
            this.followIDTextArea.setText("");

        });



        /**
         * Checking if the Tweet Button was pressed
         * - If so then Update all the news feed of all the Users that are following this user
         * By using the Observer Design pattern
         * */
        this.tweetButton.setOnAction(e->
        {
            /**
             * Everytime the Tweet Button is pressed we are going:
             * - Get the Message to tweet
             * - Pass that message to all the observers observing this User
             * - Which should handle Updating the listView
             * */
            String tweetMessage = this.tweetTextArea.getText(); //Getting the message to tweet
            this.user.addNews(this.user.getName() + " : " + tweetMessage); //Tweeting the Message to all the Observers of this User
            this.adminSingleton.addMessage(tweetMessage);
            //Clearing the TweetTextArea After every tweet
            this.tweetTextArea.setText("");
        });

        //Starting the Stage
        this.stage.show();
    }

    /**
     * Method Which adds a Item to the News Feed List View
     *
     * @param  newsToAdd
     *  */
    public void addNews(String newsToAdd)
    {
        this.newsFeed.getItems().add(newsToAdd); //Updating the News Feed
    }




    //Sets the position of the widgets in the layout
    private void setWidgetPosition()
    {

        //Follow Button and Follow Text Area
        followButton.setLayoutX(50);
        followButton.setLayoutY(0);
        followIDTextArea.setLayoutX(50);
        followIDTextArea.setLayoutY(50);

        //List View Of all the users the current user is following
        followingLabel.setLayoutX(50);
        followingLabel.setLayoutY(150);
        usersFollowing.setLayoutX(50);
        usersFollowing.setLayoutY(200);

        //Position of the Creation Time
        creationTimeLabel.setLayoutX(50);
        creationTimeLabel.setLayoutY(600);
        creationTime.setLayoutX(50);
        creationTime.setLayoutY(650);


        //Tweet Button and Tweet Text Area
        tweetButton.setLayoutX(350);
        tweetButton.setLayoutY(0);
        tweetTextArea.setLayoutX(350);
        tweetTextArea.setLayoutY(50);

        //List View of all the news feed from the users the current user is following
        newsFeedLabel.setLayoutX(350);
        newsFeedLabel.setLayoutY(150);
        newsFeed.setLayoutX(350);
        newsFeed.setLayoutY(200);


    }

}
