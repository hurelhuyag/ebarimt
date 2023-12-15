# Ebarimt.mn Client API model for OpenFeign

# References

- VAT Zero goods: https://developer.itc.gov.mn/assets/files/VAT-0-percent-goods.xlsx
- VAT Free goods: https://developer.itc.gov.mn/assets/files/VAT-free-goods.xlsx
- Classification Codes: https://ebarimt.mn/img/buteegdehuun%20uilchilgeenii%20negdsen%20angilal.pdf
- District Codes: https://api.ebarimt.mn/api/info/check/getBranchInfo
- Api Doc: https://developer.itc.gov.mn/assets/files/POS%20API%203.0.1.pdf

# Install PosService

```bash
sudo apt install unzip ar tar xz-utils
curl -s https://raw.githubusercontent.com/hurelhuyag/ebarimt/master/install.sh | bash
```

## Api Usage

pom.xml:
```xml
<project>
    <properties>
        <spring-cloud.version>2022.0.3</spring-cloud.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <dependency>
            <groupId>io.github.hurelhuyag</groupId>
            <artifactId>ebarimt</artifactId>
            <version>3.0.1+0</version>
        </dependency>
    </dependencies>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```

Set your Ebarimt service address on `application.properties`
```properties
spring.cloud.openfeign.client.config.EbarimtApi.url=http://127.0.0.1:7080
```

Enable feign bean from library
```java
@SpringBootApplication
@EnableFeignClients({
    "io.github.hurelhuyag.ebarimt",
})
class App {
    
}
```

Inject EbarimtApi as a dependency and call your desired methods
```java

@Service
@RequiredArgsConstructor
class SimpleOrderService implements OrderService {
    
    private final EbarimtApi ebarimtApi;

    @Override
    public void createEbarimt(Long id) {
        var ebarimt = ebarimtApi.createReceipt(new CreateReceipt(
            new BigDecimal("112.00"),
            new BigDecimal("10.00"),
            new BigDecimal("2.00"),
            "3502",
            37900846788L,
            "123",
            "5678",
            null,
            null,
            CreateReceipt.Type.B2C_RECEIPT,
            null,
            LocalDate.now().minusMonths(1),
            List.of(
                new CreateReceipt.Receipt(
                    new BigDecimal("112.00"),
                    new BigDecimal("10.00"),
                    new BigDecimal("2.00"),
                    CreateReceipt.VatType.VAT_ABLE,
                    37900846788L,
                    null,
                    List.of(
                        new CreateReceipt.ReceiptItem(
                            "Хатуу чихэр",
                            null,
                            CreateReceipt.BarCodeType.UNDEFINED,
                            2352010L,
                            null,
                            "p",
                            new BigDecimal("1.000"),
                            new BigDecimal("112.00"),
                            null,
                            new BigDecimal("10.00"),
                            new BigDecimal("2.00"),
                            new BigDecimal("112.00"),
                            null
                        )
                    )
                )
            ),
            List.of(
                new CreateReceipt.Payment(
                    CreateReceipt.PaymentCode.CASH,
                    CreateReceipt.PaymentStatus.PAID,
                    new BigDecimal("112.00"),
                    null
                )
            )
        ));
    }
}

```