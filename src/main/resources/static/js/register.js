document.getElementById("registerForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const messageBox = document.getElementById("registerMessage");
    const submitBtn = document.getElementById("registerSubmit");

    try {
        UI.setBusy(submitBtn, true);
        await API.http("/api/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, email, password })
        });

        // Success message
        UI.showMessage(messageBox, "✅ Registration successful! Please check your email to verify your account. Redirecting to login...", "success");

        // Redirect to login page after successful registration
        setTimeout(() => {
            window.location.href = "login.html";
        }, 3000);

    } catch (err) {
        // Show backend error message (e.g., "Email already registered")
        UI.showMessage(messageBox, err.message || "❌ Registration failed", "error");
    }
    finally {
        UI.setBusy(submitBtn, false);
    }
});
