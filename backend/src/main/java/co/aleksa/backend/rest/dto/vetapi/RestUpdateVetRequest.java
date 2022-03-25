package co.aleksa.backend.rest.dto.vetapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.aleksa.backend.api.dto.fileapi.FileUploadDTO;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.model.id.VetId;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class RestUpdateVetRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<VetId> ID = new PropertyPath<>("id");
    public static final PropertyPath<UserId> USER_ID = new PropertyPath<>("userId");
    public static final PropertyPath<FileUploadDTO> IMAGE = new PropertyPath<>("image");
    public static final PropertyPath<UserId> USER_ID = new PropertyPath<>("userId");

    @NotNull private VetId id;

    @NotNull private UserId userId;

    @NotNull @Valid private FileUploadDTO image;

    @NotNull private UserId userId;

    private RestUpdateVetRequest() {}

    public RestUpdateVetRequest(VetId id, UserId userId, FileUploadDTO image, UserId userId) {
        this();
        this.id = id;
        this.userId = userId;
        this.image = image;
        this.userId = userId;
    }

    public final VetId getId() {
        return id;
    }

    public final RestUpdateVetRequest setId(VetId id) {
        this.id = id;
        return this;
    }

    public final UserId getUserId() {
        return userId;
    }

    public final RestUpdateVetRequest setUserId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public final FileUploadDTO getImage() {
        return image;
    }

    public final RestUpdateVetRequest setImage(FileUploadDTO image) {
        this.image = image;
        return this;
    }

    public final UserId getUserId() {
        return userId;
    }

    public final RestUpdateVetRequest setUserId(UserId userId) {
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
        if (RestUpdateVetRequest.class != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = PRIME * result + ((this.userId == null) ? 0 : this.userId.hashCode());
        result = PRIME * result + ((this.image == null) ? 0 : this.image.hashCode());
        result = PRIME * result + ((this.userId == null) ? 0 : this.userId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "RestUpdateVetRequest["
                + "this.id="
                + this.id
                + ", this.userId="
                + this.userId
                + ", this.image="
                + this.image
                + ", this.userId="
                + this.userId
                + "]";
    }
}
