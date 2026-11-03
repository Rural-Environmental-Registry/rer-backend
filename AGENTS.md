# Backend

## Stack
- Java 21, Spring Boot 3, Gradle
- PostgreSQL + PostGIS
- JasperReports (PDF generation)

## Comandos
```bash
# Build
./gradlew build
# Test
./gradlew test
# Run local
./gradlew bootRun
```

## Estrutura
```
src/main/java/...  # código
src/test/...       # testes
Dockerfile         # container
build.gradle       # dependências
```

## Convenções
- Java: PascalCase classes, camelCase métodos
- Commits: conventional commits (feat/fix/chore)
- Branches: develop → release/dev → release/qa → release/prd → main
