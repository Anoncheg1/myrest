-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.1
-- PostgreSQL version: 10.0
-- Project Site: pgmodeler.io
-- Model Author: ---

-- object: motiv | type: ROLE --
-- DROP ROLE IF EXISTS motiv;



-- Database creation must be done outside a multicommand file.
-- These commands were put in this file only as a convenience.
-- -- object: myrest | type: DATABASE --
-- -- DROP DATABASE IF EXISTS myrest;
-- CREATE DATABASE myrest
-- 	ENCODING = 'UTF8'
-- 	LC_COLLATE = 'en_US.utf8'
-- 	LC_CTYPE = 'en_US.utf8'
-- 	TABLESPACE = pg_default
-- 	OWNER = motiv;
-- -- ddl-end --
-- 

-- object: public.project | type: TABLE --
-- DROP TABLE IF EXISTS public.project CASCADE;
CREATE TABLE public.project(
	id integer NOT NULL,
	name character varying(512) NOT NULL,
	fb double precision NOT NULL,
	rb double precision NOT NULL,
	id_state integer,
	mid_pr double precision NOT NULL,
	CONSTRAINT project_pk PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.project OWNER TO motiv;
-- ddl-end --

-- object: public.job | type: TABLE --
-- DROP TABLE IF EXISTS public.job CASCADE;
CREATE TABLE public.job(
	id integer NOT NULL,
	id_project integer,
	name character varying(512) NOT NULL,
	fb double precision NOT NULL,
	rb double precision NOT NULL,
	nach date NOT NULL,
	okonch date,
	dliteln double precision,
	vip_pr double precision,
	CONSTRAINT job_pk PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.job OWNER TO motiv;
-- ddl-end --

-- object: public.state | type: TABLE --
-- DROP TABLE IF EXISTS public.state CASCADE;
CREATE TABLE public.state(
	id integer NOT NULL,
	state varchar(512) NOT NULL,
	CONSTRAINT new_table_pk PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.state OWNER TO motiv;
-- ddl-end --

-- object: project_fk | type: CONSTRAINT --
-- ALTER TABLE public.job DROP CONSTRAINT IF EXISTS project_fk CASCADE;
ALTER TABLE public.job ADD CONSTRAINT project_fk FOREIGN KEY (id_project)
REFERENCES public.project (id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: state_fk | type: CONSTRAINT --
-- ALTER TABLE public.project DROP CONSTRAINT IF EXISTS state_fk CASCADE;
ALTER TABLE public.project ADD CONSTRAINT state_fk FOREIGN KEY (id_state)
REFERENCES public.state (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


