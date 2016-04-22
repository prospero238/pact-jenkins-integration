package org.jenkinsci.plugins.pact;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class PactFile {
    public PactFile(String consumer, String provider) {
        this.consumer = consumer;
        this.provider = provider;
    }

    public static PactFile readPactFile(String pact) {

        JsonElement jelement = new JsonParser().parse(pact);

        String provider = jelement.getAsJsonObject().get("provider").getAsJsonObject().get("name").getAsString();
        String consumer = jelement.getAsJsonObject().get("consumer").getAsJsonObject().get("name").getAsString();
        return new PactFile( consumer, provider);
    }

    private String consumer;
    private String provider;

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
