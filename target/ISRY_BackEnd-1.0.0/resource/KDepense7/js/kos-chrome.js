(function (window) {
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
			} else if (contains(userAgent, 'PBBrowser')) {
				result = 'PBBrowser';
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

	function KOS_Chrome_t(topWindow, window){
		var base = this;
		base.is64Bit = null;
		is64BitBrowser().then(info=> base.is64Bit = info);
		
		async function is64BitBrowser() {
			
			function getUserAgent(){
				return navigator.userAgentData.getHighEntropyValues(["bitness"]);
			}		
			
			var agent = await getUserAgent();
			
			if(agent.bitness == 64){
				return true;
			}else{
				return false;
			}
		}
		
		base.isDisposed = function () {
			return false;
		};
	}

	var topWindow = utils.getTopWindow();
	var KOS_chrome_t = null;

	if(!utils.isNullOrUndefined(topWindow.KOS_Chrome)){
		try{
			topWindow.KOS_Chrome.isDisposed();
			
			KOS_chrome_t = topWindow.KOS_Chrome;
		}catch(e){
			uitls.log.info('');
		}
	}

	if(KOS_chrome_t === null){
		KOS_chrome_t = new KOS_Chrome_t(topWindow, window);
		
		topWindow.KOS_Chrome = KOS_chrome_t;
	}
		


	window.KOS_Chrome = KOS_chrome_t;
}(this));