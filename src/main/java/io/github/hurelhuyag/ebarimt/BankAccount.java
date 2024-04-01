package io.github.hurelhuyag.ebarimt;

public record BankAccount(
    Integer id,
    Long tin,
    String bankAccountNo,
    String bankAccountName,
    Integer bankId,
    String bankName
) {
}
