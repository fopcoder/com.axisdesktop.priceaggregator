package com.axisdesktop.priceaggregator.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table( name = "catalog_category" )
public class CatalogCategory {
	@Id
	@GeneratedValue
	private int id;

	@Column( nullable = false )
	private String name;

	// @Column( nullable = false )
	private String path;

	@Column( name = "idx_left" )
	private int idxLeft;

	@Column( name = "idx_right" )
	private int idxRight;

	@Column( updatable = false )
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar created;

	@Temporal( TemporalType.TIMESTAMP )
	private Calendar modified;

	@Column( name = "meta_title" )
	private String metaTitle;

	@Column( name = "meta_keywords" )
	private String metaKeywords;

	@Column( name = "meta_description" )
	private String metaDescription;

	private String description;

	@Column( name = "parent_id" )
	private int parentId;

	@ManyToMany( fetch = FetchType.LAZY )
	@JoinTable( name = "catalog_item", joinColumns = { @JoinColumn( name = "category_id", referencedColumnName = "id" ) }, inverseJoinColumns = { @JoinColumn( name = "item_id", referencedColumnName = "id" ) } )
	private List<CatalogCategoryItem> items = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		this.created = this.modified = Calendar.getInstance();
	}

	@PreUpdate
	public void preUpdate() {
		this.modified = Calendar.getInstance();
	}

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

	public int getIdxLeft() {
		return idxLeft;
	}

	public void setIdxLeft( int idx_left ) {
		this.idxLeft = idx_left;
	}

	public int getIdxRight() {
		return idxRight;
	}

	public void setIdxRight( int idx_right ) {
		this.idxRight = idx_right;
	}

	public String getPath() {
		return path;
	}

	public void setPath( String path ) {
		this.path = path;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated( Calendar created ) {
		this.created = created;
	}

	public Calendar getModified() {
		return modified;
	}

	public void setModified( Calendar modified ) {
		this.modified = modified;
	}

	public List<CatalogCategoryItem> getItems() {
		return items;
	}

	public void setItems( List<CatalogCategoryItem> items ) {
		this.items = items;
	}

	public String getMetaTitle() {
		return metaTitle;
	}

	public void setMetaTitle( String metaTitle ) {
		this.metaTitle = metaTitle;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords( String metaKeywords ) {
		this.metaKeywords = metaKeywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription( String metaDescription ) {
		this.metaDescription = metaDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId( int parentId ) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "CatalogCategory [id=" + id + ", name=" + name + ", path="
				+ path + ", idx_left=" + idxLeft + ", idx_right=" + idxRight
				+ ", created=" + created + ", modified=" + modified
				+ ", metaTitle=" + metaTitle + ", metaKeywords=" + metaKeywords
				+ ", metaDescription=" + metaDescription + ", description="
				+ description + ", parentId=" + parentId + "]";
	}

}
