(function () {
	var app = angular.module('myApp',
			[])
  
	
   

    app.controller('indexCtrl', function ($scope,$rootScope, $http) {
    	
    	angular.element(window).bind('load', function() { 
    		
    		$http.get('/student/photos/'+$scope.studentId).success(function(response){
  			  $scope.photos = response.result.images;
  			  $scope.likeCount = response.result.likeCount;
            });
           
        });  
    	     
    });
 	
})();

	
	
	
	