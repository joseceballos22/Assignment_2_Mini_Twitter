package mini.twitter;

import java.util.ArrayList;
import java.util.UUID;

/**
 * File: User.java
 * Goal: To define what a User is
 * */

//Implementing the UserComponent Interface to use the composite pattern

/**
 * Implementing The Observer Interface So that the news Feed is Automatically Updated Everytime
 * The User it follows adds Some news
 * */
/**
 * Implementing the Subject (Publisher) Interface for the User class
 * So that everytime something gets changed in here it updates all of its observers which are also Users
 * */

public class User implements UserComponent, Observer, Subject, Visitable
{
    //Every User has a Unique ID
    private String id;
    //Every User has a Array List of Users they are following
    private ArrayList<User> following;

    //Used To Store the Time at which the User Was Created
    private long creationTime;

    //Used to Store the Last Updated Time of the User
    private long lastUpdateTime;

    //Every User has a Array List of Users that are following this User
    private ArrayList<User> followers;

    //A News Feed List Containing A List of Twitter Messages (From the Users it follows)
    private ArrayList<String> newsFeed; //WILL REFACTOR LATER USING A Observer Pattern

    //Every User is Going to have a name
    private String name;

    //Gets a Reference to the Singleton Object Which contains all the Users in the program
    private AdminControlPanelSingleton adminSingleton;

    //ArrayList Containing all the Observers
    private ArrayList<Observer> observers;
    private String news;

    //Initializing Private Variables
    public User(String name)
    {
        this.name = name;
        following = new ArrayList<>();
        followers = new ArrayList<>();
        newsFeed = new ArrayList<>();
        this.adminSingleton = AdminControlPanelSingleton.getInstance(); //Getting a Reference to the Singleton Instance

        this.observers = new ArrayList<>();
        this.news = "";


        /**Initializing the Creation Time */
        this.creationTime = System.currentTimeMillis();

        this.lastUpdateTime = 0; //Initially Zero

        /**Initializing the User ID Based On the Name (No Spaces)*/

        String tempName = this.name + " "; //Adding a Space to the end of the Name to use as delimiter
        this.id = ""; //Initially Empty
        String tempWord = ""; //Used to Get all the Words

        for(int i = 0; i < tempName.length(); i++)
        {
            //Found A Word
            if(tempName.charAt(i) == ' ')
            {
                this.id += tempWord;
                //Adding a UnderScore to Separate the Names
                if(i < tempName.length() -1 )
                {
                    this.id += "_"; //Adding a UnderScore
                }
                //Resetting the tempWord
                tempWord = "";
            }
            else
            {
                tempWord += tempName.charAt(i); //Saving the Characters
            }
        }
    }

    //Updates the LastUpdateTime Attribute
    public void setLastUpdateTime(long newTime)
    {
        this.lastUpdateTime = newTime; //Updating it
    }

    //Gets the Last Updated Time Attribute
    public long getLastUpdateTime()
    {
        return this.lastUpdateTime;
    }



    //Gets the Creation Time of this User
    public long getCreationTime()
    {
        return this.creationTime;
    }


    //Gets the following Array list
    public ArrayList<User> getFollowing()
    {
        return this.following;
    }

    //Gets the Followers Array List
    public ArrayList<User> getFollowers()
    {
        return followers;
    }

    //Gets the newsFeed ArrayList
    public ArrayList<String> getNewsFeed()
    {
        return this.newsFeed;
    }

    public String getId() {return this.id;}

    /**
     * Method Which Adds a new User that is following this User
     *
     * Returns a boolean
     * True if we successfully added the user
     * False if the User was already a follower
     * */
    public boolean addNewFollower(User userToAdd)
    {
        //Ensuring the userToFollow is A Valid User
        if(this.isUserValid(userToAdd) == false)
        {
            return false;
        }

        //Ensuring the User has not already followed this user
        for(User element : followers)
        {
            //UserToAdd has already followed this User Return False
            if(element.getName().equals(userToAdd.getName()))
            {
                return false;
            }
        }
        //Ensuring the user is not adding himself as his own follower
        if(userToAdd.getName().equals(this.getName()))
        {
            return false; //Cant add yourself as your own follower
        }
        this.followers.add(userToAdd);

        return true; //User successfully added
    }



    /**
     * Method Which allows the Current User to follow Other Users
     *  Based on the users specified
     *
     * Returns a boolean
     * True if it followed the user successfully
     * False if it Did Not follow the user successfully
     * */
    public boolean followUser(User userToFollow)
    {
        //Ensuring the userToFollow is A Valid User
        if(this.isUserValid(userToFollow) == false)
        {
            return false;
        }
        //Ensuring We have not already followed this user
        for(User element : following)
        {
            //If this is true this means we have already followed the user
            if(element.getName().equals(userToFollow.getName()))
            {
                //Since we have already followed the user returning false
                return false;
            }
        }
        //Ensuring the User is not following himself
        if(userToFollow.getName().equals(this.getName()))
        {
            return false; //Cant follow yourself
        }

        //If we made it up to here we have not followed the user so follow it
        this.following.add(userToFollow); //Following the User


        return true; //Since we followed the user successfully
    }

    //Private Helper Method Which Ensures the User we are trying to follow is valid
    private boolean isUserValid(User userToCheck)
    {

        for(User element : adminSingleton.getUsers())
        {
            if(element.getName().equals(userToCheck.getName()))
            {
                return true;
            }
        }
        //Else the user is not valid
        return false;
    }


    //returns the name of the user
    @Override
    public String getName()
    {
        return this.name;
    }



    @Override
    public String toString()
    {
        return this.name;
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
        this.newsFeed.add(newMessage); //Adding the New Message To the News Feed

        /**
         * Updating the newsFeed ListView Of this User Since it Just Got a New Message
         * - Using the adminSingleton Reference
         * - Only updating the ListView if the UserControlPanel Exists
         * */

        UserControlPanel userControlPanel = this.adminSingleton.getUserControlPanel(this.name);

        //Ensuring this User has a ControlPanel
        if(userControlPanel != null)
        {
            userControlPanel.addNews(newMessage); //Updating the ListView Of this User
        }
        //Else Do Nothing

        /**
         * Updating the lastUpdateTime Attribute of this User Since it Just Got a New Message
         * - Using the AdminSingleton Reference
         * - Only Updating the Label If the USer Control Panel Exists
         * */
        if(userControlPanel != null)
        {
            userControlPanel.updateTime(System.currentTimeMillis());
        }

    }

    //Adds news to all the Observers causing all the observers to update
    public void addNews(String news)
    {
        this.news = news; //Updating the News
        this.notifyObserver(); //Notifying all the observers of the News
    }


    //Adds a Observer to the Subject so now the subject will need to notify that observer if any changes are made
    @Override
    public void register(Observer newObserver)
    {
        this.observers.add(newObserver);
    }

    //Notifies all the Observers of the Subject Of a Change
    @Override
    public void notifyObserver()
    {
        for(int i = 0; i < observers.size(); i++)
        {
            observers.get(i).update(this.news);
        }
    }

    /**
     * Returns the Correct output based on the visitor
     *
     * @param vistor
     * @return double
     */
    @Override
    public double accept(Visitor vistor)
    {
        return vistor.visit((User)this);
    }
}
