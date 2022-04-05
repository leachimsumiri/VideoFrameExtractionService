package leachimsumiri.anyline.VideoFrameExtractionService;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws IOException {
		ImageKit imageKit = ImageKit.getInstance();
		Configuration config = Utils.getSystemConfig(Application.class);
		imageKit.setConfig(config);
		LOGGER.info("ImageKit initialization successful");

		SpringApplication.run(Application.class, args);
	}

}
