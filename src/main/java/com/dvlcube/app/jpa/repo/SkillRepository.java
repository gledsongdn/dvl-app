package com.dvlcube.app.jpa.repo;

import com.dvlcube.app.jpa.BasicRepository;
import com.dvlcube.app.jpa.DvlRepository;
import com.dvlcube.app.manager.data.SkillBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ulisses Lima
 * @since 4 de jun de 2019
 */
@Repository
public interface SkillRepository extends DvlRepository<SkillBean, Long>, BasicRepository<SkillBean, Long> {

    @Query("select s from SkillBean s where s.name like %?1%")
    List<SkillBean> findSkillByNameLike(String name);

    SkillBean findByName(String name);

    boolean existsSkillBeanByName(String name);
}
