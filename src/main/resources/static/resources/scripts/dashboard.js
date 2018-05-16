var DashboardCtrl = function ($scope, $window, $location, $http, $route, $routeParams) {

	/**
	 * Variables
	 */
	var resetCrawlerStatus = function () {
			$scope.totalDataCount = 0;
			$scope.processedDataCount = 0;
			$scope.dataKey = '';
			$scope.title = '';
			$scope.content = '';
			$scope.writer = '';
			$scope.writtenDate = '';
			$scope.crawlerStatusCode = 'STOPPED';
			$('#crawlerProgress').css('width', '0');
			$('#crawlerStatus').modal('hide');
		};

	/**
	 * Initialize
	 */
	(function () {

		/* Initialize crawler status modal */
		(function () {
			$('#crawlerStatus').modal({
				'show': false,
				'backdrop': 'static',
				'keyboard': false
			});
		})();

		/* Clear crawler status modal */
		(function () {
			resetCrawlerStatus();
		})();

		/* Get website list */
		(function () {
			$http
				.get('/api/websites')
				.then(function (response) {
					var data = response.data,
						content = data.content;
					$scope.websites = content;
				});
		})();

		/* Get crawler status */
		(function () {
			$http
				.get('/api/auth/my-info')
				.then(function (response) {
					var data = response.data,
						userName = data.userName,
						socket = new SockJS('/websocket'),
						stompClient = Stomp.over(socket);
					stompClient.debug = null;
					stompClient.connect({}, function (frame) {
						stompClient.subscribe('/queue/crawler-status/' + userName, function (response) {
							var data = JSON.parse(response.body),
								writtenDate = moment(data.writtenDate).format('YYYY-MM-DD HH:mm:ss'),
								width = 0;
							$scope.totalDataCount = data.totalDataCount;
							$scope.processedDataCount = data.processedDataCount;
							$scope.dataKey = data.dataKey;
							$scope.title = data.title;
							$scope.content = data.content;
							$scope.writer = data.writer;
							$scope.writtenDate = writtenDate;
							$scope.crawlerStatusCode = data.crawlerStatusCode;
							width = data.processedDataCount / data.totalDataCount * 100;
							$('#crawlerProgress').css('width', width + '%');
							$scope.$apply();
						});
					});
				});
		})();

	})();

	/**
	 * Events
	 */
	(function () {

		/* select[name="websiteId"] onchange */
		$('select[name="websiteId"]').on('change', function () {
			var websiteId = $(this).val();
			if (websiteId != undefined && websiteId != '') {
				$http({
					'url': '/api/crawlers',
					'method': 'get',
					'params': {
						'websiteId': websiteId
					}
				}).then(function (response) {
					var data = response.data,
						content = data.content;
					$scope.crawlers = content;
				});
			} else {
				$scope.crawlers = undefined;
			}

		});

		/* button[name="startCrawler"] onclick */
		$('button[name="startCrawler"]').on('click', function () {
			var crawlerId = $scope.crawlerId,
				keyword = $scope.keyword,
				dataSize = $scope.dataSize,
				crawlerStatusCode = $scope.crawlerStatusCode;
			if (crawlerStatusCode == 'STOPPED') {
				if ((crawlerId != undefined && crawlerId != '')
						&& (keyword != undefined && keyword != '')
						&& (dataSize != undefined && dataSize != '')) {
					resetCrawlerStatus();
					$('#crawlerStatus').modal('show');
					$http({
						'url': '/api/crawlers/' + crawlerId + '/start',
						'method': 'post',
						'params': {
							'crawlerId': crawlerId,
							'keyword': keyword,
							'dataSize': dataSize
						}
					}).then(function (response) {
						bsAlert('Crawling complete!', function () {
							resetCrawlerStatus();
						});
					});
				} else {
					bsAlert('Error!');
				}
			} else if (crawlerStatusCode == 'RUNNING') {
				bsAlert('Crawler is already running!');
			}
		});

		/* button[name="stopCrawler"] onclick */
		$('button[name="stopCrawler"]').on('click', function () {
			var crawlerId = $scope.crawlerId,
				crawlerStatusCode = $scope.crawlerStatusCode;
			if (crawlerStatusCode == 'RUNNING') {
				$http.post('/api/crawlers/' + crawlerId + '/stop');
			} else if (crawlerStatusCode == 'STOPPED') {
				bsAlert('Crawler is already stopped!');
			}
		});

	})();

};