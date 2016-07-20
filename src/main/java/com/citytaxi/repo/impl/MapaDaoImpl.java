package com.citytaxi.repo.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.citytaxi.controller.domain.Mapa;
import com.citytaxi.repo.MapaDao;

@Repository
@Transactional
public class MapaDaoImpl implements MapaDao {
	
	@Autowired
	private EntityManager em;

	@Override
	public Mapa findById(Integer id) {
		return em.find(Mapa.class, id);
	}

	@Override
	public Mapa create(Mapa mapa) {
		em.persist(mapa);
		em.flush();
		return mapa;
	}

	@Override
	public void update(Mapa mapa) {
		em.merge(mapa);
		em.flush();
	}

	@Override
	public Mapa findByTime(Integer time) {
		try {
			TypedQuery<Mapa> query = em.createQuery("SELECT m FROM Mapa m WHERE m.tempo = :tempo", Mapa.class);
			query.setParameter("tempo", time);
			Mapa mapaResult = query.getSingleResult();
			return mapaResult;
		}catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Mapa> findAll() {
		TypedQuery<Mapa> query = em.createQuery("SELECT m FROM Mapa m", Mapa.class);
		List<Mapa> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public Mapa findLastMap() {
		try {
			TypedQuery<Mapa> query = em.createQuery("SELECT m FROM Mapa m WHERE m.tempo = (SELECT MAX(m2.tempo) FROM Mapa m2)", Mapa.class);
			Mapa result = query.getSingleResult();
			return result;
		}catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public void deleteAll() {
		Query query = em.createQuery("DELETE FROM Mapa m");
		query.executeUpdate();
	}

}
