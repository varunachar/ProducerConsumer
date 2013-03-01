/**
 * 
 */

package com.trelta.producerconsumer.object;

/**
 * Object used to store producer data into the database.
 * @author Varun Achar
 */
public class DatabaseObject
{
	private long	id;
	private String	clazz;
	private String	data;
	
	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}
	
	/**
	 * @return the clazz
	 */
	public String getClazz()
	{
		return clazz;
	}
	
	/**
	 * @param clazz The full name of the class who's data is represented by {@link #data}
	 */
	public void setClazz(String clazz)
	{
		this.clazz = clazz;
	}
	
	/**
	 * @return the data
	 */
	public String getData()
	{
		return data;
	}
	
	/**
	 * @param data the data of the class in serialized form.
	 */
	public void setData(String data)
	{
		this.data = data;
	}
}
