package co.drytools.backend.api;

import co.drytools.backend.api.dto.fileapi.FileDTO;
import co.drytools.backend.api.dto.fileapi.FileUploadDTO;
import co.drytools.backend.api.dto.vetapi.CreateVetRequest;
import co.drytools.backend.api.dto.vetapi.CreateVetResponse;
import co.drytools.backend.api.dto.vetapi.DeleteVetRequest;
import co.drytools.backend.api.dto.vetapi.ReadVetRequest;
import co.drytools.backend.api.dto.vetapi.ReadVetResponse;
import co.drytools.backend.api.dto.vetapi.UpdateVetRequest;
import co.drytools.backend.api.dto.vetapi.UpdateVetResponse;
import co.drytools.backend.api.dto.vetapi.VetDTO;
import co.drytools.backend.api.dto.vetapi.VetInfoRequest;
import co.drytools.backend.api.dto.vetapi.VetWithSpecialtiesDTO;
import co.drytools.backend.model.File;
import co.drytools.backend.model.User;
import co.drytools.backend.model.Vet;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.model.id.VetId;
import co.drytools.backend.repository.UserRepository;
import co.drytools.backend.repository.VetRepository;
import co.drytools.backend.service.FileStorageService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VetApi {
    private static final Logger LOG = LoggerFactory.getLogger(VetApi.class);

    @Inject private VetRepository vetRepository;

    private static final String FILE_LOCATION_URL = "/api/file/";

    @Inject private UserRepository userRepository;

    @Inject private FileStorageService fileStorageService;

    public ReadVetResponse readVet(ReadVetRequest dto) {
        LOG.trace("readVet {}", dto.getId());
        // TODO check security constraints(id)

        final Vet model = vetRepository.getOne(dto.getId());
        final VetId responseId = model.getId();
        final UserId responseUserId = model.getUserId();
        final FileDTO responseImage = new FileDTO(FILE_LOCATION_URL + model.getImage().getPath());
        return new ReadVetResponse(responseId, responseUserId, responseImage);
    }

    public CreateVetResponse createVet(CreateVetRequest dto, FileUploadDTO imageUploadDto) throws IOException {
        LOG.trace("createVet {} {}", dto.getUserId(), imageUploadDto);
        // TODO check security constraints(userId)

        final User user = userRepository.getOne(dto.getUserId());
        final String imagePath = fileStorageService.generateFilePath(Optional.of(imageUploadDto));
        final File image = new File(imagePath);
        final Vet model = vetRepository.create(null, null);
        fileStorageService.store(imageUploadDto, imagePath);

        final VetId responseId = model.getId();
        final UserId responseUserId = model.getUserId();
        final FileDTO responseImage = new FileDTO(FILE_LOCATION_URL + model.getImage().getPath());
        return new CreateVetResponse(responseId, responseUserId, responseImage);
    }

    public UpdateVetResponse updateVet(UpdateVetRequest dto, FileUploadDTO imageUploadDto) throws IOException {
        LOG.trace("updateVet {} {} {}", dto.getId(), dto.getUserId(), imageUploadDto);
        // TODO check security constraints(id, userId)

        final Vet model = vetRepository.getOne(dto.getId());
        model.setUser(userRepository.getOne(dto.getUserId()));
        final String imagePath = fileStorageService.updateFilePath(model.getImage().getPath(), imageUploadDto);
        final String oldImagePath = model.getImage().getPath();
        model.setImage(new File(imagePath));
        vetRepository.update(model);
        fileStorageService.update(Optional.of(oldImagePath), Optional.of(imageUploadDto), imagePath);

        final VetId responseId = model.getId();
        final UserId responseUserId = model.getUserId();
        final FileDTO responseImage = new FileDTO(FILE_LOCATION_URL + model.getImage().getPath());
        return new UpdateVetResponse(responseId, responseUserId, responseImage);
    }

    public void deleteVet(DeleteVetRequest dto) {
        LOG.trace("deleteVet {}", dto.getId());
        // TODO check security constraints(id)

        final Vet model = vetRepository.getOne(dto.getId());
        fileStorageService.delete(model.getImage().getPath());

        vetRepository.deleteById(dto.getId());
    }

    public List<VetWithSpecialtiesDTO> vetsWithSpecialties(VetDTO dto) {
        LOG.trace("vetsWithSpecialties {}", dto.getId());
        // TODO check security constraints(id)

        throw new UnsupportedOperationException();
    }

    public VetWithSpecialtiesDTO vetInfo(VetInfoRequest dto) {
        LOG.trace("vetInfo {}", dto.getId());
        // TODO check security constraints(id)

        throw new UnsupportedOperationException();
    }
}
