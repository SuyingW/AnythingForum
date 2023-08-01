<script setup>
import { api } from 'boot/axios'
import { ref } from 'vue'

const email = ref('')
const password = ref('')

function handleLogin() {
  api.post('/login', {
    email: email.value,
    password: password.value
  }).then((response) => {
    console.log(response)
  }).catch((error) => {
    console.log(error)
  })
}
</script>

<template>
  <q-page>
    <div class="login-container">
      <q-card class="login-card">
        <q-card-section>
          <div class="text-h6">Login</div>
          <q-input class="q-my-md" outlined v-model="email" label="Email" />
          <q-input class="q-my-md" outlined v-model="password" label="Password" />
          <q-btn class="q-mt-md" color="primary" label="Login" @click="handleLogin" />
        </q-card-section>
      </q-card>
    </div>
  </q-page>
</template>

<style lang="scss" scoped>
.login-container {
  width: 100%;
  height: calc(100vh - 60px);
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;

  .login-card {
    width: 600px;
    height: 300px;
  }
}
</style>