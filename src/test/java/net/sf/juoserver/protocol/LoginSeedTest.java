package net.sf.juoserver.protocol;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginSeedTest {
    @Test
    public void shouldValidadeVersionsWhenCodeSent() throws IOException {
        var inetAddress = InetAddress.getLocalHost();
        var clientMajorVersion = 7;
        var clientMinorVersion = 1;
        var clientRevisionVersion = 2;
        var clientPrototypeVersion = 3;

        var baos = new ByteArrayOutputStream();
        var data = new DataOutputStream(baos);

        data.write((byte) 0xEF); // code
        data.write(inetAddress.getAddress()); // IP address
        data.writeInt(clientMajorVersion); //clientMajorVersion
        data.writeInt(clientMinorVersion); //clientMinorVersion
        data.writeInt(clientRevisionVersion); //clientRevisionVersion
        data.writeInt(clientPrototypeVersion); //clientPrototypeVersion

        var seed = new LoginSeed(baos.toByteArray());

        assertEquals(inetAddress, seed.getAddress());
        assertEquals(clientMajorVersion, seed.getClientMajorVersion());
        assertEquals(clientMinorVersion, seed.getClientMinorVersion());
        assertEquals(clientRevisionVersion, seed.getClientRevisionVersion());
        assertEquals(clientPrototypeVersion, seed.getClientPrototypeVersion());
    }

    @Test
    public void shouldValidadeAddressWhenNoCodeSent() throws IOException {
        var inetAddress = InetAddress.getLocalHost();

        var baos = new ByteArrayOutputStream();
        var data = new DataOutputStream(baos);

        data.write(inetAddress.getAddress()); // IP address

        var seed = new LoginSeed(baos.toByteArray());

        assertEquals(inetAddress, seed.getAddress());
    }
}