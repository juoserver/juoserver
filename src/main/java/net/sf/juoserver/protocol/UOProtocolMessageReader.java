package net.sf.juoserver.protocol;

import net.sf.juoserver.api.Message;
import net.sf.juoserver.api.MessageDecoderProvider;
import net.sf.juoserver.api.MessageReader;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Default {@link MessageReader} implementation.
 */
public class UOProtocolMessageReader implements MessageReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(UOProtocolMessageReader.class);
	private MessageDecoderProvider messageDecoderProvider;
	
	/**
	 * Whether or not the {@link LoginSeed} - the first message that the client
	 * sends - has been received by this reader.
	 */
	boolean seedSent;
	
	/**
	 * Default constructor.
	 * <p/>
	 * Builds a new {@link UOProtocolMessageReader} that uses a {@link DefaultMessageDecodeProvider}.
	 * @throws BadDecodableException in case a {@link Message}
	 * was misconfigured
	 */
	public UOProtocolMessageReader() {
		this(new DefaultMessageDecodeProvider(), false);
	}
	
	/**
	 * Builds a new {@link UOProtocolMessageReader}.
	 * 
	 * @param messageDecoderProvider {@link MessageDecoderProvider} to be used to identify
	 * {@link Message} types from their first byte
	 * @param seedSent when <tt>true</tt>, instructs the newly built reader to proceed as if
	 * the {@link LoginSeed} message has already been received
	 */
	public UOProtocolMessageReader(MessageDecoderProvider messageDecoderProvider, boolean seedSent) {
		this.messageDecoderProvider = messageDecoderProvider;
		this.seedSent = seedSent;
	}
	
	@Override
	public List<Message> readMessages(byte[] contents) {
		List<Message> msgs = new ArrayList<Message>();
		Message msg;
		while ((msg = nextMessage(contents)) != null) {
			msgs.add( msg );
			contents = ArrayUtils.subarray(contents, msg.getLength(), contents.length);
		}
		return msgs;
	}
	
	private Message nextMessage(byte[] contents) {
		if (contents.length == 0) {
			return null;
		}
		if (!seedSent) {
			seedSent = true;
			try {
				return new LoginSeed(contents);
			} catch (UnknownHostException e) {
				throw new MessageReaderException(e);
			}
		}
		if (messageDecoderProvider.getDecoder(contents[0]) == null) {
			LOGGER.error("Unknown client message, code: "
					+ MessagesUtils.getCodeHexString(contents) + ", contents: ["
					+ MessagesUtils.getHexString(contents) + "]");
			return null;
		}
		return messageDecoderProvider.getDecoder(contents[0]).decode(contents);
	}
}
