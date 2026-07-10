package DependencyInjectionExample;

public class Main {
    public static void main(String[] args){
        CustomerRepository repository = new CustomerRepositoryImpl();

        CustomerService service = new CustomerService(repository);

        System.out.println("--- Testing Customer Service ---");

        service.displayCustomer(1);
        service.displayCustomer(2);
        service.displayCustomer(99);

    }
}
