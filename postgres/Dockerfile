FROM postgres:10-alpine

#install dependencies
RUN apk add --update make
RUN apk add --update gcc
RUN apk add --update musl-dev
RUN apk add --update openssl-dev

#install json plugin
COPY jsonlog /plugin/jsonlog

#build plugin
WORKDIR /plugin/jsonlog/
RUN make install

#create directory for postgres
RUN mkdir /var/log/postgresql
RUN chown postgres:postgres /var/log/postgresql

