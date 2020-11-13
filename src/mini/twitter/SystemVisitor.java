package mini.twitter;

import java.util.ArrayList;

/**
 * Visitor Classed Used to Define the Logic For the Buttons in the Admin Panel
 * */
public class SystemVisitor implements Visitor
{
    /**Private Data Fields*/

    private AdminControlPanelSingleton adminSingleton;
    private ArrayList<String> positiveMessages;

    public SystemVisitor()
    {
        this.adminSingleton = AdminControlPanelSingleton.getInstance();
        this.positiveMessages = new ArrayList<>();

        /**Defining the positive Messages*/
        positiveMessages.add("hello");
        positiveMessages.add("happy");
        positiveMessages.add("congrats");
        positiveMessages.add("bye");
        positiveMessages.add("welcome");
        positiveMessages.add("good");
        positiveMessages.add("luck");
    }


    /**
     * Method Which Returns the Number Of User is The Program
     *
     * @param user
     * @return int
     */
    @Override
    public double visit(User user)
    {
        return this.adminSingleton.getUsers().size();
    }

    /**
     * Method Which Returns the Number of User Groups in the Program
     *
     * @param userGroup
     * @return int
     */
    @Override
    public double visit(UserGroup userGroup)
    {
        return this.adminSingleton.getUserGroups().size();
    }

    /**
     * Method Which Returns the Number of Messages in the Program
     *
     * @param messages
     * @return int
     */
    @Override

    public double visit(ArrayList<String> messages)
    {
        return this.adminSingleton.getMessages().size();
    }

    /**
     * Method Which Returns the Percent Of Positive Messages in the Program
     */
    @Override
    public double visit()
    {
        ArrayList<String> messages = this.adminSingleton.getMessages(); //Saving the Messages And Seeing if their are any postive words in the messages

        double positiveMessageCounter = 0;

        for(String line: messages) {
            //Lowering the message
            String temp = line.toLowerCase();

            //Splitting the String into an array list
            String[] holder = temp.split(" ");

            //Counting the Positive Words
            for (String word : holder)
            {
                if(positiveMessages.contains(word))
                {
                    positiveMessageCounter ++; //Incrementing the Counter
                }
            }
        }

        /**Returning the Percentage
         * postiveWords / Total Words * 100
         * */
        return (positiveMessageCounter / messages.size() ) * 100;
    }
}
