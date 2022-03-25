package co.aleksa.backend.api;

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
import co.aleksa.backend.audit.AuditFacade;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VetApiTransactionCaller {
    private static final Logger LOG = LoggerFactory.getLogger(VetApiTransactionCaller.class);

    @Inject private AuditFacade auditFacade;

    @Inject private VetApi vetApi;

    @Transactional(readOnly = true)
    public ReadVetResponse readVet(ReadVetRequest dto) {
        LOG.trace("readVet {}", dto.getId());

        final ReadVetResponse result = vetApi.readVet(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public CreateVetResponse createVet(CreateVetRequest dto, FileUploadDTO imageUploadDto) throws IOException {
        LOG.trace("createVet {} {} {}", dto.getUserId(), dto.getUserId(), imageUploadDto);

        final CreateVetResponse result = vetApi.createVet(dto, imageUploadDto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public UpdateVetResponse updateVet(UpdateVetRequest dto, FileUploadDTO imageUploadDto) throws IOException {
        LOG.trace("updateVet {} {} {} {}", dto.getId(), dto.getUserId(), dto.getUserId(), imageUploadDto);

        final UpdateVetResponse result = vetApi.updateVet(dto, imageUploadDto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public void deleteVet(DeleteVetRequest dto) {
        LOG.trace("deleteVet {}", dto.getId());

        vetApi.deleteVet(dto);
        auditFacade.flushInTransaction();
    }

    @Transactional(readOnly = true)
    public List<VetWithSpecialtiesDTO> vetsWithSpecialties() {
        LOG.trace("vetsWithSpecialties");

        final List<VetWithSpecialtiesDTO> result = vetApi.vetsWithSpecialties();
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public List<VetWithSpecialtiesDTO> vetInfo(VetInfoRequest dto) {
        LOG.trace("vetInfo {}", dto.getId());

        final List<VetWithSpecialtiesDTO> result = vetApi.vetInfo(dto);
        auditFacade.flushInTransaction();
        return result;
    }
}
