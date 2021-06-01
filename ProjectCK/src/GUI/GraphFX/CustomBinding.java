package GUI.GraphFX;

import static javafx.beans.binding.Bindings.createDoubleBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableDoubleValue;

public class CustomBinding {

    public static DoubleBinding arctan(final ObservableDoubleValue x, final ObservableDoubleValue y) {
        return createDoubleBinding(() -> Math.atan2(y.get(), x.get()), y, x);
    }

    public static DoubleBinding sin(final ObservableDoubleValue x, final ObservableDoubleValue y) {
        return createDoubleBinding(() -> y.get() / (Math.sqrt(Math.pow(x.get(), 2) + Math.pow(y.get(), 2))), x, y);
    }

    public static DoubleBinding cos(final ObservableDoubleValue x, final ObservableDoubleValue y) {
        return createDoubleBinding(() -> x.get() / (Math.sqrt(Math.pow(x.get(), 2) + Math.pow(y.get(), 2))), x, y);
    }

    public static DoubleBinding toDegrees(final ObservableDoubleValue angrad) {
        return createDoubleBinding(() -> Math.toDegrees(angrad.get()), angrad);
    }
}

