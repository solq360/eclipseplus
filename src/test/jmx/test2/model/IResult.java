package test.jmx.test2.model;

import javax.management.MXBean;

@MXBean
public interface IResult {	 
	public int getCode();
	public String[] getBody();
}
