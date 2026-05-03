/* 임시 */
/* 페이지 뎁스 확인용
1 - 청소년 상담 1388 이용안내
2 - 센터소개
3 - 상담실
4 - 상담정보
5 - 미디어 콘텐츠+
6 - 소통/참여
7 - 마리페이지
10 - 공통/기타
*/

function depthLnb(depth1,depth2) {
	var lnb = $("#l-lnb");
	var lnb_title_txt;
	var lnb_title = $("[area-lnb-title]", lnb);
	var lnb_menu = $(".lnb--menu", lnb);

	if(depth1 === 1) {
		lnb_title_txt = "청소년 상담 1388 <br>이용안내"
		lnb_list = `
			<li class="lnb__depth-01"><a href="../helpcall/helpcall.html">청소년 상담 1388 이용안내</a></li>
			<li class="lnb__depth-01"><a href="javascript:void(0);">청소년지원정책 <i class="xi-external-link"></i></a></li>
			<li class="lnb__depth-01"><a href="javascript:void(0);">청소년상담복지센터 <i class="xi-external-link"></i></a></li>
			<li class="lnb__depth-01"><a href="javascript:void(0);">청소년쉼터 <i class="xi-external-link"></i></a></li>
			<li class="lnb__depth-01"><a href="javascript:void(0);">청소년지원센터 꿈드림 <i class="xi-external-link"></i></a></li>
			`
	} else if(depth1 === 2) {
		lnb_title_txt = "센터소개"
		lnb_list = `
			<li class="lnb__depth-01"><a href="../introduce/history.html">연혁</a></li>
			<li class="lnb__depth-01"><a href="../introduce/notice.html">공지사항</a></li>
			<li class="lnb__depth-01"><a href="../introduce/faq.html">자주하는질문(FAQ)</a></li>
			<li class="lnb__depth-01"><a href="../introduce/comselor.html">컴슬러정보</a></li>
			<li class="lnb__depth-01"><a href="../introduce/contents.html">콘텐츠 활용 요청</a></li>
			`
	} else if(depth1 === 3) {
		lnb_title_txt = "상담실"
		lnb_list = `
			<li class="lnb__depth-01">
				<a href="../counsel/solrobot.html">솔로봇상담</a>
				<ul>
					<li class="lnb__depth-02"><a href="../counsel/solrobot.html">솔로봇상담</a></li>
					<li class="lnb__depth-02"><a href="../counsel/solrobot_board.html">솔로봇게시판</a></li>
					<li class="lnb__depth-02"><a href="../counsel/solrobot_msg.html">도담쌤의 응원 메시지</a></li>
				</ul>
			</li>
			<li class="lnb__depth-01">
				<a href="../counsel/psychology.html">웹심리검사</a>
				<ul>
					<li class="lnb__depth-02"><a href="../counsel/psychology.html">웹심리검사란?</a></li>
					<li class="lnb__depth-02"><a href="../counsel/psychology_list.html">대인관계</a></li>
					<li class="lnb__depth-02"><a href="../counsel/psychology_list_02.html">진로</a></li>
					<li class="lnb__depth-02"><a href="../counsel/psychology_list_03.html">중독</a></li>
					<li class="lnb__depth-02"><a href="../counsel/psychology_list_04.html">성격/정서</a></li>
					<li class="lnb__depth-02"><a href="../counsel/psychology_list_05.html">부모</a></li>
					<li class="lnb__depth-02"><a href="../counsel/psychology_reple.html">웹심리검사상담자답변</a></li>
				</ul>
			</li>
			<li class="lnb__depth-01">
				<a href="../counsel/baord_chat.html">채팅상담실</a>
				<ul>
					<li class="lnb__depth-02"><a href="../counsel/baord_chat.html">채팅상담실</a></li>
					<li class="lnb__depth-02"><a href="../counsel/connect_chat_apply.html">잇는채팅 온라인 신청</a></li>
				</ul>
			</li>
			<li class="lnb__depth-01">
				<a href="../counsel/secbbs.html">게시판상담실</a>
				<ul>
					<li class="lnb__depth-02"><a href="../counsel/secbbs.html">게시판상담실</a></li>
					<li class="lnb__depth-02"><a href="javascript:void(0);">코로나19 특별게시판</a></li>
				</ul>
			</li>
			<li class="lnb__depth-01"><a href="../counsel/secbbs_reple.html">댓글상담실</a></li>
			<li class="lnb__depth-01">
				<a href="../html/eum/eum_intro.html">온라인부모상담</a>
				<ul>
					<li class="lnb__depth-02"><a href="../eum/eum_intro.html">온라인부모교육 이음-e</a></li>
					<li class="lnb__depth-02"><a href="../eum/eum_touch.html">자녀맘 터치법</a></li>
				</ul>
			</li>
			<li class="lnb__depth-01"><a href="../counsel/counsel_review.html">사이버상담후기</a></li>
			`
	} else if(depth1 === 4) {
		lnb_title_txt = "상담정보"
		lnb_list = `
		<li class="lnb__depth-01">
			<a href="../gomin/intro.html">고민해결백과</a>
			<ul>
				<li class="lnb__depth-02"><a href="../gomin/intro.html">고민해결백과</a></li>
				<li class="lnb__depth-02"><a href="../gomin/reple_main.html">고민해결백과<br>상담자답변</a></li>
			</ul>
		</li>
		<li class="lnb__depth-01"><a href="../gomin/toon.html">상담툰(TOON)</a></li>
		<li class="lnb__depth-01"><a href="../gomin/youth_mind.html">청소년마음정보알리미</a></li>
		<li class="lnb__depth-01"><a href="../gomin/news.html">청소년뉴스</a></li>
		<li class="lnb__depth-01"><a href="../gomin/site.html">유용한 사이트</a></li>
	`
	} else if(depth1 === 5) {
		lnb_title_txt = "미디어 콘텐츠+"
		lnb_list = `
		<li class="lnb__depth-01">
			<a href="../hopemessage/list.html">희망메세지(e-엽서)</a>
			<ul>
				<li class="lnb__depth-02"><a href="../hopemessage/list.html">전체</a></li>
				<li class="lnb__depth-02"><a href="javascript:void(0);">기념일</a></li>
				<li class="lnb__depth-02"><a href="javascript:void(0);">다가가기</a></li>
				<li class="lnb__depth-02"><a href="javascript:void(0);">사랑</a></li>
				<li class="lnb__depth-02"><a href="javascript:void(0);">위로</a></li>
				<li class="lnb__depth-02"><a href="javascript:void(0);">응원</a></li>
			</ul>
		</li>
		<li class="lnb__depth-01"><a href="../common/cardnews.html">카드뉴스</a></li>
		<li class="lnb__depth-01"><a href="../common/growingpains.html">성장통</a></li>
		<li class="lnb__depth-01"><a href="../common/school_pds.html">미디어자료실</a></li>
		<li class="lnb__depth-01"><a href="../common/pod_free.html">고민프리(FREE) 상담소</a></li>
	`
	} else if(depth1 === 6) {
		lnb_title_txt = "소통/참여"
		lnb_list = `
		<li class="lnb__depth-01"><a href="../hopemessage/eventlist.html">이벤트</a></li>
		`
	} else if(depth1 === 7) {
		lnb_title_txt = "마이페이지"
		lnb_list = `
		<li class="lnb__depth-01"><a href="../mypage/my_page.html">개인정보관리</a></li>
		<li class="lnb__depth-01">
			<a href="../counsel/result_bbs.html">고민상담내역</a>
			<ul>
				<li class="lnb__depth-02"><a href="../counsel/result_bbs.html">비밀게시판 상담내역</a></li>
				<li class="lnb__depth-02"><a href="../counsel/chat_bbs.html">채팅 상담내역</a></li>
				<li class="lnb__depth-02"><a href="../counsel/result_bbs_02.html">공개게시판 상담내역</a></li>
			</ul>
		</li>
		<li class="lnb__depth-01"><a href="../mypage/mychat_result.html">심리겸사결과</a></li>
		<li class="lnb__depth-01"><a href="../mypage/supt_chat_list.html">내 글 보기</a></li>
		<li class="lnb__depth-01"><a href="../mypage/eum_mypage.html">이음-e 부모교육</a></li>
		`
	} else if(depth1 === 10) {
		lnb_title_txt = "홈"
		if(depth2 === 1){
			lnb_list = `<li class="lnb__depth-01"><a href="../common/sitemap.html">사이트맵</a></li>`
		} else if(depth2 === 2) {
			lnb_list = `<li class="lnb__depth-01"><a href="../common/contact.html">찾아오시는길</a></li>`
		} else if(depth2 === 3) {
			lnb_list = `<li class="lnb__depth-01"><a href="../common/policy.html">개인정보처리방침</a></li>`
		}
		depth2 = 0;
	}

	lnb_title.html(lnb_title_txt);
	lnb_menu.append(lnb_list);
	lnb_menu.attr("area-depth", depth1);

	var lnb_item = $(".lnb__depth-01", lnb);
	if(depth2){
		var _depth2 = depth2 -1 ;
		lnb_item.eq(_depth2).addClass("is-active");
	}
}

