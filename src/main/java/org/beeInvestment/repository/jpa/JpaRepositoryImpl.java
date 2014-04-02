/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.beeInvestment.repository.jpa;

import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.beeInvestment.model.BaseEntity;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;
import org.springframework.stereotype.Repository;

@Repository
public class JpaRepositoryImpl<T extends BaseEntity> implements org.beeInvestment.repository.Repository<T> {

	public JpaRepositoryImpl(Class<T> type) {
		this.jpql = "SELECT DISTINCT item FROM " + type.getSimpleName()
				+ " item";
	}

	public void setTableName(String tableName) {
		this.jpql = "SELECT DISTINCT item FROM " + tableName
				+ " item";
	}

	@PersistenceContext
	private EntityManager em;

	private String jpql;

	public void setJpql(String jpql) {
		this.jpql = jpql;
	}

	/**
	 * Important: in the current version of this method, we load Owners with all
	 * their Pets and Visits while we do not need Visits at all and we only need
	 * one property from the Pet objects (the 'name' property). There are some
	 * ways to improve it such as: - creating a Ligtweight class (example here:
	 * https://community.jboss.org/wiki/LightweightClass) - Turning on
	 * lazy-loading and using {@link OpenSessionInViewFilter}
	 */


	public Collection<T> find(Map<String, Object> parameterMap) {
		String jpql=this.jpql;
		if(!parameterMap.isEmpty()){
			jpql+=" WHERE 1=1";
		}
		for(String key:parameterMap.keySet()){
			Object value=parameterMap.get(key);
			if(value instanceof String){
				jpql+=" AND item."+key+" LIKE :"+key;
			}else{
				jpql+=" AND item."+key+" = :"+key;
			}
		}
		Query query = this.em.createQuery(jpql);
		// query.setParameter("lastName", lastName + "%");
		for(String key:parameterMap.keySet()){
			Object value=parameterMap.get(key);
			if(value instanceof String){
				query.setParameter(key, value+"%");
			}else{
				query.setParameter(key, value);
			}
		}
		return query.getResultList();
	}


	public T findById(int id) {
		Query query = this.em.createQuery(jpql+" WHERE item.id =:id");
		query.setParameter("id", id);
		return (T) query.getSingleResult();
	}

	@Override
	public void save(T t) {
		if (t.getId() == null) {
			this.em.persist(t);
		} else {
			this.em.merge(t);
		}

	}

}
