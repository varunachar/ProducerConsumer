/**
 * 
 */

package com.trelta.producerconsumer.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Varun Achar
 *
 */
public class File
{
	private static final java.io.File	file		= new java.io.File("C:\\Users\\Owner\\Desktop\\rejected.txt");
	private final static ReadWriteLock	lock		= new ReentrantReadWriteLock();
	private final static Lock			writeLock	= lock.writeLock();
	private final static Lock			readLock	= lock.readLock();
	
	/**
	 * Write the bytes to disk.
	 * @param bytes
	 */
	public static void write(byte[] bytes)
	{
		RandomAccessFile raf = null;
		writeLock.lock();
		try
		{
			raf = new RandomAccessFile(file, "rw");
			raf.seek(raf.length());
			raf.write(bytes);
			raf.write(Character.LINE_SEPARATOR);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(raf != null)
				{
					raf.close();
				}
			}
			catch(IOException e1)
			{
				e1.printStackTrace();
			}
			writeLock.unlock();
		}
	}
	
	/**
	 * Read lines from the file.
	 * @param number The number of lines to read.
	 * @return Each line as a index of the array.
	 */
	public static String[] readLine(int number)
	{
		RandomAccessFile raf = null;
		readLock.lock();
		String[] lines = new String[number];
		try
		{
			raf = new RandomAccessFile(file, "rw");
			for(int i = 0; i < number; i++)
			{
				lines[i] = raf.readLine();
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(raf != null)
				{
					raf.close();
				}
			}
			catch(IOException e1)
			{
				e1.printStackTrace();
			}
			readLock.unlock();
		}
		return lines;
	}
	
	/**
	 * Read number of lines passed in parameter and delete them from the file.
	 * @param number The number of lines to read.
	 * @return Each line in a index of the array.
	 */
	public static String[] readAndDelete(int number)
	{
		if(number == 0)
		{
			return new String[0];
		}
		RandomAccessFile raf = null;
		writeLock.lock();
		String[] lines = new String[number];
		try
		{
			raf = new RandomAccessFile(file, "rw");
			if(raf.length() == 0)
			{
				return new String[0];
			}
			long readPointerStart = 0;
			long readPointerEnd = 0;
			readPointerStart = raf.getFilePointer();
			for(int i = 0; i < number; i++)
			{
				String readLine = raf.readLine();
				if(readLine == null)
				{
					break;
				}
				lines[i] = readLine;
			}
			readPointerEnd = raf.getFilePointer();
			byte[] buf = new byte[1024];
			int n;
			while(-1 != (n = raf.read(buf)))
			{
				raf.seek(readPointerStart);
				raf.write(buf, 0, n);
				readPointerEnd += n;
				readPointerStart += n;
				raf.seek(readPointerEnd);
			}
			raf.setLength(readPointerStart);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(raf != null)
				{
					raf.close();
				}
			}
			catch(IOException e1)
			{
				e1.printStackTrace();
			}
			writeLock.unlock();
			
		}
		return lines;
		
	}
	
}
