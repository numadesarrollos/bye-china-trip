# Nuestro viaje a China 🐻🐰

App personal para el viaje de Borja y Esther a China (noviembre 2026).
Offline-first — todo funciona sin internet; sincroniza en background cuando hay conexión.

---

## Stack técnico

| Capa | Tecnología |
|------|------------|
| UI | Compose Multiplatform (Android + iOS) |
| Arquitectura | MVI — NDViewModel / NDScreen (nd-kpm-base) |
| Base de datos local | SQLDelight 2 |
| Backend / sync | Firebase (Firestore + Storage + Auth) — GitLive KMP SDK |
| DI | Koin 4 |
| Navegación | CMP Navigation Compose |
| Imágenes | Coil 3 |
| Fechas | kotlinx-datetime (China = UTC+8, sin DST) |
| Serialización | kotlinx-serialization (backup JSON + Firestore) |
| Kotlin | 2.3.0 · CMP 1.10.0 · AGP 8.13.2 · minSdk 26 |

---

## Arquitectura

```
:app:androidApp   ← Entry point Android
:app:shared       ← UI Compose + ViewModels (usa :core)
:core             ← Dominio: entidades, repositorios, casos de uso, SQLDelight
base/             ← nd-kpm-base (git submodule) — clases base MVI
```

### Reglas arquitecturales

- La UI **nunca** lee de Firebase directamente. Solo lee de SQLDelight.
- Toda escritura va primero a SQLDelight; el motor de sync la empuja a Firebase.
- Todo ViewModel extiende `NDViewModel<S,E,F>`.
- Todo caso de uso extiende `NDUseCase<P,R>`.
- Resultados siempre como `NDResult<T>`.

### Campos de sync obligatorios en todas las entidades

```kotlin
val updatedAt  : Long,     // ms Unix — resolución de conflictos LWW
val deleted    : Boolean,  // borrado lógico — nunca DELETE físico
val syncState  : String,   // "synced" | "pendingUpload"
val createdBy  : String,   // "bear" | "bun"
```

---

## Módulos

### `:core`
Lógica de negocio y acceso a datos. Sin dependencias de UI.
- Entidades del dominio
- Esquemas SQLDelight (`.sq`)
- Implementaciones de repositorios
- Casos de uso

### `:app:shared`
Todo lo que toca Compose. Depende de `:core`.
- Pantallas (extienden `NDScreen`)
- ViewModels (extienden `NDViewModel`)
- Tema (colores, tipografía, shapes)
- Componentes reutilizables

### `base/` (submodule)
Librería base KMP privada. Ver `AGENTS.md` para las reglas de uso.

---

## Diseño / mockups

Ver [`diseno/diseno-completo.html`](diseno/diseno-completo.html) — 14 pantallas, modo claro y oscuro.

Tokens visuales clave:

| Token | Color | Uso |
|-------|-------|-----|
| `--cinnabar` | `#C9402E` | Hilo rojo — acento principal |
| `--amber` | `#B07A43` | Borja 🐻 |
| `--blush` | `#D5808F` | Esther 🐰 |
| `--gold` | `#C2A05A` | Fechas especiales |
| `--paper` | `#FCF7F2` | Fondo claro |
| `--ink` | `#2B2521` | Texto sobre fondo claro |

Tipografías: **Fraunces** (títulos) · **Inter** (UI) · **Caveat** (toques personales).

---

## Cómo ejecutar

```bash
# Android
./gradlew :app:androidApp:assembleDebug

# iOS — abrir en Xcode
open app/iosApp/iosApp.xcodeproj
```

---

## Desarrollo

- **Fases y estado:** ver [`DEVLOG.md`](DEVLOG.md)
- **Reglas para agentes IA (Claude Code, Devin):** ver [`AGENTS.md`](AGENTS.md)
- **Decisiones cerradas:** stack y arquitectura NO se reabren salvo problema técnico bloqueante.
