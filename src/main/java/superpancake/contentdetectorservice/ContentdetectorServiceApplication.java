package superpancake.contentdetectorservice;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;

@SpringBootApplication
@RestController
@Configuration
public class ContentdetectorServiceApplication implements ErrorController {

	private static Log LOGGER = LogFactory.getLog(ContentdetectorServiceApplication.class);
	
	private TikaConfig tikaConfig;

	private static final Metrics METRICS = new Metrics();

	public static void main(String[] args) {
		SpringApplication.run(ContentdetectorServiceApplication.class, args);
	}

	public ContentdetectorServiceApplication(TikaConfig tikaConfig) {
		this.tikaConfig = tikaConfig;
	}

	public static class ContentTypeResponse {
		public String mediaType;
	}

	public static class Metrics {

		private Long volume;

		private Long uploads;

		public Long getUploads() {
			return uploads;
		}

		public Long getVolume() {
			return volume;
		}
	}

	@PostMapping(path = "/content", produces = "application/json; charset=UTF-8", consumes = "application/octet-stream")
	public ContentTypeResponse getContentType(@RequestBody byte[] payload) {
		final long started = System.currentTimeMillis();
		try {
			MediaType mediaType = tikaConfig.getDetector().detect(TikaInputStream.get(payload), new Metadata());

			ContentTypeResponse r = new ContentTypeResponse();
			r.mediaType = mediaType.toString();

			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(String.format("detected '%s' (%d bytes)", r.mediaType, payload.length));
			}
			
			return r;
		} catch (IOException ioException) {
			throw new HttpServerErrorException(
					HttpStatus.INTERNAL_SERVER_ERROR,
					String.format("Tika detection failed: %s", ExceptionUtils.getRootCauseMessage(ioException)));
		} finally {
			METRICS.volume += payload.length / 1024 / 1024;
			METRICS.uploads++;
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(String.format("Processing took %dms", System.currentTimeMillis() - started));
			}
		}
	}

	@GetMapping("/metrics")
	public Metrics getMetrics() {
		return METRICS;
	}


	private static final String ERROR_PATH = "/error";

	@RequestMapping(value = ERROR_PATH)
	public String error() {
		return "Error handling";
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
}
