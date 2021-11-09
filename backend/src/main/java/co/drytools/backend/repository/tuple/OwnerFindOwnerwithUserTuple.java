package co.drytools.backend.repository.tuple;

import co.drytools.backend.model.Owner;
import co.drytools.backend.model.User;

public class OwnerFindOwnerwithUserTuple {

    private final Owner owner;
    private final User user;

    public OwnerFindOwnerwithUserTuple(Owner owner, User user) {
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
