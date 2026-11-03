package br.car.registration.i18n;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class TranslationCatalogService {

    public static final String DEFAULT_LOCALE = "en-us";
    private static final Set<String> SUPPORTED = Set.of("en-us", "pt-br", "es-es");

    private final ReportTranslationLoader reportTranslationLoader;

    public TranslationCatalogService(ReportTranslationLoader reportTranslationLoader) {
        this.reportTranslationLoader = reportTranslationLoader;
    }

    public String resolveLocale(String acceptLanguage, String explicitLocale) {
        if (explicitLocale != null && !explicitLocale.isBlank()) {
            return normalizeLocale(explicitLocale);
        }
        if (acceptLanguage == null || acceptLanguage.isBlank()) {
            return DEFAULT_LOCALE;
        }

        List<Locale.LanguageRange> ranges = Locale.LanguageRange.parse(acceptLanguage);
        for (Locale.LanguageRange range : ranges) {
            String normalized = normalizeLocale(range.getRange());
            if (SUPPORTED.contains(normalized)) {
                return normalized;
            }
        }
        return DEFAULT_LOCALE;
    }

    public String normalizeLocale(String raw) {
        if (raw == null || raw.isBlank()) {
            return DEFAULT_LOCALE;
        }
        String value = raw.trim().toLowerCase(Locale.ROOT).replace('_', '-');
        if (SUPPORTED.contains(value)) {
            return value;
        }
        if (value.startsWith("pt")) {
            return "pt-br";
        }
        if (value.startsWith("es")) {
            return "es-es";
        }
        if (value.startsWith("en")) {
            return "en-us";
        }
        return DEFAULT_LOCALE;
    }

    public Map<String, Object> getReportParameters(String locale) {
        return reportTranslationLoader.loadReportParameters(normalizeLocale(locale));
    }
}
