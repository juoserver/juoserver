package net.sf.juoserver.api;

public interface NpcMobile extends Mobile {

    AIScript getAIScript();

    int getTemplateId();
}
