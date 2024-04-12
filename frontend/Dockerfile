FROM node:18.12.0-bullseye as build-stage
WORKDIR /app
COPY package*.json ./
RUN yarn install
COPY ./ .
RUN export NODE_OPTIONS=--openssl-legacy-provider && yarn build
FROM nginx as production-stage
RUN mkdir /app
COPY --from=build-stage /app/dist /app
COPY nginx.conf /etc/nginx/nginx.conf
