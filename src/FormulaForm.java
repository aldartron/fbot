import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by aldartron on 21.11.16.
 */
public class FormulaForm extends JFrame {
    App app;

    JTextField formulaField = new JTextField();
    JPanel varPanel = new JPanel();
    JPanel generalButtonsPanel = new JPanel();
    JButton acceptButton = new JButton("Принять");
    JButton cancelButton = new JButton("Отмена");
    JPanel listVarPanel = new JPanel();
    JPanel rangeVarPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    JTextField timesField = new JTextField();
    JButton generateButton = new JButton("Сгенерировать");

    // Компоненты listVarPanel
    JTextField listVarField = new JTextField();
    JList listVarList = new JList();
    JButton listVarInsertButton = new JButton("Внести");
    JButton listVarAddButton = new JButton("Добавить список");

    // Компоненты rangeVarPanel
    JTextField rangeVarStart = new JTextField("От");
    JTextField rangeVarEnd = new JTextField("До");
    JTextField rangeVarStep = new JTextField("Шаг");
    JTextField rangeVarAccuracy = new JTextField("Точность");
    JButton rangeVarAddButton = new JButton("Добавить интервал");

    FormulaForm() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(0,0,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(3,3,3,3),0,0);
        this.add(formulaField,c);
        c.gridy = 1; c.weighty = 7;
        this.add(varPanel,c);
        c.gridy = 2; c.weighty = 1; c.insets = new Insets(10,25,10,25);
        this.add(mainPanel,c);
        c.gridy = 3; c.insets = new Insets(3,3,3,3);
        this.add(generalButtonsPanel,c);

        generalButtonsPanel.setLayout(new GridBagLayout());
        c.gridx = 0;
        generalButtonsPanel.add(generateButton,c);
        c.gridx = 1;
        generalButtonsPanel.add(acceptButton,c);
        c.gridx = 2;
        generalButtonsPanel.add(cancelButton,c);

        varPanel.setLayout(new GridLayout(1,2));
        varPanel.add(listVarPanel);
        varPanel.add(rangeVarPanel);
        varPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Заполнение listVarPanel
        listVarPanel.setLayout(new GridBagLayout());
        c.gridy = 1; c.gridx = 0; c.weightx = 10;
        listVarPanel.add(listVarField,c);
        c.gridx = 1; c.weightx = 1;
        listVarPanel.add(listVarInsertButton,c);
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2; c.weighty = 7;
        listVarPanel.add(listVarList,c);
        c.gridy = 2; c.weighty = 1;
        listVarPanel.add(listVarAddButton,c);

        // Заполнение rangeVarPanel
        rangeVarPanel.setLayout(new GridBagLayout());
        c.gridy = c.gridx = 0; c.weighty = c.weightx = 1;
        rangeVarPanel.add(rangeVarStart,c);
        c.gridy = 1;
        rangeVarPanel.add(rangeVarEnd,c);
        c.gridy = 2;
        rangeVarPanel.add(rangeVarStep,c);
        c.gridy = 3;
        rangeVarPanel.add(rangeVarAccuracy,c);
        c.gridy = 4;
        rangeVarPanel.add(rangeVarAddButton,c);

        // Назначение слушателей полей rangeVarPanel
        FieldClickListener fcl = new FieldClickListener();
        rangeVarStart.addFocusListener(fcl);
        rangeVarEnd.addFocusListener(fcl);
        rangeVarStep.addFocusListener(fcl);
        rangeVarAccuracy.addFocusListener(fcl);

        mainPanel.setLayout(new GridLayout(1,2));
        JLabel timesLabel = new JLabel("Количество итераций: ");
        timesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(timesLabel);
        c.gridx = 1;
        mainPanel.add(timesField);

        this.setSize(420,360);
        this.setTitle("Редактор формулы");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    class FieldClickListener implements FocusListener {
        @Override

        public void focusGained(FocusEvent focusEvent) {
            JTextField source = (JTextField)focusEvent.getSource();
            source.selectAll();
        }

        @Override
        public void focusLost(FocusEvent focusEvent) {

        }
    }

}
