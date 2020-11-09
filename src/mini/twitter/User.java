package mini.twitter;

import java.util.ArrayList;
import java.util.UUID;

/**
 * File: User.java
 * Goal: To define what a User is
 * */

//Implementing the UserComponent Interface to use the composite pattern

public class User implements UserComponent
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


    //Initializing Private Variables
    public User(String name)
    {
        this.name = name;
        following = new ArrayList<>();
        followers = new ArrayList<>();
        newsFeed = new ArrayList<>();
        id = UUID.randomUUID(); //Creating a Random UUID
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


    /**
     * Method Which allows the Current User to follow Other Users
     *  Based on the users specified
     * */
    public void followUser(User userToFollow)
    {
        //Ensuring We have not already followed this user
        boolean alreadyFollowed = false; //Initially will assume false

        for(User element : following)
        {
            if(element.getId() == userToFollow.getId())
            {
                alreadyFollowed = true; //Already Following User
            }
        }

    }

    //returns the name of the user
    @Override
    public String getName()
    {
        return this.name;
    }



/*    //To the display the user simply return his name
    @Override
    public String display() {
        return this.name + "\n";
    }*/

    @Override
    public String toString()
    {
        return this.name;
    }


}
