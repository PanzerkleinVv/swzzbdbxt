package com.gdin.dzzwsyb.swzzbdbxt.core.util;

import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public class HandleFile {

	public static void save(MultipartFile file, String saveName) throws IOException {
		File writeFile = new File(saveName);
		file.transferTo(writeFile);
	}
	
	public static void remove(String fileName) throws Exception {
		File file = new File(fileName);
		if (file.exists()) {
			if (!file.delete()) {
				throw new Exception("文件：" + fileName + "删除出错！");
			}
		}
	}

}
