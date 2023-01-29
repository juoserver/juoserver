package net.sf.juoserver.files.mondainslegacy;

import net.sf.juoserver.files.BaseIdxFileReader;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Skills index file reader.
 */
class SkillsIdxFileReader extends BaseIdxFileReader {
	public static final int IDX_BLOCK_SIZE = 12;

	public SkillsIdxFileReader(File file) throws FileNotFoundException {
		super(file, IDX_BLOCK_SIZE, new SkillsIdxFileEntryEncoder());
	}
}
