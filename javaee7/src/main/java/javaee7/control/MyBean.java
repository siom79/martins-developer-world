package javaee7.control;

import javaee7.entity.MyEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Stateless
public class MyBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBean.class);
    @Resource
    ManagedExecutorService managedExecutorService;
    @PersistenceContext
    EntityManager entityManager;
    @Inject
    Instance<MyTask> myTaskInstance;

    public void executeAsync() throws ExecutionException, InterruptedException {
        List<Future> futures = new LinkedList<>();
        for(int i=0; i<10; i++) {
            MyTask myTask = myTaskInstance.get();
            this.managedExecutorService.submit(myTask);
        }
    }

    public List<MyEntity> list() {
        return entityManager.createQuery("select m from MyEntity m", MyEntity.class).getResultList();
    }
}
