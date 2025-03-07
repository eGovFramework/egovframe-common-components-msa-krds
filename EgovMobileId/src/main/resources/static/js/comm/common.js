/**
 * 공통 script
 */

//StringBuffer 설정
let StringBuffer = function() {
	this.buffer = new Array();
};

StringBuffer.prototype.append = function(str) {
	this.buffer[this.buffer.length] = str;
};

StringBuffer.prototype.toString = function(s) {
	return this.buffer.join((s || ''));
};

let TRX_CODE = '';  // 거래코드

// 거래상태 조회
function fnGetTrxsts() {
	let trxcode = TRX_CODE;
	
	let errMsg = new StringBuffer();

	if ((trxcode || '') == '') {
		errMsg.append('거래코드가 없습니다.');
	}
	
	if (errMsg.toString() != '') {
		alert(errMsg.toString('\n'));
		
		return;
	}
	
	let param = {
		  url: '/mip/trxsts'
		, dataType: 'json'
		, data: JSON.stringify({'data': Base64.encode(JSON.stringify({'trxcode': trxcode}))})
		, contentType: 'application/json; charset=utf-8'
		, type: 'POST'
		, success: function(data) {
			if ((data.errcode || '') == '') {
				let trxinfo = JSON.parse(Base64.decode(data.data));
				let trxStsCodeVal = {
					  '0001': '서비스 요청'
					, '0002': 'profile 요청'
					, '0003': 'VP 검증 요청'
					, '0004': 'VP 검증 완료'
					, '0005': 'VP 검증 오류'
				};
				
				$('#trxcodeTag').text(trxinfo.trxcode);
				$('#trxStsCodeTag').text(trxStsCodeVal[trxinfo.trxStsCode] + '(' + trxinfo.trxStsCode + ')');
				$('#vpVerifyResultTag').text(trxinfo.vpVerifyResult);
				$('#regDtTag').text(trxinfo.regDt);
				$('#profileSendDtTag').text(trxinfo.profileSendDt);
				$('#vpReceptDtTag').text(trxinfo.vpReceptDt);
				$('#imgSendDtTag').text(trxinfo.imgSendDt);
				$('#udtDtTag').text(trxinfo.udtDt || trxinfo.regDt);
				
				let vp = trxinfo.vp;
				
				if (vp) {
					/*
					$('#vpTag').text('보기');
					$('#vpTag').text('복호화 전 vp');
					*/
					
					$('#vpArea').text(vp);
					
					/*					
					$('#vpTag').click(function() {
						if ($(this).text() == '보기') {
							$('#vpArea').show();
						
							$(this).text('닫기');
						} else {
							$('#vpArea').hide();
						
							$(this).text('보기');
						}
					});
					*/
					$('#vpArea').show();
					
					//신분증에 담긴 정보 조회
					getPrivacy(trxinfo);
					
				} else {
					//$('#vpTag').text('');
					$('#vpArea').text('');
					$('#vpArea').hide();
				}
			} else {
				alert(data.errmsg);
			}
		}
		, error: function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
		}
	};
	
	$.ajax(param);
}

// 초기화
function fnResetTrxsts() {
	TRX_CODE = '';
	
	$('#trxcodeTag').text('');
	$('#trxStsCodeTag').text('');
	$('#vpVerifyResultTag').text('');
	$('#regDtTag').text('');
	$('#profileSendDtTag').text('');
	$('#vpReceptDtTag').text('');
	$('#imgSendDtTag').text('');
	$('#udtDtTag').text('');
	
	// 추가
	//$('#vpTag').text('');
	$('#vpArea').text('');
	$('#vpArea').hide();
	
	$('#resultAddressTag').text('');
	$('#resultBirthTag').text('');
	$('#resultIhidnumTag').text('');
	$('#resultNameTag').text('');
	$('#resultDlphotoimageTag').css('display', 'none');
	$('#resultTag').text('');
	$('#resultArea').text('');
	$('#resultArea').hide('');
	
	$('#pushResultTag').text('');
}
