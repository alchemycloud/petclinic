package co.aleksa.administration;

import cloud.alchemy.fabut.Fabut;
import co.aleksa.administration.api.dto.authenticationapi.ChangePasswordRequest;
import co.aleksa.administration.api.dto.authenticationapi.ForgotPasswordRequest;
import co.aleksa.administration.api.dto.authenticationapi.RefreshTokenRequest;
import co.aleksa.administration.api.dto.authenticationapi.ResetPasswordRequest;
import co.aleksa.administration.api.dto.authenticationapi.SignInRequest;
import co.aleksa.administration.api.dto.authenticationapi.SignInResponse;
import co.aleksa.administration.api.dto.authenticationapi.SignUpRequest;
import co.aleksa.administration.api.dto.authenticationapi.VerifyEmailRequest;
import co.aleksa.administration.api.dto.tenantapi.CreateTenantRequest;
import co.aleksa.administration.api.dto.tenantapi.CreateTenantResponse;
import co.aleksa.administration.api.dto.tenantapi.ReadTenantRequest;
import co.aleksa.administration.api.dto.tenantapi.ReadTenantResponse;
import co.aleksa.administration.api.dto.tenantapi.SearchTenantsRequest;
import co.aleksa.administration.api.dto.tenantapi.SearchTenantsResponse;
import co.aleksa.administration.api.dto.tenantapi.UpdateTenantRequest;
import co.aleksa.administration.api.dto.userapi.AdminsResponse;
import co.aleksa.administration.api.dto.userapi.CreateUserOnTenantRequest;
import co.aleksa.administration.api.dto.userapi.CreateUserRequest;
import co.aleksa.administration.api.dto.userapi.CreateUserResponse;
import co.aleksa.administration.api.dto.userapi.DeleteUserRequest;
import co.aleksa.administration.api.dto.userapi.GetActiveUserRequest;
import co.aleksa.administration.api.dto.userapi.GetActiveUserResponse;
import co.aleksa.administration.api.dto.userapi.NonAdminsResponse;
import co.aleksa.administration.api.dto.userapi.ReadUserRequest;
import co.aleksa.administration.api.dto.userapi.ReadUserResponse;
import co.aleksa.administration.api.dto.userapi.RemoveUserFromTenantRequest;
import co.aleksa.administration.api.dto.userapi.UpdateUserRequest;
import co.aleksa.administration.api.dto.userapi.UpdateUserResponse;
import co.aleksa.administration.api.dto.userapi.UserActivationDTO;
import co.aleksa.administration.audit.AuditFacade;
import co.aleksa.administration.config.CustomProperties;
import co.aleksa.administration.model.DataProcessorLog;
import co.aleksa.administration.model.DataVersion;
import co.aleksa.administration.model.Tenant;
import co.aleksa.administration.model.TenantUser;
import co.aleksa.administration.model.User;
import co.aleksa.administration.model.id.AbstractId;
import co.aleksa.administration.model.id.DataProcessorLogId;
import co.aleksa.administration.model.id.DataVersionId;
import co.aleksa.administration.model.id.TenantId;
import co.aleksa.administration.model.id.TenantUserId;
import co.aleksa.administration.model.id.UserId;
import co.aleksa.administration.repository.DataProcessorLogRepository;
import co.aleksa.administration.repository.DataVersionRepository;
import co.aleksa.administration.repository.TenantRepository;
import co.aleksa.administration.repository.TenantUserRepository;
import co.aleksa.administration.repository.UserRepository;
import co.aleksa.administration.util.AppThreadLocals;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.mail.MessagingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opentest4j.AssertionFailedError;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

@ActiveProfiles(resolver = TestProfileResolver.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AdministrationApplication.class)
@Transactional
public abstract class AbstractDatabaseTest extends Fabut {
    @Inject private CustomProperties customProperties;

    @Inject private AuditFacade auditFacade;
    @Inject protected UserRepository userRepository;

    @Inject protected TenantRepository tenantRepository;

