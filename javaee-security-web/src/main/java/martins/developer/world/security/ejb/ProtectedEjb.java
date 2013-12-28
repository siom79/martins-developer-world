package martins.developer.world.security.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Named;

@Stateless
@Named(value = "protectedEjb")
public class ProtectedEjb {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtectedEjb.class);

    @RolesAllowed("protected-role")
    public void protectedAction() {
        LOGGER.info("Called protectedAction().");
    }
}
