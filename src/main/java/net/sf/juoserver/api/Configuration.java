package net.sf.juoserver.api;

/**
 * Interface representing an object capable of providing a configuration to the
 * server.
 */
public interface Configuration {
	String getSkillsIdxPath();
	String getMulPath();
	boolean isPacketLoggingEnabled();
	String getCommandActivationCharacter();
	StatsConfiguration getStats();
	CombatConfiguration getCombat();
	ServerConfiguration getServer();
	FilesConfiguration getFiles();

	interface FilesConfiguration {
		String getMulPath();
	}

	interface ServerConfiguration {
		int getPort();
		String getName();
		String getHost();
	}

	interface StatsConfiguration {
		int getLifeLimit();
	}

	interface CombatConfiguration {
		int getDexAttackDivisorModifier();
		int getStrAttackDivisorModifier();

		int getDexDefenseDivisorModifier();
		int getStrDefenseDivisorModifier();
	}
}
