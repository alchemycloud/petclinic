package co.aleksa.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import co.aleksa.backend.api.dto.PagedDTO;
import co.aleksa.backend.api.dto.ownerapi.AllOwnersResponse;
import co.aleksa.backend.api.dto.ownerapi.CreateOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.CreateOwnersResponse;
import co.aleksa.backend.api.dto.ownerapi.EnrichedOwnerDTO;
import co.aleksa.backend.api.dto.ownerapi.MyPetsResponse;
import co.aleksa.backend.api.dto.ownerapi.OwnerVetsResponse;
import co.aleksa.backend.api.dto.ownerapi.OwnersPetsResponse;
import co.aleksa.backend.api.dto.ownerapi.ReadOwnersResponse;
import co.aleksa.backend.api.dto.ownerapi.UpdateOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.UpdateOwnersResponse;
import co.aleksa.backend.model.id.OwnerId;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.util.AppThreadLocals;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

public interface OwnerApiMockMvc {

    MockMvc getMockMvc();

    ObjectMapper getObjectMapper();

    AbstractApiTest getAbstractApiTest();

    byte[] convertObjectToJsonBytes(Object object) throws IOException;

    private ReadOwnersResponse readOwners(OwnerId id, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/owners/read-owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("id", id.getValue().toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<ReadOwnersResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), ReadOwnersResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default ReadOwnersResponse readOwners(OwnerId id) throws Exception {
        return readOwners(id, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private CreateOwnersResponse createOwners(CreateOwnersRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                post("/api" + "/owners/create-owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<CreateOwnersResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), CreateOwnersResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default CreateOwnersResponse createOwners(CreateOwnersRequest request) throws Exception {
        return createOwners(request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private UpdateOwnersResponse updateOwners(UpdateOwnersRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                put("/api" + "/owners/update-owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<UpdateOwnersResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), UpdateOwnersResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default UpdateOwnersResponse updateOwners(UpdateOwnersRequest request) throws Exception {
        return updateOwners(request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private void deleteOwners(OwnerId id, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                delete("/api" + "/owners/delete-owners")
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

    default void deleteOwners(OwnerId id) throws Exception {
        deleteOwners(id, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private PagedDTO<AllOwnersResponse> allOwners(Integer drop, Integer take, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/owners/all-owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("drop", drop.toString());
        builder = builder.param("take", take.toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<PagedDTO<LinkedHashMap<String, Object>>> interimResult = RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class);
        if (interimResult.getStatus() >= 200 && interimResult.getStatus() < 300) {
            final PagedDTO<LinkedHashMap<String, Object>> data = interimResult.getData();
            final List<AllOwnersResponse> dtos =
                    data.getResults().stream()
                            .map(
                                    r -> {
                                        final OwnerId id = new OwnerId(((Integer) r.get(AllOwnersResponse.ID.getPath())).longValue());
                                        final UserId userId = new UserId(((Integer) r.get(AllOwnersResponse.USER_ID.getPath())).longValue());
                                        final String address = (String) r.get(AllOwnersResponse.ADDRESS.getPath());
                                        final String city = (String) r.get(AllOwnersResponse.CITY.getPath());
                                        final String telephone = (String) r.get(AllOwnersResponse.TELEPHONE.getPath());
                                        return new AllOwnersResponse(id, userId, address, city, telephone);
                                    })
                            .toList();
            return new PagedDTO<>(dtos, data.getTotalCount());
        }

        final RestResponse<PagedDTO<AllOwnersResponse>> restResponse =
                RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class, AllOwnersResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default PagedDTO<AllOwnersResponse> allOwners(Integer drop, Integer take) throws Exception {
        return allOwners(drop, take, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private PagedDTO<EnrichedOwnerDTO> forAddress(Optional<String> address, Integer drop, Integer take, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/owners/for-address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);
        if (address.isPresent()) builder = builder.param("address", address.get());

        builder = builder.param("drop", drop.toString());
        builder = builder.param("take", take.toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<PagedDTO<LinkedHashMap<String, Object>>> interimResult = RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class);
        if (interimResult.getStatus() >= 200 && interimResult.getStatus() < 300) {
            final PagedDTO<LinkedHashMap<String, Object>> data = interimResult.getData();
            final List<EnrichedOwnerDTO> dtos =
                    data.getResults().stream()
                            .map(
                                    r -> {
                                        final OwnerId id = new OwnerId(((Integer) r.get(EnrichedOwnerDTO.ID.getPath())).longValue());
                                        final String email = (String) r.get(EnrichedOwnerDTO.EMAIL.getPath());
                                        final String firstName = (String) r.get(EnrichedOwnerDTO.FIRST_NAME.getPath());
                                        final String lastName = (String) r.get(EnrichedOwnerDTO.LAST_NAME.getPath());
                                        return new EnrichedOwnerDTO(id, email, firstName, lastName);
                                    })
                            .toList();
            return new PagedDTO<>(dtos, data.getTotalCount());
        }

        final RestResponse<PagedDTO<EnrichedOwnerDTO>> restResponse =
                RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class, EnrichedOwnerDTO.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default PagedDTO<EnrichedOwnerDTO> forAddress(Optional<String> address, Integer drop, Integer take) throws Exception {
        return forAddress(address, drop, take, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private List<EnrichedOwnerDTO> ownersWithPets(String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/owners/owners-with-pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<List<EnrichedOwnerDTO>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), List.class, EnrichedOwnerDTO.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default List<EnrichedOwnerDTO> ownersWithPets() throws Exception {
        return ownersWithPets(getAbstractApiTest().getCurrentUserAccessToken());
    }

    private List<OwnersPetsResponse> ownersPets(OwnerId ownerId, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/owners/" + ownerId + "/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<List<OwnersPetsResponse>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), List.class, OwnersPetsResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default List<OwnersPetsResponse> ownersPets(OwnerId ownerId) throws Exception {
        return ownersPets(ownerId, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private List<MyPetsResponse> myPets(String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/owners/my-pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<List<MyPetsResponse>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), List.class, MyPetsResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default List<MyPetsResponse> myPets() throws Exception {
        return myPets(getAbstractApiTest().getCurrentUserAccessToken());
    }

    private List<OwnerVetsResponse> ownerVets(String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/owners/owner-vets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<List<OwnerVetsResponse>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), List.class, OwnerVetsResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default List<OwnerVetsResponse> ownerVets() throws Exception {
        return ownerVets(getAbstractApiTest().getCurrentUserAccessToken());
    }
}
