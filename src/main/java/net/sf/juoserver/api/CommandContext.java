package net.sf.juoserver.api;

import net.sf.juoserver.api.Core;
import net.sf.juoserver.api.InterClientNetwork;
import net.sf.juoserver.api.PlayerSession;
import net.sf.juoserver.protocol.ClientVersion;
import net.sf.juoserver.protocol.ProtocolIoPort;

import java.util.List;

public class CommandContext {
    private final Core core;
    private final InterClientNetwork network;
    private final PlayerSession session;
    private final ProtocolIoPort protocolIoPort;
    private final List<String> arguments;

    public CommandContext(Core core, InterClientNetwork network, PlayerSession session, ProtocolIoPort protocolIoPort, List<String> arguments) {
        this.core = core;
        this.network = network;
        this.session = session;
        this.protocolIoPort = protocolIoPort;
        this.arguments = arguments;
    }

    public Core getCore() {
        return core;
    }

    public InterClientNetwork getNetwork() {
        return network;
    }

    public PlayerSession getSession() {
        return session;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public ProtocolIoPort getProtocolIoPort() {
        return protocolIoPort;
    }
}
