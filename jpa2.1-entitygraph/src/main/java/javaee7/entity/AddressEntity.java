package javaee7.entity;

import javax.persistence.*;

@Entity
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(name = "address_seq", sequenceName = "ADDRESS_SEQ_ID_SEQ", allocationSize = 1)
    private long id;

    @Column
    private String city;

    @Column
    private String street;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="CUSTOMER_ID")
    private CustomerEntity customer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
}
