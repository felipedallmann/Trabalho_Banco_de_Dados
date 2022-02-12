FROM postgres

WORKDIR /app

COPY ./scripts/seed_db.sh /docker-entrypoint-initdb.d
COPY ./scripts/sql ./scripts/sql
