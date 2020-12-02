RUN mkdir /usr/src/app
WORKDIR /usr/src/app
RUN npm install -g @angular/cli8.3.29
COPY . /usr/src/app
EXPOSE 4200
CMD ng serve --host 0.0.0.0 