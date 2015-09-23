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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table( name = "catalog_category" )
@NamedQueries( {
		@NamedQuery( name = "CatalogCategory.megamenu", query = "SELECT c FROM CatalogCategory c ORDER BY c.idxLeft" ),
		// @NamedQuery( name = "CatalogCategory.megamenu", query =
		// "SELECT c FROM CatalogCategory c LEFT JOIN FETCH c.children WHERE c.parentId = 1 AND LENGTH(c.path) > 0 ORDER BY c.idxLeft"
		// ),
		@NamedQuery( name = "CatalogCategory.listAsTree", query = "SELECT c FROM CatalogCategory c ORDER BY c.idxLeft" ),
		@NamedQuery( name = "CatalogCategory.listAsTreeWithLevel", query = "SELECT c, (SELECT COUNT(*) FROM CatalogCategory c1 WHERE c.idxLeft > c1.idxLeft AND c1.idxRight > c.idxRight) FROM CatalogCategory c ORDER BY c.idxLeft" ),
		@NamedQuery( name = "CatalogCategory.getParent", query = "SELECT c FROM CatalogCategory c WHERE c.idxLeft < :idxLeft AND c.idxRight > :idxRight ORDER BY c.idxLeft DESC" ) } )
public class CatalogCategory {
	@Id
	@GeneratedValue
	private int id;

	@Column( nullable = false )
	private String name;

	private String path = "";

	private String uri = "";

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
	private String metaTitle = "";

	@Column( name = "meta_keywords" )
	private String metaKeywords = "";

	@Column( name = "meta_description" )
	private String metaDescription = "";

	private String description = "";

	@Column( name = "status_id", nullable = false )
	private int statusId;

	@JsonIgnore
	@ManyToMany( fetch = FetchType.LAZY )
	@JoinTable( name = "catalog_category_item", joinColumns = { @JoinColumn( name = "category_id", referencedColumnName = "id" ) }, inverseJoinColumns = { @JoinColumn( name = "item_id", referencedColumnName = "id" ) } )
	private List<CatalogItem> items = new ArrayList<>();

	@Transient
	private int parentId;

	@Transient
	private long level;

	@Transient
	private List<CatalogCategory> children = new ArrayList<>();

	public CatalogCategory() {
	}

	public CatalogCategory( CatalogCategory cat ) {
		this.id = cat.getId();
		this.name = cat.getName();
		this.path = cat.getPath();
		this.uri = cat.getUri();
		this.idxLeft = cat.getIdxLeft();
		this.idxRight = cat.getIdxRight();
		this.created = cat.getCreated();
		this.modified = cat.getModified();
		this.metaTitle = cat.getMetaTitle();
		this.metaKeywords = cat.getMetaKeywords();
		this.metaDescription = cat.getMetaDescription();
		this.description = cat.getDescription();
		this.parentId = cat.getParentId();
		this.statusId = cat.getStatusId();
		this.items = cat.getItems();
		this.level = cat.getLevel();
	}

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

	public List<CatalogItem> getItems() {
		return items;
	}

	public void setItems( List<CatalogItem> items ) {
		this.items = items;
	}

	// public List<CatalogCategory> getChildren() {
	// return children;
	// }
	//
	// public void setChildren( List<CatalogCategory> children ) {
	// this.children = children;
	// }

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

	public String getUri() {
		return uri;
	}

	public void setUri( String uri ) {
		this.uri = uri;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel( long level ) {
		this.level = level;
	}

	public List<CatalogCategory> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return "CatalogCategory [id=" + id + ", name=" + name + ", path=" + path + ", uri=" + uri + ", idxLeft="
				+ idxLeft + ", idxRight=" + idxRight + ", created=" + created + ", modified=" + modified
				+ ", metaTitle=" + metaTitle + ", metaKeywords=" + metaKeywords + ", metaDescription="
				+ metaDescription + ", description=" + description + ", parentId=" + parentId + ", statusId="
				+ statusId + "]";
	}

}
