
import java.util.ArrayList;

public class Bank {

    private ArrayList<Customer> customers = new ArrayList<Customer>();
    
    public ArrayList<Customer> getCustomers() {
        return customers;
    }   
      
    void addCustomer(Customer customer) {
        getCustomers().add(customer);
    }

    Customer getCustomer(int account_position) {   // return the customer account at this position
        return getCustomers().get(account_position);
    }


    
}
