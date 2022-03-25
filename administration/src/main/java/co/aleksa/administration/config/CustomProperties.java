package co.aleksa.administration.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "custom", ignoreUnknownFields = false)
public class CustomProperties {

    private String environment;
    private String secretKey;
    private Integer accessTokenValidityInSeconds;
    private Integer refreshTokenValidityInSeconds;
    private String clientUrl;
    private final Datasource datasource = new Datasource();

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getAccessTokenValidityInSeconds() {
        return this.accessTokenValidityInSeconds;
    }

    public void setAccessTokenValidityInSeconds(Integer accessTokenValidityInSeconds) {
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
    }

    public Integer getRefreshTokenValidityInSeconds() {
        return this.refreshTokenValidityInSeconds;
    }

    public void setRefreshTokenValidityInSeconds(Integer refreshTokenValidityInSeconds) {
        this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
    }

    public Datasource getDatasource() {
        return datasource;
    }

    public static class Datasource {

        private static final int DEFAULT_CACHE_SIZE = 250;

        private boolean cachePrepStmts = true;

        private int prepStmtCacheSize = DEFAULT_CACHE_SIZE;

        private int prepStmtCacheSqlLimit = DEFAULT_CACHE_SIZE;

        private boolean useServerPrepStmts = true;

        public boolean isCachePrepStmts() {
            return cachePrepStmts;
        }

        public void setCachePrepStmts(boolean cachePrepStmts) {
            this.cachePrepStmts = cachePrepStmts;
        }

        public int getPrepStmtCacheSize() {
            return prepStmtCacheSize;
        }

        public void setPrepStmtCacheSize(int prepStmtCacheSize) {
            this.prepStmtCacheSize = prepStmtCacheSize;
        }

        public int getPrepStmtCacheSqlLimit() {
            return prepStmtCacheSqlLimit;
        }

        public void setPrepStmtCacheSqlLimit(int prepStmtCacheSqlLimit) {
            this.prepStmtCacheSqlLimit = prepStmtCacheSqlLimit;
        }

        public boolean isUseServerPrepStmts() {
            return useServerPrepStmts;
        }

        public void setUseServerPrepStmts(boolean useServerPrepStmts) {
            this.useServerPrepStmts = useServerPrepStmts;
        }
    }

    public static class Logs {

        private boolean enabled;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public static class Graphite {

        private static final int DEFAULT_PORT = 2003;

        private boolean enabled;

        private String host = "localhost";

        private int port = DEFAULT_PORT;

        private String prefix = "administration";

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    private final Mail mail = new Mail();

    public Mail getMail() {
        return mail;
    }

    public static class Mail {

        private String from;
        private String host;
        private int port;
        private String username;
        private String password;
        private String starttls;
        private String auth;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getStarttls() {
            return starttls;
        }

        public void setStarttls(String starttls) {
            this.starttls = starttls;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }
    }
}
