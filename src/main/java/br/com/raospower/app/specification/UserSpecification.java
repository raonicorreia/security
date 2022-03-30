package br.com.raospower.app.specification;

import br.com.raospower.app.repositorys.models.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Locale;

public class UserSpecification implements Specification<User> {

    private User user;

    public UserSpecification(User user) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
