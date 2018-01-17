/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.userforms;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.obs.ComplexData;

/**
 * Please note that a corresponding table schema must be created in
 * liquibase.xml.
 */
// Uncomment 2 lines below if you want to make the UserForm class persistable,
// see also UserFormsDaoTest and liquibase.xml
@Entity(name = "userforms.UserForm")
@Table(name = "user_obs")
public class UserObs extends BaseOpenmrsData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4116259395297170811L;

	private static final Log log = LogFactory.getLog(UserObs.class);

	private static final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm");

	private static final DateFormat datetimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	@Id
	@GeneratedValue
	@Column(name = "user_obs_id")
	private Integer userObsId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "concept_id")
	private Concept concept;

	@ManyToOne
	@JoinColumn(name = "user_form_id")
	private UserForm userForm;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	@ManyToOne
	@JoinColumn(name = "user_obs_datetime")
	private Date userObsDatetime;

	@ManyToOne
	@JoinColumn(name = "user_obs_group_id")
	private UserObs userObsGroup;

	private Set<UserObs> userObsGroupMembers;

	@Basic
	@Column(name = "value_group_id")
	private Integer valueGroupId;

	@ManyToOne
	@JoinColumn(name = "value_coded")
	private Concept valueCoded;

	@ManyToOne
	@JoinColumn(name = "value_coded_name_id")
	private ConceptName valueCodedName;

	@Basic
	@Column(name = "value_datetime")
	private Date valueDatetime;

	@Basic
	@Column(name = "value_numeric")
	private Double valueNumeric;

	@Basic
	@Column(name = "value_modifier")
	private String valueModifier;

	@Basic
	@Column(name = "value_text")
	private String valueText;

	@Basic
	@Column(name = "value_complex")
	private String valueComplex;

	private transient ComplexData complexData;

	@Basic
	@Column(name = "comments")
	private String comment;

	@ManyToOne
	@JoinColumn(name = "previous_version")
	private UserObs previousVersion;

	/**
	 * @return the userObsId
	 */
	public Integer getUserFormId() {
		return userObsId;
	}

	/**
	 * @param userObsId
	 *            the userObsId to set
	 */
	public void setUserFormId(Integer userFormId) {
		this.userObsId = userFormId;
	}

	@Override
	public Integer getId() {
		return getUserFormId();
	}

	@Override
	public void setId(Integer id) {
		setUserFormId(id);
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the concept
	 */
	public Concept getConcept() {
		return concept;
	}

	/**
	 * @param concept
	 *            the concept to set
	 */
	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	/**
	 * @return the userForm
	 */
	public UserForm getUserForm() {
		return userForm;
	}

	/**
	 * @param userForm
	 *            the userForm to set
	 */
	public void setUserForm(UserForm userForm) {
		this.userForm = userForm;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the userObsDatetime
	 */
	public Date getUserObsDatetime() {
		return userObsDatetime;
	}

	/**
	 * @param userObsDatetime
	 *            the userObsDatetime to set
	 */
	public void setUserObsDatetime(Date userObsDatetime) {
		this.userObsDatetime = userObsDatetime;
	}

	/**
	 * @return the userObsGroup
	 */
	public UserObs getUserObsGroup() {
		return userObsGroup;
	}

	/**
	 * @param userObsGroup
	 *            the userObsGroup to set
	 */
	public void setUserObsGroup(UserObs userObsGroup) {
		this.userObsGroup = userObsGroup;
	}

	/**
	 * @return the userObsGroupMembers
	 */
	public Set<UserObs> getUserObsGroupMembers() {
		return userObsGroupMembers;
	}

	/**
	 * @param userObsGroupMembers
	 *            the userObsGroupMembers to set
	 */
	public void setUserObsGroupMembers(Set<UserObs> userObsGroupMembers) {
		this.userObsGroupMembers = userObsGroupMembers;
	}

	public void setValueAsString(String s) throws ParseException {
		if (log.isDebugEnabled())
			log.debug("getConcept() == " + getConcept());

		if (getConcept() != null && !StringUtils.isBlank(s)) {
			String abbrev = getConcept().getDatatype().getHl7Abbreviation();
			if (abbrev.equals("BIT")) {
				setValueBoolean(Boolean.valueOf(s));
			} else if (abbrev.equals("CWE")) {
				throw new RuntimeException("Not Yet Implemented");
			} else if (abbrev.equals("NM") || abbrev.equals("SN")) {
				setValueNumeric(Double.valueOf(s));
			} else if (abbrev.equals("DT")) {
				setValueDatetime(dateFormat.parse(s));
			} else if (abbrev.equals("TM")) {
				setValueDatetime(timeFormat.parse(s));
			} else if (abbrev.equals("TS")) {
				setValueDatetime(datetimeFormat.parse(s));
			} else if (abbrev.equals("ST")) {
				setValueText(s);
			} else {
				throw new RuntimeException("Don't know how to handle " + abbrev);
			}

		} else {
			throw new RuntimeException("concept is null for " + this);
		}
	}

	/**
	 * @return the valueGroupId
	 */
	public Integer getValueGroupId() {
		return valueGroupId;
	}

	/**
	 * @param valueGroupId
	 *            the valueGroupId to set
	 */
	public void setValueGroupId(Integer valueGroupId) {
		this.valueGroupId = valueGroupId;
	}

	/**
	 * Sets the value of this obs to the specified valueBoolean if this obs has
	 * a boolean concept.
	 * 
	 * @param valueBoolean
	 *            the boolean value matching the boolean coded concept to set to
	 */
	public void setValueBoolean(Boolean valueBoolean) {
		if (valueBoolean != null && getConcept() != null
				&& getConcept().getDatatype().isBoolean())
			setValueCoded(valueBoolean.booleanValue() ? Context
					.getConceptService().getTrueConcept() : Context
					.getConceptService().getFalseConcept());
		else if (valueBoolean == null)
			setValueCoded(null);
	}

	/**
	 * Coerces a value to a Boolean representation
	 * 
	 * @return Boolean representation of the obs value
	 * @should return true for value_numeric concepts if value is 1
	 * @should return false for value_numeric concepts if value is 0
	 * @should return null for value_numeric concepts if value is neither 1 nor
	 *         0
	 */
	public Boolean getValueAsBoolean() {

		if (getValueCoded() != null) {
			if (getValueCoded().equals(
					Context.getConceptService().getTrueConcept())) {
				return Boolean.TRUE;
			} else if (getValueCoded().equals(
					Context.getConceptService().getFalseConcept())) {
				return Boolean.FALSE;
			}
		} else if (getValueNumeric() != null) {
			if (getValueNumeric() == 1)
				return Boolean.TRUE;
			else if (getValueNumeric() == 0)
				return Boolean.FALSE;
		}
		// returning null is preferred to defaulting to false to support
		// validation of user input is from a form
		return null;
	}

	/**
	 * Returns the boolean value if the concept of this obs is of boolean
	 * datatype
	 * 
	 * @return true or false if value is set otherwise null
	 * @should return true if value coded answer concept is true concept
	 * @should return false if value coded answer concept is false concept
	 */
	public Boolean getValueBoolean() {
		if (getConcept() != null && valueCoded != null
				&& getConcept().getDatatype().isBoolean()) {
			Concept trueConcept = Context.getConceptService().getTrueConcept();
			return trueConcept != null
					&& valueCoded.getId().equals(trueConcept.getId());
		}

		return null;
	}

	/**
	 * @return the valueCoded
	 */
	public Concept getValueCoded() {
		return valueCoded;
	}

	/**
	 * @param valueCoded
	 *            the valueCoded to set
	 */
	public void setValueCoded(Concept valueCoded) {
		this.valueCoded = valueCoded;
	}

	/**
	 * @return the valueCodedName
	 */
	public ConceptName getValueCodedName() {
		return valueCodedName;
	}

	/**
	 * @param valueCodedName
	 *            the valueCodedName to set
	 */
	public void setValueCodedName(ConceptName valueCodedName) {
		this.valueCodedName = valueCodedName;
	}

	/**
	 * @return the valueDatetime
	 */
	public Date getValueDatetime() {
		return valueDatetime;
	}

	/**
	 * @param valueDatetime
	 *            the valueDatetime to set
	 */
	public void setValueDatetime(Date valueDatetime) {
		this.valueDatetime = valueDatetime;
	}

	/**
	 * @return the valueNumeric
	 */
	public Double getValueNumeric() {
		return valueNumeric;
	}

	/**
	 * @param valueNumeric
	 *            the valueNumeric to set
	 */
	public void setValueNumeric(Double valueNumeric) {
		this.valueNumeric = valueNumeric;
	}

	/**
	 * @return the valueModifier
	 */
	public String getValueModifier() {
		return valueModifier;
	}

	/**
	 * @param valueModifier
	 *            the valueModifier to set
	 */
	public void setValueModifier(String valueModifier) {
		this.valueModifier = valueModifier;
	}

	/**
	 * @return the valueText
	 */
	public String getValueText() {
		return valueText;
	}

	/**
	 * @param valueText
	 *            the valueText to set
	 */
	public void setValueText(String valueText) {
		this.valueText = valueText;
	}

	/**
	 * @return the valueComplex
	 */
	public String getValueComplex() {
		return valueComplex;
	}

	/**
	 * @param valueComplex
	 *            the valueComplex to set
	 */
	public void setValueComplex(String valueComplex) {
		this.valueComplex = valueComplex;
	}

	/**
	 * @return the complexData
	 */
	public ComplexData getComplexData() {
		return complexData;
	}

	/**
	 * @param complexData
	 *            the complexData to set
	 */
	public void setComplexData(ComplexData complexData) {
		this.complexData = complexData;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the previousVersion
	 */
	public UserObs getPreviousVersion() {
		return previousVersion;
	}

	/**
	 * @param previousVersion
	 *            the previousVersion to set
	 */
	public void setPreviousVersion(UserObs previousVersion) {
		this.previousVersion = previousVersion;
	}

	public Boolean hasPreviousVersion() {
		return getPreviousVersion() != null;
	}

	@Override
	public String toString() {
		if (userObsId == null)
			return "user obs id is null";
		return "UserObs #" + userObsId.toString();
	}
}
