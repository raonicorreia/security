package br.com.raospower.app.controllers;

import br.com.raospower.app.repositorys.models.RolePermission;
import br.com.raospower.app.security.custom.CustomUserDetails;
import br.com.raospower.app.services.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BaseController {

    @Autowired
    private RolePermissionService perfilPermissaoService;

    public boolean authorize(String pathPermission, Authentication authentication) {
        CustomUserDetails user = getCustomUserDetails(authentication);
        List<String> list = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        RolePermission permissao = perfilPermissaoService.findByRolePermission(list, pathPermission, null);
        return permissao != null;
    }

    private CustomUserDetails getCustomUserDetails(Authentication authentication) {
        return (CustomUserDetails) authentication.getPrincipal();
    }
}
