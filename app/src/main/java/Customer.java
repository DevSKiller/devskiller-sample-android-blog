/**
 * Created by mariuszs on 28.04.16.
 */
public class Customer {
    private static Customer ourInstance = new Customer();

    public static Customer getInstance() {
        return ourInstance;
    }

    private Customer() {
    }
}
