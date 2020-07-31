package com.dvlcube.app.rest;

import com.dvlcube.app.interfaces.MenuItem;
import com.dvlcube.app.jpa.repo.SkillRepository;
import com.dvlcube.app.manager.data.SkillBean;
import com.dvlcube.app.manager.data.vo.MxRestResponse;
import com.dvlcube.utils.interfaces.rest.MxFilterableBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.dvlcube.app.manager.data.e.Menu.CONFIGURATION;

/**
 * @author Ulisses Lima
 * @since 4 de jun de 2019
 */
@RestController
@MenuItem(value = CONFIGURATION)
@RequestMapping("${dvl.rest.prefix}/skills")
public class SkillService implements MxFilterableBeanService<SkillBean, Long> {

    @Autowired
    private SkillRepository repo;

    @Override
    @GetMapping
    public Iterable<SkillBean> get(@RequestParam Map<String, String> params) {
        return repo.firstPage(Sort.by("name"));
    }

    @Override
    @GetMapping("/{id}")
    public Optional<SkillBean> get(@PathVariable Long id) {
        return repo.findById(id);
    }

    @Override
    @PostMapping
    public MxRestResponse post(@Valid @RequestBody SkillBean body) {
        SkillBean save = repo.save(body);
        return GenericRestResponse.ok(save.getId());
    }

    /**
     * @param params
     * @return List<SkillBean>
     * @author Ulisses Lima
     * @since 18 de abr de 2019
     */
    @GetMapping("/filtered")
    public List<SkillBean> getFiltered(@RequestParam Map<String, String> params) {
        return repo.findAllBy(params);
    }

    /**
     * @param group
     * @param params
     * @return List<SkillBean>
     * @author Ulisses Lima
     * @since 18 de abr de 2019
     */
    @GetMapping("/group/{group}/filtered")
    public List<SkillBean> getGroupFiltered(@PathVariable String group, @RequestParam Map<String, String> params) {
        return repo.findAllBy(params, group);
    }

    @GetMapping("/like")
    public Iterable<SkillBean> getLike(@RequestParam(required = true) String name) {
        return repo.findSkillByNameLike(name);
    }

    @GetMapping("/name/{name}")
    public SkillBean getByName(@PathVariable String name) {
        return repo.findByName(name);
    }

    @GetMapping("/exists/name/{name}")
    public boolean existsName(@PathVariable String name) {
        return repo.existsSkillBeanByName(name);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
