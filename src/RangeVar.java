/**
 * Created by aldartron on 22.11.16.
 */
public class RangeVar {
    double begin;
    double end;
    double step;

    static int count = 0;

    String getValue() {
        int maxSteps = (int) ((end - begin) / step);
        double result = begin + ((int)(Math.random() * maxSteps) * step);
        if (result % 1 == 0) {
            return Integer.toString((int)result);
        }
        int maxLength = Integer.toString((int)result).length() + Double.toString(step).length() - 2;
        if (Double.toString(result).length() > maxLength) {
            return Double.toString(result).substring(0,maxLength+1);
        }
        return Double.toString(result);
    }

    RangeVar(double begin, double end, double step) {
        this.begin = begin;
        this.end = end;
        this.step = step;

        count++;
    }

    public static void main(String[] args) {
        RangeVar rv = new RangeVar(2,5,0.125);
        for (int i = 0; i < 10; i++)
            System.out.println(rv.getValue());
    }
}
