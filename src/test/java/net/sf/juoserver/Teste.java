package net.sf.juoserver;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HexFormat;

public class Teste {

    @Test
    public void test() throws UnknownHostException {
        byte[] data = HexFormat.of().parseHex("879EDA16");
        var address = InetAddress.getByAddress(data);

        System.out.println(address);
    }

}
