package javaee.interceptors.control;

import javaee.interceptors.model.Customer;
import javaee.interceptors.model.CustomerDto;
import javaee.interceptors.model.Order;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

@Stateless
public class Customers {
    @PersistenceContext
    EntityManager entityManager;

    public long createCustomer(Customer customer) {
        Customer merge = entityManager.merge(customer);
        return merge.getId();
    }

    public Customer loadCustomerById(long id) {
        return entityManager.find(Customer.class, id);
    }
}
