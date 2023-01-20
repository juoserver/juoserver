package net.sf.juoserver.protocol;

import net.sf.juoserver.api.Message;
import net.sf.juoserver.api.MessageDecoder;
import net.sf.juoserver.api.MessageDecoderProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class DefaultMessageDecodeProvider implements MessageDecoderProvider {

    private final Map<Byte, MessageDecoder> decoders = new HashMap<>();

    public DefaultMessageDecodeProvider() {
        this.init();
    }

    private void init() {
        registerDecoder(RequestHelp.CODE, RequestHelp.class);
        registerDecoder(MovementAck.CODE, MovementAck.class);
        registerDecoder(WearItem.CODE, WearItem.class);
        registerDecoder(CharacterSelect.CODE, CharacterSelect.class);
        registerDecoder(PickUpItem.CODE, PickUpItem.class);
        registerDecoder(ClientVersion.CODE, ClientVersion.class);
        registerDecoder(DoubleClick.CODE, DoubleClick.class);
        registerDecoder(PingPong.CODE, PingPong.class);
        registerDecoder(LoginRequest.CODE, LoginRequest.class);
        registerDecoder(GeneralInformation.CODE, GeneralInformation.class);
        registerDecoder(GenericAOSCommands.CODE, GenericAOSCommands.class);
        registerDecoder(GetPlayerStatus.CODE, GetPlayerStatus.class);
        registerDecoder(LookRequest.CODE, LookRequest.class);
        registerDecoder(MegaClilocRequest.CODE, MegaClilocRequest.class);
        registerDecoder(MoveRequest.CODE, MoveRequest.class);
        registerDecoder(SelectServer.CODE, SelectServer.class);
        registerDecoder(ServerLoginRequest.CODE, ServerLoginRequest.class);
        registerDecoder(SkillLock.CODE, SkillLock.class);
        registerDecoder(SpyOnClient.CODE, SpyOnClient.class);
        registerDecoder(UnicodeSpeechRequest.CODE, UnicodeSpeechRequest.class);
        registerDecoder(WarMode.CODE, WarMode.class);
        registerDecoder(DropItem.CODE, DropItem.class);
        registerDecoder(AttackRequest.CODE, AttackRequest.class);
    }

    @Override
    public MessageDecoder getDecoder(byte firstByte) {
        return decoders.get(firstByte);
    }

    private void registerDecoder(int code, Class<? extends Message> clazz) {
        decoders.put((byte) (code & 0xFF), getMessageDecoder(clazz));
    }

    private MessageDecoder getMessageDecoder(Class<?> clazz) {
        final Constructor<?> constructor;
        try {
            constructor = clazz.getConstructor(byte[].class);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
        return contents -> {
            try {
                return (Message) constructor.newInstance(contents);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new MessageReaderException( getMessageDetails(contents), e );
            }
        };
    }

    /**
     * Returns a textual representation of the given message's
     * details.
     *
     * @param contents the message contents
     * @return a textual representation of the given message's
     * details
     */
    private static String getMessageDetails(byte[] contents) {
        return "Code: " + MessagesUtils.getCodeHexString(contents) + ", contents: ["
                + MessagesUtils.getHexString(contents) + "]";
    }
}
