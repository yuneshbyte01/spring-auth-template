document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("registerForm");
    const nameInput = document.getElementById("name");
    const emailInput = document.getElementById("email");
    const pwdInput = document.getElementById("password");
    const strengthMeter = document.getElementById("strength-meter");
    const messageBox = document.getElementById("registerMessage");
    const submitBtn = document.getElementById("registerSubmit");

    // Live validation as user types
    pwdInput.addEventListener("input", () => {
        const pwd = pwdInput.value;
        const email = emailInput.value;
        const results = validatePasswordRules(pwd, email);
        const score = passwordStrengthScore(pwd);

        updatePasswordRulesUI(results);
        strengthMeter.value = score;
    });

    // Form submit handler
    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const name = nameInput.value.trim();
        const email = emailInput.value.trim();
        const password = pwdInput.value.trim();

        // Run password validation before sending to the server
        const results = validatePasswordRules(password, email);
        if (!results.allValid) {
            UI.showMessage(messageBox, "❌ Password does not meet all requirements.", "error");
            return;
        }

        try {
            UI.setBusy(submitBtn, true);

            await API.http("/api/auth/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ name, email, password })
            });

            UI.showMessage(
                messageBox,
                "✅ Registration successful! Please check your email to verify your account. Redirecting to login...",
                "success"
            );

            setTimeout(() => {
                window.location.href = "login.html";
            }, 3000);

        } catch (err) {
            UI.showMessage(messageBox, err.message || "❌ Registration failed", "error");
        } finally {
            UI.setBusy(submitBtn, false);
        }
    });
});
