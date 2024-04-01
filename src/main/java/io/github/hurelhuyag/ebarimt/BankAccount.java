package io.github.hurelhuyag.ebarimt;

public record BankAccount(
    Integer id,
    String tin,
    String bankAccountNo,
    String bankAccountName,
    Integer bankId,
    String bankName
) {
}
