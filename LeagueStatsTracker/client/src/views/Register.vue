<script>
import loginService from '../services/login-service';
export default {
    data() {
        return {
            newAccount: {
                username: '',
                email: '',
                password: ''
            },
            passwordConfirm: '',
            errorMessage: null
        }
    },
    methods: {
        register() {
            if (this.newAccount.password !== this.passwordConfirm) {
                this.errorMessage = 'Passwords do not match';
                return;
            }
            loginService.register(this.newAccount).then(registerResult => {
                if (!registerResult || registerResult.error) {
                    this.errorMessage = 'Error registering account';
                } else {
                    this.$router.push('/');
                }
            });
        }
    }
}
</script>

<template>
    <form @submit.prevent="register" class="register-form">
        <h1>Register</h1>
        <p class="error-message" v-if="errorMessage">{{ errorMessage }}</p>

        <fieldset class="form-fieldset">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" v-model="newAccount.username" required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" v-model="newAccount.email" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" v-model="newAccount.password" required>
            </div>
            <div class="form-group">
                <label for="confirm-password">Confirm Password</label>
                <input type="password" id="confirm-password" v-model="passwordConfirm" required>
            </div>
            <button type="submit" class="register-button">Register</button>
        </fieldset>
        <RouterLink to="/login" class="back-to-login">Back to Login</RouterLink>
    </form>
</template>

<style scoped>

    h1 {
        color: black;
    }
    .register-form {
        max-width: 500px;
        margin: 40px auto;
        padding: 30px;
        background-color: #f9f9f9;
        border: 1px solid #ddd;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 1rem;
    }

    .error-message {
        color: red;
        font-size: 14px;
        margin-bottom: 10px;
    }

    .form-fieldset {
        border: none;
        padding: 20px;
    }

    .form-group {
        margin-bottom: 20px;
    }

    label {
        display: block;
        margin-bottom: 10px;
        font-weight: bold;
    }

    input[type="text"], input[type="email"], input[type="password"] {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    .register-button {
        width: 100%;
        padding: 10px;
        background-color: #337ab7;
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    .back-to-login {
        margin-top: 20px;
        font-size: 14px;
        color: #337ab7;
        text-decoration: none;
    }

    @media (max-width: 425px) {
        .register-form {
            width: 100%;
            margin: 20px auto;
            padding: 20px;
        }
    }
</style>