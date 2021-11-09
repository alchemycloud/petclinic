package co.drytools.backend.repository.tuple;

import co.drytools.backend.model.User;
import co.drytools.backend.model.Vet;

public class UserActiveUsersVetsTuple {

    private final User user;
    private final Vet vet;

    public UserActiveUsersVetsTuple(User user, Vet vet) {
        this.user = user;
        this.vet = vet;
    }

    public User getUser() {
        return user;
    }

    public Vet getVet() {
        return vet;
    }
}
