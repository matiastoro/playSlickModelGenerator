
# --- !Ups


CREATE TABLE "reporting_entity" (
    id bigserial NOT NULL primary key,
  "reporting_entity" VARCHAR(255) NOT NULL,
  "reporting_entity_id" BIGINT NULL
);



CREATE TABLE "organization" (
    id bigserial NOT NULL primary key,
  "organization" VARCHAR(255) NOT NULL
);



CREATE TABLE "state_area" (
    id bigserial NOT NULL primary key,
  "state_area" VARCHAR(255) NOT NULL,
  "state_area_id" BIGINT NULL
);



CREATE TABLE "language" (
    id bigserial NOT NULL primary key,
  "language" VARCHAR(255) NOT NULL
);



CREATE TABLE "aerodrome_status" (
    id bigserial NOT NULL primary key,
  "status" VARCHAR(255) NOT NULL
);



CREATE TABLE "aerodrome_type" (
    id bigserial NOT NULL primary key,
  "tpe" VARCHAR(255) NOT NULL
);



CREATE TABLE "runway_surface_type" (
    id bigserial NOT NULL primary key,
  "surface_type" VARCHAR(255) NOT NULL
);



CREATE TABLE "runway" (
    id bigserial NOT NULL primary key,
  "runway" VARCHAR(255) NOT NULL,
  "runway_surface_type_id" BIGINT NOT NULL,
  "aerodrome_id" BIGINT NOT NULL
);



CREATE TABLE "helicopter_landing_area_surface_type" (
    id bigserial NOT NULL primary key,
  "surface_type" VARCHAR(255) NOT NULL
);



CREATE TABLE "helicopter_landing_area_type" (
    id bigserial NOT NULL primary key,
  "landing_area_type" VARCHAR(255) NOT NULL
);



CREATE TABLE "helicopter_landing_area_area_configuration" (
    id bigserial NOT NULL primary key,
  "area_configuration" VARCHAR(255) NOT NULL
);



CREATE TABLE "helicopter_landing_area_area_type" (
    id bigserial NOT NULL primary key,
  "helicopter_landing_area" VARCHAR(255) NOT NULL,
  "helicopter_landing_area_type_id" BIGINT NOT NULL,
  "helicopter_landing_area_area_configuration_id" BIGINT NOT NULL,
  "helicopter_landing_area_surface_type_id" BIGINT NOT NULL
);



CREATE TABLE "helicopter_landing_area" (
    id bigserial NOT NULL primary key,
  "helicopter_landing_area" VARCHAR(255) NOT NULL,
  "aerodrome_id" BIGINT NOT NULL
);



CREATE TABLE "aerodrome" (
    id bigserial NOT NULL primary key,
  "icaoCode" VARCHAR(255) NOT NULL,
  "aerodrome_status_id" BIGINT NOT NULL,
  "aerodrome_type_id" BIGINT NOT NULL,
  "latitude" VARCHAR(255) NULL,
  "longitude" VARCHAR(255) NULL,
  "elevation_above_msl" DOUBLE PRECISION NULL
);



CREATE TABLE "aerodrome_location" (
    id bigserial NOT NULL primary key,
  "location" VARCHAR(255) NOT NULL
);



CREATE TABLE "ocurrence_class" (
    id bigserial NOT NULL primary key,
  "ocurrence_class" VARCHAR(255) NOT NULL
);



CREATE TABLE "detection_phase" (
    id bigserial NOT NULL primary key,
  "detection_phase" VARCHAR(255) NOT NULL
);



CREATE TABLE "ocurrence_category" (
    id bigserial NOT NULL primary key,
  "code" VARCHAR(255) NOT NULL,
  "ocurrence_category" VARCHAR(255) NOT NULL
);



CREATE TABLE "neo_aerodrome_ocurrence_category" (
    id bigserial NOT NULL primary key,
  "neo_aerodrome_id" BIGINT NOT NULL,
  "ocurrence_category_id" BIGINT NOT NULL
);



CREATE TABLE "neo_event_type" (
    id bigserial NOT NULL primary key,
  "event_type" VARCHAR(255) NOT NULL
);



CREATE TABLE "phase" (
    id bigserial NOT NULL primary key,
  "phase" VARCHAR(255) NOT NULL,
  "phase_id" BIGINT NULL
);



CREATE TABLE "neo_event_type_phase" (
    id bigserial NOT NULL primary key,
  "neo_aerodrome_id" BIGINT NOT NULL,
  "neo_event_type_id" BIGINT NOT NULL,
  "phase_id" BIGINT NOT NULL
);



CREATE TABLE "neo_report_status" (
    id bigserial NOT NULL primary key,
  "report_status" VARCHAR(255) NOT NULL
);



CREATE TABLE "neo_aerodrome" (
    id bigserial NOT NULL primary key,
  "reporting_entity_id" BIGINT NOT NULL,
  "reporting_entity_name" VARCHAR(255) NOT NULL,
  "when" TIMESTAMP WITH TIME ZONE NOT NULL,
  "when_local" TIMESTAMP WITH TIME ZONE NULL,
  "where_state_area_id" BIGINT NOT NULL,
  "where_location_name" VARCHAR(255) NOT NULL,
  "where_latitude_occ" VARCHAR(255) NULL,
  "where_longitude_occ" VARCHAR(255) NULL,
  "what_headline" VARCHAR(255) NOT NULL,
  "what_narrative_languaje" VARCHAR(255) NOT NULL,
  "what_narrative" VARCHAR(255) NOT NULL,
  "aerodrome_id" BIGINT NOT NULL,
  "aerodrome_location_id" BIGINT NOT NULL,
  "aerodrome_status_id" BIGINT NOT NULL,
  "aerodrome_type_id" BIGINT NOT NULL,
  "latitude" VARCHAR(255) NULL,
  "longitude" VARCHAR(255) NULL,
  "runway_id" BIGINT NULL,
  "runway_surface_type_id" BIGINT NULL,
  "helicopter_landing_area_type_id" BIGINT NULL,
  "helicopter_landing_area_area_configuration_id" BIGINT NULL,
  "helicopter_landing_area_surface_type_id" BIGINT NULL,
  "ocurrence_class_id" BIGINT NOT NULL,
  "detection_phase_id" BIGINT NOT NULL,
  "risk_classification" VARCHAR(255) NOT NULL,
  "risk_methodology" VARCHAR(255) NULL,
  "risk_assessment" VARCHAR(255) NULL,
  "analysis_follow_up" VARCHAR(255) NULL,
  "corrective_actions" VARCHAR(255) NULL,
  "conclusions" VARCHAR(255) NULL,
  "report_status_id" BIGINT NULL,
  "report_version" VARCHAR(255) NULL,
  "attachments" VARCHAR(255) NULL
);



CREATE TABLE "aircraft_manufacturer_model" (
    id bigserial NOT NULL primary key,
  "manufacturer_model" VARCHAR(255) NOT NULL,
  "aircraft_manufacturer_model_id" BIGINT NULL
);



CREATE TABLE "aircraft_category" (
    id bigserial NOT NULL primary key,
  "category" VARCHAR(255) NOT NULL,
  "aircraft_category_id" BIGINT NULL
);



CREATE TABLE "aircraft_mass_group" (
    id bigserial NOT NULL primary key,
  "mass_group" VARCHAR(255) NOT NULL
);



CREATE TABLE "aircraft_propulsion_type" (
    id bigserial NOT NULL primary key,
  "propulsion_type" VARCHAR(255) NOT NULL
);



