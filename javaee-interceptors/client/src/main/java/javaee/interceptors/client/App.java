package javaee.interceptors.client;

import javaee.interceptors.model.CustomerDto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static final String HTTP_LOCALHOST_8080_EJB = "http://localhost:8080/ejb";

    public static void main(String args[]) {
        App app = new App();
        app.run();
    }

    private void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        Client client = ClientBuilder.newClient();
                        Invocation.Builder requestCreate = client.target(HTTP_LOCALHOST_8080_EJB).path("customerService/createCustomer")
                                .request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML_TYPE);
                        CustomerDto customer = new CustomerDto();
                        customer.setName("CustomerName");
                        Response response = requestCreate.put(Entity.xml(customer));
                        int status = response.getStatus();
                        String customerId = response.readEntity(String.class);
                        System.out.println("Status=" + status);
                        Invocation.Builder requestLoad = client.target(HTTP_LOCALHOST_8080_EJB).path("customerService/loadCustomerById").queryParam("id", customerId)
                                .request(MediaType.APPLICATION_XML);
                        CustomerDto customerDto = requestLoad.get(CustomerDto.class);
                        System.out.println(customerDto);
                    }
                }
            });
        }
        executorService.shutdown();
    }
}
