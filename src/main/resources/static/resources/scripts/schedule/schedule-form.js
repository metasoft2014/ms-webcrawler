var ScheduleFormCtrl = function ($scope, $window, $location, $http, $route, $routeParams) {

	/**
	 * Initialize
	 */
	(function () {

		/* Get schedule info */
		(function () {
			if ($routeParams.id != undefined) {
				$scope.hasId = true;
				$http
					.get('/api/schedules/' + $routeParams.id)
					.then(function (response) {
						var data = response.data,
							createdDate = moment(data.createdDate).format('YYYY-MM-DD HH:mm:ss'),
							updatedDate = moment(data.updatedDate).format('YYYY-MM-DD HH:mm:ss');
						$scope.scheduleId = data.scheduleId;
						$scope.scheduleName = data.scheduleName;
						$scope.scheduleCron = data.scheduleCron;
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
					'scheduleId': $scope.scheduleId,
					'scheduleName': $scope.scheduleName,
					'scheduleCron': $scope.scheduleCron
				};
			if ($scope.scheduleId == undefined) {
				$http({
					'url': '/api/schedules',
					'method': 'post',
					'params': params
				})
				.then(function (response) {
					bsAlert('Saved!', function () {
						$window.location.href = '/#!/schedules';
					});
				});
			} else {
				$http({
					'url': '/api/schedules/' + $scope.scheduleId,
					'method': 'put',
					'params': params
				})
				.then(function (response) {
					bsAlert('Saved!', function () {
						$window.location.href = '/#!/schedules';
					});
				});
			}
		});

		/* button[name="delete"] onclick */
		$('button[name="delete"]').on('click', function () {
			bsConfirm('Are you sure you want to delete?', function () {
				$http({
					'url': '/api/schedules/' + $scope.scheduleId,
					'method': 'delete',
					'params': $scope.scheduleId
				})
				.then(function (response) {
					bsAlert('Deleted!', function () {
						$window.location.href = '/#!/schedules';
					});
				});
			});
		});

	})();
};