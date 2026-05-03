package com.tomatosystem.exbuilder6.core.util;

import com.tomatosystem.exbuilder6.core.vo.ExcelVO;

public class ExcelVOUtil {
	public static String getCellValue(ExcelVO vo, int index){
		if(index == 0) return vo.getCell0();
		else if(index == 1) return vo.getCell1();
		else if(index == 2) return vo.getCell2();
		else if(index == 3) return vo.getCell3();
		else if(index == 4) return vo.getCell4();
		else if(index == 5) return vo.getCell5();
		else if(index == 6) return vo.getCell6();
		else if(index == 7) return vo.getCell7();
		else if(index == 8) return vo.getCell8();
		else if(index == 9) return vo.getCell9();
		else if(index == 10) return vo.getCell10();
		else if(index == 11) return vo.getCell11();
		else if(index == 12) return vo.getCell12();
		else if(index == 13) return vo.getCell13();
		else if(index == 14) return vo.getCell14();
		else if(index == 15) return vo.getCell15();
		else if(index == 16) return vo.getCell16();
		else if(index == 17) return vo.getCell17();
		else if(index == 18) return vo.getCell18();
		else if(index == 19) return vo.getCell19();
		else if(index == 20) return vo.getCell20();
		else if(index == 21) return vo.getCell21();
		else if(index == 22) return vo.getCell22();
		else if(index == 23) return vo.getCell23();
		else if(index == 24) return vo.getCell24();
		else if(index == 25) return vo.getCell25();
		else if(index == 26) return vo.getCell26();
		else if(index == 27) return vo.getCell27();
		else if(index == 28) return vo.getCell28();
		else if(index == 29) return vo.getCell29();
		else if(index == 30) return vo.getCell30();
		else if(index == 31) return vo.getCell31();
		else if(index == 32) return vo.getCell32();
		else if(index == 33) return vo.getCell33();
		else if(index == 34) return vo.getCell34();
		else if(index == 35) return vo.getCell35();
		else if(index == 36) return vo.getCell36();
		else if(index == 37) return vo.getCell37();
		else if(index == 38) return vo.getCell38();
		else if(index == 39) return vo.getCell39();
		else if(index == 40) return vo.getCell40();
		else if(index == 41) return vo.getCell41();
		else if(index == 42) return vo.getCell42();
		else if(index == 43) return vo.getCell43();
		else if(index == 44) return vo.getCell44();
		else if(index == 45) return vo.getCell45();
		else if(index == 46) return vo.getCell46();
		else if(index == 47) return vo.getCell47();
		else if(index == 48) return vo.getCell48();
		else if(index == 49) return vo.getCell49();
		else if(index == 50) return vo.getCell50();
		else if(index == 51) return vo.getCell51();
		else if(index == 52) return vo.getCell52();
		else if(index == 53) return vo.getCell53();
		else if(index == 54) return vo.getCell54();
		else if(index == 55) return vo.getCell55();
		else if(index == 56) return vo.getCell56();
		else if(index == 57) return vo.getCell57();
		else if(index == 58) return vo.getCell58();
		else if(index == 59) return vo.getCell59();
		else if(index == 60) return vo.getCell60();
		
		return "";
	}
	
	public static String getCellValue(ExcelVO vo, String index){
		if(index == "ERR"){
			return vo.getErrorMSG();
		}else{
			return getCellValue(vo, Integer.parseInt(index));
		}
	}
	
	public static void setCellValue(ExcelVO item, int cellIdx, String value){
		
		if(cellIdx == 0) item.setCell0(value);
		else if(cellIdx == 1) item.setCell1(value);
		else if(cellIdx == 2) item.setCell2(value);
		else if(cellIdx == 3) item.setCell3(value);
		else if(cellIdx == 4) item.setCell4(value);
		else if(cellIdx == 5) item.setCell5(value);
		else if(cellIdx == 6) item.setCell6(value);
		else if(cellIdx == 7) item.setCell7(value);
		else if(cellIdx == 8) item.setCell8(value);
		else if(cellIdx == 9) item.setCell9(value);
		else if(cellIdx == 10) item.setCell10(value);
		else if(cellIdx == 11) item.setCell11(value);
		else if(cellIdx == 12) item.setCell12(value);
		else if(cellIdx == 13) item.setCell13(value);
		else if(cellIdx == 14) item.setCell14(value);
		else if(cellIdx == 15) item.setCell15(value);
		else if(cellIdx == 16) item.setCell16(value);
		else if(cellIdx == 17) item.setCell17(value);
		else if(cellIdx == 18) item.setCell18(value);
		else if(cellIdx == 19) item.setCell19(value);
		else if(cellIdx == 20) item.setCell20(value);
		else if(cellIdx == 21) item.setCell21(value);
		else if(cellIdx == 22) item.setCell22(value);
		else if(cellIdx == 23) item.setCell23(value);
		else if(cellIdx == 24) item.setCell24(value);
		else if(cellIdx == 25) item.setCell25(value);
		else if(cellIdx == 26) item.setCell26(value);
		else if(cellIdx == 27) item.setCell27(value);
		else if(cellIdx == 28) item.setCell28(value);
		else if(cellIdx == 29) item.setCell29(value);
		else if(cellIdx == 30) item.setCell30(value);
		else if(cellIdx == 31) item.setCell31(value);
		else if(cellIdx == 32) item.setCell32(value);
		else if(cellIdx == 33) item.setCell33(value);
		else if(cellIdx == 34) item.setCell34(value);
		else if(cellIdx == 35) item.setCell35(value);
		else if(cellIdx == 36) item.setCell36(value);
		else if(cellIdx == 37) item.setCell37(value);
		else if(cellIdx == 38) item.setCell38(value);
		else if(cellIdx == 39) item.setCell39(value);
		else if(cellIdx == 40) item.setCell40(value);
		else if(cellIdx == 41) item.setCell41(value);
		else if(cellIdx == 42) item.setCell42(value);
		else if(cellIdx == 43) item.setCell43(value);
		else if(cellIdx == 44) item.setCell44(value);
		else if(cellIdx == 45) item.setCell45(value);
		else if(cellIdx == 46) item.setCell46(value);
		else if(cellIdx == 47) item.setCell47(value);
		else if(cellIdx == 48) item.setCell48(value);
		else if(cellIdx == 49) item.setCell49(value);
		else if(cellIdx == 50) item.setCell50(value);
		else if(cellIdx == 51) item.setCell51(value);
		else if(cellIdx == 52) item.setCell52(value);
		else if(cellIdx == 53) item.setCell53(value);
		else if(cellIdx == 54) item.setCell54(value);
		else if(cellIdx == 55) item.setCell55(value);
		else if(cellIdx == 56) item.setCell56(value);
		else if(cellIdx == 57) item.setCell57(value);
		else if(cellIdx == 58) item.setCell58(value);
		else if(cellIdx == 59) item.setCell59(value);
		else if(cellIdx == 60) item.setCell60(value);
	}
}
