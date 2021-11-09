package co.drytools.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import co.drytools.backend.api.dto.authenticationapi.ChangePasswordRequest;
import co.drytools.backend.api.dto.authenticationapi.ForgotPasswordRequest;
import co.drytools.backend.api.dto.authenticationapi.RefreshTokenRequest;
import co.drytools.backend.api.dto.authenticationapi.ResetPasswordRequest;
import co.drytools.backend.api.dto.authenticationapi.SignInRequest;
import co.drytools.backend.api.dto.authenticationapi.SignInResponse;
import co.drytools.backend.api.dto.authenticationapi.SignUpRequest;
import co.drytools.backend.api.dto.authenticationapi.VerifyEmailRequest;
import co.drytools.backend.util.AppThreadLocals;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

public interface AuthenticationApiMockMvc {

    MockMvc getMockMvc();

    ObjectMapper getObjectMapper();

    AbstractApiTest getAbstractApiTest();

    byte[] convertObjectToJsonBytes(Object object) throws IOException;

    default SignInResponse refreshToken(RefreshTokenRequest request) throws Exception {

        MockHttpServletRequestBuilder builder = post("/api" + "/refresh-token").contentType(MediaType.APPLICATION_JSON).header("x-timezone", "Europe/Belgrade");

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<SignInResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), SignInResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default SignInResponse signIn(SignInRequest request) throws Exception {

        MockHttpServletRequestBuilder builder = post("/api" + "/sign-in").contentType(MediaType.APPLICATION_JSON).header("x-timezone", "Europe/Belgrade");

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<SignInResponse> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), SignInResponse.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
        return restResponse.getData();
    }

    default void signUp(SignUpRequest request) throws Exception {

        MockHttpServletRequestBuilder builder = post("/api" + "/sign-up").contentType(MediaType.APPLICATION_JSON).header("x-timezone", "Europe/Belgrade");

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<Void> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), Void.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
    }

    default void forgotPassword(ForgotPasswordRequest request) throws Exception {

        MockHttpServletRequestBuilder builder =
                post("/api" + "/forgot-password").contentType(MediaType.APPLICATION_JSON).header("x-timezone", "Europe/Belgrade");

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<Void> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), Void.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
    }

    default void resetPassword(ResetPasswordRequest request) throws Exception {

        MockHttpServletRequestBuilder builder =
                post("/api" + "/reset-password").contentType(MediaType.APPLICATION_JSON).header("x-timezone", "Europe/Belgrade");

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<Void> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), Void.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
    }

    default void verifyEmail(VerifyEmailRequest request) throws Exception {

        MockHttpServletRequestBuilder builder = post("/api" + "/verify-email").contentType(MediaType.APPLICATION_JSON).header("x-timezone", "Europe/Belgrade");

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<Void> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), Void.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
    }

    private void changePassword(ChangePasswordRequest request, String accessToken) throws Exception {

        MockHttpServletRequestBuilder builder =
                post("/api" + "/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-timezone", "Europe/Belgrade")
                        .header("Authorization", "Bearer " + accessToken);

        final MvcResult result = getMockMvc().perform(builder.content(convertObjectToJsonBytes(request)).accept(MediaType.APPLICATION_JSON)).andReturn();
        final RestResponse<Void> restResponse = RestResponse.fromMvcResult(result, getObjectMapper(), Void.class);
        AppThreadLocals.setCountQueries(false);
        if (restResponse.getStatus() != HttpStatus.OK.value()) {
            throw new ResponseStatusException(HttpStatus.valueOf(restResponse.getStatus()));
        }
    }

    default void changePassword(ChangePasswordRequest request) throws Exception {
        changePassword(request, getAbstractApiTest().getCurrentUserAccessToken());
    }
}
