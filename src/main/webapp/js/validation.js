// =========================
// CLEAR ERROR ON INPUT
// =========================
document.addEventListener("DOMContentLoaded", function () {

    const inputs = document.querySelectorAll("input");
    const errorMsg = document.getElementById("errorMsg");

    if (!errorMsg) return;

    inputs.forEach(input => {
        input.addEventListener("input", function () {
            errorMsg.innerText = "";
        });

        input.addEventListener("focus", function () {
            errorMsg.innerText = "";
        });
    });

});

// =========================
// SHOW / HIDE PASSWORD
// =========================
function togglePassword() {
    const passwordInput = document.getElementById("password");
    const eyeOpen = document.getElementById("eye-open");
    const eyeClose = document.getElementById("eye-close");

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        eyeOpen.style.display = "none";
        eyeClose.style.display = "block";
    } else {
        passwordInput.type = "password";
        eyeOpen.style.display = "block";
        eyeClose.style.display = "none";
    }
}

// =========================
// PASSWORD LIVE BORDER
// =========================
document.addEventListener("DOMContentLoaded", function () {

    const passwordInput = document.getElementById("password");

    if (!passwordInput) return;

    passwordInput.addEventListener("input", function () {

        const length = passwordInput.value.length;

        if (length === 0) {
            passwordInput.style.border = "1px solid #ccc"; // default
        } 
        else if (length < 8) {
            passwordInput.style.border = "1px solid #ccc"; // red
        } 
        else {
            passwordInput.style.border = "2px solid #22c55e"; // green
        }

    });

});
// =========================
// FORM VALIDATION
// =========================
function validateForm() {

    let emailInput = document.getElementById("email");
    let passwordInput = document.getElementById("password");
    let errorMsg = document.getElementById("errorMsg");

    let email = emailInput.value.trim().toLowerCase();
    let password = passwordInput.value.trim();

    emailInput.value = email;

    errorMsg.innerText = "";

    if (!email.includes("@")) {
        errorMsg.innerText = "❌ Email must contain '@'";
        return false;
    }

    if (!email.includes(".")) {
        errorMsg.innerText = "❌ Email must contain '.'";
        return false;
    }

    if (!email.endsWith("@gmail.com")) {
        errorMsg.innerText = "❌ Email must end with @gmail.com";
        return false;
    }

    if (!/^[a-z][a-z0-9]*@gmail\.com$/.test(email)) {
        errorMsg.innerText = "❌ Email must start with letter and contain only lowercase & numbers";
        return false;
    }

    if (password.length < 8) {
        errorMsg.innerText = "❌ Password must be at least 8 characters";
        return false;
    }

    if (!/[A-Z]/.test(password)) {
        errorMsg.innerText = "❌ Password must contain 1 capital letter";
        return false;
    }

    if (!/[0-9]/.test(password)) {
        errorMsg.innerText = "❌ Password must contain 1 number";
        return false;
    }

    if (!/[!@#$%^&*]/.test(password)) {
        errorMsg.innerText = "❌ Password must contain 1 special character";
        return false;
    }

    return true;
}