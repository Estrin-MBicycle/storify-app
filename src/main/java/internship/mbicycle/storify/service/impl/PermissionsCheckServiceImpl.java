package internship.mbicycle.storify.service.impl;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_HAVE_PERMISSION_TO_PERFORM_THIS_OPERATION;

import java.security.Principal;
import java.util.List;

import internship.mbicycle.storify.exception.PermissionsException;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.PermissionsCheckService;
import internship.mbicycle.storify.service.StoreService;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PermissionsCheckServiceImpl implements PermissionsCheckService {

    private final StorifyUserService storifyUserService;
    private final StoreService storeService;

    @Override
    public Long checkPermissionByStoreId(Principal principal, Long storeId) {
        StorifyUser user = storifyUserService.getUserByEmail(principal.getName());
        List<Store> stores = user.getProfile().getStores();
        boolean isPermissions = stores.contains(storeService.getStoreFromDbById(storeId));
        if (!isPermissions) {
            throw new PermissionsException(NOT_HAVE_PERMISSION_TO_PERFORM_THIS_OPERATION);
        }
        return storeId;
    }
}
