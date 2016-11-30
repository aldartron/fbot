import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringJoiner;

/**
 * Created by aldartron on 21.11.16.
 */
public class FormulaEngine {
    App app;

    ArrayList<ListVar> listVars = new ArrayList<>();
    ArrayList<RangeVar> rangeVars = new ArrayList<>();


    ArrayList<String> generateFormula(String formula, int times) {
        // Генерирует из полученной строки-формулы список строк
        HashSet<String> set = new HashSet<>();
        while (set.size() < times) {
            set.add(genFormula(formula));
        }

        return new ArrayList<>(set);
    }

    String genFormula(String formula) {
        int i = 0;
        String result = "";
        while (i < formula.toCharArray().length) {
            char c = formula.toCharArray()[i];
            if (c == '[') {
                String indexStr = Character.toString(formula.toCharArray()[i+1]) + Character.toString(formula.toCharArray()[i+2]);
                int index = Integer.parseInt(indexStr);
                result += listVars.get(index).getValue();
                i += 4;
            } else if (c == '{') {
                String indexStr = Character.toString(formula.toCharArray()[i+1]) + Character.toString(formula.toCharArray()[i+2]);
                int index = Integer.parseInt(indexStr);
                result += rangeVars.get(index).getValue();
                i += 4;
            } else {
                result += c;
                i++;
            }
        }
        return result;
    }

    int countIterations(String formula) {
        int result = 1;
        int i = 0;
        while (i < formula.toCharArray().length) {
            try {
                char c = formula.toCharArray()[i]; // Открывающая скобка
                char cc = formula.toCharArray()[i + 3]; // Закрывающая скобка
                if (c == '[' && cc == ']') {
                    int index = Integer.parseInt(
                            Character.toString(formula.toCharArray()[i + 1])
                                    + Character.toString(formula.toCharArray()[i + 2])
                    );
                    result *= listVars.get(index).values.size();
                    i += 4;
                } else if (c == '{' && cc == '}') {
                    int index = Integer.parseInt(
                            Character.toString(formula.toCharArray()[i + 1])
                                    + Character.toString(formula.toCharArray()[i + 2])
                    );
                    RangeVar rangeVar = rangeVars.get(index);

                    result *= (rangeVar.end - rangeVar.begin) / rangeVar.step;

                    i += 4;
                } else {
                    i++;
                }
            } catch (Exception e) {
               e.printStackTrace();
            }
        }

        if (formula.isEmpty()) result = 0;

        return result;
    }

}
