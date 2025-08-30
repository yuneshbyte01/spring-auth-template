// Modal handling
const modal = document.getElementById("forgotModal");
const openBtn = document.getElementById("forgotToggle");
const closeBtn = document.querySelector("#forgotModal .close");
let lastFocused = null;

function openModal() {
    lastFocused = document.activeElement;
    modal.style.display = "flex";
    document.body.classList.add('modal-open');
    const emailInput = document.getElementById('forgotEmail');
    emailInput && emailInput.focus();
}

function closeModal() {
    modal.style.display = "none";
    document.body.classList.remove('modal-open');
    if (lastFocused && typeof lastFocused.focus === 'function') {
        lastFocused.focus();
    }
}

openBtn.onclick = (e) => { e.preventDefault(); openModal(); };
closeBtn.onclick = () => { closeModal(); };
window.addEventListener('keydown', (e) => {
    if (e.key === 'Escape' && modal.style.display === 'flex') closeModal();
});
window.onclick = (e) => { if (e.target === modal) closeModal(); };

// Basic focus trap within modal when open
modal.addEventListener('keydown', (e) => {
    if (e.key !== 'Tab') return;
    const focusable = modal.querySelectorAll('button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])');
    if (!focusable.length) return;
    const first = focusable[0];
    const last = focusable[focusable.length - 1];
    if (e.shiftKey && document.activeElement === first) {
        e.preventDefault();
        last.focus();
    } else if (!e.shiftKey && document.activeElement === last) {
        e.preventDefault();
        first.focus();
    }
});

// Handle forgot a password form
document.getElementById("forgotForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const email = document.getElementById("forgotEmail").value.trim();
    const msg = document.getElementById("forgotMessage");
    const submit = e.submitter || document.querySelector('#forgotForm button[type="submit"]');

    try {
        UI.setBusy(submit, true);
        await API.http(`/api/auth/forgot-password?email=${encodeURIComponent(email)}`, { method: "POST" });
        UI.showMessage(msg, "✅ Reset link sent to your email!", "success");
    } catch (err) {
        UI.showMessage(msg, err.message || "❌ Failed to send reset link.", "error");
    }
    finally {
        UI.setBusy(submit, false);
    }
});