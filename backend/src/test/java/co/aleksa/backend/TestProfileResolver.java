package co.aleksa.backend;

import org.springframework.test.context.ActiveProfilesResolver;

public class TestProfileResolver implements ActiveProfilesResolver {

    @Override
    public String[] resolve(Class<?> testClass) {
        // ensures that tests are run with 'test' profile
        return new String[] {"test"};
    }
}
