package isry.itgcms.syscmmn.sms.service;

import java.util.List;

import lombok.Data;

@Data
public class SmsMessageVO {
	String userId;				/* 로그인 아이디 */
	List<String> recvTelNo;		/* 수신인 번호 아이디 */
	List<String> receiverName;	/* 수신자명 */
	String contents;			/* 메시지 내용 */
	String senderTelNo;			/* 발신인 번호 */
	String reserveYN;			/* 예약발송 Y/N */
	String reserveTime;			/* 예약 발송일 YYYYMMDDHHIISS */
}
