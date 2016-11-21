import java.awt.*;

/**
 * Created by aldartron on 21.11.16.
 */
public class TypeEngine {
    App app;

    int inputX, inputY, submitX, submitY;

    public void setCoords(Point point, String buttonName) {
        if (buttonName.equals("FormButton")) {
            setFormCoords(point);
        } else
            setSubmitCoords(point);
    }

    public void setFormCoords(Point point) {
        inputX = (int)point.getX();
        inputY = (int)point.getY();
    }

    public void setSubmitCoords(Point point) {
        submitX = (int)point.getX();
        submitY = (int)point.getY();
    }

}
