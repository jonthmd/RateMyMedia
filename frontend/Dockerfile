# --- Étape 1 : build Angular ---
FROM node:26-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# --- Étape 2 : servir les fichiers statiques via nginx ---
# Note : le dossier de sortie d'Angular (dist/frontend/browser) correspond
# au builder "application" par défaut depuis Angular 17+. Si ton build
# produit un chemin différent (ex: dist/frontend sans /browser), ajuste la
# ligne COPY ci-dessous en conséquence.
FROM nginx:alpine
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
