package javaee.interceptors.control;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class Ordering {
    @PersistenceContext
    EntityManager entityManager;


}
