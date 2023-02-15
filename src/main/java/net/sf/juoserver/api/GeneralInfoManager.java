package net.sf.juoserver.api;

import net.sf.juoserver.api.Message;
import net.sf.juoserver.protocol.GeneralInformation;

import java.util.List;

public interface GeneralInfoManager {
    List<Message> handle(GeneralInformation generalInformation);
}
