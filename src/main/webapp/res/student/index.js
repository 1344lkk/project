
	$(".note-tool-comment").each(function() {
		$(this).on('click', function() {
			var p = $(this).parent().next();
			p.toggle();
		});
	});





	$(".comment_reply").each(function() {
		$(this).on('click', function() {
			var p = $(this).parent().next();
			p.toggle();
		});
	});

