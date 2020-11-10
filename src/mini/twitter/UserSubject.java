package mini.twitter;

import java.util.ArrayList;

/**
 * Going to be the Subject (Publisher) Class for the User (Observer)
 * So that everytime something gets changed in here it updates the User Class
 * */
public class UserSubject implements Subject
{
    //ArrayList Containing all the Observers
    private ArrayList<Observer> observers;
    private String news;
    //Default Constructor
    public UserSubject()
    {
        this.observers = new ArrayList<>();
        this.news = "";
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
}
