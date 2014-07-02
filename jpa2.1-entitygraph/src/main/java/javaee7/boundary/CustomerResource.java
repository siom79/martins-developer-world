package javaee7.boundary;

import javaee7.control.CustomerBean;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/customerResource")
public class CustomerResource {
    @Inject
    private CustomerBean customerBean;

    @Path("list")
    @GET
    @Produces("text/json")
    public List<CustomerVo> list() {
        return customerBean.list();
    }

    @Path("listFetch")
    @GET
    @Produces("text/json")
    public List<CustomerVo> listFetch() {
        return customerBean.listFetch();
    }

    @Path("listNamedEntityGraph")
    @GET
    @Produces("text/json")
    public List<CustomerVo> listNamedEntityGraph() {
        return customerBean.listNamedEntityGraph();
    }

    @Path("persist")
    @GET
    @Produces("text/html")
    public String persist() throws ExecutionException, InterruptedException {
        long id = customerBean.persist();
        return "<html><h1>persist() successful: " + id + "</h1></html>";
    }

    @Path("deleteOrders")
    @GET
    @Produces("text/html")
    public String deleteOrders() {
        int deleteOrders = customerBean.deleteOrders();
        return "<html><h1>deleteOrders() successful: " + deleteOrders + "</h1></html>";
    }
}
