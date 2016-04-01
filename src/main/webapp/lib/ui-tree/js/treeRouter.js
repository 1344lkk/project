(function () {
  'use strict';
 
  angular.module('demoApp')
    .controller('knowledgeSetCtrl', ['$rootScope', '$scope', '$location', '$routeParams', '$http',function ($rootScope,$scope,$location,$routeParams,$http) {
    	$scope.id = $routeParams.id;
    	
     
      $http.get("/library/knowledgeJson?id="+$scope.id).success(function(response) {
    	  $scope.knowlagedatas = response;
    	});
      $scope.deleteKnowladge= function (code) {

		  if(confirm("您确定删除该知识？")){
			  $http.get("/library/delete?id="+code).success(function(response) {
				  $http.get("/library/knowledgeJson?id="+$scope.id).success(function(response) {
					  $scope.knowlagedatas = response;
					});
				});
		  }
        };
        $scope.showDialog=function(){
        	
        	$("#addKnowledgeModal").modal('show');
        	
        }
       
    }]);
  angular.module('demoApp')
  .controller('studentKnowledgeSetCtrl', ['$rootScope', '$scope', '$location', '$routeParams', '$http',function ($rootScope,$scope,$location,$routeParams,$http) {
  	$scope.id = $routeParams.id;
  
    
    $http.get("/library/knowledgeJson?id="+$scope.id).success(function(response) {
  	  $scope.cataDatas = response;
  	});
    
     
  }]);
  angular.module('demoApp')
  .controller('knowledgeEditCtrl', ['$rootScope', '$scope', '$location', '$routeParams', '$http',function ($rootScope,$scope,$location,$routeParams,$http) {
  	$scope.kid = $routeParams.kid;
  	$scope.pid = $routeParams.pid;
  	$http.get("/library/knowledge?id="+$scope.kid).success(function(response) {
  		$scope.title =response.name;
  	  	$scope.sort =response.sort;
  	    $scope.content = response.note;
  	});
  	
  	
  	 $scope.updateKnowledge=function(){
  	 var newKnowledge = {
 			    'id':$scope.kid,
				'name' : $scope.title,
				'note' : $scope.content ,
				'sort' : $scope.sort,
			};
 	$http.post('/library/updateKnowledge',newKnowledge).success(
				function(result) {

					
				})
				$location.url("/knowledgeSet/"+$scope.pid);
  	 }
  	 $scope.goback=function(){
  	  	
  					$location.url("/studentknowledgeSet/"+$scope.pid);
  	  	 }
  	  
     
  }]);
  angular.module('demoApp')
  .controller('knowledgeAddCtrl', ['$rootScope', '$scope','$location', '$routeParams', '$http',function ($rootScope,$scope,$location,$routeParams,$http) {
		$scope.pid = $routeParams.id;
  	
  	 $scope.addKnowledge=function(){
     	
     	var newKnowledge = {
     			'categoryId':$scope.pid,
					'name' : $scope.title,
					'note' : $scope.content,
					'sort' : $scope.list,
				};
     
     	$http.post('/library/addKnowledge',newKnowledge).success(
					function(result) {
						 $http.get("/library/knowledgeJson?id="+$scope.pid).success(function(response) {
			    	    	  $scope.knowlagedatas = response;
			    	    	  
			    	    	});
						
					})
					$location.url("/knowledgeSet/"+$scope.pid);
					
     }
  }]);

}());
