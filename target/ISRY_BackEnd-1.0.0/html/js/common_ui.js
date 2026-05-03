
$(document).on('click', function(e){
  e.stopPropagation();
}).on('click', '.js-btn-close', function(e){
  layerPopup.close(e.target);
});


$.fn.fnRdo = function() {
  var _this = $(this);
  _this.off('click').on('click', function(){    

    var $this = $(this);    
    if($this.is(":checked")) {
      $this.parent('.rdo-custom').attr("aria-checked", true).siblings('.rdo-custom').attr('aria-checked', false);
    }

  });
}

$.fn.fnTab = function() {
  var _this = $(this);

  _this.off('click').on('click', function(e) {
    console.log(_this)
    
    var $this = $(this);
    $this.addClass('active').attr('aria-selected', true).parent('li').siblings('li').find('.tab_btn').removeClass('active').attr('aria-selected', false);

    var tabId = $this.attr('aria-controls');
    $('#'+tabId).addClass('active').siblings('.tab-panel').removeClass('active');
  });
}

var layerPopup = {
  toggleScroll: function(){
    var bodyBack = document.querySelector('body');
    bodyBack.classList.toggle('pop-active');

    popArea = document.querySelector('.js-pop-area');
  
    var wrapper = document.querySelector('.teen-wrapper');
    if (bodyBack.classList.contains('pop-active')) {
      wrapper.setAttribute('aria-hidden', true);
      popArea.removeAttribute('aria-hidden');
      popArea.setAttribute('aria-live', 'assertive');
    } else {
      wrapper.removeAttribute('aria-hidden');
      popArea.setAttribute('aria-hidden', true);
      popArea.removeAttribute('aria-live');
    }
  },

  open:function(cls, obj) {
    var $popup = $('.'+cls);
    $popup.addClass('active');
    $popup.focus();

    this.toggleScroll(); 
  },
  close:function(obj) {
    var $popup = $(obj).closest('.popUp');
    $popup.removeClass('active');
    
    this.toggleScroll();
  }
}

$('.tab-list .tab_btn').fnTab();//탭메뉴 접근성 관련
$('.rdo-custom input').fnRdo();//라디오버튼 접근성 관련