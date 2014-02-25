package javaee7.boundary;

import javaee7.control.MyBean;
import javaee7.entity.MyEntity;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/myResource")
public class MyResource {
    @Inject
    private MyBean myBean;

    @Path("list")
    @GET
    @Produces("text/json")
    public List<MyEntity> list() {
        return myBean.list();
    }

    @Path("persist")
    @GET
    @Produces("text/html")
    public String persist() throws ExecutionException, InterruptedException {
        myBean.executeAsync();
        return "<html><h1>persist!</h1></html>";
    }
}
