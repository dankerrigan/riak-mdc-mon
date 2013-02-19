package com.basho.proserv.riakmdcmon.Alarm;

public class Alarm {
	private final String remoteDataCenterId;
	private final long remoteDataCenterTimestamp;
	private final long localTimestamp;
	private final boolean intol;
	
	public Alarm(String remoteDataCenterId,
				  long remoteDataCenterTimestamp,
				  long localTimestamp,
				  boolean intol) {
		this.remoteDataCenterId = remoteDataCenterId;
		this.remoteDataCenterTimestamp = remoteDataCenterTimestamp;
		this.localTimestamp = localTimestamp;
		this.intol = intol;
	}
	
	public String remoteDataCenterId() {
		return this.remoteDataCenterId;
	}
	
	public long remoteDataCenterTimestamp() {
		return this.remoteDataCenterTimestamp;
	}
	
	public long localTimestamp() {
		return this.localTimestamp;
	}
	
	public boolean intol() {
		return this.intol;
	}
}
