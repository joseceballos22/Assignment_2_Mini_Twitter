package mini.twitter;

/**
 * Subject Interface Which will be used to notify the observers automatically of any state changes
 * */
public interface Subject
{
    //Adds a Observer to the Subject so now the subject will need to notify that observer if any changes are made
    public void register(Observer o);


    //Notifies all the Observers of the Subject Of a Change
    public void notifyObserver();
}
