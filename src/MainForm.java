import javax.swing.*;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by aldartron on 21.11.16.
 */

public class MainForm extends JFrame {
    App app;

    JTextField formulaField = new JTextField();
    JButton startButton = new JButton("Старт");
    JButton stopButton = new JButton("Стоп");
    JButton formulaButton = new JButton("Настройки");
    JButton setFormButton = new JButton("Указать форму ввода");
    JButton setSubmitButton = new JButton("Указать кнопку отправки");

    JPanel buttonPanel = new JPanel();
    JPanel formulaPanel = new JPanel();

    MainForm() {
        initLaF();

        GridBagConstraints c = new GridBagConstraints(0,0,1,1,1,1,GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(3,3,3,3),0,0);
        this.setLayout(new GridBagLayout());
        this.add(buttonPanel,c);
        c.gridy = 1;
        this.add(formulaPanel,c);

        formulaField.setHorizontalAlignment(SwingConstants.CENTER);

        buttonPanel.setLayout(new GridBagLayout());
        c.gridy = 0;
        buttonPanel.add(setFormButton,c);
        c.gridy = 1;
        buttonPanel.add(setSubmitButton,c);
        c.gridx = 1;
        buttonPanel.add(stopButton,c);
        c.gridy = 0;
        buttonPanel.add(startButton,c);

        formulaPanel.setLayout(new GridBagLayout());
        c.gridx = 0;
        formulaPanel.add(formulaField,c);
        c.gridy = 1;
        formulaPanel.add(formulaButton,c);

        // Назначение слушателей
        startButton.addActionListener(new startButtonListener());
        stopButton.addActionListener(new stopButtonListener());
        setFormButton.addActionListener(new CoordButtonListener());
        setSubmitButton.addActionListener(new CoordButtonListener());
        formulaButton.addActionListener(new FormulaButtonListener());

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(320,240));
        this.setLocation(700,300);
        this.setSize(320, 240);
        this.setVisible(true);
        this.setTitle("FBot");

        setFormButton.setName("FormButton");
        setSubmitButton.setName("SubmitButton");

        startButton.setEnabled(false);
        stopButton.setEnabled(false);
        formulaField.setEditable(false);
    }

    class startButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            app.start();
        }
    }

    class stopButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            app.stop();
        }
    }


    class CoordButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JButton sourceButton = (JButton)actionEvent.getSource();
            if (sourceButton.getText() == "Наведите и нажмите пробел") {
                app.setCoords(sourceButton.getName());
            } else
                sourceButton.setText("Наведите и нажмите пробел");

            if (app.isCoordsReady()) {
                startButton.setEnabled(true);
                stopButton.setEnabled(true);
            }
        }
    }

    class FormulaButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            app.showFormulaForm();
        }
    }

    void initLaF() {
        try {
            String systemLookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
            // устанавливаем LookAndFeel
            UIManager.setLookAndFeel(systemLookAndFeelClassName);
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Can't use the specified look and feel on this platform.");
        } catch (Exception e) {
            System.err.println("Couldn't get specified look and feel, for some reason.");
        }
    }

    void updateFormula(String formula) {
        formulaField.setText(formula);
    }

}
