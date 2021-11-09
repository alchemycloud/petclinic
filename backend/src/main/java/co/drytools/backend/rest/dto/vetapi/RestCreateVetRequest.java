package co.drytools.backend.rest.dto.vetapi;

import cloud.alchemy.fabut.property.PropertyPath;
import co.drytools.backend.api.dto.fileapi.FileUploadDTO;
import co.drytools.backend.model.id.UserId;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class RestCreateVetRequest implements Serializable {

    private static final long serialVersionUID = 1;

    public static final PropertyPath<UserId> USER_ID = new PropertyPath<>("userId");
    public static final PropertyPath<FileUploadDTO> IMAGE = new PropertyPath<>("image");

    @NotNull private UserId userId;

    @NotNull @Valid private FileUploadDTO image;

    private RestCreateVetRequest() {}

    public RestCreateVetRequest(UserId userId, FileUploadDTO image) {
        this();
        this.userId = userId;
        this.image = image;
    }

    public final UserId getUserId() {
        return userId;
    }

    public final RestCreateVetRequest setUserId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public final FileUploadDTO getImage() {
        return image;
    }

    public final RestCreateVetRequest setImage(FileUploadDTO image) {
        this.image = image;
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
        if (RestCreateVetRequest.class != obj.getClass()) {
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
        return result;
    }

    @Override
    public String toString() {
        return "RestCreateVetRequest[" + "this.userId=" + this.userId + ", this.image=" + this.image + "]";
    }
}
