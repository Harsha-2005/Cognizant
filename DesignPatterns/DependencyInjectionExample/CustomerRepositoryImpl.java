package DependencyInjectionExample;

public class CustomerRepositoryImpl implements  CustomerRepository {
    @Override
    public String findCustomerById(int id) {
        if (id == 1) {
            return "Alice Smith";
        } else if (id == 2) {

            return "Bob Jones";
        }
        return "Customer not found";
    }
}