CREATE TABLE "aircraft_wake_turb_category" (
    id bigserial NOT NULL primary key,
  "wake_turb_category" VARCHAR(255) NOT NULL
);



CREATE TABLE "aircraft_landing_gear_type" (
    id bigserial NOT NULL primary key,
  "landing_gear_type" VARCHAR(255) NOT NULL,
  "aircraft_landing_gear_type_id" BIGINT NULL
);



CREATE TABLE "operator" (
    id bigserial NOT NULL primary key,
  "operator" VARCHAR(255) NOT NULL,
  "operator_type_id" BIGINT NULL
);



CREATE TABLE "operator_type" (
    id bigserial NOT NULL primary key,
  "operator_type" VARCHAR(255) NOT NULL,
  "operator_type_id" BIGINT NULL
);



CREATE TABLE "operation_type" (
    id bigserial NOT NULL primary key,
  "operation_type" VARCHAR(255) NOT NULL,
  "operation_type_id" BIGINT NULL
);



CREATE TABLE "type_of_airspeed" (
    id bigserial NOT NULL primary key,
  "type_of_airspeed" VARCHAR(255) NOT NULL
);



CREATE TABLE "flight_rule" (
    id bigserial NOT NULL primary key,
  "flight_rule" VARCHAR(255) NOT NULL
);



CREATE TABLE "traffic_type" (
    id bigserial NOT NULL primary key,
  "traffic_type" VARCHAR(255) NOT NULL
);



CREATE TABLE "instrument_appr_type" (
    id bigserial NOT NULL primary key,
  "instrument_appr_type" VARCHAR(255) NOT NULL
);



CREATE TABLE "flight_crew_category" (
    id bigserial NOT NULL primary key,
  "category" VARCHAR(255) NOT NULL
);



CREATE TABLE "license_type" (
    id bigserial NOT NULL primary key,
  "license_type" VARCHAR(255) NOT NULL,
  "license_type_id" BIGINT NULL
);



CREATE TABLE "license_issued_by" (
    id bigserial NOT NULL primary key,
  "license_issued_by" VARCHAR(255) NOT NULL,
  "license_issued_by_id" BIGINT NULL
);



CREATE TABLE "license_validity" (
    id bigserial NOT NULL primary key,
  "license_validity" VARCHAR(255) NOT NULL
);



CREATE TABLE "license_ratings" (
    id bigserial NOT NULL primary key,
  "license_ratings" VARCHAR(255) NOT NULL
);



CREATE TABLE "flight_crew_license" (
    id bigserial NOT NULL primary key,
  "flight_crew_id" BIGINT NOT NULL,
  "license_type_id" BIGINT NOT NULL,
  "license_issued_by_id" BIGINT NULL,
  "date_of_license" DATE NULL,
  "license_validity_id" BIGINT NULL,
  "license_ratings_id" BIGINT NULL
);



CREATE TABLE "flight_crew" (
    id bigserial NOT NULL primary key,
  "neo_flight_operation_id" BIGINT NOT NULL,
  "category_id" BIGINT NOT NULL,
  "experience_this" DOUBLE PRECISION NULL,
  "experience_all" DOUBLE PRECISION NULL,
  "duty_last_24_hours" DOUBLE PRECISION NULL,
  "rest_before_duty" DOUBLE PRECISION NULL
);



CREATE TABLE "neo_flight_operation_ocurrence_category" (
    id bigserial NOT NULL primary key,
  "neo_flight_operation_id" BIGINT NOT NULL,
  "ocurrence_category_id" BIGINT NOT NULL
);



CREATE TABLE "neo_flight_operation_event_type_phase" (
    id bigserial NOT NULL primary key,
  "neo_flight_operation_id" BIGINT NOT NULL,
  "neo_event_type_id" BIGINT NOT NULL,
  "phase_id" BIGINT NOT NULL
);



CREATE TABLE "neo_flight_operation" (
    id bigserial NOT NULL primary key,
  "reporting_entity_id" BIGINT NOT NULL,
  "reporting_entity_name" VARCHAR(255) NOT NULL,
  "when" TIMESTAMP WITH TIME ZONE NOT NULL,
  "when_local" TIMESTAMP WITH TIME ZONE NULL,
  "where_state_area_id" BIGINT NOT NULL,
  "where_location_name" VARCHAR(255) NOT NULL,
  "where_latitude_occ" VARCHAR(255) NULL,
  "where_longitude_occ" VARCHAR(255) NULL,
  "what_headline" VARCHAR(255) NOT NULL,
  "what_narrative_languaje" VARCHAR(255) NOT NULL,
  "what_narrative" VARCHAR(255) NOT NULL,
  "state_of_registry_id" BIGINT NOT NULL,
  "registration" VARCHAR(255) NOT NULL,
  "manufacturer_model_id" BIGINT NOT NULL,
  "serial_number" VARCHAR(255) NOT NULL,
  "year_built" INTEGER NULL,
  "category_id" BIGINT NOT NULL,
  "mass_group_id" BIGINT NOT NULL,
  "propulsion_type_id" BIGINT NOT NULL,
  "wake_turb_category_id" BIGINT NULL,
  "number_of_engines" INTEGER NULL,
  "landing_gear_type_id" BIGINT NULL,
  "maximum_mass" DOUBLE PRECISION NULL,
  "departure_id" BIGINT NOT NULL,
  "destination_id" BIGINT NOT NULL,
  "phase_id" BIGINT NOT NULL,
  "operator_id" BIGINT NOT NULL,
  "operation_type_id" BIGINT NOT NULL,
  "callsign" VARCHAR(255) NOT NULL,
  "flight_number" VARCHAR(255) NULL,
  "number_persons" INTEGER NULL,
  "aircraft_altitude" DOUBLE PRECISION NULL,
  "aircraft_flight_level" VARCHAR(255) NULL,
  "speed" DOUBLE PRECISION NULL,
  "type_of_airspeed_id" BIGINT NULL,
  "flight_rule_id" BIGINT NULL,
  "traffic_type_id" BIGINT NULL,
  "instrument_appr_type_id" BIGINT NULL,
  "ocurrence_class_id" BIGINT NOT NULL,
  "detection_phase_id" BIGINT NOT NULL,
  "risk_classification" VARCHAR(255) NOT NULL,
  "risk_methodology" VARCHAR(255) NULL,
  "risk_assessment" VARCHAR(255) NULL,
  "analysis_follow_up" VARCHAR(255) NULL,
  "corrective_actions" VARCHAR(255) NULL,
  "conclusions" VARCHAR(255) NULL,
  "report_status_id" BIGINT NULL,
  "report_version" VARCHAR(255) NULL,
  "attachments" VARCHAR(255) NULL
);



CREATE TABLE "eccairs_ocurrence_category" (
    id bigserial NOT NULL primary key,
  "eccairs_id" BIGINT NOT NULL,
  "ocurrence_category_id" BIGINT NOT NULL
);



CREATE TABLE "injury_level" (
    id bigserial NOT NULL primary key,
  "injury_level" VARCHAR(255) NOT NULL
);



CREATE TABLE "weather_condition" (
    id bigserial NOT NULL primary key,
  "weather_condition" VARCHAR(255) NOT NULL
);



CREATE TABLE "eccairs" (
    id bigserial NOT NULL primary key,
  "number" VARCHAR(255) NOT NULL,
  "date" DATE NOT NULL,
  "state_area_id" BIGINT NOT NULL,
  "ocurrence_class_id" BIGINT NOT NULL,
  "injury_level_id" BIGINT NULL,
  "mass_group_id" BIGINT NULL,
  "category_id" BIGINT NULL,
  "registry" VARCHAR(255) NULL,
  "operation_type_id" BIGINT NULL,
  "operator_type_id" BIGINT NULL,
  "operator_id" BIGINT NULL,
  "weather_condition_id" BIGINT NULL
);

