package co.drytools.backend.repository;

import co.drytools.backend.model.Owner;
import co.drytools.backend.model.User;
import co.drytools.backend.model.enumeration.OwnerOrderableOwnersSortField;
import co.drytools.backend.model.id.OwnerId;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.repository.tuple.OwnerDeactivatedOwnerWithPetsTuple;
import co.drytools.backend.repository.tuple.OwnerFindOwnerVetsTuple;
import co.drytools.backend.repository.tuple.OwnerFindOwnersForAddressTuple;
import co.drytools.backend.repository.tuple.OwnerFindOwnerwithUserTuple;
import co.drytools.backend.repository.tuple.OwnerMyPetsTuple;
import co.drytools.backend.repository.tuple.OwnerOwnersForAddressTuple;
import co.drytools.backend.repository.tuple.OwnerOwnersPetsTuple;
import co.drytools.backend.repository.tuple.OwnerOwnersWithPetsTuple;
import co.drytools.backend.repository.util.OrderableField;
import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends SimpleRepository<Owner, OwnerId> {

    Owner create(User user, Optional<String> address, Optional<String> city, Optional<String> telephone);

    List<Owner> allOwnersPaged(Integer param);

    Optional<OwnerFindOwnerwithUserTuple> findOwnerwithUser(OwnerId id);

    List<Owner> mandatoryAddress(String address);

    List<Owner> orderableOwners(List<OrderableField<OwnerOrderableOwnersSortField>> orderableFields);

    List<OwnerDeactivatedOwnerWithPetsTuple> deactivatedOwnerWithPets();

    List<OwnerFindOwnerVetsTuple> findOwnerVets();

    List<Owner> findByAddress(Optional<String> address);

    List<Owner> findByAddressMandatory(String address);

    List<Owner> findByCity(Optional<String> city);

    List<Owner> findByCityMandatory(String city);

    List<Owner> findByTelephone(Optional<String> telephone);

    List<Owner> findByTelephoneMandatory(String telephone);

    Optional<Owner> findByUserId(UserId userId);

    List<OwnerOwnersForAddressTuple> ownersForAddress(Optional<String> address);

    List<OwnerFindOwnersForAddressTuple> findOwnersForAddress(Optional<String> address, Integer drop, Integer take);

    Long countOwnersForAddress(Optional<String> address);

    List<OwnerOwnersWithPetsTuple> ownersWithPets();

    List<OwnerOwnersPetsTuple> ownersPets(OwnerId ownerId);

    List<OwnerMyPetsTuple> myPets(UserId principalId);
}
