FROM node:latest
RUN mkdir /usr/src/app
WORKDIR /usr/src/app
COPY package.json /usr/src/app
RUN npm install --legacy-peer-deps
RUN npm install -g @angular/cli
COPY . /usr/src/app
EXPOSE 4200
CMD ng serve --host 0.0.0.0