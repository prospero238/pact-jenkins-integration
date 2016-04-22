package org.jenkinsci.plugins.pact;

import hudson.model.Action;

public class PactBuildAction implements Action {

    private String consumerName;
    private String pactBrokerGroupUrl;


    public PactBuildAction() {

    }

    public PactBuildAction(String consumerName, String pactBrokerGroupUrl) {
        this.consumerName = consumerName;
        this.pactBrokerGroupUrl = pactBrokerGroupUrl;
    }

    public PactBuildAction(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getPactBrokerGroupUrl() {
        return pactBrokerGroupUrl;
    }

    public void setPactBrokerGroupUrl(String pactBrokerGroupUrl) {
        this.pactBrokerGroupUrl = pactBrokerGroupUrl;
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public String getUrlName() {
        return null;
    }
}