    @Inject protected TenantUserRepository tenantUserRepository;

    @Inject protected DataVersionRepository dataVersionRepository;

    @Inject protected DataProcessorLogRepository dataProcessorLogRepository;
    private Wiser wiser;
    protected List<TestEmailMessage> expectedEmails;

    public AbstractDatabaseTest() {
        complexTypes.add(RefreshTokenRequest.class);
        complexTypes.add(SignInResponse.class);
        complexTypes.add(SignInRequest.class);
        complexTypes.add(SignInResponse.class);
        complexTypes.add(SignUpRequest.class);
        complexTypes.add(ForgotPasswordRequest.class);
        complexTypes.add(ResetPasswordRequest.class);
        complexTypes.add(VerifyEmailRequest.class);
        complexTypes.add(ChangePasswordRequest.class);
        complexTypes.add(CreateTenantRequest.class);
        complexTypes.add(CreateTenantResponse.class);
        complexTypes.add(UpdateTenantRequest.class);
        complexTypes.add(ReadTenantRequest.class);
        complexTypes.add(ReadTenantResponse.class);
        complexTypes.add(SearchTenantsRequest.class);
        complexTypes.add(SearchTenantsResponse.class);
        complexTypes.add(ReadUserRequest.class);
        complexTypes.add(ReadUserResponse.class);
        complexTypes.add(CreateUserRequest.class);
        complexTypes.add(CreateUserResponse.class);
        complexTypes.add(UpdateUserRequest.class);
        complexTypes.add(UpdateUserResponse.class);
        complexTypes.add(DeleteUserRequest.class);
        complexTypes.add(NonAdminsResponse.class);
        complexTypes.add(AdminsResponse.class);
        complexTypes.add(UserActivationDTO.class);
        complexTypes.add(GetActiveUserRequest.class);
        complexTypes.add(GetActiveUserResponse.class);
        complexTypes.add(CreateUserOnTenantRequest.class);
        complexTypes.add(RemoveUserFromTenantRequest.class);
        entityTypes.add(User.class);
        entityTypes.add(Tenant.class);
        entityTypes.add(TenantUser.class);
        entityTypes.add(DataVersion.class);
        entityTypes.add(DataProcessorLog.class);
        ignoredFields.putIfAbsent(User.class, new ArrayList<>());
        ignoredFields.get(User.class).add("lastHistory");
    }

    @BeforeEach
    public void before() {
        AppThreadLocals.initialize(Optional.of(UUID.randomUUID().toString()), Optional.empty(), Optional.empty());
        AppThreadLocals.setCountQueries(false);
        AppThreadLocals.setQueryNumberForTest(0);
        this.expectedEmails = new LinkedList<>();
        this.wiser = new Wiser();
        this.wiser.setPort(customProperties.getMail().getPort());
        this.wiser.start();
        super.before();
    }

    public void takeSnapshot(Integer numberOfQueriesForTest, Object... objects) {
        super.takeSnapshot(objects);
        wiser.getMessages().forEach(message -> expectedEmails.add(extractMessage(message)));

        AppThreadLocals.setCountQueries(true);
        AppThreadLocals.setQueryNumberForTest(numberOfQueriesForTest);
    }

