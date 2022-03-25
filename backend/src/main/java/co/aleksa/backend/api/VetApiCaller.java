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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class VetApiCaller {
    private static final Logger LOG = LoggerFactory.getLogger(VetApiCaller.class);

    @Lazy @Inject private AuditFacade auditFacade;

    @Inject private VetApiTransactionCaller vetApiTransactionCaller;

    public ReadVetResponse readVet(ReadVetRequest dto) {
        LOG.trace("readVet {}", dto.getId());

        final ReadVetResponse result = vetApiTransactionCaller.readVet(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public CreateVetResponse createVet(CreateVetRequest dto, FileUploadDTO imageUploadDto) throws IOException {
        LOG.trace("createVet {} {} {}", dto.getUserId(), dto.getUserId(), imageUploadDto);

        final CreateVetResponse result = vetApiTransactionCaller.createVet(dto, imageUploadDto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public UpdateVetResponse updateVet(UpdateVetRequest dto, FileUploadDTO imageUploadDto) throws IOException {
        LOG.trace("updateVet {} {} {} {}", dto.getId(), dto.getUserId(), dto.getUserId(), imageUploadDto);

        final UpdateVetResponse result = vetApiTransactionCaller.updateVet(dto, imageUploadDto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public void deleteVet(DeleteVetRequest dto) {
        LOG.trace("deleteVet {}", dto.getId());

        vetApiTransactionCaller.deleteVet(dto);
        auditFacade.flushAfterTransaction();
    }

    public List<VetWithSpecialtiesDTO> vetsWithSpecialties() {
        LOG.trace("vetsWithSpecialties");

        final List<VetWithSpecialtiesDTO> result = vetApiTransactionCaller.vetsWithSpecialties();
        auditFacade.flushAfterTransaction();
        return result;
    }

    public List<VetWithSpecialtiesDTO> vetInfo(VetInfoRequest dto) {
        LOG.trace("vetInfo {}", dto.getId());

        final List<VetWithSpecialtiesDTO> result = vetApiTransactionCaller.vetInfo(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }
}
