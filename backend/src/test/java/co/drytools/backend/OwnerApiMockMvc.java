package co.drytools.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import co.drytools.backend.api.dto.PagedDTO;
import co.drytools.backend.api.dto.ownerapi.AllOwnersResponse;
import co.drytools.backend.api.dto.ownerapi.CreateOwnerRequest;
import co.drytools.backend.api.dto.ownerapi.CreateOwnerResponse;
import co.drytools.backend.api.dto.ownerapi.MyPetsResponse;
import co.drytools.backend.api.dto.ownerapi.OwnerVetsResponse;
import co.drytools.backend.api.dto.ownerapi.OwnersForAddressResponse;
import co.drytools.backend.api.dto.ownerapi.OwnersPetsResponse;
import co.drytools.backend.api.dto.ownerapi.OwnersWithPetsResponse;
import co.drytools.backend.api.dto.ownerapi.ReadOwnerResponse;
import co.drytools.backend.api.dto.ownerapi.UpdateOwnerResponse;
import co.drytools.backend.model.id.OwnerId;
import co.drytools.backend.rest.dto.ownerapi.RestUpdateOwnerRequest;
import co.drytools.backend.util.AppThreadLocals;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private ReadOwnerResponse readOwner(OwnerId id, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/owner/" + id + "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<ReadOwnerResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), ReadOwnerResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default ReadOwnerResponse readOwner(OwnerId id) throws Exception {
        return readOwner(id, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private CreateOwnerResponse createOwner(CreateOwnerRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                post("/api" + "/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<CreateOwnerResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), CreateOwnerResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default CreateOwnerResponse createOwner(CreateOwnerRequest request) throws Exception {
        return createOwner(request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private UpdateOwnerResponse updateOwner(OwnerId id, RestUpdateOwnerRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                put("/api" + "/owner/" + id + "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<UpdateOwnerResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), UpdateOwnerResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default UpdateOwnerResponse updateOwner(OwnerId id, RestUpdateOwnerRequest request) throws Exception {
        return updateOwner(id, request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private void deleteOwner(OwnerId id, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                delete("/api" + "/owner/" + id + "")
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

    default void deleteOwner(OwnerId id) throws Exception {
        deleteOwner(id, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private List<AllOwnersResponse> allOwners(Integer param, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/allOwners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("param", param.toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<List<AllOwnersResponse>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), List.class, AllOwnersResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default List<AllOwnersResponse> allOwners(Integer param) throws Exception {
        return allOwners(param, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private PagedDTO<OwnersForAddressResponse> ownersForAddress(Optional<String> address, Integer drop, Integer take, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/owners")
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
            final List<OwnersForAddressResponse> dtos =
                    data.getResults().stream()
                            .map(
                                    r -> {
                                        final OwnerId id = new OwnerId(((Integer) r.get(OwnersForAddressResponse.ID.getPath())).longValue());
                                        final String userEmail = (String) r.get(OwnersForAddressResponse.USER_EMAIL.getPath());
                                        final String userFirstName = (String) r.get(OwnersForAddressResponse.USER_FIRST_NAME.getPath());
                                        final String userLastName = (String) r.get(OwnersForAddressResponse.USER_LAST_NAME.getPath());
                                        return new OwnersForAddressResponse(id, userEmail, userFirstName, userLastName);
                                    })
                            .collect(Collectors.toList());
            return new PagedDTO<>(dtos, data.getTotalCount());
        }

        final RestResponse<PagedDTO<OwnersForAddressResponse>> restResponse =
                RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class, OwnersForAddressResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default PagedDTO<OwnersForAddressResponse> ownersForAddress(Optional<String> address, Integer drop, Integer take) throws Exception {
        return ownersForAddress(address, drop, take, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private List<OwnersWithPetsResponse> ownersWithPets(String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/ownersWithPets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<List<OwnersWithPetsResponse>> restResponse =
                RestResponse.fromMvcResult(result, getObjectMapper(), List.class, OwnersWithPetsResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default List<OwnersWithPetsResponse> ownersWithPets() throws Exception {
        return ownersWithPets(getAbstractApiTest().getCurrentUserAccessToken());
    }

    private List<OwnersPetsResponse> ownersPets(OwnerId ownerId, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/owner/" + ownerId + "/pets")
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
                get("/api" + "/my-pets")
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
                get("/api" + "/owner-vets")
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
