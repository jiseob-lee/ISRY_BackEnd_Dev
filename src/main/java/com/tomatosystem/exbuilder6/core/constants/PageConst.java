package com.tomatosystem.exbuilder6.core.constants;

public final class PageConst {

    /** 레코드 총 건수 **/
    public static final String KEY_RECORD_TOTAL_CNT = "totalCount";
    
    /** 실제 레코드 총 건수(게시판 중요글 등에서 직접 쿼리에 적용한 경우에만 활용)  **/
    public static final String KEY_RECORD_REAL_TOTAL_CNT = "realTotalCount";
    
    /** 한 페이지당 게시되는 게시물 건 수 파라미터 키 값 */
    public static final String KEY_RECORD_CNT_PER_PAGE = "pageRowCount";

    /** 현재 페이지 번호 키 값 */
    public static final String KEY_PAGE_NO = "pageNo";

    /** 페이지 데이터 맵 KEY **/
    public static final String KEY_PAGE_DATA_MAP_KEY = "pageResultKey";

    /** 로우 카운트 키 값 */
    public static final String TOTAL_COUNT = "TOT_CNT";
   
    /** 실제 레코드 로우 카운트 키 값  **/
    public static final String REAL_TOTAL_COUNT = "REAL_TOT_CNT";
    
    /**  페이지 첫번째 INDEX KEY **/
    public static final String KEY_FIRST_RECODE_IDX = "FIRST_RECORD_INDEX";
    
    /**  페이지 로우 카운트 KEY(OFFSET 사용시적용) **/
    public static final String KEY_PAGE_ROW_COUNT = "PAGE_ROW_COUNT";
    
    /**  페이지 마지막번째 INDEX(rownum 사용시적용) **/
    public static final String KEY_LAST_RECODE_IDX = "LAST_RECORD_INDEX";

    /** 페이징 오브젝트 정보 **/
    public static final String PAGINATION_INFO = "_PAGE_INFO_";

    /** 리스트에 게시되는 게시물 기본 건 수 */
    public static final int RECORD_CNT_PER_PAGE = 20;

}
