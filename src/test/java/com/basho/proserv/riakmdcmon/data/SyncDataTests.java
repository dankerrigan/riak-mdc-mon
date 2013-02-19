package com.basho.proserv.riakmdcmon.data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.basho.proserv.riakmdcmon.Configuration;
import com.basho.proserv.riakmdcmon.Alarm.Alarm;
import com.basho.proserv.riakmdcmon.Alarm.AlarmHandlerFactory;

public class SyncDataTests {

	@Test
	public void test() {
		Configuration config = new Configuration();
		config.setConsoleLoggingEnabled(true);
		
		AlarmHandlerFactory factory = new AlarmHandlerFactory(config);
		SyncData syncData = new FakeSyncData(factory);
		
		long threshold = 1000;
		
		String[] dataCenters = {"DC1", "DC2", "DC3"};
		
		long localtime = System.currentTimeMillis();
		// one intol, one outtol
		for (String dataCenter : dataCenters) {
			syncData.setDataCenterLatency(dataCenter, localtime-threshold);
			syncData.setDataCenterLatency(dataCenter, localtime - (threshold + 1));
		}
		
		List<Alarm> alarms = syncData.getOutOfToleranceDataCenters(threshold, new ArrayList<Alarm>());
		syncData.handleAlarms(alarms);
		
		assertTrue(alarms.size() == dataCenters.length);
	}

}
