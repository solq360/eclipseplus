package test.jmx.test1.model;

import javax.management.MXBean;

@MXBean
public interface IResult {	 
	public int getCode();
	public String[] getBody();
}
