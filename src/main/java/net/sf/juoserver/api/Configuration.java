package net.sf.juoserver.api;

/**
 * Interface representing an object capable of providing a configuration to the
 * server.
 */
public interface Configuration {
	String getSkillsIdxPath();
	String getMulPath();
	String getUOPath();
	int getServerPort();
	String getServerName();
	String getServerHost();
	boolean isPacketLoggingEnabled();
	String getCommandActivationCharacter();
	StatsConfiguration getStats();
	CombatConfiguration getCombat();

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
