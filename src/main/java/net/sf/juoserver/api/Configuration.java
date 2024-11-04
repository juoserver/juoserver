package net.sf.juoserver.api;

/**
 * Interface representing an object capable of providing a configuration to the
 * server.
 */
public interface Configuration {
	String getSkillsIdxPath();
	String getMulPath();
	ClientConfiguration getClient();
	StatsConfiguration getStats();
	CombatConfiguration getCombat();
	ServerConfiguration getServer();
	FilesConfiguration getFiles();
	CommandConfiguration getCommand();
	PacketConfiguration getPacket();

	interface ClientConfiguration {
		int getLos();
	}

	interface FilesConfiguration {
		String getMulPath();
	}

	interface ServerConfiguration {
		int getPort();
		String getName();
		String getHost();
	}

	interface StatsConfiguration {
		int getMaxHitPoints();
		int getMaxStamina();
		int getMaxMana();
	}

	interface CombatConfiguration {
		int getDexAttackDivisorModifier();
		int getStrAttackDivisorModifier();

		int getDexDefenseDivisorModifier();
		int getStrDefenseDivisorModifier();
	}

	interface CommandConfiguration {
		String getActivationChar();
	}

	interface PacketConfiguration {
		boolean isLogging();
	}
}
