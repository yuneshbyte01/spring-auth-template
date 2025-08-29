document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("loginEmail").value.trim();
    const password = document.getElementById("loginPassword").value.trim();
    const messageBox = document.getElementById("loginMessage");

    try {
        const res = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (!res.ok) throw new Error("❌ Invalid credentials");

        const data = await res.json();
        localStorage.setItem("jwt", data.accessToken);

        messageBox.textContent = "✅ Login successful!";
        messageBox.className = "message-box success";
        messageBox.style.display = "block";

        // Redirect example
        // window.location.href = "/dashboard.html";
    } catch (err) {
        messageBox.textContent = err.message;
        messageBox.className = "message-box error";
        messageBox.style.display = "block";
    }
});
