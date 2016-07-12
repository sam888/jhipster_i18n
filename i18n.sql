--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: i18n; Tablespace: 
--

CREATE TABLE databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255)
);


ALTER TABLE databasechangelog OWNER TO i18n;

--
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: i18n; Tablespace: 
--

CREATE TABLE databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE databasechangeloglock OWNER TO i18n;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: i18n
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hibernate_sequence OWNER TO i18n;

--
-- Name: jhi_authority; Type: TABLE; Schema: public; Owner: i18n; Tablespace: 
--

CREATE TABLE jhi_authority (
    name character varying(50) NOT NULL
);


ALTER TABLE jhi_authority OWNER TO i18n;

--
-- Name: jhi_persistent_audit_event; Type: TABLE; Schema: public; Owner: i18n; Tablespace: 
--

CREATE TABLE jhi_persistent_audit_event (
    event_id bigint NOT NULL,
    principal character varying(255) NOT NULL,
    event_date timestamp without time zone,
    event_type character varying(255)
);


ALTER TABLE jhi_persistent_audit_event OWNER TO i18n;

--
-- Name: jhi_persistent_audit_event_event_id_seq; Type: SEQUENCE; Schema: public; Owner: i18n
--

CREATE SEQUENCE jhi_persistent_audit_event_event_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE jhi_persistent_audit_event_event_id_seq OWNER TO i18n;

--
-- Name: jhi_persistent_audit_event_event_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: i18n
--

ALTER SEQUENCE jhi_persistent_audit_event_event_id_seq OWNED BY jhi_persistent_audit_event.event_id;


--
-- Name: jhi_persistent_audit_evt_data; Type: TABLE; Schema: public; Owner: i18n; Tablespace: 
--

CREATE TABLE jhi_persistent_audit_evt_data (
    event_id bigint NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255)
);


ALTER TABLE jhi_persistent_audit_evt_data OWNER TO i18n;

--
-- Name: jhi_user; Type: TABLE; Schema: public; Owner: i18n; Tablespace: 
--

CREATE TABLE jhi_user (
    id bigint NOT NULL,
    login character varying(50) NOT NULL,
    password_hash character varying(60),
    first_name character varying(50),
    last_name character varying(50),
    email character varying(100),
    activated boolean NOT NULL,
    lang_key character varying(5),
    activation_key character varying(20),
    reset_key character varying(20),
    created_by character varying(50) NOT NULL,
    created_date timestamp without time zone NOT NULL,
    reset_date timestamp without time zone,
    last_modified_by character varying(50),
    last_modified_date timestamp without time zone
);


ALTER TABLE jhi_user OWNER TO i18n;

--
-- Name: jhi_user_authority; Type: TABLE; Schema: public; Owner: i18n; Tablespace: 
--

CREATE TABLE jhi_user_authority (
    user_id bigint NOT NULL,
    authority_name character varying(50) NOT NULL
);


ALTER TABLE jhi_user_authority OWNER TO i18n;

--
-- Name: jhi_user_id_seq; Type: SEQUENCE; Schema: public; Owner: i18n
--

CREATE SEQUENCE jhi_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE jhi_user_id_seq OWNER TO i18n;

--
-- Name: jhi_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: i18n
--

ALTER SEQUENCE jhi_user_id_seq OWNED BY jhi_user.id;


--
-- Name: key_value; Type: TABLE; Schema: public; Owner: i18n; Tablespace: 
--

CREATE TABLE key_value (
    key_value_id bigint NOT NULL,
    property character varying(255) NOT NULL,
    property_value character varying(255) NOT NULL,
    description character varying(255),
    resource_bundle_id bigint
);


ALTER TABLE key_value OWNER TO i18n;

--
-- Name: key_value_key_value_id_seq; Type: SEQUENCE; Schema: public; Owner: i18n
--

CREATE SEQUENCE key_value_key_value_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE key_value_key_value_id_seq OWNER TO i18n;

--
-- Name: key_value_key_value_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: i18n
--

