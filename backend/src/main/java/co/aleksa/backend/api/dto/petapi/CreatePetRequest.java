package co.aleksa.backend.api.dto.petapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.id.OwnerId;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreatePetRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<OwnerId> OWNER_ID = new PropertyPath<>("ownerId");
    public static final PropertyPath<String> NAME = new PropertyPath<>("name");
    public static final PropertyPath<ZonedDateTime> BIRTHDAY = new PropertyPath<>("birthday");
    public static final PropertyPath<PetType> PET_TYPE = new PropertyPath<>("petType");
    public static final PropertyPath<Boolean> VACCINATED = new PropertyPath<>("vaccinated");

    @NotNull private OwnerId ownerId;

    @NotNull
    @Size(min = 2, max = 40)
    private String name;

    @NotNull private ZonedDateTime birthday;

    @NotNull private PetType petType;

    @NotNull private Boolean vaccinated;

    private CreatePetRequest() {}

    public CreatePetRequest(OwnerId ownerId, String name, ZonedDateTime birthday, PetType petType, Boolean vaccinated) {
        this();
        this.ownerId = ownerId;
        this.name = name;
        this.birthday = birthday;
        this.petType = petType;
        this.vaccinated = vaccinated;
    }

    public final OwnerId getOwnerId() {
        return ownerId;
    }

    public final CreatePetRequest setOwnerId(OwnerId ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public final String getName() {
        return name;
    }

    public final CreatePetRequest setName(String name) {
        this.name = name;
        return this;
    }

    public final ZonedDateTime getBirthday() {
        return birthday;
    }

    public final CreatePetRequest setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public final PetType getPetType() {
        return petType;
    }

    public final CreatePetRequest setPetType(PetType petType) {
        this.petType = petType;
        return this;
    }

    public final Boolean getVaccinated() {
        return vaccinated;
    }

    public final CreatePetRequest setVaccinated(Boolean vaccinated) {
        this.vaccinated = vaccinated;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (CreatePetRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.ownerId == null) ? 0 : this.ownerId.hashCode());
        result = PRIME * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = PRIME * result + ((this.birthday == null) ? 0 : this.birthday.hashCode());
        result = PRIME * result + ((this.petType == null) ? 0 : this.petType.hashCode());
        result = PRIME * result + ((this.vaccinated == null) ? 0 : this.vaccinated.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "CreatePetRequest["
                + "this.ownerId="
                + this.ownerId
                + ", this.name="
                + this.name
                + ", this.birthday="
                + this.birthday
                + ", this.petType="
                + this.petType
                + ", this.vaccinated="
                + this.vaccinated
                + "]";
    }
}
