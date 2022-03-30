package br.com.raospower.app.repositorys.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "TB_PRM_PERMISSION")
public class Permission implements Serializable {

    @Id
    @SequenceGenerator(name = "SQ_PERMISSION", sequenceName = "SQ_PERMISSION", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "SQ_PERMISSION")
    private Long id;

    @Column
    private String name;

    @Column
    private String method;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
