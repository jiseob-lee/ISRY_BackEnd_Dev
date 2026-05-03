/************************************************
 * @파일명 : Untitled.js
 * @프로그램명 :  
 * @작성자 : Song.Young.Il
 * @작성일자: 2022. 2. 28. 오후 2:39:12
 * @수정로그 : 
 * [일자, 수정자] 내용 
 ************************************************/
 
//=================================================================================
// [Object Setting]
//================================================================================= 
var util = createCommonUtil();

//=================================================================================
// [Function]
//================================================================================= 

/**
 * 목록 데이터 조회
 * @param psStatus - 조회 상태(저장 후 조회인 경우에는 'save' 구분값 넘김)
 */
function doList(psStatus){

	//조회 서브미션 호출
	util.Submit.send(app, "subList", function(pbSuccess){
		if(pbSuccess) {
			if(psStatus == "save"){
				//갱신된 데이터가 조회되었습니다.
				util.Msg.notify(app, "INF-M005");
			}else{
				//조회되었습니다.
				util.Msg.notify(app, "INF-M001");
			}
		}
	});
}

/**
 * 데이터 저장
 */
function doSave(){
	
	// 데이터 변경사항이 없을시 "변경된 데이터가 없습니다" 출력
	if (!util.Grid.isModified(app, "grdId", "MSG")) return false;
	
	// 그리드 내 컬럼 유효성 체크
	if (!util.validate(app, "grdId")) return false;
	
	// 저장 서브미션 호출
	util.Submit.send(app, "subSave", function(pbSuccess){
		if(pbSuccess){
			doList("save");
		}
	});
}

/**
 * 상세 팝업 호출
 */
function openDetailPopup(){
    
    util.Dialog.open(app, "appId", "appTitle" , 940, 760, function(/*cpr.events.CUIEvent*/ e){
        
        /*
         * 다이얼로그 close 이벤트 핸들러(닫기 동작시 반환되는 값에 대한 처리)
         * 다이얼로그가 이미 닫혀진 상태이기 때문에 다이얼로그 관련 이벤트리스너는 발생하지 않습니다.
         */        
        /**@type cpr.controls.Dialog*/
        var dialog = e.control;
        var returnValue = dialog.returnValue;
        
        /*
         * returnValue 처리
         * returnValue가 단일값을 경우 그대로 사용할 수 있으며, JSON 데이터일 경우 key값을 통해 value를 받을 수 있습니다. 
         */        
        if(!ValueUtil.isNull(returnValue)){
            
        }
               
    }, initValue);   
}

//=================================================================================
// [Control Event Function]
//=================================================================================


/*
 * 루트 컨테이너에서 load 이벤트 발생 시 호출.
 * 앱이 최초 구성된후 최초 랜더링 직후에 발생하는 이벤트 입니다.
 */
function onBodyLoad(/* cpr.events.CEvent */ e){
	
	// 화면 구성시 필요한 데이터 조회
	util.Submit.send(app, "subOnload", function(pbSuccess){
		if(pbSuccess){
			
		}
	});
	
	//사업연도 초기설정
	var year = new Date().getFullYear();
	
	var cmb2 = app.lookup("cmb2");
	cmb2.value=year;
	var cmb3 = app.lookup("cmb3");
	cmb3.setFilter("column2 == "+year);
	
	//시작날짜, 종료날짜 default 설정
	var dti1 = app.lookup("dti1");
	var dti2 = app.lookup("dti2");

    console.log("ㅁㄴㅇㄻㄴㅇㄹ"+moment().format("YYYYMMDD"));
	dti1.value = moment().subtract(3, 'months').format("YYYYMMDD");
	dti2.value = moment().format("YYYYMMDD");
	
}


/*
 * 사용자 정의 컨트롤에서 search 이벤트 발생 시 호출.
 * 조회버튼 클릭시 이벤트
 */
function onBtnSearchSearch(/* cpr.events.CUIEvent */ e){
	
	// 그리드 데이터 변경사항 체크
	if (util.Grid.isModified(app, "grdId", "CRM")) {
		return false;
	}
	
	// 조회조건 유효성 체크
	if(!util.validate(app, "grpSearch")) return false;
	
	doList();
}

/*
 * "초기화" 버튼(btnReset)에서 click 이벤트 발생 시 호출.
 * 사용자가 컨트롤을 클릭할 때 발생하는 이벤트.
 */
function onBtnResetClick(/* cpr.events.CMouseEvent */ e){
		
	util.DataMap.reset(app, "dmSearch");
}



/*
 * 콤보 박스에서 selection-change 이벤트 발생 시 호출.
 * ComboBox Item을 선택하여 선택된 값이 저장된 후에 발생하는 이벤트.
 */
function onCmb2SelectionChange(/* cpr.events.CSelectionEvent */ e){
    /** 
     * @type cpr.controls.ComboBox
     */
    var cmb2 = e.control;
    
    //사업연도 선택에 따른 자원명 목록의 변경
    var cmb3 = app.lookup("cmb3");
    cmb3.setFilter("column2 == "+cmb2.value);
    
}


/*
 * 데이트 인풋에서 value-change 이벤트 발생 시 호출.
 * Dateinput의 value를 변경하여 변경된 값이 저장된 후에 발생하는 이벤트.
 */
function onDti1ValueChange(/* cpr.events.CValueChangeEvent */ e){
    /** 
     * @type cpr.controls.DateInput
     */
    var dti1 = e.control;
    console.log(dti1.value);
}
