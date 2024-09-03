document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const loginData = {
        email: email,
        password: password
    };

    fetch('/api/v1/auth/authenticate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.token) {
                localStorage.setItem('authToken', data.token); // Store the token
                const userRole = data.role; // Get the role from the response

                Swal.fire({
                    icon: 'success',
                    title: 'Login Successful',
                    text: 'You have successfully logged in!',
                    confirmButtonText: 'Go to Dashboard'
                }).then((result) => {
                    if (result.isConfirmed) {
                        // Redirect based on user role
                        if (userRole === 'ROLE_ADMIN') {
                            window.location.href = '/admindashboard.html'; // Admin dashboard
                        } else if (userRole === 'ROLE_USER') {
                            window.location.href = '/userdashboard.html'; // User dashboard
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: 'Role Not Recognized',
                                text: 'Unrecognized role. Please contact support.'
                            });
                        }
                    }
                });
            } else {
                console.error('Login failed:', data);
                Swal.fire({
                    icon: 'error',
                    title: 'Login Failed',
                    text: 'Please check your credentials and try again.'
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'An error occurred. Please try again.'
            });
        });
});
