package DependencyInjectionExample;

public class CustomerService{
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository= customerRepository;
    }
    public void displayCustomer(int id){
        String customerName=customerRepository.findCustomerById(id);
        System.out.println("Customer Details for ID "+id+" : "+customerName);
    }
}
