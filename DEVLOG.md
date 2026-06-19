# 📒 Bitácora de desarrollo — App "Nuestro viaje a China" 🐻🐰

> Registro vivo del desarrollo. Se actualiza al final de cada sesión.
> **Al empezar una sesión nueva: leer primero la sección "🔜 Para el siguiente día".**

**Enlaces clave:**
- Plan completo por fases: `C:\Users\borja\.claude\plans\c-users-borja-appdata-local-temp-plan-a-happy-key.md`
- Diseño / mockups (fichero único): [`diseno/diseno-completo.html`](diseno/diseno-completo.html) — 14 pantallas, claro + oscuro

**Stack:** Kotlin Multiplatform + Compose Multiplatform (Android + iOS) · SQLDelight · Firebase (Firestore + Storage + Auth) · Koin · Decompose/Voyager · kotlinx-datetime · Coil 3.

---

## 🗺️ Estado de las fases

| Fase | Descripción | Estado |
|------|-------------|--------|
| 0 | Ampliar mockup y cerrar diseño | ✅ Completada |
| 1 | Setup KMP + Firebase + tema | 🔧 En progreso (Android validado; iOS bloqueado por Mac corporativo) |
| 2 | Datos + dominio (SQLDelight) | 🔧 En progreso (esquema+repos hechos; casos de uso se añaden por fase) |
| 3 | Itinerario (Trip + Lugares + Días) | ⬜ Pendiente |
| 4 | Ubicaciones y actividades por día | ⬜ Pendiente |
| 5 | Documentos (picker + visor offline) | ⬜ Pendiente |
| 6 | Vuelos y Trenes (billete offline) | ⬜ Pendiente |
| 7 | Extras (gastos, conversor, preparativos, alojamientos, diario, recordatorios, frases, emergencias) | ⬜ Pendiente |
| 8 | Sincronización Firebase | ⬜ Pendiente |
| 9 | Backup, pulido y pruebas | ⬜ Pendiente |

---

## 📆 Registro por sesión

### 2026-06-18 — Revisión de plan y diseño (Fase 0)
**Hecho:**
- Revisado el plan v3 y el prototipo HTML del usuario; propuestas de mejora aceptadas (orden por criticidad, extras nuevos, backup local, diseño HTML→tema Compose).
- Decisiones cerradas: KMP Android+iOS, Firebase desde el inicio, viaje nov 2026. Usuario tiene Mac y Android Studio.
- Plan completo reescrito y aprobado (9 fases + Fase 0 de diseño).
- **Fase 0 completada:** creado `diseno/mockups-fase0.html` con todas las pantallas restantes (Trenes, Detalle de día, Visor de documento, Gastos+Conversor, Preparativos, Alojamientos, Diario, hub "Más", Login, Sync+Backup) + modo oscuro.
- Ajustes de diseño pedidos por el usuario y aplicados:
  - Gastos: fila de **filtros por categoría** (Todos/Transporte/Hoteles/Comida/Entradas/Compras).
  - Itinerario: vista **agrupada por ciudad, colapsable** (cabecera pliega; fila de día con `›` navega al Detalle de día).
- Memoria del proyecto guardada.
- Creados `AGENTS.md` (protocolo unificado para todos los agentes) y este `DEVLOG.md`.
- **Diseños unificados** en un único fichero `diseno/diseno-completo.html` (14 pantallas).
- Repo publicado en GitHub: https://github.com/numadesarrollos/bye-china-trip
- Creados `README.md` y `AGENTS.md` para compatibilidad total con Devin y Claude Code.
- **Analizada librería base `nd-kpm-base`** (github.com/numadesarrollos/nd-kpm-base):
  se integrará como **git submodule** en `base/`. Aporta: NDViewModel (MVI), NDScreen,
  NDUseCase, NDResult, NDFailure, NDDispatcherProvider, NDRepository.
  Versiones fijadas: Kotlin 2.3.0 · CMP 1.10.0 · AGP 8.13.2 · Koin 4.0 · minSdk 26.
- Plan actualizado con submodule, arquitectura revisada y versiones fijadas.

**Decisiones de diseño tomadas (validar si se reabren):**
- Bottom nav de 5 pestañas: Hoy · Itinerario · Vuelos · Trenes · Más (extras dentro de "Más").
- Detalle de día con cabecera de color (roja normal, dorada en fechas especiales).
- Navegación al Detalle de día: cabecera de ciudad pliega/despliega; fila de día navega.

---

---

### 2026-06-18 — Fase 1: Setup KMP, tema y scaffolding Auth (sesión 2)
**Hecho:**
- **Proyecto KMP revisado:** Android Studio generó el proyecto con versiones incorrectas y módulo `:server` no deseado.
  - Eliminado `:server` (Ktor) — backend es Firebase, no es necesario.
  - Versiones corregidas: Kotlin 2.3.0, AGP 8.13.2, CMP 1.10.0, minSdk 26, material3 1.10.0-alpha05.
  - JVM target unificado a JVM_17 (alineado con nd-kpm-base).
