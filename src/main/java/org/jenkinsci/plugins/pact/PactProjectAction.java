package org.jenkinsci.plugins.pact;

import hudson.model.AbstractProject;
import hudson.model.Action;

import java.util.List;

public class PactProjectAction implements Action {

    private String pactBrokerGroupUrl;
    private String consumerName;

    private AbstractProject<?, ?> project;

    public PactProjectAction(AbstractProject<?, ?> project) {
        this.project = project;
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "PactProjectAction";
    }

    @Override
    public String getUrlName() {
        return "PactProjectAction";
    }

    public PactBuildAction lastCompletedPactBuildAction() {
        List<? extends Action> allActions = project.getLastBuild().getAllActions();
        PactBuildAction pactBuildAction = null;
        for (Action allAction : allActions) {
            if (allAction.getClass().equals(PactBuildAction.class)) {
                pactBuildAction = (PactBuildAction) allAction;
                break;
            }

        }
        return pactBuildAction;
    }
    public String getConsumerName() {

        return consumerName;
    }

    public String getPactBrokerGroupUrl() {
        return pactBrokerGroupUrl;
    }

    public void setPactBrokerGroupUrl(String pactBrokerGroupUrl) {
        this.pactBrokerGroupUrl = pactBrokerGroupUrl;
    }
}
