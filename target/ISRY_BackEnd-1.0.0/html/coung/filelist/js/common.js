$(function(){
	layout();
});

function layout() {
	var btn = $(".tab--btn button");
	var cnt = $(".tab--content");
	var guide = $(".tab--guide");
	var on = $(".tab--btn .on");
	var i = on.index();

	cnt.load("tab-content-0"+i+".html");
	guide.load("tab-guide-0"+i+".html");

	btn.on("click", function(){
		var idx = $(this).index();
		btn.eq(idx).addClass("on").siblings().removeClass("on");
		if($(this).parents('.tab--btn').is("[aria-filelist]")) {
			cnt.load("tab-content-0"+idx+".html");
			console.log(`1 :: tab-content-0${idx}.html`);
		} else {
			guide.load("tab-guide-0"+idx+".html");
			console.log(`2 :: tab-guide-0${idx}.html`);
		}
	});
}

function target() {
	$("table").each(function(){
		$(this).find("a").attr("target","_blank");
	});
}

function state() {
	$(".step-01").closest("tr").addClass("step-01");	//퍼블 진행중
	$(".step-02").closest("tr").addClass("step-02");	//퍼블완료 컨펌전
	$(".complete").closest("tr").addClass("complete");	//퍼블완료 컨펌완료
	$(".new").closest("tr").addClass("new");		//신규
	$(".modify").closest("tr").addClass("modify");	//수정중
	$(".update").closest("tr").addClass("update");	//업뎃
}

function progress() {
	$(".progress").each(function(){
		var content = $(this).closest(".tab--content");
		var total = $(".filelist td.state", content).length;
		var comp = $(".filelist td.complete", content).length;
		var mod = $(".filelist td.modify", content).length;
		$(".total",$(this)).text(total);
		$(".complete",$(this)).text(comp);
		$(".modify",$(this)).text(mod);
	});
}

function reload() {
	target();
	state();
	progress();
}
