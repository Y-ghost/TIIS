(function($) {
	// Button按钮的三种样式替换，如果isState参数为True则记录按下状态
	$.fn.btnEffect = function(normal, mouseover, isState) {
		var lastButton=null;
		this.each(function() {
			$(this).bind({
				mouseover : function() {
					if (this != lastButton || !isState) {
						$(this).removeClass().addClass(mouseover);
					}
				},
				mouseout : function() {
					if (this != lastButton || !isState) {
						$(this).removeClass().addClass(normal);
					}
				}
			});
		});
	};
})(jQuery);