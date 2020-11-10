package mini.twitter;

/**Observer Interface which is used to update the class which implements it based on the fact if the Subject is updated*/
public interface Observer
{
    /**
     * Updates the class that implements the Observer interface
     * Everytime its Associated Subject Updates the Data
     * <p>
     * Therefore When a User that this user is following post a new Message Its newsFeed gets Updated
     *
     * @param newMessage
     */
    public void update(String newMessage);
}
