#!/bin/bash

psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -a -f /app/scripts/sql/config.sql
psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -a -f /app/scripts/sql/banco_de_dados.sql
