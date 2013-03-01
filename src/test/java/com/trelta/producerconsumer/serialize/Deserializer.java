/**
 * 
 */
package com.trelta.producerconsumer.serialize;


/**
 * Deserialize data of type V and and form an object of type T.
 * @author Varun Achar
 * @param T The type of object
 * @param V The type of data
 */
public interface Deserializer<T, V>
{
	/**
	 * Deserize the data from v and form an object of Type T
	 * @param v The data
	 * @return The object that represents the data.
	 */
	T deserialize(V v);
}
