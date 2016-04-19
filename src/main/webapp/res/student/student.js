//(function () {
//	var app = angular.module('myApp',
//			[])
//      
//       app.controller('StudentCtrl', function ($scope, $http) {
//    	
//    	$scope.likeThisNote= function(id) {
//     	   
//     		$http.get("/student/like/" +id).success(
// 					function(response) {
// 						
// 						
// 					});
//     	}
//    
//    	
//    	
//
//    });
//
function likeThisNote(id){
	$.ajax({
		type : 'POST',
		url: "/student/like/"+id,
		success: function(code){
			$("#count"+id).html(code.result.likecount);
			if(code.result.praise){
				//显示点赞的样式
				$("#agree"+id).css("color","#ff5800");
				$("#count"+id).css("color","#ff5800");
			}else{
				//显示没有点赞的样式
				$("#agree"+id).css("color","#999");
				$("#count"+id).css("color","#999");
			}
		}});
}

function likeThisWork(id){
	$.ajax({
		type : 'POST',
		url: "/student/work/like/"+id,
		success: function(code){
			$("#workLikeCount"+id).html(code.count);
			if(code.flag&&code.code==1){
				//显示点赞的样式
				$("#worklike"+id).css("color","#ff5800");
				$("#workLikeCount"+id).css("color","#ff5800");
			}else{
				//显示没有点赞的样式
				$("#worklike"+id).css("color","#999");
				$("#workLikeCount"+id).css("color","#999");
			}
		}});
}

function topThisNote(id){
	window.location.href="/student/topNote?id="+id;
}
function showCreateUser(id){
	if((userId+"")==currId){
		window.location.href="/student/topNote?id="+id;
	}else{
		window.location.href="/student/"+userId+"#";
	}
}

function likeThisNoteclass(id){
	$.ajax({
		type : 'POST',
		url: "/student/like/"+id,
		success: function(code){
			$("#counts"+id).html(code.result.likecount);
			if((code.result.likeUserId).indexOf(code.result.currId+",") != -1){
				//显示点赞的样式
				$("#like"+id).css("color","#ff5800");
				$("#counts"+id).css("color","#ff5800");
			}else{
				//显示没有点赞的样式
				$("#like"+id).css("color","#999");
				$("#counts"+id).css("color","#999");
			}
		}});
}

function subCommentData(id){
	if($("#comment"+id).val()!=""){
		$.ajax({
			type:'POST',
			url:'/student/insert',
			data:$('#comment_form'+id).serialize(),
			success:function(data){
				if((data.result.comments.commentAvatar==null || data.result.comments.commentAvatar=="")){
					if(data.result.comments.userRole == 4){
						$('.comment-list')
							.append('<li class="comment"><div class="comment-head"><img src="/res/student/images/student_1.jpg"></div><div class="comment-body"><span class="comment-user"><a href="/student/'+data.result.comments.createUserId+'">'+(data.result.comments.createName)+'：</a></span><span class="comment-text">'+data.result.comments.content+'</span></div><div class="comment-foot"><span class="comment-tim e">刚刚</span><div class="comment-tool"></div></div></li>');
						$('#commentCount'+id).html(data.result.commentCount);
						$("#message"+id).attr("class","hide");
					}else{
						$('.comment-list')
							.append('<li class="comment"><div class="comment-head"><img src="/res/student/images/student_1.jpg"></div><div class="comment-body"><span class="comment-user"><a>'+(data.result.comments.createName)+'：</a></span><span class="comment-text">'+data.result.comments.content+'</span></div><div class="comment-foot"><span class="comment-tim e">刚刚</span><div class="comment-tool"></div></div></li>');
						$('#commentCount'+id).html(data.result.commentCount);
						$("#message"+id).attr("class","hide");
					}
				}else{
					$('.comment-list')
						.append('<li class="comment"><div class="comment-head"><img src='+data.result.comments.commentAvatar+'></div><div class="comment-body"><span class="comment-user"><a href="/student/'+data.result.comments.createUserId+'">'+(data.result.comments.createName)+'：</a></span><span class="comment-text">'+data.result.comments.content+'</span></div><div class="comment-foot"><span class="comment-tim e">刚刚</span><div class="comment-tool"></div></div></li>');
					$('#commentCount'+id).html(data.result.commentCount);
					$("#message"+id).attr("class","hide");
				}
			}
		});
		$('#comment'+id).val('');
	}else{
		$("#message"+id).removeClass("hide");
		return;
	}
}

