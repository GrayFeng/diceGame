package net.netne.api.service;

import net.netne.common.pojo.VersionInfo;

public interface IVersionService {
	
	public VersionInfo checkVersion(String ver);
	
	public void addVersionInfo(VersionInfo versionInfo);

}
