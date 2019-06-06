package com.spinningnoodle.communitymanager.datastorage;

import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CustomVerificationReciever implements VerificationCodeReceiver {
    private static final String address = "172.31.31.68";
    private LocalServerReceiver receiver;

    public CustomVerificationReciever() throws UnknownHostException {
        this.receiver = new LocalServerReceiver.Builder()
            .setHost(InetAddress.getByName(address).getHostName())
            .setPort(0).build();
    }
    
    @Override
    public String getRedirectUri() throws IOException {
        return "http://ec2-52-14-112-68.us-east-2.compute.amazonaws.com:8080";
    }

    @Override
    public String waitForCode() throws IOException {
        return receiver.waitForCode();
    }

    @Override
    public void stop() throws IOException {
        receiver.stop();
    }
}
