/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.util;

/**
 * @파일명        : CLOBHandler.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2022. 8. 23. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2022. 8. 23.
 * @수정내용      : 
 * -                
 * -                
 */

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

@SuppressWarnings("rawtypes")
public class CLOBHandler implements TypeHandler {

	@Override
	// 파라메터 셋팅할때
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
		throws SQLException {
		String s = (String) parameter;
		StringReader reader = new StringReader(s);
		ps.setCharacterStream(i, reader, s.length());
	}

	@Override
	// Statement 로 SQL 호출해서 ResultSet 으로 컬럼값을 읽어올때
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		return rs.getString(columnName);
	}

	@Override
	// CallableStatement 로 SQL 호출해서 컬럼값 읽어올때
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getString(columnIndex);
	}

	/**
	 * @Method명   : getResult
	 * @param rs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 8. 23. 
	 * @Method설명 :
	 */
	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getString(columnIndex);
	}
}
