package io.github.hurelhuyag.ebarimt;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ItcGovServiceApi", url = "https://service.itc.gov.mn", primary = false)
public interface ItcGovServiceApi {

    @GetMapping("/api/easy-register/api/info/consumer/{regNo}")
    ConsumerInfo getConsumerInfo(@PathVariable String regNo, @RequestHeader("Authorization") String authToken);

    @PostMapping("/api/easy-register/rest/v1/getProfile")
    Profile getProfile(@RequestBody GetProfileReq req, @RequestHeader("Authorization") String authToken);

    @PostMapping("/api/easy-register/rest/v1/approveQr")
    ApproveQrRes approveQr(@RequestBody ApproveQr req, @RequestHeader("Authorization") String authToken);

    record ConsumerInfo (
            String regNo,
            String loginName,
            String givenName,
            String familyName
    ) {}

    record GetProfileReq(String phoneNum, String consumerNo) { }

    record Profile(
        String success,
        String loginName,
        String email,
        String msg,
        int status
    ) { }

    record ApproveQr(String customerNo, String qrData) { }

    record ApproveQrRes(
        String code,
        String loginName,
        String email,
        String msg,
        int status
    ) { }
}
