--
-- PostgreSQL database dump
--

-- Dumped from database version 11.7 (Ubuntu 11.7-0ubuntu0.19.10.1)
-- Dumped by pg_dump version 11.7 (Ubuntu 11.7-0ubuntu0.19.10.1)

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

--
-- Name: deckcards; Type: TABLE; Schema: public; Owner: edgelords
--

CREATE TABLE public.deckcards (
    id uuid NOT NULL,
    name character varying(20) NOT NULL,
    count integer NOT NULL,
    deck uuid NOT NULL
);


ALTER TABLE public.deckcards OWNER TO edgelords;

--
-- Name: decks; Type: TABLE; Schema: public; Owner: edgelords
--

CREATE TABLE public.decks (
    id uuid NOT NULL,
    name character varying(30) NOT NULL,
    "user" uuid NOT NULL,
    master character varying(30) NOT NULL
);


ALTER TABLE public.decks OWNER TO edgelords;

--
-- Name: gamedecks; Type: TABLE; Schema: public; Owner: edgelords
--

CREATE TABLE public.gamedecks (
    id uuid NOT NULL,
    game uuid NOT NULL,
    "user" uuid NOT NULL,
    deck uuid NOT NULL,
    label character varying(10) NOT NULL
);


ALTER TABLE public.gamedecks OWNER TO edgelords;

--
-- Name: games; Type: TABLE; Schema: public; Owner: edgelords
--

CREATE TABLE public.games (
    id uuid NOT NULL,
    state text NOT NULL
);


ALTER TABLE public.games OWNER TO edgelords;

--
-- Name: sessions; Type: TABLE; Schema: public; Owner: edgelords
--

CREATE TABLE public.sessions (
    id uuid NOT NULL,
    token character varying(64) NOT NULL,
    expires_at timestamp without time zone NOT NULL,
    "user" uuid NOT NULL
);


ALTER TABLE public.sessions OWNER TO edgelords;

--
-- Name: users; Type: TABLE; Schema: public; Owner: edgelords
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    name character varying(20) NOT NULL,
    password_hash character varying(64) NOT NULL
);


ALTER TABLE public.users OWNER TO edgelords;

--
-- Name: deckcards deckcards_pkey; Type: CONSTRAINT; Schema: public; Owner: edgelords
--

ALTER TABLE ONLY public.deckcards
    ADD CONSTRAINT deckcards_pkey PRIMARY KEY (id);


--
-- Name: decks decks_pkey; Type: CONSTRAINT; Schema: public; Owner: edgelords
--

ALTER TABLE ONLY public.decks
    ADD CONSTRAINT decks_pkey PRIMARY KEY (id);


--
-- Name: gamedecks gamedecks_pkey; Type: CONSTRAINT; Schema: public; Owner: edgelords
--

ALTER TABLE ONLY public.gamedecks
    ADD CONSTRAINT gamedecks_pkey PRIMARY KEY (id);


--
-- Name: games games_pkey; Type: CONSTRAINT; Schema: public; Owner: edgelords
--

ALTER TABLE ONLY public.games
    ADD CONSTRAINT games_pkey PRIMARY KEY (id);


--
-- Name: sessions sessions_pkey; Type: CONSTRAINT; Schema: public; Owner: edgelords
--

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT sessions_pkey PRIMARY KEY (id);


--
-- Name: sessions sessions_token_unique; Type: CONSTRAINT; Schema: public; Owner: edgelords
--

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT sessions_token_unique UNIQUE (token);


--
-- Name: users users_name_unique; Type: CONSTRAINT; Schema: public; Owner: edgelords
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_name_unique UNIQUE (name);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: edgelords
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: sessions_user; Type: INDEX; Schema: public; Owner: edgelords
--

CREATE INDEX sessions_user ON public.sessions USING btree ("user");


--
-- Name: deckcards fk_deckcards_deck_id; Type: FK CONSTRAINT; Schema: public; Owner: edgelords
--

ALTER TABLE ONLY public.deckcards
    ADD CONSTRAINT fk_deckcards_deck_id FOREIGN KEY (deck) REFERENCES public.decks(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: decks fk_decks_user_id; Type: FK CONSTRAINT; Schema: public; Owner: edgelords
--

ALTER TABLE ONLY public.decks
    ADD CONSTRAINT fk_decks_user_id FOREIGN KEY ("user") REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: gamedecks fk_gamedecks_deck_id; Type: FK CONSTRAINT; Schema: public; Owner: edgelords
--

ALTER TABLE ONLY public.gamedecks
    ADD CONSTRAINT fk_gamedecks_deck_id FOREIGN KEY (deck) REFERENCES public.decks(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: gamedecks fk_gamedecks_game_id; Type: FK CONSTRAINT; Schema: public; Owner: edgelords
--

ALTER TABLE ONLY public.gamedecks
    ADD CONSTRAINT fk_gamedecks_game_id FOREIGN KEY (game) REFERENCES public.games(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: gamedecks fk_gamedecks_user_id; Type: FK CONSTRAINT; Schema: public; Owner: edgelords
--

ALTER TABLE ONLY public.gamedecks
    ADD CONSTRAINT fk_gamedecks_user_id FOREIGN KEY ("user") REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: sessions fk_sessions_user_id; Type: FK CONSTRAINT; Schema: public; Owner: edgelords
--

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT fk_sessions_user_id FOREIGN KEY ("user") REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- PostgreSQL database dump complete
--

