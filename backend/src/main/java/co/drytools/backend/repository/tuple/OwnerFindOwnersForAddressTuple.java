package co.drytools.backend.repository.tuple;

import co.drytools.backend.model.Owner;
import co.drytools.backend.model.User;

public class OwnerFindOwnersForAddressTuple {

    private final Owner owner;
    private final User user;

    public OwnerFindOwnersForAddressTuple(Owner owner, User user) {
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
