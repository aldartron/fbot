import java.util.ArrayList;

/**
 * Created by aldartron on 22.11.16.
 */
public class ListVar {
    ArrayList<String> values = new ArrayList<>();

    static int count = 0;

    ListVar() {
        count++;
    }

    String getValue() {
        int index = (int) (Math.random() * values.size());
        return values.get(index);
    }
}
