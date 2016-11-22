import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by aldartron on 21.11.16.
 */
public class TypeEngine {
    App app;
    ArrayList<String> list = new ArrayList<>();
    Color color;
    Robot robot;

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

    void start() {
        // Запоминаем цвет
        color = robot.getPixelColor(inputX,inputY);

        for (String s : list) {
            // Вводим одну формулу
            fEnter(s);
            robot.delay(3000);
            // Ждем когда вернется цвет
            while (true) {
                if (robot.getPixelColor(inputX,inputY).equals(color)) {
                    break;
                } else {
                    sleep(250);
                }
            }
        }
    }

    TypeEngine() {
        try {
            robot = new Robot();
        } catch (Exception e) {}
    }

    void click(int times) {
        for (int i = 0; i < times; i++) {
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            robot.delay(10);
        }
    }

    void fEnter(String formula) {
        // Вводит текст в форму и нажимает кнопку отправки
        // Наводим на форму ввода
        robot.mouseMove(inputX,inputX);
        // Тройной клик для полного выделения
        click(3);
        sleep(300);
        // Нажимаем DELETE
        robot.keyPress(KeyEvent.VK_DELETE);
        robot.keyRelease(KeyEvent.VK_DELETE);
        sleep(300);
        // Набираем формулу
        fType(formula);
        sleep(200);
        // Наводим на кнопку отправки
        robot.mouseMove(submitX,submitY);
        sleep(500);
        click(1);
    }

    void fType(String text) {
        for (char c : text.toUpperCase().toCharArray()) {
            press(c);
            sleep(30);
        }
    }

    void sleep(int max) {
       robot.delay((int) (1 + Math.random() * max));
    }

    void press(char c) {
        if(c == '+') {
            robot.keyPress(KeyEvent.VK_ADD);
            robot.keyRelease(KeyEvent.VK_ADD);
        } else if (c == '-') {
            robot.keyPress(KeyEvent.VK_SUBTRACT);
            robot.keyRelease(KeyEvent.VK_SUBTRACT);
        } else if (c == '/') {
            robot.keyPress(KeyEvent.VK_DIVIDE);
            robot.keyRelease(KeyEvent.VK_DIVIDE);
        } else if (c == '*') {
            robot.keyPress(KeyEvent.VK_MULTIPLY);
            robot.keyRelease(KeyEvent.VK_MULTIPLY);
        } else if (c == '^') {
            robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress('6');
                robot.keyRelease('6');
            robot.keyRelease(KeyEvent.VK_SHIFT);
        } else if (c == '_') {
            robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress('-');
                robot.keyRelease('-');
            robot.keyRelease(KeyEvent.VK_SHIFT);
        } else if (c == '(') {
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress('9');
            robot.keyRelease('9');
            robot.keyRelease(KeyEvent.VK_SHIFT);
        } else if (c == ')') {
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress('0');
            robot.keyRelease('0');
            robot.keyRelease(KeyEvent.VK_SHIFT);
        } else {
            robot.keyPress(c);
            robot.keyRelease(c);
        }
    }

}
