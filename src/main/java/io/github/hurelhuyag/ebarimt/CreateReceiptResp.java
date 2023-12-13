package io.github.hurelhuyag.ebarimt;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Баимт
 * @param id Баримтын ДДТД. 33 оронтой.
 * @param posId Бодит тоо
 * @param status баримтын төлөв
 * @param message
 * @param qrData
 * @param lottery
 * @param printedAt
 * @param receipts
 */
public record CreateReceiptResp(
    String id,
    String posId,
    Status status,
    String message,
    String qrData,
    String lottery,
    @JsonProperty("date") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime printedAt,
    List<Receipt> receipts
) {

    /**
     * Дэд баримт
     * @param id Дэд баримтын ДДТД. 33 оронтой тоо
     */
    public record Receipt(
        String id
    ) {}

    public enum Status {

        /**
         * Төлбөрийн баримтын мэдээлэл амжилттай үүссэн.
         */
        SUCCESS,

        /**
         * Төлбөрийн баримтын мэдээлэл үүсгэхэд алдаа гарсан.
         */
        ERROR,

        /**
         * Төлбөрийн баримтын мэдээлэл үүсгэхэд төлбөрийн мэдээлэл дутуу.
         */
        PAYMENT,
    }
}
