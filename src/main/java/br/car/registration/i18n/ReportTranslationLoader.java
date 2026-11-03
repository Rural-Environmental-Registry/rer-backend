package br.car.registration.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ReportTranslationLoader {

    private static final String BASE_PATH = "reports/i18n/";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Map<String, Object>> cache = new ConcurrentHashMap<>();

    public Map<String, Object> loadReportParameters(String locale) {
        return cache.computeIfAbsent(locale, this::readLocaleFile);
    }

    private Map<String, Object> readLocaleFile(String locale) {
        String path = BASE_PATH + locale + "/report_params.json";
        ClassPathResource resource = new ClassPathResource(path);

        if (!resource.exists()) {
            if (!TranslationCatalogService.DEFAULT_LOCALE.equals(locale)) {
                return loadReportParameters(TranslationCatalogService.DEFAULT_LOCALE);
            }
            return Collections.emptyMap();
        }

        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException ex) {
            throw new IllegalStateException("Falha ao carregar traduções de relatório: " + path, ex);
        }
    }
}
