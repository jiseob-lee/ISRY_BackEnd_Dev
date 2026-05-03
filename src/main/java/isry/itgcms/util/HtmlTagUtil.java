package isry.itgcms.util;

public class HtmlTagUtil {
	
	/**
     * 특수문자를  변환 처리하는 기능이다.
     * @param 	s 	문자열
     * @param 	n 	번호
     * @return 	변환문자열
     * @exception Exception 
     * 
     * <pre>
     *   용례) String content = &quot;&lt;TABLE&gt;&quot;;
     *         content = HtmlTagUtil.encodeHTMLSpecialChar(content, 해당 번호);
     * </pre>
     */
	 public static String encodeHtmlSpecChar(String s, int n) {
	        String str = s;
	        nchk(str);

	        switch (n) {
	            case 1 : // 데이터  입력시 특수문자 변경
	                str = rplc(str,"'","''");
	                str = rplc(str,"<","&lt;");
	                str = rplc(str,">","&gt;");
	                str = rplc(str,"\"","&quot;");
	                str = rplc(str,"(","&#40");
	                str = rplc(str,")","&#41");           
	                str = rplc(str,"&","&#38");
	                str = rplc(str,"#","&#35");
	                break;
	           case 2 : // 데이터문자를 특수문자 재변경시 사용
	               str = rplc(str,"''","'");
	               str = rplc(str,"&lt;","<");
	               str = rplc(str,"&gt;",">");
	               str = rplc(str,"&quot;","\"");
	               str = rplc(str,"&#35","#");
	               str = rplc(str,"&#38","&");
	               str = rplc(str,"&#40","(");
	               str = rplc(str,"&#41",")");
	               break;      
	           case 3 :
	               str = rplc(str,"'","''");
	               str = rplc(str,"&","&amp;");
	               str = rplc(str,"<","&lt;");
	               str = rplc(str,">","&gt;");
	               str = rplc(str,"\"","&quot;");
	               str = rplc(str,"(","&#40;");
	               str = rplc(str,")","&#41;");           
	               break;
	            case 11 : // HTML 일때
	                str = rplc(str, "&#39;", "'");
	                str = rplc(str, "&quot;", "\"");
	                break;
	            case 12 : // edit mode
	                str = rplc(str, "&#39;", "'");
	                str = rplc(str, "<STYLE>", "<>");
	                str = rplc(str, "</STYLE>", "<>");
	                str = rplc(str, "<style>", "<>");
	                str = rplc(str, "</style>", "<>");
	                str = rplc(str, "<marquee", "<");
	                str = rplc(str, "</marquee>", "<>");
	                break;
	            case 13 : // text 일때
	                str = rplc(str, "&#39;", "'");
	                str = rplc(str, "&quot;", "\"");
	                str = rplc(str, " ", "&nbsp;");
	                str = rplc(str, "\n", "<br>");
	                break;
	            case 14 : // text 일때- TEXTAREA 에서
	                str = rplc(str, "&#39;", "'");
	                break;
	            case 15 : // HTML 일때
	                str = rplc(str, "&#39;", "'");
	                str = rplc(str, "\n", "<br>");
	                str = rplc(str, "&quot;", "\"");
	                break;
	            case 21 : // Text 입력
	                str = rplc(str, "'", "&quot;");
	                str = rplc(str, "\"", "&quot;");
	                str = rplc(str, " ", "&nbsp;");
	                str = rplc(str, "<", "&lt;");
	                str = rplc(str, ">", "&gt;");
	                break;
	            case 22 : // HTML 입력
	                str = rplc(str, "'", "&quot;");
	                break;
	            case 31 : // Text view
	                str = rplc(str, "&quot;", "'");
	                str = rplc(str, "&nbsp;", " ");
	                str = rplc(str, "\n", "<br>");
	                str = rplc(str, "&lt;", "<");
	                str = rplc(str, "&gt", ">");
	                break;
	            case 32 : // HTML view
	                str = rplc(str, "&quot;", "'");
	                break;
	            case 41 : // Text 입력
	                str = rplc(str, "\n", "<br>");
	                str = rplc(str, "<", "&lt;");
	                str = rplc(str, ">", "&gt;");
	                str = rplc(str, "'", "&quot;");
	                str = rplc(str, " ", "&nbsp;");
	                break;
	            case 42 : // Text view
	                str = rplc(str, "&nbsp;", " ");
	                str = rplc(str, "&quot;", "'");
	                str = rplc(str, "&gt;", ">");
	                str = rplc(str, "&lt;", "<");
	                str = rplc(str, "<br>", "\n");
	                break;
	            case 43 : // Text view
	                str = rplc(str, "&quot;", "'");
	                str = rplc(str, "&gt;", ">");
	                str = rplc(str, "&lt;", "<");
	                break;
	            case 44 : // Text view
	                str = rplc(str, "&quot;", "'");
	                str = rplc(str, "&gt;", ">");
	                str = rplc(str, "&lt;", "<");
	                str = rplc(str, " ", "&nbsp;");
	                str = rplc(str, "\n", "<br>");
	                break;
	            case 60 : // 게시판 text 모드 REPLACE(String original, String find,
	                // String replace)
	                str = rplc(str, "'", "&quot;");
	                str = rplc(str, ">", "&gt;");
	                str = rplc(str, "<", "&lt;");
	                str = rplc(str, "<br>", "&nbsp;");
	                str = rplc(str, "<BR>", "&nbsp;");
	                str = rplc(str, "\n", "<br>");
	                str = rplc(str, "\n", "<BR>");
	                str = rplc(str, "\"", "&quot;");
	                break;
	            case 61 : // 게시판 HTML 모드 REPLACE(String original, String find,
	                // String replace)
	                str = rplc(str, "<head>", "&lt;head&gt;");
	                str = rplc(str, "</head>", "&lt;h/head&gt;");
	                str = rplc(str, "<title>", "&lt;title&gt;");
	                str = rplc(str, "</title>", "&lt;/title&gt;");
	                str = rplc(str, "<style>", "&lt;style&gt;");
	                str = rplc(str, "</style>", "&lt;/style&gt;");
	                str = rplc(str, "<script", "&lt;script&gt;");
	                str = rplc(str, "</script>", "&lt;/script&gt;");
	                str = rplc(str, "<meta", "&lt;meta");
	                str = rplc(str, "</meta>", "&lt;/meta&gt;");
	                str = rplc(str, "<iframe", "&lt;iframe");
	                str = rplc(str, "</iframe>", "&lt;/iframe&gt;");
	                str = rplc(str, "<xmp", "&lt;xmp");
	                str = rplc(str, "</xmp>", "&lt;/xmp&gt;");
	                str = rplc(str, "<marquee", "&lt;<marquee");
	                str = rplc(str, "</marquee>", "&lt;/marquee&gt;");
	                break;
	            case 99 :
	                str = rplc(str, "'", "&#39;"); 
	                str = rplc(str, "  ", " ");
	                str = rplc(str, " or ", "^");
	                str = rplc(str, " OR ", "^");
	                str = rplc(str, " ^ ", "^");
	                str = rplc(str, " and ", "&");
	                str = rplc(str, " AND ", "&");
	                str = rplc(str, " & ", "&");
	                str = rplc(str, " ) ", ")");
	                str = rplc(str, ") ", ")");
	                str = rplc(str, " )", ")");
	                str = rplc(str, " ( ", "(");
	                str = rplc(str, " (", "(");
	                str = rplc(str, "( ", "(");
	                str = rplc(str, "or", "^");
	                str = rplc(str, "OR", "^");
	                str = rplc(str, "and", "&");
	                str = rplc(str, "AND", "&");
	                str = rplc(str, " + ", "&");
	                str = rplc(str, ")^", ")");
	                str = rplc(str, "^(", "(");
	                str = rplc(str, " ", "&");
	                str = rplc(str, "(", " or ( ");
	                str = rplc(str, ")", " ) or ");
	                str = rplc(str, " or ( or ( ", " or (( ");
	                str = rplc(str, "  ) or ) or ", " )) or ");
	                str = rplc(str, "^", " or ");
	                str = rplc(str, "&", " and ");
	                str = rplc(str, "or  and", "and");
	                str = rplc(str, "and  and", "and");
	                str = rplc(str, "and  or", "or");
	                str = rplc(str, "or  or", "or");
	                str = rplc(str, "or  and", "and");
	                if (str.length() > 4) {
	                    if (str.substring(0, 4).equals(" or ")) {
	                        str = str.substring(4, str.length());
	                    }
	                }
	                if (str.length() > 4) {
	                    if (str.substring(str.length() - 4, str.length()).equals(" or ")) {
	                        str = str.substring(0, str.length() - 4);
	                    }
	                }
	                if (str.length() > 5) {
	                    if (str.substring(0, 5).equals(" and ")) {
	                        str = str.substring(5, str.length());
	                    }
	                }
	                if (str.length() > 5) {
	                    if (str.substring(str.length() - 5, str.length()).equals(" and ")) {
	                        str = str.substring(0, str.length() - 5);
	                    }
	                }
	                break;
	               default: break;
	        }
	        return str;
	    }
	 
