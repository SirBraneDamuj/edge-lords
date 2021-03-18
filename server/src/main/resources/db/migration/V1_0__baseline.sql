SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE public.deckcards (
    id uuid NOT NULL,
    name character varying(20) NOT NULL,
    count integer NOT NULL,
    deck uuid NOT NULL
);

CREATE TABLE public.decks (
    id uuid NOT NULL,
    name character varying(30) NOT NULL,
    "user" uuid NOT NULL,
    master character varying(30) NOT NULL
);

CREATE TABLE public.gamedecks (
    id uuid NOT NULL,
    game uuid NOT NULL,
    "user" uuid NOT NULL,
    deck uuid NOT NULL,
    label character varying(10) NOT NULL
);

CREATE TABLE public.games (
    id uuid NOT NULL,
    state text NOT NULL
);

CREATE TABLE public.sessions (
    id uuid NOT NULL,
    token character varying(64) NOT NULL,
    expires_at timestamp without time zone NOT NULL,
    "user" uuid NOT NULL
);

CREATE TABLE public.users (
    id uuid NOT NULL,
    name character varying(20) NOT NULL,
    password_hash character varying(64) NOT NULL
);

ALTER TABLE ONLY public.deckcards
    ADD CONSTRAINT deckcards_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.decks
    ADD CONSTRAINT decks_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.gamedecks
    ADD CONSTRAINT gamedecks_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.games
    ADD CONSTRAINT games_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT sessions_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT sessions_token_unique UNIQUE (token);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_name_unique UNIQUE (name);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

CREATE INDEX sessions_user ON public.sessions USING btree ("user");

ALTER TABLE ONLY public.deckcards
    ADD CONSTRAINT fk_deckcards_deck_id FOREIGN KEY (deck) REFERENCES public.decks(id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE ONLY public.decks
    ADD CONSTRAINT fk_decks_user_id FOREIGN KEY ("user") REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE ONLY public.gamedecks
    ADD CONSTRAINT fk_gamedecks_deck_id FOREIGN KEY (deck) REFERENCES public.decks(id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE ONLY public.gamedecks
    ADD CONSTRAINT fk_gamedecks_game_id FOREIGN KEY (game) REFERENCES public.games(id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE ONLY public.gamedecks
    ADD CONSTRAINT fk_gamedecks_user_id FOREIGN KEY ("user") REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT fk_sessions_user_id FOREIGN KEY ("user") REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE RESTRICT;

