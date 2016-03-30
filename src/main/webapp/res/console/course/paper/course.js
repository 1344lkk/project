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
    	   $scope.order=['A','B','C','D','E','F'] ;
        $scope.tabs = [
                       { title:'任务详情', template:"/res/console/course/paper/task.html"},
                       { title:'试题', template:"/res/console/course/paper/question.html" }
                       
                   ];
        
      
    	$scope.choseStage= function($event) {
    	   var courseId = $scope.courseId;
    	   $rootScope.questions = null;
    	   $rootScope.task = "";
    	   var stageId  =  $event.target.getAttribute('data-stage');
    	   $scope.stageId = stageId;
    		$http.get("/console/task/list/" + stageId+"/"+courseId).success(
					function(response) {
						$rootScope.tasks = response;
						
					});
    		$http.get("/console/lesson/paper/getStudent/"+$scope.lessonId+"/-1").success(
					function(response) {
						$rootScope.students = response.result;
						
					});
    	}
    	$scope.subGrade= function() {
     	  
     		$http.post("/console/lesson/paper/grade/"+$rootScope.studentId+"/"+$scope.taskId+"/"+$scope.score).success(
 					function(response) {
 						$scope.score = "";
 						$http.get("/student/getStudentWork/"+$rootScope.studentId+"/"+$scope.taskId ).success(
 								function(response) {
 									$scope.work = response;
 									
 								});
 						
 					});
     	}
    	$scope.choseStudent= function(studentId) {
    		 
    		 $rootScope.studentId = studentId;

         	$http.get("/console/lesson/paper/paperScore/"+$rootScope.studentId+"/"+$scope.taskId ).success(
 					function(response) {
 						$scope.choseScore = response.result;
 						
 					});
         	$http.get("/student/getStudentWork/"+$rootScope.studentId+"/"+$scope.taskId ).success(
					function(response) {
						$scope.work = response;
						
					});
         	$http.get("/console/lesson/paper/getQuestion/"+$rootScope.studentId+"/"+$scope.taskId ).success(
					function(response) {
						$rootScope.questions = response;
						
					});
    		
     	}
    	
    
    	
        $scope.showQuestion = function(id) {
            //alert($rootScope.studentId);
        	$scope.taskId = id;
        	$http.get("/student/getStudentWork/"+$rootScope.studentId+"/"+id).success(
					function(response) {
						$scope.work = response;
						
					});
        	$http.get("/console/lesson/paper/paperScore/"+$rootScope.studentId+"/"+id).success(
					function(response) {
						$scope.choseScore = response.result;
						
					});
        	
        	$http.get("/console/lesson/paper/getQuestion/"+$rootScope.studentId+"/"+id).success(
					function(response) {
						$rootScope.questions = response;
						
					});
        	$http.get("/console/task/getTask/"+id).success(
    				function(response) {
    					$rootScope.task = response;
    					
    				});
        	$http.get("/console/lesson/paper/getStudent/"+$scope.lessonId+"/"+id).success(
					function(response) {
						$rootScope.students = response.result;
						
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