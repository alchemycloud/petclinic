package co.aleksa.backend.repository;

import co.aleksa.backend.model.File;
import co.aleksa.backend.model.User;
import co.aleksa.backend.model.Vet;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.model.id.VetId;
import co.aleksa.backend.repository.tuple.VetVetInfoTuple;
import co.aleksa.backend.repository.tuple.VetVetsWithSpecialtiesTuple;
import java.util.List;
import java.util.Optional;

public interface VetRepository extends SimpleRepository<Vet, VetId> {

    Vet create(User user, File image, User user);

    List<Vet> findByImage(String imagePath);

    Optional<Vet> findByUserIdAndUserId(UserId userId);

    List<VetVetsWithSpecialtiesTuple> vetsWithSpecialties();

    List<VetVetInfoTuple> vetInfo(VetId id);
}
