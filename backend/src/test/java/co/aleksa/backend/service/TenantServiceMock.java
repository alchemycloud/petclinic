package co.aleksa.backend.service;

import co.aleksa.backend.Constants;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class TenantServiceMock implements TenantService {

    public List<String> getTenants() {
        return Collections.singletonList(Constants.DEFAULT_TEST_TENANT);
    }

    @Override
    public List<String> getTenantsByEmail(String username) {
        return getTenants();
    }

    @Override
    public List<String> getTenantsBySetPasswordCode(String code) {
        return getTenants();
    }
}
