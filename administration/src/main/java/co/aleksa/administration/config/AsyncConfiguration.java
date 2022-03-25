package co.aleksa.administration.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@Profile("!test")
public class AsyncConfiguration implements AsyncConfigurer {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncConfiguration.class);

    @Bean(name = "multiThreadExecutor")
    public Executor multiThreadExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // this should set the number of threads, and it should never change with this config (setting queue capacity will change that)
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors() * 4); // sets number of threads
        executor.setMaxPoolSize(Integer.MAX_VALUE); // maximum number of threads, but a new thread is increased only if queue is filled
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setThreadNamePrefix("async-worker-exec-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
