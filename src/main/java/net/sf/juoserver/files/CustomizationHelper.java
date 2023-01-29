package net.sf.juoserver.files;

import net.sf.juoserver.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * This helper class contains methods useful to customizing
 * your own UO server, for example, it retrieves the skills
 * IDs via method {@link #getSkillIdByName(String)}.
 */
public class CustomizationHelper {
	private Map<String, Integer> skillIdsByName = new HashMap<String, Integer>();
	private IdxFileReader skillsIdx;
	private SkillsMulFileReader skillsMul;
	
	public CustomizationHelper(Configuration configuration, FileReadersFactory fileReadersFactory) throws FileNotFoundException {
		skillsIdx = fileReadersFactory.createSkillsIdxFileReader(new File(configuration.getSkillsIdxPath()));
		skillsMul = fileReadersFactory.createSkillsMulFileReader(new File(configuration.getMulPath()), skillsIdx);
		int i = 1;
		for (SkillsFileEntry entry : skillsMul.getAllEntries()) {
			skillIdsByName.put(entry.getSkillName(), i++);
		}
	}

	public int getSkillIdByName(String name) {
		return skillIdsByName.get(name);
	}
}
