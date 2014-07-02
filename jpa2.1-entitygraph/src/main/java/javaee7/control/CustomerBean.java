package javaee7.control;

import javaee7.boundary.AddressVo;
import javaee7.boundary.CustomerVo;
import javaee7.boundary.OrderVo;
import javaee7.entity.AddressEntity;
import javaee7.entity.CustomerEntity;
import javaee7.entity.OrderEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Stateless
public class CustomerBean {
    @PersistenceContext
    EntityManager entityManager;

    public List<CustomerVo> list() {
        List<CustomerEntity> resultList = entityManager.createQuery("SELECT c FROM CustomerEntity AS c", CustomerEntity.class).getResultList();
        return map2Vos(resultList);
    }

    public List<CustomerVo> listFetch() {
        List<CustomerEntity> resultList = entityManager.createQuery("SELECT c FROM CustomerEntity AS c JOIN FETCH c.orders AS o JOIN FETCH c.addresses", CustomerEntity.class).getResultList();
        return map2Vos(resultList);
    }

    public List<CustomerVo> listNamedEntityGraph() {
        EntityGraph entityGraph = entityManager.getEntityGraph("CustomersWithOrderId");
        List<CustomerEntity> resultList = entityManager.createQuery("SELECT c FROM CustomerEntity AS c", CustomerEntity.class).setHint("javax.persistence.fetchgraph", entityGraph).getResultList();
        return map2Vos(resultList);
    }

    private List<CustomerVo> map2Vos(List<CustomerEntity> resultList) {
        List<CustomerVo> vos = new LinkedList<>();
        for(CustomerEntity customerEntity : resultList) {
            CustomerVo customerVo = new CustomerVo();
            customerVo.setId(customerEntity.getId());
            customerVo.setName(customerEntity.getName());
            Set<OrderEntity> orders = customerEntity.getOrders();
            for(OrderEntity orderEntity : orders) {
                OrderVo orderVo = new OrderVo();
                orderVo.setId(orderEntity.getId());
                orderVo.setCampaignId(orderEntity.getCampaignId());
                orderVo.setTimestamp(orderEntity.getTimestamp());
                customerVo.getOrders().add(orderVo);
            }
            for(AddressEntity addressEntity : customerEntity.getAddresses()) {
                AddressVo addressVo = new AddressVo();
                addressVo.setId(addressEntity.getId());
                addressVo.setCity(addressEntity.getCity());
                addressVo.setStreet(addressEntity.getStreet());
                customerVo.getAddresses().add(addressVo);
            }
            vos.add(customerVo);
        }
        return vos;
    }

    public long persist() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName("name");
        customerEntity.setOrders(new HashSet<OrderEntity>());
        customerEntity.setAddresses(new HashSet<AddressEntity>());
        for(int i=0; i<3; i++) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setCustomer(customerEntity);
            orderEntity.setTimestamp(new Date());
            orderEntity.setCampaignId(42);
            customerEntity.getOrders().add(orderEntity);
        }
        for(int i=0; i<2; i++) {
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setCity("City");
            addressEntity.setStreet("Street");
            customerEntity.getAddresses().add(addressEntity);
        }
        CustomerEntity mergedCustomer = entityManager.merge(customerEntity);
        return mergedCustomer.getId();
    }

    public int deleteOrders() {
        return entityManager.createQuery("delete from "+OrderEntity.class.getSimpleName()).executeUpdate();
    }
}
