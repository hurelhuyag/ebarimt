package io.github.hurelhuyag.ebarimt;

public record ErrorResponse(CreateReceiptResp.Status status, String message) {
}
