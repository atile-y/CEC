
----------------------------------
-- CREACION DE LA BASE DE DATOS --
----------------------------------

DROP TABLE IF EXISTS tblAlumno_Curso;
DROP TABLE IF EXISTS tblAlumno_Grupo;
DROP TABLE IF EXISTS tblGrupo;
DROP TABLE IF EXISTS tblCurso;
DROP TABLE IF EXISTS tblInstructor;
DROP TABLE IF EXISTS tblAlumno;
DROP TABLE IF EXISTS tblAsistente;
DROP TABLE IF EXISTS tblCoordinador;
DROP TABLE IF EXISTS tblPersona;


CREATE TABLE IF NOT EXISTS tblPersona
    (
        IdPersona           INT AUTO_INCREMENT NOT NULL,
        Nombre              VARCHAR(50) DEFAULT '',
        ApPaterno           VARCHAR(50) DEFAULT '',
        ApMaterno           VARCHAR(50) DEFAULT '',
        Correo              VARCHAR(50),
        Telefono            VARCHAR(15),
        
        INDEX(ApPaterno, ApMaterno),
        INDEX(Nombre, ApPaterno, ApMaterno),
        
        PRIMARY KEY(IdPersona)
    ) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS tblCoordinador
    (
        IdCoordinador       INT NOT NULL,
        Usuario             VARCHAR(20) NOT NULL,
        Contrasenia         VARCHAR(20) NOT NULL,
        
        FOREIGN KEY (IdCoordinador) REFERENCES tblPersona(IdPersona)
            ON UPDATE CASCADE ON DELETE RESTRICT,
        
        UNIQUE INDEX(Usuario),
        
        PRIMARY KEY(IdCoordinador)
    ) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS tblAsistente
    (
        IdAsistente         INT NOT NULL,
        Usuario             VARCHAR(20) NOT NULL,
        Contrasenia         VARCHAR(20) NOT NULL,
        
        FOREIGN KEY (IdAsistente) REFERENCES tblPersona(IdPersona)
            ON UPDATE CASCADE ON DELETE RESTRICT,
        
        UNIQUE INDEX(Usuario),

        PRIMARY KEY(IdAsistente)
    ) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS tblAlumno
    (
        IdAlumno            INT NOT NULL,
        CURP                CHAR(18) NOT NULL,
        Tipo                ENUM('PG', 'CP') NOT NULL,
        
        FOREIGN KEY (IdAlumno) REFERENCES tblPersona(IdPersona)
            ON UPDATE CASCADE ON DELETE RESTRICT,
        
        UNIQUE INDEX(CURP),

        PRIMARY KEY(IdAlumno)
    ) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS tblInstructor
    (
        IdInstructor        INT NOT NULL,
        RFC                 VARCHAR(13) NOT NULL,
        
        FOREIGN KEY (IdInstructor) REFERENCES tblPersona(IdPersona)
            ON UPDATE CASCADE ON DELETE RESTRICT,
        
        UNIQUE INDEX(RFC),

        PRIMARY KEY(IdInstructor)
    ) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS tblCurso
    (
        IdCurso             INT AUTO_INCREMENT NOT NULL,
        Nombre              VARCHAR(80) NOT NULL,
        CostoGeneral        DECIMAL(8,2),
        CostoComunidad      DECIMAL(8,2),
        HoraInicio          TIME,
        HoraFinal           TIME,
        Periodo             VARCHAR(10),
        Temario             VARCHAR(50),
        MinAlumnos          INT,
        MaxAlumnos          INT,
        Evaluacion          BOOLEAN,
        Estado              ENUM('PRE', 'OPENED', 'CLOSED') DEFAULT 'PRE',
        Folios              CHAR(40),
        
        INDEX(Nombre),
        INDEX(Estado),
        
        PRIMARY KEY(IdCurso)
    ) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS tblGrupo
    (
        IdGrupo             INT AUTO_INCREMENT NOT NULL,
        IdInstructor        INT NOT NULL,
        IdCurso             INT NOT NULL,
        Nombre              VARCHAR(10),
        Aula                VARCHAR(10),
        
        FOREIGN KEY (IdInstructor) REFERENCES tblInstructor(IdInstructor)
            ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (IdCurso) REFERENCES tblCurso(IdCurso)
            ON DELETE RESTRICT ON UPDATE CASCADE,
        
        INDEX(Nombre),
        
        PRIMARY KEY(IdGrupo)
    ) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS tblAlumno_Grupo
    (
        IdAlumno            INT NOT NULL,
        IdGrupo             INT NOT NULL,
        Calificacion        DECIMAL(3,1),
        
        FOREIGN KEY (IdAlumno) REFERENCES tblAlumno(IdAlumno)
            ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (IdGrupo) REFERENCES tblGrupo(IdGrupo)
            ON DELETE RESTRICT ON UPDATE CASCADE,
            
        PRIMARY KEY(IdAlumno, IdGrupo)
    ) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS tblAlumno_Curso
    (
        IdAlumno            INT NOT NULL,
        IdCurso             INT NOT NULL,
        FolioDeposito       VARCHAR(20),
        
        FOREIGN KEY (IdAlumno) REFERENCES tblAlumno(IdAlumno)
            ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (IdCurso) REFERENCES tblCurso(IdCurso)
            ON DELETE RESTRICT ON UPDATE CASCADE,
            
        PRIMARY KEY(IdAlumno, IdCurso)
    ) ENGINE = INNODB;

