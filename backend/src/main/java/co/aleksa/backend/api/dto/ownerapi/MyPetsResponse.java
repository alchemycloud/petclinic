package co.aleksa.backend.api.dto.ownerapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.id.PetId;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MyPetsResponse implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<PetId> ID = new PropertyPath<>("id");
    public static final PropertyPath<String> NAME = new PropertyPath<>("name");
    public static final PropertyPath<ZonedDateTime> BIRTHDAY = new PropertyPath<>("birthday");
    public static final PropertyPath<PetType> PET_TYPE = new PropertyPath<>("petType");
    public static final PropertyPath<Boolean> VACCINATED = new PropertyPath<>("vaccinated");

    @NotNull private PetId id;

    @NotNull
    @Size(min = 2, max = 40)
    private String name;

    @NotNull private ZonedDateTime birthday;

    @NotNull private PetType petType;

    @NotNull private Boolean vaccinated;

    private MyPetsResponse() {}

    public MyPetsResponse(PetId id, String name, ZonedDateTime birthday, PetType petType, Boolean vaccinated) {
        this();
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.petType = petType;
        this.vaccinated = vaccinated;
    }

    public final PetId getId() {
        return id;
    }

    public final MyPetsResponse setId(PetId id) {
        this.id = id;
        return this;
    }

    public final String getName() {
        return name;
    }

    public final MyPetsResponse setName(String name) {
        this.name = name;
        return this;
    }

    public final ZonedDateTime getBirthday() {
        return birthday;
    }

    public final MyPetsResponse setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public final PetType getPetType() {
        return petType;
    }

    public final MyPetsResponse setPetType(PetType petType) {
        this.petType = petType;
        return this;
    }

    public final Boolean getVaccinated() {
        return vaccinated;
    }

    public final MyPetsResponse setVaccinated(Boolean vaccinated) {
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
        if (MyPetsResponse.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = PRIME * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = PRIME * result + ((this.birthday == null) ? 0 : this.birthday.hashCode());
        result = PRIME * result + ((this.petType == null) ? 0 : this.petType.hashCode());
        result = PRIME * result + ((this.vaccinated == null) ? 0 : this.vaccinated.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MyPetsResponse["
                + "this.id="
                + this.id
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
