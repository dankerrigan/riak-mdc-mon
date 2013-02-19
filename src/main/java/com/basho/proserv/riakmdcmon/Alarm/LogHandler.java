package com.basho.proserv.riakmdcmon.Alarm;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.basho.proserv.riakmdcmon.Configuration;

public class LogHandler implements IAlarmHandler {
	private final Configuration config;
	public LogHandler(Configuration config) {
		this.config = config;
		
		FileAppender fa = new FileAppender();
		fa.setName("FileLogger");
		fa.setFile(config.getLogFilePath());
		fa.setLayout(new PatternLayout("%d{yyyy-MM-dd'T'HH:mm:ss}\t%m%n"));
		fa.setThreshold(Level.INFO);
		fa.setAppend(true);
		fa.activateOptions();

		//add appender to any Logger (here is root)
		Logger.getLogger(this.getClass()).addAppender(fa);
		
	}
	
	public void handleAlarm(Alarm alarm) {
		String outIn = alarm.intol() ? "INTOL" : "OUTTOL";
		String message = "%s\t%s\t%d\t%d";
		
		
		Logger.getLogger(this.getClass()).info(
			String.format(message, 
						alarm.remoteDataCenterId(), 
						outIn,
						alarm.localTimestamp()-alarm.remoteDataCenterTimestamp(),
						config.getErrorThreshold())
			);	
	}

}
