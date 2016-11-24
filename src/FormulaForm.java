import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by aldartron on 21.11.16.
 */
public class FormulaForm extends JFrame {
    App app;

    JTextField formulaField = new JTextField();
    JPanel varPanel = new JPanel();
    JPanel generalButtonsPanel = new JPanel();
    JButton closeButton = new JButton("Закрыть");
    JPanel listVarPanel = new JPanel();
    JPanel rangeVarPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    JTextField timesField = new JTextField();
    JButton genButton = new JButton("Сгенерировать");

    // Компоненты listVarPanel
    JTextField listVarField = new JTextField();
    JList listVarList = new JList();
    JButton listVarInsertButton = new JButton("Внести");
    JButton listVarAddButton = new JButton("Добавить список");
    DefaultListModel<String> listModel;

    // Компоненты rangeVarPanel
    JTextField rangeVarStart = new JTextField("От");
    JTextField rangeVarEnd = new JTextField("До");
    JTextField rangeVarStep = new JTextField("Шаг");
    JButton rangeVarAddButton = new JButton("Добавить интервал");

    FormulaForm() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(0,0,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(3,3,3,3),0,0);
        this.add(formulaField,c);
        c.gridy = 1; c.weighty = 7;
        this.add(varPanel,c);
        c.gridy = 2; c.weighty = 1; c.insets = new Insets(3,10,0,10);
        this.add(mainPanel,c);
        c.gridy = 3; c.insets = new Insets(3,3,3,3);
        this.add(generalButtonsPanel,c);

        generalButtonsPanel.setLayout(new GridBagLayout());
        c.gridx = 0;
        generalButtonsPanel.add(genButton,c);
        c.gridx = 1;
        generalButtonsPanel.add(closeButton,c);

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
        listVarPanel.add(new JScrollPane(listVarList),c);
        c.gridy = 2; c.weighty = 1;
        listVarPanel.add(listVarAddButton,c);

        // Настройка листа
        listModel = new DefaultListModel();
        listVarList.setModel(listModel);

        // Заполнение rangeVarPanel
        rangeVarPanel.setLayout(new GridBagLayout());
        c.gridy = c.gridx = 0; c.weighty = c.weightx = 1;
        rangeVarPanel.add(rangeVarStart,c);
        c.gridy = 1;
        rangeVarPanel.add(rangeVarEnd,c);
        c.gridy = 2;
        rangeVarPanel.add(rangeVarStep,c);
        c.gridy = 3;
        rangeVarPanel.add(rangeVarAddButton,c);

        // Назначение слушателей полей rangeVarPanel
        FieldClickListener fcl = new FieldClickListener();
        rangeVarStart.addFocusListener(fcl);
        rangeVarEnd.addFocusListener(fcl);
        rangeVarStep.addFocusListener(fcl);

        mainPanel.setLayout(new GridLayout(1,2));
        JLabel timesLabel = new JLabel("Итераций: ");
        timesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(timesLabel);
        c.gridx = 1;
        mainPanel.add(timesField);

        this.setLocation(600,300);
        this.setSize(360,360);
        this.setMinimumSize(new Dimension(270,240));
        this.setTitle("Редактор формулы");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

        // Назначение слушателей
        genButton.addActionListener(new GenButtonListener());
        listVarAddButton.addActionListener(new AddListVarListener());
        rangeVarAddButton.addActionListener(new AddRangeVarListener());
        listVarInsertButton.addActionListener(new InsertListVarListener());
        listVarList.addKeyListener(new DeleteListVarListener());
        closeButton.addActionListener(new CloseListener());
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

    class GenButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            app.showGenForm(formulaField.getText());
        }
    }

    class AddListVarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (checkAddedListVar()) {
                ListVar listVar = new ListVar();
                for (int i = 0; i < listModel.getSize(); i++) {
                    listVar.values.add(listModel.get(i));
                }
                app.newListVar(listVar);
                String space = (ListVar.count < 10) ? "0" : "";
                formulaField.setText(formulaField.getText() + "[" + space + (ListVar.count - 1) + "]");
                listModel.clear();
            }
        }
    }

    class AddRangeVarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (checkAddedRangeVar()) {
                RangeVar rangeVar = new RangeVar(
                        Double.parseDouble(rangeVarStart.getText()),
                        Double.parseDouble(rangeVarEnd.getText()),
                        Double.parseDouble(rangeVarStep.getText())
                );

                app.newRangeVar(rangeVar);
                String space = (RangeVar.count < 10) ? "0" : "";
                formulaField.setText(formulaField.getText() + "{" + space + (RangeVar.count - 1) + "}");
                // Очищаем поля rangeVar
                rangeVarStart.setText("");
                rangeVarEnd.setText("");
                rangeVarStep.setText("");
            }
        }
    }

    class InsertListVarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (!listVarField.getText().equals("")) {
                listModel.addElement(listVarField.getText());
                listVarField.setText("");
                listVarField.grabFocus();
            }
        }
    }

    class DeleteListVarListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE)
                listModel.remove(listVarList.getSelectedIndex());
        }

        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {

        }
    }

    class CloseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            closeForm();
        }
    }

    void closeForm() {
        this.setVisible(false);
    }

    boolean checkAddedListVar() {
        // Проверка лист-переменной на корректность (лист не пустой)
        return !listModel.isEmpty();
    }

    boolean checkAddedRangeVar() {
        // Проверка интервал-переменной на корректность (старт, шаг и конец не пустые)
        return !(rangeVarStart.getText().isEmpty() || rangeVarStep.getText().isEmpty() || rangeVarEnd.getText().isEmpty());
    }

}
