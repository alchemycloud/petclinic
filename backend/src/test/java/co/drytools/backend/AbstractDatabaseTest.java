package co.drytools.backend;

import cloud.alchemy.fabut.Fabut;
import co.drytools.backend.api.dto.authenticationapi.ChangePasswordRequest;
import co.drytools.backend.api.dto.authenticationapi.ForgotPasswordRequest;
import co.drytools.backend.api.dto.authenticationapi.RefreshTokenRequest;
import co.drytools.backend.api.dto.authenticationapi.ResetPasswordRequest;
import co.drytools.backend.api.dto.authenticationapi.SignInRequest;
import co.drytools.backend.api.dto.authenticationapi.SignInResponse;
import co.drytools.backend.api.dto.authenticationapi.SignUpRequest;
import co.drytools.backend.api.dto.authenticationapi.VerifyEmailRequest;
import co.drytools.backend.api.dto.fileapi.FindFileRequest;
import co.drytools.backend.api.dto.ownerapi.AllOwnersRequest;
import co.drytools.backend.api.dto.ownerapi.AllOwnersResponse;
import co.drytools.backend.api.dto.ownerapi.CreateOwnerRequest;
import co.drytools.backend.api.dto.ownerapi.CreateOwnerResponse;
import co.drytools.backend.api.dto.ownerapi.DeleteOwnerRequest;
import co.drytools.backend.api.dto.ownerapi.MyPetsResponse;
import co.drytools.backend.api.dto.ownerapi.OwnerVetsResponse;
import co.drytools.backend.api.dto.ownerapi.OwnersForAddressRequest;
import co.drytools.backend.api.dto.ownerapi.OwnersForAddressResponse;
import co.drytools.backend.api.dto.ownerapi.OwnersPetsRequest;
import co.drytools.backend.api.dto.ownerapi.OwnersPetsResponse;
import co.drytools.backend.api.dto.ownerapi.OwnersWithPetsResponse;
import co.drytools.backend.api.dto.ownerapi.ReadOwnerRequest;
import co.drytools.backend.api.dto.ownerapi.ReadOwnerResponse;
import co.drytools.backend.api.dto.ownerapi.UpdateOwnerRequest;
import co.drytools.backend.api.dto.ownerapi.UpdateOwnerResponse;
import co.drytools.backend.api.dto.petapi.CreatePetRequest;
import co.drytools.backend.api.dto.petapi.CreatePetResponse;
import co.drytools.backend.api.dto.petapi.DeletePetRequest;
import co.drytools.backend.api.dto.petapi.FindPetbyTypeRequest;
import co.drytools.backend.api.dto.petapi.FindPetbyTypeResponse;
import co.drytools.backend.api.dto.petapi.PetsRequest;
import co.drytools.backend.api.dto.petapi.PetsResponse;
import co.drytools.backend.api.dto.petapi.ReadPetRequest;
import co.drytools.backend.api.dto.petapi.ReadPetResponse;
import co.drytools.backend.api.dto.petapi.UpdatePetRequest;
import co.drytools.backend.api.dto.petapi.UpdatePetResponse;
import co.drytools.backend.api.dto.userapi.AdminUsersResponse;
import co.drytools.backend.api.dto.userapi.CreateUserRequest;
import co.drytools.backend.api.dto.userapi.CreateUserResponse;
import co.drytools.backend.api.dto.userapi.DeleteUserRequest;
import co.drytools.backend.api.dto.userapi.NonAdminsResponse;
import co.drytools.backend.api.dto.userapi.ReadUserRequest;
import co.drytools.backend.api.dto.userapi.ReadUserResponse;
import co.drytools.backend.api.dto.userapi.UpdateUserRequest;
import co.drytools.backend.api.dto.userapi.UpdateUserResponse;
import co.drytools.backend.api.dto.userapi.UserActivationSimpleDTO;
import co.drytools.backend.api.dto.userapi.UserDTO;
import co.drytools.backend.api.dto.userapi.UserResponseDTO;
import co.drytools.backend.api.dto.userapi.UsersResponse;
import co.drytools.backend.api.dto.vetapi.CreateVetRequest;
import co.drytools.backend.api.dto.vetapi.CreateVetResponse;
import co.drytools.backend.api.dto.vetapi.DeleteVetRequest;
import co.drytools.backend.api.dto.vetapi.ReadVetRequest;
import co.drytools.backend.api.dto.vetapi.ReadVetResponse;
import co.drytools.backend.api.dto.vetapi.UpdateVetRequest;
import co.drytools.backend.api.dto.vetapi.UpdateVetResponse;
import co.drytools.backend.api.dto.vetapi.VetDTO;
import co.drytools.backend.api.dto.vetapi.VetInfoRequest;
import co.drytools.backend.api.dto.vetapi.VetWithSpecialtiesDTO;
import co.drytools.backend.api.dto.visitapi.CreateVisitRequest;
import co.drytools.backend.api.dto.visitapi.CreateVisitResponse;
import co.drytools.backend.api.dto.visitapi.DeleteVisitRequest;
import co.drytools.backend.api.dto.visitapi.MyVisitsRequest;
import co.drytools.backend.api.dto.visitapi.MyVisitsResponse;
import co.drytools.backend.api.dto.visitapi.ReadVisitRequest;
import co.drytools.backend.api.dto.visitapi.ReadVisitResponse;
import co.drytools.backend.api.dto.visitapi.ScheduledVisitsResponse;
import co.drytools.backend.api.dto.visitapi.UpdateVisitRequest;
import co.drytools.backend.api.dto.visitapi.UpdateVisitResponse;
import co.drytools.backend.api.dto.visitapi.VetVisitsRequest;
import co.drytools.backend.api.dto.visitapi.VetVisitsResponse;
import co.drytools.backend.audit.AuditFacade;
import co.drytools.backend.config.CustomProperties;
import co.drytools.backend.model.DataProcessorLog;
import co.drytools.backend.model.DataVersion;
import co.drytools.backend.model.Owner;
import co.drytools.backend.model.Pet;
import co.drytools.backend.model.User;
import co.drytools.backend.model.Vet;
import co.drytools.backend.model.VetSpecialities;
import co.drytools.backend.model.VetSpeciality;
import co.drytools.backend.model.Visit;
import co.drytools.backend.model.id.AbstractId;
import co.drytools.backend.model.id.DataProcessorLogId;
import co.drytools.backend.model.id.DataVersionId;
import co.drytools.backend.model.id.OwnerId;
import co.drytools.backend.model.id.PetId;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.model.id.VetId;
import co.drytools.backend.model.id.VetSpecialitiesId;
import co.drytools.backend.model.id.VetSpecialityId;
import co.drytools.backend.model.id.VisitId;
import co.drytools.backend.repository.DataProcessorLogRepository;
import co.drytools.backend.repository.DataVersionRepository;
import co.drytools.backend.repository.OwnerRepository;
import co.drytools.backend.repository.PetRepository;
import co.drytools.backend.repository.UserRepository;
import co.drytools.backend.repository.VetRepository;
import co.drytools.backend.repository.VetSpecialitiesRepository;
import co.drytools.backend.repository.VetSpecialityRepository;
import co.drytools.backend.repository.VisitRepository;
import co.drytools.backend.rest.dto.ownerapi.RestUpdateOwnerRequest;
import co.drytools.backend.rest.dto.petapi.RestUpdatePetRequest;
import co.drytools.backend.rest.dto.vetapi.RestCreateVetRequest;
import co.drytools.backend.rest.dto.vetapi.RestUpdateVetRequest;
import co.drytools.backend.rest.dto.visitapi.RestUpdateVisitRequest;
import co.drytools.backend.util.AppThreadLocals;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BackendApplication.class)
@Transactional
public abstract class AbstractDatabaseTest extends Fabut {
    @Inject private CustomProperties customProperties;

