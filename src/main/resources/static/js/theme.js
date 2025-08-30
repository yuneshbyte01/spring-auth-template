// Theme Manager: handles dark/light theme with persistence
(function () {
  const STORAGE_KEY = 'preferred_theme'; // 'light' | 'dark' | 'system'

  function getSystemTheme() {
    return window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
  }

  function getSavedTheme() {
    return localStorage.getItem(STORAGE_KEY) || 'system';
  }

  function applyTheme(theme) {
    const resolved = theme === 'system' ? getSystemTheme() : theme;
    document.documentElement.setAttribute('data-theme', resolved);
    updateToggleLabel(theme, resolved);
  }

  function cycleTheme() {
    const current = getSavedTheme();
    const next = current === 'system' ? 'light' : current === 'light' ? 'dark' : 'system';
    localStorage.setItem(STORAGE_KEY, next);
    applyTheme(next);
  }

  function updateToggleLabel(selected, resolved) {
    const btn = document.querySelector('.theme-toggle');
    if (!btn) return;
    const icon = resolved === 'dark' ? '🌙' : '☀️';
    const label = selected.toUpperCase();
    btn.textContent = `${icon} Theme: ${label}`;
    btn.setAttribute('aria-label', `Switch theme (currently ${label})`);
  }

  // Setup on DOM ready
  document.addEventListener('DOMContentLoaded', function () {
    const saved = getSavedTheme();
    applyTheme(saved);

    const toggle = document.querySelector('.theme-toggle');
    if (toggle) {
      toggle.addEventListener('click', function (e) {
        e.preventDefault();
        cycleTheme();
      });
    }

    // React to system changes when in system mode
    if (window.matchMedia) {
      const mq = window.matchMedia('(prefers-color-scheme: dark)');
      mq.addEventListener?.('change', () => {
        if (getSavedTheme() === 'system') applyTheme('system');
      });
    }
  });
})();
