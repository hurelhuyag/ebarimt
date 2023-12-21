package io.github.hurelhuyag.ebarimt;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ItcGovAuthApi", url = "https://auth.itc.gov.mn", primary = false)
public interface ItcGovAuthApi {

    @PostMapping(value = "/auth/realms/ITC/protocol/openid-connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AuthToken auth(String body);

    record AuthToken (
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") int expiresIn,
        @JsonProperty("refresh_expires_in") int refreshExpiresIn,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("not-before-policy") long notBeforePolicy,
        @JsonProperty("session_state") String sessionState,
        @JsonProperty("scope") String scope
    ) {}
}
