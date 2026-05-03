package isry.itgcms.util;

public class Formatter {

	public static String phoneFormat(String str, int type) {

		if (str == null) {
			return "";
		}
		
		String regex = "[^0-9]"; // 숫자가 아닌 문자열을 선택하는 정규식
		String num = str.replaceAll(regex, "");

		String formatNum = "";

		try {

			if (num.length() == 11) {
				
				if (type == 0) {
					formatNum = num.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-****-$3");
				} else {
					formatNum = num.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
				}

			} else if (num.length() == 10 || num.length() == 9) {
				
				if (num.indexOf("02") == 0) {
					if (type == 0) {
						formatNum = num.replaceAll("(\\d{2})(\\d{3,4})(\\d{4})", "$1-****-$3");
					} else {
						formatNum = num.replaceAll("(\\d{2})(\\d{3,4})(\\d{4})", "$1-$2-$3");
					}
				} else {
					if (type == 0) {
						formatNum = num.replaceAll("(\\d{3})(\\d{3})(\\d{4})", "$1-***-$3");
					} else {
						formatNum = num.replaceAll("(\\d{3})(\\d{3})(\\d{4})", "$1-$2-$3");
					}
				}

			} else if (num.length() == 8) {

				if (type == 0) {
					formatNum = num.replaceAll("(\\d{4})(\\d{4})", "$1-****");
				} else {
					formatNum = num.replaceAll("(\\d{4})(\\d{4})", "$1-$2");
				}

			} else {
				formatNum = num;
			}

		} catch (NumberFormatException e) {
			formatNum = num;
		} catch (Exception e) {
			formatNum = num;
		}

		return formatNum;

	}


	public static String dateFormat(String str) {

		if (str == null) {
			return "";
		}
		
		String regex = "[^0-9]"; // 숫자가 아닌 문자열을 선택하는 정규식
		String num = str.replaceAll(regex, "");
		
		if (num.length() == 8) {
			num = num.substring(0, 4) + "-" + num.substring(4, 6) + "-" + num.substring(6, 8);
		}
		
		return num;
	}
}
