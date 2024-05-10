package com.simpleloadbalancer.loadbalancer;

public class LBConfiguration {

    private Integer service_timeout_in_ms;
    private Integer healthcheck_interval_in_seconds;
    private String healthcheck_url;
    private Integer healthcheck_port;
    private Integer failure_threshhold_times;

    public LBConfiguration(Integer service_timeout_in_ms, Integer healthcheck_interval_in_seconds, String healthcheck_url, Integer healthcheck_port, Integer failure_threshhold_times) {
        this.service_timeout_in_ms = service_timeout_in_ms;
        this.healthcheck_interval_in_seconds = healthcheck_interval_in_seconds;
        this.healthcheck_url = healthcheck_url;
        this.healthcheck_port = healthcheck_port;
        this.failure_threshhold_times = failure_threshhold_times;
    }

    public Integer getService_timeout_in_ms() {
        return service_timeout_in_ms;
    }

    public void setService_timeout_in_ms(Integer service_timeout_in_ms) {
        this.service_timeout_in_ms = service_timeout_in_ms;
    }

    public Integer getHealthcheck_interval_in_seconds() {
        return healthcheck_interval_in_seconds;
    }

    public void setHealthcheck_interval_in_seconds(Integer healthcheck_interval_in_seconds) {
        this.healthcheck_interval_in_seconds = healthcheck_interval_in_seconds;
    }

    public String getHealthcheck_url() {
        return healthcheck_url;
    }

    public void setHealthcheck_url(String healthcheck_url) {
        this.healthcheck_url = healthcheck_url;
    }

    public Integer getHealthcheck_port() {
        return healthcheck_port;
    }

    public void setHealthcheck_port(Integer healthcheck_port) {
        this.healthcheck_port = healthcheck_port;
    }

    public Integer getFailure_threshhold_times() {
        return failure_threshhold_times;
    }

    public void setFailure_threshhold_times(Integer failure_threshhold_times) {
        this.failure_threshhold_times = failure_threshhold_times;
    }
}