function depthTitle(depth1, depth2, depth3) {
	var title = $("[aria-title-contents]");
	var path = $("[aria-path]");
	var path_depth_1_txt;
	var path_depth_2_tit;

	if(depth1 === 1) { // 청소년 상담 1388 이용안내
		path_depth_1_txt = ["청소년 상담 1388 ","../helpcall/helpcall.html"]
		if(depth2 === 1) {
			path_depth_2_tit = "청소년상담1388 이란 (온라인 상담 ‧ 카카오톡 및 문자 ‧ 전화)"
			path_depth_2_txt = ["청소년 상담 1388","../helpcall/helpcall.html"]
		}
	} else if(depth1 === 2) { // 센터소개
		path_depth_1_txt = ["센터소개","../introduce/history.html"]
		if(depth2 === 1) {
			path_depth_2_txt = ["연혁","../introduce/history.html"]
		} else if(depth2 === 2) {
			path_depth_2_txt = ["공지사항","../introduce/notice.html"]
		} else if(depth2 === 3) {
			path_depth_2_txt = ["자주하는질문(FAQ)","../introduce/faq.html"]
		} else if(depth2 === 4) {
			path_depth_2_txt = ["컴슬러정보","../introduce/comselor.html"]
		} else if(depth2 === 5) {
			path_depth_2_txt = ["콘텐츠 활용 요청","../introduce/contents.html"]
		}
	} else if(depth1 === 3) { // 상담실
		path_depth_1_txt = ["상담실","../counsel/solrobot.html"]
		if(depth2 === 1) {
			path_depth_2_txt = ["솔로봇상담","../counsel/solrobot.html"]
			if(depth3 === 2) {
				path_depth_2_tit = "솔로봇게시판"
			} else if(depth3 === 3) {
				path_depth_2_tit = "도담쌤의 응원 메시지"
			}
		} else if(depth2 === 2) {
			path_depth_2_txt = ["웹 심리검사","../counsel/psychology.html"]
			if(depth3 === 7) {
				path_depth_2_tit = "웹심리 상담자 답변"
			}
		} else if(depth2 === 3) {
			path_depth_2_txt = ["채팅상담실","../counsel/baord_chat.html;"]
			if(depth3 === 2) {
				path_depth_2_tit = "잇는채팅 온라인 신청"
				path_depth_2_txt = ["잇는채팅 온라인 신청","../counsel/connect_chat_apply.html"]
			}
		} else if(depth2 === 4) {
			path_depth_2_txt = ["게시판상담실","../counsel/secbbs.html"]
		} else if(depth2 === 5) {
			path_depth_2_txt = ["댓글상담실","../counsel/secbbs_reple.html"]
		} else if(depth2 === 6) {
			path_depth_2_txt = ["온라인부모상담","../counsel/eum_intro.html"]
			if(depth3 === 1) {
				path_depth_2_tit = "온라인부모교육 이음-e"
				path_depth_2_txt = ["온라인부모교육 이음-e","../html/eum/eum_intro.html"]
			} else if(depth3 === 2) {
				path_depth_2_tit = "그림으로 배우는 자녀맘 터치법"
				path_depth_2_txt = ["그림으로 배우는 자녀맘 터치법","../html/eum/eum_touch.html"]
			}
		} else if(depth2 === 7) {
			path_depth_2_txt = ["사이버상담후기","../counsel/counsel_review.html"]
		}
	} else if(depth1 === 4) { // 상담정보
		path_depth_1_txt = ["상담정보","../gomin/intro.html"]
		if(depth2 === 1) {
			path_depth_2_txt = ["고민해결백과","../gomin/intro.html"]
			if(depth3 === 2) {
				path_depth_2_tit = "고민해결백과 상담자답변"
				path_depth_2_txt = ["고민해결백과 상담자답변","../gomin/reple_main.html"]
			}
		} else if(depth2 === 2) {
			path_depth_2_txt = ["상담툰(TOON)","../gomin/toon.html"]
		} else if(depth2 === 3) {
			path_depth_2_txt = ["청소년마음정보알리미","../gomin/youth_mind.html"]
		} else if(depth2 === 4) {
			path_depth_2_txt = ["청소년뉴스","../gomin/news.html"]
		} else if(depth2 === 5) {
			path_depth_2_txt = ["유용한 사이트","../gomin/site.html"]
		}
	} else if(depth1 === 5) { // 미디어 콘텐츠+
		path_depth_1_txt = ["미디어 콘텐츠+","../hopemessage/list.html"]
		if(depth2 === 1) {
			path_depth_2_txt = ["희망메세지(e-엽서)","../hopemessage/list.html"]
		} else if(depth2 === 2) {
			path_depth_2_txt = ["카드뉴스","../common/cardnews.html"]
		} else if(depth2 === 3) {
			path_depth_2_txt = ["성장통","../common/growingpains.html"]
		} else if(depth2 === 4) {
			path_depth_2_txt = ["미디어 자료실","../common/school_pds.html"]
		} else if(depth2 === 5) {
			path_depth_2_txt = ["고민프리(FREE) 상담소","../common/pod_free.html"]
		}
	} else if(depth1 === 6) { // 소통/참여
		path_depth_1_txt = ["소통/참여","../hopemessage/eventlist.html"]
		if(depth2 === 1) {
			path_depth_2_txt = ["이벤트","../hopemessage/eventlist.html"]
		}
	} else if(depth1 === 7) { // 마이페이지
		path_depth_1_txt = ["마이페이지","../mypage/my_page.html"]
		if(depth2 === 1) {
			path_depth_2_txt = ["개인정보관리","../mypage/my_page.html"]
			if(depth3 === 1) {
				path_depth_2_tit = "회원정보수정"
				path_depth_2_txt = ["회원정보수정","javascript:void(0);"]
			} else if(depth3 === 2) {
				path_depth_2_tit = "인증등록/관리"
				path_depth_2_txt = ["인증등록/관리","javascript:void(0);"]
			}
		} else if(depth2 === 2) {
			path_depth_2_txt = ["고민상담내역","../counsel/result_bbs.html"]
			if(depth3 === 1) {
				path_depth_2_tit = "비밀게시판 상담내역"
				path_depth_2_txt = ["비밀게시판 상담내역","../counsel/result_bbs.html"]
			} else if(depth3 === 2) {
				path_depth_2_tit = "채팅 상담내역"
				path_depth_2_txt = ["채팅 상담내역","../counsel/chat_bbs.html"]
			} else if(depth3 === 3) {
				path_depth_2_tit = "공개게시판 상담내역"
				path_depth_2_txt = ["공개게시판 상담내역","../counsel/result_bbs_02.html"]
			}
		} else if(depth2 === 3) {
			path_depth_2_txt = ["심리검사결과","../mypage/mychat_result.html"]
		} else if(depth2 === 4) {
			path_depth_2_txt = ["내 글 보기","../mypage/supt_chat_list.html"]
		} else if(depth2 === 5) {
			path_depth_2_txt = ["이음-e 부모교육","../mypage/eum_mypage.html"]
		}
	} else if(depth1 === 10) { // 공통,기타
		if(depth2 === 1) {
			path_depth_1_txt = ["사이트맵","../common/sitemap.html"]
		} else if(depth2 === 2) {
			path_depth_1_txt = ["찾아오시는길","../common/contact.html"]
		} else if(depth2 === 3) {
			path_depth_1_txt = ["개인정보처리방침","../common/policy.html"]
		}
	}

	var list = $(".path__list", path);
	if(depth1) {
		list.append($("<li area-path-01><a></a></li>"));
		var path_depth_1_box = $("[area-path-01] a", path);
		title.html(path_depth_1_txt[0]);
		path_depth_1_box.html(path_depth_1_txt[0]).attr("href", path_depth_1_txt[1]);
	}
	if(depth2) {
		if(depth1 !== 10) {
			list.append($("<li area-path-02><a></a></li>"));
			var path_depth_2_box = $("[area-path-02] a", path);

			if(path_depth_2_tit) {
				title.html(path_depth_2_tit);
			} else {
				title.html(path_depth_2_txt[0]);
			}
			path_depth_2_box.html(path_depth_2_txt[0]).attr("href", path_depth_2_txt[1]);
		}
	}
}

function extraTitle(type, title) {
	var box = $("[aria-title-contents]");
	if(type === "bold") {
		box.append($("<strong aria-title-extra>- "+ title +"</strong>"));
	} else {
		box.append($("<span aria-title-extra>: "+ title +"</span>"));
	}
}
