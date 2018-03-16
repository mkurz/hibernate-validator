/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.validator.test.internal.engine.constraintvalidation;

import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;

public class HibernateConstraintValidatorTwo implements HibernateConstraintValidator<ComposedHibernateConstraintValidatorConstraint, String> {

	@Override
	public void initialize(ConstraintDescriptor<ComposedHibernateConstraintValidatorConstraint> constraintDescriptor,
			HibernateConstraintValidatorInitializationContext initializationContext) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return true;
	}
}