    @Inject private AuditFacade auditFacade;
    @Inject protected UserRepository userRepository;

    @Inject protected OwnerRepository ownerRepository;

    @Inject protected VetRepository vetRepository;

    @Inject protected VetSpecialityRepository vetSpecialityRepository;

    @Inject protected VetSpecialitiesRepository vetSpecialitiesRepository;

    @Inject protected PetRepository petRepository;

    @Inject protected VisitRepository visitRepository;

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
        complexTypes.add(ReadUserRequest.class);
        complexTypes.add(ReadUserResponse.class);
        complexTypes.add(CreateUserRequest.class);
        complexTypes.add(CreateUserResponse.class);
        complexTypes.add(UpdateUserRequest.class);
        complexTypes.add(UpdateUserResponse.class);
        complexTypes.add(DeleteUserRequest.class);
        complexTypes.add(UsersResponse.class);
        complexTypes.add(NonAdminsResponse.class);
        complexTypes.add(AdminUsersResponse.class);
        complexTypes.add(UserActivationSimpleDTO.class);
        complexTypes.add(UserResponseDTO.class);
        complexTypes.add(UserDTO.class);
        complexTypes.add(UserResponseDTO.class);
        complexTypes.add(ReadOwnerRequest.class);
        complexTypes.add(ReadOwnerResponse.class);
        complexTypes.add(CreateOwnerRequest.class);
        complexTypes.add(CreateOwnerResponse.class);
        complexTypes.add(UpdateOwnerRequest.class);
        complexTypes.add(UpdateOwnerResponse.class);
        complexTypes.add(RestUpdateOwnerRequest.class);
        complexTypes.add(DeleteOwnerRequest.class);
        complexTypes.add(AllOwnersRequest.class);
        complexTypes.add(AllOwnersResponse.class);
        complexTypes.add(OwnersForAddressRequest.class);
        complexTypes.add(OwnersForAddressResponse.class);
        complexTypes.add(OwnersWithPetsResponse.class);
        complexTypes.add(OwnersPetsRequest.class);
        complexTypes.add(OwnersPetsResponse.class);
        complexTypes.add(MyPetsResponse.class);
        complexTypes.add(OwnerVetsResponse.class);
        complexTypes.add(ReadVetRequest.class);
        complexTypes.add(ReadVetResponse.class);
        complexTypes.add(CreateVetRequest.class);
        complexTypes.add(CreateVetResponse.class);
        complexTypes.add(RestCreateVetRequest.class);
        complexTypes.add(UpdateVetRequest.class);
        complexTypes.add(UpdateVetResponse.class);
        complexTypes.add(RestUpdateVetRequest.class);
        complexTypes.add(DeleteVetRequest.class);
        complexTypes.add(VetDTO.class);
        complexTypes.add(VetWithSpecialtiesDTO.class);
        complexTypes.add(VetInfoRequest.class);
        complexTypes.add(VetWithSpecialtiesDTO.class);
        complexTypes.add(ReadPetRequest.class);
        complexTypes.add(ReadPetResponse.class);
        complexTypes.add(CreatePetRequest.class);
        complexTypes.add(CreatePetResponse.class);
        complexTypes.add(UpdatePetRequest.class);
        complexTypes.add(UpdatePetResponse.class);
        complexTypes.add(RestUpdatePetRequest.class);
        complexTypes.add(DeletePetRequest.class);
        complexTypes.add(PetsRequest.class);
        complexTypes.add(PetsResponse.class);
        complexTypes.add(FindPetbyTypeRequest.class);
        complexTypes.add(FindPetbyTypeResponse.class);
        complexTypes.add(ReadVisitRequest.class);
        complexTypes.add(ReadVisitResponse.class);
        complexTypes.add(CreateVisitRequest.class);
        complexTypes.add(CreateVisitResponse.class);
        complexTypes.add(UpdateVisitRequest.class);
        complexTypes.add(UpdateVisitResponse.class);
        complexTypes.add(RestUpdateVisitRequest.class);
        complexTypes.add(DeleteVisitRequest.class);
        complexTypes.add(VetVisitsRequest.class);
        complexTypes.add(VetVisitsResponse.class);
        complexTypes.add(ScheduledVisitsResponse.class);
        complexTypes.add(MyVisitsRequest.class);
        complexTypes.add(MyVisitsResponse.class);
        complexTypes.add(FindFileRequest.class);
        entityTypes.add(User.class);
        entityTypes.add(Owner.class);
        entityTypes.add(Vet.class);
        entityTypes.add(VetSpeciality.class);
        entityTypes.add(VetSpecialities.class);
        entityTypes.add(Pet.class);
        entityTypes.add(Visit.class);
        entityTypes.add(DataVersion.class);
        entityTypes.add(DataProcessorLog.class);
        ignoredFields.putIfAbsent(User.class, new ArrayList<>());
        ignoredFields.get(User.class).add("lastHistory");
    }

    @BeforeEach
    public void before() {
        AppThreadLocals.initialize(Optional.of(UUID.randomUUID().toString()), Optional.empty());
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
        AppThreadLocals.initialize(Optional.of(UUID.randomUUID().toString()), Optional.empty());
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
        } else if (clazz == Owner.class) {
            return ownerRepository.findAll();
        } else if (clazz == Vet.class) {
            return vetRepository.findAll();
        } else if (clazz == VetSpeciality.class) {
            return vetSpecialityRepository.findAll();
        } else if (clazz == VetSpecialities.class) {
            return vetSpecialitiesRepository.findAll();
        } else if (clazz == Pet.class) {
            return petRepository.findAll();
        } else if (clazz == Visit.class) {
            return visitRepository.findAll();
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
        } else if (entityClass == Owner.class) {
            return ownerRepository.findById((OwnerId) id).orElse(null);
        } else if (entityClass == Vet.class) {
            return vetRepository.findById((VetId) id).orElse(null);
        } else if (entityClass == VetSpeciality.class) {
            return vetSpecialityRepository.findById((VetSpecialityId) id).orElse(null);
        } else if (entityClass == VetSpecialities.class) {
            return vetSpecialitiesRepository.findById((VetSpecialitiesId) id).orElse(null);
        } else if (entityClass == Pet.class) {
            return petRepository.findById((PetId) id).orElse(null);
        } else if (entityClass == Visit.class) {
            return visitRepository.findById((VisitId) id).orElse(null);
        } else if (entityClass == DataVersion.class) {
            return dataVersionRepository.findById((DataVersionId) id).orElse(null);
        } else if (entityClass == DataProcessorLog.class) {
            return dataProcessorLogRepository.findById((DataProcessorLogId) id).orElse(null);
        }

        throw new IllegalStateException("No findById for class: " + entityClass.getName());
    }
}
