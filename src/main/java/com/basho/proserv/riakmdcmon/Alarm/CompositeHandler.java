package com.basho.proserv.riakmdcmon.Alarm;

import java.util.ArrayList;
import java.util.List;

public class CompositeHandler implements IAlarmHandler {

	private final List<IAlarmHandler> handlers = new ArrayList<IAlarmHandler>();
	
	public void addHandler(IAlarmHandler handler) {
		this.handlers.add(handler);
	}
	
	@Override
	public void handleAlarm(Alarm alarm) {
		for (IAlarmHandler handler : this.handlers) {
			handler.handleAlarm(alarm);
		}
		
	}

}
