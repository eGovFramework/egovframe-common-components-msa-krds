package egovframework.com.mip.mva.sp.comm.service.impl;

import com.google.gson.JsonSyntaxException;
import com.raonsecure.omnione.core.data.did.Proof;
import com.raonsecure.omnione.core.data.iw.Unprotected;
import com.raonsecure.omnione.core.data.iw.profile.CommonProfile;
import com.raonsecure.omnione.core.data.iw.profile.result.VCVerifyProfileResult;
import com.raonsecure.omnione.core.data.rest.ResultProfile;
import com.raonsecure.omnione.core.data.rest.ResultVcStatus;
import com.raonsecure.omnione.sdk_server_core.data.VcResult;
import egovframework.com.mip.mva.sp.comm.enums.MipErrorEnum;
import egovframework.com.mip.mva.sp.comm.enums.TrxStsCodeEnum;
import egovframework.com.mip.mva.sp.comm.enums.VcStatusEnum;
import egovframework.com.mip.mva.sp.comm.exception.SpException;
import egovframework.com.mip.mva.sp.comm.service.MipDidVpService;
import egovframework.com.mip.mva.sp.comm.service.TrxInfoService;
import egovframework.com.mip.mva.sp.comm.util.Base64Util;
import egovframework.com.mip.mva.sp.comm.util.VerifyManager;
import egovframework.com.mip.mva.sp.comm.vo.*;
import egovframework.com.mip.mva.sp.config.ConfigBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName mip.mva.sp.comm.service.impl
 * @FileName MipDidVpServiceImpl.java
 * @Author Min Gi Ju
 * @Date 2022. 5. 31.
 * @Description VP 검증 ServiceImpl
 * 
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2024. 5. 28.    민기주           최초생성
 * 2025. 12.13.    박성완           표준프레임워크 v4.3 - mapPrivacy 메서드 생성
 * </pre>
 */
