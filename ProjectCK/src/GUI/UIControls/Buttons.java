package GUI.UIControls;

public class Buttons {
    private static ResetButton bindResetButton;
    private static PauseButton bindPauseButton;
    private static ContinueButton bindContinueButton;
    private static StopButton bindStopButton;

    public static void bindButtons(ResetButton resetButton, PauseButton pauseButton, ContinueButton continueButton, StopButton stopButton) {
        bindResetButton = resetButton;
        bindPauseButton = pauseButton;
        bindContinueButton = continueButton;
        bindStopButton = stopButton;
    }

    public static ResetButton getBindResetButton() {
        return bindResetButton;
    }

    public static void setBindResetButton(ResetButton bindResetButton) {
        Buttons.bindResetButton = bindResetButton;
    }

    public static PauseButton getBindPauseButton() {
        return bindPauseButton;
    }

    public static void setBindPauseButton(PauseButton bindPauseButton) {
        Buttons.bindPauseButton = bindPauseButton;
    }

    public static ContinueButton getBindContinueButton() {
        return bindContinueButton;
    }

    public static void setBindContinueButton(ContinueButton bindContinueButton) {
        Buttons.bindContinueButton = bindContinueButton;
    }

    public static StopButton getBindStopButton() {
        return bindStopButton;
    }

    public static void setBindStopButton(StopButton bindStopButton) {
        Buttons.bindStopButton = bindStopButton;
    }
}
