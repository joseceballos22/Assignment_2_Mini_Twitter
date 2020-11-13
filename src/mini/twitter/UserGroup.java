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
    private UUID id;
    //Every User Group Can Contain any number of Users or User Groups
    private ArrayList<UserComponent> userComponents;

    //Constructor
    public UserGroup(String userGroupName)
    {
        this.name = userGroupName;
        id = UUID.randomUUID(); //Randomly selecting a UUID
        userComponents = new ArrayList<>(); // Initially Empty
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

/*    //Displays the User Group Information
    @Override
    public String display() {
        String answer = this.name + "\n"; //First element to display is the name of the User Group

        //Next Displaying all the UserComponents in the UserGroup
        for(UserComponent element : userComponents)
        {
            answer += element.display();
        }

        return answer;
    }*/
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
