package happyhours.dimooon.com.happyhours.view.custom.progressbar;

public interface ProgressBar {
    void setPresenter(ProgressBarPresenter presenter);
    ProgressBarPresenter getPresenter();
    void setModel(ProgressBarModel model);
    void restoreProgress(int progress);
    void publishValue(long progress);
}
