package net.netne.api.service;

import net.netne.common.pojo.ImageUploadResult;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {

	public ImageUploadResult processupload(Integer memberId,MultipartFile file);

}
