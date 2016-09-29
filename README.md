## CEC

Este Sistema basado en Web tiene como objetivo agilizar el proceso de gestión de cursos, considerando los siguientes procesos:

- Gestión de Oferta de Cursos: Este proceso se encargará de agregar cursos y publicar los datos pertenecientes a dichos cursos: costos (público general y estudiantes), horario, periodo y temario.
- Gestión de Inscripciones: Este proceso se encargará de inscribir a los alumnos, dentro del periodo de inscripciones, recibiendo la ficha de depósito bancario por el monto de la inscripción y el formato de datos rellenado por el alumno. Para finalmente generar un comprobante de pago.
- Gestión de Apertura de Cursos: Este proceso se encargará de registrar la apertura de grupos para los cursos, con un instructor y un aula asignados, imprimir la lista de alumnos y la relación de alumnos y sus correspondientes folios de movimientos bancarios.
- Gestión de Asistencia: Este proceso se encargará de registrar las asistencias de los alumnos, así como las horas de entrada y salida de los instructores.
- Gestión de Cierre de Cursos: Esta función procederá a capturar los registros de asistencia y, en caso de ser un curso con evaluación, emitir la constancia correspondiente.

Los posibles usuarios que utilizarán el sistema son los siguientes:

- Alumnos: Se inscriben a los cursos que se ofrecen, pueden inscribirse a varios cursos que no se traslapen. Son identificados por el CURP, sirviendo este para recuperar sus datos si es alumno recurrente.
- Instructores: Imparten los cursos, registran la asistencia y las calificaciones de los alumnos, definen los temarios de los cursos a impartir, pueden impartir varios cursos que no se traslapen. Se identifican por su RFC.
- Coordinador: Determina los cursos que se ofertarán y autoriza su apertura, publica la convocatoria donde se especifican los cursos y los datos correspondientes, requiere saber el momento en que se cubre el mínimo de alumnos para un curso y el total de ingresos que genera cada curso.
- Asistente del coordinador: Realiza el proceso de inscripción de los alumnos al Sistema, genera listas de asistencia, genera las constancias de los cursos con evaluación.

## Requisitos

El Sistema está desarrollado para correr con la siguiente paquetería:

- Java EE 8.0,
- GlassFish Server 4.1.1 y
- MySQL 5.7.

El archivo 'sqlScripts/Data Base.sql' crea la Base de Datos y agrega algunos datos de ejemplo a la BD
