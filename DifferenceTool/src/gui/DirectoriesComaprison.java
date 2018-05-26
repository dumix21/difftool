package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class DirectoriesComaprison 
{
	File leftDir;
	File rightDir;
	TreeView<Object> leftTV;
	TreeView<Object> rightTV;
	
	String[] matchFiles = {"only in", "different", "identical"};
	HashMap<String, String> mapDiff = new HashMap<>();
	
	TreeItem<Object> leftRoot;
	TreeItem<Object> rightRoot;
	
	
	public TreeView<Object> getLeftTV() {
		return leftTV;
	}

	public void setLeftTV(TreeView<Object> leftTV) {
		this.leftTV = leftTV;
	}

	public TreeView<Object> getRightTV() {
		return rightTV;
	}

	public void setRightTV(TreeView<Object> rightTV) {
		this.rightTV = rightTV;
	}

	public File getLeftDir() {
		return leftDir;
	}

	public void setLeftDir(File leftDir) {
		this.leftDir = leftDir;
	}

	public File getRightDir() {
		return rightDir;
	}

	public void setRightDir(File rightDir) {
		this.rightDir = rightDir;
	}

	public DirectoriesComaprison(File dir1, File dir2, TreeView<Object> t1, TreeView<Object> t2) {
		leftDir = dir1;
		rightDir = dir2;
		leftTV = t1;
		leftRoot=leftTV.getRoot();
		rightTV = t2;
		rightRoot=rightTV.getRoot();
	}
	
	
	public void getDiff(File dirA, File dirB) throws IOException
	{
		File[] fileList1 = dirA.listFiles();
		File[] fileList2 = dirB.listFiles();
		
		Arrays.sort(fileList1);
		Arrays.sort(fileList2);
		HashMap<String, File> map1;
		
		/**
		 * Creating a map based on the shortest number of files from an directory
		 * The shortest map is used because it's faster to compare fewer file with more
		 * and set the not found ones with ONLY IN value
		 */
		if(fileList1.length < fileList2.length)
		{
			map1 = new HashMap<String, File>();
			for(int i=0;i<fileList1.length;i++)
			{
				map1.put(fileList1[i].getName(),fileList1[i]);
			}
			
			compareNow(fileList2, map1);
		}
		else
		{
			map1 = new HashMap<String, File>();
			for(int i=0;i<fileList2.length;i++)
			{
				map1.put(fileList2[i].getName(),fileList2[i]);
			}
			compareNow(fileList1, map1);
		}
	}
	
	public void compareNow(File[] fileArr, HashMap<String, File> map) throws IOException
	{
		for(int i=0;i<fileArr.length;i++)
		{
			String fName = fileArr[i].getName();
			File fComp = map.get(fName);
			map.remove(fName);
			/**
			 * First branch  - if the map has entries
			 * Second branch - if the map is empty
			 */
			if(fComp!=null)
			{
				/**
				 * In case of directory in directory, a new map will be created to store
				 * the files and values
				 */
				if(fComp.isDirectory())
				{
					getDiff(fileArr[i], fComp);
				}
				else
				{
					String cSum1 = checksum(fileArr[i]);
					String cSum2 = checksum(fComp);
					/**
					 * Checking if there are two file with same name and content
					 * Or if the content is different
					 */
					if(!cSum1.equals(cSum2))
					{
//						System.out.println(fileArr[i].getName()+"\t\t"+ "different");
						if(!mapDiff.containsKey(fileArr[i].getName())) {
							mapDiff.put(fileArr[i].getName(), matchFiles[1]);
						}
					}
					else
					{
//						System.out.println(fileArr[i].getName()+"\t\t"+"identical");
						if(!mapDiff.containsKey(fileArr[i].getName())) {
							mapDiff.put(fileArr[i].getName(), matchFiles[2]);
						}
					}
				}
			}
			else
			{
				/**
				 * Branch used when the map is empty
				 * 
				 * In case of founding a directory, will be traversed
				 */
				if(fileArr[i].isDirectory())
				{
					traverseDirectory(fileArr[i]);
					
				}
				else
				{
					/**
					 * All the files will be set with value ONLY IN
					 */
//					System.out.println(fileArr[i].getName()+"\t\t"+"only in "+fileArr[i].getParent());
					if(!mapDiff.containsKey(fileArr[i].getName())) {
						mapDiff.put(fileArr[i].getName(), matchFiles[0]+" "+fileArr[i].getParent());
					}
				}
			}
		}
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext())
		{
			String n = it.next();
			File fileFrmMap = map.get(n);
			map.remove(n);
			if(fileFrmMap.isDirectory())
			{
				traverseDirectory(fileFrmMap);
			}
			else
			{
//				System.out.println(fileFrmMap.getName() +"\t\t"+"only in "+ fileFrmMap.getParent());
				if(!mapDiff.containsKey(fileFrmMap.getName())) {
					mapDiff.put(fileFrmMap.getName(), matchFiles[0]+" "+fileFrmMap.getParent());
				}
			}
		}
	}
	
	public HashMap<String, String> getMapDiff() {
		return mapDiff;
	}

	public void traverseDirectory(File dir)
	{
		File[] list = dir.listFiles();
		for(int k=0;k<list.length;k++)
		{
			if(list[k].isDirectory())
			{
				traverseDirectory(list[k]);
			}
			else
			{
//				System.out.println(list[k].getName() +"\t\t"+"only in "+ list[k].getParent());
				if(!mapDiff.containsKey(list[k].getName())) {
					mapDiff.put(list[k].getName(), matchFiles[0]+" "+list[k].getParent());
				}
			}
		}
	}
	
	public String checksum(File file) 
	{
		try 
		{
		    InputStream fin = new FileInputStream(file);
		    java.security.MessageDigest md5er = MessageDigest.getInstance("MD5");
		    byte[] buffer = new byte[1024];
		    int read;
		    do 
		    {
		    	read = fin.read(buffer);
		    	if (read > 0)
		    		md5er.update(buffer, 0, read);
		    } while (read != -1);
		    fin.close();
		    byte[] digest = md5er.digest();
		    if (digest == null)
		      return null;
		    String strDigest = "0x";
		    for (int i = 0; i < digest.length; i++) 
		    {
		    	strDigest += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1).toUpperCase();
		    }
		    return strDigest;
		} 
		catch (Exception e) 
		{
		    return null;
		}
	}
}
