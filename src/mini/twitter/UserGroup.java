package mini.twitter;

import java.util.ArrayList;
import java.util.UUID;

/**
 * File: UserGroup.java
 * Goal: To Define what a UserGroup is
 * */


//Implementing the UserComponent Interface to use the composite pattern

public class UserGroup implements UserComponent, Visitable
{
    //Every User Group will have a name
    private String name;
    //Every User Group will have a Unique ID
    private String id;

    //Every User Group Can Contain any number of Users or User Groups
    private ArrayList<UserComponent> userComponents;

    //Constructor
    public UserGroup(String userGroupName)
    {
        this.name = userGroupName;
        userComponents = new ArrayList<>(); // Initially Empty

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

    //Adds a User Component to the
    public void add(UserComponent component)
    {
        userComponents.add(component); //Adding a UserComponent to this User Group
    }

    //returns the name of the user
    @Override
    public String getName()
    {
        return this.name;
    }

    //Returns the Id
    public String getId() {return this.id;}

    @Override
    public String toString()
    {
        return this.name;
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
        return vistor.visit((UserGroup) this);
    }
}
