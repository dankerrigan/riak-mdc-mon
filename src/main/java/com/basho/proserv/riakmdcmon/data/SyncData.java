package com.basho.proserv.riakmdcmon.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.basho.proserv.riakmdcmon.Alarm.Alarm;
import com.basho.proserv.riakmdcmon.Alarm.AlarmHandlerFactory;
import com.basho.proserv.riakmdcmon.Alarm.IAlarmHandler;

public abstract class SyncData {
	protected Map<String, Long> dataCenterLatencies = new HashMap<String, Long>();
	
	private final IAlarmHandler alarmHandler;
	
	public SyncData(AlarmHandlerFactory handlerFactory) {
		this.alarmHandler = handlerFactory.create();
	}
	
	public void setDataCenterLatency(String dataCenterId, Long time) {
		this.dataCenterLatencies.put(dataCenterId, time);
	}
	
	
	public List<Alarm> getOutOfToleranceDataCenters(long threshold, 
			List<Alarm> previousAlarms) {
		List<Alarm> alarms = new ArrayList<Alarm>();
		HashMap<String, Alarm> previous = mapAlarms(previousAlarms);
		
		long localTime = System.currentTimeMillis();
		
		for (String key : this.dataCenterLatencies.keySet()) {
			long dcTime = this.dataCenterLatencies.get(key); 
			if (localTime - dcTime > threshold) {
				alarms.add(new Alarm(key, dcTime, localTime, false));
			} else if (previous.containsKey(key)) {
				alarms.add(new Alarm(key, dcTime, localTime, true));
			}
		}
		
		return alarms;
	}
	
	public void handleAlarms(List<Alarm> alarms) {
		for (Alarm alarm : alarms) {
			this.alarmHandler.handleAlarm(alarm);
		}
	}
	
	public abstract void writeOwnSyncData() throws IOException;
	public abstract void readRemoteDCSyncData() throws IOException;
	
	private HashMap<String, Alarm> mapAlarms(List<Alarm> alarms) {
		HashMap<String, Alarm> map = new HashMap<String, Alarm>();
		
		for (Alarm alarm : alarms) {
			map.put(alarm.remoteDataCenterId(), alarm);
		}
		
		return map;
	}
}
