# Cin — versión integrada con la base compartida Cinee

Esta copia está basada en tu proyecto `Cin` original. Los cambios de integración son:

1. Se agregó `src/CLASES/PersistenciaVentasMySQL.java`.
2. `interfaces.Pago` guarda de forma transaccional el encabezado de la venta, datos del cliente, película/función/sala, ticket/QR, método de pago, productos y asientos antes de marcar la compra como terminada.
3. `interfaces.Asientos` consulta también MySQL para bloquear butacas vendidas en ejecuciones anteriores.
4. Se añadió `sql/cinee_schema.sql` con el esquema de la base compartida.
5. `nbproject/project.properties` utiliza los JAR locales de `lib/`.

## Preparación

1. Asegúrate de haber iniciado MySQL y ejecutado `sql/cinee_schema.sql` (solo una vez; si ya importaste el mismo esquema desde `CineeAdmin/sql`, no hace falta repetirlo).
2. Configura una vez la conexión desde **Cinee Admin**. Las dos aplicaciones leen `%USERPROFILE%\.cinee.properties`.
3. Ejecuta `scripts/descargar_mysql_connector.bat` para descargar MySQL Connector/J 9.7.0 a `lib/`. Requiere Internet en el equipo donde ejecutes este paso. El JAR no se incluye en este ZIP.
4. Abre la carpeta `Cin_Integrado` en NetBeans y ejecuta **Clean and Build**. El proyecto usa Java 17.
5. Prueba una compra completa. Luego abre Cinee Admin: la compra deberá aparecer en **Registro de ventas**.

## Consideraciones

- No se guardan números de tarjeta, CVV ni otros datos sensibles del medio de pago.
- El código de pago original simula la transacción; esta integración únicamente hace persistentes los datos de la compra.
- Las compras que se hicieron con la versión original y nunca se registraron en una base de datos no aparecen retroactivamente.
- Para probar el proyecto cliente en un equipo distinto del servidor MySQL, necesitas cambiar la URL JDBC al servidor y configurar usuarios/firewall de forma segura.
