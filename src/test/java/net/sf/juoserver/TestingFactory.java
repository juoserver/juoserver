package net.sf.juoserver;

import net.sf.juoserver.api.*;
import net.sf.juoserver.model.UOContainer;
import net.sf.juoserver.model.UOItem;
import net.sf.juoserver.model.UOMobile;
import net.sf.juoserver.protocol.AbstractMessage;

import java.util.Collections;
import java.util.Objects;

public class TestingFactory {
	public static Mobile createTestMobile(int serialId, String name) {
		return new UOMobile(serialId, name, 0, 0, false, StatusFlag.AOS, SexRace.FemaleElf, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, RaceFlag.Elf);
	}

	public static Configuration createTestConfiguration() {
		return new TestConfiguration();
	}

	public static Item createTestItem(int serialId, int modelId) {
		return new UOItem(serialId, modelId);
	}

	public static Container createTestContainer(int serialId, int modelId) {
		return new UOContainer(serialId, modelId, 0, "container", 1, Collections.emptyList(), Collections.emptyMap());
	}

	public static Message createTestMessage(int serialId, int code) {
		return new TestingMessage(code, 1 , serialId);
	}
}

class TestingMessage extends AbstractMessage {
	private final int internaSerialId;

	public TestingMessage(int code, int length, int internaSerialId) {
		super(code, length);
		this.internaSerialId = internaSerialId;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(internaSerialId);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TestingMessage) {
			return Objects.equals(internaSerialId, ((TestingMessage) obj).internaSerialId);
		}
		return false;
	}
}

class TestConfiguration implements Configuration {
	public TestConfiguration() {
		super();
	}

	@Override
	public FilesConfiguration getFiles() {
		return null;
	}

	@Override
	public String getSkillsIdxPath() {
		return null;
	}

	@Override
	public ServerConfiguration getServer() {
		return null;
	}

	@Override
	public String getMulPath() {
		return null;
	}

	@Override
	public StatsConfiguration getStats() {
		return null;
	}

	@Override
	public CombatConfiguration getCombat() {
		return null;
	}

	@Override
	public CommandConfiguration getCommand() {
		return null;
	}

	@Override
	public PacketConfiguration getPacket() {
		return null;
	}
}