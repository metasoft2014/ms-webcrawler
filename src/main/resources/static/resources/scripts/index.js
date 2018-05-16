var mainApp = angular.module('mainApp', ['ngRoute']),
	IndexCtrl = function ($scope, $window, $location, $http, $route, $routeParams) {

		/**
		 * Variables
		 */
		var highlightNav = function () {
			var path = '#!' + $location.path();
			$('#mainSideNav a').each(function () {
				var href = $(this).attr('href');
				if (path.startsWith(href)) {
					$(this).parent('li').addClass('active');
				} else {
					$(this).parent('li').removeClass('active');
				}
			});
		};

		/**
		 * Initialize
		 */
		(function () {

			/* Highlight nav */
			(function () {
				highlightNav();
			})();

			/* Get my info */
			(function () {
				$http
					.get('/api/auth/my-info')
					.then(function (response) {
						var data = response.data,
							userName = data.userName,
							firstName = data.firstName,
							lastName = data.lastName,
							socket = new SockJS('/websocket'),
							stompClient = Stomp.over(socket);
						$scope.firstName = firstName;
						$scope.lastName = lastName;
						stompClient.debug = null;
						stompClient.connect({}, function (frame) {
							stompClient.subscribe('/queue/users/' + userName + '/session-expired', function (response) {
								$window.location.href = '/session-expired';
							});
						});
					});
			})();

		})();

		/**
		 * Events
		 */
		(function () {

			/* window onhashchange */
			$(window).on('hashchange',function () {
				highlightNav();
			});

			/* window onbeforeunload */
			$(window).on('unload, beforeunload',function () {
				$http.post('/api/crawlers/0/stop');
			});

		})();
	};

mainApp.config(function ($routeProvider) {
	$routeProvider
		.when('/dashboard', {'templateUrl': '/dashboard'})
		.when('/my-info', {'templateUrl': '/my-info'})
		.when('/websites', {'templateUrl': '/website/website-list'})
		.when('/websites/new', {'templateUrl': '/website/website-form'})
		.when('/websites/:id/edit', {'templateUrl': '/website/website-form'})
		.when('/crawlers', {'templateUrl': '/crawler/crawler-list'})
		.when('/crawlers/new', {'templateUrl': '/crawler/crawler-form'})
		.when('/crawlers/:id/edit', {'templateUrl': '/crawler/crawler-form'})
		.when('/schedules', {'templateUrl': '/schedule/schedule-list'})
		.when('/schedules/new', {'templateUrl': '/schedule/schedule-form'})
		.when('/schedules/:id/edit', {'templateUrl': '/schedule/schedule-form'})
		.when('/analyzers', {'templateUrl': '/analyzer/analyzer-list'})
		.when('/analyzers/new', {'templateUrl': '/analyzer/analyzer-form'})
		.when('/analyzers/:id/edit', {'templateUrl': '/analyzer/analyzer-form'})
		.otherwise({'redirectTo': '/dashboard'});
})
.controller('IndexCtrl', IndexCtrl)
.controller('DashboardCtrl', DashboardCtrl)
.controller('MyInfoCtrl', MyInfoCtrl)
.controller('WebsiteListCtrl', WebsiteListCtrl)
.controller('WebsiteFormCtrl', WebsiteFormCtrl)
.controller('CrawlerListCtrl', CrawlerListCtrl)
.controller('CrawlerFormCtrl', CrawlerFormCtrl)
.controller('ScheduleListCtrl', ScheduleListCtrl)
.controller('ScheduleFormCtrl', ScheduleFormCtrl)
.controller('AnalyzerListCtrl', AnalyzerListCtrl)
.controller('AnalyzerFormCtrl', AnalyzerFormCtrl);