package internship.mbicycle.storify.service.impl;

import static internship.mbicycle.storify.util.ExceptionMessage.NOT_PERMISSIONS_EXCEPTION;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import internship.mbicycle.storify.exception.NotPermissionsException;
import internship.mbicycle.storify.model.Store;
import internship.mbicycle.storify.model.StorifyUser;
import internship.mbicycle.storify.service.CheckPermission;
import internship.mbicycle.storify.service.StorifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckPermissionImpl implements CheckPermission {

    private final StorifyUserService storifyUserService;

    @Override
    public Long checkPermissionByStoreId(Principal principal, Long storeId) {
        StorifyUser user = storifyUserService.getUserByEmail(principal.getName());
        List<Store> stores = user.getProfile().getStores();
        boolean isPermissions = stores.stream().anyMatch(store -> Objects.equals(store.getId(), storeId));
        if (!isPermissions) {
            throw new NotPermissionsException(NOT_PERMISSIONS_EXCEPTION);
        }
        return storeId;
    }
}
