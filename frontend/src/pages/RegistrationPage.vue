<script setup>
import { api } from "boot/axios";
import { ref } from "vue";

const email = ref("");
const userPassword = ref("");
const userName = ref("");

function handleRegistration() {
  api.post("/registration", {
      email: email.value,
      userName: userName.value,
      userPassword: userPassword.value,
    })
    .then((response) => {
      console.log(response);
    })
    .catch((error) => {
      console.log(error);
    });
}
</script>

<template>
  <q-page>
    <div class="registration-container">
      <q-card class="registration-card">
        <q-card-section>
          <div class="tex-h6">Registration</div>
          <q-input class="q-my-md" outlined v-model="email" label="Email" />
          <q-input
            class="q-my-md"
            outlined
            v-model="userName"
            label="username"
          />
          <q-input
            class="q-my-md"
            outlined
            v-model="userPassword"
            label="Password"
          />
          <q-btn
            class="q-mt-md"
            color="primary"
            label="Sign Up"
            @click="handleRegistration"
          />
        </q-card-section>
      </q-card>
    </div>
  </q-page>
</template>

<style lang="scss" scoped>
.registration-container {
  width: 100%;
  height: calc(100vh - 60px);
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;

  .registration-card {
    width: 600px;
    height: 300px;
  }
}
</style>
