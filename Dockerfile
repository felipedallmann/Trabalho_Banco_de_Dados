FROM postgres

WORKDIR /app

COPY ./scripts/prepare_db.sh /docker-entrypoint-initdb.d
COPY ./scripts/sql ./scripts/sql