ALTER SEQUENCE key_value_key_value_id_seq OWNED BY key_value.key_value_id;


--
-- Name: locale; Type: TABLE; Schema: public; Owner: i18n; Tablespace: 
--

CREATE TABLE locale (
    locale_id bigint NOT NULL,
    name character varying(255) NOT NULL,
    language_code character varying(255) NOT NULL,
    country_code character varying(255)
);


ALTER TABLE locale OWNER TO i18n;

--
-- Name: locale_locale_id_seq; Type: SEQUENCE; Schema: public; Owner: i18n
--

CREATE SEQUENCE locale_locale_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE locale_locale_id_seq OWNER TO i18n;

--
-- Name: locale_locale_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: i18n
--

ALTER SEQUENCE locale_locale_id_seq OWNED BY locale.locale_id;


--
-- Name: module; Type: TABLE; Schema: public; Owner: i18n; Tablespace: 
--

CREATE TABLE module (
    module_id bigint NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255)
);


ALTER TABLE module OWNER TO i18n;

--
-- Name: module_module_id_seq; Type: SEQUENCE; Schema: public; Owner: i18n
--

CREATE SEQUENCE module_module_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE module_module_id_seq OWNER TO i18n;

--
-- Name: module_module_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: i18n
--

ALTER SEQUENCE module_module_id_seq OWNED BY module.module_id;


--
-- Name: resource_bundle; Type: TABLE; Schema: public; Owner: i18n; Tablespace: 
--

CREATE TABLE resource_bundle (
    resource_bundle_id bigint NOT NULL,
    resource_bundle_name character varying(255),
    description character varying(100),
    status character varying(255),
    locale_id bigint NOT NULL,
    module_id bigint NOT NULL
);


ALTER TABLE resource_bundle OWNER TO i18n;

--
-- Name: resource_bundle_resource_bundle_id_seq; Type: SEQUENCE; Schema: public; Owner: i18n
--

CREATE SEQUENCE resource_bundle_resource_bundle_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE resource_bundle_resource_bundle_id_seq OWNER TO i18n;

--
-- Name: resource_bundle_resource_bundle_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: i18n
--

ALTER SEQUENCE resource_bundle_resource_bundle_id_seq OWNED BY resource_bundle.resource_bundle_id;


--
-- Name: event_id; Type: DEFAULT; Schema: public; Owner: i18n
--

