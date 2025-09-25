// Enhanced Accessibility Features

(function() {
    'use strict';

    // Keyboard navigation support
    function initKeyboardNavigation() {
        // Handle Enter key on buttons and links
        document.addEventListener('keydown', function(e) {
            if (e.key === 'Enter' || e.key === ' ') {
                const target = e.target;
                
                // Handle custom buttons and interactive elements
                if (target.classList.contains('social-btn') || 
                    target.classList.contains('dashboard-card') ||
                    target.classList.contains('forgot-link')) {
                    e.preventDefault();
                    target.click();
                }
            }
        });

        // Handle Escape key for modals
        document.addEventListener('keydown', function(e) {
            if (e.key === 'Escape') {
                const modal = document.querySelector('.modal:not([style*="display: none"])');
                if (modal) {
                    const closeBtn = modal.querySelector('.close');
                    if (closeBtn) {
                        closeBtn.click();
                    }
                }
            }
        });

        // Focus management for modals
        function trapFocus(element) {
            const focusableElements = element.querySelectorAll(
                'button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])'
            );
            const firstElement = focusableElements[0];
            const lastElement = focusableElements[focusableElements.length - 1];

            element.addEventListener('keydown', function(e) {
                if (e.key === 'Tab') {
                    if (e.shiftKey) {
                        if (document.activeElement === firstElement) {
                            lastElement.focus();
                            e.preventDefault();
                        }
                    } else {
                        if (document.activeElement === lastElement) {
                            firstElement.focus();
                            e.preventDefault();
                        }
                    }
                }
            });
        }

        // Apply focus trap to modals when they open
        const observer = new MutationObserver(function(mutations) {
            mutations.forEach(function(mutation) {
                if (mutation.type === 'attributes' && mutation.attributeName === 'style') {
                    const modal = mutation.target;
                    if (modal.classList.contains('modal') && 
                        !modal.style.display || modal.style.display !== 'none') {
                        trapFocus(modal);
                        // Focus first focusable element
                        const firstFocusable = modal.querySelector(
                            'button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])'
                        );
                        if (firstFocusable) {
                            firstFocusable.focus();
                        }
                    }
                }
            });
        });

        // Observe all modals
        document.querySelectorAll('.modal').forEach(function(modal) {
            observer.observe(modal, { attributes: true });
        });
    }

    // Enhanced screen reader support
    function initScreenReaderSupport() {
        // Announce dynamic content changes
        function announceToScreenReader(message, priority = 'polite') {
            const announcement = document.createElement('div');
            announcement.setAttribute('aria-live', priority);
            announcement.setAttribute('aria-atomic', 'true');
            announcement.className = 'sr-only';
            announcement.textContent = message;
            
            document.body.appendChild(announcement);
            
            // Remove after announcement
            setTimeout(() => {
                document.body.removeChild(announcement);
            }, 1000);
        }

        // Make announcements available globally
        window.announceToScreenReader = announceToScreenReader;

        // Enhanced form validation announcements
        const forms = document.querySelectorAll('form');
        forms.forEach(function(form) {
            const inputs = form.querySelectorAll('input, textarea, select');
            
            inputs.forEach(function(input) {
                input.addEventListener('invalid', function() {
                    const message = input.validationMessage || 'Please fill in this field correctly';
                    announceToScreenReader(message, 'assertive');
                });

                input.addEventListener('blur', function() {
                    if (input.checkValidity()) {
                        announceToScreenReader('Field is valid');
                    }
                });
            });
        });
    }

    // High contrast mode support
    function initHighContrastSupport() {
        // Check for high contrast mode preference
        if (window.matchMedia && window.matchMedia('(prefers-contrast: high)').matches) {
            document.documentElement.setAttribute('data-high-contrast', 'true');
        }

        // Listen for changes
        if (window.matchMedia) {
            const mediaQuery = window.matchMedia('(prefers-contrast: high)');
            mediaQuery.addEventListener('change', function(e) {
                if (e.matches) {
                    document.documentElement.setAttribute('data-high-contrast', 'true');
                } else {
                    document.documentElement.removeAttribute('data-high-contrast');
                }
            });
        }
    }

    // Reduced motion support
    function initReducedMotionSupport() {
        if (window.matchMedia && window.matchMedia('(prefers-reduced-motion: reduce)').matches) {
            document.documentElement.setAttribute('data-reduced-motion', 'true');
        }

        if (window.matchMedia) {
            const mediaQuery = window.matchMedia('(prefers-reduced-motion: reduce)');
            mediaQuery.addEventListener('change', function(e) {
                if (e.matches) {
                    document.documentElement.setAttribute('data-reduced-motion', 'true');
                } else {
                    document.documentElement.removeAttribute('data-reduced-motion');
                }
            });
        }
    }

    // Skip links for keyboard navigation
    function initSkipLinks() {
        const skipLink = document.createElement('a');
        skipLink.href = '#main-content';
        skipLink.textContent = 'Skip to main content';
        skipLink.className = 'skip-link sr-only';
        skipLink.style.cssText = `
            position: absolute;
            top: -40px;
            left: 6px;
            background: var(--primary-600);
            color: white;
            padding: 8px;
            text-decoration: none;
            border-radius: 4px;
            z-index: 1000;
            transition: top 0.3s;
        `;
        
        skipLink.addEventListener('focus', function() {
            this.style.top = '6px';
        });
        
        skipLink.addEventListener('blur', function() {
            this.style.top = '-40px';
        });

        document.body.insertBefore(skipLink, document.body.firstChild);
    }

    // Initialize all accessibility features
    function init() {
        initKeyboardNavigation();
        initScreenReaderSupport();
        initHighContrastSupport();
        initReducedMotionSupport();
        initSkipLinks();
    }

    // Initialize when DOM is ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }

})();
