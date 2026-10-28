# Cinee Admin — panel administrador de ventas

Proyecto Java 17 + Swing para NetBeans/Maven. Está separado del proyecto cliente y consulta **la misma base MySQL** que utiliza la integración incluida.

## Funciones incluidas

- Inicio de sesión con usuario y contraseña; la primera cuenta se crea desde la pantalla inicial si la tabla `administradores` está vacía.
- Contraseñas protegidas con PBKDF2-HMAC-SHA256, salt aleatorio y 210 000 iteraciones. No hay contraseña de administrador predeterminada.
- Resumen con importe vendido, número de ventas, promedio por venta y clientes registrados.
- Gráficas de ingresos por día, distribución por método de pago y películas con mayor importe vendido.
- Tabla con búsqueda por código de venta, cliente, documento, película, ticket o asiento; filtros por período y ordenación.
- Vista detallada de cada compra: fecha, cliente, documento, correo, teléfono, película, fecha y hora de función, código y número de sala, asientos, método de pago, ticket/QR, entradas, productos, cantidades, precio unitario, subtotal y total.
- Exportación a CSV para abrir en Excel o LibreOffice.
- Configuración y prueba de conexión MySQL desde la interfaz.
- Acceso de consulta para ventas: no incluye opciones para editar o eliminar compras.

## Requisitos

- JDK 17.
- MySQL 8.0 o compatible.
- NetBeans con soporte Maven, o Maven instalado.
- Conexión a Internet la primera vez que Maven descargue MySQL Connector/J y los plugins de construcción.

## 1. Crear el esquema

1. Inicia MySQL.
2. Abre MySQL Workbench y ejecuta el contenido de `sql/cinee_schema.sql`.
3. Para evitar usar `root` desde las aplicaciones, crea un usuario local dedicado con una contraseña propia. Ejecuta estos comandos como administrador de MySQL, cambiando el valor de contraseña:

```sql
CREATE USER 'cinee_app'@'localhost' IDENTIFIED BY 'PON_AQUI_UNA_CLAVE_LARGA';
GRANT SELECT, INSERT, UPDATE, DELETE ON cinee_db.* TO 'cinee_app'@'localhost';
FLUSH PRIVILEGES;
```

Si ese usuario ya existe, cambia su contraseña con `ALTER USER` en vez de crearlo otra vez. El esquema SQL **no crea** un usuario con contraseña conocida.

## 2. Abrir y ejecutar el panel

1. Descomprime el ZIP.
2. En NetBeans elige **Open Project** y abre la carpeta `CineeAdmin` (la que contiene `pom.xml`).
3. Espera a que Maven resuelva las dependencias.
4. Ejecuta `pe.cinee.admin.App`.
5. En la pantalla de acceso pulsa **Configurar base de datos**, confirma la URL, el usuario y la contraseña, y pulsa **Guardar**.
6. Si el esquema está instalado y la conexión funciona, aparecerá el botón **Crear primer administrador**. Crea un usuario personal con una contraseña de al menos 10 caracteres y después inicia sesión.

La URL predeterminada es `jdbc:mysql://localhost:3306/cinee_db?...`. Si MySQL está en otro equipo, reemplaza `localhost` por el host del servidor y configura sus permisos de red de manera segura.

También puedes ejecutar `run.bat` en Windows, si tienes Maven y Java 17 en el PATH; compila el proyecto y abre el JAR resultante.

## 3. Compartir la conexión con el proyecto cliente

Ambas aplicaciones leen esta configuración local:

`%USERPROFILE%\.cinee.properties`

El panel administrador la crea al guardar la conexión. El adaptador del proyecto cliente usa el mismo archivo, de modo que no hay dos configuraciones distintas que mantener en el mismo Windows.

El proyecto de cliente integrado está en la carpeta `Cin_Integrado`, incluida en el ZIP que acompaña este proyecto. Mira `Cin_Integrado/README_INTEGRACION.md` para prepararlo.

## Importante sobre los datos existentes

En el ZIP original, `CLASES.BaseDeDatos` mantiene películas y asientos en memoria y `Pago.java` construye la venta en el momento de compra: **no existe persistencia SQL previa ni un historial antiguo que se pueda migrar automáticamente**. Las compras se reflejarán en el panel desde que ejecutes el cliente integrado y se guarden en `cinee_db`. Las ventas anteriores que no se guardaron en ningún archivo o tabla no pueden recuperarse automáticamente.

Los métodos de pago actuales del proyecto cliente son simulados por consola; esta integración registra el método seleccionado y el total, pero no convierte el prototipo en una pasarela bancaria real. No se almacena el número completo de la tarjeta.

## Estructura

- `src/main/java/pe/cinee/admin/` — inicio, configuración y conexión.
- `src/main/java/pe/cinee/admin/repository/` — consultas de ventas y reportes.
- `src/main/java/pe/cinee/admin/model/` — modelos de consulta.
- `src/main/java/pe/cinee/admin/ui/` — login, dashboard, gráficas, tabla y detalle de compra.
- `sql/cinee_schema.sql` — esquema compartido.
- `integracion-cliente/CLASES/PersistenciaVentasMySQL.java` — adaptador que se incorpora al cliente.
