package co.aleksa.backend;

import cloud.alchemy.fabut.Fabut;
import co.aleksa.backend.api.dto.fileapi.FindFileRequest;
import co.aleksa.backend.api.dto.ownerapi.AllOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.AllOwnersResponse;
import co.aleksa.backend.api.dto.ownerapi.CreateOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.CreateOwnersResponse;
import co.aleksa.backend.api.dto.ownerapi.DeleteOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.EnrichedOwnerDTO;
import co.aleksa.backend.api.dto.ownerapi.ForAddressRequest;
import co.aleksa.backend.api.dto.ownerapi.MyPetsResponse;
import co.aleksa.backend.api.dto.ownerapi.OwnerVetsResponse;
import co.aleksa.backend.api.dto.ownerapi.OwnersPetsRequest;
import co.aleksa.backend.api.dto.ownerapi.OwnersPetsResponse;
import co.aleksa.backend.api.dto.ownerapi.ReadOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.ReadOwnersResponse;
import co.aleksa.backend.api.dto.ownerapi.UpdateOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.UpdateOwnersResponse;
import co.aleksa.backend.api.dto.petapi.CreatePetRequest;
import co.aleksa.backend.api.dto.petapi.CreatePetResponse;
import co.aleksa.backend.api.dto.petapi.DeletePetRequest;
import co.aleksa.backend.api.dto.petapi.PetsByTypeRequest;
import co.aleksa.backend.api.dto.petapi.PetsByTypeResponse;
import co.aleksa.backend.api.dto.petapi.PetsRequest;
import co.aleksa.backend.api.dto.petapi.PetsResponse;
import co.aleksa.backend.api.dto.petapi.ReadPetRequest;
import co.aleksa.backend.api.dto.petapi.ReadPetResponse;
import co.aleksa.backend.api.dto.petapi.UpdatePetRequest;
import co.aleksa.backend.api.dto.petapi.UpdatePetResponse;
import co.aleksa.backend.api.dto.vetapi.CreateVetRequest;
import co.aleksa.backend.api.dto.vetapi.CreateVetResponse;
import co.aleksa.backend.api.dto.vetapi.DeleteVetRequest;
import co.aleksa.backend.api.dto.vetapi.ReadVetRequest;
import co.aleksa.backend.api.dto.vetapi.ReadVetResponse;
import co.aleksa.backend.api.dto.vetapi.UpdateVetRequest;
import co.aleksa.backend.api.dto.vetapi.UpdateVetResponse;
import co.aleksa.backend.api.dto.vetapi.VetInfoRequest;
import co.aleksa.backend.api.dto.vetapi.VetWithSpecialtiesDTO;
import co.aleksa.backend.api.dto.visitapi.CreateVisitRequest;
import co.aleksa.backend.api.dto.visitapi.CreateVisitResponse;
import co.aleksa.backend.api.dto.visitapi.DeleteVisitRequest;
import co.aleksa.backend.api.dto.visitapi.ReadVisitRequest;
import co.aleksa.backend.api.dto.visitapi.ReadVisitResponse;
import co.aleksa.backend.api.dto.visitapi.ScheduledVisitsRequest;
import co.aleksa.backend.api.dto.visitapi.UpdateVisitRequest;
import co.aleksa.backend.api.dto.visitapi.UpdateVisitResponse;
import co.aleksa.backend.api.dto.visitapi.VisitDTO;
import co.aleksa.backend.api.dto.visitapi.VisitsByPetRequest;
import co.aleksa.backend.api.dto.visitapi.VisitsByVetRequest;
import co.aleksa.backend.api.dto.visitapi.VisitsForOwnerRequest;
import co.aleksa.backend.audit.AuditFacade;
import co.aleksa.backend.model.DataProcessorLog;
import co.aleksa.backend.model.DataVersion;
import co.aleksa.backend.model.Owner;
import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.Tenant;
import co.aleksa.backend.model.User;
import co.aleksa.backend.model.Vet;
import co.aleksa.backend.model.VetSpecialty;
import co.aleksa.backend.model.Visit;
import co.aleksa.backend.model.id.AbstractId;
import co.aleksa.backend.model.id.DataProcessorLogId;
import co.aleksa.backend.model.id.DataVersionId;
import co.aleksa.backend.model.id.OwnerId;
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.model.id.TenantId;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.model.id.VetId;
import co.aleksa.backend.model.id.VetSpecialtyId;
import co.aleksa.backend.model.id.VisitId;
import co.aleksa.backend.repository.DataProcessorLogRepository;
import co.aleksa.backend.repository.DataVersionRepository;
import co.aleksa.backend.repository.OwnerRepository;
import co.aleksa.backend.repository.PetRepository;
import co.aleksa.backend.repository.TenantRepository;
import co.aleksa.backend.repository.UserRepository;
import co.aleksa.backend.repository.VetRepository;
import co.aleksa.backend.repository.VetSpecialtyRepository;
import co.aleksa.backend.repository.VisitRepository;
import co.aleksa.backend.rest.dto.vetapi.RestCreateVetRequest;
import co.aleksa.backend.rest.dto.vetapi.RestUpdateVetRequest;
import co.aleksa.backend.util.AppThreadLocals;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles(resolver = TestProfileResolver.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BackendApplication.class)
@Transactional
public abstract class AbstractDatabaseTest extends Fabut {
    @Inject private AuditFacade auditFacade;
    @Inject protected UserRepository userRepository;

