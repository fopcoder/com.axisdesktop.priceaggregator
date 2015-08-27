package com.axisdesktop.priceaggregator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "catalog_category" )
public class CatalogCategory {
	@Id
	@GeneratedValue
	private int id;

	@Column( nullable = false )
	private String name;

	@Column( nullable = false )
	private int idx_left;

	@Column( nullable = false )
	private int idx_right;

	public int getId() {
		return id;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public int getIdx_left() {
		return idx_left;
	}

	public void setIdx_left( int idx_left ) {
		this.idx_left = idx_left;
	}

	public int getIdx_right() {
		return idx_right;
	}

	public void setIdx_right( int idx_right ) {
		this.idx_right = idx_right;
	}

}
