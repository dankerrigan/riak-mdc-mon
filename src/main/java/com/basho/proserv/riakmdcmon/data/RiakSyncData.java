package com.basho.proserv.riakmdcmon.data;

import java.io.IOException;

import com.basho.proserv.riakmdcmon.Configuration;
import com.basho.proserv.riakmdcmon.Alarm.AlarmHandlerFactory;
import com.basho.riak.client.IRiakObject;
import com.basho.riak.client.builders.RiakObjectBuilder;
import com.basho.riak.client.raw.RiakResponse;

public class RiakSyncData extends SyncData {
	private final Configuration config;
	private final RiakConnection connection;
	
	public RiakSyncData(Configuration config, RiakConnection connection, AlarmHandlerFactory handlerFactory) {
		super(handlerFactory);
		this.config = config;
		this.connection = connection;
	}
	
	public void writeOwnSyncData() throws IOException {
		Long time = System.currentTimeMillis();
		
		IRiakObject object = RiakObjectBuilder.newBuilder(config.getMdcStatsBucket(), 
				config.getDataCenterId()).withValue(time.toString()).build();
		
		this.connection.riakClient.store(object);
	}
	
	public void readRemoteDCSyncData() throws IOException {
		for (String remote : config.getRemoteDataCenterIds()) {
			RiakResponse response = this.connection.riakClient.fetch(config.getMdcStatsBucket(), remote);
			
			IRiakObject[] objects = response.getRiakObjects();
			if (objects.length > 0) {
				Long time = Long.parseLong(objects[0].getValueAsString());
				super.setDataCenterLatency(remote, time);
			} else {
				super.setDataCenterLatency(remote, 0l);
			}
		}
	}
	
	public void test(boolean intol) {
		long localtime = System.currentTimeMillis();
		for (String remote: config.getRemoteDataCenterIds()) {
			if (intol) {
				super.setDataCenterLatency(remote, localtime);
			} else {
				super.setDataCenterLatency(remote, localtime - (config.getErrorThreshold()+1));
			}
		}
	}
	
	public void close() {
		this.connection.close();
	}
}
