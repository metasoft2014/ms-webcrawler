var CrawlerFormCtrl = function ($scope, $window, $location, $http, $route, $routeParams) {

	/**
	 * Initialize
	 */
	(function () {

		/* Get website list */
		(function () {
			$http({
				'url': '/api/websites',
				'method': 'get',
				'params': {
					'size': Number.MAX_SAFE_INTEGER
				}
			}).then(function (response) {
				var data = response.data;
				$scope.websites = data.content;
			});
		})();

		/* Get crawler info */
		(function () {
			if ($routeParams.id != undefined) {
				$scope.hasId = true;
				$http
					.get('/api/crawlers/' + $routeParams.id)
					.then(function (response) {
						var data = response.data,
							createdDate = moment(data.createdDate).format('YYYY-MM-DD HH:mm:ss'),
							updatedDate = moment(data.updatedDate).format('YYYY-MM-DD HH:mm:ss');
						$scope.crawlerId = data.crawlerId;
						$scope.crawlerName = data.crawlerName;
						$scope.targetUrl = data.targetUrl;
						$scope.createdDate = createdDate;
						$scope.updatedDate = updatedDate;
						$scope.websiteId = String(data.websiteId);
					});
			}
		})();

		/* Get crawler field list */
		(function () {
			if ($routeParams.id != undefined) {
				$http({
					'url': '/api/crawlers/' + $routeParams.id + '/fields',
					'method': 'get',
					'params': {
						'size': Number.MAX_SAFE_INTEGER,
						'sort': 'crawlerFieldOrder,asc'
					}
				}).then(function (response) {
					var data = response.data,
						content = data.content,
						totalElements = data.totalElements,
						hasElements = totalElements > 0 ? true : false,
						i = 0;
					for (i = 0; i < content.length; i++) {
						content[i].createdDate = moment(content[i].createdDate).format('YYYY-MM-DD HH:mm:ss');
						content[i].updatedDate = moment(content[i].updatedDate).format('YYYY-MM-DD HH:mm:ss');
					}
					$scope.crawlerFields = content;
					$scope.crawlerFieldTotalElements = totalElements;
					$scope.hasCrawlerFieldElements = hasElements;
				});
			}
		})();

	})();

	/**
	 * Events
	 */
	(function () {

		/* input[name="crawlerFieldIdAll"] onclick */
		$('input[name="crawlerFieldIdAll"]').on('click', function () {
			if ($(this).prop('checked')) {
				$('input[name="crawlerFieldId"]').prop('checked', true);
			} else {
				$('input[name="crawlerFieldId"]').prop('checked', false);
			}
		});

		/* button[name="save"] onclick */
		$('button[name="save"]').on('click', function () {
			var params = {
					'crawlerId': $scope.crawlerId,
					'crawlerName': $scope.crawlerName,
					'targetUrl': $scope.targetUrl,
					'websiteId': $scope.websiteId
				},
				successCallback = function (response) {
					var data = response.data;
					bsAlert('Saved!', function () {
						$window.location.href = '/#!/crawlers/' + data.crawlerId + '/edit';
						$route.reload();
					});
				},
				errorCallback = function (response) {
					bsAlert('Error!');
				};
			if ($scope.crawlerId == undefined) {
				$http({
					'url': '/api/crawlers',
					'method': 'post',
					'params': params
				})
				.then(successCallback, errorCallback);
			} else {
				$http({
					'url': '/api/crawlers/' + $scope.crawlerId,
					'method': 'put',
					'params': params
				})
				.then(successCallback, errorCallback);
			}
		});

		/* button[name="delete"] onclick */
		$('button[name="delete"]').on('click', function () {
			var successCallback = function (response) {
					bsAlert('Deleted!', function () {
						$window.location.href = '/#!/crawlers';
					});
				},
				errorCallback = function (response) {
					bsAlert('Error!');
				};
			bsConfirm('Are you sure you want to delete?', function () {
				$http({
					'url': '/api/crawlers/' + $scope.crawlerId,
					'method': 'delete'
				})
				.then(successCallback, errorCallback);
			});
		});

		/* button[name="crawlerFieldNew"] onclick */
		$('button[name="crawlerFieldNew"]').on('click', function () {
			$scope.crawlerFieldId = undefined;
			$scope.crawlerFieldName = undefined;
			$scope.crawlerFieldOrder = undefined;
			$scope.crawlerFieldType = undefined;
			$scope.useStatus = undefined;
			$scope.selector = undefined;
			$scope.attributeName = undefined;
			$scope.script = undefined;
			$scope.crawlerFieldCreatedDate = undefined;
			$scope.crawlerFieldUpdatedDate = undefined;
			$scope.hasCrawlerFieldId = false;
			$scope.$apply();
		});

		/* a[name="crawlerFieldEdit"] onclick */
		$(document).on('click', 'a[name="crawlerFieldEdit"]', function () {
			var crawlerFieldId = $(this).attr('data-crawler-field-id');
			$http
				.get('/api/crawlers/' + $scope.crawlerId + '/fields/' + crawlerFieldId)
				.then(function (response) {
					var data = response.data,
						createdDate = moment(data.createdDate).format('YYYY-MM-DD HH:mm:ss'),
						updatedDate = moment(data.updatedDate).format('YYYY-MM-DD HH:mm:ss');
					$scope.crawlerFieldId = data.crawlerFieldId;
					$scope.crawlerFieldName = data.crawlerFieldName;
					$scope.crawlerFieldOrder = data.crawlerFieldOrder;
					$scope.crawlerFieldType = data.crawlerFieldType;
					$scope.useStatus = data.useStatus;
					$scope.selector = data.selector;
					$scope.attributeName = data.attributeName;
					$scope.script = data.script;
					$scope.crawlerFieldCreatedDate = createdDate;
					$scope.crawlerFieldUpdatedDate = updatedDate;
					$scope.hasCrawlerFieldId = true;
				});
		});

		/* button[name="crawlerFieldSave"] onclick */
		$('button[name="crawlerFieldSave"]').on('click', function () {
			var params = {
					'crawlerFieldName': $scope.crawlerFieldName,
					'crawlerFieldOrder': $scope.crawlerFieldOrder,
					'crawlerFieldType': $scope.crawlerFieldType,
					'useStatus': $scope.useStatus,
					'selector': $scope.selector,
					'attributeName': $scope.attributeName,
					'script': $scope.script,
					'crawlerId': $scope.crawlerId
				},
				successCallback = function (response) {
					bsAlert('Saved!', function () {
						$window.location.reload();
					});
				},
				errorCallback = function (response) {
					bsAlert('Error!');
				};
			if ($scope.crawlerFieldId == undefined) {
				$http({
					'url': '/api/crawlers/' + $scope.crawlerId + '/fields',
					'method': 'post',
					'params': params
				})
				.then(successCallback, errorCallback);
			} else {
				$http({
					'url': '/api/crawlers/' + $scope.crawlerId + '/fields/' + $scope.crawlerFieldId,
					'method': 'put',
					'params': params
				})
				.then(successCallback, errorCallback);
			}
		});

		/* button[name="crawlerFieldDelete"] onclick */
		$('button[name="crawlerFieldDelete"]').on('click', function () {
			var successCallback = function (response) {
				bsAlert('Deleted!', function () {
					$window.location.reload();
				});
			},
			errorCallback = function (response) {
				bsAlert('Error!');
			};
			bsConfirm('Are you sure you want to delete?', function () {
				$http({
					'url': '/api/crawlers/' + $scope.crawlerId + '/fields/' + $scope.crawlerFieldId,
					'method': 'delete'
				})
				.then(successCallback, errorCallback);
			});
		});

		/* button[name="crawlerFieldDeleteSelected"] onclick */
		$('button[name="crawlerFieldDeleteSelected"]').on('click', function () {
			if ($('input[name="crawlerFieldId"]:checked').length > 0) {
				bsConfirm('Are you sure you want to delete?', function () {
					$('input[name="crawlerFieldId"]:checked').each(function () {
						var crawlerFieldId = $(this).val();
						$http({
							'url': '/api/crawlers/' + $scope.crawlerId + '/fields/' + crawlerFieldId,
							'method': 'delete'
						})
						.then(function (response) {
							$route.reload();
						});
					});
				});
			}
		});

	})();
};