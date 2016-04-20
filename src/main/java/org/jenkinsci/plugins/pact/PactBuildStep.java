package org.jenkinsci.plugins.pact;

import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;

import org.kohsuke.stapler.DataBoundConstructor;

public class PactBuildStep {

    private String pactBrokerUrl;
    private String pactFileAntPath;

    private boolean transmitPactFilesAfterBuild;

    @DataBoundConstructor
    public PactBuildStep(String pactBrokerUrl, boolean transmitPactFilesAfterBuild) {
        this.pactBrokerUrl = pactBrokerUrl;
        this.transmitPactFilesAfterBuild = transmitPactFilesAfterBuild;
    }




    public String getPactBrokerUrl() {
        return pactBrokerUrl;
    }

    public void setPactBrokerUrl(String pactBrokerUrl) {
        this.pactBrokerUrl = pactBrokerUrl;
    }

    public boolean isTransmitPactFilesAfterBuild() {
        return transmitPactFilesAfterBuild;
    }

    public void setTransmitPactFilesAfterBuild(boolean transmitPactFilesAfterBuild) {
        this.transmitPactFilesAfterBuild = transmitPactFilesAfterBuild;
    }

    public static class DescriptorImpl extends BuildStepDescriptor<Builder> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return false;
        }

        @Override
        public String getDisplayName() {
            return "Pact publisher";
        }
    }
}
