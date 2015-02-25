package com.wordpress.martinsdeveloperworld.entity;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonListener {
	private static final Logger LOGGER = Logger.getLogger(PersonListener.class.getName());
	@Resource
	ManagedExecutorService managedExecutorService;
	@Inject
	Instance<MyTask> myTaskInstance;

	@PostPersist
	public void postPersist(Person person) {
		String msg = "postPersist(): " + person.getId();
		LOGGER.log(Level.INFO, msg);
		AuditLog auditLog = new AuditLog();
		auditLog.setMessage(msg);
		MyTask myTask = myTaskInstance.get();
		myTask.setAuditLog(auditLog);
		this.managedExecutorService.submit(myTask);
	}
}
