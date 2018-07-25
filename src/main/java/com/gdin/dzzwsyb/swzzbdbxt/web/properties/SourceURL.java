package com.gdin.dzzwsyb.swzzbdbxt.web.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:properties/sourceURL.properties")
public class SourceURL {
	@Value("${fileURL}")
	public String FILE_URL;

}
