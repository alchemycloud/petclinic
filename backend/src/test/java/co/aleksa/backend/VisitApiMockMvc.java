package co.aleksa.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import co.aleksa.backend.api.dto.PagedDTO;
import co.aleksa.backend.api.dto.visitapi.CreateVisitRequest;
import co.aleksa.backend.api.dto.visitapi.CreateVisitResponse;
import co.aleksa.backend.api.dto.visitapi.ReadVisitResponse;
import co.aleksa.backend.api.dto.visitapi.UpdateVisitRequest;
import co.aleksa.backend.api.dto.visitapi.UpdateVisitResponse;
import co.aleksa.backend.api.dto.visitapi.VisitDTO;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.model.id.VetId;
import co.aleksa.backend.model.id.VisitId;
import co.aleksa.backend.util.AppThreadLocals;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

public interface VisitApiMockMvc {

    MockMvc getMockMvc();

    ObjectMapper getObjectMapper();

    AbstractApiTest getAbstractApiTest();

    byte[] convertObjectToJsonBytes(Object object) throws IOException;

    private ReadVisitResponse readVisit(VisitId id, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/visits/read-visit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("id", id.getValue().toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<ReadVisitResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), ReadVisitResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default ReadVisitResponse readVisit(VisitId id) throws Exception {
        return readVisit(id, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private CreateVisitResponse createVisit(CreateVisitRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                post("/api" + "/visits/create-visit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<CreateVisitResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), CreateVisitResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default CreateVisitResponse createVisit(CreateVisitRequest request) throws Exception {
        return createVisit(request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private UpdateVisitResponse updateVisit(UpdateVisitRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                put("/api" + "/visits/update-visit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<UpdateVisitResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), UpdateVisitResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default UpdateVisitResponse updateVisit(UpdateVisitRequest request) throws Exception {
        return updateVisit(request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private void deleteVisit(VisitId id, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                delete("/api" + "/visits/delete-visit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("id", id.getValue().toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<Void> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), Void.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
    }

    default void deleteVisit(VisitId id) throws Exception {
        deleteVisit(id, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private PagedDTO<VisitDTO> visitsByVet(VetId vetId, Integer drop, Integer take, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/visits/visits-by-vet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("vetId", vetId.getValue().toString());
        builder = builder.param("drop", drop.toString());
        builder = builder.param("take", take.toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<PagedDTO<LinkedHashMap<String, Object>>> interimResult = RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class);
        if (interimResult.getStatus() >= 200 && interimResult.getStatus() < 300) {
            final PagedDTO<LinkedHashMap<String, Object>> data = interimResult.getData();
            final List<VisitDTO> dtos =
                    data.getResults().stream()
                            .map(
                                    r -> {
                                        final Integer visitNumber = (Integer) r.get(VisitDTO.VISIT_NUMBER.getPath());
                                        final String description = (String) r.get(VisitDTO.DESCRIPTION.getPath());
                                        final Boolean scheduled = (Boolean) r.get(VisitDTO.SCHEDULED.getPath());
                                        final String name = (String) r.get(VisitDTO.NAME.getPath());
                                        final PetType petType = PetType.valueOf((String) r.get(VisitDTO.PET_TYPE.getPath()));
                                        final String firstName = (String) r.get(VisitDTO.FIRST_NAME.getPath());
                                        final String lastName = (String) r.get(VisitDTO.LAST_NAME.getPath());
                                        return new VisitDTO(visitNumber, description, scheduled, name, petType, firstName, lastName);
                                    })
                            .toList();
            return new PagedDTO<>(dtos, data.getTotalCount());
        }

        final RestResponse<PagedDTO<VisitDTO>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class, VisitDTO.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default PagedDTO<VisitDTO> visitsByVet(VetId vetId, Integer drop, Integer take) throws Exception {
        return visitsByVet(vetId, drop, take, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private PagedDTO<VisitDTO> visitsByPet(PetId petId, Integer drop, Integer take, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/visits/visits-by-pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("petId", petId.getValue().toString());
        builder = builder.param("drop", drop.toString());
        builder = builder.param("take", take.toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<PagedDTO<LinkedHashMap<String, Object>>> interimResult = RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class);
        if (interimResult.getStatus() >= 200 && interimResult.getStatus() < 300) {
            final PagedDTO<LinkedHashMap<String, Object>> data = interimResult.getData();
            final List<VisitDTO> dtos =
                    data.getResults().stream()
                            .map(
                                    r -> {
                                        final Integer visitNumber = (Integer) r.get(VisitDTO.VISIT_NUMBER.getPath());
                                        final String description = (String) r.get(VisitDTO.DESCRIPTION.getPath());
                                        final Boolean scheduled = (Boolean) r.get(VisitDTO.SCHEDULED.getPath());
                                        final String name = (String) r.get(VisitDTO.NAME.getPath());
                                        final PetType petType = PetType.valueOf((String) r.get(VisitDTO.PET_TYPE.getPath()));
                                        final String firstName = (String) r.get(VisitDTO.FIRST_NAME.getPath());
                                        final String lastName = (String) r.get(VisitDTO.LAST_NAME.getPath());
                                        return new VisitDTO(visitNumber, description, scheduled, name, petType, firstName, lastName);
                                    })
                            .toList();
            return new PagedDTO<>(dtos, data.getTotalCount());
        }

        final RestResponse<PagedDTO<VisitDTO>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class, VisitDTO.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default PagedDTO<VisitDTO> visitsByPet(PetId petId, Integer drop, Integer take) throws Exception {
        return visitsByPet(petId, drop, take, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private PagedDTO<VisitDTO> scheduledVisits(Integer drop, Integer take, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/visits/scheduled-visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("drop", drop.toString());
        builder = builder.param("take", take.toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<PagedDTO<LinkedHashMap<String, Object>>> interimResult = RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class);
        if (interimResult.getStatus() >= 200 && interimResult.getStatus() < 300) {
            final PagedDTO<LinkedHashMap<String, Object>> data = interimResult.getData();
            final List<VisitDTO> dtos =
                    data.getResults().stream()
                            .map(
                                    r -> {
                                        final Integer visitNumber = (Integer) r.get(VisitDTO.VISIT_NUMBER.getPath());
                                        final String description = (String) r.get(VisitDTO.DESCRIPTION.getPath());
                                        final Boolean scheduled = (Boolean) r.get(VisitDTO.SCHEDULED.getPath());
                                        final String name = (String) r.get(VisitDTO.NAME.getPath());
                                        final PetType petType = PetType.valueOf((String) r.get(VisitDTO.PET_TYPE.getPath()));
                                        final String firstName = (String) r.get(VisitDTO.FIRST_NAME.getPath());
                                        final String lastName = (String) r.get(VisitDTO.LAST_NAME.getPath());
                                        return new VisitDTO(visitNumber, description, scheduled, name, petType, firstName, lastName);
                                    })
                            .toList();
            return new PagedDTO<>(dtos, data.getTotalCount());
        }

        final RestResponse<PagedDTO<VisitDTO>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class, VisitDTO.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default PagedDTO<VisitDTO> scheduledVisits(Integer drop, Integer take) throws Exception {
        return scheduledVisits(drop, take, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private PagedDTO<VisitDTO> visitsForOwner(Integer drop, Integer take, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/visits/visits-for-owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("drop", drop.toString());
        builder = builder.param("take", take.toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<PagedDTO<LinkedHashMap<String, Object>>> interimResult = RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class);
        if (interimResult.getStatus() >= 200 && interimResult.getStatus() < 300) {
            final PagedDTO<LinkedHashMap<String, Object>> data = interimResult.getData();
            final List<VisitDTO> dtos =
                    data.getResults().stream()
                            .map(
                                    r -> {
                                        final Integer visitNumber = (Integer) r.get(VisitDTO.VISIT_NUMBER.getPath());
                                        final String description = (String) r.get(VisitDTO.DESCRIPTION.getPath());
                                        final Boolean scheduled = (Boolean) r.get(VisitDTO.SCHEDULED.getPath());
                                        final String name = (String) r.get(VisitDTO.NAME.getPath());
                                        final PetType petType = PetType.valueOf((String) r.get(VisitDTO.PET_TYPE.getPath()));
                                        final String firstName = (String) r.get(VisitDTO.FIRST_NAME.getPath());
                                        final String lastName = (String) r.get(VisitDTO.LAST_NAME.getPath());
                                        return new VisitDTO(visitNumber, description, scheduled, name, petType, firstName, lastName);
                                    })
                            .toList();
            return new PagedDTO<>(dtos, data.getTotalCount());
        }

        final RestResponse<PagedDTO<VisitDTO>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class, VisitDTO.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default PagedDTO<VisitDTO> visitsForOwner(Integer drop, Integer take) throws Exception {
        return visitsForOwner(drop, take, getAbstractApiTest().getCurrentUserAccessToken());
    }
}
