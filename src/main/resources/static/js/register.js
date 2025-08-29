document.getElementById("registerForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const messageBox = document.getElementById("registerMessage");

    try {
        const res = await fetch("http://localhost:8080/api/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, email, password })
        });

        if (!res.ok) {
            // Try to read a backend error message
            const errorText = await res.text();
            throw new Error(errorText || "❌ Registration failed");
        }

        // Success message
        messageBox.textContent = "✅ Registration successful! Please check your email to verify your account.";
        messageBox.className = "message-box success";
        messageBox.style.display = "block";

        // Optional: Redirect to log in after 3s
        // setTimeout(() => window.location.href = "login.html", 3000);

    } catch (err) {
        // Show backend error message (e.g., "Email already registered")
        messageBox.textContent = err.message;
        messageBox.className = "message-box error";
        messageBox.style.display = "block";
    }
});
