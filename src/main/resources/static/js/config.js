// Global app configuration for frontend
// Detect API base URL. If frontend is served by Spring Boot on the same host/port, this will be same-origin.
(function () {
  const explicit = window.__API_BASE_URL__;
  const inferred = window.location.origin; // e.g., http://localhost:8080
  const BASE_URL = explicit || inferred;

  // Expose config globally
  window.APP_CONFIG = {
    BASE_URL,
  };
})();
