package co.aleksa.backend.api.dto.vetapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.api.dto.fileapi.FileDTO;
import co.aleksa.backend.model.id.UserId;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class CreateVetRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<UserId> USER_ID = new PropertyPath<>("userId");
    public static final PropertyPath<FileDTO> IMAGE = new PropertyPath<>("image");
    public static final PropertyPath<UserId> USER_ID = new PropertyPath<>("userId");

    @NotNull private UserId userId;

    @NotNull @Valid private FileDTO image;

    @NotNull private UserId userId;

    private CreateVetRequest() {}

    public CreateVetRequest(UserId userId, FileDTO image, UserId userId) {
        this();
        this.userId = userId;
        this.image = image;
        this.userId = userId;
    }

    public final UserId getUserId() {
        return userId;
    }

    public final CreateVetRequest setUserId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public final FileDTO getImage() {
        return image;
    }

    public final CreateVetRequest setImage(FileDTO image) {
        this.image = image;
        return this;
    }

    public final UserId getUserId() {
        return userId;
    }

    public final CreateVetRequest setUserId(UserId userId) {
        this.userId = userId;
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
        if (CreateVetRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.userId == null) ? 0 : this.userId.hashCode());
        result = PRIME * result + ((this.image == null) ? 0 : this.image.hashCode());
        result = PRIME * result + ((this.userId == null) ? 0 : this.userId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "CreateVetRequest[" + "this.userId=" + this.userId + ", this.image=" + this.image + ", this.userId=" + this.userId + "]";
    }
}
