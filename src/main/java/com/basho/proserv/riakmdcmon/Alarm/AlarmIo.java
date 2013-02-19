package com.basho.proserv.riakmdcmon.Alarm;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class AlarmIo {
	private final File file; 
	public AlarmIo(File file)  {
		this.file = file;
	}
	
	public void writeAlarms(List<Alarm> alarms) throws IOException {
		XStream xstream = new XStream();
		FileWriter writer = new FileWriter(this.file);
		
		xstream.toXML(alarms, writer);
		
		writer.flush();
		writer.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Alarm> readAlarms() throws IOException {
		XStream xstream = new XStream();
		FileReader reader = new FileReader(this.file);
		
		List<Alarm> alarms = (List<Alarm>)xstream.fromXML(reader);
		
		reader.close();
		
		return alarms;
	}
}

