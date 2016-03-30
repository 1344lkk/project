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
                       { title:'任务详情', template:"/res/console/course/task.html"},
                       { title:'试题', template:"/res/console/course/question.html" }
                       
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
    	$scope.removeTask= function(id) {
     	   
     		$http.get("/console/task/deleteTask/" +id).success(
 					function(response) {
 						$http.get("/console/task/list/" + $scope.stageId+"/"+$scope.courseId).success(
 								function(response) {
 									$scope.tasks = response;
 									
 								});
 						
 					});
     	}
    
    	
    	$scope.newTask = function() {
            var self = this;
            var modalInstance = $uibModal.open({
                animation: true,
                resolve: { },
                templateUrl: '/res/console/course/add_task.html',
                controller: 'modalAddTaskCtrl',
                resolve: {
                    stageId: function () {
                      return $scope.stageId;
                    },
                    courseId: function () {
                        return $scope.courseId;
                      }
                  }
            });

            modalInstance.result.then(
               
            );
        };
        $scope.editTask = function(task) {

        	
            var self = this;
            var modalInstance = $uibModal.open({
                animation: true,
                resolve: { },
                templateUrl: '/res/console/course/edit_task.html',
                controller: 'modalEditTaskCtrl',
                resolve: {
                    task: function () {
                      return task;
                    }
                  }
            });

            modalInstance.result.then(
               
            );
        };
        $scope.editQueston = function(question) {

            var self = this;
            var modalInstance = $uibModal.open({
                animation: true,
                resolve: { },
                templateUrl: '/res/console/course/edit_question.html',
                controller: 'modalEditQuesCtrl',
                resolve: {
                    question: function () {
                      return question;
                    }
                  }
            });

            modalInstance.result.then(
               
            );
        };
        $scope.showQuestion = function(id) {
        	$scope.taskId = id;
        	$http.get("/console/question/getQuestion?taskId="+id).success(
					function(response) {
						$rootScope.questions = response;
						
					});
        	$http.get("/console/task/getTask/"+id).success(
    				function(response) {
    					$rootScope.task = response;
    					
    				});

        };
        $scope.removeQuestion= function(id) {
      	   
     		$http.get("/console/question/deleteQuestion/" +id).success(
 					function(response) {
 						$http.get("/console/question/getQuestion?taskId="+$scope.taskId).success(
 								function(response) {
 									$rootScope.questions = response;
 									
 								});
 						
 					});
     	}
        $scope.newQuestion = function() {
            var self = this;
           
            var modalInstance = $uibModal.open({
                animation: true,
                
                resolve: { },
                templateUrl: '/res/console/course/add_question.html',
                controller: 'modalAddQuestionCtrl',
                resolve: {
                    taskId: function () {
                      return $scope.taskId;
                    }
                  }
            });

            modalInstance.result.then(
               
            );
        };
    	

    });

    app.controller('QuestionCtrl', function ($scope,$rootScope, $http, $uibModal) {
      
    	$scope.ok =function(){
    		  $http.post('/console/task/editTask', $scope.task).success(function(response){
    			  $http.get("/console/task/list/" + $scope.task.stageId+"/"+$scope.task.courseId).success(
      					function(response) {
      						$rootScope.tasks = response;
      						
      					});
              });
             
    		
    	}
    	
    });
	
	app.controller('modalAddTaskCtrl', function ($scope, $http, $rootScope,$uibModalInstance,stageId,courseId) {
	   
	    $scope.courseTask = {
	    		'stageId':stageId,
	    		'courseId':courseId
	    }
        $scope.ok = function () {
           
            $http.post('/console/task/addTask', $scope.courseTask).success(function(response){
            	$http.get("/console/task/list/" + stageId+"/"+courseId).success(
    					function(response) {
    						$rootScope.tasks = response;
    						
    					});
            });
           
            $uibModalInstance.close();
        };

        $scope.close = function () {
            $uibModalInstance.dismiss('取消');
        };

    });
	app.controller('modalEditTaskCtrl', function ($scope, $http, $rootScope,$uibModalInstance,task) {
		   
	    $scope.courseTask = task;
	   
        $scope.ok = function () {
           
            $http.post('/console/task/editTask', $scope.courseTask).success(function(response){
            	$http.get("/console/task/list/" + $scope.courseTask.stageId+"/"+$scope.courseTask.courseId).success(
    					function(response) {
    						$rootScope.tasks = response;
    						
    					});
            });
           
            $uibModalInstance.close();
        };

        $scope.close = function () {
            $uibModalInstance.dismiss('取消');
        };

    });
	  app.controller('modalAddQuestionCtrl', function ($scope, $http, $rootScope,$uibModalInstance,taskId) {
		  $scope.optionItems=[];
		  $scope.order=['A','B','C','D','E','F'] ;
		 
	    $scope.question = {
	    		'taskId':taskId,
	    		'options':[]
	    		
	    }
	
	    $scope.addOption = function (){

	    	 $scope.optionItems.push({name:'',correct:false});
	    	
	    }
	    Array.prototype.remove=function(obj){ 
	    	for(var i =0;i <this.length;i++){ 
	    	var temp = this[i]; 
	    	if(!isNaN(obj)){ 
	    	temp=i; 
	    	} 
	    	if(temp == obj){ 
	    	for(var j = i;j <this.length;j++){ 
	    	this[j]=this[j+1]; 
	    	} 
	    	this.length = this.length-1; 
	    	} 
	    	} 
	    	} 
	    $scope.deleteOption = function (index){

	    	 $scope.optionItems.remove(index);
	    	
	    }
	    $scope.question.options= $scope.optionItems;
        $scope.ok = function () {
	    	console.info($scope.optionItems);
            $http.post('/console/question/addQuestion', $scope.question).success(function(response){
            	$http.get("/console/question/getQuestion?taskId="+taskId).success(
							function(response) {
								$rootScope.questions = response;
								
							});
            });
            
   
           
            $uibModalInstance.close();
        };

        $scope.close = function () {
            $uibModalInstance.dismiss('取消');
        };

    });
		app.controller('modalEditQuesCtrl', function ($scope, $http, $rootScope,$uibModalInstance,question) {
			 $scope.optionItems=question.options; 
			 $scope.order=['A','B','C','D','E','F'] ;
		    $scope.question = question;
		    $scope.addOption = function (){

		    	 $scope.optionItems.push({name:'',correct:false});
		    	
		    }
		    Array.prototype.remove=function(obj){ 
		    	for(var i =0;i <this.length;i++){ 
		    	var temp = this[i]; 
		    	if(!isNaN(obj)){ 
		    	temp=i; 
		    	} 
		    	if(temp == obj){ 
		    	for(var j = i;j <this.length;j++){ 
		    	this[j]=this[j+1]; 
		    	} 
		    	this.length = this.length-1; 
		    	} 
		    	} 
		    	} 
		    $scope.deleteOption = function (index){

		    	 $scope.optionItems.remove(index);
		    	
		    }
		    $scope.question.options= $scope.optionItems;
	        $scope.ok = function () {
	           
	            $http.post('/console/question/editQuestion', $scope.question).success(function(response){
	            	$http.get("/console/question/getQuestion?taskId="+$scope.question.taskId).success(
							function(response) {
								$rootScope.questions = response;
								
							});
	            });
	           
	            $uibModalInstance.close();
	        };

	        $scope.close = function () {
	            $uibModalInstance.dismiss('取消');
	        };

	    });
})();