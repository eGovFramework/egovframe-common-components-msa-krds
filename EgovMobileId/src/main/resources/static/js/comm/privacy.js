/**
 * egovframework.com.sec.rnc.mip.mva.sp.comm.web.MipController.getPrivacy() 실행하는 Javascript 파일
 */

$(function() {
	// 초기 상태
	$('#resultTag').text('');
	$('#resultArea').text('');
	$('#resultArea').hide();
	$('#resultDlphotoimageTag').css('display', 'none');

});

function getPrivacy(trxinfo) {
    console.log("privacy.js - getPrivacy() - license(api로 보낼 pathvariable) : " + license);
	
	let errMsg = new StringBuffer();
	if (errMsg.toString() != '') {
		alert(errMsg.toString('\n'));
		return;
	}

	let param = {
		  url: '/mip/privacy/' + encodeURIComponent(license)
		, dataType: 'json'
		, data: JSON.stringify({
			'data': Base64.encode(JSON.stringify(trxinfo))
		})
		, contentType: 'application/json; charset=utf-8'
		, type: 'POST'
		, success: function(data) {
			
			if ((data.errorMsg || '') == '') {
				let map = '';
				
				map = JSON.parse(Base64.decode(data.data));
				
				fnGetPrivacy(map);

			} else {
				alert(data.errorMsg);
				
				$('#resultTag').text('');
				$('#resultArea').text('');
				$('#resultArea').hide();
				
			}
		}
		, error: function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR, textStatus, errorThrown);
		}
	};
	
	$.ajax(param);
}

function fnGetPrivacy(map){
	
	let licenseName = map.licenseName;
	let licenseVO = map.licenseVO;
	
	if(licenseVO){
		
		// 1. key가 모든 신분증의 공통 정보일 경우
		$('#resultNameTag').text(licenseVO.name);
		$('#resultIhidnumTag').text(licenseVO.ihidnum);
		$('#resultAddressTag').text(licenseVO.address);
		$('#resultBirthTag').text(licenseVO.birth);
		const hex = licenseVO.dlphotoimage;
		const uint8Array = fnHexToImage(hex);
		$('#resultDlphotoimageTag').css('display', 'block');
		$('#resultDlphotoimageTag').css('width', '120');
		$('#resultDlphotoimageTag').css('height', '150');
		$('#resultDlphotoimageTag').attr('src', URL.createObjectURL(new Blob([uint8Array], {'type': 'application/octet-stream'})));
        
        $('#resultTag').text(license + "/" + licenseName);
        
		// 모든 신분증의 공통 정보
		const commonInfo = {
			name: "name(이름)",
			ihidnum: "ihidnum(주민등록번호)",
			address: "address(주소)",
			birth: "birth(생년월일)",
			dlphotoimage : "dlphotoimage(사진이미지)"
		};
        
		// 2. key가 특정 신분증에만 있는 정보일 경우
		// licenseOfMap 객체의 모든 속성을 탐색
		const textAreaArr = [];
		for (const key in licenseVO) {
			// commonInfo와 겹치지 않는 속성만 추가
			if(!commonInfo[key]){
				 textAreaArr.push(`${key} : ${licenseVO[key]}`);
			}
	    }
	    const textArea = textAreaArr.join(' / ');
	    $('#resultArea').text(textArea);
		$('#resultArea').show();
	    
		
	}
	
}

// Hex to Image 변환
function fnHexToImage(hex) {
	
	let b = new Array();
	for (let i = 0; i < hex.length / 2; i++) {
		let h = hex.substr(i * 2, 2);	
		b[i] = parseInt(h, 16);
	}
	
	const uint8Array = new Uint8Array(b);
	
	return uint8Array;
}