# Mohan Portfolio CMS

Spring Boot portfolio CMS with a Vue, Three.js, GSAP, and WebGL public experience.

## Development

```powershell
cd frontend
npm install
npm run build
cd ..
mvn spring-boot:run
```

Open `http://localhost:8080/`. The frontend build is written to
`src/main/resources/static/portfolio` and served by Spring Boot.

## Attribution and license

The public 3D frontend is based on
[David Heckhoff's portfolio-2025](https://github.com/davidhckh/portfolio-2025),
Copyright (c) 2025 David Heckhoff. It is used under that project's custom
license, which permits personal and educational use with attribution and
prohibits commercial use without the author's written permission. The original
license is included at [frontend/LICENSE](frontend/LICENSE).
