FROM node:14
RUN mkdir /usr/src/app
WORKDIR /usr/src/app
COPY package.json package-lock.json ./
RUN npm install
RUN npm install -g @angular/cli
COPY . /usr/src/app
EXPOSE 4200
CMD ng serve --host 0.0.0.0 