package co.drytools.backend.api;

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
import co.drytools.backend.audit.AuditFacade;
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
        LOG.trace("createVet {} {}", dto.getUserId(), imageUploadDto);

        final CreateVetResponse result = vetApiTransactionCaller.createVet(dto, imageUploadDto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public UpdateVetResponse updateVet(UpdateVetRequest dto, FileUploadDTO imageUploadDto) throws IOException {
        LOG.trace("updateVet {} {} {}", dto.getId(), dto.getUserId(), imageUploadDto);

        final UpdateVetResponse result = vetApiTransactionCaller.updateVet(dto, imageUploadDto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public void deleteVet(DeleteVetRequest dto) {
        LOG.trace("deleteVet {}", dto.getId());

        vetApiTransactionCaller.deleteVet(dto);
        auditFacade.flushAfterTransaction();
    }

    public List<VetWithSpecialtiesDTO> vetsWithSpecialties(VetDTO dto) {
        LOG.trace("vetsWithSpecialties {}", dto.getId());

        final List<VetWithSpecialtiesDTO> result = vetApiTransactionCaller.vetsWithSpecialties(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public VetWithSpecialtiesDTO vetInfo(VetInfoRequest dto) {
        LOG.trace("vetInfo {}", dto.getId());

        final VetWithSpecialtiesDTO result = vetApiTransactionCaller.vetInfo(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }
}
