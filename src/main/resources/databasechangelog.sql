-- --------------------------------------------
-- Jhorkman Bernal 07/10/2019
-- --------------------------------------------

DROP TABLE public.test_history;

CREATE TABLE public.test_history
(
    id bigserial,
    id_fase_item integer,
    id_user bigint,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT test_history_pkey PRIMARY KEY (id),
    CONSTRAINT test_history_id_fase_item_fkey FOREIGN KEY (id_fase_item)
        REFERENCES public.fase_item (fit_id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT test_history_id_user_fkey FOREIGN KEY (id_user)
        REFERENCES public.usuario (usu_id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
)
    WITH (
        OIDS=FALSE
    );


-- --------------------------------------------
-- Jhorkman Bernal 20/10/2019
-- --------------------------------------------

DROP TABLE public.noticia;

CREATE TABLE public.noticia
(
    id bigserial NOT NULL ,
    titulo character varying(250),
    descripcion character varying(150),
    imagen_pricipal character varying(300),
    contenido text,
    created_at timestamp without time zone,
    autor bigint,
    CONSTRAINT noticia_pkey PRIMARY KEY (id),
    CONSTRAINT noticia_autor_fkey FOREIGN KEY (autor)
        REFERENCES public.usuario (usu_id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
)
    WITH (
        OIDS=FALSE
    );

DROP TABLE public.comentario;

CREATE TABLE public.comentario
(
    id bigserial NOT NULL,
    noticia_id bigint,
    usuario_id bigint,
    comentario text,
    fecha timestamp without time zone,
    padre bigint,
    CONSTRAINT comentario_pkey PRIMARY KEY (id),
    CONSTRAINT comentario_noticia_id_fkey FOREIGN KEY (noticia_id)
        REFERENCES public.noticia (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT comentario_padre_fkey FOREIGN KEY (padre)
        REFERENCES public.comentario (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT comentario_usuario_id_fkey FOREIGN KEY (usuario_id)
        REFERENCES public.usuario (usu_id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
)
    WITH (
        OIDS=FALSE
    );