package com.axisdesktop.priceaggregator.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table( name = "currency" )
public class Currency {
	@Id
	@GeneratedValue
	private int id;

	@Column( nullable = false )
	private String name;

	@Column( nullable = false )
	private double rate;

	@Column( nullable = false )
	private String mark;

	@Column( nullable = false )
	private int signs;

	@Column( nullable = false )
	private boolean is_default;

	@Column( updatable = false )
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar created;

	@Temporal( TemporalType.TIMESTAMP )
	private Calendar modified;

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

	public double getRate() {
		return rate;
	}

	public void setRate( double rate ) {
		this.rate = rate;
	}

	public String getMark() {
		return mark;
	}

	public void setMark( String mark ) {
		this.mark = mark;
	}

	public int getSigns() {
		return signs;
	}

	public void setSigns( int signs ) {
		this.signs = signs;
	}

	public boolean isIs_default() {
		return is_default;
	}

	public void setIs_default( boolean is_default ) {
		this.is_default = is_default;
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

}