- **Submodule nd-kpm-base** añadido en `base/` — `includeBuild("base")` en settings.gradle.kts.
  - `:core` depende de `base:domain` (NDUseCase, NDResult, NDRepository, NDDispatcherProvider).
  - `:app:shared` depende de `base:presentation` (NDViewModel, NDScreen, Koin Compose).
- **libs.versions.toml** completo: SQLDelight 2.0.2, Koin 4.0.0, GitLive Firebase 2.1.0, Coil 3, kotlinx-datetime 0.6.1, serialization 1.7.3.
- **README.md** restaurado con documentación del proyecto (el wizard lo sobreescribió).
- **.gitignore** mejorado: protección para keystores, serviceAccountKey.json, .env.
- **Tema Compose creado:**
  - `Color.kt`: Cinnabar, Amber, Blush, Gold, Paper, Ink + LightColorScheme/DarkColorScheme completos + CustomColors.
  - `Type.kt`: AppTypography completa M3 con Fraunces/Inter/Caveat (TODOs para poner TTF en `composeResources/font/`).
  - `Theme.kt`: `AppTheme` composable + `MaterialTheme.customColors` extension.
- **Scaffolding Firebase Auth completo (mayor riesgo técnico):**
  - `:core` — AuthUser, AuthFailure, AuthRepository/Impl (Firebase.auth), LoginUseCase, CoreModule (Koin).
  - `:app:shared` — LoginContract (State/Event/Effect), LoginViewModel (extiende NDViewModel), LoginScreen (NDScreen + koinViewModel).
  - DI — AppModule con `viewModelOf<LoginViewModel>`, lista `allModules` para iOS y Android.
  - Android: ByEChinaApplication (startKoin + androidContext), MainActivity extiende NDActivity.
  - iOS: KoinHelper.kt (initKoin para Swift), MainViewController usa NDViewController, iOSApp.swift con FirebaseApp.configure().

**Rama:** `fase/1-setup-kmp` — pendiente de push a GitHub tras completar Firebase.

---

### 2026-06-19 — Fase 2: esquema completo + validación de build real (sesión 3)
**Hecho:**
- **Fase 2 completa (alcance: todas las entidades del plan):**
  - 15 tablas SQLDelight con campos de sync completos: Trip, Place, Day, Location, Activity,
    Document, Flight, TrainTrip, Expense, CurrencyRate, ChecklistItem, Accommodation,
    DiaryEntry, Reminder, Phrase.
  - Modelos de dominio tipados (kotlinx-datetime, enums `SyncState`/`Owner`) agrupados por área:
    `itinerary/`, `documents/`, `transport/`, `extras/`.
  - 15 repositorios (interfaz + impl) implementando `NDRepository`, mapeo Entity↔dominio.
  - `DatabaseDriverFactory` expect/actual (Android/iOS) + módulos Koin (`DbModule`,
    `AndroidDbModule`, `IosDbModule`) registrando las 15 implementaciones.
  - **Decisión de alcance:** no se crean casos de uso por entidad todavía (serían especulativos
    sin la UI real) — se añaden en cada fase 3-7 según haga falta.
- **Validación real de compilación (primera vez que se compila el proyecto completo):**
  - `./gradlew :app:androidApp:assembleDebug` → ✅ **BUILD SUCCESSFUL**, incluye
    `processDebugGoogleServices` (confirma que `google-services.json` está bien colocado).
  - `compileKotlinIosSimulatorArm64` en `:core` y `:app:shared` → ✅ compila — valida que TODO
    el código Kotlin (incluidos los `actual` de iOS: DatabaseDriverFactory.ios.kt, KoinHelper.kt,
    MainViewController.kt) es correcto, sin necesitar Mac. El linkado final del framework
    (`linkDebugFrameworkIosSimulatorArm64`) se SKIPPEA en Windows — eso sí requiere macOS.
  - **Conclusión importante:** dado que el código Kotlin compila limpio en ambas plataformas,
    el error `PhaseScriptExecution failed` del Mac corporativo (sesión anterior) es muy
    probablemente un problema del entorno Xcode/Mac (permisos, PATH, sandboxing), **no** un bug
    de código. Revisar entorno cuando se retome el build iOS.
- **Bug real encontrado en `nd-kpm-base` (no en nuestro código):**
  `NDResult.runCatching`/`suspendRunCatching` (en `base/domain/.../NDResult.kt`) llaman a
  `NDFailure.Unknown(...)`, una clase que no existe. Bloqueaba la compilación de cualquier
  código que use ese patrón (recomendado en la propia documentación de la librería).
  **Fix aplicado en disco** (añadida `NDFailure.Unknown`) pero **NO comiteado ni pusheado**
  al repo `nd-kpm-base` — el sistema de permisos bloqueó el push directo a `main` de un repo
  externo. **Pendiente: decidir cómo aplicar este fix de forma permanente** (ver checklist).
