package net.sf.juoserver.protocol.generalinfo;

import net.sf.juoserver.api.Core;
import net.sf.juoserver.api.GeneralInfoManager;
import net.sf.juoserver.api.Message;
import net.sf.juoserver.protocol.GeneralInformation;
import net.sf.juoserver.protocol.SendSpeech;
import net.sf.juoserver.protocol.Subcommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralInfoManagerImpl implements GeneralInfoManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralInfoManagerImpl.class);
    private final Map<GeneralInformation.SubcommandType, SubCommandHandler<Subcommand<GeneralInformation, GeneralInformation.SubcommandType>>> handlers = new HashMap<>();
    private final SubCommandHandler<Subcommand<GeneralInformation, GeneralInformation.SubcommandType>> defaultSubcommand = subcommand -> {
        LOGGER.info("Unhandled Subcommand {}", subcommand);
        return Collections.emptyList();
    };

    @SuppressWarnings({"rawtypes", "unchecked"})
    public GeneralInfoManagerImpl(Core core) {
        handlers.put(GeneralInformation.SubcommandType.StatsLook, (SubCommandHandler) new StatsLookHandler(core));
        handlers.put(GeneralInformation.SubcommandType.MouseHover, (SubCommandHandler) new MouseHoverHandler());
        handlers.put(GeneralInformation.SubcommandType.ScreenSize, (SubCommandHandler) new ScreenSizeHandler());
        handlers.put(GeneralInformation.SubcommandType.ClientLanguage, (SubCommandHandler) new ClientLanguageHandler());
    }

    @Override
    public List<Message> handle(GeneralInformation generalInformation) {
        return handlers.getOrDefault(generalInformation.getSubCommand().getType(), defaultSubcommand).handle(generalInformation.getSubCommand());
    }

    interface SubCommandHandler<T extends Subcommand<GeneralInformation, GeneralInformation.SubcommandType>> {
        List<Message> handle(T subcommand);
    }

    static class MouseHoverHandler implements SubCommandHandler<GeneralInformation.MouseHover> {

        @Override
        public List<Message> handle(GeneralInformation.MouseHover subcommand) {
            // Nothing to do up to now
            return Collections.emptyList();
        }
    }

    static class ClientLanguageHandler implements SubCommandHandler<GeneralInformation.ClientLanguage> {
        @Override
        public List<Message> handle(GeneralInformation.ClientLanguage subcommand) {
            return Collections.emptyList();
        }
    }

    static class ScreenSizeHandler implements SubCommandHandler<GeneralInformation.ScreenSize> {
        @Override
        public List<Message> handle(GeneralInformation.ScreenSize subcommand) {
            return Collections.emptyList();
        }
    }

    static class StatsLookHandler implements SubCommandHandler<GeneralInformation.StatsLook> {

        private final Core core;

        public StatsLookHandler(Core core) {
            this.core = core;
        }

        @Override
        public List<Message> handle(GeneralInformation.StatsLook subcommand) {
            var serialId = subcommand.getSerialId();

            var item = core.findItemByID(serialId);
            if (item != null) {
                return List.of(new SendSpeech(item));
            }

            var mobile = core.findMobileByID(serialId);
            if (mobile != null) {
                return List.of(new SendSpeech(mobile));
            }
            return Collections.emptyList();
        }
    }
}
