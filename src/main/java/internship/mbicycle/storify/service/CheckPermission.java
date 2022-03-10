package internship.mbicycle.storify.service;

import java.security.Principal;

public interface CheckPermission {

    boolean checkPermissionByStoreId(Principal principal, Long storeId);
}
