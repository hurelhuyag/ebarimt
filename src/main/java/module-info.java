module ebarimt {
    requires java.base;
    requires com.fasterxml.jackson.annotation;
    requires spring.cloud.openfeign.core;
    requires spring.web;
    requires spring.context;
    requires spring.boot;
    requires feign.core;
    requires feign.form;
    exports io.github.hurelhuyag.ebarimt;
}
