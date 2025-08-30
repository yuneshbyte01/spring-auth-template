// Shared frontend utilities
(function () {
  function setBusy(button, busy) {
    if (!button) return;
    if (busy) {
      button.disabled = true;
      // preserve full button content including icons
      button.dataset.originalHtml = button.innerHTML;
      button.innerHTML = '<i class="fas fa-circle-notch fa-spin"></i> Please wait...';
      button.classList.add('is-busy');
    } else {
      button.disabled = false;
      if (button.dataset.originalHtml) button.innerHTML = button.dataset.originalHtml;
      button.classList.remove('is-busy');
    }
  }

  function showMessage(el, text, type) {
    if (!el) return;
    el.textContent = text;
    el.className = 'message-box ' + (type || '');
    el.style.display = 'block';
    el.setAttribute('role', 'status');
    el.setAttribute('aria-live', 'polite');
  }

  async function http(path, options = {}) {
    const base = window.APP_CONFIG?.BASE_URL || window.location.origin;
    const url = path.startsWith('http') ? path : `${base}${path.startsWith('/') ? '' : '/'}${path}`;

    const res = await fetch(url, options);

    const contentType = res.headers.get('content-type') || '';
    const isJson = contentType.includes('application/json');
    const body = isJson ? await res.json().catch(() => null) : await res.text().catch(() => '');

    if (!res.ok) {
      const msg = (isJson && body && (body.message || body.error)) || (typeof body === 'string' ? body : '') || `Request failed (${res.status})`;
      const error = new Error(msg);
      error.status = res.status;
      throw error;
    }
    return body;
  }

  window.UI = { setBusy, showMessage };
  window.API = { http };
})();
