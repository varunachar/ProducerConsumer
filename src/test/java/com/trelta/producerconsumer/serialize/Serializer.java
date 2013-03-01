/**
 * 
 */
package com.trelta.producerconsumer.serialize;

/**
 * Class which defines the serialization strategy used for serializing objects of the type T.
 * @author Varun Achar
 */
public interface Serializer<T, V>
{
	/**
	 * Serializes an object of type T and produces an output of type V
	 * @param t The object to serialize
	 * @return The serialized data.
	 */
	V serialize(T t);
}
