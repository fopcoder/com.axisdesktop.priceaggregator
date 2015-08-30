package com.axisdesktop.priceaggregator.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	private String path;

	@Column( nullable = false )
	private int idx_left;

	@Column( nullable = false )
	private int idx_right;

	@Column( updatable = false, insertable = false )
	private Date created;

	@Column
	private Date modified;

	@ManyToMany( fetch = FetchType.LAZY )
	@JoinTable( name = "catalog_item", joinColumns = { @JoinColumn( name = "category_id", referencedColumnName = "id" ) }, inverseJoinColumns = { @JoinColumn( name = "item_id", referencedColumnName = "id" ) } )
	private List<CatalogCategoryItem> items = new ArrayList<>();

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

	public String getPath() {
		return path;
	}

	public void setPath( String path ) {
		this.path = path;
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

	public List<CatalogCategoryItem> getItems() {
		return items;
	}

	public void setItems( List<CatalogCategoryItem> items ) {
		this.items = items;
	}

}
