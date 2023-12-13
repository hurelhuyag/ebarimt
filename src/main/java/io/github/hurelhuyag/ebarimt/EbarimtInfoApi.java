package io.github.hurelhuyag.ebarimt;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "EbarimtInfoApi", url = "https://info.ebarimt.mn/rest")
public interface EbarimtInfoApi {

    @GetMapping("/merchant/info")
    OrgInfo orgInfo(@RequestParam String regno) throws Exception;

    record OrgInfo(
        String vatpayerRegisteredDate,
        String lastReceiptDate,
        boolean receiptFound,
        String name,
        boolean freeProject,
        boolean found,
        boolean citypayer,
        boolean vatpayer
    ) {}
}
