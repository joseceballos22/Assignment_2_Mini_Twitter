package mini.twitter;


import java.util.ArrayList;

public interface Visitor
{
    /**
     * Method Which Returns the Number Of User is The Program
     * @return int
     * */
    public double visit(User user);

    /**
     * Method Which Returns the Number of User Groups in the Program
     * @return int
     * */
    public double visit(UserGroup userGroup);

    /**
     * Method Which Returns the Number of Messages in the Program
     * @return int
     * */
    public double visit(ArrayList<String> messages);


    /**
     * Method Which Returns the Percent Of Positive Messages in the Program
     */
    public double visit();
}