    @Inject protected VetRepository vetRepository;

    @Inject protected OwnerRepository ownerRepository;

    @Inject protected PetRepository petRepository;

    @Inject protected VetSpecialtyRepository vetSpecialtyRepository;

    @Inject protected VisitRepository visitRepository;

    @Inject protected TenantRepository tenantRepository;

    @Inject protected DataVersionRepository dataVersionRepository;

    @Inject protected DataProcessorLogRepository dataProcessorLogRepository;

    public AbstractDatabaseTest() {
        complexTypes.add(ReadOwnersRequest.class);
        complexTypes.add(ReadOwnersResponse.class);
        complexTypes.add(CreateOwnersRequest.class);
        complexTypes.add(CreateOwnersResponse.class);
        complexTypes.add(UpdateOwnersRequest.class);
        complexTypes.add(UpdateOwnersResponse.class);
        complexTypes.add(DeleteOwnersRequest.class);
        complexTypes.add(AllOwnersRequest.class);
        complexTypes.add(AllOwnersResponse.class);
        complexTypes.add(ForAddressRequest.class);
        complexTypes.add(EnrichedOwnerDTO.class);
        complexTypes.add(EnrichedOwnerDTO.class);
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
        complexTypes.add(VetWithSpecialtiesDTO.class);
        complexTypes.add(VetInfoRequest.class);
        complexTypes.add(VetWithSpecialtiesDTO.class);
        complexTypes.add(ReadPetRequest.class);
        complexTypes.add(ReadPetResponse.class);
        complexTypes.add(CreatePetRequest.class);
        complexTypes.add(CreatePetResponse.class);
        complexTypes.add(UpdatePetRequest.class);
        complexTypes.add(UpdatePetResponse.class);
        complexTypes.add(DeletePetRequest.class);
        complexTypes.add(PetsRequest.class);
        complexTypes.add(PetsResponse.class);
        complexTypes.add(PetsByTypeRequest.class);
        complexTypes.add(PetsByTypeResponse.class);
        complexTypes.add(ReadVisitRequest.class);
        complexTypes.add(ReadVisitResponse.class);
        complexTypes.add(CreateVisitRequest.class);
        complexTypes.add(CreateVisitResponse.class);
        complexTypes.add(UpdateVisitRequest.class);
        complexTypes.add(UpdateVisitResponse.class);
        complexTypes.add(DeleteVisitRequest.class);
        complexTypes.add(VisitsByVetRequest.class);
        complexTypes.add(VisitDTO.class);
        complexTypes.add(VisitsByPetRequest.class);
        complexTypes.add(VisitDTO.class);
        complexTypes.add(ScheduledVisitsRequest.class);
        complexTypes.add(VisitDTO.class);
        complexTypes.add(VisitsForOwnerRequest.class);
        complexTypes.add(VisitDTO.class);
        complexTypes.add(FindFileRequest.class);
        entityTypes.add(User.class);
        entityTypes.add(Vet.class);
        entityTypes.add(Owner.class);
        entityTypes.add(Pet.class);
        entityTypes.add(VetSpecialty.class);
        entityTypes.add(Visit.class);
        entityTypes.add(Tenant.class);
        entityTypes.add(DataVersion.class);
        entityTypes.add(DataProcessorLog.class);
        ignoredFields.putIfAbsent(User.class, new ArrayList<>());
        ignoredFields.get(User.class).add("lastHistory");
        ignoredFields.putIfAbsent(Pet.class, new ArrayList<>());
        ignoredFields.get(Pet.class).add("lastHistory");
    }

