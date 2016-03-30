(function () {
	var app = angular.module('myApp',
			[ 'ui.bootstrap','ng.ueditor'])
        app.filter(  
		    'to_trusted', ['$sce', function ($sce) {  
		        return function (text) {  
		            return $sce.trustAsHtml(text);  
		        }  
		    }]  
		) ;
	
       app.controller('CourseCtrl', function ($scope,$rootScope, $http, $uibModal) {
    	   $scope.formData = {};
    	   
    	   $scope.order=['A','B','C','D','E','F'] ;
           $scope.tabs = [
                       { title:'任务详情', template:"/res/student/learning/task.html"},
                       { title:'试题', template:"/res/student/learning/question.html" }
                       
                   ];
        
      
    	   $scope.choseStage= function($event) {
    		  
    	   var courseId = $scope.courseId;
    	   $rootScope.questions = null;
    	   $rootScope.task = "";
    	   var stageId  =  $event.target.getAttribute('data-stage');
    	   $scope.stageId = stageId;
    		$http.get("/console/task/list/" + stageId+"/"+courseId).success(
					function(response) {
						var length = response.length;
						if(length >= 1){
							$scope.taskId = response[0].id;
							$scope.showQuestion(response[0].id);
						}
						
						$rootScope.tasks = response;
						
					});
    	}
    	
    	  $scope.subAnswer = function () {
    		 
              $http.post('/student/answerQuestion?taskId='+$scope.taskId, $scope.formData).success(function(response){
              	
            	   $scope.score =  response.result;
            	    if($scope.stageId == 1){
            	    	$scope.formData = {};
            	    	$scope.showQuestion($scope.taskId);
            	    	
            	    }
            	   
              });
             
              
          };
    	

        $scope.showQuestion = function(id) {
        	$scope.taskId = id;
        	$http.get("/console/question/getQuestionRandom?taskId="+id).success(
					function(response) {
						$rootScope.questions = response;
						var length = response.length;
						if(length < 1){
						$scope.tabs = [
				                       { title:'任务详情', template:"/res/student/learning/task.html"}
				                       
				                   ];}
						else{
							  $scope.tabs = [
						                       { title:'任务详情', template:"/res/student/learning/task.html"},
						                       { title:'试题', template:"/res/student/learning/question.html" }
						                       
						                   ];
							
							
						}
						
					});
        	$http.get("/console/task/getTask/"+id).success(
    				function(response) {
    					$rootScope.task = response;
    					
    				});
        	
        	$http.get("/console/lesson/paper/paperScore/"+$scope.studentId+"/"+$scope.taskId ).success(
 					function(response) {
 						$scope.score = response.result;
 						
 					});

        };
       
       

    });

    app.controller('QuestionCtrl', function ($scope,$rootScope, $http, $uibModal) {
    	
    	$scope.ok =function(){
    		  $http.post('/console/task/editTask', $scope.task).success(function(response){
              	
              });
             
    		
    	}
    	
    });
	
	
})();