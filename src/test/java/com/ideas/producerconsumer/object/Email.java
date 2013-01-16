package com.ideas.producerconsumer.object;

/**
 * Class that represents an Email message.
 * @author Varun Achar
 *
 */
public class Email
{
	private String from;
	private String[] to;
	private String[] cc;
	private String[] bcc;
	private EmailAttachment[] attachments;
	private String subject;
	private String message;
	
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
	 * @return the attachments
	 */
	public EmailAttachment[] getAttachments()
	{
		return attachments;
	}
	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(EmailAttachment[] attachments)
	{
		this.attachments = attachments;
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
}
