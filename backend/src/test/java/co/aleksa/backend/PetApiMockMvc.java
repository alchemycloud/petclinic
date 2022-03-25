package co.aleksa.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import co.aleksa.backend.api.dto.PagedDTO;
import co.aleksa.backend.api.dto.petapi.CreatePetRequest;
import co.aleksa.backend.api.dto.petapi.CreatePetResponse;
import co.aleksa.backend.api.dto.petapi.PetsByTypeResponse;
import co.aleksa.backend.api.dto.petapi.PetsResponse;
import co.aleksa.backend.api.dto.petapi.ReadPetResponse;
import co.aleksa.backend.api.dto.petapi.UpdatePetRequest;
import co.aleksa.backend.api.dto.petapi.UpdatePetResponse;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.id.PetId;
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

public interface PetApiMockMvc {

    MockMvc getMockMvc();

    ObjectMapper getObjectMapper();

    AbstractApiTest getAbstractApiTest();

    byte[] convertObjectToJsonBytes(Object object) throws IOException;

    private ReadPetResponse readPet(PetId id, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/pets/read-pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("id", id.getValue().toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<ReadPetResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), ReadPetResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default ReadPetResponse readPet(PetId id) throws Exception {
        return readPet(id, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private CreatePetResponse createPet(CreatePetRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                post("/api" + "/pets/create-pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<CreatePetResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), CreatePetResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default CreatePetResponse createPet(CreatePetRequest request) throws Exception {
        return createPet(request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private UpdatePetResponse updatePet(UpdatePetRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                put("/api" + "/pets/update-pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<UpdatePetResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), UpdatePetResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default UpdatePetResponse updatePet(UpdatePetRequest request) throws Exception {
        return updatePet(request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private void deletePet(PetId id, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                delete("/api" + "/pets/delete-pet")
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

    default void deletePet(PetId id) throws Exception {
        deletePet(id, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private PagedDTO<PetsResponse> pets(Integer drop, Integer take, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/pets/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("drop", drop.toString());
        builder = builder.param("take", take.toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<PagedDTO<LinkedHashMap<String, Object>>> interimResult = RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class);
        if (interimResult.getStatus() >= 200 && interimResult.getStatus() < 300) {
            final PagedDTO<LinkedHashMap<String, Object>> data = interimResult.getData();
            final List<PetsResponse> dtos =
                    data.getResults().stream()
                            .map(
                                    r -> {
                                        final PetId id = new PetId(((Integer) r.get(PetsResponse.ID.getPath())).longValue());
                                        final String name = (String) r.get(PetsResponse.NAME.getPath());
                                        final PetType petType = PetType.valueOf((String) r.get(PetsResponse.PET_TYPE.getPath()));
                                        final String ownerLastName = (String) r.get(PetsResponse.OWNER_LAST_NAME.getPath());
                                        return new PetsResponse(id, name, petType, ownerLastName);
                                    })
                            .toList();
            return new PagedDTO<>(dtos, data.getTotalCount());
        }

        final RestResponse<PagedDTO<PetsResponse>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class, PetsResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default PagedDTO<PetsResponse> pets(Integer drop, Integer take) throws Exception {
        return pets(drop, take, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private List<PetsByTypeResponse> petsByType(PetType petType, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/pets/pets-by-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("petType", petType.name());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<List<PetsByTypeResponse>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), List.class, PetsByTypeResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default List<PetsByTypeResponse> petsByType(PetType petType) throws Exception {
        return petsByType(petType, getAbstractApiTest().getCurrentUserAccessToken());
    }
}
