package net.sf.juoserver;

import net.sf.juoserver.api.*;
import net.sf.juoserver.model.*;
import net.sf.juoserver.model.ai.WalkScript;
import net.sf.juoserver.protocol.AbstractMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TestingFactory {
	public static Mobile createTestMobile(int serialId, String name) {
		return new UOMobile(serialId, name, 0, 0, false, StatusFlag.AOS, SexRace.FemaleElf, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, RaceFlag.Elf, new PointInSpace(6100, 1586,5));
	}

	public static Mobile createTestMobile(int serialId, String name, Point3D position) {
		return new UOMobile(serialId, name, 0, 0, false, StatusFlag.AOS, SexRace.FemaleElf, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, RaceFlag.Elf, position);
	}

	public static NpcMobile createTestNpcMobile(int serialId) {
		return new UONpcMobile(serialId, "Balrog", new PointInSpace(1,1,1), new WalkScript());
	}

	public static NpcMobile createTestNpcMobile(int serialId, AIScript aiScript) {
		return new UONpcMobile(serialId, "Balrog", new PointInSpace(1,1,1), aiScript);
	}

	public static Configuration createTestConfiguration() {
		return new TestConfiguration();
	}

	public static Item createTestItem(int serialId, int modelId) {
		return new UOItem(serialId, modelId);
	}

	public static Container createTestContainer(int serialId, int modelId) {
		return new UOContainer(serialId, modelId, 0, "container", 1, new ArrayList<>(), new HashMap<>());
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

	@Override
	public ClientConfiguration getClient() {
		return null;
	}
}