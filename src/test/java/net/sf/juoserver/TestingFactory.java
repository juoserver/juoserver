package net.sf.juoserver;

import net.sf.juoserver.api.*;
import net.sf.juoserver.model.UOMobile;

public class TestingFactory {
	public static Mobile createTestMobile(int serialId, String name) {
		return new UOMobile(serialId, name, 0, 0, false, StatusFlag.AOS, SexRace.FemaleElf, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, RaceFlag.Elf);
	}

	public static Configuration createTestConfiguration() {
		return new TestConfiguration();
	}
}

class TestConfiguration implements Configuration {
	public TestConfiguration() {
		super();
	}

	@Override
	public String getUOPath() {
		return null;
	}

	@Override
	public String getSkillsIdxPath() {
		return null;
	}

	@Override
	public int getServerPort() {
		return 0;
	}

	@Override
	public String getServerName() {
		return null;
	}

	@Override
	public String getServerHost() {
		return null;
	}

	@Override
	public String getMulPath() {
		return null;
	}

	@Override
	public boolean isPacketLoggingEnabled() {
		return false;
	}

	@Override
	public String getCommandActivationCharacter() {
		return null;
	}
}