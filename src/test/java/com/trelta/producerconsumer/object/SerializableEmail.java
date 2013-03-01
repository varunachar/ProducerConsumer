/**
 * 
 */

package com.trelta.producerconsumer.object;

import java.io.IOException;
import java.util.Arrays;

/**
 * A converter class used for serializing emails. Since we can't serialize files into string objects that can be
 * inserted into database, the files attached in the {@link Email} are written to disk and the path is stored here.
 * @author Varun Achar
 *
 */
public class SerializableEmail
{
	private String							from;
	private String[]						to;
	private String[]						cc;
	private String[]						bcc;
	private String							subject;
	private String							message;
	private SerializableEmailAttachment[]	attachments;
	
	public SerializableEmail()
	{
		
	}
	
	/**
	 * Convert the email into a serializable email
	 * @param email The email to convert
	 * @throws NullPointerException If Email is null
	 * @throws IOException If file cannot be written to disk
	 */
	public SerializableEmail(Email email) throws IOException
	{
		if(email == null)
		{
			throw new NullPointerException("Email is null. What's the point of serializing?");
		}
		this.from = email.getFrom();
		this.to = email.getTo();
		this.cc = email.getCc();
		this.bcc = email.getBcc();
		this.subject = email.getSubject();
		this.message = email.getMessage();
		EmailAttachment[] emailAttachments = email.getAttachments();
		if((emailAttachments != null) && (emailAttachments.length > 0))
		{
			this.attachments = new SerializableEmailAttachment[emailAttachments.length];
			for(int i = 0; i < attachments.length; i++)
			{
				EmailAttachment attachment = emailAttachments[i];
				if((attachment != null) && (attachment.getFile()!=null))
				{
					attachments[i] = new SerializableEmailAttachment(attachment);
				}
			}
		}
	}
	
	/**
	 * @return the from
	 */
	public String getFrom()
	{
		return from;
	}
	
	/**
	 * @param from the from to set
	 */
	public void setFrom(String from)
	{
		this.from = from;
	}
	
	/**
	 * @return the to
	 */
	public String[] getTo()
	{
		return to;
	}
	
	/**
	 * @param to the to to set
	 */
	public void setTo(String[] to)
	{
		this.to = to;
	}
	
	/**
	 * @return the cc
	 */
	public String[] getCc()
	{
		return cc;
	}
	
	/**
	 * @param cc the cc to set
	 */
	public void setCc(String[] cc)
	{
		this.cc = cc;
	}
	
	/**
	 * @return the bcc
	 */
	public String[] getBcc()
	{
		return bcc;
	}
	
	/**
	 * @param bcc the bcc to set
	 */
	public void setBcc(String[] bcc)
	{
		this.bcc = bcc;
	}
	
	/**
	 * @return the subject
	 */
	public String getSubject()
	{
		return subject;
	}
	
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	/**
	 * @return the attachments
	 */
	public SerializableEmailAttachment[] getAttachments()
	{
		return attachments;
	}
	
	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(SerializableEmailAttachment[] attachments)
	{
		this.attachments = attachments;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SerializableEmail [from=").append(from).append(", to=").append(Arrays.toString(to)).append(", cc=").append(Arrays.toString(cc)).append(", bcc=").append(Arrays.toString(bcc)).append(", subject=").append(subject)
		.append(", message=").append(message).append(", attachments=").append(Arrays.toString(attachments)).append("]");
		return builder.toString();
	}
	
}
