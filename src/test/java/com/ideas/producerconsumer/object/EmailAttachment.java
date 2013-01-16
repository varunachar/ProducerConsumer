package com.ideas.producerconsumer.object;

import java.io.File;

/**
 * Encapsulates the file object that needs to be attached to
 * an email
 * @author Varun Achar
 * @since 2.0
 * @version 1.0
 */
public class EmailAttachment
{

	private File file;
	private String name;
	private String	extension;

	/**
	 * The file that needs to be sent
	 * @return
	 */
	public File getFile()
	{
		return file;
	}

	/**
	 * The file that needs to be sent
	 * @return
	 */
	public void setFile(File file)
	{
		this.file = file;
	}

	/**
	 * The name of the file
	 * @param name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * The name of the file
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * The file extension.
	 * @return
	 */
	public String getExtension()
	{
		return extension;
	}

	/**
	 * The file extension.
	 * @return
	 */
	public void setExtension(String extension)
	{
		this.extension = extension;
	}
}
