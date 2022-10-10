package g56020.atlg4.stibride.utils.observer;

/**
 * Observer interface for the Observer/Observable design pattern
 */
public interface Observer {

    /**
     * Updates the class that implements this interface
     *
     * @param observable Observable object
     * @param arg        argument used for the update
     */
    void update(Observable observable, Object arg);
}
