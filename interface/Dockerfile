FROM node:alpine
COPY interface.yaml ./public/interface.yaml
RUN npm install http-server -g
CMD http-server ./public -p 1350
