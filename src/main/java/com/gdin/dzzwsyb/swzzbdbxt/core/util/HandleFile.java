package com.gdin.dzzwsyb.swzzbdbxt.core.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.gdin.dzzwsyb.swzzbdbxt.web.properties.SourceURL;

@Component
public class HandleFile {
	
	private static SourceURL sourceURL;
	
	@Autowired(required = true)
    public HandleFile(@Qualifier("sourceURL") SourceURL sourceURL) {
		HandleFile.sourceURL = sourceURL;
    }

	public static void save(MultipartFile file, String saveName) throws IOException {
		File dir = new File(sourceURL.FILE_URL.substring(0, sourceURL.FILE_URL.length() - 1));
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		File writeFile = new File(sourceURL.FILE_URL + saveName);
		file.transferTo(writeFile);
	}
	
	public static void remove(String fileName) throws Exception {
		File file = new File(sourceURL.FILE_URL + fileName);
		if (file.exists()) {
			if (!file.delete()) {
				throw new Exception("文件：" + fileName + "删除出错！");
			}
		}
	}
	
	public static void download(String fileName, OutputStream os) {
		if (fileName != null) {
			File filepath = new File(sourceURL.FILE_URL + fileName);
			if (filepath.exists()) {
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(filepath);
					bis = new BufferedInputStream(fis);
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

}
