(function() {
	'use strict';

	var app = angular.module('demoApp',
			[ 'ui.tree', 'ngRoute', 'ui.bootstrap','ng.ueditor'])

	.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/knowledgeSet/:id', {

			controller : 'knowledgeSetCtrl',
			templateUrl : '/res/views/console/knowledgeSet.html'
		}).when('/studentknowledgeSet/:id', {

			controller : 'studentKnowledgeSetCtrl',
			templateUrl : '/res/views/student/knowledgeSet.html'
		}).when('/editknowledge/:kid/:pid', {

			controller : 'knowledgeEditCtrl',
			templateUrl : '/res/views/console/editKnowledge.html'
		}).when('/showknowledge/:kid/:pid', {

			controller : 'knowledgeEditCtrl',
			templateUrl : '/res/views/student/showKnowledge.html'
		}).when('/addknowledge/:id', {

			controller : 'knowledgeAddCtrl',
			templateUrl : '/res/views/console/addKnowledge.html'
		}).otherwise({
			redirectTo : '/knowledgeSet'
		});
	} ]);
	app.filter(  
		    'to_trusted', ['$sce', function ($sce) {  
		        return function (text) {  
		            return $sce.trustAsHtml(text);  
		        }  
		    }]  
		) ;
	app.controller('myCtrl', function($scope, $http) {
		$http.get("/library/json").success(function(response) {
			$scope.data = response;
		});
		
		$scope.removeNodes = function(scope) {


			var nodeData = scope.$modelValue;
			if(confirm("您确定删除该知识？")){
				if(nodeData.id==0){

				}else{
					$http.get("/library/deleteCatalog?id=" + nodeData.id).success(
						function(response) {
						});
					scope.remove();}
			}

		};

		$scope.toggle = function(scope) {
			scope.toggle();
		};

		$scope.moveLastToTheBeginning = function() {
			var a = $scope.data.pop();
			$scope.data.splice(0, 0, a);
		};

		$scope.newSubItem = function(scope) {

			var str = prompt("请输入本条目名称");
			if (str) {
				var nodeData = scope.$modelValue;
				var newNode = {
					'title' : str,
					'parentId' : nodeData.id,
					'sort' : 10
				}

				$http.post('/library/addCatalog', newNode).success(
						function(result) {
							$http.get("/library/json").success(function(response) {
								$scope.data = response;
							});
						})
				
			}

		};
		$scope.edit = function(scope) {
			var nodeData = scope.$modelValue;
			
		};
		$scope.collapseAll = function() {
			$scope.$broadcast('collapseAll');
		};

		$scope.expandAll = function() {
			$scope.$broadcast('expandAll');
		};

	});
})();