ALTER TABLE ONLY jhi_persistent_audit_event ALTER COLUMN event_id SET DEFAULT nextval('jhi_persistent_audit_event_event_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: i18n
--

ALTER TABLE ONLY jhi_user ALTER COLUMN id SET DEFAULT nextval('jhi_user_id_seq'::regclass);


--
-- Name: key_value_id; Type: DEFAULT; Schema: public; Owner: i18n
--

ALTER TABLE ONLY key_value ALTER COLUMN key_value_id SET DEFAULT nextval('key_value_key_value_id_seq'::regclass);


--
-- Name: locale_id; Type: DEFAULT; Schema: public; Owner: i18n
--

ALTER TABLE ONLY locale ALTER COLUMN locale_id SET DEFAULT nextval('locale_locale_id_seq'::regclass);


--
-- Name: module_id; Type: DEFAULT; Schema: public; Owner: i18n
--

ALTER TABLE ONLY module ALTER COLUMN module_id SET DEFAULT nextval('module_module_id_seq'::regclass);


--
-- Name: resource_bundle_id; Type: DEFAULT; Schema: public; Owner: i18n
--

ALTER TABLE ONLY resource_bundle ALTER COLUMN resource_bundle_id SET DEFAULT nextval('resource_bundle_resource_bundle_id_seq'::regclass);


--
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: i18n
--

COPY databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels) FROM stdin;
00000000000000	jhipster	classpath:config/liquibase/changelog/00000000000000_initial_schema.xml	2016-07-08 00:16:33.467	1	EXECUTED	7:eda8cd7fd15284e6128be97bd8edea82	createSequence		\N	3.4.2	\N	\N
00000000000001	jhipster	classpath:config/liquibase/changelog/00000000000000_initial_schema.xml	2016-07-08 00:16:34.431	2	EXECUTED	7:4ad265793158c33b98858df1278027d3	createTable, createIndex (x2), createTable (x2), addPrimaryKey, addForeignKeyConstraint (x2), loadData, dropDefaultValue, loadData (x2), createTable (x2), addPrimaryKey, createIndex (x2), addForeignKeyConstraint		\N	3.4.2	\N	\N
20160629121218-1	jhipster	classpath:config/liquibase/changelog/20160629121218_added_entity_Locale.xml	2016-07-08 00:16:34.581	3	EXECUTED	7:ae62b16ce79e33d0fd477ec15b075621	createTable		\N	3.4.2	\N	\N
20160629121219-1	jhipster	classpath:config/liquibase/changelog/20160629121219_added_entity_Module.xml	2016-07-08 00:16:34.667	4	EXECUTED	7:7246880ddefe533865b86adc7facc72a	createTable		\N	3.4.2	\N	\N
20160629121220-1	jhipster	classpath:config/liquibase/changelog/20160629121220_added_entity_ResourceBundle.xml	2016-07-08 00:16:34.771	5	EXECUTED	7:3924358fe0acdbeb938a1c17b199859f	createTable		\N	3.4.2	\N	\N
20160629121221-1	jhipster	classpath:config/liquibase/changelog/20160629121221_added_entity_KeyValue.xml	2016-07-08 00:16:34.85	6	EXECUTED	7:f0cf9d2def70efaaa99bd4f6eff9d962	createTable		\N	3.4.2	\N	\N
20160629121220-2	jhipster	classpath:config/liquibase/changelog/20160629121220_added_entity_constraints_ResourceBundle.xml	2016-07-08 00:16:34.867	7	EXECUTED	7:f5456ef783f912e8e8f0dbed1ab3df4b	addForeignKeyConstraint (x2)		\N	3.4.2	\N	\N
20160629121221-2	jhipster	classpath:config/liquibase/changelog/20160629121221_added_entity_constraints_KeyValue.xml	2016-07-08 00:16:34.882	8	EXECUTED	7:7998d85f46c190f11e877a35357deb0f	addForeignKeyConstraint		\N	3.4.2	\N	\N
1467615754770-2	Sam (generated)	classpath:config/liquibase/changelog/20160704190219_changelog.xml	2016-07-08 00:16:34.922	9	EXECUTED	7:c8105a766544a90884f36f0eb5f88093	addUniqueConstraint		\N	3.4.2	\N	\N
1467615754770-12	Sam (generated)	classpath:config/liquibase/changelog/20160704190219_changelog.xml	2016-07-08 00:16:34.929	10	EXECUTED	7:0e590015cebaf9cfa397bff1099703a2	addNotNullConstraint		\N	3.4.2	\N	\N
1467615754770-13	Sam (generated)	classpath:config/liquibase/changelog/20160704190219_changelog.xml	2016-07-08 00:16:34.935	11	EXECUTED	7:2adeb50f0b48c2d614f6a89618029cef	addNotNullConstraint		\N	3.4.2	\N	\N
1467888310341-2	Sam (generated)	classpath:config/liquibase/changelog/20160704190219_changelog.xml	2016-07-08 00:16:34.985	12	EXECUTED	7:61762f778e98dc0bbac106d78d622974	addUniqueConstraint		\N	3.4.2	\N	\N
1467893366830-1	Sam (generated)	classpath:config/liquibase/changelog/20160704190219_changelog.xml	2016-07-08 00:16:35.035	13	EXECUTED	7:609e2c50c862bf4384d07c7028895e4d	addUniqueConstraint		\N	3.4.2	\N	\N
1467893366830-2	Sam (generated)	classpath:config/liquibase/changelog/20160704190219_changelog.xml	2016-07-08 00:16:35.094	14	EXECUTED	7:08665db9bf32f393019b323d4c9c58b6	addUniqueConstraint		\N	3.4.2	\N	\N
1467893366830-3	Sam (generated)	classpath:config/liquibase/changelog/20160704190219_changelog.xml	2016-07-08 00:16:35.145	15	EXECUTED	7:70da9fdb23b3f3883198323a15cf9eee	addUniqueConstraint		\N	3.4.2	\N	\N
\.


