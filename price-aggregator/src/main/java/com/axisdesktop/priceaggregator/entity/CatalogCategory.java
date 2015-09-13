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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table( name = "catalog_category" )
@NamedQueries( { @NamedQuery( name = "CatalogCategory.megamenu", query = "SELECT c FROM CatalogCategory c LEFT JOIN FETCH c.children WHERE c.parentId = 1 AND LENGTH(c.path) > 0 ORDER BY c.idxLeft" ) } )
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

	// @JoinColumn( name = "parent_id" )
	// @ManyToOne( fetch = FetchType.LAZY )
	// private CatalogCategory parent;

	@JsonIgnore
	@OneToMany( fetch = FetchType.LAZY )
	@JoinColumn( name = "parent_id" )
	private List<CatalogCategory> children = new ArrayList<>();

	@Column( name = "status_id" )
	private int statusId;

	@JsonIgnore
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

	public List<CatalogCategory> getChildren() {
		return children;
	}

	public void setChildren( List<CatalogCategory> children ) {
		this.children = children;
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

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId( int statusId ) {
		this.statusId = statusId;
	}

	@Override
	public String toString() {
		return "CatalogCategory [id=" + id + ", name=" + name + ", path=" + path + ", idxLeft=" + idxLeft
				+ ", idxRight=" + idxRight + ", created=" + created + ", modified=" + modified + ", metaTitle="
				+ metaTitle + ", metaKeywords=" + metaKeywords + ", metaDescription=" + metaDescription
				+ ", description=" + description + ", parentId=" + parentId + ", statusId=" + statusId + "]";
	}

}
