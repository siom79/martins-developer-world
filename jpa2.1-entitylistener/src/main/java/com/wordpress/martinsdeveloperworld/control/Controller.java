package com.wordpress.martinsdeveloperworld.control;

import com.wordpress.martinsdeveloperworld.entity.AuditLog;
import com.wordpress.martinsdeveloperworld.entity.Person;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class Controller {
	@PersistenceContext
	EntityManager entityManager;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Person persist() {
		Person p = new Person();
		p.setFirstName("Martin");
		p.setLastName("Developer");
		return entityManager.merge(p);
	}

	public List<Person> list() {
		return entityManager.createQuery("from Person", Person.class).getResultList();
	}

	public List<AuditLog> listAudits() {
		return entityManager.createQuery("from AuditLog", AuditLog.class).getResultList();
	}

	public void persist(AuditLog auditLog) {
		entityManager.persist(auditLog);
	}
}
