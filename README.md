# Ebarimt.mn Client API model for OpenFeign

## 1. References

- VAT Zero goods: https://developer.itc.gov.mn/assets/files/VAT-0-percent-goods.xlsx
- VAT Free goods: https://developer.itc.gov.mn/assets/files/VAT-free-goods.xlsx
- Classification Codes: https://ebarimt.mn/img/buteegdehuun%20uilchilgeenii%20negdsen%20angilal.pdf
- District Codes: https://api.ebarimt.mn/api/info/check/getBranchInfo
- Api Doc: https://developer.itc.gov.mn/assets/files/POS%20API%203.0.1.pdf
- https://ebarimt.mn/img/vat/khylbarBurtgelService.pdf

## 2. Install PosService

### 2.1 Check your Ebarimt's staging server access

- https://stg-invoice.ebarimt.mn/
- https://st-operator.ebarimt.mn/

### 2.2 Install PosService

```bash
sudo apt install unzip ar tar xz-utils
curl -s https://raw.githubusercontent.com/hurelhuyag/ebarimt/master/install.sh | bash
```

### 2.3 Configure PosService

1. Open http://127.0.0.1:7080 on web browser. I expect you should click some buttons.
2. Copy POS's number. It is 8 digits number located in second text input field.
3. Open https://st-operator.ebarimt.mn -> find by POS's number -> Double Click table's first cell 
4. Click button named "Мерчант нэмэх"
5. Find by 37900846788. It is demo merchant. Click "Хадгалах"
6. Open https://stg-invoice.ebarimt.mn/ -> "Хүсэлт" -> "Хүсэлт" -> "Pos Api хүсэлт" -> "Операторын холболт" -> "+"
7. Repeat 3rd step. You should see "Баталгаажсан" instead "Хүлээгдэж буй"
8. Repeat 1st step. Hit F5, Click "Мэдээлэл илгээх". You should notice "Мерчантын жагсаалт" table has entry.


## 3. Api Usage

### 3.1 pom.xml
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
            <version>3.0.1+2</version>
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

### 3.2 Set your Ebarimt service address on `application.properties`
```properties
spring.cloud.openfeign.client.config.EbarimtApi.url=http://127.0.0.1:7080
```

### 3.3 Enable feign bean from library
```java
@SpringBootApplication
@EnableFeignClients({
    "io.github.hurelhuyag.ebarimt",
})
class App {
    
}
```

### 3.4 Inject EbarimtApi as a dependency and call your desired methods
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
            null,
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


## Contribution

It is Apache 2.0 Licensed open source project. If you anything in you mind to improve this project. Feel free to file an issue or open pull request.

