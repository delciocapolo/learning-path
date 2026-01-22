import com.customexception.org.AgeLessThanZeroException;

public class Main {
    public static void main(String[] args) throws AgeLessThanZeroException {
        IO.println(Validate.age(-2));
    }
}

class Validate {
    public static boolean age(int value) throws AgeLessThanZeroException {
        if (value < 0) {
            throw new AgeLessThanZeroException("The age cant be less than zero", new RuntimeException());
        }

        return true;
    }
}