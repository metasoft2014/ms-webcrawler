var WebsiteFormCtrl = function ($scope, $window, $location, $http, $route, $routeParams) {

	/**
	 * Initialize
	 */
	(function () {

		/* Get website info */
		(function () {
			if ($routeParams.id != undefined) {
				$scope.hasId = true;
				$http
					.get('/api/websites/' + $routeParams.id)
					.then(function (response) {
						var data = response.data,
							createdDate = moment(data.createdDate).format('YYYY-MM-DD HH:mm:ss'),
							updatedDate = moment(data.updatedDate).format('YYYY-MM-DD HH:mm:ss');
						$scope.websiteId = data.websiteId;
						$scope.websiteName = data.websiteName;
						$scope.websiteDomainName = data.websiteDomainName;
						$scope.createdDate = createdDate;
						$scope.updatedDate = updatedDate;
					});
			}
		})();

	})();

	/**
	 * Events
	 */
	(function () {

		/* button[name="save"] onclick */
		$('button[name="save"]').on('click', function () {
			var params = {
					'websiteId': $scope.websiteId,
					'websiteName': $scope.websiteName,
					'websiteDomainName': $scope.websiteDomainName
				};
			if ($scope.websiteId == undefined) {
				$http({
					'url': '/api/websites',
					'method': 'post',
					'params': params
				})
				.then(function (response) {
					bsAlert('Saved!', function () {
						$window.location.href = '/#!/websites';
					});
				});
			} else {
				$http({
					'url': '/api/websites/' + $scope.websiteId,
					'method': 'put',
					'params': params
				})
				.then(function (response) {
					bsAlert('Saved!', function () {
						$window.location.href = '/#!/websites';
					});
				});
			}
		});

		/* button[name="delete"] onclick */
		$('button[name="delete"]').on('click', function () {
			bsConfirm('Are you sure you want to delete?', function () {
				$http({
					'url': '/api/websites/' + $scope.websiteId,
					'method': 'delete',
					'params': $scope.websiteId
				})
				.then(function (response) {
					bsAlert('Deleted!', function () {
						$window.location.href = '/#!/websites';
					});
				});
			});
		});

	})();
};