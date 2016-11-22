import java.awt.*;
import java.util.ArrayList;

/**
 * Created by aldartron on 21.11.16.
 */
public class App {
    MainForm mainForm = new MainForm();
    FormulaForm formulaForm = new FormulaForm();
    TypeEngine typeEngine = new TypeEngine();
    FormulaEngine formulaEngine = new FormulaEngine();
    GenForm genForm = new GenForm();

    public static void main(String[] args) {
        App app = new App();
    }

    App() {
        this.mainForm.app = this;
        this.formulaForm.app = this;
        this.typeEngine.app = this;
        this.formulaEngine.app = this;
        this.genForm.app = this;
    }

    public void setCoords(String sourceButtonName) {
        typeEngine.setCoords(MouseInfo.getPointerInfo().getLocation(), sourceButtonName);
        if (sourceButtonName.equals("FormButton")) {
            mainForm.setFormButton.setText(typeEngine.inputX + " " + typeEngine.inputY);
            System.out.println(typeEngine.inputX + " " + typeEngine.inputY);
        } else {
            mainForm.setSubmitButton.setText(typeEngine.submitX + " " + typeEngine.submitY);
            System.out.println(typeEngine.submitX + " " + typeEngine.submitY);
        }
    }

    public boolean isCoordsReady() {
        return (typeEngine.inputX != 0 && typeEngine.submitX != 0);
    }

    public void start() {
        typeEngine.start();
    }

    void showFormulaForm() {
        formulaForm.setVisible(true);
    }

    void showGenForm(String formula) {
        genForm.setVisible(true);
        genForm.generatedList = formulaEngine.generateFormula(formula, Integer.parseInt(formulaForm.timesField.getText()));
        genForm.showGeneratedList();
    }

    void newListVar(ListVar listVar) {
        formulaEngine.listVars.add(listVar);
    }

    void newRangeVar(RangeVar rangeVar) {
        formulaEngine.rangeVars.add(rangeVar);
    }

    // Сохранение готового для ввода листа
    void saveGenList(ArrayList<String> genList) {
        typeEngine.list = genList;
    }

}