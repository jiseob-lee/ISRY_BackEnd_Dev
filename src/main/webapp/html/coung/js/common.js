$(function(){
	// layout
	includes();
	gnbAct();
	quick();

	// common
	tabAction();
	formFilename();

	// page
	joinAgree();	// 회원가입 - 개인정보처리동의
	sitemap();	// 홈 - 사이트맵
	eumParent();	// 마이페이지 - 이음부모교육
	faqTab();		// 센터소개 - 자주하는질문 - 탭반응
	faqList();		// 센터소개 - 자주하는질문
	solrobotGame();	// 상담실 - 솔로봇게임
	solrobotGameResize();	// 상담실 - 게임화면 리사이즈
	solrobotSurvey();		// 상담실 - 설문결과
	psychologyList()		// 상담실 - 웹심리검사

	$(window).on("scroll", function(){

	});
	$(window).resize(function(){
		resize();
	}).resize();
});

function resize() {
	var windowWidth = $(window).width();
	solrobotGameResize();	//상담실 - 게임화면 리사이즈
}

// Include
function includes() {
	$("#skip").load("../../inc/inc_skip.html");
	$("#l-header").load("../../inc/inc_header.html");
	$("#footer").load("../../inc/inc_footer.html");
}

// gnb PC
function gnbAct() {
	var gnb = $("#l-gnb");
	var due = 300;
	$(".gnb__depth-01", gnb).hover(function(){
		$(".gnb__depth-02").stop().slideDown(due);
	})
	gnb.mouseleave(function(){
		$(".gnb__depth-02").stop().slideUp(due);
	});

	var body = $('body');
	var btn = $(".c-btn__gnb");
	var box = $("#l-aside");
	var act = "is-active"
	var crt = "is-current"
	var duration = 300;
	var timeOut = duration / 2;
	btn.on("click", function(){
		if(box.is("."+act+"")) {
			body.removeClass("gnb-active");
			setTimeout(() => {
				btn.removeClass(act);
			}, timeOut);
			box.stop().animate({'right':'-100%'}, duration, function(){
				box.removeClass(act);
			});
		} else {
			body.addClass("gnb-active");
			box.addClass(act);
			box.stop().animate({'right':'0'}, duration);
			setTimeout(() => {
				btn.addClass(act);
			}, timeOut);
		}
	});

	$(".aside__depth-01 > li > a").click(function(){
		if($(this).is("."+act+"")) {
			$(this).removeClass("is-active");
			$(this).next().stop().slideUp(duration);
		} else {
			$(this).addClass("is-active");
			$(this).next().stop().slideDown(duration);
		}
	});
}

// Quick
function quick() {
	var quick = $("#quick");
	var menu = $(".quick__menu", quick);
	var btn = $(".quick__menu--btn", quick);
	var list = $(".quick__menu--list", quick);
	var timeSlide = 500;
	var timeOut = 300;

	btn.on("click", function(){
		if(menu.hasClass("is-active")) {
			list.slideUp(timeSlide);
			setTimeout(() => {
				menu.removeClass("is-active");
			}, timeOut);
		} else {
			list.slideDown(timeSlide);
			setTimeout(() => {
				menu.addClass("is-active");
			}, timeOut);
		}
	});
}

// Tab
function tabAction() {
	var tab = $("[aria-js-tab]");
	var cont = $("[aria-js-contents]");
	var btn = tab.find("a");
	var act = "is-active"

	btn.on("click", function() {
		var b = $(this);
		var i = b.index();
		b.addClass(act).siblings().removeClass(act);
		cont.eq(i).addClass(act).siblings().removeClass(act);
	});
}

// 첨부파일
function formFilename() {
	var fileTarget = $("[area-form-hidden]");
	fileTarget.on("change", function() {
		var box = $(this);
		if(window.FileReader){
			var filename = box[0].files[0].name;
		} else {
			var filename = box.val().split("/").pop().split("\\").pop();
		}
		box.siblings("[area-form-filename]").val(filename);
	});
}

// 회원가입 - 개인정보처리동의
function joinAgree() {
	var wrap = $(".c-join__agree-wrap");
	var chk = $(".c-set__agree input", wrap);
	var act = "is-checked"

	chk.each(function(){
		$(this).on("change", function(){
			var box = $(this).closest(wrap);
			box.addClass(act);
		});
	});
}

// 홈 - 사이트맵
function sitemap() {
	var sitemap = $(".sitemap--wrap .sitemap__item");
	var btn = $(".c-btn__arr--down", sitemap);
	var act = "is-active"

	btn.on("click", function() {
		var _sitemap = $(this).closest(sitemap);
		if(_sitemap.is("."+act+"")) {
			_sitemap.removeClass(act);
		} else {
			_sitemap.addClass(act);
		}
	});
}

