package com.basho.proserv.riakmdcmon;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import junit.framework.TestCase;

public class ConfigurationTests extends TestCase {
	
	@Test
	public void testCreateConfigurationFromProperties() throws Exception {
		TemporaryFolder tempFolder = new TemporaryFolder();
		
		File propFile = tempFolder.newFile();
		
		String[] hosts = {"host1", "host2", "host3"};
		Integer hostCount = hosts.length;
		Integer port = 8098;
		boolean useProtocolBuffers = false;
		String bucket = "mdcBucket";
		String dataCenterId = "RiakEmporium";
		Integer count = 10;
		
		List<String> remoteDataCenterIds = new ArrayList<String>();
		for (int i = 1; i <= count; ++i) {
			remoteDataCenterIds.add(String.format(Configuration.REMOTE_DATA_CENTER_ID_PROP_FMT, i));
		}
		Integer threshold = 10001;
		
		Properties props = new Properties();
		props.setProperty(Configuration.RIAK_HOST_COUNT_PROP, hostCount.toString());
		for (int i = 0; i < hostCount; ++i) {
			props.setProperty(String.format(Configuration.RIAK_HOST_PROP_FMT, i+1), hosts[i]);
		}

		props.setProperty(Configuration.RIAK_PORT_PROP, port.toString());
		props.setProperty(Configuration.RIAK_USE_PB, useProtocolBuffers ? "true" : "false");
		props.setProperty(Configuration.RIAK_STATS_BUCKET_PROP, bucket);
		props.setProperty(Configuration.DATA_CENTER_ID_PROP, dataCenterId);
		props.setProperty(Configuration.REMOTE_DATA_CENTER_COUNT_PROP, count.toString());
		for (String remote : remoteDataCenterIds) {
			props.setProperty(remote, remote);
		}
		props.setProperty(Configuration.ERROR_THRESHOLD_PROP, threshold.toString());
		
		FileOutputStream os = new FileOutputStream(propFile);
		props.store(os, "none");
		os.flush();
		os.close();
		
		Configuration config = Configuration.createConfigurationFromProperties(new File(propFile.getAbsolutePath()));
		List<String> configHosts = config.getHosts(); 
		for (int i = 0; i < hostCount; ++i) {
			assertTrue(configHosts.get(i).compareTo(hosts[i]) == 0);
		}
//		assertTrue(config.getHost().compareTo(host) == 0);
		assertTrue(config.getPort() == port);
		assertTrue(config.getUseProtocolBuffers() == useProtocolBuffers);
		assertTrue(config.getMdcStatsBucket().compareTo(bucket) == 0);
		assertTrue(config.getDataCenterId().compareTo(dataCenterId) == 0);
		assertTrue(config.getRemoteDataCenterIds().size() == count);
		int i = 1;
		for (String remote : config.getRemoteDataCenterIds()) {
			assert(remote.compareTo(String.format(Configuration.REMOTE_DATA_CENTER_ID_PROP_FMT, i)) == 0);
			++i;
		}
		assertTrue(config.getErrorThreshold() == threshold);
		
	}
}
