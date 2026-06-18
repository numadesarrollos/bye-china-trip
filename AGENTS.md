# AGENTS.md — Instrucciones para agentes IA

Este fichero es leído por todos los agentes IA que trabajen en este repositorio
(Claude Code, Devin, Copilot Workspace, etc.). Define las reglas del proyecto que
**todos deben seguir** sin excepción.

---

## Contexto del proyecto

App personal Kotlin Multiplatform para el viaje de Borja 🐻 y Esther 🐰 a China
(noviembre 2026). Offline-first con sync Firebase. Ver `README.md` para el stack
completo y la arquitectura.

---

## Reglas de trabajo

### 1. Una fase a la vez
- Consultar siempre el estado de las fases en `README.md` (tabla de fases).
- No implementar código de una fase posterior sin haber cerrado la actual.
- Si se detecta algo que pertenece a otra fase, anotarlo en `DEVLOG.md` (sección "backlog") y continuar.

### 2. Librería base — usar siempre, no reinventar
- Todo ViewModel **extiende `NDViewModel<S, E, F>`** del submodule `base/presentation`.
- Toda pantalla Compose **usa `NDScreen`** del submodule como composable raíz.
- Todo caso de uso **extiende `NDUseCase<P, R>`**; los resultados son siempre `NDResult<T>`.
- Todo repositorio **implementa `NDRepository`**.
- Los dispatchers se inyectan siempre como `NDDispatcherProvider` (nunca `Dispatchers.IO` hardcodeado).
- Las versiones de Kotlin, CMP y AGP están **fijadas por el submodule** (`base/gradle/libs.versions.toml`).
  No actualizar estas versiones en el proyecto principal sin actualizar el submodule primero.

### 3. Antes de añadir código nuevo
- Buscar primero si ya existe una función, componente o utilidad que haga lo mismo.
- Reutilizar componentes Compose existentes en `:composeApp` antes de crear nuevos.
- No abstraer prematuramente: tres líneas similares no justifican un helper.

### 3. Fuente de verdad = SQLDelight local
- La UI **nunca** lee directamente de Firebase. Siempre lee de SQLDelight.
- Toda escritura va **primero a SQLDelight**; el motor de sync se encarga del push a Firebase.
- Nunca esperar a la red para confirmar una operación al usuario.

### 4. Campos de sync obligatorios
Toda entidad nueva debe tener:
```kotlin
val updatedAt: Long,    // timestamp Unix ms — para resolución de conflictos LWW
val deleted: Boolean,   // borrado lógico — nunca DELETE físico en tablas sincronizadas
val syncState: String,  // "synced" | "pendingUpload"
val createdBy: String,  // "bear" | "bun" — quién creó el registro
```

### 5. Secretos y seguridad
- **Nunca** hardcodear API keys, tokens, contraseñas ni localizadores en el código.
- `google-services.json` y `GoogleService-Info.plist` pueden estar en el repo (app personal),
  pero `serviceAccountKey.json`, ficheros `.env` y keystores **nunca**.
- No commitear `local.properties`.

### 6. Idioma
- **Código:** inglés (nombres de variables, funciones, clases, comentarios técnicos).
- **UI y strings de la app:** español.
- **Commits y documentación del proyecto:** español.

### 7. Commits
- Un commit por tarea/cambio cohesivo. No mezclar refactors con features.
- Formato del mensaje:
  ```
  tipo(alcance): descripción corta en español

  Cuerpo opcional si hay contexto relevante.
  ```
  Tipos: `feat`, `fix`, `refactor`, `docs`, `test`, `chore`.
  Ejemplo: `feat(itinerario): añadir agrupación por ciudad colapsable`
- No usar `--no-verify` ni saltarse hooks.
- Push al terminar cada fase o tarea completa, no commits intermedios rotos.

### 8. Diseño
- El fichero `diseno/diseno-completo.html` es la **especificación visual congelada**.
  Abrirlo en un navegador para ver colores, tipografías y componentes.
- En Compose: usar siempre los tokens del tema (`MaterialTheme.colorScheme.*`,
  `MaterialTheme.typography.*`). Nunca colores o tamaños hardcodeados.
- Componentes reutilizables viven en `:composeApp/ui/components/`.

### 9. iOS
- El proyecto compila y corre en **Android e iOS**. No añadir código que rompa iOS.
- Los `expect/actual` van en `:shared/platform/`. Implementar siempre los dos lados.
- Probar en simulador iOS antes de cerrar cualquier PR que toque código multiplataforma.

### 10. Qué NO hacer
- No reescribir código que funciona sin una razón técnica clara.
- No añadir dependencias nuevas sin justificarlo en el PR/commit.
- No crear ficheros de documentación intermedios (análisis, planes, decisiones) en el repo —
  esos van al `DEVLOG.md` o a la memoria del agente.
- No cambiar el stack ni reabrir decisiones ya cerradas (ver sección "Stack" en `README.md`).

---

## Protocolo de sesión (obligatorio para todos los agentes)

### Al EMPEZAR cualquier sesión o tarea

1. **Leer `DEVLOG.md`** — sección "🔜 Para el siguiente día":
   qué toca, qué quedó a medias, mejoras detectadas y backlog pendiente.
2. **Consultar el plan completo** en
   `C:\Users\borja\.claude\plans\c-users-borja-appdata-local-temp-plan-a-happy-key.md`
   para entender el contexto de la fase actual.
3. **Verificar la rama correcta** antes de escribir código:
   trabajar siempre en `fase/N-nombre` o `fix/descripcion`, nunca directamente en `main`
   salvo commits de documentación.

### Durante el trabajo

- Trabajar **una fase a la vez**; no saltar a la siguiente sin cerrar la actual.
- Si se detecta una mejora o tarea fuera del alcance actual: **anotarla en el backlog
  del `DEVLOG.md`** y continuar — no implementarla en ese momento.
- Antes de crear código nuevo, buscar si ya existe algo reutilizable en el repo.
- Commitear con frecuencia (por tarea/cambio cohesivo), **nunca commits rotos**.

### Al TERMINAR cualquier sesión o tarea

1. **Actualizar `DEVLOG.md`**:
   - Añadir entrada en "Registro por sesión" con fecha y resumen de qué se hizo.
   - Actualizar el estado de la fase en la tabla (⬜ → 🔧 en progreso → ✅ completada).
   - Reescribir "🔜 Para el siguiente día": qué queda, qué quedó a medias, backlog detectado.
2. **Hacer commit del DEVLOG** junto con el código de la sesión.
3. **Push y PR**:
   - Devin: abrir PR contra `main` con descripción de cambios.
   - Claude Code: push directo si es la rama de fase activa; PR si hay dudas.

---

## Estructura de ramas (propuesta, a seguir en Fase 1)

```
main          → código estable, siempre compila
fase/N-nombre → rama por fase (ej: fase/1-setup-kmp)
fix/descripcion
```
Los agentes abren PRs de sus ramas hacia `main`. No pushear directamente a `main`
salvo commits de documentación.
