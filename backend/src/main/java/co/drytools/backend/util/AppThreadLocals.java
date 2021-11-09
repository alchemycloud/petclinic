package co.drytools.backend.util;

import co.drytools.backend.audit.AuditContext;
import co.drytools.backend.model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;
import org.aspectj.lang.ProceedingJoinPoint;

public final class AppThreadLocals {

    private static final ThreadLocal<String> CORRELATION_ID = ThreadLocal.withInitial(() -> UUID.randomUUID().toString());
    private static final ThreadLocal<Optional<String>> PRINCIPAL_EMAIL = ThreadLocal.withInitial(Optional::empty);
    private static final ThreadLocal<Optional<User>> PRINCIPAL = ThreadLocal.withInitial(Optional::empty);
    private static final ThreadLocal<AuditContext> CONTEXT = ThreadLocal.withInitial(AuditContext::new);
    private static final ThreadLocal<Optional<TimeZone>> TIME_ZONE = ThreadLocal.withInitial(Optional::empty);
    private static final ThreadLocal<ProceedingJoinPoint> PJP = new ThreadLocal<>();
    private static final ThreadLocal<Integer> QUERY_COUNT = ThreadLocal.withInitial(() -> 0);
    private static final ThreadLocal<Integer> QUERY_NUMBER_FOR_TEST = ThreadLocal.withInitial(() -> 0);
    private static final ThreadLocal<Boolean> COUNT_QUERIES = ThreadLocal.withInitial(() -> false);
    private static final ThreadLocal<Map<String, Integer>> QUERY_MAP = ThreadLocal.withInitial(HashMap::new);

    private AppThreadLocals() {
        throw new IllegalStateException("Utility class");
    }

    public static void initialize(Optional<String> correlationId, Optional<String> principalEmail) {
        clear();
        correlationId.ifPresent(CORRELATION_ID::set);
        principalEmail.ifPresent(AppThreadLocals::setPrincipalEmail);
    }

    public static void clear() {

        CORRELATION_ID.remove();
        final AuditContext auditContext = CONTEXT.get();
        CONTEXT.remove();
        final Optional<TimeZone> timeZone = TIME_ZONE.get();
        TIME_ZONE.remove();
        QUERY_MAP.remove();
        MDCUtil.removePrincipal();
        PRINCIPAL_EMAIL.remove();
        PRINCIPAL.remove();
        if (auditContext.isDirty()) {
            throw new IllegalStateException("App thread id dirty!");
        }
    }

    public static void forceClear() {
        final AuditContext auditContext = CONTEXT.get();
        CONTEXT.remove();
        QUERY_MAP.remove();
    }

    public static String getCorrelationId() {
        return CORRELATION_ID.get();
    }

    public static void setPrincipalEmail(String principalEmail) {
        PRINCIPAL_EMAIL.set(Optional.ofNullable(principalEmail));
        MDCUtil.putPrincipal(principalEmail);
    }

    public static Optional<String> getPrincipalEmail() {
        return PRINCIPAL_EMAIL.get();
    }

    public static void setPrincipal(User principal) {
        PRINCIPAL.set(Optional.ofNullable(principal));
    }

    public static Optional<User> getPrincipal() {
        return PRINCIPAL.get();
    }

    public static AuditContext getAuditContext() {
        return CONTEXT.get();
    }

    public static void cleanContext() {
        CONTEXT.remove();
    }

    public static Optional<TimeZone> getTimeZone() {
        return TIME_ZONE.get();
    }

    public static void setTimeZone(Optional<TimeZone> timeZone) {
        TIME_ZONE.set(timeZone);
    }

    public static void cleanTimeZone() {
        TIME_ZONE.remove();
    }

    public static void setPJP(ProceedingJoinPoint pjp) {
        PJP.set(pjp);
    }

    public static void clearPJP() {
        PJP.remove();
    }

    public static ProceedingJoinPoint getPJP() {
        return PJP.get();
    }

    public static Integer getQueryCount() {
        return QUERY_COUNT.get();
    }

    public static void setQueryCount(Integer count) {
        QUERY_COUNT.set(count);
    }

    public static void clearQueryCount() {
        QUERY_COUNT.remove();
    }

    public static Integer getQueryNumberForTest() {
        return QUERY_NUMBER_FOR_TEST.get();
    }

    public static void setQueryNumberForTest(Integer queryNumberForTest) {
        QUERY_NUMBER_FOR_TEST.set(queryNumberForTest);
    }

    public static void clearQueryNumberForTest() {
        QUERY_NUMBER_FOR_TEST.remove();
    }

    public static Boolean getCountQueries() {
        return COUNT_QUERIES.get();
    }

    public static void setCountQueries(Boolean count) {
        COUNT_QUERIES.set(count);
    }

    public static void clearCountQueries() {
        COUNT_QUERIES.remove();
    }

    public static Map<String, Integer> getQueryMap() {
        return QUERY_MAP.get();
    }
}