- **Otros fixes de build encontrados al compilar de verdad:**
  - Faltaba `org.jetbrains.kotlin.android` explícito en `androidApp` (y su `apply false` en
    el build.gradle.kts raíz) — sin él, el bloque `kotlin { compilerOptions {} }` no resolvía.
  - `:core` declaraba target `jvm()` sin actual de `DatabaseDriverFactory` — eliminado (no hace
    falta desktop, solo Android+iOS).
  - Firebase GitLive necesita el Firebase BoM de Android para resolver versiones de sus
    dependencias transitivas — añadido vía `androidMainImplementation(platform(...))` en el
    `dependencies{}` de nivel superior (el `platform()` dentro de `kotlin{sourceSets{}}` está
    deprecado/falla en KMP).
  - nd-kpm-base declara Koin como `implementation` (no `api`) — no llega transitivamente a
    `:app:shared` ni `:app:androidApp`; añadidas las dependencias de Koin directamente ahí.
  - Submodule `base/` necesita su propio `local.properties` con `sdk.dir` (es un build Gradle
    separado vía `includeBuild`, no hereda el del proyecto raíz). No se commitea (está en
    `.gitignore` del submodule).

---

## 🔜 Para el siguiente día (arrancar aquí)

**Próxima tarea: validar hello-world Auth en Android y, cuando haya Mac, en iOS.**

### ✅ Resuelto: bug de nd-kpm-base
Fix de `NDFailure.Unknown` comiteado y pusheado a `main` de `nd-kpm-base` (commit `3a4590c`,
autorizado explícitamente por el usuario). Puntero del submodule actualizado en este repo
(commit `10e8084`). Si se clona el proyecto en otra máquina, `git submodule update --remote base`
(o un clone con `--recurse-submodules`) ya trae el fix.

### Checklist de arranque

**Android — se puede hacer ahora:**
- [x] Colocar `google-services.json` en `app/androidApp/` ✅
- [ ] Sync Gradle en Android Studio
- [ ] Ejecutar en dispositivo/emulador Android — debe abrir LoginScreen
- [ ] Crear usuario de prueba en Firebase (Auth → Users → Add user)
- [ ] Probar login con ese usuario → si navega (sin crash) la Auth funciona ✅

**iOS — requiere Mac:**
- [x] Colocar `GoogleService-Info.plist` en `app/iosApp/iosApp/` ✅
- [x] Abrir `app/iosApp/iosApp.xcodeproj` en Xcode ✅
- [x] File → Add Package Dependencies → `firebase-ios-sdk` → `FirebaseAuth` añadido ✅ (commit `3f2c378`)
- [ ] **⚠️ BLOQUEADO — el Mac usado es corporativo (Grupo Santander) con restricciones que impiden compilar.**
  Error: `Command PhaseScriptExecution failed with a nonzero exit code` en el script
  "Compile Kotlin Framework" (`./gradlew :app:shared:embedAndSignAppleFrameworkForXcode`).
  No es un bug de código — son restricciones del equipo corporativo. **Decisión del usuario:**
  seguir con el resto del desarrollo (Fase 2+) en paralelo y retomar el build iOS cuando encuentre
  otra forma de probar (Mac personal u otra vía). No dar por resuelto hasta confirmación expresa.
- [ ] Compilar en simulador iOS → probar login ✅

**Mientras el build iOS está bloqueado: avanzar con Fase 2 (no depende de Mac). Cerrar Fase 1 del todo cuando se pueda probar iOS.**

### Fuentes custom (puede hacerse en cualquier momento, no bloquea nada)
Descargar TTF de Google Fonts y colocar en `app/shared/src/commonMain/composeResources/font/`:
- Fraunces: Regular, SemiBold, Bold — https://fonts.google.com/specimen/Fraunces
- Inter: Regular, Medium, SemiBold — https://fonts.google.com/specimen/Inter
- Caveat: Regular — https://fonts.google.com/specimen/Caveat

Luego en `Type.kt` sustituir `FontFamily.Default` por `FontFamily(Font(Res.font.xxx))`.

### Backlog detectado
- Limpiar ficheros wizard sobrantes (Greeting.kt, GreetingUtil.kt, Platform.jvm.kt) — arranque Fase 2.
- Estado inicial del itinerario: desplegar automáticamente la ciudad de "hoy", resto plegado.
- Categorías de gasto: fijas (las 6 del mockup).
- Confirmar fechas reales del viaje (mockup usa mayo; viaje real noviembre 2026).
