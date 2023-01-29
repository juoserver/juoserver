package net.sf.juoserver.protocol;

import net.sf.juoserver.api.*;
import net.sf.juoserver.model.ServerInfo;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AuthenticationController extends AbstractProtocolController {
	private final ProtocolIoPort clientHandler;
	private final Configuration configuration;
	private final LoginManager loginManager;
	
	private Account account;
	
	public AuthenticationController(ProtocolIoPort clientHandler, Configuration configuration, LoginManager loginManager) {
		this.clientHandler = clientHandler;
		this.configuration = configuration;
		this.loginManager = loginManager;
	}
	
	public void handle(LoginSeed seed) {
	}
	
	public Message handle(LoginRequest request) {
		try {
			account = loginManager.login(request.getUser(), request.getPassword());
		} catch (NoSuchCharacterException e) {
			clientHandler.deactivate();
			return LoginReject.loginRejectDueToNoSuchCharacter();
		} catch (WrongPasswordException e) {
			clientHandler.deactivate();
			return LoginReject.loginRejectDueToBadPassword();
		}
		
		try {
			return new ServerList(new ServerInfo(configuration.getServer().getName(), InetAddress.getByName(configuration.getServer().getHost())));
		} catch (UnknownHostException e) {
			throw new ProtocolException(e);
		}
	}
	
	public Message handle(SelectServer request) {
		try {
			int key = loginManager.generateAuthenticationKey(account);
			return new ServerConnect(InetAddress.getByName(configuration.getServer().getHost()), configuration.getServer().getPort(), key);
		} catch (UnknownHostException e) {
			throw new ProtocolException(e);
		}
	}
}
