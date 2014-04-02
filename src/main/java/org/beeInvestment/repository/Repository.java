package org.beeInvestment.repository;

import java.util.Collection;
import java.util.Map;

import org.beeInvestment.model.BaseEntity;

public interface Repository<T extends BaseEntity> {

	void save(T t);

	T findById(int id);

	Collection<T> find(Map<String, Object> parameterMap);

}