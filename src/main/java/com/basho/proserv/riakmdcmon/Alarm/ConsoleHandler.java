package com.basho.proserv.riakmdcmon.Alarm;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.basho.proserv.riakmdcmon.Configuration;

public class ConsoleHandler implements IAlarmHandler {
	private final Configuration config;
	
	public ConsoleHandler(Configuration config) {
		ConsoleAppender console = new ConsoleAppender(); //create appender
		//configure the appender
		console.setName("ConsoleLogger");
		String PATTERN = "%d{yyyy-MM-dd'T'HH:mm:ss} %m%n";
		console.setLayout(new PatternLayout(PATTERN)); 
		console.setThreshold(Level.INFO);
		console.activateOptions();
		//add appender to any Logger (here is root)
		Logger.getLogger(this.getClass()).addAppender(console);
		
		this.config = config;
	}
	
	
	@Override
	public void handleAlarm(Alarm alarm) {
		String outIn = alarm.intol() ? "in" : "out of";
		String message = "Data Center %s %s tolerance. Latency/Threshold: %d/%d milliseconds";
		
		
		Logger.getLogger(this.getClass()).info(
			String.format(message, 
						alarm.remoteDataCenterId(), 
						outIn,
						alarm.localTimestamp()-alarm.remoteDataCenterTimestamp(),
						config.getErrorThreshold())
			);
	}

}