----------------------
-- Datos de ejemplo --
----------------------
    
INSERT INTO tblPersona(IdPersona) VALUES(1); -- agregar datos del instructor
INSERT INTO tblCoordinador VALUES(1, 'instructor', 'instructor'); -- login instructor
INSERT INTO tblPersona(IdPersona) VALUES(2); -- agregar datos del asistente
INSERT INTO tblAsistente VALUES(2, 'asistente', 'asistente'); -- login asistente

-- Instructores de ejemplo
INSERT INTO tblPersona VALUES(3, 'Instructor', '1', '', 'ins@ins.com', '55123');
INSERT INTO tblInstructor VALUES(3, 'RFC1');
INSERT INTO tblPersona VALUES(4, 'Instructor', '2', '', 'ins@ins.com', '55123');
INSERT INTO tblInstructor VALUES(4, 'RFC2');
INSERT INTO tblPersona VALUES(5, 'Instructor', '3', '', 'ins@ins.com', '55123');
INSERT INTO tblInstructor VALUES(5, 'RFC3');
INSERT INTO tblPersona VALUES(6, 'Instructor', '4', '', 'ins@ins.com', '55123');
INSERT INTO tblInstructor VALUES(6, 'RFC4');

-- Alumnos de ejemplo
INSERT INTO tblPersona VALUES(7, 'Alejandro', 'Yescas', 'Benítez', 'atilexd@gmail.com', '5551844456');
INSERT INTO tblAlumno VALUES(7, 'YEBA------H--SNL01', 'CP');
INSERT INTO tblPersona VALUES(8, 'NAlumno2', 'PAlumno2', 'MAlumno2', 'ca2@ca2.com', '55-2');
INSERT INTO tblAlumno VALUES(8, 'PAMN------M--LLL02', 'PG');
INSERT INTO tblPersona VALUES(9, 'NAlumno3', 'PAlumno3', 'MAlumno3', 'ca3@ca3.com', '55-3');
INSERT INTO tblAlumno VALUES(9, 'PAMN------H--LLL03', 'PG');
INSERT INTO tblPersona VALUES(10, 'NAlumno4', 'PAlumno4', 'MAlumno4', 'ca4@ca4.com', '55-4');
INSERT INTO tblAlumno VALUES(10, 'PAMN------M--LLL04', 'CP');

-- Cursos de ejemplo
INSERT INTO tblCurso VALUES
    (
        1,
        'Curso de Preparación para Exámenes de Admisión',
        2000.00,
        1000.00,
        '08:00',
        '16:00',
        '2017',
        NULL,
        25,
        30,
        0,
        'PRE',
        NULL
    );

INSERT INTO tblCurso VALUES
    (
        2,
        'Curso de Preparación para Exámenes de Acreditación del Bachillerato',
        4000.00,
        2000.00,
        '08:00',
        '16:00',
        '2017/A',
        NULL,
        20,
        30,
        0,
        'PRE',
        NULL
    );

INSERT INTO tblCurso VALUES
    (
        3,
        'Cursos de Capacitación en Computación y Sistemas',
        8000.00,
        4000.00,
        '08:00',
        '16:00',
        '2017/A',
        NULL,
        13,
        25,
        1,
        'PRE',
        NULL
    );

INSERT INTO tblCurso VALUES
    (
        4,
        'Cursos de Matemáticas para Ingenieros',
        16000.00,
        8000.00,
        '08:00',
        '16:00',
        '2017/A',
        NULL,
        15,
        30,
        1,
        'PRE',
        NULL
    );

-- Inscripcion de alumnos a cursos
INSERT INTO tblAlumno_Curso VALUES(7, 1, 'FOLIO_1');
INSERT INTO tblAlumno_Curso VALUES(7, 4, 'FOLIO_2');
INSERT INTO tblAlumno_Curso VALUES(8, 3, 'FOLIO_3');
INSERT INTO tblAlumno_Curso VALUES(8, 4, 'FOLIO_4');
INSERT INTO tblAlumno_Curso VALUES(9, 1, 'FOLIO_5');
INSERT INTO tblAlumno_Curso VALUES(9, 2, 'FOLIO_6');
INSERT INTO tblAlumno_Curso VALUES(9, 4, 'FOLIO_7');
INSERT INTO tblAlumno_Curso VALUES(10, 1, 'FOLIO_8');
INSERT INTO tblAlumno_Curso VALUES(10, 3, 'FOLIO_9');
INSERT INTO tblAlumno_Curso VALUES(10, 4, 'FOLIO_10');
