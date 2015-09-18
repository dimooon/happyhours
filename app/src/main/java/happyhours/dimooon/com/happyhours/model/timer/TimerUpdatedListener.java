package happyhours.dimooon.com.happyhours.model.timer;

public interface TimerUpdatedListener {

    boolean publishValue(long value);
    void active(boolean active);
    String getName();
}