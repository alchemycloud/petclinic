package co.drytools.backend.util;

import org.slf4j.MDC;

public class MDCUtil {

    private static final String PRINCIPAL_KEY = "principal";

    public static void putPrincipal(String principal) {
        MDC.put(PRINCIPAL_KEY, String.format("%1$" + 25 + "s", principal));
    }

    public static void removePrincipal() {
        MDC.remove(PRINCIPAL_KEY);
    }
}
