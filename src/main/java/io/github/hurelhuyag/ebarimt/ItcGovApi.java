package io.github.hurelhuyag.ebarimt;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ItcGovApi {

    private ItcGovAuthApi.AuthToken authToken;
    private LocalDateTime authTokenObtainedAt;

    private final ItcGovServiceApi itcGovServiceApi;
    private final ItcGovAuthApi itcGovAuthApi;
    private final ItcGovProperties itcGovProperties;

    public ItcGovApi(ItcGovServiceApi itcGovServiceApi, ItcGovAuthApi itcGovAuthApi, ItcGovProperties properties) {
        this.itcGovServiceApi = itcGovServiceApi;
        this.itcGovAuthApi = itcGovAuthApi;
        this.itcGovProperties = properties;
    }

    public ItcGovServiceApi.ConsumerInfo getConsumerInfo(String regNo) {
        obtainAuthTokenIfNeeded();
        return itcGovServiceApi.getConsumerInfo(regNo, "Bearer " + authToken.accessToken());
    }

    public ItcGovServiceApi.Profile getProfile(String phoneNum, String consumerNo) {
        obtainAuthTokenIfNeeded();
        return itcGovServiceApi.getProfile(new ItcGovServiceApi.GetProfileReq(phoneNum, consumerNo), "Bearer " + authToken.accessToken());
    }

    public ItcGovServiceApi.ApproveQrRes approveQr(String consumerNo, String qrData) {
        obtainAuthTokenIfNeeded();
        return itcGovServiceApi.approveQr(new ItcGovServiceApi.ApproveQr(consumerNo, qrData), "Bearer " + authToken.accessToken());
    }

    private boolean isAuthTokenExpired() {
        return authToken == null || authTokenObtainedAt.plusSeconds(authToken.expiresIn()).isBefore(LocalDateTime.now());
    }

    private void obtainAuthTokenIfNeeded() {
        if (isAuthTokenExpired()) {
            var now = LocalDateTime.now();
            authToken = itcGovAuthApi.auth("client_id=vatps&grant_type=password&username=%s&password=%s"
                                            .formatted(itcGovProperties.username(), itcGovProperties.password()));
            authTokenObtainedAt = now;
        }
    }
}