--
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: i18n
--

COPY databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
1	f	\N	\N
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: i18n
--

SELECT pg_catalog.setval('hibernate_sequence', 1063, true);


--
-- Data for Name: jhi_authority; Type: TABLE DATA; Schema: public; Owner: i18n
--

COPY jhi_authority (name) FROM stdin;
ROLE_ADMIN
ROLE_USER
\.


--
-- Data for Name: jhi_persistent_audit_event; Type: TABLE DATA; Schema: public; Owner: i18n
--

COPY jhi_persistent_audit_event (event_id, principal, event_date, event_type) FROM stdin;
1015	admin	2016-07-11 15:12:07.314	AUTHENTICATION_SUCCESS
1023	admin	2016-07-12 15:21:30.008	AUTHENTICATION_SUCCESS
\.


--
-- Name: jhi_persistent_audit_event_event_id_seq; Type: SEQUENCE SET; Schema: public; Owner: i18n
--

SELECT pg_catalog.setval('jhi_persistent_audit_event_event_id_seq', 1, false);


--
-- Data for Name: jhi_persistent_audit_evt_data; Type: TABLE DATA; Schema: public; Owner: i18n
--

COPY jhi_persistent_audit_evt_data (event_id, name, value) FROM stdin;
\.


--
-- Data for Name: jhi_user; Type: TABLE DATA; Schema: public; Owner: i18n
--

COPY jhi_user (id, login, password_hash, first_name, last_name, email, activated, lang_key, activation_key, reset_key, created_by, created_date, reset_date, last_modified_by, last_modified_date) FROM stdin;
1	system	$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG	System	System	system@localhost	t	en	\N	\N	system	2016-07-08 00:16:33.487	\N	\N	\N
2	anonymoususer	$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO	Anonymous	User	anonymous@localhost	t	en	\N	\N	system	2016-07-08 00:16:33.487	\N	\N	\N
3	admin	$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC	Administrator	Administrator	admin@localhost	t	en	\N	\N	system	2016-07-08 00:16:33.487	\N	\N	\N
4	user	$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K	User	User	user@localhost	t	en	\N	\N	system	2016-07-08 00:16:33.487	\N	\N	\N
\.


--
-- Data for Name: jhi_user_authority; Type: TABLE DATA; Schema: public; Owner: i18n
--

COPY jhi_user_authority (user_id, authority_name) FROM stdin;
1	ROLE_ADMIN
1	ROLE_USER
3	ROLE_ADMIN
3	ROLE_USER
4	ROLE_USER
\.


--
-- Name: jhi_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: i18n
--

SELECT pg_catalog.setval('jhi_user_id_seq', 1, false);


--
-- Data for Name: key_value; Type: TABLE DATA; Schema: public; Owner: i18n
--

COPY key_value (key_value_id, property, property_value, description, resource_bundle_id) FROM stdin;
1014	aa	bb	\N	1006
\.


--
-- Name: key_value_key_value_id_seq; Type: SEQUENCE SET; Schema: public; Owner: i18n
--

SELECT pg_catalog.setval('key_value_key_value_id_seq', 1, false);


--
-- Data for Name: locale; Type: TABLE DATA; Schema: public; Owner: i18n
--

COPY locale (locale_id, name, language_code, country_code) FROM stdin;
1001	English	en	\N
\.


--
-- Name: locale_locale_id_seq; Type: SEQUENCE SET; Schema: public; Owner: i18n
--

SELECT pg_catalog.setval('locale_locale_id_seq', 1, false);


--
-- Data for Name: module; Type: TABLE DATA; Schema: public; Owner: i18n
--

