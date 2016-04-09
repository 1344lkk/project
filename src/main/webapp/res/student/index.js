
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


function getComments(noteId){
	$.ajax({
		type:'GET',
		url:'/student/getNoteComments/'+noteId,
		success:function(data){
			jQuery.each(data.result, function(i,item){
				//alert(item.id+","+item.content+","+item.createTime);
			});
		}
	});

}