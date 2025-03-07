$(function() {
	// QR 정보요청 버튼 클릭시
	$('#qrInfoReqBtn').click(function() {
		fnResetTrxsts();
		// QR 정보 요청
		fnQrInfoReq();
	});
	
	// 초기화
	$('#resetTrxstsBtn').click(function() {
		$('#form')[0].reset();
		
		fnResetTrxsts();
		
		$('#qrCodeArea').text('QR Code');
		
		TRX_CODE = '';
	});
	
	// 거래상태 조회
	$('#trxstsBtn').click(function() {
		fnGetTrxsts();
	});

});

// QR 정보요청
function fnQrInfoReq() {
	
	let cmd = $('#cmd').val();
	let mode = $('#mode').val();
	let svcCode = $('#svcCode').val();
	
	let errMsg = new StringBuffer();

	if (mode.trim() == '') {
		errMsg.append('Mode를 입력해주세요.');
	}
	
	if (svcCode.trim() == '') {
		errMsg.append('서비스 코드를 입력해주세요.');
	}
	
	if (errMsg.toString() != '') {
		alert(errMsg.toString('\n'));
		
		return;
	}

	TRX_CODE = '';
	
	let param = {
		  url: '/mip/start'
		, dataType: 'json'
		, data: JSON.stringify({
			  'cmd': cmd
			, 'mode': mode
			, 'svcCode': svcCode
		})
		, contentType: 'application/json; charset=utf-8'
		, type: 'POST'
		, success: function(data) {
			let resultData = JSON.parse(Base64.decode(data.data));
			
			if (data.result && (resultData.errmsg || '') == '') {
				$('#qrCodeArea').empty();
				
				TRX_CODE = JSON.parse(Base64.decode(resultData.m200Base64)).trxcode;
				
				let qrCodeArea = document.getElementById('qrCodeArea');
				let width = qrCodeArea.clientWidth;
				let size = width > 300 ? 300:width;
				
				new QRCode(qrCodeArea, {
					  width: size
					, height: size
					, text: resultData.m200Base64
				});
				
				fnGetTrxsts();
			} else {
				alert(resultData.errmsg);
			}
		}
		, error: function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
		}
	};
	
	$.ajax(param);
}
