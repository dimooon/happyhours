package happyhours.dimooon.com.happyhours.model.timer;

public interface TimerUpdatedListener {

    void publishValue(long value);
    void active(boolean active);
}