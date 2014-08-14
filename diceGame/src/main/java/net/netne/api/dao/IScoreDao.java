package net.netne.api.dao;

import java.util.Map;

import net.netne.common.annotation.MyBatisRepository;

import org.springframework.stereotype.Repository;

@Repository
@MyBatisRepository
public interface IScoreDao {
	
	public void freezeScore(Map<String,Object> paramMap);
	
	public void unFreezeScore(Map<String,Object> paramMap);
	
	public void addScore(Map<String,Object> paramMap);
}
