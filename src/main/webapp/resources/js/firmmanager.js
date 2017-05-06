jQuery(function($){
	$('.footable').footable();
});

jQuery(function(){
	jQuery('.js-textarea-minr3').autoResize({minRows: 3});
	jQuery('.js-textarea-minr1').autoResize({minRows: 1});
	jQuery('.js-textarea-minr10').autoResize({minRows: 10});
	jQuery('.js-textarea-minr1-maxr10').autoResize({minRows: 1, maxRows: 10});
});


$("#comment-textarea").keypress(function (e) {
	if(e.which == 13 && !e.shiftKey) {
		$(this).closest("form").submit();
		e.preventDefault();
		return false;
	}
});
