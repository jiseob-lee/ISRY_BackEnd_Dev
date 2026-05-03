/*
	Kings Online Security

	kos-ng.js - Core - 20221112
	
	smk8894@kings.co.kr
	청소년안정망시스템
*/

/* global webcrypto */

(function (window) {
	'use strict';

	// 로그 메시지 출력
	var logLevel = 4; // 0: disabled, 1: info, 2: warn, 3: error, 4: debug

	// 유틸리티 함수
	var utils = (function () {
		var domReady = false;
		var domContentLoaded = false;
		
		function getJsPath() {
			var scriptList = document.getElementsByTagName("script");
			var num = 0;
			
			for(var i=0;i<scriptList.length;i++){
				if(utils.contains(scriptList[i].src,'kos-ng.js')){
					num = i;
					break;
				}
			}
			
			var fileName = scriptList[num].getAttribute('src');
			
			var token = fileName.split('/');
			var str = '';
			for(var i=0; i<token.length-1; i++) {
				str += token[i];
				if(token.length-2 != i)
					str += '/';
			}
			
			return str;
		}

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

		function waitDOM(callback) {
			function isDOMParsed(readyState) {
				switch (readyState) {
				case 'interactive':
				case 'complete':
					return true;
				default:
					return false;
				}
			}

			function onReady() {
				if (!domReady) {
					domReady = true;
				}

				callback();
			}

			function onDOMContentLoaded() {
				domContentLoaded = true;

				window.document.removeEventListener('DOMContentLoaded', onDOMContentLoaded);
				onReady();
			}

			function onReadyStateChange() {
				if (isDOMParsed(window.document.readyState)) {
					if (window.document.removeEventListener) {
						window.document.removeEventListener('readystatechange', onReadyStateChange);
					} else {
						window.document.detachEvent('onreadystatechange', onReadyStateChange);
					}

					if (domContentLoaded) {
						return;
					}

					if (window.document.removeEventListener) {
						window.document.removeEventListener('DOMContentLoaded', onDOMContentLoaded);
					}

					onReady();
				}
			}

			function onDoScroll() {
				if (domReady) {
					return;
				}

				try {
					window.document.documentElement.doScroll("left");
				} catch (e) {
					window.setTimeout(onDoScroll, 0);
					return;
				}

				onReady();
			}

			if (domReady) {
				onReady();
				return;
			}

			if (isDOMParsed(window.document.readyState)) {
				onReady();
				return;
			}

			if (window.document.addEventListener) {
				window.document.addEventListener('DOMContentLoaded', onDOMContentLoaded);
				window.document.addEventListener('readystatechange', onReadyStateChange);
			} else {
				window.document.attachEvent('onreadystatechange', onReadyStateChange);

				if (window.document.documentElement.doScroll && window === window.top) {
					onDoScroll();
				}
			}
		}

		function waitSomething(checkCallback, completeCallback) {
			function doCheck() {
				if (!checkCallback()) {
					window.setTimeout(doCheck, 0);
					return;
				}

				completeCallback();
			}

			if (checkCallback()) {
				completeCallback();
			} else {
				window.setTimeout(doCheck, 0);
			}
		}

		function waitConfig(completeCallback) {
			waitSomething(function () {
				return !isNullOrUndefined(window.KOS_GetConfig);
			}, completeCallback);
		}

		function ExecutionSync() {
			var base = this;

			var isBusy = false;
			var taskQueue = [];

			function onSyncComplete() {
				isBusy = false;

				if (taskQueue.length > 0) {
					executeQueuedTasks();
				}
			}

			function executeQueuedTasks() {
				var task = taskQueue.shift();

				isBusy = true;

				try {
					task(onSyncComplete);
				} catch (e) {}
			}

			base.synchronize = function (task) {
				taskQueue.push(task);

				if (!isBusy) {
					executeQueuedTasks();
				}
			};
		}

		function addEventListener(object, event, handler, useCapture) {
			if (object.addEventListener) {
				object.addEventListener(event, handler, useCapture);
			} else if (object.attachEvent) {
				object.attachEvent('on' + event, handler, useCapture);
			}
		}

		function removeEventListener(object, event, handler, useCapture) {
			if (object.addEventListener) {
				object.removeEventListener(event, handler, useCapture);
			} else if (object.attachEvent) {
				object.detachEvent('on' + event, handler, useCapture);
			}
		}

		function createEvent(eventType, eventArgs) {
			var event = null;

			try {
				event = new window.Event(eventType, {
					bubbles: true,
					cancelable: true
				});
			} catch (e) {
				try {
					event = window.document.createEvent('Event');
					event.initEvent(eventType, true, true);
				} catch (e2) {}
			}

			if (event !== null) {
				for (var arg in eventArgs) {
					if (eventArgs.hasOwnProperty(arg)) {
						event[arg] = eventArgs[arg];
					}
				}
			}

			return event;
		}

		function dispatchKeyboardEvent(object, eventType, sourceEvent) {
			var event = createEvent(eventType, {
				charCode: sourceEvent.charCode,
				keyCode: sourceEvent.keyCode,
				which: sourceEvent.which,
				code: sourceEvent.code,
				key: sourceEvent.key,
				repeat: sourceEvent.repeat,

				altKey: sourceEvent.altKey,
				ctrlKey: sourceEvent.ctrlKey,
				shiftKey: sourceEvent.shiftKey,
				metaKey: sourceEvent.metaKey,

				isTriggeredByKDefense: true
			});

			return object.dispatchEvent(event);
		}

		function dispatchInputEvent(object) {
			var event = createEvent('input', {});

			return object.dispatchEvent(event);
		}

		function dispatchChangeEvent(object) {
			var event = createEvent('change', {});

			return object.dispatchEvent(event);
		}

		function hasAttribute(object, attribute) {
			if (object.hasAttribute) {
				return object.hasAttribute(attribute);
			} else {
				return typeof object[attribute] !== 'undefined';
			}
		}

		function getAttribute(object, attribute, defaultValue) {
			if (!hasAttribute(object, attribute)) {
				if (isNullOrUndefined(defaultValue)) {
					return null;
				} else {
					return defaultValue;
				}
			}

			return object.getAttribute(attribute);
		}

		function getAttributeBoolean(object, attribute) {
			return getAttribute(object, attribute, 'false') === 'true';
		}

		function setAttribute(object, attribute, value) {
			object.setAttribute(attribute, value);
		}

		function sessionStorageIsAvailable() {
			try {
				var test = '__kos_session_storage_test__';
				window.sessionStorage.setItem(test, test);
				window.sessionStorage.getItem(test);
				window.sessionStorage.removeItem(test);
			} catch (e) {
				return false;
			}

			return true;
		}

		function sessionStorageSetItem(key, value) {
			try {
				window.sessionStorage.setItem(key, value);
			} catch (e) {}
		}

		function sessionStorageGetItem(key) {
			try {
				return window.sessionStorage.getItem(key);
			} catch (e) {}

			return null;
		}

		function sessionStorageRemoveItem(key) {
			try {
				window.sessionStorage.removeItem(key);
			} catch (e) {}
		}

		function getActiveElement(childWindow) {
			var activeElement = null;

			if (childWindow) {
				activeElement = childWindow.document.activeElement;
			} else {
				activeElement = window.document.activeElement;
			}

			if (activeElement.contentWindow) {
				return getActiveElement(activeElement.contentWindow);
			}

			return activeElement;
		}

		function getElementIdentifier(object) {
			if (utils.isNullOrUndefined(object)) {
				return 'undefined';
			}

			var formIdentifier = '';
			var objectIdentifier = object.name || object.id || '';

			if (!utils.isNullOrUndefined(object.form)) {
				formIdentifier = object.form.name || object.form.id || '';
			}

			if (objectIdentifier.length === 0) {
				objectIdentifier = object.tagName || '';
				return objectIdentifier;
			}

			if (formIdentifier.length > 0) {
				return formIdentifier + '.' + objectIdentifier;
			} else {
				return objectIdentifier;
			}
		}

		function logLevelToString(level) {
			switch (level) {
			case 2:
				return '[WARN] ';
			case 3:
				return '[ERROR]';
			case 4:
				return '[DEBUG]';
			default:
				return '[INFO] ';
			}
		}

		function log(level, msg) {
			var currentLogLevel = logLevel;
			if (!isNullOrUndefined(window.KOS_GetConfig)) {
				currentLogLevel = window.KOS_GetConfig().logLevel;
			}

			if (currentLogLevel < level) {
				return;
			}

			function pad(number) {
				if (number < 10) {
					return '0' + number;
				}

				return number;
			}

			function getTimeString() {
				var now = new Date();

				return now.getFullYear() + '-' +
					pad(now.getMonth() + 1) + '-' +
					pad(now.getDate()) + ' ' +
					pad(now.getHours()) + ':' +
					pad(now.getMinutes()) + ':' +
					pad(now.getSeconds()) + '.' +
					(now.getMilliseconds() / 1000).toFixed(3).slice(2, 5);
			}

			try {
				var logMsg = '[KOSBridge] ' + logLevelToString(level) + ' [' + getTimeString() + '] ' + msg;

				if (window.console) {
					window.console.log(logMsg);
				}

				var logElement = window.document.getElementById('kos-ng-log');
				if (logElement !== null) {
					logElement.innerHTML += logMsg + '<br>';
				}
			} catch (e) {}
		}

		function logInfo(msg) {
			log(1, msg);
		}

		function logWarn(msg) {
			log(2, msg);
		}

		function logError(msg) {
			log(3, msg);
		}

		function logDebug(msg) {
			log(4, msg);
		}

		function getTopTitle() {
			var title = window.document.title;

			try {
				title = window.top.document.title;
			} catch (e) {
				// Same-origin policy 위반
			}

			return title;
		}

		function getTopWindow() {
			var parentWindow = window;

			try {
				while (parentWindow !== parentWindow.parent) {
					var parentBody = parentWindow.parent.document.body;
					if (isNullOrUndefined(parentBody) || !utils.compareNoCase(parentBody.tagName, 'BODY')) {
						break;
					}

					parentWindow = parentWindow.parent;
				}
			} catch (e) {}

			return parentWindow;
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

		function isWindows() {
			var result = false;

			var userAgent = window.navigator.userAgent;

			var containsWindows = contains(userAgent, 'Windows');
			var containsWin = contains(userAgent, 'Win');
			var containsUnixLike = contains(userAgent, 'Linux') || contains(userAgent, 'X11') || contains(userAgent, 'BSD');
			var containsApple = contains(userAgent, 'Macintosh') || contains(userAgent, 'Mac') || contains(userAgent, 'OS X');

			if (containsWindows) {
				result = true;
			}

			if (!(containsUnixLike || containsApple) && containsWin) {
				result = true;
			}

			return result;
		}

		function isNumericKey(character) {
			return "0123456789".indexOf(character) >= 0;
		}

		function isAlphaUpperKey(character) {
			return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(character) >= 0;
		}

		function isAlphaLowerKey(character) {
			return "abcdefghijklmnopqrstuvwxyz".indexOf(character) >= 0;
		}

		function isAlphaKey(character) {
			return isAlphaUpperKey(character) || isAlphaLowerKey(character);
		}

		function isSpecialKey(character) {
			return "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ ".indexOf(character) >= 0;
		}

		function isAllowedKey(character, onlyUpperCase) {
			if (onlyUpperCase) {
				return isNumericKey(character) || isAlphaUpperKey(character) || isSpecialKey(character);
			} else {
				return isNumericKey(character) || isAlphaKey(character) || isSpecialKey(character);
			}
		}

		function createXmlHttpRequest() {
			var factories = [function () {
				return new window.ActiveXObject('Microsoft.XMLHTTP');
			}, function () {
				return new window.ActiveXObject('Msxml3.XMLHTTP');
			}, function () {
				return new window.ActiveXObject('Msxml2.XMLHTTP');
			}];

			var requestObject = null;

			for (var i = 0; i < factories.length; i += 1) {
				try {
					requestObject = factories[i]();
					break;
				} catch (e) {}
			}

			return requestObject;
		}

		function makeQueryString(object) {
			var parameters = [];
			var parameterName;

			for (parameterName in object) {
				if (object.hasOwnProperty(parameterName)) {
					parameters.push(encodeURIComponent(parameterName) + '=' + encodeURIComponent(object[parameterName]));
				}
			}

			return parameters.join('&');
		}

		function makeParameterString(object, separator) {
			var parameters = [];
			var parameterName;

			for (parameterName in object) {
				if (object.hasOwnProperty(parameterName)) {
					parameters.push(parameterName + '=' + object[parameterName]);
				}
			}

			return parameters.join(separator) + separator;
		}
		
		var exports = {};
		exports.isNullOrUndefined = isNullOrUndefined;
		exports.contains = contains;
		exports.startsWith = startsWith;
		exports.compareNoCase = compareNoCase;

		exports.waitDOM = waitDOM;
		exports.waitSomething = waitSomething;
		exports.waitConfig = waitConfig;
		exports.ExecutionSync = ExecutionSync;

		exports.addEventListener = addEventListener;
		exports.removeEventListener = removeEventListener;

		exports.dispatchKeyboardEvent = dispatchKeyboardEvent;
		exports.dispatchInputEvent = dispatchInputEvent;
		exports.dispatchChangeEvent = dispatchChangeEvent;

		exports.hasAttribute = hasAttribute;
		exports.getAttribute = getAttribute;
		exports.getAttributeBoolean = getAttributeBoolean;
		exports.setAttribute = setAttribute;
		exports.getJsPath = getJsPath;

		exports.sessionStorage = {
			isAvailable: sessionStorageIsAvailable,
			setItem: sessionStorageSetItem,
			getItem: sessionStorageGetItem,
			removeItem: sessionStorageRemoveItem
		};

		exports.getActiveElement = getActiveElement;
		exports.getElementIdentifier = getElementIdentifier;

		exports.log = {
			info: logInfo,
			warn: logWarn,
			error: logError,
			debug: logDebug
		};

		exports.getTopTitle = getTopTitle;
		exports.getTopWindow = getTopWindow;
		exports.getBrowserType = getBrowserType;
		exports.isInternetExplorer = isInternetExplorer;
		exports.isLegacyBrowser = isLegacyBrowser;
		exports.isWindows = isWindows;

		exports.isNumericKey = isNumericKey;
		exports.isAlphaUpperKey = isAlphaUpperKey;
		exports.isAlphaLowerKey = isAlphaLowerKey;
		exports.isAlphaKey = isAlphaKey;
		exports.isSpecialKey = isSpecialKey;
		exports.isAllowedKey = isAllowedKey;

		exports.createXmlHttpRequest = createXmlHttpRequest;

		exports.makeQueryString = makeQueryString;
		exports.makeParameterString = makeParameterString;

		return exports;
	}());

	function BridgeHTTPSWorker(windowt, config, bridgeFrame, bridgeOrigin) {
		var base = this;

		var isBusy = false;
		var requestQueue = [];
		var requestIdCounter = 0;
		var responseQueue = [];

		function processRequestQueue() {
			if (requestQueue.length === 0 || isBusy) {
				return;
			}

			var request = requestQueue.shift();
			request.requestId = requestIdCounter;
			requestIdCounter += 1;

			request.timeout = windowt.setTimeout(function () {
				if (utils.isNullOrUndefined(responseQueue[request.requestId])) {
					return;
				}

				utils.log.warn('requestId ' + request.requestId + ' 타임아웃');
				isBusy = false;
				processRequestQueue();
			}, config.network.timeout.request);

			responseQueue[request.requestId] = request;

			var message = JSON.stringify({
				requestId: request.requestId,
				data: {
					method: request.method,
					url: request.url,
					data: request.data
				},
				timeout: config.network.timeout.request
			});

			isBusy = true;

			try {
				bridgeFrame.contentWindow.postMessage(message, bridgeOrigin);
			} catch (e) {
				utils.log.error('postMessage 실패: ' + e);
				isBusy = false;
				processRequestQueue();
			}
		}

		function onMessageReceived(event) {
			if (event.origin !== null && bridgeOrigin !== null && event.origin !== bridgeOrigin) {
				return;
			}

			var request = null;

			try {
				var message = JSON.parse(event.data);

				var requestId = message.requestId;
				request = responseQueue[requestId];
				responseQueue[requestId] = null;

				if (!utils.isNullOrUndefined(request)) {
					windowt.clearTimeout(request.timeout);

					var data = message.data;
					var status = data.status;

					if (status !== 200) {
						throw 'Invalid status code (' + status + ')';
					}

					var response = JSON.parse(data.responseText);

					try {
						request.responseCallback(response);
					} catch (e) {}
				}
			} catch (e) {
				utils.log.error('onMessageReceived: ' + e);

				if (request !== null) {
					try {
						request.responseCallback({
							result: 'ERROR_INVALID_RESPONSE',
							message: e
						});
					} catch (e) {}
				}
			}

			isBusy = false;
			processRequestQueue();
		}

		base.init = function () {
			utils.addEventListener(windowt, 'message', onMessageReceived);

			isBusy = false;
			requestQueue = [];
			responseQueue = [];
		};

		base.cleanup = function () {
			utils.removeEventListener(windowt, 'message', onMessageReceived);
		};

		base.sendData = function (method, url, data, responseCallback) {
			var request = {
				method: method,
				url: url,
				data: data,
				responseCallback: responseCallback
			};

			requestQueue.push(request);
			processRequestQueue();
		};

		base.init();
	}

	// HTTPS 구현
	function BridgeHTTPS(window, config) {
		var base = this;

		var state = 'NOT_CONNECTED';
		
		var installCheck = {
			kIns: false,
			iIns: false
		};
		
		var serviceVer = 20010801;

		var worker = null;

		function findServicePort(responseCallback) {
			var totalCount = config.network.ports.length;
			var errorCount = 0;
			var portFound = false;

			function sendPing(port, responseCallback) {
				var pingURL = config.network.protocol + '://' + config.network.host + ':' + port + '/images/ping.png?' + new Date().getTime();
				var responseCallbackCalled = false;

				var imgObject = window.document.createElement('IMG');
				utils.addEventListener(imgObject, 'load', function () {
					if (responseCallbackCalled) {
						return;
					}

					responseCallbackCalled = true;

					responseCallback({
						result: 'OK',
						port: port
					});
				});
				utils.addEventListener(imgObject, 'error', function () {
					if (responseCallbackCalled) {
						return;
					}

					responseCallback({
						result: 'ERROR_CONNECTION_FAILED'
					});
				});

				imgObject.src = pingURL;
			}

			function pingServicePort(port) {
				sendPing(port, function (response) {
					if (portFound) {
						return;
					}

					if (response.result !== 'OK') {
						errorCount += 1;
						if (errorCount >= totalCount) {
							responseCallback({
								result: 'NOT_INSTALLED'
							});
						}
					} else {
						portFound = true;

						responseCallback({
							result: 'OK',
							port: port
						});
					}
				});
			}

			function pingAllServicePort() {
				for (var i = 0; i < totalCount; i += 1) {
					pingServicePort(config.network.ports[i]);
				}
			}

			var knownPort = parseInt(utils.sessionStorage.getItem('__kos_service_known_port__'));
			if (isNaN(knownPort)) {
				pingAllServicePort();
			} else {
				var isKnownPort = false;
				for (var i = 0; i < config.network.ports.length; i += 1) {
					if (config.network.ports[i] === knownPort) {
						isKnownPort = true;
						break;
					}
				}

				if (!isKnownPort) {
					pingAllServicePort();
					return;
				}

				sendPing(knownPort, function (response) {
					if (response.result !== 'OK') {
						pingAllServicePort();
						return;
					}

					responseCallback({
						result: 'OK',
						port: knownPort
					});
				});
			}
		}

		function createHiddenFrame() {
			var bridgeFrame = window.document.createElement('IFRAME');

			// 웹 브라우저 렌더링 엔진 호환성
			// display: none으로 변경하지 말 것.
			bridgeFrame.style.position = 'absolute';
			bridgeFrame.style.left = '-1000px';
			bridgeFrame.style.top = '-1000px';
			bridgeFrame.width = 100;
			bridgeFrame.title = 'Kings Online Security Bridge';

			return bridgeFrame;
		}

		function connectFrame(port, timeout, responseCallback) {
			var bridgeFrame = createHiddenFrame();
			var bridgeOrigin = config.network.protocol + '://' + config.network.host + ':' + port;

			var bridgeFrameTimeout = null;
			var bridgeFrameEventRaised = false;

			function bridgeFrameEventHandler(type) {
				if (bridgeFrameEventRaised) {
					return;
				}

				bridgeFrameEventRaised = true;
				window.clearTimeout(bridgeFrameTimeout);

				utils.log.info('프레임 이벤트 발생: ' + type);

				switch (type) {
				case 'ERROR':
					responseCallback({
						result: 'ERROR_BRIDGE_NOT_RESPONDING'
					});

					return;
				case 'TIMED_OUT':
					responseCallback({
						result: 'ERROR_OPERATION_TIMED_OUT'
					});

					return;
				}

				responseCallback({
					result: 'OK',
					worker: new BridgeHTTPSWorker(window, config, bridgeFrame, bridgeOrigin)
				});
			}

			utils.addEventListener(bridgeFrame, 'load', function () {
				bridgeFrameEventHandler('LOAD');
			});

			utils.addEventListener(bridgeFrame, 'error', function () {
				bridgeFrameEventHandler('ERROR');
			});

			bridgeFrameTimeout = window.setTimeout(function () {
				bridgeFrameEventHandler('TIMED_OUT');
			}, timeout);

			bridgeFrame.src = bridgeOrigin + config.network.bridgePath + '?' + new Date().getTime() + '&proxy=' + encodeURIComponent('about:blank');
			window.document.body.appendChild(bridgeFrame);
		}

		function sendHandshake(responseCallback) {
			var parameters = {};
			parameters.bridgeMinVer = config.version.bridge;
			parameters.serviceMinVer = config.version.service;

			worker.sendData('POST', '/handshake', utils.makeQueryString(parameters), function (response) {
				// 노멀라이즈
				if (utils.isNullOrUndefined(response.needUpdate) || utils.isNullOrUndefined(response.bridgeVersion) || utils.isNullOrUndefined(response.serviceVersion)) {
					responseCallback({
						result: 'ERROR_HANDSHAKE_FAILED'
					});
					return;
				}
				
				if(!utils.isNullOrUndefined(response.kdfinstall)){
					installCheck.kIns = response.kdfinstall;
					installCheck.iIns = response.idfinstall;
				}

				responseCallback({
					result: 'OK',
					needUpdate: response.needUpdate,
					bridgeVersion: response.bridgeVersion,
					serviceVersion: response.serviceVersion,
					installCheck: installCheck
				});
			});
		}

		function handshake(port, responseCallback) {
			var retryCount = 0;

			function connectFrameResponseCallback(response) {
				if (response.result !== 'OK') {
					retryCount += 1;

					if (retryCount < 5) {
						utils.log.info('재시도중');

						connectFrame(port, 2000 + 500 * retryCount, connectFrameResponseCallback);
						return;
					}

					responseCallback({
						result: 'NOT_INSTALLED'
					});
					return;
				}

				utils.log.info('프레임 연결됨');
				worker = response.worker;

				sendHandshake(responseCallback);
			}

			connectFrame(port, 2000, connectFrameResponseCallback);
		}

		base.connect = function (responseCallback) {
			utils.waitSomething(function () {
				return state !== 'CONNECTING';
			}, function () {
				if (state === 'CONNECTED') {
					responseCallback({
						result: 'OK',
						installCheck: installCheck
					});

					return;
				}

				state = 'CONNECTING';

				findServicePort(function (response) {
					if (response.result === 'OK') {
						utils.log.info('서비스 포트 ' + response.port + '에 연결중');
						utils.sessionStorage.setItem('__kos_service_known_port__', response.port);

						if (utils.isNullOrUndefined(window.document.body)) {
							state = 'NOT_CONNECTED';

							responseCallback({
								result: 'ERROR_DOCUMENT_BODY_IS_NULL',
								installCheck: installCheck
							});
							return;
						}

						handshake(response.port, function (response) {
							if(!utils.isNullOrUndefined(response.kdfinstall)){
								installCheck.kIns = response.kdfinstall;
								installCheck.iIns = response.idfinstall;
							}
							
							if (response.result !== 'OK') {
								state = 'NOT_CONNECTED';

								if (worker !== null) {
									worker.cleanup();
									worker = null;
								}

								responseCallback({
									result: response.result,
									installCheck: installCheck
								});
								return;
							}
								
							utils.log.info('업데이트 필요 여부: ' + response.needUpdate);
							utils.log.info('실행중인 서비스 버전: ' + response.serviceVersion);
							utils.log.info('실행중인 브릿지 버전: ' + response.bridgeVersion);

							if (response.needUpdate) {
								var requiredVersion = 0;
								var currentVersion = 0;

								if (config.version.service > response.serviceVersion) {
									requiredVersion = config.version.service;
									currentVersion = response.serviceVersion;
								} else {
									requiredVersion = config.version.bridge;
									currentVersion = response.bridgeVersion;
								}

								state = 'NOT_CONNECTED';

								responseCallback({
									result: 'NEED_UPDATE',
									requiredVersion: requiredVersion,
									currentVersion: currentVersion,
									installCheck: installCheck
								});
								return;
							}

							serviceVer = response.serviceVersion;
							
							state = 'CONNECTED';

							responseCallback({
								result: 'OK',
								installCheck: installCheck
							});
						});
					} else {
						state = 'NOT_CONNECTED';
						utils.sessionStorage.removeItem('__kos_service_known_port__');

						responseCallback({
							result: response.result,
							installCheck: installCheck
						});
					}
				});
			});
		};

		base.isSupported = function () {
			return !utils.isLegacyBrowser();
		};

		base.isConnected = function () {
			return state === 'CONNECTED';
		};

		base.sendData = function (method, url, data, responseCallback) {
			if (state !== 'CONNECTED') {
				utils.log.warn('HTTPS.sendData 연결되지 않음');
				return;
			}

			worker.sendData(method, url, data, responseCallback);
		};
		
		base.serviceVered = function (){
			return serviceVer;
		}
		
		base.installChecked = function (){
			return installCheck;
		};
	}

	// Protocol Handler 구현
	function BridgeProtocolHandler(window, config) {
		var base = this;

		var state = 'NOT_CONNECTED';
		
		var installCheck = {
				kIns: false,
				iIns: false
			};

		var documentHead = window.document.head || window.document.getElementsByTagName('HEAD')[0];

		function makeCallbackName() {
			return 'KOSIELoaderResponse_' + (new Date().getTime() + Math.floor(Math.random() * 10000)).toString(16);
		}

		function makeRequestHead(operation) {
			var params = 'kos-loader://' + window.location.host + '/?op=' + operation + '&t=' + new Date().getTime();

			if (window.location.protocol.toLowerCase() === 'https:') {
				params += '&s=1';
			}

			return params;
		}

		function sendRequest(url, responseCallback) {
			var script = null;
			var callbackName = makeCallbackName();

			function callResultCallback(isResponse, responseText) {
				if (script !== null) {
					if (!utils.isNullOrUndefined(script.readyState)) {
						script.onreadystatechange = null;
					}

					try {
						documentHead.removeChild(script);
					} catch (e) {}

					if (!utils.isNullOrUndefined(window[callbackName])) {
						window[callbackName] = undefined;
					}
				}

				try {
					var response;

					if (isResponse) {
						response = JSON.parse(responseText);
						if (utils.isNullOrUndefined(response.result)) {
							throw 'No result';
						}

						try {
							responseCallback(response);
						} catch (e) {}
					} else {
						response = {
							result: responseText
						};

						try {
							responseCallback(response);
						} catch (e) {}
					}
				} catch (e) {
					responseCallback({
						result: 'ERROR_INVALID_RESPONSE'
					});
				}
			}

			var requestCompleteCallback = function (responseText) {
				callResultCallback(true, responseText);
			};

			try {
				var xhr = utils.createXmlHttpRequest();
				if (xhr === null) {
					// Script
					script = window.document.createElement('SCRIPT');

					if (!utils.isNullOrUndefined(script.readyState)) {
						script.onreadystatechange = function () {
							if (script.readyState === 'complete') {
								callResultCallback(false, 'ERROR_REQUEST_FAILED');
							} else {
								callResultCallback(false, 'ERROR_HANDLER_NOT_INSTALLED');
							}
						};
					} else {
						script.onerror = function () {
							callResultCallback(false, 'ERROR_HANDLER_NOT_INSTALLED');
						};
					}

					window[callbackName] = requestCompleteCallback;

					script.type = 'text/javascript';
					script.src = url + '&c=' + callbackName;

					documentHead.appendChild(script);
				} else {
					// XHR
					xhr.onreadystatechange = function () {
						if (xhr.readyState === 4) {
							requestCompleteCallback(xhr.responseText);
						}
					};

					xhr.open('GET', url);
					xhr.send();
				}
			} catch (e) {
				callResultCallback(false, 'ERROR_HANDLER_NOT_INSTALLED');
			}
		}

		base.connect = function (responseCallback) {
			utils.waitSomething(function () {
				return state !== 'CONNECTING';
			}, function () {
				if (state === 'CONNECTED') {
					responseCallback({
						result: 'OK',
						installCheck: installCheck
					});

					return;
				}

				var parameters = {};
				parameters.v = config.version.handler;

				base.sendData('handshake', utils.makeQueryString(parameters), function (response) {
					if(!utils.isNullOrUndefined(response.kdfinstall)){
						installCheck.kIns = response.kdfinstall;
						installCheck.iIns = response.idfinstall;
					}
					
					if (response.result === 'OK') {
						
						if (utils.isNullOrUndefined(response.currentVersion)) {
							state = 'NOT_CONNECTED';

							responseCallback({
								result: 'NEED_UPDATE',
								requiredVersion: config.version.handler,
								currentVersion: 0,
								installCheck: installCheck
							});

							return;
						}

						utils.log.info('실행중인 핸들러 버전: ' + response.currentVersion);

						state = 'CONNECTED';
						
						responseCallback({
							result: 'OK',
							installCheck: installCheck
						});
					} else {
						state = 'NOT_CONNECTED';

						if (response.result === 'NEED_UPDATE') {
							responseCallback({
								result: 'NEED_UPDATE',
								requiredVersion: config.version.handler,
								currentVersion: response.currentVersion,
								installCheck: installCheck
							});
						} else {
							responseCallback({
								result: 'NOT_INSTALLED',
								installCheck: installCheck
							});
						}
					}
				});
			});
		};

		base.isSupported = function () {
			if (!utils.isLegacyBrowser() && config.disableProtocolHandler) {
				return false;
			}

			return utils.getBrowserType() === 'IE';
		};

		base.isConnected = function () {
			return state === 'CONNECTED';
		};

		base.sendData = function (operation, data, responseCallback) {
			var request = makeRequestHead(operation) + '&' + data;
			sendRequest(request, responseCallback);
		};
		
		base.installChecked = function (){
			return installCheck;
		};
	}

	// Namo CrossEditor 호환 기능
	function NamoSEMonitor() {
		var base = this;

		var controlMonitorID = null;
		var controls = [];

		var onControlInitialized = null;

		function controlMonitor() {
			var controlInitedIndexes = [];

			var controlIndex;

			for (controlIndex in controls) {
				try {
					var control = controls[controlIndex];

					var namoIFrame;
					var namoEditorFrame;

					namoIFrame = control.ownerWindow.document.getElementById('NamoSE_Ifr__' + control.controlID);
					namoEditorFrame = namoIFrame.contentWindow.document.getElementById('NamoSE_editorframe_' + control.controlID);

					if (!namoEditorFrame.contentWindow.document.body.contentEditable) {
						continue;
					}

					controlInitedIndexes.push(controlIndex);
				} catch (e) {}
			}

			for (var i = 0; i < controlInitedIndexes.length; i += 1) {
				controls.splice(controlInitedIndexes[i], 1);
			}

			if (controlInitedIndexes.length > 0) {
				onControlInitialized();
			}

			if (controls.length) {
				window.clearInterval(controlMonitorID);
				controlMonitorID = null;
			}
		}

		function registerControl(ownerWindow, controlID) {
			utils.log.info('NamoSEMonitor.registerControl 컨트롤 ' + controlID + ' 등록');

			if (controlMonitorID === null) {
				controlMonitorID = window.setInterval(controlMonitor, 250);
			}

			controls.push({
				ownerWindow: ownerWindow,
				controlID: controlID
			});
		}

		function createHookCallback(window, originalCallback) {
			return function (event) {
				utils.log.info('NamoSEMonitor.hookCallback 호출됨: ' + window.location.href);

				if (originalCallback !== null) {
					utils.log.info('NamoSEMonitor.hookCallback 원본 OnInitCompleted 호출');

					try {
						originalCallback(event);
					} catch (e) {}
				}

				registerControl(window, event.editorName);
			};
		}

		base.onInitialized = function (callback) {
			onControlInitialized = callback;
		};

		base.addWindow = function (window) {
			utils.log.info('NamoSEMonitor.addWindow 호환 모드 창 추가: ' + window.location.href);

			var originalCallback = null;

			if (!utils.isNullOrUndefined(window.OnInitCompleted)) {
				originalCallback = window.OnInitCompleted;
			}

			window.OnInitCompleted = createHookCallback(window, originalCallback);
		};
	}
	
	// TagFree Editor 호환 기능
	function TagFreeMonitor() {
		var base = this;

		var TagMonitorID = null;
		var Tags = [];
		var TagIndex = 0;
		var onTagInitialized = null;

		function TagMonitor() {
			try {

				var xFreeEditorFrame;
				var xFreeIFrame;
				
				xFreeEditorFrame = Tags.ownerWindow.document.getElementById('editorSignBox');
				xFreeIFrame = xFreeEditorFrame.ownerWindow.document.getElementById('TagfreeScriptEditor');
					
				if (xFreeIFrame.contentWindow.document.body.contentEditable) {
					onTagInitialized();
				}
					
			} catch (e) {}

			if (TagIndex = 10) {
				window.clearInterval(TagMonitorID);
				TagMonitorID = null;
			}
			
			TagIndex += 1;
		}

		function registerControl(ownerWindow) {
			utils.log.info('TagMonitor.registerControl 컨트롤 등록');

			if (TagMonitorID === null) {
				TagMonitorID = window.setInterval(TagMonitor, 500);
			}
			
			Tags.push({
				ownerWindow: ownerWindow
			});
		}

		base.onInitialized = function (callback) {
			onTagInitialized = callback;
		};
		
		base.registerWindow = function (window) {
			utils.log.info('TagMonitor.registerWindow 호환 모드 창 등록: ' + window.location.href);

			registerControl(window);
		};
	}
	
	// CK Editor 호환 기능
	function CKEditMonitor() {
		var base = this;

		var CKMonitorID = null;
		var CK = [];
		var CKIndex = 0;
		var onCKInitialized = null;

		function CKMonitor() {
			try {

				var CKEditorFrame;
				var CKIFrame;
				
				CKEditorFrame = CK[0].ownerWindow.document.getElementsByClassName('cke')[0];
				CKIFrame = CKEditorFrame.getElementsByClassName('cke_wysiwyg_frame cke_reset')[0];
					
				if (CKIFrame.contentWindow.document.body.contentEditable) {
					CKIndex = 10;
					onCKInitialized();
				}
					
			} catch (e) {}

			if (CKIndex == 10) {
				window.clearInterval(CKMonitorID);
				CKMonitorID = null;
			}
			
			CKIndex += 1;
		}
		
		function registerControl(ownerWindow) {
			utils.log.info('CKMonitor.registerControl 컨트롤 등록');

			if (CKMonitorID === null) {
				CKMonitorID = window.setInterval(CKMonitor, 500);
			}
			
			CK.push({
				ownerWindow: ownerWindow
			});
		}

		base.onInitialized = function (callback) {
			onCKInitialized = callback;
		};
		
		base.registerWindow = function (window) {
			utils.log.info('CKMonitor.registerWindow 호환 모드 창 등록: ' + window.location.href);

			registerControl(window);
		};
	}
	
	// Bridge 클래스
	function Bridge(window, srcWindow) {
		var base = this;
		var products = [];

		var implHTTPS = null;
		var implHandler = null;

		var config = null;
		var globalEventHandler = null;
		var userEventHandler = null;

		var isReady = false;
		var isAutoInit = false;

		var namoSEMonitor = null;
		
		var installModeAuto = true;

		var execSync = {
			connect: new utils.ExecutionSync()
		};

		base.isKOSChrome = !utils.isNullOrUndefined(window.chrome) && !utils.isNullOrUndefined(window.navigator.userAgentData);

		function checkBrowserPolicy() {
			var browserType = utils.getBrowserType();
			var i;

			for (i = 0; i < config.webBrowsers.disabled.length; i += 1) {
				if (config.webBrowsers.disabled[i] === browserType) {
					return 'DISABLED';
				}
			}

			for (i = 0; i < config.webBrowsers.unsupported.length; i += 1) {
				if (config.webBrowsers.unsupported[i] === browserType) {
					return 'UNSUPPORTED';
				}
			}

			if (!utils.isWindows() || (!implHTTPS.isSupported() && !implHandler.isSupported())) {
				return 'UNSUPPORTED';
			}

			return 'OK';
		}

		// Prevent BF-Cache
		utils.addEventListener(window, 'pageshow', function (event) {
			if (event.persisted) {
				utils.log.info('pageshow 이벤트 발생');
				if(installModeAuto){
					base.init();
				}else{
					base.initEx();
				}
			}
		});

		utils.addEventListener(window, 'unload', function (event) {});

		// DOM 및 설정 준비시 호출됨
		base.onReady = function (currentWindow) {
			utils.log.info('onReady 호출됨');

			if (!isReady) {
				utils.log.info('설정 불러옴');

				isReady = true;
				config = srcWindow.KOS_GetConfig();
				
				if(!utils.isNullOrUndefined(config.installModeAuto)){
					installModeAuto = config.installModeAuto;
				}
				
				implHTTPS = new BridgeHTTPS(window, config);
				implHandler = new BridgeProtocolHandler(window, config);
			}

			for (var i = 0; i < products.length; i += 1) {
				var product = products[i];

				if (product.isEnabled()) {
					product.onDocumentReady();
				}
			}

			// Namo CrossEditor 호환 기능
			if (utils.isInternetExplorer() && !utils.isNullOrUndefined(currentWindow.NamoSE)) {
				if (namoSEMonitor === null) {
					namoSEMonitor = new NamoSEMonitor();

					namoSEMonitor.onInitialized(function () {
						utils.log.info('NamoSEMonitor.onInitialized 호출됨');

						if(installModeAuto){
							base.init();
						}else{
							base.initEx();
						}
					});
				}

				namoSEMonitor.addWindow(currentWindow);
			}
			
			// TagFree Editor 호환 기능
			if (utils.isInternetExplorer() && !utils.isNullOrUndefined(currentWindow.XFE)) {
				if (TagFreeConvertMonitor === null) {
					TagFreeConvertMonitor = new TagFreeMonitor();

					TagFreeConvertMonitor.onInitialized(function () {
						utils.log.info('TagFreeConvertMonitor.onInitialized 호출됨');

						if(installModeAuto){
							base.init();
						}else{
							base.initEx();
						}
					});
				}

				TagFreeConvertMonitor.registerWindow(currentWindow);
			}
			
			// CK Editor 호환 기능
			if (utils.isInternetExplorer() && !utils.isNullOrUndefined(currentWindow.CKEDITOR)) {
				if (CKConvertMonitor === null) {
					CKConvertMonitor = new CKEditMonitor();

					CKConvertMonitor.onInitialized(function () {
						utils.log.info('CKConvertMonitor.onInitialized 호출됨');

						if(installModeAuto){
							base.init();
						}else{
							base.initEx();
						}
					});
				}

				CKConvertMonitor.registerWindow(currentWindow);
			}
			
			if (!utils.isNullOrUndefined(srcWindow.KOS_CoreOnReady)) {
				srcWindow.KOS_CoreOnReady();
			}
			
			utils.log.info('웹 브라우저: ' + utils.getBrowserType());
			
			if (isAutoInit || config.autoStart) {
				utils.log.info('자동 초기화 시작');
				if(installModeAuto){
					base.init();
				}else{
					base.initEx();
				}
			}
		};

		// 핸들러 추가
		base.addProductHandler = function (product) {
			var exports = product.getExportedMethods();
			var method = null;

			products.push(product);

			// 익스포트된 메소드 추가
			for (method in exports) {
				if (exports.hasOwnProperty(method)) {
					base[method] = exports[method];
				}
			}

		};
		
				// 설치 확인 및 보호 시작
		base.init = function (eventHandlers) {
			utils.log.info('KOS.init 호출됨');

			execSync.connect.synchronize(function (onSyncComplete) {
				utils.log.info('KOS.init 진입');

				if (!utils.isNullOrUndefined(eventHandlers)) {
					utils.log.info('KOS.init 사용자 이벤트 핸들러 설정됨');
					userEventHandler = eventHandlers;
				}

				if (!isReady) {
					utils.log.info('KOS.init DOM 또는 설정이 준비되지 않음');

					isAutoInit = true;
					onSyncComplete();
					return;
				}

				if (!utils.isNullOrUndefined(srcWindow.KOS_GetGlobalEventHandlers) && utils.isNullOrUndefined(globalEventHandler)) {
					utils.log.info('KOS.init 전역 이벤트 핸들러 설정됨');
					globalEventHandler = srcWindow.KOS_GetGlobalEventHandlers();
				}

				var browserPolicy = checkBrowserPolicy();
				if (browserPolicy !== 'OK') {
					if (browserPolicy === 'UNSUPPORTED' && config.event.raiseUnsupportedEnvironment) {
						utils.log.warn('KOS.init 미지원 환경');
						base.callEventHandler('ERROR_UNSUPPORTED_ENVIRONMENT');
					} else {
						utils.log.warn('KOS.init 비활성화됨');
						base.callEventHandler('READY');
					}

					onSyncComplete();
					return;
				}

				utils.log.info('KOS.init 초기화 시작');
				base.callEventHandler('CONNECTING');

				base.connect(function (response) {
					var totalProducts = products.length;
					var completedProducts = 0;
					var failedProducts = 0;

					function checkComplete(isOK) {
						completedProducts += 1;

						if (!isOK) {
							failedProducts += 1;
						}

						if (totalProducts <= completedProducts) {
							if (failedProducts > 0) {
								base.callEventHandler('ERROR_PROTECTION_FAILED');
							} else {
								base.callEventHandler('READY');
							}

							onSyncComplete();
						}
					}

					function makeProductCompleteHandler(product) {
						return function (response) {
							if (response.result === 'OK') {
								utils.log.info(product.getProductName() + ' 시작됨');
								checkComplete(true);
							} else {
								utils.log.info(product.getProductName() + ' 시작 실패');
								checkComplete(false);
							}
						};
					}

					if (response.result !== 'OK') {
						utils.log.info('KOS.init 연결 실패: ' + response.result);

						if (response.result === 'NEED_UPDATE') {
							base.callEventHandler(response.result, response.requiredVersion, response.currentVersion);
						} else {
							base.callEventHandler(response.result);
						}

						onSyncComplete();
						return;
					}

					utils.log.info('KOS.init 연결됨');
					base.callEventHandler('INITIALIZING');

					for (var i = 0; i < products.length; i += 1) {
						var product = products[i];

						if (product.isEnabled()) {
							utils.log.info('KOS.init ' + product.getProductName() + ' 시작중');
							product.onStart(makeProductCompleteHandler(product));
						} else {
							checkComplete(true);
						}
					}
				});
			});
		};

		// 설치 확인 및 보호 시작
		base.initEx = function (eventHandlers, userAutoStart) {
			utils.log.info('KOS.initEx 호출됨');

			execSync.connect.synchronize(function (onSyncComplete) {
				utils.log.info('KOS.initEx 진입');

				if (!utils.isNullOrUndefined(eventHandlers)) {
					utils.log.info('KOS.initEx 사용자 이벤트 핸들러 설정됨');
					userEventHandler = eventHandlers;
				}
				
				if(utils.isNullOrUndefined(userAutoStart)){
					userAutoStart = false;
				}

				if (!isReady) {
					utils.log.info('KOS.initEx DOM 또는 설정이 준비되지 않음');

					isAutoInit = true;
					onSyncComplete();
					return;
				}

				if (!utils.isNullOrUndefined(srcWindow.KOS_GetGlobalEventHandlers) && utils.isNullOrUndefined(globalEventHandler)) {
					utils.log.info('KOS.initEx 전역 이벤트 핸들러 설정됨');
					globalEventHandler = srcWindow.KOS_GetGlobalEventHandlers();
				}

				var browserPolicy = checkBrowserPolicy();
				if (browserPolicy !== 'OK') {
					if (browserPolicy === 'UNSUPPORTED' && config.event.raiseUnsupportedEnvironment) {
						utils.log.warn('KOS.initEx 미지원 환경');
						base.callEventHandler('ERROR_UNSUPPORTED_ENVIRONMENT');
					} else {
						utils.log.warn('KOS.initEx 비활성화됨');
						base.callEventHandler('READY');
					}

					onSyncComplete();
					return;
				}

				utils.log.info('KOS.initEx 초기화 시작');
				base.callEventHandler('CONNECTING');

				base.connect(function (response) {
					var totalProducts = products.length;
					var completedProducts = 0;
					var failedProducts = 0;
					var kdfStart = true;
					var idfStart = true;
					
					if(utils.isNullOrUndefined(config.kdfAutoStart)){
						kdfStart = config.kdfStart;
						idfStart = config.idfStart;
					}				

					function checkComplete(isOK) {
						completedProducts += 1;

						if (!isOK) {
							failedProducts += 1;
						}

						if (totalProducts <= completedProducts) {
							if (failedProducts > 0) {
								base.callEventHandler('ERROR_PROTECTION_FAILED');
							} else {
								base.callEventHandler('READY');
							}

							onSyncComplete();
						}
					}

					function makeProductCompleteHandler(product) {
						return function (response) {
							if (response.result === 'OK') {
								utils.log.info(product.getProductName() + ' 시작됨');
								checkComplete(true);
							} else {
								utils.log.info(product.getProductName() + ' 시작 실패');
								checkComplete(false);
							}
						};
					}

					if (response.result !== 'OK') {
						utils.log.info('KOS.initEx 연결 실패: ' + response.result);

						if (response.result === 'NEED_UPDATE') {
							base.callEventHandler(response.result, response.requiredVersion, response.currentVersion);
						} else {
							base.callEventHandler(response.result);
						}

						onSyncComplete();
						return;
					}

					utils.log.info('KOS.initEx 연결됨');
					base.callEventHandler('INITIALIZING');
					
					for (var i = 0; i < products.length; i += 1) {
						var product = products[i];
						var productInit = false;
						
						if (product.isEnabled()) {
							if(product.UseChecked() == false){
								checkComplete(true);
							}else if(userAutoStart == true){
								productInit = true;
							}else{
								if(product.getModuleInstalled() == true){
									productInit = true;
								}
							}
							
							if(productInit){
								utils.log.info('KOS.initEx ' + product.getProductName() + ' 시작중');
								product.onStart(makeProductCompleteHandler(product));
							}else{
								checkComplete(true);
							}
						} else {
							checkComplete(true);
						}
					}
				});
			});
		};

		// 설치 확인
		base.checkInstall = function (responseCallback) {
			utils.log.info('KOS.checkInstall 호출됨');

			execSync.connect.synchronize(function (onSyncComplete) {
				utils.log.info('KOS.checkInstall 진입');

				utils.waitSomething(function () {
					return isReady;
				}, function () {
					if (utils.isNullOrUndefined(responseCallback)) {
						responseCallback = function () {};
					}

					var browserPolicy = checkBrowserPolicy();
					if (browserPolicy !== 'OK') {
						var result = {};

						if (browserPolicy === 'UNSUPPORTED' && config.event.raiseUnsupportedEnvironment) {
							utils.log.warn('KOS.checkInstall 미지원 환경');

							result.isInstalled = false;
							result.needUpdate = false;
							result.error = true;
							result.errorCode = 'ERROR_UNSUPPORTED_ENVIRONMENT';
						} else {
							utils.log.warn('KOS.checkInstall 비활성화됨');

							result.isInstalled = true;
							result.needUpdate = false;
							result.error = false;
						}

						responseCallback(result);
						onSyncComplete();
						return;
					}

					base.connect(function (response) {
						var result = {};

						if (response.result === 'OK') {
							result.isInstalled = true;
						} else {
							result.isInstalled = false;
						}

						if (response.result === 'NEED_UPDATE') {
							result.needUpdate = true;
							result.minimumVersion = response.requiredVersion;
							result.runningVersion = response.currentVersion;
						} else {
							result.needUpdate = false;
						}

						if (utils.startsWith(response.result, 'ERROR_')) {
							result.error = true;
							result.errorCode = response.result;
						} else {
							result.error = false;
						}

						responseCallback(result);
						onSyncComplete();
					});
				});
			});
		};

		base.checkInstallEx = function (responseCallback) {
			utils.log.info('KOS.checkInstallEx 호출됨');

			execSync.connect.synchronize(function (onSyncComplete) {
				utils.log.info('KOS.checkInstallEx 진입');

				utils.waitSomething(function () {
					return isReady;
				}, function () {
					if (utils.isNullOrUndefined(responseCallback)) {
						responseCallback = function () {};
					}

					var browserPolicy = checkBrowserPolicy();
					if (browserPolicy !== 'OK') {
						if (browserPolicy === 'UNSUPPORTED' && config.event.raiseUnsupportedEnvironment) {
							utils.log.warn('KOS.checkInstallEx 미지원 환경');

							responseCallback({
								result: 'ERROR_UNSUPPORTED_ENVIRONMENT'
							});
						} else {
							utils.log.warn('KOS.checkInstallEx 비활성화됨');

							responseCallback({
								result: 'OK'
							});
						}

						onSyncComplete();
						return;
					}

					base.connect(function (response) {
						utils.log.info('KOS.checkInstallEx 결과: ' + response.result);
						responseCallback(response);
						onSyncComplete();
					});
				});
			});
		};

		base.connect = function (responseCallback) {
			utils.log.info('KOS.connect 호출됨');

			base.connectHandler(function (response) {
				if (response.result !== 'OK') {
					var handlerResponse = response;

					base.connectService(function (response) {
						if (response.result !== 'OK' && implHandler.isSupported()) {
							if (response.result !== 'NEED_UPDATE') {
								responseCallback(handlerResponse);
								return;
							}
						}

						responseCallback(response);
					});
					return;
				}

				responseCallback({
					result: 'OK',
					installCheck: response.installCheck
				});
			});
		};

		base.connectHandler = function (responseCallback) {
			utils.log.info('KOS.connectHandler 호출됨');

			if (!implHandler.isSupported()) {
				utils.log.info('KOS.connectHandler 비활성화되었거나 지원되지 않는 웹 브라우저');

				responseCallback({
					result: 'ERROR_UNSUPPORTED_ENVIRONMENT',
					installCheck: implHandler.installChecked()
				});

				return;
			}

			if (implHandler.isConnected()) {
				utils.log.info('KOS.connectHandler 이미 연결됨');

				responseCallback({
					result: 'OK',
					installCheck: implHandler.installChecked()
				});

				return;
			}

			implHandler.connect(function (response) {
				if (response.result === 'OK') {
					utils.log.info('KOS.connectHandler 연결됨');
					
					if(response.installCheck.kIns == true){
						products[0].setModuleInstalled();
					}
					if(response.installCheck.iIns == true){
						products[1].setModuleInstalled();
					}

					responseCallback({
						result: 'OK',
						installCheck: response.installCheck
					});
				} else {
					utils.log.info('KOS.connectHandler 연결 실패: ' + response.result);

					responseCallback(response);
				}
			});
		};

		base.connectService = function (responseCallback) {
			utils.log.info('KOS.connectService 호출됨');

			if (!implHTTPS.isSupported()) {
				utils.log.info('KOS.connectService 지원되지 않는 웹 브라우저');

				responseCallback({
					result: 'ERROR_UNSUPPORTED_ENVIRONMENT',
					installCheck: implHTTPS.installChecked()
				});

				return;
			}

			if (implHTTPS.isConnected()) {
				utils.log.info('KOS.connectService 이미 연결됨');

				responseCallback({
					result: 'OK',
					installCheck: implHTTPS.installChecked()
				});

				return;
			}

			implHTTPS.connect(function (response) {
				if (response.result === 'OK') {
					utils.log.info('KOS.connectService 연결됨');
					
					if(response.installCheck.kIns == true){
						products[0].setModuleInstalled();
					}
					if(response.installCheck.iIns == true){
						products[1].setModuleInstalled();
					}

					responseCallback({
						result: 'OK',
						installCheck: response.installCheck
					});
				} else {
					utils.log.info('KOS.connectService 연결 실패: ' + response.result);

					responseCallback(response);
				}
			});
		};

		base.isHandlerSupported = function () {
			return implHandler.isSupported();
		};

		base.isHandlerConnected = function () {
			if (!isReady) {
				return false;
			}

			return implHandler.isConnected();
		};

		base.sendDataHandler = function (operation, data, responseCallback) {
			implHandler.sendData(operation, data, responseCallback);
		};

		base.isServiceSupported = function () {
			return implHTTPS.isSupported();
		};

		base.isServiceConnected = function () {
			if (!isReady) {
				return false;
			}

			return implHTTPS.isConnected();
		};

		base.sendDataService = function (method, url, data, responseCallback) {
			implHTTPS.sendData(method, url, data, responseCallback);
		};

		base.callExtFunction = function (subOperation, paramArray, responseCallback) {
			var parameters = {};
			parameters.data = JSON.stringify({
				operation: 'ext',
				subOperation: subOperation,
				paramArray: paramArray
			});

			if (base.isHandlerConnected()) {
				base.sendDataHandler('ext', utils.makeQueryString(parameters), responseCallback);
			} else if (base.isServiceConnected()) {
				base.sendDataService('POST', '/v2/', utils.makeQueryString(parameters), responseCallback);
			} else {
				responseCallback({
					result: 'ERROR_NOT_CONNECTED'
				});
			}
		};

		base.callEventHandler = function (eventType, parameter1, parameter2) {
			if (utils.startsWith(eventType, 'ERROR')) {
				parameter1 = eventType;
				eventType = 'ERROR';
			}

			if (userEventHandler !== null && !utils.isNullOrUndefined(userEventHandler[eventType])) {
				utils.log.info('사용자 이벤트 핸들러 호출: ' + eventType + ' ' + parameter1 + ' ' + parameter2);

				try {
					userEventHandler[eventType](parameter1, parameter2);
				} catch (e) {}

				return;
			}

			if (globalEventHandler !== null && typeof globalEventHandler[eventType] !== 'undefined') {
				utils.log.info('전역 이벤트 핸들러 호출: ' + eventType + ' ' + parameter1 + ' ' + parameter2);

				try {
					globalEventHandler[eventType](parameter1, parameter2);
				} catch (e) {}

				return;
			}

			utils.log.info('등록되지 않은 이벤트 핸들러: ' + eventType + ' ' + parameter1 + ' ' + parameter2);
		};
		
		base.isMultiServiceVer = function () {
			return implHTTPS.serviceVered();
		};

		base.isDisposed = function () {
			return false;
		};
		
		base.AllClear = function (){
			try {                   
				//console.log(document.body.children[12].contentWindow.document.body);
				// 기존 삭제	 
				document.body["remove"] = "";    
				var num = document.body.children.length;
				for( var i = 0; i< num ;i++){
					//let child = document.body.removeChild(document.body.children[0]);
					//delete child;
					delete document.body.removeChild(document.body.children[0]);
				}     
				
				location.reload();	
  				
			} catch(e){
				console.log("Error : All clear memory");
			}

		}
	}

	// K-Defense Legacy ExtE2E
	function KDefenseLegacyExtE2EHandler(window, initParam) {
		var base = this;

		var autoRegister = initParam.autoRegister;
		var encryptName = initParam.encrypt;
		var extEncryptName = initParam.extEncrypt;

		var encryptBlockSize = 24;

		function getExtDataInput(inputObject, resetValueWhenCreate) {
			var parentForm = inputObject.form;
			var inputObjectName = inputObject.name;

			if (utils.isNullOrUndefined(parentForm)) {
				return null;
			}

			if (inputObjectName.length === 0) {
				return null;
			}

			var extDataInputName = extEncryptName + inputObjectName;
			var extDataInput = parentForm.elements[extDataInputName];

			if (utils.isNullOrUndefined(extDataInput)) {
				if (utils.isLegacyBrowser()) {
					extDataInput = window.document.createElement('<input type="hidden" name="' + extDataInputName + '>');
				} else {
					extDataInput = window.document.createElement('INPUT');
					extDataInput.name = extDataInputName;
					extDataInput.type = 'hidden';
				}

				parentForm.appendChild(extDataInput);

				if (resetValueWhenCreate) {
					inputObject.value = '';
				}
			}

			return extDataInput;
		}

		base.onPreRegister = function (inputObject) {
			if (inputObject.name.length === 0) {
				return null;
			}

			if (!utils.hasAttribute(inputObject, 'data-kdf-option') && !autoRegister) {
				return null;
			}

			if (utils.compareNoCase(inputObject.type, 'PASSWORD')) {
				if (!utils.startsWith(inputObject.name, encryptName)) {
					inputObject.name = encryptName + inputObject.name;
				}
			}

			return {};
		};

		base.onPostRegister = function (inputObject, response) {
			return true;
		};

		base.onRegistered = function (inputObject, extraInfo) {};

		base.onRegisterFailed = function (inputObject) {
			if (inputObject.name.length === 0) {
				return;
			}

			if (utils.compareNoCase(inputObject.type, 'PASSWORD')) {
				if (utils.startsWith(inputObject.name, encryptName)) {
					inputObject.name = inputObject.name.replace(encryptName, '');
				}
			}
		};

		base.isTarget = function (inputObject) {
			if (!utils.hasAttribute(inputObject, 'data-kdf-option')) {
				if (utils.compareNoCase(inputObject.type, 'PASSWORD')) {
					return autoRegister;
				}

				return false;
			}

			return true;
		};

		base.putCharacter = function (inputObject, extraInfo) {
			if (utils.compareNoCase(inputObject.type, 'PASSWORD')) {
				return;
			}

			var extDataInput = getExtDataInput(inputObject, true);
			if (extDataInput === null) {
				return;
			}

			if (utils.isNullOrUndefined(extraInfo) || utils.isNullOrUndefined(extraInfo.encryptedValue)) {
				return;
			}

			if (extraInfo.encryptedValue.length !== encryptBlockSize) {
				return;
			}

			extDataInput.value += extraInfo.encryptedValue;
		};

		base.removeCharacter = function (inputObject) {
			if (utils.compareNoCase(inputObject.type, 'PASSWORD')) {
				return;
			}

			var extDataInput = getExtDataInput(inputObject, true);
			if (extDataInput === null) {
				return;
			}

			var encryptBlockCount = extDataInput.value.length / encryptBlockSize;
			if (encryptBlockCount <= 1) {
				inputObject.value = '';
				extDataInput.value = '';
				return;
			}

			extDataInput.value = extDataInput.value.substring(0, (encryptBlockCount - 1) * encryptBlockSize);
		};

		base.clear = function (inputObject) {
			if (utils.compareNoCase(inputObject.type, 'PASSWORD')) {
				return;
			}

			var extDataInput = getExtDataInput(inputObject, true);
			if (extDataInput === null) {
				return;
			}

			inputObject.value = '';
			extDataInput.value = '';
		};

		base.validate = function (inputObject) {
			if (utils.compareNoCase(inputObject.type, 'PASSWORD')) {
				return false;
			}

			var extDataInput = getExtDataInput(inputObject, true);
			if (extDataInput === null) {
				return false;
			}

			if (inputObject.value.length * encryptBlockSize !== extDataInput.value.length) {
				inputObject.value = '';
				extDataInput.value = '';
				return true;
			}

			return false;
		};

		base.isEncrypted = function (inputObject) {
			if (base.isExtEncrypted(inputObject)) {
				return true;
			}

			if (!utils.compareNoCase(inputObject.type, 'PASSWORD')) {
				return false;
			}

			if (!utils.startsWith(inputObject.name, encryptName)) {
				return false;
			}

			return true;
		};

		base.isExtEncrypted = function (inputObject) {
			if (utils.compareNoCase(inputObject.type, 'PASSWORD')) {
				return false;
			}

			var extDataInput = getExtDataInput(inputObject, false);
			if (extDataInput === null) {
				return false;
			}

			return true;
		};

		base.getCustomKey = function (inputObject) {
			return null;
		};

		base.getEncryptedKey = function (inputObject) {
			return window.KOS.getSeed();
		};

		base.getEncryptedValue = function (inputObject) {
			if (utils.compareNoCase(inputObject.type, 'PASSWORD')) {
				utils.log.info('KDefenseLegacyExtE2EHandler.getEncryptedValue 암호 필드 ' + utils.getElementIdentifier(inputObject));

				if (!base.isEncrypted(inputObject)) {
					utils.log.info('KDefenseLegacyExtE2EHandler.getEncryptedValue 암호화되지 않음 ' + utils.getElementIdentifier(inputObject));
					return null;
				}

				return inputObject.value;
			}

			var extDataInput = getExtDataInput(inputObject, false);
			if (extDataInput === null) {
				return null;
			}

			return extDataInput.value;
		};
	}

	// K-Defense ExtE2E
	function KDefenseExtE2EHandler(window) {
		var base = this;

		var encryptBlockSize = 24;

		base.onPreRegister = function (inputObject) {
			if (!utils.hasAttribute(inputObject, 'data-kdf-option')) {
				return null;
			}

			return {};
		};

		base.onPostRegister = function (inputObject, response) {
			return true;
		};

		base.onRegistered = function (inputObject, extraInfo) {};

		base.onRegisterFailed = function (inputObject) {};

		base.isTarget = function (inputObject) {
			if (!utils.hasAttribute(inputObject, 'data-kdf-option')) {
				return false;
			}

			return true;
		};

		base.putCharacter = function (inputObject, extraInfo) {
			if (utils.isNullOrUndefined(extraInfo) || utils.isNullOrUndefined(extraInfo.encryptedValue)) {
				return;
			}

			if (extraInfo.encryptedValue.length !== encryptBlockSize) {
				return;
			}

			utils.setAttribute(inputObject, 'data-kdf-value-ext', utils.getAttribute(inputObject, 'data-kdf-value-ext', '') + extraInfo.encryptedValue);
		};

		base.removeCharacter = function (inputObject) {
			var encryptedValue = utils.getAttribute(inputObject, 'data-kdf-value-ext', '');
			var encryptBlockCount = encryptedValue.length / encryptBlockSize;
			if (encryptBlockCount <= 1) {
				inputObject.value = '';
				utils.setAttribute(inputObject, 'data-kdf-value-ext', '');
				return;
			}

			utils.setAttribute(inputObject, 'data-kdf-value-ext', encryptedValue.substring(0, (encryptBlockCount - 1) * encryptBlockSize));
		};

		base.clear = function (inputObject) {
			inputObject.value = '';
			utils.setAttribute(inputObject, 'data-kdf-value-ext', '');
		};

		base.validate = function (inputObject) {
			if (inputObject.value.length * encryptBlockSize !== utils.getAttribute(inputObject, 'data-kdf-value-ext', '').length) {
				inputObject.value = '';
				utils.setAttribute(inputObject, 'data-kdf-value-ext', '');
				return true;
			}

			return false;
		};

		base.isEncrypted = function (inputObject) {
			return base.isExtEncrypted(inputObject);
		};

		base.isExtEncrypted = function (inputObject) {
			if (!utils.hasAttribute(inputObject, 'data-kdf-value-ext')) {
				return false;
			}

			return true;
		};

		base.getCustomKey = function (inputObject) {
			return null;
		};

		base.getEncryptedKey = function (inputObject) {
			return window.KOS.getSeed();
		};

		base.getEncryptedValue = function (inputObject) {
			return utils.getAttribute(inputObject, 'data-kdf-value-ext', '');
		};
	}

	// K-Defense ExtE2E K-Crypto
	function KDefenseExtE2EKCHandler(window) {
		var base = this;

		var encryptBlockSize = 24;

		base.onPreRegister = function (inputObject) {
			if (!utils.hasAttribute(inputObject, 'data-kdf-option')) {
				return null;
			}

			return {};
		};

		base.onPostRegister = function (inputObject, response) {
			return true;
		};

		base.onRegistered = function (inputObject, extraInfo) {};

		base.onRegisterFailed = function (inputObject) {};

		base.isTarget = function (inputObject) {
			if (!utils.hasAttribute(inputObject, 'data-kdf-option')) {
				return false;
			}

			return true;
		};

		base.putCharacter = function (inputObject, extraInfo) {
			if (utils.isNullOrUndefined(extraInfo) || utils.isNullOrUndefined(extraInfo.encryptedValue)) {
				return;
			}

			if (extraInfo.encryptedValue.length !== encryptBlockSize) {
				return;
			}

			utils.setAttribute(inputObject, 'data-kdf-value-ext', utils.getAttribute(inputObject, 'data-kdf-value-ext', '') + extraInfo.encryptedValue);
		};

		base.removeCharacter = function (inputObject) {
			var encryptedValue = utils.getAttribute(inputObject, 'data-kdf-value-ext', '');
			var encryptBlockCount = encryptedValue.length / encryptBlockSize;
			if (encryptBlockCount <= 1) {
				inputObject.value = '';
				utils.setAttribute(inputObject, 'data-kdf-value-ext', '');
				return;
			}

			utils.setAttribute(inputObject, 'data-kdf-value-ext', encryptedValue.substring(0, (encryptBlockCount - 1) * encryptBlockSize));
		};

		base.clear = function (inputObject) {
			inputObject.value = '';
			utils.setAttribute(inputObject, 'data-kdf-value-ext', '');
		};

		base.validate = function (inputObject) {
			if (inputObject.value.length * encryptBlockSize !== utils.getAttribute(inputObject, 'data-kdf-value-ext', '').length) {
				inputObject.value = '';
				utils.setAttribute(inputObject, 'data-kdf-value-ext', '');
				return true;
			}

			return false;
		};

		base.isEncrypted = function (inputObject) {
			return base.isExtEncrypted(inputObject);
		};

		base.isExtEncrypted = function (inputObject) {
			if (!utils.hasAttribute(inputObject, 'data-kdf-value-ext')) {
				return false;
			}

			return true;
		};

		base.getCustomKey = function (inputObject) {
			return null;
		};

		base.getEncryptedKey = function (inputObject) {
			return window.KOS.getSeed();
		};

		base.getEncryptedValue = function (inputObject) {
			return utils.getAttribute(inputObject, 'data-kdf-value-ext', '');
		};
	}

	// K-Defense Cert ExtE2E
	function KDefenseCertExtE2EHandler(window, initParam) {
		var base = this;

		var identifier = initParam.identifier;
		var publicKey = initParam.publicKey;
		var sessionKey = initParam.sessionKey;
		var value = initParam.value;
		var isProtected = initParam.isProtected;

		var encryptBlockSize = 24;

		base.onPreRegister = function (inputObject) {
			if (!utils.hasAttribute(inputObject, 'data-kdf-option')) {
				return null;
			}

			var customKey = base.getCustomKey(inputObject);
			if (customKey === null) {
				return null;
			}

			return {
				customKey: customKey
			};
		};

		base.onPostRegister = function (inputObject, response) {
			if (utils.isNullOrUndefined(response.encryptedKey)) {
				return false;
			}

			return true;
		};

		base.onRegistered = function (inputObject, extraInfo) {
			if (utils.isNullOrUndefined(extraInfo.encryptedKey)) {
				return;
			}

			inputObject.value = '';
			utils.setAttribute(inputObject, isProtected, true);
			utils.setAttribute(inputObject, sessionKey, extraInfo.encryptedKey);
			utils.setAttribute(inputObject, value, '');
		};

		base.onRegisterFailed = function (inputObject) {};

		base.isTarget = function (inputObject) {
			if (!utils.hasAttribute(inputObject, 'data-kdf-option') || !utils.hasAttribute(inputObject, identifier)) {
				return false;
			}

			return true;
		};

		base.putCharacter = function (inputObject, extraInfo) {
			if (utils.isNullOrUndefined(extraInfo) || utils.isNullOrUndefined(extraInfo.encryptedValue)) {
				return;
			}

			if (extraInfo.encryptedValue.length !== encryptBlockSize) {
				return;
			}

			utils.setAttribute(inputObject, value, utils.getAttribute(inputObject, value, '') + extraInfo.encryptedValue);
		};

		base.removeCharacter = function (inputObject) {
			var encryptedValue = utils.getAttribute(inputObject, value, '');
			var encryptBlockCount = encryptedValue.length / encryptBlockSize;
			if (encryptBlockCount <= 1) {
				inputObject.value = '';
				utils.setAttribute(inputObject, value, '');
				return;
			}

			utils.setAttribute(inputObject, value, encryptedValue.substring(0, (encryptBlockCount - 1) * encryptBlockSize));
		};

		base.clear = function (inputObject) {
			inputObject.value = '';
			utils.setAttribute(inputObject, value, '');
		};

		base.validate = function (inputObject) {
			if (inputObject.value.length * encryptBlockSize !== utils.getAttribute(inputObject, value, '').length) {
				inputObject.value = '';
				utils.setAttribute(inputObject, value, '');
				return true;
			}

			return false;
		};

		base.isEncrypted = function (inputObject) {
			return base.isExtEncrypted(inputObject);
		};

		base.isExtEncrypted = function (inputObject) {
			if (!utils.getAttributeBoolean(inputObject, isProtected)) {
				return false;
			}

			if (!utils.hasAttribute(inputObject, sessionKey)) {
				return false;
			}

			return true;
		};

		base.getCustomKey = function (inputObject) {
			var customKey = null;

			switch (publicKey.source) {
			case 'GLOBAL_ELEMENT':
				var publicKeyObject = window.document.getElementById(publicKey.name);
				if (publicKeyObject !== null) {
					customKey = publicKeyObject.value;
				}
				break;
			case 'ATTRIBUTE':
				if (utils.hasAttribute(inputObject, publicKey.name)) {
					customKey = utils.getAttribute(inputObject, publicKey.name);
				}
				break;
			}

			if (customKey === null || customKey.length <= 0) {
				return null;
			}

			return '01_' + customKey;
		};

		base.getEncryptedKey = function (inputObject) {
			if (!base.isExtEncrypted(inputObject)) {
				return null;
			}

			return utils.getAttribute(inputObject, sessionKey, '');
		};

		base.getEncryptedValue = function (inputObject) {
			if (!base.isExtEncrypted(inputObject)) {
				return null;
			}

			return utils.getAttribute(inputObject, value, '');
		};
	}

	// Penta Security WebCrypto E2E
	function PentaWebCryptoE2EHandler(window) {
		var base = this;

		var encryptBlockSize = 2;

		base.onPreRegister = function (inputObject) {
			if (!utils.hasAttribute(inputObject, 'data-kdf-option')) {
				return null;
			}

			var customKey = base.getCustomKey(inputObject);
			if (customKey === null) {
				return null;
			}

			return {
				customKey: customKey
			};
		};

		base.onPostRegister = function (inputObject, response) {
			return true;
		};

		base.onRegistered = function (inputObject, extraInfo) {
		};

		base.onRegisterFailed = function (inputObject) {
		};

		base.isTarget = function (inputObject) {
			if (!utils.hasAttribute(inputObject, 'data-kdf-option')) {
				return false;
			}

			var customKey = base.getCustomKey(inputObject);
			if (customKey === null && !utils.hasAttribute(inputObject, 'data-kdf-value-ext')) {
				return false;
			}

			return true;
		};

		base.putCharacter = function (inputObject, extraInfo) {
			if (utils.isNullOrUndefined(extraInfo) || utils.isNullOrUndefined(extraInfo.encryptedValue)) {
				return;
			}

			if (extraInfo.encryptedValue.length !== encryptBlockSize) {
				return;
			}

			utils.setAttribute(inputObject, 'data-kdf-value-ext', utils.getAttribute(inputObject, 'data-kdf-value-ext', '') + extraInfo.encryptedValue);
		};

		base.removeCharacter = function (inputObject) {
			var encryptedValue = utils.getAttribute(inputObject, 'data-kdf-value-ext', '');
			var encryptBlockCount = encryptedValue.length / encryptBlockSize;
			if (encryptBlockCount <= 1) {
				inputObject.value = '';
				utils.setAttribute(inputObject, 'data-kdf-value-ext', '');
				return;
			}

			utils.setAttribute(inputObject, 'data-kdf-value-ext', encryptedValue.substring(0, (encryptBlockCount - 1) * encryptBlockSize));
		};

		base.clear = function (inputObject) {
			inputObject.value = '';
			utils.setAttribute(inputObject, 'data-kdf-value-ext', '');
		};

		base.validate = function (inputObject) {
			if (inputObject.value.length * encryptBlockSize !== utils.getAttribute(inputObject, 'data-kdf-value-ext', '').length) {
				inputObject.value = '';
				utils.setAttribute(inputObject, 'data-kdf-value-ext', '');
				return true;
			}

			return false;
		};

		base.isEncrypted = function (inputObject) {
			return base.isExtEncrypted(inputObject);
		};

		base.isExtEncrypted = function (inputObject) {
			if (!utils.hasAttribute(inputObject, 'data-kdf-value-ext')) {
				return false;
			}

			return true;
		};

		base.getCustomKey = function (inputObject) {
			var seed = window.KOS.getSeed();
			if (utils.isNullOrUndefined(seed) || seed.length <= 0) {
				return null;
			}

			return '02_' + seed;
		};

		base.getEncryptedKey = function (inputObject) {
			return null;
		};

		base.getEncryptedValue = function (inputObject) {
			var extData = utils.getAttribute(inputObject, 'data-kdf-value-ext', '');
			if (extData.length <= 0) {
				return null;
			}

			if (utils.startsWith(extData, 'KENC_')) {
				return extData;
			} else {
				return 'KENC_' + extData;
			}
		};
	}

	// K-Defense 입력 핸들러 클래스
	function KDefenseInputHandler(window, srcWindow) {
		var base = this;

		var handlers = [];

		function isReadOnly(inputObject) {
			return inputObject.disabled || inputObject.readOnly;
		}

		function isOnlyNumber(inputObject) {
			return utils.getAttribute(inputObject, 'data-kdf-option') === 'onlyNumber';
		}

		function getNewValue(inputObj, character) {
			var originalVal = inputObj.value;

			var head = originalVal.substring(0, inputObj.selectionStart);
			var tail = originalVal.substring(inputObj.selectionEnd);

			return head + character + tail;
		}

		function putCharacter(inputObject, character, isEncrypted) {
			if (isEncrypted) {
				if (inputObject.maxLength > 0 && inputObject.value.length + 1 > inputObject.maxLength) {
					return false;
				}

				inputObject.value += character;
				inputObject.setSelectionRange(inputObject.value.length, inputObject.value.length);
			} else {
				var newValue = getNewValue(inputObject, character);
				var currentSelectionStart = inputObject.selectionStart;

				if (inputObject.maxLength > 0 && newValue.length > inputObject.maxLength) {
					return false;
				}

				inputObject.value = newValue;
				inputObject.setSelectionRange(currentSelectionStart + 1, currentSelectionStart + 1);
			}

			return true;
		}

		function getTargetInputHandler(inputObject) {
			for (var i = 0; i < handlers.length; i += 1) {
				var handler = handlers[i];

				if (handler.isTarget(inputObject)) {
					return handler;
				}
			}

			return null;
		}

		base.onPreRegister = function (inputObject) {
			var handler = getTargetInputHandler(inputObject);
			if (handler !== null) {
				return handler.onPreRegister(inputObject);
			}

			return null;
		};

		base.onPostRegister = function (inputObject, response) {
			var handler = getTargetInputHandler(inputObject);
			if (handler !== null) {
				return handler.onPostRegister(inputObject, response);
			}

			return false;
		};

		base.onRegistered = function (inputObject, extraInfo) {
			var handler = getTargetInputHandler(inputObject);
			if (handler !== null) {
				handler.onRegistered(inputObject, extraInfo);
			}
		};

		base.onRegisterFailed = function (inputObject) {
			var handler = getTargetInputHandler(inputObject);
			if (handler !== null) {
				handler.onRegisterFailed(inputObject);
			}
		};

		base.addE2EHandler = function (handler) {
			handlers.push(handler);
		};

		base.clearE2EHandlers = function () {
			handlers = [];
		};

		base.putCharacter = function (inputObject, character, extraInfo) {
			if (isReadOnly(inputObject)) {
				return;
			}

			if (isOnlyNumber(inputObject) && !utils.isNumericKey(character)) {
				return;
			}

			var isEncrypted = base.isEncrypted(inputObject);

			if (utils.isNullOrUndefined(character) || !utils.isAllowedKey(character, false)) {
				if (isEncrypted) {
					return;
				}
			}

			var isInputModified = putCharacter(inputObject, character, isEncrypted);

			if (isEncrypted || (inputObject.selectionStart === inputObject.value.length)) {
				base.moveCaretToEnd(inputObject);
			}

			if (!isInputModified) {
				return;
			}

			var handler = getTargetInputHandler(inputObject);
			if (handler !== null) {
				handler.putCharacter(inputObject, extraInfo);
			}
		};

		base.removeCharacter = function (inputObject) {
			if (isReadOnly(inputObject)) {
				return;
			}

			if (!base.isExtEncrypted(inputObject)) {
				return;
			}

			var handler = getTargetInputHandler(inputObject);
			if (handler !== null) {
				handler.removeCharacter(inputObject);
			}
		};

		base.moveCaretToEnd = function (inputObject) {
			inputObject.setSelectionRange(inputObject.value.length, inputObject.value.length);

			try {
				inputObject.scrollLeft = inputObject.scrollWidth;
			} catch (e) {}
		};

		base.clear = function (inputObject) {
			if (isReadOnly(inputObject)) {
				return;
			}

			inputObject.value = '';

			var handler = getTargetInputHandler(inputObject);
			if (handler !== null) {
				handler.clear(inputObject);
			}
		};

		base.validate = function (inputObject) {
			if (isReadOnly(inputObject)) {
				return false;
			}

			var handler = getTargetInputHandler(inputObject);
			if (handler !== null) {
				return handler.validate(inputObject);
			}

			return false;
		};

		base.isEncrypted = function (inputObject) {
			var handler = getTargetInputHandler(inputObject);
			if (handler !== null) {
				return handler.isEncrypted(inputObject);
			}

			return false;
		};

		base.isExtEncrypted = function (inputObject) {
			var handler = getTargetInputHandler(inputObject);
			if (handler !== null) {
				return handler.isExtEncrypted(inputObject);
			}

			return false;
		};

		base.getCustomKey = function (inputObject) {
			var handler = getTargetInputHandler(inputObject);
			if (handler !== null) {
				return handler.getCustomKey(inputObject);
			}

			return null;
		};

		base.getEncryptedKey = function (inputObject) {
			var handler = getTargetInputHandler(inputObject);
			if (handler !== null) {
				var encryptedKey = handler.getEncryptedKey(inputObject);

				if (encryptedKey !== null) {
					return encryptedKey;
				}
			}

			return null;
		};

		base.getEncryptedValue = function (inputObject) {
			var handler = getTargetInputHandler(inputObject);
			if (handler !== null) {
				var encryptedValue = handler.getEncryptedValue(inputObject);

				if (encryptedValue !== null) {
					return encryptedValue;
				}
			}

			return null;
		};
	}

	// K-Defense 핸들러 클래스
	function KDefenseHandler(window, srcWindow, bridge) {
		var base = this;

		var isStarted = false;
		
		var isProtectKey = false;

		var isExternalCryptoInitialized = false;
		var isExternalCompleteInitailized = false;

		var productParameters = null;
		var nOption = 0x00000000;
		var nOptionEx3 = 0x00000000;

		var blacklistedWindows = [];

		var inputHandler = new KDefenseInputHandler(window, srcWindow);

		var encryptedGlobalKeyName = null;
		var encryptedGlobalKey = null;
		
		var MultiEdge = null;
		var isStopProtect = false;
		
		var keyupCode = null;
		
		var moduleInstall = false;
		
		function isKingsLegacyExtE2EEnabled() {
			return (nOption & 0x40000000) !== 0;
		}

		function isKingsExtE2EEnabled() {
			return (nOptionEx3 & 0x00000002) !== 0;
		}

		function isKingsExtE2EKCEnabled() {
			return (nOptionEx3 & 0x00000010) !== 0;
		}

		function isInitechExtE2EEnabled() {
			return (nOptionEx3 & 0x00000008) !== 0;
		}

		function isPentaE2EEnabled() {
			return (nOptionEx3 & 0x00000004) !== 0;
		}

		function normalizeCaseOption(caseOption) {
			switch (caseOption) {
			case 'none':
			case 'onlyNumber':
				break;
			default:
				caseOption = 'none';
				break;
			}

			return caseOption;
		}

		function sendInfo(responseCallback) {
			var parameters = {};
			parameters[base.getParameterName()] = base.getParameters();

			function onSendInfoResponse(response) {
				if (response.result !== 'OK') {
					responseCallback({
						result: 'ERROR_PROTECTION_FAILED'
					});

					return;
				}

				var result = {
					result: 'OK'
				};

				if (isKingsExtE2EEnabled() || isKingsLegacyExtE2EEnabled() || isKingsExtE2EKCEnabled()) {
					result.encryptedGlobalKeyName = '_KDFS_';
				} else if (isInitechExtE2EEnabled()) {
					result.encryptedGlobalKeyName = '_ETEExt_SEED_';
				}

				if (!utils.isNullOrUndefined(parameters.cert) && !utils.isInternetExplorer()) {
					if (utils.isNullOrUndefined(response.seed)) {
						responseCallback({
							result: 'ERROR_PROTECTION_FAILED'
						});

						return;
					}

					result.encryptedGlobalKey = response.seed;
				}
				
				if(!utils.isNullOrUndefined(response.multiedge)) {
					result.MultiEdge = response.multiedge;
				}

				responseCallback(result);
			}

			if (bridge.isHandlerSupported() && bridge.isHandlerConnected()) {
				bridge.sendDataHandler('info', utils.makeQueryString(parameters), onSendInfoResponse);
			} else if (bridge.isServiceSupported() && bridge.isServiceConnected()) {
				parameters.webbrowser = utils.getBrowserType();
				parameters.domain = window.location.hostname;
				parameters.title = "청소년안정망시스템";
				parameters.mode = 1;

				if ((isKingsExtE2EEnabled() || isKingsLegacyExtE2EEnabled() || isKingsExtE2EKCEnabled() || isInitechExtE2EEnabled()) && !utils.isNullOrUndefined(srcWindow.Get_Cert_var)) {
					parameters.cert = srcWindow.Get_Cert_var();
				} else if (!utils.isNullOrUndefined(srcWindow.Get_Ini6_var)) {
					parameters.cert = srcWindow.Get_Ini6_var();
				}

				bridge.sendDataService('POST', '/info', utils.makeQueryString(parameters), onSendInfoResponse);
			} else {
				responseCallback({
					result: 'ERROR_UNSUPPORTED_ENVIRONMENT'
				});
			}
		}

		function sendRegister(inputObject, extraInfo, responseCallback) {
			var parameters = {};
			parameters.domain = window.location.hostname;
			parameters.title = "청소년안정망시스템";
			parameters.formname = !utils.isNullOrUndefined(inputObject.form) ? inputObject.form.name : '';
			parameters.inputname = inputObject.name || inputObject.id;
			parameters.type = inputObject.type;
			parameters.option = productParameters.nOption;
			parameters.caseoption = extraInfo.caseOption;
			parameters.optionex3 = productParameters.nOptionEx3;

			if (!utils.isNullOrUndefined(extraInfo.customKey)) {
				parameters.ckey = extraInfo.customKey;
			}

			bridge.sendDataService('POST', '/registerElement', utils.makeQueryString(parameters), function (response) {
				if (response.result !== 'OK') {
					responseCallback({
						result: 'ERROR_INPUT_PROTECTION_FAILED'
					});

					return;
				}

				if (!utils.isNullOrUndefined(response.seed)) {
					responseCallback({
						result: 'OK',
						encryptedKey: response.seed
					});

					return;
				}

				responseCallback({
					result: 'OK'
				});
			});
		}

		function sendKeyPress(inputObject, extraInfo, responseCallback) {
			if (!bridge.isServiceSupported() || !bridge.isServiceConnected()) {
				utils.log.warn('KDefenseHandler.sendKeyPress 서비스가 연결되지 않음');
				return;
			}

			var parameters = {};
			parameters.title = "청소년안정망시스템";
			parameters.formname = !utils.isNullOrUndefined(inputObject.form) ? inputObject.form.name : '';
			parameters.inputname = inputObject.name || inputObject.id;
			parameters.type = inputObject.type;
			parameters.noption = productParameters.nOption;
			parameters.clear = extraInfo.clear;
			parameters.optionex3 = productParameters.nOptionEx3;
			parameters.domain = window.location.hostname;
			parameters.webbrowser = utils.getBrowserType();
			parameters.dummy = extraInfo.dummy;

			if (utils.hasAttribute(inputObject, 'data-kdf-option')) {
				parameters.caseoption = normalizeCaseOption(utils.getAttribute(inputObject, 'data-kdf-option', 'none'));
			}

			var customKey = inputHandler.getCustomKey(inputObject);
			if (customKey !== null) {
				parameters.ckey = customKey;
			}

			bridge.sendDataService('POST', '/inputKeyPress', utils.makeQueryString(parameters), function (response) {
				if (response.result !== 'OK') {
					responseCallback({
						result: 'ERROR_KEY_PROTECTION_FAILED'
					});

					return;
				}

				if (inputHandler.isExtEncrypted(inputObject)) {
					if (response.keyCode <= 0 || utils.isNullOrUndefined(response.cipher)) {
						responseCallback({
							result: 'ERROR_KEY_ENCRYPTION_FAILED'
						});

						return;
					}

					responseCallback({
						result: 'OK',
						keyCode: response.keyCode,
						encryptedValue: response.cipher
					});

					return;
				} else if (response.keyCode <= 0) {
					responseCallback({
						result: 'ERROR_KEY_CODE_IS_ZERO'
					});

					return;
				}

				responseCallback({
					result: 'OK',
					keyCode: response.keyCode
				});
			});
		}

		function sendFocus(inputObject, extraInfo) {
			if (!bridge.isServiceSupported() || !bridge.isServiceConnected()) {
				utils.log.warn('KDefenseHandler.sendFocus 서비스가 연결되지 않음');
				return;
			}

			var parameters = {};
			parameters.title = "청소년안정망시스템";
			parameters.formname = !utils.isNullOrUndefined(inputObject.form) ? inputObject.form.name : '';
			parameters.inputname = inputObject.name || inputObject.id;
			parameters.clear = extraInfo.clear;
			parameters.domain = window.location.hostname;
			parameters.webbrowser = utils.getBrowserType();
			parameters.sitecode = productParameters.nOptionEx;
			parameters.type = inputObject.type;

			bridge.sendDataService('POST', '/inputFocus', utils.makeQueryString(parameters), function (response) {});
		}

		function sendBlur(inputObject, completeCallback) {
			if (utils.isNullOrUndefined(completeCallback)) {
				completeCallback = function () {};
			}

			if (!bridge.isServiceSupported() || !bridge.isServiceConnected()) {
				utils.log.warn('KDefenseHandler.sendBlur 서비스가 연결되지 않음');
				return;
			}

			var parameters = {};
			parameters.title = "청소년안정망시스템";
			parameters.formname = !utils.isNullOrUndefined(inputObject.form) ? inputObject.form.name : '';
			parameters.inputname = inputObject.name || inputObject.id;
			parameters.domain = window.location.hostname;
			parameters.webbrowser = utils.getBrowserType();
			parameters.sitecode = productParameters.nOptionEx;

			bridge.sendDataService('POST', '/inputBlur', utils.makeQueryString(parameters), completeCallback);
		}

		function makePrefixedInputObjects(form) {
			var result = [];

			for (var i = 0; i < form.length; i += 1) {
				var inputObject = form[i];

				if (!utils.hasAttribute(inputObject, 'data-kdf-option')) {
					continue;
				}

				if (inputObject.name.length <= 0) {
					continue;
				}

				if (utils.compareNoCase(inputObject.type, 'HIDDEN')) {
					continue;
				}

				var encryptedValue = inputHandler.getEncryptedValue(inputObject);
				if (encryptedValue === null) {
					continue;
				}

				if (inputObject.value.length > 0 && encryptedValue.length <= 0) {
					continue;
				}

				var prefixedInputObject;
				var prefixedInputObjectName;

				if (utils.compareNoCase(inputObject.type, 'PASSWORD')) {
					prefixedInputObjectName = '_M_KDFEXT_' + inputObject.name;
				} else {
					prefixedInputObjectName = '_KDFEXT_' + inputObject.name;
				}

				if (utils.isLegacyBrowser()) {
					prefixedInputObject = window.document.createElement('<input type="hidden" name="' + prefixedInputObjectName + '" value="' + encryptedValue + '">');
				} else {
					prefixedInputObject = window.document.createElement('INPUT');
					prefixedInputObject.type = 'hidden';
					prefixedInputObject.name = prefixedInputObjectName;
					prefixedInputObject.value = encryptedValue;
				}

				result.push(prefixedInputObject);
			}

			return result;
		}

		function prepareSubmit(formObject) {
			utils.log.info('KDefenseHandler.prepareSubmit 호출됨');

			if (utils.isNullOrUndefined(formObject) || !utils.compareNoCase(formObject.tagName || '', 'FORM')) {
				utils.log.error('KDefenseHandler.prepareSubmit 올바른 폼 객체가 아님');
				return;
			}

			if (!(isKingsExtE2EEnabled() || isKingsExtE2EKCEnabled())) {
				return;
			}

			var newPrefixedInputObjects = makePrefixedInputObjects(formObject);
			for (var i = 0; i < newPrefixedInputObjects.length; i += 1) {
				var newPrefixedInputObject = newPrefixedInputObjects[i];
				var prevPrefixedInputObject = formObject.elements[newPrefixedInputObject.name];

				if (utils.isNullOrUndefined(prevPrefixedInputObject)) {
					formObject.appendChild(newPrefixedInputObject);
				} else if (prevPrefixedInputObject.value !== newPrefixedInputObject.value) {
					prevPrefixedInputObject.value = newPrefixedInputObject.value;
				}
			}
		}

		function getTargetElement(event) {
			return event.target || event.srcElement;
		}

		function isValidInputType(inputObject) {
			var type = inputObject.type.toUpperCase();

			switch (type) {
			case 'SEARCH':
			case 'TEL':
			case 'TEXT':
			case 'PASSWORD':
				return true;
			}

			return false;
		}

		function isInputElement(inputObject) {
			if (utils.isNullOrUndefined(inputObject)) {
				return false;
			}

			if (utils.isNullOrUndefined(inputObject.tagName)) {
				return false;
			}

			var tagName = inputObject.tagName.toUpperCase();

			switch (tagName) {
			case 'INPUT':
				return isValidInputType(inputObject);
			case 'TEXTAREA':
				return true;
			}

			return false;
		}

		function onKeyDown(event) {
			if (!bridge.isServiceConnected() || !isStarted) {
				return;
			}
            
			var target = utils.getActiveElement(window);
			
			// 2023-06-12 유민상 예외처리
			if(utils.getAttribute(target, 'data-security') !== 'on'){
                return;
            }
            
			if (!isInputElement(target)) {
				return;
			}
			
			if (event.isTriggeredByKDefense) {
				return;
			}

			var keyCode = event.keyCode || event.which;
			if (keyCode !== 0x08) {
				if (!utils.isAllowedKey(String.fromCharCode(keyCode), true) || event.ctrlKey || event.altKey || event.metaKey) {
					isProtectKey = false;
					return;
				}
				else {
					isProtectKey = true;
				}
				
				var cancelled = !utils.dispatchKeyboardEvent(target, 'keydown', event);
				if (!cancelled) {
					return;
				}

				event.preventDefault();
				event.stopPropagation();
				event.returnValue = false;

				sendKeyPress(target, {
					clear: false,
					dummy: keyCode
				}, function (response) {
				});
			}

			if (target.value.length - 1 === 0) {
				inputHandler.clear(target);
				return;
			}

			if (inputHandler.isEncrypted(target)) {
				inputHandler.moveCaretToEnd(target);
				inputHandler.removeCharacter(target);

				window.setTimeout(function () {
					inputHandler.validate(target);
				}, 0);
			}
		}
		
		function onKeyUp(event) {
			if (!bridge.isServiceConnected() || !isStarted) {
				return;
			}
			
			var target = utils.getActiveElement(window);
			
			// 2023-06-12 유민상 예외처리
            if(utils.getAttribute(target, 'data-security') !== 'on'){
                return;
            }
            
			if (!isInputElement(target)) {
				return;
			}

			if(utils.compareNoCase(target.type, 'PASSWORD') || !utils.hasAttribute(target, 'kdf-text-protect')){
				return;
			}

			var keyCode = event.keyCode || event.which;
			if (keyCode !== 0x08) {
				if(keyCode == 16){
					utils.log.info('KeyUp Code: 16 [' + keyCode);
					return;
				}
				else if (!utils.isAllowedKey(String.fromCharCode(keyCode), false) || event.ctrlKey || event.altKey || event.metaKey) {

					target.value = target.value.replace( /[ㄱ-ㅎ|ㅏ-ㅣ|가-힝]/g,"");
					
					if(isProtectKey == false) {
						sendKeyPress(target, {
							clear: false,
							dummy: keyCode
						}, function (response) {
							//utils.log.info('KeyUp response.Code: ' + response.keyCode);
							keyupCode = response.keyCode;
						});
					}
				}			
			}
		}

		function onKeyPress(event) {
			if(document.activeElement.contentDocument != undefined) {
			     if(document.activeElement.contentDocument.getElementById("finCertSdkIframe") != null) {
    				if(document.activeElement.contentDocument.getElementById("finCertSdkIframe").style.display == 'block') {
    					return;
    				}
				}
			}
			
			if (!bridge.isServiceConnected() || !isStarted) {
				return;
			}

			var target = utils.getActiveElement(window);
			
			// 2023-06-12 유민상 예외처리
            if(utils.getAttribute(target, 'data-security') !== 'on'){
                return;
            }
            
			if (!isInputElement(target)) {
				return;
			}

			var keyCode = event.keyCode || event.which;

			if (event.isTriggeredByKDefense || !utils.isAllowedKey(String.fromCharCode(keyCode), false) || event.ctrlKey || event.altKey || event.metaKey || event.charCode === 0) {
				return;
			}

			event.preventDefault();
			event.stopPropagation();
			event.returnValue = false;

			var clear = target.value.length === 0;
			if (clear) {
				inputHandler.clear(target);
			}
			
			sendKeyPress(target, {
				clear: clear,
				dummy: keyCode
			}, function (response) {
				var cancelled = !utils.dispatchKeyboardEvent(target, 'keypress', event);
				if (cancelled) {
					return;
				}
				
				if(utils.getBrowserType() === 'Edge' && (keyupCode >= 48 && keyupCode <= 57)){
					utils.log.info('keypress hangul = ' + response.keyCode);
					inputHandler.putCharacter(target, String.fromCharCode(keyupCode));
					inputHandler.validate(target);
					keyupCode = null;
				}
				else if (response.result === 'OK') {
					var extraInfo = {};
					if (!utils.isNullOrUndefined(response.encryptedValue)) {
						extraInfo.encryptedValue = response.encryptedValue;
					}

					inputHandler.putCharacter(target, String.fromCharCode(response.keyCode), extraInfo);
					inputHandler.validate(target);
				} else {
					utils.log.error('키 입력 오류: ' + response.result);

					inputHandler.putCharacter(target, String.fromCharCode(keyCode));
					inputHandler.validate(target);
				}

				utils.setAttribute(target, 'data-kdf-is-changed', true);
				utils.dispatchInputEvent(target);
			});
		}

		function onFocus(event) {
			if (!bridge.isServiceConnected() || !isStarted) {
				return;
			}
			
			if(isStopProtect == true) {
				return;
			}

			var target = getTargetElement(event);
			
			// 2023-06-12 유민상 예외처리
            if(utils.getAttribute(target, 'data-security') !== 'on'){
                return;
            }
            
			if (!isInputElement(target)) {
				return;
			}
			
			if(utils.getBrowserType() === 'Edge' && (utils.compareNoCase(target.type, 'TEXT') || utils.compareNoCase(target.type, 'textarea')) && !utils.hasAttribute(target, 'kdf-text-protect')){
				sendBlur(target);
				return;
			}

			var clear = target.value.length === 0;
			if (clear) {
				inputHandler.clear(target);
			}
           
           // 2023-06-12 유민상 예외처리
           if(utils.getAttribute(target, 'data-security') == 'on'){
           
               utils.setAttribute(target, 'data-kdf-is-changed', inputHandler.validate(target));
    
                sendFocus(target, {
                    clear: clear
                });
           }
			
		}

		function onBlur(event) {
			if (!bridge.isServiceConnected() || !isStarted) {
				return;
			}

			var target = getTargetElement(event);
			
			// 2023-06-12 유민상 예외처리
            if(utils.getAttribute(target, 'data-security') !== 'on'){
                return;
            }
            
			if (!isInputElement(target)) {
				return;
			}

			var clear = target.value.length === 0;
			if (clear) {
				inputHandler.clear(target);
			}

			if (inputHandler.validate(target)) {
				utils.setAttribute(target, 'data-kdf-is-changed', true);
			}

			if (utils.getAttributeBoolean(target, 'data-kdf-is-changed')) {
				utils.dispatchChangeEvent(target);
			}

			if (utils.getBrowserType() === 'Edge') {
				var nextTarget = event.relatedTarget;
				if (isInputElement(nextTarget)) {
					if((utils.compareNoCase(target.type, 'TEXT') || utils.compareNoCase(target.type, 'textarea')) && !utils.hasAttribute(target, 'kdf-text-protect')){
						var Text = null;
					}
					else
						return;
				}
			}
			if (utils.getBrowserType() === 'Chrome' || utils.getBrowserType() === 'Firefox' || utils.getBrowserType() === 'Opera' || utils.getBrowserType() === 'Safari' || utils.getBrowserType() === 'Chrome_Edge') {
				if(KOS.onMultiEdgeVerify() === 'success') {
					var nextTarget = event.relatedTarget;
					if (isInputElement(nextTarget)) {
						return;
					}
				}
			}
			
			sendBlur(target);
			target.blur();
		}

		function onClick(event) {
			if (!bridge.isServiceConnected() || !isStarted) {
				return;
			}

			var target = utils.getActiveElement(window);
			
			// 2023-06-12 유민상 예외처리
            if(utils.getAttribute(target, 'data-security') !== 'on'){
                return;
            }
            
			if (!isInputElement(target)) {
				return;
			}

			if (inputHandler.isEncrypted(target)) {
				inputHandler.moveCaretToEnd(target);
			}
		}
		
		function onCopyPasteCut(event) {
			if (!bridge.isServiceConnected() || !isStarted) {
				return;
			}

			var target = utils.getActiveElement(window);
			
			// 2023-06-12 유민상 예외처리
            if(utils.getAttribute(target, 'data-security') !== 'on'){
                return;
            }
            
			if (!isInputElement(target)) {
				return;
			}

			if (inputHandler.isEncrypted(target) || inputHandler.isExtEncrypted(target)) {
				event.preventDefault();
				return false;
			}
		}

		function onSubmit(event) {
			if (!isStarted) {
				return;
			}

			var target = event.target || event.srcElement;


			prepareSubmit(target);
		}

		function isAccessible(windowt) {
			var result = null;

			try {
				for (var i = 0; i < blacklistedWindows.length; i += 1) {
					if (blacklistedWindows[i] === windowt) {
						return false;
					}
				}
			} catch (e) {
				return false;
			}

			try {
				result = windowt.location.href;
			} catch (e) {}

			if (utils.isNullOrUndefined(result)) {
				blacklistedWindows.push(windowt);
				return false;
			}

			return true;
		}

		function isRegisteredObject(windowt) {
			return windowt.__kos_events_registered === true;
		}

		function updateGlobalKey(windowt) {
			if (isKingsExtE2EEnabled() || isKingsLegacyExtE2EEnabled() || isKingsExtE2EKCEnabled() || isInitechExtE2EEnabled()) {
				var formObjects = windowt.document.getElementsByTagName('FORM');

				var srcKeyInputObject = windowt.document.createElement('INPUT');
				srcKeyInputObject.name = encryptedGlobalKeyName;
				srcKeyInputObject.value = encryptedGlobalKey;
				srcKeyInputObject.type = 'hidden';

				for (var i = 0; i < formObjects.length; i += 1) {
					var formObject = formObjects[i];
					var keyInputObject = formObject.elements[encryptedGlobalKeyName];

					if (!utils.isNullOrUndefined(keyInputObject)) {
						if (keyInputObject.value !== encryptedGlobalKey) {
							keyInputObject.value = encryptedGlobalKey;
						}

						continue;
					}

					formObject.appendChild(srcKeyInputObject.cloneNode());
				}
			}
		}

		function registerInputObject(inputObject, completeCallback) {
			var extraInfo = {};

			if (utils.getAttributeBoolean(inputObject, 'data-kdf-is-protected')) {
				completeCallback();
				return;
			}

			if (utils.getAttributeBoolean(inputObject, 'data-kdf-is-registered')) {
				completeCallback();
				return;
			}

			var registerInfo = inputHandler.onPreRegister(inputObject);
			if (registerInfo === null) {
				completeCallback();
				return;
			}

			extraInfo.caseOption = normalizeCaseOption(utils.getAttribute(inputObject, 'data-kdf-option', 'none'));

			if (!utils.isNullOrUndefined(registerInfo.customKey)) {
				extraInfo.customKey = registerInfo.customKey;
			}

			utils.setAttribute(inputObject, 'data-kdf-is-registered', true);

			sendRegister(inputObject, extraInfo, function (response) {
				if (response.result === 'OK') {
					var extraInfo = {};
					if (!utils.isNullOrUndefined(response.encryptedKey)) {
						extraInfo.encryptedKey = response.encryptedKey;
					}

					if (inputHandler.onPostRegister(inputObject, extraInfo)) {
						utils.setAttribute(inputObject, 'autocomplete', 'off');
						utils.setAttribute(inputObject, 'data-kdf-is-protected', true);
						inputHandler.onRegistered(inputObject, extraInfo);
					} else {
						inputHandler.onRegisterFailed(inputObject);
					}
				} else {
					utils.setAttribute(inputObject, 'data-kdf-is-registered', false);
					inputHandler.onRegisterFailed(inputObject);
				}

				completeCallback();
			});
		}

		function registerInputObjects(windowt, completeCallback) {
			var inputObjects = windowt.document.getElementsByTagName('INPUT');

			var totalObjects = inputObjects.length;
			var completedObjects = 0;

			function onCompleteCallback() {
				completedObjects += 1;

				if (completedObjects < totalObjects) {
					return;
				}

				completeCallback();
			}

			for (var i = 0; i < inputObjects.length; i += 1) {
				registerInputObject(inputObjects[i], onCompleteCallback);
			}
		}

		function registerFormObjects(windowt) {
			try {
				var formObjects = windowt.document.getElementsByTagName('FORM');
				var totalObjects = formObjects.length;

				for (var i = 0; i < formObjects.length; i += 1) {
					var formObject = formObjects[i];

					if (!isRegisteredObject(formObject)) {
						utils.addEventListener(formObject, 'submit', onSubmit, false);

						formObject.__kos_events_registered = true;
					}
				}
			} catch (e) {}
		}

		function registerWindow(windowt) {
			if (!isRegisteredObject(windowt)) {
				utils.log.info('윈도우 등록됨: ' + windowt.location.href);

				if (!utils.isInternetExplorer()) {
					utils.addEventListener(windowt, 'keydown', onKeyDown, true);
					if(utils.getBrowserType() === 'Edge'){
						utils.addEventListener(windowt, 'keyup', onKeyUp, true);
					}
					utils.addEventListener(windowt, 'keypress', onKeyPress, true);

					utils.addEventListener(windowt, 'focus', onFocus, true);
					utils.addEventListener(windowt, 'blur', onBlur, true);

					utils.addEventListener(windowt, 'click', onClick, true);
					utils.addEventListener(windowt, 'copy', onCopyPasteCut, true);
					utils.addEventListener(windowt, 'paste', onCopyPasteCut, true);
					utils.addEventListener(windowt, 'cut', onCopyPasteCut, true);
				}

				utils.addEventListener(windowt, 'submit', onSubmit, true);

				windowt.__kos_events_registered = true;
			}

			// IE 8 이하 호환
			if (utils.isInternetExplorer()) {
				registerFormObjects(windowt);
			}

			if (!utils.isInternetExplorer() && bridge.isServiceConnected() && isStarted) {
				registerInputObjects(windowt, function () {
				});

				updateGlobalKey(windowt);
			}
		}

		function registerWindows(windowt) {
			var frames = windowt.frames;

			registerWindow(windowt);

			for (var i = 0; i < frames.length; i += 1) {
				var frame = frames[i];

				if (isAccessible(frame)) {
					registerWindows(frame);
				}
			}
		}

		function scanDOM() {
			registerWindows(window);
		}

		function loadPentaWebCrypto(responseCallback) {
			var timeWaited = 0;

			function tryLoadPentaWebCrypto() {
				if (typeof _PENTA_CHECK !== 'undefined') {
					if(_PENTA_CHECK === 'Y'){
						isExternalCryptoInitialized = true;
					}
				}				
				
				if (!isExternalCryptoInitialized) {
					timeWaited += 100;

					if (timeWaited > 4000) {
						responseCallback({
							result: 'ERROR_SESSION_EXCHANGE_TIMED_OUT',
							message: 'WebCrypto is not responding'
						});

						return;
					}

					window.setTimeout(tryLoadPentaWebCrypto, 100);
					return;
				}

                if(!utils.isNullOrUndefined(webcrypto)){
                    var sessionRequest = webcrypto.e2e.getPasswordKeboardSession('2', 'SEED');
                }

				
				sessionRequest.oncomplete = function (result) {
					responseCallback({
						result: 'OK',
						session: result
					});
				};
				sessionRequest.onerror = function (errorMessage) {
					responseCallback({
						result: 'ERROR_SESSION_EXCHANGE_FAILED',
						message: errorMessage
					});
				};
			}

			utils.log.info('KDefenseHandler.loadPentaWebCrypto WebCrypto 초기화 대기중');
			tryLoadPentaWebCrypto();
		}

		function onPreStart(responseCallback) {
			utils.log.info('KDefenseHandler.onPreStart 호출됨');
			isStopProtect = false;

			if (isPentaE2EEnabled()) {
				if (encryptedGlobalKey !== null) {
					utils.log.info('KDefenseHandler.onPreStart Penta Security WebCrypto 캐싱된 세션 사용');

					responseCallback({
						result: 'OK'
					});

					return;
				}

				loadPentaWebCrypto(function (response) {
					if (response.result === 'OK') {
						utils.log.info('KDefenseHandler.onPreStart Penta Security WebCrypto 로드 완료됨');
                        isExternalCompleteInitailized = true;
                        encryptedGlobalKey = response.session;

					} else {
						utils.log.info('KDefenseHandler.onPreStart Penta Security WebCrypto 로드 실패: ' + response.result + ' ' + response.message);
					}

					responseCallback({
						result: 'OK'
					});
				});

				return;
			}

			responseCallback({
				result: 'OK'
			});
		}

		base.onDocumentReady = function () {
			utils.log.info('KDefenseHandler.onDocumentReady 호출됨');
			
			if (productParameters === null) {
				productParameters = srcWindow.KOS_GetKDefenseParameters();
				nOption = parseInt(productParameters.nOption, 16);
				nOptionEx3 = parseInt(productParameters.nOptionEx3, 16);

				// UNet
				inputHandler.addE2EHandler(new KDefenseCertExtE2EHandler(window, {
					identifier: 'unet-kdf-option',
					publicKey: {
						source: 'GLOBAL_ELEMENT',
						name: 'tn_e2e_kdf_pubkey'
					},
					sessionKey: 'tn_e2e_kdf_encsymm',
					value: 'tn_e2e_kdf_encdata',
					isProtected: 'tn_e2e_kdf_protected'
				}));
				// Yettie, KOSCOM
				inputHandler.addE2EHandler(new KDefenseCertExtE2EHandler(window, {
					identifier: 'data-kdf-e2e-pubkey',
					publicKey: {
						source: 'ATTRIBUTE',
						name: 'data-kdf-e2e-pubkey'
					},
					sessionKey: 'data-kdf-e2e-encsk',
					value: 'data-kdf-e2e-value',
					isProtected: 'data-kdf-e2e-enabled'
				}));

				// K-Defense
				if (isKingsLegacyExtE2EEnabled()) {
					inputHandler.addE2EHandler(new KDefenseLegacyExtE2EHandler(window, {
						autoRegister: true,
						encrypt: '_KDF_',
						extEncrypt: '_KDFEXT_'
					}));
				} else if (isKingsExtE2EEnabled()) {
					inputHandler.addE2EHandler(new KDefenseExtE2EHandler(window));
				} else if (isKingsExtE2EKCEnabled()) {
					inputHandler.addE2EHandler(new KDefenseExtE2EKCHandler(window));
				}

				// Penta Security WebCrypto
				if (isPentaE2EEnabled()) {
					inputHandler.addE2EHandler(new PentaWebCryptoE2EHandler(window));
				}

				// Initech CrossWeb
				if (isInitechExtE2EEnabled()) {
					inputHandler.addE2EHandler(new KDefenseLegacyExtE2EHandler(window, {
						autoRegister: false,
						encrypt: '_E2E123_',
						extEncrypt: '_ExtE2E123_'
					}));
				}
			}

			scanDOM();

			window.setInterval(function () {
				scanDOM();
			}, 500);
		};

		base.onStart = function (responseCallback) {
			utils.log.info('KDefenseHandler.onStart 호출됨');

			try {
				onPreStart(function (response) {
					if (response.result !== 'OK') {
						return;
					}

					try {
						sendInfo(function (response) {
							if (response.result !== 'OK') {
								responseCallback(response);
								return;
							}

							if (!utils.isNullOrUndefined(response.encryptedGlobalKeyName)) {
								encryptedGlobalKeyName = response.encryptedGlobalKeyName;
							}

							if (!utils.isNullOrUndefined(response.encryptedGlobalKey)) {
								encryptedGlobalKey = response.encryptedGlobalKey;
							}
							
							if (!utils.isNullOrUndefined(response.MultiEdge)) {
								MultiEdge = response.MultiEdge;
							}
							

							isStarted = true;

							scanDOM();

							responseCallback({
								result: 'OK'
							});
						});
					} catch (e) {
						responseCallback({
							result: 'ERROR_PROTECTION_FAILED'
						});
					}
				});
			} catch (e) {
				responseCallback({
					result: 'ERROR_UNEXPECTED_EXCEPTION'
				});
			}
		};

		base.getProductName = function () {
			return 'K-Defense';
		};

		base.getParameterName = function () {
			return 'kparam';
		};

		base.getParameters = function () {
			productParameters = srcWindow.KOS_GetKDefenseParameters();
			
			if(KOS.isKOSChrome){
				productParameters.is64Bit = KOS_Chrome.is64Bit;
			}

			return utils.makeParameterString(productParameters, '|');
		};

		base.isEnabled = function () {
			return !utils.isNullOrUndefined(srcWindow.KOS_GetKDefenseParameters);
		};
		
		base.UseChecked = function () {
			if(!utils.isNullOrUndefined(srcWindow.KOS_GetConfig.kdfStart)){
				return srcWindow.KOS_GetConfig.kdfStart;
			}
		
			return true;
		}
		
		base.getModuleInstalled = function () {
			return moduleInstall;
		}
		
		base.setModuleInstalled = function () {
			moduleInstall = true;
			return;
		}

		base.getExportedMethods = function () {
			var exports = {};
			exports.scanDOM = scanDOM;
			exports.getSeed = function () {
				if (isKingsExtE2EEnabled() || isKingsLegacyExtE2EEnabled() || isKingsExtE2EKCEnabled() || isInitechExtE2EEnabled()) {
					var srcWindowFormObjects = srcWindow.document.getElementsByName(encryptedGlobalKeyName);
					if (srcWindowFormObjects.length > 0) {
						return srcWindowFormObjects[0].value || '';
					}

					var formObjects = window.document.getElementsByName(encryptedGlobalKeyName);
					if (formObjects.length > 0) {
						return formObjects[0].value || '';
					}
				}

				if (encryptedGlobalKey === null) {
					return null;
				}

				return encryptedGlobalKey;
			};
			exports.registerElement = function (inputObject, caseOption) {
				if (utils.isNullOrUndefined(caseOption)) {
					caseOption = 'none';
				}

				utils.setAttribute(inputObject, 'data-kdf-option', caseOption);
			};
			exports.registerElementWithKey = function (inputObject, caseOption, keyName, key) {
				utils.setAttribute(inputObject, keyName, key);
				exports.registerElement(inputObject, caseOption);
			};
			exports.prepareSubmit = prepareSubmit;
			exports.getEncryptedKey = function (inputObject) {
				return inputHandler.getEncryptedKey(inputObject);
			};
			exports.getInputSessionKey = exports.getEncryptedKey;
			exports.getEncryptedValue = function (inputObject) {
				return inputHandler.getEncryptedValue(inputObject);
			};
			exports.getProtectedValue = exports.getEncryptedValue;
			exports.protectSAPGUI = function () {
				if (!bridge.isServiceSupported()) {
					utils.log.warn('KOS.protectSAPGUI 지원되지 않는 웹 브라우저');
					return;
				}

				if (!bridge.isServiceConnected()) {
					bridge.connectService(function (response) {
						if (response.result !== 'OK') {
							utils.log.warn('KOS.protectSAPGUI 서비스에 연결할 수 없음');
							return;
						}

						exports.protectSAPGUI();
					});

					return;
				}

				var parameters = {};
				parameters.webbrowser = 'SAPGUI';
				parameters.domain = window.location.hostname;
				parameters.title = "청소년안정망시스템";

				bridge.sendDataService('POST', '/info', utils.makeQueryString(parameters), function (response) {
					if (response.result !== 'OK') {
						utils.log.warn('KOS.protectSAPGUI 보호 요청 실패');
						return;
					}

					utils.log.info('KOS.protectSAPGUI 보호 요청 성공');
				});
			};
			exports.focusOut = function (completeCallback) {
				if (utils.isNullOrUndefined(completeCallback)) {
					completeCallback = function () {};
				}

				if (utils.isInternetExplorer()) {
					completeCallback();
					return;
				}

				if (!bridge.isServiceConnected()) {
					completeCallback();
					return;
				}

				var target = utils.getActiveElement();
				if (isInputElement(target)) {
					sendBlur(target, completeCallback);
				} else {
					completeCallback();
				}
			};
			exports.stopProtection = function (completeCallback) {
				if (utils.isNullOrUndefined(completeCallback)) {
					completeCallback = function () {};
				}

				utils.waitSomething(function () {
					return bridge.isHandlerConnected() || bridge.isServiceConnected();
				}, function () {
					var parameters = {};
					parameters[base.getParameterName()] = base.getParameters() + 'blStop=1|';

					function onSendInfoResponse(response) {
						completeCallback({
							result: 'OK'
						});
					}

					if (bridge.isHandlerConnected()) {
						bridge.sendDataHandler('info', utils.makeQueryString(parameters), onSendInfoResponse);
					} else if (bridge.isServiceConnected()) {
						parameters.webbrowser = utils.getBrowserType();
						parameters.domain = window.location.hostname;
						parameters.title = "청소년안정망시스템";

						bridge.sendDataService('POST', '/info', utils.makeQueryString(parameters), onSendInfoResponse);
					} else {
						completeCallback({
							result: 'OK'
						});
					}
					isStopProtect = true;
				});
			};
			exports.onExternalCryptoInitialized = function (extraInfo) {
				isExternalCryptoInitialized = true;
			};
			exports.onExternalCompleteInitailized = function () {
                return isExternalCompleteInitailized;
            };
			exports.onMultiEdgeVerify = function () {
				if(!utils.isNullOrUndefined(MultiEdge))
					return MultiEdge;
			};
			exports.onServiceModuleVer = function () {
				return bridge.isMultiServiceVer();
			};

			return exports;
		};
	}

	// i-Defense 핸들러 클래스
	function IDefenseHandler(window, srcWindow, bridge) {
		var base = this;
		var moduleInstall = false;

		function sendInfo(responseCallback) {
			var parameters = {};
			parameters[base.getParameterName()] = base.getParameters();

			if (bridge.isHandlerSupported() && bridge.isHandlerConnected()) {
				bridge.sendDataHandler('info', utils.makeQueryString(parameters), responseCallback);
			} else if (bridge.isServiceSupported() && bridge.isServiceConnected()) {
				parameters.webbrowser = utils.getBrowserType();
				parameters.domain = window.location.hostname;
				parameters.title = "청소년안정망시스템";

				bridge.sendDataService('POST', '/info', utils.makeQueryString(parameters), responseCallback);
			} else {
				responseCallback({
					result: 'ERROR_UNSUPPORTED_ENVIRONMENT'
				});
			}
		}

		base.onDocumentReady = function () {};

		base.onStart = function (responseCallback) {
			utils.log.info('IDefenseHandler.onStart 호출됨');

			sendInfo(function (response) {
				if (response.result !== 'OK') {
					responseCallback({
						result: 'ERROR_PROTECTION_FAILED'
					});

					return;
				}

				responseCallback({
					result: 'OK'
				});
			});
		};

		base.getProductName = function () {
			return 'i-Defense';
		};

		base.getParameterName = function () {
			return 'iparam';
		};

		base.getParameters = function () {
			return utils.makeParameterString(srcWindow.KOS_GetIDefenseParameters(), ';');
		};

		base.isEnabled = function () {
			return !utils.isNullOrUndefined(srcWindow.KOS_GetIDefenseParameters);
		};

		base.getExportedMethods = function () {
			var exports = [];
			return exports;
		};
		
		base.UseChecked = function () {
			if(!utils.isNullOrUndefined(srcWindow.KOS_GetConfig.idfStart)){
				return srcWindow.KOS_GetConfig.idfStart;
			}
		
			return true;
		}
		
		base.getModuleInstalled = function () {
			return moduleInstall;
		}
		
		base.setModuleInstalled = function () {
			moduleInstall = true;
			return;
		}
	}

	// ProM-Defense 핸들러 클래스
	function ProMDefenseHandler(window, srcWindow, bridge) {
		var base = this;
		var moduleInstall = false;

		function sendInfo(responseCallback) {
			var parameters = {};
			parameters[base.getParameterName()] = base.getParameters();

			if (bridge.isHandlerSupported() && bridge.isHandlerConnected()) {
				bridge.sendDataHandler('info', utils.makeQueryString(parameters), responseCallback);
			} else if (bridge.isServiceSupported() && bridge.isServiceConnected()) {
				parameters.webbrowser = utils.getBrowserType();
				parameters.domain = window.location.hostname;
				parameters.title = "청소년안정망시스템";

				bridge.sendDataService('POST', '/info', utils.makeQueryString(parameters), responseCallback);
			} else {
				responseCallback({
					result: 'ERROR_UNSUPPORTED_ENVIRONMENT'
				});
			}
		}

		base.onDocumentReady = function () {};

		base.onStart = function (responseCallback) {
			utils.log.info('ProMDefenseHandler.onStart 호출됨');

			sendInfo(function (response) {
				if (response.result !== 'OK') {
					responseCallback({
						result: 'ERROR_PROTECTION_FAILED'
					});

					return;
				}

				responseCallback({
					result: 'OK'
				});
			});
		};

		base.getProductName = function () {
			return 'ProM-Defense';
		};

		base.getParameterName = function () {
			return 'promparam';
		};

		base.getParameters = function () {
			return utils.makeParameterString(srcWindow.KOS_GetProMDefenseParameters(), '|');
		};

		base.isEnabled = function () {
			return !utils.isNullOrUndefined(srcWindow.KOS_GetProMDefenseParameters);
		};

		base.getExportedMethods = function () {
			var exports = [];
			return exports;
		};
		
		base.UseChecked = function () {
			if(!utils.isNullOrUndefined(srcWindow.KOS_GetConfig.proMStart)){
				return srcWindow.KOS_GetConfig.proMStart;
			}
		
			return true;
		}
		
		base.getModuleInstalled = function () {
			return moduleInstall;
		}
		
		base.setModuleInstalled = function () {
			moduleInstall = true;
			return;
		}
	}

	utils.log.info('스크립트 로드됨: ' + window.location.href);

	var topWindow = utils.getTopWindow();
	var bridge = null;

	utils.log.info('최상위 창: ' + topWindow.location.href + ' - ' + utils.getTopTitle());

	if (!utils.isNullOrUndefined(topWindow.KOS)) {
		try {
			topWindow.KOS.isDisposed();

			utils.log.info('이미 생성된 KOS 객체를 사용함: ' + window.location.href);
			bridge = topWindow.KOS;
		} catch (e) {
			utils.log.info('기존 생성된 KOS 객체가 해제됨: ' + window.location.href);
		}
	}

	if (bridge === null) {
		utils.log.info('새 KOS 객체 생성: ' + window.location.href);

		bridge = new Bridge(topWindow, window);
		bridge.addProductHandler(new KDefenseHandler(topWindow, window, bridge));
		bridge.addProductHandler(new IDefenseHandler(topWindow, window, bridge));
		bridge.addProductHandler(new ProMDefenseHandler(topWindow, window, bridge));

		topWindow.KOS = bridge;
	}

	window.KOS = bridge;

	utils.log.info('DOM 준비를 기다리는 중: ' + window.location.href);
	utils.waitDOM(function () {
		utils.log.info('DOM 준비됨: ' + window.location.href);
		var script = window.document.createElement('script');
		if(KOS.isKOSChrome){
			var path = utils.getJsPath()
			script.src = path+'/kos-chrome.js';
			document.getElementsByTagName('head')[0].appendChild(script);
			script.addEventListener("load",function(){
				utils.log.info('설정을 기다리는 중: ' + window.location.href);
				utils.waitConfig(function () {
					utils.log.info('설정 준비됨: ' + window.location.href);

					// onReady 이벤트 전달
					bridge.onReady(window);
				});	
			});
		}else{
			utils.log.info('설정을 기다리는 중: ' + window.location.href);
			utils.waitConfig(function () {
				utils.log.info('설정 준비됨: ' + window.location.href);

				// onReady 이벤트 전달
				bridge.onReady(window);
			});	
		}
	});
}(this));

/* JSON for Legacy Web Browsers */
"object"!==typeof JSON&&(JSON={});(function(){function m(a){return 10>a?"0"+a:a}function r(){return this.valueOf()}function t(a){u.lastIndex=0;return u.test(a)?'"'+a.replace(u,function(a){var c=w[a];return"string"===typeof c?c:"\\u"+("0000"+a.charCodeAt(0).toString(16)).slice(-4)})+'"':'"'+a+'"'}function p(a,l){var c,d,h,q,g=e,f,b=l[a];b&&"object"===typeof b&&"function"===typeof b.toJSON&&(b=b.toJSON(a));"function"===typeof k&&(b=k.call(l,a,b));switch(typeof b){case "string":return t(b);case "number":return isFinite(b)?String(b):"null";case "boolean":case "null":return String(b);case "object":if(!b)return"null";e+=n;f=[];if("[object Array]"===Object.prototype.toString.apply(b)){q=b.length;for(c=0;c<q;c+=1)f[c]=p(c,b)||"null";h=0===f.length?"[]":e?"[\n"+e+f.join(",\n"+e)+"\n"+g+"]":"["+f.join(",")+"]";e=g;return h}if(k&&"object"===typeof k)for(q=k.length,c=0;c<q;c+=1)"string"===typeof k[c]&&(d=k[c],(h=p(d,b))&&f.push(t(d)+(e?": ":":")+h));else for(d in b)Object.prototype.hasOwnProperty.call(b,d)&&(h=p(d,b))&&f.push(t(d)+(e?": ":":")+h);h=0===f.length?"{}":e?"{\n"+e+f.join(",\n"+e)+"\n"+g+"}":"{"+f.join(",")+"}";e=g;return h}}var x=/^[\],:{}\s]*$/,y=/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,z=/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,A=/(?:^|:|,)(?:\s*\[)+/g,u=/[\\\"\u0000-\u001f\u007f-\u009f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,v=/[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;"function"!==typeof Date.prototype.toJSON&&(Date.prototype.toJSON=function(){return isFinite(this.valueOf())?this.getUTCFullYear()+"-"+m(this.getUTCMonth()+1)+"-"+m(this.getUTCDate())+"T"+m(this.getUTCHours())+":"+m(this.getUTCMinutes())+":"+m(this.getUTCSeconds())+"Z":null},Boolean.prototype.toJSON=r,Number.prototype.toJSON=r,String.prototype.toJSON=r);var e,n,w,k;"function"!==typeof JSON.stringify&&(w={"\b":"\\b","\t":"\\t","\n":"\\n","\f":"\\f","\r":"\\r",'"':'\\"',"\\":"\\\\"},JSON.stringify=function(a,l,c){var d;n=e="";if("number"===typeof c)for(d=0;d<c;d+=1)n+=" ";else"string"===typeof c&&(n=c);if((k=l)&&"function"!==typeof l&&("object"!==typeof l||"number"!==typeof l.length))throw Error("JSON.stringify");return p("",{"":a})});"function"!==typeof JSON.parse&&(JSON.parse=function(a,e){function c(a,d){var g,f,b=a[d];if(b&&"object"===typeof b)for(g in b)Object.prototype.hasOwnProperty.call(b,g)&&(f=c(b,g),void 0!==f?b[g]=f:delete b[g]);return e.call(a,d,b)}var d;a=String(a);v.lastIndex=0;v.test(a)&&(a=a.replace(v,function(a){return"\\u"+("0000"+a.charCodeAt(0).toString(16)).slice(-4)}));if(x.test(a.replace(y,"@").replace(z,"]").replace(A,"")))return d=eval("("+a+")"),"function"===typeof e?c({"":d},""):d;throw new SyntaxError("JSON.parse");})})();

