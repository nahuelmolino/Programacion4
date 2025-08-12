# Diagrama de Clases - Python + PostgreSQL

Este documento describe las clases del proyecto, indicando:
- **Responsabilidad**: Propósito general de la clase.
- **Funcionalidades**: Métodos que implementa para cumplir su responsabilidad.
- **Persistencia**: Si se guarda o no en base de datos.

---

## 📊 Resumen de Clases

| Clase            | Responsabilidad | Persistencia |
|------------------|-----------------|--------------|
| **Usuarios**     | Representar datos de un usuario del sistema. | 🟩 Sí (tabla `usuarios`) |
| **UsuarioDao**   | CRUD sobre la tabla `usuarios`. | 🟥 No |
| **Conexion**     | Administrar el pool de conexiones a PostgreSQL. | 🟥 No |
| **CursorDelPool**| Manejar el cursor con contexto (`with`). | 🟥 No |
| **MenuAppUsuario** | Mostrar menú y coordinar operaciones de usuario. | 🟥 No |
| **logger_base**  | Configurar y centralizar el sistema de logs. | 🟥 No |

---

## 🟩 Clase: `Usuarios` *(Va a Base de Datos)*
**Responsabilidad**  
Representar a un usuario del sistema y sus datos persistentes.

**Funcionalidades**  
- `__init__(...)` → Inicializar usuario.  
- `__str__()` → Mostrar datos legibles.  
- `validar_password(password)` → Verificar si la contraseña es correcta.  
- `cambiar_password(nueva_password)` → Actualizar contraseña.

**Persistencia**  
Sí. Mapea a la tabla `usuarios` en PostgreSQL:

```sql
CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL
);

```

## 🟩 Clase: `UsuarioDao` (No va a Base de Datos)
### Responsabilidad
Gestionar las operaciones CRUD (Create, Read, Update, Delete) sobre la tabla usuarios.

Funcionalidades

seleccionar_todos() → Lista todos los usuarios.

insertar(usuario) → Inserta un nuevo usuario.

actualizar(usuario) → Modifica datos de un usuario.

eliminar(usuario) → Borra un usuario.

Persistencia
No. Es una capa de acceso a datos.

## 🟩 Clase:Conexion 

### Responsabilidad
Administrar la conexión y el pool hacia PostgreSQL.

Funcionalidades

obtener_conexion() → Retorna una conexión desde el pool.

cerrar_conexion(conexion) → Devuelve la conexión al pool.

Persistencia
No.

## 🟩 Clase:CursorDelPool 

### Responsabilidad


Administrar un cursor de base de datos con contexto (with) para simplificar y manejar errores.

Funcionalidades

__enter__() → Abrir conexión y cursor.

__exit__() → Cerrar cursor y devolver conexión al pool.

Persistencia
No.

## 🟩 Clase:MenuAppUsuario 

### Responsabilidad

Mostrar opciones al usuario final y coordinar acciones con el DAO.

Funcionalidades

mostrar_menu() → Imprime opciones.

ejecutar_opcion(opcion) → Llama al método DAO correspondiente.

solicitar_datos_usuario() → Pide datos por consola.

Persistencia
No.

## 🟩 Clase:logger_base 

### Responsabilidad

Configurar y centralizar el registro de logs del sistema.

Funcionalidades

configurar_logger() → Establecer nivel, formato y archivo de log.

log_info(mensaje) → Registrar mensajes informativos.

log_error(mensaje) → Registrar errores.

Persistencia
No.

# 📌 Notas sobre el diseño
Responsabilidad = rol de la clase en el sistema (su motivo de existir).

Funcionalidades = métodos concretos que permiten cumplir esa responsabilidad.

Solo las clases que representan entidades del dominio con datos persistentes deben tener tabla en la base de datos.

Clases de infraestructura (conexión, logging, menú) no se persisten.