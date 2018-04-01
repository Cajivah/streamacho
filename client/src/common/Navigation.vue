<template>
<nav class="navbar is-fixed-top" role="navigation" aria-label="main navigation">
    <div class="navbar-shadow"></div>
    <div class="container">
        <div class="navbar-brand">
            <router-link class="navbar-item" to="register">Register</router-link>
            <router-link class="navbar-item" to="login">Login</router-link>
            <div class="navbar-burger">
                <span></span>
                <span></span>
                <span></span>
            </div>
        </div>
    </div>
</nav>
</template>

<script>
export default {
    name: 'navigation'
}
</script>

<style scoped>
</style>
