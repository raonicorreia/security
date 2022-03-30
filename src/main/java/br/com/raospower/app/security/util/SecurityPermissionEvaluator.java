package br.com.raospower.app.security.util;

import br.com.raospower.app.repositorys.models.RolePermission;
import br.com.raospower.app.security.custom.CustomUserDetails;
import br.com.raospower.app.services.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private RolePermissionService perfilPermissaoService;

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Object target, Object permission) {
        return checkPermission(authentication, target.toString(), permission.toString());
    }

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable targetId, String targetType, Object permission) {
        return checkPermission(authentication, targetType, permission.toString());
    }

    public boolean checkPermission(Authentication authentication, String operation, String method) {
        CustomUserDetails user = this.getCustomUserDetails(authentication);
        List<String> list = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        RolePermission permission = this.perfilPermissaoService.findByRolePermission(list, operation, method);
        return permission != null;
    }

    private CustomUserDetails getCustomUserDetails(Authentication authentication) {
        return (CustomUserDetails)authentication.getPrincipal();
    }
}
