package com.livejournal.uisteps.core;

/**
 *
 * @author ASolyankin
 */
public class Url {

    private String protocol = "http";
    private String prefix = "";
    private String host = "";
    private Integer port = -1;
    private String postfix = "";

    public String getProtocol() {
        return protocol;
    }

    public Url setProtocol(String value) {
        if (value != null) {
            protocol = value;
        }
        return this;
    }

    public String getHost() {
        return host;
    }

    public Url setHost(String value) {
        if (value != null) {
            host = value;
        }
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public Url setPort(Integer value) {
        if (value != null) {
            port = value;
        }
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public Url setPrefix(String value) {
        if (value != null) {
            prefix = value;
        }
        return this;
    }

    public Url appendPrefix(String value) {
        if (value != null) {
            prefix += value;
        }
        return this;
    }

    public Url prependPrefix(String value) {
        if (value != null) {
            prefix = value + prefix;
        }
        return this;
    }

    public String getPostfix() {
        return postfix;
    }

    public Url setPostfix(String value) {
        if (value != null) {
            postfix = value;
        }
        return this;
    }

    public Url appendPostfix(String value) {
        if (value != null) {
            postfix += value;
        }
        return this;
    }

    public Url prependPostfix(String value) {
        if (value != null) {
            postfix = value + postfix;
        }
        return this;
    }

    @Override
    public String toString() {
        String url = protocol + "://" + prefix + host;
        if (port != null && port > -1) {
            url += ":" + port;
        }
        url += postfix;
        return url;
    }
}
