package io.github.hurelhuyag.ebarimt;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "EbarimtInquireApi", url = "https://api.ebarimt.mn")
public interface EbarimtInquireApi {

    @GetMapping("/api/info/check/getTinInfo?regNo={regNo}")
    TinInfo getTinInfo(@RequestParam String regNo);

    record TinInfo(
        String msg,
        int status,
        String data
    ) {}

    @GetMapping("/api/info/check/getBranchInfo")
    BranchInfo getBranchInfo();

    record BranchInfo (
        String msg,
        int status,
        List<BranchInfoData> data
    ) {}

    record BranchInfoData(
        String branchCode,
        String branchName,
        String subBranchCode,
        String subBranchName
    ) {}
}
