package javaee7.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@NamedEntityGraph(
        name = "CustomersWithOrderId",
        includeAllAttributes=false,
        attributeNodes = {
                @NamedAttributeNode(value = "name"),
                @NamedAttributeNode(value = "orders", subgraph = "ordersGraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "ordersGraph",
                        type = OrderEntity.class,
                        attributeNodes = {
                                @NamedAttributeNode(value = "id"),
                                @NamedAttributeNode(value = "campaignId")
                        }
                )
        }
)
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", sequenceName = "CUSTOMER_SEQ_ID_SEQ", allocationSize = 1)
    private long id;

    @Column
    private String name;

    @Column
    private int numberOfPurchases;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderEntity> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AddressEntity> addresses;

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

    public Set<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderEntity> orders) {
        this.orders = orders;
    }

    public Set<AddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<AddressEntity> addresses) {
        this.addresses = addresses;
    }

    public int getNumberOfPurchases() {
        return numberOfPurchases;
    }

    public void setNumberOfPurchases(int numberOfPurchases) {
        this.numberOfPurchases = numberOfPurchases;
    }
}
