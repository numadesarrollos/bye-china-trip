# 🇨🇳 Nuestro viaje a China — Borja 🐻 & Esther 🐰

App personal y **offline-first** para el viaje de Borja y Esther a China (noviembre 2026).
Centraliza itinerario, lugares, documentos/billetes, vuelos, trenes y utilidades de viaje,
con sincronización entre 2 móviles vía Firebase.

> El concepto visual es el **hilo rojo del destino (红线)**: la leyenda china que une a dos
> personas con un hilo invisible. En la app es la columna del itinerario; 🐻 y 🐰 firman
> cada plan que añaden.

---

## Stack tecnológico

| Capa | Tecnología |
|------|-----------|
| Lenguaje / UI | **Kotlin Multiplatform + Compose Multiplatform** (Android + iOS) |
| BD local (fuente de verdad) | **SQLDelight** |
| Sincronización | **Firebase** — Firestore + Cloud Storage + Auth (GitLive `dev.gitlive`) |
| Navegación | Decompose / Voyager |
| DI | Koin |
| Asincronía | Coroutines + Flow (StateFlow) |
| Fechas | kotlinx-datetime (China = UTC+8, sin DST) |
| Serialización | kotlinx-serialization |
| Imágenes | Coil 3 |
| PDF / ficheros | expect/actual: PdfRenderer (Android) / PDFKit (iOS) |
| Notificaciones | expect/actual: WorkManager (Android) / UNUserNotificationCenter (iOS) |

---

## Arquitectura de módulos

```
:composeApp          → UI Compose Multiplatform, pantallas, navegación, tema
:shared
   ├── domain        → modelos, casos de uso, interfaces de repositorio
   ├── data          → repositorios, SQLDelight, gestor de ficheros, backup JSON
   ├── sync          → motor Firebase (push/pull, LWW, cola de ficheros)
   └── platform      → expect/actual (picker, visor PDF, notificaciones, rutas)
:androidApp          → entry point Android
:iosApp              → entry point iOS (host SwiftUI + Compose)
```

**Patrón:** MVVM/MVI con `StateFlow`.
**Fuente de verdad:** SQLDelight local. La UI siempre lee de local (funciona sin red).
El motor de sync replica en segundo plano contra Firebase cuando hay conexión.

**Campos de sync en TODAS las entidades:** `updatedAt`, `deleted` (borrado lógico),
`syncState` (synced / pendingUpload), `createdBy` (bear / bun).
**Resolución de conflictos:** last-write-wins por `updatedAt`.

---

## Modelo de datos (resumen)

**Core:** `Trip`, `Place`, `Day`, `Location`, `Activity`, `Document`, `Flight`, `TrainTrip`

**Extras:** `Expense`, `CurrencyRate`, `ChecklistItem`, `Accommodation`, `DiaryEntry`, `Reminder`, `Phrase`

Jerarquía: `Trip` → `Place` → (por `Day`) → `Location` + `Activity` + `Document`;
en paralelo del `Trip`: `Flight`, `TrainTrip`, `Expense`, `ChecklistItem`, `Accommodation`, `DiaryEntry`, `Reminder`.

---

## Diseño

La especificación visual está congelada en **`diseno/diseno-completo.html`** (14 pantallas,
modo claro y oscuro). Ábrelo en un navegador para ver el sistema completo.

Tokens de diseño:
- `--cinnabar` `#C9402E` — hilo rojo / acción principal
- `--amber` `#B07A43` — 🐻 Borja
- `--blush` `#D5808F` — 🐰 Esther
- `--gold` `#C2A05A` — fechas especiales (aniversario)
- `--paper` `#FCF7F2` — fondo (papel de arroz)
- `--ink` `#2B2521` — texto

Tipografías: **Fraunces** (títulos) · **Inter** (interfaz) · **Caveat** (toques personales)

En Compose: `ColorScheme` claro/oscuro + `Typography` + componentes reutilizables (Card, Hero, chips 🐻/🐰, timeline hilo rojo, bottom nav).

---

## Fases de desarrollo

| Fase | Descripción | Estado |
|------|-------------|--------|
| 0 | Diseño completo (mockups HTML) | ✅ Completada |
| 1 | Setup KMP + Compose + Firebase Auth + tema Compose | ⏳ Siguiente |
| 2 | Datos: SQLDelight schema + repositorios + dominio | ⬜ |
| 3 | Itinerario: Trip, Lugares, Días (CRUD + navegación) | ⬜ |
| 4 | Ubicaciones y actividades por día | ⬜ |
| 5 | Documentos: picker + almacenamiento local + visor PDF offline | ⬜ |
| 6 | Vuelos y Trenes: CRUD + billete offline | ⬜ |
| 7 | Extras: Gastos, Conversor, Preparativos, Alojamientos, Diario, Recordatorios, Frases, Emergencias | ⬜ |
| 8 | Sincronización Firebase: push/pull, LWW, cola de ficheros | ⬜ |
| 9 | Backup JSON, pulido UI, pruebas Android + iOS | ⬜ |

**Hito MVP (Fase 6):** itinerario + documentos + vuelos + trenes, todo offline en cada móvil.
**Fecha límite:** octubre 2026 (viaje en noviembre 2026).

---

## Setup del entorno de desarrollo

### Requisitos
- **Android Studio** (última versión estable) + JDK 17+
- **Xcode 15+** en Mac (obligatorio para compilar iOS)
- **Kotlin Multiplatform Plugin** para Android Studio
- Cuenta en [Firebase Console](https://console.firebase.google.com) con acceso al proyecto

### Clonar y abrir
```bash
git clone https://github.com/numadesarrollos/bye-china-trip.git
cd bye-china-trip
```
Abrir en Android Studio → `Open` → seleccionar la carpeta raíz.

> ⚠️ El proyecto KMP todavía no existe (se crea en la Fase 1). Esta sección se completará
> cuando esté el `build.gradle.kts` raíz.

### Variables de entorno y secretos
- `local.properties` (ignorado por git) → ruta del SDK de Android.
- `google-services.json` (Android) y `GoogleService-Info.plist` (iOS) → config de Firebase.
  Solicitarlos al administrador del proyecto.
- **Nunca** commitear `serviceAccountKey.json`, ficheros `.env` ni keystores.

---

## Convenciones de código

Ver **`AGENTS.md`** para las reglas que deben seguir todos los agentes y colaboradores.

---

## Contexto especial: China 🇨🇳

Firebase (Google) está **bloqueado en China** sin VPN. La sincronización en vivo requiere
una VPN de terceros. Sin VPN, todo funciona en local y los cambios se sincronizan al recuperar
red. Por eso el principio de diseño es **offline-first**: la app es 100% funcional sin internet.
