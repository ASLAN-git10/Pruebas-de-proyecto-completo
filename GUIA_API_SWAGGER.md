# 📚 Guía de API — EduPerformance

> **URL base:** `http://localhost:8080`
> **Swagger UI:** `http://localhost:8080/swagger-ui.html`
>
> ⚠️ **IMPORTANTE:** Sigue el orden de los pasos. Cada entidad depende de la anterior.
> Guarda los `id` que te devuelva cada respuesta — los necesitarás en los pasos siguientes.

---

## 📋 Orden de creación

```
Perfil (opcional) → Usuario → Curso → Profesor → Estudiante → Asistencia / Calificación
```

---

## PASO 1 — Crear Perfil *(opcional)*

> Solo si quieres que el usuario tenga dirección y teléfono.

**Endpoint:** `POST /api/perfiles`

```json
{
  "direccion": "Calle 45 # 12-30",
  "telefono": "3001234567"
}
```

**Respuesta esperada (200):**
```json
{
  "id": 1,
  "direccion": "Calle 45 # 12-30",
  "telefono": "3001234567"
}
```

✅ Guarda el `id` del perfil (ej: `1`)

---

## PASO 2 — Crear Usuario (Profesor)

**Endpoint:** `POST /api/usuarios`

```json
{
  "nombre": "Carlos",
  "apellido": "Ramírez",
  "edad": 35,
  "email": "carlos@edu.com",
  "password": "miPassword123",
  "perfilId": 1
}
```

> Si no creaste perfil en el Paso 1, omite la línea `"perfilId"`.

**Respuesta esperada (200):**
```json
{
  "id": 1,
  "nombreCompleto": "Carlos Ramírez",
  "edad": 35,
  "email": "carlos@edu.com",
  "direccion": "Calle 45 # 12-30",
  "telefono": "3001234567"
}
```

✅ Guarda el `id` del usuario (ej: `1`)

---

## PASO 3 — Crear Usuario (Estudiante)

> Repite el Paso 2 con datos distintos para crear el usuario que será estudiante.

**Endpoint:** `POST /api/usuarios`

```json
{
  "nombre": "Ana",
  "apellido": "Gómez",
  "edad": 20,
  "email": "ana@edu.com",
  "password": "miPassword456"
}
```

**Respuesta esperada (200):**
```json
{
  "id": 2,
  "nombreCompleto": "Ana Gómez",
  "edad": 20,
  "email": "ana@edu.com"
}
```

✅ Guarda el `id` del usuario estudiante (ej: `2`)

---

## PASO 4 — Crear Curso

**Endpoint:** `POST /api/cursos`

```json
{
  "nombre": "Matemáticas",
  "descripcion": "Álgebra lineal y cálculo diferencial",
  "usuarioId": 1
}
```

> `usuarioId` es el `id` del usuario del Paso 2 (el profesor).

**Respuesta esperada (200):**
```json
{
  "id": 1,
  "nombre": "Matemáticas",
  "descripcion": "Álgebra lineal y cálculo diferencial"
}
```

✅ Guarda el `id` del curso (ej: `1`)

---

## PASO 5 — Crear Profesor

**Endpoint:** `POST /api/profesores`

```json
{
  "usuarioId": 1
}
```

> `usuarioId` es el `id` del usuario del Paso 2.

**Respuesta esperada (200):**
```json
{
  "id": 1,
  "nombreCompleto": "Carlos Ramírez",
  "cursos": []
}
```

✅ Guarda el `id` del profesor (ej: `1`)

---

## PASO 6 — Asignar Curso al Profesor

> ⚠️ Este endpoint **NO tiene body**. Los IDs van en la URL.

**Endpoint:** `POST /api/profesores/{profesorId}/asignar/{cursoId}`

**Ejemplo:** `POST /api/profesores/1/asignar/1`

**Respuesta esperada (200):**
```json
{
  "id": 1,
  "nombreCompleto": "Carlos Ramírez",
  "cursos": ["Matemáticas"]
}
```

---

## PASO 7 — Crear Estudiante

**Endpoint:** `POST /api/estudiantes`

```json
{
  "usuarioId": 2
}
```

> `usuarioId` es el `id` del usuario del Paso 3 (la estudiante Ana).

**Respuesta esperada (200):**
```json
{
  "id": 1,
  "nombreCompleto": "Ana Gómez",
  "cursos": []
}
```

✅ Guarda el `id` del estudiante (ej: `1`)

---

## PASO 8 — Registrar Asistencia

**Endpoint:** `POST /api/asistencias`

```json
{
  "fecha": "2026-05-14",
  "presente": true,
  "estudianteId": 1,
  "cursoId": 1
}
```

> - `estudianteId`: `id` del estudiante del Paso 7
> - `cursoId`: `id` del curso del Paso 4
> - `presente`: `true` = presente, `false` = ausente

---

## PASO 9 — Registrar Calificación

**Endpoint:** `POST /api/calificaciones`

```json
{
  "nota": 4.5,
  "estudianteId": 1,
  "cursoId": 1
}
```

> - Nota válida: entre `0.0` y `5.0`
> - `estudianteId`: `id` del estudiante del Paso 7
> - `cursoId`: `id` del curso del Paso 4

---

## 🔍 Consultas GET disponibles

| Endpoint | Descripción |
|---|---|
| `GET /api/usuarios` | Lista todos los usuarios |
| `GET /api/usuarios/{id}` | Busca usuario por ID |
| `GET /api/perfiles` | Lista todos los perfiles |
| `GET /api/cursos` | Lista todos los cursos |
| `GET /api/profesores` | Lista todos los profesores |
| `GET /api/estudiantes` | Lista todos los estudiantes |
| `GET /api/asistencias` | Lista todas las asistencias |
| `GET /api/calificaciones` | Lista todas las calificaciones |

---

## ⚠️ Reglas de validación importantes

| Campo | Regla |
|---|---|
| `nombre` | Entre 3 y 30 caracteres, obligatorio |
| `apellido` | Entre 3 y 30 caracteres, obligatorio |
| `edad` | Entre 18 y 80 años |
| `email` | Formato válido, único en BD |
| `password` | Mínimo 8 caracteres |
| `nota` | Entre 0.0 y 5.0 |
| `telefono` | Entre 7 y 20 dígitos |

---

*Generado para el proyecto EduPerformance — Grupo 2*
