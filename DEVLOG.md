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
- Creados `CLAUDE.md` (contexto auto-cargado en cada sesión) y este `DEVLOG.md`.
- **Diseños unificados** en un único fichero `diseno/diseno-completo.html` (14 pantallas); eliminado `mockups-fase0.html`.
- Inicializado repositorio git con `.gitignore` para KMP (Android + iOS).
- Repo publicado en GitHub: https://github.com/numadesarrollos/bye-china-trip
- Creados `README.md` y `AGENTS.md` para compatibilidad total con Devin y otros agentes IA.

**Decisiones de diseño tomadas (validar si se reabren):**
- Bottom nav de 5 pestañas: Hoy · Itinerario · Vuelos · Trenes · Más (extras dentro de "Más").
- Detalle de día con cabecera de color (roja normal, dorada en fechas especiales).
- Navegación al Detalle de día: cabecera de ciudad pliega/despliega; fila de día navega.

---

## 🔜 Para el siguiente día (arrancar aquí)

**Próxima fase: Fase 1 — Setup KMP + Firebase + tema.**

Antes de programar / a confirmar al arrancar:
- [ ] Verificar versiones: Android Studio, JDK, Kotlin, Gradle, Xcode (Mac).
- [ ] Crear proyecto KMP con módulos `composeApp` + `shared` (domain/data/sync/platform) según arquitectura del plan.
- [ ] Configurar Firebase: crear proyecto en consola, **plan Blaze con alerta de presupuesto a 0 €**, habilitar Auth (email/contraseña).
- [ ] Integrar GitLive firebase-kotlin-sdk y **probar Auth en iOS pronto** (riesgo conocido de fricción en iOS).
- [ ] Traducir el mockup a tema Compose: `ColorScheme` claro/oscuro + `Typography` (Fraunces/Inter/Caveat) + componentes base (Card, Hero, chips 🐻/🐰, timeline hilo rojo, bottom nav).

**Mejoras / pendientes detectados (backlog):**
- Decidir estado inicial del itinerario por ciudad: ¿todo plegado o desplegada la ciudad de "hoy"? (recomendado: desplegar la de hoy).
- Decidir si las categorías de gasto son fijas o editables (recomendado: fijas para un viaje).
- Confirmar fechas reales del viaje (el mockup usa fechas de mayo ilustrativas; el viaje es en noviembre 2026).
