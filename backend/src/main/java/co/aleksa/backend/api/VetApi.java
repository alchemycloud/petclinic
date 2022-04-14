package co.aleksa.backend.api;

import co.aleksa.backend.api.dto.fileapi.FileDTO;
import co.aleksa.backend.api.dto.fileapi.FileUploadDTO;
import co.aleksa.backend.api.dto.vetapi.CreateVetRequest;
import co.aleksa.backend.api.dto.vetapi.CreateVetResponse;
import co.aleksa.backend.api.dto.vetapi.DeleteVetRequest;
import co.aleksa.backend.api.dto.vetapi.ReadVetRequest;
import co.aleksa.backend.api.dto.vetapi.ReadVetResponse;
import co.aleksa.backend.api.dto.vetapi.UpdateVetRequest;
import co.aleksa.backend.api.dto.vetapi.UpdateVetResponse;
import co.aleksa.backend.api.dto.vetapi.VetInfoRequest;
import co.aleksa.backend.api.dto.vetapi.VetWithSpecialtiesDTO;
import co.aleksa.backend.model.File;
import co.aleksa.backend.model.User;
import co.aleksa.backend.model.Vet;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.model.id.VetId;
import co.aleksa.backend.repository.UserRepository;
import co.aleksa.backend.repository.VetRepository;
import co.aleksa.backend.repository.tuple.VetVetInfoTuple;
import co.aleksa.backend.repository.tuple.VetVetsWithSpecialtiesTuple;
import co.aleksa.backend.service.FileStorageService;
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
        LOG.trace("createVet {} {} {}", dto.getUserId(), dto.getUserId(), imageUploadDto);
        // TODO check security constraints(userId, userId)

        final User user = userRepository.getOne(dto.getUserId());
        final String imagePath = fileStorageService.generateFilePath(Optional.of(imageUploadDto));
        final File image = new File(imagePath);
        final User user = userRepository.getOne(dto.getUserId());
        final Vet model = vetRepository.create(null, null, null);
        fileStorageService.store(imageUploadDto, imagePath);

        final VetId responseId = model.getId();
        final UserId responseUserId = model.getUserId();
        final FileDTO responseImage = new FileDTO(FILE_LOCATION_URL + model.getImage().getPath());
        return new CreateVetResponse(responseId, responseUserId, responseImage);
    }

    public UpdateVetResponse updateVet(UpdateVetRequest dto, FileUploadDTO imageUploadDto) throws IOException {
        LOG.trace("updateVet {} {} {} {}", dto.getId(), dto.getUserId(), dto.getUserId(), imageUploadDto);
        // TODO check security constraints(id, userId, userId)

        final Vet model = vetRepository.getOne(dto.getId());
        model.setUser(userRepository.getOne(dto.getUserId()));
        final String imagePath = fileStorageService.updateFilePath(model.getImage().getPath(), imageUploadDto);
        final String oldImagePath = model.getImage().getPath();
        model.setImage(new File(imagePath));
        model.setUser(userRepository.getOne(dto.getUserId()));
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

    public List<VetWithSpecialtiesDTO> vetsWithSpecialties() {
        LOG.trace("vetsWithSpecialties");
        // TODO check security constraints

        final List<VetVetsWithSpecialtiesTuple> tuples = vetRepository.vetsWithSpecialties();
        return tuples.stream()
                .map(
                        tuple -> {
                            final String responseFirstName = null; // TODO set this field manually
                            final String responseLastName = null; // TODO set this field manually
                            final List<String> responseSpecialties = tuple.getVetSpecialty().getName();
                            return new VetWithSpecialtiesDTO(responseFirstName, responseLastName, responseSpecialties);
                        })
                .toList();
    }

    public List<VetWithSpecialtiesDTO> vetInfo(VetInfoRequest dto) {
        LOG.trace("vetInfo {}", dto.getId());
        // TODO check security constraints(id)

        final List<VetVetInfoTuple> tuples = vetRepository.vetInfo(dto.getId());
        return tuples.stream()
                .map(
                        tuple -> {
                            final String responseFirstName = null; // TODO set this field manually
                            final String responseLastName = null; // TODO set this field manually
                            final List<String> responseSpecialties = tuple.getVetSpecialty().getName();
                            return new VetWithSpecialtiesDTO(responseFirstName, responseLastName, responseSpecialties);
                        })
                .toList();
    }
}
