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
| 1 | Setup KMP + Firebase + tema | ⏳ Siguiente |
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

## 🔜 Para el siguiente día (arrancar aquí)

**Próxima fase: Fase 1 — Setup KMP + librería base (submodule) + Firebase + tema.**

Checklist antes de programar:
- [ ] Usuario crea proyecto KMP en Android Studio (plantilla Compose Multiplatform) en la carpeta raíz del repo.
- [ ] Añadir submodule: `git submodule add https://github.com/numadesarrollos/nd-kpm-base.git base`
- [ ] Conectar en `settings.gradle.kts` con `includeBuild("base")`
- [ ] Sincronizar `libs.versions.toml` con versiones del submodule: Kotlin 2.3.0 · CMP 1.10.0 · AGP 8.13.2 · Koin 4.0 · minSdk 26
- [ ] Añadir al `libs.versions.toml`: SQLDelight, navegación (Decompose/Voyager), Coil 3, GitLive Firebase KMP
- [ ] Configurar Firebase: crear proyecto en consola, **plan Blaze con alerta a 0 €**, habilitar Auth (email/contraseña)
- [ ] Hello world Auth en iOS con GitLive — validar pronto, es el mayor riesgo técnico
- [ ] Traducir mockup a tema Compose: `ColorScheme` (claro/oscuro) + `Typography` (Fraunces/Inter/Caveat) + componentes base

**Backlog de mejoras detectadas:**
- Estado inicial del itinerario por ciudad: desplegar automáticamente la ciudad de "hoy", resto plegado.
- Categorías de gasto: fijas (las 6 del mockup) para simplificar.
- Confirmar fechas reales del viaje (mockup usa mayo como ejemplo; viaje real en noviembre 2026).
