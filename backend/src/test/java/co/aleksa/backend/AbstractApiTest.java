package co.aleksa.backend;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import co.aleksa.backend.audit.AuditFacade;
import co.aleksa.backend.service.InitService;
import co.aleksa.backend.util.AppThreadLocals;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract class AbstractApiTest extends AbstractDatabaseTest implements OwnerApiMockMvc, VetApiMockMvc, PetApiMockMvc, VisitApiMockMvc, FileApiMockMvc {

    private MockMvc mockMvc;

    @Inject private AuditFacade auditFacade;

    @Inject private InitService initService;

    private String currentUserAccessToken;

    @Inject public ObjectMapper objectMapper;

    @Inject private WebApplicationContext context;

    @BeforeEach
    public void before() {
        super.before();
        AppThreadLocals.initialize(Optional.empty(), Optional.of(Constants.DEFAULT_TEST_TENANT), Optional.empty());
        initService.runDataProcessors();
        flush();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Override
    public byte[] convertObjectToJsonBytes(Object object) throws IOException {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsBytes(object);
    }

    protected void flush() {
        auditFacade.flushInTransaction();
        auditFacade.flushAfterTransaction();
    }

    @Override
    public MockMvc getMockMvc() {
        return mockMvc;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public AbstractApiTest getAbstractApiTest() {
        return this;
    }

    public String getCurrentUserAccessToken() {
        return currentUserAccessToken;
    }

    public void setCurrentUserAccessToken(String currentUserAccessToken) {
        this.currentUserAccessToken = currentUserAccessToken;
    }
}
