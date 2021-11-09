package co.drytools.backend.model.enumeration;

public enum OwnerOrderableOwnersSortField {
    OWNER_CITY("owner.city"),
    OWNER_TELEPHONE("owner.telephone");

    private final String name;

    OwnerOrderableOwnersSortField(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
