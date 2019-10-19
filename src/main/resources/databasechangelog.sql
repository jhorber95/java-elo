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