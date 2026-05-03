/*
 Kings Online Security

 kos-ng.config.js - Configuration

 Customized for 킹스정보통신
 
 20210929 sun4035@kings.co.kr
 */

(function (window) {
    'use strict';

    var KOSConfig = {
        logLevel: 4, // 0: disabled, 1: info, 2: warn, 3: error, 4: debug

        // 서비스 버전 정보
        version: {
           // service: 22070601,
            service: 22110801,
            bridge: 19122301,
            handler: 22070601
        },

        // DO NOT EDIT THIS CONFIG BLOCK
        network: {
            protocol: 'https',
            host: '127.0.0.1',
            ports: [
                64032,
                54032,
                44032
            ],
            bridgePath: '/',

            timeout: {
                request: 30000
            }
        },

        // 이벤트 발생 설정
        event: {
            // 미지원 환경 이벤트 (ERROR_UNSUPPORTED_ENVIRONMENT)의 발생 여부
            raiseUnsupportedEnvironment: false
        },

        // 지원 대상 웹 브라우저 설정
        webBrowsers: {
            // 미보호 웹 브라우저 목록
            disabled: ['Unknown'],
            // 미지원 웹 브라우저 목록
            unsupported: []
        },

        // 라이선스
        licenses: {
            // 기본 라이선스
            defaultLicense: '4cdcf4e22671773eed1fc1bd2dff6378ade9bdf826dd2159d62afdf5b5ba24db21', // 202303
            // 도메인 별 라이선스
            hostLicenses: [
                ['www.youthsafety.go.kr','c536b71b44183abd5b932c1fc7aef6bfd13e91e17aeae8fe173742b7bf839919a0'],
                ['gov.youthsafety.go.kr','6cedc2d105323af1f3a493ff7b9df12a083fc158169a0dfa0e52a201ccb14f61f0'],
                ['\\.youthsafety.go.kr','922116208aef8ec15bf2436b580a76e1a761e092a1afe0b89913ffbff85ed96640'],
                ['www.1388.go.kr','a86b9732dbaba920bbd70177302c223d198403929f88fe3e21e6dd217154fd2c21'],
                ['\\.1388.go.kr','fc6fbfd0feaeb4e9ebd8198e54ef7885760b060a644b732cb63b65226be564a841'] 
            ]
        },

        // 업데이트 서버
        updateServers: {
            // 기본 업데이트 서버
            defaultServer: {
            	baseURL: 'http://kings.nefficient.co.kr/kings',
                authKey: '58c705643199c2ff067850db4181dff9f0d63edeecde16ab33ac57c24703d22ff0'
            },
            // 도메인 별 업데이트 서버
            hostServers: [
				/* ['domain', 'baseURL', 'authKey'] */
            ]
        },

        // 스크립트 로드 시 자동 시작
        autoStart: true,
        // 비동기 프로토콜 핸들러 사용 여부
        disableProtocolHandler: false
    };

    var utils = (function () {
        function isNullOrUndefined(arg) {
            return typeof arg === 'undefined' || arg === null;
        }

        function contains(string, find) {
            if (isNullOrUndefined(string)) {
                return false;
            }

            return string.indexOf(find) >= 0;
        }

        function startsWith(string, find) {
            if (isNullOrUndefined(string)) {
                return false;
            }

            return string.indexOf(find) === 0;
        }

        function compareNoCase(string1, string2) {
            string1 = string1.toUpperCase();
            string2 = string2.toUpperCase();

            return string1 === string2;
        }

        function getBrowserType() {
        	var userAgent = window.navigator.userAgent;
            var result = 'Unknown';

            if (contains(userAgent, 'MSIE') || contains(userAgent, 'Trident')) {
                result = 'IE';
            } else if (contains(userAgent, 'Edg')) {
				if (contains(userAgent, 'Edge')){
					result = 'Edge';
				}else{
					result = 'Chrome_Edge'
				}
            } else if (contains(userAgent, 'Firefox')) {
                result = 'Firefox';
            } else if (contains(userAgent, 'Opera') || contains(userAgent, 'OPR')) {
                result = 'Opera';
            } else if (contains(userAgent, 'Whale')) {
                result = 'Whale';
            } else if (contains(userAgent, 'Vivaldi')) {
                result = 'Vivaldi';
            } else if (contains(userAgent, 'Chrome')) {
                result = 'Chrome';
            } else if (contains(userAgent, 'Safari')) {
                result = 'Safari';
            }

            return result;
        }

        function isInternetExplorer() {
            return getBrowserType() === 'IE';
        }

        function isLegacyBrowser() {
            return isNullOrUndefined(window.postMessage);
        }

        function isEdge() {
            return getBrowserType() === 'Edge';
        }

        function is64BitBrowser() {
            var ua = window.navigator.userAgent;

            if (contains(ua, 'Win64') || contains(ua, 'x64')) {
                return true;
            }

            return false;
        }

        function findElementByName(collection, objectName) {
            for (var i = 0; i < collection.length; i += 1) {
                var item = collection[i];

                if (item.name === objectName) {
                    return item;
                }
            }

            return null;
        }

        var exports = {};
        exports.isNullOrUndefined = isNullOrUndefined;
        exports.contains = contains;
        exports.startsWith = startsWith;
        exports.compareNoCase = compareNoCase;
        exports.getBrowserType = getBrowserType;
        exports.isInternetExplorer = isInternetExplorer;
        exports.isLegacyBrowser = isLegacyBrowser;
        exports.isEdge = isEdge;
        exports.is64BitBrowser = is64BitBrowser;
        exports.findElementByName = findElementByName;

        return exports;
    }());

    function KOS_CoreOnReady() {
        var inputObjects = window.document.getElementsByTagName('INPUT');
        for (var k = 0; k < inputObjects.length; k += 1) {
            var item = inputObjects[k];

            if (utils.contains(item.name, "doubly_encrypted_hexa")) {
                window.KOS.registerElement(item, "none");
            }
        }
    }

    function KOS_GetConfig() {
        return KOSConfig;
    }

    function KOS_GetLicense() {
        var domain = window.location.hostname;

        for (var i = 0; i < KOSConfig.licenses.hostLicenses.length; i += 1) {
            if (domain.search(KOSConfig.licenses.hostLicenses[i][0]) !== -1) {
                return KOSConfig.licenses.hostLicenses[i][1];
            }
        }

        return KOSConfig.licenses.defaultLicense;
    }

    function KOS_GetUpdateBaseURL() {
        var domain = window.location.hostname;

        for (var i = 0; i < KOSConfig.updateServers.hostServers.length; i += 1) {
            if (domain.search(KOSConfig.updateServers.hostServers[i][0]) !== -1) {
                return KOSConfig.updateServers.hostServers[i][1];
            }
        }

        return KOSConfig.updateServers.defaultServer.baseURL;
    }

    function KOS_GetUpdateAuthKey() {
        var domain = window.location.hostname;

        for (var i = 0; i < KOSConfig.updateServers.hostServers.length; i += 1) {
            if (domain.search(KOSConfig.updateServers.hostServers[i][0]) !== -1) {
                return KOSConfig.updateServers.hostServers[i][2];
            }
        }

        return KOSConfig.updateServers.defaultServer.authKey;
    }

    function KOS_KDefenseNOption(sOption) {
        if (!utils.isInternetExplorer()) {
            return sOption;
        }

        return '0x' + (parseInt(sOption, 16) | 0x00000014).toString(16);
    }

    function KOS_GetKDefenseParameters() {
        var isIE = utils.isInternetExplorer();
        var isEdge = utils.isEdge();
        var is64Bit = KOS.isKOSChrome ? KOS_Chrome.is64Bit:utils.is64BitBrowser();

        var parameters = {};

        parameters.License = KOS_GetLicense();

        parameters.szIconON = '';
        parameters.szIconONHash = '';
        parameters.szIconOFF = '';
        parameters.szTipImage = '';
        parameters.szTipImageHash = '';

        parameters.nOption = KOS_KDefenseNOption('0x00000002');
        parameters.nOptionEx = '0x02270001';
        parameters.nOptionEx2 = '0x00000000'; //'0x00000004';
        parameters.nOptionEx3 = '0x00000002';

        parameters.szGKey = KOS_GetUpdateAuthKey();
        parameters.szSubClassName = ':';
        //parameters.szTitle = '여성가족부';
        parameters.szTitle = '';

        if (!is64Bit || isEdge) { // 32Bit
        	parameters.nModuleVersion = '421073001';
            parameters.szModulePath = KOS_GetUpdateBaseURL() + '/kdfinj6x/421073001_6320/kdfinj.dll';
            parameters.szModuleHash = 'DE9B448CA314A397E569CB0EB65A8179';
			
			// kdfinj ARM64
			parameters.nModuleARMVersion = '419053001';
            parameters.szModuleARMPath = KOS_GetUpdateBaseURL() + '/kdfinj6xARM/419053001_6501/kdfinj.dll';
            parameters.szModuleARMHash = 'A86A524408B3E000535341DD520E94C1';

            if (isIE) {
                parameters.nExModuleVersion = '720031201';
                parameters.szExModulePath = KOS_GetUpdateBaseURL() + '/kdfmod3x/720031201_1133/kdfmod.dll';
                parameters.szExModuleHash = 'DFD3B0E60B45945E10D2474B4376FC4A';

                parameters.nNaxModuleVersion = '416080101';
                parameters.szNaxModulePath = KOS_GetUpdateBaseURL() + '/KOSNax/x86_kdfnaxs/416080101_1015/kdfnaxs.dll';
                parameters.szNaxModuleHash = 'EE212BF1851057BF2B0999950508E2A5';
            } else if (!isEdge) {
                parameters.nNPModuleVersion = '19022201';
                parameters.szNPModulePath = KOS_GetUpdateBaseURL() + '/KOSNax/x86_kdfnpmbr/19022201_1025/kdfnpmbr.dll';
                parameters.szNPModuleHash = '230906545C47770E4F1E2C354706D7EE';
            }
        } else { // 64Bit
            parameters.nModuleVersion = '421073001';
            parameters.nModuleFileVersion = '6320';
            parameters.szModulePath = KOS_GetUpdateBaseURL() + '/kdfinj64x/421073001_6320/kdfinj.dll';
            parameters.szModuleHash = '4F51ADF7516DA35BC05875EF01951945';
			
			// kdfinj ARM64
			parameters.nModuleARMVersion = '419053001';
            parameters.nModuleFileARMVersion = '6501';
            parameters.szModuleARMPath = KOS_GetUpdateBaseURL() + '/kdfinj64xARM/419053001_6501/kdfinj.dll';
            parameters.szModuleARMHash = '9215D13AECA26A639DA9DCF055F2D858';

            if (isIE) {
                parameters.nExModuleVersion = '720031201';
                parameters.nExModuleFileVersion = '1133';
                parameters.szExModulePath = KOS_GetUpdateBaseURL() + '/kdfmod64x/720031201_1133/kdfmod.dll';
                parameters.szExModuleHash = 'AA5535F56F003B4B768813F65052BC2B';

                parameters.nNaxModuleVersion = '416080101';
                parameters.nNaxModuleFileVersion = '1015';
                parameters.szNaxModulePath = KOS_GetUpdateBaseURL() + '/KOSNax/x64_kdfnaxs/416080101_1015/kdfnaxs.dll';
                parameters.szNaxModuleHash = '205D9BD79E82B867BA51385485CA72A1';
            } else {
                parameters.nNPModuleVersion = '19022201';
                parameters.nNPModuleFileVersion = '1025';
                parameters.szNPModulePath = KOS_GetUpdateBaseURL() + '/KOSNax/x64_kdfnpmbr/19022201_1025/kdfnpmbr.dll';
                parameters.szNPModuleHash = 'F3C49EF38C04205A4CD8622678783DAE';
            }
        }

        parameters.nSAPGUIModuleVersion = '1002';
        parameters.szSAPGUIModulePath = KOS_GetUpdateBaseURL() + '/KOSNax/x86_kdfSapinj/1002/kdfSapinj.dll';
        parameters.szSAPGUIModuleHash = 'C2CB2F1E264609AEF6F810FA7D270B4F';

        parameters.is64Bit = is64Bit;
		
		//hmg 20191105 이니텍 코드 (이베스트 키는 로그 확인)
		//parameters.IniSiteCode = "???";

        return parameters;
    }

    function KOS_UpdateNotifyMessage(msg) {
        var element = window.document.getElementById('_kos_notify_element_');

        if (element === null) {
            element = window.document.createElement('SPAN');
            element.id = '_kos_notify_element_';

            element.style.position = 'fixed';
            element.style.visibility = 'hidden';
            element.style.right = '0px';
            element.style.bottom = '0px';
            element.style.margin = '20px';
            element.style.padding = '10px';

            element.style.opacity = 0.8;
            element.style.backgroundColor = '#333';
            element.style.color = '#ffffff';

            window.document.body.appendChild(element);
        }

        element.innerHTML = msg;
        element.style.visibility = 'inherit';

        setTimeout(function () {
            element.style.visibility = 'hidden';
        }, 5000);
    }

    /**
     * 전역 이벤트 핸들러 정의
     */
    function KOS_GetGlobalEventHandlers() {
        function KOS_OnConnecting() {
            // Kings Online Security와 연결중일때 호출되는 이벤트
            KOS_UpdateNotifyMessage('CONNECTING');
        }

        function KOS_OnInitializing() {
            // Kings Online Security를 초기화중일때 호출되는 이벤트
            KOS_UpdateNotifyMessage('INITIALIZING');
            $('#installStatus').html('초기화 중(Initializing)');
        }

        function KOS_OnReady() {
            // 모든 준비가 완료되면 호출되는 이벤트. 이 이벤트가 호출된 이후부터 키보드 입력 암호화 가능.
            // 단, 운영체제가 Windows가 아닌경우 (MacOSX, Linux, BSD, 모바일 등)
            // reportUnsupportedOS 옵션에 따라 READY나 ERROR_UNSUPPORTED_OS 이벤트가 호출됨.
            //window.alert('Kings Online Security가 시작되었습니다.');
        	$('#installStatus').html('설치됨(Installed)');
        	
        }

        function KOS_OnNotInstalled() {

            // Kings Online Security가 설치되지 않았을 경우 호출되는 이벤트.
            //alert('Kings Online Security가 설치되지 않았습니다.');
            KOS_UpdateNotifyMessage('NOT_INSTALLED');
			
            $('#installStatus').html('설치되지 않음(Not Installed)');
			
			// HTTPS 미적용 : 20220805 임시주석처리
			var r = confirm("키보드보안 프로그램이 설치되지 않았습니다. 설치페이지로 이동하시겠습니까?");
			
			                 
			if (r == true) {
			    var contextPath = location.href.indexOf("ISRY_BackEnd") > -1 ? "ISRY_BackEnd" : "";
			    
			    if (contextPath == 'ISRY_BackEnd') {
				    window.location.href = "http://"+location.hostname+":"+location.port+"/"+contextPath+"/resource/KDepense7/New_Setup_Page.html";
				} else if (location.hostname == '10.188.131.154') {
				    window.location.href = "http://"+location.hostname+":"+location.port+"/resource/KDepense7/New_Setup_Page.html";
				} else  {
                    window.location.href = "http://"+location.hostname+":"+location.port+"/resource/KDepense7/New_Setup_Page.html";
                } 
				
			}
        }

        function KOS_OnNeedUpdate(requiredVersion, currentVersion) {
            // 현재 PC에서 실행중인 Kings Online Security의 버전이 낮은 경우 호출되는 이벤트. 필수.
            //window.alert('Kings Online Security의 업데이트가 필요합니다. (' + currentVersion + ' -> ' + requiredVersion + ')');

            $('#installStatus').html('업데이트 필요(Need Update)');
            
            var r = confirm("키보드보안 프로그램이 업데이트되지 않았습니다. 설치페이지로 이동하시겠습니까?");
            if (r == true) {
                var contextPath = location.href.indexOf("ISRY_BackEnd") > -1 ? "ISRY_BackEnd" : "";
                
                if (contextPath == 'ISRY_BackEnd') {
                    window.location.href = "http://"+location.hostname+":"+location.port+"/"+contextPath+"/resource/KDepense7/New_Setup_Page.html";
                } else if (location.hostname == '10.188.131.154') {
                    window.location.href = "http://"+location.hostname+":"+location.port+"/resource/KDepense7/New_Setup_Page.html";
                } else  {
                    window.location.href = "http://"+location.hostname+":"+location.port+"/resource/KDepense7/New_Setup_Page.html";
                } 
            }
        }

        function KOS_OnError(errorCode) {
            // Kings Online Security 작동 중 오류가 발생하면 호출되는 이벤트. 해당 이벤트를 무시 할 경우 키보드보안 비활성화 상태로 이용 가능
            //window.alert('Kings Online Security 작동 중 오류가 발생했습니다. (' + errorCode + ')');
						
        	var eMsg = null;
        	if(errorCode === 'ERROR_UNSUPPORTED_ENVIRONMENT'){
        		eMsg = '[KOS-5004] ERROR_UNSUPPORTED_ENVIRONMENT\nKings Online Security 실행중 오류가 발생했습니다.\n\n해결 방법  : IE, Chrome, Firefox, Opera, Edge등 브라우저 사용\n\n키보드보안 FAQ 이동을 원하실 경우 "확인"버튼을 눌러주세요.';
        	}
        	else if(errorCode === 'ERROR_PROTECTION_FAILED'){
        		eMsg = '[KOS-5003] ERROR_PROTECTION_FAILED\nKings Online Security 실행중 오류가 발생했습니다.\n\n원인 : 통신 오류 및 모듈 비정상 설치\n해결 방법  : 키보드보안 재설치 후 이용\n\n키보드보안 FAQ 이동을 원하실 경우 "확인"버튼을 눌러주세요.'
        	}

        	if(confirm(eMsg)){
        		window.open("http://www.kings.co.kr/k", "Kings Online Security FAQ", "width=1200, height=800" );
        	}
			
            KOS_UpdateNotifyMessage('ERROR');

            $('#installStatus').html('오류 발생(Error) (' + errorCode + ')');
            // TODO 재시도 또는 오류 안내 페이지 등으로 이동
            // 재시도 시에는 이벤트 핸들러를 제공하지 않아도 됨.
            // KOS.init();
        }

        return {
            CONNECTING: KOS_OnConnecting,
            INITIALIZING: KOS_OnInitializing,
            READY: KOS_OnReady,
            NOT_INSTALLED: KOS_OnNotInstalled,
            NEED_UPDATE: KOS_OnNeedUpdate,
            ERROR: KOS_OnError
        };
    }

    function kdfGetSeed_PTEX() {
        try {
            var seed = window.KOS.getSeed();
            if (seed === null) {
                return '';
            }

            return seed;
        } catch (e) {
            return '';
        }
    }
    
	function KOS_GetServerCertificate() {
		var SCert = "-----BEGIN CERTIFICATE-----";
		SCert += "MIIDjzCCAnegAwIBAgIJAMU9mde0sJ/DMA0GCSqGSIb3DQEBCwUAMF4xCzAJBgNV";
		SCert += "BAYTAktSMQ8wDQYDVQQIDAZHeXVuZ2kxDjAMBgNVBAcMBWhhbmFtMQ4wDAYDVQQK";
		SCert += "DAVraWduczEOMAwGA1UECwwFa2luZ3MxDjAMBgNVBAMMBWtpbmdzMB4XDTIxMDky";
		SCert += "OTA1NTU0OFoXDTIzMDkyOTA1NTU0OFowXjELMAkGA1UEBhMCS1IxDzANBgNVBAgM";
		SCert += "Bkd5dW5naTEOMAwGA1UEBwwFaGFuYW0xDjAMBgNVBAoMBWtpZ25zMQ4wDAYDVQQL";
		SCert += "DAVraW5nczEOMAwGA1UEAwwFa2luZ3MwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAw";
		SCert += "ggEKAoIBAQDQGUgpHk2BO8m1ZwK2Nh9cKc+ac8SQirAyx38ho8hosTI1DcpbVdxq";
		SCert += "7ufSpwFJqYh/cFY4HLepLKdYaAg2sEo38x6izDjO+fyg1vTTuZvJg0kpTQ83nr2t";
		SCert += "i3nR9DlBrK9wPh2u87XUr72EFWXjmG2zrNK/YoeI6V8vvmAgUf+tx3QhJ4pQfq6d";
		SCert += "B4mxrvLAHPW0jpi8pmSNOeKOehjCsJhV99dPCqng8eiZFUqNVmNce9mO4LAzuNzt";
		SCert += "uRDmGvCyaP0aFnJbfQITkqPRvMUewXl4qJAK+4QMGoS87LniYyPrQ03axt+/sk2a";
		SCert += "AabvkrwFNIARs0mr7knLE4VRL4TKAs5DAgMBAAGjUDBOMB0GA1UdDgQWBBSHfnUl";
		SCert += "lg9dn3yRTdwKThq01FaTsjAfBgNVHSMEGDAWgBSHfnUllg9dn3yRTdwKThq01FaT";
		SCert += "sjAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBCwUAA4IBAQA7xivIf9NgqT1m38ZG";
		SCert += "yOilG12oyV/mjA9YC5MzqF5mZigvktoZyaVrnyue8aCjOolO97ba/9aj+nfmlDAY";
		SCert += "d4a42edI3zm9Hz64imqa+Aa+ONmS0LllDKCwCRSeZA9JVI+sP2oQlNhXw9cMLtQk";
		SCert += "Vq1dVloIyxlvMNbodxmAY5whUq1EcGJGpgNvY6/sFN2iBIErAESntgWp3ceaqEIH";
		SCert += "zBh/J+NF3UoOwxOEs85bsRIaQElSBKhc30qYiJoYvsIanX10yGJV2NIl9z37klwZ";
		SCert += "9+Q+VhbxlGV0GTi1PuLYgK1ayhr2x6PV25w2HzNfsPT+36I/tXgymFkJCUTPVWl+";
		SCert += "8eFr";
		SCert += "-----END CERTIFICATE-----";
		return SCert;
	}

    /**
     * 입력 필드를 암호화 대상으로 지정합니다.
     *
     * @param {string} formAndInputObjectName 폼 및 입력 필드의 name 속성입니다. 입력 형식은 'formName.inputName' 입니다.
     * @param {string} caseOption 입력 필드 추가 옵션입니다. 입력 형식은 'none' 또는 'onlyNumber' 입니다.
     */
    function regFormEle_K(formAndInputObjectName, caseOption) {
        var delimiterIndex = formAndInputObjectName.indexOf('.');
        if (delimiterIndex < 0) {
            regFormEle_K_NoForm(formAndInputObjectName, caseOption);
            return;
        }

        var formObjectName = formAndInputObjectName.substring(0, delimiterIndex);
        var inputObjectName = formAndInputObjectName.substring(delimiterIndex + 1);

        var formObject = utils.findElementByName(window.document.forms, formObjectName);
        if (utils.isNullOrUndefined(formObject)) {
            return;
        }

        var inputObject = utils.findElementByName(formObject.elements, inputObjectName);
        if (utils.isNullOrUndefined(inputObject)) {
            return;
        }
        inputObject.value = "";
        window.KOS.registerElement(inputObject, caseOption);
    }

    /**
     * 입력 필드를 암호화 대상으로 지정합니다.
     *
     * @param {string} inputObjectName 입력 필드의 name 속성입니다.
     * @param {string} caseOption 입력 필드 추가 옵션입니다. 입력 형식은 'none' 또는 'onlyNumber' 입니다.
     */
    function regFormEle_K_NoForm(inputObjectName, caseOption) {
        var inputObjects = window.document.getElementsByName('INPUT');
        var inputObject = utils.findElementByName(inputObjects, inputObjectName);
        if (utils.isNullOrUndefined(inputObject)) {
            return;
        }

        window.KOS.registerElement(inputObject, caseOption);
    }

	function registerElementEdge(formAndInputObjectName) {
        var delimiterIndex = formAndInputObjectName.indexOf('.');
        if (delimiterIndex < 0) {
            registerElementEdge_NoForm(formAndInputObjectName);
            return;
        }

        var formObjectName = formAndInputObjectName.substring(0, delimiterIndex);
        var inputObjectName = formAndInputObjectName.substring(delimiterIndex + 1);

        var formObject = utils.findElementByName(window.document.forms, formObjectName);
        if (utils.isNullOrUndefined(formObject)) {
            return;
        }

        var inputObject = utils.findElementByName(formObject.elements, inputObjectName);
        if (utils.isNullOrUndefined(inputObject)) {
            return;
        }
		inputObject.setAttribute('kdf-text-protect', 'true');
	};
	
	function registerElementEdge_NoForm(inputObjectName) {
        var inputObjects = window.document.getElementsByName('INPUT');
        var inputObject = utils.findElementByName(inputObjects, inputObjectName);
        if (utils.isNullOrUndefined(inputObject)) {
            return;
        }
		
		inputObject.setAttribute('kdf-text-protect', 'true');
    }
	
    /**
     * 입력 필드에 입력된 암호문을 가져옵니다.
     * 입력 필드가 암호화 대상이 아니거나 암호문이 없는 경우 value 속성을 반환합니다.
     *
     * @param {string} formObject 더 이상 사용되지 않습니다.
     * @param {string} inputObject 암호문을 가져올 입력 필드입니다.
     *
     * @return {string}
     */
    function GetPwdValue_K(formObject, inputObject) {
        var encryptedValue = window.KOS.getEncryptedValue(inputObject);
        if (encryptedValue === null || encryptedValue.length <= 0) {
            return inputObject.value;
        }

        return encryptedValue;
    }

    /**
     * 입력 필드에 입력된 암호문의 길이를 가져옵니다.
     * 입력 필드가 암호화 대상이 아닐 경우 -1이 반환됩니다.
     *
     * @param {string} formObject 더 이상 사용되지 않습니다.
     * @param {string} inputObject 암호문의 길이를 가져올 입력 필드입니다.
     *
     * @return {string}
     */
    function GetPwdValueLength_K(formObject, inputObject) {
        //var encryptedValue = GetPwdValue_K(formObject, inputObject);
        var encryptedValue = getKencValue(inputObject);
        if (encryptedValue === null) {
            return -1;
        }

        return encryptedValue.length;
    }
	
	//hmg 20190618 키보드보안 및 가상키패드 관련 함수 (KB증권)
	function SetPwdValue_K(formObject, inputObject, encryptedValue) {
    	
    	if(encryptedValue.indexOf("knc_flag") >= 0){
	    	
	    	var dummy = '';
	        var dummyLength = (encryptedValue.length - 9);
	        var i;
	        for (i = 0; i < dummyLength; i += 1) {
	            dummy += '~';
	        }
	
	        inputObject.value = dummy;
    	}
    	else{
	    	if((encryptedValue.length) % 24 != 0){
	    		return encryptedValue;
	    	}
	    	
	    	var dummy = '';
	        var dummyLength = (encryptedValue.length) / 24;
	        var i;
	        for (i = 0; i < dummyLength; i += 1) {
	            dummy += '~';
	        }
	
	        inputObject.value = dummy;
	        inputObject.setAttribute('data-kdf-value-ext', encryptedValue);
    	}
	        
	        return dummy;
    }

	
    function getKencValue(inputObject) {
        var encryptedValue = window.KOS.getEncryptedValue(inputObject);
        if (encryptedValue === null || encryptedValue.length <= 0) {
            return inputObject.value;
        }

        return encryptedValue;
    }

    window.KOS_GetConfig = KOS_GetConfig;
    window.KOS_GetKDefenseParameters = KOS_GetKDefenseParameters;
    window.kdfGetSeed_PTEX = kdfGetSeed_PTEX;
    window.KOS_GetGlobalEventHandlers = KOS_GetGlobalEventHandlers;
    window.Get_Cert_var = KOS_GetServerCertificate;
    window.KOS_CoreOnReady = KOS_CoreOnReady;
	


    /**
     * K-Defense 하위 호환 함수
     */
    window.regFormEle_K = regFormEle_K;
	window.registerElementEdge = registerElementEdge;
	window.GetPwdValue_K = GetPwdValue_K;
    window.GetPwdValueLength_K = GetPwdValueLength_K;
    window.SetPwdValue_K = SetPwdValue_K;
    window.getKencValue = getKencValue;
}(this));
