package com.dvlcube.app.manager.data;

import com.dvlcube.utils.interfaces.MxBean;
import com.dvlcube.utils.interfaces.Nameable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Ulisses Lima
 * @since 3 de jun de 2019
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class JobBean implements MxBean<Long>, Nameable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private String name;
    private Integer max;

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

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }
}