CREATE INDEX "reporting_entity_reporting_entity_id_idx"
  ON "reporting_entity"
  USING btree
  ("reporting_entity_id");


CREATE INDEX "state_area_state_area_id_idx"
  ON "state_area"
  USING btree
  ("state_area_id");





CREATE INDEX "runway_runway_surface_type_id_idx"
  ON "runway"
  USING btree
  ("runway_surface_type_id");

CREATE INDEX "runway_aerodrome_id_idx"
  ON "runway"
  USING btree
  ("aerodrome_id");




CREATE INDEX "helicopter_landing_area_area_type_helicopter_landing_area_type_id_idx"
  ON "helicopter_landing_area_area_type"
  USING btree
  ("helicopter_landing_area_type_id");

CREATE INDEX "helicopter_landing_area_area_type_helicopter_landing_area_area_configuration_id_idx"
  ON "helicopter_landing_area_area_type"
  USING btree
  ("helicopter_landing_area_area_configuration_id");

CREATE INDEX "helicopter_landing_area_area_type_helicopter_landing_area_surface_type_id_idx"
  ON "helicopter_landing_area_area_type"
  USING btree
  ("helicopter_landing_area_surface_type_id");

CREATE INDEX "helicopter_landing_area_aerodrome_id_idx"
  ON "helicopter_landing_area"
  USING btree
  ("aerodrome_id");

CREATE INDEX "aerodrome_aerodrome_status_id_idx"
  ON "aerodrome"
  USING btree
  ("aerodrome_status_id");

CREATE INDEX "aerodrome_aerodrome_type_id_idx"
  ON "aerodrome"
  USING btree
  ("aerodrome_type_id");





CREATE INDEX "neo_aerodrome_ocurrence_category_neo_aerodrome_id_idx"
  ON "neo_aerodrome_ocurrence_category"
  USING btree
  ("neo_aerodrome_id");

CREATE INDEX "neo_aerodrome_ocurrence_category_ocurrence_category_id_idx"
  ON "neo_aerodrome_ocurrence_category"
  USING btree
  ("ocurrence_category_id");


CREATE INDEX "phase_phase_id_idx"
  ON "phase"
  USING btree
  ("phase_id");

CREATE INDEX "neo_event_type_phase_neo_aerodrome_id_idx"
  ON "neo_event_type_phase"
  USING btree
  ("neo_aerodrome_id");

CREATE INDEX "neo_event_type_phase_neo_event_type_id_idx"
  ON "neo_event_type_phase"
  USING btree
  ("neo_event_type_id");

CREATE INDEX "neo_event_type_phase_phase_id_idx"
  ON "neo_event_type_phase"
  USING btree
  ("phase_id");


CREATE INDEX "neo_aerodrome_reporting_entity_id_idx"
  ON "neo_aerodrome"
  USING btree
  ("reporting_entity_id");

CREATE INDEX "neo_aerodrome_where_state_area_id_idx"
  ON "neo_aerodrome"
  USING btree
  ("where_state_area_id");

CREATE INDEX "neo_aerodrome_aerodrome_id_idx"
  ON "neo_aerodrome"
  USING btree
  ("aerodrome_id");

CREATE INDEX "neo_aerodrome_aerodrome_location_id_idx"
  ON "neo_aerodrome"
  USING btree
  ("aerodrome_location_id");

CREATE INDEX "neo_aerodrome_aerodrome_status_id_idx"
  ON "neo_aerodrome"
  USING btree
  ("aerodrome_status_id");

CREATE INDEX "neo_aerodrome_aerodrome_type_id_idx"
  ON "neo_aerodrome"
  USING btree
  ("aerodrome_type_id");

CREATE INDEX "neo_aerodrome_runway_id_idx"
  ON "neo_aerodrome"
  USING btree
  ("runway_id");

CREATE INDEX "neo_aerodrome_runway_surface_type_id_idx"
  ON "neo_aerodrome"
  USING btree
  ("runway_surface_type_id");

CREATE INDEX "neo_aerodrome_helicopter_landing_area_type_id_idx"
  ON "neo_aerodrome"
  USING btree
  ("helicopter_landing_area_type_id");

CREATE INDEX "neo_aerodrome_helicopter_landing_area_area_configuration_id_idx"
  ON "neo_aerodrome"
  USING btree
  ("helicopter_landing_area_area_configuration_id");

CREATE INDEX "neo_aerodrome_helicopter_landing_area_surface_type_id_idx"
  ON "neo_aerodrome"
  USING btree
  ("helicopter_landing_area_surface_type_id");

CREATE INDEX "neo_aerodrome_ocurrence_class_id_idx"
  ON "neo_aerodrome"
  USING btree
  ("ocurrence_class_id");

CREATE INDEX "neo_aerodrome_detection_phase_id_idx"
  ON "neo_aerodrome"
  USING btree
  ("detection_phase_id");



CREATE INDEX "neo_aerodrome_report_status_id_idx"
  ON "neo_aerodrome"
  USING btree
  ("report_status_id");

CREATE INDEX "aircraft_manufacturer_model_aircraft_manufacturer_model_id_idx"
  ON "aircraft_manufacturer_model"
  USING btree
  ("aircraft_manufacturer_model_id");

CREATE INDEX "aircraft_category_aircraft_category_id_idx"
  ON "aircraft_category"
  USING btree
  ("aircraft_category_id");




CREATE INDEX "aircraft_landing_gear_type_aircraft_landing_gear_type_id_idx"
  ON "aircraft_landing_gear_type"
  USING btree
  ("aircraft_landing_gear_type_id");

CREATE INDEX "operator_operator_type_id_idx"
  ON "operator"
  USING btree
  ("operator_type_id");

CREATE INDEX "operator_type_operator_type_id_idx"
  ON "operator_type"
  USING btree
  ("operator_type_id");

CREATE INDEX "operation_type_operation_type_id_idx"
  ON "operation_type"
  USING btree
  ("operation_type_id");






CREATE INDEX "license_type_license_type_id_idx"
  ON "license_type"
  USING btree
  ("license_type_id");

CREATE INDEX "license_issued_by_license_issued_by_id_idx"
  ON "license_issued_by"
  USING btree
  ("license_issued_by_id");



CREATE INDEX "flight_crew_license_flight_crew_id_idx"
  ON "flight_crew_license"
  USING btree
  ("flight_crew_id");

CREATE INDEX "flight_crew_license_license_type_id_idx"
  ON "flight_crew_license"
  USING btree
  ("license_type_id");

CREATE INDEX "flight_crew_license_license_issued_by_id_idx"
  ON "flight_crew_license"
  USING btree
  ("license_issued_by_id");

CREATE INDEX "flight_crew_license_license_validity_id_idx"
  ON "flight_crew_license"
  USING btree
  ("license_validity_id");

CREATE INDEX "flight_crew_license_license_ratings_id_idx"
  ON "flight_crew_license"
  USING btree
  ("license_ratings_id");

CREATE INDEX "flight_crew_neo_flight_operation_id_idx"
  ON "flight_crew"
  USING btree
  ("neo_flight_operation_id");

CREATE INDEX "flight_crew_category_id_idx"
  ON "flight_crew"
  USING btree
  ("category_id");

