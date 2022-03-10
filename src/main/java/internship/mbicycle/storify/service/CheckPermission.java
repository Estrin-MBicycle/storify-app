package internship.mbicycle.storify.service;

import java.security.Principal;

public interface CheckPermission {

    Long checkPermissionByStoreId(Principal principal, Long storeId);
}
