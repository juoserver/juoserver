package net.sf.juoserver.api;

import net.sf.juoserver.protocol.GeneralInformation;

import java.util.List;

public interface GeneralInfoManager extends ContextRequired {
    List<Message> handle(GeneralInformation generalInformation);
}
