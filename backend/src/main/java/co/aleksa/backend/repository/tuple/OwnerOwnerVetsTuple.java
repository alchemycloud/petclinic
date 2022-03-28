package co.aleksa.backend.repository.tuple;

import co.aleksa.backend.model.Owner;
import co.aleksa.backend.model.User;

public class OwnerOwnerVetsTuple {

    private final Owner owner;
    private final User user;

    public OwnerOwnerVetsTuple(Owner owner, User user) {
        this.owner = owner;
        this.user = user;
    }

    public Owner getOwner() {
        return owner;
    }

    public User getUser() {
        return user;
    }
}
