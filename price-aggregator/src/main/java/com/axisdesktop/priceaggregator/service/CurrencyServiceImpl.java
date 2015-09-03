package com.axisdesktop.priceaggregator.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axisdesktop.priceaggregator.entity.Currency;
import com.axisdesktop.priceaggregator.repository.CurrencyRepository;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepository;

	@Override
	public List<Currency> list() {
		return currencyRepository.findAll();
	}

	@Override
	@Transactional
	public Currency create( Currency provider ) {
		return currencyRepository.save( provider );
	}

	@Override
	public Currency read( int id ) {
		return currencyRepository.findOne( id );
	}

	@Override
	@Transactional
	public Currency update( Currency provider ) {
		if( currencyRepository.exists( provider.getId() ) ) {
			return currencyRepository.save( provider );
		}
		return null;
	}

	@Override
	@Transactional
	public Currency delete( int id ) {
		Currency cur = currencyRepository.findOne( id );

		if( cur != null ) {
			currencyRepository.delete( cur );
		}

		return cur;
	}

}
