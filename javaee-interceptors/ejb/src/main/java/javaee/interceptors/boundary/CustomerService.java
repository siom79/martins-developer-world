package javaee.interceptors.boundary;

import javaee.interceptors.control.Customers;
import javaee.interceptors.control.interceptor.PerformanceInterceptor;
import javaee.interceptors.model.Customer;
import javaee.interceptors.model.CustomerDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/customerService")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
@Interceptors(PerformanceInterceptor.class)
public class CustomerService {
    @Inject
    Customers customers;

    @PUT
    @Path("/createCustomer")
    public String createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setName(customerDto.getName());
        long customerId = customers.createCustomer(customer);
        return String.valueOf(customerId);
    }

    @GET
    @Path("/loadCustomerById")
    public CustomerDto loadCustomerById(@QueryParam("id") long id) {
        Customer customer = customers.loadCustomerById(id);
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        return customerDto;
    }
}
