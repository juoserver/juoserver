package net.sf.juoserver.configuration;

import net.sf.juoserver.api.Configuration;
import net.sf.juoserver.protocol.combat.CombatSystemImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileBasedConfiguration implements Configuration {

	private static final Logger log = LoggerFactory.getLogger(PropertyFileBasedConfiguration.class);
	public static final String PROP_FILE_NAME = "juoserver.properties";
	private static final String PROP_FILE_DEFAULT_PATH =
		System.getProperty("user.home") + File.separator + ".juoserver" + 
		File.separator + PROP_FILE_NAME;
	private static final String PROP_FILE_PATH = "juoserver.propFilePath";

	private static final String DEFAULT_SERVER_NAME = "JUOServer";
	private static final String DEFAULT_SERVER_PORT = "7775";
	private static final String DEFAULT_SERVER_HOST = "localhost";
	private static final String DEFAULT_PACKET_LOGGING_ENABLED = "false";
	private static final String DEFAULT_COMMAND_ACTIVATION_CHARACTER = ".";

	private String propFilePath;
	private Properties props;

	public PropertyFileBasedConfiguration() {
		this.propFilePath = "juoserver.properties";//System.getProperty(PROP_FILE_PATH);
		// Classic filename fall-back
		if (propFilePath == null) {
			log.info("System property '" +
					PROP_FILE_PATH + "' is not set, using default path '" +
					PROP_FILE_DEFAULT_PATH + "'.");
			propFilePath = PROP_FILE_DEFAULT_PATH;
		}
		
		props = new Properties();
		try {
			FileInputStream propFile = PropertyFileBasedConfiguration.findPropertiesFile(propFilePath);
			if (propFile == null) {
				throw new IllegalStateException("Could not find file " + propFilePath);
			}
			props.load(propFile);
		} catch (FileNotFoundException e) {
			throw new IllegalStateException("Could not find file " + propFilePath);
		} catch (IOException e) {
			throw new IllegalStateException("Could not read file " + propFilePath);
		}
	}

	@Override
	public String getSkillsIdxPath() {
		return getUOPath() + File.separator + props.getProperty("skills.idxFileName");
	}

	@Override
	public String getMulPath() {
		return getUOPath() + File.separator + props.getProperty("skills.mulFileName");
	}

	@Override
	public String getUOPath() {
		String uoPath = props.getProperty("uopath");
		if (uoPath == null || !new File(uoPath).exists()
				|| !new File(uoPath).isDirectory()) {
			throw new ConfigurationException("property 'uopath' not defined, "
					+ "or does path does not point to an existing directory.");
		}
		return uoPath;
	}

	@Override
	public int getServerPort() {
		return Integer.valueOf(getProperty("server.port", DEFAULT_SERVER_PORT));
	}

	@Override
	public String getServerName() {
		return getProperty("server.name", DEFAULT_SERVER_NAME);
	}

	@Override
	public String getServerHost() {
		return getProperty("server.host", DEFAULT_SERVER_HOST);
	}

	@Override
	public String getCommandActivationCharacter() {
		return getProperty("command.activation.character", DEFAULT_COMMAND_ACTIVATION_CHARACTER);
	}

	private String getProperty(String name, String defaultValue) {
		String value = props.getProperty(name);
		if (value == null) {
			log.debug("Using default value for property '" + name + "': " + defaultValue);
			return defaultValue;
		}
		return value;
	}

	public static FileInputStream findPropertiesFile(String propFilePath) {
		if (new File(propFilePath).exists()) {
			try {
				return new FileInputStream(propFilePath);
			} catch (FileNotFoundException e) {
				throw new IllegalStateException("File " + PROP_FILE_NAME + " exists but could not be found!", e);
			}
		}
		
		JOptionPane.showMessageDialog(
				null, "File " + PROP_FILE_NAME
				+ " not found, insert its full path - e.g., /juoserver/juoserver.properties",
				PROP_FILE_NAME + " not found!",
				JOptionPane.INFORMATION_MESSAGE);
		
		JFileChooser chooser = new JFileChooser();
		int status = chooser.showOpenDialog(null);
		if (JFileChooser.APPROVE_OPTION != status) {
			return null;
		}
	
		try {
			return new FileInputStream(chooser.getSelectedFile());
		} catch (FileNotFoundException e) {
			throw new IllegalStateException("Could not find file "
					+ propFilePath, e);
		}
	}

	@Override
	public boolean isPacketLoggingEnabled() {
		return Boolean.parseBoolean(getProperty("packet.logging", DEFAULT_PACKET_LOGGING_ENABLED));
	}

	@Override
	public StatsConfiguration getStats() {
		return new StatsConfiguration() {
			@Override
			public int getLifeLimit() {
				return 100;
			}
		};
	}

	@Override
	public CombatConfiguration getCombat() {
		return new CombatConfiguration() {
			@Override
			public int getDexAttackDivisorModifier() {
				return 5;
			}

			@Override
			public int getStrAttackDivisorModifier() {
				return 2;
			}

			@Override
			public int getDexDefenseDivisorModifier() {
				return 5;
			}

			@Override
			public int getStrDefenseDivisorModifier() {
				return 2;
			}
		};
	}
}