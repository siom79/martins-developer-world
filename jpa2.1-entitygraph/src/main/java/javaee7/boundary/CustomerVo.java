package javaee7.boundary;

import java.util.LinkedList;
import java.util.List;

public class CustomerVo {
    private long id;
    private String name;
    private List<OrderVo> orders = new LinkedList<>();
    private List<AddressVo> addresses = new LinkedList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrderVo> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderVo> orders) {
        this.orders = orders;
    }

    public List<AddressVo> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressVo> addresses) {
        this.addresses = addresses;
    }
}
