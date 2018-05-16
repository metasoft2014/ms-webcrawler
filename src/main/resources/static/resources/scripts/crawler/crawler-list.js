var CrawlerListCtrl = function ($scope, $window, $location, $http, $route, $routeParams) {

	/**
	 * Initialize
	 */
	(function () {

		/* Get website list */
		(function () {
			$http({
				'url': '/api/crawlers',
				'method': 'get',
				'params': {
					'page': $location.search().page
				}
			}).then(function (response) {
				var data = response.data,
					content = data.content,
					pageable = data.pageable,
					number = data.number,
					totalElements = data.totalElements,
					totalPages = data.totalPages,
					pageListSize = 10,
					pageTemp = Math.floor(number / pageListSize) * pageListSize;
					pageStart = pageTemp + 1,
					pageEnd = totalPages <= pageTemp + pageListSize ? totalPages : pageTemp + pageListSize,
					hasElements = totalElements > 0 ? true : false,
					hasPrevious = pageStart < pageListSize ? true : false,
					hasNext = pageEnd >= totalPages ? true : false,
					previous = pageStart - 2,
					next = pageEnd,
					pages = [],
					active = false,
					i = 0;
				for (i = 0; i < content.length; i++) {
					content[i].createdDate = moment(content[i].createdDate).format('YYYY-MM-DD HH:mm:ss');
					content[i].updatedDate = moment(content[i].updatedDate).format('YYYY-MM-DD HH:mm:ss');
				}
				for (i = pageStart; i <= pageEnd; i++) {
					if (i == (number + 1)) {
						active = true;
					} else {
						active = false;
					}
					pages.push({
						'number': i,
						'active': active
					});
				}
				$scope.hasElements = hasElements;
				$scope.hasPrevious = hasPrevious;
				$scope.hasNext = hasNext;
				$scope.previous = previous;
				$scope.next = next;
				$scope.totalElements = totalElements;
				$scope.content = content;
				$scope.pages = pages;
			});
		})();

	})();

	/* Events */
	(function () {

		/* input[name="crawlerIdAll"] onclick */
		$('input[name="crawlerIdAll"]').on('click', function () {
			if ($(this).prop('checked')) {
				$('input[name="crawlerId"]').prop('checked', true);
			} else {
				$('input[name="crawlerId"]').prop('checked', false);
			}
		});

		/* button[name="delete"] onclick */
		$('button[name="delete"]').on('click', function () {
			if ($('input[name="crawlerId"]:checked').length > 0) {
				bsConfirm('Are you sure you want to delete?', function () {
					$('input[name="crawlerId"]:checked').each(function () {
						var crawlerId = $(this).val();
						$http({
							'url': '/api/crawlers/' + crawlerId,
							'method': 'delete',
							'params': {
								'crawlerId': crawlerId
							}
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