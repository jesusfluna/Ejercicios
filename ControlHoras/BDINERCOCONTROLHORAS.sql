--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.15
-- Dumped by pg_dump version 9.5.15

-- Started on 2019-05-20 17:13:15

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE "INERCOCONTROLHORAS";
--
-- TOC entry 2126 (class 1262 OID 17322)
-- Name: INERCOCONTROLHORAS; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "INERCOCONTROLHORAS" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Spanish_Spain.1252' LC_CTYPE = 'Spanish_Spain.1252';


ALTER DATABASE "INERCOCONTROLHORAS" OWNER TO postgres;

\connect "INERCOCONTROLHORAS"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2129 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 182 (class 1259 OID 17331)
-- Name: dia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dia (
    entrada timestamp without time zone NOT NULL,
    salida timestamp without time zone,
    horasviaje numeric DEFAULT 0,
    totalhoras numeric DEFAULT 0,
    id integer NOT NULL
);


ALTER TABLE public.dia OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 17417)
-- Name: dia_empleado; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dia_empleado (
    id_dia integer NOT NULL,
    usuario text NOT NULL
);


ALTER TABLE public.dia_empleado OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 17404)
-- Name: dia_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.dia_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dia_id_seq OWNER TO postgres;

--
-- TOC entry 2130 (class 0 OID 0)
-- Dependencies: 183
-- Name: dia_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.dia_id_seq OWNED BY public.dia.id;


--
-- TOC entry 181 (class 1259 OID 17323)
-- Name: empleado; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empleado (
    nombre text NOT NULL,
    usuario text NOT NULL,
    contrasena text NOT NULL,
    email text NOT NULL
);


ALTER TABLE public.empleado OWNER TO postgres;

--
-- TOC entry 1993 (class 2604 OID 17406)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dia ALTER COLUMN id SET DEFAULT nextval('public.dia_id_seq'::regclass);


--
-- TOC entry 2118 (class 0 OID 17331)
-- Dependencies: 182
-- Data for Name: dia; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.dia (entrada, salida, horasviaje, totalhoras, id) VALUES ('2019-05-16 10:46:18', '2019-05-16 13:48:41', 0, 3.0, 7);
INSERT INTO public.dia (entrada, salida, horasviaje, totalhoras, id) VALUES ('2019-05-13 09:00:00', '2019-05-13 18:00:00', 0, 8, 1);
INSERT INTO public.dia (entrada, salida, horasviaje, totalhoras, id) VALUES ('2019-05-14 09:13:36', '2019-05-14 18:31:06', 0, 8.0, 5);
INSERT INTO public.dia (entrada, salida, horasviaje, totalhoras, id) VALUES ('2019-05-16 14:32:50', '2019-05-17 18:08:48', 0, 4, 8);
INSERT INTO public.dia (entrada, salida, horasviaje, totalhoras, id) VALUES ('2019-05-15 09:01:06', '2019-05-15 20:01:06', 0, 10.5, 6);
INSERT INTO public.dia (entrada, salida, horasviaje, totalhoras, id) VALUES ('2019-05-17 09:04:05', '2019-05-17 18:18:33', 0, 9.0, 9);


--
-- TOC entry 2120 (class 0 OID 17417)
-- Dependencies: 184
-- Data for Name: dia_empleado; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.dia_empleado (id_dia, usuario) VALUES (1, 'jfluna');
INSERT INTO public.dia_empleado (id_dia, usuario) VALUES (5, 'jfluna');
INSERT INTO public.dia_empleado (id_dia, usuario) VALUES (6, 'jfluna');
INSERT INTO public.dia_empleado (id_dia, usuario) VALUES (7, 'jfluna');
INSERT INTO public.dia_empleado (id_dia, usuario) VALUES (8, 'jfluna');
INSERT INTO public.dia_empleado (id_dia, usuario) VALUES (9, 'jfluna');


--
-- TOC entry 2131 (class 0 OID 0)
-- Dependencies: 183
-- Name: dia_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dia_id_seq', 9, true);


--
-- TOC entry 2117 (class 0 OID 17323)
-- Dependencies: 181
-- Data for Name: empleado; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.empleado (nombre, usuario, contrasena, email) VALUES ('Jhon Doe', 'jdoe', 'Djhdoe', 'jdoe@empresa.com');


--
-- TOC entry 1996 (class 2606 OID 17330)
-- Name: Empleado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empleado
    ADD CONSTRAINT "Empleado_pkey" PRIMARY KEY (usuario);


--
-- TOC entry 2000 (class 2606 OID 17424)
-- Name: diaEmpleado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dia_empleado
    ADD CONSTRAINT "diaEmpleado_pkey" PRIMARY KEY (id_dia, usuario);


--
-- TOC entry 1998 (class 2606 OID 17415)
-- Name: dia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dia
    ADD CONSTRAINT dia_pkey PRIMARY KEY (id);


--
-- TOC entry 2001 (class 2606 OID 17425)
-- Name: diaEmpleado_id_dia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dia_empleado
    ADD CONSTRAINT "diaEmpleado_id_dia_fkey" FOREIGN KEY (id_dia) REFERENCES public.dia(id);


--
-- TOC entry 2002 (class 2606 OID 17430)
-- Name: diaEmpleado_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dia_empleado
    ADD CONSTRAINT "diaEmpleado_usuario_fkey" FOREIGN KEY (usuario) REFERENCES public.empleado(usuario);


--
-- TOC entry 2128 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2019-05-20 17:13:16

--
-- PostgreSQL database dump complete
--

