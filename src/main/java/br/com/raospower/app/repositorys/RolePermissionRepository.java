package br.com.raospower.app.repositorys;

import br.com.raospower.app.repositorys.keys.RolePermissionKey;
import br.com.raospower.app.repositorys.models.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionKey> {

    @Query(
            value = "SELECT PFP.* FROM TB_RPM_ROLE_PERMISSION PFP" +
                    " INNER JOIN TB_ROL_ROLE PRF ON (PRF.ID = PFP.ROLE_ID)" +
                    " INNER JOIN TB_PRM_PERMISSION PRM ON (PRM.ID = PFP.PERMISSION_ID)" +
                    " WHERE PRF.NAME in (:roles)" +
                    " AND PRM.NAME = :permission" +
                    " AND PRM.METHOD = :method"
            , nativeQuery = true
    )
    RolePermission findByRolePermission(
            @Param("roles") List<String> roles,
            @Param("permission") String permission,
            @Param("method") String method);

    List<RolePermission> findRolePermissionByRoleId(Long id);
}