COPY module (module_id, name, description) FROM stdin;
1004	resourceBundle	\N
1019	global	For English verison of global.json
1021	module	\N
1024	locale	\N
1025	activate	\N
1026	audits	\N
1027	configuration	\N
1028	error	\N
1029	gateway	\N
1031	health	\N
1032	home	\N
1033	keyValue	\N
1034	login	\N
1035	logs	\N
1036	metrics	\N
1037	password	\N
1038	register	\N
1039	reset	\N
1040	sessions	\N
1041	resourceBundleStatus	\N
1042	settings	\N
1043	tracker	\N
1044	user-management	\N
\.


--
-- Name: module_module_id_seq; Type: SEQUENCE SET; Schema: public; Owner: i18n
--

SELECT pg_catalog.setval('module_module_id_seq', 1, false);


--
-- Data for Name: resource_bundle; Type: TABLE DATA; Schema: public; Owner: i18n
--

COPY resource_bundle (resource_bundle_id, resource_bundle_name, description, status, locale_id, module_id) FROM stdin;
1006	resourceBundle_en	\N	STATIC_JSON	1001	1004
1020	global_en	\N	STATIC_JSON	1001	1019
1022	module_en	\N	STATIC_JSON	1001	1021
1045	resourceBundleStatus_en	\N	STATIC_JSON	1001	1041
1046	activate_en	\N	STATIC_JSON	1001	1025
1047	audits_en	\N	STATIC_JSON	1001	1026
1048	configuration_en	\N	STATIC_JSON	1001	1027
1049	error_en	\N	STATIC_JSON	1001	1028
1050	gateway_en	\N	STATIC_JSON	1001	1029
1051	health_en	\N	STATIC_JSON	1001	1031
1052	home_en	\N	STATIC_JSON	1001	1032
1053	keyValue_en	\N	STATIC_JSON	1001	1033
1054	login_en	\N	STATIC_JSON	1001	1034
1055	logs_en	\N	STATIC_JSON	1001	1035
1056	metrics_en	\N	STATIC_JSON	1001	1036
1057	password_en	\N	STATIC_JSON	1001	1037
1058	register_en	\N	STATIC_JSON	1001	1038
1059	reset_en	\N	STATIC_JSON	1001	1039
1060	sessions_en	\N	STATIC_JSON	1001	1040
1061	settings_en	\N	STATIC_JSON	1001	1042
1062	tracker_en	\N	STATIC_JSON	1001	1043
1063	user-management_en	\N	STATIC_JSON	1001	1044
\.


--
-- Name: resource_bundle_resource_bundle_id_seq; Type: SEQUENCE SET; Schema: public; Owner: i18n
--

SELECT pg_catalog.setval('resource_bundle_resource_bundle_id_seq', 1, false);


--
-- Name: jhi_persistent_audit_evt_data_pkey; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY jhi_persistent_audit_evt_data
    ADD CONSTRAINT jhi_persistent_audit_evt_data_pkey PRIMARY KEY (event_id, name);


--
-- Name: jhi_user_authority_pkey; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY jhi_user_authority
    ADD CONSTRAINT jhi_user_authority_pkey PRIMARY KEY (user_id, authority_name);


--
-- Name: jhi_user_email_key; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY jhi_user
    ADD CONSTRAINT jhi_user_email_key UNIQUE (email);


--
-- Name: jhi_user_login_key; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY jhi_user
    ADD CONSTRAINT jhi_user_login_key UNIQUE (login);


--
-- Name: locale_language_code_country_code_key; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY locale
    ADD CONSTRAINT locale_language_code_country_code_key UNIQUE (language_code, country_code);


--
-- Name: locale_name_key; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY locale
    ADD CONSTRAINT locale_name_key UNIQUE (name);


--
-- Name: module_name_key; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY module
    ADD CONSTRAINT module_name_key UNIQUE (name);


--
-- Name: pk_databasechangeloglock; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY databasechangeloglock
    ADD CONSTRAINT pk_databasechangeloglock PRIMARY KEY (id);


