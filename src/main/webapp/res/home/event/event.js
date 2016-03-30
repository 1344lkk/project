(function () {
	var app = angular.module('myApp',
			[ ])
       
	
       app.controller('EventCtrl', function ($scope,$http) {
    	   $http.get("/farm/events/list").success(
					function(response) {
						
						var length = response.length;
						if(length >= 1){
							$scope.classId = response[0].classId;
							$scope.showStudents(response[0].classId);
						}
						$scope.events = response;
						
					});
    	   
    	   
    	   $scope.showStudents = function(id) {
    		   
    	    	$scope.classId = id;
    	    	$http.get("/console/student/students/"+id).success(
    					function(response) {
    						
    						
    						$scope.students = response;
    						
    					});
    	    	
    	    	$http.get("/farm/events/excellentWork/"+id).success(
    					function(response) {
    						
    						
    						$scope.works = response;
    						
    					});
    	    	
    	    	$http.get("/farm/events/tasks/"+id).success(
    					function(response) {
    						
    						
    						$scope.tasks = response;
    						
    					});
    	 

    	    };
    	   
       })
       
      
       
    	   

   
	
})();