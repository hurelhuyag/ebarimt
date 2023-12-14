package io.github.hurelhuyag.ebarimt;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * И-Баримт үүсгэх форм
 * @param totalAmount Багц баримтын гүйлгээний нийт дүн
 * @param totalVAT Багц баримтын НӨАТ-н нийт дүн
 * @param totalCityTax Багц баримтын НХАТ-н нийт дүн
 * @param districtCode Баримт хэвлэсэн орон нутгийн код
 * @param merchantTin Багц баримт олгогчийн ТТД
 * @param branchNo 0-999
 * @param posNo Тухайн байгууллагын дугаар
 * @param customerTin Худалдан авагчийн ТТД. 11 эсвэл 14 оронтой бүхэл тоо
 * @param consumerNo Худалдан авагч иргэний ebarimt-ийн бүртгэлийн дугаар. 8 оронтой тоо
 * @param type Баримтын төрөл
 * @param inactiveId Баримтыг засвралах, эсвэл хэсэгчилэн буцаах тохиолдолд засварлах баримтын дугаарыг хавсаргана.
 * @param invoiceId Өмнө нь үүсгэсэн нэхэмжилэхийн баримт бол нэхэмжилэхийн дугаарын оруулна.
 * @param reportMonth Баримт харьяалагдах тайлант сар
 * @param receipts Дэд төлбөрийн баримтууд
 * @param payments Төлбөрийн хэлбэр
 */
public record CreateReceipt(
    BigDecimal totalAmount,
    BigDecimal totalVAT,
    BigDecimal totalCityTax,
    String districtCode,
    @JsonFormat(shape = JsonFormat.Shape.STRING) Long merchantTin,
    String branchNo,
    String posNo,
    @JsonFormat(shape = JsonFormat.Shape.STRING) Long customerTin,
    @JsonFormat(shape = JsonFormat.Shape.STRING) Long consumerNo,
    Type type,
    String inactiveId,
    String invoiceId,
    @JsonFormat(shape = JsonFormat.Shape.STRING) LocalDate reportMonth,
    List<Receipt> receipts,
    List<Payment> payments
) {

    public enum Type {
        B2C_RECEIPT,
        B2B_RECEIPT,
        B2C_INVOICE,
        B2B_INVOICE,
    }

    public enum VatType {
        VAT_ABLE,
        VAT_FREE,
        VAT_ZERO,
        NO_VAT,
    }

    public enum BarCodeType {
        UNDEFINED,
        GS1,
        ISBN,
    }

    public enum PaymentCode {
        CASH,
        PAYMENT_CARD,
    }

    public enum PaymentStatus {
        PAID
    }

    /**
     * Дэд төлбөрийн баримт
     * @param totalAmount Дэд төлбөрийн баримтын гүйлгээний нийт дүн
     * @param totalVAT Дэд төлбөрийн баримтын НӨАТ-н нийт дүн
     * @param totalCityTax Дэд төлбөрийн баримтын НХАТ-н нийт дүн
     * @param taxType Татварын төрөл
     * @param merchantTin Борлуулагчийн ТТД. 11 эсвэл 14 оронтой бүхэл тоо
     * @param bankAccountNo Нэхэмжилэх банкны дансны дугаар
     * @param data Дэд төлбөрийн баримтын нэмэлт өгөгдөл.
     * @param items Борлуулсан бүтээгдэхүүн, үйлчилгээний жагсаалт
     */
    public record Receipt(
        BigDecimal totalAmount,
        BigDecimal totalVAT,
        BigDecimal totalCityTax,
        VatType taxType,
        @JsonFormat(shape = JsonFormat.Shape.STRING) Long merchantTin,
        String bankAccountNo,
        Map<String, Object> data,
        List<ReceiptItem> items
    ) {}

    /**
     * @param name Бүтээгдэхүүн, үйлчилгээний нэр
     * @param barCode Бүтээгдэхүүний зураасан код
     * @param barCodeType Зураасан кодын төрөл
     * @param classificationCode Монгол улсын үндэсний статистикийн хорооноос батлан гаргасан Бүтээгдэхүүн үйлчилгээний нэгдсэн ангилал. 7 оронтой тоо
     * @param taxProductCode taxType талбарын утга нь VAT_FREE, VAT_ZERO үед татварын харгалзах 3 оронтой тоон кодыг оруулана.
     * @param measureUnit Хэмжих нэгж
     * @param quantity Борлуулсан тоо, хэмжээ
     * @param unitPrice Нэгж үнэ
     * @param totalVAT Бүтээгдэхүүн, үйлчилгээний НӨАТ-н нийт дүн
     * @param totalCityTax Бүтээгдэхүүн, үйлчилгээний НХАТ-н нийт дүн
     * @param totalAmount Бүтээгдэхүүн, үйлчилгээний гүйлгээний нийт дүн
     * @param data Json Object. Нэмэлт өгөгдөл
     */
    public record ReceiptItem(
        String name,
        String barCode,
        BarCodeType barCodeType,
        String classificationCode,
        String taxProductCode,
        String measureUnit,
        @JsonProperty("qty") BigDecimal quantity,
        BigDecimal unitPrice,
        BigDecimal totalVAT,
        BigDecimal totalCityTax,
        BigDecimal totalAmount,
        Map<String, Object> data
    ) {}

    /**
     * Төлбөрийн хэлбэр
     * @param code Төлбөрийн хэлбэрийн код
     * @param status Төлбөрийн хэлбэрийн төлөв
     * @param paidAmount Нийт төлсөн дүн
     * @param data Төлбөрийн нэмэлт өгөгдөл
     */
    public record Payment (
        PaymentCode code,
        PaymentStatus status,
        BigDecimal paidAmount,
        Map<String, Object> data
    ) {}
}