--
-- Name: pk_jhi_authority; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY jhi_authority
    ADD CONSTRAINT pk_jhi_authority PRIMARY KEY (name);


--
-- Name: pk_jhi_persistent_audit_event; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY jhi_persistent_audit_event
    ADD CONSTRAINT pk_jhi_persistent_audit_event PRIMARY KEY (event_id);


--
-- Name: pk_jhi_user; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY jhi_user
    ADD CONSTRAINT pk_jhi_user PRIMARY KEY (id);


--
-- Name: pk_key_value; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY key_value
    ADD CONSTRAINT pk_key_value PRIMARY KEY (key_value_id);


--
-- Name: pk_locale; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY locale
    ADD CONSTRAINT pk_locale PRIMARY KEY (locale_id);


--
-- Name: pk_module; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY module
    ADD CONSTRAINT pk_module PRIMARY KEY (module_id);


--
-- Name: pk_resource_bundle; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT pk_resource_bundle PRIMARY KEY (resource_bundle_id);


--
-- Name: resource_bundle_locale_id_module_id_key; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT resource_bundle_locale_id_module_id_key UNIQUE (locale_id, module_id);


--
-- Name: resource_bundle_resource_bundle_name_key; Type: CONSTRAINT; Schema: public; Owner: i18n; Tablespace: 
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT resource_bundle_resource_bundle_name_key UNIQUE (resource_bundle_name);


--
-- Name: idx_persistent_audit_event; Type: INDEX; Schema: public; Owner: i18n; Tablespace: 
--

CREATE INDEX idx_persistent_audit_event ON jhi_persistent_audit_event USING btree (principal, event_date);


--
-- Name: idx_persistent_audit_evt_data; Type: INDEX; Schema: public; Owner: i18n; Tablespace: 
--

CREATE INDEX idx_persistent_audit_evt_data ON jhi_persistent_audit_evt_data USING btree (event_id);


--
-- Name: idx_user_email; Type: INDEX; Schema: public; Owner: i18n; Tablespace: 
--

CREATE UNIQUE INDEX idx_user_email ON jhi_user USING btree (email);


--
-- Name: idx_user_login; Type: INDEX; Schema: public; Owner: i18n; Tablespace: 
--

CREATE UNIQUE INDEX idx_user_login ON jhi_user USING btree (login);


--
-- Name: fk_authority_name; Type: FK CONSTRAINT; Schema: public; Owner: i18n
--

ALTER TABLE ONLY jhi_user_authority
    ADD CONSTRAINT fk_authority_name FOREIGN KEY (authority_name) REFERENCES jhi_authority(name);


--
-- Name: fk_evt_pers_audit_evt_data; Type: FK CONSTRAINT; Schema: public; Owner: i18n
--

ALTER TABLE ONLY jhi_persistent_audit_evt_data
    ADD CONSTRAINT fk_evt_pers_audit_evt_data FOREIGN KEY (event_id) REFERENCES jhi_persistent_audit_event(event_id);


--
-- Name: fk_keyvalue_resourcebundle; Type: FK CONSTRAINT; Schema: public; Owner: i18n
--

ALTER TABLE ONLY key_value
    ADD CONSTRAINT fk_keyvalue_resourcebundle FOREIGN KEY (resource_bundle_id) REFERENCES resource_bundle(resource_bundle_id);


--
-- Name: fk_resourcebundle_locale; Type: FK CONSTRAINT; Schema: public; Owner: i18n
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk_resourcebundle_locale FOREIGN KEY (locale_id) REFERENCES locale(locale_id);


--
-- Name: fk_resourcebundle_module; Type: FK CONSTRAINT; Schema: public; Owner: i18n
--

ALTER TABLE ONLY resource_bundle
    ADD CONSTRAINT fk_resourcebundle_module FOREIGN KEY (module_id) REFERENCES module(module_id);


--
-- Name: fk_user_id; Type: FK CONSTRAINT; Schema: public; Owner: i18n
--

ALTER TABLE ONLY jhi_user_authority
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES jhi_user(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

