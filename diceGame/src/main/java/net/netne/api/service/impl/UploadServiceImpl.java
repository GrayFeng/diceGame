package net.netne.api.service.impl;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import net.netne.api.dao.IMemberDao;
import net.netne.api.dao.IPrizeDao;
import net.netne.api.service.IUploadService;
import net.netne.common.enums.EUploadType;
import net.netne.common.pojo.ImageUploadResult;
import net.netne.common.pojo.MemberPhoto;
import net.netne.common.pojo.PrizePhoto;
import net.netne.common.uitls.SimpleFileUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiceImpl implements IUploadService{
	private final static Logger logger = Logger.getLogger(UploadServiceImpl.class);
	@Autowired
	private IMemberDao memberDao;
	@Autowired
	private IPrizeDao prizeDao;

	public ImageUploadResult processupload(Integer memberId,MultipartFile uploadFile,EUploadType uploadType) {
		ImageUploadResult result = null;
		if (uploadFile == null) {
			return new ImageUploadResult(false,"请选择文件上传");
		}
		long fileSize = uploadFile.getSize();
		if (fileSize > 300 * 1024L) {
			return new ImageUploadResult(false,"图片大小不能超过300Kkb");
		}
		try {
			//奖品图片格式检测
			if(EUploadType.PRIZE_PHOTO.equals(uploadType)){
				 BufferedImage sourceImg = ImageIO.read(uploadFile.getInputStream());
				 if(sourceImg != null 
						 && (sourceImg.getHeight() > 220 || sourceImg.getWidth() > 305)){
					 return new ImageUploadResult(false,"图片尺寸大于305x220");
				 }
			}
			byte[] imgbytes = SimpleFileUtil.boBin(uploadFile.getInputStream());
			String fileExtension = SimpleFileUtil.detectFileStype(imgbytes);
			if (!cheackFileExtention(fileExtension)) {
				return new ImageUploadResult(false,"不支持的文件格式");
			}
			switch (uploadType) {
			case MEMBER_PHOTO:
				MemberPhoto memberPhoto = new MemberPhoto();
				memberPhoto.setMemberId(memberId);
				memberPhoto.setPhoto(imgbytes);
				if(memberDao.getMemberPhoto(memberId) != null){
					memberDao.updateMemberPhoto(memberPhoto);
				}else{
					memberDao.addMemberPhoto(memberPhoto);
				}
				break;
			case PRIZE_PHOTO:
				PrizePhoto prizePhoto  = new PrizePhoto();
				prizePhoto.setId(memberId);
				prizePhoto.setPhoto(imgbytes);
				prizeDao.updatePrizePhoto(prizePhoto);
			default:
				break;
			}
			result = new ImageUploadResult(true,"图片上传成功");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new ImageUploadResult(false,"图片上传失败,服务器故障");
			}
		}
		return result;
	}

	private boolean cheackFileExtention(String fileStyle) {
		boolean result = false;
		if (StringUtils.isNotBlank(fileStyle)) {
			fileStyle = fileStyle.toLowerCase();
			if (fileStyle.equals("gif") || fileStyle.equals("jpeg") || fileStyle.equals("jpg")
					|| fileStyle.equals("bmp") || fileStyle.equals("png")) {
				result = true;
			}
		}
		return result;
	}
}
