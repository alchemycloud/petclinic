package co.aleksa.backend.api.dto.visitapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.model.enumeration.PetType;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class VisitDTO implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<Integer> VISIT_NUMBER = new PropertyPath<>("visitNumber");
    public static final PropertyPath<String> DESCRIPTION = new PropertyPath<>("description");
    public static final PropertyPath<Boolean> SCHEDULED = new PropertyPath<>("scheduled");
    public static final PropertyPath<String> NAME = new PropertyPath<>("name");
    public static final PropertyPath<PetType> PET_TYPE = new PropertyPath<>("petType");
    public static final PropertyPath<String> FIRST_NAME = new PropertyPath<>("firstName");
    public static final PropertyPath<String> LAST_NAME = new PropertyPath<>("lastName");

    @NotNull private Integer visitNumber;

    @Size(max = 1024)
    private String description;

    @NotNull private Boolean scheduled;

    @NotNull
    @Size(min = 2, max = 40)
    private String name;

    @NotNull private PetType petType;

    @NotNull
    @Size(min = 1, max = 40)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 60)
    private String lastName;

    private VisitDTO() {}

    public VisitDTO(Integer visitNumber, String description, Boolean scheduled, String name, PetType petType, String firstName, String lastName) {
        this();
        this.visitNumber = visitNumber;
        this.description = description;
        this.scheduled = scheduled;
        this.name = name;
        this.petType = petType;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public final Integer getVisitNumber() {
        return visitNumber;
    }

    public final VisitDTO setVisitNumber(Integer visitNumber) {
        this.visitNumber = visitNumber;
        return this;
    }

    public final String getDescription() {
        return description;
    }

    public final VisitDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public final Boolean getScheduled() {
        return scheduled;
    }

    public final VisitDTO setScheduled(Boolean scheduled) {
        this.scheduled = scheduled;
        return this;
    }

    public final String getName() {
        return name;
    }

    public final VisitDTO setName(String name) {
        this.name = name;
        return this;
    }

    public final PetType getPetType() {
        return petType;
    }

    public final VisitDTO setPetType(PetType petType) {
        this.petType = petType;
        return this;
    }

    public final String getFirstName() {
        return firstName;
    }

    public final VisitDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public final String getLastName() {
        return lastName;
    }

    public final VisitDTO setLastName(String lastName) {
        this.lastName = lastName;
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
        if (VisitDTO.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.visitNumber == null) ? 0 : this.visitNumber.hashCode());
        result = PRIME * result + ((this.description == null) ? 0 : this.description.hashCode());
        result = PRIME * result + ((this.scheduled == null) ? 0 : this.scheduled.hashCode());
        result = PRIME * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = PRIME * result + ((this.petType == null) ? 0 : this.petType.hashCode());
        result = PRIME * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
        result = PRIME * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "VisitDTO["
                + "this.visitNumber="
                + this.visitNumber
                + ", this.description="
                + this.description
                + ", this.scheduled="
                + this.scheduled
                + ", this.name="
                + this.name
                + ", this.petType="
                + this.petType
                + ", this.firstName="
                + this.firstName
                + ", this.lastName="
                + this.lastName
                + "]";
    }
}
