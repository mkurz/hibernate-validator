/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.validator.internal.metadata.raw;

import static org.hibernate.validator.internal.util.CollectionHelper.newLinkedHashSet;

import java.lang.reflect.Executable;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;

/**
 * Contains constraint-related meta-data for one method parameter.
 *
 * @author Gunnar Morling
 * @author Guillaume Smet
 */
public class ConstrainedParameter extends AbstractConstrainedElement {

	private final Executable executable;
	private final Type type;
	private final int index;

	public ConstrainedParameter(ConfigurationSource source,
								Executable executable,
								Type type,
								int index) {
		this(
				source,
				executable,
				type,
				index,
				Collections.<MetaConstraint<?>>emptySet(),
				Collections.<MetaConstraint<?>>emptySet(),
				CascadingMetaDataBuilder.nonCascading()
		);
	}

	/**
	 * Creates a new parameter meta data object.
	 *
	 * @param source The source of meta data.
	 * @param  executable The executable of the represented method parameter.
	 * @param type the parameter type
	 * @param index the index of the parameter
	 * @param constraints The constraints of the represented method parameter, if
	 * any.
	 * @param typeArgumentConstraints Type arguments constraints, if any.
	 * @param cascadingMetaDataBuilder The cascaded validation metadata for this element and its container elements.
	 */
	public ConstrainedParameter(ConfigurationSource source,
								Executable executable,
								Type type,
								int index,
								Set<MetaConstraint<?>> constraints,
								Set<MetaConstraint<?>> typeArgumentConstraints,
								CascadingMetaDataBuilder cascadingMetaDataBuilder) {
		super(
				source,
				ConstrainedElementKind.PARAMETER,
				constraints,
				typeArgumentConstraints,
				cascadingMetaDataBuilder
		);

		this.executable = executable;
		this.type = type;
		this.index = index;
	}

	public Type getType() {
		return type;
	}

	public Executable getExecutable() {
		return executable;
	}

	public int getIndex() {
		return index;
	}

	/**
	 * Creates a new constrained parameter object by merging this and the given
	 * other parameter.
	 *
	 * @param other The parameter to merge.
	 *
	 * @return A merged parameter.
	 */
	public ConstrainedParameter merge(ConstrainedParameter other) {
		ConfigurationSource mergedSource = ConfigurationSource.max( source, other.source );

		Set<MetaConstraint<?>> mergedConstraints = newLinkedHashSet( constraints );
		mergedConstraints.addAll( other.constraints );

		Set<MetaConstraint<?>> mergedTypeArgumentConstraints = new LinkedHashSet<>( typeArgumentConstraints );
		mergedTypeArgumentConstraints.addAll( other.typeArgumentConstraints );

		CascadingMetaDataBuilder mergedCascadingMetaData = cascadingMetaDataBuilder.merge( other.cascadingMetaDataBuilder );

		return new ConstrainedParameter(
				mergedSource,
				executable,
				type,
				index,
				mergedConstraints,
				mergedTypeArgumentConstraints,
				mergedCascadingMetaData
		);
	}

	@Override
	public String toString() {
		//display short annotation type names
		StringBuilder sb = new StringBuilder();

		for ( MetaConstraint<?> oneConstraint : getConstraints() ) {
			sb.append( oneConstraint.getDescriptor().getAnnotation().annotationType().getSimpleName() );
			sb.append( ", " );
		}

		String constraintsAsString = sb.length() > 0 ? sb.substring( 0, sb.length() - 2 ) : sb.toString();

		return "ParameterMetaData [executable=" + executable + ", index=" + index + "], constraints=["
				+ constraintsAsString + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + index;
		result = prime * result + ( ( executable == null ) ? 0 : executable.hashCode() );
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}
		if ( !super.equals( obj ) ) {
			return false;
		}
		if ( getClass() != obj.getClass() ) {
			return false;
		}
		ConstrainedParameter other = (ConstrainedParameter) obj;
		if ( index != other.index ) {
			return false;
		}
		if ( executable == null ) {
			if ( other.executable != null ) {
				return false;
			}
		}
		else if ( !executable.equals( other.executable ) ) {
			return false;
		}
		return true;
	}
}
