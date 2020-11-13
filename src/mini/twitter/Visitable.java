package mini.twitter;

/**
 * Interface Used to add the Vistor Design Pattern
 * */
public interface Visitable
{
    /**
     * Returns the Correct output based on the visitor
     * @return double
     * */
    public double accept(Visitor vistor);
}
