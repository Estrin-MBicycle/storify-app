package internship.mbicycle.storify.service;

import java.security.Principal;

public interface PermissionsCheckService {

    Long checkPermissionByStoreId(Principal principal, Long storeId);
}
