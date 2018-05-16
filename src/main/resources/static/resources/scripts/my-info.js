var MyInfoCtrl = function ($scope, $window, $location, $http, $route, $routeParams) {

	/**
	 * Initialize
	 */
	(function () {

		/* Get my info */
		(function () {
			$http
				.get('/api/auth/my-info')
				.then(function (response) {
					var data = response.data,
						createdDate = moment(data.createdDate).format('YYYY-MM-DD HH:mm:ss'),
						updatedDate = moment(data.updatedDate).format('YYYY-MM-DD HH:mm:ss');
					$scope.userId = data.userId;
					$scope.userName = data.userName;
					$scope.email = data.email;
					$scope.firstName = data.firstName;
					$scope.lastName = data.lastName;
					$scope.createdDate = createdDate;
					$scope.updatedDate = updatedDate;
				});
		})();

	})();

	/**
	 * Events
	 */
	(function () {

		/* button[name="save"] onclick */
		$('button[name="save"]').on('click', function () {
			var params = {
					'userName': $scope.userName,
					'email': $scope.email,
					'password': $scope.password,
					'confirmPassword': $scope.confirmPassword,
					'firstName': $scope.firstName,
					'lastName': $scope.lastName
				};
				$http({
					'url': '/api/auth/my-info/update',
					'method': 'put',
					'params': params
				})
				.then(function (response) {
					var data = response.data,
						code = data.registerMessageCode,
						message = data.message;
					bsAlert(message, function () {
						if ('SUCCESS' == code) {
							$window.location.href = '/logout';
						} else if ('ERROR_EXISTS_USER_NAME' == code) {

						} else if ('ERROR_EXISTS_EMAIL' == code) {

						} else if ('ERROR_MISMATCH_CONFIRM_PASSWORD' == code) {

						}
					});
				}, function (response) {
					bsAlert('Error!');
				});
		});

		/* button[name="delete"] onclick */
		$('button[name="delete"]').on('click', function () {
			bsConfirm('Are you sure you want to delete?', function () {
				$http({
					'url': '/api/auth/my-info/delete',
					'method': 'delete'
				})
				.then(function (response) {
					bsAlert('Deleted!', function () {
						$window.location.href = '/logout';
					});
				});
			});
		});

	})();
};