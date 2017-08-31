package org.hibernate.validator.referenceguide.chapter12.propertypath;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.Valid;

public class Building {

	@Valid
	private Set<Apartment> apartments = new LinkedHashSet<Apartment>();

	public Set<Apartment> getApartments() {
		return apartments;
	}

	public void setApartments(Set<Apartment> apartments) {
		this.apartments = apartments;
	}
}
