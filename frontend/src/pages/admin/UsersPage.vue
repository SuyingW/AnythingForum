<script setup>
import { api } from "boot/axios";
import { ref } from "vue";

/*
const users = ref([
  { userID: "1234", userName: "Diana" },
  { userID: "2345", userName: "Suying" },
]);
*/

const users = ref([]);

api
  .get("/users")
  .then((response) => {
    users.value = response.data;
    console.log(response.data);
  })
  .catch((error) => {
    console.log(error);
  });

function handleWriter() {
  api
    .post("/becomeWriter", {
      userID: userID.value,
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
  <q-page padding>
    <div class="text-h5">Users</div>

    <q-card class="users-container">
      <q-list bordered>
        <q-item v-for="user in users" :key="user.userID">
          <q-item-section>
            <q-item-label>{{ user.userName }}</q-item-label>
            <q-item-label caption lines="1">{{ user.userID }}</q-item-label>
          </q-item-section>

          <q-item-section side>
            <q-toggle
              v-model="active"
              label="become writer"
              @change="handleWriter(user)"
            />
          </q-item-section>
        </q-item>
      </q-list>
    </q-card>
  </q-page>
</template>

<style lang="scss" scoped>
.users-container {
  margin-top: 30px;
}
</style>
