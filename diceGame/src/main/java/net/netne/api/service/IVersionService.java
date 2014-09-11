package net.netne.api.service;

import java.util.List;

import net.netne.common.pojo.VersionInfo;

public interface IVersionService {
	
	public VersionInfo checkVersion(String ver);
	
	public void addVersionInfo(VersionInfo versionInfo);
	
	public List<VersionInfo> getVersionInfoList();

}
