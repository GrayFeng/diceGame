package net.netne.api.control;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.netne.api.service.IMemberService;
import net.netne.api.service.IUploadService;
import net.netne.common.Constant;
import net.netne.common.annotation.NotNeedLogin;
import net.netne.common.annotation.NotNeedUID;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.enums.EUploadType;
import net.netne.common.pojo.ImageUploadResult;
import net.netne.common.pojo.LoginInfo;
import net.netne.common.pojo.MemberPhoto;
import net.netne.common.pojo.Result;
import net.netne.common.uitls.ResultUtil;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;

@Controller
@RequestMapping("/api")
public class UploadController {

	private final static Logger logger = Logger.getLogger(UploadController.class);

	static final String TAG_PRE = "pre";
	static final String TAG_BACK = "back";
	static final String TAG_DRIVER = "driver";
	static final String URL_PRE = "/upload/";
	
	@Autowired
	private IUploadService uploadService;
	
	@Autowired
	private IMemberService memberService;
	
	/**
	 * 只支持上传一个文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(String uid,MultipartFile file) {
		Result result = null;
		LoginInfo loginInfo = MemberCache.getInstance().get(uid);
		if(loginInfo != null && loginInfo.getMember() != null){
			ImageUploadResult imageUploadResult =  uploadService.processupload(loginInfo.getMember().getId(),file,EUploadType.MEMBER_PHOTO);
			if(imageUploadResult.isSuccess()){
				result = new Result(EEchoCode.SUCCESS.getCode(),"上传成功");
				Map<String,String> paramMap = Maps.newHashMap();
				paramMap.put("url", Constant.PHOTO_URL_PATH + loginInfo.getMember().getId());
				result.setRe(paramMap);
			}else{
				result = new Result(EEchoCode.ERROR.getCode(), imageUploadResult.getMsg());
			}
		}else{
			result = new Result(EEchoCode.ERROR.getCode(), "请先登录");
		}
		return ResultUtil.getJsonString(result);
	}

	@RequestMapping(value = "/img", method = RequestMethod.GET)
	@NotNeedLogin
	@NotNeedUID
	public void getImg(Integer key,HttpServletRequest request, HttpServletResponse response) {
		OutputStream outputStream = null;
		try{
			if(key != null){
				MemberPhoto memberPhoto = memberService.getMemberPhoto(key);
				if(memberPhoto != null){
					outputStream = response.getOutputStream();
					outputStream.write(memberPhoto.getPhoto());
					outputStream.flush();
					response.flushBuffer();
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}finally {
			IOUtils.closeQuietly(outputStream);
		}
	}
}
