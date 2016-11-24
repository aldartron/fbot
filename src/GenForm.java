import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by aldartron on 22.11.16.
 */
public class GenForm extends JFrame {
    App app;

    JList genList = new JList();
    JButton genOkButton = new JButton("OK");
    JButton genCancelButton = new JButton("Отмена");
    ArrayList<String> generatedList = new ArrayList<>();
    DefaultListModel<String> listModel = new DefaultListModel<>();

    GenForm() {
        JScrollPane scrollPane = new JScrollPane(genList);
        GridBagConstraints c = new GridBagConstraints(0,0,2,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(3,3,3,3),0,0);
        genList.setModel(listModel);
        this.setLayout(new GridBagLayout());
        this.add(scrollPane, c);
        c.gridy = 1; c.weighty = 0.05; c.gridwidth = 1;
        this.add(genOkButton, c);
        c.gridx = 1;
        this.add(genCancelButton,c);

        this.setLocation(500,300);
        this.setSize(360,420);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

        genOkButton.addActionListener(new GenOkListener());
        genCancelButton.addActionListener(new GenCancelListener());
    }

    class GenOkListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            genOk();
        }
    }

    class GenCancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            generatedList.clear();
            genHide();
        }
    }

    void genOk() {
        // Сохранить generatedList в приложении
        app.saveGenList(generatedList);
        app.updateFormula();
        listModel.clear();
        this.setVisible(false);
    }

    void showGeneratedList() {
        for (String s : generatedList) {
            listModel.addElement(s);
        }
    }

    void genHide() {
        this.setVisible(false);
    }
}
