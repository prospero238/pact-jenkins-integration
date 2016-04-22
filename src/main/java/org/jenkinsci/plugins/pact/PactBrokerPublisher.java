package org.jenkinsci.plugins.pact;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PactBrokerPublisher extends Recorder {

    @Extension
    public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

    private static final Logger LOG = LoggerFactory.getLogger(PactBrokerPublisher.class);

    private String brokerUrl;
    private String pactFileAntPath;

    private boolean transmitPactFilesAfterBuild;
    private boolean createDownstreamDependencies;



    @DataBoundConstructor
    public PactBrokerPublisher(String brokerUrl,
                               String pactFileAntPath,
                               boolean transmitPactFilesAfterBuild,
                               boolean createDownstreamDependencies) {
        this.brokerUrl = brokerUrl;
        this.pactFileAntPath = pactFileAntPath;
        this.transmitPactFilesAfterBuild = transmitPactFilesAfterBuild;
        this.createDownstreamDependencies = createDownstreamDependencies;
    }


    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener)
            throws InterruptedException, IOException {
        FilePath[] pactFiles = build.getWorkspace().list(pactFileAntPath);
        for (int i = 0; i < pactFiles.length; i++) {
            FilePath pactFilePath = pactFiles[i];
            InputStream read = pactFilePath.read();
            String pactFileContents = IOUtils.toString(read);
            File tempFile = File.createTempFile("pact" + i, ".json");

            FileUtils.write(tempFile, pactFileContents);
            PactFile pactFile = PactFile.readPactFile(pactFileContents);


            String relationshipLink = brokerUrl + "/groups/" + pactFile.getConsumer();

            LOG.debug("composed link:{}", relationshipLink);


            build.addAction(new PactBuildAction(pactFile.getConsumer(), relationshipLink));


        }
        return true;

    }

    @Override
    public Collection<? extends Action> getProjectActions(AbstractProject<?, ?> project) {
        Collection<Action> projectActions = new ArrayList<Action>();
        projectActions.add(new PactProjectAction(project));
        return projectActions;
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    @DataBoundSetter
    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public boolean isTransmitPactFilesAfterBuild() {
        return transmitPactFilesAfterBuild;
    }

    @DataBoundSetter
    public void setTransmitPactFilesAfterBuild(boolean transmitPactFilesAfterBuild) {
        this.transmitPactFilesAfterBuild = transmitPactFilesAfterBuild;
    }

    public boolean isCreateDownstreamDependencies() {
        return createDownstreamDependencies;
    }

    @DataBoundSetter
    public void setCreateDownstreamDependencies(boolean createDownstreamDependencies) {
        this.createDownstreamDependencies = createDownstreamDependencies;
    }


    public String getPactFileAntPath() {
        return pactFileAntPath;
    }
    @DataBoundSetter
    public void setPactFileAntPath(String pactFileAntPath) {
        this.pactFileAntPath = pactFileAntPath;
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }
    public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Pact publisher";
        }


    }
}
