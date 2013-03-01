/**
 * 
 */
package com.trelta.producerconsumer.producer;

import com.trelta.producerconsumer.object.Email;
import com.trelta.producerconsumer.task.Task;

/**
 * @author Owner
 *
 */
public class EmailProducer implements Task<Email>
{

	private Email email;
	public void run()
	{
		System.out.println("Sending email from " + this.email.getFrom());
	}

	public Email get()
	{
		return this.email;
	}

	public void set(Email t)
	{
		this.email = t;

	}

}
