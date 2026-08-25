const CACHE_NAME = "lughatnama-v1";

const FILES_TO_CACHE = [
  "/persian-vocabulary-app/",
  "/persian-vocabulary-app/index.html",
  "/persian-vocabulary-app/manifest.json"
];

self.addEventListener("install", event => {
  event.waitUntil(
    caches.open(CACHE_NAME)
      .then(cache => cache.addAll(FILES_TO_CACHE))
  );
});


self.addEventListener("fetch", event => {
  event.respondWith(
    caches.match(event.request)
      .then(response => response || fetch(event.request))
  );
});
