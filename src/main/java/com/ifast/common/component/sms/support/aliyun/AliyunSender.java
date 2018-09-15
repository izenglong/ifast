package com.ifast.common.component.sms.support.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QueryInterSmsIsoInfoRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QueryInterSmsIsoInfoResponse;
import com.aliyuncs.dysmsapi.model.v20170525.QueryInterSmsIsoInfoResponse.IsoSupportDTO;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ifast.api.exception.IFastApiException;
import com.ifast.common.component.sms.support.SmsSender;
import com.ifast.common.service.ConfigService;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.common.utils.JSONUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author xlongwei 2018年9月12日
 * @see <a href="https://help.aliyun.com/document_detail/55284.html?spm=5176.10629532.106.1.5f5b1cbeZg62kR">短信发送文档</a>
 */
@Slf4j
public class AliyunSender implements SmsSender {
	@Autowired
	private ConfigService configService;
	@Setter
	private AliyunProperties properties;
	
	private static final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
	private static final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
	private static String accessKeyId;// 你的accessKeyId,参考本文档步骤2
	private static String accessKeySecret;
	
	public AliyunSender(AliyunProperties properties) {
		this.properties = properties;
		accessKeyId = properties.getAccessKeyId();
		accessKeySecret = properties.getAccessKeySecret();
	}

	@Override
	public void send(String mobile, String code, String scene) throws IFastApiException {
		String k = properties.getScenes().get(scene);
		if (StringUtils.isBlank(k)) {
			log.warn("【SMS】发送短信失败：请配置ifast.sms.aliyun.scenes.{}", scene);
			throw new IFastApiException(EnumErrorCode.apiSmsSendFailed.getCodeStr());
		}
		String v = configService.getValueByKey(k);
		if (StringUtils.isBlank(v)) {
			log.warn("【SMS】发送短信失败：请添加系统配置sys_config.{}={SignName, TemplateCode}", k);
			throw new IFastApiException(EnumErrorCode.apiSmsSendFailed.getCodeStr());
		}

		Map<String, Object> config = JSONUtils.jsonToMap(v);
		if (config == null || !config.containsKey("SignName") || !config.containsKey("TemplateCode")) {
			log.warn("【SMS】发送短信失败：请修改系统配置sys_config.{}={SignName, TemplateCode}", k);
			throw new IFastApiException(EnumErrorCode.apiSmsSendFailed.getCodeStr());
		}
		
		String signName = config.get("SignName").toString(), TemplateCode = config.get("TemplateCode").toString();
		String templateParam = "{\"code\":\"" + code + "\"}";
		send(mobile, signName, TemplateCode, templateParam);
		log.info("【SMS】发送短信成功，mobile:{},code:{},scene:{}", mobile, code, scene);
	}

	/**
	 * @param phoneNumbers 待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
	 * @param signName 短信签名-可在短信控制台中找到
	 * @param templateCode 短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
	 * @param templateParam 模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为"{\"name\":\"Tom\", \"code\":\"123\"}"
	 * @throws IFastApiException
	 */
	public static void send(String phoneNumbers, String signName, String templateCode, String templateParam) {
		if(StringUtils.isBlank(accessKeyId) || StringUtils.isBlank(accessKeySecret) 
				|| StringUtils.isBlank(phoneNumbers) || StringUtils.isBlank(signName) || StringUtils.isBlank(templateCode) || StringUtils.isBlank(templateParam)) {
			throw new IFastApiException(EnumErrorCode.apiSmsSendFailed.getCodeStr());
		}
		
		try {
			// 设置超时时间-可自行调整
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			// 初始化ascClient,暂时不支持多region（请勿修改）
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			// 组装请求对象
			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);
			// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
			request.setPhoneNumbers(phoneNumbers);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(signName);
			// 必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
			request.setTemplateCode(templateCode);
			// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
			request.setTemplateParam(templateParam);
			// 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
			// request.setSmsUpExtendCode("90997");
			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			// request.setOutId("yourOutId");
			// 请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				log.info("【SMS】阿里云发送短信成功，code:{},message:{},requestId:{},bizId:{}", sendSmsResponse.getCode(), sendSmsResponse.getMessage(), sendSmsResponse.getRequestId(), sendSmsResponse.getBizId());
			}else {
				log.warn("【SMS】阿里云发送失败，code:{},message:{},requestId:{},bizId:{},mobile:{},signName:{},templateCode:{},templateParam:{}", sendSmsResponse.getCode(), sendSmsResponse.getMessage(), sendSmsResponse.getRequestId(), sendSmsResponse.getBizId(), phoneNumbers, signName, templateCode, templateParam);
				throw new IFastApiException(EnumErrorCode.apiSmsSendFailed.getCodeStr());
			}
		}catch (Exception e) {
			log.warn("【SMS】阿里云发送短信失败:{},mobile:{},signName:{},templateCode:{},templateParam:{}", e.getMessage(), phoneNumbers, signName, templateCode, templateParam);
			throw new IFastApiException(EnumErrorCode.apiSmsSendFailed.getCodeStr());
		}
	}
	
	/**
	 * 国家区号可以考虑存入字典表
	 * @return [(isoCode,countryCode,countryName), (963,SY,叙利亚), (886,TW,中国台湾), ...]
	 */
	public static List<Triple<String, String, String>> countryCode(){
		if(StringUtils.isBlank(accessKeyId) || StringUtils.isBlank(accessKeySecret)) {
			throw new IFastApiException(EnumErrorCode.apiSmsSendFailed.getCodeStr());
		}
		
		try {
			List<Triple<String, String, String>> list = new ArrayList<>();
			// 可自助调整超时时间
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			// 初始化acsClient,暂不支持region化
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("ak-openapi-pop-cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			// 组装请求对象
			QueryInterSmsIsoInfoRequest request = new QueryInterSmsIsoInfoRequest();
			// request.setCountryName("中国");
			// hint 此处可能会抛出异常，注意catch
			QueryInterSmsIsoInfoResponse queryInterSmsIsoInfoResponse = acsClient.getAcsResponse(request);
			if (queryInterSmsIsoInfoResponse.getCode() != null && "OK".equals(queryInterSmsIsoInfoResponse.getCode())) {
				List<IsoSupportDTO> isoSupportDTOs = queryInterSmsIsoInfoResponse.getIsoSupportDTOs();
				for (IsoSupportDTO isoSupportDTO : isoSupportDTOs) {
					list.add(Triple.of(isoSupportDTO.getIsoCode(), isoSupportDTO.getCountryCode(), isoSupportDTO.getCountryName()));
				}
			}
			return list;
		}catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args) {
		accessKeyId = "";
		accessKeySecret = "";
		String phoneNumbers = "", signName = "", templateCode = "", templateParam = "{\"code\":\"3572\"}";
		log.info("countryCode: {}", countryCode());
		send(phoneNumbers, signName, templateCode, templateParam);
	}
}
