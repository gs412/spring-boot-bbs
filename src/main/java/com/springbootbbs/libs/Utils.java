package com.springbootbbs.libs;


import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.HashMap;

public class Utils {

	public static String subTextString(String str,int len) {
		return subTextString(str, len, "");
	}

	public static String subTextString(String str,int len, String suffix){
		if(str.length()<len/2)return str;
		int count = 0;
		StringBuilder sb = new StringBuilder();
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

    public static String makeQueryStr(HashMap<String, String> allRequestParams) {
        StringBuilder sb = new StringBuilder();
        for (HashMap.Entry<String, String> entry : allRequestParams.entrySet()) {
            if (entry.getKey().equals("p")) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append(entry.getKey()).append('=').append(entry.getValue());
        }

        return sb.toString();
    }

	public static String markDown(String text) {
		MutableDataSet options = new MutableDataSet();

		// uncomment to set optional extensions
		//options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create()));

		// uncomment to convert soft-breaks to hard breaks
		//options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

		Parser parser = Parser.builder(options).build();
		HtmlRenderer renderer = HtmlRenderer.builder(options).build();

		// You can re-use parser and renderer instances
		Node document = parser.parse(text);
		String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
		return html;
	}

}
