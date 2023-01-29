package net.sf.juoserver.files.mondainslegacy;

import net.sf.juoserver.api.IdxFileEntry;
import net.sf.juoserver.files.IdxFileEntryEncoder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Encoder for the skills index file (namely, <tt>skills.idx</tt>).
 */
class SkillsIdxFileEntryEncoder implements IdxFileEntryEncoder {
	@Override
	public IdxFileEntry encode(byte[] contents) {
		ByteBuffer bb = ByteBuffer.wrap(contents).order(ByteOrder.LITTLE_ENDIAN);
		int start = bb.getInt();
		int length = bb.getInt();
		return new MondainsLegacyIdxFileEntry(start, length);
	}
}
