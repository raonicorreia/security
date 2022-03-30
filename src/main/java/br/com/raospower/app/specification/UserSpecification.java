package br.com.raospower.app.specification;

import br.com.raospower.app.repositorys.models.User;
import br.com.raospower.app.services.dto.UserDTO;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Locale;

public class UserSpecification implements Specification<User> {

    private UserDTO user;

    public UserSpecification(UserDTO user) {
        this.user = user;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = null;
        if (user != null && !user.getName().isEmpty()) {
                predicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")), "%" + user.getName().toLowerCase(Locale.ROOT) + "%");
        }
        return predicate;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
