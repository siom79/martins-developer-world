package com.wordpress.martinsdeveloperworld.control;

import com.wordpress.martinsdeveloperworld.entity.Person;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import java.util.List;

@Stateful
public class Controller {
	@PersistenceContext(type = PersistenceContextType.EXTENDED, synchronization = SynchronizationType.UNSYNCHRONIZED)
	EntityManager entityManager;

	public Person persist() {
		Person p = new Person();
		p.setFirstName("Martin");
		p.setLastName("Developer");
		return entityManager.merge(p);
	}

	public List<Person> list() {
		return entityManager.createQuery("from Person", Person.class).getResultList();
	}

	public void commit() {
		entityManager.joinTransaction();
	}

	@Remove
	public void remove() {

	}
}