		/**
	     * <pre>
	     *   문자열이 NULL인지 체크한다.
	     * </pre>
	     * 
	     * @param str 문자열
	     * @return NULL이면 "", 아니면 그대로 리턴
	     */
	    public static String nchk(String str) {
	        return nchk(str, "");
	    }

	    /**
	     * <pre>
	     *   문자열이 NULL이면 대치할 문자열을 리턴한다.
	     * </pre>
	     * 
	     * @param str 문자열
	     * @param defaultStr 대치할 문자열
	     * @return NULL이면 replaceStr, 아니면 그대로 리턴
	     */
	    public static String nchk(String str, String defaultStr) {
	        return (str == null) ? defaultStr : str;
	    }
	    
	 /**
	     * <pre>
	     *   문자열중에서 A문자열을 B문자열로 모두 대치한다.(대소문자 구별안함)
	     *   용례) StringUtil.replace(ex.getMessage(), &quot;\n&quot;, &quot;
	     * <br>
	     *  &quot;)
	     * </pre>
	     * 
	     * @param original 오리지날 문자열
	     * @param find 찾고자 하는 문자열
	     * @param replace 바꾸고자 하는 문자열
	     * @return 대치된 문자열
	     */
	    public static String rplc(String original, String find, String replace) {
	        if (original == null || find == null || replace == null || original.length() < 1 || find.length() < 1
	                        || replace.length() < 1)
	            return original;
	        int index = -1, fromIndex = 0, tempIndex;
	        StringBuffer sb = new StringBuffer();
	        while ((tempIndex = original.indexOf(find, fromIndex)) >= 0) {
	            index = tempIndex;
	            sb.append(original.substring(fromIndex, index)).append(replace);
	            fromIndex = index + find.length();
	        }
	        if (sb.length() < 1)
	            return original;

	        sb.append(original.substring(index + find.length()));
	        return sb.toString();
	    }

	
    /**
     * 자바스크립트 특수문자를 제거하는 기능이다
     * @param 	original String
     * @return 	변환문자열
     */
	 public static String deleteTagSign(String original) {
	        char    orgByte[]       = null;
	        char    chgByte[]       = null;
	        int     index           = 0;
	        String  deledtedValue   = null;
	        
	        if(original == null) {
	            deledtedValue = original;
	        } else {
	            orgByte = original.toCharArray();
	            chgByte = new char[orgByte.length];
	            for(int i = 0; i < orgByte.length; i++) {
	                if(orgByte[i] == '<' || orgByte[i] == '>') {
	                    @SuppressWarnings("unused")
						int dummy=1;
	                } else {
	                    chgByte[index] = orgByte[i];
	                    index++;
	                }
	            }
	            deledtedValue = new String(chgByte, 0, index);
	        }
	        return deledtedValue;
	    }

}