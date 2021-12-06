package co.drytools.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import co.drytools.backend.api.dto.PagedDTO;
import co.drytools.backend.api.dto.visitapi.CreateVisitRequest;
import co.drytools.backend.api.dto.visitapi.CreateVisitResponse;
import co.drytools.backend.api.dto.visitapi.MyVisitsResponse;
import co.drytools.backend.api.dto.visitapi.ReadVisitResponse;
import co.drytools.backend.api.dto.visitapi.ScheduledVisitsResponse;
import co.drytools.backend.api.dto.visitapi.UpdateVisitResponse;
import co.drytools.backend.api.dto.visitapi.VetVisitsResponse;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.model.id.VisitId;
import co.drytools.backend.rest.dto.visitapi.RestUpdateVisitRequest;
import co.drytools.backend.util.AppThreadLocals;
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
                get("/api" + "/visit/visit/" + id + "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

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
                post("/api" + "/visit/visit")
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

    private UpdateVisitResponse updateVisit(VisitId id, RestUpdateVisitRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                put("/api" + "/visit/visit/" + id + "")
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

    default UpdateVisitResponse updateVisit(VisitId id, RestUpdateVisitRequest request) throws Exception {
        return updateVisit(id, request, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private void deleteVisit(VisitId id, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                delete("/api" + "/visit/visit/" + id + "")
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

    default void deleteVisit(VisitId id) throws Exception {
        deleteVisit(id, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private PagedDTO<VetVisitsResponse> vetVisits(UserId userId, Integer drop, Integer take, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/visit/vet-visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("userId", userId.getValue().toString());
        builder = builder.param("drop", drop.toString());
        builder = builder.param("take", take.toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<PagedDTO<LinkedHashMap<String, Object>>> interimResult = RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class);
        if (interimResult.getStatus() >= 200 && interimResult.getStatus() < 300) {
            final PagedDTO<LinkedHashMap<String, Object>> data = interimResult.getData();
            final List<VetVisitsResponse> dtos =
                    data.getResults().stream()
                            .map(
                                    r -> {
                                        final UserId vetUserId = new UserId(((Integer) r.get(VetVisitsResponse.VET_USER_ID.getPath())).longValue());
                                        final String petName = (String) r.get(VetVisitsResponse.PET_NAME.getPath());
                                        final Integer visitNumber = (Integer) r.get(VetVisitsResponse.VISIT_NUMBER.getPath());
                                        final Boolean scheduled = (Boolean) r.get(VetVisitsResponse.SCHEDULED.getPath());
                                        return new VetVisitsResponse(vetUserId, petName, visitNumber, scheduled);
                                    })
                            .toList();
            return new PagedDTO<>(dtos, data.getTotalCount());
        }

        final RestResponse<PagedDTO<VetVisitsResponse>> restResponse =
                RestResponse.fromMvcResult(result, getObjectMapper(), PagedDTO.class, VetVisitsResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default PagedDTO<VetVisitsResponse> vetVisits(UserId userId, Integer drop, Integer take) throws Exception {
        return vetVisits(userId, drop, take, getAbstractApiTest().getCurrentUserAccessToken());
    }

    private List<ScheduledVisitsResponse> scheduledVisits(String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/visit/scheduled")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<List<ScheduledVisitsResponse>> restResponse =
                RestResponse.fromMvcResult(result, getObjectMapper(), List.class, ScheduledVisitsResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default List<ScheduledVisitsResponse> scheduledVisits() throws Exception {
        return scheduledVisits(getAbstractApiTest().getCurrentUserAccessToken());
    }

    private List<MyVisitsResponse> myVisits(UserId userIdId, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                get("/api" + "/visit/my-visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        builder = builder.param("userIdId", userIdId.getValue().toString());

        final MvcResult result = getMockMvc().perform(builder.accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<List<MyVisitsResponse>> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), List.class, MyVisitsResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default List<MyVisitsResponse> myVisits(UserId userIdId) throws Exception {
        return myVisits(userIdId, getAbstractApiTest().getCurrentUserAccessToken());
    }
}
