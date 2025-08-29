document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const messageBox = document.getElementById("messageBox");

    try {
        const response = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (!response.ok) {
            throw new Error("❌ Invalid credentials");
        }

        const data = await response.json();
        localStorage.setItem("jwt", data.accessToken);

        // Success message
        messageBox.textContent = "✅ Login successful!";
        messageBox.className = "message-box success";
        messageBox.style.display = "block";

        console.log("JWT:", data.accessToken);

        // Example: redirect after login
        // window.location.href = "/dashboard.html";

    } catch (error) {
        // Error message
        messageBox.textContent = error.message;
        messageBox.className = "message-box error";
        messageBox.style.display = "block";
    }
});