CREATE INDEX "neo_flight_operation_ocurrence_category_neo_flight_operation_id_idx"
  ON "neo_flight_operation_ocurrence_category"
  USING btree
  ("neo_flight_operation_id");

CREATE INDEX "neo_flight_operation_ocurrence_category_ocurrence_category_id_idx"
  ON "neo_flight_operation_ocurrence_category"
  USING btree
  ("ocurrence_category_id");

CREATE INDEX "neo_flight_operation_event_type_phase_neo_flight_operation_id_idx"
  ON "neo_flight_operation_event_type_phase"
  USING btree
  ("neo_flight_operation_id");

CREATE INDEX "neo_flight_operation_event_type_phase_neo_event_type_id_idx"
  ON "neo_flight_operation_event_type_phase"
  USING btree
  ("neo_event_type_id");

CREATE INDEX "neo_flight_operation_event_type_phase_phase_id_idx"
  ON "neo_flight_operation_event_type_phase"
  USING btree
  ("phase_id");

CREATE INDEX "neo_flight_operation_reporting_entity_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("reporting_entity_id");

CREATE INDEX "neo_flight_operation_where_state_area_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("where_state_area_id");

CREATE INDEX "neo_flight_operation_state_of_registry_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("state_of_registry_id");

CREATE INDEX "neo_flight_operation_manufacturer_model_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("manufacturer_model_id");

CREATE INDEX "neo_flight_operation_category_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("category_id");

CREATE INDEX "neo_flight_operation_mass_group_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("mass_group_id");

CREATE INDEX "neo_flight_operation_propulsion_type_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("propulsion_type_id");

CREATE INDEX "neo_flight_operation_wake_turb_category_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("wake_turb_category_id");

CREATE INDEX "neo_flight_operation_landing_gear_type_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("landing_gear_type_id");

CREATE INDEX "neo_flight_operation_departure_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("departure_id");

CREATE INDEX "neo_flight_operation_destination_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("destination_id");

CREATE INDEX "neo_flight_operation_phase_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("phase_id");

CREATE INDEX "neo_flight_operation_operator_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("operator_id");

CREATE INDEX "neo_flight_operation_operation_type_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("operation_type_id");

CREATE INDEX "neo_flight_operation_type_of_airspeed_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("type_of_airspeed_id");

CREATE INDEX "neo_flight_operation_flight_rule_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("flight_rule_id");

CREATE INDEX "neo_flight_operation_traffic_type_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("traffic_type_id");

CREATE INDEX "neo_flight_operation_instrument_appr_type_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("instrument_appr_type_id");

CREATE INDEX "neo_flight_operation_ocurrence_class_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("ocurrence_class_id");

CREATE INDEX "neo_flight_operation_detection_phase_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("detection_phase_id");



CREATE INDEX "neo_flight_operation_report_status_id_idx"
  ON "neo_flight_operation"
  USING btree
  ("report_status_id");

CREATE INDEX "eccairs_ocurrence_category_eccairs_id_idx"
  ON "eccairs_ocurrence_category"
  USING btree
  ("eccairs_id");

CREATE INDEX "eccairs_ocurrence_category_ocurrence_category_id_idx"
  ON "eccairs_ocurrence_category"
  USING btree
  ("ocurrence_category_id");



CREATE INDEX "eccairs_state_area_id_idx"
  ON "eccairs"
  USING btree
  ("state_area_id");

CREATE INDEX "eccairs_ocurrence_class_id_idx"
  ON "eccairs"
  USING btree
  ("ocurrence_class_id");

CREATE INDEX "eccairs_injury_level_id_idx"
  ON "eccairs"
  USING btree
  ("injury_level_id");

CREATE INDEX "eccairs_mass_group_id_idx"
  ON "eccairs"
  USING btree
  ("mass_group_id");

CREATE INDEX "eccairs_category_id_idx"
  ON "eccairs"
  USING btree
  ("category_id");

CREATE INDEX "eccairs_operation_type_id_idx"
  ON "eccairs"
  USING btree
  ("operation_type_id");

CREATE INDEX "eccairs_operator_type_id_idx"
  ON "eccairs"
  USING btree
  ("operator_type_id");

CREATE INDEX "eccairs_operator_id_idx"
  ON "eccairs"
  USING btree
  ("operator_id");

CREATE INDEX "eccairs_weather_condition_id_idx"
  ON "eccairs"
  USING btree
  ("weather_condition_id");

ALTER TABLE IF EXISTS "reporting_entity" ADD CONSTRAINT "reporting_entity_reporting_entity_fk_1" FOREIGN KEY ("reporting_entity_id")
  REFERENCES "reporting_entity" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE IF EXISTS "state_area" ADD CONSTRAINT "state_area_state_area_fk_1" FOREIGN KEY ("state_area_id")
  REFERENCES "state_area" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;




ALTER TABLE IF EXISTS "runway" ADD CONSTRAINT "runway_runway_surface_type_fk_1" FOREIGN KEY ("runway_surface_type_id")
  REFERENCES "runway_surface_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "runway" ADD CONSTRAINT "runway_aerodrome_fk_1" FOREIGN KEY ("aerodrome_id")
  REFERENCES "aerodrome" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;



ALTER TABLE IF EXISTS "helicopter_landing_area_area_type" ADD CONSTRAINT "helicopter_landing_area_area_type_helicopter_landing_area_type_fk_1" FOREIGN KEY ("helicopter_landing_area_type_id")
  REFERENCES "helicopter_landing_area_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "helicopter_landing_area_area_type" ADD CONSTRAINT "helicopter_landing_area_area_type_helicopter_landing_area_area_configuration_fk_1" FOREIGN KEY ("helicopter_landing_area_area_configuration_id")
  REFERENCES "helicopter_landing_area_area_configuration" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "helicopter_landing_area_area_type" ADD CONSTRAINT "helicopter_landing_area_area_type_helicopter_landing_area_surface_type_fk_1" FOREIGN KEY ("helicopter_landing_area_surface_type_id")
  REFERENCES "helicopter_landing_area_surface_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "helicopter_landing_area" ADD CONSTRAINT "helicopter_landing_area_aerodrome_fk_1" FOREIGN KEY ("aerodrome_id")
  REFERENCES "aerodrome" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "aerodrome" ADD CONSTRAINT "aerodrome_aerodrome_status_fk_1" FOREIGN KEY ("aerodrome_status_id")
  REFERENCES "aerodrome_status" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "aerodrome" ADD CONSTRAINT "aerodrome_aerodrome_type_fk_1" FOREIGN KEY ("aerodrome_type_id")
  REFERENCES "aerodrome_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;




