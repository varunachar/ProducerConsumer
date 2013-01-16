/**
 * 
 */

package com.ideas.producerconsumer.object;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

/**
 * Email attachment who's data is written to disk and the path to the data is store here instead.
 * @author Varun Achar
 *
 */
public class SerializableEmailAttachment
{
	
	private static final String	TEMP_DIRECTORY	= "c:" + File.separator + "Users" + File.separator + "Owner" + File.separator + "Documents" + File.separator + "Work" + File.separator + "Ideas" + File.separator + "temp";
	/**
	 * Path to the file stored on disk.
	 */
	private String				path;
	private String				name;
	private String				extension;
	
	public SerializableEmailAttachment()
	{
		
	}
	
	/**
	 * Create a serialized version of {@link EmailAttachment}. The file is written to disk and the {@link #path}
	 * to the file is stored instead.
	 * @param attachment The attachment to serialize.
	 * @throws NullPointerException If attachment or file in attacment is null
	 * @throws IOException If file cannot be written to disk.
	 */
	public SerializableEmailAttachment(EmailAttachment attachment) throws NullPointerException, IOException
	{
		if((attachment == null) || (attachment.getFile() == null))
		{
			throw new NullPointerException("Attachment sent without file");
		}
		this.name = attachment.getName();
		this.extension = attachment.getExtension();
		String path = TEMP_DIRECTORY + File.separator + UUID.randomUUID().toString();
		File tempDirectory = new File(path);
		this.path = path + File.separator + attachment.getFile().getName();
		FileUtils.copyFileToDirectory(attachment.getFile(), tempDirectory);
	}
	
	/**
	 * @return the fileName
	 */
	public String getPath()
	{
		return path;
	}
	
	/**
	 * @param fileName the fileName to set
	 */
	public void setPath(String fileName)
	{
		this.path = fileName;
	}
	
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return the extension
	 */
	public String getExtension()
	{
		return extension;
	}
	
	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension)
	{
		this.extension = extension;
	}
	
	/**
	 * 
	 * @return The file pointed by the {@link #path}
	 * @throws FileNotFoundException File at the path was not found
	 */
	public File getFile() throws FileNotFoundException
	{
		File file = new File(this.path);
		if(file.exists())
		{
			return file;
		}
		throw new FileNotFoundException("File at the path " + this.path + " was not found");
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SerializableEmailAttachment [path=").append(path).append(", name=").append(name).append(", extension=").append(extension).append("]");
		return builder.toString();
	}
}
