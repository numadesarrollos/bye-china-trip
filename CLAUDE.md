# CLAUDE.md — App "Nuestro viaje a China" 🐻🐰

App personal y **offline-first** para el viaje de Borja 🐻 y Esther 🐰 a China (**noviembre 2026**),
con sincronización entre 2 móviles. Concepto visual: el **hilo rojo del destino (红线)** como columna
del itinerario y firma 🐻/🐰 en cada plan.

## ⚠️ Al empezar cualquier sesión
El protocolo completo de sesión (inicio, durante y cierre) está en **`AGENTS.md`** — sección
"Protocolo de sesión". Seguirlo siempre, igual que Devin.

## Stack (decidido, no re-preguntar)
Kotlin Multiplatform + Compose Multiplatform (**Android + iOS**) · SQLDelight (BD local, fuente de
verdad) · Firebase: Firestore + Cloud Storage + Auth, vía GitLive `dev.gitlive` (sync, plan Blaze con
alerta a 0 €) · Koin (DI) · Decompose/Voyager (navegación) · Coroutines/Flow · kotlinx-datetime
(China = UTC+8) · kotlinx-serialization · Coil 3. El usuario tiene **Mac y Android Studio**.

## Arquitectura
`:composeApp` (UI, navegación, tema) · `:shared` (domain / data / sync / platform expect-actual) ·
`:androidApp` · `:iosApp`. Patrón MVVM/MVI con `StateFlow`. La UI siempre lee de SQLDelight local
(funciona sin red); el sync replica contra Firebase en segundo plano. Campos de sync en todas las
entidades: `updatedAt`, `deleted`, `syncState`; conflictos last-write-wins.

## Diseño
El mockup HTML es la **especificación visual congelada** → se traduce a tema Compose Material 3
(ColorScheme claro/oscuro + Typography Fraunces/Inter/Caveat + componentes reutilizables).
Fichero único de diseño: [`diseno/diseno-completo.html`](diseno/diseno-completo.html) (14 pantallas, claro + oscuro).

## Convenciones
- Idioma de UI y de comunicación con el usuario: **español**.
- Trabajar **una fase a la vez**; no saltar de fase sin cerrar la anterior.
- Antes de añadir código nuevo, reutilizar componentes/utilidades ya existentes.
