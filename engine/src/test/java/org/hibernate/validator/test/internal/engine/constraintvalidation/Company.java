/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.validator.test.internal.engine.constraintvalidation;

import javax.validation.constraints.NotNull;

/**
 * @author Matthias Kurz
 */
public class Company {

	@NotNull // simple constraint, NOT an instance of HibernateConstraintValidator (SimpleConstraintTree)
	@SimpleHibernateConstraintValidatorConstraint // simple constraint, instance of HibernateConstraintValidator (SimpleConstraintTree)
	@ComposedHibernateConstraintValidatorConstraint // two composed constraints, both instances of HibernateConstraintValidator (ComposingConstraintTree)
	private String name;
}
