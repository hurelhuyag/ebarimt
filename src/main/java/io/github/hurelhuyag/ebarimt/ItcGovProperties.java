package io.github.hurelhuyag.ebarimt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "itcgov")
public record ItcGovProperties(String username, String password) {
}
