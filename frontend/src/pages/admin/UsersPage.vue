<script setup>
import { api } from "boot/axios";
import { ref } from "vue";

const userColumns = [
  {
    name: "userID",
    label: "ID",
    field: "userID",
    align: "left",
    sortable: true,
  },
  {
    name: "userName",
    label: "User Name",
    field: "userName",
    align: "left",
    sortable: true,
  },
  {
    name: "email",
    label: "Email",
    field: "email",
    align: "left",
    sortable: true,
  },
  {
    name: "userPassword",
    label: "Password",
    field: "userPassword",
    align: "left",
  },
  {
    name: "registrationDate",
    label: "Registration Date",
    field: "registrationDate",
    align: "left",
  },
  {
    name: "actions",
    label: "Actions",
    align: "left",
  },
];

const users = ref([]);
const isWriter = ref(true);

api
  .get("/users")
  .then((response) => {
    users.value = response.data.users;
    console.log(response.data);
  })
  .catch((error) => {
    console.log(error);
  });

function becomeWriter() {
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
      <q-table :columns="userColumns" :rows="users" row-key="userID">
        <template v-slot:body-cell-actions="props">
          <q-td :props="props">
            <template v-if="!isWriter">
              <q-btn
                color="primary"
                flat
                @click="becomeWriter(props.row.userID)"
                label="Become Writer"
              />
            </template>
            <template v-else>
              <p>Writer</p>
            </template>
          </q-td>
        </template>
      </q-table>
    </q-card>
  </q-page>
</template>

<style lang="scss" scoped>
.users-container {
  margin-top: 30px;
}
</style>
