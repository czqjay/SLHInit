package com.sunit.global;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunit.global.base.Global;

@Service
public class SysSchedule  {  
	

	@Autowired
	private Global global;

	static Logger logger = Logger.getLogger(SysSchedule.class);
	

}
