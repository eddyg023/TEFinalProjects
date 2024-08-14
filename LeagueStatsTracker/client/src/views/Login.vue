<script>
import loginService from '../services/login-service';
export default {
    data() {
        return {
            username: '',
            password: '',
            rememberMe: false,
            errorMessage: null
        }
    },
    methods: {
        login() {
            loginService.login(this.username, this.password, this.rememberMe).then(loginResult => {
                if (!loginResult) {
                    this.errorMessage = 'Invalid username or password';
                } else {
                    this.$router.push('/');
                }
            });
        }
    }
}
</script>

<template>
    <form @submit.prevent="login" class="login-form">
        <h1>Login</h1>
        <p class="error-message" v-if="errorMessage">{{ errorMessage }}</p>

        <fieldset class="form-fieldset">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" v-model="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" v-model="password" required>
            </div>
            <div class="form-group">
                <label for="remember-me">
                    <input type="checkbox" id="remember-me" v-model="rememberMe">
                    Remember me
                </label>
            </div>
            <button type="submit" class="login-button">Login</button>
        </fieldset>
        <RouterLink to="/register" class="register-link">Register a new account</RouterLink>
    </form>
</template>

<style scoped>

    h1 {
        color: black;
    }
    
    .login-form {
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

    input[type="text"], input[type="password"] {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    .login-button {
        width: 100%;
        padding: 10px;
        background-color: #337ab7;
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    .register-link {
        margin-top: 20px;
        font-size: 14px;
        color: #337ab7;
        text-decoration: none;
    }

    #remember-me:hover {
        cursor: pointer;
    }

    @media (max-width: 425px) {
        .login-form {
            width: 100%;
            margin: 20px auto;
            padding: 20px;
        }
    }
</style>