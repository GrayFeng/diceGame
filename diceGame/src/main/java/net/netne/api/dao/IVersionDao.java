package net.netne.api.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.netne.common.annotation.MyBatisRepository;
import net.netne.common.pojo.VersionInfo;

@Repository
@MyBatisRepository
public interface IVersionDao {
	
	public List<VersionInfo> getVersionList();
	
	public void addVersionInfo(VersionInfo versionInfo);
	
	public void deleteVersionInfo(String channelId);
	
}
