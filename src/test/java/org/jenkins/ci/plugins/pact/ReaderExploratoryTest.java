package org.jenkins.ci.plugins.pact;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jenkinsci.plugins.pact.PactFile;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReaderExploratoryTest {

    private static final Logger LOG = LoggerFactory.getLogger(ReaderExploratoryTest.class);


    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    protected File pactFile;
    protected String pactContents;

    @Before
    public void setup() throws IOException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/example-pact.json");
        temporaryFolder.create();
        pactFile = temporaryFolder.newFile("pact.json");

        pactContents = IOUtils.toString(resourceAsStream);
        FileUtils.write(pactFile, pactContents);

    }

    @Test
    public void parse_pact_From_string() {
        PactFile pactFile = PactFile.readPactFile(pactContents);

    }
}
