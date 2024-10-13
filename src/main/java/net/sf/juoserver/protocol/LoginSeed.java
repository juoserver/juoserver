package net.sf.juoserver.protocol;

import org.apache.commons.lang3.ArrayUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HexFormat;
import java.util.List;

/**
 * First message ever sent by a client.
 * It's made up of 4 bytes, usually consisting
 * of the client's IP address.
 */
public class LoginSeed extends AbstractMessage {
	private static final long serialVersionUID = 1L;
	public static final int CODE = NO_CODE;
	private InetAddress address;
	private int clientMajorVersion;
	private int clientMinorVersion;
	private int clientRevisionVersion;
	private int clientPrototypeVersion;

	private LoginSeed() {
		super(CODE, 4);
	}

	public LoginSeed(byte[] contents) throws UnknownHostException {
		super(CODE, LoginSeed.getLength(contents));
		if (getLength() == 21) {
			address = InetAddress.getByAddress(ArrayUtils.subarray(contents, 1, 5));
			var buffer = wrapContents(5, contents);
			clientMajorVersion = buffer.getInt();
			clientMinorVersion = buffer.getInt();
			clientRevisionVersion = buffer.getInt();
			clientPrototypeVersion = buffer.getInt();
		} else {
			address = InetAddress.getByAddress(ArrayUtils.subarray(contents, 0, 4));
		}
	}

	private static int getLength(byte[] contents) {
		var code = HexFormat.of().formatHex(contents, 0,1);
		return "EF".equalsIgnoreCase(code) ? 21 : 4;
	}

	public LoginSeed(InetAddress address) {
		this();
		this.address = address;
	}

	public InetAddress getAddress() {
		return address;
	}

	public int getClientMajorVersion() {
		return clientMajorVersion;
	}

	public int getClientMinorVersion() {
		return clientMinorVersion;
	}

	public int getClientRevisionVersion() {
		return clientRevisionVersion;
	}

	public int getClientPrototypeVersion() {
		return clientPrototypeVersion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginSeed other = (LoginSeed) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (getLength() == 21) {
			return "LoginSeed{" +
					"address=" + address +
					", clientMajorVersion=" + clientMajorVersion +
					", clientMinorVersion=" + clientMinorVersion +
					", clientRevisionVersion=" + clientRevisionVersion +
					", clientPrototypeVersion=" + clientPrototypeVersion +
					'}';
		}
		return "LoginSeed{" +
				"address=" + address +
				'}';
	}
}
