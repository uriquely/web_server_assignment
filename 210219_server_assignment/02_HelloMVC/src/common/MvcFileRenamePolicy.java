package common;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MvcFileRenamePolicy implements FileRenamePolicy {

	@Override
	public File rename(File oldFile) {
		File newFile = null;
		
		do {
			//파일명 새로 부여 20210216_135645123_123.jpg
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_");
			DecimalFormat df = new DecimalFormat("000"); //30 -> 030
			
			//확장자가져오기
			String ext = "";
			String oldName = oldFile.getName(); //abc.jpg
			int dot = oldName.lastIndexOf(".");
			if(dot > -1)
				ext = oldName.substring(dot);//.jpg
			
			//새로운 파일명
			String newName = 
					sdf.format(new Date()) + df.format(Math.random() * 999) + ext;
			
			//새로운 파일객체
			//java.io.File.File(String parent, String child)
			newFile = new File(oldFile.getParent(), newName);
			System.out.println("newFile@MvcFileRenamePolicy = " + newFile);
			
		} while(!createNewFile(newFile));
		
		return newFile;
	}
	
	/**
	 * 해당경로에 파일이 존재하지 않으면, 파일을 생성하고 true를 리턴
	 * 해당경로에 파일이 존재하면, 파일을 생성하지 않고, IOException을 유발, false를 리턴
	 * 
	 * @param f
	 * @return
	 */
	private boolean createNewFile(File f) {
		try {
			return f.createNewFile();
		} catch (IOException ignored) {
			return false;
		}
	}

}
