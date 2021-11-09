package co.drytools.backend.repository;

import co.drytools.backend.model.File;
import co.drytools.backend.model.User;
import co.drytools.backend.model.Vet;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.model.id.VetId;
import java.util.List;
import java.util.Optional;

public interface VetRepository extends SimpleRepository<Vet, VetId> {

    Vet create(User user, File image);

    List<Vet> findByImage(String imagePath);

    Optional<Vet> findByUserId(UserId userId);
}