    @AfterEach
    public void after() {
        final StringBuilder messageBuilder = new StringBuilder();
        AppThreadLocals.initialize(Optional.of(UUID.randomUUID().toString()), Optional.empty(), Optional.empty());
        AppThreadLocals.clearCountQueries();
        final Integer actualQueryCount = AppThreadLocals.getQueryCount();
        final Integer queryNumberForTest = AppThreadLocals.getQueryNumberForTest();
        AppThreadLocals.clearQueryNumberForTest();
        AppThreadLocals.clearQueryCount();

        this.wiser.stop();
        // assert emails sent and expected
        final List<TestEmailMessage> actualEmails = this.wiser.getMessages().stream().map(this::extractMessage).toList();

        final Iterator<TestEmailMessage> expectedIterator = this.expectedEmails.iterator();
        while (expectedIterator.hasNext()) {
            TestEmailMessage expected = expectedIterator.next();
            final Iterator<TestEmailMessage> actualIterator = actualEmails.iterator();
            while (actualIterator.hasNext()) {
                TestEmailMessage actual = actualIterator.next();
                if (expected.equals(actual)) {
                    expectedIterator.remove();
                    actualIterator.remove();
                    break;
                }
            }
        }

        for (TestEmailMessage email : this.expectedEmails) {
            messageBuilder.append("Expected message was not received: ").append(email);
        }
        for (TestEmailMessage email : actualEmails) {
            messageBuilder.append("Message was received but not asserted in test: ").append(email);
        }

        if (messageBuilder.length() > 0) {
            throw new AssertionFailedError(messageBuilder.toString());
        }

        if (!queryNumberForTest.equals(actualQueryCount)) {
            messageBuilder
                    .append("Number of queries for test method is not correct. Expected: ")
                    .append(queryNumberForTest)
                    .append(", but was: ")
                    .append(actualQueryCount);
        }
        super.after();
    }

    // Java junk, cannot call method that throws exception inside lambda, message.getMimeMessage().getSubject()
    private TestEmailMessage extractMessage(WiserMessage message) {
        try {
            return new TestEmailMessage(message.getEnvelopeSender(), message.getEnvelopeReceiver(), message.getMimeMessage().getSubject());
        } catch (MessagingException e) {
            return null;
        }
    }

    public void assertEmail(String sender, String receiver, String subject) {
        expectedEmails.add(new TestEmailMessage(sender, receiver, subject));
    }

    @Override
    public void customAssertEquals(Object expected, Object actual) {

        if (expected instanceof final ZonedDateTime expectedTime && actual instanceof final ZonedDateTime actualTime) {
            assertTimeWithMargin(expectedTime, actualTime);
        } else if (expected instanceof final AbstractId expectedId && actual instanceof final AbstractId actualId) {
            assertEquals(expectedId.getValue(), actualId.getValue());

        } else {
            assertEquals(expected, actual);
        }
    }

    public void assertTimeWithMargin(ZonedDateTime expectedTime, ZonedDateTime actualTime) {
        final long timeMargin = 60000;
        final long expectedMilli = expectedTime.toInstant().toEpochMilli();
        final long actualMili = actualTime.toInstant().toEpochMilli();
        if (expectedMilli > actualMili) assertTrue(expectedMilli - actualMili < timeMargin);
        else assertTrue(actualMili - expectedMilli < timeMargin);
    }

    @Override
    public List<?> findAll(Class<?> clazz) {
        if (clazz == User.class) {
            return userRepository.findAll();
        } else if (clazz == Tenant.class) {
            return tenantRepository.findAll();
        } else if (clazz == TenantUser.class) {
            return tenantUserRepository.findAll();
        } else if (clazz == DataVersion.class) {
            return dataVersionRepository.findAll();
        } else if (clazz == DataProcessorLog.class) {
            return dataProcessorLogRepository.findAll();
        }

        throw new IllegalStateException("No findAll for class: " + clazz.getName());
    }

    @Override
    public Object findById(final Class<?> entityClass, final Object id) {
        if (entityClass == User.class) {
            return userRepository.findById((UserId) id).orElse(null);
        } else if (entityClass == Tenant.class) {
            return tenantRepository.findById((TenantId) id).orElse(null);
        } else if (entityClass == TenantUser.class) {
            return tenantUserRepository.findById((TenantUserId) id).orElse(null);
        } else if (entityClass == DataVersion.class) {
            return dataVersionRepository.findById((DataVersionId) id).orElse(null);
        } else if (entityClass == DataProcessorLog.class) {
            return dataProcessorLogRepository.findById((DataProcessorLogId) id).orElse(null);
        }

        throw new IllegalStateException("No findById for class: " + entityClass.getName());
    }
}
