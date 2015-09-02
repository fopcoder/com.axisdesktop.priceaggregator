package com.axisdesktop.priceaggregator.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axisdesktop.priceaggregator.entity.Provider;
import com.axisdesktop.priceaggregator.repository.ProviderRepository;

@Service
public class ProviderServiceImpl implements ProviderService {

	@Autowired
	ProviderRepository providerRepository;

	@Override
	public List<Provider> list() {
		return providerRepository.findAll();
	}

	@Override
	@Transactional
	public Provider create( Provider provider ) {
		return providerRepository.save( provider );
	}

	@Override
	public Provider read( int id ) {
		return providerRepository.findOne( id );
	}

	@Override
	@Transactional
	public Provider update( Provider provider ) {
		if( providerRepository.exists( provider.getId() ) ) {
			return providerRepository.save( provider );
		}

		return null;
	}

	@Override
	@Transactional
	public Provider delete( int id ) {
		Provider prov = providerRepository.findOne( id );

		if( prov != null ) {
			providerRepository.delete( prov );
		}

		return prov;
	}

}
