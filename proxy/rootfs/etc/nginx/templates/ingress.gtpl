server {
    listen 5000 default_server;

    include /etc/nginx/includes/server_params.conf;

    location /test {
        allow   172.30.32.2;
        deny    all;

        proxy_pass http://backend;
        proxy_set_header X-Ingress-Path {{ .entry }};
        include /etc/nginx/includes/proxy_params.conf;

        rewrite /test/(.*) /$1  break;
        proxy_redirect     off;
        proxy_set_header   Host $host;

        add_header X-uri "$uri";
    }

    location / {
        allow   172.30.32.2;
        deny    all;

        root /etc/nginx/www;
        index index.html;
        proxy_set_header X-Ingress-Path {{ .entry }};
    }
}