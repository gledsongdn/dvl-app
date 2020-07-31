package com.dvlcube.app.rest;

import com.dvlcube.app.interfaces.MenuItem;
import com.dvlcube.utils.aspects.stats.Stat;
import com.dvlcube.utils.aspects.stats.Stats;
import com.dvlcube.utils.interfaces.MxService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.dvlcube.app.manager.data.e.Menu.MONITORING;
import static com.dvlcube.utils.query.MxQuery.$;

/**
 * Application performance stats.
 *
 * @author Ulisses Lima
 * @since 17 de abr de 2019
 */
@RestController
@MenuItem(value = MONITORING, readOnly = true)
@RequestMapping("${dvl.rest.prefix}/stats")
public class StatService implements MxService {
    private Logger log = LogManager.getLogger(this.getClass());

    /**
     * @param params
     * @return stats
     * @author Ulisses Lima
     * @since 17 de abr de 2019
     */
    @GetMapping
    public List<Stat> get(@RequestParam Map<String, String> params) {
        List<Stat> values = Stats.values();
        Comparator<Stat> statComparator = Comparator.comparingLong(Stat::getTotal).thenComparingDouble(Stat::avg);
        values.sort(statComparator);
        return values;
    }

    /**
     * @param params
     * @return stats
     * @author Ulisses Lima
     * @since 17 de abr de 2019
     */
    @DeleteMapping
    public List<Stat> delete(@RequestParam Map<String, String> params) {
        Stats.consume(item -> log.info("removing {}", item));
        return get(params);
    }

    /**
     * @param id
     * @return stat by action
     * @author Ulisses Lima
     * @since 17 de abr de 2019
     */
    @GetMapping("/{id}")
    public Stat get(@PathVariable String id) {
        return $(Stats.values()).filterOne(stat -> stat.getAction().equals(id));
    }

    /**
     * @param regex expression
     * @return stats that match the regex
     * @author Ulisses Lima
     * @since 17 de abr de 2019
     */
    @GetMapping("/matching/{regex}")
    public Collection<Stat> getMatching(@PathVariable String regex) {
        return $(Stats.values()).filter(stat -> stat.getAction().matches(regex)).o;
    }

    /**
     * @param params
     * @param id
     * @return Iterable<Stat> stats like id, fallback to GET params
     * @author Ulisses Lima
     * @since 2 de mai de 2019
     */
    @GetMapping("/like")
    public Iterable<Stat> getLike(@RequestParam Map<String, String> params, @RequestParam(required = true) String id) {
        if ($(id).isBlank())
            return get(params);

        return $(Stats.values()).filter(stat -> stat.getAction().toLowerCase().contains(id.toLowerCase())).o;
    }
}
