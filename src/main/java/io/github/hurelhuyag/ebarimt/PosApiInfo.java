package io.github.hurelhuyag.ebarimt;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record PosApiInfo(
    String operatorName,
    Long operatorTIN,
    String posId,
    Integer posNo,
    String version,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime lastSentDate,
    Integer leftLotteries,
    AppInfo appInfo,
    List<PaymentType> paymentTypes,
    List<Merchant> merchants
) {

    public record AppInfo(
        String applicationDir,
        String currentDir,
        String database,
        @JsonProperty("database-host") String databaseHost,
        String workDir
    ) {}

    public record PaymentType(
        String code,
        String name
    ) {}

    public record Merchant(
        String name,
        Long tin,
        Boolean vatPayer,
        List<Customer> customers
    ) {

        public record Customer(
            String name,
            Long tin,
            Boolean vatPayer
        ) {}
    }
}
