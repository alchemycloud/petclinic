package co.drytools.backend.interceptor;

import co.drytools.backend.util.AppThreadLocals;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.hibernate.EmptyInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryCountInterceptor extends EmptyInterceptor {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(QueryCountInterceptor.class);
    private static boolean logQueries = false;

    @Override
    public String onPrepareStatement(String sql) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(sql);
        }
        if (sql.startsWith("select") && AppThreadLocals.getCountQueries()) {
            AppThreadLocals.setQueryCount(AppThreadLocals.getQueryCount() + 1);
            if (logQueries) {
                final Map<String, Integer> map = AppThreadLocals.getQueryMap();
                map.put(sql, map.getOrDefault(sql, 0) + 1);
            }
        }

        return super.onPrepareStatement(sql);
    }

    @Override
    public void postFlush(Iterator entities) {
        super.postFlush(entities);
    }

    public static void setLogQueries(boolean logQueries) {
        QueryCountInterceptor.logQueries = logQueries;
    }

    public static void printReport() {
        final Map<String, Integer> map = AppThreadLocals.getQueryMap();
        final List<Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());
        Collections.reverse(list);
        LOG.debug("number of calls, query");
        int total = 0;
        for (Entry<String, Integer> entry : list) {
            System.out.println(entry.getValue() + ", " + entry.getKey() + "");
            total += entry.getValue();
        }
        LOG.debug("total: " + total + " calls");
    }
}