ALTER TABLE IF EXISTS "neo_aerodrome_ocurrence_category" ADD CONSTRAINT "neo_aerodrome_ocurrence_category_neo_aerodrome_fk_1" FOREIGN KEY ("neo_aerodrome_id")
  REFERENCES "neo_aerodrome" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "neo_aerodrome_ocurrence_category" ADD CONSTRAINT "neo_aerodrome_ocurrence_category_ocurrence_category_fk_1" FOREIGN KEY ("ocurrence_category_id")
  REFERENCES "ocurrence_category" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE IF EXISTS "phase" ADD CONSTRAINT "phase_phase_fk_1" FOREIGN KEY ("phase_id")
  REFERENCES "phase" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "neo_event_type_phase" ADD CONSTRAINT "neo_event_type_phase_neo_aerodrome_fk_1" FOREIGN KEY ("neo_aerodrome_id")
  REFERENCES "neo_aerodrome" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "neo_event_type_phase" ADD CONSTRAINT "neo_event_type_phase_neo_event_type_fk_1" FOREIGN KEY ("neo_event_type_id")
  REFERENCES "neo_event_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "neo_event_type_phase" ADD CONSTRAINT "neo_event_type_phase_phase_fk_1" FOREIGN KEY ("phase_id")
  REFERENCES "phase" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE IF EXISTS "neo_aerodrome" ADD CONSTRAINT "neo_aerodrome_reporting_entity_fk_1" FOREIGN KEY ("reporting_entity_id")
  REFERENCES "reporting_entity" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "neo_aerodrome" ADD CONSTRAINT "neo_aerodrome_state_area_fk_1" FOREIGN KEY ("where_state_area_id")
  REFERENCES "state_area" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "neo_aerodrome" ADD CONSTRAINT "neo_aerodrome_aerodrome_fk_1" FOREIGN KEY ("aerodrome_id")
  REFERENCES "aerodrome" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "neo_aerodrome" ADD CONSTRAINT "neo_aerodrome_aerodrome_location_fk_1" FOREIGN KEY ("aerodrome_location_id")
  REFERENCES "aerodrome_location" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_aerodrome" ADD CONSTRAINT "neo_aerodrome_aerodrome_status_fk_1" FOREIGN KEY ("aerodrome_status_id")
  REFERENCES "aerodrome_status" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_aerodrome" ADD CONSTRAINT "neo_aerodrome_aerodrome_type_fk_1" FOREIGN KEY ("aerodrome_type_id")
  REFERENCES "aerodrome_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_aerodrome" ADD CONSTRAINT "neo_aerodrome_runway_fk_1" FOREIGN KEY ("runway_id")
  REFERENCES "runway" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_aerodrome" ADD CONSTRAINT "neo_aerodrome_runway_surface_type_fk_1" FOREIGN KEY ("runway_surface_type_id")
  REFERENCES "runway_surface_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_aerodrome" ADD CONSTRAINT "neo_aerodrome_helicopter_landing_area_type_fk_1" FOREIGN KEY ("helicopter_landing_area_type_id")
  REFERENCES "helicopter_landing_area_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_aerodrome" ADD CONSTRAINT "neo_aerodrome_helicopter_landing_area_area_configuration_fk_1" FOREIGN KEY ("helicopter_landing_area_area_configuration_id")
  REFERENCES "helicopter_landing_area_area_configuration" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_aerodrome" ADD CONSTRAINT "neo_aerodrome_helicopter_landing_area_surface_type_fk_1" FOREIGN KEY ("helicopter_landing_area_surface_type_id")
  REFERENCES "helicopter_landing_area_surface_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_aerodrome" ADD CONSTRAINT "neo_aerodrome_ocurrence_class_fk_1" FOREIGN KEY ("ocurrence_class_id")
  REFERENCES "ocurrence_class" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_aerodrome" ADD CONSTRAINT "neo_aerodrome_detection_phase_fk_1" FOREIGN KEY ("detection_phase_id")
  REFERENCES "detection_phase" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;


ALTER TABLE IF EXISTS "neo_aerodrome" ADD CONSTRAINT "neo_aerodrome_neo_report_status_fk_1" FOREIGN KEY ("report_status_id")
  REFERENCES "neo_report_status" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "aircraft_manufacturer_model" ADD CONSTRAINT "aircraft_manufacturer_model_aircraft_manufacturer_model_fk_1" FOREIGN KEY ("aircraft_manufacturer_model_id")
  REFERENCES "aircraft_manufacturer_model" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "aircraft_category" ADD CONSTRAINT "aircraft_category_aircraft_category_fk_1" FOREIGN KEY ("aircraft_category_id")
  REFERENCES "aircraft_category" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;



ALTER TABLE IF EXISTS "aircraft_landing_gear_type" ADD CONSTRAINT "aircraft_landing_gear_type_aircraft_landing_gear_type_fk_1" FOREIGN KEY ("aircraft_landing_gear_type_id")
  REFERENCES "aircraft_landing_gear_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "operator" ADD CONSTRAINT "operator_operator_type_fk_1" FOREIGN KEY ("operator_type_id")
  REFERENCES "operator_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "operator_type" ADD CONSTRAINT "operator_type_operator_type_fk_1" FOREIGN KEY ("operator_type_id")
  REFERENCES "operator_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "operation_type" ADD CONSTRAINT "operation_type_operation_type_fk_1" FOREIGN KEY ("operation_type_id")
  REFERENCES "operation_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;





ALTER TABLE IF EXISTS "license_type" ADD CONSTRAINT "license_type_license_type_fk_1" FOREIGN KEY ("license_type_id")
  REFERENCES "license_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "license_issued_by" ADD CONSTRAINT "license_issued_by_license_issued_by_fk_1" FOREIGN KEY ("license_issued_by_id")
  REFERENCES "license_issued_by" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;


ALTER TABLE IF EXISTS "flight_crew_license" ADD CONSTRAINT "flight_crew_license_flight_crew_fk_1" FOREIGN KEY ("flight_crew_id")
  REFERENCES "flight_crew" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "flight_crew_license" ADD CONSTRAINT "flight_crew_license_license_type_fk_1" FOREIGN KEY ("license_type_id")
  REFERENCES "license_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "flight_crew_license" ADD CONSTRAINT "flight_crew_license_license_issued_by_fk_1" FOREIGN KEY ("license_issued_by_id")
  REFERENCES "license_issued_by" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "flight_crew_license" ADD CONSTRAINT "flight_crew_license_license_validity_fk_1" FOREIGN KEY ("license_validity_id")
  REFERENCES "license_validity" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "flight_crew_license" ADD CONSTRAINT "flight_crew_license_license_ratings_fk_1" FOREIGN KEY ("license_ratings_id")
  REFERENCES "license_ratings" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "flight_crew" ADD CONSTRAINT "flight_crew_neo_flight_operation_fk_1" FOREIGN KEY ("neo_flight_operation_id")
  REFERENCES "neo_flight_operation" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "flight_crew" ADD CONSTRAINT "flight_crew_flight_crew_category_fk_1" FOREIGN KEY ("category_id")
  REFERENCES "flight_crew_category" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "neo_flight_operation_ocurrence_category" ADD CONSTRAINT "neo_flight_operation_ocurrence_category_neo_flight_operation_fk_1" FOREIGN KEY ("neo_flight_operation_id")
  REFERENCES "neo_flight_operation" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "neo_flight_operation_ocurrence_category" ADD CONSTRAINT "neo_flight_operation_ocurrence_category_ocurrence_category_fk_1" FOREIGN KEY ("ocurrence_category_id")
  REFERENCES "ocurrence_category" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "neo_flight_operation_event_type_phase" ADD CONSTRAINT "neo_flight_operation_event_type_phase_neo_flight_operation_fk_1" FOREIGN KEY ("neo_flight_operation_id")
  REFERENCES "neo_flight_operation" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "neo_flight_operation_event_type_phase" ADD CONSTRAINT "neo_flight_operation_event_type_phase_neo_event_type_fk_1" FOREIGN KEY ("neo_event_type_id")
  REFERENCES "neo_event_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "neo_flight_operation_event_type_phase" ADD CONSTRAINT "neo_flight_operation_event_type_phase_phase_fk_1" FOREIGN KEY ("phase_id")
  REFERENCES "phase" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_reporting_entity_fk_1" FOREIGN KEY ("reporting_entity_id")
  REFERENCES "reporting_entity" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_state_area_fk_1" FOREIGN KEY ("where_state_area_id")
  REFERENCES "state_area" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_state_area_fk_2" FOREIGN KEY ("state_of_registry_id")
  REFERENCES "state_area" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_aircraft_manufacturer_model_fk_1" FOREIGN KEY ("manufacturer_model_id")
  REFERENCES "aircraft_manufacturer_model" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_aircraft_category_fk_1" FOREIGN KEY ("category_id")
  REFERENCES "aircraft_category" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_aircraft_mass_group_fk_1" FOREIGN KEY ("mass_group_id")
  REFERENCES "aircraft_mass_group" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_aircraft_propulsion_type_fk_1" FOREIGN KEY ("propulsion_type_id")
  REFERENCES "aircraft_propulsion_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_aircraft_wake_turb_category_fk_1" FOREIGN KEY ("wake_turb_category_id")
  REFERENCES "aircraft_wake_turb_category" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_aircraft_landing_gear_type_fk_1" FOREIGN KEY ("landing_gear_type_id")
  REFERENCES "aircraft_landing_gear_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_aerodrome_fk_1" FOREIGN KEY ("departure_id")
  REFERENCES "aerodrome" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_aerodrome_fk_2" FOREIGN KEY ("destination_id")
  REFERENCES "aerodrome" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_phase_fk_1" FOREIGN KEY ("phase_id")
  REFERENCES "phase" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_operator_fk_1" FOREIGN KEY ("operator_id")
  REFERENCES "operator" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_operation_type_fk_1" FOREIGN KEY ("operation_type_id")
  REFERENCES "operation_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_type_of_airspeed_fk_1" FOREIGN KEY ("type_of_airspeed_id")
  REFERENCES "type_of_airspeed" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_flight_rule_fk_1" FOREIGN KEY ("flight_rule_id")
  REFERENCES "flight_rule" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_traffic_type_fk_1" FOREIGN KEY ("traffic_type_id")
  REFERENCES "traffic_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_instrument_appr_type_fk_1" FOREIGN KEY ("instrument_appr_type_id")
  REFERENCES "instrument_appr_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_ocurrence_class_fk_1" FOREIGN KEY ("ocurrence_class_id")
  REFERENCES "ocurrence_class" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_detection_phase_fk_1" FOREIGN KEY ("detection_phase_id")
  REFERENCES "detection_phase" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;


