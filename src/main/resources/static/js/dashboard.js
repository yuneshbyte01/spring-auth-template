// Dashboard JavaScript Functions

// JWT utility functions
function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        return JSON.parse(jsonPayload);
    } catch (e) {
        console.error('Error parsing JWT:', e);
        return null;
    }
}

function getStoredToken() {
    return localStorage.getItem('jwt_token') || sessionStorage.getItem('jwt_token');
}

function getUserRole() {
    const token = getStoredToken();
    if (!token) return null;
    
    const payload = parseJwt(token);
    if (!payload) return null;
    
    // Debug: Log the payload to see what's available
    console.log('JWT Payload:', payload);
    
    // Extract role from JWT claims - try multiple possible claim names
    let roles = [];
    
    // Check different possible claim names for roles
    if (payload.roles) {
        roles = Array.isArray(payload.roles) ? payload.roles : [payload.roles];
    } else if (payload.authorities) {
        roles = Array.isArray(payload.authorities) ? payload.authorities : [payload.authorities];
    } else if (payload.scope) {
        roles = Array.isArray(payload.scope) ? payload.scope : [payload.scope];
    } else if (payload.role) {
        roles = [payload.role];
    }
    
    // If no roles found in standard claims, try to extract from a subject or other fields
    if (roles.length === 0 && payload.sub) {
        console.log('No roles found in claims, checking if user exists');
        // For now, default to a USER role if no roles found
        roles = ['ROLE_USER'];
    }
    
    console.log('Extracted roles:', roles);
    
    // Find an ADMIN role
    if (roles.includes('ROLE_ADMIN')) {
        return 'ADMIN';
    } else if (roles.includes('ROLE_USER')) {
        return 'USER';
    }
    
    // If no roles found, check if we can determine from other claims
    if (payload.sub || payload.email) {
        console.log('No roles found, defaulting to USER');
        return 'USER'; // Default fallback
    }
    
    return null;
}

function showDashboard() {
    const role = getUserRole();
    if (!role) {
        showError('Unable to determine user role');
        return;
    }
    
    // Update role display
    document.getElementById('userRole').textContent = role;
    
    // Show/hide admin features based on a role
    const adminCard = document.getElementById('adminCard');
    if (role === 'ADMIN') {
        adminCard.style.display = 'block';
    } else {
        adminCard.style.display = 'none';
    }
    
    // Show dashboard content
    document.getElementById('loadingState').classList.add('hidden');
    document.getElementById('dashboardContent').classList.remove('hidden');
}

function showError(message) {
    document.getElementById('errorMessage').textContent = message;
    document.getElementById('loadingState').classList.add('hidden');
    document.getElementById('errorState').classList.remove('hidden');
}

async function accessUserProfile() {
    const token = getStoredToken();
    if (!token) {
        showError('No authentication token found');
        return;
    }
    
    console.log('Making request to /user/profile');
    
    try {
        const data = await API.http('/user/profile', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
        const text = typeof data === 'string' ? data : JSON.stringify(data, null, 2);
        alert('User Profile Access:\n\n' + text);
    } catch (error) {
        console.error('Error accessing user profile:', error);
        if (error.status === 403) {
            showError('Access denied. You need proper permissions.');
        } else if (error.status === 401) {
            showError('Unauthorized. Token may be invalid or expired.');
        } else {
            showError(error.message || 'Failed to access user profile');
        }
    }
}

async function accessAdminDashboard() {
    const token = getStoredToken();
    if (!token) {
        showError('No authentication token found');
        return;
    }
    
    try {
        const data = await API.http('/admin/dashboard', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
        const text = typeof data === 'string' ? data : JSON.stringify(data, null, 2);
        alert('Admin Dashboard Access:\n\n' + text);
    } catch (error) {
        if (error.status === 403) {
            showError('Access denied. Admin role required.');
        } else {
            showError(error.message || 'Failed to access admin dashboard');
        }
    }
}

function showSettings() {
    alert('Settings functionality coming soon! 🚀');
}

function logout() {
    // Clear stored tokens
    localStorage.removeItem('jwt_token');
    sessionStorage.removeItem('jwt_token');
    
    // Redirect to login page
    window.location.href = 'login.html';
}

// Initialize dashboard when page loads
document.addEventListener('DOMContentLoaded', function() {
    console.log('Dashboard page loaded');
    
    const token = getStoredToken();
    console.log('Stored token found:', !!token);
    
    if (!token) {
        showError('No authentication token found. Please log in.');
        return;
    }
    
    // Check if the token is valid
    const payload = parseJwt(token);
    console.log('Token parsed successfully:', !!payload);
    
    if (!payload) {
        showError('Invalid authentication token. Please log in again.');
        return;
    }
    
    // Check if the token is expired
    const currentTime = Date.now() / 1000;
    if (payload.exp && payload.exp < currentTime) {
        showError('Authentication token has expired. Please log in again.');
        return;
    }
    
    console.log('Token is valid, showing dashboard');
    // Show dashboard
    showDashboard();
});
