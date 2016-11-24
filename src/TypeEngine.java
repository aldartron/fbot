import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.security.cert.TrustAnchor;
import java.util.ArrayList;

/**
 * Created by aldartron on 21.11.16.
 */
public class TypeEngine {
    App app;
    ArrayList<String> list = new ArrayList<>();
    Color color;
    Robot robot;
    boolean isAtive = false;

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
        robot.mouseMove(submitX,submitY);
        robot.mouseMove(submitX+1,submitY+1);
        color = robot.getPixelColor(submitX,submitY);

        isAtive = true;

        Thread fThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isAtive) {
                    for (String s : list) {
                        // Вводим одну формулу
                        fEnter(s);
                        robot.delay(5000);
                        // Ждем когда вернется цвет кнопки
                        while (true) {
                            if  (isAtive) {
//                            robot.mouseMove(submitX + (int) (Math.random() * 3), submitY);
//                            robot.delay(1500);
                                if (robot.getPixelColor(submitX, submitY).equals(color)) {
                                    break;
                                } else {
                                    sleep(500);
                                }
                            }
                        }
                    }
                }
            }
        });

        fThread.start();
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
        robot.mouseMove(inputX,inputY);
        robot.delay(1000);
        sleep(500);
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
        // Кликаем в свободное пространство
        robot.mouseMove(inputX,inputY + 250);
        click(1);
        sleep(100);
        // Кликаем на кнопку
        robot.mouseMove(submitX,submitY);
        robot.delay(3000);
        sleep(500);
        click(1);
    }

    void fType(String text) {
        for (char c : text.toUpperCase().toCharArray()) {
            press(c);
            sleep(300);
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
