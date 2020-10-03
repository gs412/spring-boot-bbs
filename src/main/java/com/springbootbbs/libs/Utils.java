package com.springbootbbs.libs;

public class Utils {

	public static String subTextString(String str,int len) {
		return subTextString(str, len, "");
	}

	public static String subTextString(String str,int len, String suffix){
		if(str.length()<len/2)return str;
		int count = 0;
		StringBuffer sb = new StringBuffer();
		String[] ss = str.split("");
		for(int i=1;i<ss.length;i++){
			count+=ss[i].getBytes().length>1?2:1;
			sb.append(ss[i]);
			if(count>=len)break;
		}
		//不需要显示...的可以直接return sb.toString();
		if (sb.toString().length()<str.length()) {
			return sb.append(suffix).toString();
		} else {
			return str;
		}
	}

    public static String getBasePath() {
        return System.getProperty("user.dir");
    }

}
