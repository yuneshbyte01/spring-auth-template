document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("loginEmail").value.trim();
    const password = document.getElementById("loginPassword").value.trim();
    const messageBox = document.getElementById("loginMessage");
    const submitBtn = document.getElementById("loginSubmit");

    try {
        UI.setBusy(submitBtn, true);
        const data = await API.http("/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });
        // Store JWT token for dashboard access
        localStorage.setItem("jwt_token", data.accessToken);
        
        // Also store in sessionStorage as backup
        sessionStorage.setItem("jwt_token", data.accessToken);

        UI.showMessage(messageBox, "✅ Login successful! Redirecting to dashboard...", "success");

        // Redirect to dashboard after successful login
        setTimeout(() => {
            window.location.href = "dashboard.html";
        }, 1500);
    } catch (err) {
        UI.showMessage(messageBox, err.message || "❌ Invalid credentials", "error");
    }
    finally {
        UI.setBusy(submitBtn, false);
    }
});
