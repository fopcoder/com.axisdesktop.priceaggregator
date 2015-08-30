package com.axisdesktop.priceaggregator.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table( name = "catalog_category_item" )
public class CatalogCategoryItem {

	@Id
	@GeneratedValue
	private int id;

	@Column( nullable = false )
	private String name;

	@Column( insertable = false, updatable = false )
	private Date created;

	@Column
	private Date modified;

	@Column( nullable = false )
	private String path;

	@ManyToMany( mappedBy = "items", fetch = FetchType.LAZY )
	private List<CatalogCategory> categories;

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

	public Date getCreated() {
		return created;
	}

	public void setCreated( Date created ) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified( Date modified ) {
		this.modified = modified;
	}

	public String getPath() {
		return path;
	}

	public void setPath( String path ) {
		this.path = path;
	}

	public List<CatalogCategory> getCategories() {
		return categories;
	}

	public void setCategories( List<CatalogCategory> categories ) {
		this.categories = categories;
	}

}