    @BeforeEach
    public void before() {
        AppThreadLocals.initialize(Optional.of(UUID.randomUUID().toString()), Optional.of(Constants.DEFAULT_TEST_TENANT), Optional.empty());
        AppThreadLocals.setCountQueries(false);
        AppThreadLocals.setQueryNumberForTest(0);

        super.before();
    }

    @AfterEach
    public void after() {
        final StringBuilder messageBuilder = new StringBuilder();
        AppThreadLocals.initialize(Optional.of(UUID.randomUUID().toString()), Optional.of(Constants.DEFAULT_TEST_TENANT), Optional.empty());
        AppThreadLocals.clearCountQueries();
        final Integer actualQueryCount = AppThreadLocals.getQueryCount();
        final Integer queryNumberForTest = AppThreadLocals.getQueryNumberForTest();
        AppThreadLocals.clearQueryNumberForTest();
        AppThreadLocals.clearQueryCount();

        if (!queryNumberForTest.equals(actualQueryCount)) {
            messageBuilder
                    .append("Number of queries for test method is not correct. Expected: ")
                    .append(queryNumberForTest)
                    .append(", but was: ")
                    .append(actualQueryCount);
        }
        super.after();
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
        } else if (clazz == Vet.class) {
            return vetRepository.findAll();
        } else if (clazz == Owner.class) {
            return ownerRepository.findAll();
        } else if (clazz == Pet.class) {
            return petRepository.findAll();
        } else if (clazz == VetSpecialty.class) {
            return vetSpecialtyRepository.findAll();
        } else if (clazz == Visit.class) {
            return visitRepository.findAll();
        } else if (clazz == Tenant.class) {
            return tenantRepository.findAll();
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
        } else if (entityClass == Vet.class) {
            return vetRepository.findById((VetId) id).orElse(null);
        } else if (entityClass == Owner.class) {
            return ownerRepository.findById((OwnerId) id).orElse(null);
        } else if (entityClass == Pet.class) {
            return petRepository.findById((PetId) id).orElse(null);
        } else if (entityClass == VetSpecialty.class) {
            return vetSpecialtyRepository.findById((VetSpecialtyId) id).orElse(null);
        } else if (entityClass == Visit.class) {
            return visitRepository.findById((VisitId) id).orElse(null);
        } else if (entityClass == Tenant.class) {
            return tenantRepository.findById((TenantId) id).orElse(null);
        } else if (entityClass == DataVersion.class) {
            return dataVersionRepository.findById((DataVersionId) id).orElse(null);
        } else if (entityClass == DataProcessorLog.class) {
            return dataProcessorLogRepository.findById((DataProcessorLogId) id).orElse(null);
        }

        throw new IllegalStateException("No findById for class: " + entityClass.getName());
    }
}
