/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.validator.internal.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides some methods for simplified collection instantiation.
 *
 * @author Gunnar Morling
 * @author Kevin Pollet &lt;kevin.pollet@serli.com&gt; (C) 2011 SERLI
 * @author Hardy Ferentschik
 */
public final class CollectionHelper {

	private CollectionHelper() {
	}

	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}

	public static <K, V> HashMap<K, V> newHashMap(int size) {
		return new HashMap<K, V>( size );
	}

	public static <K, V> HashMap<K, V> newHashMap(Map<K, V> map) {
		return new HashMap<K, V>( map );
	}

	public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
		return new ConcurrentHashMap<K, V>();
	}

	public static <T> LinkedHashSet<T> newLinkedHashSet() {
		return new LinkedHashSet<T>();
	}

	public static <T> LinkedHashSet<T> newLinkedHashSet(int size) {
		return new LinkedHashSet<T>( size );
	}

	public static <T> LinkedHashSet<T> newLinkedHashSet(Collection<? extends T> c) {
		return new LinkedHashSet<T>( c );
	}

	public static <T> LinkedHashSet<T> newLinkedHashSet(Collection<? extends T> s1, Collection<? extends T> s2) {
		LinkedHashSet<T> set = CollectionHelper.<T>newLinkedHashSet( s1 );
		set.addAll( s2 );
		return set;
	}

	public static <T> LinkedHashSet<T> newLinkedHashSet(Iterable<? extends T> iterable) {
		LinkedHashSet<T> set = newLinkedHashSet();
		for ( T t : iterable ) {
			set.add( t );
		}
		return set;
	}

	public static <T> ArrayList<T> newArrayList() {
		return new ArrayList<T>();
	}

	public static <T> ArrayList<T> newArrayList(int size) {
		return new ArrayList<T>( size );
	}

	public static <T> ArrayList<T> newArrayList(Iterable<T>... iterables) {
		ArrayList<T> resultList = newArrayList();
		for ( Iterable<T> oneIterable : iterables ) {
			for ( T oneElement : oneIterable ) {
				resultList.add( oneElement );
			}
		}
		return resultList;
	}

	public static <T> Set<T> asSet(T... ts) {
		return new LinkedHashSet<T>( Arrays.asList( ts ) );
	}

	/**
	 * Creates a map containing the given list's values partitioned by the given
	 * partitioner.
	 *
	 * @param <K> The key type of the resulting map.
	 * @param <V> The element type of the list to be partitioned.
	 * @param list The list to be partitioned.
	 * @param partitioner The partitioner to be used for determining the partitions.
	 *
	 * @return A map containing the given list's values partitioned by the given
	 *         partitioner.
	 */
	public static <K, V> Map<K, List<V>> partition(List<V> list, Partitioner<K, V> partitioner) {
		if ( list == null ) {
			return Collections.emptyMap();
		}

		Map<K, List<V>> theValue = newHashMap();

		for ( V v : list ) {
			K key = partitioner.getPartition( v );

			List<V> partition = theValue.get( key );
			if ( partition == null ) {
				partition = newArrayList();
				theValue.put( key, partition );
			}

			partition.add( v );
		}

		return theValue;
	}

	/**
	 * Creates a map containing the given set's values partitioned by the given
	 * partitioner.
	 *
	 * @param <K> The key type of the resulting map.
	 * @param <V> The element type of the set to be partitioned.
	 * @param set The set to be partitioned.
	 * @param partitioner The partitioner to be used for determining the partitions.
	 *
	 * @return A map containing the given set's values partitioned by the given
	 *         partitioner.
	 */
	public static <K, V> Map<K, Set<V>> partition(Set<V> set, Partitioner<K, V> partitioner) {
		if ( set == null ) {
			return Collections.emptyMap();
		}

		Map<K, Set<V>> theValue = newHashMap();

		for ( V v : set ) {
			K key = partitioner.getPartition( v );

			Set<V> partition = theValue.get( key );
			if ( partition == null ) {
				partition = newLinkedHashSet();
				theValue.put( key, partition );
			}

			partition.add( v );
		}

		return theValue;
	}

	public interface Partitioner<K, V> {
		K getPartition(V v);
	}
}
