package com.basho.proserv.riakmdcmon.data;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.basho.proserv.riakmdcmon.Configuration;
import com.basho.proserv.riakmdcmon.Alarm.Alarm;
import com.basho.proserv.riakmdcmon.Alarm.SNMPHandler;

public class SNMPAlarmHandlerTests {

	@Test
	public void test() throws IOException {
		Configuration config = new Configuration();
		config.setDataCenterId("US_East");
		config.setSnmpAlarmEnabled(true);
		config.setSnmpHostname("127.0.0.1");
		config.setSnmpPort(162);
		config.setSnmpCommunity("public");
		config.setErrorThreshold(999);
//		config.setSnmpOID("rdbmsStateChange");
//		config.setSnmpOID("1.3.6.1.2.1.39.2.1");
		
//		config.setSnmpOID("1.3.6.1.2.1.1.1");
//		config.setSnmpOID("SNMPv2::sysDescr");
		
		SNMPHandler snmp = new SNMPHandler(config);
		
		Alarm alarm = new Alarm("TestMDC", System.currentTimeMillis(), System.currentTimeMillis()-1000, false);
		snmp.sendSNMPMessage(alarm, SNMPHandler.SNMP_VERSION.V1);
	}

}
