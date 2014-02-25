package javaee7.control;

import javaee7.entity.MyEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class MyTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyTask.class);
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run() {
        UserTransaction userTransaction = null;
        try {
            userTransaction = lookup();
            userTransaction.begin();
            MyEntity myEntity = new MyEntity();
            myEntity.setName("name");
            entityManager.persist(myEntity);
            userTransaction.commit();
        } catch (Exception e) {
            try {
                if(userTransaction != null) {
                    userTransaction.rollback();
                }
            } catch (SystemException e1) {
                LOGGER.error("Failed to rollback transaction: "+e1.getMessage());
            }
        }
    }

    private UserTransaction lookup() throws NamingException {
        InitialContext ic = new InitialContext();
        return (UserTransaction)ic.lookup("java:comp/UserTransaction");
    }
}
