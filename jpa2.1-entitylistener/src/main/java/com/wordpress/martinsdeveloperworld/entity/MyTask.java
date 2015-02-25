package com.wordpress.martinsdeveloperworld.entity;

import com.wordpress.martinsdeveloperworld.control.Controller;

import javax.inject.Inject;

public class MyTask implements Runnable {
	@Inject
	private Controller controller;
	private AuditLog auditLog;

	@Override
	public void run() {
		controller.persist(auditLog);
	}

	public void setAuditLog(AuditLog auditLog) {
		this.auditLog = auditLog;
	}

	public AuditLog getAuditLog() {
		return auditLog;
	}
}
