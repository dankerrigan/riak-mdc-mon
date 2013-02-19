package com.basho.proserv.riakmdcmon.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.basho.proserv.riakmdcmon.Configuration;

public class RiakConnectionFactory {
	private final Configuration config;
	public RiakConnectionFactory(Configuration config) {
		this.config = config;
	}
	
	public RiakConnection getRiakConnection() {
		RiakConnection connection = new RiakConnection();
		List<String> hostList = config.getHosts();
		Set<String> hosts = new HashSet<String>();
		hosts.addAll(hostList);
		if (config.getUseProtocolBuffers()) {
			if (hosts.size() > 1) {
				connection.connectPBCluster(hosts, config.getPort());
			} else {
				connection.connectPBClient(hostList.get(0), config.getPort());
			}
		} else {
			if (hosts.size() > 1) {
				connection.connectHTTPCluster(hosts, config.getPort());
			} else {
				connection.connectHTTPClient(hostList.get(0), config.getPort());
			}
		}
		return connection;
	}
}
