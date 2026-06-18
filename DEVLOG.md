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
| 1 | Setup KMP + Firebase + tema | 🔧 En progreso |
| 2 | Datos + dominio (SQLDelight) | ⬜ Pendiente |
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

## 🔜 Para el siguiente día (arrancar aquí)

**Próxima tarea: completar Fase 1 — activar Firebase y hacer hello-world en iOS.**

### ✅ Hecho en código
- ✅ Proyecto KMP limpio (sin :server, versiones correctas)
- ✅ nd-kpm-base como submodule + includeBuild
- ✅ libs.versions.toml con todas las dependencias
- ✅ Tema Compose (colores, tipografía, AppTheme)
- ✅ LoginScreen + LoginViewModel + AuthRepositoryImpl (GitLive Firebase)
- ✅ Koin DI completo (Android + iOS entry points)

### ⚠️ Pendiente manual (el usuario lo hace)
1. **Firebase console:** Crear proyecto → activar plan Blaze → poner alerta de presupuesto a 0 €.
2. **Firebase Auth:** Habilitar "Email/contraseña" como proveedor.
3. **Firebase Android:** Registrar app (package: `com.numadesarrollos.byechinaapp`) → descargar `google-services.json` → colocarlo en `app/androidApp/`.
4. **Descomentar** en `app/androidApp/build.gradle.kts`: `id("com.google.gms.google-services") version "4.4.2"`.
5. **Firebase iOS:** Registrar app (Bundle ID del iosApp) → descargar `GoogleService-Info.plist` → añadirlo al Xcode project (`app/iosApp/iosApp/`).
6. **Firebase iOS SDK:** Añadir `FirebaseCore` al Xcode project via Swift Package Manager:
   - En Xcode: File → Add Package Dependencies → `https://github.com/firebase/firebase-ios-sdk` → añadir `FirebaseAuth`.
7. **Fuentes Compose:** Descargar TTF de Google Fonts y colocar en `app/shared/src/commonMain/composeResources/font/`:
   - Fraunces (Regular, SemiBold, Bold)
   - Inter (Regular, Medium, SemiBold)
   - Caveat (Regular)
   Luego sustituir `FontFamily.Default` por `FontFamily(Font(Res.font.xxx))` en `Type.kt`.
8. **Crear usuario de prueba en Firebase** (Auth → Users → Add user) con email y contraseña.
9. **Compilar y ejecutar en iOS simulator** con Android Studio / Xcode — el login debería funcionar.

### Siguiente fase cuando esto compile: Fase 2 — Datos + dominio (SQLDelight)

**Backlog de mejoras detectadas:**
- Estado inicial del itinerario por ciudad: desplegar automáticamente la ciudad de "hoy", resto plegado.
- Categorías de gasto: fijas (las 6 del mockup) para simplificar.
- Confirmar fechas reales del viaje (mockup usa mayo como ejemplo; viaje real en noviembre 2026).
- Limpiar ficheros wizard sobrantes (Greeting.kt, GreetingUtil.kt, Platform.jvm.kt) cuando arranque Fase 2.
