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
 * The UserSubject adds Some news
 * */

public class User implements UserComponent, Observer
{
    //Every User has a Unique ID
    private UUID id;

    //Every User has a Array List of Users they are following
    private ArrayList<User> following;

    //Every User has a Array List of Users that are following this User
    private ArrayList<User> followers;

    //A News Feed List Containing A List of Twitter Messages (From the Users it follows)
    private ArrayList<String> newsFeed; //WILL REFACTOR LATER USING A Observer Pattern

    //Every User is Going to have a name
    private String name;

    //Gets a Reference to the Singleton Object Which contains all the Users in the program
    private AdminControlPanelSingleton adminSingleton;

    //UserSubject Used to register this User with the UserSubject
    private ArrayList<UserSubject> userSubjects;

    //Initializing Private Variables
    public User(String name)
    {
        this.name = name;
        following = new ArrayList<>();
        followers = new ArrayList<>();
        newsFeed = new ArrayList<>();
        id = UUID.randomUUID(); //Creating a Random UUID
        this.adminSingleton = AdminControlPanelSingleton.getInstance(); //Getting a Reference to the Singleton Instance


        this.userSubjects = new ArrayList<>(); //Not Linking Any UserSubjects with this User Initially
    }

    //Linking A User Subject with this User
    public void linkUserSubject(UserSubject userSubject)
    {
        userSubject.register(this); //Registering this User As a Observer to that UserSubject and Saving it in a ArrayList
        this.userSubjects.add(userSubject); //Linking a User Subject with this User
    }


    //Gets the ID of the User
    public UUID getId()
    {
        return this.id;
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
    }
}
