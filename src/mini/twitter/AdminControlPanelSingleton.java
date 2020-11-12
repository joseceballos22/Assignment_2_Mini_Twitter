package mini.twitter;

/**
 * File: AdminPanelSingleTon.java
 * Goal: To Define the Logic of the Admin Panel in this singleton class
 * */

import java.util.ArrayList;
import java.util.HashMap;

/**Going to use the SingleTon Pattern
 * - So that Only One instance of the Admin Panel SingleTon can be created
 * - Reason Using SingleTon Design Pattern is because we want all the users
 * which have their own User Panel To be able to know all the users / groups that exist
 * And therefore they can create their own follow list based on all the users
 * */

public class AdminControlPanelSingleton
{

    /**Private Data Fields */
    private ArrayList<User> users;    //List of all the Users in the program
    private ArrayList<UserGroup> userGroups; //List of all the User Groups in the program

    private HashMap<String, UserControlPanel> userControlPanels; //Used to keep a reference of all UserControlPanels Created

    /**SingleTon Stuff*/
    private static AdminControlPanelSingleton firstInstance = null; //Initially Null

    //Not Allowing Instances of this class to be made
    private AdminControlPanelSingleton() {
        //Initializing Private Data Fields
        this.users = new ArrayList<>();
        this.userGroups = new ArrayList<>();
        this.userControlPanels = new HashMap<>();
    }

    //Returns the Only Instance of the Admin Panel Since it Incorporates the Singleton Desing Pattern
    public static AdminControlPanelSingleton getInstance()
    {
        //Checking if we have to initialize the first Instance
        if(firstInstance == null)
        {
            firstInstance = new AdminControlPanelSingleton();
        }
        return firstInstance; //Returning the Only Instance of the Admin Panel
    }

    /**
     * Method which adds Users to the Program
     *
     * Also Ensures Unique Users for the Program
     *
     * Return True if user added to the program
     *        False if user Not Added to the program
     * */
    public boolean addUser(String userToAdd)
    {
        if(userToAdd.equals(""))
        {
            return false;
        }
        //Checking if the user was already added to the program
        for(UserComponent user : users)
        {
            if(user.getName().equals(userToAdd))
            {
                return false; //User Already Added
            }
        }

        this.users.add(new User(userToAdd)); //Adding the User

        return true; //User was added successfully
    }

    /**
     * Method Which Adds UserGroups To the Program
     *
     * Also Ensures Unique UserGroups For the Program
     *
     * Returns True if UserGroup was added to the program
     *         False if the UserGroup was NOT added to the program
     * */
    public boolean addUserGroup(String userGroupToAdd)
    {
        if(userGroupToAdd.equals(""))
        {
            return false;
        }
        //Checking if the user userGroupToAdd Already Exists
        for(UserComponent userGroup : userGroups)
        {
            if(userGroup.getName().equals(userGroupToAdd))
            {
                return false; //User Group Already Added
            }
        }
        //Else Adding the User Group
        this.userGroups.add(new UserGroup(userGroupToAdd));
        return true; //User Group was added successfully
    }

    /**
     * Method which returns the specified user
     * Based on the Users Name
     * */
    public User getUser(String userName) {
        for(int i = 0; i < this.users.size(); i++)
        {
            //We found the user
            if(this.users.get(i).getName().equals(userName))
            {
                return this.users.get(i);
            }
        }
        //Else Return Null
        return null;
    }

    /**
     * Method Which returns the ArrayList of the Users Deep Copied
     * */
    public ArrayList<User> getUsers()
    {
        ArrayList<User> toReturn = new ArrayList<>();

        for(User element : this.users)
        {
            toReturn.add(element);
        }
        //Deep copying
        return toReturn;
    }

    /**
     * Method which adds a UserControlPanel to the HashMap
     * Based on the String Name Of the User
     * */
    public void setUserControlPanels(String name, UserControlPanel panel)
    {
        this.userControlPanels.put(name, panel); //Saving a reference to that Panel based on the Name provided
    }

    /**
     * Method Which returns the Reference to the User Control Panel
     * Base On the String Name Of the User
     * */
    public UserControlPanel getUserControlPanel(String name)
    {
        if(name == null)
        {
            return null;
        }
        return this.userControlPanels.get(name);
    }

}
