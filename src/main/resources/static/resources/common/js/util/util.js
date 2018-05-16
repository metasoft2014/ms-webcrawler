/**
 * getUrlParameters
 */
var getUrlParameters = function (url, callback) {
    var parameters = {};
    url.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
		parameters[key] = value;
    });
    return parameters;
};

/**
 * bsAlert
 */
var bsAlert = function (msg, callback) {
	var modalHtml = '';
	modalHtml += '<div id="bsAlert" class="modal fade">';
	modalHtml += '	<div class="modal-dialog">';
	modalHtml += '		<div class="modal-content">';
	modalHtml += '			<div class="modal-header">';
	modalHtml += '				<h5 class="modal-title">Content of this page</h5>';
	modalHtml += '				<button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>';
	modalHtml += '			</div>';
	modalHtml += '			<div class="modal-body">' + msg + '</div>';
	modalHtml += '			<div class="modal-footer">';
	modalHtml += '				<button class="btn btn-secondary" type="button" data-dismiss="modal">Close</button>';
	modalHtml += '			</div>';
	modalHtml += '		</div>';
	modalHtml += '	</div>';
	modalHtml += '</div>';
	$('body').append(modalHtml);
	$('#bsAlert').modal('show');
	$('#bsAlert').on('hidden.bs.modal', function () {
		$('#bsAlert').remove();
		if (typeof callback == 'function') {
			callback();
		}
	});
};

/**
 * bsConfirm
 */
var bsConfirm = function (msg, callbackConfirm, callbackCancel) {
	var modalHtml = '';
	modalHtml += '<div id="bsConfirm" class="modal fade">';
	modalHtml += '	<div class="modal-dialog">';
	modalHtml += '		<div class="modal-content">';
	modalHtml += '			<div class="modal-header">';
	modalHtml += '				<h5 class="modal-title">Content of this page</h5>';
	modalHtml += '				<button id="bsConfirmClose" class="close" type="button" data-dismiss="modal"><span>&times;</span></button>';
	modalHtml += '			</div>';
	modalHtml += '			<div class="modal-body">' + msg + '</div>';
	modalHtml += '			<div class="modal-footer">';
	modalHtml += '				<button id="bsConfirmConfirm" class="btn btn-primary" type="button" data-dismiss="modal">Confirm</button>';
	modalHtml += '				<button id="bsConfirmCancel" class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>';
	modalHtml += '			</div>';
	modalHtml += '		</div>';
	modalHtml += '	</div>';
	modalHtml += '</div>';
	$('body').append(modalHtml);
	$('#bsConfirmConfirm').on('click', function () {
		if (typeof callbackConfirm == 'function') {
			callbackConfirm();
		}
	});
	$('#bsConfirmCancel, #bsConfirmClose').on('click', function () {
		if (typeof callbackCancel == 'function') {
			callbackCancel();
		}
	});
	$('#bsConfirm').modal({
		'show': false,
		'backdrop': 'static',
		'keyboard': false
	});
	$('#bsConfirm').modal('show');
	$('#bsConfirm').on('hidden.bs.modal', function () {
		$('#bsConfirm').remove();
	});
};