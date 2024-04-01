package io.github.hurelhuyag.ebarimt;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "EbarimtApi", primary = false)
public interface EbarimtApi {

    @PostMapping("/rest/receipt")
    CreateReceiptResp createReceipt(@RequestBody CreateReceipt req);

    @DeleteMapping("/rest/receipt")
    void deleteReceipt(@RequestBody DeleteReceipt req);

    @GetMapping("/rest/sendData")
    void send();

    @GetMapping("/rest/info")
    PosApiInfo info();

    @GetMapping("/rest/bankAccounts")
    List<BankAccount> bankAccounts(@RequestParam Long tin);
}
