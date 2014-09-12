package net.netne.api.service.impl;

import java.util.List;

import net.netne.api.dao.IVersionDao;
import net.netne.api.service.IVersionService;
import net.netne.common.pojo.VersionInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.primitives.Ints;

@Service
public class VersionServiceImpl implements IVersionService{
	
	@Autowired
	private IVersionDao dao;

	@Override
	public VersionInfo checkVersion(String verStr) {
		List<VersionInfo> versionList = dao.getVersionList();

        if (null == verStr || verStr.length() < 4) {
            return null;
        }

        String channel = verStr.substring(0, 3);
        String verNum = verStr.substring(3);

        for (VersionInfo versionInfo : versionList) {
            if (versionInfo.getChannel().equalsIgnoreCase(channel)) {
            	versionInfo.setUpgrade(versionInfo.getNewver() > Ints.tryParse(verNum));
                return versionInfo;
            }
        }
		return null;
	}

	@Override
	public void addVersionInfo(VersionInfo versionInfo) {
		dao.deleteVersionInfo(versionInfo.getChannel());
		dao.addVersionInfo(versionInfo);
	}

	@Override
	public List<VersionInfo> getVersionInfoList() {
		return dao.getVersionList();
	}

}
