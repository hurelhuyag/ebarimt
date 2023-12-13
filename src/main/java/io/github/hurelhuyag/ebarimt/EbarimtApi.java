package io.github.hurelhuyag.ebarimt;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "EbarimtApi")
public interface EbarimtApi {

    @PostMapping("/rest/receipt")
    CreateReceiptResp createReceipt(@RequestBody CreateReceipt req);

    @DeleteMapping("/rest/receipt")
    void deleteReceipt(@RequestBody DeleteReceipt req);

    @GetMapping("/rest/sendData")
    void send();

    @GetMapping("/rest/info")
    PosApiInfo info();
}
