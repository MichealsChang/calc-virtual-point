package com.iot.common.opentsdb.tsdb;

public class TSParser {
	public static final String TM_YEAR 		= "y";
	public static final String TM_MON 		= "n";
	public static final String TM_WEEK 		= "w";
	public static final String TM_DAY 		= "d";
	public static final String TM_HOUR 		= "h";
	public static final String TM_MIN 		= "m";
	public static final String TM_SEC 		= "s";
	
	public static final String OP_SUM 		= "sum";
	public static final String OP_AVG 		= "avg";
	public static final String OP_MIN 		= "min";
	public static final String OP_MAX 		= "max";
	public static final String OP_AGO 		= "ago";
	
	private String val = "";
	private String tm = "";
	private String op = "";
	
	public TSParser(String fun) {
		if (fun != null && !fun.isEmpty()) {
			String str[] = fun.split("-");
			if (str.length == 2) {
				val = str[0].substring(0, str[0].length()-1);
				tm = str[0].substring(str[0].length()-1);
				op = str[1];
			}
		}
	}
	
	public boolean isValid() {
		return !val.isEmpty() && !tm.isEmpty() && !op.isEmpty();
	}
	
	public long getMsLong() {
		long t = Long.parseLong(val);
		
		switch (tm) {
			case TM_YEAR:
				t = t*60*60*24*365;
				break;
			case TM_MON:
				t = t*60*60*24*30;
				break;
			case TM_WEEK:
				t = t*60*60*24*7;
				break;		
			case TM_DAY:
				t = t*60*60*24;
				break;		
			case TM_HOUR:
				t = t*60*60;
				break;		
			case TM_MIN:
				t = t*60;
				break;		
			case TM_SEC:
				break;		
			}
		
		return t*1000;
	}
	
	public void debug() {
		System.out.println("valid=" + isValid() + ", val=" + val + ", tm=" + tm + ", op=" + op + ", secLong=" + getMsLong());
	}

	public String getVal() {
		return val;
	}

	public String getTm() {
		return tm;
	}

	public String getOp() {
		return op;
	}

	public static void main(String[] arg) {
		TSParser p = new TSParser("30m-avg");
		p.debug();

	}
}
