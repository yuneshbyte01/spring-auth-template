// passwordUtils.js

function validatePasswordRules(pwd, usernameOrEmail = '') {
    const results = {
        length: pwd.length >= 8,
        lowercase: /[a-z]/.test(pwd),
        uppercase: /[A-Z]/.test(pwd),
        digit: /[0-9]/.test(pwd),
        special: /[@$!%*?&\-_\.]/.test(pwd),
        noWhitespace: !/\s/.test(pwd),
        noUsername: usernameOrEmail ? !pwd.toLowerCase().includes(usernameOrEmail.toLowerCase()) : true
    };
    results.allValid = Object.values(results).every(Boolean);
    return results;
}

function passwordStrengthScore(pwd) {
    let score = 0;
    if (pwd.length >= 8) score += 1;
    if (pwd.length >= 12) score += 1;
    if (/[a-z]/.test(pwd)) score += 1;
    if (/[A-Z]/.test(pwd)) score += 1;
    if (/[0-9]/.test(pwd)) score += 1;
    if (/[@$!%*?&\-_\.]/.test(pwd)) score += 1;
    return Math.min(score, 6);
}

function updatePasswordRulesUI(results) {
    for (const rule in results) {
        if (rule === 'allValid') continue;
        const el = document.getElementById(`rule-${rule}`);
        if (el) {
            el.classList.toggle('valid', results[rule]);
            el.classList.toggle('invalid', !results[rule]);
        }
    }
}