ALTER TABLE IF EXISTS "neo_flight_operation" ADD CONSTRAINT "neo_flight_operation_neo_report_status_fk_1" FOREIGN KEY ("report_status_id")
  REFERENCES "neo_report_status" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "eccairs_ocurrence_category" ADD CONSTRAINT "eccairs_ocurrence_category_eccairs_fk_1" FOREIGN KEY ("eccairs_id")
  REFERENCES "eccairs" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE IF EXISTS "eccairs_ocurrence_category" ADD CONSTRAINT "eccairs_ocurrence_category_ocurrence_category_fk_1" FOREIGN KEY ("ocurrence_category_id")
  REFERENCES "ocurrence_category" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE;


ALTER TABLE IF EXISTS "eccairs" ADD CONSTRAINT "eccairs_state_area_fk_1" FOREIGN KEY ("state_area_id")
  REFERENCES "state_area" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "eccairs" ADD CONSTRAINT "eccairs_ocurrence_class_fk_1" FOREIGN KEY ("ocurrence_class_id")
  REFERENCES "ocurrence_class" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "eccairs" ADD CONSTRAINT "eccairs_injury_level_fk_1" FOREIGN KEY ("injury_level_id")
  REFERENCES "injury_level" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "eccairs" ADD CONSTRAINT "eccairs_aircraft_mass_group_fk_1" FOREIGN KEY ("mass_group_id")
  REFERENCES "aircraft_mass_group" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "eccairs" ADD CONSTRAINT "eccairs_aircraft_category_fk_1" FOREIGN KEY ("category_id")
  REFERENCES "aircraft_category" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "eccairs" ADD CONSTRAINT "eccairs_operation_type_fk_1" FOREIGN KEY ("operation_type_id")
  REFERENCES "operation_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "eccairs" ADD CONSTRAINT "eccairs_operator_type_fk_1" FOREIGN KEY ("operator_type_id")
  REFERENCES "operator_type" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "eccairs" ADD CONSTRAINT "eccairs_operator_fk_1" FOREIGN KEY ("operator_id")
  REFERENCES "operator" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE IF EXISTS "eccairs" ADD CONSTRAINT "eccairs_weather_condition_fk_1" FOREIGN KEY ("weather_condition_id")
  REFERENCES "weather_condition" ("id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE SET NULL;

# --- !Downs
ALTER TABLE IF EXISTS "reporting_entity" DROP CONSTRAINT IF EXISTS  "reporting_entity_reporting_entity_fk_1";

ALTER TABLE IF EXISTS "state_area" DROP CONSTRAINT IF EXISTS  "state_area_state_area_fk_1";




ALTER TABLE IF EXISTS "runway" DROP CONSTRAINT IF EXISTS  "runway_aerodrome_fk_1";
ALTER TABLE IF EXISTS "runway" DROP CONSTRAINT IF EXISTS  "runway_runway_surface_type_fk_1";



ALTER TABLE IF EXISTS "helicopter_landing_area_area_type" DROP CONSTRAINT IF EXISTS  "helicopter_landing_area_area_type_helicopter_landing_area_surface_type_fk_1";
ALTER TABLE IF EXISTS "helicopter_landing_area_area_type" DROP CONSTRAINT IF EXISTS  "helicopter_landing_area_area_type_helicopter_landing_area_area_configuration_fk_1";
ALTER TABLE IF EXISTS "helicopter_landing_area_area_type" DROP CONSTRAINT IF EXISTS  "helicopter_landing_area_area_type_helicopter_landing_area_type_fk_1";
ALTER TABLE IF EXISTS "helicopter_landing_area" DROP CONSTRAINT IF EXISTS  "helicopter_landing_area_aerodrome_fk_1";
ALTER TABLE IF EXISTS "aerodrome" DROP CONSTRAINT IF EXISTS  "aerodrome_aerodrome_type_fk_1";
ALTER TABLE IF EXISTS "aerodrome" DROP CONSTRAINT IF EXISTS  "aerodrome_aerodrome_status_fk_1";




ALTER TABLE IF EXISTS "neo_aerodrome_ocurrence_category" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_ocurrence_category_ocurrence_category_fk_1";
ALTER TABLE IF EXISTS "neo_aerodrome_ocurrence_category" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_ocurrence_category_neo_aerodrome_fk_1";

ALTER TABLE IF EXISTS "phase" DROP CONSTRAINT IF EXISTS  "phase_phase_fk_1";
ALTER TABLE IF EXISTS "neo_event_type_phase" DROP CONSTRAINT IF EXISTS  "neo_event_type_phase_phase_fk_1";
ALTER TABLE IF EXISTS "neo_event_type_phase" DROP CONSTRAINT IF EXISTS  "neo_event_type_phase_neo_event_type_fk_1";
ALTER TABLE IF EXISTS "neo_event_type_phase" DROP CONSTRAINT IF EXISTS  "neo_event_type_phase_neo_aerodrome_fk_1";

ALTER TABLE IF EXISTS "neo_aerodrome" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_neo_report_status_fk_1";


ALTER TABLE IF EXISTS "neo_aerodrome" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_detection_phase_fk_1";
ALTER TABLE IF EXISTS "neo_aerodrome" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_ocurrence_class_fk_1";
ALTER TABLE IF EXISTS "neo_aerodrome" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_helicopter_landing_area_surface_type_fk_1";
ALTER TABLE IF EXISTS "neo_aerodrome" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_helicopter_landing_area_area_configuration_fk_1";
ALTER TABLE IF EXISTS "neo_aerodrome" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_helicopter_landing_area_type_fk_1";
ALTER TABLE IF EXISTS "neo_aerodrome" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_runway_surface_type_fk_1";
ALTER TABLE IF EXISTS "neo_aerodrome" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_runway_fk_1";
ALTER TABLE IF EXISTS "neo_aerodrome" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_aerodrome_type_fk_1";
ALTER TABLE IF EXISTS "neo_aerodrome" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_aerodrome_status_fk_1";
ALTER TABLE IF EXISTS "neo_aerodrome" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_aerodrome_location_fk_1";
ALTER TABLE IF EXISTS "neo_aerodrome" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_aerodrome_fk_1";
ALTER TABLE IF EXISTS "neo_aerodrome" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_state_area_fk_1";
ALTER TABLE IF EXISTS "neo_aerodrome" DROP CONSTRAINT IF EXISTS  "neo_aerodrome_reporting_entity_fk_1";
ALTER TABLE IF EXISTS "aircraft_manufacturer_model" DROP CONSTRAINT IF EXISTS  "aircraft_manufacturer_model_aircraft_manufacturer_model_fk_1";
ALTER TABLE IF EXISTS "aircraft_category" DROP CONSTRAINT IF EXISTS  "aircraft_category_aircraft_category_fk_1";



ALTER TABLE IF EXISTS "aircraft_landing_gear_type" DROP CONSTRAINT IF EXISTS  "aircraft_landing_gear_type_aircraft_landing_gear_type_fk_1";
ALTER TABLE IF EXISTS "operator" DROP CONSTRAINT IF EXISTS  "operator_operator_type_fk_1";
ALTER TABLE IF EXISTS "operator_type" DROP CONSTRAINT IF EXISTS  "operator_type_operator_type_fk_1";
ALTER TABLE IF EXISTS "operation_type" DROP CONSTRAINT IF EXISTS  "operation_type_operation_type_fk_1";





ALTER TABLE IF EXISTS "license_type" DROP CONSTRAINT IF EXISTS  "license_type_license_type_fk_1";
ALTER TABLE IF EXISTS "license_issued_by" DROP CONSTRAINT IF EXISTS  "license_issued_by_license_issued_by_fk_1";


ALTER TABLE IF EXISTS "flight_crew_license" DROP CONSTRAINT IF EXISTS  "flight_crew_license_license_ratings_fk_1";
ALTER TABLE IF EXISTS "flight_crew_license" DROP CONSTRAINT IF EXISTS  "flight_crew_license_license_validity_fk_1";
ALTER TABLE IF EXISTS "flight_crew_license" DROP CONSTRAINT IF EXISTS  "flight_crew_license_license_issued_by_fk_1";
ALTER TABLE IF EXISTS "flight_crew_license" DROP CONSTRAINT IF EXISTS  "flight_crew_license_license_type_fk_1";
ALTER TABLE IF EXISTS "flight_crew_license" DROP CONSTRAINT IF EXISTS  "flight_crew_license_flight_crew_fk_1";
ALTER TABLE IF EXISTS "flight_crew" DROP CONSTRAINT IF EXISTS  "flight_crew_flight_crew_category_fk_1";
ALTER TABLE IF EXISTS "flight_crew" DROP CONSTRAINT IF EXISTS  "flight_crew_neo_flight_operation_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation_ocurrence_category" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_ocurrence_category_ocurrence_category_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation_ocurrence_category" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_ocurrence_category_neo_flight_operation_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation_event_type_phase" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_event_type_phase_phase_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation_event_type_phase" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_event_type_phase_neo_event_type_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation_event_type_phase" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_event_type_phase_neo_flight_operation_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_neo_report_status_fk_1";


ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_detection_phase_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_ocurrence_class_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_instrument_appr_type_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_traffic_type_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_flight_rule_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_type_of_airspeed_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_operation_type_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_operator_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_phase_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_aerodrome_fk_2";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_aerodrome_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_aircraft_landing_gear_type_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_aircraft_wake_turb_category_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_aircraft_propulsion_type_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_aircraft_mass_group_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_aircraft_category_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_aircraft_manufacturer_model_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_state_area_fk_2";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_state_area_fk_1";
ALTER TABLE IF EXISTS "neo_flight_operation" DROP CONSTRAINT IF EXISTS  "neo_flight_operation_reporting_entity_fk_1";
ALTER TABLE IF EXISTS "eccairs_ocurrence_category" DROP CONSTRAINT IF EXISTS  "eccairs_ocurrence_category_ocurrence_category_fk_1";
ALTER TABLE IF EXISTS "eccairs_ocurrence_category" DROP CONSTRAINT IF EXISTS  "eccairs_ocurrence_category_eccairs_fk_1";


ALTER TABLE IF EXISTS "eccairs" DROP CONSTRAINT IF EXISTS  "eccairs_weather_condition_fk_1";
ALTER TABLE IF EXISTS "eccairs" DROP CONSTRAINT IF EXISTS  "eccairs_operator_fk_1";
ALTER TABLE IF EXISTS "eccairs" DROP CONSTRAINT IF EXISTS  "eccairs_operator_type_fk_1";
ALTER TABLE IF EXISTS "eccairs" DROP CONSTRAINT IF EXISTS  "eccairs_operation_type_fk_1";
ALTER TABLE IF EXISTS "eccairs" DROP CONSTRAINT IF EXISTS  "eccairs_aircraft_category_fk_1";
ALTER TABLE IF EXISTS "eccairs" DROP CONSTRAINT IF EXISTS  "eccairs_aircraft_mass_group_fk_1";
ALTER TABLE IF EXISTS "eccairs" DROP CONSTRAINT IF EXISTS  "eccairs_injury_level_fk_1";
ALTER TABLE IF EXISTS "eccairs" DROP CONSTRAINT IF EXISTS  "eccairs_ocurrence_class_fk_1";
ALTER TABLE IF EXISTS "eccairs" DROP CONSTRAINT IF EXISTS  "eccairs_state_area_fk_1";
DROP INDEX IF EXISTS "reporting_entity_reporting_entity_id_idx";

DROP INDEX IF EXISTS "state_area_state_area_id_idx";




DROP INDEX IF EXISTS "runway_runway_surface_type_id_idx";
DROP INDEX IF EXISTS "runway_aerodrome_id_idx";



DROP INDEX IF EXISTS "helicopter_landing_area_area_type_helicopter_landing_area_type_id_idx";
DROP INDEX IF EXISTS "helicopter_landing_area_area_type_helicopter_landing_area_area_configuration_id_idx";
DROP INDEX IF EXISTS "helicopter_landing_area_area_type_helicopter_landing_area_surface_type_id_idx";
DROP INDEX IF EXISTS "helicopter_landing_area_aerodrome_id_idx";
DROP INDEX IF EXISTS "aerodrome_aerodrome_status_id_idx";
DROP INDEX IF EXISTS "aerodrome_aerodrome_type_id_idx";




DROP INDEX IF EXISTS "neo_aerodrome_ocurrence_category_neo_aerodrome_id_idx";
DROP INDEX IF EXISTS "neo_aerodrome_ocurrence_category_ocurrence_category_id_idx";

DROP INDEX IF EXISTS "phase_phase_id_idx";
DROP INDEX IF EXISTS "neo_event_type_phase_neo_aerodrome_id_idx";
DROP INDEX IF EXISTS "neo_event_type_phase_neo_event_type_id_idx";
DROP INDEX IF EXISTS "neo_event_type_phase_phase_id_idx";

DROP INDEX IF EXISTS "neo_aerodrome_reporting_entity_id_idx";
DROP INDEX IF EXISTS "neo_aerodrome_where_state_area_id_idx";
DROP INDEX IF EXISTS "neo_aerodrome_aerodrome_id_idx";
DROP INDEX IF EXISTS "neo_aerodrome_aerodrome_location_id_idx";
DROP INDEX IF EXISTS "neo_aerodrome_aerodrome_status_id_idx";
DROP INDEX IF EXISTS "neo_aerodrome_aerodrome_type_id_idx";
DROP INDEX IF EXISTS "neo_aerodrome_runway_id_idx";
DROP INDEX IF EXISTS "neo_aerodrome_runway_surface_type_id_idx";
DROP INDEX IF EXISTS "neo_aerodrome_helicopter_landing_area_type_id_idx";
DROP INDEX IF EXISTS "neo_aerodrome_helicopter_landing_area_area_configuration_id_idx";
DROP INDEX IF EXISTS "neo_aerodrome_helicopter_landing_area_surface_type_id_idx";
DROP INDEX IF EXISTS "neo_aerodrome_ocurrence_class_id_idx";
DROP INDEX IF EXISTS "neo_aerodrome_detection_phase_id_idx";


DROP INDEX IF EXISTS "neo_aerodrome_report_status_id_idx";
DROP INDEX IF EXISTS "aircraft_manufacturer_model_aircraft_manufacturer_model_id_idx";
DROP INDEX IF EXISTS "aircraft_category_aircraft_category_id_idx";



DROP INDEX IF EXISTS "aircraft_landing_gear_type_aircraft_landing_gear_type_id_idx";
DROP INDEX IF EXISTS "operator_operator_type_id_idx";
DROP INDEX IF EXISTS "operator_type_operator_type_id_idx";
DROP INDEX IF EXISTS "operation_type_operation_type_id_idx";





DROP INDEX IF EXISTS "license_type_license_type_id_idx";
DROP INDEX IF EXISTS "license_issued_by_license_issued_by_id_idx";


DROP INDEX IF EXISTS "flight_crew_license_flight_crew_id_idx";
DROP INDEX IF EXISTS "flight_crew_license_license_type_id_idx";
DROP INDEX IF EXISTS "flight_crew_license_license_issued_by_id_idx";
DROP INDEX IF EXISTS "flight_crew_license_license_validity_id_idx";
DROP INDEX IF EXISTS "flight_crew_license_license_ratings_id_idx";
DROP INDEX IF EXISTS "flight_crew_neo_flight_operation_id_idx";
DROP INDEX IF EXISTS "flight_crew_category_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_ocurrence_category_neo_flight_operation_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_ocurrence_category_ocurrence_category_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_event_type_phase_neo_flight_operation_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_event_type_phase_neo_event_type_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_event_type_phase_phase_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_reporting_entity_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_where_state_area_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_state_of_registry_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_manufacturer_model_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_category_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_mass_group_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_propulsion_type_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_wake_turb_category_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_landing_gear_type_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_departure_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_destination_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_phase_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_operator_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_operation_type_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_type_of_airspeed_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_flight_rule_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_traffic_type_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_instrument_appr_type_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_ocurrence_class_id_idx";
DROP INDEX IF EXISTS "neo_flight_operation_detection_phase_id_idx";


DROP INDEX IF EXISTS "neo_flight_operation_report_status_id_idx";
DROP INDEX IF EXISTS "eccairs_ocurrence_category_eccairs_id_idx";
DROP INDEX IF EXISTS "eccairs_ocurrence_category_ocurrence_category_id_idx";


DROP INDEX IF EXISTS "eccairs_state_area_id_idx";
DROP INDEX IF EXISTS "eccairs_ocurrence_class_id_idx";
DROP INDEX IF EXISTS "eccairs_injury_level_id_idx";
DROP INDEX IF EXISTS "eccairs_mass_group_id_idx";
DROP INDEX IF EXISTS "eccairs_category_id_idx";
DROP INDEX IF EXISTS "eccairs_operation_type_id_idx";
DROP INDEX IF EXISTS "eccairs_operator_type_id_idx";
DROP INDEX IF EXISTS "eccairs_operator_id_idx";
DROP INDEX IF EXISTS "eccairs_weather_condition_id_idx";
DROP TABLE IF EXISTS "reporting_entity";
DROP TABLE IF EXISTS "organization";
DROP TABLE IF EXISTS "state_area";
DROP TABLE IF EXISTS "language";
DROP TABLE IF EXISTS "aerodrome_status";
DROP TABLE IF EXISTS "aerodrome_type";
DROP TABLE IF EXISTS "runway_surface_type";
DROP TABLE IF EXISTS "runway";
DROP TABLE IF EXISTS "helicopter_landing_area_surface_type";
DROP TABLE IF EXISTS "helicopter_landing_area_type";
DROP TABLE IF EXISTS "helicopter_landing_area_area_configuration";
DROP TABLE IF EXISTS "helicopter_landing_area_area_type";
DROP TABLE IF EXISTS "helicopter_landing_area";
DROP TABLE IF EXISTS "aerodrome";
DROP TABLE IF EXISTS "aerodrome_location";
DROP TABLE IF EXISTS "ocurrence_class";
DROP TABLE IF EXISTS "detection_phase";
DROP TABLE IF EXISTS "ocurrence_category";
DROP TABLE IF EXISTS "neo_aerodrome_ocurrence_category";
DROP TABLE IF EXISTS "neo_event_type";
DROP TABLE IF EXISTS "phase";
DROP TABLE IF EXISTS "neo_event_type_phase";
DROP TABLE IF EXISTS "neo_report_status";
DROP TABLE IF EXISTS "neo_aerodrome";
DROP TABLE IF EXISTS "aircraft_manufacturer_model";
DROP TABLE IF EXISTS "aircraft_category";
DROP TABLE IF EXISTS "aircraft_mass_group";
DROP TABLE IF EXISTS "aircraft_propulsion_type";
DROP TABLE IF EXISTS "aircraft_wake_turb_category";
DROP TABLE IF EXISTS "aircraft_landing_gear_type";
DROP TABLE IF EXISTS "operator";
DROP TABLE IF EXISTS "operator_type";
DROP TABLE IF EXISTS "operation_type";
DROP TABLE IF EXISTS "type_of_airspeed";
DROP TABLE IF EXISTS "flight_rule";
DROP TABLE IF EXISTS "traffic_type";
DROP TABLE IF EXISTS "instrument_appr_type";
DROP TABLE IF EXISTS "flight_crew_category";
DROP TABLE IF EXISTS "license_type";
DROP TABLE IF EXISTS "license_issued_by";
DROP TABLE IF EXISTS "license_validity";
DROP TABLE IF EXISTS "license_ratings";
DROP TABLE IF EXISTS "flight_crew_license";
DROP TABLE IF EXISTS "flight_crew";
DROP TABLE IF EXISTS "neo_flight_operation_ocurrence_category";
DROP TABLE IF EXISTS "neo_flight_operation_event_type_phase";
DROP TABLE IF EXISTS "neo_flight_operation";
DROP TABLE IF EXISTS "eccairs_ocurrence_category";
DROP TABLE IF EXISTS "injury_level";
DROP TABLE IF EXISTS "weather_condition";
DROP TABLE IF EXISTS "eccairs";

       