function ReplyData(id,noteId){
	if($("#reply"+id).val()!=""){
		$.ajax({
			type:'POST',
			url:'/student/insert',
			data:$('#reply_form'+id).serialize(),
			success:function(data){
				if((data.result.comments.commentAvatar==null || data.result.comments.commentAvatar=="")){
					if((data.result.replyRole == 4) && (data.result.createRole != 4)){
						$('.comment-list')
							.append('<li class="comment"><div class="comment-head"><img src="/res/student/images/student_1.jpg"></div><div class="comment-body"><span class="comment-user"><a href="/student/'+data.result.comments.replyUserId+'">'+(data.result.comments.replyUserName)+'：</a></span><font color="#ff5800">回复</font><span class="comment-text"><a>'+(data.result.comments.createName)+'：</a></span><span class="comment-text">'+(data.result.comments.content)+'</span></div><div class="comment-foot"><span class="comment-tim e">刚刚</span></div></li>');
						$('#commentCount'+noteId).html(data.result.commentCount);
						$("#mess"+id).attr("class","hide");
					}else if((data.result.replyRole != 4) && (data.result.createRole == 4)){
						$('.comment-list')
							.append('<li class="comment"><div class="comment-head"><img src="/res/student/images/student_1.jpg"></div><div class="comment-body"><span class="comment-user"><a>'+(data.result.comments.replyUserName)+'：</a></span><font color="#ff5800">回复</font><span class="comment-text"><a href="/student/'+data.result.comments.createUserId+'">'+(data.result.comments.createName)+'：</a></span><span class="comment-text">'+(data.result.comments.content)+'</span></div><div class="comment-foot"><span class="comment-tim e">刚刚</span></div></li>');
						$('#commentCount'+noteId).html(data.result.commentCount);
						$("#mess"+id).attr("class","hide");
					}
					else if((data.result.replyRole == 4) && (data.result.createRole == 4)){
						$('.comment-list')
							.append('<li class="comment"><div class="comment-head"><img src="/res/student/images/student_1.jpg"></div><div class="comment-body"><span class="comment-user"><a href="/student/'+data.result.comments.replyUserId+'">'+(data.result.comments.replyUserName)+'：</a></span><font color="#ff5800">回复</font><span class="comment-text"><a href="/student/'+data.result.comments.createUserId+'">'+(data.result.comments.createName)+'：</a></span><span class="comment-text">'+(data.result.comments.content)+'</span></div><div class="comment-foot"><span class="comment-tim e">刚刚</span></div></li>');
						$('#commentCount'+noteId).html(data.result.commentCount);
						$("#mess"+id).attr("class","hide");
					}else{
						$('.comment-list')
							.append('<li class="comment"><div class="comment-head"><img src="/res/student/images/student_1.jpg"></div><div class="comment-body"><span class="comment-user"><a>'+(data.result.comments.replyUserName)+'：</a></span><font color="#ff5800">回复</font><span class="comment-text"><a>'+(data.result.comments.createName)+'：</a></span><span class="comment-text">'+(data.result.comments.content)+'</span></div><div class="comment-foot"><span class="comment-tim e">刚刚</span></div></li>');
						$('#commentCount'+noteId).html(data.result.commentCount);
						$("#mess"+id).attr("class","hide");
					}
				}else{
					if(data.result.createRole != 4){
						$('.comment-list')
							.append('<li class="comment"><div class="comment-head"><img src='+data.result.comments.commentAvatar+'></div><div class="comment-body"><span class="comment-user"><a href="/student/'+data.result.comments.replyUserId+'">'+(data.result.comments.replyUserName)+'：</a></span><font color="#ff5800">回复</font><span class="comment-text"><a>'+(data.result.comments.createName)+'：</a></span><span class="comment-text">'+(data.result.comments.content)+'</span></div><div class="comment-foot"><span class="comment-tim e">刚刚</span></div></li>');
						$('#commentCount'+noteId).html(data.result.commentCount);
						$("#mess"+id).attr("class","hide");
					}else{
						$('.comment-list')
							.append('<li class="comment"><div class="comment-head"><img src='+data.result.comments.commentAvatar+'></div><div class="comment-body"><span class="comment-user"><a href="/student/'+data.result.comments.replyUserId+'">'+(data.result.comments.replyUserName)+'：</a></span><font color="#ff5800">回复</font><span class="comment-text"><a href="/student/'+data.result.comments.createUserId+'">'+(data.result.comments.createName)+'：</a></span><span class="comment-text">'+(data.result.comments.content)+'</span></div><div class="comment-foot"><span class="comment-tim e">刚刚</span></div></li>');
						$('#commentCount'+noteId).html(data.result.commentCount);
						$("#mess"+id).attr("class","hide");
					}
				}
			}
		});
		$('#reply_comm'+id).hide();
		$('#reply'+id).val('');
	}
	else{
		$("#mess"+id).removeClass("hide");
		return;
	}
} 



