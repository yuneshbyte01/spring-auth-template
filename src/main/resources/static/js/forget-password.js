// Modal handling
const modal = document.getElementById("forgotModal");
const openBtn = document.getElementById("forgotToggle");
const closeBtn = document.querySelector(".modal .close");

openBtn.onclick = (e) => { e.preventDefault(); modal.style.display = "flex"; };
closeBtn.onclick = () => { modal.style.display = "none"; };
window.onclick = (e) => { if (e.target === modal) modal.style.display = "none"; };

// Handle forgot a password form
document.getElementById("forgotForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const email = document.getElementById("forgotEmail").value.trim();
    const msg = document.getElementById("forgotMessage");

    try {
        const res = await fetch("http://localhost:8080/api/auth/forgot-password?email=" + email, {
            method: "POST"
        });

        msg.style.display = "block";
        if (res.ok) {
            msg.textContent = "✅ Reset link sent to your email!";
            msg.className = "message-box success";
        } else {
            const errorText = await res.text();
            msg.textContent = errorText || "❌ Failed to send reset link.";
            msg.className = "message-box error";
        }
    } catch (err) {
        msg.style.display = "block";
        msg.textContent = "❌ Something went wrong.";
        msg.className = "message-box error";
    }
});