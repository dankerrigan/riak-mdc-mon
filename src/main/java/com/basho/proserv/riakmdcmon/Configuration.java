package com.basho.proserv.riakmdcmon;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Configuration {
	public static String RIAK_HOST_COUNT_PROP = "riak.hosts.count";
	public static String RIAK_HOST_PROP_FMT = "riak.hosts.%d.hostName";
	public static String RIAK_PORT_PROP = "riak.port";
	public static String RIAK_USE_PB = "riak.useProtocolBuffers";
	public static String RIAK_STATS_BUCKET_PROP = "riak.mdcStatsBucket";
	public static String DATA_CENTER_ID_PROP = "dataCenter.id";
	public static String REMOTE_DATA_CENTER_COUNT_PROP = "remoteDataCenter.count";
	public static String REMOTE_DATA_CENTER_ID_PROP_FMT = "remoteDataCenter.%d.id";
	public static String ERROR_THRESHOLD_PROP = "remoteDataCenter.latencyThreshold";
	public static String ALARM_CONSOLE_HANDLER_ENABLE_PROP = "alarmHandler.console.enable";
	public static String ALARM_LOG_HANDLER_ENABLE_PROP = "alarmHandler.log.enable";
	public static String ALARM_LOG_HANDLER_FILENAME_PROP = "alarmHandler.log.filename";
//	public static String ALARM_EMAIL_HANDLER_PROP = "alarmHandler.email.enable";
	public static String ALARM_SNMP_HANDLER_ENABLE_PROP = "alarmHandler.snmp.enable";
	public static String ALARM_SNMP_HOST_PROP = "alarmHandler.snmp.hostName";
	public static String ALARM_SNMP_PORT_PROP = "alarmHandler.snmp.port";
	public static String ALARM_SNMP_COMMUNITY_PROP = "alarmHandler.snmp.community";
	
	private static final int ERROR_THRESHOLD = 60000;
	
	private List<String> hosts = new ArrayList<String>();
	private int port = 8098;
	private boolean useProtocolBuffers = false;
	private String mdcStatsBucket = null;
	private String dataCenterId = null;
	private List<String> remoteDataCenterIds = new ArrayList<String>();
	private int errorThreshold = ERROR_THRESHOLD; 
	
	private boolean enableConsole = false;
	
	private boolean enableLogging = false;
	private String logFilename = null;
	
	private boolean enableSNMPLogging = false;
	private String snmpHostname = null;
	private int snmpPort = 162;
	private String snmpCommunity = null;
	private String snmpOID = null;
	
	public Configuration() {
		
	}
	
	public void addHost(String host) {
		this.hosts.add(host);
	}
	public List<String> getHosts() {
		return this.hosts;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	public int getPort() {
		return this.port;
	}
	
	public void setUseProtocolBuffers(boolean useProtocolBuffers) {
		this.useProtocolBuffers = useProtocolBuffers;
	}
	public boolean getUseProtocolBuffers() {
		return this.useProtocolBuffers;
	}
	
	public void setMdcStatsBucket(String mdcStatsBucket) {
		this.mdcStatsBucket = mdcStatsBucket;
	}
	public String getMdcStatsBucket() {
		return this.mdcStatsBucket;
	}
	
	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}
	public String getDataCenterId() {
		return this.dataCenterId;
	}
	
	public void addRemoteDataCenterId(String remoteDataCenterId) {
		this.remoteDataCenterIds.add(remoteDataCenterId);
	}
	public List<String> getRemoteDataCenterIds() {
		return this.remoteDataCenterIds;
	}
	
	public void setErrorThreshold(int errorThreshold) {
		this.errorThreshold = errorThreshold;
	}
	public int getErrorThreshold() {
		return this.errorThreshold;
	}
	
	public void setConsoleLoggingEnabled(boolean enabled) {
		this.enableConsole = enabled;
	}
	public boolean getConsoleLoggingEnabled() {
		return this.enableConsole;
	}
	
	public void setFileLoggingEnabled(boolean enabled) {
		this.enableLogging = enabled;
	}
	public boolean getFileLoggingEnabled() {
		return this.enableLogging;
	}
	
	public void setLogFilePath(String path) {
		this.logFilename = path;
	}
	public String getLogFilePath() {
		return this.logFilename;
	}
	
	public void setSnmpAlarmEnabled(boolean enabled) {
		this.enableSNMPLogging = enabled;
	}
	public boolean getSnmpAlarmEnabled() {
		return this.enableSNMPLogging;
	}
	
	public void setSnmpHostname(String hostname) {
		this.snmpHostname = hostname;
	}
	public String getSnmpHostname() {
		return this.snmpHostname;
	}
	
	public void setSnmpPort(int port) {
		this.snmpPort = port;
	}
	public int getSnmpPort() {
		return this.snmpPort;
	}
	
	public void setSnmpCommunity(String community) {
		this.snmpCommunity = community;
	}
	public String getSnmpCommunity() {
		return this.snmpCommunity;
	}
	
	
	public static Configuration createConfigurationFromProperties(File propertiesFile) throws IOException {
		Configuration config = new Configuration();
		
		Properties properties = new Properties();
		properties.load(new FileInputStream(propertiesFile));
		
		int hostCount = Integer.parseInt(properties.getProperty(RIAK_HOST_COUNT_PROP));
		for (int i = 1; i <= hostCount; ++i) {
			String propStr = String.format(RIAK_HOST_PROP_FMT, i);
			config.addHost(properties.getProperty(propStr));
		}

		config.setPort(Integer.parseInt(properties.getProperty(RIAK_PORT_PROP)));
		config.setUseProtocolBuffers(properties.getProperty(RIAK_USE_PB).toLowerCase().compareTo("true") == 0);
		config.setMdcStatsBucket(properties.getProperty(RIAK_STATS_BUCKET_PROP));
		config.setDataCenterId(properties.getProperty(DATA_CENTER_ID_PROP));
		
		int dataCenterCount = Integer.parseInt(properties.getProperty(REMOTE_DATA_CENTER_COUNT_PROP));
		for (int i = 1; i <= dataCenterCount; ++i) {
			String propStr = String.format(REMOTE_DATA_CENTER_ID_PROP_FMT, i);
			config.addRemoteDataCenterId(properties.getProperty(propStr));
		}
		
		config.setErrorThreshold(Integer.parseInt(properties.getProperty(ERROR_THRESHOLD_PROP)));
		
		config.setConsoleLoggingEnabled(
				properties.getProperty(ALARM_CONSOLE_HANDLER_ENABLE_PROP).toLowerCase().compareTo("true") == 0);
		
		config.setFileLoggingEnabled(
				properties.getProperty(ALARM_LOG_HANDLER_ENABLE_PROP).toLowerCase().compareTo("true") == 0);
		config.setLogFilePath(properties.getProperty(ALARM_LOG_HANDLER_FILENAME_PROP));
		
		config.setSnmpAlarmEnabled(
				properties.getProperty(ALARM_SNMP_HANDLER_ENABLE_PROP).toLowerCase().compareTo("true") == 0);
		config.setSnmpHostname(properties.getProperty(ALARM_SNMP_HOST_PROP));
		config.setSnmpPort(Integer.parseInt(properties.getProperty(ALARM_SNMP_PORT_PROP)));
		config.setSnmpCommunity(properties.getProperty(ALARM_SNMP_COMMUNITY_PROP));
		
		return config;
	}
}
