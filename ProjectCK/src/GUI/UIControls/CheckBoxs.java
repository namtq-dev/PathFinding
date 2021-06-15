package GUI.UIControls;

public class CheckBoxs {
    private static AutoRunCheckBox bindAutoRunCheckBox;
    
    public static void bindCheckBoxs(AutoRunCheckBox autoRunCheckBox) {
        bindAutoRunCheckBox = autoRunCheckBox;
    }

    public static AutoRunCheckBox getBindAutoRunCheckbox() {
        return bindAutoRunCheckBox;
    }
}