// 마이페이지 - 이음부모교육
function eumParent() {
	var eum = $(".myeum--wrap");
	var box = $(".myeum__result--wrap", eum);
	var btn = $(".myeum__title", eum);
	var act = "is-active"

	btn.on("click", function(){
		var _box = $(this).closest(box);
		if(_box.is("."+act+"")) {
			_box.removeClass(act);
		} else {
			_box.addClass(act);
		}
	});
}

// FAQ
function faqTab() {
	var selected = $(".faq__select");
	var btn = $("[aria-js-select]", selected);
	var list = $("[aria-js-tab]", selected);
	var anch = $("a", list);
	var act = "is-active"
	var dur = 200;

	btn.on("click", function() {
		if(selected.is("."+act+"")) {
			selected.removeClass(act);
			list.stop().animate({'height':'0'}, dur, function(){
				list.removeClass(act);
			});
		} else {
			selected.addClass(act);
			list.addClass(act);
			list.stop().animate({'height':'200px'}, dur);
		}
	});

	anch.on("click", function() {
		if(selected.is(".re-size")) {
			var txt = $(this).text();
			btn.text(txt);
			selected.removeClass(act);
			list.stop().animate({'height':'0'}, dur, function(){
				list.removeClass(act);
			});
		}
	});

	$(window).resize(function(){
		var size = $(window).width();
		if (size >= 768){
			list.removeAttr("style").removeClass(act);
			selected.removeClass("re-size");
		} else {
			selected.addClass("re-size");
		}
	}).resize();
}
// FAQ
function faqList() {
	var faq = $(".faq__list");
	var list = $(".faq__item", faq);
	var btn = $("a", faq);
	var act = "is-active"

	btn.on("click", function() {
		var _list = $(this).closest(list);
		if(_list.is("."+act+"")) {
			_list.removeClass(act);
		} else {
			_list.addClass(act);
		}
	});
}

// 상담실 - 게임 : 설정변경
function solrobotGame() {
	var btn = $("[aria-btn-set]");
	var box = $("[aria-game-set]");
	var act = "is-active"
	btn.on("click", function() {
		if(btn.is("."+act+"")) {
			btn.removeClass(act);
			box.slideUp(100);
		} else {
			btn.addClass(act);
			box.slideDown(100);
		}
	});
}

// 화면 리사이즈
function solrobotGameResize() {
	var wrap = $(".solrobot-game--wrap")
	var game = $(".solrobot-game", wrap);
	var msg = $(".c-solrobot-game__msg", wrap);

	var _game = game.height();
	var _msg = msg.height();
	var size = (_game * 0.65) + _msg
	var n = size - _game

	if(_game >= size) {
		wrap.css("padding-bottom", "0");
	} else {
		wrap.css("padding-bottom", n);
	}
}

// 설문화면 이유
function solrobotSurvey() {
	var board = $(".c-board__solrobot-survey");
	var btn = $("button", board);
	var box = $(".box__reason", board);
	var act = "is-active"

	btn.on("click", function(){
		var chk = $(this).closest("tr");
		var _box = chk.next(box);

		if(chk.is("."+act+"")) {
			chk.removeClass("is-active");
			_box.removeClass("is-active");
		} else {
			chk.addClass("is-active");
			_box.addClass("is-active");
		}
		solrobotGameResize()
	});
}

// 3-2
function psychologyList() {
	var list = $(".c-board__list-psy");
	var subject = $(".subject", list);
	var detail = $(".detail", list);
	var btn = $("button", subject);
	var act = "is-active"

	btn.on("click", function(){
		var _btn = $(this);
		var _subject = _btn.closest(subject);
		var _detail = _subject.next(detail);
		if(_btn.is("."+act+"")) {
			_btn.removeClass(act)
			_detail.slideUp(200, function(){
				_detail.removeClass(act);
			});
		} else {
			_btn.addClass(act)
			_detail.slideDown(200, function(){
				_detail.addClass(act);
			});
		}
	});
}

/* Footer - Popup */
function footer_pop1() {
	var w = window.open('../html/customer/write.html','','scrollbars=auto,width=1000,height=800');
	w.document.title = "고객의소리";
}

function footer_pop4() {
	var w = window.open('../html/popup/polish.html','','scrollbars=auto,width=700,height=700');
	w.document.title = "이용약관";
}

function pop_reple() {
	var w = window.open('../html/counsel/pop_reple.html','','scrollbars=auto,width=700,height=700');
	w.document.title = "댓글상담";
}

function pop_700(url, popName) {
		var options = 'width=700, height=700, scrollbars=auto'
		window.open(url, popName, options);
	}
