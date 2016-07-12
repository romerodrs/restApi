package com.api.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.api.ws.entity.OozieJobEntity;
/**
 * Created by DLRR
 */
@Transactional
public interface OozieJobDao extends CrudRepository<OozieJobEntity, Long> {
	
	public OozieJobEntity findByjobId(long jobId);
	public OozieJobEntity findByoozieJobId(String oozieJobId);
	public List<OozieJobEntity> findAll();

}
