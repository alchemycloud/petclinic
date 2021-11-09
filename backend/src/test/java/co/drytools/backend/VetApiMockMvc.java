package co.drytools.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import co.drytools.backend.api.dto.vetapi.CreateVetResponse;
import co.drytools.backend.api.dto.vetapi.ReadVetResponse;
import co.drytools.backend.api.dto.vetapi.UpdateVetResponse;
import co.drytools.backend.api.dto.vetapi.VetWithSpecialtiesDTO;
import co.drytools.backend.model.id.VetId;
import co.drytools.backend.rest.dto.vetapi.RestCreateVetRequest;
import co.drytools.backend.rest.dto.vetapi.RestUpdateVetRequest;
import co.drytools.backend.util.AppThreadLocals;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

public interface VetApiMockMvc {

    MockMvc getMockMvc();

    ObjectMapper getObjectMapper();

    AbstractApiTest getAbstractApiTest();

    byte[] convertObjectToJsonBytes(Object object) throws IOException;

    private ReadVetResponse readVet(VetId id, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/vet/" + id + "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<ReadVetResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), ReadVetResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default ReadVetResponse readVet(VetId id) throws Exception {
        return readVet(id, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private CreateVetResponse createVet(RestCreateVetRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                post("/api" + "/vet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<CreateVetResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), CreateVetResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default CreateVetResponse createVet(RestCreateVetRequest request) throws Exception {
        return createVet(request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private UpdateVetResponse updateVet(VetId id, RestUpdateVetRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                put("/api" + "/vet/" + id + "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<UpdateVetResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), UpdateVetResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default UpdateVetResponse updateVet(VetId id, RestUpdateVetRequest request) throws Exception {
        return updateVet(id, request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private void deleteVet(VetId id, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                delete("/api" + "/vet/" + id + "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<Void> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), Void.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
    }

    default void deleteVet(VetId id) throws Exception {
        deleteVet(id, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private List<VetWithSpecialtiesDTO> vetsWithSpecialties(VetId id, String firstName, String lastName, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/vets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("id", id.getValue().toString());
        builder = builder.param("firstName", firstName);
        builder = builder.param("lastName", lastName);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<List<VetWithSpecialtiesDTO>> restResponse =
                RestResponse.fromMvcResult(result, getObjectMapper(), List.class, VetWithSpecialtiesDTO.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default List<VetWithSpecialtiesDTO> vetsWithSpecialties(VetId id, String firstName, String lastName) throws Exception {
        return vetsWithSpecialties(id, firstName, lastName, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private VetWithSpecialtiesDTO vetInfo(VetId id, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/vet/info/" + id + "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<VetWithSpecialtiesDTO> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), VetWithSpecialtiesDTO.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default VetWithSpecialtiesDTO vetInfo(VetId id) throws Exception {
        return vetInfo(id, getAbstractApiTest().getCurrentUserAccessToken());
    }
}
