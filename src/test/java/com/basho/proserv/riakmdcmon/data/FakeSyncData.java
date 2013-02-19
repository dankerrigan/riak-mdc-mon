package com.basho.proserv.riakmdcmon.data;

import java.io.IOException;

import com.basho.proserv.riakmdcmon.Alarm.AlarmHandlerFactory;

public class FakeSyncData extends SyncData {

	public FakeSyncData(AlarmHandlerFactory handlerFactory) {
		super(handlerFactory);
	}

	@Override
	public void writeOwnSyncData() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readRemoteDCSyncData() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
