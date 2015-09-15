package happyhours.dimooon.com.happyhours.view.session;

public interface ISessionView {
    void purgeSession();
    boolean isSessionStarted();
    void startSession();
    void stopSession();
}
