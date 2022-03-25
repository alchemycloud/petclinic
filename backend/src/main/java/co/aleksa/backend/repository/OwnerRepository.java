package co.aleksa.backend.repository;

import co.aleksa.backend.model.Owner;
import co.aleksa.backend.model.User;
import co.aleksa.backend.model.id.OwnerId;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.repository.tuple.OwnerFindForAddressTuple;
import co.aleksa.backend.repository.tuple.OwnerForAddressTuple;
import co.aleksa.backend.repository.tuple.OwnerMyPetsTuple;
import co.aleksa.backend.repository.tuple.OwnerOwnerVetsTuple;
import co.aleksa.backend.repository.tuple.OwnerOwnersPetsTuple;
import co.aleksa.backend.repository.tuple.OwnerOwnersWithPetsTuple;
import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends SimpleRepository<Owner, OwnerId> {

    Owner create(User user, Optional<String> address, Optional<String> city, Optional<String> telephone);

    List<Owner> findByAddress(Optional<String> address);

    List<Owner> findByAddressMandatory(String address);

    List<Owner> findByCity(Optional<String> city);

    List<Owner> findByCityMandatory(String city);

    List<Owner> findByTelephone(Optional<String> telephone);

    List<Owner> findByTelephoneMandatory(String telephone);

    Optional<Owner> findByUserId(UserId userId);

    List<Owner> allOwners();

    List<Owner> findAllOwners(Integer drop, Integer take);

    Long countAllOwners();

    List<OwnerForAddressTuple> forAddress(Optional<String> address);

    List<OwnerFindForAddressTuple> findForAddress(Optional<String> address, Integer drop, Integer take);

    Long countForAddress(Optional<String> address);

    List<OwnerOwnersWithPetsTuple> ownersWithPets();

    List<OwnerOwnersPetsTuple> ownersPets(OwnerId ownerId);

    List<OwnerMyPetsTuple> myPets(UserId principalId);

    List<OwnerOwnerVetsTuple> ownerVets();
}