@Service("mipDidVpService")
public class MipDidVpServiceImpl implements MipDidVpService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MipDidVpServiceImpl.class);

	/** 검증 Manager */
	private final VerifyManager verifyManager;
	/** 거래정보 Service */
	private final TrxInfoService trxInfoService;

	/**
	 * 생성자
	 * 
	 * @param verifyManager 검증 Manager
	 * @param trxInfoService 거래정보 Service
	 */
	public MipDidVpServiceImpl(VerifyManager verifyManager, TrxInfoService trxInfoService) {
		this.verifyManager = verifyManager;
		this.trxInfoService = trxInfoService;
	}

	/**
	 * Profile 요청
	 * 
	 * @MethodName getProfile
	 * @param trxInfo 거래정보
	 * @return Base64로 인코딩된 Profile
	 * @throws SpException
	 */
	@Override
	public String getProfile(TrxInfoVO trxInfo) throws SpException {
		ResultProfile resultProfile = null;

		String trxcode = trxInfo.getTrxcode();

		try {
			String svcCode = trxInfo.getSvcCode();

			resultProfile = verifyManager.profile(svcCode);

			CommonProfile commonProfile = ConfigBean.gson.fromJson(Base64Util.decode(resultProfile.getProfileBase64()), CommonProfile.class);

			TrxInfoVO trxInfoNew = new TrxInfoVO();

			trxInfoNew.setTrxcode(trxcode);
			trxInfoNew.setTrxStsCode(TrxStsCodeEnum.PROFILE_REQ.getVal());
			trxInfoNew.setNonce(commonProfile.getProfile().getNonce());

			trxInfoService.modifyTrxInfo(trxInfoNew);
		} catch (SpException e) {
			e.setTrxcode(trxcode);

			throw e;
		} catch (Exception e) {
			throw new SpException(MipErrorEnum.UNKNOWN_ERROR, trxcode, e.getMessage());
		}

		return resultProfile.getProfileBase64();
	}

	/**
	 * VP 검증
	 * 
	 * @MethodName verifyVP
	 * @param trxInfo 거래정보
	 * @param vp VP 정보
	 * @return 검증 성공 여부
	 * @throws SpException
	 */
	@Override
	public Boolean verifyVP(TrxInfoVO trxInfo, VP vp) throws SpException {
		Boolean result = false;

		String trxcode = trxInfo.getTrxcode();
		String profileNonce = trxInfo.getNonce();

		try {
			TrxInfoVO trxInfoNew = new TrxInfoVO();

			trxInfoNew.setTrxcode(trxcode);
			trxInfoNew.setTrxStsCode(TrxStsCodeEnum.VERIFY_REQ.getVal());

			trxInfoService.modifyTrxInfo(trxInfoNew);

			// VP 검증 Start
			VCVerifyProfileResult vCVerifyProfileResult = new VCVerifyProfileResult();

			vCVerifyProfileResult.setEncryptType(vp.getEncryptType());
			vCVerifyProfileResult.setKeyType(vp.getKeyType());
			vCVerifyProfileResult.setType(vp.getType());
			vCVerifyProfileResult.setData(vp.getData());
			vCVerifyProfileResult.setAuthType(vp.getAuthType());
			vCVerifyProfileResult.setDid(vp.getDid());
			vCVerifyProfileResult.setNonce(vp.getNonce());

			VcResult vcResult = verifyManager.verify(trxInfo.getSvcCode(), vCVerifyProfileResult);

			if (vcResult == null || !vcResult.getStatus().equals("1")) {
				return result;
			}
			// VP 검증 End

			// VP 상태 확인 Start
			ResultVcStatus resultVcStatus = verifyManager.getVCStatus(vcResult.getReqVp().getVerifiableCredential().get(0).getId());

			String vcStatus = resultVcStatus.getVcStatus();

			if (vcStatus.equalsIgnoreCase(VcStatusEnum.ACTIVE.getVal())) { // 활성화 상태
				result = true;
			} else if (vcStatus.equalsIgnoreCase(VcStatusEnum.NEED_RENEW.getVal())) { // 갱신필요 상태
				String memo = resultVcStatus.getMemo();

				if (memo.equals("주소변경")) {
					result = true;
				} else {
					throw new SpException(MipErrorEnum.UNKNOWN_ERROR, trxcode, "제출불가 상태 : " + vcStatus + "(" + memo + ")");
				}
			} else {
				throw new SpException(MipErrorEnum.UNKNOWN_ERROR, trxcode, "제출불가 상태 : " + vcStatus);
			}
			// VP 상태 확인 End

			// Nonce 위변조 확인 Start
			// 일반인증시 proof를 사용하고 안심인증시 proofs를 사용
			Proof proof = vcResult.getReqVp().getProof();
			List<Proof> proofs = vcResult.getReqVp().getProofs();

			if (ObjectUtils.isEmpty(proof) && ObjectUtils.isEmpty(proofs)) {
				throw new SpException(MipErrorEnum.SP_UNEXPECTED_MSG_FORMAT, trxcode, "proof");
			}

			String vpNonce = null;

			if (!ObjectUtils.isEmpty(proof)) {
				vpNonce = proof.getNonce();
			} else {
				vpNonce = proofs.get(0).getNonce();
			}

			LOGGER.debug("profileNonce : {}, vpNonce : {}", profileNonce, vpNonce);

			if (vpNonce.indexOf(profileNonce) == -1) {
				throw new SpException(MipErrorEnum.SP_MISMATCHING_NONCE, trxcode);
			}
			// Nonce 위변조 확인 End

			trxInfoNew.setTrxStsCode(TrxStsCodeEnum.VERIFY_COM.getVal());
			trxInfoNew.setVpVerifyResult(result ? "Y" : "N");
			trxInfoNew.setVp(ConfigBean.gson.toJson(vp));

			trxInfoService.modifyTrxInfo(trxInfoNew);
		} catch (SpException e) {
			e.setTrxcode(trxcode);

			throw e;
		} catch (Exception e) {
			throw new SpException(MipErrorEnum.UNKNOWN_ERROR, trxcode, e.getMessage());
		}

		return result;
	}

	/**
	 * VP 재검증(부인방지)
	 * 
	 * @MethodName reVerifyVP
	 * @param svcCode 서비스코드
	 * @param vp VP 정보
	 * @return 재검증 성공 여부
	 * @throws SpException
	 */
	@Override
	public Boolean reVerifyVP(VP vp) throws SpException {
		VCVerifyProfileResult vCVerifyProfileResult = new VCVerifyProfileResult();

		vCVerifyProfileResult.setEncryptType(vp.getEncryptType());
		vCVerifyProfileResult.setKeyType(vp.getKeyType());
		vCVerifyProfileResult.setType(vp.getType());
		vCVerifyProfileResult.setData(vp.getData());
		vCVerifyProfileResult.setAuthType(vp.getAuthType());
		vCVerifyProfileResult.setDid(vp.getDid());
		vCVerifyProfileResult.setNonce(vp.getNonce());

		VcResult vcResult = verifyManager.checkUserProof(vCVerifyProfileResult);

		return vcResult.getStatus().equals("1");
	}

	/**
	 * VP data 조회
	 * 
	 * @MethodName getVPData
	 * @param vp VP
	 * @return 복호화된 VP data
	 * @throws SpException
	 */
	@Override
	public String getVPData(VP vp) throws SpException {
		VCVerifyProfileResult vCVerifyProfileResult = new VCVerifyProfileResult();

		vCVerifyProfileResult.setEncryptType(vp.getEncryptType());
		vCVerifyProfileResult.setKeyType(vp.getKeyType());
		vCVerifyProfileResult.setType(vp.getType());
		vCVerifyProfileResult.setData(vp.getData());
		vCVerifyProfileResult.setAuthType(vp.getAuthType());
		vCVerifyProfileResult.setDid(vp.getDid());
		vCVerifyProfileResult.setNonce(vp.getNonce());

		String data = verifyManager.getVPData(vCVerifyProfileResult);

		return data;
	}

	/**
	 * Privacy 조회
	 * 
	 * @MethodName getPrivacy
	 * @param trxcode 거래코드
	 * @return Privacy 목록
	 * @throws SpException
	 */
	@Override
	public List<Unprotected> getPrivacy(String trxcode) throws SpException {
		TrxInfoVO trxInfo = trxInfoService.getTrxInfo(trxcode);

		VP vp = null;

		try {
			vp = ConfigBean.gson.fromJson(trxInfo.getVp(), VP.class);
		} catch (JsonSyntaxException e) {
			throw new SpException(MipErrorEnum.SP_UNEXPECTED_MSG_FORMAT, trxcode, "getPrivacy");
		}

		VCVerifyProfileResult vCVerifyProfileResult = new VCVerifyProfileResult();

		vCVerifyProfileResult.setEncryptType(vp.getEncryptType());
		vCVerifyProfileResult.setKeyType(vp.getKeyType());
		vCVerifyProfileResult.setType(vp.getType());
		vCVerifyProfileResult.setData(vp.getData());
		vCVerifyProfileResult.setAuthType(vp.getAuthType());
		vCVerifyProfileResult.setDid(vp.getDid());
		vCVerifyProfileResult.setNonce(vp.getNonce());

		String vpData = verifyManager.getVPData(vCVerifyProfileResult);

		List<Unprotected> privacyList = verifyManager.getPrivacy(vpData);

		return privacyList;
	}
	
	/**
	 * Privacy를 LicenseVO로 매핑
	 * 
	 * @MethodName mapPrivacy
	 * @param license 라이선스명
	 * @param privacy Privacy 목록
	 * @return LicenseVO로 매핑된 privacy 목록
	 * @throws SpException
	 */
	@Override
	public Map<String, Object> mapPrivacy(String license, List<Unprotected> privacy) throws SpException {
	
			String licenseName = "";
			Object licenseVO;
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			switch (license) {
			case "driver":
				licenseName = "driver";
				licenseVO = new LicenseDriverVO().mapFromList(privacy);
				break;
			case "honor":
				licenseName = "honor";
				licenseVO = new LicenseHonorVO().mapFromList(privacy);
				break;
			case "expat":
				licenseName = "expat";
				licenseVO = new LicenseExpatVO().mapFromList(privacy);
				break;
			default:
				licenseName = null;
				licenseVO = null;
				break;
			}
			
			map.put("licenseName", licenseName);
			map.put("licenseVO", licenseVO);
			
		return map;
	}

	/**
	 * 이미지 변환(Hex String to byte Array)
	 * 
	 * @MethodName transImageHexToByte
	 * @param imageData String
	 * @return 변환된 이미지 데이터
	 * @throws SpException
	 */
	@Override
	public byte[] transImageHexToByte(String imageData) throws SpException {
		int len = imageData.length();
		byte[] imageByte = new byte[len / 2];

		for (int i = 0; i < len; i += 2) {
			imageByte[i / 2] = (byte) ((Character.digit(imageData.charAt(i), 16) << 4) + Character.digit(imageData.charAt(i + 1), 16));
		}

		return imageByte;
	}

}
