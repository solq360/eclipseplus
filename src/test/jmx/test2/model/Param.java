package test.jmx.test2.model;

import java.beans.ConstructorProperties;

public class Param implements IParam {
	private int par1;
	private String par2;

	@ConstructorProperties({ "par1", "par2" })
	public Param(int par1, String par2) {
		this.par1 = par1;
		this.par2 = par2;
	}

	public int getPar1() {
		return par1;
	}

	public String getPar2() {
		return par2;
	}

}
